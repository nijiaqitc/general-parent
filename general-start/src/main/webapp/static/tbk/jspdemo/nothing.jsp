<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>提示</title>
<meta name="keywords" content="内容丢失">
<meta name="description" content="服务器找不到您要的内容，请见谅">
<jsp:include page="common.jsp"></jsp:include>
</head>
<body>
	
    <!--     开始：页面底部 -->
    <div class="aFootBefore">sdsd</div>
    <div align="center"  class="menu-box aFootTotal">
    	<jsp:include page="../commonjsp/footer.jsp"></jsp:include>
    </div>
    <!--     结束：页面底部  -->
</body>
</html>