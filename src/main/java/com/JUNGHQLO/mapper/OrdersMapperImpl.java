package com.JUNGHQLO.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.JUNGHQLO.model.Orders;
import com.JUNGHQLO.model.Product;
import com.stripe.exception.StripeException;

@Repository
public class OrdersMapperImpl implements OrdersMapper {

  // 0. constructor injection ----------------------------------------------------------------------
  SqlSessionTemplate sqlSession;
  OrdersMapperImpl(SqlSessionTemplate sqlSession) {
    this.sqlSession = sqlSession;
  }

  // 1. listOrders ------------------------------------------------------------------------------
  public List<Orders> listOrders(
    String sort,
    String type,
    String keyword,
    String member_id
  ) throws Exception {

    Map<String, String> map = new HashMap<>();
    map.put("sort", sort);
    map.put("type", type);
    map.put("keyword", keyword);
    map.put("member_id", member_id);

    return sqlSession.selectList("listOrders", map);
  }

  // 2-1. detailOrders -----------------------------------------------------------------------------
  public Orders detailOrders(
    Integer orders_number
  ) throws Exception {

    return sqlSession.selectOne("detailOrders", orders_number);
  }

  // 2-2. getStripePrice ---------------------------------------------------------------------------
  @Override
  public String getStripePrice(
    Integer product_number
  ) throws StripeException {

    return sqlSession.selectOne("getStripePrice", product_number);
  }

  // 3. saveOrders ---------------------------------------------------------------------------------
  @Override
  public void saveOrders(
    Orders orders,
    Product product
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("orders", orders);
    map.put("product", product);

    sqlSession.insert("saveOrders", map);
  }

  // 4-1. updateOrders -----------------------------------------------------------------------------
  @Override
  public void updateOrders(
    Orders orders
  ) throws Exception {

    sqlSession.update("updateOrders", orders);
  }

  // 4-2. updateProductStock -----------------------------------------------------------------------
  @Override
  public void updateProductStock(
    Integer product_number,
    Integer product_stock,
    Integer orders_quantity
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("product_number", product_number);
    map.put("product_stock", product_stock);
    map.put("orders_quantity", orders_quantity);

    sqlSession.update("updateProductStock", map);
  }

  // 5. deleteOrders -------------------------------------------------------------------------------
  @Override
  public Integer deleteOrders(
    Integer orders_number
  ) throws Exception {

    Integer result = 0;

    if (sqlSession.delete("deleteOrders", orders_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}