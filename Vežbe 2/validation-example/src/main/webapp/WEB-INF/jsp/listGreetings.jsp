<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Greetings : View all</title>

<style>
html, body {
	height: 100%;
}

body {
	margin: 0;
	background: linear-gradient(45deg, #49a09d, #5f2c82);
	font-family: sans-serif;
	font-weight: 100;
}

.container {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}

table {
	width: 800px;
	border-collapse: collapse;
	overflow: hidden;
	box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}

th, td {
	padding: 15px;
	background-color: rgba(255, 255, 255, 0.2);
	color: #fff;
}

th {
	text-align: left;
}

thead {th { background-color:#55608f;
	
}

}
tbody {tr { &:hover {
			background-color : rgba(255, 255, 255, 0.3);
	
}

}
td {
	position: relative; &: hover { & : before { 
				 content : "";
	position: absolute;
	left: 0;
	right: 0;
	top: -9999px;
	bottom: -9999px;
	background-color: rgba(255, 255, 255, 0.2);
	z-index: -1;
}

}
}
}
.form-style-4 {
	font-size: 16px;
	background: #495C70;
	border: 5px solid #53687E;
}

.form-style-4 input[type=submit], .form-style-4 input[type=button] {
	font-family: Georgia, "Times New Roman", Times, serif;
	font-size: 16px;
	color: #fff;
}

.form-style-4 input[type=submit], .form-style-4 input[type=button] {
	background: #576E86;
	border: none;
	padding: 8px 10px 8px 10px;
	border-radius: 5px;
	color: #A8BACE;
}

.form-style-4 input[type=submit]:hover, .form-style-4 input[type=button]:hover
	{
	background: #394D61;
}
</style>
</head>
<body>

	<div class="container">
		<table>
			<thead>
				<tr>
					<th>Greeting ID</th>
					<th>Text</th>
					<th>Update</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${greetings}" var="greeting">
					<tr>
						<td><c:out value="${greeting.id}" /></td>
						<td><c:out value="${greeting.text}" /></td>
						<td><a
							href="<c:url value="/greetings/update/${greeting.id}"/>">Update</a></td>
						<td><a
							href="<c:url value="/greetings/delete/${greeting.id}"/>">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<form class="form-style-4" action="/myapp/greetings/new" method="get">
			<div style="text-align: center;">
				<input type="submit" value="Create new greeting">
			</div>
		</form>
	</div>
</body>
</html>
