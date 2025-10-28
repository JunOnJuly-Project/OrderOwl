<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>OrderOwl</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/user/inform/inform/inform.css">
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
	    	<div id="informMain">
		    	<div id="informs">
		    		<div id="informText">
			    		<h1 class="mainText">${store.storeName}</h1>
			    		<p class="subText informText">매장 정보 : ${store.description}</p>
			    		<p class="subText">주소 : ${store.address}</p>
			    		<p class="subText">지역 : ${store.region}</p>
			    		<p class="subText">번호 : ${store.phoneNumber}</p>
			    		<p class="subText">이름 : ${user.username}</p>
		    		</div>
		    		
		    		<div id="informBtn">
		    			<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectForUpdate'" id="informUpdate" class="informRightBtn">수정</button>
		    			<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=quitStore&userId=${user.userId}'" id="informDelete" class="informRightBtn">탈퇴</button>
		    		</div>
		    	</div>
		    		
	    		<div id="informGraph">
   				    <div id="subselect">
				    	<h2>
							<!-- <button id="menu" class="selectBtn">메뉴별</button> -->
							<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectSales&state=hour'" id="hour" class="selectBtn">시간별</button>
							<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectSales&state=day'" id="day" class="selectBtn">일별</button>
							<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectSales&state=week'" id="week" class="selectBtn">주별</button>
							<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectSales&state=month'" id="month" class="selectBtn">월별</button>
							<button onclick="location.href='${pageContext.request.contextPath}/front?key=user&methodName=selectSales&state=year'" id="year" class="selectBtn">년별</button>
						</h2>	    
				    </div>
				    
				    <div id=mainChartDiv>
		    			<canvas id="mainChart"></canvas>
				    </div>
				    
				    <div id=subChartDiv>
		    			<canvas id="subChart"></canvas>
				    </div>
	    		</div>
	    	</div>
	    </div>
	    
	    <div id="footer">
	    </div>
    </div>
    
	
</body>
<script>
    const salesData = JSON.parse('<%= new com.google.gson.Gson().toJson(request.getAttribute("sales")) %>');
    
    const labels = Object.keys(salesData);
    const dataValues = Object.values(salesData);
	const state = JSON.parse('<%= new com.google.gson.Gson().toJson(request.getAttribute("state")) %>');
    console.log('salesData:', salesData);
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/user/inform/inform/inform.js"></script>
</html>