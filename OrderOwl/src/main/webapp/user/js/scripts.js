/*!
 * Start Bootstrap - Shop Homepage v5.0.6 (https://startbootstrap.com/template/shop-homepage)
 * Copyright 2013-2023 Start Bootstrap
 * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-shop-homepage/blob/master/LICENSE)
 */
// This file is intentionally blank
// Use this file to add JavaScript to your project

//작업을 위한 docu 정의(수정x)
const modalOverlay = document.getElementById("modal-overlay");
const modalWindow = document.getElementById("modal-window");
const modalContent = document.getElementById("modal-content");
const closeButton = document.getElementById("close-button");
const addModal = document.querySelectorAll(".addModal");
let no = 0;
document
  .getElementById("testFooter")
  .setAttribute("style", "visibility: hidden;");
//작업 Test Code

// document.getElementById("test").addEventListener("click", () => {
//   event.preventDefault();
//   alert("제품상세페이지");
//   no++;
//   console.log(no);
//   ck();
// });

// document.getElementById("testId").addEventListener("click", () => {
//   event.preventDefault();
//   alert("주문하기");
//   no--;
//   console.log(no);
//   ck();
// });

// const ck = () => {
//   if (no <= 0) {
//     document
//       .getElementById("testFooter")
//       .setAttribute("style", "visibility: hidden;");
//   } else {
//     document
//       .getElementById("testFooter")
//       .setAttribute("style", "visibility: visible;");

//     document.getElementById("testCart").innerHTML = no;
//     document.getElementById("testText").innerHTML = no;
//   }
// };

// 보여줄 페이지 경로 관리
/*const pageRoutes = {
  testId: "/OrderOwl/user/order.jsp",
  test: "/OrderOwl/user/menu.jsp",
};

//  모달을 여는 함수
const openModal = async (path) => {
  try {
    const pageUrl = pageRoutes[path];
    if (!pageUrl) throw new Error("페이지 경로를 찾을 수 없습니다.");

    const response = await fetch(pageUrl);
    if (!response.ok) throw new Error("페이지를 불러올 수 없습니다.");

    const html = await response.text();
    modalContent.innerHTML = html;

    // 오버레이와 모달 창을 보여줌
    modalOverlay.classList.add("show");
    modalWindow.classList.add("show");
  } catch (error) {
    console.error(error);
    modalContent.innerHTML = `<p>오류: ${error.message}</p>`;
  }
};*/

//  모달을 닫는 함수
const closeModal = () => {
  modalOverlay.classList.remove("show");
  modalWindow.classList.remove("show");
  modalContent.innerHTML = ""; // 내용을 비워줌
};

//  이벤트 리스너
/*addModal.forEach((link) => {
  link.addEventListener("click", (e) => {
    e.preventDefault(); // 기본 동작(페이지 이동) 방지
	
	
    const path = e.target.getAttribute("id");
    openModal(path);
  });
});*/

addModal.forEach((button) => {
  button.addEventListener("click", (e) => {
    e.preventDefault(); // 기본 동작(페이지 이동) 방지

 
    const clickedButton = e.currentTarget;

 
    const menuName = clickedButton.dataset.menuname;
    const price = clickedButton.dataset.price;

    const modalHtml = `
      <img src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg" alt="${menuName}" style="width:100%;" /> <br />
      <h3>이름 : ${menuName}</h3>
      <br />
      <h3>설명 : 맛있는 메뉴입니다.</h3>
      <br />
      <h3>가격 : ${price}원</h3>
      <br />
      <h3>옵션 : (옵션 추가 영역)</h3>
      <br />
      <button>추가하기</button>
    `;


    modalContent.innerHTML = modalHtml;


    modalOverlay.classList.add("show");
    modalWindow.classList.add("show");
  });
});

// 닫기 버튼을 클릭했을 때
closeButton.addEventListener("click", closeModal);

// 오버레이 클릭했을 때
modalOverlay.addEventListener("click", closeModal);

// 키보드 esc 키를 눌렀을 때
window.addEventListener("keydown", (e) => {
  if (e.key === "Escape" && modalOverlay.classList.contains("show")) {
    closeModal();
  }
});
