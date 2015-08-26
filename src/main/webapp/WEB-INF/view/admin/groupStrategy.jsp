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
		  		<li class="active">${groupStrategy.strategyName}</li>
			</ol>
			<form role="form" method="post" action="<%=request.getContextPath()%>${chooseGroupHref}">
				<div class="form-group">
					<label for="stratName">Feature</label>
					<input class="form-control" id="stratName" type="text" readonly value="${groupStrategy.strategyName}">
				</div>
				<div class="form-group">
					<label for="typeName">Type</label>
					<input class="form-control" id="typeName" type="text" readonly value="GROUP">
				</div>
				<br>
				<table class="table">
					<caption>Registered Groups</caption>
					<thead>
						<tr>
							<th>Group</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="it" items="${featureGroups}">
							<tr>
								<td>${it.groupName}</td>
								<td><a href="<%=request.getContextPath()%>${it.removeFeatureGroupHref}">Remove</a></td>
							</tr>
						</c:forEach>
						<tr>
							<td>
								<select class="form-control" id="groupNM" name="groupName">
									<c:forEach var="groupName" items="${groupNames}">
										<option value="${groupName}">${groupName}</option>
									</c:forEach>
								</select>
							</td>
							<td><input type="submit" value="Add" class="btn btn-primary" /></td>							
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</body>
</html>