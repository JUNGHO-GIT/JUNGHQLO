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
import com.example.junghqlo.domain.Notice;
import com.example.junghqlo.handler.PageHandler;
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
    @RequestParam(value="sort", required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model,
    Notice notice
  ) throws Exception {

    logger.info("getNoticeList GET 호출 !!!!!");

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

    return "/notice/noticeList";
  }

  // 2. getNoticeDetails (GET) -------------------------------------------------------------------->
  @GetMapping("/notice/getNoticeDetails")
  public String getNoticeDetails (
    @RequestParam("notice_number") Integer notice_number,
    Model model,
    Notice notice,
    HttpSession session
  ) throws Exception {

    logger.info("getNoticeDetails GET 호출 !!!!!");

    // 모델
    model.addAttribute("noticeModel", noticeService.getNoticeDetails(notice_number));

    // 조회수
    noticeService.updateNoticeCount(notice_number, session);

    return "/notice/noticeDetails";
  }

  // 3. searchNotice (GET) ------------------------------------------------------------------------>
  @GetMapping("/notice/searchNotice")
  public String searchNotice (
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    @RequestParam("searchType") String searchType,
    @RequestParam("keyword") String keyword,
    Model model,
    Notice notice
  ) throws Exception {

    logger.info("searchNotice GET 호출 !!!!!");

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

    return "/notice/noticeSearch";
  }

  // 4. addNotice (GET) --------------------------------------------------------------------------->
  @GetMapping("/notice/addNotice")
  public String addNotice() throws Exception {

    logger.info("addNotice GET 호출 !!!!!");

    return "/notice/noticeAdd";
  }

  // 4. addNotice (POST) -------------------------------------------------------------------------->
  @PostMapping("/notice/addNotice")
  public String addNotice(Notice notice) throws Exception {

    logger.info("addNotice POST 호출 !!!!!");

    noticeService.addNotice(notice);

    return "redirect:/notice/getNoticeList";
  }

  // 5. updateNotice (GET) ------------------------------------------------------------------------>
  @GetMapping("/notice/updateNotice")
  public String updateNotice (
    @RequestParam("notice_number") Integer notice_number,
    Model model
  ) throws Exception {

    logger.info("updateNotice GET 호출 !!!!!");

    model.addAttribute("noticeModel", noticeService.getNoticeDetails(notice_number));

    return "/notice/noticeUpdate";
  }

  // 5. updateNotice (POST) ----------------------------------------------------------------------->
  @PostMapping("/notice/updateNotice")
  public String updateNotice (
    @ModelAttribute Notice notice,
    @RequestParam("notice_imgsUrl") String existingImage
  ) throws Exception {

    logger.info("updateNotice POST 호출 !!!!!");

    noticeService.updateNotice(notice, existingImage);

    return "redirect:/board/getBoardList";
  }

  // 5-1. updateNoticeCount (GET) ----------------------------------------------------------------->

  // 5-2. updateLike (GET) ------------------------------------------------------------------------>
  @ResponseBody
  @GetMapping("/notice/updateLike")
  public Integer updateLike (
    @RequestParam("notice_number") Integer notice_number,
    HttpSession session
  ) throws Exception {

    logger.info("updateLike GET 호출 !!!!!");

    noticeService.updateLike(notice_number, session);
    Notice notice = noticeService.getNoticeDetails(notice_number);

    return notice.getNotice_like();
  }

  // 5-3. updateDislike (GET) --------------------------------------------------------------------->
  @GetMapping("/notice/updateDislike")
  @ResponseBody
  public Integer updateDislike(
    @RequestParam("notice_number") Integer notice_number,
    HttpSession session
  ) throws Exception {

    noticeService.updateDislike(notice_number, session);
    Notice notice = noticeService.getNoticeDetails(notice_number);

    return notice.getNotice_dislike();
  }

  // 6. deleteNotice (GET) ------------------------------------------------------------------------>
  @GetMapping("/notice/deleteNotice")
  public String deleteNotice(
    @RequestParam("notice_number") Integer notice_number,
    Model model,
    Notice notice
  ) throws Exception {

    logger.info("deleteNotice GET 호출 !!!!!");

    model.addAttribute("noticeModel", noticeService.getNoticeDetails(notice_number));

    return "/notice/noticeDelete";
  }

  // 6. deleteNotice (POST) ----------------------------------------------------------------------->
  @PostMapping("/notice/deleteNotice")
  public String deleteNotice(Notice notice, Integer notice_number) throws Exception {

    logger.info("deleteNotice POST 호출 !!!!!");

    noticeService.deleteNotice(notice_number);

    return "redirect:/notice/getNoticeList";
  }

}