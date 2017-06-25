
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>



	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1"/>

	<title>jQuery treeView</title>

	

	<link rel="stylesheet" href="./jquery/jqtree.css" />
<!-- 	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.min.js"></script> -->
	<script src="./jquery/extra/js/jquery-1.8.0.min.js"></script>
	<script src="./jquery/tree.jquery.js" type="text/javascript"></script>
	<script src="./jquery/extra/js/jquery.cookie.js" type="text/javascript"></script>
<script>
    $(function() {
        var data = [
            {
                label: 'node1',
                children: [
                    { label: 'child1' },
                    { label: 'child2' }
                ]
            },
            {
                label: 'node2',
                children: [
                    { label: 'child3' }
                ]
            }
        ];

        $('#tree1').tree({
            data: data
        });
    });
</script>

	</head>

	<body>

	

<h2>Demo</h2>

	<div id="tree1"></div>
	

	
</body></html>