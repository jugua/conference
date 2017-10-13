package ua.rd.cm.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ua.rd.cm.repository")
@PropertySource("classpath:default/jdbc.properties")
public class RepositoryConfig {

    @Bean(destroyMethod = "close")
    public DataSource dataSource(Environment environment) {
        DataSource dataSource = jndiDataSource();
        if (dataSource == null) {
            dataSource = embeddedDataSource(environment);
        }

        return dataSource;
    }

    private DataSource jndiDataSource() {
        DataSource dataSource = null;

        String jndiDs = "jdbc/conference";
        log().info("Using jndi datasource {}", jndiDs);
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        try {
            dataSource = dsLookup.getDataSource(jndiDs);
        } catch (Exception e) {
            log().warn("Failed to load datasource {}", jndiDs);
        }
        return dataSource;
    }

    private DataSource embeddedDataSource(Environment environment) {
        DataSource ds;
        HikariConfig h2Config = new HikariConfig();
        h2Config.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        h2Config.setJdbcUrl(environment.getProperty("jdbc.url"));
        h2Config.setUsername(environment.getProperty("jdbc.username"));
        h2Config.setPassword(environment.getProperty("jdbc.password"));

        h2Config.setMinimumIdle(environment.getProperty("jdbc.minimumIdle", Integer.class, 5));
        h2Config.setMaximumPoolSize(environment.getProperty("jdbc.maximumPoolSize", Integer.class, 10));
        h2Config.setIdleTimeout(environment.getProperty("jdbc.idleTimeout", Long.class, 1800L));

        h2Config.addDataSourceProperty("jdbc.cachePrepStmts", "true");
        h2Config.addDataSourceProperty("jdbc.prepStmtCacheSize", "250");
        h2Config.addDataSourceProperty("jdbc.prepStmtCacheSqlLimit", "2048");

        String connectionTestQuery = environment.getProperty("jdbc.connectionTestQuery");
        if (connectionTestQuery != null) {
            h2Config.setConnectionTestQuery(connectionTestQuery);
        }
        ds = new HikariDataSource(h2Config);
        return ds;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public org.h2.tools.Server h2WebConsoleServer() throws SQLException {
        return org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webDaemon", "-webPort", "8083");
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public AbstractEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                 JpaVendorAdapter jpaVendorAdapter,
                                                                 Environment environment) {
        LocalContainerEntityManagerFactoryBean emf =
                new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("ua.rd.cm.domain");

        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));

        emf.setJpaProperties(props);
        return emf;
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    private Logger log() {
        return LazyHolder.LOG;
    }

    private static class LazyHolder {
        private static final Logger LOG = LoggerFactory.getLogger(RepositoryConfig.class);
    }

}