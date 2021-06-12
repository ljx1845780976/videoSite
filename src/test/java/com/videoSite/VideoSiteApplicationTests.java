package com.videoSite;

import com.videoSite.common.constant.MyVideoPath;
import com.videoSite.entity.Video;
import com.videoSite.service.SubscribeService;
import com.videoSite.service.VideoService;
import com.videoSite.service.UserService;
import com.videoSite.utils.RedisUtils;
import com.videoSite.utils.VideoUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class VideoSiteApplicationTests {
	@Autowired
	VideoService videoService;
	@Autowired
	MyVideoPath myVideoPath;
	@Test
	void contextLoads() {
		System.out.println(VideoUtils.getVideoTime("C:\\Users\\非洲吴彦祖\\Desktop\\final.mp4"));
		}
    @Test
	void  test(){
	}

}
