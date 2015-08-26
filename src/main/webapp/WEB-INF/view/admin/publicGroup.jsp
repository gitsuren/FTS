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
		  		<li><a href="<%=request.getContextPath()%>/admin#groups">Groups</a></li>
		  		<li class="active">${group.groupName}</li>
			</ol>
			<c:choose>
				<c:when test="${isEdit}">
					<div class="form-group">
						<label for="groupName">Group Name</label>
						<input class="form-control" id="groupName" type="text" readonly value="${group.groupName}">
						<form role="form" method="post" action="<%=request.getContextPath()%>/admin/group/${group.groupName}/members">
							<div class="form-group">
	    						<div class="input-group">
									<table class="table">
										<caption>Members</caption>
										<thead>
											<tr>
												<th>Member ID</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="it" items="${members}">
												<tr>
													<td>${it.memberId}</td>
													<td><a href="<%=request.getContextPath()%>${it.deleteHref}">Delete</a></td>
												</tr>
											</c:forEach>
											<tr>
												<td><input class="from-control" id="memberId" type="text" name="memberId" /></td>
												<td><input type="submit" value="Add Member" class="btn btn-primary" /></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</form>
					</div>
				</c:when>
				<c:otherwise>
						<form role="form" method="post" action="<%=request.getContextPath()%>${addOrEditAction}">
							<div class="form-group">
	    						<div class="input-group">
	    							<label for="groupName">Group Name</label>
									<input class="form-control" id="groupName" type="text" placeholder="Enter new group name" name="groupName">
								</div>
							</div>
							<input type="submit" class="btn btn-primary" value="Submit" />
						</form>
				</c:otherwise>
			</c:choose>
		</div>
	</body>
</html>