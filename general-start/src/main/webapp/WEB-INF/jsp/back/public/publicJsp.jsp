<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style type="text/css">
.sgBtn{
    width: 135px; 
    height: 35px; 
    line-height: 35px; 
    margin-left: 10px; 
    margin-top: 10px; 
    text-align: center; 
    background-color: #0095D9; 
    color: #FFFFFF; 
    float: left; 
    border-radius: 5px;
}
.ajaxLoadCss{
    position: fixed;z-index: 3050;width: 300px;top:30%;left:45%;display: none;
}
.ajaxLoadCss .dbox{
    margin-left: 0;width: 270px;display: block;float: none;box-sizing: border-box;min-height: 30px;
    border-radius: 2px;box-shadow: 0 1px 0 1px #e4e6eb;overflow: hidden;margin: -20px 0 28px 0;
}
.ajaxLoadCss .dbox .header{
    background: #36a9e1;color: #fff;font-size: 16px;overflow: hidden;height: 33px;line-height: 33px;
}
.ajaxLoadCss .dbox .header .ti{
    margin-left: 15px;
}
.ajaxLoadCss .dbox .downBody{
    height: 60px;padding: 10px;background: #fff;
}
.ajaxLoadCss .dbox .downBody .censt{
    margin-top: 20px;
}
.ajaxLoadCss .dbox .downBody .censt .cstex{
    cursor: text;display: inline;
}
.cusModelOne{
    display: none;opacity: .8;position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	z-index: 1040;
	background-color: #000;
	transition: opacity .15s linear;
}
.cusModelTwo{
    position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: #666666; opacity: 0.5; z-index: 1100;display: none;
}
.sureMessageDlg{
    position: fixed;z-index: 6150;width: 300px;top:30%;left:45%;display: none;
}
.sureMessageDlg .dbox{
    margin-left: 0;width: 270px;display: block;float: none;box-sizing: border-box;min-height: 30px;
    border-radius: 2px;box-shadow: 0 1px 0 1px #e4e6eb;overflow: hidden;margin: -20px 0 28px 0;
}
.sureMessageDlg .dbox .header{
    background: #36a9e1;color: #fff;font-size: 16px;overflow: hidden;height: 33px;line-height: 33px;
}
.sureMessageDlg .dbox .header .ti{
    margin-left: 15px;
}
.sureMessageDlg .dbox .downBody{
    height: 80px;padding: 10px;background: #fff;
}
.sureMessageDlg .dbox .downBody .censt{
    margin-top: 10px;
}
.sureMessageDlg .dbox .downBody .censt .dlgTitle{
    cursor: text;display: inline;font-weight: bold;
}
.sureMessageDlg .dbox .downBody .censt .btn1{
    margin-top: 15px;
}
.sureMessageDlg .dbox .downBody .censt .bb{
    color: #fff;
	background: #36a9e1;
	border: 0;
	border-radius: 2px;
	text-shadow: none;
	border-color: rgba(0,0,0,0.1) rgba(0,0,0,0.1) rgba(0,0,0,0.25);
	display: inline-block;
	padding: 4px 12px;
	margin-bottom: 0;
	font-size: 14px;
	line-height: 20px;
	text-align: center;
	vertical-align: middle;
	cursor: pointer;
}
.sureMessageDlg .dbox .downBody .censt .bb:hover{
    background-color: #08c;
}
.xxcConfirm .xlayer{
    position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: #666666;
	opacity: 0.5;
	z-index: 999;
}
.xxcConfirm .pobox{
    position: fixed;
	left: 70%;
	top: 50%;
	background-color: #ffffff;
	z-index: 2147000001;
	width: 270px;
	height: 140px;
	margin-left: -285px;
	margin-top: -150px;
	border-radius: 5px;
	color: #535e66;
}
.xxcConfirm .pobox .tbox{
    height: 30px;
	line-height: 30px;
	padding: 0px 0px;
	border-bottom: solid 1px #eef0f1;
	background: #36a9e1;
}

.xxcConfirm .pobox .tbox .tt{
    font-size: 13px;
	font-weight: bold;
	margin-left: 15px;
	display: block;
	float: left;
	height: 30px;
	position: relative;
}

.xxcConfirm .pobox .textbox{
    margin: 0px 0px;
	height: 50px;
	overflow: hidden;
	text-align: center;
	font-weight: bold;
}

.xxcConfirm .pobox .textbox .pp{
    height: 84px;
	margin-top: 16px;
	line-height: 26px;
	overflow-x: hidden;
	overflow-y: auto;
}

.xxcConfirm .pobox  .btnArea .btnGroup{
    margin-left: 65px;
}
.xxcConfirm .pobox  .btnArea .btnGroup .sgBtnt{
    margin-top: 14px;
    margin-right: 10px;
    display: block;
	font-size: 14px;
	cursor: pointer;
	float: left;
	width: 50px;
	height: 30px;
	line-height: 30px;
	text-align: center;
	color: #FFFFFF;
	border-radius: 5px;
	background-color: #0095D9;
	margin-left: 10px;
}
.xxcConfirm .pobox  .btnArea .btnGroup .sgBtnt:hover{
    background-color: #08c;
}
</style>
<!-- start:加载信息 -->
<div id="infoMessage" class="ajaxLoadCss"  >
	<div class="dbox" >
		<div class="header">
			<h5 class="ti">温馨提示</h5>
		</div>
		<div class="downBody" align="center" >
			<div class="censt">
				<img alt="加载" src="${resPath }/back/img/loading.gif">
				<label class="cstex">数据正在加载中。。。</label> 
			</div>
		</div>
	</div>
</div>
<!-- end:加载信息 -->
<!--start:遮罩层-->
<div id="infoGround" class="cusModelOne"></div>
<!--end:遮罩层-->
<!-- start:第二个遮罩层 -->
<div id="secinfoGround" class="cusModelTwo" ></div>
<!-- end:第二个遮罩层 -->



<!-- start:确认提示信息 -->
<div id="sureMessage" class="sureMessageDlg" >
	<div class="dbox" >
		<div class="header" >
			<h5 class="ti" id="tipTitle">确认</h5>
		</div>
		<div class="downBody" align="center" >
			<div class="censt">
				<div>
					<label class="dlgTitle" id="tipContext">确定进行操作？</label> 
				</div>
				<div class="btn1">
					<button class="bb" onclick="cancelMessage()"  id="closeBtn" >确定</button>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- end:确认提示信息 -->

<!-- start:confirm提示框 -->

<script type="text/javascript">
$(document).ready(function(){
	$("#surBtn").hover(function(){
	    $("#surBtn").css("background-color","#006dcc");
	    },function(){
	    $("#surBtn").css("background-color","#0095D9");
	 })
	 $("#cancelBtn").hover(function(){
	    $("#cancelBtn").css("background-color","#006dcc");
	    },function(){
	    $("#cancelBtn").css("background-color","#0095D9");
	 })
})

function cancel(){
	$("#confirmDlg").remove();
}

/**
 * 显示消息框，绑定确定、关闭时间
 */
function showMsg(title,context,options){
	var showMsgBox=" <div class='xxcConfirm' id='confirmDlg'>"+ 
	"<div class='xlayer'></div> <div class='pobox'> <div class='tbox'> <a ></a> "+
	"<span class='tt' id='confirmTitle'>"+title+"</span> </div> <div class='textbox'> "+
	"<div id='confirmContext' class='pp'>"+context+"</div> </div> <div class='btnArea'> <div class='btnGroup'> "+
	"<div class='sgBtnt' id='confirmSureBtn'  >确定</div> "+
	"<div class='sgBtnt' id='confirmCancelBtn'  >取消</div> </div> </div> </div> </div>"
  	$("body").append(showMsgBox);
	//点击取消按钮
	$("#confirmSureBtn").click(function(){
		options(true);
		cancel();	
	});
	
	//点击关闭按钮
	$("#confirmCancelBtn").click(function(){
		cancel();	
	});
	
}
</script>
<!-- end:confirm提示框 -->

<!-- start:输入消息弹框 -->
<div id="inputMessage" class="sureMessageDlg"  >
	<div class="dbox" >
		<div class="header" >
			<h5 class="ti" id="inputTitle">新增</h5>
		</div>
		<div class="downBody" align="center" >
			<div class="censt">
				<div>
					<input type="text" id="inputValue">
				</div>
				<div class="btn1">
					<button class="bb" onclick="cancelInputMessage()"  id="inputCloseBtn" >取消</button>
					<button class="bb" onclick="sureInputMessage()"  id="inputSureBtn" >确定</button>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- end:输入消息弹框 -->
