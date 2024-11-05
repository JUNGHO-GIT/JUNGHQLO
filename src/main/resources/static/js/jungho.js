/** ----------------------------------------------------------------------------------------------->
* @param {string} id
* @desc 50,000 ~ 150,000 사이의 랜덤 가격을 생성
**/
(function() {
  function setRandomPrice(ids) {
    let minPrice = 50000;
    let maxPrice = 150000;

    ids.forEach(id => {
      let randomPrice = Math.floor(Math.random() * (maxPrice - minPrice + 1)) + minPrice;
      const element = document.getElementById(id);
      element && (element.innerText = numberWithCommas(randomPrice));
    });
  }

  document.addEventListener("DOMContentLoaded", function() {
    const priceIds = Array.from({ length: 8 }, (_, i) => `price-${i + 1}`);
    setRandomPrice(priceIds);
  });
})();

/** ----------------------------------------------------------------------------------------------->
* @param {number} x
* @desc 3자리마다 콤마 찍기
**/
function numberWithCommas(x) {
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

/** ----------------------------------------------------------------------------------------------->
* @desc 로그인 회원 알림
**/
function sessionAlert() {
  alert("로그인한 회원만 가능합니다.")
  location.href = `/${TITLE}/member/loginMember`;
  return false;
};

/** ----------------------------------------------------------------------------------------------->
* @desc 관리자 알림
**/
function adminAlert() {
  alert("관리자만 가능합니다.")
  location.href = `/${TITLE}/member/loginMember`;
  return false;
};

/** ----------------------------------------------------------------------------------------------->
* @desc 수정 알림
**/
function updateAlert() {

  const sessionId = getValue(getById("sessionId"));
  const writerId = getValue(getById("writerId"));
  const uniqueNumber = getValue(getById("uniqueNumber"));
  const subFix = getName(getById("uniqueNumber"));

  const preFix1 = subFix.split("_")[0];
  const preFix2 = preFix1.charAt(0).toUpperCase() + preFix1.slice(1);

  if (sessionId != writerId || sessionId == null) {
    alert("본인만 수정 가능합니다.");
    return false;
  }
  else {
    location.href = (
      `/JUNGHQLO/${preFix1}/update${preFix2}?${preFix1}_number=${uniqueNumber}`
    );
    return true;
  }
};

/** ----------------------------------------------------------------------------------------------->
* @desc 수정 확인
**/
function updateCheck() {
  const userConfirm = confirm("정말 수정하시겠습니까?");
  if (userConfirm == true) {
    return true;
  }
  else {
    alert("수정이 취소되었습니다.");
    return false;
  }
};

/** ----------------------------------------------------------------------------------------------->
* @desc 삭제 알림
**/
function deleteAlert() {

  const sessionId = getValue(getById("sessionId"));
  const writerId = getValue(getById("writerId"));
  const uniqueNumber = getValue(getById("uniqueNumber"));
  const subFix = getName(getById("uniqueNumber"));

  const preFix1 = subFix.split("_")[0];
  const preFix2 = preFix1.charAt(0).toUpperCase() + preFix1.slice(1);

  if (sessionId != writerId || sessionId == null) {
    alert("본인만 삭제 가능합니다.");
    return false;
  }
  else {
    location.href = (
      `/JUNGHQLO/${preFix1}/delete${preFix2}?${preFix1}_number=${uniqueNumber}`
    );
    return true;
  }
};

/** ----------------------------------------------------------------------------------------------->
* @desc 삭제 확인
**/
function deleteCheck() {
  const userConfirm = confirm("정말 삭제하시겠습니까?");
  if (userConfirm == true) {
    return true;
  }
  else {
    alert("삭제가 취소되었습니다.");
    window.history.back();
    return false;
  }
};

/** ---------------------------------------------------------------------------------------------->
* @desc 랜덤 이미지
**/
function randomSideImage() {
  /**
  * @param {HTMLElement} element
  **/
  function setImageURL(element) {
    let usedValues = [0];
    let randomValue = Math.random() * 35 + 1;
    let intValue = Math.floor(randomValue);
    let finalImageUrl = "";

    while (usedValues.includes(intValue)) {
      randomValue = Math.random() * 35 + 1;
      intValue = Math.floor(randomValue);
    }
    usedValues.push(intValue);

    finalImageUrl = `https://storage.googleapis.com/jungho-bucket/JUNGHQLO/IMAGE/product/all/product-${intValue}.jpg`;

    const bgElement = element.getElementsByClassName('set-bg')[0];
    if (bgElement instanceof HTMLElement) {
      bgElement.style.backgroundImage = `url(${finalImageUrl})`;
    }
  }
  document.querySelectorAll('.nav-link').forEach((element) => {
    if (element instanceof HTMLElement) {
      setImageURL(element);
    }
  });
}
window.randomSideImage = randomSideImage;

/** ----------------------------------------------------------------------------------------------->
* @desc 좋아요 버튼
**/
function likeButton() {

  const likeBtn = getById('likeBtn');
  const likeCount = getById('likeCount');
  const uniqueNumber = getById("uniqueNumber");
  const subFix = getName(getById("uniqueNumber"));

  let likeNum = 0;
  const preFix1 = subFix.split("_")[0];

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
    url: `/JUNGHQLO/${preFix1}/updateLike?${preFix1}_number=${uniqueNumber}`,
    type: "GET",
    /**
    * @param {any} response
    **/
    success: function(response) {
      if(response == 200) {
        window.location.reload();
      }
      else {
        window.location.reload();
      }
    }
  });
};

/** ----------------------------------------------------------------------------------------------->
* @desc 싫어요 버튼
**/
function dislikeButton() {

  const dislikeBtn = getById('dislikeBtn');
  const dislikeCount = getById('dislikeCount');
  const uniqueNumber = getById("uniqueNumber");
  const subFix = getName(getById("uniqueNumber"));

  let dislikeNum = 0;
  const preFix1 = subFix.split("_")[0];

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
    url: `/JUNGHQLO/${preFix1}/updateDislike?${preFix1}_number=${uniqueNumber}`,
    type: "GET",
    /**
    * @param {any} response
    **/
    success: function(response) {
      if(response == 200) {
        window.location.reload();
      }
      else {
        window.location.reload();
      }
    }
  });
};

// 0. slider -------------------------------------------------------------------------------------->
document.addEventListener("DOMContentLoaded", function() {
  $('.slider').slick({
    infinite: true,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 1500,
    dots: false,
    fade: true,
    cssEase: 'linear'
  });
});

// 0. searchForm ---------------------------------------------------------------------------------->
document.addEventListener("DOMContentLoaded", function() {
  const searchIcon = document.getElementById("searchIcon");
  const hiddenSubmit = document.getElementById("hiddenSubmit");

  if (searchIcon && hiddenSubmit) {
    searchIcon.addEventListener("click", function() {
      hiddenSubmit.click();
    });
  }
});