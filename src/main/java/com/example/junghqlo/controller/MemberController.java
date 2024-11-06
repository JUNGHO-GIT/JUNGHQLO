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
import com.example.junghqlo.handler.EmailHandler;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Member;
import com.example.junghqlo.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

  // 0. constructor injection --------------------------------------------------------------------->
  private MemberService memberService;
  private EmailHandler emailHandler;
  MemberController(MemberService memberService, EmailHandler emailHandler) {
    this.memberService = memberService;
    this.emailHandler = emailHandler;
  }

  // 0. static -------------------------------------------------------------------------------------
  private static String PAGE = "member";
  private static String PAGE_UP = "Member";
  private static Member MODEL = new Member();
  private static List<Member> LIST = new ArrayList<>();

  // 1. getMemberList (GET) ----------------------------------------------------------------------->
  @GetMapping("/getMemberList")
  public String getMemberList(
    @ModelAttribute Member member,
    @RequestParam(required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
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

    PageHandler<Member> page = memberService.getMemberList(pageNumber, itemsPer, sort, member);
    LIST = page.getContent();

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
    model.addAttribute("LIST", LIST);

    return MessageFormat.format("/pages/{0}/{1}List", PAGE, PAGE);
  };

  // 2. getMemberDetails (GET) -------------------------------------------------------------------->
  @GetMapping("/getMemberDetails")
  public String getMemberDetails (
    @ModelAttribute Member member,
    HttpSession session,
    Model model
  ) throws Exception {

    Integer member_number = (Integer) session.getAttribute("member_number");
    MODEL = memberService.getMemberDetails(member_number);

    // 모델
    model.addAttribute("MODEL", MODEL);
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return MessageFormat.format("/pages/{0}/{1}Details", PAGE, PAGE);
  }

  // 2-1. viewMemberDetails (GET) ----------------------------------------------------------------->
  @GetMapping("/viewMemberDetails")
  public String viewMemberDetails(
    @ModelAttribute Member member,
    @RequestParam Integer member_number,
    Model model
  ) throws Exception {

    MODEL = memberService.getMemberDetails(member_number);

    // 모델
    model.addAttribute("MODEL", MODEL);

    return MessageFormat.format("/pages/{0}/{1}Details", PAGE, PAGE);
  }

  // 3. searchMember (GET) ------------------------------------------------------------------------>
  @GetMapping("/searchMember")
  public String searchMember(
    @ModelAttribute Member member,
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
    else if(searchType.equals("id")) {
      searchType="member_id";
    }
    else if(searchType.equals("name")) {
      searchType="member_name";
    }

    PageHandler<Member> page
      = memberService.searchMember(pageNumber, itemsPer, keyword, searchType, member);
    LIST = page.getContent();

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
    model.addAttribute("LIST", LIST);

    return MessageFormat.format("/pages/{0}/{1}Search", PAGE, PAGE);
  }

  // 3-1. findMemberId (GET) ---------------------------------------------------------------------->
  @GetMapping("/findMemberId")
  public String findMemberId() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}FindId", PAGE, PAGE_UP);
  }

  // 3-1. findMemberId (POST) --------------------------------------------------------------------->
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
      return "~";
    }
  }

  // 3-2. findMemberPw (GET) ---------------------------------------------------------------------->
  @GetMapping("/findMemberPw")
  public String findMemberPw() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}FindPw", PAGE, PAGE_UP);
  }

  // 3-2. findMemberPw (POST) --------------------------------------------------------------------->
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
      return "~";
    }
  }

  // 4. addMember (GET) --------------------------------------------------------------------------->
  @GetMapping("/addMember")
  public String addMember() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Add", PAGE, PAGE_UP);
  }

  // 4. addMember (POST) -------------------------------------------------------------------------->
  @PostMapping("/addMember")
  public String addMember(
    @ModelAttribute Member member,
    Model model
  ) throws Exception {

    memberService.addMember(member);

    return MessageFormat.format("redirect:/{0}/get{1}List", PAGE, PAGE_UP);
  }

  // 4-1. checkMemberId (GET) --------------------------------------------------------------------->
  @ResponseBody
  @GetMapping("/checkMemberId")
  public String checkMemberId(
    @RequestParam String member_id
  ) throws Exception {

    String result = memberService.checkMemberId(member_id);

    if (result == null || result.equals("") || result =="0") {
      return "fail";
    }
    else {
      return "success";
    }
  }

  // 4-2. checkMemberIdPw (GET) ------------------------------------------------------------------->
  @ResponseBody
  @GetMapping("/checkMemberIdPw")
  public String checkMemberIdPw(
    @ModelAttribute Member member,
    @RequestParam String member_id,
    @RequestParam String member_pw,
    HttpSession session
  ) throws Exception {

    String result = memberService.checkMemberIdPw(member_id, member_pw);
    Integer member_number = memberService.getMemberNumber(member_id);

    if (result == null || result.equals("") || result =="0") {
      return "fail";
    }
    else {
      session.setAttribute("member_id", member_id);
      session.setAttribute("member_pw", member_pw);
      session.setAttribute("member_number", member_number);
      return "success";
    }
  }

  // 4-2. sendEmail (GET) ------------------------------------------------------------------------->
  @ResponseBody
  @GetMapping("/sendEmail")
  public String sendEmail(
    @RequestParam String member_email
  ) throws Exception {

    // emailHandler
    String emailCode = emailHandler.generateCode();
    String receiveEmail = member_email;

    String result = emailHandler.sendEmailCode(receiveEmail, emailCode);

    if (result == emailCode) {
      return "success";
    }
    else {
      return "fail";
    }
  }

  // 4-3. checkEmail (GET) ------------------------------------------------------------------------>
  @ResponseBody
  @GetMapping("/checkEmail")
  public String checkEmail(
    @RequestParam String member_email,
    @RequestParam String email_code
  ) throws Exception {

    Integer result = emailHandler.checkEmailCode(member_email, email_code);

    if (result != null && result > 0) {
      return "success";
    }
    else {
      return "fail";
    }
  }

  // 4-3. loginMember (GET) ----------------------------------------------------------------------->
  @GetMapping("/loginMember")
  public String loginMember() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Login", PAGE, PAGE_UP);
  }

  // 4-3. loginMember (POST) ---------------------------------------------------------------------->
  @ResponseBody
  @PostMapping("/member/loginMember")
  public String loginMember (
    @RequestParam String member_id,
    @RequestParam String member_pw,
    HttpSession session
  ) throws Exception {

    String result = memberService.checkMemberIdPw(member_id, member_pw);

    if (result == null || result.equals("")) {
      return "fail";
    }
    else {
      session.setAttribute("member_id", member_id);
      session.setAttribute("member_pw", member_pw);
      return "success";
    }
  }

  // 4-2. logoutMember (GET) ---------------------------------------------------------------------->
  @ResponseBody
  @GetMapping("/logoutMember")
  public void logoutMember(
    HttpSession session
  ) throws Exception {

    session.invalidate();
  }

  // 5. updateMember (GET) ------------------------------------------------------------------------>
  @GetMapping("/updateMember")
  public String updateMember(
    HttpSession session,
    Model model
  ) throws Exception {

    Integer member_number = (Integer) session.getAttribute("member_number");

    model.addAttribute("memberModel", memberService.getMemberDetails(member_number));

    return MessageFormat.format("/pages/{0}/{1}Update", PAGE, PAGE);
  }

  // 5. updateMember (POST) ----------------------------------------------------------------------->
  @PostMapping("/member/updateMember")
  public String updateMember(
    @ModelAttribute Member member,
    HttpSession session
  ) throws Exception {

    memberService.updateMember(member);

    return MessageFormat.format("redirect:/", PAGE);
  }

  // 5-1. updateMemberPw (GET) -------------------------------------------------------------------->
  @GetMapping("/updateMemberPw")
  public String updateMemberPw() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}UpdatePw", PAGE, PAGE);
  }

  // 5-1. updateMemberPw (POST) ------------------------------------------------------------------->
  @ResponseBody
  @PostMapping("/member/updateMemberPw")
  public String updateMemberPw (
    @RequestParam String member_pw,
    HttpSession session
  ) throws Exception {

    String member_id = (String) session.getAttribute("member_id");

    Integer result = memberService.updateMemberPw(member_id, member_pw);

    if (result == null || result < 0) {
      return "fail";
    }
    else {
      return "success";
    }
  }

  // 6. deleteMember (GET) ------------------------------------------------------------------------>
  @GetMapping("/deleteMember")
  public String deleteMember (
    @RequestParam Integer member_number,
    @ModelAttribute Member member,
    Model model
  ) throws Exception {

    MODEL = memberService.getMemberDetails(member_number);

    // 모델
    model.addAttribute("MODEL", MODEL);

    return MessageFormat.format("/pages/{0}/{1}Delete", PAGE, PAGE);
  }

  // 6. deleteMember (POST) ----------------------------------------------------------------------->
  @ResponseBody
  @PostMapping("/member/deleteMember")
  public String deleteMember (
    @RequestParam String member_name,
    @RequestParam String member_id,
    @RequestParam String member_pw
  ) throws Exception {

    Integer result = memberService.deleteMember(member_name, member_id, member_pw);

    if (result == null || result == 0) {
      return "fail";
    }
    else {
      return "success";
    }
  }

}