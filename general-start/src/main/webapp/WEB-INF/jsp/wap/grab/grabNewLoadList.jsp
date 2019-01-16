<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<?xml version="1.0" encoding="UTF-8"?>  
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>  
  <title>文章动态</title> 
  <meta name="keywords" content="文章,列表,动态文章">
  <meta name="description" content="显示最新发表的文章"> 
  <jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="${resPath }/wap/css/docTrends.css"  >
</head> 
<body>
	<!-- 	顶部div开始 -->
	<jsp:include page="${resPath }/wap/commonwap/top.jsp"></jsp:include>
	<!-- 	顶部div结束 -->
	<jsp:include page="${resPath }/wap/commonwap/loading.jsp"></jsp:include>
	<!-- 	内容区域开始 -->
	<div class="textContext adocDiv10" >
		<div class="menu-box" style="height: auto;line-height: 30px;padding-left: 10px;">
			<c:forEach items="${grabList}" var="grab" varStatus="index2" >
				<div class="adocDiv2" style="clear: both;overflow: auto;">
					<span style="float: left;">${index2.index }</span>
					<a href="<c:if test="${grab.docId==null }">javascript:void(0)</c:if>
						<c:if test="${grab.docId!=null }">${path}/wap/grab/${grab.docId }</c:if>">
						<div class="adocDiv6">${grab.title }</div>
					</a>
					<div style="float: right;margin-right: 10px;cursor: pointer;" onclick="starLabel('${grab.id}',this)">
						<span style="color: red;<c:if test="${grab.starTab!=true }">display:none;</c:if>">★</span>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<!-- 	内容区域结束 -->
	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
</body> 
</html>