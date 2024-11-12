package com.example.junghqlo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.mapper.OrdersMapper;
import com.example.junghqlo.model.Orders;
import com.example.junghqlo.model.Product;
import com.stripe.exception.StripeException;

@Service
public class OrdersServiceImpl implements OrdersService {

  @Value("${bucket-main}")
  private String BUCKET_MAIN;

  @Value("${bucket-folder}")
  private String BUCKET_FOLDER;

  // 0. constructor injection ----------------------------------------------------------------------
  private OrdersMapper ordersMapper;
  OrdersServiceImpl(OrdersMapper ordersMapper) {
    this.ordersMapper = ordersMapper;
  }

  // 1. listOrders ---------------------------------------------------------------------------------
  @Override
  public PageHandler<Orders> listOrders(
    Integer pageNumber,
    Integer itemsPer,
    String sort,
    String type,
    String keyword,
    String member_id,
    Orders orders
  ) throws Exception {

    List<Orders> content = ordersMapper.listOrders(sort, type, keyword, member_id);
    Integer itemsTotal = content.size();
    Integer pageLast = (itemsTotal + itemsPer - 1) / itemsPer;

    if (pageNumber <= 0) {
      pageNumber = 1;
    }

    if (pageLast > 0 && pageNumber > pageLast) {
      pageNumber = pageLast;
    }

    Integer pageStart = (pageNumber - 1) * itemsPer;
    Integer pageEnd = Math.min(pageStart + itemsPer, itemsTotal);

    List<Orders> pageContent;

    if (pageStart >= itemsTotal) {
      pageContent = new ArrayList<>();
    }
    else {
      pageContent = content.subList(pageStart, pageEnd);
    }

    return new PageHandler<>(pageNumber, pageStart, pageEnd, 1, pageLast, itemsPer, itemsTotal, pageContent);
  }

  // 2. detailOrders -------------------------------------------------------------------------------
  @Override
  public Orders detailOrders(
    Integer orders_number
  ) throws Exception {

    return ordersMapper.detailOrders(orders_number);
  }

  // 2-1. getStripePrice ---------------------------------------------------------------------------
  @Override
  public String getStripePrice(
    Integer orders_number
  ) throws StripeException {

    return ordersMapper.getStripePrice(orders_number);
  }

  // 3. saveOrders ---------------------------------------------------------------------------------
  @Override
  public void saveOrders(
    @ModelAttribute Orders orders,
    @ModelAttribute Product product
  ) throws Exception {

    // orders 는 리턴 없음, 이미지 저장 필요없음
    ordersMapper.saveOrders(orders, product);
  }

  // 4-1. updateProductStock -----------------------------------------------------------------------
  @Override
  public void updateProductStock(
    Integer product_number,
    Integer product_stock,
    Integer orders_quantity
  ) throws Exception {

    ordersMapper.updateProductStock(product_number, product_stock, orders_quantity);
  }

  // 5. deleteOrders -------------------------------------------------------------------------------
  @Override
  public Integer deleteOrders(
    Integer orders_number
  ) throws Exception {

    Integer result = 0;

    if (ordersMapper.deleteOrders(orders_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

}