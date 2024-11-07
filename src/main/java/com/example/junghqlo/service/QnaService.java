package com.example.junghqlo.service;

import javax.servlet.http.HttpSession;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Qna;

public interface QnaService {

  // 1-1. listQna ---------------------------------------------------------------------------------
  PageHandler<Qna> listQna(Integer pageNumber, Integer itemsPer, String sort, Qna qna)
  throws Exception;

  // 2. detailQna ------------------------------------------------------------------------------
  Qna detailQna(Integer qna_number) throws Exception;

  // 1-2. searchQna ----------------------------------------------------------------------------------
  PageHandler<Qna> searchQna(Integer pageNumber, Integer itemsPer, String searchType, String keyword, Qna qna) throws Exception;

  // 3. addQna -------------------------------------------------------------------------------------
  void addQna(Qna qna) throws Exception;

  // 4. updateQna ----------------------------------------------------------------------------------
  void updateQna(Qna qna, String existingImage) throws Exception;

  // 4-1. updateQnaCount ---------------------------------------------------------------------------
  void updateQnaCount(Integer qna_number, HttpSession session) throws Exception;

  // 4-2. updateLike -------------------------------------------------------------------------------
  void updateLike(Integer qna_number, HttpSession session) throws Exception;

  // 4-3. updateDislike ----------------------------------------------------------------------------
  void updateDislike(Integer qna_number, HttpSession session) throws Exception;

  // 5. deleteQna ----------------------------------------------------------------------------------
  Integer deleteQna(Integer qna_number) throws Exception;

}