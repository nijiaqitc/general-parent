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
<style type="text/css">
    p{
        color:red;
    }
</style>
<script type="text/javascript">
alert("fdfdf")
console.info("11")

</script>
</head>
<body>

<p>
JavaScript 能够直接写入 HTML 输出流中：
</p>

<script>
document.write("<h1>This is a heading</h1>");
document.write("<p>This is a paragraph.</p>");
console.info("1")
</script>

<p>
您只能在 HTML 输出流中使用 <strong>document.write</strong>。
如果您在文档已加载后使用它（比如在函数中），会覆盖整个文档。
</p>

</body>
</html></textarea>
		    </div>
		    <div style="float:left;width:50%;">
		    	<iframe style="width:100%;height:92%;border: 0;outline:none;" id="area2"></iframe>
		    </div>
		  </div>
		</div>
	</div>
	<script type="text/javascript">
		function tscode(){
			var html=document.createElement("html");
			html.innerHTML=$("#area1").val();
// 			eval(html.getElementsByTagName("script")[0].innerHTML)
			$("#area2")[0].contentDocument.documentElement.innerHTML=html.innerHTML;
		}
		window.onload=function(){
			tscode();
			active();
		}
	</script>
</body>
</html>