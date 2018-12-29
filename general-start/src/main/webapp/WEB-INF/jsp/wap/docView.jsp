<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<?xml version="1.0" encoding="UTF-8"?>  
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>  
  <title>${docViewText.title}</title>  
  <meta name="keywords" content="${tipString }">
  <meta name="description" content="${docViewText.general }">
  <jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="${resPath }/wap/css/docView.css"  >
</head> 
<body>
	<!-- 	顶部div开始 -->
	<jsp:include page="${resPath }/wap/commonwap/top.jsp"></jsp:include>
	<!-- 	顶部div结束 -->
	<jsp:include page="${resPath }/wap/commonwap/loading.jsp"></jsp:include>
	<!-- 	内容区域开始 -->
	<div class="textContext" >
		<div class="menu-box" align="center">
			<div>
				<div class="docView1">${docViewText.title} </div>
				<div class="docView2">
					${author }|${docViewText.formatCreatedDate }|(${docViewText.readnums })
				</div>
				<div class="docView3">
					类型:<a href="${path }/wap/toTrends?typeName=${tbkType.name}" >${tbkType.name }</a>|
					标签: 
					<c:forEach items="${docViewTbkTipList }" var="tbkTip">
							${tbkTip.name }
					</c:forEach>
				</div>
			</div>
			<div align="center" id="assss" class="docView4">
			</div>
			<div align="right" class="docView5">
				<c:if test="${docViewText.reprint==2 }">
					<div class="docView7"><span class="docView6">文章转自：<a href="${docViewText.formatGeneralHref }">点击此处</a></span></div>
					<div class="docView7"><span class="docView6">提示：若此文禁止转载，请联系站长帮您撤销！</span></div>
				</c:if>
				<div align="right" class="docView8"><a href="${authUrl }" class="docView9" >琦三叔官网</a>&nbsp;一个草根的网站...</div>
			</div>
		</div>
	</div>
	<!-- 	内容区域结束 -->
	
	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
  	<script type="text/javascript"  src="${resPath }/jsTool/custom/js/customClearStyle.js"></script>
  	<script type="text/javascript" src=".${resPath }/jsTool/custom/js/customHtmlDecoder.js"></script>
  	<script type="text/javascript">
	  	$(function(){
	  		var cd=new CustomDecoder()
	     	cd.str='${docViewText.text}'.toString();
	     	var chd=new customHtmlDecoder();
			var ispre=1;
			if($("#isPre").val()==1){
				ispre=false;
			}else{
				ispre=true
			}
	     	$("#assss").html(chd.decode(cd.decode(),{ispre:true,tranType:2,spaceType:4}));
	  	});
    </script>
</body>  
</html>