<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="njqEditor主页,njqEditor网站,njqEditor,Editor,editor,javascript 编辑器,html 编辑器,富文本编辑器,在线编辑器">
<meta name="description" content="njqEditor是一套简单的HTML编辑器，具有单引用多风格的效果，单引用多配置，样式分离等效果。njqEditor使用JavaScript 编写，纯前段不包含后端代码">
<title>njqEditor官网</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/zxgj/css/index.css">
<!-- <script type="text/javascript"> 
	var mobileAgent = new Array("iphone", "ipod", "android", "blackberry", "windows phone", "windows mobile", "webos", "incognito", "webmate", "bada", "nokia", "lg", "ucweb", "skyfire");
	var browser = navigator.userAgent.toLowerCase(); 
	var isMobile = false;
	for (var i=0; i<mobileAgent.length; i++){ 
	  //获取mobileAgent[i]的字符串在browser中的位置，如果不包含那么会返回-1，如果包含，那么就显示wap网页
	  if (browser.indexOf(mobileAgent[i])!=-1){ 
	    isMobile = true;
	    break; 
	  }
	}
	if(${ismob!=true}){
	    if(isMobile) location.href = "${authUrl}/wap/toIndex";
	}
</script>-->
</head>
<body>
    <!--     开始：顶部菜单栏-->
    <jsp:include page="top.jsp"></jsp:include>
    <!--     结束：顶部菜单栏 -->
    <!--     开始：banner区域 -->
	<div class="banner">
	    <ul></ul>
	    <ol></ol>
	    <i class="left"></i><i class="right"></i>
	</div>    
	<div align="center">
		<div style="background-color: #fff;overflow: auto;height: 250px;" align="center">
		    <div style="width: 750px;overflow: auto;">
				<div style="width: 145px;float: left;margin: 10px 50px;" align="center">
			        <div>
			            <img  src="static/zxgj/img/1.png">
			        </div>
			        挑灯写代码
			    </div>
			    <div style="width: 145px;float: left;margin: 10px 50px;" align="center">
			        <div>
			            <img  src="static/zxgj/img/2.png">
			        </div>
			        认真对待每一笔每一画
			    </div>
			    <div style="width: 145px;float: left;margin: 10px 50px;" align="center">
			        <div>
			            <img  src="static/zxgj/img/3.png">
			        </div>
			        向着目标步步前进
			    </div>
		    </div> 
		</div>
	</div>
    <!--     结束：banner区域 -->
    <div style="display: block;margin-top: 50px;"></div>
    <!--     开始：底部菜单栏-->
	<jsp:include page="bottom.jsp"></jsp:include>
    <!--     结束：底部菜单栏 -->
	<script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/index.js" type="text/javascript"></script>
</body>
</html>