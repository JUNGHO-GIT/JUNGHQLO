/** ----------------------------------------------------------------------------------------------->
* @desc 로그인 회원 알림
**/
function sessionAlert() {
  alert("로그인한 회원만 가능합니다.")
  location.href = `/${title}/member/loginMember`;
  return false;
};

/** ----------------------------------------------------------------------------------------------->
* @desc 관리자 알림
**/
function adminAlert() {
  alert("관리자만 가능합니다.")
  location.href = `/${title}/member/loginMember`;
  return false;
};

/** ----------------------------------------------------------------------------------------------->
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

/** ----------------------------------------------------------------------------------------------->
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

// -------------------------------------------------------------------------------------------------
const logFormData = () => {
  const formData = getFormByName("formData");

  // 모든 폼 요소 출력 함수
  const logFormValues = () => {
    console.log("===========================================================");
    Array.from(formData.elements).forEach((input) => {
      if (input instanceof HTMLInputElement) {
        if (input.type === 'file') {
          // 파일 입력의 경우 선택된 파일명만 출력
          console.log(`
            ${input.name}: ${input.files.length > 0 ? input.files[0].name : 'No file selected'}
          `);
        }
        else {
          // 그 외의 input 및 textarea 값 출력
          console.log(`
            ${input.name}: ${input.value}
          `);
        }
      }
    });
  };

  // 변경 사항을 감지하는 콜백 함수 (MutationObserver용)
  const callback = (mutationsList) => {
    mutationsList.forEach(mutation => {
      // DOM에 input[type="file"]이 추가되었을 때 이벤트 바인딩
      if (mutation.type === 'childList') {
        mutation.addedNodes.forEach(node => {
          if (node instanceof HTMLInputElement && node.type === 'file') {
            console.log(`New file input : ${node.name}`);
            // 새로 추가된 file input에 change 이벤트 리스너 추가
            node.addEventListener('change', (event) => {
              const inputElement = event.target;
              console.log(`${inputElement.name}: ${inputElement.files.length > 0 ? inputElement.files[0].name : 'No file selected'}`);
            });
          }
        });
      }

      // 모든 폼 요소 출력 (value 속성 변경 감지)
      if (mutation.type === 'attributes' && mutation.attributeName === 'value') {
        logFormValues();  // value가 변경되었을 때 모든 폼 값 출력
      }
    });
  };

  // MutationObserver 설정
  const observer = new MutationObserver(callback);

  // 감시할 옵션 설정
  const config = {
    attributes: true,
    childList: true,
    subtree: true,
    characterData: true,
  };

  // 폼 내의 기존 input, textarea 요소들에 대해 옵저버 설정
  Array.from(formData.querySelectorAll('input, textarea')).forEach(inputElement => {
    observer.observe(inputElement, config);
  });

  // 사용자가 input 값을 변경할 때마다 폼 내 모든 요소 값 출력
  formData.addEventListener('input', () => {
    logFormValues();  // 폼 값 변경 시 바로 출력
  });

  // 동적으로 추가된 input[type="file"]의 change 이벤트를 처리
  Array.from(formData.querySelectorAll('input[type="file"]')).forEach(inputFile => {
    inputFile.addEventListener('change', (event) => {
      const inputElement = event.target;
      console.log(`${inputElement.name}: ${inputElement.files.length > 0 ? inputElement.files[0].name : 'No file selected'}`);
    });
  });
};