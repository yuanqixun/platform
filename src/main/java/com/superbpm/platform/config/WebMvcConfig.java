package com.superbpm.platform.config;

import com.superbpm.platform.utils.PathMatchingResourceBundleMessageSource;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.Properties;

@Configuration
@ComponentScan(
        basePackages = "com.superbpm.platform.controller",
        useDefaultFilters = false,
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class})}
)
@AutoConfigureAfter(PlatformConfig.class)
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  private static final Logger logger = Logger
          .getLogger(WebMvcConfig.class);

  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  /**
   * 视图设置
   */
  @Bean(name = "viewResolver")
  public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/jsp/");
    resolver.setSuffix(".jsp");
    resolver.setViewClass(JstlView.class);
    resolver.setOrder(2);
    return resolver;
  }

  @Bean(name = "freeMarkerViewResolver")
  public FreeMarkerViewResolver freeMarkerViewResolver() {
    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
    resolver.setSuffix(".ftl");
    resolver.setPrefix("");
    resolver.setContentType("text/html; charset=UTF-8");
    resolver.setCache(false);
    resolver.setRequestContextAttribute("request");
    resolver.setExposeRequestAttributes(true);
    resolver.setExposePathVariables(true);
    resolver.setExposeSessionAttributes(true);
    resolver.setOrder(1);
    return resolver;
  }

  @Bean(name = "freeMarkerConfigurer")
  public FreeMarkerConfigurer freeMarkerConfigurer() {
    FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
    configurer.setTemplateLoaderPath("classpath:/views/ftl");
    configurer.setPreferFileSystemAccess(false);
    Properties settings = new Properties();
    settings.setProperty("auto_import", "include/spring.ftl as spring");
    settings.setProperty("default_encoding", "UTF-8");
    settings.setProperty("locale", "zh_CN");
    configurer.setFreemarkerSettings(settings);
    return configurer;
  }

  @Bean
  public MessageSource messageSource() {
    PathMatchingResourceBundleMessageSource messageSource = new PathMatchingResourceBundleMessageSource();
    messageSource.setBasenames("classpath*:/i18n/messages*","classpath*:/META-INF/message*");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
  }

  @Bean(name = "multipartResolver")
  public CommonsMultipartResolver commonsMultipartResolver() {
    return new CommonsMultipartResolver();
  }

  //---------注册filter和servlet
//  @Bean(name = "springFilterChain")
//  public FilterRegistrationBean springFilterChain() {
//    FilterRegistrationBean bean = new FilterRegistrationBean();
//    bean.setFilter(new DelegatingFilterProxy());
//    bean.addUrlPatterns("/*");
//    bean.setOrder(1);
//    return bean;
//  }

//  @Bean(name = "sitemeshFilter")
//  public FilterRegistrationBean sitemeshFilter() {
//    FilterRegistrationBean bean = new FilterRegistrationBean();
//    bean.setFilter(new SitemeshFilter());
//    bean.addUrlPatterns("/*");
//    bean.setOrder(1111);
//    return bean;
//  }
//
//  @Bean(name = "captchaServlet")
//  public ServletRegistrationBean captchaServlet() {
//    ServletRegistrationBean bean = new ServletRegistrationBean();
//    bean.setServlet(new CaptchaServlet());
//    bean.addUrlMappings("/captcha");
//    return bean;
//  }

}

