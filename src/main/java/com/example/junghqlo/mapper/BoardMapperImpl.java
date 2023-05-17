package com.example.junghqlo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.example.junghqlo.domain.Board;

@Repository
public class BoardMapperImpl implements BoardMapper {

  // 0. constructor injection --------------------------------------------------------------------->
  SqlSessionTemplate sqlSession;
  BoardMapperImpl(SqlSessionTemplate sqlSession) {
  this.sqlSession = sqlSession;
  }

  // 1. getBoardList ------------------------------------------------------------------------------>
  @Override
  public List<Board> getBoardList(String sort) throws Exception {

    return sqlSession.selectList("getBoardList", sort);
  }

  // 2. getBoardDetails --------------------------------------------------------------------------->
  @Override
  public Board getBoardDetails(Integer board_number) throws Exception {

    return sqlSession.selectOne("getBoardDetails", board_number);
  }

  // 3. searchBoard ------------------------------------------------------------------------------->
  @Override
  public List<Board> searchBoard(String searchType, String keyword) throws Exception {

    Map<String, Object> params = new HashMap<>();
    params.put("searchType", searchType);
    params.put("keyword", keyword);

    return sqlSession.selectList("searchBoard", params);
  }

  // 4. addBoard ---------------------------------------------------------------------------------->
  @Override
  public void addBoard(Board board) throws Exception {

    sqlSession.insert("addBoard", board);
  }

  // 5. updateBoard ------------------------------------------------------------------------------->
  @Override
  public void updateBoard(Board board) throws Exception {

    sqlSession.update("updateBoard", board);
  }

  // 5-1. updateBoardCount ------------------------------------------------------------------------>
  @Override
  public void updateBoardCount(Integer board_number) throws Exception {

    sqlSession.update("updateBoardCount", board_number);
  }

  // 5-2. updateLike ------------------------------------------------------------------------------>
  @Override
  public void updateLike(Integer board_number) throws Exception {

    sqlSession.update("updateLike", board_number);
  }

  // 5-3. updateDislike --------------------------------------------------------------------------->
  @Override
  public void updateDislike(Integer board_number) throws Exception {

    sqlSession.update("updateDislike", board_number);
  }

  // 6. deleteBoard ------------------------------------------------------------------------------->
  @Override
  public void deleteBoard(Integer board_number) throws Exception {

    sqlSession.delete("deleteBoard", board_number);
  }

}