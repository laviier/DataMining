<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login with Facebook</title>
</head>
<body>
<br/>
<div align="center">
	<form name="login2Form" action="LoginFacebook" method ="GET" align="center">
	<%session.setAttribute("owncallFB", "yes");%>
	<!-- 
		<button type="submit"><img src="img/facebook-login.png" alt="submit"></button>
		 -->
		<input type="image" src="img/fblogin.png" alt="Submit">
	</form>
</div>
</body>
</html>
