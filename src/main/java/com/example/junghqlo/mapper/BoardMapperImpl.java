package com.example.junghqlo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.example.junghqlo.model.Board;

@Repository
public class BoardMapperImpl implements BoardMapper {

  // 0. constructor injection ----------------------------------------------------------------------
  SqlSessionTemplate sqlSession;
  BoardMapperImpl(SqlSessionTemplate sqlSession) {
    this.sqlSession = sqlSession;
  }

  // 1-1. listBoard -------------------------------------------------------------------------------
  @Override
  public List<Board> listBoard(String sort) throws Exception {

    return sqlSession.selectList("listBoard", sort);
  }

  // 1-2. searchBoard ------------------------------------------------------------------------------
  @Override
  public List<Board> searchBoard(String searchType, String keyword) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("searchType", searchType);
    map.put("keyword", keyword);

    return sqlSession.selectList("searchBoard", map);
  }

  // 2. detailBoard --------------------------------------------------------------------------------
  @Override
  public Board detailBoard(Integer board_number) throws Exception {

    return sqlSession.selectOne("detailBoard", board_number);
  }

  // 3. addBoard -----------------------------------------------------------------------------------
  @Override
  public void addBoard(Board board) throws Exception {

    sqlSession.insert("addBoard", board);
  }

  // 4. updateBoard --------------------------------------------------------------------------------
  @Override
  public void updateBoard(Board board) throws Exception {

    sqlSession.update("updateBoard", board);
  }

  // 4-1. updateBoardCount -------------------------------------------------------------------------
  @Override
  public void updateBoardCount(Integer board_number) throws Exception {

    sqlSession.update("updateBoardCount", board_number);
  }

  // 4-2. updateLike -------------------------------------------------------------------------------
  @Override
  public void updateLike(Integer board_number) throws Exception {

    sqlSession.update("updateLike", board_number);
  }

  // 4-3. updateDislike ----------------------------------------------------------------------------
  @Override
  public void updateDislike(Integer board_number) throws Exception {

    sqlSession.update("updateDislike", board_number);
  }

  // 5. deleteBoard --------------------------------------------------------------------------------
  @Override
  public Integer deleteBoard(Integer board_number) throws Exception {

    Integer result = 0;

    if (sqlSession.delete("deleteBoard", board_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}