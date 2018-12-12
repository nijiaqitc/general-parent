<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>[小说阅读]－${doc.title }</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/zxgj/css/novelRead.css">
<link href="${resPath }/common/css/font-awesome.min.css" rel="stylesheet" />
</head>
<body>
	<!--     开始：顶部菜单栏-->
    <jsp:include page="top.jsp"></jsp:include>
    <div style="height: 20px;width: 100%;background-color: #ec8316;"></div>
    <!--     结束：顶部菜单栏 -->
    <div class="contextAare" align="center" >
		<div class="menu-box outDiv" align="center" >
			<div>
				<h1 class="topTitle">第${pn.cn.titleIndex}章&nbsp;&nbsp;&nbsp;&nbsp;${doc.title }</h1>
				<div align="center">
					<div class="topTitleBt">
						<span class="geninfo"><span class="icon-thumbs-down"></span> 1000</span>
						<span class="geninfo"><span class=" icon-thumbs-up"></span> 1000</span>
						<span class="geninfo"><span class="icon-pencil"></span> ${doc.fontNum } 字</span>
						<span class="geninfo"><span class="icon-time"></span> ${doc.formatCreateDate }</span>
					</div>
				</div>
			</div>
			<div align="center">
				<div align="left" class="contextText">
					${doc.doc }
				</div>
			</div>
			<div class="bottomArea">
				<c:if test="${pn.pre!=null}">
					<div class="pnbtn"><a href="${path }/zxgj/novelRead/${pn.pre}">上一章</a></div>
				</c:if>
				<c:if test="${pn.pre==null}">
					<div class="unbtn">上一章</div>
				</c:if>
				<div class="pnbtn"><a href="${path }/yxl/novelList">目录</a></div>
				<c:if test="${pn.next!=null}">
					<div><a href="${path }/zxgj/novelRead/${pn.next}">下一章</a></div>
				</c:if>
				<c:if test="${pn.next==null}">
					<div class="uub">下一章</div>
				</c:if>
			</div>
		</div>
    </div>
    <!--     开始：顶部菜单栏-->
	<jsp:include page="bottom.jsp"></jsp:include>
    <!--     结束：顶部菜单栏 -->
    <script type="text/javascript" src="${resPath }/jquery/jquery.min.js" ></script>
    <script type="text/javascript" src="${resPath }/zxgj/js/common.js" ></script>
</body>
</html>