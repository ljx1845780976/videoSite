package com.videoSite.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.videoSite.entity.User;
import com.videoSite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author 关注公众号：JxHub
 * @since 2021-03-10
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
   @GetMapping("/getUsersInfo/{username}")
   @ResponseBody
   public User getUsersByName(@PathVariable("username") String username){
     User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
     return user;
   }

   @GetMapping("/setSignature/{username}/{signature}")
   @ResponseBody
   public void setSignature(@PathVariable("username") String username,
                           @PathVariable("signature") String signature){
      User user=new User();
      user.setSignature(signature);
      userService.update(user,new QueryWrapper<User>().eq("username",username));
 }

   @GetMapping("/toHome/{username}")
   public String toHome(@PathVariable ("username") String username, ModelMap modelMap){
       modelMap.addAttribute("username",username);
       return "user/home";
   }

   @GetMapping("/toFans/{username}")
   public String toFans(@PathVariable ("username") String username, ModelMap modelMap){
       modelMap.addAttribute("username",username);
        return "user/fans";
   }

   @GetMapping("/toFocus/{username}")
   public String toFocus(@PathVariable ("username") String username, ModelMap modelMap){
       modelMap.addAttribute("username",username);
       return "user/focus";
   }

   @GetMapping("/toVideos/{username}")
   public String toVideos(@PathVariable ("username") String username, ModelMap modelMap){
       modelMap.addAttribute("username",username);
       return "user/videos";
   }
   @GetMapping("/toCollection/{username}")
   public String toCollection(@PathVariable ("username") String username, ModelMap modelMap){
       modelMap.addAttribute("username",username);
       return "user/collection";
   }

}
