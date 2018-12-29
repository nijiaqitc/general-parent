<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>网站介绍</title>
<meta name="keywords" content="网站架构,站长介绍,架构">
<meta name="description" content="介绍网站所用架构">
<jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${resPath }/wap/css/aboutMe.css"  >
</head>
<body>
	<!-- 	顶部div开始 -->
	<jsp:include page="${resPath }/wap/commonwap/top.jsp"></jsp:include>
	<!-- 	顶部div结束 -->
	
	<!-- 	正文部分开始 -->
	<div class="toTop" >
		<div  class="menu-box" class="contentDiv1" align="center" >
			<div align="left" class="contentDiv2">
				<div class="contentDiv3">|网站介绍</div>
				<div>
					<div><span class="contentDiv4">网站功能:</span>
						<span>此站目前为一个博客网站，用于快速发表文章、浏览文章。网站分为前后台，后台发表管理文章，前台浏览文章。在后续会将功能延伸，在网站上添加各种方便的功能，如果您对此网站功能有什么意见或建议或想添加一些您需要的工具，请联系站长，站长觉得没问题将会<em>帮您开发</em>并放在此网站上。</span></div>
					<div class="contentDiv5">
						<span class="contentDiv6">网站架构:</span>
						<div class="contentDiv12">此站买的是 阿里云的域名和服务器</div>
						<div class="contentDiv12">利用nginx反向代理将图片和项目进行分离</div>
						<div class="contentDiv12">利用多个tomcat进行集群和热备</div>
						<div class="contentDiv12">利用redis进行缓存同步</div>
						<div class="contentDiv12">利用jenkins打包发布项目</div>
						<div class="contentDiv12">利用svn进行版本控制</div>
						<div class="contentDiv12">开发技术为：jsp/jquery/springmvc/hibernate/mysql</div>
						<div class="contentDiv12">页面设计：抄袭+创意+ps</div>
					</div>
				</div>
			</div>
			<div align="left" class="contentDiv7">
				<div class="contentDiv8">|站长介绍</div>
				<div class="contentDiv12" >
					(站长)一个整天喜欢做梦的小屌丝、一个碌碌无为的代码农民工。既没有精通的技术也没有广阔的人脉，既不会说话也不会讨好上司， 整天码着一堆有用又无用的代码...
				</div>
				<div class="contentDiv12">此站由琦三叔负责<em>全权开发</em>，若有<em>侵犯</em>到任何人真的非常抱歉，请您及时<em>联系</em>琦三叔，琦三叔会立马进行相应的处理。</div>
				<div align="right">谢谢您的阅览。</div>
			</div>
		</div>
		<div>
			
		</div>
	</div>
	<!-- 	正文部分结束 -->

	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
</body>
</html>