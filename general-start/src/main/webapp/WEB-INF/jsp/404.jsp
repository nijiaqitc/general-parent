<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>页面不见啦</title>
<meta name="keywords" content="页面不见了">
<meta name="description" content="您要的页面不见啦，可能隐身了！！！">
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
</head>
<body>
    <!--     结束：顶部联系方式 -->
    <!--     开始：导航条 -->
    <jsp:include page="${path}/top"></jsp:include>
    <div style="height: 20px;width: 100%;background-color: #ec8316;"></div>
    <!--     结束：导航条 -->
	<div align="center">
		<img alt="报错图片" style="width: 800px;height: 640px;margin-top: 20px;" src="${resPath }/back/img/404.jpg">
	</div>
	<!--     开始：页面底部 -->
	<jsp:include page="${path}/bottom"></jsp:include>
    <!--     结束：页面底部  -->
    <script type="text/javascript" src="${resPath }/jquery/jquery.min.js" ></script>
    <script type="text/javascript" src="${resPath }/zxgj/js/common.js" ></script>
</body>
</html>