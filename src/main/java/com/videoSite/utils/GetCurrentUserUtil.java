package com.videoSite.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *   通过调用SecurityContextHolder获取当前用户信息
 *   前端通过获取session域对象获取当前用户信息，即[[${session.SPRING_SECURITY_CONTEXT.getAuthentication().getName()}]]
 *   不过如果用户没有登录，写了该方法的页面会出错
 **/
public class GetCurrentUserUtil {

    public static String getCurrentUserName(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return  ((UserDetails)principal).getUsername();
        } else {
            return   principal.toString();
        }
    }
}
