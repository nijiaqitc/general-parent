<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小说标题列表</title>
<jsp:include page="${path}/commonTopLink"></jsp:include>
<style type="text/css">
	.titleStyle{
		float: left;
		width: 228px;
		height: 30px;
		line-height: 30px;
		padding-left: 38px;
		border-bottom: 1px dashed #888;
	}
</style>
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
			<div align="center">
				<div class="menu-box" style="padding-top: 10px;width: 700px;" align="left">
					<div style="width: 160px;height: 230px;background-color: #fff;float: left;">
						<div style="width: 20px;font-size: 20px;padding: 18px;">${docInfo.title}</div>
					</div>
					<div style="float: left;width: 534px;height: 230px;">
						<div style="height: 110px;border-bottom: 1px dashed #888;">
							<div style="padding: 30px 10px;font-size: 20px;font-weight: 600;">${docInfo.title}</div>
							<div style="padding-left: 20px;float: left;">作者：琦三叔</div>
							<div style="float: right;padding-right: 20px;">状态：未开始</div>
						</div>
						<div style="text-indent: 24px;height: 98px;">
						  <div style="padding: 22px 10px;">
								${docInfo.contextDesc}
						  </div>
						</div>
						<div style="overflow: auto;height: 22px;">
							<div style="float: right;cursor: pointer;" onclick="showDialog(${docInfo.id},2)">
							    <a href="#" onclick="showDialog()">
								    <i class="icon-plus btnStyle "></i>
							    </a>
<!-- 								<i  class="icon-edit btnStyle"></i> -->
							</div>
						</div>
					</div>
				</div>
			</div>
			<div align="center" style="margin-top: 40px;">
				<div style="width: 800px;" align="left">
					<c:forEach items="${list }" var="doc">
						<c:if test="${doc.type==2 }">
							<div style="padding: 10px;font-weight: 600;font-size: 20px;clear: both;overflow: auto;padding-top: 20px;">
								<div style="float: left;">${doc.title } &nbsp;&nbsp;&nbsp;&nbsp; ${doc.contextDesc }</div>
								<c:if test="${doc.isShow==1 }">
	                                <a href="#" style="float: left;" onclick="openOrClose(0,${doc.id})">
	                                    <i class="icon-eye-open" style="line-height: 20px;margin-right: 4px;margin-left: 10px;"></i>
	                                </a>
	                            </c:if>
	                            <c:if test="${doc.isShow==0 }">
	                                <a href="#" style="float: left;" onclick="openOrClose(1,${doc.id})">
	                                    <i class="icon-eye-close" style="line-height: 20px;margin-right: 4px;margin-left: 10px;"></i>
	                                </a>
	                            </c:if>
								<div style="float: right;font-size: 12px;cursor: pointer;margin-left: 10px;" onclick="showDialog(${doc.id},3)">
								    <i class="icon-plus btnStyle"></i>
								</div>
								<div style="float: right;font-size: 12px;cursor: pointer;margin-left: 10px;" onclick="modiShow(${doc.id})">
									<i class="icon-edit btnStyle"></i>
								</div>
								<div style="float: right;font-size: 12px;cursor: pointer;" onclick="delJuan(${doc.id})">
									<i class="icon-minus btnStyle"></i>
								</div>
							</div>
						</c:if>
						<c:if test="${doc.type!=2 }">
							<div class="titleStyle" >
							 <a href="novelView?docId=${doc.id }" 
							     <c:if test="${doc.finishStatus==0}">style="color:green;" title="未开始"</c:if>
							     <c:if test="${doc.finishStatus==1}">style="color:red;" title="编写中"</c:if>
							     <c:if test="${doc.finishStatus==2}">style="color:black;" title="已完成"</c:if>
							 >第${doc.titleIndex}章&nbsp;&nbsp;&nbsp;&nbsp;${doc.title}</a>
							 <a href="editNovel?docId=${doc.id}" style="float: left;">
                                 <i class="icon-edit" style="line-height: 20px;margin-right: 4px;margin-left: 10px;"></i>
                             </a>
							 <c:if test="${doc.isShow==1 }">
								 <a href="#" style="float: left;" onclick="openOrClose(0,${doc.id})">
									 <i class="icon-eye-open" style="line-height: 20px;margin-right: 4px;margin-left: 10px;"></i>
								 </a>
							 </c:if>
							 <c:if test="${doc.isShow==0 }">
                                 <a href="#" style="float: left;" onclick="openOrClose(1,${doc.id})">
                                     <i class="icon-eye-close" style="line-height: 20px;margin-right: 4px;margin-left: 10px;"></i>
                                 </a>
                             </c:if>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</div>
		</div>
		<div id="bqTip" class="custom-customModel" style="display: none;">
			<form action="addTitle" method="post" id="titleForm">
				<div class="custom-customModel-header">
					<h3>新增／修改</h3>
				</div>
				<div class="custom-customModel-body" style="height: 406px;">
					<div class="body-div1" align="center">
						<div align="left" style="width: 310px;">
							<input type="hidden" id="parentId" name="parentId" >
							<input type="hidden" id="bookId" name="bookId" value="${docInfo.id}">
							<input type="hidden" id="type" name="type" >
							<div style="padding: 4px;margin-top: 20px;" id="typeJuan">
							     类别：
							 <select id="selectType" disabled="disabled" style="width: 250px;" name="type"><option value="2">卷名</option><option value="3">标题</option></select>
							</div>
							<div style="padding: 4px;margin-top: 20px;">
							     章节：<input id="titleIndex" numIndex="${titleIndex==null?0:titleIndex+1}" type="text" style="width: 250px;background: #fff;" name="titleIndex" value="${titleIndex==null?0:titleIndex+1}">
							<label style="font-size: 12px;color: red;margin-left: 40px;">不需要章节数则设为0</label>
							</div>
							<div style="padding: 4px;margin-top: 10px;">标题：<input id="title" type="text" style="width: 250px;background: #fff;" name="title"></div>
							<div style="padding: 4px;margin-top: 20px;">索引：<input  type="text" style="width: 250px;" readonly="readonly" name="orderIndex" value="${orderIndex==null?0:orderIndex+1}"></div>
							<div style="padding: 4px;margin-top: 20px;">概要：<textarea id="contextDesc" style="width: 300px;height: 100px;background: #fff;" name="contextDesc"></textarea></div>
							<div style="padding: 4px;margin-top: 20px;">显示：<input type="radio" name="isShow" value="1" > &nbsp;&nbsp;&nbsp;&nbsp;不显示<input type="radio" name="isShow" value="0" checked="checked"></div>
						</div>
					</div>
				</div>
				<div class="custom-customModel-bottom" >
					<a href="javascript:void(0)" id="closeBtn" class="custom-customModel-btnCancel" onclick="closeDialog()">关闭</a>
					<a href="javascript:void(0)" id="agreeBtn" class="custom-customModel-btnCancel" onclick="saveInfo()">确定</a>
					<a href="javascript:void(0)" id="modiBtn" class="custom-customModel-btnCancel" onclick="modi()">修改</a>
				</div>
			</form>
		</div>
		<!--end:弹出框-->
		<!--start:遮罩层-->
		<div id="custom-background" style="display: none;"></div>
		<!--end:遮罩层-->
		<!-- 通用底部 -->	
		<jsp:include page="${path}/commonBottom"></jsp:include>
	</div>
</body>
<jsp:include page="${path}/commonBottomLink"></jsp:include>
<script type="text/javascript">

	function showDialog(docId,type){
		$("#parentId").val(docId);
		$("#selectType").val(type);
		if(type==2){
			$("#titleIndex").val(0);
		}else{
			$("#titleIndex").val($("#titleIndex").attr("numIndex"));
		}
		$("#type").val(type);
		$("#bqTip").show();
		$("#modiBtn").hide();
        $("#agreeBtn").show();
		$("#custom-background").show();
		$("#parentId").val(docId);
	}
	
	function closeDialog(){
		$("#bqTip").hide();
		$("#custom-background").hide();
	}
	
	function saveInfo(){
		if($("#selectType").val()==3){
            $("#titleForm").submit();
            return;
        }
		$.ajax({
			url:"addJuan",
			type:"post",
			data:$("#titleForm").serialize(),
			success:function(data){
				if(data.state==1){
					location.reload(); 
				}
			}
		})
	}
	function saveTitle(docId){
		$("#titleForm").submit();
	}
	
	function delJuan(docId){
		alert("请勿随意删除卷")
		/* $.ajax({
            url:"delJuan",
            type:"post",
            data:{
            	docId:docId
            },
            success:function(data){
                if(data.state==1){
                    location.reload(); 
                }
            }
        }) */
	}
	
	function modiShow(docId){
// 		$("#modiBtn").show
		/* $("#parentId").val(docId);
        $("#selectType").val(type);
        if(type==2){
            $("#titleIndex").val(0);
        }else{
            $("#titleIndex").val($("#titleIndex").attr("numIndex"));
        }
        $("#type").val(type);
        $("#parentId").val(docId); */
        $("#titleIndex").val(0);
        $("#title").val("1111");
        $("#contextDesc").val();
        $("#modiBtn").show();
        $("#agreeBtn").hide();
        $("#bqTip").show();
        $("#custom-background").show();
	}
	
	
	function modi(){
		/* $.ajax({
			url:"updateJuan",
			data:{
				id:$("#parentId").val(),
				title:$("#title").val(),
				contextDesc:$("#contextDesc").val(),
				isShow:$("input[name='isShow']:checked").val()
			},
			success:function(data){
				
			}
		}) */
	}
	
	
	function openOrClose(isShow,id){
		$.ajax({
            url:"updateShowType",
            type:"post",
            data:{
                id:id,
                isShow:isShow
            },
            success:function(data){
            	if(data.state==1){
                    location.reload(); 
                }
            }
        })
	}
	
</script>
</html>