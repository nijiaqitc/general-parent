(function(){
	var dialogId,dialog,btnId;
	if(window.njqEditor){
		dialogId=window.njqEditor.dialogIds["model.js"];
		dialogId=dialogId.pop();
		btnId=dialogId.split("&")[0];
		dialogId=dialogId.split("&")[1];
	}
	if(dialogId){
		dialog=document.getElementById(dialogId);
	}else{
		dialog=document.getElementsByClassName("modelDialog")[0];
	}
	var submiBtn=dialog.getElementsByClassName("dialogValue")[0];
	if(btnId){
		submiBtn.getId=btnId;
	}
	var demos=dialog.getElementsByClassName("demo");
	var seleBtns=dialog.getElementsByClassName("seleBtn");
	var a=dialog.getElementsByClassName("modelActive")[0];
	submiBtn.demoValue=a.innerHTML;
	var sele=function(){
		var index=this.innerHTML-1;
		for(var i=0;i<demos.length;i++){
			if(i==index){
				demos[i].classList.add("modelActive");
				submiBtn.demoValue=demos[i].innerHTML;
			}else{
				demos[i].classList.remove("modelActive");
			}
		}
		for(var i=0;i<seleBtns.length;i++){
			if(seleBtns[i]==this){
				seleBtns[i].classList.add("seleActive");
			}else{
				seleBtns[i].classList.remove("seleActive");
			}
		}	        			
	}
	for(var i=0;i<seleBtns.length;i++){
		seleBtns[i].addEventListener("click",sele,false);
	}
})()
