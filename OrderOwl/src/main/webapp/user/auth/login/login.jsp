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
			
		</div>
		
		<div id="main">
			<div id="formsDiv">
				<h1 id="headerH1">OrderOwl</h1>
				<form id="loginForm" action="${pageContext.request.contextPath}/front">
					<input type="hidden" value="login" name="methodName">
					<input type="hidden" value="user" name="key">
					
					<div class="formDiv">
						<label for="userEmail"><h1>Email</h1></label>
						<input type="text" id="userEmail" name="userEmail">
					 </div>
					 
					 <div class="formDiv">
						<label for="password"><h1>Password</h1></label>
						<input type="password" id="password" name="password">
					</div>
					
					<div id="buttonDiv">
						<button class="authBtn">Login</button>
						<button class="authBtn" onclick="location.href='${pageContext.request.contextPath}/user/auth/account/account.jsp'" id="account" class="accountBtn" type="button" >Account</button>
					</div>
				</form>
			</div>
		</div>
		
		<div id="footer">
		</div>
	</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/user/auth/login/login.js"></script>
</html>