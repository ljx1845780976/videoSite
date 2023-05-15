# LangV 视频分享订阅网站      
一个基于SpringBoot、Mybatis-Plus、ffmpeg、Redis、mysql、vue.js的视频分享订阅网站，实现了一个视频网站的上传视频、播放视频、个人主页、订阅、评论、订阅评论通知等基本功能，可用于毕业设计。
（需要相关部署服务及毕设文档+qq 1845780976） <br>
 演示地址：http://langv.top:8083/ (当前已停运) <br><br>
**新版本：新版本不再像以前把视频和封面文件都存储在服务器的本地磁盘，而是存储在阿里云的oss对象存储服务器。oss服务器可以存储大型文件，并可通过文件的url直接获取到服务器上的文件，这样就免去了在原服务器上io存储视频和读取视频的操作，速度得到了极大的提升。<br>(如不熟悉oss或视频因跨域无法播放，可翻至页尾查看相关配置教程)**



**一、具体页面如下：**


**1、登录页面**
![image](https://gitee.com/ljx1845780976/img/raw/main/%E7%99%BB%E5%BD%95.png)

**2、个人主页**
![image](https://gitee.com/ljx1845780976/img/raw/main/%E4%B8%AA%E4%BA%BA%E4%B8%BB%E9%A1%B5.png)

**3、主页**
![image](https://gitee.com/ljx1845780976/img/raw/main/%E4%B8%BB%E9%A1%B5.png)

**4、具体视频播放页**
![image](https://gitee.com/ljx1845780976/img/raw/main/%E8%A7%86%E9%A2%91%E6%92%AD%E6%94%BE%E9%A1%B5.png)

**5、播放页下方评论区**
![image](https://gitee.com/ljx1845780976/img/raw/main/%E6%92%AD%E6%94%BE%E8%A7%86%E9%A2%91%E9%A1%B5%E4%B8%8B%E8%AF%84%E8%AE%BA%E5%8C%BA.png)

**6、通知栏信息**
![image](https://gitee.com/ljx1845780976/img/raw/main/%E9%80%9A%E7%9F%A5%E6%A0%8F.png)

**7、上传页面**
![image](https://gitee.com/ljx1845780976/img/raw/main/%E4%B8%8A%E4%BC%A0%E9%A1%B5%E9%9D%A2.png)

<br><br>
**二、oss相关配置：**<br>
**1、application.yaml配置文件配置相关oss信息**
![image](https://gitee.com/ljx1845780976/img/raw/main/oss%E9%85%8D%E7%BD%AE1.png)

**2、oss设置前端js跨域请求允许**
<br>①<br>
![image](https://gitee.com/ljx1845780976/img/raw/main/%E8%B7%A8%E5%9F%9F%E8%AE%BE%E7%BD%AE1.png)
<br>②<br>
![image](https://gitee.com/ljx1845780976/img/raw/main/%E8%B7%A8%E5%9F%9F%E8%AE%BE%E7%BD%AE2.png)
