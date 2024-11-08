package com.example.junghqlo.service;

import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Orders;
import com.stripe.exception.StripeException;

public interface OrdersService {

  // 1. listOrders ---------------------------------------------------------------------------------
  PageHandler<Orders> listOrders(
    Integer pageNumber,
    Integer itemsPer,
    String sort,
    String type,
    String keyword,
    String member_id,
    Orders orders
  ) throws Exception;

  // 2. detailOrders -------------------------------------------------------------------------------
  Orders detailOrders(
    Integer orders_number
  ) throws Exception;

  // 2-1. getStripePrice ---------------------------------------------------------------------------
  String getStripePrice(
    Integer orders_number
  ) throws StripeException;

  // 3. addOrders ----------------------------------------------------------------------------------
  void addOrders(
    Orders orders
  ) throws Exception;

  // 4-1. updateProductStock -----------------------------------------------------------------------
  void updateProductStock(
    Integer product_number,
    Integer product_stock,
    Integer orders_quantity
  ) throws Exception;

  // 5. deleteOrders -------------------------------------------------------------------------------
  Integer deleteOrders(
    Integer orders_number
  ) throws Exception;
}