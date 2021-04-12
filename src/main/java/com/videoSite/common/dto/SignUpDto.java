package com.videoSite.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 *
 **/
@Data
public class SignUpDto implements Serializable {
//    @NotBlank(message = "昵称不能为空")
    private String username;
//    @NotBlank(message = "密码不能为空")
    private String password;

    private String email;

}
