(function(){
	var dialogId,dialog,btnId;
	var njqEditor=window.njqEditor;
	if(njqEditor){
		dialogId=njqEditor.dialogIds["emotion.js"];
		dialogId=dialogId.pop();
		btnId=dialogId.split("&")[0];
		dialogId=dialogId.split("&")[1];
	}
	if(dialogId){
		dialog=document.getElementById(dialogId);
	}else{
		dialog=document.getElementsByClassName("emotionDialog")[0];
	}
	var dialogValue=dialog.getElementsByClassName("dialogValue")[0];
	if(btnId){
		dialogValue.getId=btnId;
	}
	var getPic=function(){
		dialogValue.getpic=this.firstElementChild.src;
		dialogValue.click();
	}
	var changeBtns=dialog.getElementsByClassName("bottomChangeBtn");
	var initImg=function(){
		var index
		if(this==window){
			index=0;
		}else{
			index=parseInt(this.innerHTML)-1;
		}
		if(changeBtns[index].classList.contains("active")){
			return;
		}
		for(var i=0;i<changeBtns.length;i++){
			if(i==index){
				changeBtns[index].classList.add("active");	            				
			}else{
				changeBtns[i].classList.remove("active");	            				
			}
		}
		var imgArea=dialog.getElementsByClassName("topArea");
		if(changeBtns[index].isinit){
			for(var i=0;i<imgArea.length;i++){
				if(i==index){
					imgArea[i].style.display="block";
				}else{
					imgArea[i].style.display="none";
				}
			}
			return;
		}
		for(var i=0;i<imgArea.length;i++){
			if(i!=index){
				imgArea[i].style.display="none";
			}else{
				imgArea[i].style.display="block";
			}
		}
		var d1=document.createElement("div");
		d1.classList.add("lineStyle");
		var d2=document.createElement("div");
		d2.classList.add("gifCss");
		changeBtns[index].isinit=true;
		var tempAttr=changeBtns[index].getAttribute("tempAttr");
		var name=tempAttr.split("_")[0];
		var num=tempAttr.split("_")[1];
		var img=document.createElement("img");
		var tempImg,tempd1=d1.cloneNode(),tempd2=d2.cloneNode();
		var picName="dialog/emotion/images/"+name+"/"+name;
		var sysurl=njqEditor.sysConfig.url;
		for(var i=0;i<num;i++){
			tempImg=img.cloneNode();
			tempd2=d2.cloneNode();
			if(i<9){
				tempImg.src=sysurl+picName+"00"+(i+1)+".gif";
			}else if(i<99){
				tempImg.src=sysurl+picName+"0"+(i+1)+".gif";
			}
			tempd2.appendChild(tempImg);
			if(i%10==0){
				tempd1=d1.cloneNode();
				imgArea[index].appendChild(tempd1);
			}
			tempd1.appendChild(tempd2);
			//绑定点击图片事件
			tempd2["click"]="_expression";
			
			tempd2.addEventListener("click",getPic,false);
			
//			njqEditor.commonUtil.addEventListener(tempd2,"click",njqEditor.eventListeners._commonDialogEventController);
		}
		var empty=10-num%10;
		if(empty!=0&&empty!=10){
			for(var i=0;i<empty;i++){
				tempd2=d2.cloneNode();
				tempd1.appendChild(tempd2);
			}
		}
	}
	
	initImg();
	for(var i=0;i<changeBtns.length;i++){
		changeBtns[i]["click"]="_changeExpress";
		changeBtns[i].addEventListener("click",initImg,false);
	}
})()
