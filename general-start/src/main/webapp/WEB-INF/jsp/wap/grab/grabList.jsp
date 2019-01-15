<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<?xml version="1.0" encoding="UTF-8"?>  
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>  
  <title>文章动态</title> 
  <meta name="keywords" content="文章,列表,动态文章">
  <meta name="description" content="显示最新发表的文章"> 
  <jsp:include page="${resPath }/wap/commonwap/common.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="${resPath }/wap/css/docTrends.css"  >
</head> 
<body>
	<!-- 	顶部div开始 -->
	<jsp:include page="${resPath }/wap/commonwap/top.jsp"></jsp:include>
	<!-- 	顶部div结束 -->
	<jsp:include page="${resPath }/wap/commonwap/loading.jsp"></jsp:include>
	<!-- 	内容区域开始 -->
	<div class="textContext adocDiv10" >
		<c:forEach items="${grabList }" var="gb" varStatus="index1" >
			<div style="cursor: pointer;margin-top: 10px;" onclick="showNext(this)">${gb.type } ※</div>
			<div class="menu-box" style="height: auto;line-height: 30px;padding-left: 10px;display: none;">
				<c:forEach items="${gb.grabTitleVOList }" var="grab" varStatus="index2" >
					<div class="adocDiv2" style="clear: both;overflow: auto;">
						<div style="float: left;">
						<c:if test="${grab.childrenCount>0 }">
							<a href="javascript:void(0)" onclick="loadChild('${grab.id}','${grab.channel}',this)">
								↓ (${grab.childrenCount })
							</a>
						</c:if>
						<c:if test="${grab.childrenCount==0 }">
							.
						</c:if>
						</div>
						<a href="${path}/wap/grab/${grab.id }">
							<div class="adocDiv6">${grab.title }</div>
						</a>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
	<!-- 	内容区域结束 -->
	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
</body> 
<script type="text/javascript">

	function loadChild(docId,channel,target){
		if($(target).attr("loaded")=="true"){
			return;
		}else{
			$(target).attr("loaded",true)
		}
		$.ajax({
			url:"${path}/wap/grab/noteList",
			type:"post",
			data:{
				docId:docId,
				channel:channel	
			},
			success:function(data){
				var left = $(target).parents(".adocDiv2").parent()[0].style.marginLeft;
				if(left == ""){
					left = 10+"px";
				}else{
					left=(Number(left.substring(0,left.length-2))+10)+"px";
				}
				var str = "<div style='margin-left: "+left+";'>";
				$.each(data[0].grabTitleVOList,function(a,b){
					str += "<div  class='adocDiv2' style='overflow: auto;clear: both;'>"+
							"<div style='float: left;'>";
							if(b.childrenCount>0){
								str+= "<a href='javascript:void(0)' onclick='loadChild(\""+b.id+"\",\""+b.channel+"\",this)'>"+
									"↓ ("+b.childrenCount+")"+
								"</a>";
							}else{
								str+=".";
							}
							str+="</div>"+
							"<a href='${path}/wap/grab/"+b.id+"'>"+
								"<div class='adocDiv6'>"+b.title+"</div>"+
							"</a>"+
						"</div>";
				
				});
				str+="</div>";
				$(target).parents(".adocDiv2").after(str);
			}
		})
	}

	function showNext(target){
		if($(target).next().css("display")=="block"){
			$(target).next().hide();
		}else{
			$(target).next().show();
		}
	}
</script> 
</html>