<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="shortcut icon" href="${resPath }/tbk/images/logo.png" />
<link rel="stylesheet" type="text/css" href="${resPath }/tbk/css/common.css"  >
<script type="text/javascript" src="${resPath }/tbk/js/jquery.min.js"></script>
<script type="text/javascript" src="${resPath }/tbk/js/common.js"></script>