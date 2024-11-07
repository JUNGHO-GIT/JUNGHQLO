CREATE TABLE `product` (
  `product_number` int(100) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) NOT NULL,
  `product_detail` varchar(255) NOT NULL,
  `product_price` decimal(10,0) NOT NULL,
  `product_stock` int(100) NOT NULL,
  `product_company` varchar(255) NOT NULL,
  `product_category` varchar(255) NOT NULL,
  `product_origin` varchar(255) NOT NULL,
  `product_imgsFile` longblob NOT NULL,
  `product_imgsUrl` varchar(255) NOT NULL,
  `product_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `product_update` timestamp NULL DEFAULT NULL,
  `stripe_id` varchar(255) NOT NULL,
  `stripe_price` varchar(255) NOT NULL,
  PRIMARY KEY (`product_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;