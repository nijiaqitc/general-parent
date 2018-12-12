(function(){
	var dialogId,dialog,btnId;
	if(window.njqEditor){
		dialogId=window.njqEditor.dialogIds["table.js"];
		dialogId=dialogId.pop();
		btnId=dialogId.split("&")[0];
		dialogId=dialogId.split("&")[1];
	}
	if(dialogId){
		dialog=document.getElementById(dialogId);
	}else{
		dialog=document.getElementsByClassName("createTable")[0];
	}
	var dialogValue=dialog.getElementsByClassName("dialogValue")[0];
	if(btnId){
		dialogValue.getId=btnId;
	}
	var lines=dialog.getElementsByClassName("tdunsele");
	var tableArea=dialog.getElementsByClassName("tableArea")[0];
	var mouseenter=function(){
		var actives=dialog.getElementsByClassName("tdactive");
		//先恢复所有变色方块
		while(actives.length>0){
			actives[0].classList.remove("tdactive");
		}
		//然后计算当前选中的位置
		var tdnum=1,trnum=1,tr=this.parentElement,td=this,table=tr.parentElement; 
		while(td.previousElementSibling){
			tdnum++;
			td=td.previousElementSibling;
		}
		while(tr.previousElementSibling){
			trnum++;
			tr=tr.previousElementSibling
		}
		dialog.getElementsByClassName("trNum")[0].innerHTML=trnum;
		dialog.getElementsByClassName("tdNum")[0].innerHTML=tdnum;
		tableArea.trnum=trnum;
		tableArea.tdnum=tdnum;
		 //最后对部分的框进行选中变色
		var trs=table.children,tds;
		for(var i=0;i<trnum;i++){
			tds=trs[i].children;
			for(var j=0;j<tdnum;j++){
				tds[j].firstElementChild.classList.add("tdactive");
			}
		}
	};
	var leave=function(){
		var actives=dialog.getElementsByClassName("tdactive");
		//先恢复所有变色方块
		while(actives.length>0){
			actives[0].classList.remove("tdactive");
		}
		dialog.getElementsByClassName("trNum")[0].innerHTML=0;
		dialog.getElementsByClassName("tdNum")[0].innerHTML=0;
		tableArea.trnum=0;
		tableArea.tdnum=0;
	}
	for(var i=0;i<lines.length;i++){
		lines[i].addEventListener("mouseenter",mouseenter,false);
	}
	var getValue=function(){
		dialogValue.trnum=tableArea.trnum;
		dialogValue.tdnum=tableArea.tdnum;
		dialogValue.colorChange=dialog.getElementsByTagName("input")[0].checked;
		dialogValue.click();
		if(!btnId){
			alert(dialogValue.trnum+"--"+dialogValue.tdnum)
		}
	}
	tableArea.addEventListener("mouseleave",leave,false);
	tableArea.addEventListener("click",getValue,false);
})()