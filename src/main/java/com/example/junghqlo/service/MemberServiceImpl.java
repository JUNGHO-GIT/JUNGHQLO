package com.example.junghqlo.service;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.mapper.MemberMapper;
import com.example.junghqlo.model.Member;

@Service
public class MemberServiceImpl implements MemberService {

  // 0. constructor injection ----------------------------------------------------------------------
	private MemberMapper memberMapper;
  MemberServiceImpl(MemberMapper memberMapper) {
    this.memberMapper = memberMapper;
  }

	// 1-1. listMember ------------------------------------------------------------------------------
  @Override
  public PageHandler<Member> listMember(Integer pageNumber, Integer itemsPer, String sort, Member member) throws Exception {

    List<Member> content = memberMapper.listMember(sort);
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

  // 2. detailMember ---------------------------------------------------------------------------
  @Override
  public Member detailMember(Integer member_number) throws Exception {

    return memberMapper.detailMember(member_number);
  }

  // 2-1. numberMember --------------------------------------------------------------------------
  @Override
  public Integer numberMember(String member_id) throws Exception {

    return memberMapper.numberMember(member_id);
  }

  // 1-2. searchMember -------------------------------------------------------------------------------
  @Override
  public PageHandler<Member> searchMember(Integer pageNumber, Integer itemsPer, String searchType, String keyword, Member member
  ) throws Exception {

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

  // 2-1. findMemberId ----------------------------------------------------------------------------
  @Override
  public String findMemberId(String member_name, String member_email) throws Exception {
    return memberMapper.findMemberId(member_name, member_email);
  }

  // 2-2. findMemberPw -----------------------------------------------------------------------------
  @Override
  public String findMemberPw(String member_name, String member_id, String member_email) throws Exception {
    return memberMapper.findMemberPw(member_name, member_id, member_email);
  }

  // 3. addMember ----------------------------------------------------------------------------------
  @Override
  public void addMember(Member member) throws Exception {

    memberMapper.addMember(member);
  }

  // 2-3 checkMemberId ----------------------------------------------------------------------------
  @Override
  public Integer checkMemberId(String member_id) throws Exception {

    Integer result = 0;

    if (memberMapper.checkMemberId(member_id) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 2-4 checkMemberIdPw --------------------------------------------------------------------------
  @Override
  public Integer checkMemberIdPw(String member_id, String member_pw) throws Exception {

    Integer result = 0;

    if (memberMapper.checkMemberIdPw(member_id, member_pw) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-6. logoutMember ----------------------------------------------------------------------------
  @Override
  public void logoutMember(HttpSession session) throws Exception {

    session.removeAttribute("member");
  }

  // 4. updateMember ------------------------------------------------------------------------------
  @Override
  public void updateMember(Member member) throws Exception {

    memberMapper.updateMember(member);
  }

  // 4-1. updateMemberPw  --------------------------------------------------------------------------
  @Override
  public Integer updateMemberPw(String member_id, String member_pw) throws Exception {

    return memberMapper.updateMemberPw(member_id, member_pw);
  }

  // 5. deleteMember -------------------------------------------------------------------------------
  @Override
  public Integer deleteMember(String member_name, String member_id, String member_pw)
  throws Exception {

    Integer result = 0;

    if (memberMapper.deleteMember(member_name, member_id, member_pw) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}