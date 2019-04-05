<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>js调试</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/zxgj/css/tools.css">
<style type="text/css">
.btnStyle {
	cursor: pointer;
	width: 50px;
	height: 30px;
	color: white;
	background-color: #E12422;
	font-size: 12px;
	border: 0;
}
.btnStyle:hover{
    background-color: red;
}
.leftCommUl {
	font-size: 12px;
	border-right: 1px solid #ddd;
}

.leftCommUl li {
	cursor: pointer;
}

.leftCommUl li:hover {
	background-color: #eee;
}

table td {
	border: 1px solid;
	padding-left: 4px;
}

.zztable {
	border-spacing: 0px;
	margin-top: 5px;
	margin-bottom: 5px;
	word-wrap: break-word;
	word-break: break-all;
	font-size: 12px;
	line-height: 22px;
	color: rgb(0, 0, 0);
	font-family: arial, 宋体, sans-serif;
	font-style: normal;
}

.leftTd {
	padding-top: 2px;
	padding-bottom: 2px;
	font-size: 12px;
	line-height: 22px;
	height: 22px;
	border-color: rgb(230, 230, 230);
}

.leftTd span {
	font-size: 12px;
	word-wrap: break-word;
	color: rgb(51, 51, 51);
	margin-top: 0px;
	margin-bottom: 0px;
	line-height: 24px;
	zoom: 1;
	height: auto;
}

.trd {
	background-color: #F6F4F0;
}

.trf {
	background-color: #fff;
}
</style>
</head>
<body>
	<!--     开始：顶部菜单栏-->
	<jsp:include page="../top1.jsp"></jsp:include>
	<div style="height: 20px;width: 100%;background-color: #ec8316;"></div>
	<!--     结束：顶部菜单栏 -->
	<!-- 中间正文公用部分 -->
	<div class="contextArea" align="center">
		<div style="font-size: 12px;background-color: #c0d9ef;overflow: auto;">
			<div style="margin-left: 10px;float: left;">当前位置：加密工具>>js调试</div>
		</div>
		<div class="btnStyle"
                    style="margin-left: 10px;float: left;margin-top: 20px;margin-bottom: 10px;width: 100px;"
                    onclick="tscode()">代码调试</div>
                    
		<div style="width: 100%;clear:both;overflow: auto;min-height: 600px;">
			<div style="float: left;height: 100%;width: 49%;">
			 <div style="height: 100%;width: 100%;">
<textarea id="text1" style="min-width: 600px;width:98%;height: 100%;resize:vertical;outline: none;min-height: 600px;" >
<html>
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
</html>
</textarea>
			 </div>
			</div>
			<div style="float: right;height: 100%;width: 49%;">
			 <div style="height: 100%;width: 100%;">
<!-- 			 src="ts" -->
			     <iframe  style="min-width: 600px;width:98%;height: 100%;resize:vertical;outline: none;min-height: 600px;border: 1px solid #aaa;"
                        id="text2" name="text2" ></iframe>
			 </div>
			</div>
		</div>
		<div class="bottomD"></div>
	</div>

	<!--     开始：顶部菜单栏-->
	<jsp:include page="../bottom.jsp"></jsp:include>
	<!--     结束：顶部菜单栏 -->
	<script src="${resPath }/jquery/jquery.min.js"
		type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
<!-- 	<script src="${resPath }/zxgj/js/tools/htmlts.js" type="text/javascript"></script> -->
	<script type="text/javascript">
		function tscode(){
			var html=document.createElement("html");
			html.innerHTML=$("#text1").val();
// 			eval(html.getElementsByTagName("script")[0].innerHTML)
			$("#text2")[0].contentDocument.documentElement.innerHTML=html.innerHTML;
		}
		tscode();
		
		
		$(document).ready(function(){
			$(".nagivationBottomSelect").removeClass("nagivationBottomSelect");
			$($(".nagivationBottom")[1]).addClass("nagivationBottomSelect");
		})
	</script>
</body>
</html>