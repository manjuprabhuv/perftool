<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="jquery,ui,easy,easyui,web">
	<meta name="description" content="easyui help you build your web page easily!">
	<title>jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
	 <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script> 
	<script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript">

//collapseAll("test");
$('#test2').treegrid('collapseAll');
//$('#test').treegrid('collapse');
alert('s2');
</script>
</head>
<body>
	<h1>Method Execution</h1>
	
	<table id="test2" title="Method Execution" class="easyui-treegrid" style="width:1200px;height:500px"
			url="json.jsp"
			rownumbers="false"
			idField="requestId" treeField="methodName">
		<thead>
			<tr>
				<th field="methodName" width="800">Name</th>
				<th field="execTime" width="60" align="right">execTime</th>
				<th field="inputParams" width="100">inputParams</th>
				
				<th field="returnVals" width="100">returnVals</th>
							
			</tr>
		</thead>
	</table>
	
</body>
</html>