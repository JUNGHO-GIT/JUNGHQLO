package com.example.junghqlo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.example.junghqlo.model.Qna;

@Repository
public class QnaMapperImpl implements QnaMapper {

  // 0. constructor injection ----------------------------------------------------------------------
  SqlSessionTemplate sqlSession;
  QnaMapperImpl(SqlSessionTemplate sqlSession) {
    this.sqlSession = sqlSession;
  }

  // 1. listQna ------------------------------------------------------------------------------------
  @Override
  public List<Qna> listQna(
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

    return sqlSession.selectList("listQna", map);
  }

  // 2. detailQna ----------------------------------------------------------------------------------
  @Override
  public Qna detailQna(
    Integer qna_number
  ) throws Exception {

    return sqlSession.selectOne("detailQna", qna_number);
  }

  // 3. saveQna ----------------------------------------------------------------------------------
  @Override
  public Integer saveQna(
    Qna qna,
    String imgsUrl
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("qna", qna);
    map.put("imgsUrl", imgsUrl);

    Integer result = 0;

    if (sqlSession.insert("saveQna", map) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-1. updateQna --------------------------------------------------------------------------------
  @Override
  public Integer updateQna(
    Qna qna,
    String imgsUrl
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("qna", qna);
    map.put("imgsUrl", imgsUrl);

    Integer result = 0;

    if (sqlSession.update("updateQna", map) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-1. updateCount ------------------------------------------------------------------------------
  @Override
  public void updateCount(
    Integer qna_number
  ) throws Exception {

    sqlSession.update("updateCount", qna_number);
  }

  // 4-2. updateLike -------------------------------------------------------------------------------
  @Override
  public void updateLike(
    Integer qna_number
  ) throws Exception {

    sqlSession.update("updateQnaLike", qna_number);
  }

  // 4-3. updateDislike ----------------------------------------------------------------------------
  @Override
  public void updateDislike(
    Integer qna_number
  ) throws Exception {

    sqlSession.update("updateQnaDislike", qna_number);
  }

  // 5. deleteQna ----------------------------------------------------------------------------------
  @Override
  public Integer deleteQna(
    Integer qna_number
  ) throws Exception {

    Integer result = 0;

    if (sqlSession.delete("deleteQna", qna_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}