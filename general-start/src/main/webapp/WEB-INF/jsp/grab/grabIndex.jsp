<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>主页</title>
</head>
<body>
	<div><a target="_blank" href="/grab/loadMenuPage">配置下载菜单</a></div>
	<div><a target="_blank" href="/grab/config">下载文章</a></div>
	<div>
		修改或重新加载文章(type:1修改   0：尚未下载过新下载 url为下载地址 loadingId为titleloading的主键id) 
		<div>/grab/loadDocUrl?type=1&url=xxx</div>
		<div>/grab/loadDocNow?type=1&loadingId=xxx</div>
	</div>
	<div><a target="_blank" href="/grab/knowledge/11">文章预览</a></div>
	<div><a target="_blank" href="/grab/loadJob">执行下载页面job任务</a></div>
	<div><a target="_blank" href="/grab/loadMenu">执行下载菜单任务</a></div>
	<div><a target="_blank" href="/grab/reloadFile">重新下载失败的文件</a></div>
</body>
</html>