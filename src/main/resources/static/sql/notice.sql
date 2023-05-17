CREATE TABLE `notice` (
  `notice_number` int(11) NOT NULL AUTO_INCREMENT,
  `notice_title` varchar(255) NOT NULL,
  `notice_contents` varchar(255) NOT NULL,
  `notice_writer` varchar(100) NOT NULL,
  `notice_count` int(11) NOT NULL,
  `notice_like` int(11) NOT NULL,
  `notice_dislike` int(11) NOT NULL,
  `notice_imgsFile` longblob NOT NULL,
  `notice_imgsUrl` varchar(255) NOT NULL,
  `notice_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `notice_update` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`notice_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;