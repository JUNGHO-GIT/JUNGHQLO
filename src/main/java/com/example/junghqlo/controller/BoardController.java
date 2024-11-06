package com.example.junghqlo.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
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

  // 0. constructor injection --------------------------------------------------------------------->
  private BoardService boardService;
  BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  // 0. static -------------------------------------------------------------------------------------
  private static String PAGE = "board";
  private static String PAGE_UP = "Board";
  private static Board MODEL = new Board();
  private static List<Board> LIST = new ArrayList<>();

  // 1. getBoardList (GET) ------------------------------------------------------------------------>
  @GetMapping("/getBoardList")
  public String getBoardList (
    @ModelAttribute Board board,
    @RequestParam(required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model
  ) throws Exception {

    // sorting order
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

    PageHandler<Board> page = boardService.getBoardList(pageNumber, itemsPer, sort, board);
    LIST = page.getContent();

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
    model.addAttribute("LIST", LIST);

    return MessageFormat.format("/pages/{0}/{1}List", PAGE, PAGE);
  };

  // 2. getBoardDetails (GET) --------------------------------------------------------------------->
  @GetMapping("/getBoardDetails")
  public String getBoardDetails (
    @ModelAttribute Board board,
    @RequestParam Integer board_number,
    HttpSession session,
    Model model
  ) throws Exception {

    // 조회수 증가
    boardService.updateBoardCount(board_number, session);

    // 모델
    model.addAttribute("MODEL", MODEL);
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return MessageFormat.format("/pages/{0}/{1}Details", PAGE, PAGE);
  }

  // 3. searchBoard (GET) ------------------------------------------------------------------------>
  @GetMapping("/searchBoard")
  public String searchBoard(
    @ModelAttribute Board board,
    @RequestParam(required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    @RequestParam String searchType,
    @RequestParam String keyword,
    Model model
  ) throws Exception {

    if (searchType == null || keyword == null) {
      return MessageFormat.format("redirect:/{0}/get{1}List", PAGE, PAGE_UP);
    }
    else if (searchType.equals("title")) {
      searchType="board_title";
    }
    else if (searchType.equals("contents")) {
      searchType="board_contents";
    }

    PageHandler<Board> page
      = boardService.searchBoard(pageNumber, itemsPer, keyword, searchType, board);

    LIST = page.getContent();

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
    model.addAttribute("LIST", LIST);

    return MessageFormat.format("/pages/{0}/{1}Search", PAGE, PAGE);
  }

  // 4. addBoard (GET) ---------------------------------------------------------------------------->
  @GetMapping("/addBoard")
  public String addBoard(
  ) throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Add", PAGE, PAGE);
  }

  // 4. addBoard (POST) --------------------------------------------------------------------------->
  @PostMapping("/addBoard")
  public String addBoard(
    @ModelAttribute Board board
  ) throws Exception {

    boardService.addBoard(board);

    return MessageFormat.format("redirect:/{0}/get{1}List", PAGE, PAGE_UP);
  }

  // 5. updateBoard (GET) ------------------------------------------------------------------------>
  @GetMapping("/updateBoard")
  public String updateBoard(
    @RequestParam Integer board_number,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", MODEL);

    return MessageFormat.format("/pages/{0}/{1}Update", PAGE, PAGE);
  }

  // 5. updateBoard (POST) ------------------------------------------------------------------------>
  @PostMapping("/updateBoard")
  public String updateBoard (
    @ModelAttribute Board board,
    @RequestParam("board_imgsUrl") String existingImage
  ) throws Exception {

    boardService.updateBoard(board, existingImage);

    return MessageFormat.format("redirect:/{0}/get{1}List", PAGE, PAGE_UP);
  }

  // 5-2. updateLike (GET) ------------------------------------------------------------------------>
  @ResponseBody
  @GetMapping("/updateLike")
  public Integer updateLike (
    @RequestParam Integer board_number,
    HttpSession session
  ) throws Exception {

    boardService.updateLike(board_number, session);

    Integer like = boardService.getBoardDetails(board_number).getBoard_like();

    return like;
  }

  // 5-3. updateDislike (GET) --------------------------------------------------------------------->
  @ResponseBody
  @GetMapping("/updateDislike")
  public Integer updateDislike(
    @RequestParam Integer board_number,
    HttpSession session
  ) throws Exception {

    boardService.updateDislike(board_number, session);

    Integer dislike = boardService.getBoardDetails(board_number).getBoard_dislike();

    return dislike;
  }

  // 6. deleteBoard (GET) ------------------------------------------------------------------------->
  @GetMapping("/deleteBoard")
  public String deleteBoard(
    @RequestParam Integer board_number,
    @ModelAttribute Board board,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", MODEL);

    return MessageFormat.format("/pages/{0}/{1}Delete", PAGE, PAGE);
  }

  // 6. deleteBoard (POST) ------------------------------------------------------------------------>
  @PostMapping("/deleteBoard")
  public String deleteBoard(
    @RequestParam Integer board_number
  ) throws Exception {

    boardService.deleteBoard(board_number);

    return MessageFormat.format("redirect:/{0}/get{1}List", PAGE, PAGE_UP);
  }
}