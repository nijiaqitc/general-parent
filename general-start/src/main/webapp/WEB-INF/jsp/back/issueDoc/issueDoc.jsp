<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>发布文章</title>
	<jsp:include page="${path}/head"></jsp:include>
    <style type="text/css">
    	.tip{
    		font-size: 12px;
    		border: 1px solid #d7d7d7;
    		background: #f4f4f4;
    		float: left;
    		padding: 1px 3px 0 6px;
    		margin: 4px 4px 2px;
    		white-space: nowrap;
    	}
    	.tipinput{
    		float: left;
    		margin-bottom: 3px;
    		border: 1px solid #fff;
    		padding: 0px 2px 0;
    		margin-top: 4px;
    		width: 130px;
    		outline: none;
    		white-space: nowrap;
    	}
    </style>
</head>
<body>
	<!-- start:公共页，存放公共框 -->
	<jsp:include page="${path}/publicJsp"></jsp:include>
	<!-- end:公共页，存放公共框 -->
	<!-- start: 顶部菜单 -->
	<jsp:include page="../public/top.jsp"></jsp:include>
	<!-- end: 顶部菜单 -->
	<div class="container-fluid-full">
		<div class="row-fluid">
			<!-- start: 左边菜单 -->
<!-- 			<jsp:include page="${path}/left"></jsp:include>
			<!-- end: 左边菜单 -->
			<!-- start: 正文 -->
			<div id="content" class="span10" >
				<div class="row-fluid">
					<div class="box span6" style="width: 100%;" align="center">
						<div class="box-content custom_pagination" style="height: 1200px;" >
							<div style="width: 1050px;">
								<div>
									<span>
										标题：<input id="title" name="title" type="text" style="width: 400px;margin-top: 12px;">
									</span>
									<span>tbk:<input type="checkbox" id="isTbk"></span>
									<span>zz:<input type="checkbox" id="isZz" onchange="seleZz()"></span>
								</div>
								<div>
									类型：
									<select id="docType" style="margin-top: 10px;width: 120px;">
										<option></option>
										<c:forEach items="${typeList }" var="type">
											<option value="${type.id }">${type.name }</option>
										</c:forEach>
									</select>
									<input type="button" onclick="newType()" value="建新类型">	
									<div id="newType" style="display: none;">
										类型名称：<input type="text" id="typeName" style="width: 100px;margin-top: 12px;">
										父类型：
										<select id="parentType" style="margin-top: 10px;width: 90px;">
											<c:forEach items="${parentList }" var="type">
												<option value="${type.id }">${type.name }</option>
											</c:forEach>
										</select>
										<input type="button" onclick="saveType()" value="保存">
									</div>
								</div>
								<div id="zzp" style="display: none;">转载地址：<input type="text" id="zzplace" style="width: 500px;"></div>
								<div style="width: 1000px;height: 160px;">
									<div style="float: left;display: inline;" align="left">
										<div style="height: 60px;width: 800px;">
											标签：
											<div id="tip" name="tip" onclick="inputfocuse()" style="border: 1px solid #C1C1BB;width: 794px;height: 30px;margin-top: 6px;">
												<input id="tInput" class="tipinput" onblur="tipcheck(this)">
											</div>
										</div>
										<div style="height: 110px;width: 800px;">
											概要：
											<textarea  id="general"  name="general" style="resize: none;width: 780px;height: 80px;overflow-y: scroll;overflow: scroll;clear: both;" ></textarea>
											<div id="lastDiv" style="margin-top: -30px;width: 250px;margin-left: 630px;">
												<span>剩余输入字数<span id="textNum">200</span>个</span>
											</div>
										</div>
									</div>
									<div align="left" style="height: 170px;width: 170px;float: left;display: inline;margin-left: 20px;">
										<input type="button" value="上传文章代表图" onclick="showDialogForSave()">自<input type="checkbox" id="autopic" >
										<img style="width: 166px;height: 133px;margin-top: 12px;" id="docPic" alt="" src="${resPath }/back/img/waitForUp.jpg">
										<input type="hidden" id="picId">
										<input type="hidden" id="picPlace">
									</div>
								</div>
								<div style="width:995px;height:500px;clear: both;margin-top: 20px;">
									<script id="editor" type="text/plain" style="width:995px;height:500px;"></script>
									<input type="button"  onclick="saveDoc()" value="确定">&nbsp;&nbsp;&nbsp;&nbsp; 
									<input type="button" value="预览" onclick="openPostWindow()">
								</div>
							</div>
						</div>
					</div><!--/span-->
				</div><!--/row-->
			</div>
			<!-- end: 正文 -->
			
			
			<!-- start:弹出框 -->
			<div class="modal hide fade in" id="myModal" aria-hidden="false" style="display: none;width: 666px;margin-left: -100px;left: 20%;height: 590px;">
				<div class="modal-header">
					<button type="button" onclick="closeDialog()" class="close" data-dismiss="modal">×</button>
					<h3 id="dialogTitleName"></h3>
				</div>
				<div class="modal-body" style="height: 620px;">
					<div class="step-pane" id="step2" style="margin-top: 20px;">
						<iframe id="upPicTool" src="${path}/admin/totalInfo/jcrop" frameborder="no" style="width: 1000px;height: 500px;margin-left: -232px;margin-top: -35px;"></iframe>
					</div>
				</div>  
			</div>
			<!--end:弹出框-->
			<!--start:遮罩层-->
			<div id="backBlackGround" class="modal-backdrop fade in" style="display: none;"></div>
			<!--end:遮罩层-->
		</div>
		<jsp:include page="${path}/boom"></jsp:include>
	</div>
	<jsp:include page="${path}/foot"></jsp:include>
	<script type="text/javascript" charset="utf-8" src="${resPath }/chajian/uedit/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${resPath }/chajian/uedit/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${resPath }/chajian/uedit/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
    	var ue = UE.getEditor('editor');
    	
    	$("#tInput").keydown(function(e){
    	    if(!e) var e = window.event; 
    	    if(e.keyCode==32){
    	    	var s=false;
    	    	if($("#tInput").val().trim()==""){
    	    		$("#tInput").val("");
        			return
        		}
    	    	if($(".tip").length>4){
    	    		$("#tInput").val("");	    	    		
    	    	}else{
    	    		$.each($(".tip"),function(a,b){
    	    			if($("#tInput").val().trim()==$($(b).children("span")).html()){
    	    				s=true;
    	    			}
    	    		})
    	    		if(s!=true){
		    	    	$("#tInput").before('<div class="tip"><span>'+$("#tInput").val().trim()+'</span><a onclick="closeTip(this)" href="javascript:;" title="删除" style="color: #999;">×</a></div>')
    	    		}
	    	    	s=false;
	    	    	$("#tInput").val("");
    	    	}
    	    }
    	 });
    	
    	$(function(){
    		  $("#general").keyup(function(){
    		   var len = $(this).val().length;
    		   if(len > 199){
    		    $(this).val($(this).val().substring(0,199));
    		   	$("#textNum").text(0);
    		   }else{
    			   var num = 200 - len;
        		   $("#textNum").text(num);
    		   } 
    		  });
    	});
    	
    	function inputfocuse(){
    		$("#tInput").focus();
    	}
    	
    	function tipcheck(e){
    		if($("#tInput").val().trim()==""){
    			$("#tInput").val("");
    			return
    		}
    		if($(".tip").length>4){
	    		$("#tInput").val("");
    		}else{
    			$("#tInput").before('<div class="tip"><span>'+$("#tInput").val().trim()+'</span><a onclick="closeTip(this)" href="javascript:;" title="删除" style="color: #999;">×</a></div>')
    	    	$("#tInput").val("");
    		}
    	}
    	
    	function closeTip(e){
    		$(e).parent("div").remove();
    	}
    	
    	function checkVail(text,tipList){
    		if(text==""){
    			showSureMessage("提示","请先输入文档内容！");
    			return true;
    		}
    		if(tipList==""){
    			showSureMessage("提示","请先输入标签！");
    			return true;
    		}
    		if($("#general").val()==""){
    			showSureMessage("提示","请先输入概要！");
    			return true;
    		}
    		if($("#title").val()==""){
    			showSureMessage("提示","请先输入标题！");
    			return true;
    		}
    		if($("#docType").val()==""){
    			showSureMessage("提示","请选择类型！");
    			return true;
    		}
    	}
    	function openPostWindow(url, data, name){ 
    		var text = UE.getEditor('editor').getContent();
    		var tipList="";
    		$.each($(".tip span"),function(a,b){
    			tipList +=$(b).html()+","
    		})
    		tipList=tipList.substring(0,tipList.length-1).trim();
    		if(checkVail(text,tipList)){
    			return 
    		}
    		var formatGeneral="";
    		var docType="";
    		var picType="";
    		if($("#isZz").prop("checked")){
    			formatGeneral=UE.getEditor('editor').getContentTxt().substr(0,150)+"&&"+$("#zzplace").val();
    			docType=1;
    		}else{
    			formatGeneral=$("#general").val();
    			docType=2;
    		}
    		if($("#autopic").prop("checked")){
    			picType=1;
    		}else{
    			picType=2;
    		}
    		
    		$.ajax({
    			url:"openPostWindow",
    			async : false,
    			type:"post",
    			data:{
    				text:UE.getEditor('editor').getContent(),
    				general:formatGeneral,
    				title:$("#title").val(),
    				tips:tipList,
    				picPlace:$("#picPlace").val(),
    				type:$("#docType").val(),
    				docType:docType,
    				picType:picType
    			},
    			success:function(data){
    				docView();
    			}
    		})
    	}
    	
    	function docView(){
    		window.open ("docView","newwindow","height=800,width=1300,top=0,left=200,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no") 
    	}
    	
    	
    	
    	function saveDoc(){
    		var text = UE.getEditor('editor').getContent();
    		var tipList="";
    		$.each($(".tip span"),function(a,b){
    			tipList +=$(b).html()+","
    		})
    		tipList=tipList.substring(0,tipList.length-1).trim();
    		if(checkVail(text,tipList)){
    			return 
    		}
    		var isTbk=1;
    		if($("#isTbk").prop("checked")==true){
    			isTbk=2;
    		}
    		var formatGeneral="";
    		var docType="";
    		var picType="";
    		if($("#isZz").prop("checked")){
    			formatGeneral=UE.getEditor('editor').getContentTxt().substr(0,150)+"&&"+$("#zzplace").val();
    			docType=1;
    		}else{
    			formatGeneral=$("#general").val();
    			docType=2;
    		}
    		if($("#autopic").prop("checked")){
    			picType=1;
    		}else{
    			picType=2;
    		}
    		$.ajax({
    			url:"${path}/admin/issueDoc/releaseDoc",
    			data:{
    				typeId:$("#docType").val(),
    				picId:$("#picId").val(),
    				title:$("#title").val(),
    				text:text,
    				general:formatGeneral,
    				tips:tipList,
    				readtype:isTbk,
    				docType:docType,
    				picType:picType
    			},
    			type:"post",
				beforeSend:ajaxBefore(),
    			success:function(data){
    				ajaxAfter();
   					showSureMessage("提示",data.message);
    			}
    		})
    	}
    	
    	/**
		 * 显示上传图片
		 */
		function showDialogForSave(e){
			$("#dialogTitleName").html("上传图片");
			$("#myModal").show();
			$("#backBlackGround").show();
		}
    	
		function closeDialog(){
			$("#myModal").hide();
			$("#backBlackGround").hide();
			var aaa=$("#upPicTool").contents().find("#hidePlace").val();
			var bbb=$("#upPicTool").contents().find("#hideId").val();
			if(aaa!=null&&aaa!=""){
				$("#docPic").attr("src",aaa);
				$("#picId").val(bbb);
				$("#picPlace").val(aaa);
			}
		}
		
		function newType(){
			$("#newType").show();
		}
		
		function saveType(){
			if($("#typeName").val()==null||$("#typeName").val()==""){
				alert("请输入类型名称！")
				return 
			}
			$.ajax({
				url:"${path}/admin/typeManage/saveDocType",
				data:{
					typeName:$("#typeName").val(),
					parentId:$("#parentType").val()
				},
				beforeSend:ajaxBefore(),
				type:"post",
				success:function(data){
					ajaxAfter();
					if(data.state==1){
						$("#typeName").val("");
						$("#docType").append("<option value=\""+data.type.id+"\">"+data.type.name+"</option>")
    				}
   					showSureMessage("提示",data.message);
				}
			})
			$("#newType").hide();
		}
		
		function seleZz(){
			if($("#isZz").prop("checked")){
				$("#zzp").show();
				$("#general").attr("disabled",true)
			}else{
				$("#general").attr("disabled",false)
				$("#zzp").hide();
			}
		}
	</script>
</body>
</html>