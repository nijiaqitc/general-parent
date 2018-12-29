<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<?xml version="1.0" encoding="UTF-8"?>  
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>  
  <title>${docViewText.title}</title>  
  <meta name="keywords" content="${tipString }">
  <jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="${resPath }/wap/css/docView.css"  >
</head>
<body>
	<!-- 	顶部div开始 -->
	<jsp:include page="${resPath }/wap/commonwap/top.jsp"></jsp:include>
	<!-- 	顶部div结束 -->
	<jsp:include page="${resPath }/wap/commonwap/loading.jsp"></jsp:include>
	<!-- 	内容区域开始 -->
	<div class="textContext" >
		<div class="menu-box" align="center">
			<div>
				<div class="docView1">${docViewText.title} </div>
			</div>
			<div align="center" id="assss" class="docView4">
			</div>
			<div align="right" class="docView5">

			</div>
			<div id="valueText" style="display: none;">${docViewText.text}</div>
			<div id="valueCss" style="display: none;">${docViewText.css}</div>
		</div>
	</div>
	<!-- 	内容区域结束 -->

	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
  	<script type="text/javascript"  src="${resPath }/jsTool/customClearStyle/customClearStyle.js"></script>
  	<script type="text/javascript" src="${resPath }/jsTool/customHtmlDecoder/customHtmlDecoder.js"></script>
  	<script type="text/javascript">
	  	$(function(){
	  		var text=$("#valueText").html();
			var css=$("#valueCss").html();
			var html= assembleContext(text,css);
			
	  		var cd=new CustomDecoder()
	     	cd.str=html;
	     	var chd=new customHtmlDecoder();
			var ispre=1;
			if($("#isPre").val()==1){
				ispre=false;
			}else{
				ispre=true
			}
	     	$("#assss").html(chd.decode(cd.decode(),{ispre:true,tranType:2,spaceType:4})); 
			
	  	});
	  	
	  	function assembleContext(html,css){
	  	    if(!css){
	  	        return html;
	  	    }
	  	    var cs=css.split("|");
	  	    var tempDiv=document.createElement("div");
	  	    tempDiv.innerHTML=html;
	  	    var cc,pl,cnode=tempDiv,vt;
	  	    for(var i=0;i<cs.length;i++){
	  	        cc=cs[i].split("=");
	  	        vt=cc[0].split("]");
	  	        pl=vt[1].split(",");
	  	        for(var j=0;j<pl.length;j++){
	  	            cnode=cnode.childNodes[pl[j]];                      
	  	        }
	  	        if(vt[0]=="[1"){
	  	            cnode.setAttribute("class",cc[1]);
	  	        }else{
	  	            cnode.setAttribute("style",cc[1]);                      
	  	        }
	  	        cnode.removeAttribute("labelIndex");
	  	        cnode=tempDiv;
	  	    }
	  	    return tempDiv.innerHTML;
	  	}
    </script>
</body>  
</html>