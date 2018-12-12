<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>加载菜单</title>
<link rel="stylesheet" href="${resPath }/back/css/bootstrap.min.css">
</head>
<body>
	<div>
		<input style="margin-top: 10px;width: 500px;" placeholder="请输入主页地址" type="text" id="queryPageIndex" name="pageIndex" />
		<input type="button" value="查询" onclick="queryPage()" />
		<label id="queryResult"></label>
	</div>
	<form action="/grab/saveNewMenu">
		<table class="table">
			<tr>
				<td>主页地址</td>
				<td>菜单地址</td>
				<td>类型</td>
				<td>操作</td>
			</tr>
			<tr>
				<td><input type="text" name="pageIndex" style="width:100%;" /></td>
				<td><input type="text" name="menuUrl" style="width:100%;" /></td>
				<td><input type="text" name="typeName" style="width:100%;" /></td>
				<td><input type="submit" value="保存" style="width: 40px;" /></td>
			</tr>
		</table>
	</form>
	<script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		function queryPage(){
			$.ajax({
				url:"/grab/queryMenuConfig",
				type:"POST",
				data:{
					pageIndex:$("#queryPageIndex").val()
				},
				success:function(data){
					$("#queryResult").html(data)
				}
			})
		}
		
	</script>
</body>
</html>