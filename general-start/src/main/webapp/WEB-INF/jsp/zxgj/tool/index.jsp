<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>格式化工具</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<style type="text/css">
.topul{
	list-style: none;
}

.topul li{
	list-style: none;
	float: left;
}
.topul li a {
	font-family: "微软雅黑";
    font-size: 14px;
    text-decoration: none;
    outline: none;
    padding: 4px 14px;
    display: block;
    width: 100%;
}
.topul li a:hover{
	background-color: #40719a;
	color:white;
}
.commonRight{
	float: left;
    width: 76%;
    height: 800px;
    margin-left: 70px;
}
</style>
</head>
<body style="background: url('${resPath }/zxgj/img/bg.png');">
	<div style="width:100%;height:40px;margin-bottom: 10px;">
		<div class="panel panel-default" style="height:100%;margin-bottom: 0px;border: 0px;">
		  <div class="panel-body" style="height: 100%;padding: 10px;">
		  	<div style="height:24px;float: left;">
		  		<img alt="" src="${resPath }/zxgj/img/logomax.png" style="height: 24px;margin-right: 10px;"> 
		  	</div>
		  	<ul class="topul">
		  		<li><a href="${path}/tools/inx/jsondecode">格式工具</a></li>
		  		<li><a href="${path}/neEditor">NE插件</a></li>
		  	</ul>
		  </div>
		</div>
	</div>
    <!-- 中间正文公用部分 -->
    <div class="contextArea" align="center" style="min-height: 800px;overflow: hidden;">
    	<jsp:include page="leftMenu.jsp"></jsp:include>
		<jsp:include page="${toolName}.jsp"></jsp:include>	
    </div>
    <!--     开始：顶部菜单栏-->
    <jsp:include page="../bottom.jsp"></jsp:include>
    <!--     结束：顶部菜单栏 -->
    <script src="${resPath }/jquery/jquery-3.4.1.min.js" type="text/javascript"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script type="text/javascript">
    	if($(document).height()<=$(window).height()){
			if(!$(".bottomInfoDiv").hasClass("bottomInfoAuto")){
				$(".bottomInfoDiv").addClass("bottomInfoAuto");				
			}
		}else{
			if($(".bottomInfoDiv").hasClass("bottomInfoAuto")){
				$(".bottomInfoDiv").removeClass("bottomInfoAuto");				
			}
		}
		$(".bottomInfoDiv").show();
    </script>
</body>
</html>