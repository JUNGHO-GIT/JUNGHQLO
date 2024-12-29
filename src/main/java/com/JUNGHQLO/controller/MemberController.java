package com.JUNGHQLO.controller;

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
import com.JUNGHQLO.handler.EmailHandler;
import com.JUNGHQLO.handler.PageHandler;
import com.JUNGHQLO.model.Member;
import com.JUNGHQLO.service.MemberService;

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

  // 0-1. loginMember (GET) ------------------------------------------------------------------------
  @GetMapping("/loginMember")
  public String loginMember() throws Exception {

    return MessageFormat.format("/page/{0}/{1}Login", page, page);
  };

  // 0-2. loginMember (POST) -----------------------------------------------------------------------
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
      session.setAttribute("member_number", memberService.getMemberNumber(member_id));
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 0-3. logoutMember (POST) ----------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/logoutMember")
  public Integer logoutMember(
    HttpSession session
  ) throws Exception {

    Integer result = 0;

    try{
      session.invalidate();
      result = 1;
    }
    catch (Exception e) {
      result = 0;
    }

    return result;
  }

  // 1. listMember(GET) --------------------------------------------------------------------------
  @GetMapping("/listMember")
  public String listMember(
    @ModelAttribute Member member,
    @RequestParam(defaultValue = "all") String sort,
    @RequestParam(defaultValue = "name") String type,
    @RequestParam(defaultValue = "") String keyword,
    @RequestParam(defaultValue = "1") Integer pageNumber,
    @RequestParam(defaultValue = "9") Integer itemsPer,
    Model model
  ) throws Exception {

    // 1. Sort handling
    String sortHandler = "";
    if(sort == null || sort.equals("all")) {
      sort = "member_number DESC";
      sortHandler = "all";
    }
    else if(sort.equals("idASC")) {
      sort = "member_id ASC";
      sortHandler = "idASC";
    }
    else if(sort.equals("idDESC")) {
      sort = "member_id DESC";
      sortHandler = "idDESC";
    }
    else if(sort.equals("nameASC")) {
      sort = "member_name ASC";
      sortHandler = "nameASC";
    }
    else if(sort.equals("nameDESC")) {
      sort = "member_name DESC";
      sortHandler = "nameDESC";
    }
    else if(sort.equals("dateASC")) {
      sort = "member_date ASC";
      sortHandler = "dateASC";
    }
    else if(sort.equals("dateDESC")) {
      sort = "member_date DESC";
      sortHandler = "dateDESC";
    }

    // 3. Type handling
    String typeHandler = "";
    if (type == null || keyword == null) {
      return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
    }
    else if (type.equals("all")) {
      type = "member_id OR member_name";
      typeHandler = "all";
    }
    else if (type.equals("id")) {
      type = "member_id";
      typeHandler = "id";
    }
    else if (type.equals("name")) {
      type = "member_name";
      typeHandler = "name";
    }

    // 4. Keyword handling
    String keywordHandler = "";
    if (keyword != null) {
      keywordHandler = keyword;
    }

    // 5. Page handling
    PageHandler<Member> pageHandler = (
      memberService.listMember(pageNumber, itemsPer, sort, type, keyword, member)
    );

    // 모델
    model.addAttribute("sortHandler", sortHandler);
    model.addAttribute("typeHandler", typeHandler);
    model.addAttribute("keywordHandler", keywordHandler);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/page/{0}/{1}List", page, page);
  };

  // 1-3. findMemberId (GET) -----------------------------------------------------------------------
  @GetMapping("/findMemberId")
  public String findMemberId() throws Exception {

    return MessageFormat.format("/page/{0}/{1}FindId", page, page);
  }

  // 1-3. findMemberId (POST) ----------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/findMemberId")
  public String findMemberId(
    @RequestParam String member_name,
    @RequestParam String member_email
  ) throws Exception {

    String result = memberService.findMemberId(member_name, member_email);

    if (result != null && !result.equals("")) {
      return result;
    }
    else {
      return "notExist";
    }
  }

  // 1-4. findMemberPw (GET) -----------------------------------------------------------------------
  @GetMapping("/findMemberPw")
  public String findMemberPw() throws Exception {

    return MessageFormat.format("/page/{0}/{1}FindPw", page, page);
  }

  // 1-4. findMemberPw (POST) ----------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/findMemberPw")
  public String findMemberPw(
    @RequestParam String member_id,
    @RequestParam String member_name,
    @RequestParam String member_email
  ) throws Exception {

    String result = memberService.findMemberPw(member_id, member_name, member_email);

    if (result != null && !result.equals("")) {
      return result;
    }
    else {
      return "notExist";
    }
  }

  // 2-1. detailMember (GET) -----------------------------------------------------------------------
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

    return MessageFormat.format("/page/{0}/{1}Detail", page, page);
  }

  // 2-2 checkMemberId (GET) ----------------------------------------------------------------------
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

  // 2-3 checkMemberIdPw (GET) --------------------------------------------------------------------
  @ResponseBody
  @GetMapping("/checkMemberIdPw")
  public Integer checkMemberIdPw(
    @ModelAttribute Member member,
    @RequestParam String member_id,
    @RequestParam String member_pw,
    HttpSession session
  ) throws Exception {

    Integer result = 0;
    Integer member_number = memberService.getMemberNumber(member_id);

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

  // 3-1. signupMember (GET) -----------------------------------------------------------------------
  @GetMapping("/signupMember")
  public String signupMember() throws Exception {

    return MessageFormat.format("/page/{0}/{1}Signup", page, page);
  }

  // 3-1. signupMember (POST) ----------------------------------------------------------------------
  @PostMapping("/signupMember")
  public String signupMember(
    @ModelAttribute Member member,
    Model model
  ) throws Exception {

    memberService.signupMember(member);

    return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
  }

  // 3-2. sendEmail (GET) --------------------------------------------------------------------------
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

  // 3-3. checkEmail (GET) -------------------------------------------------------------------------
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

  // 4-1. updateMember (GET) -----------------------------------------------------------------------
  @GetMapping("/updateMember")
  public String updateMember(
    @RequestParam Integer member_number,
    HttpSession session,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", memberService.detailMember(member_number));

    return MessageFormat.format("/page/{0}/{1}Update", page, page);
  }

  // 4-1. updateMember (POST) ----------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/updateMember")
  public Integer updateMember(
    @ModelAttribute Member member
  ) throws Exception {

    Integer result = 0;

    if (memberService.updateMember(member) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-2. updateMemberPw (GET) ---------------------------------------------------------------------
  @GetMapping("/updateMemberPw")
  public String updateMemberPw() throws Exception {

    return MessageFormat.format("/page/{0}/{1}UpdatePw", page, page);
  }

  // 4-2. updateMemberPw (POST) --------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/updateMemberPw")
  public Integer updateMemberPw(
    @RequestParam String member_id,
    @RequestParam String member_pw
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

  // 5. deleteMember (GET) -------------------------------------------------------------------------
  @GetMapping("/deleteMember")
  public String deleteMember(
    @RequestParam Integer member_number,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", memberService.detailMember(member_number));

    return MessageFormat.format("/page/{0}/{1}Delete", page, page);
  }

  // 5. deleteMember (POST) ------------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/deleteMember")
  public Integer deleteMember(
    @RequestParam String member_name,
    @RequestParam String member_id,
    @RequestParam String member_pw,
    HttpSession session
  ) throws Exception {

    Integer result = 0;

    if (memberService.deleteMember(member_name, member_id, member_pw) > 0) {
      session.invalidate();
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}