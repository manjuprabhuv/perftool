<%@page language="java" import="java.util.*,com.hawkeye.servlet.SQLStatsDisplayBean" %>

<html>
<body> 
<table style="border:1px solid #ffffff;">
<!-- <tr><td colspan=12 align="center" style="background-color:00000"><img src="logo.jpg" alt="search" /></td></tr>-->
<!-- <tr><td><a href="index.jsp">Back to Main Page</a></td></tr>-->
<tr style="background-color:00FFFF">
<th><b>SQL Statement</b></th>
<th><b>Execution Time</b></th><!-- change this to average count -->
<th><b>Average execution Time</b></th><!-- change this to falied count -->
<th><b>Failed Count</b></th>
<th><b>Worst Exec Time(TimeStamp)</b></th>
<th><b>Last TimeStamp</b></th>

</tr>

<%
int count=0;
String color = "#CCFFFF";
List<SQLStatsDisplayBean> list =(List<SQLStatsDisplayBean>)request.getAttribute("SQLstatsList");
for(int i=0;i<list.size();i++){
	if((count%2)==0){ 
		color = "#FFFFCC"; 
		}
		else{
		color = "#CCFFFF";
		}

		count++;
	%>
	<tr style="background-color:<%=color%>;"><td><%=list.get(i).getSqlString() %></td>
	<td><%=list.get(i).getCount() %></td>
	<td><%=list.get(i).getAvergeExecTime() %></td>
	<td><%=list.get(i).getFailedCount() %></td>
	<td><%=list.get(i).getWorstExecTime() %> &nbsp;(<%=list.get(i).getWorstExecTimeStamp() %>)</td>
	<td><%=list.get(i).getTimestamp() %></td></tr>
	
	
	<% }
%>

</table>
</body>
</html>