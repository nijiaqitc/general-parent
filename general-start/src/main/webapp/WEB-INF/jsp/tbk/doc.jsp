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
<meta name="keywords" content="${tipString }">
<meta name="description" content="${docViewText.general }">
<title>文章阅读</title>
<jsp:include page="../../../tbk/commonjsp/commonTop.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${resPath }/tbk/css/docView.css" >	
<script type="text/javascript">
var mobileAgent = new Array("iphone", "ipod", "android", "blackberry", "windows phone", "windows mobile", "webos", "incognito", "webmate", "bada", "nokia", "lg", "ucweb", "skyfire");
var browser = navigator.userAgent.toLowerCase(); 
var isMobile = false;
for (var i=0; i<mobileAgent.length; i++){ 
  //获取mobileAgent[i]的字符串在browser中的位置，如果不包含那么会返回-1，如果包含，那么就显示wap网页
  if (browser.indexOf(mobileAgent[i])!=-1){ 
    isMobile = true;
    break; 
  }
}
if(${ismob!=true}){
	if(isMobile) location.href = "${authUrl}/wap/docView/${docViewText.id}";
}
</script>
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
    				<c:forEach items="${recommendDocList }" var="view" varStatus="index">
						<c:if test="${index.count==1 }">
							<li class="aUlLi1">
						</c:if>
						<c:if test="${index.count!=1 }">
							<li class="aUlLi2">
						</c:if>
							<p class="aUlLi1p1">
								类型：${view.typeName } |  
							 ${view.formatCreatedDate }  |  浏览(${view.readnums })
							</p> 
							<img class="aUlLi1img" src="${path}${view.url }" alt="文章代表图片" />
							<p class="aUlLi1p2" ><a href="${view.id }" target="_blank">${fn:substring(view.title, 0, 16)}</a></p>
							<p class="aUlLi1p3">
							${fn:substring(view.formatGeneralHref1, 0, 100)}...
							</p>
						</li>
    				</c:forEach>
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
					<c:if test="${docViewText.reprint==1 }"><img class="aopenWord13" alt="原创" src="../../tbk/images/y.png"></c:if>
					<c:if test="${docViewText.reprint==2 }"><img class="aopenWord13" alt="转载" src="../../tbk/images/z.png"></c:if>
					${docViewText.title}</div>
					<div align="left" class="aGeneral">
						&nbsp;&nbsp;
						<c:if test="${docViewText.reprint==2 }">
							尊重原创文章转自：${docViewText.formatGeneralHref }
						</c:if>
						<c:if test="${docViewText.reprint==1 }">
							${docViewText.general }
						</c:if>
					</div>
					<div align="center"  class="aTypeTip">
						<div>
							${author }|
							${docViewText.formatCreatedDate }|
							(${docViewText.readnums })
						</div>
						<div>
							类型:<a href="${path }/doc/docForType/${tbkType.id }" target="_blank">${tbkType.name }</a>|
							标签: 
							<c:forEach items="${docViewTbkTipList }" var="tbkTip">
									<a href="${path }/fastSearchInit?searchValue=${tbkTip.name }" target="_blank">${tbkTip.name }</a>
							</c:forEach>
						</div>
						  
					</div>
					${docViewText.text}
					<c:if test="${docViewText.reprint==2 }"><div align="right" class="aopenWord9">此文为转载文章，若此转载对您造成了侵害，请及时联系站长避免对您造成更大的损失！</div></c:if>
					<c:if test="${docViewText.reprint==1 }"><div align="right" class="aopenWord9">此文为原创文章，期望您在转载的时候标上出处，尊重原创尊重作者！</div></c:if>
					<div align="right" class="aopenWord10">更多精彩！敬请关注&nbsp;<a href="${authUrl }" class="aopenWord11">琦三叔官网</a>&nbsp;一个草根的网站...</div>
				</div>
    		</div>
    	</div>
    </div>
    
    <!--     开始：全屏阅览 -->
    
    <div id="wordForShow" align="center" class="adocDiv"  ondblclick="closeWord()" >
     	<div class="adocDiv1" align="center" >
     		<div align="right">
		     	<div onclick="closeWord()" class="aopenWord2">
			     	<a title="关闭全屏" class="aopenWord3"></a>
			    </div>
		    </div>
     		<div class="aopenWord4" >
	     		<div class="aopenWord5">${docViewText.title}</div>
	     		<div class="aopenWord6" align="left" >
	     			<c:if test="${docViewText.reprint==2 }">
						尊重原创文章转自：${docViewText.formatGeneralHref }
					</c:if>
					<c:if test="${docViewText.reprint==1 }">
						${docViewText.general }
					</c:if>
	     		</div>
	     		<div class="aopenWord7">
	     			<div>${author }|
						${docViewText.formatCreatedDate }|
						(${docViewText.readnums })
					</div>
	     			<div>
		     			类型:<a href="${path }/doc/docForType/${tbkType.id }" target="_blank">${tbkType.name }</a>|
						标签: 
						<c:forEach items="${docViewTbkTipList }" var="tbkTip">
								<a href="${path }/fastSearchInit?searchValue=${tbkTip.name }" target="_blank">${tbkTip.name }</a>
						</c:forEach>  
	     			</div>
	     		</div>
	     		<div class="aopenWord8" align="left">${docViewText.text}</div>
	     		<c:if test="${docViewText.reprint==2 }"><div align="right" class="aopenWord9">此文为转载文章，若此转载对您造成了侵害，请及时联系站长避免对您造成更大的损失！</div></c:if>
				<c:if test="${docViewText.reprint==1 }"><div align="right" class="aopenWord9">此文为原创文章，期望您在转载的时候标上出处，尊重原创尊重作者！</div></c:if>
				<div align="right" class="aopenWord10">更多精彩！敬请关注&nbsp;<a href="${authUrl }" class="aopenWord11">琦三叔官网</a>&nbsp;一个草根的网站...</div>
	     	</div>
     	</div>
    </div>
  	<div id="wordModel" class="transparent_class"  ></div>
    
    
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