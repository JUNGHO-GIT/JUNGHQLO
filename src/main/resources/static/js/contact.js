/** ----------------------------------------------------------------------------------------------->
* @param {Event} event
* @return {boolean}
* @desc 문의 유효성 검사
**/
function contactAlert (event) {
  if (getValue(getById("contactName")) === "") {
    alert("이름을 입력해주세요.");
    document?.getElementById("contactName")?.focus();
    return false;
  }
  else if (getValue(getById("contactTel")) === "") {
    alert("연락처를 입력해주세요.");
    document?.getElementById("contactTel")?.focus();
    return false;
  }
  else if (getValue(getById("contactEmail")) == "") {
    alert("이메일을 입력해주세요.");
    document?.getElementById("contactEmail")?.focus();
    return false;
  }
  else {
    event.preventDefault();
    alert("상담 접수가 완료되었습니다.");
    window.location.reload();
    return true;
  }
};