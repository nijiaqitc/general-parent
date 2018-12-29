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
<meta name="keywords" content="琦三叔个人主页，琦三叔网站，琦三叔博客">
<meta name="description" content="显示琦三叔发表的各种文章，还有琦三叔业余时编写的各种小工具应用">
<title>琦三叔官网</title>
<jsp:include page="${resPath }/tbk/commonjsp/commonTop.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${resPath }/tbk/css/index.css" >
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
	if(isMobile) location.href = "${authUrl}/wap/toIndex";
}
</script>
</head>
<body>
    <!--     开始：顶部联系方式 -->
    <div class="menu-box topMenu"  align="center">
        <jsp:include page="${resPath }/tbk/commonjsp/top.jsp"></jsp:include>
    </div>
    <!--     结束：顶部联系方式 -->
    <!--     开始：导航条 -->
    <div class="anavi1" ></div>
    <div class="menu-box anavi2" align="center" >
    	<jsp:include page="${resPath }/tbk/commonjsp/navigate.jsp"></jsp:include>
    </div>
    <!--     结束：导航条 -->
    <!--     开始：最新文章区域 -->
    <div align="center" class="acenterBanner">
    	<div class="acenterBanner1">
	    	<div class="menu-box " style="width: 1190px;height: 300px;float:left;display: inline;">
		        <!-- 开始：banner区域 -->
		    	<jsp:include page="banner.jsp"></jsp:include> 
				<!-- 结束：banner区域 -->
	    	</div>
	    	<div class="menu-box" style="margin-left:20px; width: 180px;height: 300px;float:left;display: none;">
				<!-- 开始：分类显示 -->
	    		<%-- <jsp:include page="type.jsp"></jsp:include> --%>
	    		<!-- 结束：分类显示 --> 
	    	</div>
    	</div>
    	<div style="width: 1190px;clear: both;">
    		<div class="divLeft">
    			<c:forEach items="${recommendDocList }" var="doc" >
			    	<div class="menu-box longText" >
			    		<div align="left" class="longTextTop">
			    			<span class="longTextTitle">
			    				<a href="${path }/doc/docView/${doc.id}" target="_blank" >${doc.title }</a> 
			    			</span>
			    		</div>
			    		<div class="longTextLeft">
			    			<img alt="文章代表图片" src="${path }${doc.url }" >
			    		</div>
			    		<div align="left" class="longTextRight1" >
			    			<div class="longTextStyle">
				    			${fn:substring(fn:split(doc.general,'&&')[0],0,150) }...
			    			</div>
			    		</div>
			    		<div class="longTextRight2">
			    			<div class="longTextRight2Left" align="left"><span class="topFont">
			    			<a href="${path }/doc/docView/${doc.id}" target="_blank" style="text-shadow: 6px 6px 10px #C8C8C8;">文章详情</a></span></div>
			    			<div class="longTextRight2Right"><span class="topFont">${doc.formatCreatedDate } &nbsp;&nbsp;浏览(${doc.readnums })</span></div>
			    		</div>
			    	</div>
    			</c:forEach>
    		</div>
    		<div class="divLeft">
		    	<div class="menu-box" style="margin-left:20px; width: 310px;height: 580px;margin-top: 20px;">
		    		<img alt="右边图片" style="width: 310px;height: 580px;" src="${resPath }/tbk/images/pinbo.jpg">
		    	</div>
    		</div>
    	</div>
    </div>
    <!--     开始：小格子区 -->
    <div class="menu-box" style="height: 1px;"></div>
    <div align="center" style="clear: both;margin-top: 20px;" id="littleCenter">
    	<div class="littleDiv" >
   			<c:forEach items="${littleDivTextList }" var="text" varStatus="index" >
		    	<c:if test="${index.count==1 }">
			    	<div class="menu-box divStyle" >
		    	</c:if>
		    	<c:if test="${index.count!=1 }">
		    		<div class="menu-box divStyleLeft">
		    	</c:if>
		    		<div align="left" class="littleDivTop" >
						<div class="littleTopPic">
		    				<span><a href="${path }/doc/docForType/${text.id }" target="_blank" >${text.name }</a></span>
		    			</div>
					</div>
		    		<div class="littleDivCenter">
		    			<div align="left" class="littleDivCenterTitle">
		    				<span><a href="${path }/doc/docView/${text.textList[0].id}" target="_blank" >${text.textList[0].title }</a></span>
		    			</div>
		    			<div class="littleDivCenterDiv">
		    				<img alt="文章代表图片" src="${path }${text.textList[0].url }" >
		    			</div>
		    			<div class="littleDivCenterDivText" align="left" >
		    				<span class="topFont"><a href="${path }/doc/docView/${text.textList[0].id}" target="_blank" >${text.textList[0].formatGeneral50 }</a></span>
		    			</div>
		    		</div>
		    		<div align="left" class="littleDivBottom">
		    			<c:forEach items="${text.textList }" var="t" varStatus="c">
		    				<c:if test="${c.count!=1 }">
				    			<div class="littleDivBottomDiv">
									<div class="littleDivBottomText" ><span class="topFont" ><a href="${path }/doc/docView/${t.id}" target="_blank" >${t.title }</a></span> </div>
									<div align="right" class="littleDivBottomTime" ><span class="topFont"><a href="${path }/doc/docView/${t.id}" >${t.formatCreatedDate2 }</a></span></div>
								</div>
		    				</c:if>
		    			</c:forEach> 
		    		</div>
		    	</div>
    	 	</c:forEach>
    	</div>
    </div>
    <!--     结束：最新文章区域 -->
    <!--     结束：小格子区 -->
    <!--     开始：页面底部 -->
    <div class="aFootBefore"></div>
    <div align="center"  class="menu-box aFootTotal" >
    	<jsp:include page="${resPath }/tbk/commonjsp/footer.jsp"></jsp:include>
    </div>
    <!--     结束：页面底部  -->
    <jsp:include page="${resPath }/tbk/commonjsp/commonBottom.jsp"></jsp:include>
    <script type="text/javascript" src="${resPath }/tbk/js/index.js" ></script>
</body>
</html>