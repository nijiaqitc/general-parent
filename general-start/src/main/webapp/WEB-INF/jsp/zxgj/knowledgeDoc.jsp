<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${doc.title }</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/zxgj/css/knowledgeDoc.css">
</head>
<body>
    <!--     开始：顶部菜单栏-->
    <jsp:include page="top.jsp"></jsp:include>
    <div style="height: 20px;width: 100%;background-color: #ec8316;"></div>
    <!--     结束：顶部菜单栏 position:fixed;-->
 	<div class="centerDiv">
 		<c:if test="${showLeft==1 }">
		    <div id="leftMenu" align="center" class="leftMenuTop">
	 		    <div onclick="hideLeftMenu(this)" class="leftMenuRightTitle">
	 		             目录</div> 
		        <div class="leftMenuTopTitle">目录</div>
		        <div align="left" class="menuList">
		        	<c:forEach items="${titleList }" var="title" varStatus="index">
			        	<div class="tt1">
				        	<div class="tt2" align="center">${index.index+1}</div>
				        	<div class="tt3">
				        		<c:if test="${title.docId==doc.id }"><a href="${path }/yxl/knowledge/${title.docId}" class="active"></c:if>
		            			<c:if test="${title.docId!=doc.id }"><a href="${path }/yxl/knowledge/${title.docId}"></c:if>
		            			${title.title }</a>
				        	</div>
			        	</div>
		        	</c:forEach>
		        </div>
		    </div>
 		</c:if>
	    <div id="docContext" class="docContext" align="center">
		    <div class="contextArea" >
		        <c:if test="${doc==null }">
		         <div align="center" class="noWord">无此文章，换一篇吧！</div>                 
                </c:if>
		        <c:if test="${doc!=null }">
			        <div align="center" class="textSt">${doc.title }</div>
			        <div id="textContext"></div>
			        <div id="clearText" style="display: none;">${doc.text }</div>
			        <div id="clearCss" style="display: none;">${doc.css }</div>
		        </c:if>
		    </div>
	    </div>
	</div> 
    <!--     开始：顶部菜单栏-->
    <jsp:include page="bottom.jsp"></jsp:include> 
    <!--     结束：顶部菜单栏 -->
	<script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/knowledge.js" type="text/javascript"></script>
</body>
</html>