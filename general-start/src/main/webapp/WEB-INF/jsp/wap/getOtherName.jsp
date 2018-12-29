<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>网站介绍</title>
<jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
<script type="text/javascript">
	function getName(){
		var check=0;
		if($("#isCheck").prop("checked")){
			check=1;
		}
		$.ajax({
			url:"${path}/wap/getOtherName",
			data:{
				type:$("#type").val(),
				type2:$("#type2").val(),
				check:check
			},
			type:"post",
			success:function(data){
				if(check==1){
					$("#name").html(data.name+$("#type2").find("option:selected").text());
				}else{
					$("#name").html(data.name);
				}
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
			<div align="left" style="background-color: #BEDAFF;"><span style="margin-left: 20px;height: 30px;line-height: 30px;"><a href="${path}/wap/toSelectName">>>>为${typeName}取名</a></span></div>
			<div style="margin-top: 40px;">
			<input type="hidden" id="type" value="${type}" />
				<span>
					选择类型：<select id="type2" name="type2">
						<c:forEach items="${codeList}" var="code" >
							<option value="${code.id}">${code.name}</option>
						</c:forEach>
					</select>
				</span>
				<c:if test="${type!=184}">
					<span style="margin-left: 20px;" >
						随机取名：<input type="checkbox" id="isCheck"  />
					</span>
				</c:if>
			</div>
		</div>
		<div style="margin-top: 30px;"><div id="name" style="border-bottom: 1px solid black;width: 200px;font-size: 20px;height: 26px;"></div></div>
		<div style="margin-right: 20px;margin-top: 40px;">
			<input type="button" style="cursor: pointer;height: 30px;width: 70px;color: black;background-color: white;font-size: 12px;border: 1px solid #ccc;" value="取名" onclick="getName()">
		</div>
	</div>
	<!-- 	正文部分结束 -->

	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
</body>
</html>