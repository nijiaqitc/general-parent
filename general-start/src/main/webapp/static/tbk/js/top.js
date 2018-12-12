$(function(){
	$("#weixinImg").on('mouseenter', function() {
		$("#wechat").css("left",$("#weixinImg").position().left-65);
		$("#wechat").css("top",38);
		$("#wechat").show();
	}).on('mouseleave', function() { 
		$("#wechat").hide();
	});
	
	$(window).scroll(function(){
		var srollPosH = $(window).scrollTop(); 
		if(srollPosH>400){
			$(".commonUl").show();
		}else{
			$(".commonUl").hide();
		}
	});
}); 