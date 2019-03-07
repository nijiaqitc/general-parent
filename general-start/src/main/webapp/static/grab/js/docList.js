$(function(){
	setbottom();
	njqpage.makePage({
		excId:pageDiv,
		click:searchDoc,
		size:20
	});
	
})


function searchDoc(p,s){
	$.ajax({
		url:jspath+"/grab/pageList",
		data:{
			page:p
		},
		type:"post",
		async:false,
		success:function(data){
			$("#docList").html(null);
			if(data.list == null){
				njqpage.totalNum=Number(0);
				return;
			}
			if(data.list.length>0){
				var list=data.list;
				for(var i=0;i<list.length;i++){
					var outDiv = "<div>"+
						"<a class='lmd' href='knowledge/"+list[i].docId+"' target='_blank' >"+list[i].title+"</a>"+
						"<span class='rightArea'>";
						if(list[i].starTab != null){
							outDiv += "		<i class='icon-star starcl'></i>";
						}
						outDiv += "</span>"+
						"<span class='starspan' >"+formatTime(list[i].createDate)+"</span>"+
					"</div>";		
					$("#docList").append(outDiv);
				}
				njqpage.totalNum=Number(data.total);
				setbottom();
			}else{
				njqpage.totalNum=Number(0);
			}
		}
	})
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

function setbottom(){
	if($("body").height()<500){
		$(".bottomInfoDiv").addClass("stbot");
	}else{
		$(".bottomInfoDiv").removeClass("stbot");
	}
}