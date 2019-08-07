<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>网站介绍</title>
<meta name="keywords" content="小工具，各种应用，格式化">
<meta name="description" content="${docViewText.general }">
<jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
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
	text-align: center;
}
.cengen{
	overflow: auto;
}
</style>
</head>
<body>
	<!-- 	顶部div开始 -->
	<jsp:include page="${resPath }/wap/commonwap/top.jsp"></jsp:include>
	<!-- 	顶部div结束 -->
	
	<!-- 	正文部分开始 -->
	<div class="textContext">
		<div style="clear: both;margin-top: 50px;">
			<div style="margin-left: 24px;">复习工具</div>
			<div style="border-left: 1px dashed;border-top: 1px dashed;padding: 4px 24px;" align="left">
				简答题<input type="radio" name="titleType" checked="checked" value="1">
				选择题<input type="radio" name="titleType" value="2">
				编写题<input type="radio" name="titleType" value="3">
			</div>
			<div style="padding: 4px 4px;margin-left: 24px;" align="left">
				乱序：<input type="checkbox" id="needRange">
				标题：<input type="checkbox" id="showTitle">
			</div>
			<div class="cengen">
				<div class="hantp">
					<c:forEach items="${typeList }" var="type">
						<a class="menu-box boxa" style="width: 140px;" href="javascript:void(0)" onclick="loadInfo(${type.id})" >
							<div class="titleArea">${type.name }</div>
						</a>
					</c:forEach>
					<a class="menu-box boxa" href="javascript:void(0)" onclick="loadInfo()" >
						<div class="titleArea">全部</div>
					</a>
					<a class="menu-box boxa" href="javascript:void(0)" onclick="loadInfo(null,true)" >
						<div class="titleArea">待复习</div>
					</a>
				</div>
			</div>
			<div style="border-bottom: 1px dashed;border-right: 1px dashed;">
				<br>
			</div>
		</div>
		
		<div style="margin-top: 50px;overflow: auto;">
			<div style="margin-left: 24px;">自定义工具</div>
			<div style="border-top: 1px dashed;border-left: 1px dashed;">
				<br>
			</div>
			<div align="center" style="overflow: auto;">
				<a class="menu-box boxa"  href="${path}/wap/toSelectName">取名器</a>
				<a class="menu-box boxa"  href="${path}/feel/jump">心情录入</a>
				<a class="menu-box boxa"  href="${path}/wap/zjJump">章节设定</a>
				<a class="menu-box boxa"  href="${path}/novel/queryNovelTitle">小说</a>
			</div>
			<div style="border-bottom: 1px dashed;border-right: 1px dashed;">
				<br>
			</div>
		</div>
		
		<div style="margin-top: 50px;overflow: auto;">
			<div style="margin-left: 24px;">记录</div>
			<div style="border-top: 1px dashed;border-left: 1px dashed;">
				<br>
			</div>
			<div align="center" style="overflow: auto;">
				<c:forEach items="${folderList }" var="folder">
					<a class="menu-box boxa"  href="${path}/wap/loadStorePage?recordType=${folder.id }">${folder.name }</a>
				</c:forEach>
			</div>
			<div style="border-bottom: 1px dashed;border-right: 1px dashed;">
				<br>
			</div>
		</div>
		
		
	</div>
	<!-- 	正文部分结束 -->

	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<script type="text/javascript">
		function loadInfo(typeId,flag){
			var  url = "/wapStudy/subject?titleType="+$("input[name='titleType']:checked").val();
			if($("#needRange").prop("checked")){
				url+="&needRange=1"
			}
			if($("#showTitle").prop("checked")){
				url+="&showTitle=1"
			}
			if(typeId){
				url+="&typeId="+typeId
			}
			if(flag){
				url+="&needStudy=1"
			}
			window.location.href = url;
		}
	</script>
</body>
</html>