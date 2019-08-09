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
	    line-height: 40px;
	    font-size: 17px;
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
    .boxa{
		font-size: 20px;
		border: 1px solid #ccc;
		background: white;
		margin-left: 10px;
		width: auto;
		display: block;
		float: left;
		height: 36px;
		line-height: 36px;
		text-align: center;
		padding: 0px 10px;
	}
</style>
</head>
<body>
	<!-- 	正文部分开始 -->
	<div class="textContext" align="center">
	   <div style="width: 90%;padding-top: 20px;" align="left">
		   <div style="overflow: auto;">
		   		<div align="right">
					<div style="height: 40px;line-height: 44px;">
						<input type="button" value="更新" onclick="load()" style="float: left;margin-top: 14px;">
						<input type="text" id="bookName" name="bookName"><input type="button" name="搜索" value="搜索"  onclick="searchBook()">
					</div>
		   		</div>
				<div align="center" style="overflow: auto;margin-top: 20px;">
					<c:forEach items="${list}" var="thc" >
						<div class="titleStyle">
							<input type="checkbox" style="float: left;margin-top: 14px;" name="book" value="${thc.id}">
							<a class="menu-box boxa" href="queryNovelTitle?parentId=${thc.id}" >
								<div style="float: left;margin-left: 4px;">${thc.title}</div>
							</a>
						</div>
					</c:forEach>
				</div>
			</div>
	   </div>
	</div>
	<!-- 	正文部分结束 -->

	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
	<script type="text/javascript">

    function load(){
    	var checkedBooks = $("input[name='book']:checked");
    	if(checkedBooks.size()==0){
    		return;
    	}
    	var values="";
    	for(var i = 0 ;i<checkedBooks.size();i++){
    		values += checkedBooks[i].value+",";
    	}
    	values = values.substr(0,values.length-1);
		$.ajax({
    		url:"/grab/upNovel",
    		type:"post",
    		data:{
    			menuId:values
    		},
    		success:function(data){
    			if(data.state == "1"){
	    			alert("正在处理...！");
	    			loadDoc();
    			}else{
    				alert(data);
    			}
    		}
    	})
    }
    
    function searchBook(){
    	$.ajax({
    		url:"/grab/loadbook",
    		type:"post",
    		data:{
    			bookName:$("#bookName").val()
    		},
    		success:function(data){
    			if(data.state == "1"){
	    			alert("正在处理...！");
	    			loadDoc();
    			}else{
    				alert(data);
    			}
    		}
    	})
    }
    
    function loadDoc(){
    	$.ajax({
    		url:"/grab/loaddoc",
    		type:"post",
    		success:function(data){
    			window.location.reload();
    		}
    	})
    }
    
    </script>
</body>
</html>