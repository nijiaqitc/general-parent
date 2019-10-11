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
<title>正文</title>
<jsp:include page="${path}/commonTopLink"></jsp:include>
<jsp:include page="${path}/head"></jsp:include>
<!-- 自定义分页 -->
<link href="${resPath }/jsTool/customPage/customPage.css" rel="stylesheet" />
</head>
<body>
	<!-- 通用顶部 -->
	<jsp:include page="${path}/commonTop"></jsp:include>
	<!-- 中下部分 -->
	<div id="centerPlace">
		<!-- 通用左边菜单 -->
		<jsp:include page="${path}/commonLeft"></jsp:include>
		<!-- 正文区域 -->
		<div id="rightContext">
			<div class="menu-box barAreaDiv">
				<div class="barTopw">
					<h2 class="barLeftDiv">
						<i class="icon-ambulance leftLogo"></i> chunk列表
					</h2>
					<div class="barRightDiv">
						<a href="javascript:void(0)"
							onclick=""><i class="icon-plus barRightBtn"></i></a> <input
							id="upPicInput" onchange="fileChange(this)" type="file"
							style="display: none;"> 
						<a href="javascript:void(0)"
							onclick=""><i class="icon-minus barRightBtn"></i></a>
					</div>
				</div>
				<div class="barAreaContextDiv">
					<div style="min-height: 480px;">
						<table class="cTable">
							<thead>
								<tr align="left">
									<th style="width: 30px;"><i id="topCheck"
										class="icon-check-empty" onclick="checkAllOrNot(this)"></i></th>
									<th style="width: 30px;">ID</th>
									<th style="width: 150px;">片名</th>
									<th style="width: 60px;">操作</th>
								</tr>
							</thead>
							<tbody id="dataBody"></tbody>
						</table>
					</div>
					<!--start:分页条  -->
					<div align="center">
						<div id="pageDiv"></div>
						<ul id="pageNum"></ul>
					</div>
					<!-- end:分页条 -->
				</div>
			</div>
		</div>
		<!-- 通用底部 -->
		<jsp:include page="${path}/commonBottom"></jsp:include>
	</div>
	
	<!-- start:弹出框 -->
	<div class="modal hide fade in" id="myModal" aria-hidden="false" style="display: none;">
		<div class="modal-header">
			<button type="button" onclick="closeDialog()" class="close" data-dismiss="modal">×</button>
			<h3 id="dialogTitleName"></h3>
		</div>
		<div class="modal-body">
			<div class="step-pane" id="step2" style="margin-top: 20px;">
				<form class="form-horizontal" id="ruleForm" />
					<fieldset>	
						<div class="control-group">
							<input type="hidden" id="id" name="id" >
							<label  class="control-label" for="input1">名称：</label>
							<div class="controls">
					  			<input id="ruleName" name="ruleName" type="text" style="width: 180px;"  maxlength="10" />
							</div>
				  		</div>
				  		<div class="control-group">
							<label class="control-label" for="input2">序号：</label>
							<div class="controls">
					  			<textarea id="ruleDesc" name="columDesc"  style="height: 98px;width: 180px;"></textarea>
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
	<jsp:include page="${path}/foot"></jsp:include>
	<jsp:include page="${path}/commonBottomLink"></jsp:include>
	<script src="${resPath }/jsTool/customPage/customPage.js"></script>
	<script src="${resPath }/common/js/publicJs.js"></script>
	<!-- start:公共页，存放公共框 -->
	<jsp:include page="${path}/publicJsp"></jsp:include>
	<!-- end:公共页，存放公共框 -->
	<script type="text/javascript">
		$(document).ready(function() {
			njqpage.makePage({
				excId : pageDiv,
				click : queryPage,
				size : 10
			});
		})

		/**
		 * 加载表格数据
		 */
		function queryPage(page, size) {
			$.ajax({
				url : "${path}/admin/notes/chunkList",
				data : {
					page : page,
					size : size
				},
				async : false,
				type : "post",
				beforeSend : ajaxBefore(),
				success : function(data) {
					ajaxAfter();
					var str = "";
					$.each(data.list,function(n, d) {
							str += "<tr><td><i class='icon-check-empty'></td><td>"
									+ d.id
									+ "</td><td class='center'>"
									+ d.name
									+ "</td><td><a class='btn btn-info' onclick='' href='javascript:void(0)'><i  class='icon-edit btnStyle'></i></a></td></tr>";
					})
					$("#dataBody").html(str);
					//$("#topCheck").attr("class","icon-check-empty");
					njqpage.totalNum = Number(data.total);
				}
			})
		}

		function checkAllOrNot(e) {
			if ($(e).attr("class") == "icon-check-empty") {
				$(e).removeClass("icon-check-empty")
						.attr("class", "icon-check");
				$.each($("tbody tr"), function(a, b) {
					$(b.children[0].children[0]).attr("class", "icon-check");
					$(b).css("backgroundColor", "#eee");
				})
			} else {
				$(e).removeClass("icon-check")
						.attr("class", "icon-check-empty");
				$.each($("tbody tr"), function(a, b) {
					$(b.children[0].children[0]).attr("class",
							"icon-check-empty");
					$(b).css("backgroundColor", "#fff");
				})
			}
		}

		$(document).on("click","tbody tr",
			function() {
				$(this).css("backgroundColor", "#eee");
				if ($(this.children[0].children).attr("class") == "icon-check-empty") {
					$(this.children[0].children).removeClass(
							"icon-check-empty").attr("class",
							"icon-check");
				} else {
					$(this.children[0].children).removeClass(
							"icon-check").attr("class",
							"icon-check-empty");
				}
		});

		
		function delPic(){
			var checkList=$("#dataBody").find(".icon-check");
            var delIds="";
            if(checkList.size()==0){
                showSureMessage("sure","提示","请先选择要删除的图片");
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
                            url:"delBanner",
                            data:{
                            	delIds:delIds
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
		 * 修改显示弹出框
		 */
		function showDialogForUpdate(e){
			$("#saveButton").hide();
			$("#updateButton").show();
			$("#dialogTitleName").html("修改");
 			$("#id").val($($(e).parents("tr").children()[1]).html());
			$("#ruleName").val($($(e).parents("tr").children()[2]).html());
			$("#ruleDesc").val($($(e).parents("tr").children()[5]).html());
			$("#myModal").show();
			$("#backBlackGround").show();
		}
		
		/**
		 * 新增显示弹出框
		 */
		function showDialogForSave(){
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
			$("#ruleName").val("");
			$("#ruleDesc").val("");
			$("#ruleForm").validate().resetForm();
		}
		
		/**
		 * 保存数据
		 */
		function save(){
			if(!$("#ruleForm").valid()){
				return;
			}
			showMsg("确定","确定保存信息？",function(t){
				if(t){
					$.ajax({
						url:"${path}/admin/ruleManage/saveRule",
						type:"post",
						beforeSend:ajaxBefore(),
						data:$("#ruleForm").serialize(),
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
			if(!$("#ruleForm").valid()){
				return;
			}
			showMsg("确认","确认修改？",function(t){
				if(t){
					$.ajax({
						url:"${path}/admin/ruleManage/updateRule",
						data:$("#ruleForm").serialize(),
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
	</script>
</body>
</html>