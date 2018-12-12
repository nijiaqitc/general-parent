<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>网站介绍</title>
<jsp:include page="../../../wap/commonwap/common.jsp"></jsp:include>
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
	   <div class="showSaveDlg">
	       <div style="padding: 14px;">
	           <div>原书本名字：<input id="oldName" type="text"  readOnly="true" style="background-color: #f2f2f2;" /></div>
	           <div>新书本名字：<input id="newName" type="text"/></div>   
	           <div><textarea id="contextDetail" style="width: 244px;height: 100px;resize: none;"  ></textarea></div>    
		       <div>
		          <input type="hidden" id="docId" />
		          <input type="button" value="提交" onclick="saveDocName()"/>
		          <input type="button" onclick="hideDialog()" value="关闭" style="margin-left:18px;"/>
		       </div>
	       </div>
	   </div>
	   <div class="model"></div>
	   <div style="height: 30px;font-size: 40px;padding-top: 14px;width: 90%;line-height: 26px;display: block;">
	       <div style="float: right;height: 100%;width: 30px;border: 1px solid #aaa;" onclick="showDialog()">+</div>
	   </div>
	   <div style="width: 90%;padding-top: 20px;" align="left">
	       <c:forEach items="${list}" var="thc" >
			   <div class="titleStyle">
			        <a href="queryTitleList?docId=${thc.id}" ><div style="float: left;">☆</div>
			        <div style="float: left;margin-left: 4px;">${thc.title}</div></a>
			        <div style="float: right;margin-left: 10px;" onclick="showDetail(this)">↓</div>
			        <div style="float: right;margin-left: 10px;" onclick="showModiBtn('${thc.title}',${thc.id},this)">※</div>
			        <div style="float: right;font-size: 12px;">(${thc.total})</div> 
    		   </div>
			   <div style="min-height: 100px;clear:both;width: 100%;border: 1px solid #aaa;border-top: none;display: none;word-wrap: break-word;text-indent: 24px;">${thc.contextDesc}</div>
	       </c:forEach>
	   </div>
	</div>
	<!-- 	正文部分结束 -->

	<!-- 	底部通用部分开始 -->
	<jsp:include page="../../../wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="../../../wap/commonwap/commonBottom.jsp"></jsp:include>
	<script type="text/javascript">

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