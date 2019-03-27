<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>首页</title>
<link href="${resPath }/tbk/index.css" type="text/css" rel="stylesheet">
<script src="${resPath }/tbk/banner/js/jquery.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="${resPath }/tbk/css/index.css" type="text/css">
<link rel="stylesheet" href="${resPath }/tbk/docView.css" type="text/css">
<script type="text/javascript" src="${resPath }/tbk/js/jquery.min.js"></script>
<script type="text/javascript" src="${resPath }/tbk/js/zzsc.js"></script>
</head>
<body>
    <!--     开始：顶部联系方式 -->
    <div class="menu-box topMenu"  align="center">
        <div align="left">
            <ul class="topUl">
                <li><span class="topFont">QQ:12345678</span></li>
                <li style="margin-left: 20px;"><span class="topFont">QQ:12345678</span></li>
            </ul>
        </div>
    </div>
    <!--     结束：顶部联系方式 -->
    <!--     开始：导航条 -->
    <div style="height: 30px;" ></div>
    <!--     结束：导航条 -->
    <!--     开始：最新文章区域 -->
    <div align="center" style="clear: both;">
    	<div class="aTotal">
    		<div align="left" class="menu-box aYulanTotal">
    			<div class="aYulanText">
    				<p>小文档预览</p>
    			</div>
    			<ul class="aUl">
					<li class="aUlLi1">
						<p class="aUlLi1p1">
							类型：${docViewType } |  
						 2016-03-29  |  浏览(10)
						</p> 
						<img class="aUlLi1img" src="${resPath }/tbk/${docViewPicPlace }" alt="" />
						<p class="aUlLi1p2" >${docViewTitle }</p>
						<p class="aUlLi1p3">${docViewGeneral }</p>
					</li>
					<li class="aUlLi2">
						<p class="aUlLi1p1">August 11, 2002  |  (10 )  Comments</p>
						<img class="aUlLi1img" src="${resPath }/tbk/images/pic05.jpg" alt="" />
						<p class="aUlLi1p2" >title</p>
						<p class="aUlLi1p3">Nullam non wisi a sem eleifend. Donec mattis libero eget urna. Pellentesque viverra enim.</p>
					</li>
				</ul>
    		</div>
    		<div align="center" class="menu-box aTotalDiv" >
				<div align="left" class="aTotalDiv1">
					<div align="left" class="aTitle">${docViewTitle}</div>
					<div align="left" class="aGeneral">
						&nbsp;&nbsp;${docViewGeneral }
					</div>
					<div align="center"  class="aTypeTip">
						类型:${docViewType }|
						标签:<c:forEach items="${docViewTips }" var="tip">
								${tip }
						</c:forEach> |
						时间:${docViewTime }|
						阅览：(0)
					</div>
					${docViewText}
				</div>
    		</div>
    	</div>
    </div>
    <!--     开始：页面底部 -->
    <div class="aFootBefore"></div>
    <div align="center" class="menu-box aFootTotal" >
    	<div class="aFootDiv1"></div>
    	<div class="aFootDiv2"><span>浙ICP备16006900号</span></div>
    	<div><span>若有疑问请及时联系站长，QQ:2439794916 微信：qisanshu</span></div>
    </div>
    <!--     结束：页面底部  -->
    <script src="${resPath }/tbk/index.js" type="text/javascript"></script>
    <script type="text/javascript">
    	window.onresize=function(){
	    	/* console.info($(window).width()) */
    	}
    </script>
</body>
</html>