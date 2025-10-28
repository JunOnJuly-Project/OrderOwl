<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OrderOwl</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/user/qr/qr/qr.css">
</head>
<body>
	<div id="modalcreate" class="modal">
		<form action="${pageContext.request.contextPath}/front">
			<input type="hidden" name="storeId" value="${store.storeId}">
			<input type="hidden" value="createTable" name="methodName">
			<input type="hidden" value="user" name="key">
			
			<div class="formDiv">
				<label for="tableNo">테이블 이름</label>
				<input type="text" name="tableNo" id="tableNo">
			</div>
			
			<button id="tabldAddBtn">테이블 추가</button>
		</form>
	</div>
	
    <div id="background">
    	<div id="header">
    		<h1>${store.storeName}</h1>
    		<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=logout'" id="logOut" class="selectBtn">로그아웃</button>
    	</div>
    	
	    <div id="select">
	    	<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectAllMenu'" id="menu" class="selectBtn">메뉴</button>
	    	<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectSales&state=hour'" id="inform" class="selectBtn">정보</button>
	    	<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectAllQr&storeId=${store.storeId}'" id="qr" class="selectBtn">QR</button>
	    	<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectAllOrder'" id="order" class="selectBtn">주문</button>
	    </div>

		<div id="subselect">
			<h2>
				<button id="create" class="modalBtn selectBtn">추가</button>
			</h2>	
		</div>
	    <div id="list">
			<div id="qrMain">
				<c:forEach var="q" items="${qrs}" varStatus="status">
			  		<div id="modal${q.qrcodeId}" class="qrDiv">
			  			<img class="qrImage" alt="${q.qrcodeId}" src="${q.qrImgSrc}">
			  			<c:forEach var="table" items="${tables}" varStatus="status">
			  				<c:if test="${q.tableId == table.tableId}">
					        	<p>${table.tableNo}</p>
					    	</c:if>
			  			</c:forEach>
			  		</div>
				</c:forEach>
			</div>
	    </div>
	    
	    <div id="footer">
	    </div>
    </div>
    
	
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/user/qr/qr/qr.js"></script>
</html>