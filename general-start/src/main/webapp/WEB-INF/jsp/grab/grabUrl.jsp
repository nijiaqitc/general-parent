<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
    <link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png"/>
    <link rel="stylesheet" href="${resPath }/back/css/bootstrap.min.css">
    <style type="text/css">
        td input {
            width: 100%;
        }
    </style>
</head>
<body>
<div>
    <div>加载读取已配置的文章</div>
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
                    <td>类型(必填)</td>
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
                        <input type="radio" name="reload" checked value="0">不更新
                        <input type="radio" name="reload" value="1">更新
                    </td>
                    <td><input type="submit" value="保存"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div>
    <div>加载未配置指定标签内的文章(渠道指 文章来源 类型指归档为哪个类，如https://www.cnblogs.com/sllina/p/5694808.html 渠道:cnblogs 类型：sllina)</div>
    <form action="/grab/grabCustomDoc">
        <table class="table">
            <tr>
                <td>标题</td>
                <td>地址</td>
                <td style="width: 140px;">提取类型</td>
                <td>获取标签</td>
                <td>渠道</td>
                <td>类型(必填)</td>
                <td>标签</td>
                <td>操作</td>
            </tr>
            <tr>
                <td><input type="text" name="title"></td>
                <td><input type="text" name="url"></td>
                <td>
                    <input type="radio" name="getType" value="1">id
                    <input type="radio" name="getType" value="2">标签
                    <input type="radio" name="getType" value="3">class
                </td>
                <td><input type="text" name="name"></td>
                <td><input type="text" name="channel"></td>
                <td><input type="text" name="type"></td>
                <td><input type="text" name="tips"></td>
                <td><input type="submit" value="保存"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>