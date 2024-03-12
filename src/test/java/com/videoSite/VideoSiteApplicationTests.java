package com.videoSite;

import com.videoSite.common.constant.MyVideoPath;
import com.videoSite.entity.Video;
import com.videoSite.service.SubscribeService;
import com.videoSite.service.VideoService;
import com.videoSite.service.UserService;
import com.videoSite.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class VideoSiteApplicationTests {
	@Autowired
	VideoService videoService;
	
    @Test
	void  test(){
		List<Video> list = videoService.list();
		System.out.println(list);

	}

}
