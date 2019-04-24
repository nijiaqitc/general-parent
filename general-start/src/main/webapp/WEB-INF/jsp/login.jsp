<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登陆</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">

<style type="text/css">
td{padding-top:10px;}

input{
	height: 26px;
	-webkit-appearance: textfield;
	padding: 1px;
	background-color: white;
	border: 2px inset;
	border-image-source: initial;
	border-image-slice: initial;
	border-image-width: initial;
	border-image-outset: initial;
	border-image-repeat: initial;
	-webkit-rtl-ordering: logical;
	-webkit-user-select: text;
	cursor: auto;
	width: 250px;
	margin: 0em;
	font: -webkit-small-control;
	color: initial;
	letter-spacing: normal;
	word-spacing: normal;
	text-transform: none;
	text-indent: 0px;
	text-shadow: none;
	display: inline-block;
	text-align: start;
}
button{
	background-color: white;
}
label {
	color: red;
}
</style>

</head>
<body style="background-color: #f1f1f1;">
    <!--     开始：导航条 -->
    <jsp:include page="${path}/top"></jsp:include>
    <div style="height: 20px;width: 100%;background-color: #ec8316;"></div>
    <!--     结束：导航条 -->
    <div class="menu-box"  align="center" style="margin-top: 80px;height: 270px;" >
        <div style="padding-top: 40px;">
	        <form id="loginForm" name="loginForm" action="" >
				<table>
					<input type="hidden" name="jumpurl" value="${jumpurl }">
					<tr style=""><td>用户名：</td><td colspan="2"><input name="loginAccount" id="loginAccount" type="text" maxlength="20" style="width: 250px;"></td></tr>
					<tr><td>密码：</td><td colspan="2"><input id="loginPasswd" name="loginPasswd" type="password" maxlength="20" style="width: 250px;"></td></tr>
					<tr><td>验证码：</td><td><input maxlength="4" type="text" id="code" name="code" style="width: 124px;">
					<img src="${path }/getPic" id="imgCode" onclick="reloadcode()" style="cursor: pointer;display: inline-block;width: 106px;vertical-align: middle;height: 30px;margin-left: 16px;" alt="看不清楚,换一张">
					</td></tr>
					<tr><td colspan="3" ><button type="button" onclick="userLogin()" value="登陆" style="width: 320px;height: 34px;">登陆</button></td></tr>
				</table>        
	        </form>
        </div>
        <div align="center" style="font-size: 12px;" >当前系统暂不支持外部注册，只允许内部推荐！</div>
    </div>
    <!--     开始：页面底部 -->
    <jsp:include page="${path}/bottom"></jsp:include>
    <!--     结束：页面底部  -->
    <script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="${resPath }/front/js/jquery.validate.min.js"></script>
	<script src="${resPath }/back/js/jqueryValidateExtend.js"></script>
	<script type="text/javascript">
		function reloadcode(){
		    var verify=document.getElementById('imgCode');
		    verify.setAttribute('src','${path}/getPic?it='+Math.random());
		}
		
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
		
		function userLogin(){
			if(!$("#loginForm").valid()){
				return;
			}
			if($("#code").val()==undefined||$("#code").val()==""){
				$("#code").val("");
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
					code:$("#code").val(),
					jumpurl:$("#jumpurl").val()
				},
				type:"post",
				dataType: "json",
				success:function(data){
					if(data.state==1){
						if(data.jumpurl){
							window.location.href=data.jumpurl;							
						}else{
							window.location.href="${path}/loginIndex";
						}
					}else{
						reloadcode();
						$("#code").val("");
						alert(data.message)
					}
				}
			}) 
		}
	</script>
</body>
</html>