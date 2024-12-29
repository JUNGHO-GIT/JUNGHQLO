package com.JUNGHQLO.service;

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
import com.JUNGHQLO.handler.PageHandler;
import com.JUNGHQLO.mapper.QnaMapper;
import com.JUNGHQLO.model.Qna;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class QnaServiceImpl implements QnaService {

  @Value("${bucket-main}")
  private String BUCKET_MAIN;

  @Value("${bucket-folder}")
  private String BUCKET_FOLDER;

  // 0. constructor injection ----------------------------------------------------------------------
  private QnaMapper qnaMapper;
  QnaServiceImpl(QnaMapper qnaMapper) {
    this.qnaMapper = qnaMapper;
  }

  // 1. listQna ------------------------------------------------------------------------------------
  @Override
  public PageHandler<Qna> listQna(
    Integer pageNumber,
    Integer itemsPer,
    String sort,
    String category,
    String type,
    String keyword,
    Qna qna
  ) throws Exception {

    List<Qna> content = qnaMapper.listQna(sort, category, type, keyword);

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

    List<Qna> pageContent;

    if (pageStart >= itemsTotal) {
      pageContent = new ArrayList<>();
    }
    else {
      pageContent = content.subList(pageStart, pageEnd);
    }

    return new PageHandler<>(pageNumber, pageStart, pageEnd, 1, pageLast, itemsPer, itemsTotal, pageContent);
  }

  // 2. detailQna ------------------------------------------------------------------------------
  @Override
  public Qna detailQna(
    Integer qna_number
  ) throws Exception {

    return qnaMapper.detailQna(qna_number);
  }


  // 3. saveQna ---------------------------------------------------------------------------------
  @Override
  public Integer saveQna(
    @ModelAttribute Qna qna,
    @RequestParam(required = false) List<MultipartFile> imgsFile
  ) throws Exception {

    // 변수 선언
    StringBuilder newImgsUrlBuilder = new StringBuilder();
    String existingImgsUrl = qna.getQna_imgsUrl();
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
          "qna_%s_%d.webp",
          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")),
          i
        );

        // storage 객체 생성
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // blobId 생성
        BlobId blobId = BlobId.of(BUCKET_MAIN, BUCKET_FOLDER + "/qna/" + googleFileName);

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

    if (qnaMapper.saveQna(qna, mergedImgsUrl) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  };

  // 4-1. updateQna -----------------------------------------------------------------------------
  @Override
  public Integer updateQna(
    @ModelAttribute Qna qna,
    @RequestParam(required = false) List<MultipartFile> imgsFile
  ) throws Exception {

    // 변수 선언
    StringBuilder newImgsUrlBuilder = new StringBuilder();
    String existingImgsUrl = qna.getQna_imgsUrl();
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
          "qna_%s_%d.webp",
          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")),
          i
        );

        // storage 객체 생성
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // blobId 생성
        BlobId blobId = BlobId.of(BUCKET_MAIN, BUCKET_FOLDER + "/qna/" + googleFileName);

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

    if (qnaMapper.updateQna(qna, mergedImgsUrl) > 0) {
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
    Integer qna_number,
    HttpSession session
  ) throws Exception {

    // 최초로 조회수를 올린 경우
    if (session.getAttribute("member_id") != null) {
      long update_time = 0;

      // 최근에 조회수를 올린 시간 검색
      String qnaViewKey = (
        "update_qna_view" + qna_number + "_" + session.getAttribute("member_id")
      );

      if (session.getAttribute(qnaViewKey) != null) {
        update_time = (long)session.getAttribute(qnaViewKey);
      }
      long current_time = System.currentTimeMillis();

      // 일정 시간이 경과한 후 조회수 증가 처리 (하루에 한번만 조회수 증가)
      if (current_time - update_time > 24 * 60 * 60 * 1000) {
        qnaMapper.updateCount(qna_number);

        // 조회수 증가 처리 시간 저장
        session.setAttribute(qnaViewKey, current_time);
      }
    }
  }

  // 4-3. updateLike -------------------------------------------------------------------------------
  @Override
  public void updateLike(
    Integer qna_number,
    HttpSession session
  ) throws Exception {

    // 최초로 좋아요를 누른 경우
    if (session.getAttribute("member_id") != null) {
      long update_time = 0;

      // 최근에 좋아요를 누른 시간 검색
      String qnaLikeKey = (
        "update_qna_like" + qna_number + "_" + session.getAttribute("member_id")
      );

      if (session.getAttribute(qnaLikeKey) != null) {
        update_time = (long) session.getAttribute(qnaLikeKey);
      }
      long current_time = System.currentTimeMillis();

      // 일정 시간이 경과한 후 좋아요 증가 처리 (하루에 한번만 좋아요 증가)
      if (current_time - update_time > 24 * 60 * 60 * 1000) {
        qnaMapper.updateLike(qna_number);

        // 좋아요 증가 처리 시간 저장
        session.setAttribute(qnaLikeKey, current_time);
      }
    }
  }

  // 4-4. updateDislike ----------------------------------------------------------------------------
  @Override
  public void updateDislike(
    Integer qna_number,
    HttpSession session
  ) throws Exception {

    // 최초로 싫어요를 누른 경우
    if (session.getAttribute("member_id") != null) {
      long update_time = 0;

      // 최근에 싫어요를 누른 시간 검색
      String qnaDislikeKey = (
        "update_qna_dislike" + qna_number + "_" + session.getAttribute("member_id")
      );

      if (session.getAttribute(qnaDislikeKey) != null) {
        update_time = (long) session.getAttribute(qnaDislikeKey);
      }
      long current_time = System.currentTimeMillis();

      // 일정 시간이 경과한 후 싫어요 증가 처리 (하루에 한번만 싫어요 증가)
      if (current_time - update_time > 24 * 60 * 60 * 1000) {
        qnaMapper.updateDislike(qna_number);

        // 싫어요 증가 처리 시간 저장
        session.setAttribute(qnaDislikeKey, current_time);
      }
    }
  }

  // 5. deleteQna ----------------------------------------------------------------------------------
  @Override
  public Integer deleteQna(
    Integer qna_number
  ) throws Exception {

    Integer result = 0;

    if (qnaMapper.deleteQna(qna_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}