<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>菜单列表</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
</head>
<body>
	<div class="centerDiv">
		<div id="docContext" class="docContext" align="center">
			    <div class="contextArea" >
			    	<c:forEach items="${titleList }" var="t">
				    	<div> 
				    		<span>${t.id }</span>
				    		<span>${t.title}</span>
				    		<span>${t.docId }</span>
				    	</div>
			    	</c:forEach>
			    </div>
		    </div>
	</div>
	<script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$.ajax({
			url:"",
			data:{
				load:${type}
			},
			type:"post",
			success:function(data){
			
			}
		});
	
	</script>
</body>
</html>