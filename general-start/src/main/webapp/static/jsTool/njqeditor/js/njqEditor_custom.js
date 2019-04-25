(function(){
	function loadCustom(editorConfig){
		var constants=njqEditor.constants;
		var util=njqEditor.util;
		var styles=njqEditor.styleConfig;
		var service=njqEditor.serviceImpl;
		var customEvent={
			_decodePre:function() {
				service._preDecode();
			}
		}
		var customService={
			_preDecode:function(){
				var range =service.getRange();
				var pre = util.getSpecalParentNode(constants.PRE, range.startContainer);
				if(pre){
					var textarea = util.createCustomNode("textarea");
					textarea.value = js_beautify(pre.innerText,2,"&nbsp;");
					pre.innerHTML=textarea.value;
					textarea.remove();
				}
			},
			_demoService:function(){
				alert("111")
			}
		}
		
		//进行触发事件合并
		util.assign(njqEditor.eventListeners,customEvent);
		//进行服务事件合并
		util.assign(njqEditor.serviceImpl,customService);
	}
	njqEditor.customEvent=loadCustom;
})();
