package com.videoSite.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 *
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification implements Serializable {
     private String notifier;
     private String beNotifier;
     private Date notify_time;
     private String url;
     private String msg;
}
