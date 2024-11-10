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

  // 1. listBoard ----------------------------------------------------------------------------------
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

  // 3. saveBoard ---------------------------------------------------------------------------------
  @Override
  public Integer saveBoard(
    @ModelAttribute Board board,
    @RequestParam(required = false) List<MultipartFile> imgsFile
  ) throws Exception {

    // 변수 선언
    StringBuilder newImgsUrlBuilder = new StringBuilder();
    String existingImgsUrl = board.getBoard_imgsUrl();
    String newImgsUrl = "";
    String mergedImgsUrl = "";
    String googleFileName = "";

    // 이미지가 있을 경우 google cloud storage에 업로드
    if (imgsFile.size() > 0) {
      for (int i = 0; i < imgsFile.size(); i++) {
        MultipartFile file = imgsFile.get(i);
        byte[] bytes = file.getBytes();

        // 고유한 파일 이름 생성 (인덱스 추가)
        googleFileName = String.format(
          "board_%s_%d.webp",
          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")),
          i
        );

        // storage 객체 생성
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // blobId 생성
        BlobId blobId = BlobId.of(BUCKET_MAIN, BUCKET_FOLDER + "/board/" + googleFileName);

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
          .setContentType(file.getContentType())
          .setContentDisposition("inline; filename=\"" + googleFileName + "\"")
          .build();

        Blob blob = storage.create(blobInfo, bytes);
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        // 업로드된 파일 이름을 newImgsUrl에 추가
        if (newImgsUrlBuilder.length() > 0) {
          newImgsUrlBuilder.append(",");
        }
        newImgsUrlBuilder.append(googleFileName);
      }

      // 최종 newImgsUrl 설정
      newImgsUrl = newImgsUrlBuilder.toString().trim();
    }

    // 이미지 URL 합치기
    if (existingImgsUrl != null && existingImgsUrl.length() > 0) {
      if (newImgsUrl != null && newImgsUrl.length() > 0) {
        mergedImgsUrl = existingImgsUrl + "," + newImgsUrl;
      }
      else {
        mergedImgsUrl = existingImgsUrl;
      }
    }
    else {
      mergedImgsUrl = newImgsUrl;
    }

    Integer result = 0;

    if (boardMapper.saveBoard(board, mergedImgsUrl) > 0) {
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
    @RequestParam(required = false) List<MultipartFile> imgsFile
  ) throws Exception {

    // 변수 선언
    StringBuilder newImgsUrlBuilder = new StringBuilder();
    String existingImgsUrl = board.getBoard_imgsUrl();
    String newImgsUrl = "";
    String mergedImgsUrl = "";
    String googleFileName = "";

    // 이미지가 있을 경우 google cloud storage에 업로드
    if (imgsFile.size() > 0) {
      for (int i = 0; i < imgsFile.size(); i++) {
        MultipartFile file = imgsFile.get(i);
        byte[] bytes = file.getBytes();

        // 고유한 파일 이름 생성 (인덱스 추가)
        googleFileName = String.format(
          "board_%s_%d.webp",
          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")),
          i
        );

        // storage 객체 생성
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // blobId 생성
        BlobId blobId = BlobId.of(BUCKET_MAIN, BUCKET_FOLDER + "/board/" + googleFileName);

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
          .setContentType(file.getContentType())
          .setContentDisposition("inline; filename=\"" + googleFileName + "\"")
          .build();

        Blob blob = storage.create(blobInfo, bytes);
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        // 업로드된 파일 이름을 newImgsUrl에 추가
        if (newImgsUrlBuilder.length() > 0) {
          newImgsUrlBuilder.append(",");
        }
        newImgsUrlBuilder.append(googleFileName);
      }

      // 최종 newImgsUrl 설정
      newImgsUrl = newImgsUrlBuilder.toString().trim();
    }

    // 이미지 URL 합치기
    if (existingImgsUrl != null && existingImgsUrl.length() > 0) {
      if (newImgsUrl != null && newImgsUrl.length() > 0) {
        mergedImgsUrl = existingImgsUrl + "," + newImgsUrl;
      }
      else {
        mergedImgsUrl = existingImgsUrl;
      }
    }
    else {
      mergedImgsUrl = newImgsUrl;
    }

    Integer result = 0;

    if (boardMapper.updateBoard(board, mergedImgsUrl) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  };

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