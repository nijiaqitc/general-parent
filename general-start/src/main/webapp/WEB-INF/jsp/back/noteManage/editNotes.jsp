<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑笔记</title>
<link rel="shortcut icon" href="${resPath }/tbk/images/logo.png" />
<jsp:include page="${path}/commonTopLink"></jsp:include>
<link href="${resPath }/jsTool/njqeditor/css/njqEditor_styleOne.css"  rel="stylesheet">
<link href="${resPath }/back/css/yxlIssueDoc.css"  rel="stylesheet">
<style type="text/css">

</style>
</head>
<body>
	<!-- 通用顶部 -->
	<jsp:include page="${path}/commonTop"></jsp:include>
	<!-- 中下部分 -->
	<div id="centerPlace">
		<!-- 通用左边菜单 -->
		<jsp:include page="${path}/commonLeft"></jsp:include>
		<!-- 正文区域 -->
		<div id="rightContext">
			<div style="padding-top: 20px;overflow: auto;">
				<div align="center">
					<div style="display: none;" prefix="" name="njqEditorDiv">${review.doc }</div>
				</div>
				<div style="display: none;">
				    <input id="chunkId" type="hidden" value="${chunkId }">
				</div>
			</div>
			<div onclick="showhideDialog()" class="rightBtn rightBtnsz">设置</div>
			<div onclick="saveDoc()" class="rightBtn rightBtnse" >保存</div>
			<div id="csdialog" style="position: fixed;width: 550px;min-height: 350px;background-color: #fff;top: 130px;border: 1px solid #888;right: -560px;" align="center">
				<div style="width: 300px;margin-top: 30px;">
				    <input type="hidden" id="docId" value="${review.id }">;
					<div align="left" style="padding: 16px 0;">描述：<textarea id="general"  style="width: 290px;height: 100px;">${review.general }</textarea> </div>
					<div align="left" style="padding: 16px 0;">序号：<input id="index"  style="width: 290px;" value="${maxIndex }" /></div>
					<div onclick="saveValue()" align="center" style="width: 145px;overflow: auto;margin-top: 20px;margin-bottom: 20px;" >
						<div style="cursor:pointer;float: left;width: 50px;height: 26px;background-color: #4aa0f5;line-height: 26px;margin-left: 10px;">确定</div>
						<div onclick="showhideDialog()" style="cursor:pointer;float: left;width: 50px;height: 26px;background-color: #4aa0f5;line-height: 26px;margin-left: 30px;">隐藏</div>
					</div>
				</div>
			</div>
		</div>
	    <!--     加载用户自定义配置 -->
	    <script type="text/javascript" src="${resPath }/jsTool/njqeditor/js/njqEditor_config.js"></script>
		<!-- 通用底部 -->
		<jsp:include page="${path}/commonBottom"></jsp:include>
	</div>
	<jsp:include page="${path}/commonBottomLink"></jsp:include>
	<script src="${resPath }/common/js/publicJs.js"></script>
    <script type="text/javascript" src="${resPath }/back/js/yxlIssueNotes.js"></script>
	<!-- start:公共页，存放公共框 -->
	<jsp:include page="${path}/publicJsp"></jsp:include>
	<!-- end:公共页，存放公共框 -->
</body>
</html>