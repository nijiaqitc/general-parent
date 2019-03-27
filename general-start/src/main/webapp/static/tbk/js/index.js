/* 开始：banner*/
$(function() {
    var sWidth = $("#focus").width(); //获取焦点图的宽度（显示面积）
    var len = $("#focus ul li").length; //获取焦点图个数
    var index = 0;
    var picTimer;
    
    //以下代码添加数字按钮和按钮后的半透明条，还有上一页、下一页两个按钮
    var btn = "<div class='btnBg'></div><div class='btn'>";
    for(var i=0; i < len; i++) {
        btn += "<span></span>";
    }
    btn += "</div><div class='preNext pre'></div><div class='preNext next'></div>";
    $("#focus").append(btn);
    $("#focus .btnBg").css("opacity",0.5);

    //为小按钮添加鼠标滑入事件，以显示相应的内容
    $("#focus .btn span").css("opacity",0.4).mouseenter(function() {
        index = $("#focus .btn span").index(this);
        showPics(index);
    }).eq(0).trigger("mouseenter");

    //上一页、下一页按钮透明度处理
    $("#focus .preNext").css("opacity",0.2).hover(function() {
        $(this).stop(true,false).animate({"opacity":"0.5"},300);
    },function() {
        $(this).stop(true,false).animate({"opacity":"0.2"},300);
    });

    //上一页按钮
    $("#focus .pre").click(function() {
        index -= 1;
        if(index == -1) {index = len - 1;}
        showPics(index);
    });

    //下一页按钮
    $("#focus .next").click(function() {
        index += 1;
        if(index == len) {index = 0;}
        showPics(index);
    });

    //本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
    $("#focus ul").css("width",sWidth * (len));
    
    //鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
    $("#focus").hover(function() {
        clearInterval(picTimer);
    },function() {
        picTimer = setInterval(function() {
            showPics(index);
            index++;
            if(index == len) {index = 0;}
        },4000); //此4000代表自动播放的间隔，单位：毫秒
    }).trigger("mouseleave");
    
    //显示图片函数，根据接收的index值显示相应的内容
    function showPics(index) { //普通切换
        var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
        $("#focus ul").stop(true,false).animate({"left":nowLeft},300); //通过animate()调整ul元素滚动到计算出的position
        //$("#focus .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
        $("#focus .btn span").stop(true,false).animate({"opacity":"0.4"},300).eq(index).stop(true,false).animate({"opacity":"1"},300); //为当前的按钮切换到选中的效果
    }
});
/*结束：banner*/


window.onresize=function(){
//	console.info($(window).width())
}

var flag=true;
var page=2;
$(document).ready(function(){
	getlittleDoc();
    $(window).scroll(function(){
        var srollPos = $(window).scrollTop();    //滚动条距顶部距离(页面超出窗口的高度)  
        var docheight=$(document).height();//整个页面到高度
        var rollheight=$(window).height();//滚动条到高度
        var leftHeight=  docheight-rollheight-srollPos;//到底部距离为总高度减去滚动条高度和滚动条到顶部到高度
    	if(flag){
            if(leftHeight<100){
            	getlittleDoc();
            }
    	}
    });     
});

function getlittleDoc(){
	$.ajax({
    	url:"tbk/queryLittleDoc",
    	data:{
    		page:page
    	},
    	type:"post",
    	async:false,
    	success:function(data){
    		var str="<div class='littleDiv' >";
            for(var i=0;i<data.littleDivTextList.length;i++){
            	if(i==0){
            		str+="<div class='menu-box divStyle'>";
            	}else{
            		str+="<div class='menu-box divStyleLeft'>";
            	}
            	str+="<div align='left' class='littleDivTop' >"+
					"<div class='littleTopPic'>"+
		    				"<span><a href='"+jspath+"/doc/docForType/"+data.littleDivTextList[i].id+"' target='_blank' >"+data.littleDivTextList[i].name+"</a></span>"+
		    			"</div>"+
					"</div>";
	            	str+="<div class='littleDivCenter'>";
					if(data.littleDivTextList[i].textList!=null&&data.littleDivTextList[i].textList!=""){
					    			str+="<div align='left' class='littleDivCenterTitle'>"+
				    				"<span><a href='"+jspath+"/doc/docView/"+data.littleDivTextList[i].textList[0].id+"' target='_blank' >"+data.littleDivTextList[i].textList[0].title+"</a></span>"+
				    			"</div>"+
				    			"<div class='littleDivCenterDiv'>"+
				    				"<img alt='' src='"+jspath+data.littleDivTextList[i].textList[0].url+"' >"+
				    			"</div>"+
				    			"<div class='littleDivCenterDivText' align='left'>"+
				    				"<span class='topFont'><a href='"+jspath+"/doc/docView/"+data.littleDivTextList[i].textList[0].id+"' target='_blank' >"+data.littleDivTextList[i].textList[0].formatGeneral50+"</a></span>"+
				    			"</div>"
					}
					str+="</div>";
		    		str+="<div align='left' class='littleDivBottom'>"+
		    			"<div class='littleDivBottomDiv'>";
			    		if(data.littleDivTextList[i].textList!=null&&data.littleDivTextList[i].textList!=""){
			    			for(var j=1;j<data.littleDivTextList[i].textList.length;j++){
			    				str+="<div class='littleDivBottomText' ><span class='topFont' ><a href='"+jspath+"/doc/docView/"+data.littleDivTextList[i].textList[j].id+"' target='_blank' >"+data.littleDivTextList[i].textList[j].title+"</a></span> </div>"+
			    				"<div align='right' class='littleDivBottomTime' ><span class='topFont'><a href='"+jspath+"/doc/docView/"+data.littleDivTextList[i].textList[j].id+"' target='_blank' >"+data.littleDivTextList[i].textList[j].formatCreatedDate2+"</a></span></div>";
			    			}		    			
			    		}
						str+="</div>"+
		    		"</div>"+
		    	"</div>"
            }
            str+="</div>";
        	$("#littleCenter").append(str);
        	if(page>=3){
            	flag=false;
        	}else{
        		page+=1;
        	}
    	}
    });
}
