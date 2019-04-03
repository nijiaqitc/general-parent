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
</head>
<body>
	<!-- start:公共页，存放公共框 -->
	<jsp:include page="${path}/publicJsp"></jsp:include>
	<!-- end:公共页，存放公共框 -->
	<!-- start: 顶部菜单 -->
	<jsp:include page="../../public/top.jsp"></jsp:include>
	<!-- end: 顶部菜单 -->
	<div class="container-fluid-full">
		<div class="row-fluid">
			<!-- start: 左边菜单 -->
			<jsp:include page="${path}/left"></jsp:include>
			<!-- end: 左边菜单 -->
			<!-- start: 正文 -->
			<div id="content" class="span10" >
				<div class="row-fluid">
					<div class="box span6" style="width: 100%;">
						<div class="box-header">
							<h2><i class="icon-align-justify"></i><span class="break"></span>栏目权限</h2>
							<div class="box-icon">
								<a href="javascript:void(0)"  onclick="applyChannel()"><i style="font-style: inherit;width: 40px;">应用</i></a>
								<a href="javascript:void(0)"  onclick="showDialogForSave()" ><i class="icon-plus"></i></a>
								<a href="javascript:void(0)"  onclick="del()" ><i class="icon-minus"></i></a>
							</div>
						</div>
						<div class="box-content custom_pagination" 
							option="url:${path}/admin/powerManage/getChannelList,pageShow:[10:20:30]">
							<table class="table">
								  <thead>
									  <tr>
									  	  <th style="width: 15px;"><i id="topCheck" class="icon-check-empty" onclick="checkAllOrNot(this)" ></i></th>
										  <th style="width: 20px;">ID</th>
										  <th>权限名称</th>
										  <th style="width: 30px;">上级</th>
										  <th>url</th>
										  <th style="width: 30px;">应用</th>
										  <th style="width: 30px;">图标</th>
										  <th style="width: 30px;">索引</th>
										  <th>描述</th> 
										  <th style="width: 20px;">操作</th>                                         
									  </tr>
								  </thead>   
								  <tbody id="dataBody">
									 <tr>
										<td>Samppa Nori</td>
										<td class="center">2012/01/01</td>
										<td class="center">Member</td>
										<td></td>
										<td></td>
										<td>
											<a class="btn btn-info" href="#">
												<i class="icon-edit "></i>                                            
											</a> 
										</td>
									</tr> 
									<tr>
										<td colspan="7">
											<table class="table">
												<tr><td>1</td><td>2</td></tr>
												<tr><td>3</td><td>4</td></tr>
											</table>
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
					<form class="form-horizontal" id="channelForm" />
						<fieldset>	
							<div class="control-group">
								<input type="hidden" id="id" name="id" >
								<label  class="control-label" for="input1">名称：</label>
								<div class="controls">
						  			<input id="channelName" name="channelName" type="text" style="width: 180px;"  maxlength="16" />
								</div>
					  		</div>
					  		<div class="control-group">
								<label class="control-label" for="selectError30">父级：</label>
								<div class="controls">
								  	<select id="selectError30" name="parentId" style="width: 194px;" >
										<option>Option 1 </option>
								  	</select>
								</div>
							</div>
					  		<div class="control-group" >
								<label  class="control-label" for="input1">地址：</label>
								<div class="controls">
						  			<input id="url" name="url" type="text" style="width: 180px;"  maxlength="50" />
								</div>
					  		</div>
					  		<div class="control-group">
								<label  class="control-label" for="input1">图标：</label>
								<div class="controls">
						  			<input id="icon" name="icon" type="text" style="width: 180px;"  maxlength="20" />
								</div>
					  		</div>
					  		<div class="control-group">
								<label  class="control-label" for="input1">索引：</label>
								<div class="controls">
						  			<input id="inTurn" name="inTurn" type="text" style="width: 180px;"  maxlength="16" />
								</div>
					  		</div>
					  		<div class="control-group">
								<label class="control-label" for="input2">描述：</label>
								<div class="controls">
						  			<textarea id="channelDesc" name="columDesc"  style="height: 78px;width: 180px;"></textarea>
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
	<jsp:include page="${path}/foot"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function(){
			njqpage.makePage({
				excId:pageDiv,
				click:queryPage
			}); 
		})	
		
		$("#channelForm").validate({
			rules:{
				channelName:{
					required:true,
					maxlength:10,
					minlength:2
				},
				url:{
					required:true,
					maxlength:30
				},
				icon:{
					required:true,
					maxlength:30
				},
				inTurn:{
					required:true,
					digits:true
				}
				
			},
			messages:{
				channelName:{
					required:"不能为空！",
					maxlength:"最大长度为{0}",
					minlength:"最小长度为{0}"
				},
				url:{
					required:"不能为空！",
					maxlength:"最大长度为{0}"
				},
				icon:{
					required:"不能为空！",
					maxlength:"最大长度为{0}"
				},
				inTurn:{
					required:"不能为空！",
					digits:"请输入整数！"
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
		
		/**
		 * 加载表格数据
		 */
		function queryPage(page,size){
			$.ajax({
				url:"${path}/admin/powerManage/getChannelList",
				data:{
					page:page,
					size:size
				},
				async:false,
				type:"get",
				beforeSend:ajaxBefore(),
				success:function(data){
					ajaxAfter();
					var str="";
					$.each(data.list,function(n,d){
						str +="<tr><td><i class='icon-check-empty'></td><td>"+d.id+"</td><td class='center'>"+d.channelName+"</td><td>"+d.parentId+"</td><td>"+d.url+"</td><td>";
						if(d.apply==1){
							str+="是</td><td><i class='"+d.icon+"'></i></td><td>"+d.inTurn+"</td><td class='center'>"+d.columDesc+"</td><td><a class='btn btn-info' onclick='showDialogForUpdate(this)' href='javascript:void(0)'><i  class='icon-edit'></i></a></td></tr>";
						}else{
							str+="否</td><td><i class='"+d.icon+"'></i></td><td>"+d.inTurn+"</td><td class='center'>"+d.columDesc+"</td><td><a class='btn btn-info' onclick='showDialogForUpdate(this)' href='javascript:void(0)'><i  class='icon-edit'></i></a></td></tr>";
						}
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
			$("#saveButton").hide();
			$("#updateButton").show();
			$("#dialogTitleName").html("修改");
 			$("#id").val($($(e).parents("tr").children()[1]).html());
			$("#channelName").val($($(e).parents("tr").children()[2]).html());
			$("#url").val($($(e).parents("tr").children()[4]).html());
			$("#icon").val($($($(e).parents("tr").children()[6]).html()).attr("class"));
			$("#inTurn").val($($(e).parents("tr").children()[7]).html());
			$("#channelDesc").val($($(e).parents("tr").children()[8]).html());
			$("#myModal").show();
			$("#backBlackGround").show();
			$.ajax({
				url:"${path}/admin/powerManage/getParentsList",
				type:"post",
				success:function(data){
					var str="<option value='0' >---选择父级---</option>";
					$.each(data,function(n,c){
 						str += "<option value='"+c.id+"' >"+c.channelName+"</option>"						
					})
 					$("#selectError30").html(str);
					$("#selectError30").val($($(e).parents("tr").children()[3]).html())
				}
			})
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
			$.ajax({
				url:"${path}/admin/powerManage/getParentsList",
				type:"post",
				success:function(data){
					var str="<option value='0' >---选择父级---</option>";
					$.each(data,function(n,c){
 						str += "<option value='"+c.id+"' >"+c.channelName+"</option>"						
					})
 					$("#selectError30").html(str);
				}
			})
		}
		
		/**
		 * 关闭弹出框
		 */
		function closeDialog(){
			$("#myModal").hide();
			$("#backBlackGround").hide();
			$("#id").val("");
			$("#channelName").val("");
			$("#channelDesc").val("");
			$("#url").val("");
			$("#icon").val("");
			$("#inTurn").val("");
			$("#channelForm").validate().resetForm();
		}
		
		/**
		 * 保存数据
		 */
		function save(){
			if(!$("#channelForm").valid()){
				return;
			}
			showMsg("确认","确认修改？",function(t){
				if(t){
					$.ajax({
						url:"${path}/admin/powerManage/saveChannel",
						type:"post",
						data:$("#channelForm").serialize(),
						success:function(data){
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
			if(!$("#channelForm").valid()){
				return;
			}
			showMsg("确认","确认修改？",function(t){
				if(t){
					$.ajax({
						url:"${path}/admin/powerManage/updateChannel",
						data:$("#channelForm").serialize(),
						type:"post",
						success:function(data){
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
			if($(e.children).attr("class")=="icon-check-empty"){
				$(e.children).removeClass("icon-check-empty").attr("class","icon-check");
			}else{
				$(e.children).removeClass("icon-check").attr("class","icon-check-empty");
			}
		}
		
		/**
		 * 删除栏目
		 */
		function del(){
			var checkList=$("#dataBody").find(".icon-check");
			var delIds="";
			if(checkList.size()==0){
				showSureMessage("sure","提示","请先选择要删除的栏目");
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
							url:"${path}/admin/powerManage/delChannel",
							data:{
								ids:delIds
							},
							type:"post",
							success:function(data){
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
		
		/**
		 * 权限应用
		 */
		function applyChannel(){
			var checkList=$("#dataBody").find(".icon-check");
			var delIds="";
			var flag=false;
			var msg="";
			var ty=1;
			if(checkList.size()==0){
				showSureMessage("提示","请先选择要应用的权限！");
				return ;
			}else{ 
				for(var i=0;i<checkList.length;i++){
					delIds +=$(checkList[i]).parent().next().html()+","
					if($(checkList[i]).parent().next().next().next().next().next().html()!="是"){
						flag=true;						
					}
				}
				delIds=delIds.substring(0,delIds.length-1);
			}
			if(flag==true){
				msg="存在尚未应用的权限，确定应用？";
				ty=1;
			}else{
				msg="取消应用？";
				ty=2;
			}
			if(delIds.length>0){
				showMsg("确认",msg,function(t){
					if(t){
						 $.ajax({
							url:"${path}/admin/powerManage/applyChannel",
							data:{
								channelIds:delIds,
								type:ty
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