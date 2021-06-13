package com.videoSite.common.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 **/
@Data
@Component
@ConfigurationProperties(prefix = "my-video-path")
public class  MyVideoPath {
  private String MYPATH;
}
