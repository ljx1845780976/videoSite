package com.videoSite.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videoSite.common.constant.MyVideoPath;
import com.videoSite.common.dto.VideoDto;
import com.videoSite.entity.Video;
import com.videoSite.service.VideoService;
import com.videoSite.utils.FFmpegUtils;
import com.videoSite.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 关注公众号：MarkerHub
 * @since 2021-03-10
 */
@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    VideoService videoService;
    @Autowired
    MyVideoPath myVideoPath;
    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/getAllVideos/{pageNum}")
    @ResponseBody
    public IPage<Video> getAllVideo(@PathVariable(value = "pageNum", required = false) Integer pageNum) {
        Page<Video> videoPage = new Page<>(pageNum, 6);
        IPage<Video> page = videoService.page(videoPage, new QueryWrapper<Video>().ne("status", 2).orderByDesc("id"));
        return page;
    }

    @GetMapping("/toVideo/{videoId}")
    public String toVideo(@PathVariable("videoId") Integer videoId, ModelMap modelMap) {
        modelMap.addAttribute("videoId", videoId);
        return "video/video";
    }

    @GetMapping("/getVideo/{videoId}")
    @ResponseBody
    public void getVideo(@PathVariable("videoId") Integer videoId,
                         HttpServletResponse response) {

        Video video = videoService.getById(videoId);
        String file = video.getUrl();
        FileInputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(file);

            byte[] data = new byte[is.available()];
            is.read(data);
            String diskfilename = video.getTitle() + ".mp4";
            response.setContentType("video/mp4");
            response.setHeader("Content-Disposition", "inline; filename=\"" + diskfilename + "\"");
            System.out.println("video.length: " + data.length / (1024 * 1024) + "MB");
            response.setContentLength(data.length);
            response.setHeader("Content-Range", "" + Integer.valueOf(data.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Etag", "W/\"9767057-1323779115364\"");
            os = response.getOutputStream();
            os.write(data);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //    }
    @GetMapping("/getVideoInfo/{videoId}")
    @ResponseBody
    public Video getVideoInfo(@PathVariable("videoId") Integer videoId) {
        Video video = videoService.getById(videoId);
        return video;
    }

    @GetMapping("/getImg/{videoId}")
    @ResponseBody
    public void getImg(@PathVariable("videoId") Integer videoId, HttpServletResponse response) {
        Video video = videoService.getById(videoId);
        String file = video.getImg();
        FileInputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(file);
            byte[] data = new byte[is.available()];
            is.read(data);
            String diskfilename = video.getTitle() + ".png";
            response.setContentType("image/png");
            response.setHeader("Content-Disposition", "inline; filename=\"" + diskfilename + "\"");
            System.out.println("img.length: " + data.length / (1024) + "KB");
            response.setContentLength(data.length);
            response.setHeader("Content-Range", "" + Integer.valueOf(data.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Etag", "W/\"9767057-1323779115364\"");
            os = response.getOutputStream();
            os.write(data);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/addVideo")
    @ResponseBody
    public String addVideo(VideoDto videoDto) throws IOException {
        //第一步：处理并写入视频上传的视频，并获得视频路径
        MultipartFile videoFile = videoDto.getVideoFile();
        if (!videoFile.isEmpty()) {
            //获取文件类型及后缀
            String[] fileType = videoFile.getContentType().split("\\/");
            //如果用户上传的文件属于video类型才允许执行下面创建用户目录、存入数据库操作等一系列操作
            if (fileType[0].equals("video")) {
                //先为用户创建一保存其视频及图片的用户根目录
                File userDir = new File(myVideoPath.getMYPATH() + "user-" + videoDto.getUsername());
                String videoPath=userDir.getAbsolutePath() + "\\video";
                String imgPath=userDir.getAbsolutePath() + "\\img";
                File videoDir = new File(videoPath);
                File imgDir = new File(imgPath);
                if (!userDir.exists()) {
                    userDir.mkdir();
                    //为用户创建视频目录和图片目录
                    videoDir.mkdir();
                    imgDir.mkdir();
                }
                //获取文件名,这里设置新的文件名，为了避免用户上传多个同名视频发生文件名相同
                String suffix = "." + fileType[1];
                String finalFilename = videoDto.getUsername() + new Date().getTime() + suffix;
                //文件上传
                File file = new File(videoDir + "\\" + finalFilename);
                videoFile.transferTo(new File(videoDir + "\\" + finalFilename));
                //获得新上传的文件路径,等下一起封装入对象存入数据库
                String videoUrl = videoDir.getAbsolutePath() + "\\" + finalFilename;

                //第二步：通过ffmpeg获取视频时长及写入视频封面图片和获取路径
                String imgUrl= FFmpegUtils.grabberVideoFramer(finalFilename,videoPath,imgPath);
                String videoTime = FFmpegUtils.formatDuration(FFmpegUtils.videoDuration(file));

                //第三步：此时可以封装进pojo对象存入数据库了
                Video video = new Video();
                video.setUsername(videoDto.getUsername());
                video.setTitle(videoDto.getTitle());
                video.setIntroduction(videoDto.getIntroduction());
                video.setUrl(videoUrl);
                video.setImg(imgUrl);
                video.setTime(videoTime);
                video.setStatus(videoDto.getStatus());
                video.setPostTime(new Date());
                videoService.save(video);
                //大功告成，返回页面
                return "上传成功";
            } else {
                throw new RuntimeException("请上传mp4视频格式的文件");
            }
        } else {
            throw new RuntimeException("请选择上传的视频");
        }

    }

    @GetMapping("/get7VideosInfoPerPageByUsername/{pageNum}/{username}")
    @ResponseBody
    public IPage<Video> get7VideosInfoPerPageByUsername(@PathVariable(value = "pageNum", required = false) Integer pageNum, @PathVariable("username") String username) {
        Page<Video> videoPage = new Page<>(pageNum, 7);
        IPage<Video> page = videoService.page(videoPage, new QueryWrapper<Video>().eq("username", username).orderByDesc("id"));
        return page;
    }

    @GetMapping("/get8VideosInfoPerPageByUsername/{pageNum}/{username}")
    @ResponseBody
    public IPage<Video> get8VideosInfoPerPageByUsername(@PathVariable(value = "pageNum", required = false) Integer pageNum, @PathVariable("username") String username) {
        Page<Video> videoPage = new Page<>(pageNum, 8);
        IPage<Video> page = videoService.page(videoPage, new QueryWrapper<Video>().eq("username", username).ne("status", 2).orderByDesc("id"));
        return page;
    }

    @GetMapping("/getOwn8VideosInfoPerPageByUsername/{pageNum}/{username}")
    @ResponseBody
    public IPage<Video> getOwn8VideosInfoPerPageByUsername(@PathVariable(value = "pageNum", required = false) Integer pageNum, @PathVariable("username") String username) {
        Page<Video> videoPage = new Page<>(pageNum, 8);
        IPage<Video> page = videoService.page(videoPage, new QueryWrapper<Video>().eq("username", username).orderByDesc("id"));
        return page;
    }

    @GetMapping("/getVideoInfoById/{videoId}")
    @ResponseBody
    public Video getVideoInfoById(@PathVariable("videoId") Integer videoId) {
        Video videoInfo = videoService.getById(videoId);
        return videoInfo;
    }

    @GetMapping("/getTopVideoInfoByUsernameAndStatus/{username}")
    @ResponseBody
    public Video getTopVideoInfoByUsername(@PathVariable("username") String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("status", 3);
        Video videoInfo = videoService.getOne(new QueryWrapper<Video>().allEq(map));
        return videoInfo;
    }

    @GetMapping("/setTopVideoStatus/{videoId}")
    @ResponseBody
    public void setTopVideoStatus(@PathVariable("videoId") long videoId) {
        Video video = new Video();
        video.setStatus(3);
        videoService.update(video, new QueryWrapper<Video>().eq("id", videoId));

    }

    @GetMapping("/cancelTopVideoStatus/{videoId}")
    @ResponseBody
    public void cancelTopVideoStatus(@PathVariable("videoId") long videoId) {
        Video video = new Video();
        video.setStatus(0);
        videoService.update(video, new QueryWrapper<Video>().eq("id", videoId));
    }

}



