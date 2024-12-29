package com.JUNGHQLO.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.JUNGHQLO.model.Board;
import com.JUNGHQLO.handler.LocalDateTimeTypeHandler;

@Mapper
public interface BoardMapper {

  // 0. result mapping -----------------------------------------------------------------------------
  @Results ({
    @Result (
      property = "board_number",
      column = "board_number",
      id = true
    ),
    @Result (
      property = "board_title",
      column = "board_title"
    ),
    @Result (
      property = "board_contents",
      column = "board_contents"
    ),
    @Result (
      property = "board_writer",
      column = "board_writer"
    ),
    @Result (
      property = "board_count",
      column = "board_count"
    ),
    @Result (
      property = "board_like",
      column = "board_like"
    ),
    @Result (
      property = "board_dislike",
      column = "board_dislike"
    ),
    @Result (
      property = "board_imgsUrl",
      column = "board_imgsUrl"
    ),
    @Result (
      property = "board_date",
      column = "board_date",
      typeHandler = LocalDateTimeTypeHandler.class
    ),
    @Result (
      property = "board_update",
      column = "board_update",
      typeHandler = LocalDateTimeTypeHandler.class
    )
  })

  // 1. listBoard ---------------------------------------------------------------------------------
  @Select(
    """
    SELECT
      *
    FROM
      board
    WHERE
      ${type} LIKE CONCAT('%', #{keyword}, '%')
    ORDER BY
      ${sort}
    """
  )
  List<Board> listBoard(
    @Param("sort") String sort,
    @Param("type") String type,
    @Param("keyword") String keyword
  ) throws Exception;

  // 2. detailBoard --------------------------------------------------------------------------------
  @Select(
    """
    SELECT
      *
    FROM
      board
    WHERE
      board_number = #{board_number}
    """
  )
  Board detailBoard(
    Integer board_number
  ) throws Exception;

  // 3. saveBoard ----------------------------------------------------------------------------------
  @Insert(
    """
    INSERT INTO
      board(
        board_title,
        board_contents,
        board_writer,
        board_count,
        board_like,
        board_dislike,
        board_imgsUrl,
        board_date
      )
    VALUES (
      #{board.board_title},
      #{board.board_contents},
      #{board.board_writer},
      #{board.board_count},
      #{board.board_like},
      #{board.board_dislike},
      #{imgsUrl},
      NOW()
    )
    """
  )
  Integer saveBoard(
    @Param("board") Board board,
    @Param("imgsUrl") String imgsUrl
  ) throws Exception;

  // 4-1. updateBoard ------------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      board
    SET
      board_title = #{board.board_title},
      board_contents = #{board.board_contents},
      board_writer = #{board.board_writer},
      board_count = #{board.board_count},
      board_like = #{board.board_like},
      board_dislike = #{board.board_dislike},
      board_imgsUrl = #{imgsUrl},
      board_update = NOW()
    WHERE
      board_number = #{board.board_number}
    """
  )
  Integer updateBoard(
    @Param("board") Board board,
    @Param("imgsUrl") String imgsUrl
  ) throws Exception;

  // 4-2. updateCount ------------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      board
    SET
      board_count = board_count + 1
    WHERE
      board_number = #{board_number}
    """
  )
  void updateCount(
    Integer board_number
  ) throws Exception;

  // 4-3. updateLike -------------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      board
    SET
      board_like = board_like + 1
    WHERE
      board_number = #{board_number}
    """
  )
  void updateLike(
    Integer board_number
  ) throws Exception;

  // 4-4. updateDislike ----------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      board
    SET
      board_dislike = board_dislike + 1
    WHERE
      board_number = #{board_number}
    """
  )
  void updateDislike(
    Integer board_number
  ) throws Exception;

  // 5. deleteBoard --------------------------------------------------------------------------------
  @Delete(
    """
    DELETE FROM
      board
    WHERE
      board_number = #{board_number}
    """
  )
  Integer deleteBoard(
    Integer board_number
  ) throws Exception;

}