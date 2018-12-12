window.onresize=function(){
//	console.info($(window).width())
}
$(document).ready(function(){
	njqpage.makePage({
		excId:pageDiv,
		click:searchDoc
	}); 
})

function searchDoc(p,s){
	/* $("#textCenter").html("");  */
	if(p==undefined){
		p=1;
	}
	if(s==undefined){
		s=10;
	}
	$.ajax({
		url:"../docListForType",
		data:{
			typeId:$("#typeId").val(),
			page:p,
			size:s
		},
		type:"post",
		async:false,
		success:function(data){
			if(data.state==1){
				var str="";
				for(var i=0;i<data.doc.list.length;i++){
					if(i==0){
    					str+="<div align='left' class='arightDiv2'>";
					}else{
						str+="<div align='left' class='arightDiv3' >";
					}
					str+="<div><a href='"+jspath+"/doc/docView/"+data.doc.list[i].id+"' target='_blank'>"+data.doc.list[i].title+"</a></div>"+
	    				"<div class='arightDiv2div1' >"+
	    					"<img src='"+data.doc.list[i].url+"' alt='' />"+
	    				"</div>"+
	    				"<div class='arightDiv2div2'><span>标签:</span>";
			    	for(var j=0;j<data.doc.list[i].tipsList.length;j++){
			    		str+="<span><a href='"+jspath+"/fastSearchInit?searchValue="+data.doc.list[i].tipsList[j]+"' target='_blank' >"+data.doc.list[i].tipsList[j]+"</a></span>"
			    	}
	    			str+="</div>"+
	    				"<div class='arightDiv2div3'>"+
	    					"<span><a href='"+jspath+"/doc/docView/"+data.doc.list[i].id+"' target='_blank'>"+data.doc.list[i].searchGeneral+
	    					"</a></span>"+
	    				"</div>"+
	    				"<div align='right' class='arightDiv2div4'>"+
	    				data.doc.list[i].formatCreatedDate+" &nbsp&nbsp阅读量("+data.doc.list[i].readnums+")"+
	    				"</div>"+
	    			"</div>";
				}
				$("#textCenter").append(str);
				njqpage.totalNum=Number(data.doc.total);
				setBootomPlace();
			}else{
				var str="<div align='center' class='arightDiv2' style='height: 40px;'>"+
    				"<div>未搜索到文章</div>"+
	    			"</div>";
	    			$("#textCenter").append(str);
    				njqpage.totalNum=Number(0);
			}
		}
	})
}