<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>404</title>
    <link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png"/>
    <link rel="stylesheet" href="${resPath }/zxgj/js/prettify.css"/>
    <link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
    <link rel="stylesheet" href="${resPath }/zxgj/css/knowledgeDoc.css">
    <link rel="stylesheet" href="${resPath }/zxgj/css/grab.css">
    <link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"/>
    <style type="text/css">
        .stbot {
            bottom: 0;
            position: fixed;
            width: 100%;
        }

        .tipcs {
            text-align: center;
            font-size: 12px;
            border-bottom: 1px solid #ddd;
            width: 600px;
        }

        .tipcs span {
            padding: 0px 14px;
        }

        .toptip {
            padding: 2px;
            margin-left: 6px;
            cursor: pointer;
            display: block;
            float: left;
        }

        .labelsavebtn {
            margin-left: 14px;
            border: 1px solid #eee;
            cursor: pointer;
        }

        .labelsavebtn:hover {
            background-color: #ddd;
        }

        .toptip:hover {
            background-color: #ddd;
        }

        .classify-more {
            height: 40px;
            box-sizing: border-box;
            text-align: center;
            padding-top: 20px;
        }

        .classify-more p {
            display: inline-block;
            width: 350px;
            border-top: 1px solid #ddd;
            height: 0;
        }

        .classify-more p span {
            display: inline-block;
            padding: 0 31px;
            background: #fff;
            position: relative;
            top: -11px;
        }

        .classify-more p span a {
            padding: 20px;
            font-size: 14px;
            color: #666;
            -webkit-transition: all .2s;
            -o-transition: all .2s;
            transition: all .2s;
            cursor: pointer;
            text-decoration: none;
        }

        .letop {
            top: 0px;
            right: 0px;
            position: fixed;
            width: 20px;
            background-color: red;
            color: black;
            z-index: 99999;
            cursor: pointer;
            text-align: center;
            margin-right: 20px;
        }

        .bottomlrmenu {

        }

        .bottomlrmenu a:hover {
            background-color: #59b5a4;
            color: white;
            padding: 2px 2px;
        }

        .lrdiv {
            width: 50%;
            float: left;
        }

        .lrdivspan {
            margin-left: 14px;
        }

        .contextArea a:hover {
            background-color: #2f889a;
            color: white;
        }

        .starcs {
            padding: 2px 4px;
            cursor: pointer;
            float: right;
            border: 1px solid #eee;
        }

        .starcs:hover {
            background-color: #ddd;
        }
    </style>
</head>
<body>
<div class="centerDiv">
    <div id="docContext" class="docContext" align="center">
        <div class="contextArea">
            	无！
        </div>
    </div>
</div>


<!--     开始：底部菜单栏-->
<jsp:include page="../zxgj/bottom.jsp"></jsp:include>
</body>
<script type="text/javascript" src="${resPath }/jquery/jquery.min.js"></script>
</html>