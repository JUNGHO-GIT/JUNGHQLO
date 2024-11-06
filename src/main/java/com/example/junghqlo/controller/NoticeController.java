package com.example.junghqlo.controller;

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
import com.example.junghqlo.model.Notice;
import com.example.junghqlo.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {

  // 0. constructor injection --------------------------------------------------------------------->
  private NoticeService noticeService;
  NoticeController(NoticeService noticeService) {
    this.noticeService = noticeService;
  }

  // 0. static -------------------------------------------------------------------------------------
  private static String RETURN = "/pages/notice/";
  private static String PAGE = "notice";
  private static String PAGE_UP = "Notice";
  private static Notice MODEL = new Notice();
  private static List<Notice> LIST = new ArrayList<>();

  // 1. getNoticeList (GET) ----------------------------------------------------------------------->
  @GetMapping("/getNoticeList")
  public String getNoticeList (
    @ModelAttribute Notice notice,
    @RequestParam(required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model
  ) throws Exception {

    // sorting order
    if(sort == null || sort.equals("default")) {
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

    PageHandler<Notice> page = noticeService.getNoticeList(pageNumber, itemsPer, sort, notice);
    LIST = page.getContent();

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
    model.addAttribute("LIST", LIST);
    model.addAttribute("PAGE", PAGE);
    model.addAttribute("PAGE_UP", PAGE_UP);

    return RETURN + PAGE + "List";
  };

  // 2. getNoticeDetails (GET) -------------------------------------------------------------------->
  @GetMapping("/getNoticeDetails")
  public String getNoticeDetails (
    @ModelAttribute Notice notice,
    @RequestParam Integer notice_number,
    HttpSession session,
    Model model
  ) throws Exception {

    // 조회수 증가
    noticeService.updateNoticeCount(notice_number, session);
    MODEL = noticeService.getNoticeDetails(notice_number);

    // 모델
    model.addAttribute("MODEL", MODEL);
    model.addAttribute("PAGE", PAGE);
    model.addAttribute("PAGE_UP", PAGE_UP);
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return RETURN + PAGE + "Details";
  }

  // 3. searchNotice (GET) ------------------------------------------------------------------------>
  @GetMapping("/searchNotice")
  public String searchNotice (
    @ModelAttribute Notice notice,
    @RequestParam(required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    @RequestParam String searchType,
    @RequestParam String keyword,
    Model model
  ) throws Exception {

    // searchType order
    if (searchType == null || keyword == null) {
      return "redirect:/" + PAGE + "/get" + PAGE_UP + "List";
    }
    else if(searchType.equals("title")) {
      searchType="notice_title";
    }
    else if(searchType.equals("contents")) {
      searchType="notice_contents";
    }

    PageHandler<Notice> page
    = noticeService.searchNotice(pageNumber, itemsPer, keyword, searchType, notice);

    LIST = page.getContent();

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
    model.addAttribute("LIST", LIST);
    model.addAttribute("PAGE", PAGE);
    model.addAttribute("PAGE_UP", PAGE_UP);

    return RETURN + PAGE + "Search";
  }

  // 4. addNotice (GET) --------------------------------------------------------------------------->
  @GetMapping("/addNotice")
  public String addNotice(
    Model model
  ) throws Exception {

    model.addAttribute("PAGE", PAGE);
    model.addAttribute("PAGE_UP", PAGE_UP);

    return RETURN + PAGE + "Add";
  }

  // 4. addNotice (POST) -------------------------------------------------------------------------->
  @PostMapping("/addNotice")
  public String addNotice(
    @ModelAttribute Notice notice
  ) throws Exception {

    noticeService.addNotice(notice);

    return "redirect:/" + PAGE + "/get" + PAGE_UP + "List";
  }

  // 5. updateNotice (GET) ------------------------------------------------------------------------>
  @GetMapping("/updateNotice")
  public String updateNotice (
    @RequestParam Integer notice_number,
    Model model
  ) throws Exception {

    MODEL = noticeService.getNoticeDetails(notice_number);

    // 모델
    model.addAttribute("MODEL", MODEL);
    model.addAttribute("PAGE", PAGE);
    model.addAttribute("PAGE_UP", PAGE_UP);

    return RETURN + PAGE + "Update";
  }

  // 5. updateNotice (POST) ----------------------------------------------------------------------->
  @PostMapping("/updateNotice")
  public String updateNotice (
    @ModelAttribute Notice notice,
    @RequestParam("notice_imgsUrl") String existingImage
  ) throws Exception {

    noticeService.updateNotice(notice, existingImage);

    return "redirect:/" + PAGE + "/get" + PAGE_UP + "List";
  }

  // 5-2. updateLike (GET) ------------------------------------------------------------------------>
  @ResponseBody
  @GetMapping("/updateLike")
  public Integer updateLike (
    @RequestParam Integer notice_number,
    HttpSession session
  ) throws Exception {

    noticeService.updateLike(notice_number, session);

    Integer like = noticeService.getNoticeDetails(notice_number).getNotice_like();

    return like;
  }

  // 5-3. updateDislike (GET) --------------------------------------------------------------------->
  @ResponseBody
  @GetMapping("/updateDislike")
  public Integer updateDislike (
    @RequestParam Integer notice_number,
    HttpSession session
  ) throws Exception {

    noticeService.updateDislike(notice_number, session);

    Integer dislike = noticeService.getNoticeDetails(notice_number).getNotice_dislike();

    return dislike;
  }

  // 6. deleteNotice (GET) ------------------------------------------------------------------------>
  @GetMapping("/deleteNotice")
  public String deleteNotice(
    @RequestParam Integer notice_number,
    @ModelAttribute Notice notice,
    Model model
  ) throws Exception {

    MODEL = noticeService.getNoticeDetails(notice_number);

    // 모델
    model.addAttribute("MODEL", MODEL);
    model.addAttribute("PAGE", PAGE);
    model.addAttribute("PAGE_UP", PAGE_UP);

    return RETURN + PAGE + "Delete";
  }

  // 6. deleteNotice (POST) ----------------------------------------------------------------------->
  @PostMapping("/notice/deleteNotice")
  public String deleteNotice(
    @RequestParam Integer notice_number
  ) throws Exception {

    noticeService.deleteNotice(notice_number);

    return "redirect:/" + PAGE + "/get" + PAGE_UP + "List";
  }
}