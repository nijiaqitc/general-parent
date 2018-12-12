<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="琦三叔网站导航，网站地图，功能记录">
<meta name="description" content="显示琦三叔网站的全站地图，由于刚处于起步阶段所以功能比较少，以后慢慢进行扩张">
<title>琦三叔网站导航</title>
<jsp:include page="../../../tbk/commonjsp/commonTop.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${resPath }/tbk/css/webMap.css"  >
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
    <div align="center" class="aoutDiv1">
    	<div align="center" class="aoutDiv2">
    		<div align="center" class="menu-box ahomeDiv" >
    			<div align="left" class="ahomeDiv1">
    				<div class="ahomeDiv2"><a href="${path }">首页</a></div>
    				<div class="ahomeDiv3">
    					展示banner、类型、最新发表文章、文章分类
    					<div class="ahomeDiv4">
	    					目前网站暂时处于开发状态中，平常要工作所以慢慢开发中，如果您想在此网站添加一些模块，请联系站长，更多功能敬请期待！
    					</div>
    				</div>
    			</div>
    		</div>
    		<div align="center" class="menu-box adocsearch" >
    			<div align="left" class="ahomeDiv1">
    				<div class="ahomeDiv2"><a href="fastSearchInit">文章搜索</a></div>
    				<div class="ahomeDiv3">
    					根据标题、标签、文章内容快速检索出文章（左右两边暂时啥都不放，有空在两边放搜索排行等）
    				</div>
    			</div>
    		</div>
    		<div align="center" class="menu-box adocsearch2" >
    			<div align="left" class="ahomeDiv1">
    				<div class="ahomeDiv2"><a href="technologyUpInit">技术成长</a></div>
    				<div class="ahomeDiv3">
    					暂时就放一张图片。等有空把此栏目换成小工具，显示各种小工具列表如json格式转换、xml格式转换等
    				</div>
    			</div>
    		</div>
    		<div align="center" class="menu-box adocsearch2" >
    			<div align="left" class="ahomeDiv1">
    				<div class="ahomeDiv2"><a href="picWallInit">图片墙</a></div>
    				<div class="ahomeDiv3">
    					显示文章对应的图片，列出所有的文章
    				</div>
    			</div>
    		</div>
    		<div align="center" class="menu-box adocsearch1" >
    			<div align="left" class="ahomeDiv1">
    				<div class="ahomeDiv2"><a href="webMapInit">网站导航</a></div>
    				<div class="ahomeDiv3">
    					风格不怎么好看，以后再改版。暂时没时间
    				</div>
    			</div>
    		</div>
    		<div align="center" class="menu-box adocsearch1" >
    			<div align="left" class="ahomeDiv1">
    				<div class="ahomeDiv2"><a href="aboutMeInit">关于站长</a></div>
    				<div class="ahomeDiv3">
    					站长简历
    				</div>
    			</div>
    		</div>
    	</div>
    </div>
    <!--     开始：页面底部 -->
    <div class="aFootBefore"></div>
    <div align="center"  class="menu-box aFootTotal">
    	<jsp:include page="../../../tbk/commonjsp/footer.jsp"></jsp:include>
    </div>
    <!--     结束：页面底部  -->
    <jsp:include page="../../../tbk/commonjsp/commonBottom.jsp"></jsp:include>
    <script src="${resPath }/tbk/js/webMap.js" type="text/javascript"></script>
</body>
</html>