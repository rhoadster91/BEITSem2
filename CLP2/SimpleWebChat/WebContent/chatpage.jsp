<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form name = "form2" action = "SimpleChatServlet" method = "get">

Hello and welcome to chat page <br>
Say something:<br>
<input type = "text" name = "chat" width = 28/>
<input type = "hidden" name = "type" value = "msg" />
<button name = "cmd1" type = "submit">Say it</button>

</form>
</body>
</html>