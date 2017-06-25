<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <script language="javascript" type="text/javascript"
	src="./script/jquery.js"></script>
<script language="javascript" type="text/javascript"
	src="./script/jquery.flot.js"></script>
<script language="javascript" type="text/javascript"
	src="./script/jquery.flot.selection.js"></script>
</head>
<body>
	<div id="placeholder" style="width: 600px; height: 300px;"></div>
	<div id="overview"
		style="margin-left: 50px; margin-top: 20px; width: 400px; height: 50px"></div>
<script id="source">
<%String maxArray = (String) request.getAttribute("max");
String usedArray = (String) request.getAttribute("used");%>

$(function () {
    var d = <%=maxArray%>;
    var d2 = <%=usedArray%>;

    // first correct the timestamps - they are recorded as the daily
    // midnights in UTC+0100, but Flot always displays dates in UTC
    // so we have to add one hour to hit the midnights in the plot
    for (var i = 0; i < d.length; ++i)
      d[i][0] += 60 * 60 * 1000;
    for (var i = 0; i < d2.length; ++i)
        d2[i][0] += 60 * 60 * 1000;

    // helper for returning the weekends in a period
    function weekendAreas(axes) {
        var markings = [];
        var d = new Date(axes.xaxis.min);
        // go to the first Saturday
        d.setUTCDate(d.getUTCDate() - ((d.getUTCDay() + 1) % 7))
        d.setUTCSeconds(0);
        d.setUTCMinutes(0);
        d.setUTCHours(0);
        var i = d.getTime();
        do {
            // when we don't set yaxis, the rectangle automatically
            // extends to infinity upwards and downwards
            markings.push({ xaxis: { from: i, to: i + 2 * 24 * 60 * 60 * 1000 } });
            i += 7 * 24 * 60 * 60 * 1000;
        } while (i < axes.xaxis.max);

        return markings;
    }
    
    var options = {
        xaxis: { mode: "time", tickLength: 5 },
        selection: { mode: "x" },
        grid: { markings: weekendAreas }
    };
    
    var plot = $.plot($("#placeholder"), [{
		label : "Max Memory(MB)",
		data : d
	},{
		label : "Used Memory(MB)",
		data : d2
	}], options);
    
    var overview = $.plot($("#overview"), [d,d2], {
        series: {
            lines: { show: true, lineWidth: 1 },
            shadowSize: 0
        },
        xaxis: { ticks: [], mode: "time" },
        yaxis: { ticks: [], min: 0, autoscaleMargin: 0.1 },
        selection: { mode: "x" }
    });

    // now connect the two
    
    $("#placeholder").bind("plotselected", function (event, ranges) {
        // do the zooming
        plot = $.plot($("#placeholder"), [{
    		label : "Max Memory(MB)",
    		data : d
    	},{
    		label : "Used Memory(MB)",
    		data : d2
    	}],
                      $.extend(true, {}, options, {
                          xaxis: { min: ranges.xaxis.from, max: ranges.xaxis.to }
                      }));

        // don't fire event on the overview to prevent eternal loop
        overview.setSelection(ranges, true);
    });
    
    $("#overview").bind("plotselected", function (event, ranges) {
        plot.setSelection(ranges);
    });
});
</script>
</body>
</html>