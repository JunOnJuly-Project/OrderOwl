<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>OrderOwl</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/user/inform/update/update.css">
	<script src="https://cdn.jsdelivr.net/npm/chart.js@4.5.0/dist/chart.umd.min.js"></script>
</head>
<body>
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
	    
	    <div id="list">
	    	<div id="updateMain">
		    	<form id="informUpdateForm" action="${pageContext.request.contextPath}/front">
					<input type="hidden" value="updateStore" name="methodName">
					<input type="hidden" value="user" name="key">
					<input type="hidden" value="${store.storeId}" name="storeId">					
					
		    		<p class="mainText">
						<label for="storeName">매장이름:</label>
						<input type="text" id="storeName" name="storeName" value="${store.storeName}">
					</p>
		    		<p class="subText">
						<label for="storeName">주소:</label>
						<input type="text" id="address" name="address" value="${store.address}">
					</p>
		    		<p class="subText">
						<label for="storeName">지역:</label>
						<input type="text" id="region" name="region" value="${store.region}">
					</p>
		    		<p class="subText">
		    			<label for="storeName">번호:</label>
						<input type="text" id="phoneNumber" name="phoneNumber" value="${store.phoneNumber}">
		    		</p>
		    		<p class="subText">
		    			<label for="storeName">매장 정보:</label>
						<input type="text" id="description" name="description" value="${store.description}">
		    		</p>
		    		<div class="subText">
		    			<p class="subText">이름 : </p>
						<p class="subText">${user.username}</p>
		    		</div>
		    		
		    		<div id="informBtn">
		    			<button id="informUpdate">수정</button>
		    		</div>
		    	</form>
	    	</div>
	    </div>
	    
	    <div id="footer">	
	    </div>
    </div>
    
	
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/user/inform/update/update.js"></script>
</html>