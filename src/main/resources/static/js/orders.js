/** ----------------------------------------------------------------------------------------------->
* @param null
* @return {boolean}
* @desc 주문 입력 유효성 검사
**/
function ordersCheck() {
  if (
    String(getValue(getById("orders_name"))) === "" ||
    Number(getValue(getById("orders_quantity"))) === 0
  ) {
    alert("주문 수량을 입력하세요");
    getById("orders_quantity").focus();
    window.location.reload();
    return false;
  }

  return true;
};