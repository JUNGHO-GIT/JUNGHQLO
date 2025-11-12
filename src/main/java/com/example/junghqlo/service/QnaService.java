package com.example.junghqlo.service;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Qna;

public interface QnaService {

  // 1. listQna ---------------------------------------------------------------------------------
  PageHandler<Qna> listQna(
    Integer pageNumber,
    Integer itemsPer,
    String sort,
    String category,
    String type,
    String keyword,
    Qna qna
  ) throws Exception;

  // 2. detailQna ----------------------------------------------------------------------------------
  Qna detailQna(
    Integer qna_number
  ) throws Exception;

  // 3. saveQna ------------------------------------------------------------------------------------
  Integer saveQna(
    Qna qna,
    List<MultipartFile> imgsFile
  ) throws Exception;

  // 4-1. updateQna --------------------------------------------------------------------------------
  Integer updateQna(
    Qna qna,
    List<MultipartFile> imgsFile
  ) throws Exception;

  // 4-2. updateCount ------------------------------------------------------------------------------
  void updateCount(
    Integer qna_number,
    HttpSession session
  ) throws Exception;

  // 4-3. updateLike -------------------------------------------------------------------------------
  void updateLike(
    Integer qna_number,
    HttpSession session
  ) throws Exception;

  // 4-4. updateDislike ----------------------------------------------------------------------------
  void updateDislike(
    Integer qna_number,
    HttpSession session
  ) throws Exception;

  // 5. deleteQna ----------------------------------------------------------------------------------
  Integer deleteQna(
    Integer qna_number
  ) throws Exception;
}