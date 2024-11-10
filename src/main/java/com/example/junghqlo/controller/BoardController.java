package com.example.junghqlo.controller;

import java.text.MessageFormat;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Board;
import com.example.junghqlo.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

  // 0. constructor injection ----------------------------------------------------------------------
  Logger logger = LoggerFactory.getLogger(this.getClass());
  private BoardService boardService;
  BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  // 0. static -------------------------------------------------------------------------------------
  private static String page = "board";
  private static String PAGE = "Board";

  // 1. listBoard (GET) --------------------------------------------------------------------------
  @GetMapping("/listBoard")
  public String listBoard(
    @ModelAttribute Board board,
    @RequestParam(defaultValue = "all") String sort,
    @RequestParam(defaultValue = "title") String type,
    @RequestParam(defaultValue = "") String keyword,
    @RequestParam(defaultValue = "1") Integer pageNumber,
    @RequestParam(defaultValue = "9") Integer itemsPer,
    Model model
  ) throws Exception {

    // 1. Sort handling
    String sortHandler = "";
    if (sort == null || sort.equals("all")) {
      sort = "board_number DESC";
      sortHandler = "all";
    }
    else if (sort.equals("titleASC")) {
      sort = "board_title ASC";
      sortHandler = "titleASC";
    }
    else if (sort.equals("titleDESC")) {
      sort = "board_title DESC";
      sortHandler = "titleDESC";
    }
    else if (sort.equals("dateASC")) {
      sort = "board_date ASC";
      sortHandler = "dateASC";
    }
    else if (sort.equals("dateDESC")) {
      sort = "board_date DESC";
      sortHandler = "dateDESC";
    }

    // 3. Type handling
    String typeHandler = "";
    if (type == null || keyword == null) {
      return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
    }
    else if (type.equals("all")) {
      type = "board_title OR board_contents";
      typeHandler = "all";
    }
    else if (type.equals("title")) {
      type = "board_title";
      typeHandler = "title";
    }
    else if (type.equals("contents")) {
      type = "board_contents";
      typeHandler = "contents";
    }

    // 4. Keyword handling
    String keywordHandler = "";
    if (keyword != null) {
      keywordHandler = keyword;
    }

    // 5. Page handling
    PageHandler<Board> pageHandler = (
      boardService.listBoard(pageNumber, itemsPer, sort, type, keyword, board)
    );

    // 모델
    model.addAttribute("sortHandler", sortHandler);
    model.addAttribute("typeHandler", typeHandler);
    model.addAttribute("keywordHandler", keywordHandler);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/pages/{0}/{1}List", page, page);
  };

  // 2. detailBoard(GET) ---------------------------------------------------------------------------
  @GetMapping("/detailBoard")
  public String detailBoard(
    @ModelAttribute Board board,
    @RequestParam Integer board_number,
    HttpSession session,
    Model model
  ) throws Exception {

    // 조회수 증가
    boardService.updateCount(board_number, session);

    // 모델
    model.addAttribute("MODEL", boardService.detailBoard(board_number));
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return MessageFormat.format("/pages/{0}/{1}Detail", page, page);
  }

  // 3. saveBoard (GET) -----------------------------------------------------------------------------
  @GetMapping("/saveBoard")
  public String saveBoard() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Save", page, page);
  }

  // 3. saveBoard (POST) ----------------------------------------------------------------------------
  @PostMapping("/saveBoard")
  public Integer saveBoard(
    @ModelAttribute Board board,
    @RequestParam MultipartFile[] imgsFile
  ) throws Exception {

    Integer result = 0;

    if (boardService.saveBoard(board, imgsFile) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-1. updateBoard (GET) ------------------------------------------------------------------------
  @GetMapping("/updateBoard")
  public String updateBoard(
    @RequestParam Integer board_number,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", boardService.detailBoard(board_number));

    return MessageFormat.format("/pages/{0}/{1}Update", page, page);
  }

  // 4-1. updateBoard (POST) -----------------------------------------------------------------------
  @PostMapping("/updateBoard")
  public Integer updateBoard(
    @ModelAttribute Board board,
    @RequestParam MultipartFile[] imgsFile
  ) throws Exception {

    Integer result = 0;

    if (boardService.updateBoard(board, imgsFile) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-2. updateLike (GET) -------------------------------------------------------------------------
  @ResponseBody
  @GetMapping("/updateLike")
  public Integer updateLike (
    @RequestParam Integer board_number,
    HttpSession session
  ) throws Exception {

    boardService.updateLike(board_number, session);

    Integer like = boardService.detailBoard(board_number).getBoard_like();

    return like;
  }

  // 4-3. updateDislike (GET) ----------------------------------------------------------------------
  @ResponseBody
  @GetMapping("/updateDislike")
  public Integer updateDislike(
    @RequestParam Integer board_number,
    HttpSession session
  ) throws Exception {

    boardService.updateDislike(board_number, session);

    Integer dislike = boardService.detailBoard(board_number).getBoard_dislike();

    return dislike;
  }

  // 5. deleteBoard (POST) -------------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/deleteBoard")
  public Integer deleteBoard(
    @RequestParam Integer board_number
  ) throws Exception {

    Integer result = 0;

    if (boardService.deleteBoard(board_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}