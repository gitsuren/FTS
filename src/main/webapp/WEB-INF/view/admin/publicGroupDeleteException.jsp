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
		<div class="container">
			<div class="container">
				<ol class="breadcrumb">
		  			<li><a href="<%=request.getContextPath()%>/admin#systems">Home</a></li>
		  		</ol>
		  	</div>
			<div class="alert alert-danger">
				<h4>Unable to delete group ${groupName} due to existing use.</h4>
				<table class="table">
					<thead>
						<tr>
							<th>System</th>
							<th>Feature</th>
							<th>Strategy</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="it" items="${strategies}">
							<tr>
								<td>${it.systemName}</td>
								<td>${it.featureName}</td>
								<td>${it.strategyName}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<p>Please remove the group from each strategy before deleting</p>
			</div>
		</div>
	</body>
</html>