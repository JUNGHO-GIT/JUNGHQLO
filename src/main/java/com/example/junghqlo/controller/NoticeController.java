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
import com.example.junghqlo.model.Notice;
import com.example.junghqlo.service.NoticeService;

@Controller
public class NoticeController {

  // 0. constructor injection --------------------------------------------------------------------->
  Logger logger = LoggerFactory.getLogger(this.getClass());
  private NoticeService noticeService;
  NoticeController(NoticeService noticeService) {
  this.noticeService = noticeService;
  }

  // 1. getNoticeList (GET) ----------------------------------------------------------------------->
  @GetMapping("/notice/getNoticeList")
  public String getNoticeList (
    @RequestParam(required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model,
    Notice notice
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
    List<Notice> noticeList = page.getContent();

    model.addAttribute("page", page);
    model.addAttribute("noticeList", noticeList);

    return "/pages/notice/noticeList";
  }

  // 2. getNoticeDetails (GET) -------------------------------------------------------------------->
  @GetMapping("/notice/getNoticeDetails")
  public String getNoticeDetails (
    @RequestParam Integer notice_number,
    Model model,
    Notice notice,
    HttpSession session
  ) throws Exception {

    // 모델
    model.addAttribute("noticeModel", noticeService.getNoticeDetails(notice_number));

    // 조회수
    noticeService.updateNoticeCount(notice_number, session);

    return "/pages/notice/noticeDetails";
  }

  // 3. searchNotice (GET) ------------------------------------------------------------------------>
  @GetMapping("/notice/searchNotice")
  public String searchNotice (
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    @RequestParam String searchType,
    @RequestParam String keyword,
    Model model,
    Notice notice
  ) throws Exception {

    if(searchType == null || keyword == null) {
      return "redirect:/notice/getNoticeList";
    }
    else if(searchType.equals("title")) {
      searchType="notice_title";
    }
    else if(searchType.equals("contents")) {
      searchType="notice_contents";
    }

    PageHandler<Notice> page = noticeService.searchNotice(pageNumber, itemsPer, keyword, searchType, notice);
    List<Notice> noticeList = page.getContent();

    model.addAttribute("page", page);
    model.addAttribute("noticeList", noticeList);

    return "/pages/notice/noticeSearch";
  }

  // 4. addNotice (GET) --------------------------------------------------------------------------->
  @GetMapping("/notice/addNotice")
  public String addNotice() throws Exception {

    return "/pages/notice/noticeAdd";
  }

  // 4. addNotice (POST) -------------------------------------------------------------------------->
  @PostMapping("/notice/addNotice")
  public String addNotice(Notice notice) throws Exception {

    noticeService.addNotice(notice);

    return "redirect:/notice/getNoticeList";
  }

  // 5. updateNotice (GET) ------------------------------------------------------------------------>
  @GetMapping("/notice/updateNotice")
  public String updateNotice (
    @RequestParam Integer notice_number,
    Model model
  ) throws Exception {

    model.addAttribute("noticeModel", noticeService.getNoticeDetails(notice_number));

    return "/pages/notice/noticeUpdate";
  }

  // 5. updateNotice (POST) ----------------------------------------------------------------------->
  @PostMapping("/notice/updateNotice")
  public String updateNotice (
    @ModelAttribute Notice notice,
    @RequestParam("notice_imgsUrl") String existingImage
  ) throws Exception {

    noticeService.updateNotice(notice, existingImage);

    return "redirect:/notice/getNoticeList";
  }

  // 5-1. updateNoticeCount (GET) ----------------------------------------------------------------->

  // 5-2. updateLike (GET) ------------------------------------------------------------------------>
  @ResponseBody
  @GetMapping("/notice/updateLike")
  public Integer updateLike (
    @RequestParam Integer notice_number,
    HttpSession session
  ) throws Exception {

    noticeService.updateLike(notice_number, session);
    Notice notice = noticeService.getNoticeDetails(notice_number);

    return notice.getNotice_like();
  }

  // 5-3. updateDislike (GET) --------------------------------------------------------------------->
  @GetMapping("/notice/updateDislike")
  @ResponseBody
  public Integer updateDislike(
    @RequestParam Integer notice_number,
    HttpSession session
  ) throws Exception {

    noticeService.updateDislike(notice_number, session);
    Notice notice = noticeService.getNoticeDetails(notice_number);

    return notice.getNotice_dislike();
  }

  // 6. deleteNotice (GET) ------------------------------------------------------------------------>
  @GetMapping("/notice/deleteNotice")
  public String deleteNotice(
    @RequestParam Integer notice_number,
    Model model,
    Notice notice
  ) throws Exception {

    model.addAttribute("noticeModel", noticeService.getNoticeDetails(notice_number));

    return "/pages/notice/noticeDelete";
  }

  // 6. deleteNotice (POST) ----------------------------------------------------------------------->
  @PostMapping("/notice/deleteNotice")
  public String deleteNotice(Notice notice, Integer notice_number) throws Exception {

    noticeService.deleteNotice(notice_number);

    return "redirect:/notice/getNoticeList";
  }

}