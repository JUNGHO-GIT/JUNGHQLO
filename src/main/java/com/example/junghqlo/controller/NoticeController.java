package com.example.junghqlo.controller;

import java.io.File;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.example.junghqlo.adapter.LocalDateTimeAdapter;
import com.example.junghqlo.adapter.FileAdapter;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Notice;
import com.example.junghqlo.service.NoticeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

  private static Logger logger = LoggerFactory.getLogger(NoticeController.class);
  private static Gson gson = new GsonBuilder()
  .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
  .registerTypeAdapter(File.class, new FileAdapter())
  .setPrettyPrinting()
  .create();

  // 1. listNotice (GET) ---------------------------------------------------------------------------
  @GetMapping("/listNotice")
  public String listNotice(
    @ModelAttribute Notice notice,
    @RequestParam(defaultValue = "all") String sort,
    @RequestParam(defaultValue = "title") String type,
    @RequestParam(defaultValue = "") String keyword,
    @RequestParam(defaultValue = "1") Integer pageNumber,
    @RequestParam(defaultValue = "9") Integer itemsPer,
    Model model
  ) throws Exception {

    // 1. Sort handling
    String sortHandler = "";
    if (sort == null || sort.equals("all")) {
      sort = "notice_number DESC";
      sortHandler = "all";
    }
    else if (sort.equals("titleASC")) {
      sort = "notice_title ASC";
      sortHandler = "titleASC";
    }
    else if (sort.equals("titleDESC")) {
      sort = "notice_title DESC";
      sortHandler = "titleDESC";
    }
    else if(sort.equals("dateASC")) {
      sort = "notice_date ASC";
      sortHandler = "dateASC";
    }
    else if(sort.equals("dateDESC")) {
      sort = "notice_date DESC";
      sortHandler = "dateDESC";
    }

    // 3. Type handling
    String typeHandler = "";
    if (type == null || keyword == null) {
      return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
    }
    else if (type.equals("all")) {
      type = "notice_title OR notice_contents";
      typeHandler = "all";
    }
    else if (type.equals("title")) {
      type = "notice_title";
      typeHandler = "title";
    }
    else if (type.equals("contents")) {
      type = "notice_contents";
      typeHandler = "contents";
    }

    // 4. Keyword handling
    String keywordHandler = "";
    if (keyword != null) {
      keywordHandler = keyword;
    }

    // 5. Page handling
    PageHandler<Notice> pageHandler = (
      noticeService.listNotice(pageNumber, itemsPer, sort, type, keyword, notice)
    );

    // 모델
    model.addAttribute("sortHandler", sortHandler);
    model.addAttribute("typeHandler", typeHandler);
    model.addAttribute("keywordHandler", keywordHandler);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/pages/{0}/{1}List", page, page);
  };

  // 2. detailNotice(GET) --------------------------------------------------------------------------
  @GetMapping("/detailNotice")
  public String detailNotice(
    @ModelAttribute Notice notice,
    @RequestParam Integer notice_number,
    HttpSession session,
    Model model
  ) throws Exception {

    // 조회수 증가
    noticeService.updateCount(notice_number, session);

    // 모델
    model.addAttribute("MODEL", noticeService.detailNotice(notice_number));
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return MessageFormat.format("/pages/{0}/{1}Detail", page, page);
  }

  // 3. saveNotice (GET) ---------------------------------------------------------------------------
  @GetMapping("/saveNotice")
  public String saveNotice() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Save", page, page);
  }

  // 3. saveNotice (POST) --------------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/saveNotice")
  public Integer saveNotice(
    @ModelAttribute Notice notice,
    @RequestParam(required = false) List<MultipartFile> imgsFile
  ) throws Exception {

    // imgsFile이 null이면 빈 리스트로 초기화
    if (imgsFile == null || imgsFile.size() == 0) {
      imgsFile = new ArrayList<MultipartFile>();
    }

    Integer result = 0;

    if (noticeService.saveNotice(notice, imgsFile) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-1. updateNotice (GET) -----------------------------------------------------------------------
  @GetMapping("/updateNotice")
  public String updateNotice (
    @RequestParam Integer notice_number,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", noticeService.detailNotice(notice_number));

    return MessageFormat.format("/pages/{0}/{1}Update", page, page);
  }

  // 4-1. updateNotice (POST) ----------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/updateNotice")
  public Integer updateNotice (
    @ModelAttribute Notice notice,
    @RequestParam(required = false) List<MultipartFile> imgsFile
  ) throws Exception {

    // imgsFile이 null이면 빈 리스트로 초기화
    if (imgsFile == null || imgsFile.size() == 0) {
      imgsFile = new ArrayList<MultipartFile>();
    }

    Integer result = 0;

    if (noticeService.updateNotice(notice, imgsFile) > 0) {
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