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
import com.example.junghqlo.model.Qna;
import com.example.junghqlo.service.QnaService;

@Controller
@RequestMapping("/qna")
public class QnaController {

  // 0. constructor injection -------------------------------------------------------------------->
  private QnaService qnaService;
  QnaController(QnaService qnaService) {
    this.qnaService = qnaService;
  }

  // 0. static -------------------------------------------------------------------------------------
  private static String PAGE = "qna";
  private static String PAGE_UP = "Qna";
  private static Qna MODEL = new Qna();
  private static List<Qna> LIST = new ArrayList<>();

  // 1. getQnaList (GET) -------------------------------------------------------------------------->
  @GetMapping("/getQnaList")
  public String getQnaList (
    @ModelAttribute Qna qna,
    @RequestParam(required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model
  ) throws Exception {

    // sorting order
    if(sort == null || sort.equals("default")) {
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
    LIST = page.getContent();

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
    model.addAttribute("LIST", LIST);

    return MessageFormat.format("/pages/{0}/{1}List", PAGE, PAGE);
  };

  // 2. getQnaDetails (GET) ----------------------------------------------------------------------->
  @GetMapping("/getQnaDetails")
  public String getQnaDetails(
    @ModelAttribute Qna qna,
    @RequestParam Integer qna_number,
    HttpSession session,
    Model model
  ) throws Exception {

    // 조회수 증가
    qnaService.updateQnaCount(qna_number, session);
    MODEL = qnaService.getQnaDetails(qna_number);

    // 모델
    model.addAttribute("MODEL", MODEL);
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return MessageFormat.format("/pages/{0}/{1}Details", PAGE, PAGE);
  }

  // 3. searchQna (GET) --------------------------------------------------------------------------->
  @GetMapping("/searchQna")
  public String searchQna (
    @ModelAttribute Qna qna,
    @RequestParam(required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    @RequestParam String searchType,
    @RequestParam String keyword,
    Model model
  ) throws Exception {

    // searchType order
    if (searchType == null || keyword == null) {
      return MessageFormat.format("redirect:/{0}/get{1}List", PAGE, PAGE_UP);
    }
    else if(searchType.equals("title")) {
      searchType="qna_title";
    }
    else if(searchType.equals("contents")) {
      searchType="qna_contents";
    }

    PageHandler<Qna> page
    = qnaService.searchQna(pageNumber, itemsPer, keyword, searchType, qna);
    LIST = page.getContent();

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
    model.addAttribute("LIST", LIST);

    return MessageFormat.format("/pages/{0}/{1}Search", PAGE, PAGE);
  }

  // 4. addQna (GET) ------------------------------------------------------------------------------>
  @GetMapping("/addQna")
  public String addQna() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Add", PAGE, PAGE);
  }

  // 4. addQna (POST) ----------------------------------------------------------------------------->
  @PostMapping("/addQna")
  public String addQna(
    @ModelAttribute Qna qna
  ) throws Exception {

    qnaService.addQna(qna);

    return MessageFormat.format("redirect:/{0}/get{1}List", PAGE, PAGE_UP);
  }

  // 4. updateQna (GET) --------------------------------------------------------------------------->
  @GetMapping("/updateQna")
  public String updateQna (
    @RequestParam Integer qna_number,
    Model model
  ) throws Exception {

    MODEL = qnaService.getQnaDetails(qna_number);

    // 모델
    model.addAttribute("MODEL", MODEL);

    return MessageFormat.format("/pages/{0}/{1}Update", PAGE, PAGE);
  }

  // 4. updateQna (POST) -------------------------------------------------------------------------->
  @PostMapping("/updateQna")
  public String updateQna (
    @ModelAttribute Qna qna,
    @RequestParam("qna_imgsUrl") String existingImage
  ) throws Exception {

    qnaService.updateQna(qna, existingImage);

    return MessageFormat.format("redirect:/{0}/get{1}List", PAGE, PAGE_UP);
  }

  // 5-2. updateLike (GET) ------------------------------------------------------------------------>
  @ResponseBody
  @GetMapping("/updateLike")
  public Integer updateLike (
    @RequestParam Integer qna_number,
    HttpSession session
  ) throws Exception {

    qnaService.updateLike(qna_number, session);

    Integer like = qnaService.getQnaDetails(qna_number).getQna_like();

    return like;
  }

  // 5-3. updateDislike (GET) --------------------------------------------------------------------->
  @ResponseBody
  @GetMapping("/updateDislike")
  public Integer updateDislike (
    @RequestParam Integer qna_number,
    HttpSession session
  ) throws Exception {

    qnaService.updateDislike(qna_number, session);

    Integer dislike = qnaService.getQnaDetails(qna_number).getQna_dislike();

    return dislike;
  }

  // 5. deleteQna (GET) --------------------------------------------------------------------------->
  @GetMapping("/deleteQna")
  public String deleteQna (
    @RequestParam Integer qna_number,
    @ModelAttribute Qna qna,
    Model model
  ) throws Exception {

    MODEL = qnaService.getQnaDetails(qna_number);

    // 모델
    model.addAttribute("MODEL", MODEL);

    return MessageFormat.format("/pages/{0}/{1}Delete", PAGE, PAGE);
  }

  // 5. deleteQna (POST) -------------------------------------------------------------------------->
  @PostMapping("/deleteQna")
  public String deleteQna (
    @RequestParam Integer qna_number
  ) throws Exception {

    qnaService.deleteQna(qna_number);

    return MessageFormat.format("redirect:/{0}/get{1}List", PAGE, PAGE_UP);
  }
}