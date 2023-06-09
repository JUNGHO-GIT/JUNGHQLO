package com.example.junghqlo.service;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.mapper.MemberMapper;
import com.example.junghqlo.model.Member;

@Service
public class MemberServiceImpl implements MemberService {

  // 0. constructor injection --------------------------------------------------------------------->
  Logger logger = LoggerFactory.getLogger(this.getClass());
	private MemberMapper memberMapper;
  MemberServiceImpl(MemberMapper memberMapper) {
  this.memberMapper = memberMapper;
  }

	// 1. getMemberList ----------------------------------------------------------------------------->
  @Override
  public PageHandler<Member> getMemberList(Integer pageNumber, Integer itemsPer, String sort, Member member) throws Exception {

    logger.info("getMemberList SERVICE  호출 !!!!!");

    List<Member> content = memberMapper.getMemberList(sort);
    Integer itemsTotal = content.size();
    Integer pageLast = (itemsTotal + itemsPer - 1) / itemsPer;

    // Ensure the pageNumber is greater than 0
    if (pageNumber <= 0) {
      pageNumber = 1;
    }

    // Ensure the pageNumber does not exceed pageLast, only if pageLast is greater than 0
    if (pageLast > 0 && pageNumber > pageLast) {
      pageNumber = pageLast;
    }

    Integer pageStart = (pageNumber - 1) * itemsPer;
    Integer pageEnd = Math.min(pageStart + itemsPer, itemsTotal);

    List<Member> pageContent;

    // If pageStart is greater than or equal to itemsTotal, set pageContent to an empty list
    if (pageStart >= itemsTotal) {
      pageContent = new ArrayList<>();
    }
    else {
      pageContent = content.subList(pageStart, pageEnd);
    }

    return new PageHandler<>(pageNumber, pageStart, pageEnd, 1, pageLast, itemsPer, itemsTotal, pageContent);
  }

  // 2. getMemberDetails -------------------------------------------------------------------------->
  @Override
  public Member getMemberDetails(Integer member_number) throws Exception {

    logger.info("getMemberDetails SERVICE  호출 !!!!!");

    return memberMapper.getMemberDetails(member_number);
  }

  // 2-1. getMemberNumber ------------------------------------------------------------------------->
  @Override
  public Integer getMemberNumber(String member_id) throws Exception {

    logger.info("getMemberNumber SERVICE  호출 !!!!!");

    return memberMapper.getMemberNumber(member_id);
  }

  // 3. searchMember ------------------------------------------------------------------------------>
  @Override
  public PageHandler<Member> searchMember(Integer pageNumber, Integer itemsPer, String searchType, String keyword, Member member
  ) throws Exception {

    logger.info("searchMember SERVICE  호출 !!!!!");

    List<Member> content = memberMapper.searchMember(searchType, keyword);
    Integer itemsTotal = content.size();
    Integer pageLast = (itemsTotal + itemsPer - 1) / itemsPer;

    // Ensure the pageNumber is greater than 0
    if (pageNumber <= 0) {
      pageNumber = 1;
    }

    // Ensure the pageNumber does not exceed pageLast, only if pageLast is greater than 0
    if (pageLast > 0 && pageNumber > pageLast) {
      pageNumber = pageLast;
    }

    Integer pageStart = (pageNumber - 1) * itemsPer;
    Integer pageEnd = Math.min(pageStart + itemsPer, itemsTotal);

    List<Member> pageContent;

    // If pageStart is greater than or equal to itemsTotal, set pageContent to an empty list
    if (pageStart >= itemsTotal) {
      pageContent = new ArrayList<>();
    }
    else {
      pageContent = content.subList(pageStart, pageEnd);
    }

    return new PageHandler<>(pageNumber, pageStart, pageEnd, 1, pageLast, itemsPer, itemsTotal, pageContent);
  }

  // 3-1. findMemberId  --------------------------------------------------------------------------->
  @Override
  public String findMemberId(String member_name, String member_email) throws Exception {

    logger.info("findMemberId SERVICE  호출 !!!!!");
    return memberMapper.findMemberId(member_name, member_email);
  }

  // 3-2. findMemberPw ---------------------------------------------------------------------------->
  @Override
  public String findMemberPw(String member_name, String member_id, String member_email) throws Exception {

    logger.info("findMemberPw SERVICE  호출 !!!!!");
    return memberMapper.findMemberPw(member_name, member_id, member_email);
  }

  // 4. addMember --------------------------------------------------------------------------------->
  @Override
  public void addMember(Member member) throws Exception {

    logger.info("addMember SERVICE  호출 !!!!!");

    memberMapper.addMember(member);
  }

  // 4-1. checkMemberId --------------------------------------------------------------------------->
  @Override
  public String checkMemberId(String member_id) throws Exception {

    logger.info("checkMemberId SERVICE  호출 !!!!!");

    return memberMapper.checkMemberId(member_id);
  }

  // 4-2. checkMemberIdPw
  @Override
  public String checkMemberIdPw(String member_id, String member_pw) throws Exception {

    logger.info("checkMemberIdPw SERVICE  호출 !!!!!");

    return memberMapper.checkMemberIdPw(member_id, member_pw);
  }

  // 4-3. sendEmail 4.4 checkEmail 4-5. loginMember

  // 4-6. logoutMember --------------------------------------------------------------------------->
  @Override
  public void logoutMember(HttpSession session) throws Exception {

    logger.info("logoutMember SERVICE  호출 !!!!!");

    session.removeAttribute("member");
  }

  // 5. updateMember ----------------------------------------------------------------------------->
  @Override
  public void updateMember(Member member) throws Exception {

    logger.info("updateMember SERVICE  호출 !!!!!");

    memberMapper.updateMember(member);
  }

  // 5-1. updateMemberPw  ------------------------------------------------------------------------>
  @Override
  public Integer updateMemberPw(String member_id, String member_pw) throws Exception {

    logger.info("updateMemberPw SERVICE  호출 !!!!!");

    return memberMapper.updateMemberPw(member_id, member_pw);
  }

  // 6. deleteMember ----------------------------------------------------------------------------->
  @Override
  public Integer deleteMember(String member_name, String member_id, String member_pw)
  throws Exception {

    logger.info("deleteMember SERVICE  호출 !!!!!");

    return memberMapper.deleteMember(member_name, member_id, member_pw);
  }

}