package com.videoSite.common.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 **/
@Data
@Component
@ConfigurationProperties(prefix = "my-img-server")
public class MyImgServer {
    private String url;
}
