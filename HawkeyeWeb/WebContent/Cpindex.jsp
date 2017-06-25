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
				// Button Set
				$("#radiotab").buttonset();
				});
		</script>
</head>
<body>
<table width=100% align="center" style="border:1px solid #ffffff;">
<tr><td colspan=12 align="center" style="background-color:00000"><img src="logo.jpg" alt="search" /></td></tr>

<tr>
<td colspan=12>
		<div id="radiotab">
			<input type="radio" id="radio0" name="radio" checked="checked" /><label for="radio0"><a href="welcome.jsp" target="iframe_a">Home</a></label>
			<input type="radio" id="radio4" name="radio" /><label for="radio4"><a href="treeUITest.jsp" target="iframe_a">Method Trace Display</a></label>
			<input type="radio" id="radio5" name="radio" /><label for="radio5"><a href="DisplayBeanServlet" target="iframe_a">SQL Trace Display</a></label>
			<input type="radio" id="radio6" name="radio" /><label for="radio6"><a href="WSStatsDisplayServlet" target="iframe_a">Webservice Trace Display</a></label>
			<input type="radio" id="radio7" name="radio" /><label for="radio7"><a href="MemoryUIServlet" target="iframe_a">MemoryUtilization</a></label>
			<input type="radio" id="radio8" name="radio" /><label for="radio8"><a href="Maintenance.jsp" target="iframe_a">Maintenance</a></label>									
			<input type="radio" id="radio9" name="radio" /><label for="radio9"><a href="WatchServlet?ch=display" target="iframe_a">Watches</a></label>
		</div>
</td>
</tr>
</table>
<iframe src="welcome.jsp" name="iframe_a" width="100%" height="500" scrolling="auto" frameborder="0"></iframe>
</body>
</html>