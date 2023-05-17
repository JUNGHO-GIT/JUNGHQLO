CREATE TABLE `orders` (
  `orders_number` int(11) NOT NULL AUTO_INCREMENT,
  `product_number` int(100) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `member_id` varchar(255) NOT NULL,
  `orders_quantity` int(100) NOT NULL,
  `orders_totalPrice` int(100) NOT NULL,
  `product_imgsUrl` varchar(255) NOT NULL,
  `orders_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `orders_update` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`orders_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;