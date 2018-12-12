<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<div id="leftMenu">
	<div class="fd" align="center">
		<ul class="wmenu">
			<c:forEach items="${powerList }" var="power"  >
				<c:if test="${power.children.size()>0}">
					<li title="${power.name}">
						<a onclick="shownmenu(this)" href="javascript:void(0)"><label>${power.children.size() }</label><i class="${power.icon} nnum2i"></i><span class="menuName">${power.name}</span> <span class="nnum">${power.children.size() }</span></a>
						<ul class="nmenu">
							<c:forEach items="${power.children}" var="tree">
								<li title="${power.name}"><a href="${path}/${tree.url}"><i class="${tree.icon} nnum1i"></i><span class="menuName">${tree.name}</span></a></li>
							</c:forEach>
						</ul> 
					</li>
				</c:if>
				<c:if test="${power.children.size()==0}">
					<c:if test="${power.url=='#'}">
						<li title="${power.name}"><a href="javascript:void(0)"><i class="${power.icon} nnum1i"></i><span class="menuName"> ${power.name} </span></a></li>
					</c:if>
					<c:if test="${power.url!='#'}">
						<li title="${power.name}"><a href="${path}/${power.url}"><i class="${power.icon} nnum1i"></i><span class="menuName"> ${power.name} </span></a></li>
					</c:if>
				</c:if>
			</c:forEach>
		</ul>
	</div>
</div>