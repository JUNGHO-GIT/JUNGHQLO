// 4. addQna -------------------------------------------------------------------------------------->
function addQna() {

  const qna_title = document.getElementById("qna_title");
  const qna_contents = document.getElementById("qna_contents");
  const qna_category = document.getElementById("qna_category");

  if (qna_title.value === "") {
    alert("제목을 입력하세요");
    qna_title.focus();
    return false;
  }
  if (qna_contents.value === "") {
    alert("내용을 입력하세요");
    qna_contents.focus();
    return false;
  }
  if (qna_category.value === "") {
    alert("카테고리를 선택하세요");
    qna_category.focus();
    return false;
  }

  alert("등록이 완료되었습니다.");
  window.location.reload();
  return true;
}

// 5. updateQna ----------------------------------------------------------------------------------->
function updateQna() {

  const qna_title = document.getElementById("qna_title");
  const qna_contents = document.getElementById("qna_contents");
  const qna_category = document.getElementById("qna_category");

  if (qna_title.value === "") {
    alert("제목을 입력하세요");
    qna_title.focus();
    return false;
  }
  if (qna_contents.value === "") {
    alert("내용을 입력하세요");
    qna_contents.focus();
    return false;
  }
  if (qna_category.value === "") {
    alert("카테고리를 선택하세요");
    qna_category.focus();
    return false;
  }

  alert("수정이 완료되었습니다.");
  window.location.reload();
  return true;
}