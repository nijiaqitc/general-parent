$(function(){
	 var toTop=0;
     $(window).scroll(function(){
	  	toTop = $(this).scrollTop();
	 });
     var h=$("#textReload").css("margin-top");
     var h1=h.substring(0,h.length-2);
     var h2=h1;
     var h3=0;
     touch.on(".textContext", "touchstart", function(ev){
// 	    		ev.preventDefault();
     });
	 touch.on(".textContext", "drag", function(ev){
		 
		 if(toTop==0&&ev.y>-10){
			if(h3<40){
				h3=parseInt(h2)+parseInt(ev.y);
			    $("#textReload").css("margin-top",h3+"px");				
			}else{
			}
		}
	 });
	 touch.on(".textContext", "dragend", function(ev){
		if(h3>30){
			getReloadText(0,1,10);
		}
		h3=0;
		if($(".textContext").offset().top>50){
			$("#textReload").animate({"margin-top":"-30px"});
		}
	 });
	 
  }); 
  
var flag=true;
var page=2;
$(document).ready(function(){
$(window).scroll(function(){
        var srollPos = $(window).scrollTop();    //滚动条距顶部距离(页面超出窗口的高度)  
        var docheight=$(document).height();//整个页面到高度
        var rollheight=$(window).height();//滚动条到高度
        var leftHeight=  docheight-rollheight-srollPos;//到底部距离为总高度减去滚动条高度和滚动条到顶部到高度
	     if(flag){
	     	if(leftHeight<100){
	     		getAppendText(1,page,10);
       	 }
     	}
    }); 
})
function getReloadText(type,p,size){
	 $.ajax({
		  url:"reloadDoc",
		  data:{
			  typeName:$("#seleTypeName").val(),
			  type:type,
			  page:p,
			  size:size
		  },
		  type:"post",
		  async:false,
		  success:function(data){
			  $(".textContext").html("");
			  appendText(data);
			  page=2
		  }
	 });
}
  
function getAppendText(type,p,size){
	 $.ajax({
		  url:"reloadDoc",
		  data:{
			  type:type,
			  page:p,
			  size:size
		  },
		  type:"post",
		  async:false,
		  success:function(data){
			  if(data.docList.length>0){
				  appendText(data);
				  page+=1;
			  }
		  }
	 });
}
function appendText(data){
	 var str="";
	 for(var i=0;i<data.docList.length;i++){
		 str="<div class='menu-box adocDiv1'>"+
			"<div class='adocDiv2'>"+
				"<div class='adocDiv6'><a href="+jspath+"/wap/doc/"+data.docList[i].id+">"+data.docList[i].title+"</a></div>"+
			"</div>"+
			"<div class='adocDiv7'>"+
			"	<div class='adocDiv8'><a href="+jspath+"/wap/doc/"+data.docList[i].id+">"+data.docList[i].general+"</a></div>"+
			"</div>"+
		"</div>";
		$(".textContext").append(str);		  
	 }
}