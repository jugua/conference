package ua.rd.cm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Yaroslav_Revin
 */
@Configuration
@ComponentScan(basePackages = "ua.rd.cm.repository")
@PropertySources({
        @PropertySource("classpath:jdbc.properties"),
        @PropertySource("classpath:persistence.properties")
})
@EnableTransactionManagement
public class RepositoryConfig {
    
    @Value("#{driver}")
    private String driver;
    
    @Value("#{url}")
    private String url;
    
    @Value("#{user}")
    private String user;
    
    @Value("#{password}")
    private String password;
    
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        	
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        
        return ds;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public Properties jpaProperties() {
        Properties properties = new Properties();

//        properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
//        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//        properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//        properties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));

        return properties;
    }

    @Bean
    public AbstractEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                 JpaVendorAdapter jpaVendorAdapter,
                                                                 Properties jpaProperties) {
        LocalContainerEntityManagerFactoryBean emf =
                new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("ua.rd.cm.domain");
        emf.setJpaProperties(jpaProperties);

        return emf;
    }
}