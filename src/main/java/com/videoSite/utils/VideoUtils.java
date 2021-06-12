package com.videoSite.utils;

import com.videoSite.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 **/
public class VideoUtils {



    /*   1、获取视频时间方法

     * */
     public static String getVideoTime(String videoUrl) {
         String dosCommands = "ffprobe " + videoUrl;
         String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
         Pattern pattern = Pattern.compile(regexDuration);
         Matcher m = pattern.matcher(execDosAndReturn(dosCommands));
         if (m.find()) {
             return getTime(m.group(1));
         }
             return null;

     }
    /*2、获取视频图片方法：可由ffmpeg自动生成(现水平只能获取开头的视频帧)*/
     public static String getImgUrl(String videoUrl,String imgDir){
         String imgName=new Date().getTime()+".png";
         String dosCommands = "ffmpeg -i " + videoUrl + " -r 1 -ss 00:00:01 -vframes 1 " +
                            imgDir+"\\"+imgName;
       //执行获取图片命令
       execDosOnly(dosCommands);
       return imgDir+"\\"+imgName;

    }


    //执行dos命令方法并返回数据给java
    private static String execDosAndReturn(String dosCommands){
        Runtime runtime=Runtime.getRuntime();
        try {
            Process process=runtime.exec(dosCommands);
            process.waitFor();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }
    //仅仅执行dos命令不返回数据
    private static void execDosOnly(String dosCommands){
        Runtime runtime=Runtime.getRuntime();
        try {
            Process process=runtime.exec(dosCommands);
            process.waitFor();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    //去除秒位的小数点，如果超过一小时则获取小时位；如果没超出，则只获取分秒位
    private static String getTime(String videoTime){
        //小时位有值,直接返回
        if (videoTime.substring(1,2).compareTo("0")>0){
            return videoTime.substring(0,8);
        }else{
            return videoTime.substring(3,8);
        }


    }

    public static void deleteTempFile(String path) {
        //指定磁盘里需要删除的文件
        File file = new File(path);
        //遍历文件
        File[] listFiles = file.listFiles();
        for (File f:listFiles) {
                f.delete();
        }
    }

}
