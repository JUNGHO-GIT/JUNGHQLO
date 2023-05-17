package com.example.junghqlo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.example.junghqlo.domain.Product;

@Repository
public class ProductMapperImpl implements ProductMapper {

  // 0. constructor injection --------------------------------------------------------------------->
  SqlSessionTemplate sqlSession;
  ProductMapperImpl(SqlSessionTemplate sqlSession) {
  this.sqlSession = sqlSession;
  }

  // 1. getProductList ---------------------------------------------------------------------------->
  @Override
  public List<Product> getProductList(String sort) throws Exception {

    return sqlSession.selectList("getProductList", sort);
  }

  // 1-1. getProductCategory ---------------------------------------------------------------------->
  @Override
  public List<Product> getProductCategory(String category, String sort) throws Exception {

    Map<String, Object> params = new HashMap<>();
    params.put("category", category);
    params.put("sort", sort);

    return sqlSession.selectList("getProductCategory", params);
  }

  // 2. getProductDetails ------------------------------------------------------------------------->
  @Override
  public Product getProductDetails(Integer product_number) throws Exception {

    return sqlSession.selectOne("getProductDetails", product_number);
  }

  // 3. searchProduct ----------------------------------------------------------------------------->
  @Override
  public List<Product> searchProduct(String searchType, String keyword) throws Exception {

    Map<String, Object> params = new HashMap<>();
    params.put("searchType", searchType);
    params.put("keyword", keyword);

    return sqlSession.selectList("searchProduct", params);
  }

  // 4. addProduct -------------------------------------------------------------------------------->
  @Override
  public void addProduct(Product product) throws Exception {

    sqlSession.insert("addProduct", product);
  }

  // 5. updateProduct ----------------------------------------------------------------------------->
  @Override
  public void updateProduct(Product product) throws Exception {

    sqlSession.update("updateProduct", product);
  }

  // 6. deleteProduct ----------------------------------------------------------------------------->
  @Override
  public void deleteProduct(Product product) throws Exception {

    sqlSession.delete("deleteProduct", product);
  }

}