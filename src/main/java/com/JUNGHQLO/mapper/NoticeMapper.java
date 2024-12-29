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
import com.JUNGHQLO.model.Notice;
import com.JUNGHQLO.handler.LocalDateTimeTypeHandler;

@Mapper
public interface NoticeMapper {

  // 0. result mapping -----------------------------------------------------------------------------
  @Results ({
    @Result (
      property = "notice_number",
      column = "notice_number",
      id = true
    ),
    @Result (
      property = "notice_title",
      column = "notice_title"
    ),
    @Result (
      property = "notice_contents",
      column = "notice_contents"
    ),
    @Result (
      property = "notice_writer",
      column = "notice_writer"
    ),
    @Result (
      property = "notice_count",
      column = "notice_count"
    ),
    @Result (
      property = "notice_like",
      column = "notice_like"
    ),
    @Result (
      property = "notice_dislike",
      column = "notice_dislike"
    ),
    @Result (
      property = "notice_imgsUrl",
      column = "notice_imgsUrl"
    ),
    @Result (
      property = "notice_date",
      column = "notice_date",
      typeHandler = LocalDateTimeTypeHandler.class
    ),
    @Result (
      property = "notice_update",
      column = "notice_update",
      typeHandler = LocalDateTimeTypeHandler.class
    )
  })

  // 1. listNotice ---------------------------------------------------------------------------------
  @Select(
    """
    SELECT
      *
    FROM
      notice
    WHERE
      ${type} LIKE CONCAT('%', #{keyword}, '%')
    ORDER BY
      ${sort}
    """
  )
  List<Notice> listNotice(
    @Param("sort") String sort,
    @Param("type") String type,
    @Param("keyword") String keyword
  ) throws Exception;

  // 2. detailNotice -------------------------------------------------------------------------------
  @Select(
    """
    SELECT
      *
    FROM
      notice
    WHERE
      notice_number = #{notice_number}
    """
  )
  Notice detailNotice(
    Integer notice_number
  ) throws Exception;

  // 3. saveNotice ---------------------------------------------------------------------------------
  @Insert(
    """
    INSERT INTO
      notice(
        notice_title,
        notice_contents,
        notice_writer,
        notice_count,
        notice_like,
        notice_dislike,
        notice_imgsUrl,
        notice_date
      )
    VALUES (
      #{notice.notice_title},
      #{notice.notice_contents},
      #{notice.notice_writer},
      #{notice.notice_count},
      #{notice.notice_like},
      #{notice.notice_dislike},
      #{imgsUrl},
      NOW()
    )
    """
  )
  Integer saveNotice(
    @Param("notice") Notice notice,
    @Param("imgsUrl") String imgsUrl
  ) throws Exception;

  // 4-1. updateNotice -----------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      notice
    SET
      notice_title = #{notice.notice_title},
      notice_contents = #{notice.notice_contents},
      notice_writer = #{notice.notice_writer},
      notice_count = #{notice.notice_count},
      notice_like = #{notice.notice_like},
      notice_dislike = #{notice.notice_dislike},
      notice_imgsUrl = #{imgsUrl},
      notice_update = NOW()
    WHERE
      notice_number = #{notice.notice_number}
    """
  )
  Integer updateNotice(
    @Param("notice") Notice notice,
    @Param("imgsUrl") String imgsUrl
  ) throws Exception;

  // 4-2. updateCount ------------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      notice
    SET
      notice_count = notice_count + 1
    WHERE
      notice_number = #{notice_number}
    """
  )
  void updateCount(
    Integer notice_number
  ) throws Exception;

  // 4-3. updateLike -------------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      notice
    SET
      notice_like = notice_like + 1
    WHERE
      notice_number = #{notice_number}
    """
  )
  void updateLike(
    Integer notice_number
  ) throws Exception;

  // 4-4. updateDislike ----------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      notice
    SET
      notice_dislike = notice_dislike + 1
    WHERE
      notice_number = #{notice_number}
    """
  )
  void updateDislike(
    Integer notice_number
  ) throws Exception;

  // 5. deleteNotice -------------------------------------------------------------------------------
  @Delete(
    """
    DELETE FROM
      notice
    WHERE
      notice_number = #{notice_number}
    """
  )
  Integer deleteNotice(
    Integer notice_number
  ) throws Exception;
}