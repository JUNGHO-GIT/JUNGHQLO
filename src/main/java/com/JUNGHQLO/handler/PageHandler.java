package com.JUNGHQLO.handler;

import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class PageHandler<T> {

  // fields ----------------------------------------------------------------------------------------
  private Integer pageNumber;
  private Integer pageStart;
  private Integer pageEnd;
  private Integer pageFirst;
  private Integer pageLast;
  private Integer itemsPer;
  private Integer itemsTotal;
  private List<T> content;

  // getter ----------------------------------------------------------------------------------------
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

  // setter ----------------------------------------------------------------------------------------
  public void setTotalPages(Integer totalPages) {
    this.pageLast = totalPages;
  }
  public void setHasNext(boolean hasNext) {
    if (hasNext) {
      this.pageNumber++;
    }
  }
  public void setHasPrev(boolean hasPrev) {
    if (hasPrev) {
      this.pageNumber--;
    }
  }
  public void setHasContent(boolean hasContent) {
    if (!hasContent) {
      this.content = null;
    }
  }
}