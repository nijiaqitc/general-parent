<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公用顶部菜单</title>
<script type="text/javascript">
	var _vds = _vds || [];
	window._vds = _vds;
	(function(){
	  _vds.push(['setAccountId', 'a5c2fa961b6d17da']);
	  (function() {
	    var vds = document.createElement('script');
	    vds.type='text/javascript';
	    vds.async = true;
	    vds.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'dn-growing.qbox.me/vds.js';
	    var s = document.getElementsByTagName('script')[0];
	    s.parentNode.insertBefore(vds, s);
	  })();
	})();



	var jspath="${path }";
</script>
</head>
<body>
<div align="left" class="topDiv">
    <ul class="topUl">
        <li><span class="topFont">QQ:2439794916</span></li>
        <li class="weili"><span class="topFont"><img alt="琦三叔微信" id="weixinImg"  src="${resPath }/tbk/images/weixin.png" > </span></li>
    </ul>
    <c:if test="${user==null }"><div align="right" ><a href="${path}/login.jsp" class="aloginButton1" >登陆</a></div></c:if>
    <c:if test="${user!=null }"><div align="right" class="aloginButton2" >欢迎:${user.userName }</div></c:if>
</div>
<div id="wechat" class="popover" >
	<div>
		<img alt="琦三叔二维码"  class="erwei" src="${resPath }/tbk/images/jiantou.png">
	</div>
	<div class="ma">
		<p>扫码微信关注琦三叔</p>
		<img alt="琦三叔二维码"  src="${resPath }/tbk/images/weixin.jpg">
	</div>
</div>
<ul class="commonUl">
	<li><a href="#" class="helpTip" ></a></li>
</ul>
</body>
</html>