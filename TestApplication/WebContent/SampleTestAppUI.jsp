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
<table align="center" style="border:1px solid #ffffff;">
<tr><td colspan=12 align="center" style="background-color:00000"><img src="logo.jpg" alt="search" /></td></tr>
<tr><td>&nbsp;</td></tr>
<tr><td>&nbsp;</td></tr>
<tr><th><font size=3>Sample Web Application</font></th></tr>
<tr>
<td>
		<div id="tabs">
			<ul>
				<li><a href="sqlTests.jsp" target="#tabs-1">Sample SQL tests</a></li>
				<li><a href="traceTests.jsp" target="#tabs-2">Sample Trace tests</a></li>
				<li><a href="#tabs-3">Sample WS tests</a></li>
			</ul>
			<div id="tabs-1"></div>
			<div id="tabs-2"></div>
			<div id="tabs-3">To Do</div>
		</div>
</td>
<tr>
</table>
</body>
</html>