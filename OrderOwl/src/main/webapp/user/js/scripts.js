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
const orderModal = document.querySelector(".orderModal");
const listModal = document.querySelector(".listModal");
let no = 0;

document
  .getElementById("testFooter")
  .setAttribute("style", "visibility: hidden;");

let orderList = [];

//  모달을 닫는 함수
const closeModal = () => {
  qua = 1;
  modalOverlay.classList.remove("show");
  modalWindow.classList.remove("show");
  modalContent.innerHTML = ""; // 내용을 비워줌
};

let qua = 1;
let price = 0;
let orderid= '';
addModal.forEach((button) => {
  button.addEventListener("click", (e) => {
    e.preventDefault(); // 기본 동작(페이지 이동) 방지
	
    const clickedButton = e.currentTarget;

    const menuName = clickedButton.dataset.menuname;
	let menuId = clickedButton.dataset.menuid
    price = clickedButton.dataset.price;
    
	orderid = clickedButton.dataset.orderid;
    const modalHtml = `
      <h4>메뉴 : ${menuName}</h3>
      <br />
      <h6>설명 : 맛있는 메뉴입니다.</h3>
      <br />
      <h6>가격 : ${price}원</h3>
      <br />
      <h6>옵션 : (옵션 추가 영역)</h3>
      <br />
	  <h6>갯수 : <button class="mbtn">-</button><span class="quantityElement"> ${qua} </span> <button class="pbtn">+</button></h3>
	    <br />
		<h6>총금액 : <span class="totalPriceElement">${qua * price}</span></h3>
		<br />
      <button class="abtn">추가하기</button>
    `;

    modalContent.innerHTML = modalHtml;

    document.querySelector(".mbtn").addEventListener("click", () => {
      if (qua > 1) {
        qua--;
        updatePrice();
      }
    });

    document.querySelector(".pbtn").addEventListener("click", () => {
      qua++;
      updatePrice();
    });

    document.querySelector(".abtn").addEventListener("click", () => {
      orderList.push([menuName, price, qua, qua * price,menuId]);
      console.log(orderList);
      no++;
      updateInfo();

      closeModal();
    });

    modalOverlay.classList.add("show");
    modalWindow.classList.add("show");
  });
});

//오더 클릭했을때
orderModal.addEventListener("click", (e) => {
  e.preventDefault();
  console.log(orderid);
  // 1. 주문 목록이 비어있는지 확인
  if (orderList.length === 0) {
    modalContent.innerHTML = "<h6>담은 메뉴가 없습니다.</h6>";
    modalOverlay.classList.add("show");
    modalWindow.classList.add("show");
    return; // 함수 종료
  }

  // 2. HTML 문자열을 담을 변수를 반복문 시작 전에 초기화
  let orderHtml = "<h4>주문 내역</h4><br/>";
  let sum = 0;
  // 3. 반복문을 돌면서 += 연산자로 HTML을 계속 추가
  for (let i = 0; i < orderList.length; i++) {
    // 각 주문 항목을 div로 감싸서 구분하기 쉽게 만듭니다.
    orderHtml += `
            <div style="border-bottom: 1px solid #ccc; margin-bottom: 10px; padding-bottom: 10px;">
                <h6>메뉴 : ${orderList[i][0]}</h6>
                <h6>가격 : ${orderList[i][1].toLocaleString()}원</h6>
                <h6>갯수 : ${orderList[i][2]} 개</h6>
                <h6>총계 : ${orderList[i][3].toLocaleString()}원</h6>
            </div>
        `;

    sum += orderList[i][3];
  }

  orderHtml += `
	<div style="border-bottom: 1px solid #ccc; margin-bottom: 10px; padding-bottom: 10px;">
	<h6>총금액 : ${sum}원</h6>
	</div>
	`;

  orderHtml += `
		<div style="border-bottom: 1px solid #ccc; margin-bottom: 10px; padding-bottom: 10px;">	
		<button class="obtn">주문하기</button>
		</div>
		`;

  modalContent.innerHTML = orderHtml;

  document.querySelector(".obtn").addEventListener("click", async (e) => {
	e.preventDefault();
    if (sessionStorage.getItem("list") == null) {
      let sOrderList = [];
      for (let i = 0; i < orderList.length; i++) {
        sOrderList.push([
          orderList[i][0],
          orderList[i][1],
          orderList[i][2],
          orderList[i][3],
        ]);
      }
      sessionStorage.setItem("list", JSON.stringify(sOrderList));
    } else {
      let sOrderList = JSON.parse(sessionStorage.getItem("list"));
      for (let i = 0; i < orderList.length; i++) {
        sOrderList.push([
          orderList[i][0],
          orderList[i][1],
          orderList[i][2],
          orderList[i][3],
        ]);
      }
      sessionStorage.setItem("list", JSON.stringify(sOrderList));
    }
	
	const postData = {
	      key: "cusOrder",
	      methodName: "requestOrder",
		  orderid:orderid,
	      orders: orderList // 현재 장바구니(orderList)를 보냅니다.
	    };
	
		try {
		      await fetch("http://localhost:8080/OrderOwl/front", {
		        method: "POST",
		        headers: {
		   
		          "Content-Type": "application/json", 
		        },
		 
		        body: JSON.stringify(postData), 
		      })}
		catch (error) {
			        console.error("네트워크 오류:", error);
			        alert("주문 전송에 실패했습니다. (네트워크 문제)");
			      }
		
		
    orderList = [];

    closeModal();
    no = 0;
    updateInfo();
  });
  // 모달창 표시
  modalOverlay.classList.add("show");
  modalWindow.classList.add("show");
});

//리스트 클릭했을때
listModal.addEventListener("click", (e) => {
  e.preventDefault();

  if (sessionStorage.getItem("list") == null) {
    modalContent.innerHTML = "<h6>주문 내역이 없습니다.</h6>";
    modalOverlay.classList.add("show");
    modalWindow.classList.add("show");
    return;
  }

  let listHtml = "<h4>주문 내역</h4><br/>";
  let sum = 0;
  let sList = sessionStorage.getItem("list");
  let myList = JSON.parse(sList);
  console.log(myList);
  for (let i = 0; i < myList.length; i++) {
    listHtml += `
            <div style="border-bottom: 1px solid #ccc; margin-bottom: 10px; padding-bottom: 10px;">
                <h6>메뉴 : ${myList[i][0]}</h6>
                <h6>가격 : ${myList[i][1]}원</h6>
                <h6>갯수 : ${myList[i][2]} 개</h6>
                <h6>총계 : ${myList[i][3]}원</h6>
            </div>
        `;

    sum += parseInt(myList[i][3]);

  }

  listHtml += `
	<div style="border-bottom: 1px solid #ccc; margin-bottom: 10px; padding-bottom: 10px;">
	<h6>총금액 : ${sum}원</h6>
	</div>
	`;


  modalContent.innerHTML = listHtml;

  modalOverlay.classList.add("show");
  modalWindow.classList.add("show");
});

// 닫기 버튼을 클릭했을 때
closeButton.addEventListener("click", closeModal);

// 오버레이 클릭했을 때
/*modalOverlay.addEventListener("click", closeModal);*/

function updatePrice() {
  document.querySelector(".quantityElement").textContent = qua;
  document.querySelector(".totalPriceElement").textContent = (
    qua * price
  ).toLocaleString();
}

const updateInfo = () => {
  document.querySelector("#testCart").innerHTML = no;
};

// 키보드 esc 키를 눌렀을 때
window.addEventListener("keydown", (e) => {
  if (e.key === "Escape" && modalOverlay.classList.contains("show")) {
    closeModal();
  }
});
