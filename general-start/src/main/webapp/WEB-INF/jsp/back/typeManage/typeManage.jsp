<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
				<div class="row-fluid">
					<div class="box span6" style="width: 100%;">
						<div class="box-header">
							<h2><i class="icon-align-justify"></i><span class="break"></span>类型管理</h2>
							<div class="box-icon">
								<a href="javascript:void(0)"  onclick="showDialogForSave()" ><i class="icon-plus"></i></a>
								<a href="javascript:void(0)"  onclick="del()" ><i class="icon-minus"></i></a>
							</div>
						</div>
						<div class="box-content custom_pagination"  >
							<table class="table">
								  <thead>
									  <tr>
									  	  <th style="width: 15px;"><i id="topCheck" class="icon-check-empty" onclick="checkAllOrNot(this)" ></i></th>
										  <th style="width: 20px;">ID</th>
										  <th style="width: 80px;">类型名称</th>
										  <th>上级</th>
										  <th>描述</th> 
										  <th style="width: 30px;">操作</th>                                         
									  </tr>
								  </thead>   
								  <tbody id="dataBody">
									
								  </tbody>
							 </table> 
							 <!--start:分页条  -->
							 <div class="pagination pagination-centered">
							 	<div id="pageDiv"></div>
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
					<form class="form-horizontal" id="typeForm" />
						<fieldset>	
							<c:if test="${userId!=1 }">
								<div class="control-group">
									<label  class="control-label" for="input1">父类：</label>
									<div style="margin-left: 180px;">
										<select id="parentId" name="parentId" style="width: 194px;">
											<c:forEach items="${parentList }" var="type">
												<option value="${type.id }">${type.name }</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</c:if>
							<div class="control-group">
								<input type="hidden" id="id" name="id" >
								<label  class="control-label" for="input1">名称：</label>
								<div class="controls">
						  			<input id="name" name="name" type="text" style="width: 180px;"  maxlength="16" />
								</div>
					  		</div>
					  		<div class="control-group">
								<label class="control-label" for="input2">描述：</label>
								<div class="controls">
						  			<textarea id="columDesc" name="columDesc"  style="height: 78px;width: 180px;"></textarea>
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
	
	<script type="text/javascript">
		$(document).ready(function(){
			njqpage.makePage({
				excId:pageDiv,
				click:queryPage
			}); 
		})	
		
		$("#typeForm").validate({
			rules:{
				name:{
					required:true,
					maxlength:10,
					minlength:2
				}
				
			},
			messages:{
				name:{
					required:"不能为空！",
					maxlength:"最大长度为{0}",
					minlength:"最小长度为{0}"
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
				url:"${path}/admin/typeManage/getTypeList",
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
						str +="<tr><td><i class='icon-check-empty'></td><td>"+d.id+"</td><td class='center'>"+d.name+"</td><td>";
						if(d.parentName != null){
							str+=d.parentName;
						}
						str+="</td><td>"+d.columDesc+"</td><td><a class='btn btn-info' onclick='showDialogForUpdate(this)' href='javascript:void(0)'><i  class='icon-edit'></i></a></td></tr>";
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
 			$("#name").val($($(e).parents("tr").children()[2]).html());
 			$("#columDesc").val($($(e).parents("tr").children()[4]).html());
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
			$("#name").val("");
			$("#columDesc").val("");
			$("#typeForm").validate().resetForm();
		}
		
		/**
		 * 保存数据
		 */
		function save(){
			if(!$("#typeForm").valid()){
				return;
			}
			showMsg("确认","确认添加？",function(t){
				if(t){
					$.ajax({
						url:"${path}/admin/typeManage/saveType",
						type:"post",
						data:$("#typeForm").serialize(),
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
			if(!$("#typeForm").valid()){
				return;
			}
			showMsg("确认","确认修改？",function(t){
				if(t){
					$.ajax({
						url:"${path}/admin/typeManage/updateType",
						data:$("#typeForm").serialize(),
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
				showSureMessage("提示","请先选择要删除的类型");
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
							url:"${path}/admin/typeManage/delType",
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
		 * 新增显示弹出框
		 */
		function showDialogForSave(e){
			$("#saveButton").show();
			$("#updateButton").hide();
			$("#dialogTitleName").html("新增");
			$("#myModal").show();
			$("#backBlackGround").show();
		}
	</script>
</body>
</html>