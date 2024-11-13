/** Preloader ***********************************************************************************/
$(window).on("load", function () {
  $(".loader-wrapper").fadeOut("slow");
});

/** Background Set ******************************************************************************/
$(".set-bg").each(function () {
  var bg = $(this).data("setbg");
  $(this).css("background-image", "url(" + bg + ")");
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