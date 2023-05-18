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
import com.example.junghqlo.model.Orders;

@Mapper
public interface OrdersMapper {

  // 0. result mapping ---------------------------------------------------------------------------->
  @Results ({
    @Result (property="orders_number",     column="orders_number",  id=true),
    @Result (property="product_number",    column="product_number"),
    @Result (property="product_name",      column="product_name"),
    @Result (property="member_id",         column="member_id"),
    @Result (property="orders_quantity",   column="orders_quantity"),
    @Result (property="orders_totalPrice", column="orders_totalPrice"),
    @Result (property="product_imgsUrl",   column="product_imgsUrl"),
    @Result (property="orders_date",       column="orders_date",
            typeHandler=com.example.junghqlo.handler.LocalDateTimeTypeHandler.class),
    @Result (property="orders_update",     column="orders_update",
            typeHandler=com.example.junghqlo.handler.LocalDateTimeTypeHandler.class),
  })

  // 1. getOrdersList ----------------------------------------------------------------------------->
  @Select("SELECT * FROM orders WHERE member_id=#{member_id} ORDER BY ${sort}")
  List<Orders> getOrdersList(@Param("member_id") String member_id, @Param("sort") String sort)
  throws Exception;

  // 2. getOrdersDetails -------------------------------------------------------------------------->
  @Select("SELECT * FROM orders WHERE orders_number=#{orders_number}")
  Orders getOrdersDetails(Integer orders_number);

  // 3. searchOrders ------------------------------------------------------------------------------>
  @Select("SELECT * FROM orders WHERE (${keyword} LIKE CONCAT('%', #{searchType}, '%')) AND member_id=#{member_id}")
  List<Orders> searchOrders(@Param("searchType") String searchType, @Param("keyword") String keyword, @Param("member_id") String member_id) throws Exception;

  // 3-1. getStripePrice -------------------------------------------------------------------------->
  @Select("SELECT stripe_price FROM product WHERE product_number=#{product_number}")
  String getStripePrice(Integer product_number);

  // 4. addOrders --------------------------------------------------------------------------------->
  @Insert("INSERT INTO orders(product_number, product_name, member_id, orders_quantity, orders_totalPrice, product_imgsUrl, orders_date) VALUES (#{product_number}, #{product_name}, #{member_id}, #{orders_quantity}, #{orders_totalPrice}, #{product_imgsUrl}, NOW())")
  void addOrders(Orders orders);

  // 4-2. successOrders 4-3. failOrders 5. updateOrders

  // 5-1. updateProductStock ---------------------------------------------------------------------->
  @Update("UPDATE product SET product_stock=product_stock-#{orders_quantity} WHERE product_number=#{product_number}")
  Integer updateProductStock(Integer product_number, Integer product_stock, Integer orders_quantity);

  // 6. deleteOrders ------------------------------------------------------------------------------>
  @Delete("DELETE FROM orders WHERE orders_number=#{orders_number}")
  void deleteOrders(Integer orders_number);

}