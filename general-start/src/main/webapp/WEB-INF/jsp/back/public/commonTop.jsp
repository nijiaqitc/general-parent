<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<div class="top">
	<div class="fd" id="customFull" align="center" onclick="customfull()">后台系统</div>
	<div class="td" id="recover"  align="center" onclick="recover(this,1)">后台系统</div>
	<div class="fod">
		<ul class="topPic">
			<li>
				<a href="javascript:void(0)">
					<c:if test="${user.picPlace!=null }">
						<div><img id="topPic" src="${user.picPlace}" alt="头像" /></div>
					</c:if>
					<c:if test="${user.picPlace==null }">
						<div><img id="topPic" src="${resPath }/back/img/avatar.jpg" alt="头像" /></div>
					</c:if>
				</a>
			</li>
			<li class="tli">用户昵称</li>
			<li class="tli"><a href="javascript:void(0)" onclick="loginOut()">退出</a></li>
		</ul>
	</div>
</div>