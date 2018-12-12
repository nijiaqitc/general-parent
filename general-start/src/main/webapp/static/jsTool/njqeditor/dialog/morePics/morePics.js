(function(){
	var dialogId,dialog,btnId;
	if(window.njqEditor){
		dialogId=window.njqEditor.dialogIds["morePics.js"];
		dialogId=dialogId.pop();
		btnId=dialogId.split("&")[0];
		dialogId=dialogId.split("&")[1];
	}
	if(dialogId){
		dialog=document.getElementById(dialogId);
	}else{
		dialog=document.getElementsByClassName("morePicsDialog")[0];
	}
	var dialogValue=dialog.getElementsByClassName("dialogValue")[0];
	if(btnId){
		dialogValue.getId=btnId;
	}
	var sbtn=dialog.getElementsByClassName("selepicbtn")[0];
	var chosePics=dialog.getElementsByClassName("chosePics")[0];
	chosePics.addEventListener("change",fileChange,false);
	sbtn.addEventListener("click",function(){
		chosePics.click();
	},false);
	var clickImg=dialog.getElementsByClassName("clickImg")[0];
	clickImg.addEventListener("click",function(){
		chosePics.click();
	},false);
	var startUp=dialog.getElementsByClassName("startUp")[0];
	startUp.addEventListener("click",upToServer,false);
	var continueUp=dialog.getElementsByClassName("continueUp")[0];
	continueUp.addEventListener("click",function(){
	    chosePics.click();
	},false);
	var picAreas=dialog.getElementsByClassName("picsArea")[0];
	var closeBtns=dialog.getElementsByClassName("closeDialog");
	var s=dialog.getElementsByClassName("manyPicAreas")[0];
	var f=dialog.getElementsByClassName("manyPicAreaf")[0];
	var hide=function(){
		var lastChild=picAreas.lastElementChild;
		picAreas.innerHTML="";
		picAreas.appendChild(lastChild);
		f.style.display="block";
	    s.style.display="none";
	}
	dialog.hideFun=hide;

	function fileChange(e){
		var target=e.target;
	    var isIE = /msie/i.test(navigator.userAgent) && !window.opera; 
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
	    var newpicWidth=1000;
	    var newpicHeight=600;
	    var img=new Image();
	    img.src=window.URL.createObjectURL(target.files[0]);
	    img.fileData=target.files[0];
	    this.value=""
	    img.onload = function(){
	    	if(!img.dataHeight){
	    		img.dataHeight=img.height;
	    		img.dataWidth=img.width;	    		
	    	}
	    	img.style.height=118+"px";
	    	img.style.width=118+"px";
	    }
	    //1代表待上传的意思
	    img.wait=1;
	    createImgDiv(img);
	} 

	function createImgDiv(img){
		var div=document.createElement("div");
	    div.classList.add("upPic");
	    var topDiv=document.createElement("div");
	    topDiv.classList.add("outTopDiv");
	    var topDiv1=document.createElement("div");
	    topDiv1.classList.add("outTopDiv1");
	    topDiv.appendChild(topDiv1);
	    var topDiv2=document.createElement("div");
	    topDiv1.appendChild(topDiv2);
	    topDiv2.classList.add("outTopDiv1Child");
	    topDiv2.innerHTML="删除";
	    topDiv2.addEventListener("click",delPic,false);
	    div.appendChild(topDiv);
	    //鼠标进入的时候，顶部向下显示
	    div.addEventListener("mouseenter",function(){
	    	if(img.wait==1){
		        startMover(topDiv,22);
	    	}
	    },false);
	    //鼠标进入的时候，顶部向上隐藏
	    div.addEventListener("mouseleave",function(){
	        startMover(topDiv,0);
	    },false); 
	    div.appendChild(img);
	    var div1=document.createElement("div");
	    div1.classList.add("outDiv");
	    div.appendChild(div1);
	    var div2=document.createElement("div");
	    div2.classList.add("outBottomDiv1");
	    div2.innerHTML="0%";
	    div1.appendChild(div2);
	    var div3=document.createElement("div");
	    div3.classList.add("outBottomDiv2");
	    div1.appendChild(div3);
	    var title=dialog.getElementsByClassName("picTitleTip")[0];
	    picAreas.insertBefore(div,picAreas.lastElementChild);
	    title.innerHTML="当前选择"+(picAreas.children.length-1)+"张图片"
	    s.style.display="block";
	    f.style.display="none";
	}

	function delPic(){
		var img=this.parentElement.parentElement.parentElement.getElementsByTagName("img")[0];
		this.parentElement.parentElement.parentElement.remove();
	}
	
	var sure=dialog.getElementsByClassName("sure")[0];
	sure.addEventListener("click",check,false);
	function check(){
		if(f.style.display==""||f.style.display=="block"){
			hide();
		}else{
			var imgs=picAreas.getElementsByTagName("img");
			var unUpNum=0;
			for(var i=0;i<imgs.length;i++){
				if(imgs[i].wait!=2){
					unUpNum++;
				}
			}
			var errTip=dialog.getElementsByClassName("errTip")[0];
			if(unUpNum>0){
				errTip.innerHTML="有"+unUpNum+"张图片尚未上传!";
			}else{
				errTip.innerHTML="";
				dialogValue.picArray=imgs;
				dialogValue.click();
				hide();
			}
		}
	}
	
	function startMover(node,itarget){//目标值
	    clearInterval(node.timer);//执行当前动画同时清除之前的动画
	    node.timer=setInterval(function(){
	        var speed = 2;
	        var curValue=Number(node.style.height.split("px")[0]);
	        if(curValue<itarget){
	            node.style.height=(curValue+speed)+"px";
	        }else if(curValue>itarget){
	            node.style.height=(curValue-speed)+"px";
	        }else{
	             clearInterval(node.timer);
	        }
	    },30);
	}

	function upToServer(){
		var pics=dialog.getElementsByClassName("picsArea")[0];
		var imgs=pics.getElementsByTagName("img");
		for(var i=0;i<imgs.length;){
			if(imgs[i].wait==1){
				if(btnId){
					if(njqEditor.userConfig.pic.upType==2){
						postFile(imgs[i],njqEditor.userConfig.pic.picSrc);						
					}else{
						getFiles(imgs[i]);
					}
				}
			}
			i++;
		}
	}

	function getFiles(img){
		img.wait=2;
		img.nextElementSibling.firstElementChild.innerHTML="完成";
		img.nextElementSibling.style.display="block";
		img.nextElementSibling.firstElementChild.nextElementSibling.style.width="100%";
	}
	
	function postFile(img,url){
	    var data=img.fileData;
	    var formData=new FormData();
	    formData.append("myfile", data);
	    var xmlhttp; 
	    if (window.XMLHttpRequest){ 
	        xmlhttp=new XMLHttpRequest(); 
	    }else{ 
	        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); 
	    }
	    xmlhttp.open("post", url, true);
	    xmlhttp.onreadystatechange=function(){
	        if (xmlhttp.readyState==4&&xmlhttp.status==200){
	            if(xmlhttp.responseText != null){
		            img.src=xmlhttp.responseText;
		            var numNode=dialog.getElementsByClassName("readyUpnums")[0];
		            var num=Number(numNode.innerHTML);
		            numNode.innerHTML=(num+1);
	            }
	        }
	    };
	    xmlhttp.upload.onprogress=function(evt){
	        if (evt.lengthComputable){
	            var percentComplete = Math.round(evt.loaded*100/evt.total);
	            var d1=img.nextElementSibling;
	            var d2=d1.firstElementChild;
	            var d3=d2.nextElementSibling;
	            if(percentComplete==100){
	                d2.innerHTML="完成";
	                //2代表上传成功
	                img.wait=2;
	            }else{
	            	d2.innerHTML=percentComplete+"%";
	            }
	            d1.style.display="block";
	            d3.style.width=percentComplete+"%";
	        }
	    };
	    xmlhttp.send(formData);
	}
})()
