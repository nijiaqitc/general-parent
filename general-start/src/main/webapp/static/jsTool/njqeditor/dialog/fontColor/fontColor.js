(function(){
	var dialogId,dialog,btnId;
	if(window.njqEditor){
		dialogId=window.njqEditor.dialogIds["fontColor.js"];
		dialogId=dialogId.pop();
		btnId=dialogId.split("&")[0];
		dialogId=dialogId.split("&")[1];
	}
	if(dialogId){
		dialog=document.getElementById(dialogId);
	}else{
		dialog=document.getElementsByClassName("colorDialog")[0];
	}
	var dialogValue=dialog.getElementsByClassName("dialogValue")[0];
	if(btnId){
		dialogValue.getId=btnId;
	}
	var getValue=function(){
		dialogValue.getTitle=this.innerHTML;
		dialogValue.getValue=this.getAttribute("tempattr").toUpperCase();
		dialogValue.click();
		if(!dialogId){
			alert("title:"+this.innerHTML+" value:"+this.getAttribute("tempattr"));
		}
	}
	
	var getColor=function(){
		if(!this.coloValue){
			this.coloValue="#ffffff";
		}
		dialogValue.getColor=this.coloValue;
		dialogValue.click();
		if(!dialogId){
			alert("title:"+this.innerHTML+" value:"+this.getAttribute("tempattr"));
		}
	}
	var allColors=dialog.getElementsByClassName("singleColor");
	for(var i=0;i<allColors.length;i++){
		allColors[i].coloValue=allColors[i].style.backgroundColor;
		allColors[i].addEventListener("click",getColor,false);
	}
	
	var colorSure=dialog.getElementsByClassName("colorSure")[0];
	var getMoreColor=dialog.getElementsByClassName("getMoreColor")[0];
	var backToSingleColor=dialog.getElementsByClassName("backToSingleColor")[0];
	var manycolorSele=dialog.getElementsByClassName("manycolorSele")[0];
	var singleColorSele=dialog.getElementsByClassName("singleColorSele")[0];
	getMoreColor.addEventListener("click",function(){
		manycolorSele.style.display="block";
		singleColorSele.style.display="none";
	},false);
	backToSingleColor.addEventListener("click",function(){
		manycolorSele.style.display="none";
		singleColorSele.style.display="block";
	},false);
	var colorBar=dialog.getElementsByClassName("colorgl")[0];
	var rtcolor=dialog.getElementsByClassName("rtcolor")[0];
	var colorArea1=dialog.getElementsByClassName("colorArea1")[0];
	var colorPoint=dialog.getElementsByClassName("colpick_selector_outer")[0];
	var colorBarSet=dialog.getElementsByClassName("colpick_hue_arrs")[0];
	var colorValue=dialog.getElementsByClassName("colorVlaue")[0];
	var colorView=dialog.getElementsByClassName("colorView")[0];
	var rtcolor=dialog.getElementsByClassName("rtcolor")[0];
	var colorTypes=dialog.getElementsByClassName("colorType");
	colorSure.addEventListener("click",getColor,false);
	rtcolor.r=0;
	rtcolor.g=255;
	rtcolor.b=0;
	colorBar.addEventListener("click", function(e){
		var cc=seleColor(e.offsetY);
		rtcolor.style.backgroundColor="#"+cc.r+cc.g+cc.b;
		var pointsetx=colorPoint.offsetLeft;
		var pointsety=colorPoint.offsetTop;
		var cc1=getColor(pointsetx,pointsety,pointsetx/180,pointsety/180);
		colorBarSet.style.top=e.offsetY+"px";
		colorView.style.backgroundColor="#"+cc1.r+cc1.g+cc1.b;
		colorValue.innerHTML="#"+cc1.r+cc1.g+cc1.b;
		colorSure.coloValue="#"+cc1.r+cc1.g+cc1.b;
	});
	var setColor=function(e){
		var cc=getColor(e.offsetX,e.offsetY,e.offsetX/this.offsetWidth,e.offsetY/this.offsetHeight);
		colorView.style.backgroundColor="#"+cc.r+cc.g+cc.b;
		colorPoint.style.top=e.offsetY+"px";
		colorPoint.style.left=e.offsetX+"px";
		colorValue.innerHTML="#"+cc.r+cc.g+cc.b;
		colorSure.coloValue="#"+cc.r+cc.g+cc.b;
	}
	colorArea1.addEventListener("click", setColor);
	var seleColor=function(h){
		var r,g,b;
		var ch=parseInt(h/1.5);
		if(ch<20){
			r=255;g=0;b=parseInt(ch/20*255);
		}else if(ch<40){
			r=255-parseInt((ch-20)/20*255);g=0;b=255;
		}else if(ch<60){
			r=0;g=parseInt((ch-40)/20*255);b=255;
		}else if(ch<80){
			r=0;g=255;b=255-parseInt((ch-60)/20*255)
		}else if(ch<100){
			r=parseInt((ch-80)/20*255);b=0;g=255;
		}else if(ch<120){
			r=255;g=255-parseInt((ch-100)/20*255);b=0;
		}
		rtcolor.r=r;
		rtcolor.g=g;
		rtcolor.b=b;
		var cc={};
		cc.r=r.toString(16);
		cc.g=g.toString(16);
		cc.b=b.toString(16);
		if(cc.r.length==1){
			cc.r="0"+cc.r;
		}
		if(cc.g.length==1){
			cc.g="0"+cc.g;
		}
		if(cc.b.length==1){
			cc.b="0"+cc.b;
		}
		return cc;
	}
	var getColor=function(w,h,w1,h1){
		var r,g,b;
		if(rtcolor.r<255){
			r=rtcolor.r+(255-rtcolor.r)*(1-w1);
		}else{
			r=255;
		}
		if(rtcolor.g<255){
			g=rtcolor.g+(255-rtcolor.g)*(1-w1);
		}else{
			g=255;
		}
		if(rtcolor.b<255){
			b=rtcolor.b+(255-rtcolor.b)*(1-w1);
		}else{
			b=255;
		}
		r=r-r*h1;
		g-=g*h1;
		b-=b*h1;
		var cc={};
		cc.r=parseInt(r).toString(16);
		cc.g=parseInt(g).toString(16);
		cc.b=parseInt(b).toString(16);
		if(cc.r.length==1){
			cc.r="0"+cc.r;
		}
		if(cc.g.length==1){
			cc.g="0"+cc.g;
		}
		if(cc.b.length==1){
			cc.b="0"+cc.b;
		}
		return cc;
	}
	for(var i=0;i<colorTypes.length;i++){
		colorTypes[i].addEventListener("click",function(){
			colorView.style.backgroundColor=this.getAttribute("tempattr");
			colorValue.innerHTML=this.getAttribute("tempattr");
			colorSure.coloValue=this.getAttribute("tempattr");
		});
	}
	/*colorSure["click"]="_setBackGroundColor";
	njqEditor.commonUtil.addEventListener(colorSure,"click",njqEditor.eventListeners._commonDialogEventController);*/
})()