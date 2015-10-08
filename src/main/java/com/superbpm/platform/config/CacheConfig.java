package com.superbpm.platform.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

@Order(3)
@Configuration
@EnableCaching
public class CacheConfig {
    @Bean(name = "ehcache")
    public static EhCacheCacheManager ehcache() {
        return new EhCacheCacheManager(cacheManager().getObject());
    }

    @Bean(name = "cacheManager")
    public static EhCacheManagerFactoryBean cacheManager() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache/ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }
}
