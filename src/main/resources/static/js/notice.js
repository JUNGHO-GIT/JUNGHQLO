// 4. addNotice ----------------------------------------------------------------------------------->
function addNotice() {
  if ($("#notice_title").val() == "") {
    alert("제목을 입력하세요");
    $("#board_title").focus();
    return false;
  }
  if ($("#notice_contents").val() == "") {
    alert("내용을 입력하세요");
    $("#board_contents").focus();
    return false;
  }
  alert("등록이 완료되었습니다.");
  window.location.reload();
  return true;
}

// 5. updateNotice -------------------------------------------------------------------------------->
function updateNotice() {
  if ($("#notice_title").val() == "") {
    alert("제목을 입력하세요");
    $("#board_title").focus();
    return false;
  }
  if ($("#notice_contents").val() == "") {
    alert("내용을 입력하세요");
    $("#board_contents").focus();
    return false;
  }
  alert("수정이 완료되었습니다.");
  window.location.reload();
  return true;
}

// 5-2. likeNotice -------------------------------------------------------------------------------->
function likeNotice() {
  const likeBtn = document.getElementById('likeBtn');
  const likeCount = document.getElementById('likeCount');
  const uniqueNumber = document.getElementById("uniqueNumber");
  let likecount = 0;

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
    likecount++;
    likeBtn.classList.add('liked');
    addParticle();
    likeCount.innerText = likecount;
  }
  likeBtn.addEventListener('click', onLikeBtnClick);
  document.addEventListener('DOMContentLoaded', likeNotice);

  $.ajax({
    url: "/JUNGHQLO/notice/updateLike?notice_number="+uniqueNumber.value,
    type: "GET",
    success: function(response) {
      if(response.status == 200) {
        window.location.reload();
      }
      else {
        window.location.reload();
      }
    }
  });
}

// 5-3. dislikeNotice ----------------------------------------------------------------------------->
function dislikeNotice() {
  const dislikeBtn = document.getElementById('dislikeBtn');
  const dislikeCount = document.getElementById('dislikeCount');
  const uniqueNumber = document.getElementById("uniqueNumber");
  let dislikecount = 0;

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
    dislikecount++;
    dislikeBtn.classList.add('disliked');
    addParticle();
    dislikeCount.innerText = dislikecount;
  }
  dislikeBtn.addEventListener('click', onDislikeBtnClick);
  document.addEventListener('DOMContentLoaded', dislikeNotice);

  $.ajax({
    url: "/JUNGHQLO/notice/updateDislike?notice_number="+uniqueNumber.value,
    type: "GET",
    success: function(response) {
      if(response.status == 200) {
        window.location.reload();
      }
      else {
        window.location.reload();
      }
    }
  });
}