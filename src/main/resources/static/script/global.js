/** ------------------------------------------------------------------------------------------------
* @desc 전역변수 선언
**/
let currentUrl = window.location.href;

// ex. JUNGHQLO
let title = currentUrl.split("/")[3];

// ex. notice
let preFix1 = currentUrl.split("/")[4];

// ex. Notice
let preFix2 = preFix1.charAt(0).toUpperCase() + preFix1.slice(1);

// ex. notice_number
let numberId = document.getElementById(`${preFix1}_number`);
let number = !!numberId && numberId instanceof HTMLInputElement ? numberId.value : null;

// id
let sessionId = document.getElementById("sessionId");
let STORAGE = 'https://storage.googleapis.com/jungho-bucket/JUNGHQLO/IMAGE';

/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 다음 우편번호
**/
function openDaumPostcode() {
  new daum.Postcode({
    /**
    * @param {any} data
    **/
    oncomplete: function (data) {
      setValue(getById("member_address1"), data.address);
    },
  }).open();
  return false;
};