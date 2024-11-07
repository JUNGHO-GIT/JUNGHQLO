package com.example.junghqlo.controller;

import java.text.MessageFormat;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Board;
import com.example.junghqlo.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

  // 0. constructor injection ----------------------------------------------------------------------
  private BoardService boardService;
  BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  // 0. static -------------------------------------------------------------------------------------
  private static String page = "board";
  private static String PAGE = "Board";

  // 1-1. listBoard (GET) --------------------------------------------------------------------------
  @GetMapping("/listBoard")
  public String listBoard(
    @ModelAttribute Board board,
    @RequestParam(defaultValue = "default") String sort,
    @RequestParam(defaultValue = "1") Integer pageNumber,
    @RequestParam(defaultValue = "9") Integer itemsPer,
    Model model
  ) throws Exception {

    // sort order
    if(sort == null || sort.equals("default")) {
      sort="board_number DESC";
    }
    else if(sort.equals("titleASC")) {
      sort="board_title ASC";
    }
    else if(sort.equals("titleDESC")) {
      sort="board_title DESC";
    }
    else if(sort.equals("countASC")) {
      sort="board_count ASC";
    }
    else if(sort.equals("countDESC")) {
      sort="board_count DESC";
    }
    else if(sort.equals("dateASC")) {
      sort="board_date ASC";
    }
    else if(sort.equals("dateDESC")) {
      sort="board_date DESC";
    }

    PageHandler<Board> pageHandler = (
      boardService.listBoard(pageNumber, itemsPer, sort, board)
    );

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/pages/{0}/{1}List", page, page);
  };

  // 1-2. searchBoard (GET) ------------------------------------------------------------------------
  @GetMapping("/searchBoard")
  public String searchBoard(
    @ModelAttribute Board board,
    @RequestParam(defaultValue = "default") String sort,
    @RequestParam(defaultValue = "1") Integer pageNumber,
    @RequestParam(defaultValue = "9") Integer itemsPer,
    @RequestParam String searchType,
    @RequestParam String keyword,
    Model model
  ) throws Exception {

    // searchType order
    if (searchType == null || keyword == null) {
      return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
    }
    else if (searchType.equals("title")) {
      searchType="board_title";
    }
    else if (searchType.equals("contents")) {
      searchType="board_contents";
    }

    PageHandler<Board> pageHandler = (
      boardService.searchBoard(pageNumber, itemsPer, searchType, keyword, board)
    );

    // 모델
    model.addAttribute("sort", sort);
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
    boardService.updateBoardCount(board_number, session);

    // 모델
    model.addAttribute("MODEL", boardService.detailBoard(board_number));
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return MessageFormat.format("/pages/{0}/{1}Detail", page, page);
  }

  // 3. addBoard (GET) -----------------------------------------------------------------------------
  @GetMapping("/addBoard")
  public String addBoard() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Add", page, page);
  }

  // 3. addBoard (POST) ----------------------------------------------------------------------------
  @PostMapping("/addBoard")
  public String addBoard(
    @ModelAttribute Board board
  ) throws Exception {

    boardService.addBoard(board);

    return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
  }

  // 4. updateBoard (GET) --------------------------------------------------------------------------
  @GetMapping("/updateBoard")
  public String updateBoard(
    @RequestParam Integer board_number,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", boardService.detailBoard(board_number));

    return MessageFormat.format("/pages/{0}/{1}Update", page, page);
  }

  // 4. updateBoard (POST) -------------------------------------------------------------------------
  @PostMapping("/updateBoard")
  public String updateBoard (
    @ModelAttribute Board board,
    @RequestParam("board_imgsUrl") String existingImage
  ) throws Exception {

    boardService.updateBoard(board, existingImage);

    return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
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