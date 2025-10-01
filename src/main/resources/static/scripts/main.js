/** Loader ****************************************************************************************/
const startLoader = () => {
  const loaderLayout = document.querySelector(".loader-layout");
  const loaderWrapper = Object.assign(document.createElement("div"), {
    innerHTML: (`
      <div class="loader-wrapper">
        <div class="loader"></div>
      </div>
    `),
  });
  if (loaderLayout) {
    loaderLayout.appendChild(loaderWrapper);
  }
};

const removeLoader = () => {
  const loaderLayout = document.querySelector(".loader-layout");
  const loaderWrapper = document.querySelector(".loader-wrapper");

  if (loaderLayout) {
    loaderLayout.removeChild(loaderWrapper);
  }
}

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

/** Filter ****************************************************************************************/
$(window).on("load", function () {
  if ($(".product-filter").length > 0) {
    var containerEl = document.querySelector(".product-filter");
    var mixer = mixitup(containerEl);
  }
});
