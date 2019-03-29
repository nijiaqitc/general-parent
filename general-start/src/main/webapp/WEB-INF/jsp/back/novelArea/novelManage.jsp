<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小说管理</title>
<jsp:include page="${path}/commonTopLink"></jsp:include>
<style type="text/css">
	.box-icon{
		background: #1e8fc6;
    	float: right;
	}
	.icon-plus{
		display: inline-block !important;
	    color: #fff;
	    text-align: center;
	    width: 36px;
	    padding: 10px 0;
	    -webkit-transition: all .1s ease-in-out;
	    -moz-transition: all .1s ease-in-out;
	    -ms-transition: all .1s ease-in-out;
	    -o-transition: all .1s ease-in-out;
	    transition: all .1s ease-in-out;
	    opacity: .8;
	    filter: alpha(opacity=80);
	    -ms-filter: "alpha(Opacity=80)";
	    border-left: 1px solid #36a9e1;
	    text-decoration: none;
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
			<div class="menu-box barAreaDiv" >
				<div class="barTopw">
					<h2 class="barLeftDiv">
                        <i class="icon-ambulance leftLogo"></i> 小说展示
                    </h2>
                    <div class="box-icon">
						<a href="javascript:void(0)"  onclick="showDialogForSave()" ><i class="icon-plus"></i></a>
					</div>
				</div>
			</div>
		     <c:forEach items="${list }" var="doc">
	             <div class="menu-box" style="width: 150px;height: 220px;background-color: white;float: left;margin-left: 30px;margin-top: 20px;">
			         <a href="novelTitleList?docId=${doc.id }">
				         <div style="width: 100%;height: 200px;clear: both;">
					         <div style="width: 30px;font-size: 18px;margin-left: 10px;padding-top: 10px;">
						         ${doc.title}
					         </div>
				         </div>
			         </a>
				     <div style="width: 100%;font-size: 12px;overflow: auto;">
				         <i class="icon-heart nnum1i" style="color: red;line-height: 20px;margin-right: 4px;margin-left: 10px;"></i><span style="float: left;">(1000)</span>
				         <i class="icon-thumbs-down nnum1i" style="color: red;line-height: 20px;margin-right: 4px;margin-left: 10px;"></i><span style="float: left;">(1000)</span>
				         <i class="icon-eye-open nnum1i" style="color: red;line-height: 20px;margin-right: 4px;margin-left: 10px;"></i><span style="float: left;">(1000)</span>   
				     </div>
			    </div>
		     </c:forEach>
		</div>
		
		
		
		<div id="myModal" class="custom-customModel" style="display: none;">
			<form action="addTitle" method="post" id="novelForm">
				<div class="custom-customModel-header">
					<h3 id="dialogTitleName"></h3>
				</div>
				<div class="custom-customModel-body" style="height: 350px;">
					<div class="body-div1" align="center">
						<div align="left" style="width: 310px;">
							<input type="hidden" id="id" name="id" >
							<div style="padding: 4px;margin-top: 10px;">名称：
								<input id="title" type="text" style="width: 300px;background: #fff;" name="title">
							</div>
							<div style="padding: 4px;margin-top: 20px;">概要：
								<textarea id="contextDesc" name="contextDesc"  style="width: 300px;height: 230px;background: #fff;"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="custom-customModel-bottom" >
					<a href="javascript:void(0)" id="closeBtn" class="custom-customModel-btnCancel" onclick="closeDialog()">关闭</a>
					<a href="javascript:void(0)" id="agreeBtn" class="custom-customModel-btnCancel" onclick="save()">确定</a>
					<a href="javascript:void(0)" id="modiBtn" class="custom-customModel-btnCancel" onclick="update()">修改</a>
				</div>
			</form>
		</div>
		<!--end:弹出框-->
		<!--start:遮罩层-->
		<div id="custom-background" style="display: none;"></div>
		<!--end:遮罩层-->
		
		
		<!-- 通用底部 -->	
		<jsp:include page="${path}/commonBottom"></jsp:include>
	</div>
</body>
<jsp:include page="${path}/commonBottomLink"></jsp:include>
<script src="${resPath }/back/js/jquery.validate.min.js"></script>
<script src="${resPath }/back/js/jqueryValidateExtend.js"></script>
<script type="text/javascript">
$("#novelForm").validate({
	rules:{
		title:{
			required:true,
			maxlength:10,
			minlength:2
		}
		
	},
	messages:{
		title:{
			required:"不能为空！",
			maxlength:"最大长度为{0}",
			minlength:"最小长度为{0}"
		}
	}
}) 
/**
 * 新增显示弹出框
 */
function showDialogForSave(e){
    $("#agreeBtn").show();
	$("#modiBtn").hide();
	$("#dialogTitleName").html("新增小说");
	$("#myModal").show();
	$("#custom-background").show();
}

/**
 * 关闭弹出框
 */
function closeDialog(){
	$("#myModal").hide();
	$("#custom-background").hide();
	$("#title").val("");
	$("#contextDesc").val("");
	$("#novelForm").validate().resetForm();
}

/**
 * 保存数据
 */
function save(){
	if(!$("#novelForm").valid()){
		return;
	}
	showMsg("确认","确认添加？",function(t){
		if(t){
			$.ajax({
				url:"${path}/novelManage/addNovel",
				type:"post",
				data:$("#novelForm").serialize(),
				success:function(data){
					if(data.state==1){
						closeDialog();
					}
					showSureMessage("提示",data.message);
				}
			})
		}
	})
}
/**
 * 修改数据
 */
function update(){
	if(!$("#novelForm").valid()){
		return;
	}
	showMsg("确认","确认修改？",function(t){
		if(t){
			$.ajax({
				url:"${path}/novelManage/updateNovel",
				data:$("#novelForm").serialize(),
				type:"post",
				success:function(data){
					if(data.state==1){
						closeDialog();
					}
					showSureMessage("提示",data.message);
				}
			})
		}
	})
}
</script>
</html>