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
	
	<script type="text/javascript" charset="utf-8" src="${resPath }/chajian/uedit/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${resPath }/chajian/uedit/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${resPath }/chajian/uedit/lang/zh-cn/zh-cn.js"></script>

  
</head>
<body>
	<jsp:include page="${basePath}/frontHead"></jsp:include>
	
	
	<article class="aboutcon" >
		<h1 class="t_nav"><span>快速复制、快速黏贴！随心所欲，随写随记。</span></h1>
		<div style="width: 1100px;">
		    <script id="editor" type="text/plain" style="width:995px;height:500px;"></script>
		</div>
	</article>
	
 	<script type="text/javascript">
    	var ue = UE.getEditor('editor');
	</script>
	<jsp:include page="${basePath}/frontBoom"></jsp:include> 
 	<jsp:include page="${basePath}/frontBoomJs"></jsp:include> 
</body>
</html>