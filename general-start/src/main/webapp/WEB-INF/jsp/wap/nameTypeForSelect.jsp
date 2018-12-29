<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>取名类型选择</title>
<jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
<style>
span {
		font-size: 20px;
		border: 1px solid #ccc;
		background: white;
		margin-left: 18px;
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
	
	<!-- 	正文部分开始 -->
	<div class="textContext">
		<div align="center">
			<div style="margin-top: 80px;">
				<span><a href="${path}/wap/toPeopleName?type=1&typeName=人">人名</a></span>
				<span><a href="${path}/wap/toOtherName?type=19&typeName=装备">装备名</a></span>
				<span><a href="${path}/wap/toOtherName?type=20&typeName=丹药">丹药名</a></span>
			</div>
			<div style="margin-top: 20px;clear:both;"> 
				<span><a href="${path}/wap/toOtherName?type=21&typeName=符箓">符箓名</a></span>
				<span><a href="${path}/wap/toOtherName?type=22&typeName=野兽">野兽名</a></span>
				<span><a href="${path}/wap/toOtherName?type=23&typeName=灵草">灵草名</a></span>
			</div>
			<div style="margin-top: 20px;clear:both;">
				<span><a href="${path}/wap/toOtherName?type=147&typeName=材料">材料名</a></span>
				<span><a href="${path}/wap/toOtherName?type=176&typeName=技能书">技能书名</a></span>
				<span><a href="${path}/wap/toOtherName?type=184&typeName=鱼类">鱼名</a></span>
			</div>
		</div>
	</div>
	<!-- 	正文部分结束 -->

	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
</body>
</html>