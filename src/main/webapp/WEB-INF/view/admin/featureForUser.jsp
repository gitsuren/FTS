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
	  			<li class="active">Verify</li>
			</ol>
			<form role="form" method="post" action="">
				<div class="form-group">
					<label for="sysName">System</label>
					<select class="form-control" id="sysName" name="systemName">
						<option value="" />
						<c:forEach var="system" items="${systems}">
							<option value="${system.systemName}"
							<c:choose>
								<c:when test="${system.systemName eq systemName}">selected</c:when>
							</c:choose>
							>${system.systemName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="idValue">ID</label>
					<input class="form-control" id="idValue" type="text" name="userId" value="${userId}">
				</div>
				<input type="submit" value="Submit" class="btn btn-primary" />
				<br>
				<table class="table">
					<caption>Qualified Features</caption>
					<thead>
						<tr>
							<th>Feature</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="feature" items="${featureNames}">
							<tr>
								<td>${feature}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:choose>
					<c:when test="${empty featureNames && !empty hasFeatures}">
						<p>There are no feature(s) in system "${systemName}" that are available to user "${userId}"</p>
					</c:when>
				</c:choose>
			</form>
		</div>
	</body>
</html>