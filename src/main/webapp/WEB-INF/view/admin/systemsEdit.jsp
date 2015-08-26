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
		<script src="<c:url value="/resources/js/jquery-min.js" />"></script>
		<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	</head>
	<body>
		<div class="container-fluid">
			<ol class="breadcrumb">
	  			<li><a href="<%=request.getContextPath()%>/admin#systems">Home</a></li>
	  			<li class="active">Systems</li>
			</ol>
			<form role="form" method="post" action="<%=request.getContextPath()%>${addOrEditAction}">
				<div class="form-group">
					<label for="sysName">System</label>
					<input class="form-control" id="sysName" type="text" name="systemName" value="${systemName}" ${isEdit ? "disabled" : ""}>
				</div>
				<div class="form-group">
					<label for="desc">System</label>
					<input class="form-control" id="desc" type="text" name="description" value="${description}">
				</div>
				<input type="submit" value="Submit" class="btn btn-primary" />
			</form>
		</div>
	</body>
</html>