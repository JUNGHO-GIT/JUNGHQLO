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

	// 1. listMember ---------------------------------------------------------------------------------
  @Override
  public PageHandler<Member> listMember(
    Integer pageNumber,
    Integer itemsPer,
    String sort,
    String type,
    String keyword,
    Member member
  ) throws Exception {

    List<Member> content = memberMapper.listMember(sort, type, keyword);
    Integer itemsTotal = content.size();
    Integer pageLast = (itemsTotal + itemsPer - 1) / itemsPer;

    if (pageNumber <= 0) {
      pageNumber = 1;
    }

    if (pageLast > 0 && pageNumber > pageLast) {
      pageNumber = pageLast;
    }

    Integer pageStart = (pageNumber - 1) * itemsPer;
    Integer pageEnd = Math.min(pageStart + itemsPer, itemsTotal);

    List<Member> pageContent;

    if (pageStart >= itemsTotal) {
      pageContent = new ArrayList<>();
    }
    else {
      pageContent = content.subList(pageStart, pageEnd);
    }

    return new PageHandler<>(pageNumber, pageStart, pageEnd, 1, pageLast, itemsPer, itemsTotal, pageContent);
  }

  // 1-3. findMemberId -----------------------------------------------------------------------------
  @Override
  public String findMemberId(
    String member_name,
    String member_email
  ) throws Exception {

    return memberMapper.findMemberId(member_name, member_email);
  }

  // 1-4. findMemberPw -----------------------------------------------------------------------------
  @Override
  public String findMemberPw(
    String member_name,
    String member_id,
    String member_email
  ) throws Exception {

    return memberMapper.findMemberPw(member_name, member_id, member_email);
  }

  // 2-1. detailMember -----------------------------------------------------------------------------
  @Override
  public Member detailMember(
    Integer member_number
  ) throws Exception {

    return memberMapper.detailMember(member_number);
  }

  // 2-2. getMemberNumber --------------------------------------------------------------------------
  @Override
  public Integer getMemberNumber(
    String member_id
  ) throws Exception {

    return memberMapper.getMemberNumber(member_id);
  }

  // 2-3 checkMemberId ----------------------------------------------------------------------------
  @Override
  public Integer checkMemberId(
    String member_id
  ) throws Exception {

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
  public Integer checkMemberIdPw(
    String member_id,
    String member_pw
  ) throws Exception {

    Integer result = 0;

    if (memberMapper.checkMemberIdPw(member_id, member_pw) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 3. signupMember ----------------------------------------------------------------------------------
  @Override
  public void signupMember(
    Member member
  ) throws Exception {

    memberMapper.signupMember(member);
  }

  // 4-1. updateMember -----------------------------------------------------------------------------
  @Override
  public Integer updateMember(
    Member member
  ) throws Exception {

    Integer result = 0;

    if (memberMapper.updateMember(member) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-2. updateMemberPw  --------------------------------------------------------------------------
  @Override
  public Integer updateMemberPw(
    String member_id,
    String member_pw
  ) throws Exception {

    return memberMapper.updateMemberPw(member_id, member_pw);
  }

  // 5. deleteMember -------------------------------------------------------------------------------
  @Override
  public Integer deleteMember(
    String member_name,
    String member_id,
    String member_pw
  ) throws Exception {

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