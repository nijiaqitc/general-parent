<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
<script src="${resPath }/zxgj/js/stopCopy.js" type="text/javascript"></script>
<link rel="stylesheet" href="${resPath }/zxgj/js/prettify.css"/>
<style>
html,body{
	height: 100%;
}
p{
    word-break: break-all;
    font-size: 20px;
    margin: 1.6px 0;
    display: block;
    -webkit-margin-before: 16px;
    -webkit-margin-after: 16px;
    -webkit-margin-start: 0px;
    -webkit-margin-end: 0px;
    line-height: 34px;
    min-height:20px;
    overflow: hidden;
    margin: 0 16px;
    text-align: justify;
    color: rgba(0,0,0,.85);
    font-family: sans-serif;
    fill: currentColor;
    -webkit-tap-highlight-color: transparent;
}
.titleStyle{
	margin-left: 4px;
    font-size: 12px;
    height: 24px;
    line-height: 24px;
    border-bottom: 1px #eee dashed;
    width: 200px
}
.contextCss{
	line-height: 1.8;
    overflow: hidden;
    min-height: calc(100vh - 44px);
    margin: 0 16px;
    text-align: justify;
    font-size: 1rem;
}
.skin-default{
	background-color: #c4b395; 
}

.skin-default .page-read {
    background: url("http://image.njqityun.com/uploadImage/novel/1.jpg") no-repeat center top,url("http://image.njqityun.com/uploadImage/novel/2.jpg") no-repeat center bottom,url("http://image.njqityun.com/uploadImage/novel/3.jpg") repeat-y center 119px;
    background-size: 100%;
}
.skin-default .ye-page-read{
	color: rgba(255,255,255,.5);
    background: #1a1a1a;
}
.shouldHid{
	display: none;
}

.rmenu{
    border-bottom: 1px solid #ddd;
    padding: 2px 0px;
 	padding-left: 10px;
}	
.consultArea{
	position: fixed;
	top: 0px;
	right: -300px;
	background-color: white;
	padding-left: 4px;
	overflow: auto;
	height: 100%;
	z-index: 1055;
	min-width: 200px;
}
.activeChunk{
	background-color: #fbfbfb;
}
table {
    border-collapse: collapse;
}

th {
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
}

td {
    border: 1px solid #ddd;
    padding: 7px 10px;
    vertical-align: top;
    text-align: left;
}

pre {
    position: relative !important;
    overflow-y: hidden !important;
    overflow-x: auto !important;
    font-size: 16px !important;
    line-height: 22px !important;
    font-family: Source Code Pro, DejaVu Sans Mono, Ubuntu Mono, Anonymous Pro, Droid Sans Mono, Menlo, Monaco, Consolas, Inconsolata, Courier, monospace, PingFang SC, Microsoft YaHei, sans-serif !important;
    margin: 0 0 24px !important;
    padding: 8px 16px 6px 44px !important;
    background-color: #282C33 !important;
    border: none !important;
    white-space: pre !important;
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
    color: #abb2bf !important;
    border-right: 1px solid #c5c5c5;
    padding: 0 8px;
    list-style: none;
    margin: 0;
}

.pre-numbering li span {
    color: #fff !important;
}
</style>
</head>
<body class="skin-default">
	<!-- 	正文部分开始 -->
	<div class="textContext" style="height: 100%;">
		<input type="hidden" id="beforeMenuId" value="">
		<input type="hidden" id="nextMenu" value="">
		<input type="hidden" id="chunkId" value="">
       <div class="novelZj page-read" id="contextArea">
       </div>
       
		<div class="consultArea">
			<div align="center" style="border-bottom: 1px solid #f7caca;font-size: 16px;padding: 4px 0px;">大纲</div>
			<c:forEach items="${chunkList }" var="chunk">
				<a href="javascript:void(0)" onclick="loadChunk(${chunk.chunkId})">
					<div class="rmenu" chunkId = "${chunk.chunkId}" >${chunk.title }</div>
				</a>
			 </c:forEach>
		</div>
		<div class="leftUp">
			<div></div>
		</div>
		<div id="backBlackGround" class="modal-backdrop fade in" style="display: none;"></div>
	</div>
	<!-- 	正文部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
	<script type="text/javascript"  src="${resPath }/jsTool/customClearStyle/customClearStyle.js"></script>
  	<script type="text/javascript"  src="${resPath }/jsTool/customHtmlDecoder/customHtmlDecoder.js"></script>
  	<script type="text/javascript" src="${resPath }/zxgj/js/prettify.js"></script>
  	<script type="text/javascript" src="${resPath }/zxgj/js/grab.js"></script>
	<script type="text/javascript">
		function changeBgd(){
			if($("#contextArea").hasClass("ye-page-read")){
				$("#contextArea").removeClass("ye-page-read");
			}else{
				$("#contextArea").addClass("ye-page-read");
			}
		}
		
		function loadDoc(reviewId,chunkId,flag){
	     	$.ajax({
	     		url:"/review/getReviewDoc",
	     		data:{
	     			reviewId:reviewId,
	     			chunkId:chunkId,
	     			queryFlag:flag
	     		},
	     		async:false,
	     		type:"post",
	     		success:function(data){
	     			if(data != null && data != ""){
		     			var cd=new CustomDecoder();
				     	cd.str=data.text;
				     	var chd=new customHtmlDecoder();
				     	var context = "<div align='left'>";
				     	if(data.title){
				     		context += "<div class='titleStyle'>"+data.index+"."+data.title+"</div>";
				     	}
					    context +="</div>"+
					    "<div  class='contextCss' chunkId="+data.chunkId+" >"+chd.decode(cd.decode(),{ispre:false,tranType:2,spaceType:4})+"</div><div class='bottomDiv'></div>";
				     	if(flag){
					     	$("#beforeMenuId").val(data.beforeMenuId);
				     		$("#contextArea").prepend(context);
				     	}else{
					     	$("#contextArea").append(context);
					     	$("#nextMenu").val(data.afterMenuId);
				     	}
				     	if(!reviewId){
				     		$("#beforeMenuId").val(data.beforeMenuId);
				     		$("#nextMenu").val(data.afterMenuId);
				     	}
				     	if(data.chunkId != $("#chunkId").val()){
					     	$("#chunkId").val(data.chunkId);
					     	setActivityChunk(data.chunkId);
				     	}
				     	decodePre();
	     			}else{
	     				$("#contextArea").append("<div align='center'><h3>文章不存在，或正在加载中，请稍后....</h3></div>");
	     			}
	     		}
	     	})
     	}
		function setActivityChunk(chunkId){
			$(".rmenu").removeClass("activeChunk");
			$.each($(".rmenu"),function(a,b){
				if($(b).attr("chunkId") == chunkId){
					$(b).addClass("activeChunk");
				}
			})
		}
		function loadChunk(chunkId){
			$("#contextArea").html("");
			loadDoc(null,chunkId);
			rightmove();
		}
	  	$(function(){
	     	var flag=true;
			$(window).scroll(function(){
				//滚动条距顶部距离(页面超出窗口的高度)  
		        var srollPos = $(window).scrollTop();    
		        //整个页面到高度
		        var docheight=$(document).height();
		        //滚动条到高度
		        var rollheight=$(window).height();
		        //到底部距离为总高度减去滚动条高度和滚动条到顶部到高度
		        var leftHeight=  docheight-rollheight-srollPos;
	        	if(flag){
	        		if(srollPos == 0){
	        			if($("#beforeMenuId").val()){
		        			flag = false;
							loadDoc($("#beforeMenuId").val(),$("#chunkId").val(),1);
							flag = true;
	        			}
	        		}else if(leftHeight<100){
	        			if($("#nextMenu").val()){
				     		flag = false;
							loadDoc($("#nextMenu").val(),$("#chunkId").val());
							flag = true;
		        		}
			       	 }
		     	}
	        	resetCurChunk();
		    });
	  	});
	  	
	  	
	$(function(){
	    touch.on(".textContext", "swipeleft", function(ev){
	    	if(ev.target.tagName != "PRE" && $(ev.target).parents("pre").length == 0){
				leftmove();
	    	}
		});
    	touch.on(".textContext", "swiperight", function(ev){
			rightmove();
		});
    	touch.on(".textContext", "swipedown", function(ev){
			downmove();
		});
    	//双击切换背景色
	    touch.on(".skin-default", "doubletap", function(ev){
	    	changeBgd();
		 });
    })
   	function leftmove(){
   		animate($(".consultArea")[0],"right","0");
   		animate($(".leftUp")[0],"left","0");
   		$("#backBlackGround").show();
   	}
   	function rightmove(){
   		animate($(".consultArea")[0],"right","-300");
   		animate($(".leftUp")[0],"left","-300");
   		$("#backBlackGround").hide();
   	}
 	
   	function resetCurChunk(){
   		var scroTop = $(window).scrollTop();
   		var wheight = $(window).height();
   		$.each($(".contextCss"),function(a,b){
   			var bt = b.offsetTop - scroTop; 
   			if(bt>0&&bt<wheight/3){
   				if($(b).attr("chunkId") != $("#chunkId").val()){
	   				$(".rmenu").removeClass("activeChunk");
					$(".rmenu[chunkId="+$(b).attr("chunkId")+"]").addClass("activeChunk");
					$("#chunkId").val($(b).attr("chunkId"));
				}
   			}
   		})
   	}
   	
   	function downmove(){
   		var srollPos = $(window).scrollTop();    
   		if(srollPos < 20){
   			if($("#beforeMenuId").val()){
				loadDoc($("#beforeMenuId").val(),$("#chunkId").val(),1);
     		}
   		}
   	}
 
   	function animate(obj, attr, target) {
        // 防止连续移入元素会生成多个计时器，所以进入之前先清除
        clearInterval(obj.timer);
        obj.timer = setInterval(function() {
            // 属性当前值
            var icur = parseInt(getStyle(obj, attr));
            // 动画的速度
            var speed = (target - icur) / 2;
            speed = speed > 0 ? Math.ceil(speed) : Math.floor(speed);
            if (icur == target) {
                clearInterval(obj.timer);
            } else {
                obj.style[attr] = icur + speed + 'px';
            }
        }, 30);
    }
    
    function getStyle(obj, attr) {
        if (obj.currentStyle) {
            return obj.currentStyle[attr];
        } else {
            return getComputedStyle(obj, false)[attr];
        }
    }
    </script>
</body>
</html>