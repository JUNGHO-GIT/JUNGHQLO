package com.JUNGHQLO.service;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import com.JUNGHQLO.handler.PageHandler;
import com.JUNGHQLO.model.Notice;

public interface NoticeService {

  // 1. listNotice ---------------------------------------------------------------------------------
  PageHandler<Notice> listNotice(
    Integer pageNumber,
    Integer itemsPer,
    String sort,
    String type,
    String keyword,
    Notice notice
  ) throws Exception;

  // 2. detailNotice -------------------------------------------------------------------------------
  Notice detailNotice(
    Integer notice_number
  ) throws Exception;

  // 3. saveNotice ---------------------------------------------------------------------------------
  Integer saveNotice(
    Notice notice,
    List<MultipartFile> imgsFile
  ) throws Exception;

  // 4-1. updateNotice -----------------------------------------------------------------------------
  Integer updateNotice(
    Notice notice,
    List<MultipartFile> imgsFile
  ) throws Exception;

  // 4-2. updateCount ------------------------------------------------------------------------------
  void updateCount(
    Integer notice_number,
    HttpSession session
  ) throws Exception;

  // 4-3. updateLike -------------------------------------------------------------------------------
  void updateLike(
    Integer notice_number,
    HttpSession session
  ) throws Exception;

  // 4-4. updateDislike ----------------------------------------------------------------------------
  void updateDislike(
    Integer notice_number,
    HttpSession session
  ) throws Exception;

  // 5. deleteNotice -------------------------------------------------------------------------------
  Integer deleteNotice(
    Integer notice_number
  ) throws Exception;
}