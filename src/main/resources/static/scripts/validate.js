/** ------------------------------------------------------------------------------------------------
* @param {string} page
* @return {boolean}
* @desc board 입력 유효성 검사
**/
function validateBoard(page) {
  if (page === "delete") {
    if (!confirm("삭제하시겠습니까?")) {
      return false;
    }
  }
  else if (page === "save" || page === "update") {
    if (!getValue(getById("board_title"))) {
      alert("제목을 입력하세요");
      getById("board_title")?.focus();
      return false;
    }
    if (!getValue(getById("board_contents"))) {
      alert("내용을 입력하세요");
      getById("board_contents")?.focus();
      return false;
    }
    if (page === "save") {
      if (!confirm("등록하시겠습니까?")) {
        return false;
      }
    }
    else if (page === "update") {
      if (!confirm("수정하시겠습니까?")) {
        return false;
      }
    }
  }
  return true;
};

/** ------------------------------------------------------------------------------------------------
* @param {string} page
* @return {boolean}
* @desc notice 입력 유효성 검사
**/
function validateNotice(page) {
  if (page === "delete") {
    if (!confirm("삭제하시겠습니까?")) {
      return false;
    }
  }
  else if (page === "save" || page === "update") {
    if (!getValue(getById("notice_title"))) {
      alert("제목을 입력하세요");
      getById("notice_title")?.focus();
      return false;
    }
    if (!getValue(getById("notice_contents"))) {
      alert("내용을 입력하세요");
      getById("notice_contents")?.focus();
      return false;
    }
    if (page === "save") {
      if (!confirm("등록하시겠습니까?")) {
        return false;
      }
    }
    else if (page === "update") {
      if (!confirm("수정하시겠습니까?")) {
        return false;
      }
    }
  }
  return true;
};

/** ------------------------------------------------------------------------------------------------
* @param {string} page
* @return {boolean}
* @desc qna 입력 유효성 검사
**/
function validateQna(page) {
  if (page === "delete") {
    if (!confirm("삭제하시겠습니까?")) {
      return false;
    }
  }
  else if (page === "save" || page === "update") {
    if (!getValue(getById("qna_title"))) {
      alert("제목을 입력하세요");
      getById("qna_title")?.focus();
      return false;
    }
    if (!getValue(getById("qna_contents"))) {
      alert("내용을 입력하세요");
      getById("qna_contents")?.focus();
      return false;
    }
    if (!getValue(getById("qna_category"))) {
      alert("카테고리를 선택하세요");
      getById("qna_category")?.focus();
      return false;
    }
    if (!confirm("등록하시겠습니까?")) {
      return false;
    }
    if (page === "save") {
      if (!confirm("등록하시겠습니까?")) {
        return false;
      }
    }
    else if (page === "update") {
      if (!confirm("수정하시겠습니까?")) {
        return false;
      }
    }
  }
  return true;
};

/** ------------------------------------------------------------------------------------------------
* @param {string} page
* @return {boolean}
* @desc contact 입력 유효성 검사
**/
function validateContact(page) {
  if (page === "delete") {
    if (!confirm("삭제하시겠습니까?")) {
      return false;
    }
  }
  else if (page === "save" || page === "update") {
    if (!getValue(getById("contact_name"))) {
      alert("이름을 입력해주세요.");
      document?.getElementById("contact_name")?.focus();
      return false;
    }
    if (!getValue(getById("contact_tel"))) {
      alert("연락처를 입력해주세요.");
      document?.getElementById("contact_tel")?.focus();
      return false;
    }
    if (!getValue(getById("contact_email"))) {
      alert("이메일을 입력해주세요.");
      document?.getElementById("contact_email")?.focus();
      return false;
    }
  }
  alert("문의가 접수되었습니다.");
  location.reload();
  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param {string} page
* @return {boolean}
* @desc product 입력 유효성 검사
**/
function validateProduct(page) {
  if (page === "save" || page === "update") {
    if (!getValue(getById("product_name"))) {
      alert("상품명을 입력하세요");
      getById("product_name")?.focus();
      return false;
    }
    else if (getValue(getById("product_name")).length > 10) {
      alert("상품명은 10글자 이하로 입력하세요");
      getById("product_name")?.focus();
      return false;
    }
    if (!getValue(getById("product_detail"))) {
      alert("상품 상세 설명을 입력하세요");
      getById("product_detail")?.focus();
      return false;
    }
    else if (getValue(getById("product_detail")).length > 100) {
      alert("상품 상세 설명은 100글자 이하로 입력하세요");
      getById("product_detail")?.focus();
      return false;
    }
    if (!getValue(getById("product_price"))) {
      alert("상품 가격을 입력하세요");
      getById("product_price")?.focus();
      return false;
    }
    else if (!isNumeric(getValue(getById("product_price")))) {
      alert("상품 가격은 숫자만 입력하세요");
      getById("product_price")?.focus();
      return false;
    }
    if (!getValue(getById("product_stock"))) {
      alert("상품 재고를 입력하세요");
      getById("product_stock")?.focus();
      return false;
    }
    else if (!isNumeric(getValue(getById("product_stock")))) {
      alert("상품 재고는 숫자만 입력하세요");
      getById("product_stock")?.focus();
      return false;
    }
    if (!getValue(getById("product_brand"))) {
      alert("상품 제조사를 입력하세요");
      getById("product_brand")?.focus();
      return false;
    }
    else if (getValue(getById("product_brand")).length > 10) {
      alert("상품 제조사는 10글자 이하로 입력하세요");
      getById("product_brand")?.focus();
      return false;
    }
    if (!getValue(getById("product_category"))) {
      alert("상품 카테고리를 선택하세요");
      getById("product_category")?.focus();
      return false;
    }
    else if (getValue(getById("product_category")) === "선택") {
      alert("상품 카테고리를 선택하세요");
      getById("product_category")?.focus();
      return false;
    }
    if (!getValue(getById("product_origin"))) {
      alert("상품 원산지를 선택하세요");
      getById("product_origin")?.focus();
      return false;
    }
    else if (getValue(getById("product_origin")) === "선택") {
      alert("상품 원산지를 선택하세요");
      getById("product_origin")?.focus();
      return false;
    }
    if (!getValue(getById("imgsFile1"))) {
      alert("상품 이미지를 선택하세요");
      getById("imgsFile1")?.focus();
      return false;
    }
    if (!getValue(getById("imgsFile2"))) {
      alert("상품 이미지를 선택하세요");
      getById("imgsFile2")?.focus();
      return false;
    }
    if (page === "save") {
      if (!confirm("등록하시겠습니까?")) {
        return false;
      }
    }
    else if (page === "update") {
      if (!confirm("수정하시겠습니까?")) {
        return false;
      }
    }
  }
  else if (page === "delete") {
    if (!confirm("삭제하시겠습니까?")) {
      return false;
    }
  }
  return true;
};

/** ------------------------------------------------------------------------------------------------
* @param {string} page
* @return {boolean}
* @desc orders 입력 유효성 검사
**/
function validateOrders(page) {
  if (page === "delete") {
    if (!confirm("삭제하시겠습니까?")) {
      return false;
    }
  }
  else if (page === "save" || page === "update") {
    if (!getValue(getById("orders_quantity"))) {
      alert("주문 수량을 입력하세요");
      getById("orders_quantity")?.focus();
      return false;
    }
    if (page === "save") {
      if (!confirm("주문하시겠습니까?")) {
        return false;
      }
    }
    else if (page === "update") {
      if (!confirm("수정하시겠습니까?")) {
        return false;
      }
    }
  }

  return true;
}