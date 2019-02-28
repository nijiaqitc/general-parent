<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>加载菜单</title>
    <link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png"/>
    <link rel="stylesheet" href="${resPath }/back/css/bootstrap.min.css">
</head>
<body>
<div>
    <input style="margin-top: 10px;width: 500px;" placeholder="请输入主页地址" type="text" id="queryPageIndex"
           name="pageIndex"/>
    <input type="button" value="查询" onclick="queryPage()"/>
    <label id="queryResult"></label>
</div>
<form action="/grab/saveNewMenu">
    <table class="table">
        <tr>
            <td>主页地址</td>
            <td>菜单地址(分析的菜单url)</td>
            <td>类型(设置来源，如来自作者wy99)</td>
            <td>操作</td>
        </tr>
        <tr>
            <td><input type="text" name="pageIndex" style="width:100%;"/></td>
            <td><input type="text" name="menuUrl" style="width:100%;"/></td>
            <td><input type="text" name="typeName" style="width:100%;"/></td>
            <td><input type="submit" value="保存" style="width: 40px;"/></td>
        </tr>
    </table>
</form>
<div>
    <table style="width: 100%;">
        <thead>
        <tr>
            <td>id</td>
            <td>主页地址</td>
            <td>渠道</td>
            <td>创建时间</td>
            <td>开闭状态</td>
            <td>操作</td>
        </tr>
        </thead>
        <c:forEach items="${infoList }" var="info">
            <tr>
                <td>${info.id }</td>
                <td>${info.pageIndex }</td>
                <td>${info.channel }</td>
                <td>${info.createDate }</td>
                <td>
                    <c:if test="${info.loadBtn==false }">已关闭</c:if>
                    <c:if test="${info.loadBtn }">开启中</c:if>
                </td>
                <td>
                    <c:if test="${info.loadBtn==false }"><a href="javascript:void(0)"
                                                            onclick="enOrDisStatus('${info.id }','1')">开启</a></c:if>
                    <c:if test="${info.loadBtn }"><a style="color:red;" href="javascript:void(0)"
                                                     onclick="enOrDisStatus('${info.id }','0')">关闭</a></c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>


<script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
    function queryPage() {
        $.ajax({
            url: "/grab/queryMenuConfig",
            type: "POST",
            data: {
                pageIndex: $("#queryPageIndex").val()
            },
            success: function (data) {
                $("#queryResult").html(data)
            }
        })
    }


    function enOrDisStatus(id, status) {
        $.ajax({
            url: "/grab/updateStatus",
            type: "POST",
            data: {
                id: id,
                status: status
            },
            success: function (data) {
                location.reload();
            }
        })

    }
</script>
</body>
</html>