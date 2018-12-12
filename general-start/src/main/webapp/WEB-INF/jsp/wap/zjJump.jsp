<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>网站介绍</title>
<jsp:include page="../../../wap/commonwap/common.jsp"></jsp:include>
<script type="text/javascript">
	function addTitle(e){
		var tr=$(e).parents("tr")[0];
		var inputs=$(tr).find("input");
		var desc=$(tr).find("textarea")[0];
		var index=inputs[0];
		var title=inputs[1];
		var grade=inputs[2];
		var parentId=inputs[3];
		var type=1;
		$.ajax({
			url:"${path}/novel/addTitle",
			data:{
				indexOne:index.value,
				title:title.value,
				grade:grade.value,
				type:type,
				contextDesc:desc.value,
				parentId:parentId.value
			},
			type:"post",
			success:function(data){
				if(data.state==1){
					alert("保存成功！")
					console.info(tr)
					
					
					var str="<tr>"+
						"<td>第"+data.index+"章</td>"+
						"<td>"+data.title+"</td>"+
						"<td>（"+data.grade+"）</td>"+
						"<td></td>"+
						"<td><input type='hidden' value='' /><label onclick='addInput(this)' style='cursor: pointer;'>添加</label></td>"+
					"</tr>";
					var tempNode=document.createElement("table");
					tempNode.innerHTML=str;
					var table=tr.parentNode;
					table.insertBefore(tempNode.getElementsByTagName("tr")[0],tr);
				}
			}
		})		
	}
	
	function addInput(e){
		var tr=$(e).parents("tr")[0];
		var table=tr.parentNode;
		var tempNode=document.createElement("table");
		var str="<tr>"+
			"<td><input name='index' /></td>"+
			"<td><input name='title'/></td>"+
			"<td><input name='grade'/></td>"+
			"<td>"+
			"	<textarea name='desc' style='width: 40px;height: 20px'></textarea>"+
			"	<select>"+
			"		<option value='1'>章节名</option>"+
			"		<option value='0'>卷名</option>"+
			"	</select> "+
			"	<input type='hidden' name='parentId' value='"+e.previousSibling.value+"'/>"+
			"</td>"+
			"<td><label onclick='addTitle(this)'>保存</label><label>重置</label><label>删除</label></td>"+
		"</tr>";
		tempNode.innerHTML=str;
		if(tr.nextSibling){
			table.insertBefore(tempNode.getElementsByTagName("tr")[0],tr.nextSibling);
		}else{
			table.appendChild(tr);
		}
		console.info("111")
	}
	
	function delName(){
		$.ajax({
			url:"${path}/novel/delName",
			data:{
				name:$("#delName").val()
			},
			type:"post",
			success:function(data){
				$("#name").html(data.name);
			}
		})		
	}
</script>
</head>
<body>
	<!-- 	顶部div开始 -->
	<jsp:include page="../../../wap/commonwap/top.jsp"></jsp:include>
	<!-- 	顶部div结束 -->
	
	<!-- 	正文部分开始 -->
	<div class="textContext" align="center">
		<div style="margin-top: 50px;">
			<form action="${path}/novel/showDocTitleList" >
				<input type="text" name="token"/>
				<input type="submit" value="提交"/>
			</form>
		</div>
	</div>
	<!-- 	正文部分结束 -->

	<!-- 	底部通用部分开始 -->
	<jsp:include page="../../../wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="../../../wap/commonwap/commonBottom.jsp"></jsp:include>
</body>
</html>