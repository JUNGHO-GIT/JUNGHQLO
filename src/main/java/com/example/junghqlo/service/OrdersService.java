package com.example.junghqlo.service;

import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Orders;
import com.stripe.exception.StripeException;

public interface OrdersService {

  // 1. getOrdersList ----------------------------------------------------------------------------->
  PageHandler<Orders> getOrdersList(Integer pageNumber, Integer itemsPer, String member_id, String sort, Orders orders) throws Exception;

  // 2. getOrdersDetails -------------------------------------------------------------------------->
  Orders getOrdersDetails(Integer orders_number) throws Exception;

  // 3. searchOrders ------------------------------------------------------------------------------>
  PageHandler<Orders> searchOrders(Integer pageNumber, Integer itemsPer, String searchType, String keyword, String member_id, Orders orders) throws Exception;

  // 3-1. getStripePrice -------------------------------------------------------------------------->
  String getStripePrice(Integer product_number) throws StripeException;

  // 4. addOrders --------------------------------------------------------------------------------->
  void addOrders(Orders orders) throws Exception;

  // 4-2. successOrders 4-3. failOrders 5. updateOrders

  // 5-1. updateProductStock ---------------------------------------------------------------------->
  Integer updateProductStock(Integer product_number, Integer product_stock, Integer orders_quantity) throws Exception;

  // 6. deleteOrders ------------------------------------------------------------------------------>
  void deleteOrders(Integer orders_number);
}