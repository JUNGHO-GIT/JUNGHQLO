CREATE TABLE `member` (
  `member_number` int(100) NOT NULL AUTO_INCREMENT,
  `member_id` varchar(100) NOT NULL,
  `member_pw` varchar(100) NOT NULL,
  `member_name` varchar(100) NOT NULL,
  `member_phone` varchar(100) NOT NULL,
  `member_email` varchar(100) NOT NULL,
  `member_address1` varchar(100) NOT NULL,
  `member_address2` varchar(100) NOT NULL,
  `member_agree` tinyint(2) NOT NULL DEFAULT '0',
  `member_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `member_update` timestamp NULL DEFAULT NULL,
  `email_code` varchar(255) NOT NULL,
  PRIMARY KEY (`member_number`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;