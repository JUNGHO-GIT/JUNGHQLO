/** Preloader ***********************************************************************************/
$(window).on("load", function () {
  $(".loader-wrapper").fadeOut("slow");

  // isotope filter
  $(".filter-controls li").on("click", function () {
    $(".filter-controls li").removeClass("active");
    $(this).addClass("active");
  });
  if ($(".product-filter").length > 0) {
    var containerEl = document.querySelector(".product-filter");
    var mixer = mixitup(containerEl);
  }
});

/** Background Set ******************************************************************************/
$(".set-bg").each(function () {
  var bg = $(this).data("setbg");
  $(this).css("background-image", "url(" + bg + ")");
});

/** Navigation **********************************************************************************/
$(".mobile-menu").slicknav({
  prependTo: "#mobile-menu-wrap",
  allowParentLinks: true,
});

/** Accordion Active *****************************************************************************/
$(".collapse").on("shown.bs.collapse", function () {
  $(this).prev().addClass("active");
});

$(".collapse").on("hidden.bs.collapse", function () {
  $(this).prev().removeClass("active");
});

/** Canvas Menu Open/Close **********************************************************************/
$(".mobile-toggle").on("click", function () {
  $(".off-canvas-menu-wrapper").addClass("active");
  $(".off-canvas-menu-overlay").addClass("active");
});

$(".off-canvas-menu-overlay").on("click", function () {
  $(".off-canvas-menu-wrapper").removeClass("active");
  $(".off-canvas-menu-overlay").removeClass("active");
});

/** Hero Slider *********************************************************************************/
$(".hero-slider").owlCarousel({
  loop: true,
  margin: 0,
  items: 1,
  dots: false,
  nav: true,
  navText: [
    "<span class='arrow_left'><span/>",
    "<span class='arrow_right'><span/>",
  ],
  animateOut: "fadeOut",
  animateIn: "fadeIn",
  smartSpeed: 1200,
  autoHeight: false,
  autoplay: false,
});

/** Product Slider *****************************************************************************/
$("select").niceSelect();
$(".product-color-select label, .shop-sidebar-size label, .product-details-option-size label")
.on("click", function () {
  $(".product-color-select label, .shop-sidebar-size label, .product-details-option-size label")
  .removeClass("active");
  $(this).addClass("active");
});

/** Product Details Slider **********************************************************************/
$(".nice-scroll").niceScroll({
  cursorcolor: "#0d0d0d",
  cursorwidth: "5px",
  background: "#e5e5e5",
  cursorborder: "",
  autohidemode: true,
  horizrailenabled: false,
});

/** Magnific Popup ******************************************************************************/
$(".video-popup").magnificPopup({
  type: "iframe",
});

/** Quantity change *****************************************************************************/
var proQty = $(".pro-qty");
proQty.prepend('<span class="fa fa-angle-up dec qtybtn"></span>');
proQty.append('<span class="fa fa-angle-down inc qtybtn"></span>');

proQty.on("click", ".qtybtn", function () {
  var $button = $(this);
  var oldValue = $button.parent().find("input").val();
  if ($button.hasClass("dec")) { // Changed to "dec" here
    var newVal = parseFloat(oldValue) + 1;
  } else {
    // Don't allow decrementing below zero
    if (oldValue > 0) {
      var newVal = parseFloat(oldValue) - 1;
    } else {
      newVal = 0;
    }
  }
  $button.parent().find("input").val(newVal);
});