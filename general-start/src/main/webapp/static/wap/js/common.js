$(function(){  
    // 设置jQuery Ajax全局的参数  
    $.ajaxSetup({  
        error: function(jqXHR, textStatus, errorThrown){ 
            alert("不好意思，服务器出了一点小问题，换个其他功能试试吧。")  
        }  
    });
    $(window).scroll(function(){
    	var srollPosH=$(window).scrollTop();
    	if(srollPosH>400){
    		$("#J-catalog-button").show();
    	}else{
    		$("#J-catalog-button").hide();
    	}
    });
}); 

function goToTop(){
	numGroup($(window).scrollTop());
}
function numGroup(num){
	if(num>50){
		$(window).scrollTop(num-50);
	}else{
		$(window).scrollTop(0);
	}
	if((num-50)>0){
		setTimeout("numGroup("+(num-50)+")", 0.5);
	}
}

$(".hide-menu").click(function(){
	if($(".hide-menu").html()=="&lt;&lt;&lt;&lt;"){
		$(".hide-menu").html("&gt;&gt;&gt;&gt;");
		$(".bottom-menu").animate({left:'-400px'}).animate({left:'-300px'}).animate({left:'-400px'}).animate({left:'-376px'},"fast",function(){
			$(".hide-menu").animate({bottom:'0px'})	;	
		});
	}else{
		$(".hide-menu").html("&lt;&lt;&lt;&lt;");
		$(".hide-menu").animate({bottom:'54px'},"fast",function(){
			$(".bottom-menu").animate({left:'20px'}).animate({left:'-30px'}).animate({left:'0px'});			
		})	;
	}
}); 
