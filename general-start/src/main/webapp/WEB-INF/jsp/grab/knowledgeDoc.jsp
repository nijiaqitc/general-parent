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
		
		.toptip{
			padding: 2px;
			margin-left: 6px;
			cursor: pointer;
		    display: block;
    		float: left;
		}
		.labelsavebtn{
			margin-left: 14px;border: 1px solid #eee;cursor: pointer;
		}
		.labelsavebtn:hover{
			background-color: #ddd;
		}
		.toptip:hover{
			background-color: #ddd;
		}
		
		.classify-more {
		    height: 40px;
		    box-sizing: border-box;
		    text-align: center;
		    padding-top: 20px;
		}
		.classify-more p {
		    display: inline-block;
		    width: 350px;
		    border-top: 1px solid #ddd;
		    height: 0;
		}
		.classify-more p span {
		    display: inline-block;
		    padding: 0 31px;
		    background: #fff;
		    position: relative;
		    top: -11px;
		}
		.classify-more p span a{
			padding: 20px;
			font-size: 14px;
    		color: #666;
    		-webkit-transition: all .2s;
		    -o-transition: all .2s;
		    transition: all .2s;
		    cursor: pointer;
		    text-decoration: none;
		}
		.letop{
			top: 0px;
			right: 0px;
			position: fixed;
		    width:20px;
		    background-color: red;
		    color: black;
		    z-index: 99999;
		    cursor: pointer;
		    text-align: center;
		    margin-right: 20px;
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
	
	
	<div id="csdialog" style="position: fixed;width: 550px;min-height: 330px;background-color: #fff;top: -360px;border: 1px solid #888;right: 0px;" align="center">
		<div style="width: 300px;margin-top: 30px;">
		    <input type="hidden" id="docId" value="">
			<div align="center" style="padding: 16px 0;">
				<input type="text" id="tipName" name="tipName"><input type="button" name="保存" onclick="saveTip()"  value="保存"  class="labelsavebtn">
			</div>
			<c:if test="${tipList!=null&&tipList.size()>0 }">
				<div class="classify-more" style="margin-left: -50px;">
			        <p>
			        	<span>
			        		<a rel="nofollow" href="javascript:void(0)" target="_blank" style="padding: 20px;" class="click-statistics">当前标签</a>
			        	</span>
			        </p>
			    </div>
			    <div align="center" style="font-size: 12px;">
				 	<c:forEach items="${tipList }" var="t">
				      	<span>${t }</span> 
					</c:forEach>
			    </div>
			</c:if>
			
			<div class="classify-more" style="margin-left: -50px;">
		        <p>
		        	<span>
		        		<a rel="nofollow" href="javascript:void(0)" target="_blank" style="padding: 20px;" class="click-statistics">新标签</a>
		        	</span>
		        </p>
		    </div>
			
			<div align="left" style="padding: 6px 0;font-size: 12px;">
				<c:forEach items="${topList }" var="tip">
					<span class="toptip" onclick="savett('${tip }')">
						${tip }
					</span>
				</c:forEach>
			</div>
		</div>
	</div>
	<div class="letop" onclick="showhideDialog()">+</div>
	
	
	
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

	function showhideDialog(){
	    if($("#csdialog").css("top")=="-360px"){
	        $("#csdialog").animate({top: '0px'},"fast");
	    }else{
	        $("#csdialog").animate({top: '-360px'},"fast");   
	    }
	}
	
	function saveTip(){
		savett($("#tipName").val());
	}
	
	
	function savett(tipName){
		$.ajax({
			url:"${path}/grab/addTip",
			data:{
				docId:${doc.id},
				tipName:tipName
			},
			type:"post",
			success:function(data){
				if(data.state == "1"){
					alert(data.message);
					showhideDialog();
				}
			}
		})
	}
</script>
</html>