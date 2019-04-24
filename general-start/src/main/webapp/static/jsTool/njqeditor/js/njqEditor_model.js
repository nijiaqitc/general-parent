(function() {
	function loadPage(editorNode) {
		var sysUrl = njqEditor.sysConfig.url;
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var text = xmlhttp.responseText
						.match(/<body>[\S\s]*?<\/body>/g)[0];
				editorNode.modelText = text.replace("<body>", "").replace("</body>", "").trim();
			}
		}
		var mo = editorNode.editorDiv.getAttribute("modelStyle");
		if (mo != null && mo != "") {
			xmlhttp.open("GET", sysUrl + "modelStyle/" + mo + ".html", true);
		} else {
			xmlhttp.open("GET", sysUrl + "modelStyle/styleOne.html", true);
		}
		xmlhttp.send();
	}
	for(var i = 0 ; i <njqEditor.editorNodes.length;i++){
		loadPage(njqEditor.editorNodes[i]);
	}
})();
