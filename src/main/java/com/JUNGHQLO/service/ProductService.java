package com.JUNGHQLO.service;

import java.util.List;
import com.JUNGHQLO.handler.PageHandler;
import com.JUNGHQLO.model.Product;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

  // 1. listProduct --------------------------------------------------------------------------------
  PageHandler<Product> listProduct(
    Integer pageNumber,
    Integer itemsPer,
    String sort,
    String category,
    String origin,
    String type,
    String keyword,
    Product product
  ) throws Exception;

  // 2. detailProduct ------------------------------------------------------------------------------
  Product detailProduct(
    Integer product_number
  ) throws Exception;

  // 3. saveProduct --------------------------------------------------------------------------------
  Integer saveProduct(
    Product product,
    List<MultipartFile> imgsFile
  ) throws Exception;

  // 4. updateProduct ------------------------------------------------------------------------------
  Integer updateProduct(
    Product product,
    List<MultipartFile> imgsFile
  ) throws Exception;

  // 5. deleteProduct ------------------------------------------------------------------------------
  Integer deleteProduct(
    Integer product_number
  ) throws Exception;

}