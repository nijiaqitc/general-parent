<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>小说列表</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"  />
<link rel="stylesheet" href="${resPath }/zxgj/css/novelList.css">
</head>
<body>
	<!--     开始：顶部菜单栏-->
	<div style="height: 20px;width: 100%;background-color: #ec8316;"></div>
	<!--     结束：顶部菜单栏 -->
	<!-- 中间正文公用部分 -->
	<div align="center">
		<div class="topgen">
			<div class="leftpart"><img ></div>
			<div class="centerpart">
				<div align="left" class="docTitle">${docInfo.title }</div>
				<div class="contextDesc" style="text-indent: 28px;" align="left">
					${docInfo.contextDesc }
				</div>
				<div align="left">
					<div class="topjj red-btn" ><a href="#" >开始阅读</a></div>
				</div>
			</div>
			<div class="rightpart" align="right">
				<div class="numfen">共 ${generalInfo.fontNum } 字</div>
				<div class="plxl">${discussCount } 人评价</div>
				<div class="thi"></div>
				<div>
					<span class="icon-thumbs-up uop" >赞</span>(${generalInfo.goodNum })
					<span class="icon-thumbs-down uop risp" >踩</span>(${generalInfo.badNum })
				</div>
			</div>
		</div>
		<div class="cengen">
			<c:forEach items="${xsList }" var="xs">
				<div class="hantp">
					<div align="left" class="tt">
						<h3>${xs.juanDetail.title }·共${xs.total }章</h3>
					</div>
					<div class="zwqy" align="left">
						<ul class="ttul" >
							<c:forEach items="${xs.list }" var="menu">
								<li><a href="${path}/xs/novelRead/${menu.id }">第${menu.orderIndex }章 ${menu.title }</a><span class="icon-lock lockEm"></span></li>
							</c:forEach>
							<li><a href="#">第一章 陨落的天才</a><span class="icon-lock lockEm"></span></li>
							<li><a href="#">第一章 陨落的天才</a><span class="icon-lock lockEm"></span></li>
							<li><a href="#">第一章 陨落的天才</a><span class="icon-lock lockEm"></span></li>
							<li><a href="#">第一章 陨落的天才</a><span class="icon-lock lockEm"></span></li>
							<li><a href="#">第一章 陨落的天才</a><span class="icon-lock lockEm"></span></li>
							<li><a href="#">第一章 陨落的天才</a><span class="icon-lock lockEm"></span></li>
							<li><a href="#">第一章 陨落的天才</a><span class="icon-lock lockEm"></span></li>
							<li><a href="#">第一章 陨落的天才</a><span class="icon-lock lockEm"></span></li>
							<li><a href="#">第一章 陨落的天才</a><span class="icon-lock lockEm"></span></li>
						</ul>
					</div>
				</div>
			</c:forEach>
			
			
			
			<div class="hantp">
				<div align="left" class="tt">
					<h3>正文·共100章</h3>
				</div>
				<div class="zwqy" align="left">
					<ul class="ttul" >
						<li><a href="#">第一章 陨落的天才</a></li>
						<li><a href="#">第一章 陨落的天才</a></li>
						<li><a href="#">第一章 陨落的天才</a></li>
						<li><a href="#">第一章 陨落的天才</a></li>
						<li><a href="#">第一章 陨落的天才</a></li>
						<li><a href="#">第一章 陨落的天才</a></li>
						<li><a href="#">第一章 陨落的天才</a></li>
						<li><a href="#">第一章 陨落的天才</a></li>
						<li><a href="#">第一章 陨落的天才</a></li>
						<li><a href="#">第一章 陨落的天才</a></li>
					</ul>
				</div>
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