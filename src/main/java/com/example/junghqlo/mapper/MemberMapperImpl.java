package com.example.junghqlo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.example.junghqlo.model.Member;

@Repository
public class MemberMapperImpl implements MemberMapper {

  // 0. constructor injection ----------------------------------------------------------------------
  SqlSessionTemplate sqlSession;
  MemberMapperImpl(SqlSessionTemplate sqlSession) {
    this.sqlSession = sqlSession;
  }

  // 1. listMember ------------------------------------------------------------------------------
  @Override
  public List<Member> listMember(
    String sort,
    String type,
    String keyword
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("sort", sort);
    map.put("type", type);
    map.put("keyword", keyword);

    return sqlSession.selectList("listMember", map);
  }

  // 1-3. findMemberId -----------------------------------------------------------------------------
  @Override
  public String findMemberId(
    String member_name,
    String member_email
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("member_name", member_name);
    map.put("member_email", member_email);

    return sqlSession.selectOne("findMemberId", map);
  }

  // 1-4. findMemberPw -----------------------------------------------------------------------------
  @Override
  public String findMemberPw(
    String member_id,
    String member_name,
    String member_email
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("member_id", member_id);
    map.put("member_name", member_name);
    map.put("member_email", member_email);

    return sqlSession.selectOne("findMemberPw", map);
  }

  // 2-1. detailMember -----------------------------------------------------------------------------
  @Override
  public Member detailMember(
    Integer member_number
  ) throws Exception {

    return sqlSession.selectOne("detailMember", member_number);
  }

  // 2-2. getMemberNumber --------------------------------------------------------------------------
  @Override
  public Integer getMemberNumber(
    String member_id
  ) throws Exception {

    return sqlSession.selectOne("getMemberNumber", member_id);
  }

  // 2-3 checkMemberId ----------------------------------------------------------------------------
  @Override
  public Integer checkMemberId(
    String member_id
  ) throws Exception {

    Integer result = 0;

    if ((Integer) sqlSession.selectOne("checkMemberId", member_id) > 0) {
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

    Map<String, Object> map = new HashMap<>();
    map.put("member_id", member_id);
    map.put("member_pw", member_pw);

    if ((Integer) sqlSession.selectOne("checkMemberIdPw", map) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 3-1. signupMember -----------------------------------------------------------------------------
  @Override
  public void signupMember(
    Member member
  ) throws Exception {

    sqlSession.insert("signupMember", member);
  }

  // 4-1. updateMember -----------------------------------------------------------------------------
  @Override
  public Integer updateMember(
    Member member
  ) throws Exception {

    Integer result = 0;

    if (sqlSession.update("updateMember", member) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-2. updateMemberPw ---------------------------------------------------------------------------
  @Override
  public Integer updateMemberPw(
    String member_id,
    String member_pw
  ) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("member_id", member_id);
    map.put("member_pw", member_pw);

    return sqlSession.selectOne("updateMemberPw", map);
  }

  // 5. deleteMember -------------------------------------------------------------------------------
  @Override
  public Integer deleteMember(
    String member_name,
    String member_id,
    String member_pw
  ) throws Exception {

    Integer result = 0;

    Map<String, Object> map = new HashMap<>();
    map.put("member_name", member_name);
    map.put("member_id", member_id);
    map.put("member_pw", member_pw);

    if (sqlSession.delete("deleteMember", map) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}