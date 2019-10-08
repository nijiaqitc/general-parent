<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>加载页面</title>
    <link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png"/>
    <link rel="stylesheet" href="${resPath }/back/css/bootstrap.min.css">
</head>
<body>
<div>
    <input style="margin-top: 10px;width: 500px;" placeholder="请输入指定页面" type="text" id="pageIndex"
           name="pageIndex"/>
	<input style="margin-top: 10px;width: 300px;" placeholder="请输入cookie，如acw_sc__v2=5d9" type="text" id="pageCookie"
           name="pageIndex"/>
    <input type="button" value="提取" onclick="grabPage()"/>
</div>


<script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
    function grabPage() {
        $.ajax({
            url: "/grab/grabAppointPage",
            type: "POST",
            data: {
            	url: $("#pageIndex").val(),
            	cookie:$("#pageCookie").val()
            },
            success: function (data) {
            	alert(data.message);
            }
        })
    }
</script>
</body>
</html>