/** ------------------------------------------------------------------------------------------------
* @desc 페이지 이동
**/
function goToPage(url, param) {
  if (url && !param) {
    if (url === "refresh") {
      location.reload();
    }
    else if (url === "home") {
      location.href = `/${title}`;
    }
    else if (url === "save") {
      location.href = `/${title}/${preFix1}/save${preFix2}`;
    }
    else {
      location.href = `/${title}/${url}`;
    }
  }
  if (url && param) {
    if (url === 'detail') {
      location.href = `/${title}/${preFix1}/detail${preFix2}?${preFix1}_number=${param}`;
    }
    if (url === 'update') {
      location.href = `/${title}/${preFix1}/update${preFix2}?${preFix1}_number=${param}`;
    }
    if (url === 'delete') {
      location.href = `/${title}/${preFix1}/delete${preFix2}?${preFix1}_number=${param}`;
    }
    if (url === "list") {
      const parsedParam = JSON.parse(JSON.stringify(param));
      // 1. 빈 객체일 경우
      if (!Array.isArray(parsedParam) && Object.keys(parsedParam).length === 0) {
        location.href = `/${title}/${preFix1}/list${preFix2}`;
      }
      // 2. 키-값이 1개만 있는 경우
      else if (Object.keys(parsedParam).length === 1) {
        const key = Object.keys(parsedParam)[0];
        const value = parsedParam[key];
        location.href = `/${title}/${preFix1}/list${preFix2}?${key}=${value}`;
      }
      // 3. 키-값이 2개 이상인 경우
      else {
        let url = `/${title}/${preFix1}/list${preFix2}?`;
        const keys = Object.keys(parsedParam);
        keys.forEach((key, index) => {
          url += `${key}=${parsedParam[key]}`;
          if (index < keys.length - 1) {
            url += '&';
          }
        });
        location.href = url;
      }
    }
  }
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
  if (!validate) {
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
        goToPage('list');
      }
      else if (response === 0) {
        alert("등록 실패했습니다.");
        goToPage("refresh");
      }
      else {
        alert("오류가 발생했습니다.");
        goToPage("refresh");
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
  if (!validate) {
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
        goToPage('list');
      }
      else if (response === 0) {
        alert("수정 실패했습니다.");
        goToPage("refresh");
      }
      else {
        alert("오류가 발생했습니다.");
        goToPage("refresh");
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
  if (!validate) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/${preFix1}/delete${preFix2}`,
    data: `${preFix1}_number=${number}`,
    success: function(response) {
      if (response === 1) {
        alert("삭제되었습니다.");
        goToPage('list');
      }
      else if (response === 0) {
        alert("삭제 실패했습니다.");
        goToPage("refresh");
      }
      else {
        alert("오류가 발생했습니다.");
        goToPage("refresh");
      }
    }
  });
};

/** ----------------------------------------------------------------------------------------------->
* @desc 구매 하기
**/
function getOrders() {

  // form 요소 찾기
  const form = getElem("#formData");

  // 동적 validate 함수 호출
  const validate = eval(`validateOrders('save')`);
  if (!validate) {
    return;
  }

  // form 서브밋 구성 설정
  if (form && form instanceof HTMLFormElement) {
    form.method = "POST";
    form.action = `/${title}/orders/saveOrders`;
    form.submit();
  }
};