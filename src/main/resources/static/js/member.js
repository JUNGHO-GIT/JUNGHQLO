/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 아이디 찾기
**/
function findMemberId() {
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

  $.ajax({
    url: `/JUNGHQLO/member/findMemberId`,
    method: "POST",
    data: {
      member_name: getValue(getById("member_name")),
      member_email: getValue(getById("member_email")),
    },
    /**
    * @param {any} response
    **/
    success: function (response) {
      if (response == "~") {
        alert("일치하는 회원정보가 없습니다.");
        window.location.reload();
      }
      else {
        alert(`회원님의 아이디는 ${response} 입니다.`);
        window.location.reload();
      }
    },
  });

  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 비밀번호 찾기
**/
function findMemberPw() {
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

  $.ajax({
    url: `/JUNGHQLO/member/findMemberPw`,
    method: "POST",
    data: {
      member_id: getValue(getById("member_id")),
      member_name: getValue(getById("member_name")),
      member_email: getValue(getById("member_email")),
    },
    /**
    * @param {any} responseText
    **/
    success: function (responseText) {
      if (responseText == "~") {
        alert("일치하는 회원정보가 없습니다.");
        window.location.reload();
      }
      else {
        alert(`회원님의 비밀번호는 ${responseText} 입니다.`);
        window.location.reload();
      }
    },
  });

  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 회원가입 유효성 검사
**/
function addMember() {
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
  if (getValue(getById("member_name")).toString().match(/^[가-힣]+$/)) {
    alert("이름은 한글만 입력 가능합니다");
    getById("member_name")?.focus();
    return false;
  }
  if (getValue(getById("member_phone")) === "") {
    alert("전화번호를 입력하세요");
    getById("member_phone")?.focus();
    return false;
  }
  if (getValue(getById("member_phone")).toString().match(/^010[0-9]{8}$/)) {
    alert("전화번호는 01012345678 형식으로 입력해주세요");
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
  alert("등록이 완료되었습니다.");
  location.href = `/${TITLE}/member/loginMember`;
  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 아이디 중복 확인
**/
function checkMemberId() {

  if (getValue(getById("member_id")) === "") {
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

  $.ajax({
    url: `/JUNGHQLO/member/checkMemberId?member_id=${getValue(getById("member_id"))}`,
    method: "GET",
    /**
    * @param {any} responseText
    **/
    success: function (responseText) {
      if (responseText === "fail") {
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
* @desc 다음 우편번호
**/
function openDaumPostcode() {
  new daum.Postcode({
    /**
    * @param {any} data
    **/
    oncomplete: function (data) {
      setValue(getById("member_address1"), data.address);
    },
  }).open();
  return false;
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
    url: `/JUNGHQLO/member/sendEmail?member_email=${member_email}`,
    method: "GET",
    /**
    * @param {any} response
    **/
    success: function (response) {
      if (response === "success") {
        alert("인증번호가 전송되었습니다.");
      }
      else if (response === "fail") {
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
    url: `/JUNGHQLO/member/checkEmail?member_email=${member_email}&email_code=${email_code}`,
    method: "GET",
    /**
    * @param {any} response
    **/
    success: function (response) {
      if (response === "success") {
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
* @param null
* @return {boolean}
* @desc 로그인 아이디 비밀번호 체크
**/
function checkMemberIdPw() {
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

  $.ajax({
    url: `/JUNGHQLO/member/checkMemberIdPw?member_id=${getValue(getById("member_id"))}&member_pw=${getValue(getById("member_pw"))}`,
    method: "GET",
    /**
    * @param {any} response
    **/
    success: function (response) {
      if (response === "success") {
        alert("로그인 되었습니다.");
        location.href = `/${TITLE}`;
        return true;
      }
      else if (response === "fail") {
        alert("아이디 또는 비밀번호가 일치하지 않습니다.");
        getById("member_id")?.focus();
        return false;
      }
    },
  });

  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 로그아웃
**/
function logoutMember() {
  if (confirm("로그아웃 하시겠습니까?")) {
    $.ajax({
      url: `/JUNGHQLO/member/logoutMember`,
      method: "GET",
      success: function () {
        alert("로그아웃 되었습니다.");
        location.href = "/JUNGHQLO";
        return true;
      }
    });
  }
  else {
    alert("로그아웃이 취소되었습니다.");
    return false;
  }

  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 회원정보 수정
**/
function updateMember() {
  if (getValue(getById("member_phone")) === "") {
    alert("전화번호를 입력하세요");
    getById("member_phone")?.focus();
    return false;
  }
  if (!getValue(getById("member_phone")).toString().match(/^010[0-9]{8}$/)) {
    alert("전화번호는 01012345678 형식으로 입력해주세요");
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
  alert("수정이 완료되었습니다.");
  window.location.reload();
  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 비밀번호 변경
**/
function updateMemberPw() {
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
  if (!getValue(getById("member_pw")).toString().match(/^[a-zA-Z0-9]{8,12}$/)) {
    alert("비밀번호는 8~12자리의 영문 대소문자와 숫자만 입력 가능합니다");
    getById("member_pw")?.focus();
    return false;
  }

  $.ajax({
    url: `/JUNGHQLO/member/updateMemberPw`,
    method: "POST",
    data: {
      member_pw: getValue(getById("member_pw")),
    },
    /**
    * @param {any} response
    **/
    success: function (response) {
      if (response === "success") {
        alert("비밀번호가 변경되었습니다.");
        location.href = `/${TITLE}/member/loginMember`;
        return true;
      }
      else if (response === "fail") {
        alert("비밀번호 변경에 실패했습니다.");
        window.location.reload();
        return false;
      }
    },
  });

  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 회원탈퇴
**/
function deleteMember() {
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

  $.ajax({
    url: `/JUNGHQLO/member/deleteMember`,
    method: "POST",
    data: {
      member_name: getValue(getById("member_name")),
      member_id: getValue(getById("member_id")),
      member_pw: getValue(getById("member_pw")),
    },
    /**
    * @param {any} response
    **/
    success: function (response) {
      if (response === "fail") {
        alert("일치하는 회원정보가 없습니다.");
        window.location.reload();
        return false;
      }
      else if (response === "success") {
        if (confirm("회원 탈퇴를 하시겠습니까?")) {
          alert("회원 탈퇴 되었습니다.");
          location.href = `/${TITLE}/member/loginMember`;
          return true;
        }
        else {
          alert("회원탈퇴가 취소되었습니다.");
          window.location.reload();
          return false;
        }
      }
    },
  });

  return true;
};