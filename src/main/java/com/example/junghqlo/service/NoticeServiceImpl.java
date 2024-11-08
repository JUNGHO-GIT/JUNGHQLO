package com.example.junghqlo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.mapper.NoticeMapper;
import com.example.junghqlo.model.Notice;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class NoticeServiceImpl implements NoticeService {

  // 0. constructor injection ----------------------------------------------------------------------
  private NoticeMapper noticeMapper;
  NoticeServiceImpl (NoticeMapper noticeMapper) {
    this.noticeMapper = noticeMapper;
  }

  // 1. listNotice ---------------------------------------------------------------------------------
  @Override
  public PageHandler<Notice> listNotice(
    Integer pageNumber,
    Integer itemsPer,
    String sort,
    String type,
    String keyword,
    Notice notice
  ) throws Exception {

    List<Notice> content = noticeMapper.listNotice(sort, type, keyword);
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

    List<Notice> pageContent;

    if (pageStart >= itemsTotal) {
      pageContent = new ArrayList<>();
    }
    else {
      pageContent = content.subList(pageStart, pageEnd);
    }
    return new PageHandler<>(pageNumber, pageStart, pageEnd, 1, pageLast, itemsPer, itemsTotal, pageContent);
  }

  // 2. detailNotice -------------------------------------------------------------------------------
  @Override
  public Notice detailNotice(
    Integer notice_number
  ) throws Exception {

    return noticeMapper.detailNotice(notice_number);
  }

  // 3. addNotice ----------------------------------------------------------------------------------
  @Override
  public void addNotice(
    @ModelAttribute Notice notice
  ) throws Exception {

    MultipartFile notice_imgsFile = notice.getNotice_imgsFile();
    String googleFileName;
    String googleBucketUrl;
    String googleBucketName="jungho-bucket";
    String googleFolderPath="JUNGHQLO/DB/notices/";

    if (notice_imgsFile.isEmpty()) {
      googleBucketUrl="https://storage.googleapis.com/jungho-bucket/JUNGHQLO/IMAGE/icon/logo.png";
      notice.setNotice_imgsUrl(googleBucketUrl);
    }
    else {

      // google cloud storage upload
      byte[] bytes = notice_imgsFile.getBytes();

      // google cloud storage file name
      googleFileName="_notice_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".png";
      Storage storage = StorageOptions.getDefaultInstance().getService();

      // Create the blobId
      BlobId blobId = BlobId.of(googleBucketName, googleFolderPath + googleFileName);
      BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
        .setContentType(notice_imgsFile.getContentType())
        .setContentDisposition("inline; filename=\"" + googleFileName + "\"")
        .build();
      Blob blob = storage.create(blobInfo, bytes);
      blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

      // Path to Google Cloud Storage bucket URL
      googleBucketUrl="https://storage.googleapis.com/" + googleBucketName + "/" + googleFolderPath + googleFileName;

      // Path to imgsUrl
      notice.setNotice_imgsUrl(googleBucketUrl);
    }
    noticeMapper.addNotice(notice);
  }

  // 4-1. updateNotice -----------------------------------------------------------------------------
  @Override
  public void updateNotice(
    @ModelAttribute Notice notice,
    String existingImage
  ) throws Exception {

    MultipartFile notice_imgsFile = notice.getNotice_imgsFile();

    String googleFileName;
    String googleBucketUrl;
    String googleBucketName="jungho-bucket";
    String googleFolderPath="JUNGHQLO/DB/notice/";

    if (notice_imgsFile.isEmpty()) {
      notice.setNotice_imgsUrl(existingImage);
    }
    else {

      // google cloud storage upload
      byte[] bytes = notice_imgsFile.getBytes();

      // google cloud storage file name
      googleFileName="_notice_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".png";
      Storage storage = StorageOptions.getDefaultInstance().getService();

      // Create the blobId
      BlobId blobId = BlobId.of(googleBucketName, googleFolderPath + googleFileName);
      BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
        .setContentType(notice_imgsFile.getContentType())
        .setContentDisposition("inline; filename=\"" + googleFileName + "\"")
        .build();
      Blob blob = storage.create(blobInfo, bytes);
      blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

      // Path to Google Cloud Storage bucket URL
      googleBucketUrl="https://storage.googleapis.com/" + googleBucketName + "/" + googleFolderPath + googleFileName;

      // Path to imgsUrl
      notice.setNotice_imgsUrl(googleBucketUrl);
    }
    noticeMapper.updateNotice(notice);
  }

  // 4-2. updateCount ------------------------------------------------------------------------------
  @Override
  public void updateCount(
    Integer notice_number,
    HttpSession session
  ) throws Exception {

    // 최초로 조회수를 올린 경우
    if (session.getAttribute("member_id") != null) {
      long update_time = 0;

      // 최근에 조회수를 올린 시간 검색
      String noticeViewKey = (
        "update_notice_view" + notice_number + "_" + session.getAttribute("member_id")
      );

      if (session.getAttribute(noticeViewKey) != null) {
        update_time = (long)session.getAttribute(noticeViewKey);
      }
      long current_time = System.currentTimeMillis();

      // 일정 시간이 경과한 후 조회수 증가 처리 (하루에 한번만 조회수 증가)
      if (current_time - update_time > 24 * 60 * 60 * 1000) {
        noticeMapper.updateCount(notice_number);

        // 조회수 증가 처리 시간 저장
        session.setAttribute(noticeViewKey, current_time);
      }
    }
  }

  // 4-3. updateLike -------------------------------------------------------------------------------
  @Override
  public void updateLike(
    Integer notice_number,
    HttpSession session
  ) throws Exception {

    // 최초로 좋아요를 누른 경우
    if (session.getAttribute("member_id") != null) {
      long update_time = 0;

      // 최근에 좋아요를 누른 시간 검색
      String noticeLikeKey = (
        "update_notice_like" + notice_number + "_" + session.getAttribute("member_id")
      );

      if (session.getAttribute(noticeLikeKey) != null) {
        update_time = (long) session.getAttribute(noticeLikeKey);
      }
      long current_time = System.currentTimeMillis();

      // 일정 시간이 경과한 후 좋아요 증가 처리 (하루에 한번만 좋아요 증가)
      if (current_time - update_time > 24 * 60 * 60 * 1000) {
        noticeMapper.updateLike(notice_number);

        // 좋아요 증가 처리 시간 저장
        session.setAttribute(noticeLikeKey, current_time);
      }
    }
  }

  // 4-4. updateDislike ----------------------------------------------------------------------------
  @Override
  public void updateDislike(
    Integer notice_number,
    HttpSession session
  ) throws Exception {

    // 최초로 싫어요를 누른 경우
    if (session.getAttribute("member_id") != null) {
      long update_time = 0;

      // 최근에 싫어요를 누른 시간 검색
      String noticeDislikeKey = (
        "update_notice_dislike" + notice_number + "_" + session.getAttribute("member_id")
      );

      if (session.getAttribute(noticeDislikeKey) != null) {
        update_time = (long) session.getAttribute(noticeDislikeKey);
      }
      long current_time = System.currentTimeMillis();

      // 일정 시간이 경과한 후 싫어요 증가 처리 (하루에 한번만 싫어요 증가)
      if (current_time - update_time > 24 * 60 * 60 * 1000) {
        noticeMapper.updateDislike(notice_number);

        // 싫어요 증가 처리 시간 저장
        session.setAttribute(noticeDislikeKey, current_time);
      }
    }
  }

  // 5. deleteNotice -------------------------------------------------------------------------------
  @Override
  public Integer deleteNotice(
    Integer notice_number
  ) throws Exception {

    Integer result = 0;

    if (noticeMapper.deleteNotice(notice_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  };
};