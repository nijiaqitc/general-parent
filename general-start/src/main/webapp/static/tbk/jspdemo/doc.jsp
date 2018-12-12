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
	
	.posted{
		letter-spacing: 1px;
	    font-size: 8pt;
	    color: #A2A2A2;
	}
	.postedMsg{
		float: left;
    	margin-right: 20px;
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
    <div align="center" style="clear: both;">
    	<div style="width: 1190px;">
    		<div align="left" class="menu-box" style="margin-top: 20px;width: 360px;float: left;display: inline;">
    			<div style="font-size: 1.4em;font-weight: bold;height: 80px;">
    				<p style="margin-top: 28px;margin-left: 20px;">推荐文章</p>
    			</div>
    			<ul style="width: 360px;margin-left: 20px;">
					<li style="height: 140px;margin: 0;">
						<p style="padding: 0em 0em 1em 0em;letter-spacing: 1px;text-transform: uppercase;font-size: 8pt;color: #A2A2A2;">August 11, 2002  |  (10 )  Comments</p>
						<img style="clear: both;float: left;margin-right: 20px;" src="images/pic04.jpg" alt="" />
						<p style="margin-bottom: 1em">Nullam non wisi a sem eleifend. Donec mattis libero eget urna. Pellentesque viverra enim.</p>
					</li>
					<li style="height: 140px;margin: 0;padding: 1em 0em 1.5em 0em;border-top: 1px solid #ECECEC;">
						<p style="padding: 0em 0em 1em 0em;letter-spacing: 1px;text-transform: uppercase;font-size: 8pt;color: #A2A2A2;">August 11, 2002  |  (10 )  Comments</p>
						<img style="clear: both;float: left;margin-right: 20px;" src="images/pic05.jpg" alt="" />
						<p style="margin-bottom: 1em">Nullam non wisi a sem eleifend. Donec mattis libero eget urna. Pellentesque viverra enim.</p>
					</li>
					<li style="height: 140px;margin: 0;padding: 1em 0em 1.5em 0em;border-top: 1px solid #ECECEC;">
						<p style="padding: 0em 0em 1em 0em;letter-spacing: 1px;text-transform: uppercase;font-size: 8pt;color: #A2A2A2;">August 11, 2002  |  (10 )  Comments</p>
						<img style="clear: both;float: left;margin-right: 20px;" src="images/pic06.jpg" alt="" />
						<p style="margin-bottom: 1em">Nullam non wisi a sem eleifend. Donec mattis libero eget urna. Pellentesque viverra enim.</p>
					</li>
					<li style="height: 140px;margin: 0;padding: 1em 0em 1.5em 0em;border-top: 1px solid #ECECEC;">
						<p style="padding: 0em 0em 1em 0em;letter-spacing: 1px;text-transform: uppercase;font-size: 8pt;color: #A2A2A2;">August 11, 2002  |  (10 )  Comments</p>
						<img style="clear: both;float: left;margin-right: 20px;" src="images/pic05.jpg" alt="" />
						<p style="margin-bottom: 1em">Nullam non wisi a sem eleifend. Donec mattis libero eget urna. Pellentesque viverra enim.</p>
					</li>
					<li style="height: 140px;margin: 0;padding: 1em 0em 1.5em 0em;border-top: 1px solid #ECECEC;">
						<p style="padding: 0em 0em 1em 0em;letter-spacing: 1px;text-transform: uppercase;font-size: 8pt;color: #A2A2A2;">August 11, 2002  |  (10 )  Comments</p>
						<img style="clear: both;float: left;margin-right: 20px;" src="images/pic05.jpg" alt="" />
						<p style="margin-bottom: 1em">Nullam non wisi a sem eleifend. Donec mattis libero eget urna. Pellentesque viverra enim.</p>
					</li>
				</ul>
    		</div>
    		<div align="center" class="menu-box" style="margin-top: 20px;margin-left:10px;width: 820px;float: left;display: inline;">
				<div align="left" style="width: 780px;">
					<div align="left" style="font-size: 3em;font-weight: bold;height: 38px;margin-top: 24px;">Left Sidebar</div>
					<div align="left" style="font-size: 1.40em;display: block;margin: 0.5em 0 20px 0;padding: 0 0 0.5em 0;">
						Integer sit amet pede vel arcu aliquet pretium
					</div>
	    			<a href="#"><img src="images/pic07.jpg" alt="" /></a>
					<p>Sed etiam vestibulum velit, euismod lacinia quam nisl id lorem. Quisque erat. Vestibulum pellentesque, justo mollis pretium suscipit, justo nulla blandit libero, in blandit augue justo quis nisl. Fusce mattis viverra elit. Fusce quis tortor. Consectetuer adipiscing elit. Nam pede erat, porta eu, lobortis eget lorem ipsum dolor. Sed etiam vestibulum velit, euismod lacinia quam nisl id lorem. Quisque erat. Vestibulum pellentesque, justo mollis pretium suscipit, justo nulla blandit libero, in blandit augue justo quis nisl. Fusce mattis viverra elit. Fusce quis tortor. Consectetuer adipiscing elit. Nam pede erat, porta eu, lobortis eget lorem ipsum dolor.</p>
					<p>Maecenas pede nisl, elementum eu, ornare ac, malesuada at, erat. Proin gravida orci porttitor enim accumsan lacinia. Donec condimentum, urna non molestie semper, ligula enim ornare nibh, quis laoreet eros quam eget ante. Aliquam libero. Vivamus nisl nibh, iaculis vitae, viverra sit amet, ullamcorper vitae, turpis. Aliquam erat volutpat. Vestibulum dui sem, pulvinar sed, imperdiet nec, iaculis nec, leo. Fusce odio. Etiam arcu dui, faucibus eget, placerat vel, sodales eget, orci. Donec ornare neque ac sem. Mauris aliquet. Aliquam sem leo, vulputate sed, convallis at, ultricies quis, justo. Donec nonummy magna quis risus. Quisque eleifend. Phasellus tempor vehicula justo. Aliquam lacinia metus ut elit.</p>
					<p>Donec nonummy magna quis risus. Quisque eleifend. Maecenas pede nisl, elementum eu, ornare ac, malesuada at, erat. Proin gravida orci porttitor enim accumsan lacinia. Donec condimentum, urna non molestie semper, ligula enim ornare nibh, quis laoreet eros quam eget ante. Aliquam libero. Vivamus nisl nibh, iaculis vitae, viverra sit amet, ullamcorper vitae, turpis. Aliquam erat volutpat. Vestibulum dui sem, pulvinar sed, imperdiet nec, iaculis nec, leo. Fusce odio. Etiam arcu dui, faucibus eget, placerat vel, sodales eget, orci. Donec ornare neque ac sem. Mauris aliquet. Aliquam sem leo, vulputate sed, convallis at, ultricies quis, justo. Phasellus tempor vehicula justo. Aliquam lacinia metus ut elit.</p>
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