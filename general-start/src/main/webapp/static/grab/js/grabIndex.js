function loadTipData(target){
	if(checkBtn(target)){
		return;
	}
	$("#tipLabel").show();
	$("#typeLabel").hide();
	$.ajax({
		url:jspath+"/grab/getTipList",
		type:"post",
		success:function(data){
			$("#tipLabel").children(":first").html("");
			if(data != null){
	    		data.forEach(d =>{  
	    			var name = d.name.length>6? d.name.substring(0,6)+"..":d.name; 
	    			$("#tipLabel").children(":first").append("<div class='outd'>" + 
	    				"<a href='showTitleListByTip?tipId="+d.id+"' target='_blank' title = '"+d.name+"' >" + name + "</a>" + 
	    				"<span class='rightLabel'>"+d.num+"</span></div> ");
				});
			}
		}
	})
}

function loadTypeData(target){
	if(checkBtn(target)){
		return;
	}
	$("#tipLabel").hide();
	$("#typeLabel").show();
	$.ajax({
		url:jspath+"/grab/getTypeList",
		type:"post",
		success:function(data){
			$("#typeLabel").children(":first").html("");
			if(data != null){
	    		data.forEach(d =>{ 
	    			var name = d.name.length>6? d.name.substring(0,6)+"..":d.name;  
	    			$("#typeLabel").children(":first").append("<div class='outd'>" + 
	    				"<a href='showTitleListByType?typeId="+d.id+"'  target='_blank' title ='"+d.name+"'>" + name + "</a>" +
	    				"<span class='rightLabel'>"+d.num+"</span></div>");
				});
			}
		}
	})
}

function checkBtn(target){
	var tlist = $(target).parent().find(".active");
	if(tlist.size() > 0){
		if(tlist.size()==1 && tlist[0] == target){
			return true;
		}else{
			for(var i = 0 ;i<tlist.size();i++){
	   			$(tlist[i]).removeClass("active");
			}
		}
	}
	$(target).addClass("active");
}