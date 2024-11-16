/** ------------------------------------------------------------------------------------------------
* @desc 페이지 이동
**/
function goToPage(url, param, extra) {
  if ((!preFix1 || !preFix2) && extra) {
    preFix1 = extra;
    preFix2 = extra.charAt(0).toUpperCase() + extra.slice(1);
  }
  if (url && !param) {
    if (url === "refresh") {
      location.reload();
    }
    else if (url === "home") {
      location.href = `/${title}`;
    }
    else if (url === "list") {
      location.href = `/${title}/${preFix1}/list${preFix2}`;
    }
    else if (url === "save") {
      location.href = `/${title}/${preFix1}/save${preFix2}`;
    }
    else {
      location.href = `/${title}/${url}`;
    }
  }
  else if (url && param) {
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
        goToPage(`${preFix1}/list${preFix2}`);
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
        goToPage(`${preFix1}/list${preFix2}`);
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
    data: {
      [`${preFix1}_number`]: number
    },
    success: function(response) {
      if (response === 1) {
        alert("삭제되었습니다.");
        goToPage(`${preFix1}/list${preFix2}`);
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

/** ------------------------------------------------------------------------------------------------
* @desc 로그인
**/
function getLogin() {

  // 동적 validate 함수 호출
  const validate = eval(`validateMember('login')`);
  if (!validate) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/member/loginMember`,
    data: {
      member_id: getValue(getById("member_id")),
      member_pw: getValue(getById("member_pw")),
    },
    success: function(response) {
      if (response === 1) {
        alert("로그인 되었습니다.");
        goToPage("home");
      }
      else if (response === 0) {
        alert("아이디 또는 비밀번호가 일치하지 않습니다.");
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
* @param null
* @return {boolean}
* @desc 로그아웃
**/
function getLogout() {
  $.ajax({
    url: `/${title}/member/logoutMember`,
    method: "POST",
    data: {},
    success: function (response) {
      if (response === 1) {
        goToPage("home");
      }
      else if (response === 0) {
        goToPage("refresh");
      }
      else {
        alert("오류가 발생했습니다.");
        goToPage("refresh");
      }
    }
  });
  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 아이디 중복 확인
**/
function checkMemberId() {
  $.ajax({
    method: "GET",
    url: `/${title}/member/checkMemberId`,
    data: {
      member_id: getValue(getById("member_id")),
    },
    success: function (response) {
      if (response === 0) {
        alert("이미 사용중인 아이디입니다.");
        getById("member_id")?.focus();
        return false;
      }
      else {
        alert("사용 가능한 아이디입니다.");
        return true;
      }
    }
  });

  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 이메일 인증
**/
function sendEmail() {
  $.ajax({
    method: "GET",
    url: `/${title}/member/sendEmail`,
    data: {
      member_email: getValue(getById("member_email")),
    },
    success: function (response) {
      if (response === 1) {
        alert("인증번호가 전송되었습니다.");
      }
      else if (response === 0) {
        alert("인증번호 전송에 실패했습니다.");
        setValue(getById("member_email"), "");
        getById("member_email")?.focus();
      }
      else {
        alert("오류가 발생했습니다. 다시 시도해주세요");
        setValue(getById("member_email"), "");
      }
    },
  });

  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 이메일 인증 확인
**/
function checkEmail() {
  $.ajax({
    url: `/${title}/member/checkEmail`,
    method: "GET",
    data: {
      member_email: getValue(getById("member_email")),
      email_code: getValue(getById("email_code")),
    },
    success: function (response) {
      if (response === 1) {
        alert("인증번호가 일치합니다.");
        getById("email_button")?.setAttribute("disabled", "true");
        getById("email_verified")?.setAttribute("value", "true");
        return true;
      }
      else {
        alert("인증번호가 일치하지 않습니다.");
        setValue(getById("email_code"), "");
        getById("email_code")?.focus();
        return false;
      }
    },
  });

  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @desc 회원가입
**/
function getSignup() {

  // form 요소 찾기
  const form = getElem("#formData");
  const formDataObject = form instanceof HTMLFormElement ? new FormData(form) : new FormData();

  // 동적 validate 함수 호출
  const validate = eval(`validateMember('signup')`);
  if (!validate) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/member/signupMember`,
    data: formDataObject,
    contentType: false,
    processData: false,
    success: function(response) {
      if (response === 1) {
        alert("회원가입 되었습니다.");
        goToPage("member/memberLogin");
      }
      else if (response === 0) {
        alert("회원가입 실패했습니다.");
        goToPage("refresh");
      }
      else {
        alert("오류가 발생했습니다.");
        goToPage("refresh");
      }
    }
  });

}

/** ----------------------------------------------------------------------------------------------->
* @desc 회원정보 수정
**/
function getUpdateMember() {

  // form 요소 찾기
  const form = getElem("#formData");
  const formDataObject = form instanceof HTMLFormElement ? new FormData(form) : new FormData();

  // 동적 validate 함수 호출
  const validate = eval(`validateMember('update')`);
  if (!validate) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/member/updateMember`,
    data: formDataObject,
    contentType: false,
    processData: false,
    success: function(response) {
      if (response === 1) {
        alert("수정되었습니다.");
        goToPage("refresh");
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
* @desc 비밀번호 변경
**/
function getUpdateMemberPw() {

  // 동적 validate 함수 호출
  const validate = eval(`validateMember('updatePw')`);
  if (!validate) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/member/updateMemberPw`,
    data: {
      member_id: getValue(getById("member_id")),
      member_pw: getValue(getById("member_pw")),
    },
    success: function(response) {
      if (response === 1) {
        alert("비밀번호가 변경되었습니다.");
        goToPage("refresh");
      }
      else if (response === 0) {
        alert("비밀번호 변경 실패했습니다.");
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
* @desc 아이디 찾기
**/
function getFindId() {

  // 동적 validate 함수 호출
  const validate = eval(`validateMember('findId')`);
  if (!validate) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/member/findMemberId`,
    data: {
      member_name: getValue(getById("member_name")),
      member_email: getValue(getById("member_email")),
    },
    success: function(response) {
      if (response === "notExist") {
        alert("일치하는 회원정보가 없습니다.");
      }
      else if (response) {
        alert(`회원님의 아이디는 ${response} 입니다.`);
      }
      else {
        alert("아이디를 찾을 수 없습니다.");
      }
    }
  });
};

/** ------------------------------------------------------------------------------------------------
* @desc 비밀번호 찾기
**/
function getFindPw() {

  // 동적 validate 함수 호출
  const validate = eval(`validateMember('findPw')`);
  if (!validate) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/member/findMemberPw`,
    data: {
      member_id: getValue(getById("member_id")),
      member_name: getValue(getById("member_name")),
      member_email: getValue(getById("member_email")),
    },
    success: function(response) {
      if (response === "notExist") {
        alert("일치하는 회원정보가 없습니다.");
      }
      else if (response) {
        alert(`회원님의 비밀번호는 ${response} 입니다.`);
      }
      else {
        alert("비밀번호를 찾을 수 없습니다.");
      }
    }
  });
};

/** ------------------------------------------------------------------------------------------------
* @desc 회원 탈퇴
**/
function getDeleteMember() {

  // 동적 validate 함수 호출
  const validate = eval(`validateMember('delete')`);
  if (!validate) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/member/deleteMember`,
    data: {
      member_name: getValue(getById("member_name")),
      member_id: getValue(getById("member_id")),
      member_pw: getValue(getById("member_pw")),
    },
    success: function(response) {
      if (response === 1) {
        alert("탈퇴되었습니다.");
        goToPage("home");
      }
      else if (response === 0) {
        alert("비밀번호가 일치하지 않습니다.");
        goToPage("refresh");
      }
      else {
        alert("오류가 발생했습니다.");
        goToPage("refresh");
      }
    }
  });
};