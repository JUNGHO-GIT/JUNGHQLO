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

@Mapper
public interface QnaMapper {

  // 0. result mapping ---------------------------------------------------------------------------->
  @Results ({
    @Result (property="qna_number",   column="qna_number" , id=true),
    @Result (property="qna_title",    column="qna_title"),
    @Result (property="qna_contents", column="qna_contents"),
    @Result (property="qna_writer",   column="qna_writer"),
    @Result (property="qna_category", column="qna_category"),
    @Result (property="qna_count",    column="qna_count"),
    @Result (property="qna_like",     column="qna_like"),
    @Result (property="qna_dislike",  column="qna_dislike"),
    @Result (property="qna_answer1",   column="qna_answer1"),
    @Result (property="qna_answer2",   column="qna_answer2"),
    @Result (property="qna_imgsFile", column="qna_imgsFile",
            typeHandler=com.example.junghqlo.handler.MultipartFileHandler.class),
    @Result (property="qna_imgsUrl",  column="qna_imgsUrl"),
    @Result (property="qna_date",     column="qna_date",
            typeHandler=com.example.junghqlo.handler.LocalDateTimeTypeHandler.class),
    @Result (property="qna_update",   column="qna_update",
            typeHandler=com.example.junghqlo.handler.LocalDateTimeTypeHandler.class),
  })

  // 1. getQnaList -------------------------------------------------------------------------------->
  @Select("SELECT * FROM qna WHERE ${sort}")
  List<Qna> getQnaList(@Param("sort") String sort) throws Exception;

  // 2. getQnaDetails ----------------------------------------------------------------------------->
  @Select("SELECT * FROM qna WHERE qna_number=#{qna_number}")
  Qna getQnaDetails(Integer qna_number) throws Exception;

  // 3. searchQna --------------------------------------------------------------------------------->
  @Select("SELECT * FROM qna WHERE ${keyword} LIKE CONCAT('%', #{searchType}, '%')")
  List<Qna> searchQna(@Param("searchType") String searchType, @Param("keyword") String keyword) throws Exception;

  // 4. addQna ------------------------------------------------------------------------------------>
  @Insert("INSERT INTO qna(qna_title, qna_contents, qna_writer, qna_category, qna_count, qna_like, qna_dislike, qna_answer1, qna_answer2, qna_imgsFile, qna_imgsUrl, qna_date) VALUES(#{qna_title}, #{qna_contents}, #{qna_writer}, #{qna_category}, #{qna_count}, #{qna_like}, #{qna_dislike}, #{qna_answer1}, #{qna_answer2}, #{qna_imgsFile, typeHandler=com.example.junghqlo.handler.MultipartFileHandler}, #{qna_imgsUrl}, NOW())")
  void addQna(Qna qna) throws Exception;

  // 5. updateQna --------------------------------------------------------------------------------->
  @Update("UPDATE qna SET qna_title=#{qna_title}, qna_contents=#{qna_contents}, qna_category=#{qna_category}, qna_answer1=#{qna_answer1}, qna_answer2=#{qna_answer2}, qna_imgsFile=#{qna_imgsFile, typeHandler=com.example.junghqlo.handler.MultipartFileHandler}, qna_imgsUrl=#{qna_imgsUrl}, qna_update=NOW() WHERE qna_number=#{qna_number}")
  void updateQna(Qna qna) throws Exception;

  // 5-1. updateQnaCount -------------------------------------------------------------------------->
  @Update("UPDATE qna SET qna_count=qna_count+1 WHERE qna_number=#{qna_number}")
  void updateQnaCount(Integer qna_number) throws Exception;

  // 5-2. updateLike ------------------------------------------------------------------------------>
  @Update("UPDATE qna SET qna_like=qna_like+1 WHERE qna_number=#{qna_number}")
  void updateLike(Integer qna_number) throws Exception;

  // 5-3. updateDislike --------------------------------------------------------------------------->
  @Update("UPDATE qna SET qna_dislike=qna_dislike+1 WHERE qna_number=#{qna_number}")
  void updateDislike(Integer qna_number) throws Exception;

  // 6. deleteQna --------------------------------------------------------------------------------->
  @Delete("DELETE FROM qna WHERE qna_number=#{qna_number}")
  void deleteQna(Integer qna_number) throws Exception;

}