<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>首页</title>
<link href="index.css" type="text/css" rel="stylesheet">
<script src="banner/js/jquery.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="css/index.css" type="text/css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/zzsc.js"></script>
<style type="text/css">
	.aaa{
		font-size: 30px;
		color: red;
	}
	.bbb{
		font-size: 30px;
		color:blue;
	}
</style>
</head>
<body>
    <!--     开始：顶部联系方式 -->
    <div class="menu-box topMenu"  align="center">
        <div style="width: 1000px;" align="left">
            <ul class="topUl">
                <li><span class="topFont">QQ:12345678</span></li>
                <li style="margin-left: 20px;"><span class="topFont">QQ:12345678</span></li>
            </ul>
        </div>
    </div>
    <!--     结束：顶部联系方式 -->
    <!--     开始：导航条 -->
    <div style="height: 30px;" ></div>
    <div class="menu-box" align="center" style="margin-top: 10px;height: 150px;">
    	<jsp:include page="navigate.jsp"></jsp:include>
    </div>
    <!--     结束：导航条 -->
    <!--     开始：最新文章区域 -->
    <div align="center" style="height: 620px;clear: both;">
    	<!-- <div>
    		<span class="aaa">aaaaaaaaaa</span>
    		$(".bbb").removeClass("bbb").addClass("aaa");
    	</div> -->
	    <div style="margin-top: 20px;">
	    	<img style="width: 800px;height: 600px;" src="images/53bd1576b3605.jpg" alt="" >
	    </div>
    </div>
    <!--     开始：页面底部 -->
    <div style="clear:both; height: 50px;"></div>
    <div align="center" class="menu-box" style="clear:both; height: 100px;">
    	<div style="width: 100%;height: 20px;background-color: #555"></div>
    	<div style="margin-top: 10px;"><span style="font-size: 14px;">浙ICP备16006900号</span></div>
    	<div><span style="font-size: 14px;">若有疑问请及时联系站长，QQ:2439794916 微信：qisanshu</span></div>
    </div>
    <!--     结束：页面底部  -->
    <script src="index.js" type="text/javascript"></script>
    <script type="text/javascript">
    	window.onresize=function(){
	    	/* console.info($(window).width()) */
    	}
    </script>
</body>
</html>