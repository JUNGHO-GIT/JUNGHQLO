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
import com.stripe.exception.StripeException;
import com.example.junghqlo.model.Orders;
import com.example.junghqlo.handler.LocalDateTimeTypeHandler;

@Mapper
public interface OrdersMapper {

  // 0. result mapping -----------------------------------------------------------------------------
  @Results ({
    @Result (
      property = "orders_number",
      column = "orders_number",
      id = true
    ),
    @Result (
      property = "product_number",
      column = "product_number"
    ),
    @Result (
      property = "product_name",
      column = "product_name"
    ),
    @Result (
      property = "member_id",
      column = "member_id"
    ),
    @Result (
      property = "orders_quantity",
      column = "orders_quantity"
    ),
    @Result (
      property = "orders_totalPrice",
      column = "orders_totalPrice"
    ),
    @Result (
      property = "product_imgsUrl",
      column = "product_imgsUrl"
    ),
    @Result (
      property = "orders_date",
      column = "orders_date",
      typeHandler = LocalDateTimeTypeHandler.class
    ),
    @Result (
      property = "orders_update",
      column = "orders_update",
      typeHandler = LocalDateTimeTypeHandler.class
    )
  })

  // 1. listOrders ---------------------------------------------------------------------------------
  @Select(
    """
    SELECT
      *
    FROM
      orders
    WHERE
      member_id = #{member_id}
    AND
      ${type} LIKE CONCAT('%', #{keyword}, '%')
    ORDER BY
      ${sort}
    """
  )
  List<Orders> listOrders(
    @Param("sort") String sort,
    @Param("type") String type,
    @Param("keyword") String keyword,
    @Param("member_id") String member_id
  ) throws Exception;

  // 2-1. detailOrders -----------------------------------------------------------------------------
  @Select(
    """
    SELECT
      *
    FROM
      orders
    WHERE
      orders_number = #{orders_number}
    """
  )
  Orders detailOrders(
    Integer orders_number
  ) throws Exception;

  // 2-2. getStripePrice ---------------------------------------------------------------------------
  @Select(
    """
    SELECT
      stripe_price
    FROM
      product
    WHERE
      product_number = #{product_number}
    """
  )
  String getStripePrice(
    Integer orders_number
  ) throws StripeException;

  // 3. addOrders ----------------------------------------------------------------------------------
  @Insert(
    """
    INSERT INTO
      orders(
        product_number,
        product_name,
        member_id,
        orders_quantity,
        orders_totalPrice,
        product_imgsUrl,
        orders_date
      )
    VALUES (
      #{product_number},
      #{product_name},
      #{member_id},
      #{orders_quantity},
      #{orders_totalPrice},
      #{product_imgsUrl},
      NOW()
    )
    """
  )
  void addOrders(
    Orders orders
  ) throws Exception;

  // 4-1. updateOrders ----------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      orders
    SET
      orders_quantity = #{orders_quantity},
      orders_totalPrice = #{orders_totalPrice},
      orders_update = NOW()
    WHERE
      orders_number = #{orders_number}
    """
  )
  void updateOrders(
    Orders orders
  ) throws Exception;

  // 4-2. updateProductStock ---------------------------------------------------------------------
  @Update(
    """
    UPDATE
      product
    SET
      product_stock = product_stock - #{orders_quantity}
    WHERE
      product_number = #{product_number}
    """
  )
  void updateProductStock(
    Integer product_number,
    Integer product_stock,
    Integer orders_quantity
  ) throws Exception;

  // 5. deleteOrders -------------------------------------------------------------------------------
  @Delete(
    """
    DELETE FROM
      orders
    WHERE
      orders_number = #{orders_number}
    """
  )
  Integer deleteOrders(
    Integer orders_number
  ) throws Exception;
}