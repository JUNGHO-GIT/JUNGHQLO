package com.example.junghqlo.service;

import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Orders;
import com.stripe.exception.StripeException;

public interface OrdersService {

  // 1-1. listOrders ------------------------------------------------------------------------------
  PageHandler<Orders> listOrders(Integer pageNumber, Integer itemsPer, String member_id, String sort, Orders orders) throws Exception;

  // 2. detailOrders ---------------------------------------------------------------------------
  Orders detailOrders(Integer orders_number) throws Exception;

  // 1-2. searchOrders -------------------------------------------------------------------------------
  PageHandler<Orders> searchOrders(Integer pageNumber, Integer itemsPer, String searchType, String keyword, String member_id, Orders orders) throws Exception;

  // 3-1. getStripePrice ---------------------------------------------------------------------------
  String getStripePrice(Integer product_number) throws StripeException;

  // 3. addOrders ----------------------------------------------------------------------------------
  void addOrders(Orders orders) throws Exception;

  // 4-2. successOrders 4-3. failOrders 4. updateOrders

  // 4-1. updateProductStock -----------------------------------------------------------------------
  Integer updateProductStock(Integer product_number, Integer product_stock, Integer orders_quantity) throws Exception;

  // 5. deleteOrders -------------------------------------------------------------------------------
  Integer deleteOrders(Integer orders_number);
}