<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="${basePath}/commonTopLink"></jsp:include>
<script type="text/javascript" charset="utf-8" src="${resPath }/chajian/uedit/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${resPath }/chajian/uedit/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="${resPath }/chajian/uedit/lang/zh-cn/zh-cn.js"></script>
<link href="${resPath }/chajian/uedit/themes/default/css/customUeditor.css" type="text/css" rel="stylesheet">
</head>
<body>
	<!-- 通用顶部 -->
	<jsp:include page="${basePath}/commonTop"></jsp:include>
	<!-- 中下部分 -->
	<div id="centerPlace">
		<!-- 通用左边菜单 -->	
		<jsp:include page="${basePath}/commonLeft"></jsp:include>
		<!-- 正文区域 -->
		<div id="rightContext" class="">
			<div id="mylayer" style="width: 100%;height: 45px;border:1px solid #d4d4d4; ">
			</div>
			<div align="center" >
				<div id="textBody" style="width: 800px;height: 800px;background-color: white;cursor: text;margin-top: 5px;border: 1px solid #c8c9cc;">
					
				</div>
			</div>
			<script id="editor" type="text/plain" style="width:995px;height:500px;"></script>
			<script type="text/javascript">
			var ue = UE.getEditor('editor');
			</script>
		</div>
		<!-- 通用底部 -->	
		<jsp:include page="${basePath}/commonBottom"></jsp:include>
	</div>
</body>
<jsp:include page="${basePath}/commonBottomLink"></jsp:include>
<script type="text/javascript">
		$("#editor").css("display","none");
</script>

</html>