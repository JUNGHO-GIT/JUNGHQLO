package com.example.junghqlo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.example.junghqlo.domain.Notice;

@Repository
public class NoticeMapperImpl implements NoticeMapper {

  // 0. constructor injection --------------------------------------------------------------------->
  SqlSessionTemplate sqlSession;
  NoticeMapperImpl(SqlSessionTemplate sqlSession) {
  this.sqlSession = sqlSession;
  }

  // 1. getNoticeList ----------------------------------------------------------------------------->
  @Override
  public List<Notice> getNoticeList(String sort) throws Exception {

    return sqlSession.selectList("getNoticeList", sort);
  }

  // 2. getNoticeDetails -------------------------------------------------------------------------->
  @Override
  public Notice getNoticeDetails(Integer notice_number) throws Exception {

    return sqlSession.selectOne("getNoticeDetails", notice_number);
  }

  // 3. searchNotice ------------------------------------------------------------------------------>
  @Override
  public List<Notice> searchNotice(String searchType, String keyword) throws Exception {

    Map<String, Object> params = new HashMap<>();
    params.put("searchType", searchType);
    params.put("keyword", keyword);

    return sqlSession.selectList("searchNotice", params);
  }

  // 4. addNotice --------------------------------------------------------------------------------->
  @Override
  public void addNotice(Notice notice) throws Exception {

    sqlSession.insert("addNotice", notice);
  }

  // 5. updateNotice ------------------------------------------------------------------------------>
  @Override
  public void updateNotice(Notice notice) throws Exception {

    sqlSession.update("updateNotice", notice);
  }

  // 5-1. updateNoticeCount ----------------------------------------------------------------------->
  @Override
  public void updateNoticeCount(Integer notice_number) throws Exception {

    sqlSession.update("updateNoticeCount", notice_number);
  }

  // 5-2. updateLike ------------------------------------------------------------------------------>
  @Override
  public void updateLike(Integer notice_number) throws Exception {

    sqlSession.update("updateNoticeLike", notice_number);
  }

  // 5-3. updateDislike --------------------------------------------------------------------------->
  @Override
  public void updateDislike(Integer notice_number) throws Exception {

    sqlSession.update("updateNoticeDislike", notice_number);
  }

  // 6. deleteNotice ------------------------------------------------------------------------------>
  @Override
  public void deleteNotice(Integer notice_number) throws Exception {

    sqlSession.delete("deleteNotice", notice_number);
  }

}