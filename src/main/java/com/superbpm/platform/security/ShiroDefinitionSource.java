package com.superbpm.platform.security;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.PostConstruct;

public class ShiroDefinitionSource {

  private Ini ini;

  @PostConstruct
  void afterCreate(){
    ini = Ini.fromResourcePath("classpath:shiro.ini");
  }

  public Ini.Section getUrls() throws Exception {
    if(ini != null)
      return ini.getSection(IniFilterChainResolverFactory.URLS);
    return null;
  }

  public Ini.Section getFilters() throws Exception {
    if(ini != null)
      return ini.getSection(IniFilterChainResolverFactory.FILTERS);
    return null;
  }

  public Ini.Section getMain() throws Exception {
    if(ini != null)
      return ini.getSection(IniSecurityManagerFactory.MAIN_SECTION_NAME);
    return null;
  }



  public boolean isSingleton() {
    return true;
  }

  /**
   * 重新加载配置文件
   */
  public void reloadSecuritySection() {
    ini = Ini.fromResourcePath("classpath:shiro.ini");
  }

}
