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
		<header class="navbar navbar-static-top bs-docs-nav" id="top" role="banner">
			<div class="container">
			    <nav class="navbar navbar-default" role="navigation">
			    	<div class="container-fluid">
			        	<div class="navbar-header">              
			        		<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse"> 
			               		<span class="sr-only">Toggle navigation</span> 
			              		<span class="icon-bar"></span> 
			               		<span class="icon-bar"></span>
			               		<span class="icon-bar"></span>
			             	</button>
			             	<a class="navbar-brand" href="#">SURU Feature Toggle System</a>
			           </div>
			           <div class="collapse navbar-collapse">

			            	<ul class="nav navbar-nav">
			                	<li role="presentation" class="active"><a href="#systems" data-toggle="tab">Systems</a></li>
			                	<li><a href="#groups" data-toggle="tab">Groups</a></li>
			                	<li role="presentation"><a href="#verify" data-toggle="tab">Verify</a></li>
			            	</ul>
			          </div>
			        </div>
			    </nav>
		   </div>
	   </header>
	   <div class="container">
		    <div class="tab-content">
		        <div class="tab-pane active" id="systems">
		            <a href="<%=request.getContextPath()%>${addNewSystemHref}">Add New System</a>
		            <br>
		            <table class="table">
		            	<thead>
		            		<tr>
		            			<th>Name</th>
		            			<th>Description</th>
		  						<th>Action</th>
		            		</tr>
		            	</thead>
		            	<tbody>
		            		<c:forEach var="system" items="${systems}">
		            			<tr>
		            				<td><a href="<%=request.getContextPath()%>${system.detailHref}">${system.systemName}</a></td>
		            				<td>${system.description}</td>
		            				<td><a href="<%=request.getContextPath()%>${system.deleteHref}">Delete</a></td>
		            			</tr>
		            		</c:forEach>
		            	</tbody>
		            </table>
		        </div>
		        <div class="tab-pane" id="groups">
		            <p><a href="<%=request.getContextPath()%>${addNewGroupHref}">Add New Group</a></p>
		            <table class="table">
		            	<thead>
		            		<tr>
		            			<th>Group</th>
		            			<th>Action</th>
		            		</tr>
		            	</thead>
		            	<tbody>
		            		<c:forEach var="group" items="${groups}">
		            			<tr>
		            				<td><a href="<%=request.getContextPath()%>${group.detailHref}">${group.groupName}</a></td>
		            				<td><a href="<%=request.getContextPath()%>${group.deleteHref}">Delete</a></td>
		            			</tr>
		            		</c:forEach>
		            	</tbody>
		            </table>
		        </div>
		        <div class="tab-pane" id="verify">
		            <p><a href="<%=request.getContextPath()%>/admin/getfeatures">Show Features for given system and user ID</a></p>
		        </div>
		    </div>
		</div>
		<script>
			$('.navbar-collapse .nav > li > a').click(function () {
				$('.collapse.in').removeClass('in').css('height', '0'); 
			});
		</script>
	</body>
</html>