package com.example.junghqlo.service;

import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Product;
import org.springframework.web.multipart.MultipartFile;

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

  // 3. saveProduct ---------------------------------------------------------------------------------
  Integer saveProduct(
    Product product,
    MultipartFile[] imgsFile
  ) throws Exception;

  // 4. updateProduct ------------------------------------------------------------------------------
  Integer updateProduct(
    Product product,
    MultipartFile[] imgsFile
  ) throws Exception;

  // 5. deleteProduct ------------------------------------------------------------------------------
  Integer deleteProduct(
    Integer product_number
  ) throws Exception;

}