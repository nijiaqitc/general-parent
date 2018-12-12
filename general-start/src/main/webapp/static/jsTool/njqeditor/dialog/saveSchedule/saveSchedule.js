(function(){
	var dialogId,dialog,btnId;
	if(window.njqEditor){
		dialogId=window.njqEditor.dialogIds["saveSchedule.js"];
		dialogId=dialogId.pop();
		btnId=dialogId.split("&")[0];
		dialogId=dialogId.split("&")[1];
	}
	if(dialogId){
		dialog=document.getElementById(dialogId);
	}else{
		dialog=document.getElementsByClassName("saveSc")[0];
	}
	var dialogValue=dialog.getElementsByClassName("dialogValue")[0];
	if(btnId){
		dialogValue.getId=btnId;		
	}
	var sch=dialog.getElementsByClassName("schedulep")[0];
	var jindu=dialog.getElementsByClassName("scheduleText")[0];
	var ktime,num=100;	
	var reset=function(){
		num=100;
		ktime=setInterval(start, 10);
	}
	var start=function(){
		console.info("111")
		num=num-0.1;
		sch.style.width=num+"%";
		if(num<=0){
			window.clearInterval(ktime);
			sch.style.width="0%";
			jindu.innerHTML=100+"%";		
		}else{
			jindu.innerHTML=100-parseInt(num)+"%";			
		}
	}
	var dialogValue=dialog.getElementsByClassName("dialogValue")[0];
	var contineBar=function(){
		var max=Number(this.max);
		var cn;
		if(this.restNum){
			sch.style.width=this.restNum+"%";
		}
		if(sch.style.width){
			cn=Number(sch.style.width.split("%")[0]);
		}else{
			cn=100;
		}
		var growBar=function(){
			console.info("-----")
			cn=cn-0.5;
			console.info(cn)
			if((100-cn)<=max){
				sch.style.width=cn+"%";
				jindu.innerHTML=100-parseInt(cn)+"%";				
			}else{
				window.clearInterval(growBarInter);
				sch.style.width=(100-max)+"%";
				jindu.innerHTML=max+"%";
			}
		}
		if(growBarInter){
			window.clearInterval(growBarInter);			
		}
		var growBarInter=setInterval(growBar, 10);
	} 
	dialogValue.addEventListener("click",contineBar,false);
})()
