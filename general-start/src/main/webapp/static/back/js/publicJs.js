/**
 * 一个所有页面都共用的js方法
 */

$(function(){  
    // 设置jQuery Ajax全局的参数  
    $.ajaxSetup({  
        error: function(jqXHR, textStatus, errorThrown){ 
        	ajaxAfter();
        }  
    });
    
    var isChrome = navigator.userAgent.toLowerCase().match(/chrome/) != null;
	if (!isChrome) {
		window.location.href="../chromeTip";
	}
});

/**
 * ajax请求发送前显示提示信息
 */
function ajaxBefore(){
	$("#infoMessage").show();
	$("#infoGround").show();
}

/**
 * ajax收到回馈信息后隐藏提示信息
 */
function ajaxAfter(){
	$("#infoMessage").hide();
	$("#infoGround").hide();
}

/**
 * 显示确认信息 
 */
function showSureMessage(tip,context){
	$("#tipTitle").html(tip);
	$("#tipContext").html(context);
	$("#sureMessage").show();
	$("#secinfoGround").show();
//	$("#sureBtn").click(fun(true));
//	$("#sureBtn").bind("click",fun(true));
//	$("#sureBtn").unbind( "click" ) 
//	$("#sureBtn").onclick(fun(false));
//	button1.onclick=function(){
	
}

/**
 * 关闭确认框
 */
function cancelMessage(){
	$("#sureMessage").hide();
	$("#secinfoGround").hide();
	return false;
}

/**
 * 确认操作
 */
function sureMessage(){
	$("#sureMessage").hide();
	$("#secinfoGround").hide();
	return true;
}


