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
<link rel="stylesheet" href="${resPath }/zxgj/js/prettify.css"/>
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
.subEx{
	cursor: pointer;
    border: 1px solid #eee;
    padding: 2px 4px;
}

.subEx:hover{
	background-color: #eee;
}

.fractionValue{
	color:#ef4a4a;
	position: absolute;
    right: 10%;
    font-size: 60px;
    overflow: auto;
    height: 70px;
    line-height: 60px;
}

.prettyprint ul {
    position: absolute;
    width: 36px;
    background-color: #282C33;
    top: 0;
    left: 0;
    margin: 0;
    padding: 8px 0;
    list-style: none;
    text-align: right;
}
.prettyprint ul li {
    color: #abb2bf!important;
    border-right: 1px solid #c5c5c5;
    padding: 0 8px;
    list-style: none;
    margin: 0;
    text-indent: 0px;
}
pre{
	position: relative !important;
    overflow-y: hidden !important;
    overflow-x: auto !important;
    font-size: 16px !important;
    line-height: 22px !important;
    font-family: Source Code Pro,DejaVu Sans Mono,Ubuntu Mono,Anonymous Pro,Droid Sans Mono,Menlo,Monaco,Consolas,Inconsolata,Courier,monospace,PingFang SC,Microsoft YaHei,sans-serif !important;
    margin: 0 0 0px !important;
    padding: 8px 16px 6px 56px !important;
    background-color: #282C33 !important;
    border: none !important;
    white-space: pre !important;
}
.pre-numbering li span{
    color: #fff !important;
}
input{
	cursor: pointer;
}

.questions .div{
	width: 160px;
    float: left;
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
			<div class="topTip"><span class="subEx" onclick="submitEx()">交卷</span></div>
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
				<div class="fractionValue" >100</div>
	     		<div class="aopenWord4" align="left">
	     			<div class="aopenT">
	     				一、单选题（<span>共30题，每题1分</span>）
	     			</div>
	     			<c:forEach items="${examap.selectSub }" var="sub" varStatus="order">
		     			<div class="bonT">
				     		<div class="aopenWord5">
				     			${order.index+1 }、${sub.title }
				     		</div>
				     		<div class="aopenWord6 selectSub" align="left"  answer="${sub.answerList[0].answer }" attId="${sub.id }" >
				     			<c:if test="${sub.general != null}">
					     			<div>
					     				${sub.general }
					     			</div>
				     			</c:if>
				     			<c:forEach items="${sub.optionsub }" var="option">
					     			<div>
						     			<input type="radio"  name="st${sub.id }" value="A" >
						     			<span>${option }</span>
					     			</div>
				     			</c:forEach>
				     			
<!-- 				     			<div> -->
<!-- 					     			<input type="radio"  name="aab"> -->
<!-- 					     			<span>B、选项aaa</span> -->
<!-- 				     			</div> -->
<!-- 				     			<div> -->
<!-- 					     			<input type="radio"  name="aab"> -->
<!-- 					     			<span>C、选项aaa</span> -->
<!-- 				     			</div> -->
<!-- 				     			<div> -->
<!-- 					     			<input type="radio"  name="aab"> -->
<!-- 					     			<span>D、选项aaa</span> -->
<!-- 				     			</div> -->
				     			
				     			
				     		</div>
		     			</div>	     			
	     			</c:forEach>
	     			
	     			
	     			
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
	     			<div class="aopenT">
	     				二、简答题（<span>共30题，每题2分</span>）
	     			</div>
	     			<div class="bonT">
			     		<div class="aopenWord5">
			     			2、请在以下选项中选择一个正确的答案
			     		</div>
			     		<div class="aopenWord6 questions" align="left"  attId="11">
			     			<div>
				     			<input type="radio"  name="ques11">
				     			<span>A、通晓原理</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="ques11">
				     			<span>B、知晓大概</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="ques11">
				     			<span>C、含含糊糊</span>
			     			</div>
			     		</div>
	     			</div>
	     			<div class="bonT">
			     		<div class="aopenWord5">
			     			2、请在以下选项中选择一个正确的答案
			     		</div>
			     		<div class="aopenWord6 questions" align="left"  attId="12">
			     			<div>
				     			<input type="radio"  name="ques12">
				     			<span>A、通晓原理</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="ques12">
				     			<span>B、知晓大概</span>
			     			</div>
			     			<div>
				     			<input type="radio"  name="ques12">
				     			<span>C、含含糊糊</span>
			     			</div>
			     		</div>
	     			</div>
		     		<div class="aopenT">
	     				三、笔试题（<span>共1题，每题10分</span>）
	     			</div>
	     			<div class="bonT">
			     		<div class="aopenWord5">
			     			2、请在以下选项中选择一个正确的答案
			     		</div>
			     		<div class="aopenWord6" align="left" >
			     			<div>
				     			<input type="radio" value="1"  name="pen" onclick="showOrHideWriteArea(this)">
				     			<span>A、会</span>
			     			</div>
			     			<div>
				     			<input type="radio" value="2"  name="pen"  onclick="showOrHideWriteArea(this)">
				     			<span>B、不会</span>
			     			</div>
			     			<div align="left" id="penarea" style="text-indent: 0px;display: none;">
			     				<label>会，那就写一个</label>
			     				<textarea style="width: 100%;height: 300px;"></textarea>
			     			</div>
			     		</div>
	     			</div>
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
	<script src="${resPath }/zxgj/js/prettify.js" type="text/javascript"></script>
	<script type="text/javascript">
		$("#leftTime").countDown({
		    times: 30,  
		    ms: false,   
		    Hour: true   
		},function(){});
		
		function decodePre() {
		    for (var i = 0; i < $("pre").length; i++) {
		        var code = document.createElement("code");
		        code.innerHTML = $($("pre")[i]).html();
		        $($("pre")[i]).html(code);
		        var ul = document.createElement("ul");
		        ul.classList.add("pre-numbering");
		        var height = $($("pre")[i]).height();
		        var num = parseInt(Number(height / 22));
		        for (var j = 0; j < num; j++) {
		            var li = document.createElement("li");
		            li.innerHTML = j + 1;
		            ul.append(li);
		        }
		        $($("pre")[i]).append(ul);
		    }
		    $("pre").addClass("prettyprint");
		    prettyPrint();
		}
		
		decodePre();
		
		function submitEx(){
			var selsub = $(".selectSub");
			var total = 0;
			for(var i = 0 ;i < selsub.length;i++){
				console.info($(selsub[i]).attr("answer"));
				console.info($(selsub[i]).attr("attId"));
				var id = $(selsub[i]).attr("attId");
				$("input[name='st"+id+"']:checked").val();
			}
			
			var qus = $(".questions");
			for(var i = 0 ;i < qus.length;i++){
				console.info($(qus[i]).attr("attId"));
			}
			
			
			console.info("提交");
		}
		
		
		
		function showOrHideWriteArea(target){
			if($(target).val() == 1){
				$("#penarea").show();
			}else{
				$("#penarea").hide();
			}
		}
	</script>
</body>
</html>