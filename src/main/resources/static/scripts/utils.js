/** ------------------------------------------------------------------------------------------------
* @param null
* @return {boolean}
* @desc 로그인 회원 알림
**/
function sessionAlert() {
  alert("로그인한 회원만 가능합니다.")
  location.href = `/${title}/member/loginMember`;
  return false;
}

/** ------------------------------------------------------------------------------------------------
* @param null
* @return {boolean}
* @desc 관리자 알림
**/
function adminAlert() {
  alert("관리자만 가능합니다.")
  location.href = `/${title}/member/loginMember`;
  return false;
}

/** ------------------------------------------------------------------------------------------------
* @param null
* @return {void}
* @desc 좋아요 버튼
**/
function likeButton() {

  const currentUrl = window.location.href;
  const title = currentUrl.split("/")[3];
  const preFix1 = currentUrl.split("/")[4];
  const preFix2 = preFix1.charAt(0).toUpperCase() + preFix1.slice(1);
  const number = getValue(getById(`${preFix1}_number`));

  const likeBtn = getById('likeBtn');
  const likeCount = getById('likeCount');

  let likeNum = 0;
  function addParticle() {
    const particle = document.createElement('span');
    particle.classList.add('particle');
    particle.innerText = '+1';
    likeBtn?.appendChild(particle);

    setTimeout(() => {
      likeBtn?.removeChild(particle);
    }, 1000);
  }
  function onLikeBtnClick() {
    likeNum++;
    likeBtn?.classList.add('liked');
    addParticle();
    likeCount && (likeCount.innerText = likeNum.toString());
  }

  likeBtn?.addEventListener('click', onLikeBtnClick);
  document.addEventListener('DOMContentLoaded', likeButton);

  $.ajax({
    url: `/${title}/${preFix1}/updateLike?${preFix1}_number=${number}`,
    type: "GET",
    success: function(response) {
      if(response == 200) {
        location.reload();
      }
      else {
        location.reload();
      }
    }
  });
};

/** ------------------------------------------------------------------------------------------------
* @param null
* @return {void}
* @desc 싫어요 버튼
**/
function dislikeButton() {

  const currentUrl = window.location.href;
  const title = currentUrl.split("/")[3];
  const preFix1 = currentUrl.split("/")[4];
  const preFix2 = preFix1.charAt(0).toUpperCase() + preFix1.slice(1);
  const number = getValue(getById(`${preFix1}_number`));

  const dislikeBtn = getById('dislikeBtn');
  const dislikeCount = getById('dislikeCount');

  let dislikeNum = 0;
  function addParticle() {
    const particle = document.createElement('span');
    particle.classList.add('particle');
    particle.innerText = '+1';
    dislikeBtn?.appendChild(particle);

    setTimeout(() => {
      dislikeBtn?.removeChild(particle);
    }, 1000);
  }
  function onDislikeBtnClick() {
    dislikeNum++;
    dislikeBtn?.classList.add('disliked');
    addParticle();
    dislikeCount && (dislikeCount.innerText = dislikeNum.toString());
  }

  dislikeBtn?.addEventListener('click', onDislikeBtnClick);
  document.addEventListener('DOMContentLoaded', dislikeButton);

  $.ajax({
    url: `/${title}/${preFix1}/updateDislike?${preFix1}_number=${number}`,
    type: "GET",
    success: function(response) {
      if(response == 200) {
        location.reload();
      }
      else {
        location.reload();
      }
    }
  });
};