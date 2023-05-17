package com.example.junghqlo.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Member {

  // fields --------------------------------------------------------------------------------------->
	private Integer member_number;
	private String member_id;
	private String member_pw;
	private String member_name;
	private String member_phone;
	private String member_email;
	private String member_address1;
	private String member_address2;
	private Integer member_agree;
  private LocalDateTime member_date;
  private LocalDateTime member_update;
  private String email_code;

  // getter --------------------------------------------------------------------------------------->
  public String getMember_phone() {
		return this.member_phone;
	}
	public String getMember_email() {
		return this.member_email;
	}
	public String getMember_address1() {
		return this.member_address1;
	}
	public String getMember_address2() {
		return this.member_address2;
	}
	public Integer getMember_agree() {
		return this.member_agree;
	}
	public Integer getMember_number() {
		return this.member_number;
	}
	public String getMember_id() {
		return this.member_id;
	}
	public String getMember_pw() {
		return this.member_pw;
	}
	public String getMember_name() {
		return this.member_name;
	}
  public LocalDateTime getMember_date() {
    return this.member_date;
  }
  public LocalDateTime getMember_update() {
    return this.member_update;
  }
  public String getEmail_code() {
    return this.email_code;
  }

  // setter --------------------------------------------------------------------------------------->
	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}
	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}
	public void setMember_address1(String member_address1) {
		this.member_address1 = member_address1;
	}
	public void setMember_address2(String member_address2) {
		this.member_address2 = member_address2;
	}
	public void setMember_agree(Integer member_agree) {
		this.member_agree = member_agree;
	}
	public void setMember_number(Integer member_number) {
		this.member_number = member_number;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
  public void setMember_date(LocalDateTime member_date) {
    this.member_date = member_date;
  }
  public void setMember_update(LocalDateTime member_update) {
    this.member_update = member_update;
  }
  public void setEmail_code(String email_code) {
    this.email_code = email_code;
  }

}