<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>菜单列表</title>
    <link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png"/>
    <link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
    <link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"/>
    <style type="text/css">
        .lmd {
            margin-left: 8px;
        }

        .contextArea div {
            clear: both;
        }

        .contextArea div:hover {
            background-color: #a8d2d2;
            color: white;
        }

        .starcl {
            color: red;
            margin-top: 5px;
            position: relative;
            float: right;
        }

        .starspan {
            float: right;
            margin-right: 4px;
            font-size: 12px;
        }

        .rightArea {
            width: 30px;
            display: block;
            height: 25px;
            float: right;
        }

        .leftlabel {
            display: block;
            width: 10px;
            float: left;
            padding-top: 5px;
        }

        .stbot {
            bottom: 0;
            position: fixed;
            width: 100%;
        }
    </style>
</head>
<body>
<div class="centerDiv">
    <div style="text-align: center;font-size: 20px;font-weight: 600;margin-top: 20px;">${searchTitle }</div>
    <div id="docContext" class="docContext" style="margin-bottom: 20px;margin-top: 10px;" align="center">
        <div class="contextArea" style="width: 1000px;text-align: left;padding: 10px 10px;">
            <c:forEach items="${titleList }" var="tt">
                <div>
                    <a class='lmd' href="knowledge/${tt.docId }" target='_blank'>${tt.title }</a>
                    <span class='rightArea'>
							<c:if test="${tt.starTab != null }">
                                <i class='icon-star starcl'></i>
                            </c:if>
						</span>
                    <span class='starspan'>${tt.createDate }</span>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<!--     开始：底部菜单栏-->
<jsp:include page="../zxgj/bottom.jsp"></jsp:include>
<script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        if ((document.documentElement.clientHeight - $("body").height()) > 80) {
            $(".bottomInfoDiv").addClass("stbot");
        } else {
            $(".bottomInfoDiv").removeClass("stbot");
        }
    })
</script>
</body>
</html>