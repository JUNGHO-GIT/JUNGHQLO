
// ------------------------------------------------------------------------------------------------>
const contactAlert = (event) => {

  const contactName = document.getElementById("contactName").value;
  const contactEmail = document.getElementById("contactEmail").value;
  const contactTel = document.getElementById("contactTel").value;

  if (contactName == "") {
    alert("이름을 입력해주세요.");
    document.getElementById("contactName").focus();
    return false;
  }
  else if (contactTel == "") {
    alert("연락처를 입력해주세요.");
    document.getElementById("contactTel").focus();
    return false;
  }
  else if (contactEmail == "") {
    alert("이메일을 입력해주세요.");
    document.getElementById("contactEmail").focus();
    return false;
  }
  else {
    event.preventDefault();
    alert("상담 접수가 완료되었습니다.");
    window.location.reload();
    return true;
  }
};