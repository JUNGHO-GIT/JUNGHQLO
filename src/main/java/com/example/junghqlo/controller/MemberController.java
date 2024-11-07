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
import com.example.junghqlo.handler.EmailHandler;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Member;
import com.example.junghqlo.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

  // 0. constructor injection ----------------------------------------------------------------------
  private MemberService memberService;
  private EmailHandler emailHandler;
  MemberController(MemberService memberService, EmailHandler emailHandler) {
    this.memberService = memberService;
    this.emailHandler = emailHandler;
  }

  // 0. static -------------------------------------------------------------------------------------
  private static String page = "member";
  private static String PAGE = "Member";

  // 1-1. listMember(GET) --------------------------------------------------------------------------
  @GetMapping("/listMember")
  public String listMember(
    @ModelAttribute Member member,
    @RequestParam(defaultValue = "default") String sort,
    @RequestParam(defaultValue = "1") Integer pageNumber,
    @RequestParam(defaultValue = "9") Integer itemsPer,
    Model model
  ) throws Exception {

    // sorting order
    if(sort == null || sort.equals("default")) {
      sort="member_number DESC";
    }
    else if(sort.equals("idASC")) {
      sort="member_id ASC";
    }
    else if(sort.equals("idDESC")) {
      sort="member_id DESC";
    }
    else if(sort.equals("nameASC")) {
      sort="member_name ASC";
    }
    else if(sort.equals("nameDESC")) {
      sort="member_name DESC";
    }
    else if(sort.equals("dateASC")) {
      sort="member_date ASC";
    }
    else if(sort.equals("dateDESC")) {
      sort="member_date DESC";
    }

    PageHandler<Member> pageHandler = (
      memberService.listMember(pageNumber, itemsPer, sort, member)
    );

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/pages/{0}/{1}List", page, page);
  };

  // 1-2. searchMember (GET) -----------------------------------------------------------------------
  @GetMapping("/searchMember")
  public String searchMember(
    @ModelAttribute Member member,
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
    else if(searchType.equals("id")) {
      searchType="member_id";
    }
    else if(searchType.equals("name")) {
      searchType="member_name";
    }

    PageHandler<Member> pageHandler = (
      memberService.searchMember(pageNumber, itemsPer, searchType, keyword, member)
    );

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/pages/{0}/{1}List", page, page);
  }

  // 2. detailMember(GET) --------------------------------------------------------------------------
  @GetMapping("/detailMember")
  public String detailMember(
    @ModelAttribute Member member,
    @RequestParam Integer member_number,
    HttpSession session,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", memberService.detailMember(member_number));
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return MessageFormat.format("/pages/{0}/{1}Detail", page, page);
  }

  // 2-1. findMemberId (GET) -----------------------------------------------------------------------
  @GetMapping("/findMemberId")
  public String findMemberId() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}FindId", page, page);
  }

  // 2-1. findMemberId (POST) ----------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/findMemberId")
  public String findMemberId(
    @RequestParam String member_name,
    @RequestParam String member_email
  ) throws Exception {

    String result = memberService.findMemberId(member_name, member_email);
    if (result != null) {
      return result;
    }
    else {
      return "notExist";
    }
  }

  // 2-2. findMemberPw (GET) -----------------------------------------------------------------------
  @GetMapping("/findMemberPw")
  public String findMemberPw() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}FindPw", page, page);
  }

  // 2-2. findMemberPw (POST) ----------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/findMemberPw")
  public String findMemberPw(
    @RequestParam String member_name,
    @RequestParam String member_id,
    @RequestParam String member_email
  ) throws Exception {

    String result = memberService.findMemberPw(member_name, member_id, member_email);

    if (result != null) {
      return result;
    }
    else {
      return "notExist";
    }
  }

  // 3. addMember (GET) ----------------------------------------------------------------------------
  @GetMapping("/addMember")
  public String addMember() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Signup", page, page);
  }

  // 3. addMember (POST) ---------------------------------------------------------------------------
  @PostMapping("/addMember")
  public String addMember(
    @ModelAttribute Member member,
    Model model
  ) throws Exception {

    memberService.addMember(member);

    return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
  }

  // 2-3 checkMemberId (GET) ----------------------------------------------------------------------
  @ResponseBody
  @GetMapping("/checkMemberId")
  public Integer checkMemberId(
    @RequestParam String member_id
  ) throws Exception {

    Integer result = 0;

    if (memberService.checkMemberId(member_id) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 2-4 checkMemberIdPw (GET) --------------------------------------------------------------------
  @ResponseBody
  @GetMapping("/checkMemberIdPw")
  public Integer checkMemberIdPw(
    @ModelAttribute Member member,
    @RequestParam String member_id,
    @RequestParam String member_pw,
    HttpSession session
  ) throws Exception {

    Integer result = 0;
    Integer member_number = memberService.numberMember(member_id);

    if (memberService.checkMemberIdPw(member_id, member_pw) > 0) {
      session.setAttribute("member_id", member_id);
      session.setAttribute("member_pw", member_pw);
      session.setAttribute("member_number", member_number);
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-2. sendEmail (GET) --------------------------------------------------------------------------
  @ResponseBody
  @GetMapping("/sendEmail")
  public Integer sendEmail(
    @RequestParam String member_email
  ) throws Exception {

    // emailHandler
    String emailCode = emailHandler.generateCode();
    String receiveEmail = member_email;

    Integer result = 0;

    if (emailHandler.sendEmailCode(receiveEmail, emailCode).equals(emailCode)) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-3. checkEmail (GET) -------------------------------------------------------------------------
  @ResponseBody
  @GetMapping("/checkEmail")
  public Integer checkEmail(
    @RequestParam String member_email,
    @RequestParam String email_code
  ) throws Exception {

    Integer result = 0;

    if (emailHandler.checkEmailCode(member_email, email_code) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-3. loginMember (GET) ------------------------------------------------------------------------
  @GetMapping("/loginMember")
  public String loginMember() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Login", page, page);
  }

  // 4-3. loginMember (POST) -----------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/loginMember")
  public Integer loginMember(
    @RequestParam String member_id,
    @RequestParam String member_pw,
    HttpSession session
  ) throws Exception {

    Integer result = 0;

    if (memberService.checkMemberIdPw(member_id, member_pw) > 0) {
      session.setAttribute("member_id", member_id);
      session.setAttribute("member_pw", member_pw);
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-2. logoutMember (GET) -----------------------------------------------------------------------
  @ResponseBody
  @GetMapping("/logoutMember")
  public void logoutMember(
    HttpSession session
  ) throws Exception {

    session.invalidate();
  }

  // 4. updateMember (GET) -------------------------------------------------------------------------
  @GetMapping("/updateMember")
  public String updateMember(
    @RequestParam Integer member_number,
    HttpSession session,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", memberService.detailMember(member_number));

    return MessageFormat.format("/pages/{0}/{1}Update", page, page);
  }

  // 4. updateMember (POST) ------------------------------------------------------------------------
  @PostMapping("/updateMember")
  public String updateMember(
    @ModelAttribute Member member,
    HttpSession session
  ) throws Exception {

    memberService.updateMember(member);

    return MessageFormat.format("redirect:/", page);
  }

  // 4-1. updateMemberPw (GET) ---------------------------------------------------------------------
  @GetMapping("/updateMemberPw")
  public String updateMemberPw() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}UpdatePw", page, page);
  }

  // 4-1. updateMemberPw (POST) --------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/updateMemberPw")
  public Integer updateMemberPw(
    @RequestParam String member_id,
    @RequestParam String member_pw,
    HttpSession session
  ) throws Exception {

    Integer result = 0;

    if (memberService.updateMemberPw(member_id, member_pw) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 5. deleteMember (POST) ------------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/deleteMember")
  public Integer deleteMember(
    @RequestParam String member_name,
    @RequestParam String member_id,
    @RequestParam String member_pw
  ) throws Exception {

    Integer result = 0;

    if (memberService.deleteMember(member_name, member_id, member_pw) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}