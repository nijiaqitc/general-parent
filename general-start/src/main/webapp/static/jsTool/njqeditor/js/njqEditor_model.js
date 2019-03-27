(function() {
	var sysUrl = window.njqEditor.userConfig.url;
	var util = window.njqEditor.util;
	var loadPage = function() {
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		var editorNode = njqEditor.editorNode;
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var text = xmlhttp.responseText
						.match(/<body>[\S\s]*?<\/body>/g)[0];
				text = text.replace("<body>", "").replace("</body>", "").trim();
				var div = document.createElement("div");
				div.innerHTML = text;
				var ed = div.firstChild;
				// 根据配置设置是否隐藏
				if (!njqEditor.userConfig.initShow) {
					ed.style.display = "none";
				}
				var userConfig = window.njqEditor.userConfig;
				var contextBody = util.getElementsByClassName(ed,
						"context-background")[0];
				if (userConfig.autoHeight) {
					contextBody.style.height = "inherit";
				} else {
					// 设置编辑器高度
					if (userConfig.initHeight) {
						contextBody.style.height = userConfig.initHeight + "px";
					}
				}
				// 设置是否显示字数统计
				if (userConfig.wordCount.isShow) {
					if (!userConfig.wordCount.top) {
						var topNum = util.getElementsByClassName(ed,
								"topNumCountArea")[0];
						topNum.style.display = "none";
					}
					if (!userConfig.wordCount.bottom) {
						var bottomNum = util.getElementsByClassName(ed,
								"textNum")[0];
						bottomNum.style.display = "none";
					}
				} else {
					var bottomNum = util.getElementsByClassName(ed, "textNum")[0];
					bottomNum.style.display = "none";
					var topNum = util.getElementsByClassName(ed,
							"topNumCountArea")[0];
					topNum.style.display = "none";
				}
				editorNode.parentNode.insertBefore(ed, editorNode);
				window.njqEditor.userConfig.initText = editorNode.innerHTML;
				//添加隐藏参数域
				var sdpd = document.createElement("div");
				sdpd.style.display="none";
				sdpd.id=njqEditor.sysConfig.ids.editorParamDiv;
				var sendp= document.createElement("input");
				sendp.value = editorNode.getAttribute("pv");
				sdpd.append(sendp);
				//将隐藏域置于底部
				ed.append(sdpd);
				util.remove(editorNode);
				var scriptList = div.getElementsByTagName("script");
				var fn, evalHtml;
				for (var i = 0; i < scriptList.length; i++) {
					if (scriptList[i].id) {
						evalHtml = util.trim(scriptList[i].innerHTML);
						if (window.execScript) {
							window.execScript(evalHtml);
						} else {
							if (util.endsWith(evalHtml, "\\(\\)")) {
								evalHtml = evalHtml.substr(0,
										evalHtml.length - 2);
								fn = eval("(true&&" + evalHtml + ")");
							}
							fn = eval("(true&&" + evalHtml + ")");
							fn();
						}
					}
				}
				util.remove(div);
				// 将所需要用到的节点引用封装到对象中避免每次都要getElementById来找
				var editNode = njqEditor.sysConfig.editNode = {};
				var ids = njqEditor.sysConfig.ids;
				for ( var editId in ids) {
					editNode[editId] = document.getElementById(ids[editId]);
				}
				//未加载完全不允许编辑
				editNode["editorContext"].setAttribute("contenteditable", false);
				// 由于要进行顺序加载js，所以必须写成这样，否则js是并行下载，先下载完成的先执行，导致报错
				loadScript(sysUrl + "js/njqEditor_event.js", function() {
					loadScript(sysUrl + "js/njqEditor_custom.js", function() {
						loadScript(sysUrl + "js/njqEditor_bind_event.js",
								function() {});
					});
				});
			}
		}
		var mo = editorNode.getAttribute("modelStyle");
		if (mo != null && mo != "") {
			xmlhttp.open("GET", sysUrl + "modelStyle/" + mo + ".html", true);
		} else {
			xmlhttp.open("GET", sysUrl + "modelStyle/styleOne.html", true);
		}
		xmlhttp.send();
		var loadScript = function(url, callback) {
			var script = document.createElement("script");
			script.type = "text/javaScript";
			if (script.readyState) {// IE
				script.onreadystatechange = function() {
					if (script.readyState == "loaded"
							|| script.readyState == "complete") {
						script.onreadystatechange = null;
						callback();
					}
				};
			} else {// 其他浏览器
				script.onload = function() {
					callback();
				};
			}
			script.src = url;
			document.getElementsByTagName("head")[0].appendChild(script);
		}
	}
	loadPage();
})();
