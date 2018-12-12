function queryRc(page){
	$.ajax({
		url:"queryResourceShare",
		data:{
			type1:$($("#selectType .checked")[0]).attr("typeId"),
			type2:$($("#selectType .checked")[1]).attr("typeId"),
			type3:$($("#selectType .checked")[2]).attr("typeId"),
			isDetail:"1",
			queryText:"",
			page:page
		},
		type:"post",
		async: false,
		success:function(data){
			var str="";
			var wiclass="";
			if($(window).width()>1265&&$(window).width()<1680){
     			wiclass=" mulone";
     		}
			if($(window).width()>1680&&$(window).width()<2300){
				wiclass=" multwo";
			}
			for(var i=0;i<data.list.length;i++){
				str+="<li class='menu-box"+wiclass+"'>"+
             	"<div class='lidiv1'>"+
             		"<img class='lidiv1img1' picOne='"+data.list[i].picUrlA+"' picTwo='"+data.list[i].picUrlB+"' picThree='"+data.list[i].picUrlC+"' onclick='viewpic(this)' src='"+data.list[i].picUrlBase+"-z-0.jpg'>"+
             		"<img class='lidiv1img2' src='../tbk/images/shadowimg300.gif' >"+
             	"</div>"+
             	"<div class='lidiv2'>"+
             	"	<span>"+data.list[i].resourceDesc+"</span>"+
             	"</div>"+
             	"<div class='lidiv3'>"+
             	"	<span><a href='javascript:void(0)' onclick='downLoadResource(\""+data.list[i].id+"\")' >下载</a></span>"+
                "    <span>密码："+data.list[i].pwd+"</span>"+
             	"</div>"+
             	"</li>";    					
			}
			$("#contextUl").append(str);
			if(data.list.length>0){
				
			}else{
				//一旦出现加载没有数据的情况下，那么拖到底部再也不进行动态加载
				isContinueLoad=false;
			}
//			"<img class='lidiv1img1' style='display:none;' src='"+data.list[i].picUrlB+"'>"+
//     		"<img class='lidiv1img1' style='display:none;' src='"+data.list[i].picUrlC+"'>"+
		}
	})
}


var flag=true;
var page=0;
var isContinueLoad=true;
$(document).ready(function(){
	if($("#isRead").val()!=1){
		$("#bqTip").show();
		$("#custom-background").show();
	}
	loadResources();
    $(window).scroll(function(){
        var srollPos = $(window).scrollTop();    //滚动条距顶部距离(页面超出窗口的高度)  
        var docheight=$(document).height();//整个页面的高度
        var rollheight=$(window).height();//滚动条的高度
        var leftHeight=  docheight-rollheight-srollPos;//到底部距离为总高度减去滚动条高度和滚动条到顶部到高度
    	if(flag){
            if(leftHeight<100){
            	loadResources();
            }
    	}
    	
    });  
    window.onresize=function(){
    	if($(window).width()>1265&&$(window).width()<1680){
    		if(!$($("#contextShare ul li")[0]).hasClass("mulone")){
    			for(var i=0;i<$("#contextShare ul li").length;i++){
					$($("#contextShare ul li")[i]).addClass("mulone");
					$($("#contextShare ul li")[i]).removeClass("multwo");
    			}
    		}
    	}
    	if($(window).width()<1265){
    		if($($("#contextShare ul li")[0]).hasClass("mulone")){
    			for(var i=0;i<$("#contextShare ul li").length;i++){
    				$($("#contextShare ul li")[i]).removeClass("mulone");
    				$($("#contextShare ul li")[i]).removeClass("multwo");
    			}
    		}
    	}
    	if($(window).width()>1680&&$(window).width()<2300){
    		if($($("#contextShare ul li")[0]).hasClass("mulone")){
    			for(var i=0;i<$("#contextShare ul li").length;i++){
    				$($("#contextShare ul li")[i]).removeClass("mulone");
    				$($("#contextShare ul li")[i]).addClass("multwo");
    			}
    		}
    	}
    	
    }
    if($(window).width()>1265&&$(window).width()<1680){
    	for(var i=0;i<$("#contextShare ul li").length;i++){
    		$($("#contextShare ul li")[i]).addClass("mulone");
    	}
    }
    if($(window).width()>1680&&$(window).width()<2300){
    	for(var i=0;i<$("#contextShare ul li").length;i++){
			$($("#contextShare ul li")[i]).addClass("multwo");
		}
    }
});

function loadResources(){
	if(isContinueLoad){
		page++;
		queryRc(page);		
	}
}
function selectTypeForOne(id,e,type){
	//如果已经选中了，那么点击不再进行查询
	if(e.classList.contains("checked")){
		return;
	}
	isContinueLoad=true;
	page=1;
	$("#contextUl").children().remove();
	$(e).parents("ul").find(".checked").removeClass("checked");
	$(e).addClass("checked");
	var tip=type+1;
	if(id==-1){
		if(type==1){
			$("#secondMenu").hide();
			$("#secondMenu ul li").remove();
			$("#thirdMenu").hide();
			$("#thirdMenu ul li").remove();
		}else if(type==2){
			$("#thirdMenu").hide();
			$("#thirdMenu ul li").remove();
		}
		queryRc();
		return;
	}
	var parentName="";
	if(type==1){
		parentName="secondMenu";
		$("#secondMenu").hide();
		$("#secondMenu ul li").remove();
		$("#thirdMenu").hide();
		$("#thirdMenu ul li").remove();
	}else if(type==2){
		parentName="thirdMenu";
		$("#thirdMenu").hide();
		$("#thirdMenu ul li").remove();
	}else if(type==3){
		
	}
	
	$.ajax({
		url:"queryChildrenCode",
		data:{
			parentId:id
		},
		async: false,
		type:"post",
		success:function(data){
			if(parentName!=""){
				$("#"+parentName).show();
				$("#"+parentName+" ul").append("<li><span class='chooseBtn checked' typeId='-1' onclick='selectTypeForOne(-1,this,"+tip+")'>全部</span></li>");
   				if(data.codeList.length>0){
					for(var i=0;i<data.codeList.length;i++){
						$("#"+parentName+" ul").append("<li style='margin-left: 10px;'><span class='chooseBtn' typeId=\""+data.codeList[i].id+"\" onclick='selectTypeForOne(\""+data.codeList[i].id+"\",this,"+tip+")'>"+data.codeList[i].name+"</span></li>");
					}
				}
			}
    		$("#contextUl").children().remove();
			queryRc(1);
		}
	})
	
}

function downLoadResource(downLoad){
	$.ajax({
		url:"resourceDownLoad",
		data:{
			downLoad:downLoad
		},
		type:"post",
		async: false,
		success:function(data){
			if(data.state==1){
				window.open(data.jumpUrl);
			}else{
				alert(data.message);						
			}
		}
	})
}


function choosePicBtn(index){
	$("#choosePic").click();
	$("#picPlace").val(index);
	$("#picUp h3").html("图片上传第"+index+"张");
}

function showSureBtn(){
	if($("#agreeBtn").hasClass("custom-customModel-btnCancel")){
		$("#agreeBtn").removeClass("custom-customModel-btnCancel");
		$("#agreeBtn").addClass("custom-customModel-btnSave");
	}else{
		$("#agreeBtn").addClass("custom-customModel-btnCancel");
		$("#agreeBtn").removeClass("custom-customModel-btnSave");
	}
}

function agreeBtn(){
	if($("#agreeBtn").hasClass("custom-customModel-btnSave")){
		$("#bqTip").hide();
		$("#custom-background").hide();
	}
	
	$.ajax({
		url:"setRead",
		type:"post",
		success:function(data){
			
		}
	})
}


function noBtn(){
	window.location.href="/";
}

function showNext(){
	var l1=$(".dots li").length-1;
	var l2=$(".dots").find(".active").attr("picindex");
	$($(".dots").find(".dot")[l2]).removeClass("active");
	if(l2<l1){
		$($(".dots").find(".dot")[parseInt(l2)+1]).addClass("active");
    	var picWidth=(parseInt(l2)+1)*860;
		$("#picUl").stop(true,true).animate({
			"margin-left": "-"+picWidth+"px"
	    },
	    400);
	}else{
		$($(".dots").find(".dot")[0]).addClass("active");
		$("#picUl").stop(true,true).animate({
			"margin-left": "0px"
	    },
	    400);
	}
}
function showBefore(){
	var l1=$(".dots li").length-1;
	var l2=$(".dots").find(".active").attr("picindex");
	$($(".dots").find(".dot")[l2]).removeClass("active");
	if(l2>0){
		$($(".dots").find(".dot")[parseInt(l2)-1]).addClass("active");
    	var picWidth=(parseInt(l2)-1)*860;
		$("#picUl").stop(true,true).animate({
			"margin-left": "-"+picWidth+"px"
	    },
	    400);
	}else{
		var l1=$(".dots li").length-1;
		$($(".dots").find(".dot")[l1]).addClass("active");
		var picWidth=(parseInt(l1))*860;
		$("#picUl").stop(true,true).animate({
			"margin-left": "-"+picWidth+"px"
	    },
	    400);
	}
}

$(".dots li").hover(function(){
	$(".dots").find(".active").removeClass("active")
	this.classList.add("active");
	var picWidth=$(this).attr("picIndex")*860;
	$("#picUl").stop(true,true).animate({
		"margin-left": "-"+picWidth+"px"
    },
    400);
})


function closeDialog(e){
	$(e).parents(".custom-customModel").hide();
	if($("#myModal").css("display")!="block"){
		$("#custom-background").hide();
	}
}



$(document).ready(function(){ 
	$("#picShowTitle").mousedown(function(e){ 
		$(this).css("cursor","move");//改变鼠标指针的形状 
		var offset={};//DIV在页面的位置 
		offset.left=$("#picView").css("left");
		offset.top=$("#picView").css("top");
		var x = e.clientX - parseInt(offset.left);//获得鼠标指针离DIV元素左边界的距离 
		var y = e.clientY - parseInt(offset.top);//获得鼠标指针离DIV元素上边界的距离 
		$(document).bind("mousemove",function(ev){ 
			$("#picView").stop();//加上这个之后 
			var _x = ev.clientX - x;//获得X轴方向移动的值 
			var _y = ev.clientY - y;//获得Y轴方向移动的值 
			$("#picView").css("left",_x+"px");
			$("#picView").css("top",_y+"px");
//			$("#picView").animate({left:_x+"px",top:_y+"px"},1); 
		}); 
	}); 

	$(document).mouseup(function(){ 
		$(this).unbind("mousemove"); 
	}) 
}) 






var jcrop_api;
$(function(){
	$('#element_id').Jcrop({
	  bgFade:     true,
	  bgOpacity: .2,
	  boxWidth:1000,
	  boxHeight:600,
	  setSelect: [ 70, 30, 540, 330 ],
	  onChange:   showCoords,
	  onSelect:   showCoords
	},function(){
	  jcrop_api = this;
	  jcrop_api.setOptions({ aspectRatio: 5/3 });
	  jcrop_api.setOptions({ minSize:[860,570],maxSize:[860,570] });
	  jcrop_api.disable();
	});
})


function showCoords(c)
{
	$("#x1").val(c.x);
	$("#y1").val(c.y);
	$("#x2").val(c.x2);
	$("#y2").val(c.y2);
	$("#w").val(c.w);
	$("#h").val(c.h);
	cropaaa();
};

function cropaaa() {
    var crop_canvas,
    left = $("#x1").val(),
    top = $("#y1").val(),
    width = $("#w").val(),
    height = $("#h").val();
    crop_canvas = document.createElement('canvas');
    var newpicWidth=860;
    var newpicHeight=516;
    crop_canvas.width = newpicWidth;
    crop_canvas.height = newpicHeight;  
    var imgaaa=new Image();
    imgaaa.src=$("#element_id").attr("src");
//     	    imgaaa.onload = function(){
//     	    	this.width=1000;
//     		    this.height=600;
//     	    }
//     	    crop_canvas.getContext('2d').drawImage(imgaaa, 0, 0, 860, 516);
    /* crop_canvas.getImageData(0,0,400,400); */
    crop_canvas.getContext('2d').drawImage(imgaaa, left, top, width, height, 0, 0, 860, 516);
    $("#hidePic").html(crop_canvas);
// 			$("#selepicOne").attr("src",crop_canvas.toDataURL("image/png"));    	    
//     	    window.open(crop_canvas.toDataURL("image/png"));
}


var isIE = /msie/i.test(navigator.userAgent) && !window.opera; 
function fileChange(target,id) { 
	var fileSize = 0; 
	var filetypes =[".jpg",".png"]; 
	var filepath = target.value; 
	var filemaxsize = 1024*2;//2M 
	if(filepath){ 
		var isnext = false; 
		var fileend = filepath.substring(filepath.indexOf(".")); 
		if(filetypes && filetypes.length>0){ 
		for(var i =0; i<filetypes.length;i++){ 
		if(filetypes[i]==fileend){ 
		isnext = true; 
		break; 
	} 
	} 
	} 
	if(!isnext){ 
		alert("不接受此文件类型！"); 
		target.value =""; 
		return false; 
	} 
	}else{ 
		return false; 
	} 
	if (isIE && !target.files) { 
		var filePath = target.value; 
		var fileSystem = new ActiveXObject("Scripting.FileSystemObject"); 
		if(!fileSystem.FileExists(filePath)){ 
		alert("附件不存在，请重新输入！"); 
		return false; 
	} 
	var file = fileSystem.GetFile (filePath); 
	fileSize = file.Size; 
	} else { 
		fileSize = target.files[0].size; 
	} 
	
	var size = fileSize / 1024; 
	if(size>filemaxsize){ 
		alert("附件大小不能大于"+filemaxsize/1024+"M！"); 
		target.value =""; 
		return false; 
	} 
	if(size<=0){ 
		alert("附件大小不能为0M！"); 
		target.value =""; 
		return false; 
	}
	
	var fcanvas = document.createElement('canvas');
    var newpicWidth=1000;
    var newpicHeight=600;
    fcanvas.width = newpicWidth;
    fcanvas.height = newpicHeight;  
    
    var img=new Image();
    img.src=window.URL.createObjectURL(target.files[0]);
    img.onload = function(){
    	fcanvas.getContext('2d').drawImage(img, 0, 0, this.width, this.height, 0, 0, 1000, 600);
 		$("#element_id").attr("src",fcanvas.toDataURL("image/png"));
		jcrop_api.enable();
		jcrop_api.setCustomImage( $("#element_id").attr("src"),1000,600);
		jcrop_api.setOptions({ setSelect: [ 70, 30, 540, 330 ] });
		$("#picUp").show();
		$("#choosePic").val(null);
    }
} 

function surePic(){
	if($("#picPlace").val()==1){
		$("#selepicOne").attr("src",$("#hidePic canvas")[0].toDataURL("image/png"));    	    
	}else if($("#picPlace").val()==2){
		$("#selepicTwo").attr("src",$("#hidePic canvas")[0].toDataURL("image/png"));
	}else if($("#picPlace").val()==3){
		$("#selepicThree").attr("src",$("#hidePic canvas")[0].toDataURL("image/png"));
	}
	$("#picUp").hide();
}


function topicUp(){
	$("#myModal").show();
	$("#custom-background").show();
	$("#saveBtn").show();
}

function saveShareResource(e){
	if($("#selepicOne").attr("src").indexOf("tbk/images/image.png")>-1){
		alert("请选择第一张图片");
		return
	}
	if($("#selepicTwo").attr("src").indexOf("tbk/images/image.png")>-1){
		alert("请选择第二张图片");
		return
	}
	if($("#selepicThree").attr("src").indexOf("tbk/images/image.png")>-1){
		alert("请选择第三张图片");
		return
	}
	if($("#shareUrl").val()==null||$("#shareUrl").val()==""){
		alert("请输入分享地址");
		return
	}
	if($("#resourceDesc").val()==null||$("#resourceDesc").val()==""){
		alert("请输入标题简介");
		return
	}
	$("#saveBtn").hide();
	$.ajax({
		url:"saveShareResource",
		data:{
			shareTypeOne:$("#shareTypeOne").val(),
			shareTypeTwo:$("#shareTypeTwo").val(),
			shareTypeThree:$("#shareTypeThree").val(),
			selepicOne:$("#selepicOne").attr("src"),
			selepicTwo:$("#selepicTwo").attr("src"),
			selepicThree:$("#selepicThree").attr("src"),
			shareUrl:$("#shareUrl").val(),
			sharePwd:$("#sharePwd").val(),
			resourceDesc:$("#resourceDesc").val()
		},
		type:"post",
		success:function(data){
			if(data.state==1){
				alert("提交成功，感谢您的分享，谢谢！");
				$("#shareForm")[0].reset();
				$("#selepicOne").attr("src","../tbk/images/image.png");
				$("#selepicTwo").attr("src","../tbk/images/image.png");
				$("#selepicThree").attr("src","../tbk/images/image.png");
				$("#myModal").hide();
	    		$("#custom-background").hide();
			}else{
				alert(data.message);
			}
		}
	})
}


function viewpic(e){
	$("#picUl img")[0].src=$(e).attr("picOne");
	$("#picUl img")[1].src=$(e).attr("picTwo");
	$("#picUl img")[2].src=$(e).attr("picThree");
	
	
	var l2=$(".dots").find(".active").attr("picindex");
	$($(".dots").find(".dot")[l2]).removeClass("active");
	$($(".dots").find(".dot")[0]).addClass("active");
	$("#picUl").css("margin-left","0px");
	$("#picView").show();
	$("#custom-background").show();
}

function selectType(dex){
	var parentId;
	if(dex==1){
		parentId=$("#shareTypeOne").val();
	}else if(dex==2){
		parentId=$("#shareTypeTwo").val();
	}else if(dex==3){
		parentId=$("#shareTypeThree").val();
	}
	$.ajax({
		url:"queryChildrenCode",
		data:{
			parentId:parentId
		},
		type:"post",
		success:function(data){
			var inner="<option value='-1'>请选类别</option>"
			for(var i=0;i<data.codeList.length;i++){
				inner+="<option value='"+data.codeList[i].id+"'>"+data.codeList[i].name+"</option>";
			}
			if(dex==1){
    			$("#shareTypeTwo").html(inner);
    		}else if(dex==2){
    			$("#shareTypeThree").html(inner);
    		}
		}
	})
}