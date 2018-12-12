<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>Insert title here</title>
<jsp:include page="${basePath}/frontHeadJs"></jsp:include> 
<link href="${resPath }/chajian/login/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${resPath }/chajian/login/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${resPath }/front/js/jquery.validate.min.js"></script>
<script src="${resPath }/back/js/jqueryValidateExtend.js"></script>
<style type="text/css">
	label.error{
		color:red;
		margin-left: 64px;
	}
</style>
</head>
<body>
<jsp:include page="${basePath}/frontHead"></jsp:include>
<!-- 代码 开始 -->
	<div class="wrap">
		<div class="banner-show" id="js_ban_content">
			<div class="cell bns-01">
				<div class="con">
				</div>
			</div>
			<div class="cell bns-02" style="display:none;">
				<div class="con">
					
				</div>
			</div>
			<div class="cell bns-03" style="display:none;">
				<div class="con">
					
				</div>
			</div>
		</div>
		<div class="banner-control" id="js_ban_button_box">
			<a href="javascript:;" class="left">左</a>
			<a href="javascript:;" class="right">右</a>
		</div>
		<div class="container">
			<div id="registDiv" class="register-box" style="left: 0;filter:alpha(opacity=0);-moz-opacity:0; opacity:0;">
				<div class="reg-slogan" style="margin-top: 25px;">
					<span style="font-size: 30px;font-weight: bold;color: #999"> 注册</span>	
				</div>
				<div style="width: 328px;height: 280px;margin: auto;margin-top: 10px;" id="js-form-mobile">
					<form id="regForm" >
						<div style="margin-top: 10px;">
							<label style="font-size: 16px;color: #999;font-weight: bold;" >账&#12288;号:&nbsp;</label>
							<input type="text" id="regAccount" name="regAccount" style="width: 245px;" id="js-mobile_ipt" class="text" maxlength="11" />
						</div>
						<div style="margin-top: 10px;">
							<label style="font-size: 16px;color: #999;font-weight: bold;" >昵&#12288;称:&nbsp;</label>
							<input type="text" id="regUserName" name="regUserName" style="width: 245px;" id="js-mobile_ipt" class="text" maxlength="11" />
						</div>
						<div style="margin-top: 10px;">
							<label style="font-size: 16px;color: #999;font-weight: bold;" >密&#12288;码:&nbsp;</label>
							<input type="password" id="regPwd" name="regPwd" style="width: 245px;" id="js-mobile_pwd_ipt" class="text" />
						</div>
						<div style="margin-top: 10px;">
							<label style="font-size: 16px;color: #999;font-weight: bold;" >重&#12288;复:&nbsp;</label>
							<input type="password" id="regrePwd" name="regrePwd" style="width: 245px;" id="js-mobile_pwd_ipt" class="text" />
						</div>
						<div class="bottom" style="margin-top: 20px;">
							<a id="js-mobile_btn" href="javascript:void(0)" style="width: 300px;" onclick="regirst()" class="button btn-green">
							立即注册</a></div>
					</form>
				</div>
			</div>
			
			<div id="loginDiv" class="register-box" style="filter:alpha(opacity=100);-moz-opacity:1; opacity:1;">
				<div class="reg-slogan" style="margin-top: 25px;">
					<span style="font-size: 30px;font-weight: bold;color: #999"> 登录 </span>
				</div>
				<div style="width: 328px;height: 280px;margin: auto;margin-top: 10px;" id="js-form-mobile">
					<form id="loginForm" >
						<div>
							<label style="font-size: 16px;color: #999;font-weight: bold;" >账&#12288;号:&nbsp;</label>
							<input type="text" id="loginAccount" name="loginAccount" style="width: 245px;" id="js-mobile_ipt" class="text" />
						</div>
						<div style="margin-top: 10px;">
							<label style="font-size: 16px;color: #999;font-weight: bold;">密&#12288;码:&nbsp;</label>
							<input type="password" id="loginPasswd" name="loginPasswd" style="width: 245px;" id="js-mobile_pwd_ipt" class="text" />
						</div>
						<div style="margin-top: 10px;">
							<label style="font-size: 16px;color: #999;font-weight: bold;vertical-align: middle;" >验证码:&nbsp;</label>
							<input type="text" id="code" name="code"  maxlength="4" style="height: 36px;vertical-align: middle;" />
							<img src="<%=basePath%>getPic" id="imgCode" onclick="reloadcode()" style="cursor: pointer;display: inline-block;width: 112px;vertical-align: middle;height: 40px;" alt="看不清楚,换一张">
<!--  							<a href="javascript:void(0)" style="left: 144px; width: 182px; padding: 0; font-size: 14px;" >免费获取验证码</a>   -->
						</div>
						<div style="margin-top: 20px;">
							<a id="js-mobile_btn" href="javascript:void(0)" style="width: 120px;" onclick="userLogin()" class="button btn-green">登录</a>&nbsp;&nbsp;&nbsp;	&nbsp;&nbsp;
							<a id="js-mobile_btn" href="javascript:void(0)" style="width: 120px;" onclick="showOthers(1)" class="button btn-green">注册</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$(document).bind("keypress",function(obj){
		if(obj.keyCode==13){
			if(obj.target.name=="loginAccount"){
				$("input[name=loginPasswd]").focus();
			}else if(obj.target.name=="loginPasswd"){
				$("input[name=code]").focus();
			}else{
				userLogin();
			}
		}
	})
	
	function reloadcode(){
        var verify=document.getElementById('imgCode');
        verify.setAttribute('src','<%=basePath%>getPic?it='+Math.random());
	}
		
		$("#loginForm").validate({
			onkeyup:false,
			onclick:false,
			rules: {
				loginAccount: {
					required:true,
					maxlength:16,
					minlength:2
				},
				loginPasswd: {
					required:true,
					maxlength:16,
					minlength:8
				}
			},
			messages: {
				loginAccount: {
					required:"*必填",
					maxlength:"*最大长度为{0}",
					minlength:"*最小长度为{0}"
				},
				loginPasswd: {
					required:"*必填",
					maxlength:"*最大长度为{0}",
					minlength:"*最小长度为{0}"
				}
			}
		});
	
		$("#regForm").validate({
			onkeyup:false,
			onclick:false,
			rules: {
				regAccount: {
					required:true,
					maxlength:16,
					minlength:8
				},
				regUserName: {
					required:true,
					maxlength:16,
					minlength:2
				},
				regPwd:{
					required:true,
					maxlength:16,
					minlength:8,
					notChinese:true
				},
				regrePwd:{
					required:true,
					equalTo:"#regPwd"
				}
			},
			messages: {
				regAccount: {
					required:"*必填",
					maxlength:"*最大长度为{0}",
					minlength:"*最小长度为{0}"
				},
				regUserName: {
					required:"*必填",
					maxlength:"*最大长度为{0}",
					minlength:"*最小长度为{0}"
				},
				regPwd:{
					required:"*必填",
					maxlength:"*最大长度为{0}",
					minlength:"*最小长度为{0}"
				},
				regrePwd:{
					required:"*必填",
					equalTo:"两次密码不相同！"
				}
			}
		});
		
		(function(){
			var defaultInd = 0;
			var list = $('#js_ban_content').children();
			var count = 0;
			var change = function(newInd, callback){
				if(count) return;
				count = 2;
				$(list[defaultInd]).fadeOut(400, function(){
					count--;
					if(count <= 0){
						if(start.timer) window.clearTimeout(start.timer);
						callback && callback();
					}
				});
				$(list[newInd]).fadeIn(400, function(){
					defaultInd = newInd;
					count--;
					if(count <= 0){
						if(start.timer) window.clearTimeout(start.timer);
						callback && callback();
					}
				});
			}
			
			var next = function(callback){
				var newInd = defaultInd + 1;
				if(newInd >= list.length){
					newInd = 0;
				}
				change(newInd, callback);
			}
			
			var start = function(){
				if(start.timer) window.clearTimeout(start.timer);
				start.timer = window.setTimeout(function(){
					next(function(){
						start();
					});
				}, 8000);
			}
			
			start();
			
			$('#js_ban_button_box').on('click', 'a', function(){
				var btn = $(this);
				if(btn.hasClass('right')){
					//next
					next(function(){
						start();
					});
				}
				else{
					//prev
					var newInd = defaultInd - 1;
					if(newInd < 0){
						newInd = list.length - 1;
					}
					change(newInd, function(){
						start();
					});
				}
				return false;
			});
			
		})();
	
		var FancyForm=function(){
			return{
				inputs:".reg-form input",
				setup:function(){
					var a=this;
					this.inputs=$(this.inputs);
					a.inputs.each(function(){
						var c=$(this);
						a.checkVal(c)
					});
					a.inputs.live("keyup blur",function(){
						var c=$(this);
						a.checkVal(c);
					});
				},checkVal:function(a){
					a.val().length>0?a.parent("div").addClass("val"):a.parent("div").removeClass("val")
				}
			}
		}();
	
		$(document).ready(function() {
			FancyForm.setup();
		});
		var right=1;
		var left=0;
		var v=0.2;
		function showOthers(type){
			$.post(
				"${path}/",
				{},
				function(data){
					
				});
			if(type==1){
				v=0.2;
				$("#registDiv").css("display","block");
				showRegirst();
			}else{
				v=-0.2;
				$("#loginDiv").css("display","block");
				showLogin();
			}
		}
		function showLogin(){
			right=right-v;
			left=left+v;
			$("#loginDiv").css("opacity",right);
			$("#registDiv").css("opacity",left);
			if(right<=1){
				setTimeout("showLogin()", 400);
			}else{
				$("#registDiv").css("display","none");
			}
		}
		function showRegirst(){
			right=right-v;
			left=left+v;
			$("#loginDiv").css("opacity",right);
			$("#registDiv").css("opacity",left);
			if(right>=0){ 
				setTimeout("showRegirst()", 400);
			}else{
				$("#loginDiv").css("display","none");
			}
		}
		
		function userLogin(){
			if(!$("#loginForm").valid()){
				return;
			}
			if($("#code").val()==undefined||$("#code").val()==""){
				alert("请填写验证码");
				return;
			}
			if($("#code").val().length!=4){
				alert("请填写正确的验证码");
				return;
			}
			$.ajax({
				url:"${path}/loginCheck",
				data:{
					account:$("#loginAccount").val(),
					pwd:$("#loginPasswd").val(),
					code:$("#code").val()
				},
				type:"post",
				dataType: "json",
				success:function(data){
					if(data.state==1){
						window.location.href="${path}/loginIndex";
					}else{
						reloadcode();
						$("#code").val("");
						alert(data.message)
					}
				}
			}) 
		}
		
		function regirst(){
			if(!$("#regForm").valid()){
				return;
			}
			$.ajax({
				url:"${path}/register",
				data:{
					account:$("#regAccount").val(),
					pwd:$("#regPwd").val(),
					userName:$("#regUserName").val()
				},
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.state==1){
						alert("注册成功！ ");
						v=-0.2;
						$("#loginDiv").css("display","block");
						showLogin();
						$("#regAccount").val("");
						$("#regUserName").val("");
						$("#regPwd").val("");
						$("#regrePwd").val("");
					}else{
						alert(data.message);
					}
				}
			})
		}
		
		$(document).ready(function() {
			var h=document.documentElement.clientHeight;
			var w=document.documentElement.clientWidth ;
			if(h>864){
				var d = h-864;
				$("#customBottom").css("padding-top",(100+d));
			}
			if(w>1094){
				$("#customBottom").css("width","");
			}else{
				$("#customBottom").css("width",1094);	
			}
			
		});
		window.onresize = function () {
			var h=document.documentElement.clientHeight;
			var w=document.documentElement.clientWidth ;
			if(h>864){
				var d = h-864;
				$("#customBottom").css("padding-top",(100+d));
			}
			if(w>1094){
				$("#customBottom").css("width","");
			}else{
				$("#customBottom").css("width",1094);	
			}
		}
	</script>
	<!-- 代码 结束 -->
	<div id="customBottom" style="position: relative;background: #1d1d1d;display: block;color: #FFF;padding-top: 100px;padding-bottom: 2em;font-size: 1.4em;" >
		<div style="margin-top: 20px;">
			<div align="center" style="color: rgba(255,255,255,.5);" class="aTextStyle" >
				Copyright &copy; 2014.Company name All rights reserved.
			</div>
		</div>
	</div>
</body>
</html>