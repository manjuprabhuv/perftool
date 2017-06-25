<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test Application</title>
		<link type="text/css" href="./Aristo-jquery/css/Aristo/Aristo.css" rel="stylesheet" />	
		<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
		<script type="text/javascript" src="http://code.jquery.com/ui/1.8.13/jquery-ui.min.js"></script>
		<script type="text/javascript">

			$(function(){
				
			// Tabs
				$('#tabs').tabs();
				});
		</script>
</head>
<body>
<table width=100% align="center" style="border:1px solid #ffffff;">
<tr><td colspan=12 align="center" style="background-color:00000"><img src="logo.jpg" alt="search" /></td></tr>

<tr>
<td colspan=12>
		<div id="tabs">
			<ul>
				<li><a href="sqlTests.jsp" target="#tabs-1">Sample Web App SQL Testing</a></li>
				<li><a href="traceTests.jsp" target="#tabs-2">Sample Web App Method Trace Testing</a></li>
				<li><a href="WebServiceTest.jsp" target="#tabs-3">Sample Web App WebService Testing</a></li>				
				<li><a href="treeUITest.jsp" target="#tabs-4">Method Trace Display</a></li>
				<li><a href="DisplayBeanServlet" target="#tabs-5">SQL Trace Display</a></li>
				<li><a href="MemoryUIServlet" target="#tabs-6">MemoryUtilization</a></li>
				<li><a href="Maintenance.jsp" target="#tabs-6">Maintenance</a></li>
			</ul>
			<div id="tabs-1"></div>
			<div id="tabs-2"></div>
			<div id="tabs-3"></div>
			<div id="tabs-4"></div>
			<div id="tabs-5"></div>
			<div id="tabs-6"></div>
		</div>
</td>
<tr>
</table>
</body>
</html>