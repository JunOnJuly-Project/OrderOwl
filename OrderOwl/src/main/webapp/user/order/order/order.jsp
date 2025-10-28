<%@page import="service.user.UserServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://js.tosspayments.com/v2/standard"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/user/order/order/order.css">
</head>
<body>
	<div id="modalpay" class="modal" class="pay">	
		<!-- 결제 UI -->
	    <div id="payment-method"></div>
	    <!-- 이용약관 UI -->
	    <div id="agreement"></div>
	    <!-- 결제하기 버튼 -->
	    <button class="button" id="payment-button" style="margin-top: 30px">
	      결제하기
	    </button>
	</div>

	<c:forEach var="order" items="${orders}" varStatus="status">
  		<div id="modal${order.orderId}" class="modal">
  			<c:forEach var="orderDetail" items="${order.orderDetail}" varStatus="status">
	  			<c:if test="${orderDetail.orderId==order.orderId}">
		  			<c:forEach var="menu" items="${menus}" varStatus="status">
		  				<c:if test="${menu.menuId==orderDetail.menuId}">
					        <p>메뉴 : ${menu.menuName}</p>
					    </c:if>
		  			</c:forEach>
		  			<p>id : ${orderDetail.orderId}</p>
		  			<p>수량 : ${orderDetail.quantity}</p>
		  			<p>가격 : ${orderDetail.price}</p>
		  			<P>---</P>
	  			</c:if>
  			</c:forEach>
  		</div>
	</c:forEach>
	
    <div id="background">
    	<div id="header">
    		<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=logout'" id="logOut" class="selectBtn">로그아웃</button>
    	</div>
    	
	    <div id="select">
	    	<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectAllMenu'" id="menu" class="selectBtn">메뉴</button>
	    	<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectSales&state=hour'" id="inform" class="selectBtn">정보</button>
	    	<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectAllQr&storeId=${store.storeId}'" id="qr" class="selectBtn">QR</button>
	    	<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectAllOrder'" id="order" class="selectBtn">주문</button>
	    </div>
	    
	    <div id="list">
	    	<table>
	    		<tr>
	    			<th>주문 번호</th>
	    			<th>주문 시간</th>
	    			<th>가격</th>
	    			<th>테이블</th>
	    			<th>처리</th>
	    		</tr>

	    		<c:forEach var="order" items="${orders}" varStatus="status">
	    			<tr>
					    <td>${order.orderId}</td>
					    <td>${order.orderDate.toLocalTime()}</td>
					    <td>${order.totalPrice}</td>
					    <td>${order.tableId}</td>
						<td class="process">
		 					<button id="${order.orderId}" class="modalBtn">확인</button>
							<button class="modalBtn" id="pay">결제</button>

							<form action="${pageContext.request.contextPath}/front">
								<input type="hidden" value="user" name="key">
								<input type="hidden" value="updateOrder" name="methodName">
								<input type="hidden" value="completed" name="state">
								<input type="hidden" value="${order.orderId}" name="orderId">
								<input type="hidden" value="${order.storeId}" name="storeId">
								<button>완료</button>
							</form>
							
							<form action="${pageContext.request.contextPath}/front">
								<input type="hidden" value="user" name="key">
								<input type="hidden" value="updateOrder" name="methodName">
								<input type="hidden" value="cancelled" name="state">
								<input type="hidden" value="${order.orderId}" name="orderId">
								<input type="hidden" value="${order.storeId}" name="storeId">
								<button>취소</button>
							</form>
						</td>
					</tr>
				</c:forEach>
	    	</table>
	    </div>
	    
	    <div id="footer">
	    	<h2>footer</h2>	
	    </div>
    </div>
    
	
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/user/order/order/order.js"></script>
<script>
  main();
  async function main() {
    const button = document.getElementById("payment-button");
    // test용 키라 상관없음
    const clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm";
    const tossPayments = TossPayments(clientKey);
    // 회원 결제
    const customerKey = "RdcLpG6ILf2P36Ufvr91Y";
    const widgets = tossPayments.widgets({
      customerKey,
    });
    // ------ 주문의 결제 금액 설정 ------
    await widgets.setAmount({
      currency: "KRW",
      value: 3000,
    });
    await Promise.all([
      // ------  결제 UI 렌더링 ------
      widgets.renderPaymentMethods({
        selector: "#payment-method",
        variantKey: "DEFAULT",
      }),
      // ------  이용약관 UI 렌더링 ------
      widgets.renderAgreement({
        selector: "#agreement",
        variantKey: "AGREEMENT",
      }),
    ]);
    // ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
    button.addEventListener("click", async function () {
      await widgets.requestPayment({
        orderId: "tDOsevySY3uhR3C8GM5u6",
        orderName: "토스 티셔츠 외 2건",
        successUrl: window.location.origin + "/success.html",
        failUrl: window.location.origin + "/fail.html",
        customerEmail: "customer123@gmail.com",
        customerName: "김토스",
        customerMobilePhone: "01012341234",
      });
    });
  }
</script>
</html>