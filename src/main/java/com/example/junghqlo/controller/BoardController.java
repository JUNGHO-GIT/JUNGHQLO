package com.example.junghqlo.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Board;
import com.example.junghqlo.service.BoardService;

@Controller
public class BoardController {

  // 0. constructor injection --------------------------------------------------------------------->
  Logger logger = LoggerFactory.getLogger(this.getClass());
  private BoardService boardService;
  BoardController(BoardService boardService) {
  this.boardService = boardService;
  }

  // 1. getBoardList (GET) ------------------------------------------------------------------------>
  @GetMapping("/board/getBoardList")
  public String getBoardList (
    @RequestParam(required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model,
    Board board
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

    model.addAttribute("page", page);
    model.addAttribute("boardList", boardList);

    return "/pages/board/boardList";
  }

  // 2. getBoardDetails (GET) --------------------------------------------------------------------->
  @GetMapping("/board/getBoardDetails")
  public String getBoardDetails (
    @RequestParam Integer board_number,
    Model model,
    Board board,
    HttpSession session
  ) throws Exception {

    // 모델
    model.addAttribute("boardModel", boardService.getBoardDetails(board_number));
    model.addAttribute("member_id", session.getAttribute("member_id"));

    // 조회수 증가
    boardService.updateBoardCount(board_number, session);

    return "/pages/board/boardDetails";
  }

  // 3. searchBoard (GET) ------------------------------------------------------------------------>
  @GetMapping("/board/searchBoard")
  public String searchBoard(
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    @RequestParam String searchType,
    @RequestParam String keyword,
    Model model,
    Board board
  ) throws Exception {

    if(searchType == null || keyword == null) {return "redirect:/board/getBoardList";}
    else if(searchType.equals("title"))       {searchType="board_title";}
    else if(searchType.equals("contents"))    {searchType="board_contents";}

    PageHandler<Board> page = boardService.searchBoard(pageNumber, itemsPer, keyword, searchType, board);
    List<Board> boardList = page.getContent();

    model.addAttribute("page", page);
    model.addAttribute("boardList", boardList);

    return "/pages/board/boardSearch";
  }

  // 4. addBoard (GET) ---------------------------------------------------------------------------->
  @GetMapping("/board/addBoard")
  public String addBoard() throws Exception {

    return "/pages/board/boardAdd";
  }

  @PostMapping("/board/addBoard")
  public String addBoard(Board board) throws Exception {

    boardService.addBoard(board);

    return "redirect:/board/getBoardList";
  }

  // 5. updateBoard (GET) ------------------------------------------------------------------------>
  @GetMapping("/board/updateBoard")
  public String updateBoard(
    @RequestParam Integer board_number,
    Model model, Board board
  ) throws Exception {

    model.addAttribute("boardModel", boardService.getBoardDetails(board_number));

    return "/pages/board/boardUpdate";
  }

  // 5. updateBoard (POST) ------------------------------------------------------------------------>
  @PostMapping("/board/updateBoard")
  public String updateBoard (
    @ModelAttribute Board board,
    @RequestParam("board_imgsUrl") String existingImage
  ) throws Exception {

    boardService.updateBoard(board, existingImage);

    return "redirect:/board/getBoardList";
  }

  // 5-1. updateBoardCount ------------------------------------------------------------------------>

  // 5-2. updateLike (GET) ------------------------------------------------------------------------>
  @ResponseBody
  @GetMapping("/board/updateLike")
  public Integer updateLike (
    @RequestParam Integer board_number,
    HttpSession session
  ) throws Exception {

    boardService.updateLike(board_number, session);

    return boardService.getBoardDetails(board_number).getBoard_like();
  }

  // 5-3. updateDislike (GET) --------------------------------------------------------------------->
  @ResponseBody
  @GetMapping("/board/updateDislike")
  public Integer updateDislike(
    @RequestParam Integer board_number,
    HttpSession session
  ) throws Exception {

    boardService.updateDislike(board_number, session);

    return boardService.getBoardDetails(board_number).getBoard_dislike();
  }

  // 6. deleteBoard (GET) ------------------------------------------------------------------------->
  @GetMapping("/board/deleteBoard")
  public String deleteBoard(
    @RequestParam Integer board_number,
    Model model,
    Board board
  ) throws Exception {

    model.addAttribute("boardModel", boardService.getBoardDetails(board_number));

    return "/pages/board/boardDelete";
  }

  // 6. deleteBoard (POST) ------------------------------------------------------------------------>
  @PostMapping("/board/deleteBoard")
  public String deleteBoard(Board board, Integer board_number) throws Exception {

    boardService.deleteBoard(board_number);

    return "redirect:/board/getBoardList";
  }

}