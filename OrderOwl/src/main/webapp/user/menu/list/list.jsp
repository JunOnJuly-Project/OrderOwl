<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/user/menu/list/list.css">
</head>
<body>
	<c:forEach var="menu" items="${menus}" varStatus="status">
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
	</c:forEach>
	
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
				<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectAllMenu'" id="menus" class="selectBtn">목록</button>
				<button onclick="location.href='${pageContext.request.contextPath}/user/menu/insert/insert.jsp'" id="insert" class="selectBtn">추가</button>
			</h2>	    
	    </div>
	    
	    <div id="list">
	    	<table>
	    		<tr>
	    			<th>메뉴명</th>
	    			<th>가격</th>
	    			<th>설명</th>
	    			<th>처리</th>
	    		</tr>

	    		<c:forEach var="menu" items="${menus}" varStatus="status">
	    			<tr>
					    <td><button id="${menu.menuId}" class="modalBtn">${menu.menuName}</button></td>
					    <td>${menu.price}</td>
					    <td>${menu.description}</td>
						<td class="process">
							<form action="${pageContext.request.contextPath}/front">
								<input type="hidden" value="${menu.menuId}" name="menuId">
								<input type="hidden" value="user" name="key">
								<input type="hidden" value="deleteMenu" name="methodName">
								<button>삭제</button>
							</form>
							
							<form action="${pageContext.request.contextPath}/front">
								<input type="hidden" value="user" name="key">
								<input type="hidden" value="selectById" name="methodName">
								<input type="hidden" value="${menu.menuId}" name="menuId">
								<button>수정</button>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/user/menu/list/list.js"></script>
</html>