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
						<i class="icon-ambulance leftLogo"></i> 笔记列表
					</h2>
					<div class="barRightDiv">
						<a href="javascript:void(0)" onclick=""><i class="icon-minus barRightBtn"></i></a>
					</div>
				</div>
				<div class="barAreaContextDiv">
					<div style="min-height: 480px;">
						<table class="cTable">
							<thead>
								<tr align="left">
									<th style="width: 30px;"><i id="topCheck" class="icon-check-empty" onclick="checkAllOrNot(this)"></i></th>
									<th style="width: 30px;">ID</th>
									<th style="width: 30px;">分片名称</th>
									<th style="width: 150px;">描述</th>
									<th style="width: 150px;">序号</th>
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
	
	<jsp:include page="${path}/commonBottomLink"></jsp:include>
	<jsp:include page="${path}/foot"></jsp:include>
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
				url : "${path}/admin/notes/reviewList",
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
									+ d.index
									+ "</td><td>"+d.index
									+ "</td><td>"+d.index
									+ "</td><td><a class='btn btn-info'  href='${path}/admin/notes/aupage?id='"+d.id+"><i  class='icon-edit btnStyle'></i></a>"
									+ "</td></tr>";
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

		
		function delReview(){
			var checkList=$("#dataBody").find(".icon-check");
            var delIds="";
            if(checkList.size()==0){
                showSureMessage("sure","提示","请先选择要删除的笔记");
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
		
	</script>
</body>
</html>