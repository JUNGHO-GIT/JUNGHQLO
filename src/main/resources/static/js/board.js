// 4. addBoard ------------------------------------------------------------------------------------>
function addBoard() {
  if ($("#board_title").val() == "") {
    alert("제목을 입력하세요");
    $("#board_title").focus();
    return false;
  }
  if ($("#board_contents").val() == "") {
    alert("내용을 입력하세요");
    $("#board_contents").focus();
    return false;
  }
  alert("등록이 완료되었습니다.");
  window.location.reload();
  return true;
}

// 5. updateBoard --------------------------------------------------------------------------------->
function updateBoard() {
  if ($("#board_title").val() == "") {
    alert("제목을 입력하세요");
    $("#board_title").focus();
    return false;
  }
  if ($("#board_contents").val() == "") {
    alert("내용을 입력하세요");
    $("#board_contents").focus();
    return false;
  }
  alert("수정이 완료되었습니다.");
  window.location.reload();
  return true;
}