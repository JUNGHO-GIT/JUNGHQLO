package com.example.junghqlo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.example.junghqlo.domain.Orders;

@Repository
public class OrdersMapperImpl implements OrdersMapper {

  // 0. constructor injection --------------------------------------------------------------------->
  SqlSessionTemplate sqlSession;
  OrdersMapperImpl(SqlSessionTemplate sqlSession) {
  this.sqlSession = sqlSession;
  }

  // 1. getOrdersList ----------------------------------------------------------------------------->
  public List<Orders> getOrdersList(String member_id, String sort) {

    Map<String, String> map = new HashMap<>();
    map.put("member_id", member_id);
    map.put("sort", sort);

    return sqlSession.selectList("getOrdersList", map);
  }

  // 2. getOrdersDetails -------------------------------------------------------------------------->
  public Orders getOrdersDetails(Integer orders_number) {

    return sqlSession.selectOne("getOrdersDetails", orders_number);
  }

  // 3. searchOrders ------------------------------------------------------------------------------>
  public List<Orders> searchOrders(String searchType, String keyword, String member_id)
  throws Exception {

    Map<String, String> map = new HashMap<>();
    map.put("searchType", searchType);
    map.put("keyword", keyword);
    map.put("member_id", member_id);

    return sqlSession.selectList("searchOrders", map);
  }

  // 3. getStripePrice ---------------------------------------------------------------------------->
  @Override
  public String getStripePrice(Integer product_number) {

    return sqlSession.selectOne("getStripePrice", product_number);
  }

  // 4. addOrders --------------------------------------------------------------------------------->
  @Override
  public void addOrders(Orders orders) {

    sqlSession.insert("addOrders", orders);
  }

  // 4-2. successOrders 4-3. failOrders 5. updateOrders

  // 5-1. updateProductStock ---------------------------------------------------------------------->
  @Override
  public Integer updateProductStock(Integer product_number, Integer product_stock, Integer orders_quantity) {

    Map<String, Object> map = new HashMap<>();
    map.put("product_number", product_number);
    map.put("product_stock", product_stock);
    map.put("orders_quantity", orders_quantity);

    return sqlSession.update("updateProductStock", map);
  }

  // 6. deleteOrders ------------------------------------------------------------------------------>
  @Override
  public void deleteOrders(Integer orders_number) {

    sqlSession.delete("deleteOrders", orders_number);
  }

}