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
import com.example.junghqlo.model.Qna;
import com.example.junghqlo.service.QnaService;

@Controller
public class QnaController {

  // 0. constructor injection -------------------------------------------------------------------->
  Logger logger = LoggerFactory.getLogger(this.getClass());
  private QnaService qnaService;
  QnaController(QnaService qnaService) {
  this.qnaService = qnaService;
  }

  // 1. getQnaList (GET) -------------------------------------------------------------------------->
  @GetMapping("/qna/getQnaList")
  public String getQnaList (
    @RequestParam(value="sort", required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model,
    Qna qna
  ) throws Exception {

    logger.info("getQnaList GET 호출 !!!!!");

    // sorting order
    if(sort == null) {
      sort="qna_category IS NOT NULL ORDER BY qna_number DESC";
    }
    else if(sort.equals("goods")) {
      sort="qna_category='상품문의'";
    }
    else if(sort.equals("exchange")) {
      sort="qna_category='교환/반품'";
    }
    else if(sort.equals("refund")) {
      sort="qna_category='환불문의'";
    }
    else if(sort.equals("payment")) {
      sort="qna_category='결제문의'";
    }
    else if(sort.equals("delivery")) {
      sort="qna_category='배송문의'";
    }
    else if(sort.equals("etc")) {
      sort="qna_category='기타문의'";
    }

    PageHandler<Qna> page = qnaService.getQnaList(pageNumber, itemsPer, sort, qna);
    List<Qna> qnaList = page.getContent();

    model.addAttribute("page", page);
    model.addAttribute("qnaList", qnaList);

    return "/qna/qnaList";
  }

  // 2. getQnaDetails (GET) ----------------------------------------------------------------------->
  @GetMapping("/qna/getQnaDetails")
  public String getQnaDetails(
    @RequestParam("qna_number") Integer qna_number,
    Model model,
    Qna qna,
    HttpSession session
  ) throws Exception {

    logger.info("getQnaDetails GET 호출 !!!!!");

    // 모델
    model.addAttribute("qnaModel", qnaService.getQnaDetails(qna_number));

    // 조회수
    qnaService.updateQnaCount(qna_number, session);

    return "/qna/qnaDetails";
  }

  // 3. searchQna (GET) --------------------------------------------------------------------------->
  @GetMapping("/qna/searchQna")
  public String searchQna (
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    @RequestParam("searchType") String searchType,
    @RequestParam("keyword") String keyword,
    Model model,
    Qna qna
  ) throws Exception {

    logger.info("searchQna GET 호출 !!!!!");

    // searchType order
    if(searchType == null || keyword == null) {
      return "redirect:/qna/getQnaList";
    }
    else if(searchType.equals("title")) {
      searchType="qna_title";
    }
    else if(searchType.equals("contents")) {
      searchType="qna_contents";
    }

    PageHandler<Qna> page = qnaService.searchQna(pageNumber, itemsPer, keyword, searchType, qna);
    List<Qna> qnaList = page.getContent();

    model.addAttribute("page", page);
    model.addAttribute("qnaList", qnaList);

    return "/qna/qnaSearch";
  }

  // 4. addQna (GET) ------------------------------------------------------------------------------>
  @GetMapping("/qna/addQna")
  public String addQna() throws Exception {

    logger.info("addQna GET 호출 !!!!!");

    return "/qna/qnaAdd";
  }

  // 4. addQna (POST) ----------------------------------------------------------------------------->
  @PostMapping("/qna/addQna")
  public String addQna(Qna qna) throws Exception {

    logger.info("addQna POST 호출 !!!!!");

    qnaService.addQna(qna);

    return "redirect:/qna/getQnaList";
  }

  // 4. updateQna (GET) --------------------------------------------------------------------------->
  @GetMapping("/qna/updateQna")
  public String updateQna (
    @RequestParam("qna_number") Integer qna_number,
    Model model
  ) throws Exception {

    logger.info("updateQna GET 호출 !!!!!");

    model.addAttribute("qnaModel", qnaService.getQnaDetails(qna_number));

    return "/qna/qnaUpdate";
  }

  // 4. updateQna (POST) -------------------------------------------------------------------------->
  @PostMapping("/qna/updateQna")
  public String updateQna (
    @ModelAttribute Qna qna,
    @RequestParam("qna_imgsUrl") String existingImage
  ) throws Exception {

    logger.info("updateQna POST 호출 !!!!!");

    qnaService.updateQna(qna, existingImage);

    return "redirect:/board/getBoardList";
  }

  // 5-1. updateQnaCount -------------------------------------------------------------------------->

  // 5-2. updateLike (GET) ------------------------------------------------------------------------>
  @ResponseBody
  @GetMapping("/qna/updateLike")
  public Integer updateLike (
    @RequestParam("qna_number") Integer qna_number,
    HttpSession session
  ) throws Exception {

    logger.info("updateLike GET 호출 !!!!!");

    qnaService.updateLike(qna_number, session);

    return qnaService.getQnaDetails(qna_number).getQna_like();
  }

  // 5-3. updateDislike (GET) --------------------------------------------------------------------->
  @GetMapping("/qna/updateDislike")
  @ResponseBody
  public Integer updateDislike (
    @RequestParam("qna_number") Integer qna_number,
    HttpSession session
  ) throws Exception {

    logger.info("updateDislike GET 호출 !!!!!");

    qnaService.updateDislike(qna_number, session);

    return qnaService.getQnaDetails(qna_number).getQna_dislike();
  }

  // 5. deleteQna (GET) --------------------------------------------------------------------------->
  @GetMapping("/qna/deleteQna")
  public String deleteQna (
    @RequestParam("qna_number") Integer qna_number,
    Model model,
    Qna qna
  ) throws Exception {

    logger.info("deleteQna GET 호출 !!!!!");

    model.addAttribute("qnaModel", qnaService.getQnaDetails(qna_number));

    return "/qna/qnaDelete";
  }

  // 5. deleteQna (POST) -------------------------------------------------------------------------->
  @PostMapping("/qna/deleteQna")
  public String deleteQna (Qna qna, Model model, Integer qna_number) throws Exception {

    logger.info("deleteQna POST 호출 !!!!!");

    model.addAttribute("qnaModel", qna);
    qnaService.deleteQna(qna_number);

    return "redirect:/qna/getQnaList";
  }

}