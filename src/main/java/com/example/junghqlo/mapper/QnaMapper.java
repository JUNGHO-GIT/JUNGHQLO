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
import com.example.junghqlo.model.Qna;
import com.example.junghqlo.handler.LocalDateTimeTypeHandler;

@Mapper
public interface QnaMapper {

  // 0. result mapping -----------------------------------------------------------------------------
  @Results ({
    @Result (
      property = "qna_number",
      column = "qna_number",
      id = true
    ),
    @Result (
      property = "qna_title",
      column = "qna_title"
    ),
    @Result (
      property = "qna_contents",
      column = "qna_contents"
    ),
    @Result (
      property = "qna_writer",
      column = "qna_writer"
    ),
    @Result (
      property = "qna_category",
      column = "qna_category"
    ),
    @Result (
      property = "qna_count",
      column = "qna_count"
    ),
    @Result (
      property = "qna_like",
      column = "qna_like"
    ),
    @Result (
      property = "qna_dislike",
      column = "qna_dislike"
    ),
    @Result (
      property = "qna_answer",
      column = "qna_answer"
    ),
    @Result (
      property = "qna_imgsUrl",
      column = "qna_imgsUrl"
    ),
    @Result (
      property = "qna_date",
      column = "qna_date",
      typeHandler = LocalDateTimeTypeHandler.class
    ),
    @Result (
      property = "qna_update",
      column = "qna_update",
      typeHandler = LocalDateTimeTypeHandler.class
    )
  })

  // 1. listQna ----------------------------------------------------------------------------------
  @Select(
    """
    SELECT
      *
    FROM
      qna
    WHERE
      ${category}
    AND
      ${type} LIKE CONCAT('%', #{keyword}, '%')
    ORDER BY
      ${sort}
    """
  )
  List<Qna> listQna(
    @Param("sort") String sort,
    @Param("category") String category,
    @Param("type") String type,
    @Param("keyword") String keyword
  ) throws Exception;

  // 2. detailQna ----------------------------------------------------------------------------------
  @Select(
    """
    SELECT
      *
    FROM
      qna
    WHERE
      qna_number = #{qna_number}
    """
  )
  Qna detailQna(
    Integer qna_number
  ) throws Exception;

  // 3. saveQna ------------------------------------------------------------------------------------
  @Insert(
    """
    INSERT INTO
      qna(
        qna_title,
        qna_contents,
        qna_writer,
        qna_category,
        qna_count,
        qna_like,
        qna_dislike,
        qna_answer,
        qna_imgsUrl,
        qna_date
      )
    VALUES(
      #{qna.qna_title},
      #{qna.qna_contents},
      #{qna.qna_writer},
      #{qna.qna_category},
      #{qna.qna_count},
      #{qna.qna_like},
      #{qna.qna_dislike},
      #{qna.qna_answer},
      #{imgsUrl},
      NOW()
    )
    """
  )
  Integer saveQna(
    @Param("qna") Qna qna,
    @Param("imgsUrl") String imgsUrl
  ) throws Exception;

  // 4-1. updateQna --------------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      qna
    SET
      qna_title = #{qna.qna_title},
      qna_contents = #{qna.qna_contents},
      qna_category = #{qna.qna_category},
      qna_answer = #{qna.qna_answer},
      qna_imgsUrl = #{imgsUrl},
      qna_update = NOW()
    WHERE
      qna_number = #{qna.qna_number}
    """
  )
  Integer updateQna(
    @Param("qna") Qna qna,
    @Param("imgsUrl") String imgsUrl
  ) throws Exception;

  // 4-2. updateCount ------------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      qna
    SET
      qna_count = qna_count + 1
    WHERE
      qna_number = #{qna_number}
    """
  )
  void updateCount(
    Integer qna_number
  ) throws Exception;

  // 4-3 updateLike -------------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      qna
    SET
      qna_like = qna_like + 1
    WHERE
      qna_number = #{qna_number}
    """
  )
  void updateLike(
    Integer qna_number
  ) throws Exception;

  // 4-4. updateDislike ----------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      qna
    SET
      qna_dislike = qna_dislike + 1
    WHERE
      qna_number = #{qna_number}
    """
  )
  void updateDislike(
    Integer qna_number
  ) throws Exception;

  // 5. deleteQna ----------------------------------------------------------------------------------
  @Delete(
    """
    DELETE FROM
      qna
    WHERE
      qna_number = #{qna_number}
    """
  )
  Integer deleteQna(
    Integer qna_number
  ) throws Exception;
}