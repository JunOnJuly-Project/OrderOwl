<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/user/auth/login/login.css">
<title>Insert title here</title>
</head>
<body>
	<div id="background">
		<div id="header">
			<h1>header</h1>
		</div>
		
		<div id="main">
			<h1>
				<form id="accountForm" action="${pageContext.request.contextPath}/front">
					<input type="hidden" value="account" name="methodName">
					<input type="hidden" value="user" name="key">
					
					<div class="formDiv">
						<label for="storeName">매장명</label>
						<input type="text" id="storeName" name="storeName">
					 </div>
					<div class="formDiv">
						<label for="userName">사업자명</label>
						<input type="text" id="userName" name="username">
					 </div>
					<div class="formDiv">
						<label for="password">비밀번호</label>
						<input type="password" id="password" name="password">
					 </div>
					<div class="formDiv">
						<label for="confirmPassword">비밀번호 확인</label>
						<input type="password" id="confirmPassword" name="confirmPassword">
				 	</div>	
					<div class="formDiv">
						<label for="email">이메일</label>
						<input type="text" id="email" name="email">
					 </div>
					<div class="phoneNumber">
						<label for="phoneNumber">매장 번호</label>
						<input type="text" id="phoneNumber" name="phoneNumber">
					 </div>
					<div class="formDiv">
						<label for="description">매장 정보</label>
						<input type="text" id="description" name="description">
					 </div>
					<div class="formDiv">
						<label for="region">지역</label>
						<input type="text" id="region" name="region">
					 </div>
					<div class="formDiv">
						<label for="address">매장 주소</label>
						<input type="text" id="address" name="address">
					 </div>
					<div class="formDiv">
						<label for="storeNum">사업자 번호</label>
						<input type="text" id="storeNum" name="storeNum">
					 </div>
					<div class="formDiv">
						<label for="imgSrc">대표 이미지</label>
						<input type="text" id="imgSrc" name="imgSrc">
					 </div>
					
					<div id="buttonDiv">
						<button type="button">취소</button>
						<button>가입 요청</button>
					</div>
				</form>
			
			</h1>
		</div>
		
		<div id="footer">
			<p>footer</p>
		</div>
	</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/user/auth/login/login.js"></script>
</html>