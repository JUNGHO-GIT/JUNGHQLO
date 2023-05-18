package com.example.junghqlo.service;

import javax.servlet.http.HttpSession;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Qna;

public interface QnaService {

  // 1. getQnaList -------------------------------------------------------------------------------->
  PageHandler<Qna> getQnaList(Integer pageNumber, Integer itemsPer, String sort, Qna qna)
  throws Exception;

  // 2. getQnaDetails ----------------------------------------------------------------------------->
  Qna getQnaDetails(Integer qna_number) throws Exception;

  // 3. searchQna --------------------------------------------------------------------------------->
  PageHandler<Qna> searchQna(Integer pageNumber, Integer itemsPer, String searchType, String keyword, Qna qna) throws Exception;

  // 4. addQna ------------------------------------------------------------------------------------>
  void addQna(Qna qna) throws Exception;

  // 5. updateQna --------------------------------------------------------------------------------->
  void updateQna(Qna qna, String existingImage) throws Exception;

  // 5-1. updateQnaCount -------------------------------------------------------------------------->
  void updateQnaCount(Integer qna_number, HttpSession session) throws Exception;

  // 5-2. updateLike ------------------------------------------------------------------------------>
  void updateLike(Integer qna_number, HttpSession session) throws Exception;

  // 5-3. updateDislike --------------------------------------------------------------------------->
  void updateDislike(Integer qna_number, HttpSession session) throws Exception;

  // 6. deleteQna --------------------------------------------------------------------------------->
  void deleteQna(Integer qna_number) throws Exception;

}