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
};

/** ------------------------------------------------------------------------------------------------
* @param {string} url
* @param {string} param
* @param {string} value
* @return {void}
* @desc url로 페이지 이동
**/
function goToPage(url, param, value) {
  if (url && !param && !value) {
    window.location.href = url;
  }
  else if (url && param && !value) {
    window.location.href = `${url}?${param}=''`;
  }
  else {
    window.location.href = `${url}?${param}=${value}`;
  }
};

/** ------------------------------------------------------------------------------------------------
* @param {string}
* @return {void}
* @desc 홈으로 이동
**/
function goHome() {
  window.location.href = `/${TITLE}`;
}

/** ----------------------------------------------------------------------------------------------->
* @param {string} el
* @return {HTMLElement}
* @desc 아이디로 찾기
**/
function getById(el) {
  const element = document.getElementById(el);
  if (element) {
    return element;
  }
  else {
    console.error(`Element with id ${el} not found`);
  }
}

/** ----------------------------------------------------------------------------------------------->
* @param {string} el
* @return {HTMLCollectionOf<Element>}
* @desc 클래스로 찾기
**/
function getByClass(el) {
  const element = document.getElementsByClassName(el);
  if (element) {
    return element;
  }
  else {
    console.error(`Element with class ${el} not found`);
  }
}

/** ----------------------------------------------------------------------------------------------->
* @param {HTMLElement} el
* @return {string}
* @desc name 값 얻기
**/
function getName(el) {
  const element = el instanceof HTMLInputElement ? el.name : null;
  if (element) {
    return element;
  }
  else {
    console.error(`Element with name ${el} not found`);
  }
};

/** ----------------------------------------------------------------------------------------------->
* @param {HTMLElement} el
* @return {string}
* @desc innerHTML 값 얻기
**/
function getInnerHTML(el) {
  const element = el instanceof HTMLElement ? el.innerHTML : null;
  if (element) {
    return element;
  }
  else {
    console.error(`Element with innerHTML ${el} not found`);
  }
}

/** ----------------------------------------------------------------------------------------------->
* @param {HTMLElement} el
* @return {string}
* @desc value 값 얻기
**/
function getValue(el) {
  if (el && el instanceof HTMLElement) {
    return el.value;
  }
  else {
    console.error(`Element with value ${el} not found`);
  }
};

/** ----------------------------------------------------------------------------------------------->
* @param {HTMLElement} el
* @param {string} text
* @return {void}
* @desc value 값 설정
**/
function setValue(el, text) {
  if (el && el instanceof HTMLInputElement) {
    el.value = text;
  }
  else {
    console.error(`Element with value ${el} not found`);
  }
};

/** ----------------------------------------------------------------------------------------------->
* @param {HTMLElement} el
* @param {string} text
* @return {void}
* @desc innerHTML 값 설정
**/
function setInnerHTML(el, text) {
  if (el && el instanceof HTMLElement) {
    el.innerHTML = text;
  }
  else {
    console.error(`Element with innerHTML ${el} not found`);
  }
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

/** ------------------------------------------------------------------------------------------------
* @param {null}
* @return {void}
* @desc 수정하기 페이지로 이동
**/
function goUpdate() {
  const uniqueNumber = getValue(getById("uniqueNumber"));
  const subFix = getName(getById("uniqueNumber"));

  const preFix1 = subFix.split("_")[0];
  const preFix2 = preFix1.charAt(0).toUpperCase() + preFix1.slice(1);

  goToPage(`/${TITLE}/${preFix1}/update${preFix2}`, `${preFix1}_number`, uniqueNumber);
}

/** ------------------------------------------------------------------------------------------------
* @param {null}
* @return {void}
* @desc 수정 하기
**/
function getUpdate() {

  const sessionId = getValue(getById("sessionId"));
  const writerId = getValue(getById("writerId"));
  const uniqueNumber = getValue(getById("uniqueNumber"));
  const subFix = getName(getById("uniqueNumber"));

  const preFix1 = subFix.split("_")[0];
  const preFix2 = preFix1.charAt(0).toUpperCase() + preFix1.slice(1);

  if (confirm("수정하시겠습니까?") == false) {
    return false;
  }
  else if (sessionId != writerId || sessionId == null) {
    alert("본인만 수정 가능합니다.");
    return false;
  }
  else {
    $.ajax({
      url: `/${TITLE}/${preFix1}/update${preFix2}?${preFix1}_number=${uniqueNumber}`,
      type: "POST",
      success: function(response) {
        if(response === 1) {
          alert("수정되었습니다.");
          location.href = `/${TITLE}/${preFix1}/list${preFix2}`;
        }
        else if (response === 0) {
          alert("수정 실패했습니다.");
          location.reload();
        }
        else {
          alert("오류가 발생했습니다.");
          location.reload();
        }
      }
    });
  }
};

/** ------------------------------------------------------------------------------------------------
* @param {null}
* @return {void}
* @desc 삭제하기 페이지로 이동
**/
function goDelete() {
  const uniqueNumber = getValue(getById("uniqueNumber"));
  const subFix = getName(getById("uniqueNumber"));

  const preFix1 = subFix.split("_")[0];
  const preFix2 = preFix1.charAt(0).toUpperCase() + preFix1.slice(1);

  goToPage(`/${TITLE}/${preFix1}/delete${preFix2}`, `${preFix1}_number`, uniqueNumber);
};

/** ------------------------------------------------------------------------------------------------
* @param {null}
* @return {void}
* @desc 삭제
**/
function getDelete() {

  const sessionId = getValue(getById("sessionId"));
  const writerId = getValue(getById("writerId"));
  const uniqueNumber = getValue(getById("uniqueNumber"));
  const subFix = getName(getById("uniqueNumber"));

  const preFix1 = subFix.split("_")[0];
  const preFix2 = preFix1.charAt(0).toUpperCase() + preFix1.slice(1);

  if (confirm("삭제하시겠습니까?") == false) {
    return false;
  }
  else if (sessionId != writerId || sessionId == null) {
    alert("본인만 삭제 가능합니다.");
    return false;
  }
  else {
    $.ajax({
      url: `/${TITLE}/${preFix1}/delete${preFix2}?${preFix1}_number=${uniqueNumber}`,
      type: "POST",
      success: function(response) {
        if(response === 1) {
          alert("삭제되었습니다.");
          location.href = `/${TITLE}/${preFix1}/list${preFix2}`;
        }
        else if (response === 0) {
          alert("삭제 실패했습니다.");
          location.reload();
        }
        else {
          alert("오류가 발생했습니다.");
          location.reload();
        }
      }
    });
  }
};

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
    url: `/${TITLE}/${preFix1}/updateLike?${preFix1}_number=${uniqueNumber}`,
    type: "GET",
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
    url: `/${TITLE}/${preFix1}/updateDislike?${preFix1}_number=${uniqueNumber}`,
    type: "GET",
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

/** ---------------------------------------------------------------------------------------------->
* @desc 랜덤 이미지
**/
function randomSideImage() {
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

    finalImageUrl = `${STORAGE}/product/all/product-${intValue}.jpg`;

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

// 0. slider ---------------------------------------------------------------------------------------
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