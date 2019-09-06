<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>
    <!-- 中间正文公用部分 -->
    <div class="contextArea" align="center" style="height: 80%;min-height: 800px;">
    	<jsp:include page="leftMenu.jsp"></jsp:include>
		<jsp:include page="jsontest.jsp"></jsp:include>	
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