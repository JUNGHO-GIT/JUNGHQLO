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
import com.example.junghqlo.domain.Notice;

@Mapper
public interface NoticeMapper {

  // 0. result mapping ---------------------------------------------------------------------------->
  @Results ({
    @Result (property="notice_number",   column="notice_number", id=true),
    @Result (property="notice_title",    column="notice_title"),
    @Result (property="notice_contents", column="notice_contents"),
    @Result (property="notice_writer",   column="notice_writer"),
    @Result (property="notice_count",    column="notice_count"),
    @Result (property="notice_like",     column="notice_like"),
    @Result (property="notice_dislike",   column="notice_dislike"),
    @Result (property="notice_imgsFile", column="notice_imgsFile",
            typeHandler=com.example.junghqlo.handler.MultipartFileHandler.class),
    @Result (property="notice_imgsUrl",  column="notice_imgsUrl"),
    @Result (property="notice_date",     column="notice_date",
            typeHandler=com.example.junghqlo.handler.LocalDateTimeTypeHandler.class),
    @Result (property="notice_update",   column="notice_update",
            typeHandler=com.example.junghqlo.handler.LocalDateTimeTypeHandler.class)
  })

  // 1. getNoticeList ----------------------------------------------------------------------------->
  @Select("SELECT * FROM notice ORDER BY ${sort}")
  List<Notice> getNoticeList(@Param("sort") String sort) throws Exception;

  // 2. getNoticeDetails -------------------------------------------------------------------------->
  @Select("SELECT * FROM notice WHERE notice_number=#{notice_number}")
  Notice getNoticeDetails(Integer notice_number) throws Exception;

  // 3. searchNotice ------------------------------------------------------------------------------>
  @Select("SELECT * FROM notice WHERE ${keyword} LIKE CONCAT('%', #{searchType}, '%')")
  List<Notice> searchNotice(@Param("searchType") String searchType, @Param("keyword") String keyword) throws Exception;

  // 4. addNotice --------------------------------------------------------------------------------->
  @Insert("INSERT INTO notice(notice_title, notice_contents, notice_writer, notice_count, notice_like, notice_dislike, notice_imgsFile, notice_imgsUrl, notice_date) VALUES (#{notice_title}, #{notice_contents}, #{notice_writer}, #{notice_count}, #{notice_like}, #{notice_dislike}, #{notice_imgsFile, typeHandler=com.example.junghqlo.handler.MultipartFileHandler}, #{notice_imgsUrl}, NOW())")
  void addNotice(Notice notice) throws Exception;

  // 5. updateNotice ------------------------------------------------------------------------------>
  @Update("UPDATE notice SET notice_title=#{notice_title}, notice_contents=#{notice_contents},  notice_writer=#{notice_writer}, notice_count=#{notice_count}, notice_like=#{notice_like}, notice_dislike=#{notice_dislike}, notice_imgsFile=#{notice_imgsFile, typeHandler=com.example.junghqlo.handler.MultipartFileHandler}, notice_imgsUrl=#{notice_imgsUrl}, notice_update=NOW() WHERE notice_number=#{notice_number}")
  void updateNotice(Notice notice) throws Exception;

  // 5-1. updateNoticeCount ----------------------------------------------------------------------->
  @Update("UPDATE notice SET notice_count=notice_count+1 WHERE notice_number=#{notice_number}")
  void updateNoticeCount(Integer notice_number) throws Exception;

  // 5-2. updateLike ------------------------------------------------------------------------------>
  @Update("UPDATE notice SET notice_like=notice_like+1 WHERE notice_number=#{notice_number}")
  void updateLike(Integer notice_number) throws Exception;

  // 5-3. updateDislike --------------------------------------------------------------------------->
  @Update("UPDATE notice SET notice_dislike=notice_dislike+1 WHERE notice_number=#{notice_number}")
  void updateDislike(Integer notice_number) throws Exception;

  // 6. deleteNotice ------------------------------------------------------------------------------>
  @Delete("DELETE FROM notice WHERE notice_number=#{notice_number}")
  void deleteNotice(Integer notice_number) throws Exception;

}