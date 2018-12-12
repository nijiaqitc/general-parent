$(function(){ 
	setTimeout(function(){
		setBootomPlace()},100);
	window.onresize=function(){
		setBootomPlace();
	}	
	var index=0,ali,node;
	for(var i=0;i<$(".wmenu").children().size();i++){
		node=$(".wmenu").children()[i];
		ali=node.firstChild;
		if(ali!=null){
			if(ali.nodeType==1){
				index=document.URL.search(ali.getAttribute("href"));
				if(index>=0){
					ali.classList.add("menuActive");
				}				
			}
		}
	}
});
function setBootomPlace(){
	if($(".aFootBefore").offset()!=undefined){
		var toTopHeight=$(".aFootBefore").offset().top;
		var windowHeight=$(window).height();
		var docHeight = $(document).height();
		if((windowHeight-toTopHeight)>100){
			$(".aFootTotal").addClass("aBootomFoot");
			$(".aFootTotal").css("position","fixed")
			$("#centerPlace").css("height",windowHeight-40);
		}else{
			$(".aFootTotal").removeClass("aBootomFoot");
			$(".aFootTotal").css("position","absolute");
			$("#centerPlace").css("height","100%");
		}
	}
}
function customfull(){
	var type=Math.ceil(Math.random()*10);
	if(type>4){
		toUp();
	}else{
		toLeft();
	}
}
var slideType=0;
function toUp(){
	$("#leftMenu").animate({
       top: '-100%'
    }, 500,function(){
    	slideType = 1;
		$("#customFull").hide();
		$("#recover").show();
    });
}
function toLeft(){
	$("#leftMenu").animate({
       left: '-150'
    }, 500,function(){
    	slideType = 2;
		$("#customFull").hide();
		$("#recover").show();
    });
}

function recover(e,type){
	if(slideType==1){
		$("#leftMenu").animate({
           top: '0'
        }, 500,function(){
        	slideType=0;
			$("#customFull").show();
			$("#recover").hide();
        });
	}
	if(slideType==2){
		$("#leftMenu").animate({
           left: '0'
        }, 500,function(){
        	slideType=0;
			$("#customFull").show();
			$("#recover").hide();
        });
	}
	
}


function shownmenu(e){
	var u=$(e).next();
	if($(u).css("display")=="block"){
		$(e).css("background-color","");
		$(u).animate({
	           height: '0'
	    }, 500,function(){
	    	$(u).hide();
	    });
	}else{
		$(e).css("background-color","#a06909");
    	$(u).show();
    	var h=$(u).children("li").size()*40;
		$(u).animate({
	           height: h
	    }, 500,function(){
	    	$(u).show();
	    });
	}
}

function loginOut(){
	$.ajax({
		url:jspath+"/loginOut",
		type:"get",
		success:function(data){
			if(data.state==1){
				window.location=jspath+"/login.jsp?type=1"
			}
		}
	})
}

/*footReset();
$(window).resize(function(){
	footReset();
});
function footReset(){
	if($(document).height()<=$(window).height()){
		if(!$(".bottomInfoDiv").hasClass("bottomInfoAuto")){
			$(".bottomInfoDiv").addClass("bottomInfoAuto");				
		}
	}else{
		if($(".bottomInfoDiv").hasClass("bottomInfoAuto")){
			$(".bottomInfoDiv").removeClass("bottomInfoAuto");				
		}
	}
}*/