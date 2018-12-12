<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>提示</title>
<meta name="keywords" content="内容丢失">
<meta name="description" content="服务器找不到您要的内容，请见谅">
<jsp:include page="../../../tbk/commonjsp/commonTop.jsp"></jsp:include>
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
    <div class="menu-box"  align="center" style="margin-top: 80px;height: 270px;" >
        <div style="padding-top: 110px;">
	        	抱歉！由于网站不给力，未找到您要的内容，要不回<a href="${path }" style="font-size: 20px;font-weight: 600;color: red;">首页</a>再看看？
        </div>
    </div>
    <!--     开始：页面底部 -->
    <div class="aFootBefore"></div>
    <div align="center"  class="menu-box aFootTotal">
    	<jsp:include page="../../../tbk/commonjsp/footer.jsp"></jsp:include>
    </div>
    <!--     结束：页面底部  -->
    <jsp:include page="../../../tbk/commonjsp/commonBottom.jsp"></jsp:include>
</body>
</html>