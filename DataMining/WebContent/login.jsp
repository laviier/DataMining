<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login with LinedIn</title>
</head>
<body>
	<form name="loginForm" action="LoginServlet" method ="GET" align="center">
	<%session.setAttribute("owncall", "yes");%>
		<button type="submit"><img src="img/linkedin-login.png" alt="submit"></button>
	</form>
	<br/>
	<br/>
	<form name="loginForm" action="LoginFBServlet" method ="GET" align="center">
		<button type="submit"><img src="img/facebook-login.png" alt="submit"></button>
		<br/>
		Login with the pre-generated access token
	</form>
	<br/>
	<br/>
	<form name="loginForm" action="LoginFacebook" method ="GET" align="center">
	<%session.setAttribute("owncallFB", "yes");%>
		<button type="submit"><img src="img/facebook-login.png" alt="submit"></button>
	</form>
</body>
</html>