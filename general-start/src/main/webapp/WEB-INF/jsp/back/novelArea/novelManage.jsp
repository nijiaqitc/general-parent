<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小说管理</title>
<jsp:include page="${path}/commonTopLink"></jsp:include>
</head>
<body>
	<!-- 通用顶部 -->
	<jsp:include page="${path}/commonTop"></jsp:include>
	<!-- 中下部分 -->
	<div id="centerPlace">
		<!-- 通用左边菜单 -->	
		<jsp:include page="${path}/commonLeft"></jsp:include>
		<!-- 正文区域 -->
		<div id="rightContext">
			<div class="menu-box barAreaDiv" >
				<div class="barTopw">
					<h2 class="barLeftDiv">
                        <i class="icon-ambulance leftLogo"></i> 小说展示
                    </h2>
				</div>
			</div>
		     <c:forEach items="${list }" var="doc">
	             <div class="menu-box" style="width: 150px;height: 220px;background-color: white;float: left;margin-left: 30px;margin-top: 20px;">
			         <a href="novelTitleList?docId=${doc.id }">
				         <div style="width: 100%;height: 200px;clear: both;">
				         <div style="width: 30px;font-size: 18px;margin-left: 10px;padding-top: 10px;">
					         ${doc.title}
				         </div>
				         </div>
			         </a>
				     <div style="width: 100%;font-size: 12px;overflow: auto;">
				         <i class="icon-heart nnum1i" style="color: red;line-height: 20px;margin-right: 4px;margin-left: 10px;"></i><span style="float: left;">(1000)</span>
				         <i class="icon-thumbs-down nnum1i" style="color: red;line-height: 20px;margin-right: 4px;margin-left: 10px;"></i><span style="float: left;">(1000)</span>
				         <i class="icon-eye-open nnum1i" style="color: red;line-height: 20px;margin-right: 4px;margin-left: 10px;"></i><span style="float: left;">(1000)</span>   
				     </div>
			    </div>
		     </c:forEach>
		</div>
		<!-- 通用底部 -->	
		<jsp:include page="${path}/commonBottom"></jsp:include>
	</div>
</body>
<jsp:include page="${path}/commonBottomLink"></jsp:include>
</html>