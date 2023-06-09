package com.example.junghqlo.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.junghqlo.handler.EmailHandler;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Member;
import com.example.junghqlo.service.MemberService;

@Controller
public class MemberController {

  // 0. constructor injection --------------------------------------------------------------------->
  Logger logger = LoggerFactory.getLogger(this.getClass());
  private MemberService memberService;
  private EmailHandler emailHandler;
  MemberController(MemberService memberService, EmailHandler emailHandler) {
  this.memberService = memberService;
  this.emailHandler = emailHandler;
  }

  // 1. getMemberList (GET) ----------------------------------------------------------------------->
  @GetMapping("/member/getMemberList")
  public String getMemberList(
    @RequestParam(value="sort", required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model,
    Member member
  ) throws Exception {

    logger.info("getMemberList GET 호출 !!!!!");

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
    List<Member> memberList = page.getContent();

    model.addAttribute("page", page);
    model.addAttribute("memberList", memberList);

    return "/member/memberList";
  }

  // 2. getMemberDetails (GET) -------------------------------------------------------------------->
  @GetMapping("/member/getMemberDetails")
  public String getMemberDetails (HttpSession session, Model model) throws Exception {

    logger.info("getMemberDetails GET 호출 !!!!!");

    Integer member_number = (Integer) session.getAttribute("member_number");
    model.addAttribute("memberModel", memberService.getMemberDetails(member_number));

    return "/member/memberDetails";
  }

  // 2-1. viewMemberDetails (GET) ----------------------------------------------------------------->
  @GetMapping("/member/viewMemberDetails")
  public String viewMemberDetails(
    @RequestParam("member_number") Integer member_number,
    Model model
  ) throws Exception {

    logger.info("viewMemberDetails GET 호출 !!!!!");

    model.addAttribute("memberModel", memberService.getMemberDetails(member_number));

    return "/member/memberDetails";
  }

  // 3. searchMember (GET) ------------------------------------------------------------------------>
  @GetMapping("/member/searchMember")
  public String searchMember(
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    @RequestParam("searchType") String searchType,
    @RequestParam("keyword") String keyword,
    Model model,
    Member member
  ) throws Exception {

    logger.info("searchMember GET 호출 !!!!!");

    if(searchType == null || keyword == null) {
      return "redirect:/member/getMemberList";
    }
    else if(searchType.equals("id")) {
      searchType="member_id";
    }
    else if(searchType.equals("name")) {
      searchType="member_name";
    }

    PageHandler<Member> page = memberService.searchMember(pageNumber, itemsPer, keyword, searchType, member);
    List<Member> memberList = page.getContent();

    model.addAttribute("page", page);
    model.addAttribute("memberList", memberList);

    return "/member/memberSearch";
  }

  // 3-1. findMemberId (GET) ---------------------------------------------------------------------->
  @GetMapping("/member/findMemberId")
  public String findMemberId() throws Exception {

    logger.info("findMemberId GET 호출 !!!!!");

    return "/member/memberFindId";
  }

  // 3-1. findMemberId (POST) --------------------------------------------------------------------->
  @ResponseBody
  @PostMapping("/member/findMemberId")
  public String findMemberId(
    @RequestParam("member_name") String member_name,
    @RequestParam("member_email") String member_email
  ) throws Exception {

    logger.info("findMemberId POST 호출 !!!!!");

    String result = memberService.findMemberId(member_name, member_email);
    if (result != null) {
      return result;
    }
    else {
      return "~";
    }
  }

  // 3-2. findMemberPw (GET) ---------------------------------------------------------------------->
  @GetMapping("/member/findMemberPw")
  public String findMemberPw() throws Exception {

    logger.info("findMemberPw GET 호출 !!!!!");

    return "/member/memberFindPw";
  }

  // 3-2. findMemberPw (POST) --------------------------------------------------------------------->
  @ResponseBody
  @PostMapping("/member/findMemberPw")
  public String findMemberPw(
    @RequestParam("member_name") String member_name,
    @RequestParam("member_id") String member_id,
    @RequestParam("member_email") String member_email
  ) throws Exception {

    logger.info("findMemberPw POST 호출 !!!!!");

    String result = memberService.findMemberPw(member_name, member_id, member_email);

    if (result != null) {
      return result;
    }
    else {
      return "~";
    }
  }

  // 4. addMember (GET) --------------------------------------------------------------------------->
  @GetMapping("/member/addMember")
  public String addMember() throws Exception {

    logger.info("addMember GET 호출 !!!!!");

    return "/member/memberAdd";
  }

  // 4. addMember (POST) -------------------------------------------------------------------------->
  @PostMapping("/member/addMember")
  public String addMember(Member member
  ) throws Exception {

    logger.info("addMember POST 호출 !!!!!");

    memberService.addMember(member);

    return "redirect:/";
  }

  // 4-1. checkMemberId (GET) --------------------------------------------------------------------->
  @ResponseBody
  @GetMapping("/member/checkMemberId")
  public String checkMemberId(
    @RequestParam("member_id") String member_id
  ) throws Exception {

    logger.info("checkMemberId GET 호출 !!!!!");

    String result = memberService.checkMemberId(member_id);

    if (result == null || result.equals("") || result =="0") {
      return "0";
    }
    else {
      return "1";
    }
  }

  // 4-2. checkMemberIdPw (GET) ------------------------------------------------------------------->
  @ResponseBody
  @GetMapping("/member/checkMemberIdPw")
  public String checkMemberIdPw(
    @RequestParam("member_id") String member_id,
    @RequestParam("member_pw") String member_pw,
    HttpSession session,
    Member member
  ) throws Exception {

    logger.info("checkMemberIdPw GET 호출 !!!!!");

    String result = memberService.checkMemberIdPw(member_id, member_pw);

    Integer member_number = memberService.getMemberNumber(member_id);

    if (result == null || result.equals("") || result =="0") {
      return "0";
    }
    else {
      session.setAttribute("member_id", member_id);
      session.setAttribute("member_pw", member_pw);
      session.setAttribute("member_number", member_number);
      return "1";
    }
  }

  // 4-2. sendEmail (GET) ------------------------------------------------------------------------->
  @ResponseBody
  @GetMapping("/member/sendEmail")
  public Integer sendEmail(
    @RequestParam("member_email") String member_email
  ) throws Exception {

    logger.info("sendEmail GET 호출 !!!!!");

    // emailHandler
    String emailCode = emailHandler.generateCode();
    String receiveEmail = member_email;

    String result = emailHandler.sendEmailCode(receiveEmail, emailCode);

    if (result == emailCode) {
      return 1;
    }
    else {
      return 0;
    }
  }

  // 4-3. checkEmail (GET) ------------------------------------------------------------------------>
  @ResponseBody
  @GetMapping("/member/checkEmail")
  public Integer checkEmail(
    @RequestParam("member_email") String member_email,
    @RequestParam("email_code") String email_code
  ) throws Exception {

    logger.info("checkEmail GET 호출 !!!!!");

    Integer result = emailHandler.checkEmailCode(member_email, email_code);

    if (result != null && result > 0) {
      return 1;
    }
    else {
      return 0;
    }
  }

  // 4-3. loginMember (GET) ----------------------------------------------------------------------->
  @GetMapping("/member/loginMember")
  public String loginMember() throws Exception {

    logger.info("loginMember GET 호출 !!!!!");

    return "/member/memberLogin";
  }

  // 4-3. loginMember (POST) ---------------------------------------------------------------------->
  @ResponseBody
  @PostMapping("/member/loginMember")
  public String loginMember (
    @RequestParam("member_id") String member_id,
    @RequestParam("member_pw") String member_pw,
    HttpSession session
  ) throws Exception {

    logger.info("loginMember POST 호출 !!!!!");

    String result = memberService.checkMemberIdPw(member_id, member_pw);

    if (result == null || result.equals("")) {
      return "0";
    }
    else {
      session.setAttribute("member_id", member_id);
      session.setAttribute("member_pw", member_pw);
      return "1";
    }
  }

  // 4-2. logoutMember (GET) ---------------------------------------------------------------------->
  @ResponseBody
  @GetMapping("/member/logoutMember")
  public void logoutMember(HttpSession session
  ) throws Exception {

    logger.info("logoutMember GET 호출 !!!!!");

    session.invalidate();
  }

  // 5. updateMember (GET) ------------------------------------------------------------------------>
  @GetMapping("/member/updateMember")
  public String updateMember(HttpSession session, Model model) throws Exception {

    logger.info("updateMember GET 호출 !!!!!");

    Integer member_number = (Integer) session.getAttribute("member_number");

    model.addAttribute("memberModel", memberService.getMemberDetails(member_number));

    return "/member/memberUpdate";
  }

  // 5. updateMember (POST) ----------------------------------------------------------------------->
  @PostMapping("/member/updateMember")
  public String updateMember(Member member) throws Exception {

    logger.info("updateMember POST 호출 !!!!!");

    memberService.updateMember(member);

    return "redirect:/";
  }

  // 5-1. updateMemberPw (GET) -------------------------------------------------------------------->
  @GetMapping("/member/updateMemberPw")
  public String updateMemberPw() throws Exception {

    logger.info("updateMemberPw GET 호출 !!!!!");

    return "/member/memberUpdatePw";
  }

  // 5-1. updateMemberPw (POST) ------------------------------------------------------------------->
  @ResponseBody
  @PostMapping("/member/updateMemberPw")
  public String updateMemberPw (
    @RequestParam("member_pw") String member_pw,
    HttpSession session
  ) throws Exception {

    logger.info("updateMemberPw POST 호출 !!!!!");

    String member_id = (String) session.getAttribute("member_id");

    Integer result = memberService.updateMemberPw(member_id, member_pw);

    if (result == null || result < 0) {
      return "0";
    }
    else {
      return "1";
    }
  }

  // 6. deleteMember (GET) ------------------------------------------------------------------------>
  @GetMapping("/member/deleteMember")
  public String deleteMember (
    @RequestParam("member_number") Integer member_number,
    Model model
  ) throws Exception {

    logger.info("updateMember GET 호출 !!!!!");

    model.addAttribute("memberModel", memberService.getMemberDetails(member_number));

    return "/member/memberDelete";
  }

  // 6. deleteMember (POST) ----------------------------------------------------------------------->
  @ResponseBody
  @PostMapping("/member/deleteMember")
  public Integer deleteMember (
    @RequestParam("member_name") String member_name,
    @RequestParam("member_id") String member_id,
    @RequestParam("member_pw") String member_pw
  ) throws Exception {

    logger.info("deleteMember POST 호출 !!!!!");

    Integer result = memberService.deleteMember(member_name, member_id, member_pw);

    if (result == null || result == 0) {
      return 0;
    }
    else {
      return 1;
    }
  }

}