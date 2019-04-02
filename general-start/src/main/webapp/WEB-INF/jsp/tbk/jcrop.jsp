<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${resPath }/chajian/Jcrop-0.9.12/css/jquery.Jcrop.css">
<script src="${resPath }/chajian/Jcrop-0.9.12/js/jquery.min.js"></script>
<script src="${resPath }/chajian/Jcrop-0.9.12/js/jquery.Jcrop.js"></script>
<style type="text/css">
	.choosePic{
		position: relative;
		background: #be0000;
		width: 97px;
		height: 28px;
		line-height: 28px;
		color: #fff;
		font-size: 14px;
		display: block;
		text-align: center;
		text-decoration: none;
		margin-bottom: 20px;
		cursor: pointer;
		float: left;
	}
	.chooseBtn{
		position: absolute;
		top: 0;
		left: 0;
		width: 97px;
		height: 28px;
		line-height: 0;
		opacity: 0;
		cursor: pointer;
	}
</style>
</head>
<body>
<div style="margin-left: 235px;">
	<div style="width: 616px;">
		<div class="choosePic">
			选择照片<input type="file" class="chooseBtn" name="contractFileName"  onchange="fileChange(this);"/> 
		</div>
		<div id="myUpImg" onclick="uppic()" class="choosePic" style="float: right;margin-left: 10px;">确定</div>
<!-- 		<div id="myUpImg" onclick="uppic()" class="choosePic" style="float: right;background:#c5c5c5;">取消</div> -->
		
	</div>
	<div style="clear: both;">
		<div style="float: left;width: 420px;"><img id="element_id" src="${resPath }/back/img/waitForUp.jpg"></div>
		<div id="nnn" style="display: none;">
			<img id="viewImg"  alt="" src="">
		</div> 
		<div id="mmm" >
			<img id="element_id" src="${resPath }/back/img/waitForUp.jpg" style="width: 200px;height: 150px;">
		</div>
		<div id="ppp" style="margin-top: 35px;">
			<img id="element_id" src="${resPath }/back/img/waitForUp.jpg" style="width: 100px;height: 75px;">
		</div>
		<div id="ooo" style="margin-top: 35px;">
			<img id="element_id" src="${resPath }/back/img/waitForUp.jpg" style="width: 100px;height: 100px;border-radius: 50%;">
		</div>
	</div>
</div>

<input type="hidden" id="x1">
<input type="hidden" id="y1">
<input type="hidden" id="x2">
<input type="hidden" id="y2">
<input type="hidden" id="w">
<input type="hidden" id="h">



<input type="hidden" id="hidePlace" >
<input type="hidden" id="hideId" >
<script type="text/javascript">
	var flag=false;
	var jcrop_api;
	var picLeft = 70;
    var picTop = 70;
    var picWidth = 200;
    var picHeight = 150;
	var attr="border-radius: 50%;"
	
	$(function(){
		$('#element_id').Jcrop({
		  bgFade:     true,
		  bgOpacity: .2,
		  boxWidth:400,
		  boxHeight:400,
		  isBorderDefault:1,
		  onChange:   showCoords,
		  onSelect:   showCoords
		},function(){
		  jcrop_api = this;
		  jcrop_api.setOptions({ aspectRatio: 4/3 });
		  jcrop_api.setOptions({ minSize:[200,150] });
		  jcrop_api.disable();
		});
	})

	function changeImg(){
		var fcanvas,
	    fcanvas = document.createElement('canvas');
		fcanvas.width = 400;
		fcanvas.height = 400;
		var imgaaa=new Image();
	    imgaaa.src=$("#element_id").attr("src");
	    imgaaa.onload = function(){
	    	fcanvas.getContext('2d').drawImage(imgaaa, 0, 0, this.width, this.height, 0, 0, 400, 400);
	 		$("#viewImg").attr("src",fcanvas.toDataURL("image/png"));
	    }
	}
	
	
	function showCoords(c){
		$("#x1").val(c.x);
		$("#y1").val(c.y);
		$("#x2").val(c.x2);
		$("#y2").val(c.y2);
		$("#w").val(c.w);
		$("#h").val(c.h);
		cropaaa();
	};
	
	function cropaaa(){
	    picLeft = $("#x1").val(),
	    picTop = $("#y1").val(),
	    picWidth = $("#w").val(),
	    picHeight = $("#h").val();
	    var crop_canvas;
	    var crop_canvas2;
	    var crop_canvas3;
	    crop_canvas = document.createElement('canvas');
	    crop_canvas2 = document.createElement('canvas');
	    crop_canvas3 = document.createElement('canvas');
	    var newpicWidth=200;
	    var newpicHeight=150;
	    crop_canvas.width = newpicWidth;
	    crop_canvas.height = newpicHeight; 
	    crop_canvas2.width = 100;
	    crop_canvas2.height = 100;
	    crop_canvas3.width = 100;
	    crop_canvas3.height = 75;
	    var imgaaa=new Image();
	    imgaaa.src=$("#viewImg").attr("src");
	    imgaaa.onload = function(){
		    crop_canvas.getContext('2d').drawImage(imgaaa, picLeft, picTop, picWidth, picHeight, 0, 0, newpicWidth, newpicHeight);
	  		$("#mmm").html(crop_canvas);
	  		crop_canvas2.getContext('2d').drawImage(imgaaa, picLeft, picTop, picWidth, picHeight, 0, 0, 100, 100);
	  		$(crop_canvas2).attr("style",attr)
	  		$("#ooo").html(crop_canvas2);
	  		crop_canvas3.getContext('2d').drawImage(imgaaa, picLeft, picTop, picWidth, picHeight, 0, 0, 100, 75);
	  		$("#ppp").html(crop_canvas3);
	    }
	}
	
	function jc(){
		/*
		 * 变换原理：先将原图转换成canvas（变形图），
		 * 然后再转成图片（变形图）
		 * 最后在变形图上进行剪裁操作
		 */
		var canvas;
		canvas = document.createElement('canvas');
		canvas.height = 200;
		canvas.width = 200;
		canvas.getContext('2d').drawImage($("#viewImg")[0],0,0,500,300, 0, 0,200,200);
		$("#ccc").html(canvas)
	}
	
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera; 
	var sss="";
	function fileChange(target,id) { 
		var fileSize = 0; 
		var filetypes =[".jpg",".png",".rar",".txt",".zip",".doc",".ppt",".xls",".pdf",".docx",".xlsx"]; 
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
		$("#element_id").attr("src",window.URL.createObjectURL(target.files[0]));
		jcrop_api.enable();
		jcrop_api.setCustomImage(window.URL.createObjectURL(target.files[0]));
		jcrop_api.setOptions({ setSelect: [ 20, 20, 200, 150 ] });
		changeImg();
	} 
	
	
	
	
	function uppic(){
		if($("#mmm canvas").length==0){
			alert("请选择上传的图片！");
			return ;
		}
  		$.ajax({
  			url:"${path}/admin/issueDoc/upPic",
  			type:"post",
  			data:{
  				base64Data:$("#mmm canvas")[0].toDataURL("image/png")
  			},
  			success:function(data){
  				if(data.state==1){
  					$("#hidePlace").val("${imgPath}"+data.place);
  					$("#hideId").val(data.id);
  					$(".close").trigger("click");
  					parent.closeDialog();
  				}
  			}
  		})
  	}
  	
</script>
</body>
</html>