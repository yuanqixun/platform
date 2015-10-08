/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  web.config.DaoConfig.java
 * 功能: cpframework框架
 * 版本:	@version 1.0
 * 编制日期: 2014年9月3日 下午2:55:14
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2014年9月3日    |    Administrator     |     Created
 */
package com.superbpm.platform.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * 扫描注册@Service标注的服务
 */
@Configuration
@MapperScan(basePackages = "com.superbpm.platform.dao")
@ComponentScan(basePackages = "com.superbpm.platform",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class})})
public class PlatformConfig implements TransactionManagementConfigurer {

  private static final Logger logger = Logger.getLogger(PlatformConfig.class);

  @Value("${platform.jdbc.driverClassName}")
  protected String driverClassName;

  @Value("${platform.jdbc.url}")
  protected String url;

  @Value("${platform.jdbc.user}")
  protected String username;

  @Value("${platform.jdbc.pass}")
  protected String password;

  @Value("${platform.jdbc.maxActive}")
  protected int maxActive;

  @Value("${platform.jdbc.minIdle}")
  protected int minIdle;

  @Value("${hibernate.hbm2ddl.auto}")
  protected String hbm2ddl;

  @Value("${hibernate.show_sql}")
  protected boolean showSql;

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean(name = "platform.dataSource")
  public DataSource dataSource() {
    logger.info("platform.dataSource");
    DruidDataSource ds = new DruidDataSource();
    ds.setUrl(url);
    ds.setDriverClassName(driverClassName);
    ds.setUsername(username);
    ds.setPassword(password);
    ds.setMaxActive(maxActive);
    ds.setMinIdle(minIdle);
    return ds;
  }

  @Override
  public PlatformTransactionManager annotationDrivenTransactionManager() {
    return transactionManager();
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    DataSourceTransactionManager txManager = new DataSourceTransactionManager();
    txManager.setDataSource(dataSource());
    return txManager;
  }

  @Bean(name = "sessionFactory")
  public SqlSessionFactory setupSqlSessionFactory() throws Exception {
    logger.info("sessionFactory");
    final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    sessionFactory.setMapperLocations(resolver.getResources("classpath*:mybatis/**/*Mapper.xml"));
    return sessionFactory.getObject();

  }

}
