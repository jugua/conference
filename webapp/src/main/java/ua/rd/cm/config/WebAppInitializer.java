package ua.rd.cm.config;

import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

import javax.servlet.*;
import java.util.Arrays;

/**
 * @author Yaroslav_Revin
 */
@Order(2)
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer/*WebApplicationInitializer*/{
    private Logger logger = Logger.getLogger(WebAppInitializer.class);

    /*@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext context = setUpContext(servletContext);
        logger.info("LOADED BEANS " + Arrays.toString(context.getBeanDefinitionNames()));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
    }

    private AnnotationConfigWebApplicationContext setUpContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class, SecurityConfig.class);
        context.setServletContext(servletContext);
        context.refresh();
        return context;
    }*/
    
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        OpenEntityManagerInViewFilter openEntityManagerInViewFilter = new OpenEntityManagerInViewFilter();
        return new Filter[] { characterEncodingFilter, openEntityManagerInViewFilter };
    }
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
                SecurityConfig.class,
                ServiceConfig.class,
                RepositoryConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebMvcConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

}
