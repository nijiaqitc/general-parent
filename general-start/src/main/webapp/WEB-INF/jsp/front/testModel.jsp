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
	<title>Insert title here</title>
	<jsp:include page="${basePath}/frontHeadJs"></jsp:include> 
	<link href="${resPath }/front/css/about.css" rel="stylesheet"> 
</head>
<body>
	<jsp:include page="${basePath}/frontHead"></jsp:include>
	
	
	<article class="aboutcon">
		<h1 class="t_nav"><span>---------------------------------</span><a href="/" class="n1">网站首页</a><a href="/" class="n2">关于我</a></h1>
		<div class="about left" style="height:500px;">
		  <h2>Just about me</h2>
		    <ul> 
		     <p>cccccccccccccccccccccccccc</p>
		<p>
		vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv</p>
		<p>ffffffffffffffffffffffffffffffffff</p>
		<p>
		nnnnnnnnnnnnnnnnnnnnnnnnnnn
		</p>
		    </ul>
		    <h2>About my blog</h2>
		    <p>域  名111111111：  <a href="/" class="blog_link" target="_blank">注册域名</a></p>
		    <p>服务器：<a href="/" class="blog_link" target="_blank">购买空间</a></p>
		    <p>备案号：</p>
		    <p>程  序：</p>
		</div>
		<aside class="right">  
		    <div class="about_c">
		    <p>ccc:<span>aaa</span> | zxzx</p>
		    <p> </p>
		    <p></p>
		    <p></p>
		    <p></p>
		    <p></p>
		    <p></p>
		    <p></p>
		<a target="_blank" href="">
		<img src="" ></a>
		<a target="_blank" href="" ><img src="" ></a>
		</div>     
		</aside>
		</article>
	
	
	<jsp:include page="${basePath}/frontBoom"></jsp:include> 
 	<jsp:include page="${basePath}/frontBoomJs"></jsp:include> 
</body>
</html>