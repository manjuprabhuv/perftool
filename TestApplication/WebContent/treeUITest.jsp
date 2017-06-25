<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="jquery,ui,easy,easyui,web">
<meta name="description"
	content="easyui help you build your web page easily!">

<link rel="stylesheet" type="text/css"
	href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="http://www.jeasyui.com/easyui/themes/icon.css">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
<script type="text/javascript"
	src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">

	//$(document).ready(function() {
//collapseAll('#test2');
//$('#test2').treegrid({onLoadSuccess:{$(this).treegrid('collapseAll');}
//alert('s2');
//});
</script>
</head>
<body>

	<table id="test2" title="Method Execution Trace" class="easyui-treegrid"
		url="json.jsp" rownumbers="false" idField="requestId"
		treeField="methodName">
		<thead>
			<tr>
				<th field="methodName">Method Name</th>
				<th field="execTime">Execution Time taken</th>
				<th field="inputParams">Input Parameters</th>
				<th field="returnVals">Return Values</th>

			</tr>
		</thead>
	</table>

</body>
</html>