CREATE TABLE `qna` (
  `qna_number` int(100) NOT NULL AUTO_INCREMENT,
  `qna_category` varchar(255) NOT NULL,
  `qna_title` varchar(255) NOT NULL,
  `qna_contents` varchar(255) NOT NULL,
  `qna_writer` varchar(100) NOT NULL,
  `qna_count` int(100) NOT NULL,
  `qna_like` int(100) NOT NULL,
  `qna_dislike` int(100) NOT NULL,
  `qna_answer1` tinyint(1) NOT NULL DEFAULT '0',
  `qna_answer2` varchar(255) NOT NULL DEFAULT '답변준비중',
  `qna_imgsFile` longblob NOT NULL,
  `qna_imgsUrl` varchar(255) NOT NULL,
  `qna_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `qna_update` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`qna_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;