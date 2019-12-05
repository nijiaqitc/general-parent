<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>主页</title>
    <link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png"/>
    <link rel="stylesheet" href="${resPath }/grab/css/grabIndex.css">
    <jsp:include page="commonTop.jsp"></jsp:include>
</head>
<body>

<div align="center" class="asearchDiv">
    <div align="center" class="asearchDiv1">
        <div class="asearchDiv2">
            <form action="searchForList" method="get" target="_blank">
                <span><input type="text" class="asearchText" id="searchValue" name="searchValue" maxlength="60"
                             value=""></span>
                <span><input type="submit" onclick="" value="搜索一下" class="asearchbutton" id="searchButton"></span>
            </form>
        </div>
    </div>
</div>

<div align="center">
    <div class="labelArea">
        <div style="width: 516px;" class="totalMenu">
            <a href="javascript:void(0)" onclick="loadTypeData(this)">类型</a>
            <a href="javascript:void(0)" onclick="loadTipData(this)">标签</a>
            <a href="searchForList?star=1" target="_blank">星标</a>
            <a href="docListPage" target="_blank">最近更新</a>
        </div>
    </div>
</div>

<div id="tipLabel" class="label-list" align="center">
    <div class="labelInArea"></div>
</div>

<div id="typeLabel" class="label-list" align="center">
    <div class="labelInArea"></div>
</div>

<script type="text/javascript" src="${resPath }/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${resPath }/grab/js/grabIndex.js"></script>
<script type="text/javascript">
    window.onresize = function () {
        var w = document.documentElement.clientWidth;
        if (w > 940) {
            $(".labelInArea").css("width","910");
        } else {
            $(".labelInArea").css("width","520");
        }
    }
</script>
</body>
</html>