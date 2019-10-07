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
	<style type="text/css">
		.searchBtn{
			width: 42px;border: 1px solid #ddd;text-decoration: none;margin: 0px 3px;padding: 5px 6px;font-size: 14px;color: #383e4b;
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
			<jsp:include page="${path}/left"></jsp:include> 
			<!-- end: 左边菜单 -->
			<!-- start: 正文 -->
			<div id="content" class="span10" >
				<div class="row-fluid">
					<div class="box span6" style="width: 100%;">
						<div class="box-header">
							<h2><i class="icon-align-justify"></i><span class="break"></span>复习内容</h2>
							<div class="box-icon">
								<a href="javascript:void(0)"  onclick="upexcel()" ><i class="icon-plus"></i></a>
								<a href="javascript:void(0)"  onclick="showDialogForSave()" ><i class="icon-plus"></i></a>
								<a href="javascript:void(0)"  onclick="del()" ><i class="icon-minus"></i></a>
								<input type="file" style="display: none;" onchange="upbatchskus(this)" id="crowd_file">
							</div>
						</div>
						<div class="box-content custom_pagination"  >
							<div style="width: 100%;height: 50px;background-color: #eee;">
								<div style="float: right;">
									<select id="st_typeId" style="width: 160px;margin-top: 10px;">
										<option value="0">可选择类型</option>
										<c:forEach items="${typeList }" var="type">
											<option value="${type.id }">${type.name }</option>
										</c:forEach>
									</select>
									简答题
									<input type="radio" name="st_titleType" value="1" checked="checked">
									选择题
									<input type="radio" name="st_titleType" value="2">
									编写题
									<input type="radio" name="st_titleType" value="3">
									<select id="st_sure" name="st_sure" style="width: 80px;margin-top: 10px;">
										<option value="0">全部</option>
										<option value="1">确认</option>
										<option value="2">未确认</option>
									</select>
									<input type="hidden" id="hideValue" >
									<input id="st_searchValue" name="st_searchValue" type="text" style="margin-top: 10px;">
									<a href="javascript:void(0)" type="button" id="searchId" name="searchId"  onclick="searchPage()" class="searchBtn" >确定</a>						
								</div>
							</div>
							<table class="table">
								  <thead>
									  <tr>
									  	  <th style="width: 15px;"><i id="topCheck" class="icon-check-empty" onclick="checkAllOrNot(this)" ></i></th>
										  <th style="width: 20px;">ID</th>
										  <th style="width: 65%;">标题(绿色：以完成 ，红色：未完成)</th>
										  <th>分类</th>
										  <th>类型</th>
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
		<div class="modal hide fade in" id="myModal" aria-hidden="false" style="display: none;width: 740px;max-height: 550px;overflow: auto;">
			<div class="modal-header">
				<button type="button" onclick="closeDialog()" class="close" data-dismiss="modal">×</button>
				<h3 id="dialogTitleName"></h3>
			</div>
			<div class="modal-body" style="max-height: fit-content;">
				<div class="step-pane" id="step2" style="margin-top: 20px;">
					<form class="form-horizontal" id="studyForm" />
						<fieldset>	
							<c:if test="${userId!=1 }">
								<div class="control-group">
									<label  class="control-label" for="input1">父类：</label>
									<div style="margin-left: 180px;">
										<select id="typeId" name="typeId" style="width: 330px;">
											<c:forEach items="${typeList }" var="type">
												<option value="${type.id }">${type.name }</option>
											</c:forEach>
										</select>
										确认<input type="checkbox" value="1" id="sure" name="sure"  >
									</div>
								</div>
							</c:if>
							<div class="control-group">
								<input type="hidden" id="id" name="id" >
								<label  class="control-label">题目：</label>
								<div class="controls">
						  			<textarea id="title" name="title"  style="height: 30px;width: 378px;resize: auto;"></textarea>
								</div>
					  		</div>
					  		<div class="control-group" style="line-height: 30px;">
								<label class="control-label" >类型：</label>
								<div class="controls">
									简答题
									<input type="radio" name="titleType" value="1" checked="checked">
									选择题
									<input type="radio" name="titleType" value="2">
									编写题
									<input type="radio" name="titleType" value="3">
								</div>
					  		</div>
					  		<div class="control-group">
								<label class="control-label">答案：</label>
								<div class="controls" style="width: 420px;">
									<iframe src="${path}/admin/studyManage/loadEditor" frameborder="no" style="width: 400px;height: 255px;"></iframe>
									<input type="hidden" id="answer" name="answer">
								</div>
					  		</div>
					  		<div class="control-group">
								<label class="control-label">备注：</label>
								<div class="controls">
									<iframe src="${path}/admin/studyManage/loadEditor" frameborder="no" style="width: 400px;height: 255px;"></iframe>
									<input type="hidden" id="columDesc" name="columDesc">
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
		
		$("#studyForm").validate({
			rules:{
				title:{
					required:true,
					minlength:2
				}
				
			},
			messages:{
				title:{
					required:"不能为空！",
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
		
		function searchPage(){
			$("#hideValue").val($("#searchValue").val());
			njqpage.makePage({},5);
		}
		
		/**
		 * 加载表格数据
		 */
		function queryPage(page,size){
			$.ajax({
				url:"${path}/admin/studyManage/queryTitleList",
				data:{
					page:page,
					size:size,
					stTypeId:$("#st_typeId").val(),
					stTitleType:$("input[name='st_titleType']:checked").val(),
					stSure:$("#st_sure").val(),
					searchValue:$("#st_searchValue").val()
				},
				async:false,
				type:"get",
				beforeSend:ajaxBefore(),
				success:function(data){
					ajaxAfter();
					var str="";
					$.each(data.list,function(n,d){
						str +="<tr><td><i class='icon-check-empty'></td><td ";
						if(d.sure){
							str+=" style='background-color: #9efba5;text-align: center;' ";
						}else{
							str+=" style='text-align: center;' ";
						}
						str += ">"+d.id+"</td><td ";
						if(!d.sure){
							str+=" style='color:#f1591c' ";
						}
						str+=" >"+d.title+"</td><td>"+d.typeName+"</td><td>";
						if(d.titleType == 1){
							str+="简答题";
						}else if(d.titleType == 2){
							str+="选择题";
						}else{
							str+="编程题";
						}
						str+="</td><td><a class='btn btn-info' onclick='showDialogForUpdate(this)' href='javascript:void(0)'><i  class='icon-edit'></i></a></td></tr>";
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
			window.location="${path}/admin/studyManage/issueRevise?id="+$(e).parents("tr").children()[1].innerHTML;
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
			$("#studyForm").validate().resetForm();
		}
		
		/**
		 * 保存数据
		 */
		function save(){
			if(!$("#studyForm").valid()){
				return;
			}
			if(window.frames[0].njq.hasText() == ""){
				$("#answer").val("");			
			}else{
				$("#answer").val(window.frames[0].njq.getContent());
			}
			if(window.frames[1].njq.hasText() == ""){
				$("#columDesc").val("");
			}else{
				$("#columDesc").val(window.frames[1].njq.getContent());
			}
			
			showMsg("确认","确认添加？",function(t){
				if(t){
					$.ajax({
						url:"${path}/admin/studyManage/addOrUpdate",
						type:"post",
						data:$("#studyForm").serialize(),
						success:function(data){
							if(data.state==1){
								closeDialog();
								njqpage.reMake();
							}
							showSureMessage("提示",data.message);
							location.reload(); 
						}
					})
				}
			})
		}
		/**
		 * 修改数据
		 */
		function update(){
			if(!$("#studyForm").valid()){
				return;
			}
			showMsg("确认","确认修改？",function(t){
				if(t){
					$.ajax({
						url:"${path}/admin/studyManage/addOrUpdate",
						data:$("#studyForm").serialize(),
						type:"post",
						success:function(data){
							if(data.state==1){
								closeDialog();
								njqpage.reMake();
							}
							showSureMessage("提示",data.message);
							location.reload(); 
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
				showSureMessage("提示","请先选择要删除的数据");
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
							url:"${path}/admin/studyManage/delSubject",
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
		
		function upexcel(){
	        $("#crowd_file").click();
	    }
	    function upbatchskus(target){
	        var formData = new FormData();
	        formData.append("studyFiles", target.files[0]);
	        $.ajax({
	            url:'${path}/admin/studyManage/upstudyexcel',
	            data: formData,
	            type:'POST',
	            processData: false,
	            contentType: false,
	            success: function(data){
	                alert(data.message);
	            },
	            error:function(response){
	                console.log(response);
	            }
	        });
	    }
	</script>
</body>
</html>