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
function getUpdatePw() {

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