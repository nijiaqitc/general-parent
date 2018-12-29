<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="搜索文章，快速搜索，文章检索">
<meta name="description" content="快速查找文章，对本站的所有文章进行搜索">
<title>文章搜索</title>
<jsp:include page="${resPath }/tbk/commonjsp/commonTop.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${resPath }/tbk/css/fastSearch.css"  >
<link rel="stylesheet" type="text/css" href="${resPath }/back/css/customPage.css"  />
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
    <div align="center" class="aoutDiv1">
    	<div align="center" class="asearchDiv">
	    	<div align="center"  class="asearchDiv1">
	    		<div class="asearchDiv2" >
	    			<span><input type="text" class="asearchText" id="searchValue" maxlength="60" value="${searchValue }" ></span>
	    			<span><input type="button" onclick="search()" value="搜索一下" class="asearchbutton" id="searchButton" ></span>
	    		</div>
	    	</div>
    	</div>
    	<div align="center" class="aDiv">
	    	<div align="left" class="asearchValueDiv">
	    		<div class="menu-box asearchValueDiv1"  >
	    			<div id="textCenter">
	    			</div>
	    			<!--start:分页条  -->
					 <div align="center" style="margin-top: 30px;">
					   <div id="pageDiv"></div>
					   <ul id="pageNum">
					   </ul>
					</div>
                   <!-- end:分页条 -->
	    		</div>
	    	</div>
    	</div>
    </div>
    <!--     开始：页面底部 -->
    <div class="aFootBefore"></div>
    <div align="center"  class="menu-box aFootTotal">
    	<jsp:include page="${resPath }/tbk/commonjsp/footer.jsp"></jsp:include>
    </div>
    <!--     结束：页面底部  -->
    <jsp:include page="${resPath }/tbk/commonjsp/commonBottom.jsp"></jsp:include>
    <script type="text/javascript" src="${resPath }/back/js/customPage.js"></script>
	<script type="text/javascript" src="${resPath }/tbk/js/fastSearch.js" ></script>
</body>
</html>