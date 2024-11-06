package com.example.junghqlo.controller;

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

  // 0. static -------------------------------------------------------------------------------------
  private static final String PAGES = "/pages/board";
  private static final String PAGE = "board";
  private static final String PAGE_UP = "Board";

  // 0. constructor injection --------------------------------------------------------------------->
  private BoardService boardService;
  BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

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
    List<Board> boardList = page.getContent();

    model.addAttribute("PAGE", PAGE);
    model.addAttribute("PAGE_UP", PAGE_UP);
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
    model.addAttribute("boardList", boardList);

    return PAGES + "/" + PAGE + "List";
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
    model.addAttribute("PAGE", PAGE);
    model.addAttribute("PAGE_UP", PAGE_UP);
    model.addAttribute("boardModel", boardService.getBoardDetails(board_number));
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return PAGES + "/" + PAGE + "Details";
  }

  // 3. searchBoard (GET) ------------------------------------------------------------------------>
  @GetMapping("/searchBoard")
  public String searchBoard(
    @ModelAttribute Board board,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    @RequestParam String searchType,
    @RequestParam String keyword,
    Model model
  ) throws Exception {

    if (searchType == null || keyword == null) {
      return "redirect:/" + PAGE + "/get" + PAGE_UP + "List";
    }
    else if (searchType.equals("title")) {
      searchType="board_title";
    }
    else if (searchType.equals("contents")) {
      searchType="board_contents";
    }

    PageHandler<Board> page = boardService.searchBoard(pageNumber, itemsPer, keyword, searchType, board);
    List<Board> boardList = page.getContent();

    model.addAttribute("PAGE", PAGE);
    model.addAttribute("PAGE_UP", PAGE_UP);
    model.addAttribute("page", page);
    model.addAttribute("boardList", boardList);

    return PAGES + "/" + PAGE + "Search";
  }

  // 4. addBoard (GET) ---------------------------------------------------------------------------->
  @GetMapping("/addBoard")
  public String addBoard(
    Model model
  ) throws Exception {

    model.addAttribute("PAGE", PAGE);
    model.addAttribute("PAGE_UP", PAGE_UP);

    return PAGES + "/" + PAGE + "Add";
  }

  // 4. addBoard (POST) --------------------------------------------------------------------------->
  @PostMapping("/addBoard")
  public String addBoard(
    @ModelAttribute Board board
  ) throws Exception {

    boardService.addBoard(board);

    return "redirect:/" + PAGE + "/get" + PAGE_UP + "List";
  }

  // 5. updateBoard (GET) ------------------------------------------------------------------------>
  @GetMapping("/updateBoard")
  public String updateBoard(
    @RequestParam Integer board_number,
    Model model
  ) throws Exception {

    model.addAttribute("PAGE", PAGE);
    model.addAttribute("PAGE_UP", PAGE_UP);
    model.addAttribute("boardModel", boardService.getBoardDetails(board_number));

    return PAGES + "/" + PAGE + "Update";
  }

  // 5. updateBoard (POST) ------------------------------------------------------------------------>
  @PostMapping("/updateBoard")
  public String updateBoard (
    @ModelAttribute Board board,
    @RequestParam("board_imgsUrl") String existingImage
  ) throws Exception {

    boardService.updateBoard(board, existingImage);

    return "redirect:/" + PAGE + "/get" + PAGE_UP + "List";
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

    model.addAttribute("PAGE", PAGE);
    model.addAttribute("PAGE_UP", PAGE_UP);
    model.addAttribute("boardModel", boardService.getBoardDetails(board_number));

    return PAGES + "/" + PAGE + "Delete";
  }

  // 6. deleteBoard (POST) ------------------------------------------------------------------------>
  @PostMapping("/deleteBoard")
  public String deleteBoard(
    @RequestParam Integer board_number
  ) throws Exception {

    boardService.deleteBoard(board_number);

    return "redirect:/" + PAGE + "/get" + PAGE_UP + "List";
  }
}