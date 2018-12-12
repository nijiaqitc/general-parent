<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>html代码格式化</title>
<jsp:include page="../tbk/jspdemo/common.jsp"></jsp:include>
<style type="text/css">
	textarea{
		width:100%;
		height: 640px;
		background: white;
		overflow: auto;
	    vertical-align: top;
	    outline: none;
	    resize: none;
	    font-size: 12px;
	    margin-top: 10px;
	}
	
	.startBtn{
		cursor: pointer;
	    width: 80px;
	    height: 30px;
	    color: white;
	    background-color: #E12422;
	    font-size: 12px;
	    border: 0;
	    float: right;
	    margin-top: 10px;
	}
	select{
		border-radius: 4px;
	    font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
	    background-color: #fff;
	    border: 1px solid #ccc;
	    height: 30px;
	    line-height: 30px;
	    margin-top: 10px;
	    float: left;
	    margin-left: 10px;
	}
</style>
<script type="text/javascript" src="${resPath }/tbk/js/jquery.min.js"></script>
<script type="text/javascript" src="${resPath }/jsTool/custom/js/customHtmlDecoder.js"></script>
<script type="text/javascript">
	function decode(){
		var chd=new customHtmlDecoder();
		var ispre=1;
		if($("#isPre").val()==1){
			ispre=false;
		}else{
			ispre=true
		}
		$("#decodeAfter").val(chd.decode($("#decodeBefore").val(),{ispre:ispre,tranType:$("#tranType").val(),spaceType:$("#spaceType").val()}));
	}
</script>
</head>
<body>
	<div align="center" class="menu-box" style="width: 100%;height: 910px;margin-top: 60px;">
		<div style="width: 1250px;height: 100%;">
			<div style="width: 45%;float: left;margin-left: 40px;">
				<div>
					<select id="isPre">
						<option value="1">pre标签不格式</option>
						<option value="2">pre标签格式</option>
					</select>
					<select id="tranType">
						<option value="1">内结束标签换行</option>
						<option value="2">内结束标签不换行</option>
					</select>
					<select id="spaceType">
						<option value="1">缩进4空格</option>
						<option value="2">缩进8空格</option>
						<option value="3">缩进16空格</option>
						<option value="4">缩进1Tab</option>
					</select>
				</div>
				<textarea id="decodeBefore"><html><head><title></title></head><body></body></html></textarea>
			</div>
			<div style="width: 45%;float: left;margin-left: 20px;">
				<div>
					<input type="button" onclick="decode()" class="startBtn" value="进行格式化">
				</div>
				<textarea id="decodeAfter"></textarea>
			</div>
			<div style="width: 20px;display: block;height: 20px;clear: both;"></div>
			<div style="margin-left: -20px;border: 1px solid rgb(169,169,169);width: 1150px;" align="left">
				<div style="font-size: 12px;font-weight: 600;margin-left: 10px;">格式化说明：</div>
				<div style="text-indent: 24px;font-size: 12px;">
					<p>目前只针对html代码格式化，但并<em>非</em>支持html所有标签，以下几种标签将被忽略：</p>
					<p>&nbsp;&nbsp;<em>script</em>标签、<em>style</em>标签、<em>textarea</em>标签</p>
					<p>若需要格式化的代码中<em>包含</em>以上标签，那么在格式化后这些标签将放在<em>第一位</em>，只需简单的<em>手动</em>调整即可。</p>
					<p>对于pre标签中的代码格式化，目前只支持pre中<em>含标签</em>的代码格式化，若pre标签中放的是js、css等代码，此格式化依旧不支持，此格式化非常简单易上手。</p>
					<p>谢谢您的使用!</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>