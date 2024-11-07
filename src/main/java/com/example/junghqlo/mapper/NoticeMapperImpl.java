package com.example.junghqlo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.example.junghqlo.model.Notice;

@Repository
public class NoticeMapperImpl implements NoticeMapper {

  // 0. constructor injection ----------------------------------------------------------------------
  SqlSessionTemplate sqlSession;
  NoticeMapperImpl(SqlSessionTemplate sqlSession) {
  this.sqlSession = sqlSession;
  }

  // 1-1. listNotice ------------------------------------------------------------------------------
  @Override
  public List<Notice> listNotice(String sort) throws Exception {

    return sqlSession.selectList("listNotice", sort);
  }

  // 1-2. searchNotice -----------------------------------------------------------------------------
  @Override
  public List<Notice> searchNotice(String searchType, String keyword) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("searchType", searchType);
    map.put("keyword", keyword);

    return sqlSession.selectList("searchNotice", map);
  }

  // 2. detailNotice ---------------------------------------------------------------------------
  @Override
  public Notice detailNotice(Integer notice_number) throws Exception {

    return sqlSession.selectOne("detailNotice", notice_number);
  }

  // 3. addNotice ----------------------------------------------------------------------------------
  @Override
  public void addNotice(Notice notice) throws Exception {

    sqlSession.insert("addNotice", notice);
  }

  // 4. updateNotice -------------------------------------------------------------------------------
  @Override
  public void updateNotice(Notice notice) throws Exception {

    sqlSession.update("updateNotice", notice);
  }

  // 4-1. updateNoticeCount ------------------------------------------------------------------------
  @Override
  public void updateNoticeCount(Integer notice_number) throws Exception {

    sqlSession.update("updateNoticeCount", notice_number);
  }

  // 4-2. updateLike -------------------------------------------------------------------------------
  @Override
  public void updateLike(Integer notice_number) throws Exception {

    sqlSession.update("updateNoticeLike", notice_number);
  }

  // 4-3. updateDislike ----------------------------------------------------------------------------
  @Override
  public void updateDislike(Integer notice_number) throws Exception {

    sqlSession.update("updateNoticeDislike", notice_number);
  }

  // 5. deleteNotice -------------------------------------------------------------------------------
  @Override
  public Integer deleteNotice(Integer notice_number) throws Exception {

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