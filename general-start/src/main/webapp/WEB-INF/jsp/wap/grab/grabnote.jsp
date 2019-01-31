<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<?xml version="1.0" encoding="UTF-8"?>  
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>  
  <title>${docViewText.title}</title>  
  <meta name="keywords" content="${tipString }">
  <jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="${resPath }/wap/css/docView.css"  >
  <style type="text/css">
    	table{
    		border-collapse: collapse;
    	}
    	th{
    		user-select: none;
		    min-width: 0px;
		    max-width: none;
		    background: #f0f0f0 center right no-repeat;
		    padding-right: 15px;
		    cursor: pointer;
		    border: 1px solid #ddd;
		    padding: 7px 10px;
		    vertical-align: top;
		    text-align: left;
		    font-size: 0.8rem;
    	}
    	
    	td{
    		border: 1px solid #ddd;
		    padding: 7px 10px;
		    vertical-align: top;
		    text-align: left;
		    font-size: 0.8rem;
    	}
    	
    	td span {
    		font-size: 0.8rem;
    	}
    	
    	td p {
    		font-size: 0.8rem;
    	}
    	
    </style>
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
			</div>
			<div align="center" id="contextValue" class="docView4" style="padding-bottom: 40px;">
				${docViewText.doc}
			</div>
		</div>
	</div>
	<!-- 	内容区域结束 -->

	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
  	<script type="text/javascript"  src="${resPath }/jsTool/customClearStyle/customClearStyle.js"></script>
  	<script type="text/javascript">
	    $(function(){
	    	var cd=new CustomDecoder();
	    	cd.showPlatform="wap";
			cd.str=$("#contextValue").html();
	    	$("#contextValue").html(cd.decode());
	    })
	</script>
</body>  
</html>