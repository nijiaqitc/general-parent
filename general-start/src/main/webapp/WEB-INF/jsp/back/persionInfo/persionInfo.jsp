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
	<jsp:include page="${path}/head"></jsp:include>
</head>
<body>
	<!-- start:公共页，存放公共框 -->
	<jsp:include page="${path}/publicJsp"></jsp:include>
	<!-- end:公共页，存放公共框 -->
	<!-- start: 顶部菜单 -->
	<jsp:include page="../public/top.jsp"></jsp:include>
	<!-- end: 顶部菜单 -->
	<div class="container-fluid-full">
		<div class="row-fluid">
			<!-- start: 左边菜单 -->
<!-- 			<jsp:include page="${path}/left"></jsp:include> 
			<!-- end: 左边菜单 -->
			<!-- start: 正文 -->
			<div id="content" class="span10" >
				<div class="row-fluid">
					<div class="box span6" style="width: 100%;" align="center">
						<div class="box-content custom_pagination" style="height: 1200px;" >
							<div style="width: 1050px;">
							
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end: 正文 -->
		</div>
		<jsp:include page="${path}/boom"></jsp:include>
		<jsp:include page="${path}/foot"></jsp:include>
	</div>
</body>
</html>