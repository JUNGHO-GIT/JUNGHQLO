/** ----------------------------------------------------------------------------------------------->
* @param {string} value
* @return {boolean}
* @desc 숫자 확인
**/
function isNumeric(value) {
  return /^\d+$/.test(value);
};

/** ----------------------------------------------------------------------------------------------->
* @param {string} value
* @return {boolean}
* @desc 가격 확인
**/
function isPrice(value) {
  return /^\d{4,}$/.test(value);
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 상품 입력 유효성 검사
**/
function productCheck() {
  // 상품명은 10글자 이하로 입력
  if (!getValue(getById("product_name"))) {
    alert("상품명을 입력하세요");
    getById("product_name").focus();
    return false;
  }
  else if (getValue(getById("product_name")).length > 10) {
    alert("상품명은 10글자 이하로 입력하세요");
    getById("product_name").focus();
    return false;
  }

  // 상품 상세 설명은 100글자 이하로 입력
  if (!getValue(getById("product_detail"))) {
    alert("상품 상세 설명을 입력하세요");
    getById("product_detail").focus();
    return false;
  }
  else if (getValue(getById("product_detail")).length > 100) {
    alert("상품 상세 설명은 100글자 이하로 입력하세요");
    getById("product_detail").focus();
    return false;
  }

  // 상품 가격은 1,000원 이상 입력
  if (!getValue(getById("product_price"))) {
    alert("상품 가격을 입력하세요");
    getById("product_price").focus();
    return false;
  }
  else if (!isNumeric(getValue(getById("product_price")))) {
    alert("상품 가격은 숫자만 입력하세요");
    getById("product_price").focus();
    return false;
  }

  // 상품 재고는 0 이상 입력
  if (!getValue(getById("product_stock"))) {
    alert("상품 재고를 입력하세요");
    getById("product_stock").focus();
    return false;
  }
  else if (!isNumeric(getValue(getById("product_stock")))) {
    alert("상품 재고는 숫자만 입력하세요");
    getById("product_stock").focus();
    return false;
  }

  // 상품 제조사는 10글자 이하로 입력
  if (!getValue(getById("product_company"))) {
    alert("상품 제조사를 입력하세요");
    getById("product_company").focus();
    return false;
  }
  else if (getValue(getById("product_company")).length > 10) {
    alert("상품 제조사는 10글자 이하로 입력하세요");
    getById("product_company").focus();
    return false;
  }

  // 상품 카테고리
  if (!getValue(getById("product_category"))) {
    alert("상품 카테고리를 선택하세요");
    getById("product_category").focus();
    return false;
  }
  else if (getValue(getById("product_category")) == "선택") {
    alert("상품 카테고리를 선택하세요");
    getById("product_category").focus();
    return false;
  }

  // 상품 원산지
  if (!getValue(getById("product_origin"))) {
    alert("상품 원산지를 선택하세요");
    getById("product_origin").focus();
    return false;
  }
  else if (getValue(getById("product_origin")) == "선택") {
    alert("상품 원산지를 선택하세요");
    getById("product_origin").focus();
    return false;
  }

  // 상품 이미지는 2개 이상 등록
  if (!getValue(getById("imageFile1"))) {
    alert("상품 이미지를 선택하세요");
    getById("imageFile1").focus();
    return false;
  }
  if (!getValue(getById("imageFile2"))) {
    alert("상품 이미지를 선택하세요");
    getById("imageFile2").focus();
    return false;
  }

  // 상품 등록 여부 확인
  if (confirm("상품을 등록하시겠습니까?")) {
    return true;
  }
  else {
    alert("상품 등록이 취소되었습니다.");
    location.href = `/${TITLE}`;
    return false;
  }
};