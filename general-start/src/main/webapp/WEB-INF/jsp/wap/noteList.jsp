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
  <jsp:include page="../../../wap/commonwap/common.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="${resPath }/wap/css/docTrends.css"  >
</head> 
<body>
	<!-- 	顶部div开始 -->
	<jsp:include page="../../../wap/commonwap/top.jsp"></jsp:include>
	<!-- 	顶部div结束 -->
	<jsp:include page="../../../wap/commonwap/loading.jsp"></jsp:include>
	<!-- 	内容区域开始 -->
	<input type="hidden" id="seleTypeName" value="${typeName}" />
	<div class="textContext adocDiv10" >
		<c:forEach items="${noteList }" var="note" varStatus="index" >
			<a href="${path}/wap/note/${note.docId }">
				<div class="menu-box adocDiv1" style="height: 40px;line-height: 30px;">
						<div class="adocDiv2">
							<div class="adocDiv6">${note.title }</div>
						</div>
				</div>
			</a>
		</c:forEach>
	</div>
	<!-- 	内容区域结束 -->
	
	<!-- 	底部通用部分开始 -->
	<jsp:include page="../../../wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="../../../wap/commonwap/commonBottom.jsp"></jsp:include>
</body>  
</html>