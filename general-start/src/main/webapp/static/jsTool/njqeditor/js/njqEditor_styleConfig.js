(function(){
	njqEditor.styleConfig={
		//事件的名称:添加的样式
		btnDefault:function(node){
			node.classList.add("btnDiv");
		},
		//鼠标经过时变色
		btnChangeHover:function(node){
			node.firstElementChild.classList.add(node.hoverClass);
		},
		//鼠标移出时变色
		btnRecoverHover:function(node){
			node.firstElementChild.classList.remove(node.hoverClass);
		},
		//事件的名称:添加的样式
		btnDown:function(node){
			node.classList.add("btnDown1");
			if(node.btnDownFlag){
				node.classList.remove(node.downClass);
			}else{
				node.classList.add(node.downClass);
			}
		},
		//设置按钮为不可用
		btnDisable:function(node){
			node.style.opacity="0.3";
		},
		//恢复按钮为可用
		btnEnable:function(node){
			node.style.opacity="1";
		},
		//修改按钮状态为按下
		btnChangeColor:function(node){
			node.classList.add(node.downClass);
		},
		//将按下按钮状态恢复成原样
		btnRecoverColor:function(node){
			node.classList.remove(node.downClass);
		},
		//显示弹框
		showDialog:function(node){
			node.classList.remove("hideDialog");
			node.classList.add("showDialog");
		},
		//隐藏弹框
		hideDialog:function(node){
			node.classList.add("hideDialog");
			node.classList.remove("showDialog");
		},
		//弹框是否有被隐藏
		dialogIsHide:function(dialog){
			if(dialog.classList.contains("hideDialog")){
				return true;
			}else{
				return false;
			}
		},
		//样式强行显示
		showStyleNode:function(node){
			node.style.display="block";
		},
		//样式强行隐藏
		hideStyleNode:function(node){
			if(node){
				node.style.display="none";				
			}
		},
		//设置弹框的位置
		setDialogOffset:function(node,top,left){
			node.style.top=top+"px";
			node.style.left=left+"px";
		},
		//设置宽度和高度
		setNodeWidthAndHeight:function(node,width,height){
			node.style.width=width+"px";
			node.style.height=height+"px";
		},
		//设置位置
		setNodePlace:function(node,left,top){
			node.style.left=left+"px";
    		node.style.top=top+"px";
		},
		//设置表格为选中状态
		selectTd:function(td){
			td.classList.add("selectTdClass");
		},
		//单元格是否有被选中
		isTdselected:function(td){
			if(td.classList.contains("selectTdClass")){
				return true;
			}else{
				return false;
			}
		},
		//设为可用
		enable:function(btn){
			btn.classList.add("commonEnable");
		},
		//设为不可用
		unenable:function(btn){
			btn.classList.remove("commonDisable");
		},
		//按钮是否可用
		isEnable:function(btn){
			if(btn.classList.contains("commonDisable")){
				return true;
			}else{
				return false;
			}
		},
		//设置表格为非选中状态
		unselectTd:function(td){
			td.classList.remove("selectTdClass");
		},
		//为节点添加特定的值
		setSpecalStyleValue:function(node,styleName,styleValue){
			node.style[styleName]=styleValue;	
		},
		//全屏操作
		fullSceen:function(editorNode,prefix){
			var body=document.getElementById(prefix+"njqEditor_body");
			var bottom=document.getElementById(prefix+"njqEditor_wordCount").parentNode;
			var toos=document.getElementById(prefix+"njqEditor_toolDiv");
			var editorDiv=document.getElementById(prefix+"njqEditor_editorDiv");
			var editorTip=document.getElementById(prefix+"njqEditor_tip");
			editorNode.classList.remove("editor_normal");
			editorNode.classList.add("editor_full");
			body.classList.add("editor_full_body");
			body.classList.remove("context-background");
			bottom.classList.add("editor_full_bottom");
			bottom.classList.remove("editor_recover_bottom");
			toos.classList.add("editor_full_tools");
			toos.classList.remove("editor_recover_tools");
			editorDiv.classList.add("editor_full_editorDiv");
			editorDiv.classList.remove("editor_recover_editorDiv");
			editorTip.classList.add("editor_tip_full");
			var btnArea=toos.getElementsByClassName("toolTopDiv");
			if(btnArea){
				for(var i=0;i<btnArea.length;i++){
					if(i==0){
						btnArea[i].parentElement.style.width="16%";						
					}else if(i==1){
						btnArea[i].parentElement.style.width="28%";
					}else if(i==2){
						btnArea[i].parentElement.style.width="28%";
					}else if(i==3){
						btnArea[i].parentElement.style.width="16%";
					}
				}
			}
		},
		//取消全屏操作
		cancelFullSceen:function(editorNode,prefix){
			var body=editorNode.getElementById(prefix+"njqEditor_body");
			var bottom=editorNode.getElementById(prefix+"njqEditor_wordCount").parentNode;
			var toos=editorNode.getElementById(prefix+"njqEditor_toolDiv");
			var editorDiv=editorNode.getElementById(prefix+"njqEditor_editorDiv");
			var editorTip=editorNode.getElementById(prefix+"njqEditor_tip");
			editorNode.classList.add("editor_normal");
			editorNode.classList.remove("editor_full");
			body.classList.remove("editor_full_body");
			body.classList.add("context-background");
			bottom.classList.remove("editor_full_bottom");
			bottom.classList.add("editor_recover_bottom");
			toos.classList.remove("editor_full_tools");
			toos.classList.add("editor_recover_tools");
			editorDiv.classList.remove("editor_full_editorDiv");
			editorDiv.classList.add("editor_recover_editorDiv");
			editorTip.classList.remove("editor_tip_full");
			var btnArea=toos.getElementsByClassName("toolTopDiv");
			if(btnArea){
				for(var i=0;i<btnArea.length;i++){
					btnArea[i].parentElement.removeAttribute("style");
				}
			}
		},
		//删除指定的样式
		delStyleCSS:function(node,styleName){
			node.style[styleName]=null;
		},
		//移除样式
		clearStyle:function(node){
			node.removeAttribute("style");
		},
		//移除样式类
		clearClass:function(node){
			node.classList.remove();
		},
		//移除样式和样式类
		clearBoth:function(node){
			node.removeAttribute("style");
			node.classList.remove();
		},
		//设定自定义样式
		customStyle:function(node,style){
			node.setAttribute("style",style);
		},
		//设置input为可用
		setInputEnable:function(input){
			input.classList.add("inputEnable");
			input.classList.remove("inputDisable");
		},
		//设置input为不可用
		setInputDisable:function(input){
			input.classList.remove("inputEnable");
			input.classList.add("inputDisable");
		},
		//为节点添加边框样式
		setBorderSolid:function(node){
			node.style.border="1px solid";
		},
		//格式化样式清理
		decodeClear:function(node){
			if(node.nodeType==3){
				return;
			}
			for (var i=0;i<node.attributes.length;) {
				if(njqEditor.sysConfig.removeAttr.indexOf(node.attributes[i].name)==-1){
					node.removeAttribute(node.attributes[i].name);
				}else{
					i++;
				}
			}
			if(node.getAttribute("face")){
				node.style["fontFamily"]=node.getAttribute("face");
				node.removeAttribute("face");
			}
			//去除背景图片
			node.style["backgroundImage"]=null;
			//剔除节点中一些不必要的样式
			if(node.getAttribute("style")){
				var temp,value,st,styleObj={},arr;
				st=node.getAttribute("style").split(";");
				//提取前一个节点的样式
				for(var i in st){
					if(st[i]==""){
						continue;
					}else{
						arr=st[i].split(":");
						styleObj[arr[0].trim()]=arr[1].trim();
						temp=arr[0].trim();
						value=arr[1].trim();
						//mso是指从word中复制过来的无用样式，initial是word中复制过来的使用默认值，也没用
						if(new RegExp("^mso").test(temp)){
							styleObj[temp]=null;
							continue;
						}else if(node.style[temp]=="initial"){
							styleObj[temp]=null;
							continue;
						}else if(njqEditor.sysConfig.removeStyle.indexOf(temp)>-1){
							//去除掉定位的样式
							styleObj[temp]=null;
							continue;	
						}
						//将pt单位转换为px单位
						if(new RegExp("pt$").test(value)){
							styleObj[temp]=parseInt(parseFloat(value.substr(0,value.length-2))*4/3)+"px";
						}
					}
				}
				var str="";
				for(var i in styleObj){
					if(styleObj[i]!=null){
						str+=i+":"+styleObj[i]+";";						
					}
				}
				node.setAttribute("style",str);
			}
			//设置表格宽度，避免超出编辑器
			if(node.tagName=="TABLE"){
				node.style.minWidth="100%";
				node.style.maxWidth="100%";
				node.style.width=null;
			}
			//清除序列的高度，避免影响格局
			if(node.tagName=="OL"||node.tagName=="UL"){
				node.style.height=null;
			}
			//对指定节点进行样式清除
			if(njqEditor.sysConfig.unStyleLabel.indexOf(node.tagName)>-1){
				node.removeAttribute("style");
			}
		},
		setTableStyle:function(table){
			table.setAttribute("style","margin-bottom: 10px;border-collapse: collapse;display: table;border-spacing: 2px;border-color: grey;min-width: 100%;max-width: 100%;");
		},
		setTrStyle:function(tr){
			tr.setAttribute("style","display: table-row;vertical-align: inherit;border-color: inherit;padding: 0;word-wrap: break-word;cursor: text;");
		},
		setTrColorStyle:function(tr){
			tr.setAttribute("style","background-color:#f7faff;display: table-row;vertical-align: inherit;border-color: inherit;padding: 0;word-wrap: break-word;cursor: text;");
		},
		setTdStyle:function(td,num){
			/*var width=njqEditor.sysConfig.editNode.editorContext.offsetWidth-num;*/
			var w=Math.ceil(100/num);
			td.setAttribute("style","width: "+w+"%;vertical-align: top;display: table-cell;padding: 5px 10px;border: 1px solid #DDD;");
		},
		setTdReWidth:function(td,w){
			td.style.width=w+"%";
		},
		//设置菜单置顶
		setToolBarTop:function(bar){
			bar.setAttribute("style","width:"+bar.offsetWidth+"px;position:fixed;top:0px;");
		},
		//恢复菜单置顶
		setToolBarRecover:function(bar){
			bar.removeAttribute("style");
		},
		//检测工具条置顶状态
		checkBarFlag:function(bar){
			if(bar.style.position=='fixed'){
				return true;
			}else{
				return false;
			}
		},
		//设置代码高亮
		setCodeHighLight:function(node){
			node.style["color"]="red";
		},
		//检测节点是否是高亮
		checkIsHigh:function(node){
			if(node.style["color"]=="red"){
				return true;
			}else{
				return false;
			}
		},
		//检测是否隐藏了
		checkIsHide:function(node){
			if(!node){
				return true;
			}
			if(node.style.display=="none"||node.style.display==""){
				return true;
			}else{
				return false;
			}
		},
		removePreStyle:function(node){
			node.style.fontSize=null;
		}
	}
})();