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
<jsp:include page="${resPath }/tbk/commonjsp/commonTop.jsp"></jsp:include>
<link rel="shortcut icon" href="${resPath }/tbk/images/logo.png" />
<link rel="stylesheet" type="text/css" href="${resPath }/tbk/css/docView.css" >
</head>
<body>
    <!--     开始：顶部联系方式 -->
    <div class="menu-box topMenu"  align="center">
        <jsp:include page="${resPath }/tbk/commonjsp/top.jsp"></jsp:include>
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
						<img class="aUlLi1img" src="${resPath }/tbk${docViewPicPlace }" alt="" />
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
					<div align="right" style="font-weight: 600;font-size: 12px;margin: 10px 0 4px 0;">此文为原创文章，期望您在转载的时候标上出处，尊重原创尊重作者！</div>
					<div align="right" style="font-weight: 600;font-size: 12px;margin-bottom: 20px;">更多精彩！敬请关注&nbsp;<a href="${authUrl }" style="color: red;font-size: 14px;">琦三叔官网</a>&nbsp;一个草根的网站...</div>
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
    <jsp:include page="${resPath }/tbk/commonjsp/commonBottom.jsp"></jsp:include>
    <script type="text/javascript">
    	window.onresize=function(){
	    	/* console.info($(window).width()) */
    	}
    </script>
</body>
</html>