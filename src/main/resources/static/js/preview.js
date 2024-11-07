/**
* @author Jungho
* @since 2023-05-29
* @version 1.1
* @desc 이미지를 선택하면 프리뷰로 보여주는 함수, 이미지는 총 2개 까지 가능합니다.
* @desc 인덱스값이 1개 이상이면 onchange 이벤트 등록, 아니면 oninput 이벤트를 등록합니다.
**/

function junghoPreview(index) {

  if(index == '' || index == null) {
    index = '';
  }

  // variables (getElement는 document.getElementById를 간략화한 함수) ----------------------------->
  const getElement = (id) => document.getElementById(id);
  const imageFile = getElement(`imageFile${index}`);
  const imageBox = getElement("imageBox");
  const imageLoader = getElement("imageLoader");
  const imageMiniBox = getElement(`imageMiniBox${index}`);
  const imageMiniLoader = getElement(`imageMiniLoader${index}`);

  // processImageFiles(이미지 파일을 선택했을 때의 이벤트를 처리) --------------------------------->
  function processImageFiles() {
    function handleImageFileUpload() {
      if (imageFile.files.length > 0) {
        Array.from(imageFile.files).forEach((file) => {
          if (!file.type.match(/image.*/)) {
            alert("이미지 파일만 업로드 가능합니다");
            imageLoader.style.display = "none";
            imageMiniLoader.style.display = "none";
            window.location.reload();
            return;
          }

          const reader = new FileReader();
          reader.onload = () => {
            imageBox.style.opacity = 0;
            loadImage(reader.result, file);
          };
          reader.readAsDataURL(file);
        });
      }
    }
    window.addEventListener("resize", updateImageLoaderSize);
    handleImageFileUpload();
  }
  processImageFiles();

  // getMaxImageDimensions(이미지의 최대 사이즈) -------------------------------------------------->
  function getMaxImageDimensions() {
    const width
    = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    const height
    = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
    const maxWidth = width < 768 ? 330 : width < 1024 ? 330 : 330;
    const maxHeight = height < 768 ? 400 : height < 1024 ? 400 : 400;
    return { maxWidth, maxHeight };
  }

  // updateImageLoaderSize(이미지 로더의 사이즈) -------------------------------------------------->
  function updateImageLoaderSize() {
    const {maxWidth, maxHeight} = getMaxImageDimensions();
    Object.assign(imageLoader.style, {width: maxWidth + "px", height: maxHeight + "px"});
  }

  // loadImage(이미지를 로드) --------------------------------------------------------------------->
  function loadImage(src, file) {
    const image = new Image();
    image.onload = () => {
      const canvas = document.createElement("canvas");
      const ctx = canvas.getContext("2d");
      const {maxWidth, maxHeight} = getMaxImageDimensions();
      let [width, height] = [image.width, image.height];
      if (width > maxWidth) [height, width] = [(height * maxWidth) / width, maxWidth];
      if (height > maxHeight) [width, height] = [(width * maxHeight) / height, maxHeight];
      const [offsetX, offsetY] = [(maxWidth - width) / 2, (maxHeight - height) / 2];
      const imageDimensions = {maxWidth, maxHeight, width, height, offsetX, offsetY,};
      renderImageOnCanvas(canvas, ctx, image, imageDimensions);
      const dataUrl = canvas.toDataURL();
      setImageBox(imageBox, dataUrl, imageLoader);
      setImageBoxMini(imageMiniBox, dataUrl, file.name, imageBox, getElement("imageMiniLoader" + index));
    };
    image.src = src;
  }

  // renderImageOnCanvas(이미지를 캔버스에 렌더링) ------------------------------------------------>
  function renderImageOnCanvas(canvas, ctx, image, imageDimensions) {
    const { maxWidth, maxHeight, width, height, offsetX, offsetY } = imageDimensions;
    Object.assign(canvas, { width: maxWidth, height: maxHeight });
    const borderRadius = 10;

    ctx.save();
    ctx.beginPath();
    ctx.moveTo(offsetX + borderRadius, offsetY);
    ctx.lineTo(offsetX + width - borderRadius, offsetY);
    ctx.quadraticCurveTo(offsetX + width, offsetY, offsetX + width, offsetY + borderRadius);
    ctx.lineTo(offsetX + width, offsetY + height - borderRadius);
    ctx.quadraticCurveTo(
      offsetX + width,
      offsetY + height,
      offsetX + width - borderRadius,
      offsetY + height
    );
    ctx.lineTo(offsetX + borderRadius, offsetY + height);
    ctx.quadraticCurveTo(offsetX, offsetY + height, offsetX, offsetY + height - borderRadius);
    ctx.lineTo(offsetX, offsetY + borderRadius);
    ctx.quadraticCurveTo(offsetX, offsetY, offsetX + borderRadius, offsetY);
    ctx.closePath();
    ctx.clip();
    ctx.drawImage(image, offsetX, offsetY, width, height);
    ctx.restore();
  }

  // setImageBox(이미지 박스에 이미지 추가) ------------------------------------------------------->
  function setImageBox(imageBox, dataUrl, imageLoader) {
    imageBox.src = dataUrl;
    imageLoader.style.display = "none";
    fadeInImage(imageBox);
    const imgProps = {
      borderRadius: "10px",
      boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.2)",
      objectFit: "cover",
    };
    Object.assign(imageBox.style, imgProps);
  }

  // setImageBoxMini(미니 이미지 박스에 이미지 추가) ---------------------------------------------->
  function setImageBoxMini(imageMiniBox, dataUrl, title, imageBox, imageMiniLoader) {
    imageMiniBox.src = dataUrl;
    imageMiniBox.title = title;
    imageMiniLoader.style.display = "none";

    const imgProps = {
      borderRadius: "10px",
      boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.2)",
      backgroundColor: "transparent",
    };
    Object.assign(
      imageMiniBox.style, imgProps, {
        width: "100px",
        height: "100px",
        cursor: "pointer",
      }
    );
    imageMiniBox.onclick = () => {
      imageBox.style.opacity = 0;
      imageBox.src = imageMiniBox.src;
      fadeInImage(imageBox);
      Object.assign(imageBox.style, imgProps);
    };
  }


  // fadeInImage(페이드인 효과 추가) -------------------------------------------------------------->
  function fadeInImage(imageBox) {
    setTimeout(() => (imageBox.style.opacity = 1), 100);
    let opacity = parseFloat(imageBox.style.opacity) || 0;
    const fadeIn = setInterval(
      () => {
        opacity += 0.1;
        imageBox.style.opacity = opacity;
        if (opacity >= 1) clearInterval(fadeIn);
      }, 30
    );
    imageBox.classList.add("pulse");
    setTimeout(() => imageBox.classList.remove("pulse"), 500);
  }
}

// resetImage(이미지 리셋) ------------------------------------------------------------------------>
function resetImage() {
  const getElement = (id) => document.getElementById(id);
  const imageFile = getElement(`imageFile${index}`);
  const imageBox = getElement("imageBox");
  const imageLoader = getElement("imageLoader");
  const imageMiniBox = getElement(`imageMiniBox${index}`);
  const imageMiniLoader = getElement(`imageMiniLoader${index}`);
  const resetButton = document.getElementById("resetButton");

  resetButton.onclick = () => {
    imageFile.value = "";
    imageBox.src = "";
    imageBox.style.opacity = 0;
    imageLoader.style.display = "block";
    imageMiniBox.src = "";
    imageMiniLoader.style.display = "block";
  };
}

// reloadPage(페이지 리로드) ---------------------------------------------------------------------->
function reloadPage() {
  const reloadButton = document.getElementById("reloadButton");
  reloadButton.onclick = () => window.location.reload();
}