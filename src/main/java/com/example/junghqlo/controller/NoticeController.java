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
import com.example.junghqlo.model.Notice;
import com.example.junghqlo.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {

  // 0. constructor injection ----------------------------------------------------------------------
  private NoticeService noticeService;
  NoticeController(NoticeService noticeService) {
    this.noticeService = noticeService;
  }

  // 0. static -------------------------------------------------------------------------------------
  private static String page = "notice";
  private static String PAGE = "Notice";

  // 1-1. listNotice (GET) -------------------------------------------------------------------------
  @GetMapping("/listNotice")
  public String listNotice(
    @ModelAttribute Notice notice,
    @RequestParam(defaultValue = "default") String sort,
    @RequestParam(defaultValue = "1") Integer pageNumber,
    @RequestParam(defaultValue = "9") Integer itemsPer,
    Model model
  ) throws Exception {

    // sort order
    if (sort == null || sort.equals("default")) {
      sort="notice_number DESC";
    }
    else if(sort.equals("titleASC")) {
      sort="notice_title ASC";
    }
    else if(sort.equals("titleDESC")) {
      sort="notice_title DESC";
    }
    else if(sort.equals("countASC")) {
      sort="notice_count ASC";
    }
    else if(sort.equals("countDESC")) {
      sort="notice_count DESC";
    }
    else if(sort.equals("dateASC")) {
      sort="notice_date ASC";
    }
    else if(sort.equals("dateDESC")) {
      sort="notice_date DESC";
    }

    PageHandler<Notice> pageHandler = (
      noticeService.listNotice(pageNumber, itemsPer, sort, notice)
    );

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/pages/{0}/{1}List", page, page);
  };

  // 1-2. searchNotice (GET) -----------------------------------------------------------------------
  @GetMapping("/searchNotice")
  public String searchNotice (
    @ModelAttribute Notice notice,
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
      searchType="notice_title";
    }
    else if(searchType.equals("contents")) {
      searchType="notice_contents";
    }

    PageHandler<Notice> pageHandler = (
      noticeService.searchNotice(pageNumber, itemsPer, searchType, keyword, notice)
    );

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("searchType", searchType);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/pages/{0}/{1}List", page, page);
  }

  // 2. detailNotice(GET) --------------------------------------------------------------------------
  @GetMapping("/detailNotice")
  public String detailNotice(
    @ModelAttribute Notice notice,
    @RequestParam Integer notice_number,
    HttpSession session,
    Model model
  ) throws Exception {

    // 조회수 증가
    noticeService.updateNoticeCount(notice_number, session);

    // 모델
    model.addAttribute("MODEL", noticeService.detailNotice(notice_number));
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return MessageFormat.format("/pages/{0}/{1}Detail", page, page);
  }

  // 3. addNotice (GET) ----------------------------------------------------------------------------
  @GetMapping("/addNotice")
  public String addNotice() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Add", page, page);
  }

  // 3. addNotice (POST) ---------------------------------------------------------------------------
  @PostMapping("/addNotice")
  public String addNotice(
    @ModelAttribute Notice notice
  ) throws Exception {

    noticeService.addNotice(notice);

    return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
  }

  // 4. updateNotice (GET) -------------------------------------------------------------------------
  @GetMapping("/updateNotice")
  public String updateNotice (
    @RequestParam Integer notice_number,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", noticeService.detailNotice(notice_number));

    return MessageFormat.format("/pages/{0}/{1}Update", page, page);
  }

  // 4. updateNotice (POST) ------------------------------------------------------------------------
  @PostMapping("/updateNotice")
  public String updateNotice (
    @ModelAttribute Notice notice,
    @RequestParam("notice_imgsUrl") String existingImage
  ) throws Exception {

    noticeService.updateNotice(notice, existingImage);

    return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
  }

  // 4-2. updateLike (GET) -------------------------------------------------------------------------
  @ResponseBody
  @GetMapping("/updateLike")
  public Integer updateLike (
    @RequestParam Integer notice_number,
    HttpSession session
  ) throws Exception {

    noticeService.updateLike(notice_number, session);

    Integer like = noticeService.detailNotice(notice_number).getNotice_like();

    return like;
  }

  // 4-3. updateDislike (GET) ----------------------------------------------------------------------
  @ResponseBody
  @GetMapping("/updateDislike")
  public Integer updateDislike (
    @RequestParam Integer notice_number,
    HttpSession session
  ) throws Exception {

    noticeService.updateDislike(notice_number, session);

    Integer dislike = noticeService.detailNotice(notice_number).getNotice_dislike();

    return dislike;
  }

  // 5. deleteNotice (POST) ------------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/deleteNotice")
  public Integer deleteNotice (
    @RequestParam Integer notice_number
  ) throws Exception {

    Integer result = 0;

    if (noticeService.deleteNotice(notice_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}