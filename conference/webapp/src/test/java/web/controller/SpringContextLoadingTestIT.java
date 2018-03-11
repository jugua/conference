package web.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.config.RepositoryConfig;
import service.config.ServiceConfig;
import web.config.SecurityConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        RepositoryConfig.class,
        ServiceConfig.class,
        SecurityConfig.class
})
public class SpringContextLoadingTestIT {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void dummyTest() {
        assertNotNull(applicationContext.getBean("securityConfig"));
    }

}
