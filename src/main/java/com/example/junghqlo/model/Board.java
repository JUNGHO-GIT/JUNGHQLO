package com.example.junghqlo.model;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}