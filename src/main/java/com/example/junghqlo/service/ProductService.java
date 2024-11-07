package com.example.junghqlo.service;

import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Product;

public interface ProductService {

  // 1-1. listProduct -----------------------------------------------------------------------------
  PageHandler<Product> listProduct(Integer pageNumber, Integer itemsPer, String sort, Product product) throws Exception;

  // 1-1. categoryProduct -----------------------------------------------------------------------
  PageHandler<Product> categoryProduct(Integer pageNumber, Integer itemsPer,  String category, String sort, Product product) throws Exception;

  // 2. detailProduct --------------------------------------------------------------------------
  Product detailProduct(Integer product_number) throws Exception;

  // 1-2. searchProduct ------------------------------------------------------------------------------
  PageHandler<Product> searchProduct(Integer pageNumber, Integer itemsPer, String searchType, String keyword, Product product) throws Exception;

  // 3. addProduct ---------------------------------------------------------------------------------
  void addProduct(Product product, String product_name, String product_detail, Integer product_price) throws Exception;

  // 4. updateProduct ------------------------------------------------------------------------------
  void updateProduct(Product product, String product_imgsUrl1, String product_imgsUrl2)
  throws Exception;

  // 5. deleteProduct ------------------------------------------------------------------------------
  Integer deleteProduct(Integer product_number) throws Exception;

}