<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>知识复习</title>
    <link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png"/>
    <link rel="stylesheet" href="${resPath }/zxgj/js/prettify.css"/>
    <link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
    <link rel="stylesheet" href="${resPath }/zxgj/css/knowledgeDoc.css">
    <link rel="stylesheet" href="${resPath }/zxgj/css/grab.css">
    <link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"/>
    <style type="text/css">
       .textStx{
       		font-size: 16px;
       	    clear: both;
		    margin-top: 20px;
		    text-indent: 24px;
       }
    </style>
</head>
<body>
<div class="centerDiv">
    <div id="docContext" class="docContext" align="center">
        <div class="contextArea">
        	<h2 style="font-size: 24px;">${titleTypeInfo.name }</h2>
        	<c:forEach items="${studyList }" var="info" varStatus="status">
        		<div style = "margin-top: 20px;overflow: auto;">
        			<div align="left" style="font-size: 20px;float: left;height: 28px;">
        				<strong>
	        				${status.index+1 }、${info.title }
        				</strong>
        			</div>
        			<div style="float: right;padding-top: 8px;">
        				<c:if test="${showTitle == true }">
	        				<span onclick="showAnswer(this)"   style="cursor: pointer;" >
								<i class='icon-eye-open starcl'></i>
							</span>
        				</c:if> 
						<span onclick="setValue('${info.id }',this)"  
							style="cursor: pointer;
							 <c:if test="${info.isNeedStudy == true}"> color: red; </c:if> " >
							<i class='icon-star starcl'></i>
						</span>
        			</div>
        		</div>
       			
        		<div  class="textStx" <c:if test="${showTitle == true }"> style="display: none;" </c:if>>
        			<c:forEach items="${info.answerList }" var="an">
	        			${an.answer }
        			</c:forEach>
        		</div>
        	</c:forEach>
        </div>
    </div>
</div>
<%-- <div id="csdialog" align="center"
     style="position: fixed;width: 550px;min-height: 330px;background-color: #fff;top: -360px;border: 1px solid #888;right: 0px;" >
    <div style="width: 300px;margin-top: 30px;">
        <c:forEach items="${titleTypeList }" var="ttl">
			<span class="toptip" onclick="">
                         ${ttl.name }
            </span>
        </c:forEach>
    </div>
</div>
<div class="letop" onclick="showhideDialog()">+</div> --%>




<!--     开始：底部菜单栏-->
<jsp:include page="../zxgj/bottom.jsp"></jsp:include>
</body>
<script type="text/javascript" src="${resPath }/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${resPath }/zxgj/js/prettify.js"></script>
<script type="text/javascript" src="${resPath }/jsTool/customClearStyle/customClearStyle.js"></script>
<script type="text/javascript" src="${resPath }/zxgj/js/grab.js"></script>
<script type="text/javascript" src="${resPath }/common/js/grabCommon.js"></script>
<script type="text/javascript">
    function showhideDialog() {
        if ($("#csdialog").css("top") == "-360px") {
            $("#csdialog").animate({top: '0px'}, "fast");
        } else {
            $("#csdialog").animate({top: '-360px'}, "fast");
        }
    }


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
</html>