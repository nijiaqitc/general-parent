$(document).ready(function(){
	$(".nagivationBottomSelect").removeClass("nagivationBottomSelect");
	$($(".nagivationBottom")[1]).addClass("nagivationBottomSelect");
})

function showApi(t){
	if($("#apiArea").css("left")=="0px"){
		$(t).animate({left: '-10px'},"fast");
    	$("#apiArea").animate({left: '-2000px'},"fast");
		$("#apiArea").parent().animate({height: '0px'},"fast");
		$("#njqEditor").parent().animate({marginTop:"50px"},"fast");
	}else{
		$(t).animate({left: '-55px'},"fast");
    	$("#apiArea").parent().animate({height: '150px'},"fast");
    	$("#apiArea").animate({left: '0px'},"fast");
		$("#njqEditor").parent().animate({marginTop:"20px"},"fast");
		
	}
}