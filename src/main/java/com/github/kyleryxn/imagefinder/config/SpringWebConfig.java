package com.github.kyleryxn.imagefinder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * The `SpringWebConfig` class is a configuration class that configures <a href="https://www.spring.io">Spring</a> MVC web application settings,
 * including resource handling, template resolver, template engine, and view resolver.
 *
 * This class is annotated with `@EnableWebMvc` and `@Configuration`, indicating that it provides configuration for
 * Spring Web MVC and it should be considered as a configuration class that can be loaded during application startup.
 * It also scans for components under the package "com.github.kyleryxn.imagefinder" using `@ComponentScan` annotation.
 *
 * This class implements the {@link WebMvcConfigurer} interface, allowing for further customization of Spring MVC
 * configuration.
 */
@EnableWebMvc
@Configuration
@ComponentScan({"com.github.kyleryxn.imagefinder"})
public class SpringWebConfig implements WebMvcConfigurer {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Configures resource handling in the application by adding resource handlers and their corresponding
     * resource locations.
     *
     * @param registry the {@link ResourceHandlerRegistry} to configure resource handling
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/");
        registry.addResourceHandler("/font/**").addResourceLocations("/resources/font/");
        registry.addResourceHandler("/images/**").addResourceLocations("/resources/images/");
    }

    /**
     * Creates and configures a `SpringResourceTemplateResolver` bean, which is used by the <a href="https://www.thymeleaf.org">Thymeleaf</a>
     * template engine to resolve templates.
     *
     * @return the configured {@link SpringResourceTemplateResolver} bean
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(true);

        return templateResolver;
    }

    /**
     * Creates and configures a {@link SpringTemplateEngine} bean, which is the template engine used by <a href="https://www.thymeleaf.org">Thymeleaf</a>
     * to process templates.
     *
     * @return the configured SpringTemplateEngine bean
     */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);

        return templateEngine;
    }

    /**
     * Creates and configures a {@link ThymeleafViewResolver} bean, which is the view resolver used by <a href="https://www.thymeleaf.org">Thymeleaf</a>
     * to resolve views for rendering.
     *
     * @return the configured `ThymeleafViewResolver` bean
     */
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());

        return viewResolver;
    }
}
