<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<jsp:include page="${basePath}/frontHeadJs"></jsp:include> 
	<link href="${resPath }/front/css/about.css" rel="stylesheet"> 
</head>
<body>
	<jsp:include page="${basePath}/frontHead"></jsp:include>
	
	
	<article >
		<h1 class="t_nav"><span>大脑短路，暂未开发！</span></h1>
		<div align="center" style="height: 500px;">
			<font style="font-size: 50px;">爱看不看，不看滚蛋！</font>
		</div>
	</article>
	
	
	<jsp:include page="${basePath}/frontBoom"></jsp:include> 
 	<jsp:include page="${basePath}/frontBoomJs"></jsp:include> 
</body>
</html>