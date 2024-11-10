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
* @param {string} value
* @return {boolean}
* @desc 숫자 확인
**/
function isNumeric(value) {
  return /^\d+$/.test(value);
};

/** ----------------------------------------------------------------------------------------------->
* @param {string} value
* @return {boolean}
* @desc 가격 확인
**/
function isPrice(value) {
  return /^\d{4,}$/.test(value);
};

/** ----------------------------------------------------------------------------------------------->
* @param {number} x
* @desc 3자리마다 콤마 찍기
**/
function numberWithCommas(x) {
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};
