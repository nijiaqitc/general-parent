(function(){
	var dialogId,dialog,btnId;
	if(window.njqEditor){
		dialogId=window.njqEditor.dialogIds["tip.js"];
		dialogId=dialogId.pop();
		btnId=dialogId.split("&")[0];
		dialogId=dialogId.split("&")[1];
	}
	if(dialogId){
		dialog=document.getElementById(dialogId);
	}else{
		dialog=document.getElementsByClassName("tipOutDiv")[0];
	}
	var dialogValue=dialog.getElementsByClassName("dialogValue")[0];
	if(btnId){
		dialogValue.getId=btnId;
		dialog.dialogValue=dialogValue;
	}
	
	dialogValue.addEventListener("click",function(){
		dialog.firstElementChild.innerHTML=this.text;			
	},false);
})()