/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 공지사항 입력 유효성 검사
**/
function addNotice() {
  if (getValue(getById("notice_title")) == "") {
    alert("제목을 입력하세요");
    getById("notice_title").focus();
    return false;
  }
  if (getValue(getById("notice_contents")) == "") {
    alert("내용을 입력하세요");
    getById("notice_contents").focus();
    return false;
  }
  alert("등록이 완료되었습니다.");
  window.location.reload();
  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 공지사항 수정 유효성 검사
**/
function updateNotice() {
  if (getValue(getById("notice_title")) == "") {
    alert("제목을 입력하세요");
    getById("notice_title").focus();
    return false;
  }
  if (getValue(getById("notice_contents")) == "") {
    alert("내용을 입력하세요");
    getById("notice_contents").focus();
    return false;
  }
  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 공지사항 좋아요 기능
**/
function likeNotice() {

  const likeBtn = getById('likeBtn');
  const likeCount = getById('likeCount');
  const uniqueNumber = getValue(getById("uniqueNumber"));

  let likeNum = 0;
  function addParticle() {
    const particle = document.createElement('span');
    particle.classList.add('particle');
    particle.innerText = '+1';
    likeBtn.appendChild(particle);

    setTimeout(() => {
      likeBtn.removeChild(particle);
    }, 1000);
  }

  function onLikeBtnClick() {
    likeNum++;
    likeBtn.classList.add('liked');
    addParticle();
    likeCount.innerText = likeNum.toString();
  }

  likeBtn.addEventListener('click', onLikeBtnClick);
  document.addEventListener('DOMContentLoaded', likeButton);

  $.ajax({
    url: `/${TITLE}/notice/updateLike`,
    type: "GET",
    data: {
      notice_number: uniqueNumber
    },
    success: function(response) {
      if(response == 200) {
        window.location.reload();
      }
      else {
        window.location.reload();
      }
    }
  });

  return true;
};

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 공지사항 싫어요 기능
**/
function dislikeNotice() {

  const dislikeBtn = getById('dislikeBtn');
  const dislikeCount = getById('dislikeCount');
  const uniqueNumber = getValue(getById("uniqueNumber"));

  let dislikeNum = 0;
  function addParticle() {
    const particle = document.createElement('span');
    particle.classList.add('particle');
    particle.innerText = '+1';
    dislikeBtn.appendChild(particle);

    setTimeout(() => {
      dislikeBtn.removeChild(particle);
    }, 1000);
  }

  function onDislikeBtnClick() {
    dislikeNum++;
    dislikeBtn.classList.add('disliked');
    addParticle();
    dislikeCount.innerText = dislikeNum.toString();
  }

  dislikeBtn.addEventListener('click', onDislikeBtnClick);
  document.addEventListener('DOMContentLoaded', dislikeButton);

  $.ajax({
    url: `/${TITLE}/notice/updateDislike`,
    type: "GET",
    data: {
      notice_number: uniqueNumber
    },
    success: function(response) {
      if(response == 200) {
        window.location.reload();
      }
      else {
        window.location.reload();
      }
    }
  });

  return true;
};