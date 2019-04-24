(function(){
	//将编辑器的工具对象赋予一个新的对象
	function loadBind(editorNode){
		var util=njqEditor.util;
		var tools=njqEditor.toolConfig.toolsNode;
		var ids=editorNode.idsNode;
		var userConfig=editorNode.userConfig;
		var btn,eventList,cnode,childNode;
		for(var t in tools){
			if(util.indexOf(userConfig.useBtn,t)==-1){
        		continue;
        	}
			btn=tools[t];
			for(var eventName in btn.event){
				cnode=btn.firstElementChild;
				if(eventName=="click"){
					eventList=btn.event[eventName].split(",");
					childNode=cnode.children;
					for(var i=0;i<eventList.length;i++){
						if(i==0){
							//左边图案为整个按钮绑定事件
							util.addCommonEventListener(editorNode.bindEventListeners,cnode,eventName,eventList[i],1);
						}else{
							//右边箭头单独绑定事件
							util.addCommonEventListener(editorNode.bindEventListeners,childNode[i],eventName,eventList[i],1);
						}
					}
				}else{
					util.addCommonEventListener(editorNode.bindEventListeners,cnode,eventName,btn.event[eventName],1);
				}
			}
		}
		
		if(util.indexOf(userConfig.useBtn,"njqEditor_upPic")>-1){
			//特殊事件，单独自己去绑定
			var put=document.getElementById(editorNode.prefix+"njqEditor_upPic").getElementsByTagName("input")[0];
			util.addCommonEventListener(editorNode.bindEventListeners,put,"change","_fileChange",1);
    	}
		
		//编写文本区域事件绑定
		var bodyEvent=njqEditor.bodyEvent={
				njqEditor_context:{
					keyup:["_keyUp","_addhistroy"],
					keydown:["_keyDown"],
					blur:["_onblur"],
					paste:["_onpaste"],
					mousedown:["_mouseDown"],
					mouseup:["_mouseUp"]
				}	
		};
		//将其他控件与事件进行关联绑定
		for(var eventName in bodyEvent){
			//绑定用户自定义事件
			for(var bEvent in bodyEvent[eventName]){
				for(var i=0;i<bodyEvent[eventName][bEvent].length;i++){
					util.addCommonEventListener(editorNode.bindEventListeners,ids.editorContext,bEvent,bodyEvent[eventName][bEvent][i],3);
				}
			}
		}
		
		//需要绑定的document事件
		var docEvent=njqEditor.docEvent={
				njqEditor_document:{
					mouseup:"_docMouseUp",
					mousedown:"_docMouseDown",
					scroll:"_scroll",
					mousemove:"_mouseMove",
					ondragstart:"_unEval"
				}
		};
		//绑定document事件
		for(var eventName in docEvent){
			for(var bEvent in docEvent[eventName]){
				util.addCommonEventListener(editorNode.bindEventListeners,ids.editor,bEvent,docEvent[eventName][bEvent],4);
			}
		}
	}
	window.njqEditor.bindfun=loadBind;
	
	//数据初始化区
//	(function(){
//		//初始化填充文本区中的内容
//		var context=ids.editorContext;
//		if(njqEditor.userConfig.initText!=null&&njqEditor.userConfig.initText!=""){
//			context.innerHTML=njqEditor.userConfig.initText.replace(/^\s+|\s+$/gm, '').replace(/[\r\n\t]/g, "");			
//		}else{
//			context.innerHTML="<div><br></div>";
//		}
//		var userConfig=njqEditor.userConfig;
//		//如果设置了初始内容，那么进行加载
//		if(userConfig.initContent){
//			context.innerHTML=userConfig.initContent.replace(/[\f\n\r\t\v]/g, "");
//		}
//		njqEditor.eventListeners._init();
//	})();
	
})();