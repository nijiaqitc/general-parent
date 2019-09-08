<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<body>
	<div style="float:right;width:78%;height:100%;">
		<div class="panel panel-default" style="margin-bottom: 0px;height:100%;">
		  <div class="panel-body" style="height:100%;">
		  	<div style="height: 34px;">
			  	<div style="float:left;">
				    <button type="button" class="btn btn-danger" onclick="tscode()">调试</button>
			  	</div>
		  	</div>
		    <hr style="margin-top: 8px;margin-bottom: 8px;">
		    <div style="float:left;width:50%;border-right:1px solid #ddd;">
			    <textarea style="width:450px;height:720px;border: 0;outline:none;" id="area1"><html>
    <head>
        <title>html调试</title>
    </head>
    <body>
	    <p>html调试</p>
	    <hr />
	    <p>html调试</p>
	    <hr />
	    <p>html调试</p>
	    <hr />
    </body>
</html></textarea>
		    </div>
		    <div style="float:left;width:50%;">
		    	<iframe style="width:100%;height:92%;border: 0;outline:none;height:720px;min-height: 600px;" id="area2"></iframe>
		    </div>
		  </div>
		</div>
	</div>
	<script type="text/javascript">
		function tscode(){
			var html=document.createElement("html");
			html.innerHTML=$("#area1").val();
			$("#area2")[0].contentDocument.documentElement.innerHTML=html.innerHTML;
		}
		window.onload=function(){
			tscode();
			active();
		}
	</script>
</body>
</html>