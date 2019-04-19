<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="${resPath }/jsTool/customPage/customPage.css" rel="stylesheet" />
<script src="${resPath }/jsTool/customPage/paginationst.js"></script>
</head>
<body>

	<!-- hasPreviousPage -->
	<!-- searchParams -->

	<!-- current -->
	<!-- begin -->
	<!-- end -->

	<!-- totalPage -->
	<!-- totalItem -->

	${req }
	${page }
	<div class="pagination pagination-centered">
		<div id="pageDiv">
			
		</div>
	</div>


	<script type="text/javascript" src="${resPath }/jquery/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			njqpage.makePage({
				excId:pageDiv,
				index:${page},
				req:"${req}"
			}); 
		})
	</script>
</body>
</html>