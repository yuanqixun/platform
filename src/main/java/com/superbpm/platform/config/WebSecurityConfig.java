package com.superbpm.platform.config;

import com.superbpm.platform.security.ShiroDefinitionSource;
import com.superbpm.platform.security.UserRealm;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@AutoConfigureAfter(WebMvcConfig.class)
public class WebSecurityConfig {
  private static Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

  @Bean
  public ShiroFilterFactoryBean shiroFilter() throws Exception {
    logger.debug("create shiro filter.");
    Map<String, Filter> filters = new HashMap<String, Filter>();
    FormAuthenticationFilter authenticationFilter = new FormAuthenticationFilter();
    authenticationFilter.setUsernameParam("loginName");
    authenticationFilter.setPasswordParam("loginPass");
    filters.put("authc", authenticationFilter);

    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    ShiroDefinitionSource shiroDefinitionSource = shiroDefinitionSource();
    Ini.Section main = shiroDefinitionSource.getMain();
    if(main != null){
      shiroFilterFactoryBean.setLoginUrl(main.get("authc.loginUrl"));
      shiroFilterFactoryBean.setSuccessUrl(main.get("authc.successUrl"));
      shiroFilterFactoryBean.setUnauthorizedUrl(main.get("authc.unauthorizedUrl"));
    }else {
      shiroFilterFactoryBean.setLoginUrl("/login");
      shiroFilterFactoryBean.setSuccessUrl("/");
      shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
    }
    shiroFilterFactoryBean.setFilters(filters);
    shiroFilterFactoryBean.setSecurityManager(securityManager());
    //加载classpath路径的shiro.ini配置文件
    shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroDefinitionSource.getUrls());
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionsMap());
    return shiroFilterFactoryBean;
  }

  @Bean(name = "securityManager")
  public org.apache.shiro.mgt.SecurityManager securityManager() {
    logger.debug("create security manager.");

    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(realm());
//        securityManager.setSessionManager(sessionManager());
//    securityManager.setRememberMeManager(rememberMeManager());
//        securityManager.setCacheManager(cacheManager());
    return securityManager;
  }

//    @Bean(name = "sessionManager")
//    public ValidatingSessionManager sessionManager() {
//        logger.debug("create session manager.");
//
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setGlobalSessionTimeout(1800000);
//        sessionManager.setSessionDAO(shiroSessionDao());
//        sessionManager.setCacheManager(cacheManager());
//        return sessionManager;
//    }

//    @Bean(name = "shiroSessionDao")
//    public SessionDAO shiroSessionDao() {
//        logger.debug("create session dao.");
//
//        SecuritySessionDao sessionDao = new SecuritySessionDao();
//        sessionDao.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
//        return sessionDao;
//    }

  @Bean(name = "realm")
  @DependsOn("lifecycleBeanPostProcessor")
  public AuthorizingRealm realm() {
    return new UserRealm();
  }

//    @Bean(name = "shiroCacheManager")
//    public CacheManager cacheManager() {
//        logger.debug("create cache mamager.");
//        EhCacheManager cacheManager = new EhCacheManager();
//        cacheManager.setCacheManager(CacheConfig.cacheManager().getObject());
//        return cacheManager;
//    }

  @Bean
  public RememberMeManager rememberMeManager() {
    logger.debug("create remember me manager.");

    SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
    simpleCookie.setHttpOnly(true);
    simpleCookie.setMaxAge(2592000);

    CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
    rememberMeManager.setCookie(simpleCookie);
    return rememberMeManager;
  }

  @Bean
  public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  @Bean
  @DependsOn("lifecycleBeanPostProcessor")
  public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
    advisorAutoProxyCreator.setProxyTargetClass(true);
    return advisorAutoProxyCreator;
  }

  @Bean
  public ShiroDefinitionSource shiroDefinitionSource() {
    return new ShiroDefinitionSource();
  }

//  @Bean
//  public Map<String, String> filterChainDefinitionsMap() {
//    ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder<String, String>()
//            .put("/static*//**", "anon")
//            .put("/api*//**", "anon")
//            .put("/oauth2*//**", "anon")
//            .put("/login", "anon")
//            .put("/logout", "logout")
//            .put("/error", "anon")
//            .put("*/**", "myauthc");
//    return mapBuilder.build();
//  }
}
