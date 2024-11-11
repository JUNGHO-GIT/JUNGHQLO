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
      property = "product_brand",
      column = "product_brand"
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
      property = "product_imgsUrl",
      column = "product_imgsUrl"
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
    @Result (
      property = "stripe_id",
      column = "stripe_id"
    ),
    @Result (
      property = "stripe_price",
      column = "stripe_price"
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
      ${origin}
    AND
      ${type} LIKE CONCAT('%', #{keyword}, '%')
    ORDER BY
      ${sort}
    """
  )
  List<Product> listProduct(
    @Param("sort") String sort,
    @Param("category") String category,
    @Param("origin") String origin,
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

  // 3. saveProduct --------------------------------------------------------------------------------
  @Insert(
    """
    INSERT INTO
      product
      (
        product_name,
        product_detail,
        product_price,
        product_stock,
        product_brand,
        product_category,
        product_origin,
        product_imgsUrl,
        product_date,
        stripe_id,
        stripe_price
      )
    VALUES
      (
        #{product.product_name},
        #{product.product_detail},
        #{product.product_price},
        #{product.product_stock},
        #{product.product_brand},
        #{product.product_category},
        #{product.product_origin},
        #{imgsUrl},
        NOW(),
        #{product.stripe_id},
        #{product.stripe_price}
      )
    """
  )
  Integer saveProduct(
    @Param("product") Product product,
    @Param("imgsUrl") String imgsUrl
  ) throws Exception;

  // 4. updateProduct ------------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      product
    SET
      product_name = #{product.product_name},
      product_detail = #{product.product_detail},
      product_price = #{product.product_price},
      product_stock = #{product.product_stock},
      product_brand = #{product.product_brand},
      product_category = #{product.product_category},
      product_origin = #{product.product_origin},
      product_imgsUrl = #{imgsUrl},
      product_update = NOW(),
      stripe_id = #{product.stripe_id},
      stripe_price = #{product.stripe_price}
    WHERE
      product_number = #{product_number}
    """
  )
  Integer updateProduct(
    @Param("product") Product product,
    @Param("imgsUrl") String imgsUrl
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