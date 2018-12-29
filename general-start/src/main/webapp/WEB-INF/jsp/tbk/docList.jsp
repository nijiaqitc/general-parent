<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="${selectType.name}">
<meta name="description" content="${selectType.name}类型下所有文章的显示">
<title>文章列表</title>
<jsp:include page="${resPath }/tbk/commonjsp/commonTop.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${resPath }/tbk/css/docList.css" >
<link rel="stylesheet" type="text/css" href="${resPath }/back/css/customPage.css" />
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
    <div align="center" >
    	<div align="center" class="acenterDiv">
	    	<div align="center" class="aleftDiv">
	    		<div align="left" class="menu-box aleftDiv2"  >
	    			<div class="aleftDiv3">
		    			<div class="aleftDiv4">类型阅读排行</div>
		    			<c:forEach items="${typeVoList }" var="type">
		    				<div><a href="${type.id }" target="_blank" >${type.name }(${type.num })</a></div>
		    			</c:forEach>
		    			<input type="hidden" id="typeId" name="typeId" value="${typeId}" >
	    			</div>
	    		</div>
	    	</div>
	    	<div align="left" class="arightDiv">
	    		<div class="menu-box arightDiv1"  >
	    			<div align="left" style="height: 20px;font-size: 15px;padding-top: 10px;padding-left: 10px;">
	    				<span style="border-bottom: 1px solid #ECECEC">>>当前类型是</span><span style="font-size: 20px;color: red;">${selectType.name}</span>
	    			</div>
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
    <div align="center" class="menu-box aFootTotal" >
    	<jsp:include page="${resPath }/tbk/commonjsp/footer.jsp"></jsp:include>
    </div>
    <!--     结束：页面底部  -->
    <jsp:include page="${resPath }/tbk/commonjsp/commonBottom.jsp"></jsp:include>
    <script type="text/javascript" src="${resPath }/back/js/customPage.js"></script>
	<script type="text/javascript" src="${resPath }/tbk/js/docList.js" ></script>
</body>
</html>