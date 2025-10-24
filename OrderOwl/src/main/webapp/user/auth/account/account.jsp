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
				<form id="loginForm" action="${pageContext.request.contextPath}/front">
					<input type="hidden" value="login" name="methodName">
					<input type="hidden" value="user" name="key">
					
					<div class="formDiv">
						<label for="userEmail">email</label>
						<input type="text" id="userEmail" name="userEmail">
					 </div>
					 
					 <div class="formDiv">
						<label for="password">password</label>
						<input type="password" id="password" name="password">
					</div>
					
					<div id="buttonDiv">
						<button>Login</button>
						<button>Account</button>
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