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
<title>导航条</title>
<link rel="stylesheet" type="text/css" href="${resPath }/tbk/css/navigate.css"  >
</head>
<body>
<div class="abothSide">
   	<div class="atotalDiv" align="center">
   		
    	<ul class="menuName">
               <li class="shouye" >
               	  <a href="${path }/"   ><span class="hideTitle">首页</span></a>
               </li>
               <li class="picwall">
               	  <a href="${path }/picWallInit"  ><span class="hideTitle">图片墙</span></a>
               </li>
               <li class="fastSearch">
                  <a href="${path }/fastSearchInit" ><span class="hideTitle">文章搜索</span></a>
               </li>
               <li class="techup">
                  <a href="${path }/rcShare/init" ><span class="hideTitle">技术成长</span></a>
               </li>
               <li class="webinit">
                  <a href="${path }/webMapInit" ><span class="hideTitle">网站导航</span></a>
               </li>
               <li class="aboutmeinit">
                  <a href="${path }/aboutMeInit" ><span class="hideTitle">关于站长</span></a>
               </li> 
           </ul>
    	<img alt="" src="${resPath }/tbk/images/daohang.jpg">
   	</div> 
</div>
</body>
</html>