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
						<i class="icon-ambulance leftLogo"></i> banner列表
					</h2>
					<div class="barRightDiv">
						<a href="javascript:void(0)" onclick="applyChannel()"><i
							class="barRightBtn">应用</i></a> 
						<a href="javascript:void(0)"
							onclick="upPic()"><i class="icon-plus barRightBtn"></i></a> <input
							id="upPicInput" onchange="fileChange(this)" type="file"
							style="display: none;"> 
						<a href="javascript:void(0)"
							onclick="delPic()"><i class="icon-minus barRightBtn"></i></a>
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
									<th style="width: 150px;">图片名称</th>
									<th>url</th>
									<th style="width: 30px;">应用</th>
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
				url : "${path}/admin/banner/getBannerList",
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
									+ "</td><td>"
									+ d.picPlace
									+ "</td><td>";
							if (d.isUse == 1) {
								str += "是</td><td><a class='btn btn-info' onclick='showDialogForUpdate(this)' href='javascript:void(0)'><i  class='icon-edit btnStyle'></i></a></td></tr>";
							} else {
								str += "否</td><td><a class='btn btn-info' onclick='showDialogForUpdate(this)' href='javascript:void(0)'><i  class='icon-edit btnStyle'></i></a></td></tr>";
							}
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

		function applyChannel() {
			var checkList = $("#dataBody").find(".icon-check");
			var delIds = "";
			var flag = false;
			var msg = "";
			var ty = 1;
			if (checkList.size() == 0) {
				showSureMessage("提示", "请先选择要应用的权限！");
				return;
			} else {
				for (var i = 0; i < checkList.length; i++) {
					delIds += $(checkList[i]).parent().next().html() + ","
					if ($(checkList[i]).parent().next().next().next().next()
							.html() != "是") {
						flag = true;
					}
				}
				delIds = delIds.substring(0, delIds.length - 1);
			}
			if (flag == true) {
				msg = "存在尚未应用的权限，确定应用？";
				ty = 1;
			} else {
				msg = "取消应用？";
				ty = 0;
			}
			if (delIds.length > 0) {
				showMsg("确认", msg, function(t) {
					if (t) {
						$.ajax({
							url : "${path}/admin/banner/setPicUseType",
							data : {
								ids : delIds,
								isUse : ty
							},
							type : "post",
							beforeSend : ajaxBefore(),
							success : function(data) {
								ajaxAfter();
								if (data.state == 1) {
									njqpage.reMake();
								}
								showSureMessage("提示", data.message);
							}
						})
					}
				})
			}
		}

		function upPic() {
			$("#upPicInput").click();
		}

		var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
		function fileChange(target) {
			var fileSize = 0;
			var filetypes = [ ".jpg", ".png", ".rar", ".txt", ".zip", ".doc",
					".ppt", ".xls", ".pdf", ".docx", ".xlsx" ];
			var filepath = target.value;
			var filemaxsize = 1024 * 2;//2M 
			if (filepath) {
				var isnext = false;
				var fileend = filepath.substring(filepath.indexOf("."));
				if (filetypes && filetypes.length > 0) {
					for (var i = 0; i < filetypes.length; i++) {
						if (filetypes[i] == fileend) {
							isnext = true;
							break;
						}
					}
				}
				if (!isnext) {
					alert("不接受此文件类型！");
					target.value = "";
					return false;
				}
			} else {
				return false;
			}
			if (isIE && !target.files) {
				var filePath = target.value;
				var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
				if (!fileSystem.FileExists(filePath)) {
					alert("附件不存在，请重新输入！");
					return false;
				}
				var file = fileSystem.GetFile(filePath);
				fileSize = file.Size;
			} else {
				fileSize = target.files[0].size;
			}

			var size = fileSize / 1024;
			if (size > filemaxsize) {
				alert("附件大小不能大于" + filemaxsize / 1024 + "M！");
				target.value = "";
				return false;
			}
			if (size <= 0) {
				alert("附件大小不能为0M！");
				target.value = "";
				return false;
			}

			var formData = new FormData();
			formData.append("file", target.files[0]);
			$.ajax({
				url : "upBanner",
				type : 'POST',
				data : formData,
				// 告诉jQuery不要去处理发送的数据
				processData : false,
				// 告诉jQuery不要去设置Content-Type请求头
				contentType : false,
				success : function(responseStr) {
					if (responseStr.state==1){
                        alert("上传成功！");
						njqpage.reMake();
					}else{
						alert(responseStr.message);
					}
				}
			});
		}
		
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
	</script>
</body>
</html>