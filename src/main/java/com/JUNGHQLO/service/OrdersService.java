package com.JUNGHQLO.service;

import com.JUNGHQLO.handler.PageHandler;
import com.JUNGHQLO.model.Orders;
import com.JUNGHQLO.model.Product;
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

  // 3. saveOrders ---------------------------------------------------------------------------------
  void saveOrders(
    Orders orders,
    Product product
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