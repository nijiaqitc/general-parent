(function(){
	/*----------------浏览器校验低版本不绑定事件开始--------------------*/
	var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
    if (isIE) {
        var IE5 = IE55 = IE6 = IE7 = IE8 = false;
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if(fIEVersion<10){
        	console.error("低版本ie绑定事件后，会出现无响应状态");
        	return;
        }
    }
    /*----------------浏览器校验低版本不绑定事件结束--------------------*/
    
    
	var njqEditor=window.njqEditor;
	//将编辑器的工具对象赋予一个新的对象
	var util=njqEditor.util;
	var tools=njqEditor.toolConfig.toolsNode;
	var ids=njqEditor.sysConfig.editNode;
	var userConfig=njqEditor.userConfig;
	//事件绑定区域
	(function(){
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
							util.addCommonEventListener(cnode,eventName,eventList[i],1);
						}else{
							//右边箭头单独绑定事件
							util.addCommonEventListener(childNode[i],eventName,eventList[i],1);
						}
					}
				}else{
					util.addCommonEventListener(cnode,eventName,btn.event[eventName],1);
				}
			}
		}
		
		if(util.indexOf(userConfig.useBtn,"njqEditor_upPic")>-1){
			//特殊事件，单独自己去绑定
			var put=document.getElementById("njqEditor_upPic").getElementsByTagName("input")[0];
			util.addCommonEventListener(put,"change","_fileChange",1);
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
					util.addCommonEventListener(ids.editorContext,bEvent,bodyEvent[eventName][bEvent][i],3);
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
				util.addCommonEventListener(document,bEvent,docEvent[eventName][bEvent],4);
			}
		}
	})();
	
	
	//数据初始化区
	(function(){
		//初始化填充文本区中的内容
		var context=ids.editorContext;
//		context.innerHTML="<div>222<span style='font-family: 黑体, SimHei;'>22222</span>2222</div><div><ol><li>111111</li><li>111111</li><li>111111</li></ol></div><p><span>111<a href='#'>222</a>333</span></p><p>111<a href='#'>222</a>333</p><p>111<a href='#'>222</a>333</p><div>22222</div><div><ol><li>111111</li><li>111111</li><li>111111</li></ol></div><p>111111111</p><p><pre>111112222223333333</pre></p><div><span><strong>22222222!2222</strong>2222!22222222</span></div>"+
//		"<span>123<span>!<span>#<span><strong>$$<span>mmm</span></strong></span><strong>#</strong></span>!</span>@@<span><span>1</span>4<span>2</span>5<span>3</span>6</span>7</span>"+
//		"人人人人人人ffff<label>NNN</label>gggg人人人人人人人<hr></br>"+
//		"sssasssss\\asssssssssssa\\sssssssssss<br/></br></br><div></br></br></div><span>111</span>"+
//		"ddd弟弟顶顶<img src='blob:http://localhost:8080/2128e7e4-baa9-424b-89cc-bbe66c27c293' height='106' width='400'>顶顶<dd1>1111</dd1><span  class='aaa bbb' style='color:rgb(130,217,0)' >222222222</span></br><a href='#'>aaaa<label>sss</label><label>sss</label><label>sss</label><label>sss</label>vvvv</a>1111111111111111111111<a href='#'>aaaaaaaaa</a>"+
//		"</br><div><span>11111<strong>111</strong></span></div><div></br></div><div><span><strong>222222222222</strong>222222222222</span></div><div><br/><br/><br/><br/></div><div><br/></div><div><br/></div><div><br/></div><div><br/></div><div><br/></div><strong><span>11<label >ssss</label>111</span></strong><strong>dddd</strong>"+
//		"eeeeeew</br>"+
//		"人人<strong>人人人</strong>111<strong>人人人</strong>人人人人</br>"+
//		"<label>ffff</label>去去去去去去去去去123456789 eee<span>!!</span>eee"+
//		"<span>www<span><canvas width='300' height='200'></canvas>!!</span>www</span><strong >ll</strong><strong  class='aaa bb'>ll</strong> <strong></strong></br>"+
//		"<strong >是是是<img src='blob:http://localhost:8080/2128e7e4-baa9-424b-89cc-bbe66c27c293' height='106' width='400'>是试试事实上事实上事实上事实上事实上是是试试事实上<span>1事实上1</span><span>2事实上2</span>事实上事实上</strong><strong>bbb</strong></br>"+
//		"<span><strong>bvvb</strong></span><span><strong>caac</strong></span><div>111111</div>"+
//		"<table>"+
//		"<tr>"+
//		"<td><strong>1111111</strong></td><td>222222</td><td>3333333</td>"+
//		"</tr>"+
//		"<tr>"+
//		"<td>aaaa</td><td></td><td></td>"+
//		"</tr>"+
//		"</table><p>1111dddddd</p><table><tr><td>vbvbvb</td><td></td><td></td><td></td><td></td></tr><tr><td></td><td colspan='2'></td><td colspan='2'></td></tr>"+
//		"<tr><td colspan='2'></td><td colspan='2' rowspan='2'></td><td></td></tr><tr><td></td><td></td><td></td></tr></table>";
		
		
		
//		context.innerHTML="<div><span style='color:blue;'>111</span><span><div>22<h1>5555</h1>222</div></span>333333</div>";
//		aaa.innerHTML="<div><strong><span>11\u200B22</span><lean>111</lean></strong></div>";
		/*aaa.innerHTML="<div><strong><span>111</span></strong><strong><span>222</span></strong></div>";*/
		if(njqEditor.userConfig.initText!=null&&njqEditor.userConfig.initText!=""){
			context.innerHTML=njqEditor.userConfig.initText.replace(/^\s+|\s+$/gm, '').replace(/[\r\n\t]/g, "");			
		}else{
			context.innerHTML="<div><br></div>";
		}
		var userConfig=njqEditor.userConfig;
		//如果设置了初始内容，那么进行加载
		if(userConfig.initContent){
			context.innerHTML=userConfig.initContent.replace(/[\f\n\r\t\v]/g, "");
		}
		njqEditor.eventListeners._init();
	})();
	
})();