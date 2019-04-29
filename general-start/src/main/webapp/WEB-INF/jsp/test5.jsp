<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${resPath }/jsTool/decodeJava.js"></script>
<title>Insert title here</title>

</head>
<body>
	<textarea style="width: 100%;height: 200px;" id="textInfo">111</textarea>
	<textarea style="width: 100%;height: 200px;" id="outInfo"></textarea>
	<script type="text/javascript" src="${resPath }/jquery/jquery.min.js"></script>
	<script type="text/javascript">
		decodeJ($("#textInfo").val());
	</script>
</body>
</html>