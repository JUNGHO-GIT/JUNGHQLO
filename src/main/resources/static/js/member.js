// 3. findMemberId -------------------------------------------------------------------------------->
function findMemberId() {

  const member_name = $("#member_name").val();
  const member_email = $("#member_email").val();

  if (member_name.trim() === "") {
    alert("이름을 입력하세요");
    $("#member_name").focus();
    return false;
  }
  if (member_email.trim() === "") {
    alert("이메일을 입력하세요");
    $("#member_email").focus();
    return false;
  }
  $.ajax({
    url: "/JUNGHQLO/member/findMemberId",
    method: "POST",
    data: { member_name: member_name, member_email: member_email },
    success: function (response) {
      if (response == "~") {
        alert("일치하는 회원정보가 없습니다.");
        window.location.reload();
      }
      else {
        alert("회원님의 아이디는 " + response + " 입니다.");
        window.location.reload();
      }
    },
  });
}

// 3-1. findMemberPw ------------------------------------------------------------------------------>
function findMemberPw() {

  const member_id = $("#member_id").val();
  const member_name = $("#member_name").val();
  const member_email = $("#member_email").val();

  if (member_id.trim() === "") {
    alert("아이디를 입력하세요");
    $("#member_id").focus();
    return false;
  }
  if (member_name.trim() === "") {
    alert("이름을 입력하세요");
    $("#member_name").focus();
    return false;
  }
  if (member_email.trim() === "") {
    alert("이메일을 입력하세요");
    $("#member_email").focus();
    return false;
  }
  $.ajax({
    url: "/JUNGHQLO/member/findMemberPw",
    method: "POST",
    data: {
      member_id: member_id,
      member_name: member_name,
      member_email: member_email,
    },
    success: function (response) {
      if (response == "~") {
        alert("일치하는 회원정보가 없습니다.");
        window.location.reload();
      }
      else {
        alert("회원님의 비밀번호는 " + response + " 입니다.");
        window.location.reload();
      }
    },
  });
}

// 4. addMember ----------------------------------------------------------------------------------->
function addMember() {

  if ($("#member_id").val() == "") {
    alert("아이디를 입력하세요");
    $("#member_id").focus();
    return false;
  }

  // 비밀번호는 8~12자리의 영문 대소문자와 숫자만 입력 가능
  if ($("#member_pw").val() == "") {
    alert("비밀번호를 입력하세요");
    $("#member_pw").focus();
    return false;
  }
  if (!$("#member_pw").val().match(/^[a-zA-Z0-9]{8,12}$/)) {
    alert("비밀번호는 8~12자리의 영문 대소문자와 숫자만 입력 가능합니다");
    $("#member_pw").focus();
    return false;
  }

  // 이름은 한글만 입력 가능
  if ($("#member_name").val() == "") {
    alert("이름을 입력하세요");
    $("#member_name").focus();
    return false;
  }
  if (!$("#member_name").val().match(/^[가-힣]+$/)) {
    alert("이름은 한글만 입력 가능합니다");
    $("#member_name").focus();
    return false;
  }

  // 전화번호는 맨앞 세자리는 010 만 가능하고, 뒤에는 8자리만 가능
  if ($("#member_phone").val() == "") {
    alert("전화번호를 입력하세요");
    $("#member_phone").focus();
    return false;
  }
  if (!$("#member_phone").val().match(/^010[0-9]{8}$/)) {
    alert("전화번호는 01012345678 형식으로 입력해주세요");
    $("#member_phone").focus();
    return false;
  }

  if ($("#member_email").val() == "") {
    alert("이메일을 입력하세요");
    $("#member_email").focus();
    return false;
  }
   if (document.getElementById("email_verified").value != "true") {
    alert("이메일 인증을 완료해주세요");
    return false;
  }
  if ($("#member_address1").val() == "") {
    alert("주소를 입력하세요");
    $("#member_address1").focus();
    return false;
  }
  if ($("#member_address2").val() == "") {
    alert("상세주소를 입력하세요");
    $("#member_address2").focus();
    return false;
  }

  if (!$("#member_agree").is(":checked")) {
    alert("약관에 동의해주세요");
    $("#member_agree").focus();
    return false;
  }

  alert("등록이 완료되었습니다.");
  window.location.href="/JUNGHQLO/member/loginMember";
  return true;
}

// 4-1. checkMemberId ----------------------------------------------------------------------------->
function checkMemberId() {

  const memberIdInput = document.getElementById("member_id");
  const member_id = memberIdInput.value;
  const hasLetter = /[a-zA-Z]/.test(member_id);
  const hasNumber = /\d/.test(member_id);

  if (member_id.trim() === "") {
    memberIdInput.focus();
    return;
  }

  else if (member_id.length < 4 || member_id.length > 12 || !member_id.match(/^[a-zA-Z0-9]+$/) || !hasLetter || !hasNumber) {
    alert("아이디는 4~12자리의 영문 대소문자와 숫자를 포함해야 합니다");
    memberIdInput.value = "";
    memberIdInput.focus();
    return false;
  }
  else {
    const request = new XMLHttpRequest();
    request.open("GET", "/JUNGHQLO/member/checkMemberId?member_id=" + member_id);
    request.send();
    request.onreadystatechange = function () {
      if (request.readyState == 4 && request.status == 200) {
        if (request.responseText == "1" || request.responseText == 1) {
          alert("이미 사용중인 아이디입니다.");
          memberIdInput.value = "";
          memberIdInput.focus();
          return false;
        }
        else {
          alert("사용 가능한 아이디입니다.");
          return true;
        }
      }
    };
  }
}

// 4-2. daumPostcode ------------------------------------------------------------------------------>
function openDaumPostcode() {
  new daum.Postcode({
    oncomplete: function (data) {
      document.getElementById("member_address1").value = data.address;
    },
  }).open();
  return false;
}

// 4-3. sendEmail --------------------------------------------------------------------------------->
function sendEmail() {

  const front_email = document.getElementById('email_front').value;
  const back_email = document.getElementById('email_back').value;
  const member_email = front_email + back_email;
  document.getElementById('member_email').value = member_email;

  $.ajax({
    url: "/JUNGHQLO/member/sendEmail",
    method: "GET",
    data: { member_email: member_email },
    success: function (response) {
      if (response == "1") {
        alert("인증번호가 전송되었습니다.");
      }
      else {
        alert("인증번호 전송에 실패했습니다.");
        $("#member_email").val("");
        $("#member_email").focus();
      }
    },
  });
}

// 4-4. checkEmail -------------------------------------------------------------------------------->
function checkEmail() {

  const email_code = document.getElementById('email_code').value;
  const member_email = document.getElementById('member_email').value;

  $.ajax({
    url: "/JUNGHQLO/member/checkEmail",
    method: "GET",
    data: {member_email : member_email, email_code : email_code},
    success: function (response) {
      if (response == 1) {
        alert("인증번호가 일치합니다.");
        document.getElementById('email_button').disabled = true;
        document.getElementById('email_verified').value = "true";
        return true;
      }
      else {
        alert("인증번호가 일치하지 않습니다.");
        $("#email_code").val("");
        $("#email_code").focus();
        return false;
      }
    },
  });
}

// 5-2. checkMemberIdPw --------------------------------------------------------------------------->
function checkMemberIdPw() {

  let memberIdElem = document.getElementById('member_id');
  let memberPwElem = document.getElementById('member_pw');

  let memberId = memberIdElem.value;
  let memberPw = memberPwElem.value;

  if (memberId.trim() == "") {
    alert("아이디를 입력하세요");
    memberIdElem.focus();
    return false;
  }
  else if (memberPw.trim() == "") {
    alert("비밀번호를 입력하세요");
    memberPwElem.focus();
    return false;
  }
  else {
    let xhr = new XMLHttpRequest();
    let url = "/JUNGHQLO/member/checkMemberIdPw?member_id=" + memberId + "&member_pw=" + memberPw;
    let method = "GET";

    xhr.open(method, url, true);
    xhr.send();
    xhr.onreadystatechange = function () {
      if (xhr.readyState == 4 && xhr.status == 200) {
        if (xhr.responseText == "0") {
          alert("아이디 또는 비밀번호가 일치하지 않습니다.");
          memberIdElem.focus();
        }
        else if (xhr.responseText == "1") {
          alert("로그인 되었습니다.");
          window.location.href = "/JUNGHQLO";
          return true;
        }
      }
    };
  }
}

// 4-4. logoutMember ------------------------------------------------------------------------------>
function logoutMember() {
  if (confirm("로그아웃 하시겠습니까?")) {
    $.ajax({
      url: "/JUNGHQLO/member/logoutMember",
      method: "GET",
      success: function () {
        alert("로그아웃 되었습니다.");
        window.location.href = "/JUNGHQLO";
        return true;
      },
    });
  }
  else {
    alert("로그아웃이 취소되었습니다.");
    return false;
  }
}

// 5. updateMember -------------------------------------------------------------------------------->
function updateMember() {

  // 전화번호는 맨앞 세자리는 010 만 가능하고, 뒤에는 8자리만 가능
  if ($("#member_phone").val() == "") {
    alert("전화번호를 입력하세요");
    $("#member_phone").focus();
    return false;
  }
  if (!$("#member_phone").val().match(/^010[0-9]{8}$/)) {
    alert("전화번호는 01012345678 형식으로 입력해주세요");
    $("#member_phone").focus();
    return false;
  }
  if ($("#member_email").val() == "") {
    alert("이메일을 입력하세요");
    $("#member_email").focus();
    return false;
  }
  if ($("#member_address1").val() == "") {
    alert("주소를 입력하세요");
    $("#member_address1").focus();
    return false;
  }
  if ($("#member_address2").val() == "") {
    alert("상세주소를 입력하세요");
    $("#member_address2").focus();
    return false;
  }
  alert("수정이 완료되었습니다.");
  window.location.href="/JUNGHQLO";
  return true;
}

// 5-1. updateMemberPw ---------------------------------------------------------------------------->
function updateMemberPw() {
  if ($("#member_pw").val() == "") {
    alert("비밀번호를 입력하세요");
    $("#member_pw").focus();
    return false;
  }
  if ($("#member_pw2").val() == "") {
    alert("비밀번호를 다시 입력하세요");
    $("#member_pw2").focus();
    return false;
  }
  if ($("#member_pw").val() != $("#member_pw2").val()) {
    alert("비밀번호 확인이 일치하지 않습니다.");
    $("#member_pw_check").val("");
    $("#member_pw_check").focus();
    return false;
  }
  // 비밀번호는 8~12자리의 영문 대소문자와 숫자만 입력 가능
  if (!$("#member_pw").val().match(/^[a-zA-Z0-9]{8,12}$/)) {
    alert("비밀번호는 8~12자리의 영문 대소문자와 숫자만 입력 가능합니다");
    $("#member_pw").focus();
    return false;
  }
  else {
    $.ajax({
      url: "/JUNGHQLO/member/updateMemberPw",
      method: "POST",
      data: { member_pw: $("#member_pw").val() },
      success: function (response) {
        if (response == "1") {
          alert("비밀번호가 변경되었습니다.");
          window.location.href="/JUNGHQLO";
          return true;
        }
        else if (response == "0") {
          alert("비밀번호 변경에 실패했습니다.");
          window.location.reload();
          return false;
        }
      },
    });
  }
}

// 6. deleteMember -------------------------------------------------------------------------------->
function deleteMember() {
  const member_name = $("#member_name").val();
  const member_id = $("#member_id").val();
  const member_pw = $("#member_pw").val();
  const member_pw2 = $("#member_pw2").val();

  if (member_pw.trim() === "") {
    alert("비밀번호를 입력하세요");
    $("#member_pw").focus();
    return false;
  }
  if (member_pw2.trim() === "") {
    alert("비밀번호를 다시 입력하세요");
    $("#member_pw2").focus();
    return false;
  }
  if (member_pw != member_pw2) {
    alert("비밀번호 확인이 일치하지 않습니다.");
    $("#member_pw_check").val("");
    $("#member_pw_check").focus();
    return false;
  }
  $.ajax({
    url: "/JUNGHQLO/member/deleteMember",
    method: "POST",
    data: {
      member_name: member_name,
      member_id: member_id,
      member_pw: member_pw,
    },
    success: function (response) {
      if (response == 0) {
        alert("일치하는 회원정보가 없습니다.");
        window.location.reload();
      }
      else if (response == 1) {
        if (confirm("회원 탈퇴를 하시겠습니까?") == true) {
          alert("회원 탈퇴 되었습니다.");
          window.location.href = "/JUNGHQLO/member/loginMember";
        }
        else {
          alert("회원탈퇴가 취소되었습니다.");
          window.location.reload();
        }
      }
    },
  });
}