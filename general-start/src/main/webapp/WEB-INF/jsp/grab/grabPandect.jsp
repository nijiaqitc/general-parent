<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>总览</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png"/>
</head>
<body>
	<div><a target="_blank" href="/grab/loadMenuPage">配置下载菜单</a></div>
	<div><a target="_blank" href="/grab/config">下载文章</a></div>
	<div>
	    修改或重新加载文章(type:1修改 0：尚未下载过新下载 url为下载地址 loadingId为titleloading的主键id)
	    <div>/grab/loadDocUrl?type=1&url=xxx</div>
	    <div>/grab/loadDocNow?type=1&loadingId=xxx</div>
	</div>
	<div><a target="_blank" href="/grab/knowledge/11">文章预览</a></div>
	<div><a target="_blank" href="/grab/loadJob">执行下载页面job任务</a></div>
	<div><a target="_blank" href="/grab/loadMenu">执行下载菜单任务</a></div>
	<div><a target="_blank" href="/grab/reloadFile">重新下载失败的文件</a></div>
	
	<div><a target="_blank" href="/grab/grabPage">csdn单页面下载</a></div>
	<div>
		<div>重新加载系列文章</div>
		<div>/grab/reloadJob?channel=xxx&docId=xxx</div>
	</div>
	
	<div><a target="_blank" href="/noteManager">笔记管理</a></div>
	<div><a target="_blank" href="/recordManager">记录管理</a></div>
	<div><a target="_blank" href="/pwdPage">权限登录</a></div>
	<div><a target="_blank" href="/mdValue?value=1">获取md值</a></div>
	<div><a target="_blank" href="/rcShare/init">资源分享页</a></div>
	
	<div>
		<a href="/xs">小说</a>
		<a href="/tbk">tbk</a>
		<a href="/grab/index">抓文</a>
	</div>
	<div><a target="_blank" href="/yxlIssueDoc">yxl文章发表</a></div>
	<div><a target="_blank" href="/updateYxlDocPage?docId=34">yxl文章修改</a></div>
	<div>修改抓文 <a target="_blank" href="/grab/editKnowledge?docId=1181">抓文修改</a></div>
	<div><a target="_blank" href="/tbk/fastNote">tbk发表文章</a></div>
</body>
</html>