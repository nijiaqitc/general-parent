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
			getReloadText(0,null,1,10);
		}
		h3=0;
		if($(".textContext").offset().top>50){
			$("#textReload").animate({"margin-top":"-30px"});
		}
	 });
	 
	 $("body").keydown(function() {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		        $('#searchBtn').click();
		        $("#newSearchValue").blur()
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
        var leftHeight=docheight-rollheight-srollPos;//到底部距离为总高度减去滚动条高度和滚动条到顶部到高度
	     if(flag){
	     	if(leftHeight<100){
	     		getAppendText(1,page,10);
       	 }
     	}
    }); 
	if($(".textContext").html().trim()==""){
		$("#noDoc").show();
	}
})

function research(){
	if($("#newSearchValue").val().trim()==""){
		alert("请输入搜索内容！")
		return
	}
	if($("#newSearchValue").val().trim()=="请输入搜索关键字以 空格 进行分隔！"){
		alert("请输入搜索内容！")
		return
	}
	var searchValue=$("#newSearchValue").val().replace(/(^\s*)|(\s*$)/g,'').replace(/\s+/g,",");
	getReloadText(0,searchValue,1,10);
	$("#newSearchValue").val("请输入搜索关键字以 空格 进行分隔！")
	$("#newSearchValue").css("color","rgb(205, 205, 205)");
	$("#newSearchValue").blur()
}

function getReloadText(type,sv,p,size){
	if(sv==null){
		sv=$("#searchValue").html();
	}
	$.ajax({
		  url:"reLoadsearchDoc",
		  data:{
			 searchValue:sv,
			 type:type,
			 page:p,
			 size:size
		 },
		 type:"post",
		 async:false,
		 success:function(data){
			 $(".textContext").html("");
			 appendText(data);
			 page=2;
			 $("#searchValue").html(sv)
		 }
	});
}
  
function getAppendText(type,p,size){
	 $.ajax({
		  url:"reLoadsearchDoc",
		  data:{
			  type:type,
			  searchValue:$("#searchValue").html(),
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
	 if(data.docList.length==0){
		 $("#noDoc").show();	  
	 }else{
		 $("#noDoc").hide();
	 }
	 for(var i=0;i<data.docList.length;i++){
		 if(i==0){
			 str="<div class='menu-box adocDiv11'>"
		 }else{
			 str="<div class='menu-box adocDiv1'>"
		 }
		 str+="<div class='adocDiv2'>"+
			"<div class='adocDiv3'>"+
				"<img class='adocDiv4' src='"+jspath+data.docList[i].url+"' alt='文章图片' />"+ 
			"</div>"+
			"<div class='adocDiv5'><span>"+data.docList[i].userName+"</span>";
		 str+="</div><div class='adocDiv6'><a href="+jspath+"/wap/docView/"+data.docList[i].id+">"+data.docList[i].searchTitle+"</a></div>"+
			"</div>"+
			"<div class='adocDiv7'>"+
				"<div class='adocDiv8'><a href="+jspath+"/wap/docView/"+data.docList[i].id+">"+data.docList[i].searchGeneral.substring(0,100)+"...</a></div>"+
				"<div align='right' class='adocDiv9'>浏览("+data.docList[i].readnums+")</div>"+
			"</div></div>";
		$(".textContext").append(str);		  
	 }
}

function clearText(){
	$("#newSearchValue").val("");
	$("#newSearchValue").css("color","");
}

function losfoc(){
	if($("#newSearchValue").val().trim()==""||$("#newSearchValue").val().trim()=="请输入搜索关键字以 空格 进行分隔！"){
		$("#newSearchValue").val("请输入搜索关键字以 空格 进行分隔！")
		$("#newSearchValue").css("color","rgb(205, 205, 205)");
		return
	}
}