package com.example.junghqlo.service;

import javax.servlet.http.HttpSession;
import com.example.junghqlo.domain.Board;
import com.example.junghqlo.handler.PageHandler;

public interface BoardService {

  // 1.getBoardList ------------------------------------------------------------------------------->
  PageHandler<Board> getBoardList(Integer pageNumber, Integer itemsPer, String sort, Board board)
  throws Exception;

  // 2. getBoardDetails --------------------------------------------------------------------------->
  Board getBoardDetails(Integer board_number) throws Exception;

  // 3. searchBoard --------------------------------------------------------------------------->
  PageHandler<Board> searchBoard(Integer pageNumber, Integer itemsPer, String searchType, String keyword ,Board board) throws Exception;

  // 4. addBoard ---------------------------------------------------------------------------------->
  void addBoard(Board board) throws Exception;

  // 5. updateBoard ------------------------------------------------------------------------------->
  void updateBoard(Board board, String existingImage) throws Exception;

  // 5-1. updateBoardCount ------------------------------------------------------------------------>
  void updateBoardCount(Integer board_number, HttpSession session) throws Exception;

  // 5-2. updateLike ------------------------------------------------------------------------------>
  void updateLike(Integer board_number, HttpSession session) throws Exception;

  // 5-3. updateDislike --------------------------------------------------------------------------->
  void updateDislike(Integer board_number, HttpSession session) throws Exception;

  // 6. deleteBoard ------------------------------------------------------------------------------->
  void deleteBoard(Integer board_number) throws Exception;

}