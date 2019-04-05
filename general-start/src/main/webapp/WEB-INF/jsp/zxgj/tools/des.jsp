<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>des加密</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/zxgj/css/tools.css">
<style type="text/css">
	.btnStyle{
		cursor: pointer;
	    width: 50px;
	    height: 30px;
	    color: white;
	    background-color: #E12422;
	    font-size: 12px;
	    border: 0;
	}

</style>
</head>
<body>
    <!--     开始：顶部菜单栏-->
    <jsp:include page="../top1.jsp"></jsp:include>
    <div style="height: 20px;width: 100%;background-color: #ec8316;"></div>
    <!--     结束：顶部菜单栏 -->
    <!-- 中间正文公用部分 -->
    <div class="contextArea" align="center">
    	<div style="font-size: 12px;background-color: #c0d9ef;overflow: auto;">
    		<div style="margin-left: 10px;float: left;">当前位置：加密工具>>des加密</div>
    	</div>
		<div align="center" style="width: 1000px;overflow: auto;margin-top: 20px;clear: both;">
			<div style="float: left;">
				<textarea style="width: 400px;height: 480px;resize:vertical;outline: none;" id="text1" ></textarea>
			</div>
			<div style="float: left;height: 480px;"> 
				<div class="btnStyle" style="margin-left: 50px;margin-top: 200px;" onclick="jiami()">加密</div>
				<div style="margin-left: 4px;margin-top:10px;">密码：<input type="text" id="desckey" style="width: 100px;"></div>
				<div class="btnStyle" style="margin-left: 50px;margin-top: 40px;" onclick="jiemi()">解密</div>
			</div>
			<div style="float: right: ;">
				<textarea style="width: 400px;height: 480px;resize:vertical;outline: none;" id="text2" readonly="readonly"></textarea>
			</div>
		</div>
		<div class="bottomD"></div>
    </div>
    
    <!--     开始：顶部菜单栏-->
    <jsp:include page="../bottom.jsp"></jsp:include>
    <!--     结束：顶部菜单栏 -->
    
    <script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
    <script src="${resPath }/zxgj/js/tools/des.js" type="text/javascript"></script>
    <script type="text/javascript">
    
    	function jiami(){
    		if($("#text1").val()==""){
    			return
    		}
    		var lockst=encMe($("#text1").val());
    		$("#text2").val(lockst);
    	}
    	
    	function jiemi(){
    		if($("#text1").val()==""){
                return
            }
            var lockst=uncMe($("#text1").val());
            $("#text2").val(lockst);
    	}
    	function encMe(str){
	   		if($("#desckey").val()==""){
	   			alert("请输入密码!")
	   			return;
	   		}
	   		var k=$("#desckey").val();
	   		var s=str;
	   		return base64encode(des(k,s,1,0));
   		}
   		function uncMe(str){
   			if($("#desckey").val()==""){
                alert("请输入密码!")
                return;
            }
	   		var s=$("#desckey").val();
	   		return des(s,base64decode(str),0,0);
   		}
   		$(document).ready(function(){
			$(".nagivationBottomSelect").removeClass("nagivationBottomSelect");
			$($(".nagivationBottom")[1]).addClass("nagivationBottomSelect");
		})
    </script>
</body>
</html>