window.onresize=function(){
//	console.info($(window).width())
}

var flag=true;
var page=1;
$(document).ready(function(){ 
	getDocList(page)
	setBootomPlace();
    $(window).scroll(function(){
    	if(flag){
            var srollPos = $(window).scrollTop();    //滚动条距顶部距离(页面超出窗口的高度)  
            var docheight=$(document).height();//整个页面到高度
            var rollheight=$(window).height();//滚动条到高度
            var leftHeight=  docheight-rollheight-srollPos;//到底部距离为总高度减去滚动条高度和滚动条到顶部到高度
            if(leftHeight<100){
            	getDocList(page)
            }
    	}
    });  
});


function getDocList(p){
	$.ajax({
    	url:jspath+"/doc/queryDocByNumDesc",
    	data:{
    		page:p
    	},
    	type:"post",
    	async:false,
    	success:function(data){
    		var str="";
    		if(data.state==1){
        		for(var i=0;i<data.viewList.length;i++){
            		str+="<div align='center' class='menu-box aoutDiv7'>"+
	    	    		"<div>"+"<a href='"+jspath+"/doc/docView/"+data.viewList[i].id+"'  target='_blank'>"+
	    		    		"<img class='aoutDiv2 picWallSize' src='"+data.viewList[i].url+"' alt='文章图片' >"+
	    		    		"<img class='aoutDiv3' src='"+jspath+"/tbk/images/shadowimg300.gif' alt='底部阴影' >"+"</a>"+
	    	    		"</div>"+
	    	    		"<div align='left' class='aoutDiv4' ><a href='"+jspath+"/doc/docView/"+data.viewList[i].id+"' class='atitleSize'  target='_blank'>"+data.viewList[i].formatTitle+"</a></div>"+
	    	    		"<div class='aoutDiv5' align='left'>"+
	    	    			"<span class='aoutDiv6' >"+data.viewList[i].formatGeneral50+"</span>"+
	    	    		"</div>"+
	        		"</div>";
        		}
        		$("#textCenter").append(str);
        		page+=1;
    		}else{
    			flag=false;
    		}
    	}
    });
}