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
		width: 360px;
		height: 30px;
		line-height: 30px;
		padding-left: 38px;
		border-bottom: 1px dashed #888;
	}
	.juanBtnStyle{
		float: right;font-size: 12px;cursor: pointer;
	}
	.juanBtnStyle i:hover{
		background: #5293b3;
	}
	.rightTip{
		float: right;
	    font-size: 12px;
	    margin-top: -10px;
        color: red;
        position: absolute;
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
							<div style="padding-left: 20px;float: left;">作者：${user.userName }</div>
							<div style="float: right;padding-right: 20px;">状态：${docInfo.statusName }</div>
						</div>
						<div style="text-indent: 24px;height: 98px;">
						  <div style="padding: 22px 10px;">
								${docInfo.contextDesc}
						  </div>
						</div>
						<div style="overflow: auto;height: 22px;">
							<div style="float: right;cursor: pointer;" >
							    <a href="#" onclick="showDialog(${docInfo.id},2,${docInfo.maxTitleIndex},${docInfo.maxOrderIndex })">
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
							<div style="padding: 10px;font-weight: 600;font-size: 20px;clear: both;overflow: auto;padding-top: 20px;"  
								attIdlabel="${doc.id}"  attTypelabel="${doc.type }" 
								attTitleIndex="${doc.titleIndex }" attOrderIndex="${doc.orderIndex }" 
								attTitle="${doc.title }" attIsShow="${doc.isShow }" attDesc="${doc.contextDesc }">
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
								<div class="juanBtnStyle" style="margin-left: 10px;" onclick="showDialog(${doc.id},3,${doc.maxTitleIndex},${doc.maxOrderIndex })">
								    <i class="icon-plus btnStyle"></i>
								</div>
								<div class="juanBtnStyle" style="margin-left: 10px;" onclick="modiShow(this)">
									<i class="icon-edit btnStyle"></i>
								</div>
								<div class="juanBtnStyle" style="margin-left: 10px;" onclick="delJuan(${doc.id})">
									<i class="icon-minus btnStyle"></i>
								</div>
								<div title="显示本卷序号" class="juanBtnStyle" style="" onclick="showIndex(${doc.id})">
									<i class="icon-eye-open btnStyle"></i>
								</div>
							</div>
						</c:if>
						<c:if test="${doc.type!=2 }">
							<div  class="titleStyle"  attIdlabel="${doc.id}"  attTypelabel="${doc.type }" 
								attTitleIndex="${doc.titleIndex }" attOrderIndex="${doc.orderIndex }" 
								attTitle="${doc.title }" attIsShow="${doc.isShow }" attDesc="${doc.contextDesc }"  >
							 <a href="novelView?docId=${doc.id }" 
							 	<c:if test="${doc.type==4 }">
							 		style="color:#cc9f10;" title="非章节"
							 	</c:if>
							 	<c:if test="${doc.type==3 }">
								     <c:if test="${doc.finishStatus==0}">style="color:green;" title="未开始"</c:if>
								     <c:if test="${doc.finishStatus==1}">style="color:red;" title="编写中"</c:if>
								     <c:if test="${doc.finishStatus==2}">style="color:black;" title="已完成"</c:if>
							 	</c:if>
							 >第${doc.titleIndex}章&nbsp;&nbsp;&nbsp;&nbsp;${doc.title}</a>
							 <a  href="javascript:void(0)"  title="修改标题信息"    onclick="modiShow(this)" style="float: left;">
                                 <i class="icon-edit"  style="line-height: 20px;margin-right: 4px;margin-left: 10px;"></i>
                             </a>
							 <a href="editNovel?docId=${doc.id}" title="修改章节信息" style="float: left;">
                                 <i class="icon-edit" style="line-height: 20px;margin-right: 4px;margin-left: 10px;"></i>
                             </a>
							 <c:if test="${doc.isShow==1 }">
								 <a href="#" style="float: left;" onclick="openOrClose(0,${doc.id})">
									 <i class="icon-eye-open" title="当前文章已可见"  style="line-height: 20px;margin-right: 4px;margin-left: 10px;"></i>
								 </a>
							 </c:if>
							 <c:if test="${doc.isShow==0 }">
                                 <a href="#" style="float: left;" onclick="openOrClose(1,${doc.id})">
                                     <i class="icon-eye-close" title="当前文章已关闭"  style="line-height: 20px;margin-right: 4px;margin-left: 10px;"></i>
                                 </a>
                             </c:if>
                             	<label class="rightTip" style="display: none;" name="pjuan-${doc.parentId }">${doc.orderIndex }</label>
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
							<input type="hidden" id="modiId" name="modiId">
							<input type="hidden" id="parentId" name="parentId" >
							<input type="hidden" id="bookId" name="bookId" value="${docInfo.id}">
							<input type="hidden" id="type" name="type" >
							<div style="padding: 4px;margin-top: 20px;" id="typeJuan">
							     类别：
							    <input type="radio" name="selectType" value="2">卷名
							    <input type="radio" name="selectType" value="3">章节
							    <input type="radio" name="selectType" value="4">非章节
							</div>
							<div style="padding: 4px;">
									<div style="font-size: 12px;color: red;margin-left: 40px;" >
										当前卷最大章节序号为：
										<label id="maxTitleIndex"></label>
									</div>
							     章序：<input id="titleIndex" numIndex="${titleIndex==null?0:titleIndex+1}" 
								     	type="text" style="width: 250px;background: #fff;" 
								     	name="titleIndex" value="${titleIndex==null?0:titleIndex+1}"
								     	autocomplete="off"
								     	oninput = "value=value.replace(/[^\d]/g,'0')" >
								    <div style="font-size: 12px;color: red;margin-left: 40px;">
										填写章节序号，不需要则设为
										<label>0</label>
								    </div>
							</div>
							<div style="padding: 4px;">
								标题：<input id="title" type="text" style="width: 250px;background: #fff;" autocomplete="off" name="title">
							</div>
							<div style="padding: 4px;">
								<div style="font-size: 12px;color: red;margin-left: 40px;">
									当前卷最大索引（章节排序）序号为：
									<label id="maxOrderIndex">0</label>
								</div>
								索引：<input  type="text" style="width: 250px;background: #fff;" id="orderIndex"
									name="orderIndex" autocomplete="off" 
									autocomplete="off"
									oninput = "value=value.replace(/[^\d]/g,'0')"
									value="${orderIndex==null?0:orderIndex+1}">
							</div>
							<div style="padding: 4px;margin-top: 20px;">
								概要：<textarea id="contextDesc" style="width: 300px;height: 100px;background: #fff;" name="contextDesc"></textarea>
							</div>
							<div style="padding: 4px;margin-top: 20px;">
								显示：<input type="radio" name="isShow" value="1" > &nbsp;&nbsp;&nbsp;&nbsp;不显示<input type="radio" name="isShow" value="0" checked="checked">
							</div>
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

	function showDialog(docId,type,tindex,oindex){
		if(type==2){
			$($("input[name='selectType']:first")).prop("checked",true);
			$($("input[name='selectType']")[1]).attr("disabled",true);
			$($("input[name='selectType']")[2]).attr("disabled",true);
		}else{
			$($("input[name='selectType']:first")).attr("disabled",true);
			$($("input[name='selectType']")[1]).attr("disabled",false);
			$($("input[name='selectType']")[2]).attr("disabled",false);
			$($("input[name='selectType']")[1]).prop("checked",true);
		}
		if(tindex){
			$("#maxTitleIndex").html(tindex);
			$("#titleIndex").val(tindex+1);
		}else{
			$("#maxTitleIndex").parent().hide();
		}
		if(oindex){
			$("#maxOrderIndex").html(oindex);
			$("input[name='orderIndex']").val(oindex+1);
		}else{
			$("#maxOrderIndex").parent().hide();
		}
		$("#parentId").val(docId);
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
		$("#type").val($("input[name='selectType']:checked").val());
		if($("input[name='selectType']:checked").val() != 2){
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
				}else{
					alert(data.message)
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
	
	function modiShow(target){
// 		$("#modiBtn").show
		/* $("#parentId").val(docId);
        $("#selectType").val(type);
        if(type==2){
            $("#titleIndex").val(0);
        }else{
            $("#titleIndex").val($("#titleIndex").attr("numIndex"));
        }
        $("#type").val(type);
        $("#parentId").val(docId); 
        
        attIdlabel="${doc.id}"  attTypelabel="${doc.type }" 
								attTitleIndex="${doc.titleIndex }" attOrderIndex="${doc.orderIndex }" attTitle="${doc.title }" attIsShow="${doc.isShow }"
        
        */
        $("#titleIndex").val($(target).parents("div").attr("attTitleIndex"));
        $("#orderIndex").val($(target).parents("div").attr("attOrderIndex"));
        $("#title").val($(target).parents("div").attr("attTitle"));
        $("#modiId").val($(target).parents("div").attr("attIdlabel"));
        
        $.each($("input[name='selectType']"),function(a,b){
        	if($(b).val()==$(target).parents("div").attr("attTypelabel")){
        		$(b).attr("disabled",false);
        		$(b).prop("checked",true);
        	}else{
        		$(b).attr("disabled",true);
        	}
        });
        $.each($("input[name='isShow']"),function(a,b){
        	if($(b).val()==$(target).parents("div").attr("attIsShow")){
        		$(b).prop("checked",true);
        	}
        });
        
        $("#contextDesc").val($(target).parents("div").attr("attDesc"));
        
        $("#modiBtn").show();
        $("#agreeBtn").hide();
        $("#bqTip").show();
        $("#custom-background").show();
	}
	
	
	function modi(){
		$.ajax({
			url:"updateJuan",
			type:"post",
			data:{
				id:$("#modiId").val(),
				titleIndex:$("#titleIndex").val(),
				title:$("#title").val(),
				orderIndex:$("#orderIndex").val(),
				contextDesc:$("#contextDesc").val(),
				isShow:$("input[name='isShow']:checked").val()
			},
			success:function(data){
				if(data.state==1){
					location.reload(); 
				}else{
					alert(data.message)
				}
			}
		}) 
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
	
	function showIndex(index){
		var ts = $("label[name='pjuan-"+index+"']");
		if(ts.length>0){
			$.each(ts,function(a,b){
				$(b).show();
			})
		}
	}
</script>
</html>