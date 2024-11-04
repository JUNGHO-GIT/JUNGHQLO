/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc add 유효성 검사
**/
function addBoard() {
  if (getValue(getById("board_title")) === "") {
    alert("제목을 입력하세요");
    $("#board_title").focus();
    return false;
  }
  if (getValue(getById("board_contents")) === "") {
    alert("내용을 입력하세요");
    $("#board_contents").focus();
    return false;
  }
  alert("등록이 완료되었습니다.");
  window.location.reload();
  return true;
}

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc update 유효성 검사
**/
function updateBoard() {
  if (getValue(getById("board_title")) === "") {
    alert("제목을 입력하세요");
    $("#board_title").focus();
    return false;
  }
  if (getValue(getById("board_contents")) === "") {
    alert("내용을 입력하세요");
    $("#board_contents").focus();
    return false;
  }
  alert("수정이 완료되었습니다.");
  window.location.reload();
  return true;
}