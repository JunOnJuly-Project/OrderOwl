<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OrderOwl</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/user/menu/insert/insert.css">
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
	    
	    <div id="subselect">
	    	<h2>
				<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectAllMenu'"id="menus" class="selectBtn">목록</button>
				<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=preInsert'" id="insert" class="selectBtn">메뉴 추가</button>
				<button onclick="location.href='${pageContext.request.contextPath}/user/menu/category/category.jsp'" id="category" class="selectBtn">카테고리 추가</button>
			</h2>	    
	    </div>
	    
	    <div id="list">	
		    <div id="listInner">
				<form id="writeForm" name="writeForm" method="post" action="${pageContext.request.contextPath}/front" enctype="multipart/form-data">
					<input type="hidden" name="key" value = "user" />
					<input type="hidden" name="methodName" value = "insertMenu" /> 
					 
					<div class="inputDiv">
				 		<label for="categiryName">이름</label>
				 		<input type="text" name="categiryName" id="categiryName">
			 		</div>
			 		
					<div class="inputDiv">
				 		<label for="categiryName">메뉴</label>
				 		<select>
				 			<option value="">--선택--</option>
				 		</select>
			 		</div>
			 		
			 		
			 		
			 		<input type="submit" id="inputSubmit">
		 		</form>
		    </div>
	    </div>
	    
	    <div id="footer">
	
	    </div>
    </div>
    
	
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/user/menu/insert/insert.js"></script>
</html>