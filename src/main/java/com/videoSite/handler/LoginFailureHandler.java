package com.videoSite.handler;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 *    解决登录失败回显信息问题
 **/
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public LoginFailureHandler(){
        this.setDefaultFailureUrl("/userLogin?error=true");
    }
}
