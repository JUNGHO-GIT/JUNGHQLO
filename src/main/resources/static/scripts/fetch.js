/** ------------------------------------------------------------------------------------------------
* @desc 페이지 이동
**/
function goToPage(url) {
  location.href = url;
};

/** ------------------------------------------------------------------------------------------------
* @desc 홈으로 이동
**/
function goHome() {
  goToPage(`/${title}`);
}

/** ------------------------------------------------------------------------------------------------
* @desc 새로고침
**/
function goRefresh() {
  location.reload();
}

/** ----------------------------------------------------------------------------------------------->
* @desc 로그인 페이지로 이동
**/
function goLogin() {
  goToPage(`/${title}/member/loginMember`);
};

/** ----------------------------------------------------------------------------------------------->
* @desc 리스트 페이지로 이동
**/
function goList(param) {

  if (!param) {
    goToPage(`/${title}/${preFix1}/list${preFix2}`);
    return;
  }

  const parsedParam = JSON.parse(JSON.stringify(param));

  // 1. 빈 객체일 경우
  if (!Array.isArray(parsedParam) && Object.keys(parsedParam).length === 0) {
    goToPage(`/${title}/${preFix1}/list${preFix2}`);
  }
  // 2. 키-값이 1개만 있는 경우
  else if (Object.keys(parsedParam).length === 1) {
    const key = Object.keys(parsedParam)[0];
    const value = parsedParam[key];
    goToPage(`/${title}/${preFix1}/list${preFix2}?${key}=${value}`);
  }
  // 3. 키-값이 2개 이상인 경우
  else {
    let url = `/${title}/${preFix1}/list${preFix2}?`;
    for (const key in parsedParam) {
      url += `${key}=${parsedParam[key]}&`;
    }
    goToPage(url);
  }
};

/** ----------------------------------------------------------------------------------------------->
* @desc 상세 페이지로 이동
**/
function goDetail(number) {
  goToPage(`/${title}/${preFix1}/detail${preFix2}?${preFix1}_number=${number}`);
};

/** ----------------------------------------------------------------------------------------------->
* @desc 등록하기 페이지로 이동
**/
function goSave() {
  goToPage(`/${title}/${preFix1}/save${preFix2}`);
};

/** ------------------------------------------------------------------------------------------------
* @desc 수정하기 페이지로 이동
**/
function goUpdate(number) {
  goToPage(`/${title}/${preFix1}/update${preFix2}?${preFix1}_number=${number}`);
};

/** ------------------------------------------------------------------------------------------------
* @desc 삭제하기 페이지로 이동
**/
function goDelete(number) {
  goToPage(`/${title}/${preFix1}/delete${preFix2}?${preFix1}_number=${number}`);
};

/** ----------------------------------------------------------------------------------------------->
* @desc 구매 하기
**/
function getOrders() {

  // form 요소 찾기
  const form = getElem("#formData");
  const formDataObject = form instanceof HTMLFormElement ? new FormData(form) : new FormData();

  /* const productNumber = getValue(getById("product_number"));
  const productName = getValue(getById("product_name"));
  const productStock = getValue(getById("product_stock"));
  const ordersQuantity = getValue(getById("orders_quantity")); */

  // form 데이터 추가
  /* formDataObject.append("product_number", productNumber);
  formDataObject.append("product_name", productName);
  formDataObject.append("product_stock", productStock);
  formDataObject.append("orders_quantity", ordersQuantity); */

  // 동적 validate 함수 호출
  const validate = eval(`validateOrders('save')`);
  if (validate === false) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/orders/saveOrders`,
    data: formDataObject,
    processData: false,
    contentType: false,
    enctype: 'multipart/form-data',
    success: function(response) {
      if (response === 1) {
        alert("구매되었습니다.");
        goList();
      }
      else if (response === 0) {
        alert("구매 실패했습니다.");
        goRefresh();
      }
      else {
        alert("오류가 발생했습니다.");
        goRefresh();
      }
    }
  });
};

/** ----------------------------------------------------------------------------------------------->
* @desc 등록 하기
**/
function getSave() {

  // form 요소 찾기
  const form = getElem("#formData");
  const formDataObject = form instanceof HTMLFormElement ? new FormData(form) : new FormData();

  // 동적 validate 함수 호출
  const validate = eval(`validate${preFix2}('save')`);
  if (validate === false) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/${preFix1}/save${preFix2}`,
    data: formDataObject,
    processData: false,
    contentType: false,
    enctype: 'multipart/form-data',
    success: function(response) {
      if (response === 1) {
        alert("등록되었습니다.");
        goList();
      }
      else if (response === 0) {
        alert("등록 실패했습니다.");
        goRefresh();
      }
      else {
        alert("오류가 발생했습니다.");
        goRefresh();
      }
    }
  });
};

/** ------------------------------------------------------------------------------------------------
* @return {void}
* @desc 수정 하기
**/
function getUpdate() {

  // form 요소 찾기
  const form = getElem("#formData");
  const formDataObject = form instanceof HTMLFormElement ? new FormData(form) : new FormData();

  // 동적 validate 함수 호출
  const validate = eval(`validate${preFix2}('update')`);
  if (validate === false) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/${preFix1}/update${preFix2}`,
    data: formDataObject,
    processData: false,
    contentType: false,
    enctype: 'multipart/form-data',
    success: function(response) {
      if (response === 1) {
        alert("수정되었습니다.");
        goList();
      }
      else if (response === 0) {
        alert("수정 실패했습니다.");
        goRefresh();
      }
      else {
        alert("오류가 발생했습니다.");
        goRefresh();
      }
    }
  });
};

/** ------------------------------------------------------------------------------------------------
* @return {void}
* @desc 삭제 하기
**/
function getDelete() {

  // 동적 validate 함수 호출
  const validate = eval(`validate${preFix2}('delete')`);
  if (validate === false) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/${preFix1}/delete${preFix2}`,
    data: `${preFix1}_number=${number}`,
    success: function(response) {
      if (response === 1) {
        alert("삭제되었습니다.");
        goList();
      }
      else if (response === 0) {
        alert("삭제 실패했습니다.");
        goRefresh();
      }
      else {
        alert("오류가 발생했습니다.");
        goRefresh();
      }
    }
  });
};