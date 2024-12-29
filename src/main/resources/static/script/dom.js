/** ------------------------------------------------------------------------------------------------
* @namespace Getter
* @description DOM 데이터 가져오기
**/

/** ------------------------------------------------------------------------------------------------
* @memberof Getter
* @param {string} el
* @returns {Element | null}
* @description 쿼리 셀렉터
**/
const getEl = (el) => {
  const element = document.querySelector(el);

  if (!element) {
    return null;
  }

  return element;
}

/** ------------------------------------------------------------------------------------------------
* @memberof Getter
* @param {string} el
* @returns {NodeListOf<Element> | []}
* @description 쿼리 셀렉터 올
**/
const getElAll = (el) => {
  const elements = document.querySelectorAll(el);

  if (!elements || elements.length === 0) {
    return [];
  }

  return elements;
}

/** ------------------------------------------------------------------------------------------------
* @memberof Getter
* @param {string} el
* @returns {HTMLElement | null}
* @description 아이디로 찾기
**/
const getById = (el) => {
  const element = document.getElementById(el);

  if (!element) {
    return null;
  }

  return element;
}

/** ------------------------------------------------------------------------------------------------
* @memberof Getter
* @param {string} el
* @returns {HTMLCollectionOf<Element> | null}
* @description 클래스로 찾기
**/
const getByClass = (el) => {
  const element = document.getElementsByClassName(el);
  if (!element || element.length === 0) {
    return null;
  }

  return element;
}

/** ------------------------------------------------------------------------------------------------
* @memberof Getter
* @param {string} el
* @returns {NodeListOf<HTMLElement> | null}
* @description name값으로 찾기
**/
const getByName = (el) => {
  const element = document.getElementsByName(el);
  if (!element || element.length === 0) {
    return null;
  }

  return element;
}

/** ------------------------------------------------------------------------------------------------
* @memberof Getter
* @param {string} el
* @returns {HTMLFormElement | null}
* @description form 찾기
**/
const getFormByName = (el) => {
  const element = document.forms[el];
  if (!element) {
    return null;
  }

  return element;
}

/** ------------------------------------------------------------------------------------------------
* @memberof Getter
* @param {HTMLElement} el
* @returns {string | null}
* @description innerHTML 값 얻기
**/
const getInnerHTML = (el) => {
  if (!el || !(el instanceof HTMLElement)) {
    return null;
  }

  return String(el.innerHTML).trim();
}

/** ------------------------------------------------------------------------------------------------
* @memberof Getter
* @param {HTMLElement} el
* @returns {string | null}
* @description textContent 값 얻기
**/
const getTextContent = (el) => {
  if (!el || !(el instanceof HTMLElement)) {
    return null;
  }

  return String(el.textContent).trim();
}

/** ------------------------------------------------------------------------------------------------
* @memberof Getter
* @param {HTMLElement | null} el
* @returns {string | null}
* @description value 값 얻기
**/
const getValue = (el) => {
  if (!el || !(el instanceof HTMLInputElement || el instanceof HTMLTextAreaElement || el instanceof HTMLSelectElement || el instanceof HTMLButtonElement || el instanceof HTMLOptionElement)) {
    return null;
  }

  return String(el.value).trim();
}


/** ------------------------------------------------------------------------------------------------
* @namespace Setter
* @description DOM 데이터 설정하기
**/

/** ------------------------------------------------------------------------------------------------
* @memberof Setter
* @param {HTMLElement} el
* @param {string} className
* @returns {void}
* @description 클래스 추가
**/
const addClass = (el, className) => {
  if (!el || !(el instanceof HTMLElement)) {
    return;
  }

  el.classList.add(className);
}

/** ------------------------------------------------------------------------------------------------
* @memberof Setter
* @param {HTMLElement} el
* @param {string} className
* @returns {void}
* @description 클래스 제거
**/
const removeClass = (el, className) => {
  if (!el || !(el instanceof HTMLElement)) {
    return;
  }

  el.classList.remove(className);
}

/** ------------------------------------------------------------------------------------------------
* @memberof Setter
* @param {HTMLElement} el
* @param {string} text
* @returns {void}
* @description value 값 설정
**/
const setValue = (el, text) => {
  if (!el || !(el instanceof HTMLInputElement || el instanceof HTMLTextAreaElement || el instanceof HTMLSelectElement || el instanceof HTMLButtonElement || el instanceof HTMLOptionElement)) {
    return;
  }

  el.value = text ? String(text).trim() : "";
};

/** ------------------------------------------------------------------------------------------------
* @memberof Setter
* @param {HTMLElement} el
* @param {string} text
* @returns {void}
* @description innerHTML 값 설정
**/
const setInnerHTML = (el, text) => {
  if (!el || !(el instanceof HTMLElement)) {
    return;
  }

  el.innerHTML = text ? String(text).trim() : "";
};

/** ------------------------------------------------------------------------------------------------
* @memberof Setter
* @param {HTMLElement} el
* @param {string} text
* @returns {void}
* @description textContent 값 설정
**/
const setTextContent = (el, text) => {
  if (!el || !(el instanceof HTMLElement)) {
    return;
  }

  el.textContent = text ? String(text).trim() : "";
};

/** ------------------------------------------------------------------------------------------------
* @namespace DOM
* @description DOM 데이터 조작
**/

/** ------------------------------------------------------------------------------------------------
* @memberof DOM
* @param {string} el
* @returns {void}
* @description 페이지 이동
**/
const goPage = (el) => {
  window.location.href = el;
};
