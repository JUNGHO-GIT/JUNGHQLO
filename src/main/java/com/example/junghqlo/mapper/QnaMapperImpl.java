package com.example.junghqlo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.example.junghqlo.model.Qna;

@Repository
public class QnaMapperImpl implements QnaMapper {

  // 0. constructor injection --------------------------------------------------------------------->
  SqlSessionTemplate sqlSession;
  QnaMapperImpl(SqlSessionTemplate sqlSession) {
  this.sqlSession = sqlSession;
  }

  // 1. getQnaList -------------------------------------------------------------------------------->
  @Override
  public List<Qna> getQnaList(String sort) throws Exception {

    return sqlSession.selectList("getQnaList", sort);
  }

  // 2. getQnaDetails ----------------------------------------------------------------------------->
  @Override
  public Qna getQnaDetails(Integer qnd_number) throws Exception {

    return sqlSession.selectOne("getQnaDetails", qnd_number);
  }

  // 3. searchQna --------------------------------------------------------------------------------->
  @Override
  public List<Qna> searchQna(String searchType, String keyword) throws Exception {

    Map<String, Object> params = new HashMap<>();
    params.put("searchType", searchType);
    params.put("keyword", keyword);

    return sqlSession.selectList("searchQna", params);
  }

  // 4. addQna ------------------------------------------------------------------------------------>
  @Override
  public void addQna(Qna qna) throws Exception {

    sqlSession.insert("addQna", qna);
  }

  // 5. updateQna --------------------------------------------------------------------------------->
  @Override
  public void updateQna(Qna qna) throws Exception {

    sqlSession.update("updateQna", qna);
  }

  // 5-1. updateQnaCount ----------------------------------------------------------------------->
  @Override
  public void updateQnaCount(Integer qna_number) throws Exception {

    sqlSession.update("updateQnaCount", qna_number);
  }

  // 5-2. updateLike ------------------------------------------------------------------------------>
  @Override
  public void updateLike(Integer qna_number) throws Exception {

    sqlSession.update("updateQnaLike", qna_number);
  }

  // 5-3. updateDislike --------------------------------------------------------------------------->
  @Override
  public void updateDislike(Integer qna_number) throws Exception {

    sqlSession.update("updateQnaDislike", qna_number);
  }

  // 6. deleteQna --------------------------------------------------------------------------------->
  @Override
  public void deleteQna(Integer qnd_number) throws Exception {

    sqlSession.delete("deleteQna", qnd_number);
  }

}