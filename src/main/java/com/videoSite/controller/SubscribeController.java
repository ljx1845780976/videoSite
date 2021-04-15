package com.videoSite.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.videoSite.entity.Notification;
import com.videoSite.entity.Subscribe;
import com.videoSite.service.SubscribeService;
import com.videoSite.utils.GetCurrentUserUtil;
import com.videoSite.utils.NotificationUtil;
import com.videoSite.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：JxHub
 * @since 2021-03-17
 */
@RestController
@RequestMapping("/subscription")
public class SubscribeController {
    @Autowired
    NotificationUtil notificationUtil;
    @Autowired
    SubscribeService subscribeService;
    /*
    *   订阅
    * */
    @GetMapping("/subscribe/{youtuber}")
    public void subscribe(@PathVariable("youtuber" ) String youtuber){
        String currentSubscriber=GetCurrentUserUtil.getCurrentUserName();
        Map<String,Object> map=new HashMap<>();
        map.put("youtuber",youtuber);
        map.put("subscriber",currentSubscriber);
        //该订阅关系不存在才保存
        if(subscribeService.getOne(new QueryWrapper<Subscribe>().allEq(map))==null){
        Subscribe subscribe=new Subscribe();
        subscribe.setSubscriber(currentSubscriber);
        subscribe.setYoutuber(youtuber);
        subscribe.setSubscribe_time(new Date());
        System.out.println("订阅成功");
        //设置订阅该youtuber的 youtuber名:订阅者名 的键值对
         String url="/user/toHome/"+currentSubscriber;
         Notification notification=new Notification(currentSubscriber,youtuber,new Date(),url,"关注了你");
         notificationUtil.Notify(youtuber,notification);
         subscribeService.save(subscribe);
        }
        else {
            System.out.println("已订阅，不必重复订阅");
        }

    }
    /*
    *   取消订阅
    * */
    @GetMapping("/undo_subscribe/{youtuber}")
    public void undo_subscribe(@PathVariable("youtuber" ) String youtuber){

        Map<String,Object> map=new HashMap<>();
        map.put("youtuber",youtuber);
        map.put("subscriber",GetCurrentUserUtil.getCurrentUserName());
        System.out.println("取消订阅成功");
        subscribeService.remove(new QueryWrapper<Subscribe>().allEq(map));

    }
    /*
    *  查询当前订阅状态
    * */
    @GetMapping("/status/{youtuber}/{subscriber}")
    public Integer status(@PathVariable("youtuber" ) String youtuber,
                          @PathVariable("subscriber")String subscriber ){
        Map<String,Object> map=new HashMap<>();
        map.put("youtuber",youtuber);
        map.put("subscriber",subscriber);
        Subscribe one = subscribeService.getOne(new QueryWrapper<Subscribe>().allEq(map));
        if (one!=null){
            return 0;
        }
        else {
            return 1;
        }
    }
    /*
     *  查询已关注
     * */
    @GetMapping("/search_focus/{subscriber}")
    public List<Subscribe> getYoutubers(@PathVariable("subscriber") String subscriber){
         List<Subscribe> youtubers=subscribeService.list(new QueryWrapper<Subscribe>().eq("subscriber",subscriber));
         return youtubers;
    }
    /*
    *   查询粉丝
    * */
    @GetMapping("/search_fans/{youtuber}")
    public List<Subscribe> getSubscribers(@PathVariable ("youtuber")String youtuber){
        List<Subscribe> youtubers=subscribeService.list(new QueryWrapper<Subscribe>().eq("youtuber",youtuber));
        return youtubers;
    }

}
