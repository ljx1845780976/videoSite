package com.videoSite.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videoSite.entity.Notification;
import com.videoSite.entity.Subscribe;
import com.videoSite.entity.Video;
import com.videoSite.mapper.CollectionMapper;
import com.videoSite.utils.GetCurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CollectionController
 *
 * @author: liuwei
 * @date: 2021/6/25
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {
    @Autowired
    private CollectionMapper collectionMapper;

    @GetMapping("/addCollection/{videoId}")
    public void addCollection(@PathVariable("videoId") Integer videoId) {
        String username = GetCurrentUserUtil.getCurrentUserName();
        if (collectionMapper.isExisted(username, videoId) == null) {
            collectionMapper.addCollectionByUsername(username, videoId);
        }
    }

    @GetMapping("/cancelCollection/{videoId}")
    public void cancelCollection(@PathVariable("videoId") Integer videoId) {
        String username = GetCurrentUserUtil.getCurrentUserName();
        collectionMapper.cancelCollectionByUsername(username, videoId);
    }

    @GetMapping("/getCollection/{pageNum}/{username}")
    public IPage<Video> getCollection(@PathVariable(value = "pageNum", required = false) Integer pageNum,
                                      @PathVariable("username") String username) {
        Page<Video> videoPage = new Page<>(pageNum, 8);
        IPage<Video> page = collectionMapper.getCollectionByUsername(videoPage, username);
        return page;
    }

    @GetMapping("/getCollectionStatus/{videoId}")
    public Integer getCollectionStatus(@PathVariable("videoId") Integer videoId) {
        String username = GetCurrentUserUtil.getCurrentUserName();
        if (collectionMapper.isExisted(username, videoId) == null) {
            return 1;
        }else {
            return 0;
        }
    }

}
