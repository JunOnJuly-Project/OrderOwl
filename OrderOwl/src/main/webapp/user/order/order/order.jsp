<%@page import="service.user.UserServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/user/order/order/order.css">
</head>
<body>
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
	    	<button id="qr" class="selectBtn">QR</button>
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
</html>