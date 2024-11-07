package com.example.junghqlo.service;

import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Board;

public interface BoardService {

  // 1-1. listBoard --------------------------------------------------------------------------------
  PageHandler<Board> listBoard(
    Integer pageNumber,
    Integer itemsPer,
    String sort
  ) throws Exception;

  // 1-2. searchBoard ------------------------------------------------------------------------------
  PageHandler<Board> searchBoard(
    Integer pageNumber,
    Integer itemsPer,
    String searchType,
    String keyword
  ) throws Exception;

  // 2. detailBoard --------------------------------------------------------------------------------
  Board detailBoard(
    Integer board_number
  ) throws Exception;

  // 3. addBoard -----------------------------------------------------------------------------------
  void addBoard(
    Board board
  ) throws Exception;

  // 4-1. updateBoard ------------------------------------------------------------------------------
  void updateBoard(
    Board board,
    String existingImage
  ) throws Exception;

  // 4-2. updateCount ------------------------------------------------------------------------------
  void updateCount(
    Integer board_number
  ) throws Exception;

  // 4-3. updateLike -------------------------------------------------------------------------------
  void updateLike(
    Integer board_number
  ) throws Exception;

  // 4-4. updateDislike ----------------------------------------------------------------------------
  void updateDislike(
    Integer board_number
  ) throws Exception;

  // 5. deleteBoard --------------------------------------------------------------------------------
  Integer deleteBoard(
    Integer board_number
  ) throws Exception;
}