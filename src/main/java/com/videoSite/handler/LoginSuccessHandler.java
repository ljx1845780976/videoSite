package com.videoSite.handler;/**
 *
 **/

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @introduction
 * @author ljz
 * @date 2022年03月25日 12:52
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
            System.out.println(basePath);
            if (roles.contains("ROLE_ADMIN")) {
                //拥有admin角色的用户登录时进入manage页面
                response.sendRedirect(basePath + "manager");
                return;
            }
            //否则进入index页面
            response.sendRedirect(basePath + "index");
        }

}
