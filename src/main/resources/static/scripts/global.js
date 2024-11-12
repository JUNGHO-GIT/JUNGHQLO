/** ------------------------------------------------------------------------------------------------
* @desc 전역변수 선언
**/
const currentUrl = window.location.href;

// ex. JUNGHQLO
const title = currentUrl.split("/")[3];

// ex. notice
const preFix1 = currentUrl.split("/")[4];

// ex. Notice
const preFix2 = preFix1.charAt(0).toUpperCase() + preFix1.slice(1);

// ex. notice_number
const numberId = document.getElementById(`${preFix1}_number`);
const number = !!numberId && numberId instanceof HTMLInputElement ? numberId.value : null;

// id
const sessionId = document.getElementById("sessionId");
const STORAGE = 'https://storage.googleapis.com/jungho-bucket/JUNGHQLO/IMAGE';

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