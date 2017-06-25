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

function btn_onclick() 
{
window.location="MainWatch.jsp";
}
 
$(document).ready(function(){
 
    var counter = 2;
 
    $("#addButton").click(function () {
 
	if(counter>5){
            alert("Only 5 URL's allowed");
            return false;
	}   
 
	var newTextBoxDiv = $(document.createElement('div'))
	     .attr("id", 'TextBoxDiv' + counter);
 
	newTextBoxDiv.after().html('<label>URL #'+ counter + ' : </label>' +
	      '<input type="text" name="url' + counter + 
	      '" id="url' + counter + '" value="" >');
 
	newTextBoxDiv.appendTo("#TextBoxesGroup");
 
 
	counter++;
     });
 
    
  });
</script>
</head>
<body>
<form action="WatchServlet" method="POST">
<input type='button' value='Add another URL' id='addButton'>
<div id='TextBoxesGroup'>
	<div id="TextBoxDiv1">
		<label>URL #1 : </label><input type='text' id='url1' name='url1'>
	</div>
</div>

<input type='submit' value='Save' id='Save'>
<input type='button' value='Cancel' id='Cancel' onclick="btn_onclick()">
</form> 
</body>
</html>