(function(){
	var dialogId,dialog,btnId;
	if(window.njqEditor){
		dialogId=window.njqEditor.dialogIds["customStyle.js"];
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
		dialogValue.getStyle=this.getAttribute("style");
		dialogValue.click();
		if(!dialogId){
			alert("title:"+this.innerHTML+" value:"+this.getAttribute("tempattr"));
		}
	}
	dialogValue.bindAttr={};
	var styleList,key;
	//对每个下拉选项进行按钮绑定
	for(var i=0;i<contextList.length;i++){
		if(contextList[i].nodeType==1){
			contextList[i].addEventListener("click",getValue,false);
			styleList=contextList[i].getAttribute("style").split(";");
			key=contextList[i].innerHTML;
			dialogValue.bindAttr[key]={};
			for(var j=0;j<styleList.length;j++){
				if(styleList[j]!=""){
					dialogValue.bindAttr[key][styleList[j].split(":")[0].trim()]=styleList[j].split(":")[1].trim();
				}
			}
		}
    }
	//初始化时，为按钮添加基本属性
	if(btnId){
		var btn=document.getElementById(btnId);
		btn.bindAttr=dialogValue.bindAttr;
	}
})()