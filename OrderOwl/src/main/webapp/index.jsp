<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Index Page</h1>

	<form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/front">
			<input type="hidden" name="key" value = "cusOrder" /> <!-- Controller를 찾는 정보 -->
			<input type="hidden" name="methodName" value = "selectByModelNum" />  <!-- 메소드이름 -->
			<input type="text" name="tableNo"/>
			<br>
			<button type ="submit">테스트하기</button>
		</form>


</body>
</html>