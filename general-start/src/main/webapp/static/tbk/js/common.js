$(function(){  
    // 设置jQuery Ajax全局的参数  
    $.ajaxSetup({  
        error: function(jqXHR, textStatus, errorThrown){ 
            alert("不好意思，服务器出了一点小问题，换个其他功能试试吧。")  
        }  
    });
    setBootomPlace();
    window.onresize=function(){
    	setBootomPlace();
    }
}); 

function setBootomPlace(){
//	console.info($(".aFootBefore").offset().top);
	if($(".aFootBefore").offset()!=undefined){
		var toTopHeight=$(".aFootBefore").offset().top;
		var windowHeight=$(window).height();
		var docHeight = $(document).height();
		if((windowHeight-toTopHeight)>150){
			$(".aFootTotal").addClass("aBootomFoot");
		}else{
			$(".aFootTotal").removeClass("aBootomFoot");
		}
	}
}