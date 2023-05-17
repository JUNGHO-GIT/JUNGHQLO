package com.example.junghqlo.service;

import javax.servlet.http.HttpSession;
import com.example.junghqlo.domain.Notice;
import com.example.junghqlo.handler.PageHandler;
public interface NoticeService {

  // 1. getNoticeList ----------------------------------------------------------------------------->
  PageHandler<Notice> getNoticeList(Integer pageNumber, Integer itemsPer, String sort, Notice notice) throws Exception;

  // 2. getNoticeDetails -------------------------------------------------------------------------->
  Notice getNoticeDetails(Integer notice_number) throws Exception;

  // 3. searchNotice ------------------------------------------------------------------------------>
  PageHandler<Notice> searchNotice(Integer pageNumber, Integer itemsPer, String searchType, String keyword, Notice notice) throws Exception;

  // 4. addNotice --------------------------------------------------------------------------------->
  void addNotice(Notice notice) throws Exception;

  // 5. updateNotice ------------------------------------------------------------------------------>
  void updateNotice(Notice notice, String existingImage) throws Exception;

  // 5-1. updateNoticeCount ----------------------------------------------------------------------->
  void updateNoticeCount(Integer notice_number, HttpSession session) throws Exception;

  // 5-2. updateLike ------------------------------------------------------------------------------>
  void updateLike(Integer notice_number, HttpSession session) throws Exception;

  // 5-3. updateDislike --------------------------------------------------------------------------->
  void updateDislike(Integer notice_number, HttpSession session) throws Exception;

  // 6. deleteNotice ------------------------------------------------------------------------------>
  void deleteNotice(Integer notice_number) throws Exception;

}