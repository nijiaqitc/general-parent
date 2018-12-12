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
<title>正文</title>
<link rel="shortcut icon" href="${resPath }/tbk/images/logo.png" />
<jsp:include page="${basePath}/commonTopLink"></jsp:include>
<!-- 自定义分页 -->
<link href="${resPath }/jsTool/customPage/customPage.css"
	rel="stylesheet" />
<link href="${resPath }/jsTool/njqeditor/css/njqEditor_styleOne.css"  rel="stylesheet">
<link href="${resPath }/back/css/yxlIssueDoc.css"  rel="stylesheet">
<style type="text/css">

</style>
</head>
<body>
	<!-- 通用顶部 -->
	<jsp:include page="${basePath}/commonTop"></jsp:include>
	<!-- 中下部分 -->
	<div id="centerPlace">
		<!-- 通用左边菜单 -->
		<jsp:include page="${basePath}/commonLeft"></jsp:include>
		<!-- 正文区域 -->
		<div id="rightContext">
			<div style="padding-top: 20px;overflow: auto;">
				<div align="center">
					<div id="njqEditorDiv">${docdetail.doc }</div>
				</div>
				<div style="display: none;">
				    <input id="upIsUpdate" type="hidden" value="${isUpdate }">
				    <input id="upTipName" type="hidden" value="${tipName }">
				    <input id="upTypeId" type="hidden" value="${type.id }">
				    <input id="upTitle" type="hidden" value="${doc.title }">
				    <input id="upIsShow" type="hidden" value="${search.isShow }">
				    <input id="upType" type="hidden" value="${search.typeId }">
				    <div id="upDocText">${doc.text }</div>
				    <div id="upDocCss">${doc.css }</div>
				</div>
			</div>
			<div onclick="showhideDialog()" class="rightBtn rightBtnsz">设置</div>
			<div onclick="saveDoc()" class="rightBtn rightBtnse" >保存</div>
			<div id="csdialog" style="position: fixed;width: 550px;min-height: 350px;background-color: #fff;top: 130px;border: 1px solid #888;right: -560px;" align="center">
				<div style="width: 300px;margin-top: 30px;">
				    <input type="hidden" id="docId" value="${doc.id }">;
					<div align="left" style="padding: 16px 0;">标题：<textarea id="title"  style="width: 290px;height: 100px;"></textarea> </div>
					<div align="left" style="padding: 6px 0;">
					系列： <span>是<input onclick="isxl(1)" type="radio" name="isxl" value="1" ></span>&nbsp;&nbsp;&nbsp;&nbsp;<span>否<input onclick="isxl(0)" type="radio" name="isxl" value="0" checked="checked"></span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					展示： <span>是<input type="radio" name="isshow" value="1" ></span>&nbsp;&nbsp;&nbsp;&nbsp;<span>否<input type="radio" name="isshow" value="0" checked="checked"></span>
					</div>
					<div align="left">
					  标签：<div id="tip" name="tip" onclick="inputfocuse()" style="border: 1px solid #C1C1BB;width: 294px;margin-top: 6px;min-height: 30px;overflow: auto;">
                             <input id="tInput" class="tipinput" onblur="tipcheck(this)">
                         </div>
                    </div>
					<div align="left" id="xlhd" style="padding: 10px 0; display: none;"> 
						<div>类型：<select id="selectType" style="width: 256px;" onchange="seleChange(this)">
								<option value="">------新类型------</option>
								<c:forEach items="${list }" var="t" >
									<option value="${t.id }">${t.name}</option>
								</c:forEach>
							</select>
						</div>
						<div id="newType" style="padding: 6px 0;">类型名称：<input type="text" id="name" style="width: 222px;"></div>
					</div>
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
		<jsp:include page="${basePath}/commonBottom"></jsp:include>
	</div>
	<jsp:include page="${basePath}/commonBottomLink"></jsp:include>
	<script src="${resPath }/jsTool/customPage/customPage.js"></script>
	<script src="${resPath }/common/js/publicJs.js"></script>
    <script type="text/javascript" src="${resPath }/back/js/yxlIssueDoc.js"></script>
	<!-- start:公共页，存放公共框 -->
	<jsp:include page="${basePath}/publicJsp"></jsp:include>
	<!-- end:公共页，存放公共框 -->
</body>
</html>