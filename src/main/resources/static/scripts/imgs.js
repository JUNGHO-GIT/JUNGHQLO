// -------------------------------------------------------------------------------------------------
let imgsExistCnt = 0;
let imgsNewCnt = 0;

// 기존 이미지 개수 가져오기 -----------------------------------------------------------------------
const getExistingImgs = () => {
  const imgsExist = getById(`${preFix1}_imgsUrl`);
  const imgsExistValue = getValue(imgsExist);

  const updateImgsExistCnt = (value) => {
    if (value.trim() === "") {
      imgsExistCnt = 0;
    }
    else if (value.trim().indexOf(",") === -1) {
      imgsExistCnt = 1;
    }
    else {
      imgsExistCnt = value.trim().split(",").length;
    }
  };

  // 초기값 설정
  updateImgsExistCnt(imgsExistValue);
  console.log("Initial imgsExist value:", imgsExistValue);
  console.log("Initial imgsExistCnt:", imgsExistCnt);

  // MutationObserver로 값 변화 감지
  new MutationObserver(() => {
    const newValue = getValue(imgsExist);
    updateImgsExistCnt(newValue);
    console.log("Updated imgsExistCnt:", imgsExistCnt);
  })
  .observe(imgsExist, {
    attributes: true,
    attributeFilter: ['value'],
  });
};

// 기존 이미지 삭제 기능 ---------------------------------------------------------------------------
const deleteExistingImgs = (index) => {
  const existingImgs = getById(`existingImgs${index}`);
  const existingName = getById(`existingName${index}`);
  const existingDelete = getById(`existingDelete${index}`);

  // 기존 imgsUrl 스트링에서 이미지명을 제거
  const existingUrl = getValue(getById(`${preFix1}_imgsUrl`)).trim();

  if (existingUrl.trim() === "") {
    imgsExistCnt = 0;
  }
  else if (existingUrl.indexOf(",") === -1) {
    setValue(getById(`${preFix1}_imgsUrl`), "");
    imgsExistCnt = 1;
  }
  else {
    const existingImgsUrl = existingUrl.split(",");
    existingImgsUrl.splice(index, 1);
    setValue(getById(`${preFix1}_imgsUrl`), existingImgsUrl.join(","));
    imgsExistCnt = existingImgsUrl.length;
  }

  console.log("Modified imgsUrl:", getValue(getById(`${preFix1}_imgsUrl`)));

  // 이미지 삭제
  existingImgs.remove();
  existingName.remove();
  existingDelete.remove();
};

// 새로운 이미지 생성하기 --------------------------------------------------------------------------
const createImgs = () => {

  // dom 요소 찾기
  const imgsContainer = getById("imgsContainer");
  const inputContainer = getById("inputContainer");
  const imgsLimit = parseInt(imgsContainer.dataset.limit)

  // 0. 유효성 검사
  const validateImgs = (inputElement) => {
    // 이미지 개수 검사
    if (
      imgsContainer.querySelectorAll(".imgsPreview").length >= imgsLimit ||
      imgsExistCnt + imgsNewCnt >= imgsLimit
    ) {
      alert(`최대 ${imgsLimit}개의 이미지만 업로드할 수 있습니다.`);
      return false;
    }
    // 파일 크기 검사
    if (
      inputElement instanceof HTMLInputElement &&
      inputElement.files.length > 0
    ) {
      const file = inputElement.files[0];
      if (file.size > 3 * 1024 * 1024) {
        alert("이미지는 최대 3MB까지 업로드 가능합니다.");
        return false;
      }
    }
    // 이미지 확장자 검사
    if (
      inputElement instanceof HTMLInputElement &&
      inputElement.files.length > 0
    ) {
      const file = inputElement.files[0];
      const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.webp)$/i;
      if (!allowedExtensions.exec(file.name)) {
        alert("jpg, jpeg, png, webp 형식의 이미지만 업로드 가능합니다.");
        return false;
      }
    }
    return true;
  };

  // 0. input 요소 생성 및 클릭 이벤트
  const initClickEvent = () => {
    const inputElement = createFileInput();
    inputElement.id = `imgsInput-${Date.now()}`;
    inputElement.addEventListener("change", (e) => {
      if (!(e.target instanceof HTMLInputElement) || !(e.target.files.length > 0)) {
        return;
      }
      if (!validateImgs(e.target)) {
        return;
      }
      const file = e.target.files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        createImagePreview(e.target.result, file.name, inputElement);
      };
      reader.readAsDataURL(file);
    });
    inputElement.click();
  };

  // 1-1. 제거 (input)
  const removeInput = (inputId) => {
    const inputElement = getById(inputId);
    if (inputElement) {
      inputElement.remove();
    }
  };

  // 1-2. 제거 (preview)
  const removePreview = (inputId) => {
    const previewElement = getElem(`.imgsPreviewContainer-${inputId}`);
    if (previewElement) {
      previewElement.remove();
    }
    imgsNewCnt -= 1;
    console.log("imgsNewCnt:", imgsNewCnt);
  };

  // 2-1. 생성 (input)
  const createFileInput = () => {
    return Object.assign(document.createElement("input"), {
      type: "file",
      id: "imgsFile",
      name: "imgsFile",
      accept: "image/*",
      multiple: false
    });
  };

  // 2-2. 생성 (preview)
  const createImagePreview = (imageSrc, imageName, inputElement) => {
    const imgsPreviewContainer = Object.assign(document.createElement("div"), {
      className: `d-row-between mb-10 imgsPreviewContainer-${inputElement.id}`,
      innerHTML: (`
        <div class="d-row-center">
          <img
            id="imgsPreview"
            name="imgsPreview"
            alt="imgsPreview"
            class="imgsPreview w-30 h-30"
            loading="lazy"
            src="${imageSrc}"
          />
          <div
            id="imgsName"
            name="imgsName"
            class="fs-0-9rem fw-500 dark ms-10"
          >
            ${imageName.length > 10 ? imageName.substring(0, 10) + "..." : imageName}
          </div>
        </div>
        <div class="d-row-center">
          <div
            id="imgsDelete"
            name="imgsDelete"
            class="imgsDelete fs-1-0rem fw-800 burgundy pointer"
          >
            X
          </div>
        </div>
      `)
    });
    imgsContainer.appendChild(imgsPreviewContainer);
    inputContainer.appendChild(inputElement);
    imgsPreviewContainer.querySelector(".imgsDelete").addEventListener("click", () => {
      removeInput(inputElement.id);
      removePreview(inputElement.id);
    });
    imgsNewCnt += 1;
    console.log("imgsNewCnt:", imgsNewCnt);
  };

  // 3. 높이 조절
  const adjustContainerHeight = () => {
    const previewElement = getById("imgsPreview");
    const previewCount = getElemAll(".imgsPreview").length;
    const previewHeight = previewElement ? previewElement.offsetHeight : 0;
    if (previewCount === 0) {
      imgsContainer.style.height = "60px";
      return;
    }
    imgsContainer.style.height = `${previewCount * previewHeight + 60}px`;
  };

  // 4. 컨테이너 높이 조절
  new MutationObserver(() => {
    adjustContainerHeight();
  })
  .observe(imgsContainer, {
    childList: true,
    subtree: true
  });

  // 6. 초기화
  initClickEvent();
};

// -------------------------------------------------------------------------------------------------
if (currentUrl.includes("save") || currentUrl.includes("update")) {
  document.addEventListener('DOMContentLoaded', function () {
    getExistingImgs();
  });
}
