/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc QNA 입력 유효성 검사
**/
function addQna() {
  if (!getValue(getById("qna_title"))) {
    alert("제목을 입력하세요");
    getById("qna_title").focus();
    return false;
  }
  else if (getValue(getById("qna_title")).length > 10) {
    alert("제목은 10글자 이하로 입력하세요");
    getById("qna_title").focus();
    return false;
  }

  if (!getValue(getById("qna_contents"))) {
    alert("내용을 입력하세요");
    getById("qna_contents").focus();
    return false;
  }
  else if (getValue(getById("qna_contents")).length > 100) {
    alert("내용은 100글자 이하로 입력하세요");
    getById("qna_contents").focus();
    return false;
  }

  if (!getValue(getById("qna_category"))) {
    alert("카테고리를 선택하세요");
    getById("qna_category").focus();
    return false;
  }

  alert("등록이 완료되었습니다.");
  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc QNA 수정 유효성 검사
**/
function updateQna() {
  if (!getValue(getById("qna_title"))) {
    alert("제목을 입력하세요");
    getById("qna_title").focus();
    return false;
  }
  else if (getValue(getById("qna_title")).length > 10) {
    alert("제목은 10글자 이하로 입력하세요");
    getById("qna_title").focus();
    return false;
  }

  if (!getValue(getById("qna_contents"))) {
    alert("내용을 입력하세요");
    getById("qna_contents").focus();
    return false;
  }
  else if (getValue(getById("qna_contents")).length > 100) {
    alert("내용은 100글자 이하로 입력하세요");
    getById("qna_contents").focus();
    return false;
  }

  if (!getValue(getById("qna_category"))) {
    alert("카테고리를 선택하세요");
    getById("qna_category").focus();
    return false;
  }

  alert("수정이 완료되었습니다.");
  return true;
};