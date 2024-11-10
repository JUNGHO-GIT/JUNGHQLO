package com.example.junghqlo.model;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class Board {

  // fields --------------------------------------------------------------------------------------->
  private Integer board_number;
  private String board_title;
  private String board_contents;
  private String board_writer;
  private Integer board_count;
  private Integer board_like;
  private Integer board_dislike;
  private String board_imgsUrl;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime board_date;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime board_update;

  // getter --------------------------------------------------------------------------------------->
  public Integer getBoard_number() {
    return this.board_number;
  }
  public String getBoard_title() {
    return this.board_title;
  }
  public String getBoard_contents() {
    return this.board_contents;
  }
  public String getBoard_writer() {
    return this.board_writer;
  }
  public Integer getBoard_count() {
    return this.board_count;
  }
  public Integer getBoard_like() {
    return this.board_like;
  }
  public Integer getBoard_dislike() {
    return this.board_dislike;
  }
  public String getBoard_imgsUrl() {
    return this.board_imgsUrl;
  }
  public LocalDateTime getBoard_date() {
    return this.board_date;
  }
  public LocalDateTime getBoard_update() {
    return this.board_update;
  }

  // setter --------------------------------------------------------------------------------------->
  public void setBoard_number(Integer board_number) {
    this.board_number = board_number;
  }
  public void setBoard_title(String board_title) {
    this.board_title = board_title;
  }
  public void setBoard_contents(String board_contents) {
    this.board_contents = board_contents;
  }
  public void setBoard_writer(String board_writer) {
    this.board_writer = board_writer;
  }
  public void setBoard_count(Integer board_count) {
    this.board_count = board_count;
  }
  public void setBoard_like(Integer board_like) {
    this.board_like = board_like;
  }
  public void setBoard_dislike(Integer board_dislike) {
    this.board_dislike = board_dislike;
  }
  public void setBoard_imgsUrl(String board_imgsUrl) {
    this.board_imgsUrl = board_imgsUrl;
  }
  public void setBoard_date(LocalDateTime board_date) {
    this.board_date = board_date;
  }
  public void setBoard_update(LocalDateTime board_update) {
    this.board_update = board_update;
  }

}