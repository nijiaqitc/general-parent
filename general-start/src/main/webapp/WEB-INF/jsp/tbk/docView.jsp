<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文章阅读</title>
<jsp:include page="../../../tbk/commonjsp/commonTop.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${resPath }/tbk/css/docView.css" >

<style type="text/css">
	.adocDivaaa{
		z-index: 9980;width: 100%;margin: 10px;position:absolute;
	}
	.adocDiv1aaa{
		background-color: #fff;border-radius: 6px;width: 997px;box-shadow: 0 3px 9px rgba(0,0,0,0.5);
	}
	.transparent_classaaa {  
	    /* Required for IE 5, 6, 7 */  
	    /* ...or something to trigger hasLayout, like zoom: 1; */  
	    width: 100%;
	    height: 100%; 
	    position: fixed;
	    line-height:300px;  
	    text-align:center;  
	    background:#000;  
	    color:#fff;  
	    /* older safari/Chrome browsers */  
	    -webkit-opacity: 0.2;  
	    /* Netscape and Older than Firefox 0.9 */  
	    -moz-opacity: 0.2;  
	    /* Safari 1.x (pre WebKit!) 老式khtml内核的Safari浏览器*/  
	    -khtml-opacity: 0.2;  
	    /* IE9 + etc...modern browsers */  
	    opacity: .2;  
	    /* IE 4-9 */  
	    filter:alpha(opacity=20);  
	    /*This works in IE 8 & 9 too*/  
	    -ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=20)";  
	    /*IE4-IE9*/  
	    filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=20); 
	    top: 0;right: 0;bottom: 0;left: 0; 
	}
</style>
</head>
<body>
    <!--     开始：顶部联系方式 -->
    <div class="menu-box topMenu"  align="center">
        <jsp:include page="../../../tbk/commonjsp/top.jsp"></jsp:include>
    </div>
    <!--     结束：顶部联系方式 -->
    <!--     开始：导航条 -->
    <div class="anavi1" ></div>
    <div class="menu-box anavi2" align="center" >
    	<jsp:include page="../../../tbk/commonjsp/navigate.jsp"></jsp:include>
    </div>
    <!--     结束：导航条 -->
    <!--     开始：最新文章区域 -->
    <div align="center" style="clear: both;">
    	<div class="aTotal">
    		<div align="left" class="menu-box aYulanTotal">
    			<div class="aYulanText">
    				<p>推荐文章</p>
    			</div>
    			<ul class="aUl">
				</ul>
    		</div>
    		<div align="center" class="menu-box aTotalDiv" >
    			<div align="right">
	    			<div onclick="openWord()" class="aopenWord">
				     	<a title="全屏" class="aopenWord1"></a>
				    </div>
			    </div>
				<div align="left" class="aTotalDiv1">
					<div align="left" class="aTitle">
					<c:if test="${docType==2 }"><img style="width: 50px;" alt="转载" src="../tbk/images/z.png"></c:if>
					<c:if test="${docType==1 }"><img style="width: 50px;" alt="转载" src="../tbk/images/y.png"></c:if>
					${docViewTitle}</div>
					<div align="left" class="aGeneral">
						&nbsp;&nbsp;
						<c:if test="${docType==2 }">文章转载自：</c:if>
						${docViewGeneral }
					</div>
					<div align="center"  class="aTypeTip">
						<div>琦三叔|${docViewTime}|(0)</div>
						<div>类型:无|标签:${docViewTips }</div>
					</div>
					${docViewText}
				</div>
    		</div>
    	</div>
    </div>
    <!--     结束：最新文章区域 -->
    
    <!--     开始：全屏阅览 -->
    
    <div id="wordForShow" align="center" class="adocDiv"  ondblclick="closeWord()" >
     	<div class="adocDiv1" align="center" >
     		<div align="right">
		     	<div onclick="closeWord()" class="aopenWord2">
			     	<a title="关闭全屏" class="aopenWord3"></a>
			    </div>
		    </div>
     		<div class="aopenWord4" >
	     		<div class="aopenWord5">${docViewTitle}</div>
	     		<div class="aopenWord6" align="left" >
	     			&nbsp;&nbsp;文章转载自：${docViewGeneral }
	     		</div>
	     		<div class="aopenWord7">
	     			<div>琦三叔|${docViewTime}|(0)
					</div>
	     			<div>
		     			类型:无|
						标签:${docViewTips }  
	     			</div>
	     		</div>
	     		<div class="aopenWord8" align="left">${docViewText}</div>
	     		<div align="right" class="aopenWord9">此文为转载文章，若此转载对您造成了侵害，请及时联系站长避免对您造成更大的损失！</div>
				<div align="right" class="aopenWord10">更多精彩！敬请关注&nbsp;<a href="${authUrl }" class="aopenWord11">琦三叔官网</a>&nbsp;一个草根的网站...</div>
	     	</div>
     	</div>
    </div>
  	<div id="wordModel" class="transparent_class" ></div>
    
    
    <!--     结束：全屏阅览  -->
    
    
    <!--     开始：页面底部 -->
    <div class="aFootBefore"></div>
    <div align="center" class="menu-box aFootTotal" >
    	<jsp:include page="../../../tbk/commonjsp/footer.jsp"></jsp:include>
    </div>
    <!--     结束：页面底部  -->
    <jsp:include page="../../../tbk/commonjsp/commonBottom.jsp"></jsp:include>
    <script src="${resPath }/tbk/js/doc.js" type="text/javascript"></script>
</body>
</html>