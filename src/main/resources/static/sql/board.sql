CREATE TABLE `board` (
  `board_number` int(100) NOT NULL AUTO_INCREMENT,
  `board_title` varchar(255) NOT NULL,
  `board_contents` varchar(255) NOT NULL,
  `board_writer` varchar(100) NOT NULL,
  `board_count` int(100) NOT NULL DEFAULT '1',
  `board_like` int(100) NOT NULL DEFAULT '0',
  `board_dislike` int(100) NOT NULL DEFAULT '0',
  `board_imgsFile` longblob NOT NULL,
  `board_imgsUrl` varchar(255) NOT NULL,
  `board_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `board_update` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`board_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;