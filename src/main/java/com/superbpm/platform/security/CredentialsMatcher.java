package com.superbpm.platform.security;

import com.superbpm.platform.service.security.PasswordHelper;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yqx on 10/7/15.
 */
@Component
public class CredentialsMatcher extends SimpleCredentialsMatcher{

  @Autowired
  private PasswordHelper passwordHelper;

  /**
   * 校验密码是否匹配
   * @param authcToken
   * @param info
   * @return
   */
  @Override
  public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
    boolean passed = false;
    UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
    String username = token.getUsername();
    String password = String.valueOf(token.getPassword());
    if(info instanceof SimpleAuthenticationInfo){
      String encryptedPassword  = (String) ((SimpleAuthenticationInfo) info).getCredentials();
      ByteSource salt = ((SimpleAuthenticationInfo) info).getCredentialsSalt();
      passed = encryptedPassword.equals(passwordHelper.encryptPassword(username, password, salt));
    }
    return passed;
  }
}
