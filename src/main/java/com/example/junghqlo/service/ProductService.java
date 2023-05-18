package com.example.junghqlo.service;

import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Product;

public interface ProductService {

  // 1. getProductList ---------------------------------------------------------------------------->
  PageHandler<Product> getProductList(Integer pageNumber, Integer itemsPer, String sort, Product product) throws Exception;

  // 1-1. getProductCategory ---------------------------------------------------------------------->
  PageHandler<Product> getProductCategory(Integer pageNumber, Integer itemsPer,  String category, String sort, Product product) throws Exception;

  // 2. getProductDetails ------------------------------------------------------------------------->
  Product getProductDetails(Integer product_number) throws Exception;

  // 3. searchProduct ----------------------------------------------------------------------------->
  PageHandler<Product> searchProduct(Integer pageNumber, Integer itemsPer, String searchType, String keyword, Product product) throws Exception;

  // 4. addProduct -------------------------------------------------------------------------------->
  void addProduct(Product product, String product_name, String product_details, Integer product_price) throws Exception;

  // 5. updateProduct ----------------------------------------------------------------------------->
  void updateProduct(Product product, String product_imgsUrl1, String product_imgsUrl2)
  throws Exception;

  // 6. deleteProduct ----------------------------------------------------------------------------->
  void deleteProduct(Integer product_number) throws Exception;

}