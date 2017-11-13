package domain.config;

import static org.hibernate.cfg.AvailableSettings.PHYSICAL_NAMING_STRATEGY;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;

@Configuration
@EnableTransactionManagement
@DirtiesContext
@EnableJpaRepositories(basePackages = "domain.repository")
public class InMemoryRepositoryConfig {

    @Bean
    public DataSource dataSourceSpied() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public DataSource dataSource(DataSource dataSourceSpied) {
        Log4jdbcProxyDataSource ds = new Log4jdbcProxyDataSource(dataSourceSpied);
        Log4JdbcCustomFormatter cf = new Log4JdbcCustomFormatter();

        cf.setLoggingType(LoggingType.MULTI_LINE);
        cf.setSqlPrefix("SQL::");

        ds.setLogFormatter(cf);

        return ds;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.H2);

        return vendorAdapter;
    }

    @Bean
    public AbstractEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                 JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("domain.model");
        emf.setJpaProperties(ormProperties());

        return emf;
    }

    private Properties ormProperties() {
        Properties props = new Properties();
        props.setProperty(PHYSICAL_NAMING_STRATEGY, ConferencePhysicalNamingStrategy.class.getName());
        return props;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager =
                new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }
}
