package com.example.junghqlo.service;

import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Product;

public interface ProductService {

  // 1. listProduct --------------------------------------------------------------------------------
  PageHandler<Product> listProduct(
    Integer pageNumber,
    Integer itemsPer,
    String sort,
    String category,
    String type,
    String keyword,
    Product product
  ) throws Exception;

  // 2. detailProduct ------------------------------------------------------------------------------
  Product detailProduct(
    Integer product_number
  ) throws Exception;

  // 3. addProduct ---------------------------------------------------------------------------------
  void addProduct(
    String product_name,
    String product_detail,
    Integer product_price,
    Product product
  ) throws Exception;

  // 4. updateProduct ------------------------------------------------------------------------------
  void updateProduct(
    String product_imgsUrl1,
    String product_imgsUrl2,
    Product product
  ) throws Exception;

  // 5. deleteProduct ------------------------------------------------------------------------------
  Integer deleteProduct(
    Integer product_number
  ) throws Exception;

}