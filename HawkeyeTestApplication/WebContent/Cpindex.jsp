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
<tr><td colspan=12 align="center" style="background-color:00000"><p>HAWKEYE DEMO APPLICATION</p></td></tr>

<tr>
<td colspan=12>
		<div id="radiotab">
			<input type="radio" id="radio0" name="radio" checked="checked" /><label for="radio0"><a href="welcome.jsp" target="iframe_a">Home</a></label>
			<input type="radio" id="radio1" name="radio" /><label for="radio1"><a href="sqlTests.jsp" target="iframe_a">Sample Web App SQL Testing</a></label>
			<input type="radio" id="radio2" name="radio" /><label for="radio2"><a href="traceTests.jsp" target="iframe_a">Sample Web App Method Trace Testing</a></label>
			<input type="radio" id="radio3" name="radio" /><label for="radio3"><a href="WebServiceTest.jsp" target="iframe_a">Sample Web App WebService Testing</a></label>
		</div>
</td>
</tr>
</table>
<iframe src="welcome.jsp" name="iframe_a" width="100%" height="500" scrolling="auto" frameborder="0"></iframe>
</body>
</html>