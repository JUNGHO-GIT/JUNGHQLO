package com.JUNGHQLO.model;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Qna {

  // fields ----------------------------------------------------------------------------------------
  private Integer qna_number;
  private String qna_category;
  private String qna_title;
  private String qna_contents;
  private String qna_writer;
  private Integer qna_count;
  private Integer qna_like;
  private Integer qna_dislike;
  private String qna_answer;
  private String qna_imgsUrl;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime qna_date;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime qna_update;
}