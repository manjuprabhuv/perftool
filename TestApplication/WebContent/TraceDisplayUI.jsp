<%@page language="java" import="java.util.*,com.hawkeye.beans.TraceStatsBean" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<title>Trace Display</title>
		<link type="text/css" href="./Aristo-jquery/css/Aristo/Aristo.css" rel="stylesheet" />
		<script type="text/javascript" src="./Aristo-jquery/picnet.table.filter.min.js"></script>		
		<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>-->
		<script src='http://ajax.microsoft.com/ajax/jquery/jquery-1.4.2.min.js' type='text/javascript'></script>
		<script type="text/javascript" src="http://code.jquery.com/ui/1.8.13/jquery-ui.min.js"></script>
		<script type='text/javascript'>
		
		$(document).ready(function() {
			
			$('#disptab').tableFilter();
			
		});
		</script>
		</head>
<body> 

<table id='disptab' style="border:1px solid #ffffff;">
<!-- <tr><td colspan=12 align="center" style="background-color:00000"><img src="logo.jpg" alt="search" /></td></tr>-->
<!-- <tr><td><a href="index.jsp">Back to Main Page</a></td></tr>-->
<thead>
<tr style="background-color:CCFFFF">
<th><b>Method Name</b></th>
<th filter-type='ddl'><b>Action</b></th>
<th><b>Input Params</b></th>
<th><b>Return Values</b></th>
<th><b>Execution Time</b></th><!-- change this to average count -->

</tr>
</thead>
<%
int count=0;
String color = "#CCFFFF";
List<TraceStatsBean> list =(List<TraceStatsBean>)request.getAttribute("TracestatsList");
for(int i=0;i<list.size();i++){
	if((count%2)==0){ 
		color = "#FFFFCC"; 
		}
		else{
		color = "#CCFFFF";
		}

		count++;
	%>
<tbody>
	<tr style="background-color:<%=color%>;"><td><%=list.get(i).getMethodName() %></td>
	<td><%=list.get(i).getAction() %></td>
	<td><%=list.get(i).getInputParams() %></td>
	<td><%=list.get(i).getReturnVals() %></td>
	<td><%=list.get(i).getExecTime() %></td></tr>
</tbody>	
	
	<% }
%>

</table>
</body>
</html>