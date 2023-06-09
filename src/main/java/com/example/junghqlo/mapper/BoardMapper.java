package com.example.junghqlo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.example.junghqlo.model.Board;

@Mapper
public interface BoardMapper {

  // 0. result mapping ---------------------------------------------------------------------------->
  @Results ({
    @Result (property="board_number",   column="board_number", id=true),
    @Result (property="board_title",    column="board_title"),
    @Result (property="board_contents", column="board_contents"),
    @Result (property="board_writer",   column="board_writer"),
    @Result (property="board_count",    column="board_count"),
    @Result (property="board_like",     column="board_like"),
    @Result (property="board_dislike",   column="board_dislike"),
    @Result (property="board_imgsFile", column="board_imgsFile",
            typeHandler=com.example.junghqlo.handler.MultipartFileHandler.class),
    @Result (property="board_imgsUrl",  column="board_imgsUrl"),
    @Result (property="board_date",     column="board_date",
            typeHandler=com.example.junghqlo.handler.LocalDateTimeTypeHandler.class),
    @Result (property="board_update",   column="board_update",
            typeHandler=com.example.junghqlo.handler.LocalDateTimeTypeHandler.class)
  })

  // 1. getBoardList ------------------------------------------------------------------------------>
  @Select("SELECT * FROM board ORDER BY ${sort}")
  List<Board> getBoardList(@Param("sort") String sort) throws Exception;

  // 2. getBoardDetails --------------------------------------------------------------------------->
  @Select("SELECT * FROM board WHERE board_number=#{board_number}")
  Board getBoardDetails(Integer board_number) throws Exception;

  // 3. searchBoard ------------------------------------------------------------------------------->
  @Select("SELECT * FROM board WHERE ${keyword} LIKE CONCAT('%', #{searchType}, '%')")
  List<Board> searchBoard(@Param("searchType") String searchType, @Param("keyword") String keyword) throws Exception;

  // 4. addBoard ---------------------------------------------------------------------------------->
  @Insert("INSERT INTO board(board_title, board_contents, board_writer, board_count, board_like, board_dislike, board_imgsFile, board_imgsUrl, board_date) VALUES (#{board_title}, #{board_contents}, #{board_writer}, #{board_count}, #{board_like}, #{board_dislike}, #{board_imgsFile, typeHandler=com.example.junghqlo.handler.MultipartFileHandler}, #{board_imgsUrl}, NOW())")
  void addBoard(Board board) throws Exception;

  // 5. updateBoard ------------------------------------------------------------------------------->
  @Update("UPDATE board SET board_title=#{board_title}, board_contents=#{board_contents}, board_writer=#{board_writer}, board_count=#{board_count}, board_like=#{board_like}, board_dislike=#{board_dislike}, board_imgsFile=#{board_imgsFile, typeHandler=com.example.junghqlo.handler.MultipartFileHandler}, board_imgsUrl=#{board_imgsUrl}, board_update=NOW() WHERE board_number=#{board_number}")
  void updateBoard(Board board) throws Exception;

  // 5-1. updateBoardCount ------------------------------------------------------------------------>
  @Update("UPDATE board SET board_count=board_count+1 WHERE board_number=#{board_number}")
  void updateBoardCount(Integer board_number) throws Exception;

  // 5-2. updateLike ------------------------------------------------------------------------------>
  @Update("UPDATE board SET board_like=board_like+1 WHERE board_number=#{board_number}")
  void updateLike(Integer board_number) throws Exception;

  // 5-3. updateDislike --------------------------------------------------------------------------->
  @Update("UPDATE board SET board_dislike=board_dislike+1 WHERE board_number=#{board_number}")
  void updateDislike(Integer board_number) throws Exception;

  // 6. deleteBoard ------------------------------------------------------------------------------->
  @Delete("DELETE FROM board WHERE board_number=#{board_number}")
  void deleteBoard(Integer board_number) throws Exception;

}