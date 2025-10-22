<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/user/menu/update/update.css">
</head>
<body>
    <div id="background">
    	<div id="header">
    		<h1>header</h1>
    	</div>
    	
	    <div id="select">
	    	<button id="menu" class="selectBtn">메뉴</button>
	    	<button id="inform" class="selectBtn">정보</button>
	    	<button id="qr" class="selectBtn">QR</button>
	    	<button id="order" class="selectBtn">주문</button>
	    </div>
	    
	    <div id="subselect">
	    	<h2>
				<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectAllMenu'"id="menus" class="selectBtn">목록</button>
				<button onclick="location.href='${pageContext.request.contextPath}/user/menu/insert/insert.jsp'" id="insert" class="selectBtn">추가</button>
			</h2>	    
	    </div>
	    
	    <div id="list">	
			<form id="writeForm" name="writeForm" method="post" action="${pageContext.request.contextPath}/front">
				<input type="hidden" name="key" value = "user" />
				<input type="hidden" name="methodName" value = "updateMenu" /> 
				<input type="hidden" name="menuId" value = "${menu.menuId}" /> 
				<input type="hidden" name="storeId" value = "${menu.storeId}" /> 
				 
				<div class="inputDiv">
			 		<label for="name">메뉴명</label>
			 		<input type="text" name="name" id="name" value="${menu.menuName}">
		 		</div>
		 		
		 		<div class="inputDiv">
			 		<label for="price">가격</label>
			 		<input type="number" name="price" id="price" value="${menu.price}">
		 		</div>
		 		
		 		<div class="inputDiv">
			 		<label for="description">설명</label>
			 		<input type="text" name="description" id="description" value="${menu.description}">
		 		</div>
		 		
		 		<div class="inputDiv">
			 		<label for="src">이미지</label>
			 		<input type="url" name="src" id="src" value="${menu.imgSrc}">
		 		</div>
		 		
		 		<div class="inputDiv">
			 		<fieldset>
			 			<legend>카테고리1</legend>
					 		<label for=cat1opt1>분류1</label>
					 		<input type="radio" name="category1Code" id="cat1opt1" value=1 ${menu.category1Code == 1 ? "checked" : ""}>
					 		<label for=cat1opt2>분류2</label>
					 		<input type="radio" name="category1Code" id="cat1opt2" value=2 ${menu.category1Code == 2 ? "checked" : ""}>
					 		<label for=cat1opt3>분류3</label>
					 		<input type="radio" name="category1Code" id="cat1opt3" value=3 ${menu.category1Code == 3 ? "checked" : ""}><br>
			 		</fieldset>
		 		</div>
		 		
		 		<div class="inputDiv">
			 		<fieldset>
			 			<legend>카테고리2</legend>
				 		<label for="cat2opt1">분류1</label>
				 		<input type="radio" name="category2Code" id="cat2opt1" value=1 ${menu.category2Code == 1 ? "checked" : ""}>
				 		<label for="cat2opt2">분류2</label>
				 		<input type="radio" name="category2Code" id="cat2opt2" value=2 ${menu.category2Code == 2 ? "checked" : ""}>
				 		<label for="cat2opt3">분류3</label>
				 		<input type="radio" name="category2Code" id="cat2opt3" value=3 ${menu.category2Code == 3 ? "checked" : ""}><br>
			 		</fieldset>
		 		</div>
		 		
		 		<div class="inputDiv">
			 		<fieldset>
			 			<legend>추천 여부</legend>
				 		<label for="추천">추천</label>
				 		<input type="radio" name="checkRec" id="추천" value="Y" ${menu.checkRec == "Y" ? "checked" : ""}>
				 		<label for="일반">일반</label>
				 		<input type="radio" name="checkRec" id="일반" value="N" ${menu.checkRec == "N" ? "checked" : ""}><br>
			 		</fieldset>
		 		</div>
		 		
		 		<div class="inputDiv">
			 		<fieldset>
			 			<legend>옵션</legend>
				 		<label for="옵션1">옵션1</label>
				 		<input type="checkbox" name="orderRequest" id="옵션1" value="옵션1" ${menu.orderRequest == "옵션1" ? "checked" : ""}>
				 		<label for="옵션2">옵션2</label>
				 		<input type="checkbox" name="orderRequest" id="옵션2" value="옵션2" ${menu.orderRequest == "옵션2" ? "checked" : ""}>>
				 		<label for="옵션3">옵션3</label>
				 		<input type="checkbox" name="orderRequest" id="옵션3" value="옵션3" ${menu.orderRequest == "옵션3" ? "checked" : ""}>><br>
			 		</fieldset>
		 		</div>
		 		
		 		<div class="inputDiv">
			 		<label for="closeTime">마감 시간</label>
			 		<input type="time" name="closeTime" id="closeTime" value="${menu.closeTime}"><br>
		 		</div>
		 		
		 		<div class="inputDiv">
			 		<fieldset>
			 			<legend>옵션</legend>
				 		<label for="마감">마감</label>
				 		<input type="radio" name="soldOut" id="마감" value="Y" ${menu.soldOut == "Y" ? "checked" : ""}>
				 		<label for="비마감">비마감</label>
				 		<input type="radio" name="soldOut" id="비마감" value="N" ${menu.soldOut == "N" ? "checked" : ""}><br>
			 		</fieldset>
		 		</div>
		 		
		 		<input type="submit" id="inputSubmit">
	 		</form>
	    </div>
	    
	    <div id="footer">
	    	<h2>footer</h2>	
	    </div>
    </div>
    
	
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/user/menu/update/update.js"></script>
</html>