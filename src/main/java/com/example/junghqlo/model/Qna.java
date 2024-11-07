package com.example.junghqlo.model;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class Qna {

  // fields --------------------------------------------------------------------------------------->
  private Integer qna_number;
  private String qna_category;
  private String qna_title;
  private String qna_contents;
  private String qna_writer;
  private Integer qna_count;
  private Integer qna_like;
  private Integer qna_dislike;
  private boolean qna_answer1;
  private String qna_answer2;
  private MultipartFile qna_imgsFile;
  private String qna_imgsUrl;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime qna_date;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime qna_update;

  // getter --------------------------------------------------------------------------------------->
  public Integer getQna_number() {
    return this.qna_number;
  }
  public String getQna_category() {
    return this.qna_category;
  }
  public String getQna_title() {
    return this.qna_title;
  }
  public String getQna_contents() {
    return this.qna_contents;
  }
  public String getQna_writer() {
    return this.qna_writer;
  }
  public Integer getQna_count() {
    return this.qna_count;
  }
  public Integer getQna_like() {
    return this.qna_like;
  }
  public Integer getQna_dislike() {
    return this.qna_dislike;
  }
  public LocalDateTime getQna_date() {
    return this.qna_date;
  }
  public MultipartFile getQna_imgsFile() {
    return this.qna_imgsFile;
  }
  public String getQna_imgsUrl() {
    return this.qna_imgsUrl;
  }
  public Boolean getQna_answer1() {
    return this.qna_answer1;
  }
  public String getQna_answer2() {
    return this.qna_answer2;
  }
  public LocalDateTime getQna_update() {
    return this.qna_update;
  }

  // setter --------------------------------------------------------------------------------------->
  public void setQna_number(Integer qna_number) {
    this.qna_number = qna_number;
  }
  public void setQna_category(String qna_category) {
    this.qna_category = qna_category;
  }
  public void setQna_title(String qna_title) {
    this.qna_title = qna_title;
  }
  public void setQna_contents(String qna_contents) {
    this.qna_contents = qna_contents;
  }
  public void setQna_writer(String qna_writer) {
    this.qna_writer = qna_writer;
  }
  public void setQna_count(Integer qna_count) {
    this.qna_count = qna_count;
  }
  public void setQna_like(Integer qna_like) {
    this.qna_like = qna_like;
  }
  public void setQna_dislike(Integer qna_dislike) {
    this.qna_dislike = qna_dislike;
  }
  public void setQna_date(LocalDateTime qna_date) {
    this.qna_date = qna_date;
  }
  public void setQna_imgsFile(MultipartFile qna_imgsFile) {
    this.qna_imgsFile = qna_imgsFile;
  }
  public void setQna_imgsUrl(String qna_imgsUrl) {
    this.qna_imgsUrl = qna_imgsUrl;
  }
  public void setQna_answer1(Boolean qna_answer1) {
    this.qna_answer1 = qna_answer1;
  }
  public void setQna_answer2(String qna_answer2) {
    this.qna_answer2 = qna_answer2;
  }
  public void setQna_update(LocalDateTime qna_update) {
    this.qna_update = qna_update;
  }

}