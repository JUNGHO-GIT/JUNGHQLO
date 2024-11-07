package com.example.junghqlo.service;

import javax.servlet.http.HttpSession;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Notice;
public interface NoticeService {

  // 1-1. listNotice ------------------------------------------------------------------------------
  PageHandler<Notice> listNotice(Integer pageNumber, Integer itemsPer, String sort, Notice notice) throws Exception;

  // 2. detailNotice ---------------------------------------------------------------------------
  Notice detailNotice(Integer notice_number) throws Exception;

  // 1-2. searchNotice -------------------------------------------------------------------------------
  PageHandler<Notice> searchNotice(Integer pageNumber, Integer itemsPer, String searchType, String keyword, Notice notice) throws Exception;

  // 3. addNotice ----------------------------------------------------------------------------------
  void addNotice(Notice notice) throws Exception;

  // 4. updateNotice -------------------------------------------------------------------------------
  void updateNotice(Notice notice, String existingImage) throws Exception;

  // 4-1. updateNoticeCount ------------------------------------------------------------------------
  void updateNoticeCount(Integer notice_number, HttpSession session) throws Exception;

  // 4-2. updateLike -------------------------------------------------------------------------------
  void updateLike(Integer notice_number, HttpSession session) throws Exception;

  // 4-3. updateDislike ----------------------------------------------------------------------------
  void updateDislike(Integer notice_number, HttpSession session) throws Exception;

  // 5. deleteNotice -------------------------------------------------------------------------------
  Integer deleteNotice(Integer notice_number) throws Exception;

}