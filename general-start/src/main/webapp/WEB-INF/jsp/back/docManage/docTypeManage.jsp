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
<title>正文</title>
<link rel="shortcut icon" href="${resPath }/tbk/images/logo.png" />
<jsp:include page="${path}/commonTopLink"></jsp:include>
<!-- 自定义分页 -->
<link href="${resPath }/jsTool/customPage/customPage.css"
	rel="stylesheet" />
	<style type="text/css">
		.seleType{
			width: 200px;height: 60px;float: left;font-size: 24px;line-height: 60px;border: 1px solid;margin-left: 20px;
		}
		
		.seleType:hover{
			background-color: #2d323d;
		}
	</style>
</head>
<body>
	<!-- 通用顶部 -->
	<jsp:include page="${path}/commonTop"></jsp:include>
	<!-- 中下部分 -->
	<div id="centerPlace">
		<!-- 通用左边菜单 -->
		<jsp:include page="${path}/commonLeft"></jsp:include>
		<!-- 正文区域 -->
		<div id="rightContext">
			<div style="padding-top: 20px;overflow: auto;">
				<a href="yxlDocManage"><div align="center" class="seleType">系列文章</div></a>
				<a href="tbkDocManage"><div align="center" class="seleType">tbk文章</div></a>
			</div>
			
		</div>
		<!-- 通用底部 -->
		<jsp:include page="${path}/commonBottom"></jsp:include>
	</div>

	<jsp:include page="${path}/commonBottomLink"></jsp:include>
	<script src="${resPath }/jsTool/customPage/customPage.js"></script>
	<script src="${resPath }/common/js/publicJs.js"></script>
	<!-- start:公共页，存放公共框 -->
	<jsp:include page="${path}/publicJsp"></jsp:include>
	<!-- end:公共页，存放公共框 -->
</body>
</html>