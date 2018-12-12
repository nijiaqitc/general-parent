(function(){
	var dialogId,dialog,btnId;
	if(window.njqEditor){
		dialogId=window.njqEditor.dialogIds["fontTitle.js"];
		dialogId=dialogId.pop();
		btnId=dialogId.split("&")[0];
		dialogId=dialogId.split("&")[1];
	}
	if(dialogId){
		dialog=document.getElementById(dialogId);
	}else{
		dialog=document.getElementsByClassName("setTitleDiv")[0];
	}
	var dialogValue=dialog.getElementsByClassName("dialogValue")[0];
	if(btnId){
		dialogValue.getId=btnId;
	}
	var contextList=dialog.getElementsByClassName("contextList")[0].children;
	var getValue=function(){
		dialogValue.getTitle=this.innerHTML;
		dialogValue.getValue=this.getAttribute("tempattr").toUpperCase();
		dialogValue.click();
		if(!dialogId){
			alert("title:"+this.innerHTML+" value:"+this.getAttribute("tempattr"));
		}
	}
	dialogValue.bindAttr={};
	for(var i=0;i<contextList.length;i++){
		contextList[i].addEventListener("click",getValue,false);
		dialogValue.bindAttr[contextList[i].getAttribute("tempattr").toUpperCase()]=contextList[i].innerHTML;
	}
	//初始化时，为按钮添加基本属性
	if(btnId){
		var btn=document.getElementById(btnId);
		btn.bindAttr=dialogValue.bindAttr;
	}
})()