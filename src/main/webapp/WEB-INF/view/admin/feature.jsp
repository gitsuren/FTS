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
		  		<li class="active">${feature.featureName}</li>
			</ol>
			<form role="form" method="post" action="<%=request.getContextPath()%>${addOrEditAction}">
				<div class="form-group">
					<label for="systemName">System</label>
					<input class="form-control" id="systemName" type="text" readonly value="${systemName}">
				</div>
				<div class="form-group">
					<label for="feature">Feature Name</label>
					<c:choose>
						<c:when test="${isNew}">
							<input type="text" class="form-control" id="feature" name="featureName">
						</c:when>
						<c:otherwise>
							<input type="text" class="form-control" placeholder="Text input" id="feature" readonly name="featureName" value="${feature.featureName}">
						</c:otherwise>
					</c:choose>	
				</div>
				<div class="form-group">
					<label for="desc">Description</label>
					<c:choose>
						<c:when test="${isNew}">
							<input type="text" class="form-control" id="desc" name="description">
						</c:when>
						<c:otherwise>
							<input type="text" class="form-control" id="desc" readonly name="description" value="${feature.description}">
						</c:otherwise>
					</c:choose>	
				</div>
				<div class="form-group">
					<label for="stat">Status</label>
					<select class="form-control" id="stat" name="status">
						<option ${feature.status == "NOT_RELEASED" ? "selected" : ""}>NOT_RELEASED</option>
						<option ${feature.status == "LIMITED" ? "selected" : ""}>LIMITED</option>
						<option ${feature.status == "RELEASED" ? "selected" : ""}>RELEASED</option>
					</select>
				</div>
				<c:choose>
					<c:when test="${isEdit}">
						<table class="table">
							<caption>Strategies</caption>
							<thead>
								<tr>
									<th>Strategy Name</th>
									<th>Type</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${empty strategies}">
										<tr>
											<td colspan="3">No strategies found</td>
										</tr>
									</c:when>
								</c:choose>
								<c:forEach var="it" items="${strategies}">
									<tr>
										<td><a href="<%=request.getContextPath()%>${it.detailHref}">${it.strategyName}</a></td>
										<td>${it.strategyType}</td>
										<td><a href="<%=request.getContextPath()%>${it.deleteHref}">Delete</a></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="3"><a href="<%=request.getContextPath()%>${feature.addStrategyHref}">Add New Feature Strategy</a></td>
								</tr>
							</tbody>
						</table>
									
					</c:when>
				</c:choose>
				<input type="submit" value="Submit" class="btn btn-primary" />
			</form>
		</div>
	</body>
</html>