<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>顶部菜单</title>
</head>
<body>
	<header>
	  <div id="logo"><a href="javaScript:void(0)"></a></div>
	  <nav class="topnav" id="topnav">
	  	<a href="${path}/index.jsp"><span>首页</span><span class="en">SHOU</span></a>
	  	<a href="${path}/docSearch/docSearch"><span>文章检索</span><span class="en">JIANSUO</span></a>
	  	<a href="${path}/fastNote/fastNote"><span>笔记速成</span><span class="en">BIJI</span></a>
	  	<a href="${path}/docView/docView"><span>文章预览</span><span class="en">YULAN</span></a>
	  	<a href="${path}/testModel/testModel"><span>测试模块</span><span class="en">CESHI</span></a>
	  	<a href="${path}/about/about"><span>关于我</span><span class="en">WO</span></a>
	  	<a href="${path}/other/other"><span>其他功能</span><span class="en">QITA</span></a></nav>
	  </nav>
	</header>
</body>
</html>