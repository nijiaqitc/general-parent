(function(){
	var dialogId,dialog,btnId,btn;
	if(window.njqEditor){
		dialogId=window.njqEditor.dialogIds["lineHeight.js"];
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
		btn=document.getElementById(btnId);
	}
	var contextList=dialog.getElementsByClassName("contextList")[0].children;
	var getValue=function(){
		dialogValue.getValue=this.getAttribute("tempattr");
		var act=this.parentElement.getElementsByClassName("active");
		if(act.length>0){
			act[0].classList.remove("active");		    				
		}
		this.firstChild.firstChild.classList.add("active");
		if(btnId){
			btn.setValue=this.getAttribute("tempattr");
		}
		dialogValue.click();
		if(!btnId){
			alert(" value:"+this.getAttribute("tempattr"));
		}
	}
	dialogValue.bindAttr={};
	for(var i=0;i<contextList.length;i++){
		contextList[i].addEventListener("click",getValue,false);
		dialogValue.bindAttr[contextList[i].getAttribute("tempattr")]=contextList[i];
	}
	if(btn){
		btn.bindAttr=dialogValue.bindAttr;
	}
})()