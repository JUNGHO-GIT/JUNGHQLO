package com.example.junghqlo.model;

import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class Notice {

  // fields --------------------------------------------------------------------------------------->
  private Integer notice_number;
  private String notice_title;
  private String notice_contents;
  private String notice_writer;
  private Integer notice_count;
  private Integer notice_like;
  private Integer notice_dislike;
  private MultipartFile notice_imgsFile;
  private String notice_imgsUrl;
  private LocalDateTime notice_date;
  private LocalDateTime notice_update;

  // getter --------------------------------------------------------------------------------------->
  public Integer getNotice_number() {
    return this.notice_number;
  }
  public String getNotice_title() {
    return this.notice_title;
  }
  public String getNotice_contents() {
    return this.notice_contents;
  }
  public String getNotice_writer() {
    return this.notice_writer;
  }
  public Integer getNotice_count() {
    return this.notice_count;
  }
  public Integer getNotice_like() {
    return this.notice_like;
  }
  public Integer getNotice_dislike() {
    return this.notice_dislike;
  }
  public MultipartFile getNotice_imgsFile() {
    return this.notice_imgsFile;
  }
  public String getNotice_imgsUrl() {
    return this.notice_imgsUrl;
  }
  public LocalDateTime getNotice_date() {
    return this.notice_date;
  }
  public LocalDateTime getNotice_update() {
    return this.notice_update;
  }

  // setter --------------------------------------------------------------------------------------->
  public void setNotice_number(Integer notice_number) {
    this.notice_number = notice_number;
  }
  public void setNotice_title(String notice_title) {
    this.notice_title = notice_title;
  }
  public void setNotice_contents(String notice_contents) {
    this.notice_contents = notice_contents;
  }
  public void setNotice_writer(String notice_writer) {
    this.notice_writer = notice_writer;
  }
  public void setNotice_count(Integer notice_count) {
    this.notice_count = notice_count;
  }
  public void setNotice_like(Integer notice_like) {
    this.notice_like = notice_like;
  }
  public void setNotice_dislike(Integer notice_dislike) {
    this.notice_dislike = notice_dislike;
  }
  public void setNotice_imgsFile(MultipartFile notice_imgsFile) {
    this.notice_imgsFile = notice_imgsFile;
  }
  public void setNotice_imgsUrl(String notice_imgsUrl) {
    this.notice_imgsUrl = notice_imgsUrl;
  }
  public void setNotice_date(LocalDateTime notice_date) {
    this.notice_date = notice_date;
  }
  public void setNotice_update(LocalDateTime notice_update) {
    this.notice_update = notice_update;
  }

}