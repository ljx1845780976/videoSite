package com.videoSite.common.constant;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OssClient
 *
 * @author: liuwei
 * @date: 2021/6/4
 */
@Component
public class OssClient {
    @Autowired
    MyOss oss;
    private OssClient(){};
    private OSS ossClient=null;
    public  OSS getOssClient(){
        if (ossClient==null){
            return new OSSClientBuilder().build(oss.ENDPOINT,oss.ACCESS_KEY_Id,oss.ACCESS_KEY_SECRET);
        }else {
            return ossClient;
        }

    }

}
