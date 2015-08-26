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
		<div class="container">
			<ol class="breadcrumb">
		  		<li><a href="<%=request.getContextPath()%>/admin#systems">Home</a></li>
		  		<li><a href="<%=request.getContextPath()%>/admin#systems">Systems</a></li>
		  		<li><a href="<%=request.getContextPath()%>/admin/system/${systemName}">${systemName}</a></li>
		  		<li><a href="<%=request.getContextPath()%>/admin/system/${systemName}">Features</a></li>
		  		<li><a href="<%=request.getContextPath()%>/admin/system/${systemName}/feature/${featureName}">${featureName}</a></li>
		  		<li><a href="<%=request.getContextPath()%>/admin/system/${systemName}/feature/${featureName}">Strategies</a></li>
		  		<li class="active">${strategyName}</li>
			</ol>
			<BR>
			<form role="form" method="post" action="<%=request.getContextPath()%>${addOrEditAction}">
				<div class="form-group">
					<label for="feature">Feature</label>
					<input class="form-control" id="feature" type="text" readonly value="${featureName}">
				</div>
				<div class="form-group">
					<label for="stratname">Strategy Name</label>
					<input class="form-control" id="stratname" type="text" name="strategyName">
				</div>
				<div class="form-group">
					<label for="stat">Status</label>
					<select class="form-control" id="strattype" name="strategyType">
						<option>GROUP</option>
					</select>
				</div>
				<input type="submit" value="Submit" class="btn btn-primary" />
			</form>
		</div>
	</body>
</html>