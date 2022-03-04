
CREATE DATABASE langv;
USE langv;

/*Table structure for table `m_user` */

DROP TABLE IF EXISTS `m_user`;
CREATE TABLE `m_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` varchar(10) DEFAULT 'ROLE_USER',
  `STATUS` int NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `signature` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `m_collection`;
CREATE TABLE `m_collection` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `video_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;



/*Table structure for table `m_comment` */

DROP TABLE IF EXISTS `m_comment`;

CREATE TABLE `m_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `video_id` bigint NOT NULL,
  `video_username` varchar(20) NOT NULL,
  `commentator` varchar(20) NOT NULL,
  `comment_time` datetime NOT NULL,
  `comment_info` varchar(500) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `video_username` (`video_username`),
  KEY `commentator` (`commentator`),
  CONSTRAINT `m_comment_ibfk_1` FOREIGN KEY (`video_username`) REFERENCES `m_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `m_comment_ibfk_2` FOREIGN KEY (`commentator`) REFERENCES `m_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `m_subscribe` */

DROP TABLE IF EXISTS `m_subscribe`;

CREATE TABLE `m_subscribe` (
  `subscriber` varchar(20) NOT NULL,
  `youtuber` varchar(20) NOT NULL,
  `subscribe_time` datetime NOT NULL,
  PRIMARY KEY (`subscriber`,`youtuber`),
  KEY `youtuber` (`youtuber`),
  CONSTRAINT `m_subscribe_ibfk_1` FOREIGN KEY (`youtuber`) REFERENCES `m_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `m_subscribe_ibfk_2` FOREIGN KEY (`subscriber`) REFERENCES `m_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*Table structure for table `m_video` */

DROP TABLE IF EXISTS `m_video`;

CREATE TABLE `m_video` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TIME` varchar(20) NOT NULL,
  `img` varchar(200) NOT NULL,
  `username` varchar(20) NOT NULL,
  `url` varchar(200) NOT NULL,
  `introduction` varchar(250) DEFAULT NULL,
  `post_time` datetime NOT NULL,
  `status` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `username` (`username`),
  CONSTRAINT `m_video_ibfk_1` FOREIGN KEY (`username`) REFERENCES `m_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

