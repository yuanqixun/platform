package com.superbpm.platform.security;

import com.superbpm.platform.entity.security.User;
import com.superbpm.platform.service.security.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by yqx on 9/21/15.
 */
public class UserRealm extends AuthorizingRealm {

  @Autowired
  CredentialsMatcher credentialsMatcher;

  @Autowired
  UserService userService;

  @PostConstruct
  public void initCredentialsMatcher() {
    setCredentialsMatcher(credentialsMatcher);//new CredentialsMatcher());
  }
    /**
     * 授权
     * @param principals
     * @return
     */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    return null;
  }

  /**
   * 验证
   * @param token
   * @return
   * @throws AuthenticationException
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    UsernamePasswordToken _token = (UsernamePasswordToken) token;
    String username = _token.getUsername();
    User user = userService.findByUsername(username);
    if(user == null) {
      throw new UnknownAccountException();//没找到帐号
    }
    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username,user.getPassword(),ByteSource.Util.bytes(user.getCredentialsSalt()),getName());
    return authenticationInfo;
  }
}
