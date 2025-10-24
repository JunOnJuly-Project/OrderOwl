<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/user/qr/qr/qr.css">
</head>
<body>
	<!--<c:forEach var="menu" items="${menus}" varStatus="status">
  		<div id="modal${menu.menuId}" class="modal">
  			<p>메뉴명 : ${menu.menuName}</p>
  			<p>가격 : ${menu.price}</p>
  			<p>설명 : ${menu.description}</p>
  			<p>이미지 : ${menu.imgSrc}</p>
  			<p>분류1 : ${menu.category1Code}</p>
  			<p>분류2 : ${menu.category2Code}</p>
  			<p>추천 여부 : ${menu.checkRec}</p>
  			<p>옵션 : ${menu.orderRequest}</p>
  			<p>마감 시간 : ${menu.closeTime}</p>
  			<p>마감 여부 : ${menu.soldOut}</p>
  		</div>
	</c:forEach>-->
	
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
			<div id="qrMain">
				<!--<c:forEach var="q" items="${qr}" varStatus="status">
			  		<div id="modal${q.qrcodeId}" class="modal">
			  			<p>메뉴명 : ${q.qrImgSrc}</p>
			  			<p>메뉴명 : ${q.tableId}</p>
			  			<p>메뉴명 : ${q.qrcodeData}</p>
			  			<p>메뉴명 : ${q.createdAt}</p>
			  		</div>
				</c:forEach>-->
				<c:forEach var="i" begin="1" end="8">
					<div id="div${i}">
						<div id="imageDiv">
						
						</div>
						<p>${i}</p>
					</div>
				</c:forEach>
			</div>
	    </div>
	    
	    <div id="footer">
	    	<h2>footer</h2>	
	    </div>
    </div>
    
	
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/user/qr/qr/qr.js"></script>
</html>