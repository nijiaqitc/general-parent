<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>左边菜单</title>
<script src="${resPath }/back/js/jquery-1.10.2.min.js"></script>
<!-- <script type="text/javascript">
	var aaa="${powerList }";
	$(document).ready(function(){
		for(var i=0;i<"${powerList.size() }";i++){
		}
	})
</script> -->
</head>
<body>
	<div id="sidebar-left" class="span2">
		<!-- <div class="row-fluid actions">
			<input type="text" class="search span12" placeholder="..." />
		</div>	 -->
		<div class="nav-collapse sidebar-nav">
			<ul class="nav nav-tabs nav-stacked main-menu">
				<c:forEach items="${powerList }" var="power"  >
					<c:if test="${power.children.size()>0}">
						<li>
							<a class="dropmenu" href="javascript:void(0)"><i class="${power.icon}"></i><span class="hidden-tablet">${power.name}</span> <span class="label">${power.children.size() }</span></a>
							<ul>
								<c:forEach items="${power.children}" var="tree">
									<li><a class="submenu"  href="${path}/${tree.url}"><i style="margin-left: 10px;" class="${tree.icon}"></i><span class="hidden-tablet"> ${tree.name}</span></a></li>
								</c:forEach>
							</ul> 
						</li>
					</c:if>
					<c:if test="${power.children.size()==0}">
						<c:if test="${power.url=='#'}">
							<li><a href="javascript:void(0)"><i class="${power.icon}"></i><span class="hidden-tablet"> ${power.name} </span></a></li>
						</c:if>
						<c:if test="${power.url!='#'}">
							<li><a href="${path}/${power.url}"><i class="${power.icon}"></i><span class="hidden-tablet"> ${power.name} </span></a></li>
						</c:if>
					</c:if>
				</c:forEach> 
			</ul>
		</div>
	</div>
</body>
</html>