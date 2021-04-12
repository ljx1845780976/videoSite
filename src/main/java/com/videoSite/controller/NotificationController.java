package com.videoSite.controller;

import com.videoSite.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 关注公众号：JxHub
 * @since 2021-03-22
 **/
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    RedisUtils redisUtils;
    /*
     *  获取通知信息
     * */
    @GetMapping("/getNotifications/{beNotifier}")
    public List<Object> getNotifications(@PathVariable("beNotifier") String beNotifier){
        List<Object> list = redisUtils.lGet(beNotifier, 0, -1);
        return list;
    }
    @GetMapping("/getNotificationsNum/{beNotifier}")
    public Integer getNotificationsNum(@PathVariable("beNotifier") String beNotifier){
        Integer num = (Integer) redisUtils.get(beNotifier+"_num");
        return num;
    }
    /*
     *  通知信息点击已读
     * */
    @GetMapping("/clearNotifications/{beNotifier}")
    public void clearNotifications(@PathVariable("beNotifier") String beNotifier){
        redisUtils.del(beNotifier);
    }
    @GetMapping("/clearNotificationsNum/{beNotifier}")
    public void clearNotificationsNum(@PathVariable("beNotifier") String beNotifier){
        beNotifier=beNotifier+"_num";
        redisUtils.del(beNotifier);
    }
}
