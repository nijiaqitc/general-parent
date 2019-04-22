<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<?xml version="1.0" encoding="UTF-8"?>  
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>  
  <title>知识复习</title>  
  <meta name="keywords" content="${tipString }">
  <jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="${resPath }/wap/css/studyDoc.css"  >
  <link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"/>
  <link rel="stylesheet" href="${resPath }/jsTool/customPage/wapPage.css"/>
	<style type="text/css">
       .colorTr {
		    background-color: #f7faff;
		    display: table-row;
		    vertical-align: inherit;
		    border-color: inherit;
		    padding: 0;
		    word-wrap: break-word;
		    cursor: text;
		}
		table{
			width: 100%;
			border-collapse: collapse;
		}
		thead {
		    border: 1px solid #DDD;
		}
		td,th{
			padding: 8px 10px;
		}
		td {
		    border: 1px solid #DDD;
		    word-break: break-all;
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
		<div class="menu-box" align="left">
			<table>
				<thead>
					<th>人物</th>
					<th style="width: 80%;">内容</th>
				</thead>
				<c:forEach items="${storeList }" var="store" varStatus="index">
					<tr <c:if test="${index.index%2==0 }">class="colorTr"</c:if> >
						<td>${store.col1 }</td>
						<td>${store.col2 }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<!-- 	内容区域结束 -->
	<div id="pageDiv"></div>
	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
	<script type="text/javascript" src="${resPath }/jsTool/customPage/wapPage.js"></script>
  	<script type="text/javascript">
	  	$(document).ready(function(){
			njqpage.makePage({
				excId:pageDiv,
				index:${page},
				totalNum:${total},
				size:100,
				req:"${req}"
			}); 
		})
	  	
	    
    </script>
</body>  
</html>