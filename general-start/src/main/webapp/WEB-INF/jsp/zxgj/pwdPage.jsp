<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>密码页面</title>
    <link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png"/>
</head>
<body>
    <form action="/setPwd" method="post">
        <input type="text" name = "token" >
        <input type="submit" name="提交">
    </form>
</body>
</html>