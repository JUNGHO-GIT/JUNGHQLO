/**
* @author Jungho
* @since 2023-03-13
* @since 2023-04-08
* @desc 주제 : 1.board / 2.member / 3.notice / 4.orders / 5.product / 6.qna
* @desc 순서 : 1.list / 2.details / 3.find / 4.sort / 5.add / 6.update / 7.delete
**/

// 0. slider -------------------------------------------------------------------------------------->
document.addEventListener("DOMContentLoaded", function() {
  $('.jungho-slider').slick({
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
  const hiddenSubmit = document.querySelector("#hiddenSubmit");

  searchIcon.addEventListener("click", function() {
    hiddenSubmit.click();
  });
});


// 0. main random price make ---------------------------------------------------------------------->
function setRandomPrice(id) {
  var minPrice = 50000;
  var maxPrice = 150000;
  var randomPrice = Math.floor(Math.random() * (maxPrice - minPrice + 1)) + minPrice;
  document.getElementById(id).innerText = numberWithCommas(randomPrice);
}
function numberWithCommas(x) {
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
document.addEventListener("DOMContentLoaded", function() {
  for (var i = 1; i <= 2; i++) {
    setRandomPrice("price-" + i);
  }
  for (var i = 5; i <= 6; i++) {
    setRandomPrice("price-" + i);
  }
});


// 1. sessionAlert() ------------------------------------------------------------------------------>
function sessionAlert() {
  alert("로그인한 회원만 가능합니다.")
  window.location.href = "/JUNGHQLO/member/loginMember";
  return false;
}

// 1. adminAlert() -------------------------------------------------------------------------------->
function adminAlert() {
  alert("관리자만 가능합니다.")
  window.location.href = "/JUNGHQLO/member/loginMember";
  return false;
}

// 2. updateAlert() ------------------------------------------------------------------------------->
function updateAlert() {
  const sessionId = document.getElementById("sessionId").value;
  const writerId = document.getElementById("writerId").value;
  const uniqueNumber = document.getElementById("uniqueNumber").value;

  // ex) board_number
  const subFix = document.getElementById("uniqueNumber").name;
  // ex) board
  const preFix1 = subFix.split("_")[0];
  // ex) Board
  const preFix2 = preFix1.substr(0, 1).toUpperCase()+preFix1.substr(1,preFix1.length-1);

  if (sessionId != writerId || sessionId == null) {
    alert("본인만 수정 가능합니다.");
    return false;
  }
  else {
    window.location.href = "/JUNGHQLO/"+preFix1+"/update"+preFix2+"?"+preFix1+"_number="+uniqueNumber;
    return true;
  }
}

// 2-1. updateCheck() ---------------------------------------------------------------------------->
function updateCheck() {
  const userConfirm = confirm("정말 수정하시겠습니까?");
  if (userConfirm == true) {
    return true;
  }
  else {
    alert("수정이 취소되었습니다.");
    return false;
  }
}

// 3. deleteAlert() ------------------------------------------------------------------------------->
function deleteAlert() {
  const sessionId = document.getElementById("sessionId").value;
  const writerId = document.getElementById("writerId").value;
  const uniqueNumber = document.getElementById("uniqueNumber").value;

  // ex) board_number
  const subFix = document.getElementById("uniqueNumber").name;
  // ex) board
  const preFix1 = subFix.split("_")[0];
  // ex) Board
  const preFix2 = preFix1.substr(0, 1).toUpperCase()+preFix1.substr(1,preFix1.length-1);

  if (sessionId != writerId || sessionId == null) {
    alert("본인만 삭제 가능합니다.");
    return false;
  }
  else {
    window.location.href = "/JUNGHQLO/"+preFix1+"/delete"+preFix2+"?"+preFix1+"_number="+uniqueNumber;
    return true;
  }
}

// 3-1. deleteCheck() ----------------------------------------------------------------------------->
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
}

// 4. randomSideImage() --------------------------------------------------------------------------->
function randomSideImage() {
  var usedValues = [];

  function setImageURL(element) {
    var randomValue = Math.random() * 35 + 1;
    var intValue = Math.floor(randomValue);

    while (usedValues.includes(intValue)) {
      randomValue = Math.random() * 35 + 1;
      intValue = Math.floor(randomValue);
    }

    usedValues.push(intValue);
    var finalImageUrl = 'https://storage.googleapis.com/jungho-bucket/JUNGHQLO/IMAGE/product/all/product-' + intValue + '.jpg';
    element.getElementsByClassName('set-bg')[0].style.backgroundImage = 'url(' + finalImageUrl + ')';
  }
  document.querySelectorAll('.nav-link').forEach((element) => setImageURL(element));
}
window.randomSideImage = randomSideImage;

// 5. likeButton() -------------------------------------------------------------------------------->
function likeButton() {
  const likeBtn = document.getElementById('likeBtn');
  const likeCount = document.getElementById('likeCount');
  const uniqueNumber = document.getElementById("uniqueNumber").value;
  let likecount = 0;

  // ex) board_number
  const subFix = document.getElementById("uniqueNumber").name;
  // ex) board
  const preFix1 = subFix.split("_")[0];

  function onLikeBtnClick() {
    likecount++;
    likeBtn.classList.add('liked');
    addParticle();
    likeCount.innerText = likecount;
  }
  likeBtn.addEventListener('click', onLikeBtnClick);
  document.addEventListener('DOMContentLoaded', likeButton);

  $.ajax({
    url: "/JUNGHQLO/"+preFix1+"/updateLike"+"?"+preFix1+"_number="+uniqueNumber,
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
}

// 6. dislikeButton() ----------------------------------------------------------------------------->
function dislikeButton() {
  const dislikeBtn = document.getElementById('dislikeBtn');
  const dislikeCount = document.getElementById('dislikeCount');
  const uniqueNumber = document.getElementById("uniqueNumber").value;
  let dislikecount = 0;

  // ex) board_number
  const subFix = document.getElementById("uniqueNumber").name;
  // ex) board
  const preFix1 = subFix.split("_")[0];

  function onDislikeBtnClick() {
    dislikecount++;
    dislikeBtn.classList.add('disliked');
    addParticle();
    dislikeCount.innerText = dislikecount;
  }
  dislikeBtn.addEventListener('click', onDislikeBtnClick);
  document.addEventListener('DOMContentLoaded', dislikeButton);

  $.ajax({
    url: "/JUNGHQLO/"+preFix1+"/updateDislike"+"?"+preFix1+"_number="+uniqueNumber,
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
}