<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>${doc.title }</title>
    <link rel="stylesheet" href="${resPath }/zxgj/js/prettify.css"/>
    <link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
	<link rel="stylesheet" href="${resPath }/zxgj/css/knowledgeDoc.css">
	<link rel="stylesheet" href="${resPath }/zxgj/css/grab.css">
</head>
<body>
<div class="centerDiv">
	<div id="docContext" class="docContext" align="center">
		    <div class="contextArea" >
		        <div align="center" class="textSt">${doc.title }</div>
		        <div id="textContext">${doc.doc }</div>
		    </div>
	    </div>
</div>
</body>
<script type="text/javascript" src="${resPath }/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${resPath }/zxgj/js/prettify.js"></script>
<script type="text/javascript" src="${resPath }/jsTool/customClearStyle/customClearStyle.js"></script>
<script type="text/javascript" src="${resPath }/zxgj/js/grab.js"></script>
</html>