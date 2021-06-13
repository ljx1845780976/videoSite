package com.videoSite.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * 视频处理工具
 * @author layduo
 *
 */
@Component
public class FFmpegUtils {

    private static final String IMAGE_SUFFIX = "png";

    /**
     * 获取视频时长，单位为秒S
     * @param file 视频源
     * @return
     */
    @SuppressWarnings("resource")
    public static long videoDuration(File file) {
        long duration = 0L;
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
        try {
            ff.start();
            duration = ff.getLengthInTime() / (1000 * 1000);
            ff.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;
    }



    /**
     * 生成图片的相对路径
     * @param args 传入生成图片的名称、为空则用UUID命名
     * @return 例如 upload/images.png
     */
    public static String getImagePath(Object...args) {
            return String.valueOf(args[0]) + "." + IMAGE_SUFFIX;
    }

    /**
     * 生成唯一的uuid
     * @return uuid
     */
    private static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 时长格式换算
     * @param duration 时长
     * @return HH:mm:ss
     */
    public static String formatDuration(Long duration) {
        String formatTime = StringUtils.EMPTY;
        double time = Double.valueOf(duration);
        if (time > -1) {
            int hour = (int) Math.floor(time / 3600);
            int minute = (int) (Math.floor(time / 60) % 60);
            int second = (int) (time % 60);
            if(hour!=0) {
                if (hour < 10) {
                    formatTime = "0";
                }
                formatTime += hour + ":";
            }
            if (minute < 10) {
                formatTime += "0";
            }
            formatTime += minute + ":";

            if (second < 10) {
                formatTime += "0";
            }
            formatTime += second;
        }
        return formatTime;
    }

    public static String grabberVideoFramer(String videoFileName,String videoPath){
        //最后获取到的视频的图片的路径
        String videPicture="";
        //Frame对象
        Frame frame = null;
        //标识
        int flag = 0;
        try {
			 /*
            获取视频文件
            */
            FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoPath + "/" + videoFileName);
            fFmpegFrameGrabber.start();

            //获取视频总帧数
            int ftp = fFmpegFrameGrabber.getLengthInFrames();
//            System.out.println("时长 " + ftp / fFmpegFrameGrabber.getFrameRate() / 60);

            while (flag <= ftp) {
                frame = fFmpegFrameGrabber.grabImage();
				/*
				对视频的第五帧进行处理
				 */
                if (frame != null && flag==5) {
                    //文件绝对路径+名字
                    String fileName = videoPath + UUID.randomUUID().toString()+"_" + String.valueOf(flag) + ".jpg";

                    //文件储存对象
                    File outPut = new File(fileName);
                    ImageIO.write(FrameToBufferedImage(frame), "jpg", outPut);

                    //视频第五帧图的路径
                    String savedUrl =videoPath+outPut.getName();
                    videPicture=savedUrl;
                    break;
                }
                flag++;
            }
            fFmpegFrameGrabber.stop();
            fFmpegFrameGrabber.close();
        } catch (Exception E) {
            E.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videPicture;
    }

    public static BufferedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }

}
