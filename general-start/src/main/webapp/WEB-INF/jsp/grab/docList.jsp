<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文章列表</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"  />
<link rel="stylesheet" href="${resPath }/jsTool/customPage/customPage.css">
<link rel="stylesheet" href="${resPath }/grab/css/docList.css">
<jsp:include page="commonTop.jsp"></jsp:include>
</head>
<body>
	<div class="centerDiv">
		<div style="text-align: center;font-size: 20px;font-weight: 600;margin-top: 20px;">文章列表</div>
		<div id="docContext" class="docContext" style="margin-bottom: 20px;margin-top: 10px;" align="center">
		    <div id="docList" class="contextArea" style="width: 1000px;text-align: left;padding: 10px 10px;">
		    	<c:forEach items="${titleList }" var="tt">
			    	<div>
						<a class='lmd' href="knowledge/${tt.docId }" target='_blank' >${tt.title }</a>
						<span class='rightArea'>
							<c:if test="${tt.starTab != null }">
								<i class='icon-star starcl'></i>
							</c:if>
						</span>
						<span class='starspan' >${tt.createDate }</span>
					</div>		    	
		    	</c:forEach>
		    </div>
		    <div align="center">
			   <div id="pageDiv"></div>
			   <ul id="pageNum"></ul>
			</div>
	    </div>
	</div>
	<!--     开始：底部菜单栏-->
	<jsp:include page="../zxgj/bottom.jsp"></jsp:include>
	<script type="text/javascript" src="${resPath }/jquery/jquery.min.js" ></script>
	<script type="text/javascript" src="${resPath }/jsTool/customPage/customPage.js"></script>
	<script type="text/javascript" src="${resPath }/grab/js/docList.js"></script>
</body>
</html>