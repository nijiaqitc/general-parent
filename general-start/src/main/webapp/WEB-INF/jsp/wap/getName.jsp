<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>网站介绍</title>
<jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
<script type="text/javascript">
	function getName(){
		$.ajax({
			url:"${path}/wap/getName",
			data:{
				type1:$("#xname").val(),
				type2:$("#yname").val(),
				namea:$("#custNameA").val(),
				nameb:$("#custNameB").val()
			},
			type:"post",
			success:function(data){
				$("#name").html(data.name);
			}
		})		
	}
	
	function delName(){
		$.ajax({
			url:"${path}/wap/delName",
			data:{
				name:$("#delName").val()
			},
			type:"post",
			success:function(data){
				$("#name").html(data.name);
			}
		})		
	}
</script>
</head>
<body>
	<!-- 	顶部div开始 -->
	<jsp:include page="${resPath }/wap/commonwap/top.jsp"></jsp:include>
	<!-- 	顶部div结束 -->
	
	<!-- 	正文部分开始 -->
	<div class="textContext" align="center">
		<div style="margin-top: 50px;">
			<div>
				姓氏：<select id="xname" name="xname">
					<option value="0">任意</option>
					<option value="1">单姓</option>
					<option value="2">复姓</option>
				</select>
				名字：<select id="yname" name="yname">
					<option value="0">任意</option>
					<option value="1">单字</option>
					<option value="2">双字</option>
				</select>
			</div>
			<div style="margin-top: 20px;">
				自定义姓：<input type="text" id="custNameA" name="custNameA" style="width: 50px;">
				自定义名：<input type="text" id="custNameB" name="custNameB" style="width: 50px;">
			</div>
			<div align="right" style="margin-right: 20px;margin-top: 20px;"><input type="button" value="取名" onclick="getName()"></div>
		</div>
		<div style="margin-top: 30px;"><div id="name" style="border-bottom: 1px solid black;width: 200px;font-size: 20px;height: 26px;"></div></div>
		<div style="margin-top: 250px;"><input type="text" id="delName" name="delName"> <input type="button" id="delbtn" onclick="delName()" value="删除">  </div>
	</div>
	<!-- 	正文部分结束 -->

	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
</body>
</html>