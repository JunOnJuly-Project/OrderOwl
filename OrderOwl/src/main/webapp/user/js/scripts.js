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
const categoryView = document.querySelector(".categoryView");
let no = 0;
let hostUrl ='';
window.onload = () =>{
	
	let query = window.location.search;
	let param = new URLSearchParams(query);
	let id = param.get('tableNo');
	orderid=id;
	hostUrl=
		window.location.host+
		window.location.pathname;
	calCartNum();
	updateInfo();
}
const calCartNum = () =>{
if(sessionStorage.getItem("list") == null){
	no = 0;

}
else {
	no =  JSON.parse(sessionStorage.getItem("list")).length;
	
}
}
document
  .getElementById("testFooter")
  .setAttribute("style", "visibility: hidden;");

let orderList = []

if(JSON.parse(sessionStorage.getItem("list")) !== null){
	
	orderList =  JSON.parse(sessionStorage.getItem("list"));
	
}

calCartNum();
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
	  sessionStorage.setItem("list", JSON.stringify(orderList));
      console.log(orderList);
     calCartNum();
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
	let ssOrderList = JSON.parse(sessionStorage.getItem("list"));
	console.log(ssOrderList);
  if (ssOrderList == null) {
    modalContent.innerHTML = "<h6>담은 메뉴가 없습니다.</h6>";
    modalOverlay.classList.add("show");
    modalWindow.classList.add("show");
    return; 
  }


  let orderHtml = "<h4>주문 내역</h4><br/>";
  let sum = 0;

  for (let i = 0; i < ssOrderList.length; i++) {
 
    orderHtml += `
            <div style="border-bottom: 1px solid #ccc; margin-bottom: 10px; padding-bottom: 10px;">
                <h6>메뉴 : ${ssOrderList[i][0]}</h6>
                <h6>가격 : ${ssOrderList[i][1].toLocaleString()}원</h6>
                <h6>갯수 : ${ssOrderList[i][2]} 개</h6>
                <h6>총계 : ${ssOrderList[i][3].toLocaleString()}원</h6>
				<button class="delMenu">삭제하기</button>
            </div>
        `;

    sum += ssOrderList[i][3];
  }

  orderHtml += `
	<div style="border-bottom: 1px solid #ccc; margin-bottom: 10px; padding-bottom: 10px;">
	<h6 class="setPrice">총금액 : ${sum}원</h6>
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
   /* if (sessionStorage.getItem("list") == null) {
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
    }*/
	
	const postData = {
	      key: "cusOrder",
	      methodName: "requestOrder",
		  orderid:orderid,
	      orders: ssOrderList // 현재 장바구니(orderList)를 보냅니다.
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
		
	sessionStorage.removeItem("list")
    orderList = [];

    closeModal();
    calCartNum();
	
	
	
	
    updateInfo();
  });
  
  //삭제 버튼 눌렀을때
  document.querySelectorAll(".delMenu").forEach((delbtn) => {
      
      delbtn.addEventListener('click', (e) => {
          
          const delButton = Array.from(document.querySelectorAll(".delMenu"));
          
          const index = delButton.indexOf(e.target);

          
          
          console.log(ssOrderList.length);
          
          sum -= parseInt(ssOrderList[index][3]); 
          
          document.querySelector(".setPrice").innerHTML = `총금액 : ${sum}원</h6>`;
          
          ssOrderList.splice(index, 1);
		  orderList.splice(index, 1);
          console.log(ssOrderList);
          
          e.target.parentElement.remove();
		  sessionStorage.setItem("list",JSON.stringify(ssOrderList));
		  calCartNum();
		  console.log(ssOrderList.length);
		  if(ssOrderList.length ===0){
			
			
			closeModal();
			
		  }
		  updateInfo();
      });
  });
  
  // 모달창 표시
  modalOverlay.classList.add("show");
  modalWindow.classList.add("show");
});

//리스트 클릭했을때
listModal.addEventListener("click", async (e) => {
  e.preventDefault();
  
  // 리스트 비동기 처리 로직
  const postData = {
  	      key: "cusOrder",
  	      methodName: "requestOrderData",
  		  orderid:orderid,
  	    };
  	    let orderDetailList=[];
  		try {
  		     const response =  await fetch("http://localhost:8080/OrderOwl/front", {
  		        method: "POST",
  		        headers: {
  		   
  		          "Content-Type": "application/json", 
  		        },
  		 
  		        body: JSON.stringify(postData), 
  		      })
			  
			  orderDetailList = await response.json();
	
			  }
  		catch (error) {
  			        console.error("네트워크 오류:", error);
  			        alert("주문 전송에 실패했습니다. (네트워크 문제)");
  			      }
  		
	
  

  if (orderDetailList == null) {
    modalContent.innerHTML = "<h6>주문 내역이 없습니다.</h6>";
    modalOverlay.classList.add("show");
    modalWindow.classList.add("show");
    return;
  }

  let listHtml = "<h4>주문 내역</h4><br/>";
  let sum = 0;
/*  let sList = sessionStorage.getItem("list");
  let myList = JSON.parse(sList);
  console.log(myList);*/
  
 console.log(orderDetailList[0]);
  for (let i = 0; i < orderDetailList.length; i++) {
    listHtml += `
            <div style="border-bottom: 1px solid #ccc; margin-bottom: 10px; padding-bottom: 10px;">
                <h6>메뉴 : ${orderDetailList[i].menuName}</h6>
                <h6>가격 : ${orderDetailList[i].price}원</h6>
                <h6>갯수 : ${orderDetailList[i].quantity} 개</h6>
                <h6>총계 : ${orderDetailList[i].price * orderDetailList[i].quantity}원</h6>
            </div>
        `;

    sum += parseInt(orderDetailList[i].price * orderDetailList[i].quantity);

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

categoryView.addEventListener('click',async ()=>{
	console.log(orderid);
	let categoryList
	const postData = {
		      key: "cusOrder",
		      methodName: "requestCategoryData",
			  orderid:orderid,
		    };
			try {
			     const response =  await fetch("http://localhost:8080/OrderOwl/front", {
			        method: "POST",
			        headers: {
			   
			          "Content-Type": "application/json", 
			        },
			 
			        body: JSON.stringify(postData), 
			      })
			  
			  categoryList = await response.json();

			  }
			catch (error) {
				        console.error("네트워크 오류:", error);
				        alert("주문 전송에 실패했습니다. (네트워크 문제)");
				      }
	
	
	
	let categoryContent = `
	<form method = "get" action="http://${hostUrl}">
	<select name="categoryKey">
	<option value ="null"> 전체메뉴보기 </option>
	`;
	for(j = 0 ; j < categoryList.length ; j++){
		categoryContent+=`<option value="${categoryList[j].categoryId}"> ${categoryList[j].categoryName} </option>`
	}
	categoryContent+=`</select>
	
	<input type="hidden" name="key" value = "cusOrder" />
	<input type="hidden" name="methodName" value = "selectByModelNum" /> 
	<input type="hidden" name="tableNo" value=${orderid}>
	<button> 조회하기 </button>
	</form>
	`
	
	modalContent.innerHTML = categoryContent;
	modalOverlay.classList.add("show");
	 modalWindow.classList.add("show");
	
	
})

