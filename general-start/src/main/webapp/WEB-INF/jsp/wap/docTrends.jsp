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
	<input type="hidden" id="seleTypeName" value="${typeName}" />
	<div class="textContext adocDiv10" >
		<c:forEach items="${viewList }" var="view" varStatus="index" >
			<div class="menu-box adocDiv1">
				<div class="adocDiv2">
					<div class="adocDiv3">
						<img class="adocDiv4" src="${path }${view.url }" alt="文章图片" /> 
					</div>
					<div class="adocDiv5"><span>${view.userName }</span>
						<c:if test="${typeName!=null}">
							<span class="aspan1">类型:${typeName}</span>
						</c:if>
					</div>
					<div class="adocDiv6"><a href="${path}/wap/docView/${view.id }">${view.title }</a></div>
				</div>
				<div class="adocDiv7">
					<div class="adocDiv8"><a href="${path}/wap/docView/${view.id }">
					${fn:substring(fn:split(view.general,'&&')[0],0,100) }...
					</a></div>
					<div align="right" class="adocDiv9">${view.formatCreatedDate }&nbsp;&nbsp;浏览(${view.readnums })</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<!-- 	内容区域结束 -->
	
	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
  	<script type="text/javascript" src="${resPath }/wap/js/docTrends.js"></script>
</body>  
</html>