<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>抓文信息</title>
<jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${resPath }/wap/css/index.css"  >
<style>
.boxa{
	font-size: 20px;
	border: 1px solid #ccc;
	background: white;
	margin-left: 20px;
	width: 96px;
	display: block;
	float: left;
	height: 36px;
	line-height: 36px;
	margin-top: 20px;
}
</style>
</head>
<body>
	<!-- 	顶部div开始 -->
	<jsp:include page="${resPath }/wap/commonwap/top.jsp"></jsp:include>
	<!-- 	顶部div结束 -->
	<div class="toTop">
		<!-- 	一个大的搜索框 -->
		<div align="center" > 
			<div align="center"  class="searchDiv">
	    		<div class="searchDiv1">
	    			<form action="grabSearchList" method="get" id="isearch" >
		    			<input name="searchValue" type="text" class="searchText">
		    			<input type="submit" value="文章搜索" class="searchButton" >
	    			</form>
	    		</div>
	    	</div>
		</div>
	</div>
	<!-- 	正文部分开始 -->
	<div class="textContext">
		<div>
			<div align="center" >
				<a class="menu-box boxa"  href="${path}/wap/grab/grabList">抓文列表</a>
				<a class="menu-box boxa"  href="${path}/wap/grab/grabStarList">星标列表</a>
				<a class="menu-box boxa"  href="${path}/wap/grab/grabNewLoadList">最近抓文</a>
			</div>
		</div>	
	</div>
	<!-- 	正文部分结束 -->

	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
</body>
<script type="text/javascript">
	
</script>
</html>