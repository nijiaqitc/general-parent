<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="琦三叔文章，琦三叔文章图片墙，图片排行">
<meta name="description" content="显示琦三叔发表的文章图片，以阅读量的多少进行排行">
<title>文章图片墙</title>
<jsp:include page="../../../tbk/commonjsp/commonTop.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${resPath }/tbk/css/picWall.css"  >
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
    <div align="center" class="aoutDiv1" >
    	<div align="center" id="textCenter" class="menu-box aoutDiv">
    	</div>
    </div>
    <!--     开始：页面底部 -->
    <div class="aFootBefore"></div>
    <div align="center" class="menu-box aFootTotal">
    	<jsp:include page="../../../tbk/commonjsp/footer.jsp"></jsp:include>
    </div>
    <!--     结束：页面底部  -->
    <jsp:include page="../../../tbk/commonjsp/commonBottom.jsp"></jsp:include>
    <script src="${resPath }/tbk/js/picWall.js" type="text/javascript"></script>
</body>
</html>