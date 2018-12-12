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
</head>
<body>
	<!-- start:公共页，存放公共框 -->
	<jsp:include page="${basePath}/publicJsp"></jsp:include>
	<!-- end:公共页，存放公共框 -->
	<!-- start: 顶部菜单 -->
	<jsp:include page="${basePath}/top"></jsp:include>
	<!-- end: 顶部菜单 -->
	<div class="container-fluid-full">
		<div class="row-fluid">
			<!-- start: 左边菜单 -->
			<jsp:include page="${basePath}/left"></jsp:include>
			<!-- end: 左边菜单 -->
			<!-- start: 正文 -->
			<div id="content" class="span10" >
				<div class="row-fluid">
					<div class="box span6" style="width: 100%;">
						<div class="box-header">
							<h2><i class="icon-align-justify"></i><span class="break"></span>栏目权限</h2>
							<div class="box-icon">
								<a href="javascript:void(0)"  onclick="showDialogForSave()" ><i class="icon-plus"></i></a>
								<a href="javascript:void(0)"  onclick="del()" ><i class="icon-minus"></i></a>
								
								<%-- <a href="#" class="btn-close"><img alt="" src="${resPath }/back/img/loading.gif"></a> --%> 
							</div>
						</div>
						<div class="box-content custom_pagination" 
							option="url:${path}/powerManage/getChannelList,pageShow:[10:20:30]">
							<table class="table">
								  <thead>
									  <tr>
									  	  <th style="width: 15px;"><i class="icon-check-empty"></i></th>
										  <th style="width: 20px;">ID</th>
										  <th>权限名称</th>
										  <th>描述</th> 
										  <th style="width: 20px;">操作</th>                                         
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
						  			<input id="channelName" name="channelName" type="text"  maxlength="16" />
								</div>
					  		</div>
					  		<div class="control-group">
								<label class="control-label" for="input2">描述：</label>
								<div class="controls">
						  			<textarea id="channelDesc" name="desc"  style="height: 98px;"></textarea>
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
		<jsp:include page="${basePath}/boom"></jsp:include>
	</div>
	<jsp:include page="${basePath}/foot"></jsp:include>
	<script type="text/javascript">
		var page=0;
		var size=3;
		var num=0;
		var index=0;
		$(document).ready(function(){
			/* var aaa=$(".custom_pagination");
			var attr=aaa.attr("option")
			console.info(attr)
			var op=attr.split(",");
			var option="";
			for(var i=0;i<op.length;i++){
				option+=op[i].split(":")[0]+":'"+op[i].substr(op[i].split(":")[0].length+1,op[i].length)+"'"
				if(i!=0&&i!=(op.length-1)){
					option+=",";
				}
			}
			console.info(option)
			console.info(eval('({'+option+'})')) */
			
// 			console.info(op.length)
			queryPage(page,size); 
			makePage();
		})
		
		/**
		 * 分页条跳转事件
		 */
		function turnToPage(page,e){
			if($(e).parent().attr("class")=="active"){
				return ;
			}
			$("li").removeClass("active")
			if(page==-1){
				if(index <= 0){
					return;
				}else{
					page=index-1;		
				}
			}else if(page==-2){
				if(index>=num){
					return ;
				}else{
					page=index+1;
				}
			}else if(page==1){
				$("#beforePage").attr('class','active'); 
				$(e).parent().attr('class','active')
			}else if(page==num){
				$("#nextPage").attr('class','active'); 
				$(e).parent().attr('class','active')
			}else{
				$(e).parent().attr('class','active')
			}
			index=page;
			queryPage(page,size);
		}
		
		
		/**
		 * 加载表格数据
		 */
		function queryPage(page,size){
			$.ajax({
				url:"${path}/powerManage/getChannelList",
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
						str +="<tr><td onclick='check(this)'><i class='icon-check-empty'></td><td>"+d.id+"</td><td class='center'>"+d.channelName+"</td><td class='center'>"+d.desc+"</td><td><a class='btn btn-info' onclick='showDialogForUpdate(this)' href='javascript:void(0)'><i  class='icon-edit'></i></a></td></tr>";
					})
					$("#dataBody").html(str);
					num=Math.ceil(data.total/size);
				}
			})
		}
		
		/**
		 * 加载分页条
		 */
		function makePage(){
			var pageStr="<li id='beforePage' class='active' ><a  onclick='turnToPage(-1,this)' href='javascript:void(0)'>上一页</a></li>";
			for(var i=0;i<num;i++){
				if(i==0){
					pageStr+="<li  class='active'><a href='javascript:void(0)' onclick='turnToPage("+(i+1)+",this)'>"+(i+1)+"</a></li>";
				}else{
					pageStr+="<li><a href='javascript:void(0)' onclick='turnToPage("+(i+1)+",this)'>"+(i+1)+"</a></li>";
				}
			}
			pageStr+="<li id='nextPage' ><a  href='javascript:void(0)' onclick='turnToPage(-2,this)'>下一页</a></li>";
			$("#pageNum").html(pageStr)
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
			$("#channelDesc").val($($(e).parents("tr").children()[3]).html());
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
		}
		
		/**
		 * 保存数据
		 */
		function save(){
			$.ajax({
				url:"${path}/powerManage/saveChannel",
				type:"post",
				data:$("#channelForm").serialize(),
				success:function(data){
					if(data.state==1){
						closeDialog();
						queryPage(page,size); 
						makePage();
					}else{
						alert(data.message);
					}
				}
			})
		}
		/**
		 * 修改数据
		 */
		function update(){
			$.ajax({
				url:"${path}/powerManage/updateChannel",
				data:$("#channelForm").serialize(),
				type:"post",
				success:function(data){
					if(data.state==1){
						closeDialog();
						queryPage(page,size); 
						makePage();
					}else{
						alert(data.message);
					}
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
				alert("请先选择要删除的栏目");
				return ;
			}else{
				for(var i=0;i<checkList.length;i++){
					delIds +=$(checkList[i]).parent().next().html()+","
				}
				delIds=delIds.substring(0,delIds.length-1);
			}
			
			if(delIds.length>0){
				$.ajax({
					url:"${path}/powerManage/delChannel",
					data:{
						ids:delIds
					},
					type:"post",
					success:function(data){
						if(data.state==1){
							queryPage(page,size); 
							makePage();
						}else{
							alert(data.message);
						}
					}
				})
			}
			
		}
		
	</script>
</body>
</html>