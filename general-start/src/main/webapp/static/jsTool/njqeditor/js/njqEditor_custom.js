(function(){
	var njqEditor=window.njqEditor;
	var constants=njqEditor.constants;
	var service=njqEditor.serviceImpl;
	var util=njqEditor.util;
	var styles=njqEditor.styleConfig;
	var customEvent=njqEditor.customEvent={
		_demoEvent:function(){
			customService._demoService();
		}	
	}
	var customService=njqEditor.customService={
		_demoService:function(){
			alert("111")
		}
	}
	//进行触发事件合并
	util.assign(njqEditor.eventListeners,njqEditor.customEvent);
	//进行服务事件合并
	util.assign(njqEditor.serviceImpl,njqEditor.customService);
})();
