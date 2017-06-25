<%@page import="com.hawkeye.beans.WebserviceStatsBean"%>
<%@page language="java" import="java.util.*" %>

<html>
<body> 
<table style="border:1px solid #ffffff;">
<!-- <tr><td colspan=12 align="center" style="background-color:00000"><img src="logo.jpg" alt="search" /></td></tr>-->
<!-- <tr><td><a href="index.jsp">Back to Main Page</a></td></tr>-->
<tr style="background-color:00FFFF">
<th><b>REQUEST</b></th>
<th><b>RESPONSE</b></th><!-- change this to average count -->
<th><b>TIME STAMP</b></th><!-- change this to falied count -->


</tr>

<%
int count=0;
String color = "#CCFFFF";
List<WebserviceStatsBean> list =(List<WebserviceStatsBean>)request.getAttribute("list");
for(int i=0;i<list.size();i++){
	if((count%2)==0){ 
		color = "#FFFFCC"; 
		}
		else{
		color = "#CCFFFF";
		}

		count++;
	%>
	<tr style="background-color:<%=color%>;">
	<td><pre><%=list.get(i).getRequest() %></pre></td>
	<td><pre><%=list.get(i).getResponse() %></pre></td>
	<td><%=list.get(i).getTimestamp() %></td>
	
	
	
	<% }
%>

</table>
</body>
</html>