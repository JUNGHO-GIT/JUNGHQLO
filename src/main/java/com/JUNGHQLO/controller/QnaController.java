package com.JUNGHQLO.controller;

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
import org.springframework.web.multipart.MultipartFile;
import com.JUNGHQLO.handler.PageHandler;
import com.JUNGHQLO.model.Qna;
import com.JUNGHQLO.service.QnaService;

@Controller
@RequestMapping("/qna")
public class QnaController {

  // 0. constructor injection ----------------------------------------------------------------------
  private QnaService qnaService;
  QnaController(QnaService qnaService) {
    this.qnaService = qnaService;
  }

  // 0. static -------------------------------------------------------------------------------------
  private static String page = "qna";
  private static String PAGE = "Qna";

  // 1. listQna (GET) ------------------------------------------------------------------------------
  @GetMapping("/listQna")
  public String listQna(
    @ModelAttribute Qna qna,
    @RequestParam(defaultValue = "all") String sort,
    @RequestParam(defaultValue = "all") String category,
    @RequestParam(defaultValue = "title") String type,
    @RequestParam(defaultValue = "") String keyword,
    @RequestParam(defaultValue = "1") Integer pageNumber,
    @RequestParam(defaultValue = "9") Integer itemsPer,
    Model model
  ) throws Exception {

    // 1. Sort handling
    String sortHandler = "";
    if (sort == null || sort.equals("all")) {
      sort = "qna_date DESC";
      sortHandler = "all";
    }
    else if (sort.equals("nameASC")) {
      sort = "qna_title ASC";
      sortHandler = "nameASC";
    }
    else if (sort.equals("nameDESC")) {
      sort = "qna_title DESC";
      sortHandler = "nameDESC";
    }
    else if (sort.equals("dateASC")) {
      sort = "qna_date ASC";
      sortHandler = "dateASC";
    }
    else if (sort.equals("dateDESC")) {
      sort = "qna_date DESC";
      sortHandler = "dateDESC";
    }

    // 2. Category handling
    String categoryHandler = "";
    if (category == null || category.equals("all")) {
      category = "qna_category IS NOT NULL";
      categoryHandler = "all";
    }
    else if (category.equals("goods")) {
      category = "qna_category = 'goods'";
      categoryHandler = "goods";
    }
    else if (category.equals("exchange")) {
      category = "qna_category = 'exchange'";
      categoryHandler = "exchange";
    }
    else if (category.equals("refund")) {
      category = "qna_category = 'refund'";
      categoryHandler = "refund";
    }
    else if (category.equals("payment")) {
      category = "qna_category = 'payment'";
      categoryHandler = "payment";
    }
    else if (category.equals("delivery")) {
      category = "qna_category = 'delivery'";
      categoryHandler = "delivery";
    }
    else if (category.equals("etc")) {
      category = "qna_category = 'etc'";
      categoryHandler = "etc";
    }

    // 3. Type handling
    String typeHandler = "";
    if (type == null || keyword == null) {
      return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
    }
    else if (type.equals("all")) {
      type = "qna_title OR qna_contents";
      typeHandler = "all";
    }
    else if (type.equals("title")) {
      type = "qna_title";
      typeHandler = "title";
    }
    else if (type.equals("contents")) {
      type = "qna_contents";
      typeHandler = "contents";
    }

    // 4. Keyword handling
    String keywordHandler = "";
    if (keyword != null) {
      keywordHandler = keyword;
    }

    // 5. Page handling
    PageHandler<Qna> pageHandler = (
      qnaService.listQna(pageNumber, itemsPer, sort, category, type, keyword, qna)
    );

    // 모델
    model.addAttribute("sortHandler", sortHandler);
    model.addAttribute("categoryHandler", categoryHandler);
    model.addAttribute("typeHandler", typeHandler);
    model.addAttribute("keywordHandler", keywordHandler);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/page/{0}/{1}List", page, page);
  };

  // 2. detailQna(GET) -----------------------------------------------------------------------------
  @GetMapping("/detailQna")
  public String detailQna(
    @RequestParam Integer qna_number,
    HttpSession session,
    Model model
  ) throws Exception {

    // 조회수 증가
    qnaService.updateCount(qna_number, session);

    // 모델
    model.addAttribute("MODEL", qnaService.detailQna(qna_number));
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return MessageFormat.format("/page/{0}/{1}Detail", page, page);
  }

  // 3. saveQna (GET) ------------------------------------------------------------------------------
  @GetMapping("/saveQna")
  public String saveQna() throws Exception {

    return MessageFormat.format("/page/{0}/{1}Save", page, page);
  }

  // 3. saveQna (POST) -----------------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/saveQna")
  public Integer saveQna (
    @ModelAttribute Qna qna,
    @RequestParam(required = false) List<MultipartFile> imgsFile
  ) throws Exception {

    // imgsFile이 null이면 빈 리스트로 초기화
    if (imgsFile == null || imgsFile.size() == 0) {
      imgsFile = new ArrayList<MultipartFile>();
    }

    Integer result = 0;

    if (qnaService.saveQna(qna, imgsFile) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-1. updateQna (GET) --------------------------------------------------------------------------
  @GetMapping("/updateQna")
  public String updateQna (
    @RequestParam Integer qna_number,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", qnaService.detailQna(qna_number));

    return MessageFormat.format("/page/{0}/{1}Update", page, page);
  }

  // 4-1. updateQna (POST) -------------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/updateQna")
  public Integer updateQna (
    @ModelAttribute Qna qna,
    @RequestParam(required = false) List<MultipartFile> imgsFile
  ) throws Exception {

    // imgsFile이 null이면 빈 리스트로 초기화
    if (imgsFile == null || imgsFile.size() == 0) {
      imgsFile = new ArrayList<MultipartFile>();
    }

    Integer result = 0;

    if (qnaService.updateQna(qna, imgsFile) > 0) {
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
    @RequestParam Integer qna_number,
    HttpSession session
  ) throws Exception {

    qnaService.updateLike(qna_number, session);

    Integer like = qnaService.detailQna(qna_number).getQna_like();

    return like;
  }

  // 4-3. updateDislike (GET) ----------------------------------------------------------------------
  @ResponseBody
  @GetMapping("/updateDislike")
  public Integer updateDislike (
    @RequestParam Integer qna_number,
    HttpSession session
  ) throws Exception {

    qnaService.updateDislike(qna_number, session);

    Integer dislike = qnaService.detailQna(qna_number).getQna_dislike();

    return dislike;
  }

  // 5. deleteProduct (POST) -----------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/deleteProduct")
  public Integer deleteProduct(
    @RequestParam Integer qna_number
  ) throws Exception {

    Integer result = 0;

    if (qnaService.deleteQna(qna_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}