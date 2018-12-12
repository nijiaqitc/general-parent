$(document).ready(function(){
	$(".nagivationBottomSelect").removeClass("nagivationBottomSelect");
	$($(".nagivationBottom")[3]).addClass("nagivationBottomSelect");
})
//     $("#leftMenu").css("height",$(window).height());
$(document).ready(function(){
	
	/* window.onresize=function(){
    	if($("#leftMenu").css("display")=="none"){
    		$("#docContext").css("margin-left",null);
    	}else{
    		if($("#docContext").css("margin-left")!="160px"){
    		    $("#docContext").css("margin-left","160px");
    		}
    	}
	}
	*/
	var flag=false;
    $(window).scroll(function(){
    	var srollPos = $(window).scrollTop();    //滚动条距顶部距离(页面超出窗口的高度)  
        var docheight=$(document).height();//整个页面到高度
        var rollheight=$(window).height();//滚动条的高度
        var leftHeight=  docheight-rollheight-srollPos;//到底部距离为总高度减去滚动条高度和滚动条到顶部到高度
//        console.info(leftHeight+" "+rollheight)
        /* if(srollPos>100){
        	$("#leftMenu").css({
        		"top":"0px",
        		"position":"fixed"
        	});
        	$("#docContext").css({
        		"width":"99%"
        		
        	})
        	if($("#leftMenu").css("marginLeft")=="-260px"){
            	$(".contextArea").css({
            		"marginLeft":"0px"
            	})
        	}else{
        		$(".contextArea").css({
                    "marginLeft":"271px"
                })
        	}
        }else{
        	$("#leftMenu").css("position","inherit");
            if($("#leftMenu").css("marginLeft")=="-260px"){
            	$("#docContext").css({
                    "width":"99%"
                })
                $(".contextArea").css({
                    "marginLeft":"0px"
                })
            }else{
                $("#docContext").css({
                    "width":"79%"
                })
            	$(".contextArea").css({
                    "marginLeft":"30px"
                })
            }
        }
        if(leftHeight<80){
        	var l=80-leftHeight;
            $("#leftMenu").css("height",(rollheight-l)+"px");
        }else{
	        $("#leftMenu").css("height",rollheight+"px");
        }  */

    }); 
    
    var text=assembleContext($("#clearText").html(),$("#clearCss").html());
	$("#textContext").html(text);
	$("#clearCss").remove();
	$("#clearText").remove();
	function assembleContext(html,css){
		if(!css){
			return html;
		}
		var cs=css.split("|");
		var tempDiv=document.createElement("div");
		tempDiv.innerHTML=html;
		var cc,pl,cnode=tempDiv,vt;
		for(var i=0;i<cs.length;i++){
			cc=cs[i].split("=");
			vt=cc[0].split("]");
			pl=vt[1].split(",");
			for(var j=0;j<pl.length;j++){
				cnode=cnode.childNodes[pl[j]];						
			}
			if(vt[0]=="[1"){
				cnode.setAttribute("class",cc[1]);
			}else{
				cnode.setAttribute("style",cc[1]);						
			}
			cnode.removeAttribute("labelIndex");
			cnode=tempDiv;
		}
		return tempDiv.innerHTML;
	}
    
}); 


function hideLeftMenu(t){
    if($("#leftMenu").css("marginLeft")=="0px"){
        $("#leftMenu").animate({marginLeft: '-260px'},"fast");
//        $(".leftMenuRightTitle").html(">目录>");
    }else{
        $("#leftMenu").animate({marginLeft: '0px'},"fast");
//        $(".leftMenuRightTitle").html("<目录<");
    }   
} 