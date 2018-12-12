<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发表小说</title>
<jsp:include page="${basePath}/commonTopLink"></jsp:include>
<link href="${resPath }/jsTool/njqeditor/css/njqEditorTypeOne.css"  rel="stylesheet">
</head>
<body>
	<!-- 通用顶部 -->
	<jsp:include page="${basePath}/commonTop"></jsp:include>
	<!-- 中下部分 -->
	<div id="centerPlace">
		<!-- 通用左边菜单 -->	
		<jsp:include page="${basePath}/commonLeft"></jsp:include>
		<!-- 正文区域 -->
		<div id="rightContext">
			 <div align="center">
			    <div style="height: 40px;width: 100%;"></div>
			    <input type="hidden" id="title" name="title" value="${title }" >
			    <input type="hidden" id="titleIndex" name="titleIndex" value="${titleIndex }">
			    <input type="hidden" id="docId" name="docId" value="${docId }" >
		        <div id="njqEditorDiv"></div>
		    </div>
		    <div style="position: absolute;z-index: 10;top: 100px;right: 0px;width: 52px;background-color: #fff;height: 200px;">
		      <div id="rightToolTitle" align="right" style="width: 47px;height: 40px;line-height: 40px;cursor: pointer;" onclick="showDialog(this,1)">标题Ψ</div>
		      <div id="rightToolNum" align="right" style="width: 47px;height: 40px;line-height: 40px;cursor: pointer;" onclick="showDialog(this,2)">序号Ψ</div>
		      <div id="finishStatus" align="right" style="width: 47px;height: 40px;line-height: 40px;cursor: pointer;" onclick="showDialog1(this)">状况Ψ</div>
		      <div align="right" style="width: 47px;height: 40px;line-height: 40px;cursor: pointer;" onclick="saveNode()">保存Ψ</div>
		    </div>
		    <div id="tkSetValue" style="position: absolute;top: 100px;width: 244px;height: 150px;right: 60px;background-color: #222;z-index: 15;display: none;">
		      <textarea style="width: 238px;height: 96%;"></textarea>
		      <input type="button" onclick="setValue(this)" style="margin-top: -29px;position: absolute;margin-left: 200px;" value="确定">
		    </div>
		    <div id="fintype" style="border:1px solid black; position: absolute;top: 100px;width: 200px;height: 50px;right: 60px;background-color: #fff;z-index: 15;display: none;">
		                  未开始<input type="radio" name="fin" value="0" checked="checked"> 进行中<input type="radio" name="fin" value="1"> 已完成<input type="radio" name="fin" value="2"> 
		    </div>
		</div>
		<!-- 通用底部 -->	
		<jsp:include page="${basePath}/commonBottom"></jsp:include>
	</div>
</body>
<jsp:include page="${basePath}/commonBottomLink"></jsp:include>
    <script type="text/javascript">
	    //ie8不支持trim方法
	    String.prototype.trim = function () {
	        return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
         }
    </script>
    <!--     加载用户自定义配置 -->
    <script type="text/javascript" src="${resPath }/jsTool/njqeditor/js/njqEditor_config.js"></script>
    <script type="text/javascript">
    function showDialog(t,num){
    	$("#fintype").hide();
    	$("#tkSetValue").show();    
    	$("#tkSetValue textarea").val(t.targetValue);
    	$("#tkSetValue")[0].attrType=t;
    	var top=t.parentElement.offsetTop+t.offsetTop;
    	$("#tkSetValue").css({"top":top+"px"});
    	$("#tkSetValue")[0].typeValue=num;
    }
    function setValue(btn){
    	$("#tkSetValue")[0].attrType.targetValue=btn.previousElementSibling.value;
    	$(btn.parentNode).hide();
    	if($("#tkSetValue")[0].typeValue==1){
    		   $("#njqEditor_name_value").html(btn.previousElementSibling.value);
    	}else{
    		   $("#njqEditor_num_value").html("第"+btn.previousElementSibling.value+"章");
    	}
    }
    function showDialog1(t){
    	$("#fintype").show();
    	var top=t.parentElement.offsetTop+t.offsetTop;
        $("#fintype").css({"top":top+"px"});
        $("#tkSetValue").hide();
    }
    
    function saveNode(){
    	$.ajax({
    		url:"saveNovel",
    		type:"post",
    		data:{
    			text:$("#njqEditor_context").html(),
    			title:$("#title").val(),
    			thcId:$("#docId").val(),
    			userId:"",
    			doc:$("#njqEditor_context").html(),
    			finishStatus:$("input[name='fin']:checked").val(),
    			fontNum:$("#njqEditor_wordCount").html().split("：")[1]
    		},
    		success:function(data){
    			if(data.state==1){
    				alert("保存成功");
    			}
    		}
    	})
    }
    $(function(){ 
    	setTimeout(function(){
    		$("#rightToolTitle")[0].targetValue=$("#title").val();
    		$("#rightToolNum")[0].targetValue=$("#titleIndex").val();
	    	$("#njqEditor_num_value").html("第"+$("#titleIndex").val()+"章");
	    	$("#njqEditor_name_value").html($("#title").val());
    	},50)
    }); 
    </script>
</html>