package com.example.junghqlo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.mapper.QnaMapper;
import com.example.junghqlo.model.Qna;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class QnaServiceImpl implements QnaService {

  // 0. constructor injection ----------------------------------------------------------------------
  private QnaMapper qnaMapper;
  QnaServiceImpl(QnaMapper qnaMapper) {
  this.qnaMapper = qnaMapper;
  }

  // 1-1. listQna ---------------------------------------------------------------------------------
  @Override
  public PageHandler<Qna> listQna(Integer pageNumber, Integer itemsPer, String sort, Qna qna) throws Exception {

    List<Qna> content = qnaMapper.listQna(sort);

    Integer itemsTotal = content.size();
    Integer pageLast = (itemsTotal + itemsPer - 1) / itemsPer;

    // Ensure the pageNumber is greater than 0
    if (pageNumber <= 0) {
      pageNumber = 1;
    }

    // Ensure the pageNumber does not exceed pageLast, only if pageLast is greater than 0
    if (pageLast > 0 && pageNumber > pageLast) {
      pageNumber = pageLast;
    }

    Integer pageStart = (pageNumber - 1) * itemsPer;
    Integer pageEnd = Math.min(pageStart + itemsPer, itemsTotal);

    List<Qna> pageContent;

    // If pageStart is greater than or equal to itemsTotal, set pageContent to an empty list
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
  public Qna detailQna(Integer qna_number) throws Exception {

    return qnaMapper.detailQna(qna_number);
  }

  // 1-2. searchQna ----------------------------------------------------------------------------------
  @Override
  public PageHandler<Qna> searchQna(Integer pageNumber, Integer itemsPer, String searchType, String keyword, Qna qna) throws Exception {

    List<Qna> content = qnaMapper.searchQna(searchType, keyword);
    Integer itemsTotal = content.size();
    Integer pageLast = (itemsTotal + itemsPer - 1) / itemsPer;

    // Ensure the pageNumber is greater than 0
    if (pageNumber <= 0) {
      pageNumber = 1;
    }

    // Ensure the pageNumber does not exceed pageLast, only if pageLast is greater than 0
    if (pageLast > 0 && pageNumber > pageLast) {
      pageNumber = pageLast;
    }

    Integer pageStart = (pageNumber - 1) * itemsPer;
    Integer pageEnd = Math.min(pageStart + itemsPer, itemsTotal);

    List<Qna> pageContent;

    // If pageStart is greater than or equal to itemsTotal, set pageContent to an empty list
    if (pageStart >= itemsTotal) {
      pageContent = new ArrayList<>();
    }
    else {
      pageContent = content.subList(pageStart, pageEnd);
    }

    return new PageHandler<>(pageNumber, pageStart, pageEnd, 1, pageLast, itemsPer, itemsTotal, pageContent);
  }

  // 3. addQna -------------------------------------------------------------------------------------
  @Override
  public void addQna(Qna qna) throws Exception {

    MultipartFile qna_imgsFile = qna.getQna_imgsFile();

    String googleBucketName="jungho-bucket";
    String googleFolderPath="JUNGHQLO/DB/qna/";
    String googleFileName;
    String googleBucketUrl;

    if (qna_imgsFile.isEmpty()) {
      googleBucketUrl="https://storage.googleapis.com/jungho-bucket/JUNGHQLO/IMAGE/icon/logo.png";
      qna.setQna_imgsUrl(googleBucketUrl);
    }
    else {

      // google cloud storage bucket upload
      byte[] bytes = qna_imgsFile.getBytes();
      String originalFilename = qna_imgsFile.getOriginalFilename();

      // google cloud storage file name
      googleFileName = originalFilename + "_qna_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".png";
      Storage storage = StorageOptions.getDefaultInstance().getService();

      // create blobId
      BlobId blobId = BlobId.of(googleBucketName, googleFolderPath + googleFileName);
      BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
        .setContentType(qna_imgsFile.getContentType())
        .setContentDisposition("inline; filename=\"" + googleFileName + "\"")
        .build();
      Blob blob = storage.create(blobInfo, bytes);
      blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

      // Path to Google Cloud Storage bucket URL
      googleBucketUrl="https://storage.googleapis.com/" + googleBucketName + "/" + googleFolderPath + googleFileName;

      // Path to imgsUrl
      qna.setQna_imgsUrl(googleBucketUrl);
    }
    qnaMapper.addQna(qna);
  }

  // 4. updateQna ----------------------------------------------------------------------------------
  @Override
  public void updateQna(Qna qna, String existingImage) throws Exception {

    MultipartFile qna_imgsFile = qna.getQna_imgsFile();

    String googleFileName;
    String googleBucketUrl;
    String googleBucketName="jungho-bucket";
    String googleFolderPath="JUNGHQLO/DB/qna/";

    if (qna_imgsFile.isEmpty()) {
      qna.setQna_imgsUrl(existingImage);
    }
    else {

      // google cloud storage bucket upload
      byte[] bytes = qna_imgsFile.getBytes();
      String originalFilename = qna_imgsFile.getOriginalFilename();

      // google cloud storage file name
      googleFileName = originalFilename + "_qna_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".png";
      Storage storage = StorageOptions.getDefaultInstance().getService();

      // create blobId
      BlobId blobId = BlobId.of(googleBucketName, googleFolderPath + googleFileName);
      BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
        .setContentType(qna_imgsFile.getContentType())
        .setContentDisposition("inline; filename=\"" + googleFileName + "\"")
        .build();
      Blob blob = storage.create(blobInfo, bytes);
      blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

      // Path to Google Cloud Storage bucket URL
      googleBucketUrl="https://storage.googleapis.com/" + googleBucketName + "/" + googleFolderPath + googleFileName;

      // Path to imgsUrl
      qna.setQna_imgsUrl(googleBucketUrl);
    }
    qnaMapper.updateQna(qna);
  }

  // 4-1. updateQnaCount ---------------------------------------------------------------------------
  @Override
  public void updateQnaCount(Integer qna_number, HttpSession session) throws Exception {

    // 최초로 조회수를 올린 경우
    if (session.getAttribute("member_id") != null) {
      long update_time = 0;

      // 최근에 조회수를 올린 시간 검색
      String qnaViewKey
     ="update_qna_view" + qna_number + "_" + session.getAttribute("member_id");

      if (session.getAttribute(qnaViewKey) != null) {
        update_time = (long)session.getAttribute(qnaViewKey);
      }
      long current_time = System.currentTimeMillis();

      // 일정 시간이 경과한 후 조회수 증가 처리 (하루에 한번만 조회수 증가)
      if (current_time - update_time > 24 * 60 * 60 * 1000) {
        qnaMapper.updateQnaCount(qna_number);

        // 조회수 증가 처리 시간 저장
        session.setAttribute(qnaViewKey, current_time);
      }
    }
  }

  // 4-2. updateLike -------------------------------------------------------------------------------
  @Override
  public void updateLike(Integer qna_number, HttpSession session) throws Exception {

    // 최초로 좋아요를 누른 경우
    if (session.getAttribute("member_id") != null) {
      long update_time = 0;

      // 최근에 좋아요를 누른 시간 검색
      String qnaLikeKey
     ="update_qna_like" + qna_number + "_" + session.getAttribute("member_id");
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

  // 4-3. updateDislike ----------------------------------------------------------------------------
  @Override
  public void updateDislike(Integer qna_number, HttpSession session) throws Exception {

    // 최초로 싫어요를 누른 경우
    if (session.getAttribute("member_id") != null) {
      long update_time = 0;

      // 최근에 싫어요를 누른 시간 검색
      String qnaDislikeKey
     ="update_qna_dislike" + qna_number + "_" + session.getAttribute("member_id");

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
  public Integer deleteQna(Integer qna_number) throws Exception {

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