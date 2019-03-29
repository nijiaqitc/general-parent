<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>主页</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"  />
<link rel="stylesheet" href="${resPath }/zxgj/css/novelList.css">
<style type="text/css">
	.titleArea{
		float: left;
    	width: 200px;
    	height: 100px;
	    line-height: 100px;
	    border: 1px dotted;
	}
	
	.titleArea:hover{
		background-color: #3973ae;
    	color: white;
	}
</style>
</head>
<body>
	<!--     开始：顶部菜单栏-->
	<div style="height: 20px;width: 100%;background-color: #ec8316;"></div>
	<!--     结束：顶部菜单栏 -->
	<!-- 中间正文公用部分 -->
	<div align="center">
		<div class="cengen">
			<div class="hantp">
				<c:forEach items="${docList }" var="doc">
					<a href="xs/novelList?titleId=${doc.id }" >
						<div class="titleArea">${doc.title }</div>
					</a>	
				</c:forEach>
			</div>
		</div>
	</div>
	<!--     开始：顶部菜单栏-->
	<jsp:include page="../zxgj/bottom.jsp"></jsp:include>
	<!--     结束：顶部菜单栏 -->
	<script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
</body>
</html>