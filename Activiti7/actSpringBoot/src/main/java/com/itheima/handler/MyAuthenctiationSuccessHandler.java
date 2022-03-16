package com.itheima.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.entity.Result;
import com.itheima.entity.User;
import com.itheima.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component("myAuthSuccessHandler")
public class MyAuthenctiationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private UserService userService;
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
    log.info("###########登录成功");
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    log.info("name="+name);
    User user = userService.findOneUserByName(name);
    request.getSession().setAttribute("userid",user.getId());
    Result result = new Result(200,"登录成功！！");

    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(result));
  }
}

