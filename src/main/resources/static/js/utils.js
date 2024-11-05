
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
* @desc value 값 얻기
**/
function getValue(el) {
  if (el instanceof HTMLInputElement) {
    return el.value;
  }
  else {
    throw new Error(`Expected an HTMLInputElement, but received ${el}`);
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
* @param {string} text
* @return {void}
* @desc value 값 설정
**/
function setValue(el, text) {
  if (el instanceof HTMLInputElement) {
    el.value = text;
  }
  else {
    throw new Error(`Expected an HTMLInputElement, but received ${el}`);
  }
};

/** ----------------------------------------------------------------------------------------------->
* @param {HTMLElement} el
* @param {string} text
* @return {void}
* @desc innerHTML 값 설정
**/
function setInnerHTML(el, text) {
  if (el instanceof HTMLElement) {
    el.innerHTML = text;
  }
  else {
    throw new Error(`Element with innerHTML ${el} not found`);
  }
}