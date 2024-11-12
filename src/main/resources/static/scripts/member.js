

/** ------------------------------------------------------------------------------------------------
* @desc 로그인
**/
function getLogin() {

  // id, password 값 찾기
  const memberId = getValue(getById("member_id"));
  const memberPw = getValue(getById("member_pw"));

  // 동적 validate 함수 호출
  const validate = eval(`validateMember('login')`);
  if (!validate) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/member/loginMember`,
    data: {
      member_id: memberId,
      member_pw: memberPw
    },
    success: function(response) {
      if (response === 1) {
        alert("로그인 되었습니다.");
        goToPage("home");
      }
      else if (response === 0) {
        alert("로그인 실패했습니다.");
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
  if (confirm("로그아웃 하시겠습니까?")) {
    $.ajax({
      url: `/${title}/member/logoutMember`,
      method: "GET",
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
  }
  else {
    location.reload();
    return false;
  }

  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 아이디 중복 확인
**/
function checkMemberId() {
  $.ajax({
    url: `/${title}/member/checkMemberId?member_id=${getValue(getById("member_id"))}`,
    method: "GET",
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
  const front_email = getValue(getById("email_front"));
  const back_email = getValue(getById("email_back"));
  const member_email = front_email + back_email;
  setValue(getById("member_email"), member_email);

  $.ajax({
    url: `/${title}/member/sendEmail?member_email=${member_email}`,
    method: "GET",
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
  const email_code = getValue(getById("email_code"));
  const member_email = getValue(getById("member_email"));

  $.ajax({
    url: `/${title}/member/checkEmail?member_email=${member_email}&email_code=${email_code}`,
    method: "GET",
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
    url: `/${title}/member/saveMember`,
    data: formDataObject,
    success: function(response) {
      if (response === 1) {
        alert("회원가입 되었습니다.");
        goToPage("home");
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
    success: function(response) {
      if (response === 1) {
        alert("수정되었습니다.");
        goToPage("home");
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

  // form 요소 찾기
  const form = getElem("#formData");
  const formDataObject = form instanceof HTMLFormElement ? new FormData(form) : new FormData();

  // 동적 validate 함수 호출
  const validate = eval(`validateMember('change')`);
  if (!validate) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/member/changePassword`,
    data: formDataObject,
    success: function(response) {
      if (response === 1) {
        alert("비밀번호가 변경되었습니다.");
        goToPage("home");
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
    url: `/${title}/member/findId`,
    data: {
      member_name: getValue(getById("member_name")),
      member_email: getValue(getById("member_email")),
    },
    success: function(response) {
      if (response) {
        alert(`회원님의 아이디는 ${response} 입니다.`);
        goToPage("home");
      }
      else {
        alert("아이디를 찾을 수 없습니다.");
        goToPage("refresh");
      }
    }
  });
};

/** ------------------------------------------------------------------------------------------------
* @desc 비밀번호 찾기
**/
function getFindPw() {

  // 동적 validate 함수 호출
  const validate = eval(`validateMember('findPassword')`);
  if (!validate) {
    return;
  }

  $.ajax({
    type: "POST",
    url: `/${title}/member/findPassword`,
    data: {
      member_id: getValue(getById("member_id")),
      member_name: getValue(getById("member_name")),
      member_email: getValue(getById("member_email")),
    },
    success: function(response) {
      if (response) {
        alert(`회원님의 비밀번호는 ${response} 입니다.`);
        goToPage("home");
      }
      else {
        alert("비밀번호를 찾을 수 없습니다.");
        goToPage("refresh");
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
    success: function(response) {
      if (response === 1) {
        alert("탈퇴되었습니다.");
        goToPage("home");
      }
      else if (response === 0) {
        alert("탈퇴 실패했습니다.");
        goToPage("refresh");
      }
      else {
        alert("오류가 발생했습니다.");
        goToPage("refresh");
      }
    }
  });
};