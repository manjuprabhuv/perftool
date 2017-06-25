<%@page language="java" import="java.util.*,com.hawkeye.beans.WatchEntriesBean" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>jQuery add / remove textbox example</title>
 
<script type="text/javascript" src="http://code.jquery.com/jquery-1.3.2.min.js"></script>
 
<style type="text/css">
	div{
		padding:8px;
	}
</style>
 
</head>
 
<body>
 
 
<script type="text/javascript">
 
function ChkStatus(u,ele)
{
var xmlhttp;

document.forms['rsltfrm'].elements[ele].value="Checking...";
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
    document.forms['rsltfrm'].elements[ele].value=xmlhttp.responseText;
    }
  }
  
  
  var statuschk = "CheckStatus?url="+u;
xmlhttp.open("GET",statuschk,true);
xmlhttp.send();
}
</script>
</head>
<body>
<form name="rsltfrm">
<table>
<tr>
<th>URL</th>
<th>Status</th>
</tr>
<%

if(request.getAttribute("WatchList")!=null)
{
List<WatchEntriesBean> al =(List<WatchEntriesBean>)request.getAttribute("WatchList");
for(int i=0;i<al.size();i++){
%>
<tr>
<td><%=al.get(i).getURL()%></td>
<td><input type="button" name="Button<%=i%>" id="Button<%=i%>" onclick="ChkStatus('<%=al.get(i).getURL()%>','Button<%=i%>')" value="Check Status"></td>
<%
}
}
%>
</tr>
</table>

<div id='URLGroup'>
	
</div>
<a href="EditWatch.jsp">Edit External system URL's</a>

</form> 
</body>
</html>