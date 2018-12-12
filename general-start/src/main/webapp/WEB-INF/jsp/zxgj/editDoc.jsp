<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="njqEditor主页,njqEditor网站,njqEditor,Editor,editor,javascript 编辑器,html 编辑器,富文本编辑器,在线编辑器">
<meta name="description" content="njqEditor是一套简单的HTML编辑器，具有单引用多风格的效果，单引用多配置，样式分离等效果。njqEditor使用JavaScript 编写，纯前段不包含后端代码">
<title>njqEditor开发文档</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"  />
<link rel="stylesheet" href="${resPath }/zxgj/js/prettify.css" />
<link rel="stylesheet" href="${resPath }/zxgj/css/editDoc.css" />
</head>
<body>
    <!--     开始：顶部菜单栏-->
    <jsp:include page="top.jsp"></jsp:include>
    <div class="menuLine"></div>
    <!--     结束：顶部菜单栏 -->
    <div align="center" style="min-height: 600px;">
	    <div style="width: 80%;" align="left">
		    <div style="float: left;width: 250px;">
		        <ul class="leftUl">
		        	<c:forEach items="${titleList}" var="title" varStatus="status">
			            <li>
				            <a href="javascript:void(0)" onclick="hideOrShow(this)">
				                <i class="icon-caret-down"></i>${ status.index + 1}. ${title.name}
				            </a>
			                <ul>
			                	<c:set var="index" value="0" />
			                	<c:forEach items="${nameList}" var="name" varStatus="status1">
			                		<c:if test="${name.id==title.id }">
			                			<c:set var="index" value="${index+1 }" />
			                			<li><a href="javascript:void(0)"  onclick="readNode(this,${name.docId})">${status.index + 1}.${index} ${name.title }</a></li>
			                		</c:if>
			                	</c:forEach>
			                </ul>
			            </li>
		        	</c:forEach>
		        </ul>
		    </div>
		    <div id="docContext" class="rightContext" >
		    </div>
	    </div>
    </div>
    <!--     开始：底部菜单栏-->
    <jsp:include page="bottom.jsp"></jsp:include>
    <!--     结束：底部菜单栏 -->
    <script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/prettify.js" type="text/javascript"></script>
    <script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
    <script src="${resPath }/zxgj/js/editDoc.js" type="text/javascript"></script>
</body>
</html>