<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<?xml version="1.0" encoding="UTF-8"?>  
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>  
  <title>文章搜索</title> 
  <meta name="keywords" content="文章搜索,搜索结果,检索">
  <meta name="description" content="在检索框搜索文章后显示匹配的结果"> 
  <jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="${resPath }/wap/css/searchDocList.css"  >
</head> 
<body>
	<!-- 	顶部div开始 -->
	<jsp:include page="${resPath }/wap/commonwap/top.jsp"></jsp:include>
	<!-- 	顶部div结束 -->
	<jsp:include page="${resPath }/wap/commonwap/loading.jsp"></jsp:include>
	<div class="menu-box" style="position: fixed;height: 45px;" >
		<div style="margin-left: 10px;">当前搜索：<span style="color: red;" id="searchValue" >${searchValue}</span></div>
		<div>
			<div style="background: url('../wap/images/tip.gif');width: 348px;height: 24px;background-position: -54px -8px;margin-left: 6px">
				<input type="text" id="newSearchValue"   onblur="losfoc()" maxlength="20" onfocus="clearText()" value="请输入搜索关键字以 空格 进行分隔！" style="border: 0px;width: 300px;margin-left: 20px;color: rgb(205, 205, 205);height: 20px;" />
			</div>
		<label onclick="research()" id="searchBtn" style="background: url('../wap/images/tip.gif');width: 20px;height: 24px;background-position: -402px -5px;position: relative;display: block;margin-top: -28px;margin-left: 333px;" ></label>
<!-- 		<input type="button" onclick="research()" value="再搜"/> -->
			
		</div>
	</div>
	<!-- 	内容区域开始 -->
	<div class="textContext adocDiv10" >
		<c:forEach items="${viewList }" var="view" varStatus="index" >
			<c:if test="${index.count==1 }">
				<div class="menu-box adocDiv11">
			</c:if>
			<c:if test="${index.count!=1 }">
				<div class="menu-box adocDiv1">
			</c:if>
				<div class="adocDiv2">
					<div class="adocDiv3">
						<img class="adocDiv4" src="${path }${view.url }" alt="文章图片" /> 
					</div>
					<div class="adocDiv5">
						<span>${view.userName }</span>
					</div>
					<div class="adocDiv6"><a href="${path}/wap/docView/${view.id }">${view.searchTitle }</a></div>
				</div>
				<div class="adocDiv7">
					<div class="adocDiv8">
					<a href="${path}/wap/docView/${view.id }">${fn:substring(view.searchGeneral,0,100) }...</a></div>
					<div align="right" class="adocDiv9">浏览(${view.readnums })</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<div id="noDoc" class="menu-box" style="margin-top: 60px;height: 170px;display: none;" align="center">
		<img src="../wap/images/1.gif" style="margin-left: -200px;"/>
		<span style="margin-top: -80px;font-size: 1.2rem;display: block;margin-left: 130px;">抱歉，未搜索到内容！</span>
	</div>
	<!-- 	内容区域结束 -->
	
	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
  	<script type="text/javascript" src="${resPath }/wap/js/searchDocList.js"></script>
</body>  
</html>