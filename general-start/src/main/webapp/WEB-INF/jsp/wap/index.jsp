<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<?xml version="1.0" encoding="UTF-8"?>  
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="琦三叔手机端主页，琦三叔网站，琦三叔博客">
<meta name="description" content="显示琦三叔发表的各种文章，还有琦三叔业余时编写的各种小工具应用">
<title>琦三叔官网</title>
<jsp:include page="../../../wap/commonwap/common.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${resPath }/wap/css/index.css"  >
</head>
<body>
	<!-- 	顶部div开始 -->
	<jsp:include page="../../../wap/commonwap/top.jsp"></jsp:include>
	<!-- 	顶部div结束 -->
	
	<div class="toTop">
		<!-- 	一个大的搜索框 -->
		<div align="center" > 
			<div align="center"  class="searchDiv">
	    		<div class="searchDiv1">
	    			<form action="searchDoc" method="get" id="isearch" >
		    			<input name="searchValue" type="text" class="searchText">
		    			<input type="button" onclick="searchDoc()"  value="文章搜索" class="searchButton" >
	    			</form>
	    		</div>
	    	</div>
		</div>
	</div>
	
	<!-- 	类型区域显示开始 -->
	<div align="center" class="typeDiv">
		<div class="typeLi" >
		
		
			<c:forEach items="${yxlList }" var="yxl">
				<a href="${path }/wap/toQueryDocList?typeName=${yxl.name}">
					<div class="menu-box typePo"> 
						<span>${yxl.name }</span>
						<label>(${yxl.num })</label>
					</div>
				</a>
			</c:forEach>
		
			 <%-- <c:forEach items="${typeVoList }" var="type">
				<a href="${path }/wap/toTrends?typeName=${type.name}"><div class="menu-box typePo"> <span>${type.name }</span><label>(${type.num })</label></div></a>
			 </c:forEach> --%> 
		</div>
	</div>
	<!-- 	类型区域显示结束 -->
	
	<!-- 	底部通用部分开始 -->
	<jsp:include page="../../../wap/commonwap/bottom.jsp"></jsp:include>
	<jsp:include page="../../../wap/commonwap/commonBottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<!--     结束：页面底部  -->
    <script type="text/javascript" src="${resPath }/wap/js/index.js" ></script>
</body>
</html>