package com.videoSite.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.videoSite.common.dto.SignUpDto;
import com.videoSite.entity.User;
import com.videoSite.service.UserService;
import com.videoSite.utils.MD5utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 关注公众号：JxHub
 * @since 2021-03-22
 **/
@Controller
public class Login_RegisterController {

    private static final String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    @Autowired
    UserService userService;
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/userLogin")
    public String login(){
        return "login";
    }

    @PostMapping ("/save")
    public String save( SignUpDto signUpDto, ModelMap modelMap){
        User user=userService.getOne(new QueryWrapper<User>().eq("username",signUpDto.getUsername()));
        if (user!=null) {
            modelMap.addAttribute("repeated_username",signUpDto.getUsername());
            return "register";

        }
        User email=userService.getOne(new QueryWrapper<User>().eq("email",signUpDto.getEmail()));
        if(email!=null){
            modelMap.addAttribute("repeated_email",signUpDto.getEmail());
            return "register";
        }
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(signUpDto.getEmail());
        if(!m.matches()){
            modelMap.addAttribute("invalid_email",signUpDto.getEmail());
            return "register";
        }
        user=new User();
        BeanUtils.copyProperties(signUpDto, user);
        user.setPassword(MD5utils.encode(signUpDto.getPassword()));
        user.setStatus(1);
        userService.save(user);
        modelMap.addAttribute("newUsername", user.getUsername());
        modelMap.addAttribute("newPassword", signUpDto.getPassword());
        return "login";
    }

    @GetMapping("/logOut")
    public String logOut(){
        return "login";
    }

    @GetMapping("/manager")
    public String manager(){
        return "manager";
    }
}
