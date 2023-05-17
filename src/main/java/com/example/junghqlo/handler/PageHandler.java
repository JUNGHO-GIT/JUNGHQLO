package com.example.junghqlo.handler;

import java.util.List;
import lombok.Data;

@Data
public class PageHandler<T> {

  // fields --------------------------------------------------------------------------------------->
  private Integer pageNumber;
  private Integer pageStart;
  private Integer pageEnd;
  private Integer pageFirst;
  private Integer pageLast;
  private Integer itemsPer;
  private Integer itemsTotal;
  private List<T> content;

  // constructor ---------------------------------------------------------------------------------->
  public PageHandler(Integer pageNumber, Integer pageStart, Integer pageEnd, Integer pageFirst, Integer pageLast,Integer itemsPer, Integer itemsTotal, List<T> content) {
  this.pageNumber = pageNumber;
    this.pageStart = pageStart;
    this.pageEnd = pageEnd;
    this.pageFirst = pageFirst;
    this.pageLast = pageLast;
    this.itemsPer = itemsPer;
    this.itemsTotal = itemsTotal;
    this.content = content;
  }

  // getter --------------------------------------------------------------------------------------->
  public Integer getPageNumber() {
    return pageNumber;
  }
  public Integer getPageStart() {
    return pageStart;
  }
  public Integer getPageEnd() {
    return pageEnd;
  }
  public Integer getPageFirst() {
    return pageFirst;
  }
  public Integer getItemsPer() {
    return itemsPer;
  }
  public Integer getTotalItems() {
    return itemsTotal;
  }
  public List<T> getContent() {
    return content;
  }
  public Integer getTotalPages() {
    return pageLast;
  }
  public boolean hasNext() {
    return pageNumber < pageLast;
  }
  public boolean hasPrev() {
    return pageNumber > pageFirst;
  }
  public boolean hasContent() {
    return !content.isEmpty();
  }

  // setter --------------------------------------------------------------------------------------->
  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }
  public void setPageStart(Integer pageStart) {
    this.pageStart = pageStart;
  }
  public void setPageEnd(Integer pageEnd) {
    this.pageEnd = pageEnd;
  }
  public void setPageFirst(Integer pageFirst) {
    this.pageFirst = pageFirst;
  }
  public void setPageLast(Integer pageLast) {
    this.pageLast = pageLast;
  }
  public void setItemsPer(Integer itemsPer) {
    this.itemsPer = itemsPer;
  }
  public void setItemsTotal(Integer itemsTotal) {
    this.itemsTotal = itemsTotal;
  }
  public void setContent(List<T> content) {
    this.content = content;
  }

}