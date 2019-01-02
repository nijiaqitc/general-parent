<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<jsp:include page="${path}/frontHeadJs"></jsp:include> 
	<link href="${resPath }/front/css/style.css" rel="stylesheet"> 
	<style type="text/css">
	
		.search{
			width: 75px;
		    height: 35px;
		    line-height: 28px;
		    font: 12px \5b8b\4f53;
		    cursor: pointer;
		    outline: none;
		    display: inline-block;
		    text-align: center;
		    color: #fff;
		    border: none;
		    background-color: #1a89ed;
		    vertical-align: text-bottom;
		}
		
		.text{
		       	width: 400px;
			    padding-left: 5px;
			    margin-left: 10px;
			    margin-right: 10px;
			    height: 30px;
			    line-height: 22px;	
			    vertical-align: text-bottom;
			}
	</style>
</head>
<body>
	<jsp:include page="${path}/frontHead"></jsp:include>
	
	
	
	<article class="blogs">
		<h1 class="t_nav"><span>此为检索自己的笔记，想检索别人的文章赶紧百度去！</span></h1>
	
		<!-- 	检索框 -->
		<div align="center" style="margin-top: 25px;">
			<form onsubmit="" action="" target="_blank">
			    <input class="text" type="text"  onfocus="" name="word"  size="30">
			    <input class="search" type="button"value="搜索一下">
			</form>
		</div>
		<div align="center" >
			<div class="newblog">
			   <h2>程序员请放下你的技术情节，与你的同伴一起进步</h2>
			   <p class="dateview"><span>发布时间：2013-11-04</span><span>作者：杨青</span><span>分类：[<a href="/news/life/">程序人生</a>]</span></p>
			    <figure><img src="${resPath }/front/images/001.png"></figure>
			    <ul class="nlist">
			      <p>如果说掌握一门赖以生计的技术是技术人员要学会的第一课的话， 那么我觉得技术人员要真正学会的第二课，不是技术，而是业务、交流与协作，学会关心其他工作伙伴的工作情况和进展...</p>
			      <a title="/" href="/" target="_blank" class="readmore">阅读全文>></a>
			    </ul>
			    <div class="line"></div>
			     <h2>程序员请放下你的技术情节，与你的同伴一起进步</h2>
			   <p class="dateview"><span>发布时间：2013-11-04</span><span>作者：杨青</span><span>分类：[<a href="/news/life/">程序人生</a>]</span></p>
			    <figure><img src="${resPath }/front/images/001.png"></figure>
			    <ul class="nlist">
			      <p>如果说掌握一门赖以生计的技术是技术人员要学会的第一课的话， 那么我觉得技术人员要真正学会的第二课，不是技术，而是业务、交流与协作，学会关心其他工作伙伴的工作情况和进展...</p>
			      <a title="/" href="/" target="_blank" class="readmore">阅读全文>></a>
			    </ul>
			    <div class="line"></div>
			         <h2>程序员请放下你的技术情节，与你的同伴一起进步</h2>
			   <p class="dateview"><span>发布时间：2013-11-04</span><span>作者：杨青</span><span>分类：[<a href="/news/life/">程序人生</a>]</span></p>
			    <figure><img src="${resPath }/front/images/001.png"></figure>
			    <ul class="nlist">
			      <p>如果说掌握一门赖以生计的技术是技术人员要学会的第一课的话， 那么我觉得技术人员要真正学会的第二课，不是技术，而是业务、交流与协作，学会关心其他工作伙伴的工作情况和进展...</p>
			      <a title="/" href="/" target="_blank" class="readmore">阅读全文>></a>
			    </ul>
			    <div class="line"></div>
			         <h2>程序员请放下你的技术情节，与你的同伴一起进步</h2>
			   <p class="dateview"><span>发布时间：2013-11-04</span><span>作者：杨青</span><span>分类：[<a href="/news/life/">程序人生</a>]</span></p>
			    <figure><img src="${resPath }/front/images/001.png"></figure>
			    <ul class="nlist">
			      <p>如果说掌握一门赖以生计的技术是技术人员要学会的第一课的话， 那么我觉得技术人员要真正学会的第二课，不是技术，而是业务、交流与协作，学会关心其他工作伙伴的工作情况和进展...</p>
			      <a title="/" href="/" target="_blank" class="readmore">阅读全文>></a>
			    </ul>
			    <div class="page"><a title="Total record"><b>41</b></a><b>1</b><a href="/news/s/index_2.html">2</a><a href="/news/s/index_2.html">&gt;</a><a href="/news/s/index_2.html">&gt;&gt;</a></div>
			</div>
		</div>
	</article>
	
	
	<jsp:include page="${path}/frontBoom"></jsp:include> 
 	<jsp:include page="${path}/frontBoomJs"></jsp:include> 
</body>
</html>