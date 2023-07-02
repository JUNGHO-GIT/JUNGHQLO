// 1. ordersCheck --------------------------------------------------------------------------------->
function ordersCheck() {

  if ($("#orders_quantity").val() == "" || $("#orders_quantity").val() == 0) {
    alert("주문 수량을 입력하세요");
    $("#orders_quantity").focus();
    window.location.reload();
    return false;
  }
  return true;
}