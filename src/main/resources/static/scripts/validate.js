/** ------------------------------------------------------------------------------------------------
* @param null
* @return {boolean}
* @desc 로그인 회원 알림
**/
function sessionAlert() {
  alert("로그인한 회원만 가능합니다.")
  location.href = `/${title}/member/loginMember`;
  return false;
}

/** ------------------------------------------------------------------------------------------------
* @param null
* @return {boolean}
* @desc 관리자 알림
**/
function adminAlert() {
  alert("관리자만 가능합니다.")
  location.href = `/${title}/member/loginMember`;
  return false;
}

/** ------------------------------------------------------------------------------------------------
* @param {string} page
* @return {boolean}
* @desc board 입력 유효성 검사
**/
function validateBoard (page) {
  function validate (page) {
    if (page) {
      if (!sessionId) {
        alert("로그인이 필요합니다.");
        goToPage(`/${title}/member/loginMember`);
        return false;
      }
    }
    if (page === "save" || page === "update") {
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
    }
    return true;
  };
  function confirm (page) {
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
    else if (page === "delete") {
      if (!confirm("삭제하시겠습니까?")) {
        return false;
      }
    }
    return true;
  }

  return validate(page) && confirm(page);
}

/** ------------------------------------------------------------------------------------------------
* @param {string} page
* @return {boolean}
* @desc notice 입력 유효성 검사
**/
function validateNotice(page) {
  function validate (page) {
    if (page) {
      if (!sessionId) {
        alert("로그인이 필요합니다.");
        goToPage(`/${title}/member/loginMember`);
        return false;
      }
    }
    if (page === "save" || page === "update") {
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
    }
    return true;
  }
  function confirm (page) {
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
    else if (page === "delete") {
      if (!confirm("삭제하시겠습니까?")) {
        return false;
      }
    }
    return true;
  }

  return validate(page) && confirm(page);
};

/** ------------------------------------------------------------------------------------------------
* @param {string} page
* @return {boolean}
* @desc qna 입력 유효성 검사
**/
function validateQna (page) {
  function validate (page) {
    if (page) {
      if (!sessionId) {
        alert("로그인이 필요합니다.");
        goToPage(`/${title}/member/loginMember`);
        return false;
      }
    }
    if (page === "save" || page === "update") {
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
    }
    return true;
  }
  function confirm (page) {
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
    else if (page === "delete") {
      if (!confirm("삭제하시겠습니까?")) {
        return false;
      }
    }
    return true;
  }

  return validate(page) && confirm(page);
};

/** ----------------------------------------------------------------------------------------------->
* @param {string} page
* @return {boolean}
* @desc product 입력 유효성 검사
**/
function validateProduct(page) {
  function validate (page) {
    if (page) {
      if (!sessionId) {
        alert("로그인이 필요합니다.");
        goToPage(`/${title}/member/loginMember`);
        return false;
      }
    }
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
      if (getById("inputContainer").hasChildNodes() === false) {
        alert("상품 이미지를 선택하세요");
        return false;
      }
    }
    return true;
  }
  function confirm (page) {
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
    else if (page === "delete") {
      if (!confirm("삭제하시겠습니까?")) {
        return false;
      }
    }
    return true;
  }

  return validate(page) && confirm(page);
};

/** ------------------------------------------------------------------------------------------------
* @param {string} page
* @return {boolean}
* @desc orders 입력 유효성 검사
**/
function validateOrders(page) {
  function validate (page) {
    if (page) {
      if (!sessionId) {
        alert("로그인이 필요합니다.");
        goToPage(`/${title}/member/loginMember`);
        return false;
      }
    }
    if (page === "save" || page === "update") {
      if (!getValue(getById("orders_quantity"))) {
        alert("주문 수량을 입력하세요");
        getById("orders_quantity")?.focus();
        return false;
      }
    }
    return true;
  }
  function confirm (page) {
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
    else if (page === "delete") {
      if (!confirm("삭제하시겠습니까?")) {
        return false;
      }
    }
    return true;
  }

  return validate(page) && confirm(page);
};

/** ----------------------------------------------------------------------------------------------->
* @param {string} page
* @return {boolean}
* @desc 로그인 아이디 비밀번호 체크
**/
function validateMember (page) {
  function validate (page) {
    // 1. login
    if (page === "login") {
      if (getValue(getById("member_id")) === "") {
        alert("아이디를 입력하세요");
        getById("member_id")?.focus();
        return false;
      }
      if (getValue(getById("member_pw")) === "") {
        alert("비밀번호를 입력하세요");
        getById("member_pw")?.focus();
        return false;
      }
    }
    // 2. signup
    else if (page === "signup") {
      if (getValue(getById("member_id")) === "") {
        alert("아이디를 입력하세요");
        getById("member_id")?.focus();
        return false;
      }
      if (
        getValue(getById("member_id")).length < 4 ||
        getValue(getById("member_id")).length > 12 ||
        !getValue(getById("member_id")).toString().match(/^[a-zA-Z0-9]+$/)
      ) {
        alert("아이디는 4~12자리의 영문 대소문자와 숫자를 포함해야 합니다");
        getById("member_id")?.focus();
        return false;
      }
      if (getValue(getById("member_pw")) === "") {
        alert("비밀번호를 입력하세요");
        getById("member_pw")?.focus();
        return false;
      }
      if (!getValue(getById("member_pw")).toString().match(/^[a-zA-Z0-9]{8,12}$/)) {
        alert("비밀번호는 8~12자리의 영문 대소문자와 숫자만 입력 가능합니다");
        getById("member_pw")?.focus();
        return false;
      }
      if (getValue(getById("member_name")) === "") {
        alert("이름을 입력하세요");
        getById("member_name")?.focus();
        return false;
      }
      if (getValue(getById("member_phone")) === "") {
        alert("전화번호를 입력하세요");
        getById("member_phone")?.focus();
        return false;
      }
      if (getValue(getById("member_email")) === "") {
        alert("이메일을 입력하세요");
        getById("member_email")?.focus();
        return false;
      }
      if (getValue(getById("email_verified")) !== "true") {
        alert("이메일 인증을 해주세요");
        return false;
      }
      if (getValue(getById("member_address1")) === "") {
        alert("주소를 입력하세요");
        getById("member_address1")?.focus();
        return false;
      }
      if (getValue(getById("member_address2")) === "") {
        alert("상세주소를 입력하세요");
        getById("member_address2")?.focus();
        return false;
      }
      if (!getValue(getById("member_agree"))) {
        alert("약관에 동의해주세요");
        getById("member_agree")?.focus();
        return false;
      }
    }
    // 3. update
    else if (page === "update") {
      if (getValue(getById("member_name")) === "") {
        alert("이름을 입력하세요");
        getById("member_name")?.focus();
        return false;
      }
      if (getValue(getById("member_phone")) === "") {
        alert("전화번호를 입력하세요");
        getById("member_phone")?.focus();
        return false;
      }
      if (getValue(getById("member_email")) === "") {
        alert("이메일을 입력하세요");
        getById("member_email")?.focus();
        return false;
      }
      if (getValue(getById("member_address1")) === "") {
        alert("주소를 입력하세요");
        getById("member_address1")?.focus();
        return false;
      }
      if (getValue(getById("member_address2")) === "") {
        alert("상세주소를 입력하세요");
        getById("member_address2")?.focus();
        return false;
      }
    }
    // 3. updatePw
    else if (page === "updatePw") {
      if (getValue(getById("member_pw")) === "") {
        alert("비밀번호를 입력하세요");
        getById("member_pw")?.focus();
        return false;
      }
      if (getValue(getById("member_pw2")) === "") {
        alert("비밀번호를 다시 입력하세요");
        getById("member_pw2")?.focus();
        return false;
      }
      if (getValue(getById("member_pw")) !== getValue(getById("member_pw2"))) {
        alert("비밀번호 확인이 일치하지 않습니다.");
        setValue(getById("member_pw2"), "");
        getById("member_pw2")?.focus();
        return false;
      }
    }
    // 4. findId
    else if (page === "findId") {
      if (getValue(getById("member_name")) === "") {
        alert("이름을 입력하세요");
        getById("member_name")?.focus();
        return false;
      }
      if (getValue(getById("member_email")) === "") {
        alert("이메일을 입력하세요");
        getById("member_email")?.focus();
        return false;
      }
    }
    // 4. findPw
    else if (page === "findPw") {
      if (getValue(getById("member_id")) === "") {
        alert("아이디를 입력하세요");
        getById("member_id")?.focus();
        return false;
      }
      if (getValue(getById("member_name")) === "") {
        alert("이름을 입력하세요");
        getById("member_name")?.focus();
        return false;
      }
      if (getValue(getById("member_email")) === "") {
        alert("이메일을 입력하세요");
        getById("member_email")?.focus();
        return false;
      }
    }
    // 5. delete
    else if (page === "delete") {
      if (getValue(getById("member_pw")) === "") {
        alert("비밀번호를 입력하세요");
        getById("member_pw")?.focus();
        return false;
      }
      if (getValue(getById("member_pw2")) === "") {
        alert("비밀번호를 다시 입력하세요");
        getById("member_pw2")?.focus();
        return false;
      }
      if (getValue(getById("member_pw")) !== getValue(getById("member_pw2"))) {
        alert("비밀번호 확인이 일치하지 않습니다.");
        setValue(getById("member_pw2"), "");
        getById("member_pw2")?.focus();
        return false;
      }
    }
    return true;
  }
  function confirm (page) {
    if (page === "update") {
      if (!confirm("수정하시겠습니까?")) {
        return false;
      }
    }
    else if (page === "updatePw") {
      if (!confirm("비밀번호를 변경하시겠습니까?")) {
        return false;
      }
    }
    else if (page === "delete") {
      if (!confirm("회원 탈퇴를 하시겠습니까?")) {
        return false;
      }
    }
    return true;
  }

  return validate(page) && confirm(page);
};