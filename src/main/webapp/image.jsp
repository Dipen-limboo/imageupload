<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.List" %>
<%@ page import = "Controller.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
	<% List <User> list = (List<User>) request.getAttribute("UserList"); %>
		<tr>
			<th>Id</th>
			<th>First Name</th>
			<th>Last Name </th>
			<th> Images </th>
		</tr>
		<% if (list != null){
			for (User usr: list){
				
			
			%>
		
		<tr>
			<td><%= usr.getId() %></td>
			<td><%= usr.getFname() %></td>
			<td><%= usr.getLname() %></td>
			<td><%= usr.getImage() %></td>
		</tr>
		<% 		}
			}
		%>
	</table>
</body>
</html>