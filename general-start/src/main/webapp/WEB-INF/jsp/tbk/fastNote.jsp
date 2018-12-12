<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="技术区域，暂未开发">
<meta name="description" content="目前暂未开发，只显示1张图片冲冲场">
<title>快速发表文章</title>
<jsp:include page="../../../tbk/commonjsp/commonTop.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8" src="${resPath }/chajian/uedit/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${resPath }/chajian/uedit/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${resPath }/chajian/uedit/lang/zh-cn/zh-cn.js"></script>
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
    <div align="center" style="height: 800px;clear: both;">
    	<div class="menu-box" style="margin-top: 20px;" >
	    	<div style="padding-top: 20px">标题：<input id="title" name="title" type="text" style="width: 382px;background-color: white;"><input onchange="qiehuan()" id="qie" type="checkbox"></div>
	    	<div style="padding-top: 10px;display: none;" id="mygeneral">
	    		摘要：<textarea id="gentext" style="background-color: white;width: 400px;height: 80px;vertical-align: middle;"></textarea>
	    	</div>
	    	<div style="padding-top: 10px" id="docFrom">转自：<input id="general" name="general" type="text" style="width: 404px;background-color: white;"></div>
	    	<div style="margin-top: 10px">
	    		标签：<input id="tips" name="tips"  type="text" style="width: 156px;background-color: white;"> 类型：
		    	<select id="typeId" name="typeId" style="width: 126px;background-color: white;" >
		    		<c:forEach items="${typeList }" var="type"><option value="${type.id }" >${type.name } </option></c:forEach>
		    	</select>
		    	tbk:<input type="checkbox" id="isTbk">
		    	<div style="margin-top: 10px">
		    	</div>
	    		<script id="editor" type="text/plain" style="width:995px;height:500px;"></script>
	    	</div>
	    	<div><input type="button" onclick="save()" value="发表"> <input type="button" value="预览" onclick="openPostWindow()"></div>
    	</div>
    	
    </div>
    <!--     开始：页面底部 -->
    <div class="aFootBefore"></div>
    <div align="center"  class="menu-box aFootTotal">
    	<jsp:include page="../../../tbk/commonjsp/footer.jsp"></jsp:include>
    </div>
    <!--     结束：页面底部  -->
    <jsp:include page="../../../tbk/commonjsp/commonBottom.jsp"></jsp:include>
    <script type="text/javascript">
    	var ue = UE.getEditor('editor');
    	function save(){
    		var formatGeneral="";
    		var docType="";
    		if($("#qie").prop("checked")){
    			formatGeneral=$("#gentext").val();
    			docType=1;
    		}else{
    			formatGeneral=UE.getEditor('editor').getContentTxt().substr(0,150)+"&&"+$("#general").val();
    			docType=2;
    		}
    		var isTbk=1;
    		if($("#isTbk").prop("checked")==true){
    			isTbk=2;
    		}
    		$.ajax({
    			url:"doc/fastIssueDoc",
    			data:{
    				title:$("#title").val(),
    				tips:$("#tips").val(),
    				typeId:$("#typeId").val(),
    				general:formatGeneral,
    				text:UE.getEditor('editor').getContent(),
    				readtype:isTbk,
    				docType:docType
    			},
    			type:"post",
    			success:function(data){
    				alert(data.message)
    			}
    		})
    	}
    	
    	function openPostWindow(url, data, name){
    		var formatGeneral="";
    		var docType="";
    		if($("#qie").prop("checked")){
    			formatGeneral=$("#gentext").val();
    			docType=1;
    		}else{
    			formatGeneral=UE.getEditor('editor').getContentTxt().substr(0,150)+"&&"+$("#general").val();
    			docType=2;
    		}
    		if($("#title").val()==""){
    			alert("标题别忘记写了！");
    			return;
    		}
    		if($("#tips").val()==""){
    			alert("标签别忘记写了！");
    			return;
    		}
    		
    		$.ajax({
    			url:"doc/openPostWindow",
    			async : false,
    			type:"post",
    			data:{
    				text:UE.getEditor('editor').getContent(),
    				general:formatGeneral,
    				title:$("#title").val(),
    				tips:$("#tips").val(),
    				typeId:$("#typeId").val(),
    				docType:docType
    			},
    			success:function(data){
    				docView();
    			}
    		})
    	}
    	
    	function docView(){
    		window.open ("doc/fastView","newwindow","height=800,width=1300,top=0,left=200,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no") 
    	}
    	
    	function qiehuan(){
    		if($("#qie").prop("checked")){
    			$("#mygeneral").show();
    			$("#docFrom").hide();
    		}else{
    			$("#docFrom").show();
    			$("#mygeneral").hide();
    		}
    	}
	</script>
</body>
</html>