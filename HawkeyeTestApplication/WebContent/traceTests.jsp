<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
	

function loadservlet_onclick(ch)
{
var xmlhttp;
//$('#test').value('Executing....');
document.getElementById('test').innerHTML="Executing...";
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
  
xmlhttp.onreadystatechange=function ()
{
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
	  //$('#test').value(xmlhttp.responseText);
	  document.getElementById('test').innerHTML=xmlhttp.responseText;
    }
  }
  
  
 var load = "TraceTestServlet";
xmlhttp.open("GET",load,true);
xmlhttp.send();
}

</script>
		<style type="text/css">
			/*demo page css*/
			body{ font: 100% Cambria, Georgia, serif; margin: 50px;}
			.demoHeaders { margin-top: 2em; }
			ul#icons { margin: 0; padding: 0;}
			ul#icons li { margin: 2px; position: relative; padding: 4px 0; cursor: pointer; float: left; list-style: none; }
			ul#icons span.ui-icon { float: left; margin: 0 4px; }
			.columnbox { width: 500px; }
			#eq span { height: 120px; float: left; margin: 15px; }
			#countries { width: 300px; }
		</style>
<title>Test Application</title>
</head>
<body>
<form name="rsltfr">
<table align="center" id="istable1" cellpadding="5px"
style="border:0px solid #000000;">
<tr>
<td><h3>Execute Sample Method Trace</h3></td>
</tr>
<tr>
<td colspan="10" align="left" >
<input  type="button"  value="Execute" id="execute" onclick="loadservlet_onclick('execute')">
</td>
</tr>
<tr>
<td>
<div id="test"></div>
</td>
</tr>
</table>
</form>
</body>
</html>