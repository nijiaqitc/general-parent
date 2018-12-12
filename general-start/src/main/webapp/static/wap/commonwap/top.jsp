<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<script type="text/javascript">
	var jspath="${path }";
</script>
</head>
<body>
	<div class="header menu-box" style="position: absolute;"><div class="topleft"></div> <div class="topright"><a class="topright" href="${authUrl}?ismob=true">电脑版</a></div></div>
	<div class="catalog-button" id="J-catalog-button" onclick="goToTop()" style="display: none;">
		<div style="font-size: 20px;margin-top: 0px;">↑</div>
	</div> 
	<!-- <div id="J-gotop" class="gotop fixed" style="display: block; transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
		<div style="background-image: url(../wap/images/tip.gif);"></div>
	</div> -->
</body>
</html>