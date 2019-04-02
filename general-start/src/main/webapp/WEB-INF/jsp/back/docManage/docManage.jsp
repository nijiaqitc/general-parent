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
	<title>文档管理${path}</title>
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
                        <i class="icon-ambulance leftLogo"></i> tbk文档管理
                    </h2>
                    <div class="barRightDiv">
                        <a href="${path}/admin/issueDoc/issueDoc" onclick="applyChannel()"><i class="icon-plus barRightBtn"></i></a> 
                        <a href="javascript:void(0)" onclick="del()"><i class="icon-minus barRightBtn"></i></a> 
                    </div>
                </div>
                <div class="barAreaContextDiv">
                    <div style="min-height: 480px;">
                        <table class="cTable">
                            <thead>
                                <tr align="left">
                                    <th style="width: 15px;"><i id="topCheck" class="icon-check-empty" onclick="checkAllOrNot(this)" ></i></th>
                                    <th style="width: 20px;">ID</th>
                                    <th>标题</th>
                                    <th>概要</th>
                                    <th>类型</th>
                                    <th style="width: 120px;">发布时间</th> 
                                    <th style="width: 80px;">操作</th> 
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
    <script src="${resPath }/jsTool/customPage/customPage.js"></script>
    <script src="${resPath }/common/js/publicJs.js"></script>
    <!-- start:公共页，存放公共框 -->
    <jsp:include page="${path}/publicJsp"></jsp:include>
	<script type="text/javascript">
	$(document).ready(function(){
		njqpage.makePage({
			excId:pageDiv,
			click:queryPage
		}); 
	})
	
	/**
	 * 加载表格数据
	 */
	function queryPage(page,size){
		$.ajax({
			url:"${path}/admin/docManage/getDocList",
			data:{
				page:page,
				size:size
			},
			async:false,
			type:"post",
			beforeSend:ajaxBefore(),
			success:function(data){
				ajaxAfter();
				var str="";
				$.each(data.list,function(n,d){
					str +="<tr><td><i class='icon-check-empty'></td><td>"+d.id+
						"</td><td title='"+d.title+"'>"+d.formatTitle+"</td><td title='"+d.general+"'>"+d.formatGeneral+"</td><td>暂不考虑</td><td>"+d.formatCreatedDate+"</td><td><a title='修改' class='btn btn-info'  href='${path}/admin/issueDoc/upDocPage?docId="+d.id+"'><i  class='icon-edit'></i></a>&nbsp;<a title='预览' class='btn btn-info' href='${path}/doc/docView/"+d.id+"' target='_blank' ><i  class='icon-eye-open'></i></a></td></tr>";
				})
				$("#dataBody").html(str);
				njqpage.totalNum=Number(data.total);
				$("#topCheck").attr("class","icon-check-empty");
			}
		})
	}
	
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
	
	function check(e){
		if($(e.children).attr("class")=="icon-check-empty"){
			$(e.children).removeClass("icon-check-empty").attr("class","icon-check");
		}else{
			$(e.children).removeClass("icon-check").attr("class","icon-check-empty");
		}
	}
	
	function del(){
		var checkList=$("#dataBody").find(".icon-check");
		var delIds="";
		if(checkList.size()==0){
			showSureMessage("提示","请先选择要删除的文章！");
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
						url:"${path}/admin/docManage/delDoc",
						data:{
							ids:delIds
						},
						type:"post",
						beforeSend:ajaxBefore(),
						success:function(data){
							ajaxAfter();
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