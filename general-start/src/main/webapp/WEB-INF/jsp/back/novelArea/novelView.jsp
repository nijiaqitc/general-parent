<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小说预览</title>
<jsp:include page="${path}/commonTopLink"></jsp:include>
</head>
<body>
	<!-- 通用顶部 -->
	<jsp:include page="${path}/commonTop"></jsp:include>
	<!-- 中下部分 -->
	<div id="centerPlace">
		<!-- 通用左边菜单 -->	
		<jsp:include page="${path}/commonLeft"></jsp:include>
		<!-- 正文区域 -->
		<div id="rightContext" align="center">
			<div class="menu-box" style="padding-top: 10px;width: 80%;min-width: 860px;padding-top: 60px;" align="center" >
				<div><h1>第${titleDetail.titleIndex}章&nbsp;&nbsp;&nbsp;&nbsp;${docdetail.title }</h1></div>
				<div align="center">
					<div align="left" style="width: 800px;font-size: 18px;margin-top: 24px;">
						${docdetail.doc }
					</div>
				</div>
			</div>
		</div>
		<!-- 通用底部 -->	
		<jsp:include page="${path}/commonBottom"></jsp:include>
	</div>
</body>
<jsp:include page="${path}/commonBottomLink"></jsp:include>
</html>