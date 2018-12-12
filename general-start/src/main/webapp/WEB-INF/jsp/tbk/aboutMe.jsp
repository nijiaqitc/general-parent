<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="站长介绍，网站介绍，个人介绍">
<meta name="description" content="琦三叔的自我介绍，以及网站所用的技术介绍">
<title>关于站长</title>
<jsp:include page="../../../tbk/commonjsp/commonTop.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${resPath }/tbk/css/aboutMe.css"  >
</head>
<body>
    <!--     开始：顶部联系方式 -->
    <div class="menu-box topMenu"  align="center">
       <jsp:include page="../../../tbk/commonjsp/top.jsp"></jsp:include>
    </div>
    <!--     结束：顶部联系方式 -->
    <!--     开始：导航条 -->
    <div class="anavi1" ></div>
    <div class="menu-box anavi2" align="center" >
    	<jsp:include page="../../../tbk/commonjsp/navigate.jsp"></jsp:include>
    </div>
    <!--     结束：导航条 -->
    <!--     开始：最新文章区域 -->
    <div align="center" class="aoutDiv1">
    	<div class="aoutDiv2">
	    	<div class="aoutDiv3" ></div>
	    	<div class="menu-box aoutDiv4" >
	    		<div align="left" class="aoutDiv5">
	    			<span class="aoutDiv5span1"><span class="aoutDiv5span2" >站长琦三叔</span>_－_－_－_－!</span><br/>
<!-- 	    			<span class="aoutDiv5span3">你怎么对待别人，别人就怎么对待你</span>	 -->
	    			<span class="aoutDiv5span4" >
	    			<p class="aoutDiv5span5">（站长）一个整天喜欢做梦的小屌丝、一个碌碌无为的代码农民工。既没有精通的技术也没有广阔的人脉，既不会说话也不会讨好上司，
	    			整天码着一堆有用又无用的代码，总之基本上是一个普通的不能再普通的码农。相信有很多同行和琦三叔一样，琦三叔喜欢广交朋友，如果大家觉得自己像琦三叔一样那么可以加琦三叔，让我们一起探讨人生、探讨未来，哈哈。<br/>
	    			</p> 
	    			<p class="aoutDiv5span5">此站由琦三叔负责全权开发，若有侵犯到任何人真的非常抱歉，请您及时联系琦三叔，琦三叔会立马进行相应的处理。若您看上了此网站上的技术或者想开发制作一套像琦三叔一样的网站，但又有好多疑问或者不敢做，
	    			那么请速度联系琦三叔，琦三叔将与您一起分析探讨。
	    			</p>
	    			<p class="aoutDiv5span5">
	    				琦三叔微博、微信、QQ皆同步，可随时进行联系。谢谢大家的支持！
	    			</p>
	    			</span>
	    		</div>
	    	</div>
    	</div>
    </div>
    <!--     开始：页面底部 -->
    <div class="aFootBefore"></div>
    <div align="center"  class="menu-box aFootTotal">
    	<jsp:include page="../../../tbk/commonjsp/footer.jsp"></jsp:include>
    </div>
    <!--     结束：页面底部  -->
    <jsp:include page="../../../tbk/commonjsp/commonBottom.jsp"></jsp:include>
    <script src="${resPath }/tbk/js/aboutMe.js" type="text/javascript"></script>
</body>
</html>