package com.example.junghqlo.mapper;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.example.junghqlo.model.Member;

@Mapper
public interface MemberMapper {

  // 0. result mapping ---------------------------------------------------------------------------->
  @Results ({
    @Result (property="member_number",   column="member_number", id=true),
    @Result (property="member_id",       column="member_id"),
    @Result (property="member_pw",       column="member_pw"),
    @Result (property="member_name",     column="member_name"),
    @Result (property="member_phone",    column="member_phone"),
    @Result (property="member_email",    column="member_email"),
    @Result (property="member_address1", column="member_address1"),
    @Result (property="member_address2", column="member_address2"),
    @Result (property="member_agree",    column="member_agree"),
    @Result (property="member_date",     column="member_date",
            typeHandler=com.example.junghqlo.handler.LocalDateTimeTypeHandler.class),
    @Result (property="member_update",   column="member_update",
            typeHandler=com.example.junghqlo.handler.LocalDateTimeTypeHandler.class),
    @Result (property="email_code",      column="email_code"),
  })

  // 1. getMemberList ----------------------------------------------------------------------------->
  @Select("SELECT * FROM member ORDER BY ${sort}")
  List<Member> getMemberList(@Param("sort") String sort) throws Exception;

  // 2. getMemberDetails -------------------------------------------------------------------------->
  @Select("SELECT * FROM member WHERE member_number=#{member_number}")
  Member getMemberDetails(Integer member_number) throws Exception;

  // 2-1. getMemberNumber ------------------------------------------------------------------------->
  @Select("SELECT member_number FROM member WHERE member_id=#{member_id}")
  Integer getMemberNumber(String member_id) throws Exception;

  // 3. searchMember ------------------------------------------------------------------------------>
  @Select("SELECT * FROM member WHERE ${keyword} LIKE CONCAT('%', #{searchType}, '%')")
  List<Member> searchMember( @Param("searchType") String searchType, @Param("keyword") String keyword) throws Exception;

  // 3-1. findMemberId ---------------------------------------------------------------------------->
  @Select("SELECT member_id FROM member WHERE member_name=#{member_name} AND member_email=#{member_email}")
  String findMemberId(String member_name, String member_email) throws Exception;

  // 3-2. findMemberPw ---------------------------------------------------------------------------->
  @Select("SELECT member_pw FROM member WHERE member_id=#{member_id} AND member_name=#{member_name} AND member_email=#{member_email}")
  String findMemberPw(String member_name, String member_id, String member_email) throws Exception;

  // 4. addMember --------------------------------------------------------------------------------->
  @Insert("INSERT INTO member(member_id, member_pw, member_name, member_phone, member_email, member_address1, member_address2, member_agree, member_date, email_code) VALUES (#{member_id}, #{member_pw}, #{member_name}, #{member_phone}, #{member_email}, #{member_address1}, #{member_address2}, #{member_agree}, NOW(), #{email_code})")
  void addMember(Member member) throws Exception;

  // 4-1. checkMemberId --------------------------------------------------------------------------->
  @Select("SELECT member_id FROM member WHERE member_id=#{member_id}")
  String checkMemberId(String member_id) throws Exception;

  // 4-2. checkMemberIdPw ------------------------------------------------------------------------->
  @Select("SELECT member_id FROM member WHERE member_id=#{member_id} AND member_pw=#{member_pw}")
  String checkMemberIdPw(String member_id, String member_pw) throws Exception;

  // 4-3. sendEmail 4-4. checkEmail 4-5. loginMember

  // 4-6. logoutMember ---------------------------------------------------------------------------->
  void logoutMember(HttpSession session) throws Exception;

  // 5. updateMember ------------------------------------------------------------------------------>
  @Update("UPDATE member SET member_id=#{member_id}, member_pw=#{member_pw}, member_name=#{member_name}, member_phone=#{member_phone}, member_email=#{member_email}, member_address1=#{member_address1}, member_address2=#{member_address2}, member_agree=#{member_agree}, member_update=NOW() WHERE member_id=#{member_id}")
  void updateMember(Member member) throws Exception;

  // 5-1. updateMemberPw -------------------------------------------------------------------------->
  @Update("UPDATE member SET member_pw=#{member_pw} WHERE member_id=#{member_id}")
  String updateMemberPw(String member_id, String member_pw) throws Exception;

  // 6. deleteMember ----------------------------------------------------------------------------
  @Delete("DELETE FROM member WHERE member_name=#{member_name} AND member_id=#{member_id} AND member_pw=#{member_pw}")
  Integer deleteMember(String member_name, String member_id, String member_pw) throws Exception;

}