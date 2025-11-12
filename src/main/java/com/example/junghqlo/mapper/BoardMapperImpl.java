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

  // 1. listBoard ----------------------------------------------------------------------------------
  @Override
  public List<Board> listBoard(
    String sort,
    String type,
    String keyword
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("sort", sort);
    map.put("type", type);
    map.put("keyword", keyword);

    return sqlSession.selectList("listBoard", map);
  }

  // 2. detailBoard --------------------------------------------------------------------------------
  @Override
  public Board detailBoard(
    Integer board_number
  ) throws Exception {

    return sqlSession.selectOne("detailBoard", board_number);
  }

  // 3. saveBoard ----------------------------------------------------------------------------------
  @Override
  public Integer saveBoard(
    Board board,
    String imgsUrl
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("board", board);
    map.put("imgsUrl", imgsUrl);

    Integer result = 0;

    if (sqlSession.insert("saveBoard", map) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-1. updateBoard ------------------------------------------------------------------------------
  @Override
  public Integer updateBoard(
    Board board,
    String imgsUrl
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("board", board);
    map.put("imgsUrl", imgsUrl);

    Integer result = 0;

    if (sqlSession.update("updateBoard", map) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-2. updateCount ------------------------------------------------------------------------------
  @Override
  public void updateCount(
    Integer board_number
  ) throws Exception {

    sqlSession.update("updateCount", board_number);
  }

  // 4-3. updateLike -------------------------------------------------------------------------------
  @Override
  public void updateLike(
    Integer board_number
  ) throws Exception {

    sqlSession.update("updateLike", board_number);
  }

  // 4-4. updateDislike ----------------------------------------------------------------------------
  @Override
  public void updateDislike(
    Integer board_number
  ) throws Exception {

    sqlSession.update("updateDislike", board_number);
  }

  // 5. deleteBoard --------------------------------------------------------------------------------
  @Override
  public Integer deleteBoard(
    Integer board_number
  ) throws Exception {

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