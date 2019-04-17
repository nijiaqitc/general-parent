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
  <style type="text/css">
       .textStx{
       		font-size: 16px;
       	    clear: both;
		    margin-top: 10px;
		    text-indent: 24px;
       }
       .refcls{
       		font-size: 12px;
		    margin-top: 20px;
		    border-top: 1px dashed #aaa;
		    text-indent: 0px;
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
			<h2 style="font-size: 24px;">${titleTypeInfo.name }</h2>
        	<c:forEach items="${studyList }" var="info" varStatus="status">
        		<div style = "margin-top: 10px;overflow: auto;">
        			<div align="left" style="font-size: 20px;float: left;padding: 0px 10px;">
        				<strong>
	        				${status.index+1 }、
	        				<span onclick="setValue('${info.id }',this)" style="cursor: pointer;margin-right: 20px;
								 <c:if test="${info.isNeedStudy == true}"> color: red; </c:if> " >
		        				${info.title }
							</span>
        				</strong>
        			</div>
       				<c:if test="${showTitle == true }">
	        			<div style="float: right;">
							<span onclick="showAnswer(this)"   style="cursor: pointer;" >
								<i class='icon-eye-open starcl'></i>
							</span>
	        			</div>
       				</c:if>
        		</div>
        		<div  class="textStx"  style="padding: 0px 20px;text-align: justify;<c:if test="${showTitle == true }"> display: none; </c:if>" >
        			<c:forEach items="${info.answerList }" var="an">
        				<div>
		        			${an.answer }
        				</div>
        				<c:if test="${an.columDesc !=null && an.columDesc != '' }">
			        		<div class="refcls">
			        			${an.columDesc }
			        		</div>
						</c:if>
        			</c:forEach>
        		</div>
        	</c:forEach>
		</div>
	</div>
	<!-- 	内容区域结束 -->
	
	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
  	<script type="text/javascript">
	  	function setValue(titleId,target) {
	        var type = 1;
	        if(target.style["color"]=="red"){
				type=0;        	
	        }
	        $.ajax({
	            url: "${path}/study/setUnOrNeedStudy",
	            data: {
	                id: titleId,
	                type: type
	            },
	            type: "post",
	            success: function (data) {
	                if(type==0){
	                	target.style["color"]="black";
	                }else{
	                	target.style["color"]="red";
	                }
	            }
	        })
	    }
	    
	    function showAnswer(target){
	    	console.info(target)
	    	var node = target.parentElement.parentElement.nextElementSibling;
	    	if(node.style["display"]=="block"){
	    		node.style["display"]="none";
	    	}else{
	    		node.style["display"]="block";
	    	}
	    }
    </script>
</body>  
</html>