/** ------------------------------------------------------------------------------------------------
* @param {string} el
* @return {Element | null}
* @desc 쿼리 셀렉터
**/
function getEl(el) {
  const element = document.querySelector(el);

  if (!element) {
    return null;
  }

  return element;
}

/** ------------------------------------------------------------------------------------------------
* @param {string} el
* @return {NodeListOf<Element> | []}
* @desc 쿼리 셀렉터 올
**/
function getElAll(el) {
  const elements = document.querySelectorAll(el);

  if (!elements || elements.length === 0) {
    return [];
  }

  return elements;
}

/** ------------------------------------------------------------------------------------------------
* @param {string} el
* @return {HTMLElement | null}
* @desc 아이디로 찾기
**/
function getById(el) {
  const element = document.getElementById(el);
  if (!element) {
    return null;
  }

  return element;
}

/** ------------------------------------------------------------------------------------------------
* @param {string} el
* @return {HTMLCollectionOf<Element> | null}
* @desc 클래스로 찾기
**/
function getByClass(el) {
  const element = document.getElementsByClassName(el);
  if (!element || element.length === 0) {
    return null;
  }

  return element;
}

/** ------------------------------------------------------------------------------------------------
* @param {string} el
* @return {NodeListOf<HTMLElement> | null}
* @desc name값으로 찾기
**/
function getByName(el) {
  const element = document.getElementsByName(el);
  if (!element || element.length === 0) {
    return null;
  }

  return element;
}

/** ------------------------------------------------------------------------------------------------
* @param {string} el
* @return {HTMLFormElement | null}
* @desc form 찾기
**/
function getFormByName(el) {
  const element = document.forms[el];
  if (!element) {
    return null;
  }

  return element;
}

/** ------------------------------------------------------------------------------------------------
* @param {HTMLElement} el
* @return {string | null}
* @desc innerHTML 값 얻기
**/
function getInnerHTML(el) {
  if (!el || !(el instanceof HTMLElement)) {
    return null;
  }

  return String(el.innerHTML).trim();
}

/** ------------------------------------------------------------------------------------------------
* @param {HTMLElement | null} el
* @return {string | null}
* @desc value 값 얻기
**/
function getValue(el) {
  if (!el || !(el instanceof HTMLInputElement || el instanceof HTMLTextAreaElement || el instanceof HTMLSelectElement || el instanceof HTMLButtonElement || el instanceof HTMLOptionElement)) {
    return null;
  }

  return String(el.value).trim();
}

/** ------------------------------------------------------------------------------------------------
* @param {HTMLElement} el
* @param {string} text
* @return {void}
* @desc value 값 설정
**/
function setValue(el, text) {
  if (!el || !(el instanceof HTMLInputElement || el instanceof HTMLTextAreaElement || el instanceof HTMLSelectElement || el instanceof HTMLButtonElement || el instanceof HTMLOptionElement)) {
    return;
  }

  el.value = text ? String(text).trim() : "";
};

/** ------------------------------------------------------------------------------------------------
* @param {HTMLElement} el
* @param {string} text
* @return {void}
* @desc innerHTML 값 설정
**/
function setInnerHTML(el, text) {
  if (!el || !(el instanceof HTMLElement)) {
    return;
  }

  el.innerHTML = text ? String(text).trim() : "";
};
