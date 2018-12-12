<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>提示</title>
<link rel="shortcut icon" href="${resPath }/tbk/images/logo.png" />
</head>
<body>
	<div align="center">后台系统只支持谷歌浏览器，请换用谷歌再进行登陆！</div>
</body>
</html>