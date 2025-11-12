package com.example.junghqlo.service;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Board;

public interface BoardService {

  // 1. listBoard ----------------------------------------------------------------------------------
  PageHandler<Board> listBoard(
    Integer pageNumber,
    Integer itemsPer,
    String sort,
    String type,
    String keyword,
    Board board
  ) throws Exception;

  // 2. detailBoard --------------------------------------------------------------------------------
  Board detailBoard(
    Integer board_number
  ) throws Exception;

  // 3. saveBoard ----------------------------------------------------------------------------------
  Integer saveBoard(
    Board board,
    List<MultipartFile> imgsFile
  ) throws Exception;

  // 4-1. updateBoard ------------------------------------------------------------------------------
  Integer updateBoard(
    Board board,
    List<MultipartFile> imgsFile
  ) throws Exception;

  // 4-2. updateCount ------------------------------------------------------------------------------
  void updateCount(
    Integer board_number,
    HttpSession session
  ) throws Exception;

  // 4-3. updateLike -------------------------------------------------------------------------------
  void updateLike(
    Integer board_number,
    HttpSession session
  ) throws Exception;

  // 4-4. updateDislike ----------------------------------------------------------------------------
  void updateDislike(
    Integer board_number,
    HttpSession session
  ) throws Exception;

  // 5. deleteBoard --------------------------------------------------------------------------------
  Integer deleteBoard(
    Integer board_number
  ) throws Exception;
}