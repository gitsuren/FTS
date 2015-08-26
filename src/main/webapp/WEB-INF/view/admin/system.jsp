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
	  			<li class="active">${system.systemName}</li>
			</ol>
			<div class="form-group">
				<label for="systemName">System</label>
				<input class="form-control" id="systemName" type="text" readonly value="${system.systemName}">
				<br>
				<label for="systemDescription">Description</label>
				<input class="form-control" id="systemDescription" type="text" readonly value="${system.description}">
			</div>
			<table class="table">
				<caption>Features</caption>
				<thead>
					<tr>
						<th>Name</th>
						<th>Description</th>
						<th>Status</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="feature" items="${features}">
						<tr>
							<td><a href="<%=request.getContextPath()%>${feature.detailHref}">${feature.featureName}</a></td>
							<td>${feature.description}</td>
							<td>${feature.status}</td>
							<td><a href="<%=request.getContextPath()%>${feature.deleteHref}">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<a href="<%=request.getContextPath()%>/admin/system/${system.systemName}/features/add">Add Feature</a>
		</div>
	</body>
</html>