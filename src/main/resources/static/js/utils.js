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
    throw new Error(`Element with id ${el} not found`);
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
    throw new Error(`Element with class ${el} not found`);
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
    throw new Error(`Element with name ${el} not found`);
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
    throw new Error(`Element with innerHTML ${el} not found`);
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
    throw new Error(`Element with value ${el} not found`);
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
    throw new Error(`Element with value ${el} not found`);
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
    throw new Error(`Element with innerHTML ${el} not found`);
  }
}

/** ----------------------------------------------------------------------------------------------->
* @desc 수정하기 페이지로 이동
**/
function goUpdate() {
  const uniqueNumber = getValue(getById("uniqueNumber"));
  const subFix = getName(getById("uniqueNumber"));

  const preFix1 = subFix.split("_")[0];
  const preFix2 = preFix1.charAt(0).toUpperCase() + preFix1.slice(1);

  goToPage(`/${TITLE}/${preFix1}/update${preFix2}`, `${preFix1}_number`, uniqueNumber);
}

/** ----------------------------------------------------------------------------------------------->
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

/** ----------------------------------------------------------------------------------------------->
* @desc 삭제하기 페이지로 이동
**/
function goDelete() {
  const uniqueNumber = getValue(getById("uniqueNumber"));
  const subFix = getName(getById("uniqueNumber"));

  const preFix1 = subFix.split("_")[0];
  const preFix2 = preFix1.charAt(0).toUpperCase() + preFix1.slice(1);

  goToPage(`/${TITLE}/${preFix1}/delete${preFix2}`, `${preFix1}_number`, uniqueNumber);
};

/** ----------------------------------------------------------------------------------------------->
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