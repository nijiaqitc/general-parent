window.onresize=function(){
//	console.info($(window).width())
}

function search(){
	njqpage.makePage({},5);
}

function searchDoc(p,s){
	$("#textCenter").html(""); 
	var searchValue=$("#searchValue").val().replace(/(^\s*)|(\s*$)/g,'').replace(/\s+/g,",");
	if(searchValue==""){
		var str="<div align='center' class='asearchValueDiv2 asearchValueDiv2height' ><div class='atipfontstyle'>亲，搜索前请先填写搜索内容呗~</div></div>";
		$("#textCenter").append(str);
		setBootomPlace();
		return;
	}
	if(p==undefined){
		p=1;
	}
	if(s==undefined){
		s=10;
	}
	$.ajax({
		url:"/doc/searchDoc",
		data:{
			searchValue:searchValue,
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
    					str+="<div align='left' class='asearchValueDiv2'>";
					}else{
						str+="<div align='left' class='asearchValueDiv2' style='border-top: 1px solid #ECECEC;'>";
					}
					str+="<div><a href='"+jspath+"/doc/docView/"+data.doc.list[i].id+"' class='asearchValueTitle' target='_Blank'>"+data.doc.list[i].searchTitle+"</a></div>"+
	    				"<div class='asearchValueDiv3' >"+
	    					"<img src='"+data.doc.list[i].url+"' alt='' />"+
	    				"</div>"+
	    				"<div class='asearchValueDiv4'><span>标签:</span>";
			    	for(var j=0;j<data.doc.list[i].tipsList.length;j++){
			    		str+="<span><a href='javascrip:void(0)' onclick='searchForTip(\""+data.doc.list[i].tipsList[j]+"\")'>"+data.doc.list[i].tipsList[j]+"</a></span>"
			    	}
	    			str+="</div>"+
	    				"<div class='asearchValueDiv5'>"+
	    					"<span><a href='"+jspath+"/doc/docView/"+data.doc.list[i].id+"' target='_Blank'>"+data.doc.list[i].searchGeneral+
	    					"</a></span>"+
	    				"</div>"+
	    				"<div align='right' class='asearchValueDiv6'>"+
	    				data.doc.list[i].formatCreatedDate+" &nbsp&nbsp阅读量("+data.doc.list[i].readnums+")"+
	    				"</div>"+
	    			"</div>";
				}
				$("#textCenter").append(str);
				njqpage.totalNum=Number(data.doc.total);
				setBootomPlace();
			}else{
				var str="<div align='center' class='asearchValueDiv2 asearchValueDiv2height' >"+
    				"<div class='atipfontstyle'>sorry！本站不给力，没找到您要的文章...要不换个词查一查，反正又不会怀孕~</div>"+
    			"</div>";
    			$("#textCenter").append(str);
				njqpage.totalNum=Number(0);
			}
		}
	})
}

function searchForTip(tip){
	$("#searchValue").val(tip.replace("<em>","").replace("</em>",""));
	searchDoc();
}

$(document).ready(function(){
	var str="<div align='center' class='asearchValueDiv2 asearchValueDiv2height' ><div class='atipfontstyle'>亲，快来搜索一下~</div></div>";
	$("#textCenter").append(str);
	njqpage.makePage({
		excId:pageDiv,
		click:searchDoc,
		loading:false
	}); 
	$("body").keydown(function() {
	    if (event.keyCode == "13") {//keyCode=13是回车键
	        $('#searchButton').click();
	    }
	});
	
	if($("#searchValue").val()!=undefined&&$("#searchValue").val()!=""){
		searchDoc();
	}
	
}) 