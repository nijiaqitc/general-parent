<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>主页</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"  />
<link rel="stylesheet" href="${resPath }/zxgj/css/novelList.css">
<style type="text/css">
	
/*全屏开始*/
.adocDiv{
	z-index: 998;width: 100%;margin: 10px;position:absolute;
}
.adocDiv1{
	background-color: #fff;border-radius: 6px;width: 997px;box-shadow: 0 3px 9px rgba(0,0,0,0.5);min-height: 400px;
}
.transparent_class {  
    /* Required for IE 5, 6, 7 */  
    /* ...or something to trigger hasLayout, like zoom: 1; */  
    width: 100%;
    height: 100%; 
    position: fixed;
    line-height:300px;  
    text-align:center;  
    background:#cccccc;  
    color:#fff;  
    top: 0;right: 0;bottom: 0;left: 0; 
}

.aopenWord{
	width: 20px;border-radius: 2px;cursor: pointer;
}
.aopenWord1{
	background: url('../images/icons.png') no-repeat;width: 20px;display: block;height: 20px;background-position: -100px -20px;
}
.aopenWord2{
	background-color: #ffe69f;width: 20px;border: 1px #dcac6c solid;border-radius: 2px;cursor: pointer;
}
.aopenWord3{
	background: url('../images/icons.png') no-repeat;width: 20px;display: block;height: 20px;background-position: -100px -20px;
}
.aopenWord4{
	width:840px;padding-top: 60px;
}
.aopenT{
	font-size: 18px;line-height: 2;font-weight: normal;margin-top: 10px;margin-bottom: 10px;
}
.aopenWord5{
	font-size: 16px;font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
}
.aopenWord6{
	text-indent: 24px;font-size: 14px;
}
.aopenWord6  div{
    padding: 4px 0px;
}
.aopenWord7{
	font-size: 12px;
}
.aopenWord8{
	font-family:'SimSun';font-size: 16px;line-height: 26px;margin-top: 50px;
}
.aopenWord9{
	font-weight: 600;font-size: 12px;margin: 10px 0 4px 0;
}
.aopenWord10{
	font-weight: 600;font-size: 12px;margin-bottom: 20px;
}
.aopenWord11{
	color: red;font-size: 14px;
}
.aopenWord12{
	
}
.aopenWord13{
	width: 50px;
}
.bonT{
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;margin-bottom: 24px;margin-left: 24px;
}
/*全屏结束*/

.topTip{
	float: left;
    margin-right: 40px;
}
</style>
</head>
<body>
	<!--     开始：顶部菜单栏-->
	<div style="height: 40px;" class="menu-box topMenu" align="center">
		<div style="width: 860px;overflow: auto;line-height: 40px;">
			<div class="topTip">本试卷总分150分，75分通过</div>
			<div class="topTip">考题数量：30 题</div>
			<div class="topTip">考试时长：30 分钟</div>
			<div class="topTip">交卷</div>
			<div style="float: right;display:inline-block;vertical-align:top;color:#fead11;font-size:21px;">
				<i class="icon-time"></i>
				<span id="leftTime">00:00:00</span>
			</div>
		</div>
	</div>
	<!--     结束：顶部菜单栏 -->
	<!-- 中间正文公用部分 -->
	<div align="center">
		<div id="wordForShow" align="center" class="adocDiv"  style="margin-top: 50px;" >
	     	<div class="adocDiv1"  align="center">
	     		<div class="aopenWord4" align="left">
	     			<div class="aopenT">
	     				一、单选题（<span>共27题，每题5分</span>）
	     			</div>
	     			<div class="bonT">
			     		<div class="aopenWord5">
			     			1、请在以下选项中选择一个正确的答案
			     		</div>
			     		<div class="aopenWord6" align="left" >
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>A、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>B、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>C、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>D、选项aaa</span>
			     			</div>
			     		</div>
	     			</div>
	     			<div class="bonT">
			     		<div class="aopenWord5">
			     			2、请在以下选项中选择一个正确的答案
			     		</div>
			     		<div class="aopenWord6" align="left" >
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>A、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>B、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>C、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>D、选项aaa</span>
			     			</div>
			     		</div>
	     			</div>
	     			<div class="bonT">
			     		<div class="aopenWord5">
			     			2、请在以下选项中选择一个正确的答案
			     		</div>
			     		<div class="aopenWord6" align="left" >
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>A、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>B、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>C、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>D、选项aaa</span>
			     			</div>
			     		</div>
	     			</div>
	     			<div class="bonT">
			     		<div class="aopenWord5">
			     			2、请在以下选项中选择一个正确的答案
			     		</div>
			     		<div class="aopenWord6" align="left" >
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>A、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>B、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>C、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>D、选项aaa</span>
			     			</div>
			     		</div>
	     			</div>
	     			<div class="bonT">
			     		<div class="aopenWord5">
			     			2、请在以下选项中选择一个正确的答案
			     		</div>
			     		<div class="aopenWord6" align="left" >
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>A、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>B、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>C、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>D、选项aaa</span>
			     			</div>
			     		</div>
	     			</div>
	     			<div class="bonT">
			     		<div class="aopenWord5">
			     			2、请在以下选项中选择一个正确的答案
			     		</div>
			     		<div class="aopenWord6" align="left" >
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>A、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>B、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>C、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>D、选项aaa</span>
			     			</div>
			     		</div>
	     			</div>
	     			<div class="bonT">
			     		<div class="aopenWord5">
			     			2、请在以下选项中选择一个正确的答案
			     		</div>
			     		<div class="aopenWord6" align="left" >
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>A、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>B、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>C、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>D、选项aaa</span>
			     			</div>
			     		</div>
	     			</div>
	     			<div class="bonT">
			     		<div class="aopenWord5">
			     			2、请在以下选项中选择一个正确的答案
			     		</div>
			     		<div class="aopenWord6" align="left" >
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>A、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>B、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>C、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>D、选项aaa</span>
			     			</div>
			     		</div>
	     			</div>
	     			<div class="bonT">
			     		<div class="aopenWord5">
			     			2、请在以下选项中选择一个正确的答案
			     		</div>
			     		<div class="aopenWord6" align="left" >
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>A、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>B、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>C、选项aaa</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="aab">
				     			<span>D、选项aaa</span>
			     			</div>
			     		</div>
	     			</div>
		     		<!-- <div class="aopenWord7">
		     		
		     		333
		     		</div> -->
		     	</div>
		     	<div style="height: 50px;">
		     		<br>
		     	</div>
	     	</div>
	    </div>
	  	<div id="wordModel" class="transparent_class" ></div>
	</div>
	<!--     开始：顶部菜单栏-->
	<jsp:include page="../zxgj/bottom.jsp"></jsp:include>
	<!--     结束：顶部菜单栏 -->
	<script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
	<script src="${resPath }/jquery/countDown.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
	<script type="text/javascript">
		$("#leftTime").countDown({
		    times: 30,  
		    ms: false,   
		    Hour: true   
		},function(){});
	
	</script>
</body>
</html>