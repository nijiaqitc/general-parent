<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>${doc.title }</title>
	<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
    <link rel="stylesheet" href="${resPath }/zxgj/js/prettify.css"/>
    <link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
	<link rel="stylesheet" href="${resPath }/zxgj/css/knowledgeDoc.css">
	<link rel="stylesheet" href="${resPath }/zxgj/css/grab.css">
	<style type="text/css">
		.stbot{
		    bottom: 0;
		    position: fixed;
		    width: 100%;
		}
		.tipcs{
			text-align: center;
		    font-size: 12px;
		    border-bottom: 1px solid #ddd;
		    width: 600px;
		}
		.tipcs span{
			margin-left: 14px;
		}
	</style>
</head>
<body>
<div class="centerDiv">
	<div id="docContext" class="docContext" align="center">
		    <div class="contextArea" >
		        <div align="center" class="textSt">${doc.title }</div>
		        <c:if test="${tipList!=null&&tipList.size()>0 }">
			        <div align="center">
				    	<div class="tipcs">
					        标签：<c:forEach items="${tipList }" var="t">
					        	<span>${t }</span> 
					        </c:forEach>
				    	</div>    
			        </div>
		        </c:if>
		        <c:if test="${loaded != null}">
			        <div style="text-align: center;">提取来源：<a href="${loaded.url }">${loaded.url }</a></div>
		        </c:if>
		        <div id="textContext" style="margin-top: 20px;">${doc.doc }</div>
		    </div>
	    </div>
</div>
	<!--     开始：底部菜单栏-->
	<jsp:include page="../zxgj/bottom.jsp"></jsp:include>
</body>
<script type="text/javascript" src="${resPath }/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${resPath }/zxgj/js/prettify.js"></script>
<script type="text/javascript" src="${resPath }/jsTool/customClearStyle/customClearStyle.js"></script>
<script type="text/javascript" src="${resPath }/zxgj/js/grab.js"></script>
<script type="text/javascript">
	$(function(){
		if($("body").height()<500){
			$(".bottomInfoDiv").addClass("stbot");
		}
	})

</script>
</html>