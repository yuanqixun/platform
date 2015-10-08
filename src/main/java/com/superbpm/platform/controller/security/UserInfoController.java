package com.superbpm.platform.controller.security;

import com.superbpm.platform.service.oauth2.OAuthService;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/security")
public class UserInfoController {

  @Autowired
  private OAuthService oAuthService;

  @RequestMapping("/userInfo")
  public String userInfo(Model model,HttpServletRequest request) throws OAuthSystemException {
    try {

      //构建OAuth资源请求
      OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
      //获取Access Token
      String accessToken = oauthRequest.getAccessToken();

      //验证Access Token
      if (!oAuthService.checkAccessToken(accessToken)) {
        // 如果不存在/过期了，返回未验证错误，需重新验证
        return "redirect:/oauth2/authorize?client_id=279984a1-8338-4182-abad-7a095eb16960&response_type=code&redirect_uri=http://baidu.com";
      }
      //返回用户名
      String username = oAuthService.getUsernameByAccessToken(accessToken);
//      return new ResponseEntity(username, HttpStatus.OK);
      return "redirect:/oauth2/user";
    } catch (OAuthProblemException e) {
      e.printStackTrace();
      return "redirect:/oauth2/authorize?client_id=279984a1-8338-4182-abad-7a095eb16960&response_type=code&redirect_uri=http://baidu.com";
    }
  }
}
