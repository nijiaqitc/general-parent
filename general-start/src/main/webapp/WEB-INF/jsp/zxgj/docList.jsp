<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文章列表</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/zxgj/css/docList.css">
<link rel="stylesheet" href="${resPath }/jsTool/customPage/customPage.css">
<link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"  />
</head>
<body>
	<!--     开始：顶部菜单栏-->
    <jsp:include page="top.jsp"></jsp:include>
    <div style="height: 20px;width: 100%;background-color: #ec8316;"></div>
    <!--     结束：顶部菜单栏 -->
    <div class="contextAare" align="center">
        <div class="dtcontextArea">
            <div class="leftArea">
	            <input type="hidden" id="bsearchValue">
	            <div id="docList" class="topArea"></div>
                <!--start:分页条  -->
					 <div align="center">
					   <div id="pageDiv"></div>
					   <ul id="pageNum"></ul>
					</div>
                   <!-- end:分页条 -->
            </div>
            <div class="rightArea">
                <div class="searchArea">
	                <span><input type="text" class="asearchText"></span>
	                <span><input type="button" onclick="likeSearch()" value="搜索" class="asearchbutton" id="searchButton"></span>
                </div>
                <div id="nowSeleTip" class="seleArea">
                	<c:if test="${tipName!=null }">
	                	<div class="tip"><span>${tipName }</span><a onclick="closeTip(this)" href="javascript:void(0);" title="删除" style="color: #999;">×</a></div>
                	</c:if>
                </div>
                <div align="center">
                    <div class="tipsArea" align="left">
                        <c:forEach items="${tipList }" var="tip">
	                        <span onclick="searchTip(this)" class="tips">${tip.tipName }</span>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--     开始：顶部菜单栏-->
	<jsp:include page="bottom.jsp"></jsp:include>
    <!--     结束：顶部菜单栏 -->
    <script type="text/javascript" src="${resPath }/jquery/jquery.min.js" ></script>
    <script type="text/javascript" src="${resPath }/zxgj/js/common.js" ></script>
    <script type="text/javascript" src="${resPath }/jsTool/customPage/customPage.js"></script>
    <script type="text/javascript" src="${resPath }/zxgj/js/docList.js" ></script>
    <script type="text/javascript" src="${resPath }/common/js/grabCommon.js"></script>
</body>
</html>