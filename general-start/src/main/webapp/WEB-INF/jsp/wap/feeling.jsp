<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>站长说说</title>
<jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
<style type="text/css">
	.select{
		background-color: #fd9595;width: 60px;display: block;text-align: center;float: left;
	}
	.unselect{
		background-color: #fff;width: 60px;display: block;text-align: center;float: left;	
	}
</style>
<script type="text/javascript">
	function clickspan(e){
		if(!e.classList.contains("select")){
			e.classList.remove("unselect");
			e.classList.add("select");
			if(e.previousElementSibling){
				e.previousElementSibling.classList.remove("select");
				e.previousElementSibling.classList.add("unselect");
			}
			if(e.nextElementSibling){
				e.nextElementSibling.classList.remove("select");
				e.nextElementSibling.classList.add("unselect");
			}
			if(e.getAttribute("name")=="think"){
				$("#topInfo").hide();
			}else{
				$("#topInfo").show();	
			}
		}else{
			return
		}
	}
	
	function save(){
		$.ajax({
			url:"${path}/feel/addFeel",
			type:"post",
			data:{
				feelType:$("#feelType").val(),
				textType:$(".select")[0].getAttribute("value"),
				place:$("#place").val(),
				text:$("#mytext").val(),
				isLock:$("#isLock").val()
			},
			success:function(data){
				if(data.state=="1"){
					alert("发表成功！");
				}else{
					alert(data.message);
				}
			}
		})
	}
	
	function reset(){
		$("#place").val("");
		$("#mytext").val("");
	}
</script>
</head>
<body>
	<!-- 	顶部div开始 -->
	<jsp:include page="${resPath }/wap/commonwap/top.jsp"></jsp:include>
	<!-- 	顶部div结束 -->
	
	<!-- 	正文部分开始 -->
	<div class="textContext" align="center">
		<div align="center" style="width: 300px;margin-top: 50px;">
			<div align="left">
				<span class="select" onclick="clickspan(this)" name="feel" value="1" >心情篇</span>
				<span class="unselect" style="margin-left: 10px;" name="think" onclick="clickspan(this)" value="2">语录篇</span>
			</div>
			<div style="clear: both;border:1px solid #fd9595;border-bottom: 0px;">
				<div id="topInfo">
					<div style="margin-top: 5px;">
						心情：<select id="feelType" name="feelType">
							<c:forEach items="${codeList}" var="code" >
								<option value="${code.id}">${code.name}</option>
							</c:forEach>
						</select>
						加密：<select id="isLock" name="isLock">
							<option value="1">是</option>
							<option value="2">否</option>
						</select>
					</div>
					<div style="margin-top: 20px;">
						<div>
							发表地点：<input type="text" id="place" name="place" />
						</div>
					</div>
				</div>
				<div style="margin-top: 20px;">
					<textarea style="width: 250px;height: 200px;" id="mytext"></textarea>
				</div>
				<div>
					<input type="button" name="reset" value="重置" style="width: 60px;height: 30px;" onclick="reset()" />
					<input type="button" name="reset" value="保存" style="width: 60px;height: 30px;margin-left: 20px;" onclick="save()" /> 
				</div>
			</div>
		</div>
	</div>
	<!-- 	正文部分结束 -->
	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
</body>
</html>