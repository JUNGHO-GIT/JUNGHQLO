package com.example.junghqlo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.example.junghqlo.model.Product;

@Repository
public class ProductMapperImpl implements ProductMapper {

  // 0. constructor injection ----------------------------------------------------------------------
  SqlSessionTemplate sqlSession;
  ProductMapperImpl(SqlSessionTemplate sqlSession) {
    this.sqlSession = sqlSession;
  }

  // 1. listProduct --------------------------------------------------------------------------------
  @Override
  public List<Product> listProduct(
    String sort,
    String category,
    String type,
    String keyword
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("sort", sort);
    map.put("category", category);
    map.put("type", type);
    map.put("keyword", keyword);

    return sqlSession.selectList("listProduct", map);
  }

  // 2. detailProduct ------------------------------------------------------------------------------
  @Override
  public Product detailProduct(
    Integer product_number
  ) throws Exception {

    return sqlSession.selectOne("detailProduct", product_number);
  }

  // 3. addProduct ---------------------------------------------------------------------------------
  @Override
  public void addProduct(
    Product product
  ) throws Exception {

    sqlSession.insert("addProduct", product);
  }

  // 4. updateProduct ------------------------------------------------------------------------------
  @Override
  public void updateProduct(
    Product product
  ) throws Exception {

    sqlSession.update("updateProduct", product);
  }

  // 5. deleteProduct ------------------------------------------------------------------------------
  @Override
  public Integer deleteProduct(
    Integer product_number
  ) throws Exception {

    Integer result = 0;

    if (sqlSession.delete("deleteProduct", product_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}