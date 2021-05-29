<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="https://unpkg.com/bootstrap@4.3.1/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>

<body>
<h1>Login Page</h1>
<form action="LoginController" method="post">
<input type="text" name="email" placeholder="Enter Email" class="form-controller"><br>
<input type="text" name="password" placeholder="Enter Password" class="form-controller"><br>
<button type="submit">Login</button>
</form>
</body>
</html>