package com.example.junghqlo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.example.junghqlo.model.Orders;

@Repository
public class OrdersMapperImpl implements OrdersMapper {

  // 0. constructor injection ----------------------------------------------------------------------
  SqlSessionTemplate sqlSession;
  OrdersMapperImpl(SqlSessionTemplate sqlSession) {
    this.sqlSession = sqlSession;
  }

  // 1-1. listOrders ------------------------------------------------------------------------------
  public List<Orders> listOrders(String member_id, String sort) {

    Map<String, String> map = new HashMap<>();
    map.put("member_id", member_id);
    map.put("sort", sort);

    return sqlSession.selectList("listOrders", map);
  }

  // 1-2. searchOrders -----------------------------------------------------------------------------
  public List<Orders> searchOrders(String searchType, String keyword, String member_id)
  throws Exception {

    Map<String, String> map = new HashMap<>();
    map.put("searchType", searchType);
    map.put("keyword", keyword);
    map.put("member_id", member_id);

    return sqlSession.selectList("searchOrders", map);
  }

  // 2-1. detailOrders -----------------------------------------------------------------------------
  public Orders detailOrders(Integer orders_number) {

    return sqlSession.selectOne("detailOrders", orders_number);
  }

  // 2-2. getStripePrice ---------------------------------------------------------------------------
  @Override
  public String getStripePrice(Integer product_number) {

    return sqlSession.selectOne("getStripePrice", product_number);
  }

  // 3. addOrders ----------------------------------------------------------------------------------
  @Override
  public void addOrders(Orders orders) {

    sqlSession.insert("addOrders", orders);
  }

  // 4-1. updateOrders -----------------------------------------------------------------------------
  @Override
  public void updateOrders(Orders orders) {

    sqlSession.update("updateOrders", orders);
  }

  // 4-2. updateProductStock -----------------------------------------------------------------------
  @Override
  public Integer updateProductStock(Integer product_number, Integer product_stock, Integer orders_quantity) {

    Map<String, Object> map = new HashMap<>();
    map.put("product_number", product_number);
    map.put("product_stock", product_stock);
    map.put("orders_quantity", orders_quantity);

    return sqlSession.update("updateProductStock", map);
  }

  // 5. deleteOrders -------------------------------------------------------------------------------
  @Override
  public Integer deleteOrders(Integer orders_number) {

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