package com.example.junghqlo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.mapper.BoardMapper;
import com.example.junghqlo.model.Board;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class BoardServiceImpl implements BoardService {

  @Value("${storage}")
  private String STORAGE;

  @Value("${bucket-main}")
  private String BUCKET_MAIN;

  @Value("${bucket-folder}")
  private String BUCKET_FOLDER;

  // 0. constructor injection ----------------------------------------------------------------------
  private BoardMapper boardMapper;
  BoardServiceImpl (BoardMapper boardMapper) {
    this.boardMapper = boardMapper;
  }

  // 1. listBoard -------------------------------------------------------------------------------
  @Override
  public PageHandler<Board> listBoard(
    Integer pageNumber,
    Integer itemsPer,
    String sort,
    String type,
    String keyword,
    Board board
  ) throws Exception {

    List<Board> content = boardMapper.listBoard(sort, type, keyword);
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

    List<Board> pageContent;

    if (pageStart >= itemsTotal) {
      pageContent = new ArrayList<>();
    }
    else {
      pageContent = content.subList(pageStart, pageEnd);
    }
    return new PageHandler<>(pageNumber, pageStart, pageEnd, 1, pageLast, itemsPer, itemsTotal, pageContent);
  }

  // 2. detailBoard --------------------------------------------------------------------------------
  @Override
  public Board detailBoard(
    Integer board_number
  ) throws Exception {

    return boardMapper.detailBoard(board_number);
  }

  // 3. saveBoard ----------------------------------------------------------------------------------
  @Override
  public Integer saveBoard(
    @ModelAttribute Board board,
    @RequestParam MultipartFile[] imgsFile
  ) throws Exception {

    String imgsUrl = "";
    String googleFileName = "";

    if (imgsFile.length == 0) {
      imgsUrl = STORAGE + "icon/logo.png";
    }

    // google cloud storage upload
    for (MultipartFile file : imgsFile) {
      byte[] bytes = file.getBytes();

      // google cloud storage file name
      googleFileName = (
        "board_"
        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))
        + ".webp"
      );
      Storage storage = StorageOptions.getDefaultInstance().getService();

      // Create the blobId
      BlobId blobId = BlobId.of(BUCKET_MAIN, BUCKET_FOLDER + "/board/" + googleFileName);

      BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
      .setContentType(file.getContentType())
      .setContentDisposition("inline; filename=\"" + googleFileName + "\"")
      .build();

      Blob blob = storage.create(blobInfo, bytes);
      blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

      // Path to Google Cloud Storage bucket URL
      imgsUrl = STORAGE + "/board/" + googleFileName;
    }

    Integer result = 0;

    if (boardMapper.saveBoard(board, imgsUrl) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  };

  // 4-1. updateBoard -----------------------------------------------------------------------------
  @Override
  public Integer updateBoard(
    @ModelAttribute Board board,
    @RequestParam MultipartFile[] imgsFile
  ) throws Exception {

    String imgsUrl = "";
    String googleFileName = "";

    if (imgsFile.length == 0) {
      imgsUrl = board.getBoard_imgsUrl();
    }

    // google cloud storage upload
    for (MultipartFile file : imgsFile) {
      byte[] bytes = file.getBytes();

      // google cloud storage file name
      googleFileName = (
        "board_"
        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))
        + ".webp"
      );
      Storage storage = StorageOptions.getDefaultInstance().getService();

      // Create the blobId
      BlobId blobId = BlobId.of(BUCKET_MAIN, BUCKET_FOLDER + "/board/" + googleFileName);

      BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
      .setContentType(file.getContentType())
      .setContentDisposition("inline; filename=\"" + googleFileName + "\"")
      .build();

      Blob blob = storage.create(blobInfo, bytes);
      blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

      // Path to Google Cloud Storage bucket URL
      imgsUrl = STORAGE + "/board/" + googleFileName;
    }

    Integer result = 0;

    if (boardMapper.updateBoard(board, imgsUrl) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4-2. updateCount ------------------------------------------------------------------------------
  @Override
  public void updateCount(
    Integer board_number,
    HttpSession session
  ) throws Exception {

    // 최초로 조회수를 올린 경우
    if (session.getAttribute("member_id") != null) {
      long update_time = 0;

      // 최근에 조회수를 올린 시간 검색
      String boardViewKey = (
        "update_board_view" + board_number + "_" + session.getAttribute("member_id")
      );

      if (session.getAttribute(boardViewKey) != null) {
        update_time = (long)session.getAttribute(boardViewKey);
      }
      long current_time = System.currentTimeMillis();

      // 일정 시간이 경과한 후 조회수 증가 처리 (하루에 한번만 조회수 증가)
      if (current_time - update_time > 1 * 60 * 60 * 1000) {
        boardMapper.updateCount(board_number);

        // 조회수 증가 처리 시간 저장
        session.setAttribute(boardViewKey, current_time);
      }
    }
  }

  // 4-3. updateLike -------------------------------------------------------------------------------
  @Override
  public void updateLike(
    Integer board_number,
    HttpSession session
  ) throws Exception {

    // 최초로 좋아요를 누른 경우
    if (session.getAttribute("member_id") != null) {
      long update_time = 0;

      // 최근에 좋아요를 누른 시간 검색
      String boardLikeKey = (
        "update_board_like" + board_number + "_" + session.getAttribute("member_id")
      );

      if (session.getAttribute(boardLikeKey) != null) {
        update_time = (long)session.getAttribute(boardLikeKey);
      }
      long current_time = System.currentTimeMillis();

      // 일정 시간이 경과한 후 좋아요 증가 처리 (1시간에 한번만 좋아요 증가)
      if (current_time - update_time > 1 * 60 * 60 * 1000) {
        boardMapper.updateLike(board_number);

        // 좋아요 증가 처리 시간 저장
        session.setAttribute(boardLikeKey, current_time);
      }
    }
  }

  // 4-4. updateDislike ----------------------------------------------------------------------------
  @Override
  public void updateDislike(
    Integer board_number,
    HttpSession session
  ) throws Exception {

    // 최초로 싫어요를 누른 경우
    if (session.getAttribute("member_id") != null) {
      long update_time = 0;

      // 최근에 싫어요를 누른 시간 검색
      String boardDislikeKey = (
        "update_board_dislike" + board_number + "_" + session.getAttribute("member_id")
      );

      if (session.getAttribute(boardDislikeKey) != null) {
        update_time = (long)session.getAttribute(boardDislikeKey);
      }
      long current_time = System.currentTimeMillis();

      // 일정 시간이 경과한 후 싫어요 증가 처리 (하루에 한번만 싫어요 증가)
      if (current_time - update_time > 1 * 60 * 60 * 1000) {
        boardMapper.updateDislike(board_number);

        // 싫어요 증가 처리 시간 저장
        session.setAttribute(boardDislikeKey, current_time);
      }
    }
  }

  // 5. deleteBoard -------------------------------------------------------------------------------
  @Override
  public Integer deleteBoard(
    Integer board_number
  ) throws Exception {

    Integer result = 0;

    if (boardMapper.deleteBoard(board_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}