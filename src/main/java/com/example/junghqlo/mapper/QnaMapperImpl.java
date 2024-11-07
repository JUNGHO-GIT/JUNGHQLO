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

  // 1-1. listQna ---------------------------------------------------------------------------------
  @Override
  public List<Qna> listQna(
    String sort
  ) throws Exception {

    return sqlSession.selectList("listQna", sort);
  }

  // 1-2. searchQna -------------------------------------------------------------------------------
  @Override
  public List<Qna> searchQna(
    String searchType,
    String keyword
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("searchType", searchType);
    map.put("keyword", keyword);

    return sqlSession.selectList("searchQna", map);
  }

  // 2. detailQna ----------------------------------------------------------------------------------
  @Override
  public Qna detailQna(
    Integer qna_number
  ) throws Exception {

    return sqlSession.selectOne("detailQna", qna_number);
  }

  // 3. addQna -------------------------------------------------------------------------------------
  @Override
  public void addQna(
    Qna qna
  ) throws Exception {

    sqlSession.insert("addQna", qna);
  }

  // 4. updateQna ----------------------------------------------------------------------------------
  @Override
  public void updateQna(
    Qna qna
  ) throws Exception {

    sqlSession.update("updateQna", qna);
  }

  // 4-1. updateQnaCount ------------------------------------------------------------------------
  @Override
  public void updateQnaCount(
    Integer qna_number
  ) throws Exception {

    sqlSession.update("updateQnaCount", qna_number);
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
  public Integer deleteQna(Integer qnd_number) throws Exception {

    Integer result = 0;

    if (sqlSession.delete("deleteQna", qnd_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}