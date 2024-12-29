package com.JUNGHQLO.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.JUNGHQLO.model.Notice;

@Repository
public class NoticeMapperImpl implements NoticeMapper {

  // 0. constructor injection ----------------------------------------------------------------------
  SqlSessionTemplate sqlSession;
  NoticeMapperImpl(SqlSessionTemplate sqlSession) {
    this.sqlSession = sqlSession;
  }

  // 1. listNotice ---------------------------------------------------------------------------------
  @Override
  public List<Notice> listNotice(
    String sort,
    String type,
    String keyword
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("sort", sort);
    map.put("type", type);
    map.put("keyword", keyword);

    return sqlSession.selectList("listNotice", map);
  }

  // 2. detailNotice -------------------------------------------------------------------------------
  @Override
  public Notice detailNotice(
    Integer notice_number
  ) throws Exception {

    return sqlSession.selectOne("detailNotice", notice_number);
  }

  // 3. saveNotice ---------------------------------------------------------------------------------
  @Override
  public Integer saveNotice(
    Notice notice,
    String imgsUrl
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("notice", notice);
    map.put("imgsUrl", imgsUrl);

    Integer result = 0;

    if (sqlSession.insert("saveNotice", map) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-1. updateNotice -----------------------------------------------------------------------------
  @Override
  public Integer updateNotice(
    Notice notice,
    String imgsUrl
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("notice", notice);
    map.put("imgsUrl", imgsUrl);

    Integer result = 0;

    if (sqlSession.update("updateNotice", map) > 0) {
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
    Integer notice_number
  ) throws Exception {

    sqlSession.update("updateCount", notice_number);
  }

  // 4-3. updateLike -------------------------------------------------------------------------------
  @Override
  public void updateLike(
    Integer notice_number
  ) throws Exception {

    sqlSession.update("updateNoticeLike", notice_number);
  }

  // 4-4. updateDislike ----------------------------------------------------------------------------
  @Override
  public void updateDislike(
    Integer notice_number
  ) throws Exception {

    sqlSession.update("updateNoticeDislike", notice_number);
  }

  // 5. deleteNotice -------------------------------------------------------------------------------
  @Override
  public Integer deleteNotice(
    Integer notice_number
  ) throws Exception {

    Integer result = 0;

    if (sqlSession.delete("deleteNotice", notice_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  };
};