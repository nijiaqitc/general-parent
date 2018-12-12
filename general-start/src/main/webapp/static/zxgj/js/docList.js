$(document).ready(function(){
	$(".nagivationBottomSelect").removeClass("nagivationBottomSelect");
	$($(".nagivationBottom")[3]).addClass("nagivationBottomSelect");

	njqpage.makePage({
		excId:pageDiv,
		click:searchDoc
	}); 
	
	$(".asearchText").keydown(function() {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $("#searchButton").click();
        }
    });
	
})

function closeTip(target){
	target.parentNode.remove();
}

function searchTip(target){
    var tips="";
    var flag=false;
	$.each($(".tip"),function(a,b){
		if(target.innerHTML==b.firstChild.innerHTML){
			flag=true;
			return;
		}
	})
    if(flag){
    	return;
    }
	if($(".tip").length>4){
		$("#nowSeleTip").html(null);
	}
	var str="<div class='tip'>"+
        "<span>"+target.innerHTML+"</span>"+
        "<a onclick='closeTip(this)' href='javascript:void(0);' title='删除' style='color: #999;'>×</a>"+
    "</div>";
	$("#nowSeleTip").append(str);
	njqpage.makePage({},5);
}

function likeSearch(){
	njqpage.makePage({},5);
}

function searchDoc(p,s){
	if(p==undefined){
		p=1;
	}
	if(s==undefined){
		s=10;
	}
	var tips="";
	$.each($(".tip"),function(a,b){
		tips+=b.firstChild.innerHTML+",";
	})
	tips=tips.substring(0, tips.length-1);
	
	$("#bsearchValue").val($(".asearchText").val());
	$.ajax({
		url:jspath+"/yxl/queryDocList",
		data:{
			page:p,
			size:s,
			searchValue:$("#bsearchValue").val(),
			tipNames:tips
		},
		type:"post",
		async:false,
		success:function(data){
			$("#docList").html(null);
			if(data.list.length>0){
				var list=data.list;
				for(var i=0;i<list.length;i++){
					var outDiv="<div align='left' class='asearchValueDiv2'>"+
	                "   <div><a href='"+jspath+"/yxl/knowledge/"+list[i].docId+"' class='asearchValueTitle' target='_blank'>"+list[i].title+"</a>";
	                if(list[i].typeId!=null){
	                	outDiv+="<i class='icon-link isty'></i>";
	                }
	                outDiv+="   </div><div class='asearchValueDiv5'>"+
	                "       <span><a href='"+jspath+"/yxl/knowledge/"+list[i].docId+"' target='_blank'>"+list[i].general+"</a></span>"+
	                "   </div>"+
	                "   <div class='docTipsAreas'>"+
	                "       <div class='asearchValueDiv4'>";
	                for(var j=0;j<list[i].tipList.length;j++){
	                	outDiv+="<span><a href='"+jspath+"/docList?tipName="+list[i].tipList[j]+"' >"+list[i].tipList[j]+"</a></span>";
	                }
	                outDiv+="       </div>"+
	                "       <div align='right' class='asearchValueDiv6'>"+list[i].createDate+" &nbsp;&nbsp;";
	                outDiv+="</div></div>"+
	                "</div>";
	                if(i!=(list.length-1)){
	                	outDiv+="<div class='docUnderLine'></div>";
	                }
					$("#docList").append(outDiv);
				}
				njqpage.totalNum=Number(data.total);
			}else{
				njqpage.totalNum=Number(0);
			}
		}
	})
}