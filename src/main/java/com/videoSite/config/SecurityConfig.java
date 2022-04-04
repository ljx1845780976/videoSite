package com.videoSite.config;

import com.videoSite.handler.LoginSuccessHandler;
import com.videoSite.utils.MD5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 *
 **/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
    DataSource dataSource;

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //permitAll()表示所有人可访问，
        //authenticated()表示只有登录了才能访问
        //hasRole()有对应角色的人访问
        http.authorizeRequests()
                .antMatchers("/index").permitAll()
                .antMatchers("/comment/postComment","/notification/**",
                        "/subscription/**","/user/setSignature","/video/addVideo",
                        "/video/setTopVideoStatus","/video/cancelTopVideoStatus").authenticated()
                .antMatchers("/manager").hasRole("ADMIN");


        //没有权限默认去登录页, loginPage定制登录页,defaultSuccessUrl定制成功后跳转的页面
        http.formLogin().loginPage("/userLogin").defaultSuccessUrl("/index").successHandler(new LoginSuccessHandler());

        //开启注销,设置注销成功前往首页;默认跳到登录页
        //取消csrf工具
        http.csrf().disable();
        http.logout().logoutSuccessUrl("/login.html");

        //开启记住我功能，默认创建一个cookie两周过期
        // rememberMeParameter对应前端的name为其里面的内容的参数，
        http.rememberMe().rememberMeParameter("remember");
    }
    //认证
    //要先对密码加密编码PasswordEncoder，即从页面获取的123456会先加密，再和数据库的密码比较
    //因此注册用户时要先对页面获取来的密码进行加密再存入数据库
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new MD5PasswordEncoder())
                //必须为username,password和状态status三个字段，状态为0禁止登录
                .usersByUsernameQuery("select username,password ,status from m_user WHERE username=?")
                .authoritiesByUsernameQuery("select username,role from m_user where username=?")
                ;
    }
}
