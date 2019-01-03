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
<title>获取页面配置</title>
<link rel="stylesheet" href="${resPath }/back/css/bootstrap.min.css">
<style type="text/css">
td input{
	width: 100%;
}
</style>
</head>
<body>
	<div style="margin-bottom: 20px;margin-top: 10px;">
		<div>当前适用渠道：${channelList}</div>
	</div>
	<div>
		<form action="/grab/saveAndGrab">
			<table class="table">
				<tr>
					<td>标题</td>
					<td>地址</td>
					<td>docId</td>
					<td>渠道</td>
					<td>类型</td>
					<td>标签</td>
					<td>更新</td>
					<td>操作</td>
				</tr>
				<tr>
					<td><input type="text" name="title"></td>
					<td><input type="text" name="url"></td>
					<td><input type="text" name="docId"></td>
					<td><input type="text" name="channel"></td>
					<td><input type="text" name="type"></td>
					<td><input type="text" name="tips"></td>
					<td>
						<input type="radio" name="reload" value="0">不更新
						<input type="radio" name="reload" value="1">更新
					</td>
					<td><input type="submit" value="保存"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>