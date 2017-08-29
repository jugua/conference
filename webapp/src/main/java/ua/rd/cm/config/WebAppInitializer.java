package ua.rd.cm.config;

import javax.servlet.Filter;

import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author Yaroslav_Revin
 */
@Order(2)
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer/*WebApplicationInitializer*/ {

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        OpenEntityManagerInViewFilter openEntityManagerInViewFilter = new OpenEntityManagerInViewFilter();
        return new Filter[]{characterEncodingFilter, openEntityManagerInViewFilter};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                SecurityConfig.class,
                ServiceConfig.class,
                RepositoryConfig.class,
                AsyncConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
