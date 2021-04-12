CREATE DATABASE LangV;
USE LangV;

DROP TABLE IF EXISTS m_user ;
CREATE TABLE `m_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `role` VARCHAR(10) DEFAULT 'ROLE_USER',
  `STATUS` INT NOT NULL,
  `email` VARCHAR(20) DEFAULT NULL,
  `signature` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=INNODB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
 
INSERT INTO m_user VALUES(NULL,'root','e10adc3949ba59abbe56e057f20f883e','ROLE_ROOT',1,'1845780976@qq.com','关注我的公众号：JxHub');

DROP TABLE IF EXISTS m_video ;
CREATE TABLE `m_video` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(80) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TIME` VARCHAR(20) NOT NULL,
  `img` VARCHAR(200) NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `url` VARCHAR(200) NOT NULL,
  `introduction` VARCHAR(250) DEFAULT NULL,
  `post_time` DATETIME NOT NULL,
  `status` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `username` (`username`),
  CONSTRAINT `m_video_ibfk_1` FOREIGN KEY (`username`) REFERENCES `m_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS m_comment ;
CREATE TABLE `m_comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `video_id` BIGINT NOT NULL,
  `video_username` VARCHAR(20) NOT NULL,
  `commentator` VARCHAR(20) NOT NULL,
  `comment_time` DATETIME NOT NULL,
  `comment_info` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `video_username` (`video_username`),
  KEY `commentator` (`commentator`),
  CONSTRAINT `m_comment_ibfk_1` FOREIGN KEY (`video_username`) REFERENCES `m_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `m_comment_ibfk_2` FOREIGN KEY (`commentator`) REFERENCES `m_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS m_subscribe ;
CREATE TABLE `m_subscribe` (
  `subscriber` VARCHAR(20) NOT NULL,
  `youtuber` VARCHAR(20) NOT NULL,
  `subscribe_time` DATETIME NOT NULL,
  PRIMARY KEY (`subscriber`,`youtuber`),
  KEY `youtuber` (`youtuber`),
  CONSTRAINT `m_subscribe_ibfk_1` FOREIGN KEY (`youtuber`) REFERENCES `m_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `m_subscribe_ibfk_2` FOREIGN KEY (`subscriber`) REFERENCES `m_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;



