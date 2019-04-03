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
			<jsp:include page="${path}/left"></jsp:include> 
			<!-- end: 左边菜单 -->
			<!-- start: 正文 -->
			<div id="content" class="span10" >
				<div class="row-fluid" >
					<div class="box span6" style="width: 100%;    margin-top: 5px;">
						<div class="box-header">
							<h2><i class="icon-align-justify"></i><span class="break"></span>所有用户</h2>
							<div class="box-icon">
							</div>
						</div>
						<div class="box-content custom_pagination">
							<div style="width: 100%;height: 50px;background-color: #eee;">
								<div style="float: right;">
<!-- 									开始：<input placeholder="请输入日期" id="startDate" class="laydate-icon" onclick="laydate()"> -->
<!-- 									结束：<input placeholder="请输入日期" id="endDate" class="laydate-icon" onclick="laydate()"> -->
										<input type="text" class="Wdate" id="searchStart" name="stime" style="width:100px;margin-top: 10px;background-color: #fff;vertical-align: middle;cursor: context-menu;" readonly="readonly" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'searchEnd\')}',dateFmt:'yyyy-MM-dd'})">
										- <input type="text" class="Wdate" id="searchEnd" name="etime" style="width:100px;margin-top: 10px;background-color: #fff;vertical-align: middle;cursor: context-menu;" readonly="readonly" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'searchStart\')}',dateFmt:'yyyy-MM-dd'})"  >
									<a href="javascript:void(0)" type="button" id="searchId" name="searchId"  onclick="downLoad()" style="width: 42px;border: 1px solid #ddd;text-decoration: none;margin: 0px 3px;padding: 5px 6px;font-size: 14px;color: #383e4b;">下载</a>
									<input type="hidden" id="hideValue" >
									<input id="searchValue" name="searchValue" type="text" style="margin-top: 10px;">
									<a href="javascript:void(0)" type="button" id="searchId" name="searchId"  onclick="searchPage()" style="width: 42px;border: 1px solid #ddd;text-decoration: none;margin: 0px 3px;padding: 5px 6px;font-size: 14px;color: #383e4b;">确定</a>						
								</div>
							</div>
							<table class="table">
								  <thead>
									  <tr>
										  <th style="width: 20px;">ID</th>
										  <th style="width: 30px;">用户</th>
										  <th>表名</th>
										  <th style="width: 30px;">动作</th>
										  <th>内容</th>
										  <th style="width: 135px;">操作时间</th> 
									  </tr>
								  </thead>   
								  <tbody id="dataBody">
									 <tr>
										<td>Samppa Nori</td>
										<td class="center">2012/01/01</td>
										<td class="center">Member</td>
										<td></td>
										<td></td>
										<td></td>
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
		<!--end:遮罩层-->
		<jsp:include page="${path}/boom"></jsp:include>
	</div>
	<jsp:include page="${path}/foot"></jsp:include>
	<script type="text/javascript" src="${resPath }/chajian/my97/WdatePicker.js"></script>
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
		
		function searchPage(){
			$("#hideValue").val($("#searchValue").val());
			njqpage.makePage({},5);
		}
		/**
		 * 加载表格数据
		 */
		function queryPage(page,size){
			$.ajax({
				url:"${path}/admin/operLog/getLogList",
				data:{
					page:page,
					size:size,
					searchValue:$("#hideValue").val(),
					start:$("#searchStart").val(),
					end:$("#searchEnd").val()
				},
				async:false,
				type:"post",
				beforeSend:ajaxBefore(),
				success:function(data){
					ajaxAfter();
					var str="";
					$.each(data.list,function(n,d){
						str +="<tr><td>"+
							d.id+"</td><td class='center'>"+d.userId+"</td><td class='center'>"+d.operTable+
							"</td><td>"+d.type+"</td><td>"+d.operCon+"</td><td>"+d.formatDate+"</td></tr>";
					})
					$("#dataBody").html(str);
					njqpage.totalNum=Number(data.total);
				}
			})
		}
		
		/*
		 * 导出日志信息
		 */
		function downLoad(){
			showMsg("确认","确定导出？",function(t){
				if(t){
					 var form=$("<form>");//定义一个form表单
				     form.attr("style","display:none");
				     form.attr("target","");
				     form.attr("method","post");
				     form.attr("action","${path}/admin/operLog/downLoadLog");
				     var input1 = $("<input>");
			         input1.attr("type", "hidden");
			         input1.attr("name", "start");
			         var input2 = $("<input>");
			         input2.attr("type", "hidden");
			         input2.attr("name", "end");
			         var input3 = $("<input>");
			         input3.attr("type", "hidden");
			         input3.attr("name", "searchValue");
		 	         input1.attr("value", $("#searchStart").val());
			         input2.attr("value", $("#searchEnd").val());
			         input3.attr("value", $("#hideValue").val());
		 	         form.append(input1);
		 	         form.append(input2);
		 	         form.append(input3);
				     form.submit();//表单提交
				}
			});
		}
	</script>
</body>
</html>