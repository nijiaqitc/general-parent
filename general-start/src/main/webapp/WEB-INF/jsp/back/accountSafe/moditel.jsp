<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>正文</title>
	<jsp:include page="${basePath}/head"></jsp:include>
	<jsp:include page="${basePath}/foot"></jsp:include>
	<style type="text/css">
		.btn_red{
			width: 120px;
    		height: 40px;
    		margin: 0 15px;
    		-webkit-border-radius: 3px;
        	background: #f2463d!important;
        	box-shadow: none;
        	color: #fff;
        	padding-top: 6px;
        	padding-bottom: 6px;
            border: 0;
            line-height: 20px;
            padding: 4px 20px;
            cursor: pointer;
            font-family: proxima-nova,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","Helvetica Neue",Helvetica,Arial,sans-serif,"Wenquanyi Micro Hei","WenQuanYi Micro Hei Mono","WenQuanYi Zen Hei","WenQuanYi Zen Hei","Apple LiGothic Medium",SimHei,"ST Heiti","WenQuanYi Zen Hei Sharp";
    		font-size: 14px;
		}
		.btn_grey{
			width: 120px;
    		height: 40px;
    		margin: 0 15px;
    		-webkit-border-radius: 3px;
        	background: #666!important;
        	box-shadow: none;
        	color: #fff;
        	padding-top: 6px;
        	padding-bottom: 6px;
            border: 0;
            line-height: 20px;
            padding: 4px 20px;
            cursor: pointer;
            font-family: proxima-nova,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","Helvetica Neue",Helvetica,Arial,sans-serif,"Wenquanyi Micro Hei","WenQuanYi Micro Hei Mono","WenQuanYi Zen Hei","WenQuanYi Zen Hei","Apple LiGothic Medium",SimHei,"ST Heiti","WenQuanYi Zen Hei Sharp";
    		font-size: 14px;
		}
	</style>
	<script type="text/javascript">
		function sendCode(){
			$.ajax({
				url:"${path}/accountSafe/sendCode",
				data:{
					type:2
				},
				type:"post",
				success:function(data){
				}
			})
		}
		function countdown() {
			var wait = 59; // 设置秒数(单位秒)
			var secs = 0;
			sendCode();
			for (var i = 0; i < wait; i++) {
				window.setTimeout("sTimer(" + i + "," + wait + "," + secs + ")", i * 1000);
			}
		}

		function sTimer(num, wait, secs) {
			if (num == wait) {
				$('#sendVerifyCode').html('获取验证码');
				disableButton(false);
			} else {
				secs = wait - num;
				$('#sendVerifyCode').html('已发送' + secs);
				disableButton(true);
			}
		}
		function disableButton(flag) {
			if (flag)
				$('#sendVerifyCode').removeClass("btn_red").addClass("btn_grey").attr('disabled', true);
			else
				$('#sendVerifyCode').removeClass("btn_grey").addClass("btn_red").removeAttr('disabled');
		}
		
		function toNext(index){
			if(index==1){
				$.ajax({
					url:"${path}/accountSafe/checkCode",
					data:{
						code:$("#emailCode").val()
					},
					type:"post",
					success:function(data){
						if(data.state==1){
							$("#modiPwd").show();
							$("#checkCoke").hide();
							$($(".steps span")[1]).css("background-color","#ddf3ff");
							$($(".steps span")[1]).css("border","1px solid #36a9e1");
							$($(".steps span")[0]).css("border","0px");
						}else{
							$("#codeError").show();
						}
					}
				})
				
			}else if(index==2){
				if($("#input2").val().length!=11){
					$("#pwdError").html("*不正确的手机号!");
					$("#pwdError").show();
					return 
				}
				
				$.ajax({
					url:"${path}/accountSafe/upTel",
					data:{
						newTel:$("#input2").val()
					},
					type:"post",
					success:function(data){
						if(data.state==1){
							$("#modiPwd").hide();
							$("#lastStep").show();
							$($(".steps span")[2]).css("background-color","#ddf3ff");
							$($(".steps span")[2]).css("border","1px solid #36a9e1");
							$($(".steps span")[1]).css("border","0px");
						}else{
							$("#pwdError").html(data.message);
							$("#pwdError").show();
						}
					}
				})
			}
			
		}
	</script>
	
	
	
</head>
<body>
	<!-- start:公共页，存放公共框 -->
	<jsp:include page="${basePath}/publicJsp"></jsp:include>
	<!-- end:公共页，存放公共框 -->
	<!-- start: 顶部菜单 -->
	<jsp:include page="../public/top.jsp"></jsp:include>
	<!-- end: 顶部菜单 -->
	<div class="container-fluid-full">
		<div class="row-fluid">
			<!-- start: 左边菜单 -->
<!-- 			<jsp:include page="${basePath}/left"></jsp:include> 
			<!-- end: 左边菜单 -->
			<!-- start: 正文 -->
			<div id="content" class="span10" >
				<div class="span3 smallstat box mobileHalf" style="width: 900px;height: 700px;">
					<div align="center" style="font-size: 30px;font-weight: bolder;margin-top: 30px;">手机号码</div>
					<div style="margin-top: 50px;">
						<div id="MyWizard" class="wizard">
							<ul class="steps">
								<li data-target="#step1" class="active" style="padding-left: 120px;width: 90px;"><span class="badge badge-info" style="width: 120px;background-color: #ddf3ff;">获取验证</span></li>
								<li data-target="#step2" style="padding-left: 120px;"><span class="badge" style="width: 120px;">替换手机</span></li>
								<li data-target="#step3" style="padding-left: 120px;width: 220px;"><span class="badge" style="width: 120px;" >操作完成</span></li>
							</ul>
							<!-- <div class="actions">
								<button type="button" class="btn btn-prev"> <i class="icon-arrow-left"></i> Prev</button>
								<button type="button" class="btn btn-success btn-next" data-last="Finish">Next <i class="icon-arrow-right"></i></button>
							</div> -->
						</div>
					</div>
					<div id="checkCoke" align="center" style="margin-top: 40px;">
						邮箱验证码：
						<input id="emailCode" type="text" style="margin-top: 10px;height: 30px;" >
						<button class="btn_red" id="sendVerifyCode" type="button" onclick="countdown()">获取验证码</button>
						<div id="codeError" style="font-size: 12px;color: red;margin-left: -150px;display: none;">*验证码错误，请重新输入!</div>
						<div><button class="btn_red" type="button" onclick="toNext(1)" style="width: 450px;margin-left: 0px;margin-top: 10px;">下一步</button></div>
					</div>
					
					<div id="modiPwd" align="center" style="display: none;margin-top: 60px;">
						 <div class="step-pane active" id="step2" align="center">
								<form class="form-horizontal">
									<fieldset>	
								  		<div class="control-group" style="margin-top: 30px;">
											<label class="control-label" for="input2" style="width: 280px;">新手机号：</label>
											<div class="controls">
									  			<input type="text" id="input2" maxlength="16" style="width: 350px;margin-left: -210px;">
											</div>
								  		</div>
								  		<div id="pwdError" style="font-size: 12px;color: red;margin-left: -150px;display: none;"></div>
								  		<div><button class="btn_red" type="button" onclick="toNext(2)" style="width: 450px;margin-left: 0px;margin-top: 10px;">下一步</button></div>
									</fieldset>
								</form>
							</div>
					</div>
					<div id="lastStep" style="display: none;" align="center">
						<div style="font-size: 20px;margin-top: 100px;">恭喜，您的账号修改完成！请<a href="javascript:void(0)" style="font-size: 24px;color: red;" onclick="loginOut()">重新登录</a>进行数据更新</div>
					</div>
				</div>
			</div>
			<!-- end: 正文 -->
		</div>
		<jsp:include page="${basePath}/boom"></jsp:include>
	</div>
</body>
</html>