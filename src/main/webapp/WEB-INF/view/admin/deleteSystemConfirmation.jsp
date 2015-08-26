<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
		<title>Feature Toggling System</title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="<c:url value="/resources/css/bootstrap.min.css" />"	rel="stylesheet">
		<link href="<c:url value="/resources/css/bootstrap-theme.min.css" />"	rel="stylesheet">
		<script src="<c:url value="/resources/js/jquery-min.js" />"></script>
		<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	</head>
	<body>
		<div class="container-fluid">
			<form role="form" method="post" action="<%=request.getContextPath()%>${deleteHref}">
				<p>Are you sure you want to delete this system? This process cannot be undone.<p>
				<button type="submit" id="deleteButton" name="action" value="delete"
					class="btn btn-primary">Delete</button>
				<button type="submit" id="cancelButton" name="action" value="cancel"
					class="btn btn-primary">Cancel</button>
					<input type="hidden" name="systemName" value="${systemName}" />
			</form>
		</div>
	</body>
</html>