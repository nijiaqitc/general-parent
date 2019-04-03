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
					<div class="box span12">
						<div class="box-header">
							<h2><span class="break"></span>工具栏</h2>
							<div class="box-icon">
								<a href="javascript:void(0)"  onclick="showDialogForSave()" ><i class="icon-plus"></i></a>
								<a href="javascript:void(0)"  onclick="del()" ><i class="icon-minus"></i></a>
							</div>
						</div>
					</div><!--/span-->
				</div><!--/row-->
				<div id="codeContent"></div>
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
					<form class="form-horizontal" id="codeForm" />
						<fieldset>
							<div class="control-group">
								<label class="control-label" for="input2">类别：</label>
								<div class="controls">
									<input id="codeType" name="type" type="text"  maxlength="16" />
								</div>
					  		</div>	
							<div class="control-group">
								<input type="hidden" id="id" name="id" >
								<label  class="control-label" for="input1">名称：</label>
								<div class="controls">
						  			<input id="codeName" name="name" type="text"  maxlength="16" />
								</div>
					  		</div>
					  		<div class="control-group">
								<label class="control-label" for="input2">赋值：</label>
								<div class="controls">
									<input id="codeValue" name="value" type="text"  maxlength="16" />
								</div>
					  		</div>
					  		<div class="control-group">
								<label class="control-label" for="input2">描述：</label>
								<div class="controls">
						  			<textarea id="codeDesc" name="columDesc"  style="height: 98px;"></textarea>
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
		var page=0;
		var size=3;
		var num=0;
		var index=0;
		$(document).ready(function(){
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
			$("#codeContent").html("");
			$.ajax({
				url:"${path}/admin/dataCode/getDataList",
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
						createCode(d);
						str +="<tr><td onclick='check(this)'><i class='icon-check-empty'></td><td>"+d.id+"</td><td class='center'>"+d.name+"</td><td>";
						if(d.value != null){
							str += d.value;
						}
						str += "</td><td class='center'>";
						if(d.columDesc != null){
							str +=d.columDesc;
						}
						str +="</td><td><a class='btn btn-info' onclick='showDialogForUpdate(this)' href='javascript:void(0)'><i  class='icon-edit'></i></a></td></tr>";
						
					})
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
 			$("#codeType").val($($(e).parents("tr").children()[0]).val());
 			$("#codeType").attr("disabled",true);
 			$("#id").val($($(e).parents("tr").children()[2]).html());
			$("#codeName").val($($(e).parents("tr").children()[3]).html());
			$("#codeValue").val($($(e).parents("tr").children()[4]).html());
			$("#codeDesc").val($($(e).parents("tr").children()[5]).html());
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
			$("#codeName").val("");
			$("#codeDesc").val("");
			$("#codeValue").val("");
			$("#codeType").val("");
		}
		
		/**
		 * 保存数据
		 */
		function save(){
			showMsg("确认","确定保存？",function(t){
				if(t){
					$.ajax({
						url:"${path}/admin/dataCode/saveData",
						type:"post",
						data:$("#codeForm").serialize(),
						success:function(data){
							if(data.state==1){
								closeDialog();
								queryPage(page,size); 
								makePage();
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
			showMsg("确认","确定保存?",function(t){
				if(t){
					$.ajax({
						url:"${path}/admin/dataCode/updateData",
						data:$("#codeForm").serialize(),
						type:"post",
						success:function(data){
							if(data.state==1){
								closeDialog();
								queryPage(page,size); 
								makePage();
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
			var checkList=$("#codeContent").find(".icon-check");
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
				showMsg("确认","确定删除？",function(t){
					if(t){
						$.ajax({
							url:"${path}/admin/dataCode/delData",
							data:{
								ids:delIds
							},
							type:"post",
							success:function(data){
								if(data.state==1){
									queryPage(page,size); 
									makePage();
								}
								showSureMessage("提示",data.message);
							}
						})
					}	
				})
			}
		}
		
		
		/**
		 * 创建数据字典
		 */
		function createCode(code){
			var str = "<div class='row-fluid'>"
				+"<div class='box span12'>"
				+"<div class='box-header'>"
				+"<h2><i class='icon-caret-right'></i><span class='break'></span>"+code.type+"</h2>"
				+"<div class='box-icon'>"
				+"<a href='#' class='btn-minimize codeList' ><i class='icon-chevron-up'><input type='hidden' id='codeId' value='"+code.id+"' ></i></a>"
				+"</div>"
				+"</div>"
				+"<div class='box-content' style='display: none'>"
				+"<table class='table table-bordered table-striped table-condensed'>"
				+"<thead>"
				+"<tr>"
				+"<th style='width: 15px;'><i class='icon-check-empty'></i></th>"
				+"<th style='width: 60px;'>ID</th>"
				+"<th>名称</th>"
				+"<th>值</th>"
				+"<th>描述</th>"
				+"<th style='width:50px;'>操作</th>"
				+"</tr>"
				+"</thead>"
				+"<tbody>"
				+"</tbody>"
				+"</table>"
				+"</div>"
				+"</div>"
				+"</div>";
			$("#codeContent").append(str);
		}
		
		
		$(document).on('click', '.codeList', function(c) {
	    	c.preventDefault();
	        var b = $(this).parent().parent().next(".box-content");
	        var node=$(this).parent().parent().parent().find("tbody");
	        if (b.is(":visible")) {
	        	$($(this).parent().parent().find("i")[0]).removeClass("icon-caret-down").addClass("icon-caret-right")
	            $("i", $(this)).removeClass("icon-chevron-down").addClass("icon-chevron-up")
	        } else {
	        	$($(this).parent().parent().find("i")[0]).removeClass("icon-caret-right").addClass("icon-caret-down")
	            $("i", $(this)).removeClass("icon-chevron-up").addClass("icon-chevron-down")
	        }
	        $.ajax({
	        	url:"${path}/admin/dataCode/getCodeList",
	        	data:{
	        		id:$(this).find("input").val()	
	        	},
	        	type:"post",
	        	success:function(data){
	        		var str="";
					$.each(data.list,function(n,d){
						str +="<tr><input type='hidden' name='type' value='"+d.type+"' ><td onclick='check(this)'><i class='icon-check-empty'></i></td><td>"+d.id+"</td><td class='center'>"+d.name+"</td><td>";
						if(d.value != null){
							str+=d.value;
						}
						str+="</td><td class='center'>";
						if(d.columDesc != null){
							str += d.columDesc; 
						}
						str +="</td><td><a class='btn btn-info' onclick='showDialogForUpdate(this)' href='javascript:void(0)'><i  class='icon-edit'></i></a></td></tr>";
					})
					node.html(str);
					str="";
			        b.slideToggle()
	        	}
	        }) 
	    })
		
		
	</script>
</body>
</html>