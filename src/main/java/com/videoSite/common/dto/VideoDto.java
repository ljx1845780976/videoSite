package com.videoSite.common.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 *
 **/
@Data
public class VideoDto {
    private String title;
    private String username;
    private MultipartFile videoFile;
    private String introduction;
    private int status;
    private MultipartFile img;

}
