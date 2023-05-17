package com.example.junghqlo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.example.junghqlo.domain.Product;

@Mapper
public interface ProductMapper {

  // 0. result mapping ---------------------------------------------------------------------------->
  @Results ({
    @Result (property="product_number",   column="product_number", id=true),
    @Result (property="product_name",     column="product_name"),
    @Result (property="product_details",  column="product_details"),
    @Result (property="product_price",    column="product_price"),
    @Result (property="product_stock",    column="product_stock"),
    @Result (property="product_company",  column="product_company"),
    @Result (property="product_category", column="product_category"),
    @Result (property="product_origin",   column="product_origin"),
    @Result (property="product_imgsFile1", column="product_imgsFile1",
            typeHandler = com.example.junghqlo.handler.MultipartFileHandler.class),
    @Result (property="product_imgsFile2", column="product_imgsFile2",
            typeHandler = com.example.junghqlo.handler.MultipartFileHandler.class),
    @Result (property="product_imgsUrl1",  column="product_imgsUrl1"),
    @Result (property="product_imgsUrl2",  column="product_imgsUrl2"),
    @Result (property="product_date",     column="product_date",
            typeHandler=com.example.junghqlo.handler.LocalDateTimeTypeHandler.class),
    @Result (property="product_update",   column="product_update",
            typeHandler=com.example.junghqlo.handler.LocalDateTimeTypeHandler.class),
    @Result (property="stripe_id", column="stripe_id"),
    @Result (property="stripe_price",  column="stripe_price")
  })

  // 1. getProductList ---------------------------------------------------------------------------->
  @Select("SELECT * FROM product ORDER BY ${sort}")
  List<Product> getProductList(@Param("sort") String sort) throws Exception;

  // 1-1. getProductCategory ---------------------------------------------------------------------->
  @Select("SELECT * FROM product WHERE product_category = ${category} ORDER BY ${sort}")
  List<Product> getProductCategory(@Param("category") String category, @Param("sort") String sort)
  throws Exception;

  // 2. getProductDetails ------------------------------------------------------------------------->
  @Select("SELECT * FROM product WHERE product_number=#{product_number}")
  Product getProductDetails(Integer product_number) throws Exception;

  // 3. searchProduct ----------------------------------------------------------------------------->
  @Select("SELECT * FROM product WHERE ${keyword} LIKE CONCAT('%', #{searchType}, '%')")
  List<Product> searchProduct(@Param("searchType") String searchType, @Param("keyword") String keyword) throws Exception;

  // 4. addProduct -------------------------------------------------------------------------------->
  @Insert("INSERT INTO product (product_name, product_details, product_price, product_stock, product_company, product_category, product_origin, product_imgsFile1, product_imgsFile2, product_imgsUrl1, product_imgsUrl2, product_date, stripe_id, stripe_price) VALUES (#{product_name}, #{product_details}, #{product_price}, #{product_stock}, #{product_company}, #{product_category}, #{product_origin}, #{product_imgsFile1, typeHandler=com.example.junghqlo.handler.MultipartFileHandler}, #{product_imgsFile2, typeHandler=com.example.junghqlo.handler.MultipartFileHandler}, #{product_imgsUrl1}, #{product_imgsUrl2}, NOW(), #{stripe_id}, #{stripe_price})")
  void addProduct(Product product) throws Exception;

  // 5. updateProduct ----------------------------------------------------------------------------->
  @Update("UPDATE product SET product_name=#{product_name}, product_details=#{product_details}, product_price=#{product_price}, product_stock=#{product_stock}, product_company=#{product_company}, product_category=#{product_category}, product_origin=#{product_origin}, product_imgsFile1=#{product_imgsFile1, typeHandler=com.example.junghqlo.handler.MultipartFileHandler}, product_imgsFile2=#{product_imgsFile2, typeHandler=com.example.junghqlo.handler.MultipartFileHandler}, product_imgsUrl1=#{product_imgsUrl1}, product_imgsUrl2=#{product_imgsUrl2}, product_update=NOW(), stripe_id=#{stripe_id}, stripe_price=#{stripe_price} WHERE product_number=#{product_number}")
  void updateProduct(Product product) throws Exception;

  // 6. deleteProduct ----------------------------------------------------------------------------->
  @Delete("DELETE FROM product WHERE product_number=#{product_number}")
  void deleteProduct(Product product) throws Exception;

}