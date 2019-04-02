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
	<jsp:include page="${path}/head"></jsp:include>
	<jsp:include page="${path}/foot"></jsp:include>
	<style type="text/css">
		.buttonType{
			height: 27px;
			line-height: 27px;
			text-decoration: none;
			text-align: center;
			border: 1px solid #ccc;
			background-color: #fff;
			cursor: pointer;
			margin-right: -1px;
			padding: 4px 6px;
		}
		.buttonType:hover{
			text-decoration: none;
			background-color:#f5f5f5;
		}
	</style>
	
</head>
<body>
	<!-- start:公共页，存放公共框 -->
	<jsp:include page="${path}/publicJsp"></jsp:include>
	<!-- end:公共页，存放公共框 -->
	<!-- start: 顶部菜单 -->
	<jsp:include page="../../public/top.jsp"></jsp:include>
	<!-- end: 顶部菜单 -->
	<div class="container-fluid-full">
		<div class="row-fluid" >
			<!-- start: 左边菜单 -->
			<!-- <jsp:include page="${path}/left"></jsp:include> 
			<!-- end: 左边菜单 -->
			<!-- start: 正文 -->
			<div id="content" class="span10" >
				<div class="row-fluid" >
					<div class="box span6" style="width: 100%;margin-top: 5px;">
						<div class="box-header">
							<h2><i class="icon-align-justify"></i><span class="break"></span>所有用户</h2>
							<div class="box-icon">
								<a href="javascript:void(0)"  onclick="showDialogForSave()" ><i class="icon-plus"></i></a>
								<a href="javascript:void(0)"  onclick="del()" ><i class="icon-minus"></i></a>
								<a href="javascript:void(0)" onclick="pwdReset()"><i style="font-style: inherit;width: 80px;">重置密码</i></a>
							</div>
						</div>
						<div class="box-content custom_pagination">
							<div style="width: 100%;height: 50px;background-color: #eee;">
								<div style="float: right;">
									<input type="hidden" id="hideValue" >
									<input id="searchValue" name="searchValue" type="text" style="margin-top: 10px;">
									<a href="javascript:void(0)" type="button"  onclick="searchPage()" style="width: 42px;border: 1px solid #ddd;text-decoration: none;margin: 0px 3px;padding: 5px 6px;font-size: 14px;color: #383e4b;">确定</a>						
								</div>
							</div>
							<table class="table">
								  <thead>
									  <tr>
									  	  <th style="width: 15px;"><i id="topCheck" class="icon-check-empty" onclick="checkAllOrNot(this)"></i></th>
										  <th style="width: 20px;">ID</th>
										  <th>用户账号</th>
										  <th>用户昵称</th>
										  <th>联系方式</th>
										  <th>邮件</th> 
										  <th style="width: 30px;">角色</th>
										  <th style="width: 30px;">权限</th>
										  <th style="width: 30px;">操作</th>                                         
									  </tr>
								  </thead>   
								  <tbody id="dataBody">
									 <tr>
										<td>Samppa Nori</td>
										<td class="center">2012/01/01</td>
										<td class="center">Member</td>
										<td>
											<a class="btn btn-info" href="#">
												<i class="icon-edit "></i>                                            
											</a> 
										</td>
									</tr> 
								  </tbody>
							 </table> 
							 <!--start:分页条  -->
							 <div class="pagination pagination-centered">
							 	<div id="pageDiv"></div>
							 	<!-- <ul ><li><a href="#">上一页</a></li><li class="active"><a href="#">1</a></li><li class="active"><a href="#">2</a></li><li class="active"><a href="#">3</a></li><li class="active"><a href="#">4</a></li><li><a href="#">下一页</a></li></ul> -->
							 	<!-- <ul ><li><a href="#">上一页</a></li><li class="active"><a href="#">1</a></li><li class="active"><a href="#">2</a></li><li class="active"><a href="#">3</a></li><li class="active"><a href="#">4</a></li><li><a href="#">下一页</a></li></ul> -->
							 	<!-- <ul><li><a>第<input type="text" style="width: 10px;" >页</a>  </li><li><a>共几页</a> </li></ul> -->
							   <ul id="pageNum">
							   </ul>
							   <!-- <div style="display: inline-block;vertical-align: middle;margin-top: -20px;"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							   		到 &nbsp;<input style="width: 16px;height: 20px;margin-top: 8px;" maxlength="2" type="text">&nbsp;页/<span id="pageTotal" >93</span>页 
							   		<span>&nbsp;&nbsp;&nbsp;&nbsp;</span><a class="buttonType">确定</a>&nbsp;&nbsp;&nbsp; 共<span id="countTotal">100</span>条
							   </div>  -->
							</div>
                            <!-- end:分页条 -->
						</div>
					</div><!--/span-->
				</div><!--/row-->
			</div>
			<!-- end: 正文 -->
		</div>
		<!-- start:弹出框 -->
		<div class="modal hide fade in" id="myModal" aria-hidden="false" style="display: none;">
			<div class="modal-header">
				<button type="button" onclick="closeDialog()" class="close" data-dismiss="modal">×</button>
				<h3 id="dialogTitleName"></h3>
			</div>
			<div class="modal-body">
				<div class="step-pane" id="step2" style="margin-top: 20px;">
					<form class="form-horizontal" id="userForm" />
						<fieldset>	
							<div class="control-group">
								<input type="hidden" id="id" name="id" >
								<label  class="control-label" for="input1">账号：</label>
								<div class="controls">
						  			<input id="account" name="account" style="width: 180px;" type="text"  maxlength="16" />
								</div>
					  		</div>
					  		<div class="control-group" id="pwdDiv">
								<label class="control-label" for="input2">密码：</label>
								<div class="controls">
						  			<input id="pwd" name="pwd" type="password" style="width: 180px;" maxlength="16" />
								</div>
					  		</div>
					  		<div class="control-group" id="rePwd">
								<label class="control-label" for="input2">重复：</label>
								<div class="controls">
						  			<input id="rePwd" name="rePwd" type="password" style="width: 180px;" maxlength="16" />
								</div>
					  		</div>
					  		<div class="control-group">
								<label class="control-label" for="input2">昵称：</label>
								<div class="controls">
						  			<input id="userName" name="userName" style="width: 180px;" type="text"  maxlength="20" />
								</div>
					  		</div>
					  		<div class="control-group">
								<label class="control-label" for="input2">手机：</label>
								<div class="controls">
						  			<input id="tel" name="tel" type="text" style="width: 180px;" maxlength="11" />
								</div>
					  		</div>
					  		<div class="control-group">
								<label class="control-label" for="input2">邮件：</label>
								<div class="controls">
						  			<input id="email" name="email" type="text"  style="width: 180px;" maxlength="20" />
								</div>
					  		</div>
						</fieldset>
					</form>
				</div>
			</div>  
			<div class="modal-footer" >
				<span id="saveButton" style="display: none;">
					<a href="javascript:void(0)" class="btn btn-primary" onclick="save()">保存</a>
				</span>
				<span id="updateButton" style="display: none;">
					<a href="javascript:void(0)" class="btn btn-primary" onclick="update()">保存</a>
				</span>
				<a href="#" class="btn" onclick="closeDialog()" >关闭</a>
			</div>
		</div>
		<!--end:弹出框-->
		<!--start:遮罩层-->
		<div id="backBlackGround" class="modal-backdrop fade in" style="display: none;"></div>
		<!--end:遮罩层-->
		<jsp:include page="${path}/boom"></jsp:include>
	</div>
	<!-- start:左右分栏框，用于角色管理 -->
	<div id="ruleMessage" class="row-fluid" style="z-index:1050; position: fixed;width: 300px;top:30%;left:45%;display: none;" >
		<input type="hidden" id="hideUserId" >
		<div class="box span6" style="width: 400px;">
			<div class="box-header">
				<h2 style="margin-left: 15px;font-size: 15px;">角色管理</h2>
				<div class="box-icon">
					<a href="javascript:void(0)"  onclick="closeManageDialog()" ><i class="icon-remove"></i></a>
				</div>
			</div>
			<div style="background: #fff;">
				<span style="margin-left: 10px;font-size: 12px;">所有角色</span><span style="margin-left: 160px;font-size: 12px;">拥有角色</span>
			</div>
			<div class="box-content custom_pagination" style="height: 300px;" align="center" >
				<div id="xiao1"  style="width:170px; height:300px; float:left; background:#efefef;OVERFLOW-Y: auto; OVERFLOW-X:hidden;">
<!-- 					<div style="margin-top: 10px;cursor: pointer;" hideId="1">--------l1</div>
					<div style="margin-top: 10px;cursor: pointer;" hideId="2">--------l2</div>
					<div style="margin-top: 10px;cursor: pointer;" hideId="3">--------l3</div>
 -->				</div>
				<div id="xiao2"  style="width:40px; height:300px; float:left;">
					<div style="width: 30px;" >
						<div style="margin-top: 30px;" onclick="rightSingle()">
							<a class="quick-button-small">
								<i class="icon-angle-right"></i>
								<p></p>
							</a>
						</div>
						<div style="margin-top: 30px;" onclick="leftSingle()" >
							<a class="quick-button-small">
								<i class="icon-angle-left"></i>
								<p></p>
							</a>
						</div>
						<div style="margin-top: 30px;" onclick="leftAll()" >
							<a class="quick-button-small">
								<i class="icon-double-angle-left"></i>
								<p></p>
							</a>
						</div>
					</div>
				</div>
				<div id="xiao3"  style="width:170px; height:300px; float:left; background:#efefef;OVERFLOW-Y: auto; OVERFLOW-X:hidden;">
					<!-- <div style="margin-top: 10px;cursor: pointer;" hideId="4">--------r1</div>
					<div style="margin-top: 10px;cursor: pointer;" hideId="5">--------r2</div>
					<div style="margin-top: 10px;cursor: pointer;" hideId="6">--------r3</div> -->
				</div>
			</div>
			<div align="center" style="background: #fff;clear: both;">
				<input id="channelBtn" style="display: none;" type="button" value="确定" onclick="saveManage(2)" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="ruleBtn" type="button" value="确定" onclick="saveManage(1)" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="关闭" onclick="closeManageDialog()" />
			</div>
			<div style="height: 10px;clear: both;background: #fff;"></div>
		</div>
	</div>
	<!-- end:左右分栏框，用于角色管理 -->
	<script type="text/javascript">
		$(document).ready(function(){
			njqpage.makePage({
				excId:pageDiv,
				click:queryPage
			}); 
		})
		$(document).bind("keypress",function(obj){
			if(obj.keyCode==13){
				if(obj.target.name=="searchValue"){
					$("input[name=searchValue]").focus();
					$("#hideValue").val($("#searchValue").val());
					njqpage.makePage({},5);
				}
			}
		})
		
		$(document).on("mousemove","tbody tr",function(){
			$(this).css("backgroundColor","#ffffcc");
		});
		$(document).on("mouseout","tbody tr",function(){
			if($($($(this).children()[0]).children()[0]).attr("class")!="icon-check"){
				$(this).css("backgroundColor","#fff");
			}else{
				$(this).css("backgroundColor","#eee");				
			}
		});
		
		$(document).on("click","tbody tr",function(){
			$(this).css("backgroundColor","#eee");
			if($(this.children[0].children).attr("class")=="icon-check-empty"){
				$(this.children[0].children).removeClass("icon-check-empty").attr("class","icon-check");
			}else{
				$(this.children[0].children).removeClass("icon-check").attr("class","icon-check-empty");
			}
			
		});
		
		function checkAllOrNot(e){
			if($(e).attr("class")=="icon-check-empty"){
				$(e).removeClass("icon-check-empty").attr("class","icon-check");
				$.each($("tbody tr"),function(a,b){
					$(b.children[0].children[0]).attr("class","icon-check");
					$(b).css("backgroundColor","#eee");	
				})
			}else{
				$(e).removeClass("icon-check").attr("class","icon-check-empty");
				$.each($("tbody tr"),function(a,b){
					$(b.children[0].children[0]).attr("class","icon-check-empty");
					$(b).css("backgroundColor","#fff");
				})
			}
		}
		
		<%-- remote: {
		    url: "<%=basePath%>manager/channel/exist",     //后台处理程序
		    type: "post",               //数据发送方式
		    dataType: "json",           //接受数据格式   
		    data: {    
		    	filed:"channelUrl",//要传递的数据//要传递的数据
		    	value: function() {
		            return $("#channelUrl").val();
		        },
		        id:function(){
		        	var id=$("#id").val();
		        	if(id==null || id=="" || id=="-1" || id=="null"){
		        		return -1;
		        	}else{
		        		return id;
		        	}
		        	
		       	}
		    }
		 } --%>
		
		$("#userForm").validate({
			rules:{
				account:{
					required:true,
					maxlength:16,
					minlength:6
				},
				pwd:{
					required:true,
					maxlength:16,
					minlength:8,
					notChinese:true
				},
				rePwd:{
					required:true,
					equalTo:"#pwd"
				},
				userName:{
					required:true,
					maxlength:16,
					minlength:2
				},
				tel:{
					required:true,
					digits:true,
					isMobile:true
				},
				email:{
					required:true,
					email:true
				}
				
			},
			messages:{
				account:{
					required:"不能为空！",
					maxlength:"最大长度为{0}",
					minlength:"最小长度为{0}"
				},
				pwd:{
					required:"不能为空！",
					maxlength:"最大长度为{0}",
					minlength:"最小长度为{0}"
				},
				rePwd:{
					required:"不能为空！",
					equalTo:"两次密码不相同！"
				},
				userName:{
					required:"不能为空！",
					maxlength:"最大长度为{0}",
					minlength:"最小长度为{0}"
				},
				tel:{
					required:"不能为空！",
					digits:"请输入整数！"
				},
				email:{
					required:"不能为空！",
					email:"请输入正确的邮箱格式！"
				}
			}
		}) 
		
		function searchPage(){
			$("#hideValue").val($("#searchValue").val());
			njqpage.makePage({},5);
		}
		/**
		 * 加载表格数据
		 */
		function queryPage(page,size){
			$.ajax({
				url:"${path}/admin/userManage/getUserList",
				data:{
					page:page,
					size:size,
					searchValue:$("#hideValue").val()
				},
				async:false,
				type:"post",
				beforeSend:ajaxBefore(),
				success:function(data){
					ajaxAfter();
					var str="";
					$.each(data.list,function(n,d){
						str +="<tr><td onclick='check(this)'><i class='icon-check-empty'></td><td>"+d.id+"</td><td class='center'>"+d.account+"</td><td class='center'>"+d.userName+"</td><td>"+d.tel+"</td><td>"+d.email+"</td><td><span onclick='showRuleManageDialog("+d.id+")' style='cursor: pointer;color:#36a9e1;'>管理</span></td><td><span onclick='showPorManageDialog("+d.id+")' style='cursor: pointer;color:#36a9e1;'>管理</span></td><td><a class='btn btn-info' onclick='showDialogForUpdate(this)' href='javascript:void(0)'><i  class='icon-edit'></i></a></td></tr>";
					})
					$("#dataBody").html(str);
					njqpage.totalNum=Number(data.total);
					$("#topCheck").attr("class","icon-check-empty");
				}
			})
		}
		
		/**
		 * 修改显示弹出框
		 */
		function showDialogForUpdate(e){
			$("#pwdDiv").hide();
			$("#rePwd").hide();
			$("#saveButton").hide();
			$("#updateButton").show();
			$("#dialogTitleName").html("修改");
 			$("#id").val($($(e).parents("tr").children()[1]).html());
			$("#account").val($($(e).parents("tr").children()[2]).html());
			$("#userName").val($($(e).parents("tr").children()[3]).html());
			$("#tel").val($($(e).parents("tr").children()[4]).html());
			$("#email").val($($(e).parents("tr").children()[5]).html());
			$("#myModal").show();
			$("#backBlackGround").show();
		}
		
		/**
		 * 新增显示弹出框
		 */
		function showDialogForSave(e){
			$("#saveButton").show();
			$("#updateButton").hide();
			$("#dialogTitleName").html("新增");
			$("#myModal").show();
			$("#backBlackGround").show();
			$("#pwdDiv").show();
			$("#rePwd").show();
		}
		
		/**
		 * 关闭弹出框
		 */
		function closeDialog(){
			$("#myModal").hide();
			$("#backBlackGround").hide();
			$("#id").val("");
			$("#tel").val("");
			$("#email").val("");
			$("#account").val("");
			$("#pwd").val("");
			$("#userName").val("");
			$("#rePwd").val("");
			$("#userForm").validate().resetForm();
			$("input").removeClass("error");
		}
		
		/**
		 * 保存数据
		 */
		function save(){
			if(!$("#userForm").valid()){
				return;
			}
			showMsg("确认","确定保存？",function(t){
				if(t){
					$.ajax({
						url:"${path}/admin/userManage/saveUser",
						type:"post",
						beforeSend:ajaxBefore(),
						data:$("#userForm").serialize(),
						success:function(data){
							ajaxAfter();
							if(data.state==1){
								closeDialog();
								njqpage.reMake();
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
			if(!$("#userForm").valid()){
				return;
			}
			showMsg("确认","确定修改？",function(t){
				if(t){
					$.ajax({
						url:"${path}/admin/userManage/updateUser",
						data:$("#userForm").serialize(),
						type:"post",
						beforeSend:ajaxBefore(),
						success:function(data){
							ajaxAfter();
							if(data.state==1){
								closeDialog();
								njqpage.reMake();
							}
							showSureMessage("提示",data.message);
						}
					})
				}
			})
		}
		
		/**
		 * 点击复选框
		 */
		function check(e){
		}
		
		/**
		 * 删除栏目
		 */
		function del(){
			var checkList=$("#dataBody").find(".icon-check");
			var delIds="";
			if(checkList.size()==0){
				showSureMessage("提示","请先选择要删除的用户！");
				return ;
			}else{
				for(var i=0;i<checkList.length;i++){
					delIds +=$(checkList[i]).parent().next().html()+","
				}
				delIds=delIds.substring(0,delIds.length-1);
			}
			if(delIds.length>0){
				showMsg("确认","确定删除？",function(t){
					if(t){
						$.ajax({
							url:"${path}/admin/userManage/delUser",
							data:{
								ids:delIds
							},
							type:"post",
							beforeSend:ajaxBefore(),
							success:function(data){
								ajaxAfter();
								if(data.state==1){
									njqpage.reMake();
								}
								showSureMessage("提示",data.message);
							}
						})
					}
				})
			}
			
		}
		
		
		//---------------------start:左右分栏空间的js----------------------------
		$(document).on('click','#xiao1 div',function(){
			if($(this).attr("check")=="true"){
				$(this).css("background","");
				$(this).attr("check","false ");
				$(this).removeClass("lll");
			}else{
				$(this).css("background","#36a9e1");
				$(this).attr("check","true");
				$(this).attr("class","lll");			
			}
		});
		$(document).on('click','#xiao3 div',function(){
			if($(this).attr("check")=="true"){
				$(this).css("background","");
				$(this).attr("check","false ");
				$(this).removeClass("rrr");
			}else{
				$(this).css("background","#36a9e1");
				$(this).attr("check","true");
				$(this).attr("class","rrr");			
			}
		});

		/**
		 * 把右边的移动到左边
		 */
		function rightSingle(){
			$("#xiao3").append($(".lll"));
			$("#xiao3 div").css("background","");
			$("#xiao3 div").attr("check","false ");
			$("#xiao3 div").removeClass("lll");
			$("#xiao3 div").removeClass("rrr");
			$(".lll").remove();
		}

		/**
		 * 把左边的移动到右边
		 */
		function leftSingle(){
			$("#xiao1").append($(".rrr"));
			$("#xiao1 div").css("background","");
			$("#xiao1 div").attr("check","false ");
			$("#xiao1 div").removeClass("rrr");
			$("#xiao1 div").removeClass("lll");
			$(".rrr").remove();
		}

		/**
		 * 把右边的全部移到左边
		 */
		function leftAll(){
			$("#xiao1").append($("#xiao3").html())
			$("#xiao1 div").css("background","");
			$("#xiao1 div").attr("check","false ");
			$("#xiao1 div").removeClass("rrr");
			$("#xiao1 div").removeClass("lll");
			$("#xiao3 div").remove();
		}

		/**
		 * 关闭管理框
		 */
		function closeManageDialog(){
			$("#ruleMessage").hide();
			$("#xiao1").html("");
			$("#xiao3").html("");
			$("#hideUserId").val("");
			$("#infoGround").hide();
		}
		
		/** 
		 * 显示角色管理框
		 */
		function showRuleManageDialog(userId){
			$("#hideUserId").val(userId);
			$("#channelBtn").hide();
			$("#ruleBtn").show();
			$.ajax({
				url:"${path}/admin/ruleManage/getUserRuleList",
				data:{
					userId:userId
				},
				type:"post",
				success:function(data){
					for(var i=0;i<data.notHaveList.length;i++){
						$("#xiao1").append("<div style='margin-top: 10px;cursor: pointer;' hideId='"+data.notHaveList[i].id+"'>"+data.notHaveList[i].ruleName+"</div>");
					}
					for(var i=0;i<data.haveList.length;i++){
						$("#xiao3").append("<div style='margin-top: 10px;cursor: pointer;' hideId='"+data.haveList[i].id+"'>"+data.haveList[i].ruleName+"</div>");	
					}
				}
			})
			$("#ruleMessage").show();
			$("#infoGround").show();
		}
		
		/**
		 * 显示权限管理框
		 */
		function showPorManageDialog(userId){
			$("#hideUserId").val(userId);
			$("#channelBtn").show();
			$("#ruleBtn").hide();
			$.ajax({
				url:"${path}/admin/powerManage/getUserChannelList",
				data:{
					userId:userId
				},
				type:"post",
				success:function(data){
					for(var i=0;i<data.notHaveList.length;i++){
						$("#xiao1").append("<div style='margin-top: 10px;cursor: pointer;' hideId='"+data.notHaveList[i].id+"'>"+data.notHaveList[i].channelName+"</div>");
					}
					for(var i=0;i<data.haveList.length;i++){
						$("#xiao3").append("<div style='margin-top: 10px;cursor: pointer;' hideId='"+data.haveList[i].id+"'>"+data.haveList[i].channelName+"</div>");	
					}
				}
			})
			$("#ruleMessage").show();
			$("#infoGround").show();
		}
		/**
		 * 保存数据
		 */
		function saveManage(type){
			var ids="";
			var url="";
			var data={};
			for(var i=0;i<$("#xiao3 div").length;i++){
				ids+=$($("#xiao3 div")[i]).attr("hideId")+","
			}
			ids=ids.substr(0,ids.length-1);
			if(type==1){
				url="${path}/admin/ruleManage/updateUserRuleConfig";
				data={
						ruleIds:ids,
						userId:$("#hideUserId").val()
					}
			}else{
				url="${path}/admin/powerManage/updateUserChannelConfig";
				data={
					channelIds:ids,
					userId:$("#hideUserId").val()
				}
			}
			showMsg("确认","确定保存？",function(t){
				if(t){
					$.ajax({
						url:url,
						data:data,
						type:"post",
						success:function(data){
							closeManageDialog();
						}
					})
				}
			})
			
		}
		//---------------------end:左右分栏空间的js----------------------------
		// 	重置密码
		function pwdReset(){
			var checkList=$("#dataBody").find(".icon-check");
			var delIds="";
			if(checkList.size()==0){
				showSureMessage("提示","请先选择要重置的用户！");
				return ;
			}else{ 
				for(var i=0;i<checkList.length;i++){
					delIds +=$(checkList[i]).parent().next().html()+","
				}
				delIds=delIds.substring(0,delIds.length-1);
			}
			if(delIds.length>0){
				showMsg("确认","确定重置？",function(t){
					if(t){
						$.ajax({
							url:"${path}/admin/userManage/resetPwd",
							data:{
								ids:delIds
							},
							type:"post",
							beforeSend:ajaxBefore(),
							success:function(data){
								ajaxAfter();
								if(data.state==1){
									njqpage.reMake();
								}
								showSureMessage("提示",data.message);
							}
						})
					}
				})
			}
		}
	</script>
</body>
</html>