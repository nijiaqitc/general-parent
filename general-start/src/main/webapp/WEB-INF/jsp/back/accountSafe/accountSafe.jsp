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
	<title>正文</title>
	<jsp:include page="${path}/head"></jsp:include>
	<jsp:include page="${path}/foot"></jsp:include>
	<style type="text/css">
		tr{
		    height: 100px;
    		vertical-align: middle;
    		border-bottom: 1px #ddd dashed;
		}
		
		.jinfo{
			font-size:12px;
			height: 26px;
			line-height: 70px;
		}
		
		.modi{
			color: green;
		}
		
		.unmodi{
			color:#FF9800;
		}
		.modi1{
			color: #06C;
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
				<div class="row-fluid" style="max-width: 1060px;">
					<div class="box span6" style="width: 100%;" >
						<div class="box-content custom_pagination" style="height: 800px;" >
							<div style="width: 1050px;" >
								<div style="border-bottom: 1px #ddd solid;height: 160px;margin-top: 20px;">
									<div style="width: 160px;height: 120px;float: left;" align="center">
										<div style="width: 120px;height: 120px;border-radius:50%; overflow:hidden;">
											<c:if test="${user.picPlace!=null }">
												<img id="docPic" src="${user.picPlace}" style="width: 130px;height: 130px;" alt="头像" />
											</c:if>
											<c:if test="${user.picPlace==null }">
												<img id="docPic" src="${resPath }/back/img/avatar.jpg" style="width: 130px;height: 130px;" alt="头像" /> 
											</c:if>
											<input type="hidden" id="picId">
											<input type="hidden" id="picPlace">
										</div>
										<div style="font-size: 12px;color: #06C;cursor: pointer;" onclick="showDialogForSave()">修改头像</div>
									</div>
									<div class="jinfo">登录账号:&nbsp;&nbsp;&nbsp;${user.account }</div>
									<div class="jinfo">邮箱地址:&nbsp;&nbsp;&nbsp;${user.email }</div>
									<div class="jinfo">创建时间:&nbsp;&nbsp;&nbsp;${user.createDate }</div>
								</div>
								<div style="clear: both;height: 60px;line-height: 60px;border-bottom: 1px #ddd dashed;">
									<span style="margin-left: 40px;">请选择以下内容进行修改↓</span>
								</div>
								<div>
									<table align="center" style="width: 100%;">
										<tr>
											<td style="width: 100px;font-weight: bolder;" align="center" >登录密码</td><td style="width: 640px;">定期更换密码能保障您账户的安全，但请一定要记住更换后的密码，避免您自己也无法登录，设置登录密码建议有大写、小写、数字。</td><td></td><td  style="width: 50px;" class="modi">已设置</td><td style="width: 80px;" ><a class="modi1" href="${path }/accountSafe/modipwd">修改</a></td>
										</tr>
										<tr>
											<td style="font-weight: bolder;" align="center">手机号码</td><td>站长比较穷没钱发短信，手机号只作为后期通信的选择手段之一。</td><td></td><td class="modi">已设置</td><td><a class="modi1" href="${path }/accountSafe/moditel">修改</a></td>
										</tr>
										<tr>
											<td style="font-weight: bolder;" align="center">邮箱地址</td><td>目前以邮箱地址为本站的主要通信手段，请绑定一个您常用的邮箱地址，本站不会发送任何广告消息打扰您。</td><td></td><td class="modi" >已设置</td><td><a class="modi1" href="${path }/accountSafe/modiemail">修改</a></td>
										</tr>
										<tr style="border-bottom: 0px;">
											<td style="font-weight: bolder;" align="center">个人信息</td><td>作为以后提交申请密码找回的准则之一，不做强制实名要求。</td><td></td><td class="unmodi">未设置</td><td><a class="modi1" href="#">设置</a></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
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
						<iframe id="upPicTool" src="${path}/totalInfo/jcrop" frameborder="no" style="width: 1000px;height: 500px;margin-left: -232px;margin-top: -35px;"></iframe>
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
	
	<script type="text/javascript">
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
				
				$("#picId").val(bbb);
				$("#picPlace").val(aaa);
				$.ajax({
					url:"${path}/accountSafe/upUserPic",
		  			type:"post",
		  			data:{
		  				picPlace:aaa
		  			},
		  			success:function(data){
		  				if(data.state==1){
		  					$("#docPic").attr("src",aaa);
		  					$("#topPic").attr("src",aaa);
		  				}
		  			}
				})
			}
		}
	</script>
</body>
</html>