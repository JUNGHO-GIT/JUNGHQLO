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
import com.example.junghqlo.model.Product;
import com.example.junghqlo.handler.MultipartFileHandler;
import com.example.junghqlo.handler.LocalDateTimeTypeHandler;

@Mapper
public interface ProductMapper {

  // 0. result mapping -----------------------------------------------------------------------------
  @Results ({
    @Result (
      property = "product_number",
      column = "product_number",
      id = true
    ),
    @Result (
      property = "product_name",
      column = "product_name"
    ),
    @Result (
      property = "product_detail",
      column = "product_detail"
    ),
    @Result (
      property = "product_price",
      column = "product_price"
    ),
    @Result (
      property = "product_stock",
      column = "product_stock"
    ),
    @Result (
      property = "product_company",
      column = "product_company"
    ),
    @Result (
      property = "product_category",
      column = "product_category"
    ),
    @Result (
      property = "product_origin",
      column = "product_origin"
    ),
    @Result (
      property = "product_imgsFile1",
      column = "product_imgsFile1",
      typeHandler = MultipartFileHandler.class
    ),
    @Result (
      property = "product_imgsFile2",
      column = "product_imgsFile2",
      typeHandler = MultipartFileHandler.class
    ),
    @Result (
      property = "product_imgsUrl1",
      column = "product_imgsUrl1"
    ),
    @Result (
      property = "product_imgsUrl2",
      column = "product_imgsUrl2"
    ),
    @Result (
      property = "stripe_id",
      column = "stripe_id"
    ),
    @Result (
      property = "stripe_price",
      column = "stripe_price"
    ),
    @Result (
      property = "product_date",
      column = "product_date",
      typeHandler = LocalDateTimeTypeHandler.class
    ),
    @Result (
      property = "product_update",
      column = "product_update",
      typeHandler = LocalDateTimeTypeHandler.class
    ),
  })

  // 1. listProduct --------------------------------------------------------------------------------
  @Select(
    """
    SELECT
      *
    FROM
      product
    WHERE
      ${category}
    AND
      ${type} LIKE CONCAT('%', #{keyword}, '%')
    ORDER BY
      ${sort}
    """
  )
  List<Product> listProduct(
    @Param("sort") String sort,
    @Param("category") String category,
    @Param("type") String type,
    @Param("keyword") String keyword
  ) throws Exception;

  // 2. detailProduct ------------------------------------------------------------------------------
  @Select(
    """
    SELECT
      *
    FROM
      product
    WHERE
      product_number = #{product_number}
    """
  )
  Product detailProduct(
    Integer product_number
  ) throws Exception;

  // 3. addProduct ---------------------------------------------------------------------------------
  @Insert(
    """
    INSERT INTO
      product
      (
        product_name,
        product_detail,
        product_price,
        product_stock,
        product_company,
        product_category,
        product_origin,
        product_imgsFile1,
        product_imgsFile2,
        product_imgsUrl1,
        product_imgsUrl2,
        product_date,
        stripe_id,
        stripe_price
      )
    VALUES
      (
        #{product_name},
        #{product_detail},
        #{product_price},
        #{product_stock},
        #{product_company},
        #{product_category},
        #{product_origin},
        #{product_imgsFile1, typeHandler = MultipartFileHandler},
        #{product_imgsFile2, typeHandler = MultipartFileHandler},
        #{product_imgsUrl1},
        #{product_imgsUrl2},
        NOW(),
        #{stripe_id},
        #{stripe_price}
      )
    """
  )
  void addProduct(
    Product product
  ) throws Exception;

  // 4. updateProduct ------------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      product
    SET
      product_name = #{product_name},
      product_detail = #{product_detail},
      product_price = #{product_price},
      product_stock = #{product_stock},
      product_company = #{product_company},
      product_category = #{product_category},
      product_origin = #{product_origin},
      product_imgsFile1 = #{product_imgsFile1, typeHandler = MultipartFileHandler},
      product_imgsFile2 = #{product_imgsFile2, typeHandler = MultipartFileHandler},
      product_imgsUrl1 = #{product_imgsUrl1},
      product_imgsUrl2 = #{product_imgsUrl2},
      product_update = NOW(),
      stripe_id = #{stripe_id},
      stripe_price = #{stripe_price}
    WHERE
      product_number = #{product_number}
    """
  )
  void updateProduct(
    Product product
  ) throws Exception;

  // 5. deleteProduct ------------------------------------------------------------------------------
  @Delete(
    """
    DELETE FROM
      product
    WHERE
      product_number = #{product_number}
    """
  )
  Integer deleteProduct(
    Integer product_number
  ) throws Exception;
}