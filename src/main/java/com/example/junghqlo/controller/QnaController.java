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
import com.example.junghqlo.model.Qna;
import com.example.junghqlo.service.QnaService;

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

  // 1-1. listQna (GET) ----------------------------------------------------------------------------
  @GetMapping("/listQna")
  public String listQna(
    @ModelAttribute Qna qna,
    @RequestParam(defaultValue = "default") String sort,
    @RequestParam(defaultValue = "1") Integer pageNumber,
    @RequestParam(defaultValue = "9") Integer itemsPer,
    Model model
  ) throws Exception {

    // sort order
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

    PageHandler<Qna> pageHandler = (
      qnaService.listQna(pageNumber, itemsPer, sort, qna)
    );

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/pages/{0}/{1}List", page, page);
  };

  // 1-2. searchQna (GET) ----------------------------------------------------------------------------
  @GetMapping("/searchQna")
  public String searchQna (
    @ModelAttribute Qna qna,
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
    else if(searchType.equals("title")) {
      searchType="qna_title";
    }
    else if(searchType.equals("contents")) {
      searchType="qna_contents";
    }

    PageHandler<Qna> pageHandler = (
      qnaService.searchQna(pageNumber, itemsPer, searchType, keyword, qna)
    );

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/pages/{0}/{1}List", page, page);
  }

  // 2. detailQna(GET) -----------------------------------------------------------------------------
  @GetMapping("/detailQna")
  public String detailQna(
    @ModelAttribute Qna qna,
    @RequestParam Integer qna_number,
    HttpSession session,
    Model model
  ) throws Exception {

    // 조회수 증가
    qnaService.updateQnaCount(qna_number, session);

    // 모델
    model.addAttribute("MODEL", qnaService.detailQna(qna_number));
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return MessageFormat.format("/pages/{0}/{1}Detail", page, page);
  }

  // 3. addQna (GET) -------------------------------------------------------------------------------
  @GetMapping("/addQna")
  public String addQna() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Add", page, page);
  }

  // 3. addQna (POST) ------------------------------------------------------------------------------
  @PostMapping("/addQna")
  public String addQna(
    @ModelAttribute Qna qna
  ) throws Exception {

    qnaService.addQna(qna);

    return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
  }

  // 4. updateQna (GET) ----------------------------------------------------------------------------
  @GetMapping("/updateQna")
  public String updateQna (
    @RequestParam Integer qna_number,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", qnaService.detailQna(qna_number));

    return MessageFormat.format("/pages/{0}/{1}Update", page, page);
  }

  // 4. updateQna (POST) ---------------------------------------------------------------------------
  @PostMapping("/updateQna")
  public String updateQna (
    @ModelAttribute Qna qna,
    @RequestParam("qna_imgsUrl") String existingImage
  ) throws Exception {

    qnaService.updateQna(qna, existingImage);

    return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
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