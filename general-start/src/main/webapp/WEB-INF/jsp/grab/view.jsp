<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${resPath }/zxgj/js/prettify.css"/>
</head>
<body>

${doc}

<script type="text/javascript" src="${resPath }/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${resPath }/zxgj/js/prettify.js"></script>
<script type="text/javascript">
    $(function(){
        $("pre").addClass("prettyprint");//如果其他地方也要用到pre，我们可以再加一个父标签的选择器来区分
        prettyPrint();//代替body上的onload事件加载该方法
    })
</script>
</body>
</html>
