<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="njqEditor主页,njqEditor网站,njqEditor,Editor,editor,javascript 编辑器,html 编辑器,富文本编辑器,在线编辑器">
<meta name="description" content="njqEditor是一套简单的HTML编辑器，具有单引用多风格的效果，单引用多配置，样式分离等效果。njqEditor使用JavaScript 编写，纯前段不包含后端代码">
<title>njqEditor演示</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<c:if test="${modelStyle!=null}">
	<link href="${resPath }/jsTool/njqeditor/css/njqEditor_${modelStyle}.css"  rel="stylesheet">
</c:if>
<c:if test="${modelStyle==null}">
    <link href="${resPath }/jsTool/njqeditor/css/njqEditor_styleOne.css"  rel="stylesheet">
</c:if>
<link rel="stylesheet" href="${resPath }/zxgj/css/editor.css">	
</head>
<body>
	<jsp:include page="top.jsp"></jsp:include>
    <!--     结束：顶部菜单栏 -->
    <div class="menuline"></div>
    <!-- 中间正文公用部分 -->
    <div align="center" style="margin-top: 30px;min-height: 860px;">
	    <div class="blinebtoom" align="center">
	    	<div class="styleBtnArea" >
		    	<div class="styleBtn" <c:if test="${modelStyle=='styleOne'||modelStyle==null}"> style="background-color: #ec8316;"</c:if>><a href="editor?modelStyle=styleOne">风格一</a></div>
		    	<div class="styleBtn" <c:if test="${modelStyle=='styleTwo'}"> style="background-color: #ec8316;"</c:if>><a href="editor?modelStyle=styleTwo">风格二</a></div>
		    	<div class="styleBtn" <c:if test="${modelStyle=='styleThree'}"> style="background-color: #ec8316;"</c:if>><a href="editor?modelStyle=styleThree">风格三</a></div>
		    	<div class="styleBtn" <c:if test="${modelStyle=='styleFour'}"> style="background-color: #ec8316;"</c:if>><a href="editor?modelStyle=styleFour">风格四</a></div>
		    	<div class="styleBtn" <c:if test="${modelStyle=='styleFive'}"> style="background-color: #ec8316;"</c:if>><a href="editor?modelStyle=styleFive">风格五</a></div>
		    	<div class="styleBtn" <c:if test="${modelStyle=='styleSix'}"> style="background-color: #ec8316;"</c:if>><a href="editor?modelStyle=styleSix">风格六</a></div>
		    	<div class="styleBtn" <c:if test="${modelStyle=='styleSeven'}"> style="background-color: #ec8316;"</c:if>><a href="editor?modelStyle=styleSeven">风格七</a></div>
		    	<div class="styleBtn" <c:if test="${modelStyle=='styleEight'}"> style="background-color: #ec8316;"</c:if>><a href="editor?modelStyle=styleEight">风格八</a></div>
		    	<div class="styleBtn" <c:if test="${modelStyle=='styleNine'}"> style="background-color: #ec8316;"</c:if>><a href="editor?modelStyle=styleNine">风格九</a></div>
	    	</div>
	    </div>
	   	<div onclick="showApi(this)" class="leftLabel">常用API</div>
	    <div>
		    <div id="apiArea" class="apiAreastyle1">
		    	<div class="apiAreastyle2">
		            <div class="apiBtn" onclick="javascript:alert(njq.getContent())">获取编辑器内容</div>
		            <div class="apiBtn" onclick="njq.setContent('欢迎使用')">写入内容</div>
		            <div class="apiBtn" onclick="javascript:alert(njq.getContentTxt())" >获得纯文本</div>
		            <div class="apiBtn" onclick="javascript:alert(njq.getPlainTxt())" >获得带格式的纯文本</div>
		            <div class="apiBtn" onclick="javascript:alert(njq.hasContent())" >判断是否有内容</div>
		            <div class="apiBtn" onclick="njq.setFocus()" >使编辑器获得焦点</div>
		            <div class="apiBtn" onclick="javascript:alert(njq.getText())" >获得当前选中的文本</div>
		            <div class="apiBtn" onclick="javascript:alert(njq.getSaveContext())" >获取准备保存的内容</div>
		            <div class="apiBtn" onclick="njq.assembleContext()" >组合内容</div>
		            <div class="apiBtn" onclick="njq.insertHtml('欢迎使用')" >插入给定的内容</div>
		            <div class="apiBtn" onclick="njq.setEnabled()" >可以编辑</div>
		            <div class="apiBtn" onclick="njq.setDisabled()" >不可编辑</div>
		            <div class="apiBtn" onclick="njq.setHide()" >隐藏编辑器</div>
		            <div class="apiBtn" onclick="njq.setShow()" >显示编辑器</div>
		            <div class="apiBtn" onclick="njq.setHeight(500)" >设置编辑器的高度</div>
		            <div class="apiBtn" onclick="njq.setTitleNum(500)" >设置标题序号</div>
		            <div class="apiBtn" onclick="njq.setTitle('我的标题')" >设置标题</div>
		            <div class="apiBtn" onclick="javascript:alert(njq.getTitle())" >获取标题</div>
		            <div class="apiBtn" onclick="javascript:alert(njq.getTitleNum())" >获取标题序号</div>
		       	</div>
		    </div>
    	</div>
    	<div style="overflow: auto;">
	        <div style="float: left;width: 100%" align="center">
		        <div style="margin-top: 50px;" align="center">
		            <div prefix="" name="njqEditorDiv" modelStyle="${modelStyle}" env="2" ></div>
		        </div>
		    </div>  
    	</div>
		<div style="height: 50px;"></div>
    </div>
    <!--     开始：顶部菜单栏-->
    <jsp:include page="bottom.jsp"></jsp:include>
    <!--     结束：顶部菜单栏 -->
    <script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
    <script src="${resPath }/zxgj/js/editor.js" type="text/javascript"></script>
    <script type="text/javascript">
    var jspath="${path }";
    </script>
    <!--     加载用户自定义配置 -->
    <script type="text/javascript" src="${resPath }/jsTool/njqeditor/js/njqEditor_config.js"></script>
</body>
</html>