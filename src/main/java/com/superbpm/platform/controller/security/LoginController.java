package com.superbpm.platform.controller.security;

import com.superbpm.platform.service.security.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.Map;

/**
 * Created by yqx on 9/16/15.
 */
@Controller
public class LoginController {

  @Autowired
  private UserService userService;

  @RequestMapping(value="login",method = {RequestMethod.GET})
  public String loginGet(Map<String, Object> model) {
    model.put("username","");
    return "login";
  }

  @RequestMapping(value="login",method = {RequestMethod.POST})
  public String loginPost(Map<String,String> model,
                          @RequestParam("loginName") String username,
                          @RequestParam("loginPass") String password,
                          RedirectAttributesModelMap modelMap) {
    String error = null;
    model.put("username", username);
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    try {
      subject.login(token);
    } catch (UnknownAccountException e) {
      error = "用户名/密码错误";
    } catch (IncorrectCredentialsException e) {
      error = "用户名/密码错误";
    } catch (AuthenticationException e) {
      //其他错误，比如锁定，如果想单独处理请单独catch处理
      error = "其他错误：" + e.getMessage();
    }
    if(StringUtils.isBlank(error)){
      //传参
      modelMap.addFlashAttribute("username",username);
      return ("redirect:/");
    } else {
      model.put("error", error);
      return "login";
    }
  }

  /**
   * 首页
   * @param model
   * @return
   */
  @RequestMapping(value="/",method = {RequestMethod.GET})
  public String loginSuccess(Map<String, Object> model) {
    if(!model.containsKey("username")){
      Subject subject = SecurityUtils.getSubject();
      if(subject != null){
        model.put("username",subject.getPrincipal());
      }
    }
    return "index";
  }
}
