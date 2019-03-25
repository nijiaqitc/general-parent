<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="琦三叔个人主页，琦三叔网站，琦三叔博客">
<meta name="description" content="显示琦三叔发表的各种文章，还有琦三叔业余时编写的各种小工具应用">
<title>琦三叔官网</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/zxgj/css/index.css">
<script type="text/javascript">
	var mobileAgent = new Array("iphone", "ipod", "android", "blackberry", "windows phone", "windows mobile", "webos", "incognito", "webmate", "bada", "nokia", "lg", "ucweb", "skyfire");
	var browser = navigator.userAgent.toLowerCase(); 
	var isMobile = false;
	for (var i=0; i<mobileAgent.length; i++){ 
	  //获取mobileAgent[i]的字符串在browser中的位置，如果不包含那么会返回-1，如果包含，那么就显示wap网页
	  if (browser.indexOf(mobileAgent[i])!=-1){ 
	    isMobile = true;
	    break; 
	  }
	}
	if(${ismob!=true}){
	    if(isMobile) location.href = "${authUrl}/wap/toIndex";
	}
</script>
</head>
<body>
    <!--     开始：顶部菜单栏-->
    <jsp:include page="top.jsp"></jsp:include>
    <!--     结束：顶部菜单栏 -->
    <!--     开始：banner区域 -->
	<div class="banner">
	    <ul></ul>
	    <ol></ol>
	    <i class="left"></i><i class="right"></i>
	</div>    
    <!--     结束：banner区域 -->
	<div align="center" class="areaTotal">
	    <div class="modelTitleDiv">
		    <div class="modelArea">在线工具</div>
	    </div>
	    <div class="showArea1">
		    <div class="toolArea" >
		       <div class="topTitle">
		           <div class="toolsTitle">代码格式化</div>
		           <div class="toolsMore"><a href="tools" class="moreLink">更多>>></a></div>
		       </div>
		       <div class="linkArea">
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/jsonDecode">JSON格式化</a></div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/jsDecode">JS格式化</a></div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/sqlDecode">SQL格式化</a></div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/cssDecode">CSS格式化</a></div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/xmlDecode">XML格式化</a></div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		       </div>
		    </div>
		    <div class="toolArea" >
		       <div class="topTitle">
		           <div class="toolsTitle">加密/解密</div>
	               <div class="toolsMore"><a href="tools" class="moreLink">更多>>></a></div>
		       </div>
	           <div class="linkArea">
	               <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/md5">MD5加密</a></div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/jsDecode">RSA加密/解密</a></div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/base">BASE64加密/解密</a></div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/sha">sha1加密/解密</a></div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/jslock">js加密/解密</a></div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/encrypt">encryp加密/解密</a></div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/des">des加密/解密</a></div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/cipherlock">暗号加密/解密</a></div></div>
	           </div>
		    </div>
		    <div class="toolArea" >
			    <div class="topTitle">
			        <div class="toolsTitle">计算器</div>
		            <div class="toolsMore"><a href="tools" class="moreLink">更多>>></a></div>
			    </div>
	           <div class="linkArea">
	               <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
	           </div>
		    </div>
		    <div class="toolArea" >
		       <div class="topTitle">
		           <div class="toolsTitle">其他</div>
	               <div class="toolsMore"><a href="tools" class="moreLink">更多>>></a></div>
		       </div>
	           <div class="linkArea">
	               <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/zzbds">正则表达式</a></div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord "><a href="${path }/tools/htmlts">HTML调试</a></div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
		           <div class="toolsBtn3 btnWord"><div class="toolsBtn4 btnWord ">暂无</div></div>
	           </div>
		    </div>
	    </div>
	    <div class="modelTitleDiv"> 
	        <div class="modelArea">知识拓展</div>
	    </div>
	    <div align="center">
	        <div class="showArea2" >
	            <div class="contextCloud" align="left">
	            	<c:forEach items="${doclist }" var="doc">
		                <div class="toolsBtn2 titleStyleDiv" >
		                    <div class="showTitle"><a target="_blank" href="${path }/yxl/knowledge/${doc.id }">${doc.title }</a></div>
		                    <div class="labelStyle"><label class="labelNameStyle">标签：</label>
		                    	<c:forEach items="${doc.tipList }" var="tip">
				                    <a href="${path }/docList?tipName=${tip.tipName }" class="textType">${tip.tipName }</a>
		                    	</c:forEach>
		                    <span class="readNum"></span></div>
		                </div>
	            	</c:forEach>
	                <c:forEach items="${xlDoclist }" var="doc">
		                <div class="toolsBtn2 titleStyleDiv2" style="clear: both;"><a target="_blank" href="${path }/yxl/knowledge/${doc.docId }">${doc.title }</a></div>   
	                </c:forEach>
	            </div>
	        </div>
	    </div>
	</div>
    <!--     开始：顶部菜单栏-->
	<jsp:include page="bottom.jsp"></jsp:include>
    <!--     结束：顶部菜单栏 -->
	<script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/index.js" type="text/javascript"></script>
</body>
</html>