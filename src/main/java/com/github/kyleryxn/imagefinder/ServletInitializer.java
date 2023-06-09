package com.github.kyleryxn.imagefinder;

import com.github.kyleryxn.imagefinder.config.SpringWebConfig;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * The `ServletInitializer` class is responsible for configuring the {@link DispatcherServlet}
 * for <a href="https://www.spring.io">Spring</a> MVC web application initialization. It extends the `{@link AbstractAnnotationConfigDispatcherServletInitializer}`
 * abstract class provided by Spring framework.
 *
 * This class overrides three methods: `getRootConfigClasses()`, `getServletConfigClasses()`, and `getServletMappings()`
 * to configure the root application context, servlet application context, and servlet mappings respectively.
 */
public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Returns the configuration classes for the root application context.
     *
     * @return an array of configuration classes for the root application context
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    /**
     * Returns the configuration classes for the servlet application context.
     *
     * @return an array of configuration classes for the servlet application context
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringWebConfig.class};
    }

    /**
     * Returns the servlet mappings for the {@link DispatcherServlet}.
     *
     * @return an array of servlet mappings for the DispatcherServlet
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
