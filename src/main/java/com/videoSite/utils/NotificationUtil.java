package com.videoSite.utils;

import com.videoSite.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 **/
@Component
public class NotificationUtil {
    @Autowired
    RedisUtils redisUtils;
    public void Notify(String beNotifier, Notification notification){
        redisUtils.rSet(beNotifier,notification);
        beNotifier=beNotifier+"_num";
        redisUtils.incr(beNotifier,1);
    }

}
