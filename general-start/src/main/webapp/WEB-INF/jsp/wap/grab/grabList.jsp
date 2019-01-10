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
	<div class="textContext adocDiv10" style="text-align: center;">
		<SELECT id="seleContr" onchange = "seleVal(this)"></SELECT>
		<div id="subArea"></div>
		
	</div>
	<!-- 	内容区域结束 -->
	
	<!-- 	底部通用部分开始 -->
	<jsp:include page="${resPath }/wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="${resPath }/wap/commonwap/commonBottom.jsp"></jsp:include>
</body> 
<script type="text/javascript">

	loadMenu("","yhwiki",function(data){
		var str = ""; 
		$.each(data,function(a,b){
			str +="<OPTION value='"+b.id+"'>"+b.title+"</OPTION>";
		});
		$("#seleContr").append(str);
	});
	
	function loadMenu(docId,channel,fun,t){
		$.ajax({
			url:"${path}/wap/grab/noteList",
			type:"post",
			data:{
				docId:docId,
				channel:channel				
			},
			success:function(data){
				if(t!=null){
					$(t).parents(".adocDiv1").after(fun(data));
				}else{
					fun(data);	
				}
				console.info(data)
			}
		})
	
	}

	function seleVal(target){
		loadMenu($(target).val(),"yhwiki",function(data){
			var str = ""; 
			$.each(data,function(a,b){
				str += "<div class='menu-box adocDiv1' style='height: 40px;line-height: 30px;'>"+
								"<div class='adocDiv2'>"+
								"<div class='adocDiv6'>";
				if(b.childrenCount>0){
					str += "<span><a href = 'javascript:void(0)' onclick='loadsubMenu("+b.id+",this)'>↓"+b.childrenCount+"</a></span>";
				}
				str += "<span style='margin-left: 6px;'>"+"<a href='${path}/wap/grab/"+b.docId+"'>" ;
				if(b.title.length>20){
					str += b.title.substring(0,15)+"...";
				}else{
					str += b.title;
				}
					str +=  "</a></span></div>"+
							"</div>"+
							"</div>";
			});
			$("#subArea").html(str)	;	
		});
		console.info(target)
	}

	function loadsubMenu(docId,target){
		loadMenu(docId,"yhwiki",load,target);
		console.info(target);
	}

	function load(data){
		var str = ""; 
		$.each(data,function(a,b){
			str += "<div class='menu-box adocDiv1' style='height: 40px;line-height: 30px; margin-left: 30px; width: 90%;'>"+
							"<div class='adocDiv2'>"+
							"<div class='adocDiv6'>";
			if(b.childrenCount>0){
				str += "<span><a href = 'javascript:void(0)' onclick='loadMenu("+b.id+",\"yhwiki\",load)'>↓"+b.childrenCount+"</a></span>";
			}
			str += "<span style='margin-left: 6px;'>"+"<a href='${path}/wap/grab/"+b.docId+"'>" ;
			if(b.title.length>20){
				str += b.title.substring(0,15)+"...";
			}else{
				str += b.title;
			}
				str +=  "</a></span></div>"+
						"</div>"+
						"</div>";
		});
		return str;
	}

</script> 
</html>