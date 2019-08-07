<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>网站介绍</title>
<jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
<style>
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
    font-size: 29px;
    height: 54px;
    line-height: 54px;
    font-weight: 400;
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
</style>
</head>
<body class="skin-default">
	<!-- 	正文部分开始 -->
	<div class="textContext">
       <div class="novelZj page-read" id="contextArea">
			<input type="hidden" id="beforeMenuId" value="${beforeMenuId }">
			<input type="hidden" id="nextMenu" value="${menuId }">
       </div>
	</div>
	<!-- 	正文部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
	<script type="text/javascript"  src="${resPath }/jsTool/customClearStyle/customClearStyle.js"></script>
  	<script type="text/javascript"  src="${resPath }/jsTool/customHtmlDecoder/customHtmlDecoder.js"></script>
	<script type="text/javascript">
		function changeBgd(){
			if($("#contextArea").hasClass("ye-page-read")){
				$("#contextArea").removeClass("ye-page-read");
			}else{
				$("#contextArea").addClass("ye-page-read");
			}
		}
		
		
	  	$(function(){
	     	loadDoc("${menuId}");
	     	function loadDoc(menuId,flag){
		     	$.ajax({
		     		url:"/novel/getNovelDoc",
		     		data:{
		     			menuId:menuId
		     		},
		     		async:false,
		     		type:"post",
		     		success:function(data){
		     			if(data != null){
			     			var cd=new CustomDecoder()
					     	cd.str=data.text;
					     	var chd=new customHtmlDecoder();
					     	var context = "<div align='center'>"+
							   	"<h3 class='titleStyle'>"+data.title+"</h3>"+
							   "</div>"+
							   "<div  class='contextCss'>"+chd.decode(cd.decode(),{ispre:true,tranType:2,spaceType:4})+"</div><div class='bottomDiv'></div>";
					     	if(flag){
					     		$("#contextArea").prepend(context);
					     	}else{
						     	$("#contextArea").append(context);
					     	}
					     	$("#beforeMenuId").val(data.beforeMenuId);
					     	$("#nextMenu").val(data.afterMenuId);
		     			}
		     		}
		     	})
	     	}
	     	
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
							loadDoc($("#beforeMenuId").val(),1);
							flag = true;
	        			}
	        		}else if(leftHeight<100){
	        			if($("#nextMenu").val()){
				     		flag = false;
							loadDoc($("#nextMenu").val());
							flag = true;
		        		}
			       	 }
		     	}
		        
		    });
		    
		    
		    //双击切换背景色
		    touch.on(".skin-default", "doubletap", function(ev){
		    	changeBgd();
			 });
	  	});
	  	
	  	
    </script>
</body>
</html>