# LangV 视频分享订阅网站
一个基于SpringBoot、ffmpeg、Redis、Dplayer.js、vue.js的视频分享订阅网站，实现了一个视频网站的上传视频、播放视频、个人主页、订阅、评论、订阅评论通知等基本功能。
（具体技术交流请联系qq 1845780976）

**一、要实现上传视频后获取视频封面和时长等功能，得先下载ffmpeg并配置环境变量** <br>
(ffmpeg还可以转换视频格式及改变视频分辨率等，在这由于我还对ffpmeg研究不够深，仅用他实现了获取封面和时长的功能。有志者可以用他来加强对视频的处理)

1、下载ffmpeg：官方下载地址win版：https://github.com/BtbN/FFmpeg-Builds/releases 下载第一个ffmpeg-N-101994-g84ac1440b2-win64-gpl-shared.zip 即可<br>
2、解压后配置环境变量：解压下载后的的zip包，配置解压后bin目录下的环境变量即可。<br>
3、在dos命令窗口输入ffmpeg，出现它的信息则配置成功。<br>
4、在项目的application.yaml全局配置文件下设置自定义上传视频后的存储根路径。


**二、具体页面如下：**

**1、登录页面**
![image](https://github.com/ljx1845780976/img/blob/main/%E7%99%BB%E5%BD%95%E9%A1%B5.png)

**2、个人主页**
![image](https://github.com/ljx1845780976/img/blob/main/%E4%B8%AA%E4%BA%BA%E4%B8%BB%E9%A1%B5.png)

**3、主页**
![image](https://github.com/ljx1845780976/img/blob/main/%E4%B8%BB%E9%A1%B5.png)

**4、具体视频播放页**
![image](https://github.com/ljx1845780976/img/blob/main/%E5%85%B7%E4%BD%93%E6%92%AD%E6%94%BE%E8%A7%86%E9%A2%91%E9%A1%B5.png)

**5、播放页下方评论区**
![image](https://github.com/ljx1845780976/img/blob/main/%E6%92%AD%E6%94%BE%E8%A7%86%E9%A2%91%E9%A1%B5%E4%B8%8B%E8%AF%84%E8%AE%BA%E5%8C%BA.png)

**6、通知栏**
![image](https://github.com/ljx1845780976/img/blob/main/%E9%80%9A%E7%9F%A5%E6%A0%8F.png)

**7、上传页面**
![image](https://github.com/ljx1845780976/img/blob/main/%E4%B8%8A%E4%BC%A0%E9%A1%B5.png)
