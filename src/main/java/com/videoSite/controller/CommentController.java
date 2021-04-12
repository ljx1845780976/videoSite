package com.videoSite.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videoSite.entity.Comment;
import com.videoSite.entity.Notification;
import com.videoSite.service.CommentService;
import com.videoSite.utils.NotificationUtil;
import com.videoSite.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 *
 * @author 关注公众号：JxHub
 * @since 2021-03-22
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    NotificationUtil notificationUtil;
    @Autowired
    CommentService commentService;

    @PostMapping("/postComment")
    public void postComment(@RequestBody Comment comment){
        String beNotifier=comment.getVideo_username();
        String url="/video/toVideo/"+comment.getVideo_id();
        Notification notification=new Notification(comment.getCommentator(),beNotifier,comment.getCommentTime(),url,"评论了你的视频");
        notificationUtil.Notify(beNotifier,notification);
        commentService.save(comment);
    }

    @GetMapping("/getComment/{pageNum}/{video_id}")
    public IPage<Comment> getComment(@PathVariable("video_id") Integer video_id,
                                    @PathVariable(value = "pageNum",required = false) Integer pageNum){
        Page<Comment> commentPage=new Page<>(pageNum,8);
        IPage<Comment> page = commentService.page(commentPage, new QueryWrapper<Comment>().eq("video_id",video_id).orderByDesc("id"));
        return page;
    }

}
