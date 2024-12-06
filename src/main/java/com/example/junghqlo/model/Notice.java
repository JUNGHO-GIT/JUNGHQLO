package com.example.junghqlo.model;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notice {

  // fields --------------------------------------------------------------------------------------->
  private Integer notice_number;
  private String notice_title;
  private String notice_contents;
  private String notice_writer;
  private Integer notice_count;
  private Integer notice_like;
  private Integer notice_dislike;
  private String notice_imgsUrl;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime notice_date;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime notice_update;
}