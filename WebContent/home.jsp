<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
if(session.getAttribute("name")==null)
	response.sendRedirect("index.jsp");

%>
<h1>Dear ${name} Welcome To home page </h1>

<form action="LogoutController" method="get">
<button class="btn btn-primary" type="Submit">Log out</button><br/><br/><br>
</form>

</body>
</html>