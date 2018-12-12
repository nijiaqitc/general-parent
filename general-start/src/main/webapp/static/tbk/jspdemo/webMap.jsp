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
    <div align="center" style="height: 400px;clear: both;">
    	<div align="center" style="width: 1190px;">
    		<div align="center" class="menu-box" style="height: 400px;margin-top: 20px;width: 280px;float: left;display: inline;">
    			<div align="left" style="margin: 20px 20px;">
    				<div style="font-size: 30px;font-weight: bold;">首页</div>
    				<div style="font-size: 14px;line-height: 20px;margin-top: 20px;text-indent: 28px;">
    					展示banner、类型、最新发表文章、文章分类
    				</div>
    			</div>
    		</div>
    		<div align="center" class="menu-box" style="height: 400px;margin-top: 20px;margin-left:10px;width: 280px;float: left;display: inline;">
    			<div align="left" style="margin: 20px 20px;">
    				<div style="font-size: 30px;font-weight: bold;">文章搜索</div>
    				<div style="font-size: 14px;line-height: 20px;margin-top: 20px;text-indent: 28px;">
    					根据标题、标签、文章内容快速检索出文章（左右两边暂时啥都不放，有空在两边放搜索排行等）
    				</div>
    			</div>
    		</div>
    		<div align="center" class="menu-box" style="height: 190px;margin-top: 20px;margin-left:10px;width: 280px;float: left;display: inline;">
    			<div align="left" style="margin: 20px 20px;">
    				<div style="font-size: 30px;font-weight: bold;">技术成长</div>
    				<div style="font-size: 14px;line-height: 20px;margin-top: 20px;text-indent: 28px;">
    					暂时就放一张图片。等有空把此栏目换成小工具，显示各种小工具列表如json格式转换、xml格式转换等
    				</div>
    			</div>
    		</div>
    		<div align="center" class="menu-box" style="height: 190px;margin-top: 20px;margin-left:10px;width: 280px;float: left;display: inline;">
    			<div align="left" style="margin: 20px 20px;">
    				<div style="font-size: 30px;font-weight: bold;">图片墙</div>
    				<div style="font-size: 14px;line-height: 20px;margin-top: 20px;text-indent: 28px;">
    					显示一堆收集的该显示的图片
    				</div>
    			</div>
    		</div>
    		<div align="center" class="menu-box" style="height: 200px;margin-top: 10px;margin-left:10px;width: 280px;float: left;display: inline;">
    			<div align="left" style="margin: 20px 20px;">
    				<div style="font-size: 30px;font-weight: bold;">网站导航</div>
    				<div style="font-size: 14px;line-height: 20px;margin-top: 20px;text-indent: 28px;">
    					风格不怎么好看，以后再改版。暂时没思路
    				</div>
    			</div>
    		</div>
    		<div align="center" class="menu-box" style="height: 200px;margin-top: 10px;margin-left:10px;width: 280px;float: left;display: inline;">
    			<div align="left" style="margin: 20px 20px;">
    				<div style="font-size: 30px;font-weight: bold;">关于站长</div>
    				<div style="font-size: 14px;line-height: 20px;margin-top: 20px;text-indent: 28px;">
    					介绍站长嘻嘻
    				</div>
    			</div>
    		</div>
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