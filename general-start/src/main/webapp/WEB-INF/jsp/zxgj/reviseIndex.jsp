<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>知识复习</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"  />
<link rel="stylesheet" href="${resPath }/zxgj/css/novelList.css">
<style type="text/css">
	.titleArea{
		float: left;
    	width: 200px;
    	height: 100px;
	    line-height: 100px;
	    border: 1px dotted;
	}
	
	.titleArea:hover{
		background-color: #3973ae;
    	color: white;
	}
</style>
</head>
<body>
	<!--     开始：顶部菜单栏-->
	<div style="height: 20px;width: 100%;background-color: #ec8316;"></div>
	<!--     结束：顶部菜单栏 -->
	<!-- 中间正文公用部分 -->
	<div align="center">
		<div style="width: 1150px;" align="left">
			简答题<input type="radio" name="titleType" checked="checked" value="1">
			选择题<input type="radio" name="titleType" value="2">
			编写题<input type="radio" name="titleType" value="3">
		</div>
		<div style="width: 1150px;" align="left">
			乱序：<input type="checkbox" id="needRange">
			标题：<input type="checkbox" id="showTitle">
		</div>
		<div class="cengen">
			<div class="hantp">
				<c:forEach items="${typeList }" var="type">
					<a href="javascript:void(0)" onclick="loadInfo(${type.id})" >
						<div class="titleArea">${type.name }</div>
					</a>
				</c:forEach>
				<a href="javascript:void(0)" onclick="loadInfo()" >
					<div class="titleArea">全部</div>
				</a>
				<a class="menu-box boxa" href="javascript:void(0)" onclick="loadInfo(null,true)" >
					<div class="titleArea">待复习</div>
				</a>
			</div>
		</div>
	</div>
	<!--     开始：顶部菜单栏-->
	<jsp:include page="../zxgj/bottom.jsp"></jsp:include>
	<!--     结束：顶部菜单栏 -->
	<script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
	<script type="text/javascript">
		function loadInfo(typeId,flag){
			var  url = "study/subject?titleType="+$("input[name='titleType']:checked").val();
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