<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>网站介绍</title>
<jsp:include page="../../../wap/commonwap/common.jsp"></jsp:include>
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
    margin: 22px 10px 10px 10px;
    font-weight: 400;
}
</style>
<script type="text/javascript"> 
function userOp(op,titleId){
	var userId=document.getElementById("userId");
	$.ajax({
		url:"${path}/novel/userOp",
		type:"post",
		data:{
			op:op,
			appId:1,
			docId:titleId,
			userId:userId.value
		},
		success:function(data){
			
		}
	})
} 
</script>
</head>
<body>
    <input type="hidden" id="userId" value="1"/>
	<!-- 	正文部分开始 -->
	<div class="textContext">
       <!-- 	http://m.qidian.com/book/2952453/47370768 -->
	   <div style="width: 70px;position: fixed;background-color: red;right: 0px;top: 0px;text-align: center;height: 22px;">
	   		<input type="button" onclick="userOp(2,${novel.docId})" value="赞" />
	   		<input onclick="userOp(3,${novel.docId})" type="button" value="踩" />
	   </div>
       <div class="novelZj">
		   <div align="center"><h3 class="titleStyle">${novel.title}</h3></div>
		   <div align="center" style="margin-bottom: 10px;font-size: 12px;">赞(${novel.good})|踩(${novel.down})|浏览(${novel.view})</div>
<!-- 		   <div>${docUp}||||${docDown }</div> -->
		   <div>${novel.text}</div>
       </div>
	</div>
	<!-- 	正文部分结束 -->

	<!-- 	底部通用部分开始 -->
	<jsp:include page="../../../wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="../../../wap/commonwap/commonBottom.jsp"></jsp:include>
	<script type="text/javascript" src="${resPath }/wap/js/readDoc.js"></script>
</body>
</html>