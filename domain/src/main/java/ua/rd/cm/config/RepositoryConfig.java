package ua.rd.cm.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "ua.rd.cm.repository")
@EnableTransactionManagement
@PropertySource("classpath:jdbc.properties")
public class RepositoryConfig {
    private static final String DRIVER_PROP = "driver";
    private static final String URL_PROP = "url";
    private static final String USER_PROP = "user";
    private static final String PASSWORD_PROP = "password";

    @Bean(destroyMethod = "close")
    public DataSource dataSource(Environment environment) {
        BasicDataSource ds = new BasicDataSource();

        ds.setDriverClassName(environment.getProperty(DRIVER_PROP));
        ds.setUrl(environment.getProperty(URL_PROP));
        ds.setUsername(environment.getProperty(USER_PROP));
        ds.setPassword(environment.getProperty(PASSWORD_PROP));
        return ds;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public AbstractEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                 JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean emf =
                new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("ua.rd.cm.domain");

        return emf;
    }
}