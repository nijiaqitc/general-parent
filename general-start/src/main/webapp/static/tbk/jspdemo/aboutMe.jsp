<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>首页</title>
<jsp:include page="common.jsp"></jsp:include>
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
    <div align="center" style="height: 550px;clear: both;">
    	<div style="width: 990px;margin-top: 20px;">
	    	<div style="width: 660px;height: 590px;background-image: url('../images/me.jpg');display: inline;float: left;" ></div>
	    	<div class="menu-box" style="display: inline;float: left;width: 300px;margin-left: 20px;">
	    		<div align="left" style="margin: 20px 20px;">
	    			<span style="margin-left: -4px;font-size: 22px;font-weight: bold;color: rgb(155,10,10);"><span style="text-decoration:underline;" >站长琦三叔</span>_－_－_－_－_</span><br/>
	    			<span style="-webkit-transform: scale(0.5);display: block;margin-left: -70px;margin-top: -10px;text-decoration:underline;">你怎么对待别人，别人就怎么对待你</span>	
	    			<span style="font-size: 12px;" >
	    			<p style="text-indent:24px">（站长）一个整天喜欢做梦的小屌丝、一个碌碌无为的代码农民工。既没有精通的技术也没有广阔的人脉，既不会说话也不会讨好上司，
	    			整天码着一堆有用又无用的代码，总之基本上是一个普通的不能再普通的码农。相信有很多同行和琦三叔一样，琦三叔喜欢广交朋友，如果大家觉得自己像琦三叔一样那么可以加琦三叔，让我们一起探讨人生、探讨未来，哈哈。<br/>
	    			</p> 
	    			<p style="text-indent:24px">此站由琦三叔负责进行开发，若有侵犯到任何人真的非常抱歉，请您及时联系琦三叔，琦三叔会立马进行相应的处理。若您看上了此网站上的技术或者想开发制作一套像琦三叔一样的网站，但又有好多疑问或者不敢做，
	    			那么请速度联系琦三叔，琦三叔必倾囊相授！不要怀疑琦三叔，可以试试加琦三叔哦（先把你骗过来再说嘿嘿）。
	    			</p>
	    			<p style="text-indent:24px">
	    				琦三叔微博、微信、QQ皆同步，任何时刻您都可以找到琦三叔。谢谢大家的支持！
	    			</p>
	    			</span>
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