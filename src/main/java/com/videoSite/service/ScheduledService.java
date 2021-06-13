package com.videoSite.service;

import com.videoSite.common.constant.MyVideoPath;
import com.videoSite.utils.VideoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.util.Date;

/**
 *
 **/
@Service
public class ScheduledService {

    @Autowired
    MyVideoPath myVideoPath;
    //在一个特定时间执行定时任务
    /*  cron表达式：
     *    秒    分    时    日    月    周几
     *   30    15    10    *     *      ?    // 代表每天10点15分30s 执行一次
     *   30    0/5  10,18  *     *     WEN   // 代表每个周三的10点和18点，每隔五分钟执行一次
     *   0     15    10    ?     *     1-6  // 代表每个月的周日到周五 10点15分执行一次
     *   注意： 周为具体周时，日要为? ; 还有 周日代表数字1或SUN 周一代表数字2 以此类推
     * */


    @Scheduled(cron = "0 0 3 * * ? ")//代表每天凌晨3点执行一次
    public void deleteTempFile(){
        System.out.println(new Date()+"：开始删除临时文件...");
        VideoUtils.deleteTempFile(myVideoPath.getMYPATH());
        System.out.println("删除临时文件成功");
    }
}
