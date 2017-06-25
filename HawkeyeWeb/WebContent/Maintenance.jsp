<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Maintenance</title>
		<link type="text/css" href="./Aristo-jquery/css/Aristo/Aristo.css" rel="stylesheet" />	
		<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
		<script type="text/javascript" src="http://code.jquery.com/ui/1.8.13/jquery-ui.min.js"></script>
		<script type="text/javascript">
		function confirmtruncate(ch){
		var r = confirm("Truncate " +ch +" Stats Tables??");
		if (r==true)
		  {
			truncate(ch);
		  }
		}
		
		function truncate(table)
		{
			var xmlhttp;
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
				  if (xmlhttp.responseText == "Success")
					  alert(table +" Stats Table successfully truncated");
				  else
					  alert(table +" Stats Table failed truncate");
			    }
			  }
			  
			  
			  var status = "MaintenanceServlet?table="+table;
			xmlhttp.open("GET",status,true);
			xmlhttp.send();
			
		}
			$(function(){

				// Icon Buttons
				$("#SQL").button({
					icons: {
						primary: 'ui-icon-wrench'
					}
				});
				$("#Trace").button({
					icons: {
						primary: 'ui-icon-wrench'
					}
				});
				$("#WS").button({
					icons: {
						primary: 'ui-icon-wrench'
					}
				});
				$("#Vital").button({
					icons: {
						primary: 'ui-icon-wrench'
					}
				});
			});
		</script>
				
</head>
<body>
<table align="center">
<tr>
<th>Truncate SQL Stats Table</th><td><div id="SQL" onclick="confirmtruncate(this.id)">Go</div> <br/><br/></td>
</tr>
<tr>
<th>Truncate Trace Stats Table</th><td><div id="Trace" onclick="confirmtruncate(this.id)">Go</div> <br/><br/></td>
</tr>
<tr>
<th>Truncate WebServices Stats Table</th><td><div id="WS" onclick="confirmtruncate(this.id)">Go</div> <br/><br/></td>
</tr>
<tr>
<th>Truncate Vital Stats Table</th><td><div id="Vital" onclick="confirmtruncate(this.id)">Go</div> <br/><br/></td>
</tr>
</table>
</body>
</html>