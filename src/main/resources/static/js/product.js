// 4. addProduct ---------------------------------------------------------------------------------->
function addProduct() {

  let productName = document.getElementById("product_name");
  let productDetails = document.getElementById("product_details");
  let productPrice = document.getElementById("product_price");
  let productStock = document.getElementById("product_stock");
  let productCompany = document.getElementById("product_company");
  let productCategory = document.getElementById("product_category");
  let productOrigin = document.getElementById("product_origin");
  let productImgsFile1 = document.getElementById("imageFile1");
  let productImgsFile2 = document.getElementById("imageFile2");

  function isNumeric(value) {
    return /^\d+$/.test(value);
  }
  function  isPrice(value) {
    return /^\d{4,}$/.test(value);
  }

  // 상품명은 10글자 이하로 입력
  if (!productName.value) {
    alert("상품명을 입력하세요");
    productName.focus();
    return false;
  }
  else if (productName.value.length > 10) {
    alert("상품명은 10글자 이하로 입력하세요");
    productName.focus();
    return false;
  }

  // 상품 상세 설명은 100글자 이하로 입력
  if (!productDetails.value) {
    alert("상품 상세 설명을 입력하세요");
    productDetails.focus();
    return false;
  }
  else if (productDetails.value.length > 100) {
    alert("상품 상세 설명은 100글자 이하로 입력하세요");
    productDetails.focus();
    return false;
  }

  // 상품 가격은 1,000원 이상 입력
  if (!productPrice.value) {
    alert("상품 가격을 입력하세요");
    productPrice.focus();
    return false;
  }
  else if (!isNumeric(productPrice.value)) {
    alert("상품 가격은 숫자만 입력하세요");
    productPrice.focus();
    return false;
  }
  else if (!isPrice(productPrice.value)) {
    alert("상품 가격은 1,000원 이상 입력하세요");
    productPrice.focus();
    return false;
  }

  // 상품 재고는 0 이상 입력
  if (!productStock.value) {
    alert("상품 재고를 입력하세요");
    productStock.focus();
    return false;
  }
  else if (!isNumeric(productStock.value)) {
    alert("상품 재고는 숫자만 입력하세요");
    productStock.focus();
    return false;
  }
  else if (productStock.value < 0) {
    alert("상품 재고는 0 이상 입력하세요");
    productStock.focus();
    return false;
  }

  // 상품 제조사는 10글자 이하로 입력
  if (!productCompany.value) {
    alert("상품 제조사를 입력하세요");
    productCompany.focus();
    return false;
  }
  else if (productCompany.value.length > 10) {
    alert("상품 제조사는 10글자 이하로 입력하세요");
    productCompany.focus();
    return false;
  }

  // 상품 카테고리
  if (!productCategory.value) {
    alert("상품 카테고리를 선택하세요");
    productCategory.focus();
    return false;
  }
  else if (productCategory.value == "선택") {
    alert("상품 카테고리를 선택하세요");
    productCategory.focus();
    return false;
  }

  // 상품 원산지
  if (!productOrigin.value) {
    alert("상품 원산지를 선택하세요");
    productOrigin.focus();
    return false;
  }
  else if (productOrigin.value == "선택") {
    alert("상품 원산지를 선택하세요");
    productOrigin.focus();
    return false;
  }

  // 상품 이미지는 2개 이상 등록
  if (!productImgsFile1.value) {
    alert("상품 이미지를 선택하세요");
    productImgsFile1.focus();
    return false;
  }
  if (!productImgsFile2.value) {
    alert("상품 이미지를 선택하세요");
    productImgsFile2.focus();
    return false;
  }

  // 상품 등록 여부 확인
  let userConfirm = confirm("상품을 등록하시겠습니까?");
  if (userConfirm == true) {
    return true;
  }
  else {
    alert("상품 등록이 취소되었습니다.");
    window.history.href = "/JUNGHQLO";
    return false;
  }
}

// 5. updateProduct (onclick) --------------------------------------------------------------------->
function updateProduct() {

  let productName = document.getElementById("product_name");
  let productDetails = document.getElementById("product_details");
  let productPrice = document.getElementById("product_price");
  let productStock = document.getElementById("product_stock");
  let productCompany = document.getElementById("product_company");
  let productCategory = document.getElementById("product_category");
  let productOrigin = document.getElementById("product_origin");
  let productImgsFile1 = document.getElementById("imageFile1");
  let productImgsFile2 = document.getElementById("imageFile2");
  let productImgsUrl1 = document.getElementById("product_imgUrl1");
  let productImgsUrl2 = document.getElementById("product_imgUrl2");

  function isNumeric(value) {
    return /^\d+$/.test(value);
  }
  function  isPrice(value) {
    return /^\d{4,}$/.test(value);
  }

  // 상품명은 10글자 이하로 입력
  if (!productName.value) {
    alert("상품명을 입력하세요");
    productName.focus();
    return false;
  }
  else if (productName.value.length > 10) {
    alert("상품명은 10글자 이하로 입력하세요");
    productName.focus();
    return false;
  }

  // 상품 상세 설명은 100글자 이하로 입력
  if (!productDetails.value) {
    alert("상품 상세 설명을 입력하세요");
    productDetails.focus();
    return false;
  }
  else if (productDetails.value.length > 100) {
    alert("상품 상세 설명은 100글자 이하로 입력하세요");
    productDetails.focus();
    return false;
  }

  // 상품 가격은 1,000원 이상 입력
  if (!productPrice.value) {
    alert("상품 가격을 입력하세요");
    productPrice.focus();
    return false;
  }
  else if (!isNumeric(productPrice.value)) {
    alert("상품 가격은 숫자만 입력하세요");
    productPrice.focus();
    return false;
  }
  else if (!isPrice(productPrice.value)) {
    alert("상품 가격은 1,000원 이상 입력하세요");
    productPrice.focus();
    return false;
  }

  // 상품 재고는 0 이상 입력
  if (!productStock.value) {
    alert("상품 재고를 입력하세요");
    productStock.focus();
    return false;
  }
  else if (!isNumeric(productStock.value)) {
    alert("상품 재고는 숫자만 입력하세요");
    productStock.focus();
    return false;
  }
  else if (productStock.value < 0) {
    alert("상품 재고는 0 이상 입력하세요");
    productStock.focus();
    return false;
  }

  // 상품 제조사는 10글자 이하로 입력
  if (!productCompany.value) {
    alert("상품 제조사를 입력하세요");
    productCompany.focus();
    return false;
  }
  else if (productCompany.value.length > 10) {
    alert("상품 제조사는 10글자 이하로 입력하세요");
    productCompany.focus();
    return false;
  }

  // 상품 카테고리
  if (!productCategory.value) {
    alert("상품 카테고리를 선택하세요");
    productCategory.focus();
    return false;
  }
  else if (productCategory.value == "선택") {
    alert("상품 카테고리를 선택하세요");
    productCategory.focus();
    return false;
  }

  // 상품 원산지
  if (!productOrigin.value) {
    alert("상품 원산지를 선택하세요");
    productOrigin.focus();
    return false;
  }
  else if (productOrigin.value == "선택") {
    alert("상품 원산지를 선택하세요");
    productOrigin.focus();
    return false;
  }

  // 상품 이미지는 2개 이상 등록
  if (!productImgsFile1.value && !productImgsUrl1.value) {
    alert("상품 이미지를 선택하세요");
    productImgsFile1.focus();
    return false;
  }
  if (!productImgsFile2.value && !productImgsUrl2.value) {
    alert("상품 이미지를 선택하세요");
    productImgsFile2.focus();
    return false;
  }

  // 상품 수정 여부 확인
  let userConfirm = confirm("상품을 수정하시겠습니까?");
  if (userConfirm == true) {
    return true;
  }
  else {
    alert("상품 수정이 취소되었습니다.");
    return false;
  }
}