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
    function showDialog(){
    	$(".showSaveDlg").show();
    	$(".model").show();
    	
    }
    function hideDialog(){
    	$(".showSaveDlg").hide();
    	$(".model").hide();
    	$("#newName").val("");
    	$("#oldName").val("");
    	$("#docId").val("");
    	$("#contextDetail").val("");
    }
    
    function saveDocName(){
    	$.ajax({
    		url:"saveDocName",
    		type:"post",
    		data:{
    			newName:$("#newName").val(),
    		    oldName:$("#oldName").val(),
    		    id:$("#docId").val(),
    		    contextDetail:$("#contextDetail").val()
    		},
    		success:function(data){
    			alert("操作成功！")
    			location.reload()
    			
    		}
    	})
    	$(".showSaveDlg").hide();
        $(".model").hide();
    }
    function showModiBtn(title,id,target){
        $("#oldName").val(title);
        $("#newName").val(title);
        $("#docId").val(id);
        $("#contextDetail").val($(target.parentElement.nextElementSibling).html());
        showDialog();
    }
    
    function showDetail(target){
    	var detail=$(target.parentElement.nextElementSibling);
    	if(detail.css("display")=="none"){
    		detail.show();
    		target.innerHTML="↑";
    	}else{
    		detail.hide();
    		target.innerHTML="↓";
    	}
    }
    </script>
</body>
</html>