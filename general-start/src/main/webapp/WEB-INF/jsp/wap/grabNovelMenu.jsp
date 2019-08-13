<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>标题列表</title>
<jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
<style>
    .titleStyle{
        clear: both;
	    overflow: auto;
	    height: 40px;
	    line-height: 40px;
	    font-size: 17px;
	    border-bottom: 1px solid #bbb;
	    padding-top: 6px;
    }
    .showSaveDlg{
        background-color: #fff;
	    width: 280px;
	    line-height: 30px;
	    position: absolute;
	    margin-left: 50px;
	    margin-top:80px;
	    display: none;
	    z-index: 99;
    }
    .model{
        width:100%;height:100%;position: fixed;z-index: 50;background-color: #222;opacity: 0.5;display: none;
    }
    .rmenu{
   	    border-bottom: 1px solid #ddd;
   	    padding: 2px 0px;
	    padding-left: 10px;
    }
    .consultArea{
    	position: fixed;
	    top: 0px;
	    right: -300px;
	    background-color: white;
	    padding-left: 4px;
	    overflow: auto;
	    height: 100%;
    }
</style>
</head>
<body>
	<!-- 	正文部分开始 -->
	<div class="textContext" align="center">
	   <div style="width: 90%;padding-top: 20px;" align="left">
	   	   <div align="center" style="font-size: 20px;font-weight: 600;" onclick="reload()">${title }</div>
	       <c:forEach items="${list}" var="thc" >
			   <div class="titleStyle">
			        <a href="queryNovelDoc?menuId=${thc.id}" >
			        	<div style="float: left;">☆</div>
			        	<div style="float: left;margin-left: 4px;">${thc.title}</div>
			        </a>
    		   </div>
	       </c:forEach>
	       <div class="consultArea">
       			<div align="center" style="border-bottom: 1px solid #f7caca;font-size: 16px;padding: 4px 0px;">目录</div>
		       <c:forEach items="${consultList }" var="consult">
		       		<div class="rmenu">${consult }</div>
		       </c:forEach>
	       </div>
	   </div>
	</div>
	<!-- 	正文部分结束 -->

	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
	<script type="text/javascript">
	function reload(){
		window.location.href=window.location.href+"&sort=desc";
		if(window.location.search.indexOf("sort") != -1){
			if(window.location.search.split("sort=")[1]=="desc"){
				window.location.href = window.location.href.split("?")[0]+window.location.search.split("&")[0]+"&sort=asc";
			}else{
				window.location.href = window.location.href.split("?")[0]+window.location.search.split("&")[0]+"&sort=desc";
			}
		}else{
			window.location.href = window.location.href.split("?")[0]+window.location.search.split("&")[0]+"&sort=asc";
		}
	}
    
    
    $(function(){
	    touch.on(".textContext", "swipeleft", function(ev){
			    	leftmove();
		});
    	touch.on(".textContext", "swiperight", function(ev){
			    	rightmove();
		});
    
    	function leftmove(){
    		animate($(".consultArea")[0],"right","0");
    	}
    	function rightmove(){
    		animate($(".consultArea")[0],"right","-300");
    	}
    	touch.on(".consultArea", "doubletap", function(ev){
		  	$.ajax({
	    		url:"/novel/reloadMenu",
	    		type:"post",
	    		success:function(data){
	    			window.location.reload();
	    		}
	    	})
		});
    })
   	function animate(obj, attr, target) {
        // 防止连续移入元素会生成多个计时器，所以进入之前先清除
        clearInterval(obj.timer);
        obj.timer = setInterval(function() {
            // 属性当前值
            var icur = parseInt(getStyle(obj, attr));
            // 动画的速度
            var speed = (target - icur) / 4;
            speed = speed > 0 ? Math.ceil(speed) : Math.floor(speed);
            if (icur == target) {
                clearInterval(obj.timer);
            } else {
                obj.style[attr] = icur + speed + 'px';
            }
        }, 30);
    }
    
    function getStyle(obj, attr) {
        // IE
        if (obj.currentStyle) {
            return obj.currentStyle[attr];
        } else {
            return getComputedStyle(obj, false)[attr];
        }
    }
    </script>
</body>
</html>