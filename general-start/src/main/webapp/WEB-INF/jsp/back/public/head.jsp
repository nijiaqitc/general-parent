<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link href="${resPath }/back/css/bootstrap.min.css" rel="stylesheet" />
<link href="${resPath }/back/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link href="${resPath }/back/css/style.min.css" rel="stylesheet" />
<link href="${resPath }/back/css/style-responsive.min.css" rel="stylesheet" />
<link href="${resPath }/back/css/retina.css" rel="stylesheet" />
<link rel="shortcut icon" href="${resPath }/tbk/images/logo.png" />

<script src="${resPath }/back/js/jquery-1.10.2.min.js"></script>

<script src="${resPath }/back/js/publicJs.js"></script>
<!-- 自定义分页 -->
<script src="${resPath }/jsTool/customPage/customPage.js"></script>
<link href="${resPath }/jsTool/customPage/customPage.css" rel="stylesheet" />
