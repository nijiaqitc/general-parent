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
	<title>正文</title>
	<jsp:include page="${basePath}/head"></jsp:include>
	<jsp:include page="${basePath}/foot"></jsp:include>
	<script type="text/javascript">
		/* var isChrome = navigator.userAgent.toLowerCase().match(/chrome/) != null;
		if (isChrome) {
		        alert('Chrome');
		} */
	</script>
</head>
<body>
	<!-- start: 顶部菜单 -->
	<jsp:include page="${basePath}/top"></jsp:include>
	<!-- end: 顶部菜单 -->
	<div class="container-fluid-full">
		<div class="row-fluid">
			<!-- start: 左边菜单 -->
<!-- 			<jsp:include page="${basePath}/left"></jsp:include> 
			<!-- end: 左边菜单 -->
			<!-- start: 正文 -->
			<div id="content" class="span10" align="center" >
				没有权限，别瞎搞！
			</div>
			<!-- end: 正文 -->
		</div>
		<jsp:include page="${basePath}/boom"></jsp:include>
	</div>
</body>
</html>