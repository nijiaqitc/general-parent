<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>正文</title>
<link rel="shortcut icon" href="${resPath }/tbk/images/logo.png" />
<jsp:include page="${path}/commonTopLink"></jsp:include>
<link href="${resPath }/jsTool/njqeditor/css/njqEditor_styleTwo.css"  rel="stylesheet">
<style type="text/css">
	.rightBtnsz {
	    top: 78px;
	}
	.rightBtn {
	    width: 50px;
	    height: 50px;
	    background-color: red;
	    position: fixed;
	    right: 0px;
	    line-height: 50px;
	    text-align: center;
	    cursor: pointer;
        z-index: 9;
	}
	.error{
		color: red;
	}
</style>
</head>
<body>
	<!-- 通用顶部 -->
	<jsp:include page="${path}/commonTop"></jsp:include>
	<!-- 中下部分 -->
	<div id="centerPlace">
		<!-- 通用左边菜单 -->
		<jsp:include page="${path}/commonLeft"></jsp:include>
		<!-- 正文区域 -->
		<div id="rightContext">
			<div style="padding-top: 20px;overflow: auto;">
				<div align="left">
					<div onclick="save()" class="rightBtn rightBtnsz">保存</div>
					<form class="form-horizontal" id="studyForm" action="/admin/studyManage/addOrUpdate"/>
						<fieldset>	
							<div class="control-group">
								<label  class="control-label"  style="float: left;" >父类：</label>
								<div style="margin-left: 180px;">
									<select id="typeId" name="typeId" style="width: 330px;">
										<c:forEach items="${typeList }" var="type">
											<option value="${type.id }">${type.name }</option>
										</c:forEach>
									</select>
									<span style="float: right;">
										确认<input type="checkbox" value="1" id="sure" name="sure"  >
									</span>
								</div>
							</div>
							<div class="control-group">
								<input type="hidden" id="id" name="id" value="${studyInfo.id }">
								<label  class="control-label">题目：</label>
								<div class="controls">
						  			<textarea id="title" name="title"  style="height: 30px;width: 100%;resize: auto;">${studyInfo.title }</textarea>
								</div>
					  		</div>
					  		<div class="control-group" id="generalArea" style="display: none;">
								<label class="control-label">描述：</label>
								<div class="controls" >
									<div prefix="general" name="njqEditorDiv" modelStyle="styleTwo" env="4" >${studyInfo.general }</div>
									<input type="hidden" id="general" name="general" value="${studyInfo.general }">
								</div>
					  		</div>
					  		<div class="control-group" style="line-height: 30px;">
								<label class="control-label"  style="float: left;">类型：</label>
								<div class="controls">
									简答题
									<input type="radio" name="titleType" value="1" checked="checked" onclick="radioClick(this)">
									选择题
									<input type="radio" name="titleType" value="2"  onclick="radioClick(this)">
									编写题
									<input type="radio" name="titleType" value="3"  onclick="radioClick(this)">
								</div>
					  		</div>
					  		<div class="control-group" id="optionsArea" style="display: none;">
								<label class="control-label">选项：</label>
								<div class="controls" >
									<div prefix="options" name="njqEditorDiv" modelStyle="styleTwo" env="4" >${studyInfo.options }</div>
									<input type="hidden" id="options" name="options" value="${studyInfo.options }">
								</div>
					  		</div>
					  		<div class="control-group">
								<label class="control-label">答案：</label>
								<div class="controls" >
									<div prefix="answer" name="njqEditorDiv" modelStyle="styleTwo" env="4" >${studyInfo.answerList[0].answer }</div>
									<input type="hidden" id="answer" name="answer" value="${studyInfo.answerList[0].answer }">
								</div>
					  		</div>
					  		<div class="control-group">
								<label class="control-label">备注：</label>
								<div class="controls">
									<div prefix="columDesc" name="njqEditorDiv" modelStyle="styleTwo" env="4" >${studyInfo.answerList[0].columDesc }</div>
									<input type="hidden" id="columDesc" name="columDesc" value="${studyInfo.answerList[0].columDesc }">
								</div>
					  		</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	    <!--     加载用户自定义配置 -->
	    <script type="text/javascript" src="${resPath }/jsTool/njqeditor/js/njqEditor_config.js"></script>
		<!-- 通用底部 -->
		<jsp:include page="${path}/commonBottom"></jsp:include>
	</div>
	<jsp:include page="${path}/commonBottomLink"></jsp:include>
	<script src="${resPath }/common/js/publicJs.js"></script>
	<!-- start:公共页，存放公共框 -->
	<jsp:include page="${path}/publicJsp"></jsp:include>
	<!-- jqueryvalide验证 -->
	<script src="${resPath }/back/js/jquery.validate.min.js"></script>
	<script src="${resPath }/back/js/jqueryValidateExtend.js"></script>
	<!-- end:公共页，存放公共框 -->
	<script type="text/javascript">
		$("#studyForm").validate({
			rules:{
				title:{
					required:true,
					minlength:2
				}
				
			},
			messages:{
				title:{
					required:"不能为空！",
					minlength:"最小长度为{0}"
				}
			}
		}) 
		
		
		$(function(){
			setTimeout(function () {
				var tiv = "${studyInfo.titleType }"; 
				$.each($("input[name='titleType']"),function(a,b){
					if($(b).val() == tiv){
						$(b).click();
					}
				}); 
	        }, 500);
			
    	});
    	
    	
		
		function radioClick(target){
			if($(target).val()==2){
				$("#generalArea").show();
				$("#optionsArea").show();
			}else{
				$("#generalArea").hide();
				$("#optionsArea").hide();
				njqEditor.editorNodes[0].api.setContent("");
				njqEditor.editorNodes[1].api.setContent("");
			}
		}
		
		
		function save(){
			if(!$("#studyForm").valid()){
				return;
			}
			if(njqEditor.editorNodes[0].api.hasText() == ""){
				$("#general").val("");			
			}else{
				$("#general").val(njqEditor.editorNodes[0].api.getContent());
			}
			if(njqEditor.editorNodes[1].api.hasText() == ""){
				$("#options").val("");
			}else{
				$("#options").val(njqEditor.editorNodes[1].api.getContentTxt());
			}
			if(njqEditor.editorNodes[2].api.hasText() == ""){
				$("#answer").val("");			
			}else{
				$("#answer").val(njqEditor.editorNodes[2].api.getContent());
			}
			if(njqEditor.editorNodes[3].api.hasText() == ""){
				$("#columDesc").val("");
			}else{
				$("#columDesc").val(njqEditor.editorNodes[3].api.getContent());
			}
			
			showMsg("确认","确认添加？",function(t){
				if(t){
					$.ajax({
						url:"${path}/admin/studyManage/addOrUpdate",
						type:"post",
						data:$("#studyForm").serialize(),
						success:function(data){
							if(data.state==1){
								showSureMessage("提示",data.message);
								location.reload(); 
							}
						}
					})
				}
			})
		}
	</script>
</body>
</html>