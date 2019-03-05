<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>菜单列表</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"  />
<style type="text/css">
	.lmd{
		margin-left: 8px;
	}
	.contextArea div{
		clear: both;
	}
	.contextArea div:hover{
		background-color: #a8d2d2;
    	color: white;
	}
	.starcl{
		color: red;
	    margin-top: 5px;
	    position: relative;
	    float: right;
	}
	.starspan{
		float: right;
	    margin-right: 4px;
	    font-size: 12px;
	}
	.rightArea{
		width: 30px;
	    display: block;
	    height: 25px;
	    float: right;
	}
	.leftlabel{
		display: block;
	    width: 10px;
	    float: left;
	    padding-top: 5px;
	}
	.stbot{
	    bottom: 0;
	    position: fixed;
	    width: 100%;
	}
</style>
</head>
<body>
	<div class="centerDiv">
		<div style="text-align: center;font-size: 20px;font-weight: 600;margin-top: 20px;">${typeInfo.name}</div>
		<div id="docContext" class="docContext" align="center">
		    <div class="contextArea" style="width: 1000px;text-align: left;padding: 10px 10px;">
		    	
		    </div>
	    </div>
	</div>
	<!--     开始：底部菜单栏-->
	<jsp:include page="../zxgj/bottom.jsp"></jsp:include>
	<script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		getMenu();
		
		function getMenu(parentId,target){
			
			$.ajax({
				url:"${path}/grab/getTitleListByType",
				data:{
					typeId:${typeId},
					parentId:parentId
				},
				type:"post",
				success:function(data){
					if(data.length>0){
						var str = "";
						for(var i = 0 ; i<data.length;i++){
							str += "<div><a href='javascript:void(0)' class='leftlabel' ";
							if(data[i].isParent){
								str += " style='display: block;' "
							}else{
								str += " style='display: none;' "
							}
							str += " onclick='loadChildMenu(\""+data[i].id+"\",this)'"+"><i class='icon-caret-right'></i></a>"+
								"<a class='lmd' href='knowledge/"+data[i].docId+"' target='_blank' >"+data[i].title+" </a>";
							str +="<span class='rightArea'>"
							if(data[i].starTab != null){
								str+="<i class='icon-star starcl'></i>";
							}
							str +="</span>";
							str +="<span class='starspan' >"+formatTime(data[i].createDate)+"</span>";
							str +="</div>";
						}
					}
					if(target==null){
						$(".contextArea").html(str);
					}else{
						var dd=document.createElement("div");
						dd.innerHTML=str;
						var labelIndex = $($(target).parent()).attr("labelIndex"); 
						var invalue = 1;
						if(labelIndex != null){
							invalue=Number(labelIndex)+1;
						}
						for(var i = 0 ;i<dd.children.length;i++){
							$(dd.children[i]).attr("labelIndex",invalue);
							$(dd.children[i]).css("paddingLeft","20px");
							if(invalue != 1){
								$(dd.children[i]).css("marginLeft",((invalue-1)*20)+"px");
							}
							$(dd.children[i]).css("borderLeft","1px solid");
							$(dd.children[i]).css("borderBottom","1px solid");
						}
						$(target).parent().after(dd.innerHTML);
					}
				}
			});
		}
	
		function loadChildMenu(pid,target){
			if(target.firstElementChild.classList.contains("icon-caret-right")){
				target.firstElementChild.classList.add("icon-caret-down");
				getMenu(pid,target);
			}
		}
		
		
		Date.prototype.format = function(fmt) { 
		    var o = { 
		       "M+" : this.getMonth()+1,                 //月份 
		       "d+" : this.getDate(),                    //日 
		       "h+" : this.getHours(),                   //小时 
		       "m+" : this.getMinutes(),                 //分 
		       "s+" : this.getSeconds(),                 //秒 
		       "q+" : Math.floor((this.getMonth()+3)/3), //季度 
		       "S"  : this.getMilliseconds()             //毫秒 
		   }; 
		   if(/(y+)/.test(fmt)) {
		           fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
		   }
		    for(var k in o) {
		       if(new RegExp("("+ k +")").test(fmt)){
		            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
		        }
		    }
		   return fmt; 
		}
		
		function formatTime(long){
			var date=new Date(long);
			return date.format("yyyy-MM-dd hh:mm");
		}
		
		$(function(){
			if($("body").height()<500){
				$(".bottomInfoDiv").addClass("stbot");
			}
		})
	</script>
</body>
</html>