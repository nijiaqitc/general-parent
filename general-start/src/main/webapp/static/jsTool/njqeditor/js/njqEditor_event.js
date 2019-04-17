(function () {
    // 编辑器总对象
    var njqEditor = window.njqEditor;
    var sysConfig = njqEditor.sysConfig;
    var userConfig = njqEditor.userConfig;
    var tools = njqEditor.toolConfig.toolsNode,
        toolsConfig = njqEditor.toolConfig.tools,
        util = njqEditor.util,
        constants = njqEditor.constants,
        ieFlag = sysConfig.ieFlag,
        njqHistory = sysConfig.njqHistory,
        rangeTable = sysConfig.rangeTable,
        rangePic = sysConfig.rangePic,
        parentIncludeNode = sysConfig.parentIncludeNode,
        singleNode = sysConfig.singleNode,
        ignoreNode = sysConfig.ignoreNode,
        resetRange = null,
        ids = njqEditor.sysConfig.editNode;
    // 存放一些中间变量，用于方法间的过渡
    var tempVar = {};
    // 上传图片缓存
    var upPicArray = {};
    // 触发事件区域
    (function () {
        // 将经常要用到的几个节点单独提取出来，省的经常要进行全文寻找这个节点，获取编辑器
        var editorNode = ids.editor;
        // 将经常要用到的几个节点单独提取出来，省的经常要进行全文寻找这个节点，获取编辑器文本区
        var editorContext = ids.editorContext;
        var toolBar = ids.editorTool;
        var styles = njqEditor.styleConfig;
        /**
         * 初始化各种任务 （由于使用任务将导致代码可读性大大降低，特别是打断点走流程，在不熟悉的情况下，
         * 根本不知道啥时候调用了啥东西，故特意减少了任务，只留下几个必要的任务） saveSceneTimer:历史记录
         * countTimer:统计字数 initTimer:重置按钮背景
         */
        var saveSceneTimer, countTimer, initTimer;
        var makeDialog = njqEditor.makeDialog;
        var dialog = njqEditor.initDialog;
        var stMark = util.createCustomNode(constants.BOOKMARK);
        stMark.checkNext = true;
        stMark.unclear = true;
        var enMark = util.createCustomNode(constants.BOOKMARK);
        enMark.checkNext = true;
        enMark.unclear = true;
        stMark.innerHTML = "[[";
        enMark.innerHTML = "]]";
        // 暴露在外的api接口
        var api = window.njq = window.njqEditor.api = {
            // 获取编辑器内容
            getContent: function () {
                return editorContext.innerHTML;
            },
            // 写入内容
            setContent: function (text) {
                if (text == constants.EMPTY || text == null) {
                    editorContext.innerHTML = "<div><br></div>";
                    return;
                }
                editorContext.innerHTML = "<div>" + text + "</div>";
                service.decodeService();
                service.sethistroy(resetRange);
            },
            // 获得纯文本
            getContentTxt: function () {
                return service.getContentTxt(editorContext.firstChild, editorContext);
            },
            // 获得带格式的纯文本
            getPlainTxt: function () {
                return service.getPlainTxt();
            },
            // 是否有文章内容
            hasText: function() {
            	if(this.getContentTxt().trim() == ""){
            		return false;
            	}else{
            		return true;
            	}
            },
            // 判断是否有内容
            hasContent: function () {
                if (editorContext.childNodes.length == 1 && editorContext.firstChild.tagName == constants.DIV) {
                    if (editorContext.firstElementChild.childNodes.length == 1 && editorContext.firstElementChild.firstElementChild.tagName == constants.BR) {
                        return false;
                    }
                }
                return true;
            },
            // 使编辑器获得焦点
            setFocus: function () {
                editorContext.focus();
            },
            // 获得当前选中的文本
            getText: function () {
                var div = util.createEmptyNode(constants.DIV);
                if (resetRange.cloneContents) {
                    div.appendChild(resetRange.cloneContents());
                    return service.getContentTxt(div.firstChild, div);
                } else {
                    return constants.EMPTY;
                }
            },
            // 获取准备保存的内容
            getSaveContext: function () {
                if (userConfig.saveWord.saveType == 2) {
                    var array = service.separateData();
                    return array;
                } else {
                    return editorContext.innerHTML;
                }
            },
            // 组合内容
            assembleContext: function (html, css) {
                return service.assembleContext(html, css);
            },
            // 插入给定的内容
            insertHtml: function (text) {
                var div = util.createEmptyNode(constants.DIV);
                div.innerHTML = text;
                if (editorContext.lastChild.childNodes.length == 1 &&
                    util.isBrLabel(editorContext.lastChild.firstChild)) {
                    util.insertBefore(div, editorContext.lastChild);
                } else {
                    editorContext.appendChild(div);
                }
                service.decodeService();
                service.sethistroy(resetRange);
            },
            // 可以编辑
            setEnabled: function () {
                service.enableUsedEdit();
            },
            // 不可编辑
            setDisabled: function () {
                service.unUsedEdit();
            },
            // 隐藏编辑器
            setHide: function () {
                ids.editor.style.display = "none";
            },
            // 显示编辑器
            setShow: function () {
                ids.editor.style.display = "block";
            },
            // 设置编辑器的高度
            setHeight: function (height) {
                if (typeof(height) == "number") {
                    ids.editorBody.style.height = height + "px";
                }
            },
            // 设置标题
            setTitle: function (setValue) {
                var title = ids.editorNameValue;
                if (title) {
                    title.innerHTML = setValue;
                }
            },
            // 设置标题序号
            setTitleNum: function (setValue) {
                var num = ids.editorNumValue;
                if (num) {
                    num.innerHTML = setValue;
                }
            },
            // 获取标题
            getTitle: function () {
                var title = ids.editorNameValue;
                if (title) {
                    return title.innerHTML;
                }
            },
            // 获取标题序号
            getTitleNum: function () {
                var num = ids.editorNumValue;
                if (num) {
                    return num.innerHTML;
                }
            },
            // 上传图片到服务器，并替换文本中对应的图片地址
            upPicToServer: function () {
                upPicToServer();
            },
            // 保存内容,str为要附带上传的内容，fun为上传成功后执行的方法
            saveData: function (str, fn) {
                if (userConfig.pic.upType == 3) {
                    upContextToServer(str, fn);
                } else {
                    upPicToServer(upContextToServer, str, fn);
                }
            },
            //将指定按钮置为不可用
            btnSetFalse: function (btnId) {
                tools[btn].handFalse = true;
            },
            //将指定按钮置为可用
            btnSetTrue: function (btnId) {
                tools[btn].handFalse = false;
            }
        };

        /**
         * 按钮绑定事件，类似于controller层
         */
        var allEvents = njqEditor.eventListeners = {
            // 初始化
            _init: function () {
                // 格式化文本区中的内容
                this._decodeHtml();
                // 初始化历史记录
                var initHis = {
                    range: {}
                };
                initHis.context = ids.editorContext.innerHTML;
                initHis.range.startNode = [0];
                initHis.range.endNode = [0];
                initHis.range.startOffset = 0;
                initHis.range.endOffset = 0;
                initHis.range.collapsed = true;
                njqHistory.list.push(initHis);
                njqHistory.historyIndex += 1;

                //初始化数据的时候创建粘贴div
                var pas = util.createCustomNode("div");
                //此节点主要用于存放粘贴的内容且不能隐藏，如果隐藏是无法粘贴进去
                pas.style.cssText = "position:absolute;width:1px;height:1px;overflow:hidden;left:-1000px;white-space:nowrap;";
                pas.id = njqEditor.sysConfig.ids.editorPaste;
                ids.editorPaste = pas;

                // 加载外部js时需要使用
                njqEditor.dialogIds = {};
                var makeDialog = njqEditor.makeDialog;
                var btn;
                var waitDialog = [];
                for (var tool in tools) {
                    btn = tools[tool];
                    tools[tool].isEnable = true;
                    // 是否还需要检测
                    tools[tool].tempFlag = true;
                    // 按钮按下状态
                    tools[tool].btnDownFlag = false;
                    // 内容是否有改变
                    tools[tool].valueChanged = false;
                    if (btn.initDialog) {
                        waitDialog.push(1);
                        makeDialog["_" + btn.dlgId](btn, function () {
                            //初始化弹框加载完成后才能用，否则有bug
                            waitDialog.pop();
                            if (waitDialog.length == 0) {
                            	loadJsSuccess();
                            }
                        });
                    }
                }
                if (waitDialog.length == 0) {
                	loadJsSuccess();
                }
                function loadJsSuccess(){
                	editorContext.setAttribute("contenteditable", true);
                    resetRange = util.initRange(editorContext);
                    njqEditor.sysConfig.initFinshFlag = true;
                    njqEditor.sysConfig.finishEvent();
                    service.resetBtnStatus();
                    allEvents._wordCountReckon();
                    service.loadClass();
                }
            },
            // 所有事件绑定执行方法
            _totalBandEvent: function (e) {
                //未初始化完成前不允许操作
                if (!njqEditor.sysConfig.initFinshFlag) {
                    return;
                }
                if (this.njqEvent.common) {
                    try {
                        allEvents[this.njqEvent.common].apply(this, [e]);
                    } catch (e) {
                        allEvents._recoverData();
                        if (sysConfig.errorLog) {
                            console.error("外层控制器捕获异常，选区进行了重置！", e);
                        }
                    }
                }
            },
            // 公共绑定的方法,所有按钮事件的唯一入口
            _commonController: function (e) {
                var btn = util.findToolBtn(this);
                if (btn.startEvent) {
                    return;
                }
                /*
				 * 判断初始按钮是否允许点击
				 */
                if (!btn.isEnable && e.type != 'mouseleave') {
                    return;
                }
                btn.startEvent = true;
                try {
                    for (var index in this.njqEvent[e.type]) {
                        allEvents[this.njqEvent[e.type][index]].apply(btn);
                    }
                } catch (e) {
                    if (sysConfig.errorLog) {
                        console.error(constants.EMPTY, e);
                    }
                    throw new Error("按钮控制器捕获异常，忽略异常继续执行！");
                }
                if (e.type == 'click') {
                    if (!btn.needModel) {
                        service.clearAndAddNewRange(resetRange);
                        if (btn.isNeedResetTool) {
                            //重置按钮状态
                            service.resetBtnStatus();
                        }
                        // 是否需要加入到历史记录中
                        if (btn.needHistroy) {
                            allEvents._addhistroy();
                        }
                    }
                    service.hiPic();
                }
                btn.startEvent = false;
                e.stopPropagation();
            },
            /**
             * 公共绑定的方法，所有弹框中事件的唯一入口
             *
             * @param e
             */
            _commonDialogEventController: function (e) {
                var dialog = util.findParentDialog(this);
                var btn = ids.editorTool.getElementById(dialog.btnId);
                try {
                    for (var index in this.njqEvent[e.type]) {
                        allEvents[this.njqEvent[e.type][index]].apply(this);
                    }
                } catch (e) {
                    if (sysConfig.errorLog) {
                        console.error(constants.EMPTY, e);
                    }
                    throw new Error("弹框控制器捕获异常，忽略异常继续执行！");
                }
                if (e.type == 'click' && (!this.uncloseDialog)) {
                    allEvents._closeAllDialog();
                    service.clearAndAddNewRange(resetRange);
                    // 重置按钮状态
                    service.resetBtnStatus();
                    // 是否需要加入到历史记录中
                    if (btn.needDlgHistroy) {
                        allEvents._addhistroy();
                        // 比如ol初始状态是不需要加入历史记录的
                        if ((!btn.needHistroy) && btn.clevty == 2) {
                            btn.needHistroy = true;
                        }
                    }
                }
                e.stopPropagation();
            },
            /**
             * 公共绑定的方法，所有本编辑器的事件，如绑定在编辑器body上的mousedown事件
             */
            _commonSysEventController: function (e) {
                try {
                    for (var index in this.njqEvent[e.type]) {
                        allEvents[ids.editorContext.njqEvent[e.type][index]].apply(this, [e]);
                    }
                    if (!tempVar.unClearRange) {
                        service.clearAndAddNewRange(resetRange);
                        // 重置按钮状态
                        service.resetBtnStatus();
                    } else {
                        tempVar.unClearRange = false;
                    }
                } catch (e) {
                    if (sysConfig.errorLog) {
                        console.error(constants.EMPTY, e);
                    }
                    throw new Error("编辑器控制器捕获异常，忽略异常继续执行！");
                }
            },
            /**
             * 公共绑定的方法，所有绑定在document上的事件
             */
            _commonDocEventController: function (e) {
                try {
                    for (var index in this.njqEvent[e.type]) {
                        allEvents[document.njqEvent[e.type][index]].apply(this, [e]);
                    }
                } catch (e) {
                    if (sysConfig.errorLog) {
                        console.error(constants.EMPTY, e);
                    }
                    throw new Error("页面控制器捕获异常，忽略异常继续执行！");
                }
            },
            /**
             * 公共绑定的方法，执行自定义的方法
             */
            _commonCustomEventController: function (e) {
                try {
                    if (e.target.njqEvent["otevent"]) {
                        e.target.njqEvent["otevent"]();
                    }
                    for (var index in this.njqEvent[e.type]) {
                        allEvents[this.njqEvent[e.type][index]].apply(this, [e]);
                    }
                    service.clearAndAddNewRange(resetRange);
                    // 重置按钮状态
                    service.resetBtnStatus();
                } catch (e) {
                    if (sysConfig.errorLog) {
                        console.error(constants.EMPTY, e);
                    }
                    throw new Error("执行自定义控制器捕获异常，忽略异常继续执行！");
                }
            },
            /**
             * 公共绑定的方法，一些其他控件的绑定事件，比如点击图片事件
             */
            _commonOtherEventController: function (e) {
                try {
                    for (var index in this.njqEvent[e.type]) {
                        allEvents[this.njqEvent[e.type][index]].apply(this, [e]);
                    }
                } catch (e) {
                    if (sysConfig.errorLog) {
                        console.error(constants.EMPTY, e);
                    }
                    throw new Error("其他事件控制器捕获异常，忽略异常继续执行！");
                }
            },
            // 源码-按钮事件
            _html: function () {
                if (this.btnDownFlag) {
                    service.setAllBtnStatus(1, [this]);
                    this.isNeedResetTool = true;
                    service.backToView();
                    this.btnDownFlag = false;
                    styles.btnRecoverColor(this);
                } else {
                    service.setAllBtnStatus(2, [this]);
                    var node = service.htmlSource1();
                    makeDialog._htmlSourceShow(this, node);
                    this.isNeedResetTool = false;
                    this.btnDownFlag = true;
                    styles.btnChangeColor(this);
                }
            },
            // 源码-查看源码重置两边行数
            _resetNum: function () {
                var range = util.getRange();
                if (range.startContainer.nodeType == 3) {
                    if (range.startContainer.data.startsWith(constants.SPACE)) {
                        var offset = range.startOffset;
                        range.startContainer.data = range.startContainer.data.replace(constants.SPACE, constants.EMPTY);
                        range.setStart(range.startContainer, offset - 1);
                        range.collapse(true);
                        util.setRange(range);
                    }
                }
                var node = util.getElementsByClassName(ids.editorEditorDiv, "codeCenterCenter")[0];
                if (node.beforeHeight != node.offsetHeight) {
                    var num = node.offsetHeight / 15;
                    var pre = node.previousElementSibling;
                    var next = node.nextElementSibling;
                    var n = pre.childElementCount;
                    var l = num - n;
                    if (l > 0) {
                        var div1, div2;
                        for (var i = 0; i < l; i++) {
                            div1 = util.createCustomNode(constants.DIV);
                            div1.innerHTML = (++n);
                            div2 = div1.cloneNode();
                            div2.innerHTML = div1.innerHTML;
                            pre.appendChild(div1);
                            next.appendChild(div2);
                        }
                    }
                    if (l < 0) {
                        l = Math.abs(l);
                        for (var i = 0; i < l; i++) {
                            pre.removeChild(pre.lastChild);
                            next.removeChild(next.lastChild);
                        }
                    }
                }
            },
            // 修改源码时，将新增的进行标红处理
            _resetRangePlace: function (event) {
                if (util.checkIsValueKeyCode(event.keyCode)) {
                    service.resetRangePlace()
                }
            },
            //代码编辑区的粘贴事件
            _htmlPaste: function () {
                //创建一个节点，然后选区放在节点中，再用定时获取节点中的内容就可以了
                var range = range = util.getRange();
                //把选区的内容进行删除
                if (!range.collapsed) {
                    util.rangeAreaDele(range);
                }
                util.removeAllChild(ids.editorPaste);
                ids.editorPaste.appendChild(util.createTextNode(constants.SPACE));
                service.insertNode(range, stMark);
                //不动态设置位置，会出现跳动的情况
                ids.editorPaste.style.top = stMark.offsetTop + "px";
                ids.editorEditorDiv.appendChild(ids.editorPaste);
                //设置节点为可编辑
                ids.editorPaste.setAttribute("contenteditable", true);
                range.setStart(ids.editorPaste, 0);
                range.setEnd(ids.editorPaste, ids.editorPaste.childNodes.length);
                util.setRange(range);
//		    	var scollTop=ids.editorBody.scrollTop;
                setTimeout(function () {
                    ids.editorPaste.remove();
                    //出现滚动条变动的时候自动修复下位置
//		    		if(scollTop!=ids.editorBody.scrollTop){
//		    			ids.editorBody.scrollTop=scollTop;
//		    		}
                    service.codePaste(ids.editorPaste);
                    stMark.remove();
                    allEvents._resetNum();
                }, 0);
            },
            // 保存-按钮事件
            _save: function () {
                var dialog = service.showDialog(this);
                dialog.dialogValue.text = "请稍等，正在上传...";
                dialog.unclose = true;
                dialog.dialogValue.click();
                var endFun = function (text, status) {
                    if (status == 200) {
                        if (text.state == 0) {
                            dialog.dialogValue.text = text.message;
                        } else {
                            dialog.dialogValue.text = "保存成功！";
                        }
                    } else if (status == "saveFalse") {
                        dialog.dialogValue.text = text;
                    } else {
                        dialog.dialogValue.text = "保存失败，请重新保存！";
                    }
                    dialog.dialogValue.click();
                    dialog.unclose = false;
                }
                if (userConfig.pic.upType == 3) {
                    upContextToServer(null, endFun);
                } else {
                    upPicToServer(upContextToServer, null, endFun);
                }
            },
            // 后退事件-按钮事件
            _back: function () {
                //由于节点被全清空了所以表格也要清空
                service.resetTable();
                service.getCache(false);
            },
            // 前进事件-按钮事件
            _go: function () {
                //由于节点被全清空了所以表格也要清空
                service.resetTable();
                service.getCache(true);
            },
            // 清空文档-按钮事件
            _clearAll: function () {
                var empty = util.createEmptyNode(constants.DIV);
                editorContext.innerHTML = constants.EMPTY;
                editorContext.appendChild(empty);
                allEvents._wordCountReckon();
                resetRange.setStartBefore(empty.firstChild);
                resetRange.setEndBefore(empty.firstChild);
                if (ids.editorNameValue) {
                    ids.editorNameValue.innerHTML = constants.EMPTY;
                }
                if (ids.editorNumValue) {
                    ids.editorNumValue.innerHTML = constants.EMPTY;
                }
            },
            // 全屏-按钮事件
            _allFullSceen: function () {
                if (this.btnDownFlag) {
                    // 取消全屏
                    styles.cancelFullSceen(editorNode);
                    this.btnDownFlag = false;
                    styles.btnRecoverColor(this);
                } else {
                    // 全屏
                    styles.fullSceen(editorNode);
                    this.btnDownFlag = true;
                    styles.btnChangeColor(this);
                }
            },
            // 全选-按钮事件
            _seleAll: function () {
                resetRange.setStartBefore(editorContext.firstChild);
                resetRange.setEndAfter(editorContext.lastChild);
            },
            // 预览-开启文档预览模式
            _viewDoc: function () {
                makeDialog._viewDocShow(this);
            },
            // 预览-关闭文档预览模式
            _closeViewDoc: function () {
                styles.showStyleNode(toolBar);
                styles.showStyleNode(ids.editorBody);
                styles.hideStyleNode(ids.editorViewBtn);
            },
            // 添加特殊标签公共方法-添加特殊标签如：strong、lean、sup
            _addSpecialLabel: function () {
                if (this.btnDownFlag) {
                    service.cancelLabel(this.labelName);
                    this.btnDownFlag = false;
                } else {
                    var node = util.createCustomNode(this.labelName);
                    service.labelSet(this, node);
                    this.btnDownFlag = true;
                }
            },
            // 下划线-字体添加下划线
            _underLine: function () {
                if (this.btnDownFlag) {
                    service.cancelLabel(this.labelName, "textDecoration", this.checkType.style.textDecoration);
                    this.btnDownFlag = false;
                } else {
                    var spanNode = util.createCustomNode(this.labelName);
                    styles.setSpecalStyleValue(spanNode, "textDecoration", this.checkType.style.textDecoration);
                    // 添加标签或样式
                    service.labelSet(this, spanNode);
                    this.btnDownFlag = true;
                }
            },
            // 边框-字体添加边框
            _around: function () {
                if (this.btnDownFlag) {
                    service.cancelLabel(this.labelName, "border", this.checkType.style.border);
                    this.btnDownFlag = false;
                } else {
                    var spanNode = util.createCustomNode(this.labelName);
                    styles.setSpecalStyleValue(spanNode, "border", this.checkType.style.border);
                    // 添加标签或样式
                    service.labelSet(this, spanNode);
                    this.btnDownFlag = true;
                }
            },
            // 删除线-字体添加删除线
            _deleLine: function () {
                if (this.btnDownFlag) {
                    service.cancelLabel(this.labelName, "textDecoration", this.checkType.style.textDecoration);
                    this.btnDownFlag = false;
                } else {
                    var spanNode = util.createCustomNode(this.labelName);
                    styles.setSpecalStyleValue(spanNode, "textDecoration", this.checkType.style.textDecoration);
                    // 添加标签或样式
                    service.labelSet(this, spanNode);
                    this.btnDownFlag = true;
                }
            },
            // 弹框-大多简单的弹框按钮绑定事件
            _showDialog: function () {
                service.showDialog(this);
            },
            // 弹框-直接关闭所有弹框
            _closeAllDialog: function () {
                var showDialogList = util.getElementsByClassName(editorNode, "showDialog");
                for (var i in showDialogList) {
                    styles.hideDialog(showDialogList[i]);
                }
                styles.hideStyleNode(ids.editorShadeBox);
            },
            // 字体颜色-按钮事件
            _setFontColor: function (e) {
                var node;
                /*
				 * 如果点击的对象含有自定义属性，那么代表点击的是弹框中的内容
				 */
                if (this.getColor) {
                    node = ids.editorTool.getElementById(this.getId);
                    styles.setSpecalStyleValue(node.firstElementChild.firstElementChild.firstElementChild, "backgroundColor", this.getColor);
                    node.hasColor = true;
                    node.color = this.getColor;
                } else {
                    if (this.id) {
                        node = this;
                    } else {
                        node = this.parentNode;
                    }
                }
                if (!node.hasColor) {
                    allEvents._showDialog.apply(this, [e]);
                    return;
                }
                var spanNode = util.createCustomNode(node.labelName);
                styles.setSpecalStyleValue(spanNode, "color", node.color);
                // 添加标签或样式
                service.labelSet(node, spanNode);
            },
            // 字体颜色-弹框选择字体颜色
            _onFontColor: function () {
                var seleNode = this.parentNode.nextSibling.firstChild;
                styles.setSpecalStyleValue(seleNode, "backgroundColor", this.getAttribute("color"));
                seleNode.nextSibling.innerHTML = this.getAttribute("color");
            },
            // 背景颜色-按钮事件
            _setBackGroundColor: function (e) {
                var node;
                if (this.getColor) {
                    node = ids.editorTool.getElementById(this.getId);
                    styles.setSpecalStyleValue(node.firstElementChild.firstElementChild.firstElementChild, "backgroundColor", this.getColor);
                    node.hasColor = true;
                    node.color = this.getColor;
                } else {
                    if (this.id) {
                        node = this;
                    } else {
                        node = this.parentNode;
                    }
                }
                if (!node.hasColor) {
                    allEvents._showDialog.apply(this, [e]);
                    return;
                }
                var spanNode = util.createCustomNode(node.labelName);
                styles.setSpecalStyleValue(spanNode, "backgroundColor", node.color);
                // 添加标签或样式
                service.labelSet(node, spanNode);
            },
            // 背景色-弹框选择字体背景颜色
            _onBackGroundColor: function () {
                var seleNode = this.parentNode.nextSibling.firstChild;
                styles.setSpecalStyleValue(seleNode, "backgroundColor", this.getAttribute("color"));
                seleNode.nextSibling.innerHTML = this.getAttribute("color");
            },
            // 字符转小写-按钮事件
            _charToLow: function () {
                if (util.isEmptyObject(rangeTable.seleTdArray)) {
                    service.charToOther(2);
                } else {
                    service.charToOtherTable(2);
                }
            },
            // 字符转大写-按钮事件
            _charToUp: function () {
                if (util.isEmptyObject(rangeTable.seleTdArray)) {
                    service.charToOther(1);
                } else {
                    service.charToOtherTable(1);
                }
            },
            // 超链接-超链接按钮
            _clickHrefBtn: function () {
                var dialog = service.showDialog(this);
                if (dialog) {
                    var data = util.getElementsByClassName(dialog, "datadialog")[0];
                    var inputs = util.getElementsByTagName(data, constants.INPUT);
                    inputs[0].value = constants.EMPTY;
                    inputs[1].value = constants.EMPTY;
                    inputs[2].value = constants.EMPTY;
                    inputs[3].checked = false;
                    var range = service.getRange();
                    // 获取焦点
                    inputs[1].focus();
                    if (!util.isEmptyObject(rangeTable.seleTdArray) || !range.collapsed) {
                        inputs[0].setAttribute("readonly", true);
                        styles.setInputDisable(inputs[0]);
                    } else {
                        inputs[0].removeAttribute("readonly");
                        styles.setInputEnable(inputs[0]);
                    }
                }
            },
            // 超链接-添加超链接
            _addHref: function () {
                var inputs = util.getElementsByTagName(this.parentNode.parentNode.firstElementChild, constants.INPUT);
                if (!inputs[0].getAttribute("readonly") && inputs[0].value == constants.EMPTY) {
                    if (inputs[0].value == constants.EMPTY) {
                        return;
                    }
                }
                if (inputs[1].value == constants.EMPTY) {
                    return;
                }
                service.addHref(inputs);
            },
            // 超链接-取消超链接
            _cancelHref: function () {
                service.cancelHref();
            },
            // 字体类型-弹框设置字体类型绑定事件
            _selectFontType: function () {
                var btn = ids.editorTool.getElementById(this.getId);
                // 下拉框文字显示
                btn.firstElementChild.firstElementChild.innerHTML = this.getTitle;
                var spanNode = util.createCustomNode(btn.labelName);
                btn.setAttribute("defaultAttr", this.getValue);
                styles.setSpecalStyleValue(spanNode, "fontFamily", this.getValue);
                // 添加标签或样式
                service.labelSet(btn, spanNode);
            },
            // 字体大小-弹框设置字体大小绑定事件
            _selectFontSize: function () {
                var btn = ids.editorTool.getElementById(this.getId);
                // 下拉框文字显示
                btn.firstElementChild.firstElementChild.innerHTML = this.getTitle;
                var spanNode = util.createCustomNode(btn.labelName);
                btn.setAttribute("defaultAttr", this.getValue);
                styles.setSpecalStyleValue(spanNode, "fontSize", this.getValue + "px");
                // 添加标签或样式
                service.labelSet(btn, spanNode);
            },
            // 居左事件-按钮事件
            _alignLeft: function () {
                service.setParaOper("textAlign:" + this.checkType.style.textAlign);
            },
            // 居中事件-按钮事件
            _alignCenter: function () {
                service.setParaOper("textAlign:" + this.checkType.style.textAlign);
            },
            // 居右事件-按钮事件
            _alignRight: function () {
                service.setParaOper("textAlign:" + this.checkType.style.textAlign);
            },
            // 两边对齐事件-按钮事件
            _alignBoth: function () {
                service.setParaOper("textAlign:" + this.checkType.style.textAlign);
            },
            // 首行缩进-按钮事件
            _textIndent: function () {
                // 判断按钮是否已经按下
                if (this.btnDownFlag) {
                    // 恢复段落原始状态
                    service.recoverParaOper("textIndent");
                } else {
                    service.setParaOper("textIndent:" + this.checkType.style.textIndent);
                }
            },
            // 段前距-按钮事件
            _beforeHeight: function () {
                var pnode;
                if (this.id) {
                    pnode = this;
                    if (!pnode.setValue) {
                        allEvents._showDialog.apply(this);
                    }
                } else {
                    pnode = ids.editorTool.getElementById(this.getId);
                }
                service.setParaOper("margin-top: " + pnode.setValue);
            },
            // 段后距-按钮事件
            _afterHeight: function () {
                var pnode;
                if (this.id) {
                    pnode = this;
                    if (!pnode.setValue) {
                        allEvents._showDialog.apply(this);
                    }
                } else {
                    pnode = ids.editorTool.getElementById(this.getId);
                }
                service.setParaOper("margin-bottom: " + pnode.setValue);
            },
            // 上下间距-行间距按钮事件
            _lineHeight: function () {
                var pnode;
                if (this.id) {
                    pnode = this;
                    if (!pnode.setValue) {
                        allEvents._showDialog.apply(this);
                    }
                } else {
                    pnode = ids.editorTool.getElementById(this.getId);
                }
                service.setParaOper("line-height: " + pnode.setValue);
            },
            // 有序列表-按钮事件
            _selectOrderList: function () {
                var node, ol;
                if (this.id) {
                    node = this;
                    if (this.btnDownFlag) {
                        service.removeListLabel(node.labelName);
                        this.btnDownFlag = false;
                        return;
                    } else {
                        this.btnDownFlag = true;
                    }
                } else {
                    node = ids.editorTool.getElementById(this.getId);
                }
                if (node.setValue) {
                    ol = util.createCustomNode(node.labelName);
                    styles.setSpecalStyleValue(ol, "listStyleType", node.setValue);
                    service.addListLabel(resetRange, ol, util.createCustomNode(constants.LI));
                } else {
                    allEvents._showDialog.apply(this);
                }
            },
            // 无序列表-按钮事件
            _selectunList: function () {
                var node;
                if (this.id) {
                    node = this;
                    if (this.btnDownFlag) {
                        service.removeListLabel(node.labelName);
                        this.btnDownFlag = false;
                        return;
                    } else {
                        this.btnDownFlag = true;
                    }
                } else {
                    node = ids.editorTool.getElementById(this.getId);
                }
                if (node.setValue) {
                    var ul = util.createCustomNode(node.labelName);
                    styles.setSpecalStyleValue(ul, "listStyleType", node.setValue);
                    var li = util.createCustomNode(constants.LI);
                    service.addListLabel(resetRange, ul, li);
                } else {
                    allEvents._showDialog.apply(this);
                }
            },
            // 清除格式-清除外标签内部所有节点的格式
            _clearDecode: function () {
                service.clearDecode();
            },
            // 标题类型-弹框选择标题类型
            _selectType: function () {
                var btn = ids.editorTool.getElementById(this.getId);
                // 下拉框文字显示
                btn.firstElementChild.firstElementChild.innerHTML = this.getTitle;
                service.setTextTitle(this.getValue);
            },
            // 自定义字体-弹框选择自定义样式
            _selectCustom: function () {
                var btn = ids.editorTool.getElementById(this.getId);
                btn.firstElementChild.firstElementChild.innerHTML = this.getTitle;
                var spanNode = util.createCustomNode(constants.SPAN);
                styles.customStyle(spanNode, this.getStyle);
                // 添加标签或样式
                service.labelSet(btn, spanNode);
            },
            // 表格-创建表格
            _createTable: function () {
                if (this.trnum < 1 || this.tdnum < 1) {
                    return
                }
                var table = util.createCustomNode(constants.TABLE);
                // 表格是否要各行变色的标记
                table.colorChange = this.colorChange;
                styles.setTableStyle(table);
                var tr = util.createCustomNode(constants.TR);
                styles.setTrStyle(tr);
                var ctr = util.createCustomNode(constants.TR);
                styles.setTrColorStyle(ctr);
                var td = util.createCustomNode(constants.TD);
                styles.setTdStyle(td, this.tdnum);
                var ntd = util.createCustomNode(constants.TD);
                var tempTr, tempTd, ftd;
                for (var i = 0; i < this.trnum; i++) {
                    if (table.colorChange) {
                        if (i % 2 == 0) {
                            tempTr = ctr.cloneNode();
                        } else {
                            tempTr = tr.cloneNode();
                        }
                    } else {
                        tempTr = tr.cloneNode();
                    }
                    for (var j = 0; j < this.tdnum; j++) {
                        if (i == 0) {
                            tempTd = td.cloneNode();
                        } else {
                            tempTd = ntd.cloneNode();
                        }
                        tempTd.appendChild(util.createCustomNode(constants.BR));
                        if (i == 0 && j == 0) {
                            ftd = tempTd;
                        }
                        tempTr.appendChild(tempTd);
                        table.appendChild(tempTr);
                    }
                }
                var range = service.getRange();
                util.cutDiv(table, range);
                rangeTable.table = table;
                service.getTableInfo(ftd);
                resetRange.setStart(ftd, 0);
                resetRange.collapse(true);
            },
            // 表格-删除表格
            _delTable: function () {
                if (rangeTable.table) {
                    var beforeNode = util.getMinLastNode(rangeTable.table.previousSibling);
                    if (beforeNode) {
                        resetRange.setStartAfter(beforeNode);
                        resetRange.collapse(true);
                    } else {
                        var nextNode = util.getMinNode(rangeTable.table.nextSibling);
                        resetRange.setEndBefore(nextNode);
                        resetRange.collapse(true);
                    }
                    util.removeNode(rangeTable.table);
                    // 清空选中表格
                    service.resetTable();
                }
            },
            // 表格-合并
            _tableMergeCells: function () {
                service.mergeCells(rangeTable);
            },
            // 表格-拆分单元格
            _tableBreakTd: function () {
                service.breakTd(rangeTable);
            },
            // 表格-插入行事件
            _tableInsertRow: function () {
                var range = service.getRange();
                service.insertRow(range.startContainer);
            },
            // 表格-删除行
            _tableDeleteRow: function (e) {
                service.deleteRow(rangeTable);
            },
            // 表格-插入列事件
            _tableInsertCol: function () {
                service.insertCol();
            },
            // 表格-删除列事件
            _tableDeleteCol: function () {
                service.deleteCol(rangeTable);
            },
            // 表格-鼠标拖动选择单元格事件
            _tableMouseOver: function (e) {
                // 判断鼠标是否已经按下
                if (rangeTable.isDown) {
                    var tdnode = util.getSpecalParentNode(constants.TD, e.target);
                    if (tdnode) {
                        var tableNode = util.getSpecalParentNode(constants.TABLE, e.target);
                        // 判断当前table和初始table是否是同一个table
                        if (tableNode != rangeTable.table) {
                            return;
                        }
                        // 如果是同一个td那么不进行 选区变换
                        if (rangeTable.currenttNode == tdnode) {
                            service.clearTdRange(rangeTable);
                            return;
                        }
                        // 设置此时鼠标已经更换
                        rangeTable.hasChange = true;
                        rangeTable.endNode = tdnode;
                        var nodeIndex = util.getArrayIndex(tdnode, rangeTable.tempTable, 1);
                        if (nodeIndex) {
                            var v = nodeIndex.split(",");
                            rangeTable.endRowIndex = parseInt(v[0]);
                            rangeTable.endColIndex = parseInt(v[1]);

                        }
                        var nodeIndex1 = util.getArrayIndex(rangeTable.rangeTd, rangeTable.tempTable, 0);
                        if (nodeIndex1) {
                            var v = nodeIndex1.split(",");
                            rangeTable.beginRowIndex = parseInt(v[0]);
                            rangeTable.beginColIndex = parseInt(v[1]);
                        }
                        // 每次移动鼠标清除之前全部选区
                        service.clearTdRange(rangeTable);
                        // 重新进行td选择
                        service.seleTdRange(rangeTable);
                        // 选择td完之后再重新设置光标选区
                        resetRange.setStart(tdnode, 0);
                        resetRange.setEnd(tdnode, 0);
                        service.clearAndAddNewRange(resetRange);
                    }
                }
            },
            // 上传图片-点击上传图片按钮
            _imageUpClick: function () {
                this.firstElementChild.firstElementChild.firstElementChild.click();
            },
            // 上传图片-隐藏图片控件按钮事件
            _fileChange: function () {
                service.fileChange(util.getElementsByTagName(this, "input")[0], service.getRange());
            },
            // 上传图片-选择图片
            _seleImg: function () {
                makeDialog._picSele(this);
                service.setTranRange(service.getRange());
                service.setRangeBefore(this);
            },
            // 上传图片-图片锚点按下事件
            _picMouseDown: function (e) {
                rangePic.isDown = true;
                rangePic.node = this;
                rangePic.startx = e.x;
                rangePic.starty = e.y;
                // 设置不能选中文本
                document.onselectstart = function () {
                    return false
                };
            },
            // 上传图片-图片锚点松开事件
            _picMouseUp: function () {
                rangePic.isDown = false;
                rangePic.startx = null;
                rangePic.starty = null;
                // 设置不能选中文本
                document.onselectstart = function () {
                    return true
                };
            },
            // 多图-多图上传
            _clickUpManyPicsBtn: function () {
                service.showDialog(this);
            },
            // 多图-批量插入图片
            _insertPics: function () {
                service.morePicsInsert(this.picArray);
            },
            // 添加代码-添加代码片段
            _dataCode: function () {
                var range = service.getRange();
                var spanNode = util.createCustomNode(this.labelName);
                service.addPreLabel(range, spanNode);
            },
            // 模板-点击模板按钮
            _docModel: function () {
                service.showDialog(this);
            },
            // 模板-选择模板
            _useModel: function () {
                editorContext.innerHTML = this.demoValue;
                service.decodeService();
            },
            // 分割线-添加分割线
            _cutLine: function () {
                service.cutLine();
            },
            // 年月日-插入年月日
            _monthDay: function () {
                var range = util.rangeAreaDele(service.getRange());
                var monthNode = service.getYMD();
                service.insertNode(range, monthNode);
                service.setRangeAfter(monthNode);
            },
            // 时分秒-插入时分秒
            _dayTime: function () {
                var range = util.rangeAreaDele(service.getRange());
                var timeNode = service.getHmD();
                service.insertNode(range, timeNode);
                service.setRangeAfter(timeNode);
            },
            // 表情-弹出表情框
            _emotion: function () {
                service.showDialog(this);
            },
            // 表情-插入表情
            _expression: function () {
                var range = util.rangeAreaDele(service.getRange());
                var img = util.createCustomNode(constants.IMG);
                img.src = this.getpic;
                util.addCommonEventListener(img, "click", "_seleImg", 5);
                service.insertNode(range, img);
                service.setRangeAfter(img);
            },
            // 格式化代码
            _decodeHtml: function () {
                service.decodeService(editorContext.firstChild);
            },
            // 插入字符串事件
            _insertStr: function (str) {
                editorContext.focus();
                var range = _range;
                range.collapse(false);
                if (!ieFlag) {
                    range.pasteHTML(str);
                    range.select();
                } else {
                    // 转换成dom对象
                    var hasR = range.createContextualFragment(str);
                    var hasR_lastChild = hasR.lastChild;
                    // 判断最后一个子元素是否是br，如果是那么进行删除
                    while (hasR_lastChild && util.isBrLabel(hasR_lastChild)
                    && hasR_lastChild.previousSibling
                    && util.isBrLabel(hasR_lastChild.previousSibling)) {
                        var e = hasR_lastChild;
                        hasR_lastChild = hasR_lastChild.previousSibling;
                        hasR.removeChild(e)
                    }
                    // 往光标处插入内容
                    service.insertNode(range, hasR);
                    if (hasR_lastChild) {
                        service.setRangeAfter(hasR_lastChild);
                    }
                }
            },
            // 统计文本区中的字数
            _wordCountReckon: function () {
                if (userConfig.wordCount.isShow) {
                    var text = editorContext.innerHTML;
                    var count = text.replace(/<.*?>/g, constants.EMPTY).replace(/&nbsp;/g, " ").length;
                    if (userConfig.wordCount.top) {
                        ids.editorTopWordCount.innerHTML = count;
                    }
                    if (userConfig.wordCount.bottom) {
                        util.getElementsByTagName(ids.editorWordCount, constants.LABEL)[0].innerHTML = count;
                    }
                }
            },
            // 按钮鼠标经过事件
            _hover: function () {
                styles.btnChangeHover(this);
            },
            // 按钮鼠标离开事件
            _out: function () {
                styles.btnRecoverHover(this);
            },
            // 阻止事件冒泡
            _stopEvent: function (event) {
                // 阻止绑定在document上事件
                event.stopPropagation();
            },
// --------------------------------------编辑区绑定区域开始-------------------------------------
            // 失去焦点事件
            _onblur: function () {
                tempVar.unClearRange = true;
//		    	alert("此处报错了")
//		    	service.setTranRange(util.getRange());
            },
            // 编辑器粘贴时触发
            _onpaste: function (e) {
                //创建一个节点，然后选区放在节点中，再用定时获取节点中的内容就可以了
                var range = service.getRange();
                //把选区的内容进行删除
                if (!range.collapsed) {
                    util.rangeAreaDele(range);
                }
                //将开始标签插入到节点中做标记
                service.insertNode(range, stMark);
                if (util.isBrLabel(stMark.nextSibling)) {
                    util.remove(stMark.nextSibling);
                }
                util.deleteAllChildrenEmptyNodes(stMark.parentNode);
                util.removeAllChild(ids.editorPaste);
                ids.editorPaste.appendChild(util.createTextNode(constants.SPACE));
                //不动态设置位置，会出现跳动的情况
                ids.editorPaste.style.top = stMark.offsetTop + "px";
                ids.editorContext.appendChild(ids.editorPaste);
                //设置节点为可编辑
                ids.editorPaste.setAttribute("contenteditable", true);
                resetRange.setStart(ids.editorPaste, 0);
                resetRange.setEnd(ids.editorPaste, ids.editorPaste.childNodes.length);
                tempVar.unClearRange = true;
                service.clearAndAddNewRange(resetRange);
                var imgs = util.getPasteImage(e);
                if (imgs && imgs.length > 0) {
                    var allImg = [];
                    for (var i = 0; i < imgs.length; i++) {
                        var reader = new FileReader();
                        var img = new Image();
                        reader.onload = function (e) {
                            img.src = e.target.result;
                            img.onload = function () {
                                if (img.width > userConfig.pic.maxWidth) {
                                    img.height = userConfig.pic.maxWidth / (img.width / img.height)
                                    img.width = userConfig.pic.maxWidth;
                                }
                            }
                        };
                        reader.readAsDataURL(imgs[i].getAsFile());
                        allImg.push(img);
                        util.insertAfter(img, stMark);
                        resetRange.setStartAfter(img);
                        resetRange.collapse(true);
                    }
                    service.decodeDealImg(allImg);
                    stMark.remove();
                    service.clearAndAddNewRange(resetRange);
                    ids.editorPaste.remove();
                } else {
                    var scollTop = ids.editorBody.scrollTop;
                    setTimeout(function () {
                        ids.editorPaste.remove();
                        //出现滚动条变动的时候自动修复下位置
                        if (scollTop != ids.editorBody.scrollTop) {
                            ids.editorBody.scrollTop = scollTop;
                        }
                        service.decodePaste(ids.editorPaste);
                        service.sethistroy(resetRange);
                    }, 0);
                }
            },
            // 鼠标按下事件
            _mouseDown: function (e) {
                allEvents._closeDialog();
                var tdnode = util.getSpecalParentNode(constants.TD, e.target);
                if (tdnode) {
                    rangeTable.isDown = true;
                    rangeTable.table = util.getSpecalParentNode(constants.TABLE, tdnode);
                    service.getTableInfo(tdnode);
                    util.addCommonEventListener(editorContext, "mouseover", "_tableMouseOver", 5);
                } else {
                    if (!rangeTable.empty) {
                        service.resetTable();
                    }
                }
                tempVar.unClearRange = true;
                tempVar.mouseDown = true;
                e.stopPropagation();
            },
            // 绑定在document上的鼠标按下事件
            _docMouseDown: function (e) {
                allEvents._closeDialog();
                if (ids.editorContext.contains(e.target)) {
                    tempVar.mouseDown = true;
                } else {
                    return;
                }
            },
            // 绑定在document上的鼠标释放事件
            _docMouseUp: function (e) {
                if (tempVar.mouseDown) {
                    service.resetRangeAndSelection(e);
                    service.resetBtnStatus();
                    e.stopPropagation();
                    tempVar.mouseDown = false;
                }
            },
            // 鼠标释放事件
            _mouseUp: function (e) {
                tempVar.mouseDown = false;
                service.setTranRange(service.getRange());
                service.resetRangeAndSelection(e);
                service.resetBtnStatus();
                tempVar.unClearRange = true;
                e.stopPropagation();
            },
            // 按键释放事件
            _keyUp: function (e) {
                // mac里面的command键
                if (e.keyCode == 91) {
                    tempVar.commandKey = false;
                }
                // 上下左右事件
                if (e.keyCode >= 37 && e.keyCode <= 40) {
                    tempVar.unClearRange = true;
                    if (!e.shiftKey) {
                        // 重置按钮状态
                        service.resetBtnStatus();
                    }
                    return;
                }
                //删除按钮判断是否删除最后一个节点
                if (e.keyCode == 8) {
                    if (ids.editorContext.childNodes.length == 1 && util.isBrLabel(ids.editorContext.firstChild)) {
                        util.removeNode(ids.editorContext.firstChild);
                    }
                    if (ids.editorContext.childNodes.length == 0) {
                        var div = util.createEmptyNode(constants.DIV);
                        ids.editorContext.appendChild(div);
                        resetRange.setStart(div, 0);
                        resetRange.collapse(true);
                        util.setRange(resetRange);
                        tempVar.unClearRange = true;
                        return;
                    }
                }
                if (e.keyCode == 16) {
                    service.resetBtnStatus();
                }
                service.setTranRange(service.getRange());
                tempVar.unClearRange = true;
            },
            // 按键按下事件
            _keyDown: function (e) {
                //如果有出现弹窗，光标不能在遮罩后面
                if (!styles.checkIsHide(ids.editorShadeBox) &&
                    document.activeElement.id == ids.editorContext.id) {
                    return;
                }
                tempVar.unClearRange = true;
                if (!util.checkIsUseFulKeyCode(e.keyCode)) {
                    return;
                }
                // 处理回车事件
                if (e.keyCode == 13) {
                    service.hidePic();
                    service.enterKeyClick(e);
                    tempVar.unClearRange = false;
                }
                // 处理tab事件
                if (e.keyCode == 9) {
                    service.hidePic();
                    service.tabKeyClick(e);
                    tempVar.unClearRange = false;
                }
                // mac里面的command键
                if (e.keyCode == 91) {
                    tempVar.commandKey = true;
                }
                // ctrl+a事件
                if ((e.ctrlKey || tempVar.commandKey) && e.keyCode == 65) {
                    tempVar.unClearRange = false;
                    return;
                }
                // ctrl+z事件
                if ((e.ctrlKey || tempVar.commandKey) && e.keyCode == 90) {
                    allEvents._back();
                    //不需要记录到历史记录中
                    tempVar.noHis = true;
                    e.stopPropagation();
                    tempVar.unClearRange = false;
                    return;
                }

                // ctrl+y事件
                if (((e.ctrlKey || tempVar.commandKey) && e.keyCode == 89)
                    || (e.shiftKey && (e.ctrlKey || tempVar.commandKey) && e.keyCode == 90)) {
                    allEvents._go();
                    //不需要记录到历史记录中
                    tempVar.noHis = true;
                    e.stopPropagation();
                    tempVar.unClearRange = false;
                    return;
                }

                // 删除按钮
                if (e.keyCode == 46 || e.keyCode == 8 || e.keyCode == 32) {
                    service.hidePic();
                    tempVar.unClearRange = false;
                }
                // 上下左右事件
                if (e.keyCode >= 37 && e.keyCode <= 40) {
                    service.hiPic();
                    return;
                }
                if (userConfig.wordCount.isShow) {
                    // 统计字数
                    clearTimeout(countTimer);
                    countTimer = setTimeout(function () {
                        allEvents._wordCountReckon();
                    }, 300);
                }
            },
            // 加入到历史中，进行定时处理，每200毫秒记录一次
            _addhistroy: function (event) {
                if (tempVar.noHis) {
                    tempVar.noHis = false;
                    return;
                }
                if (event) {
                    if (!util.checkHisKeyCode(event.keyCode)) {
                        return;
                    }
                }
                clearTimeout(saveSceneTimer);
                saveSceneTimer = setTimeout(function () {
                    service.sethistroy(resetRange);
                }, 200);
            },
// --------------------------------------编辑区绑定区域结束-------------------------------------

// --------------------------------------document绑定区域开始-------------------------------------
            // document滚动条事件(工具条到顶部时触发)
            _scroll: function (e) {
                if (userConfig.isToolScrollTop) {
                    if (editorNode.getBoundingClientRect().top <= 0) {
                        if (!styles.checkBarFlag(toolBar)) {
                            styles.setToolBarTop(toolBar);
                        }
                    } else {
                        if (styles.checkBarFlag(toolBar)) {
                            styles.setToolBarRecover(toolBar);
                        }
                    }
                }
            },
            // document鼠标移动事件
            _mouseMove: function (e) {
                // 鼠标移动，针对图片拖动的情况
                if (rangePic.isDown) {
                    service.picDrop(e);
                }
            },
            // 某些特定的弹框是不允许关闭的
            _closeDialog: function () {
                var showDialogList = util.getElementsByClassName(editorNode, "showDialog");
                if (showDialogList.length == 0) {
                    return;
                }
                var flag = false;
                for (var i in showDialogList) {
                    // 有些弹框是不允许点击其他地方进行弹框关闭的
                    if (!showDialogList[i].unclose) {
                        styles.hideDialog(showDialogList[i]);
                    } else {
                        flag = true;
                        i++;
                    }
                }
                if (!flag) {
                    styles.hideStyleNode(ids.editorShadeBox);
                }
            },
            // 不执行某些事件，比如鼠标拖动
            _unEval: function () {
                return false;
            },
            // 通用的关闭事件
            _commonClose: function (e) {
                styles.hideDialog(e.target.njqEvent["otobj"]);
                styles.hideStyleNode(ids.editorShadeBox);
            },
            //当编辑器执行报错时，重置数据，避免编辑器无法使用
            _recoverData: function (e) {
                //恢复所有按钮为可点击
                for (var t in tools) {
                    tools[t].startEvent = false;
                }
                // 格式化文本区中的内容
                this._decodeHtml();
                //关闭遮罩
                styles.hideStyleNode(ids.editorShadeBox);
                //关闭所有弹窗
                this._closeAllDialog();
                //重置选区
                resetRange = util.initRange(ids.editorContext);
                tempVar.unClearRange = false;
                tempVar.isNeedResetTool = false;
                //修改选区并发状态
                tempVar.changeFlag = false;
                //重置按钮按下状态
                service.resetBtnStatus();
                //重置字体数量
                this._wordCountReckon();
                // 添加当前历史记录
                var his = {
                    range: {}
                };
                his.context = ids.editorContext.innerHTML;
                his.range.startNode = service.getRangeTagAddress(resetRange, true);
                his.range.endNode = service.getRangeTagAddress(resetRange, false);
                his.range.startOffset = resetRange.startOffset;
                his.range.endOffset = resetRange.endOffset;
                his.range.collapsed = resetRange.collapsed;
                njqHistory.list.push(his);
                njqHistory.historyIndex += 1;
            }
// --------------------------------------document绑定区域结束-------------------------------------
        };

        /**
         * 业务逻辑处理层，类似于service层
         */
        var service = njqEditor.serviceImpl = {
            //源码编写添加标红节点
            resetRangePlace: function () {
                var range = util.getRange();
                var modiSpan = util.getSpecalParentNode(constants.SPAN, range.startContainer);
                if (modiSpan) {
                    if (!styles.checkIsHigh(modiSpan)) {
                        var modiSpan1 = util.createCustomNode(constants.SPAN);
                        styles.setCodeHighLight(modiSpan1);
                        var snode = range.startContainer;
                        if (util.isTextNode(snode)) {
                            var str = snode.data;
                            if (range.startOffset == 0 && str.charAt(0) == "<") {
                                util.insertBefore(modiSpan1, modiSpan);
                                modiSpan1.appendChild(util.createTextNode(constants.SPACE));
                                range.setStart(modiSpan1, 1);
                                range.setEnd(modiSpan1, 1);
                                util.setRange(range);
                            } else if (range.endOffset == str.length &&
                                (str.charAt(range.endOffset - 1) == ">" || str.charAt(range.endOffset - 1) == "\"")) {
                                util.insertAfter(modiSpan1, modiSpan);
                                modiSpan1.appendChild(util.createTextNode(constants.SPACE));
                                range.setStart(modiSpan1, 1);
                                range.setEnd(modiSpan1, 1);
                                util.setRange(range);
                            }
                        } else {
                            styles.setCodeHighLight(modiSpan);
                        }
                    }
                } else {
                    var modiSpan1 = util.createCustomNode(constants.SPAN);
                    styles.setCodeHighLight(modiSpan1);
                    modiSpan1.appendChild(util.createTextNode(constants.SPACE));
                    service.insertNode(range, modiSpan1, true);
                    range.setStart(modiSpan1, 1);
                    range.setEnd(modiSpan1, 1);
                    util.setRange(range);
                }
            },
            // 样式和内容进行分离（分离成style）
            separateData: function () {
                var tempDiv = util.createCustomNode(constants.DIV);
                tempDiv.innerHTML = editorContext.innerHTML;
                var place = {};
                this.completeElementPlace(tempDiv, -1, place);
                var styleStr = constants.EMPTY;
                for (var i in place) {
                    styleStr += "[0]" + i + "=" + place[i] + "|"
                }
                styleStr = styleStr.substring(0, styleStr.length - 1);
                // 进行url编码转义，目前转义的字符为 % & +
                var array = [util.tranWord(tempDiv.innerHTML), constants.EMPTY + util.tranWord(styleStr)];
//				this.styleToClass(styleStr,array[0]);
                return array;
            },
            //样式和内容进行分离（分离成class）
            separateDataClass: function () {
                var tempDiv = util.createCustomNode(constants.DIV);
                tempDiv.innerHTML = editorContext.innerHTML;
                var place = {};
                this.completeElementPlace(tempDiv, -1, place);
                var styleStr = constants.EMPTY, stlen, sty, flag = true;
                for (var i in place) {
                    sty = place[i].split(";");
                    stlen = sty.length
                    for (var j in userConfig.cssObj[stlen]) {
                        if (place[i] == userConfig.cssObj[stlen][j]) {
                            styleStr += "[1]" + i + "=" + j + "|";
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        styleStr += "[0]" + i + "=" + place[i] + "|";
                    } else {
                        flag = true;
                    }
                }
                styleStr = styleStr.substring(0, styleStr.length - 1);
                // 进行url编码转义，目前转义的字符为 % & +
                return [util.tranWord(tempDiv.innerHTML), constants.EMPTY + util.tranWord(styleStr)];
            },
            //把样式内容转换成class内容
            styleToClass: function (style, html) {
                var cssArray = style.split("|");
                var stvalue, styv, stlen, styleStr = constants.EMPTY, flag = true;
                for (var i in cssArray) {
                    stvalue = cssArray[i].split("=");
                    styv = stvalue[0].split("]");
                    stlen = stvalue[1].split(";").length;
                    for (var j in userConfig.cssObj[stlen]) {
                        if (stvalue[1] == userConfig.cssObj[stlen][j]) {
                            styleStr += "[1]" + styv[1] + "=" + j + "|";
                            flag = false;
                            break;
                        }
                    }
                    flag ? styleStr += "[0]" + styv[1] + "=" + stvalue[1] + "|" : flag = true;
                }
                styleStr = styleStr.substring(0, styleStr.length - 1);
                console.info(this.assembleContext(html, styleStr))
            },
            // 组合内容
            assembleContext: function (html, css) {
                if (!css) {
                    return html;
                }
                var cs = css.split("|");
                var tempDiv = util.createCustomNode(constants.DIV);
                tempDiv.innerHTML = html;
                var cc, pl, cnode = tempDiv, vt;
                for (var i = 0; i < cs.length; i++) {
                    cc = cs[i].split("=");
                    vt = cc[0].split("]");
                    pl = vt[1].split(",");
                    for (var j = 0; j < pl.length; j++) {
                        cnode = cnode.childNodes[pl[j]];
                    }
                    if (vt[0] == "[1") {
                        cnode.setAttribute("class", cc[1]);
                    } else {
                        cnode.setAttribute("style", cc[1]);
                    }
                    cnode.removeAttribute("labelIndex");
                    cnode = tempDiv;
                }
                return tempDiv.innerHTML;
            },
            // 计算所有元素的位置
            completeElementPlace: function (node, cdex, place) {
                var outList = node.childNodes;
                var style, tempIndex;
                for (var i = 0; i < outList.length; i++) {
                    if (util.isElementNode(outList[i])) {
                        style = outList[i].style.cssText;
                        outList[i].removeAttribute("style");
                    } else {
                        style = null;
                    }
                    if (cdex == -1) {
                        tempIndex = i;
                    } else {
                        tempIndex = cdex + "," + i;
                    }
                    if (style) {
                        place[tempIndex] = style;
                        if (userConfig.saveWord.breakType == 2) {
                            outList[i].setAttribute("labelIndex", tempIndex);
                        }
                    }
                    if (outList[i].childNodes.length > 0) {
                        this.completeElementPlace(outList[i], tempIndex, place);
                    }
                }
            },
            // 剥离样式
            delStyle: function (node) {
                var outList = node.children;
                for (var i = 0; i < outList.length; i++) {
                    outList[i].removeAttribute("style");
                    if (outList[i].childNodes.length > 0) {
                        this.delStyle(outList[i]);
                    }
                }
            },
            //加载并解析css文件(前提是页面要引用css文件)
            loadClass: function () {
                var cssName = document.styleSheets[0].href.split("/").pop();
                if (userConfig.saveWord.isTranClass) {
                    var classNames = userConfig.saveWord.tranClassName;
                    var styleSheets = document.styleSheets;
                    var loadSheets = [];
                    for (var i in classNames) {
                        for (var j = 0; j < styleSheets.length; j++) {
                            if (styleSheets[j].href && classNames[i] == styleSheets[j].href.split("/").pop()) {
                                loadSheets.push(styleSheets[j]);
                            }
                        }
                    }
                    var cssObj = userConfig.cssObj = {}, num;
                    for (var i in loadSheets) {
                        for (var j = 0, len = loadSheets[i].cssRules.length; j < len; j++) {
                            num = loadSheets[i].cssRules[j].cssText.split(";").length;
                            if (!cssObj[num]) {
                                cssObj[num] = {};
                            }
                            cssObj[num][loadSheets[i].cssRules[j].selectorText.split(" ").pop().split(".").pop()]
                                = loadSheets[i].cssRules[j].style.cssText;
                        }
                    }
                }
            },
            // 重置按钮状态和文本区的选中状态
            resetRangeAndSelection: function (e) {
                // 点击document其他地方的时候不进行任何触发事件
                /*
				 * if(document.activeElement.id!=ids.editorContext){ return; }
				 */
                if (rangeTable.noclear != null && !rangeTable.noclear) {
                    // 选中多个单元格时，选区放在第一个单元格中
                    resetRange.setStart(rangeTable.rangeTd, 0);
                    resetRange.setEnd(rangeTable.rangeTd, 0);
                    service.clearAndAddNewRange(resetRange);
                } else {
                    // 只要鼠标释放位置放在td上，那么直接重置选区
                    var td = util.getSpecalParentNode(constants.TD, e.target);
                    if (td) {
                        var table = util.getSpecalParentNode(constants.TABLE, td);
                        if (rangeTable.table != table) {
                            rangeTable.table = table;
                            this.getTableInfo(td);
                            resetRange.setStart(td, 0);
                            resetRange.setEnd(td, 0);
                            service.clearAndAddNewRange(resetRange);
                        }
                    } else if (rangeTable.rangeTd) {
                        resetRange.setStart(rangeTable.rangeTd, 0);
                        resetRange.setEnd(rangeTable.rangeTd, 0);
                        service.clearAndAddNewRange(resetRange);
                    }
                }
                if (rangeTable.isDown) {
                    rangeTable.isDown = false;
                    if (rangeTable.hasChange) {
                        util.removeListener(editorContext, "mouseover", allEvents._commonOtherEventController);
                        rangeTable.hasChange = false;
                    } else {
                        rangeTable.beginNode = rangeTable.endNode;
                        rangeTable.endColIndex = rangeTable.beginColIndex;
                        rangeTable.endRowIndex = rangeTable.beginRowIndex;
                    }
                }
                // 如果鼠标呈按下状态，那么进行取消
                if (rangePic.isDown) {
                    rangePic.isDown = false;
                    rangePic.startx = null;
                    rangePic.starty = null;
                    // 恢复可以选中文本
                    document.onselectstart = function () {
                        return true
                    };
                }
                this.hiPic();
            },
            // 弹框服务
            showDialog: function (btnNode) {
                var id = btnNode.dlgId;
                var nodeDialog = ids.editorDlgDiv.getElementById(id);
                /*
				 * 如果弹框尚未初始化，那么进行初始化
				 */
                if (!nodeDialog) {
                    makeDialog["_" + id](btnNode);
                    if (btnNode.needModel) {
                        styles.showStyleNode(ids.editorShadeBox);
                    }
                } else {
                    if (styles.dialogIsHide(nodeDialog)) {
                        styles.showDialog(nodeDialog);
                        if (btnNode.needModel) {
                            styles.showStyleNode(ids.editorShadeBox);
                            // 设置居中位置
                            styles.setDialogOffset(nodeDialog,
                                (ids.editor.clientHeight - nodeDialog.offsetHeight) / 2,
                                (ids.editor.clientWidth - nodeDialog.offsetWidth) / 2);
                        } else {
                            styles.setDialogOffset(nodeDialog, btnNode.offsetTop + 30, btnNode.offsetLeft);
                        }
                    } else {
                        styles.hideDialog(nodeDialog);
                        if (btnNode.needModel) {
                            styles.hideStyleNode(ids.editorShadeBox);
                        }
                    }
                }
                return nodeDialog;
            },
            // 添加超链接
            addHref: function (inputs) {
                var a = util.createCustomNode(constants.A);
                var href = inputs[1].value;
                var hrefs = href.split("//");
                if (hrefs.length > 1) {
                    a.setAttribute("href", href);
                } else {
                    a.setAttribute("href", "http://" + hrefs[0]);
                }
                a.title = inputs[2].value;
                if (inputs[3].checked) {
                    a.target = "_blank";
                } else {
                    a.target = "_self";
                }
                var range = this.getRange();
                if (!util.isEmptyObject(rangeTable.seleTdArray)) {
                    this.tdLabelSet(null, a);
                } else if (range.collapsed) {
                    a.innerHTML = inputs[0].value;
                    this.insertNode(range, a);
                    resetRange.setStartBefore(a);
                    resetRange.setEndAfter(a);
                } else {
                    var parentArray = util.getOutNode(this.getRange());
                    var cutNode;
                    var startAnode = util.getSpecalParentNode(constants.A, range.startContainer);
                    if (startAnode) {
                        range.setStartBefore(startAnode);
                    }
                    var endNode = util.getSpecalParentNode(constants.A, range.endContainer);
                    if (endNode) {
                        range.setEndAfter(endNode);
                    }
                    this.addSpecalLabel(range, a);
                }
            },
            // 添加链接节点
            makeHrefLink: function (cutNode, a1) {
                var dd = util.createCustomNode(constants.DIV);
                dd.appendChild(cutNode);
                var nodeList = util.getElementsByTagName(dd, constants.A);
                if (nodeList) {
                    for (var i in nodeList) {
                        // 将节点中的内容放到节点外面
                        util.moveAllChildNodeToOut(nodeList[i]);
                    }
                }
                a1.innerHTML = dd.innerHTML;
                return a1;
            },
            // 取消超链接
            cancelHref: function () {
                var range = this.getRange();
                var rangeSnode = range.startContainer;
                var startOffset = range.startOffset;
                var rangeEnode = range.endContainer;
                var endOffset = range.endOffset;
                if (range.collapsed) {
                    var outNode = util.getNodeOutNode(rangeEnode);
                    if (!util.checkIsPreNode(outNode)) {
                        var aNode = util.getSpecalParentNode(constants.A, rangeEnode);
                        if (aNode) {
                            util.moveAllChildNodeToOut(aNode);
                        }
                    }
                } else {
                    var parentArray = util.getOutNode(this.getRange());
                    var startANode = util.getSpecalParentNode(constants.A, rangeSnode);
                    if (parentArray.length > 1) {
                        if (!util.checkIsPreNode(parentArray[0])) {
                            if (startANode) {
                                util.forListNode(startANode, parentArray[0].lastChild, this.cancelAfun(), 1);
                            } else {
                                util.forListNode(rangeSnode, parentArray[0].lastChild, this.cancelAfun(), 1);
                            }
                        }
                        var l = parentArray.length - 1;
                        for (var i = 1; i < l; i++) {
                            if (!util.checkIsPreNode(parentArray[i])) {
                                util.forListNode(parentArray[i].firstChild, parentArray[i].lastChild, this.cancelAfun(), 1);
                            }
                        }
                        if (!util.checkIsPreNode(parentArray[l])) {
                            var endANode = util.getSpecalParentNode(constants.A, rangeEnode);
                            if (endANode) {
                                util.forListNode(parentArray[l].firstChild, endANode, this.cancelAfun(), 1);
                            } else {
                                util.forListNode(parentArray[l].firstChild, rangeEnode, this.cancelAfun(), 1);
                            }
                        }
                    } else {
                        var outNode = util.getNodeOutNode(rangeEnode);
                        if (!util.checkIsPreNode(outNode)) {
                            var endANode = util.getSpecalParentNode(constants.A, rangeEnode);
                            if (startANode && endANode) {
                                util.forListNode(startANode, endANode, this.cancelAfun(), 1);
                            } else if (!startANode && endANode) {
                                util.forListNode(rangeSnode, endANode, this.cancelAfun(), 1);
                            } else if (startANode && !endANode) {
                                util.forListNode(startANode, rangeEnode, this.cancelAfun(), 1);
                            } else {
                                util.forListNode(rangeSnode, rangeEnode, this.cancelAfun(), 1);
                            }
                        }
                    }
                    this.setTranRange(range);
                }
                resetRange.setStart(rangeSnode, startOffset);
                resetRange.setEnd(rangeEnode, endOffset);
            },
            // 将a标签中的内容移到外面
            cancelAfun: function () {
                return function (node) {
                    if (node.tagName == constants.A) {
                        var lastNode = node.lastChild;
                        util.moveAllChildNodeToOut(node);
                        return lastNode;
                    }
                    return node;
                }
            },
            // 插入分隔线
            cutLine: function () {
                var hr = util.createCustomNode(constants.HR);
                var range = util.cutDiv(hr, this.getRange());
                resetRange.setStartBefore(util.getMinNode(range.startContainer.nextSibling));
                resetRange.collapse(true);
            },
            // 清除格式
            clearDecode: function () {
                var range = this.getRange();
                var fflag = false, lflag = false;
                var stText, stOffset, endText, endOffset;
                if (!range.collapsed) {
                    // 设定开始选区
                    if (util.isElementNode(range.startContainer) && range.startOffset == 0
                        && range.startContainer.childNodes.length > 0) {
                        util.insertBefore(stMark, util.getMinNode(range.startContainer));
                        fflag = true;
                    }
                    if (util.isTextNode(range.startContainer)) {
                        stText = range.startContainer;
                        stOffset = range.startOffset;
                    }
                    // 设定结束选区
                    if (util.isElementNode(range.endContainer) && range.endContainer.childNodes.length > 0
                        && range.endOffset == range.endContainer.childNodes.length) {
                        util.insertAfter(enMark, util.getMinLastNode(range.endContainer));
                        lflag = true;
                    }
                    if (util.isTextNode(range.endContainer)) {
                        endText = range.endContainer;
                        endOffset = range.endOffset;
                    }
                } else {
                    stText = endText = range.startContainer;
                    stOffset = endOffset = range.startOffset;
                }
                var parentArray = util.getOutNode(range);
                util.forListNode(parentArray[0], parentArray[parentArray.length - 1], this.clearDecodefun(), 1);
                // 主要进行选区重置
                if (fflag) {
                    resetRange.setStartBefore(stMark);
                    stMark.remove();
                } else {
                    if (stText) {
                        resetRange.setStart(stText, stOffset);
                    } else {
                        resetRange.setStart(range.startContainer, range.startOffset);
                    }
                }
                if (lflag) {
                    resetRange.setEndAfter(enMark);
                    enMark.remove();
                } else {
                    if (endText) {
                        resetRange.setEnd(endText, endOffset);
                    } else {
                        resetRange.setEnd(range.endContainer, range.endOffset);
                    }
                }
            },
            clearDecodefun: function () {
                return function (node) {
                    if (node.tagName == constants.SPAN) {
                        return service.delUnNeedNode(node);
                    }
                    styles.clearBoth(node);
                    return node;
                }
            },
            // 字符转大小写
            charToOther: function (type) {
                var range = this.getRange();
                if (range.collapsed) {
                    return;
                }
                // 对开始节点和结束节点进行切割处理
                var startNode = range.startContainer;
                var endNode = range.endContainer;
                // 针对选区在文本节点之前
                if (util.isElementNode(startNode)) {
                    startNode = startNode.childNodes[range.startOffset];
                    range.setStart(startNode, 0);
                }
                if (util.isElementNode(endNode)) {
                    endNode = util.getEndRangeNode(range);
                    if (endNode.nodeType == 3) {
                        range.setEnd(endNode, endNode.nodeValue.length);
                    } else {
                        endNode = util.getBeforeTextNode(endNode, startNode);
                        range.setEndAfter(endNode);
                    }
                }
                if (startNode.nodeType == 3 && startNode == endNode) {
                    var t2;
                    if (range.startOffset == 0 && range.endContainer.length == range.endOffset) {
                        // 如果选中的是一个完整的文本节点，就不需要进行切割
                        t2 = this.charTranfun(type)(range.startContainer);
                    } else {
                        var t = util.extractContents(range);
                        var t1 = util.createTextNode(t.textContent);
                        this.insertNode(range, t1);
                        util.deleteAllChildrenEmptyNodes(t1.previousSibling, true);
                        t2 = this.charTranfun(type)(t1);
                    }
                    resetRange.setStart(t2, 0);
                    resetRange.setEnd(t2, t2.length);
                } else {
                    // 针对选区在文本第0个位置
                    if (startNode.nodeType == 3 && range.startOffset != 0) {
                        var tempRange = range.cloneRange();
                        tempRange.setEnd(startNode, startNode.nodeValue.length);
                        var t = util.extractContents(tempRange);
                        var textNode = util.createTextNode(t.textContent);
                        util.insertAfter(textNode, startNode);
                        util.deleteAllChildrenEmptyNodes(textNode.previousSibling, true);
                        util.deleteAllChildrenEmptyNodes(textNode.nextSibling, true);
                        startNode = textNode;
                    }
                    if (endNode.nodeType == 3 && range.endOffset != endNode.nodeValue.length) {
                        var tempRange = range.cloneRange();
                        tempRange.setStart(endNode, 0);
                        var t = util.extractContents(tempRange);
                        var textNode = util.createTextNode(t.textContent);
                        util.insertBefore(textNode, endNode);
                        util.deleteAllChildrenEmptyNodes(textNode.previousSibling, true);
                        util.deleteAllChildrenEmptyNodes(textNode.nextSibling, true);
                        endNode = textNode;
                    }
                    this.addBookMark(startNode, endNode);
                    // 由于循环中是将节点替换了，无法再定位选区，所以要用书签
                    util.forListNode(startNode, endNode, this.charTranfun(type), 3);
                    this.removeBookMark(range);
                    this.setTranRange(range);
                }
            },
            // 添加书签
            addBookMark: function (sNode, eNode) {
                util.insertBefore(stMark, sNode);
                util.insertAfter(enMark, eNode);
            },
            // 移除书签
            removeBookMark: function (range) {
                range.setStartAfter(stMark);
                range.setEndBefore(enMark);
                stMark.remove();
                enMark.remove();
            },
            // 转变函数
            charTranfun: function (type) {
                if (type == 1) {
                    return function (node) {
                        var textNode = util.createTextNode(node.nodeValue.toUpperCase());
                        node.parentNode.replaceChild(textNode, node);
                        return textNode;
                    }
                } else if (type == 2) {
                    return function (node) {
                        var textNode = util.createTextNode(node.nodeValue.toLowerCase());
                        node.parentNode.replaceChild(textNode, node);
                        return textNode;
                    }
                } else {
                    return function (node) {
                    }
                }
            },
            /**
             * 表格中的文本进行大小写转换
             */
            charToOtherTable: function (type) {
                var nodeList, tempNode, td;
                for (var i in rangeTable.seleTdArray) {
                    td = rangeTable.seleTdArray[i];
                    if (util.checkIsEmpty(td)) {
                        continue;
                    }
                    // 由于循环中是将节点替换了，无法再定位选区，所以要用书签
                    util.forListNode(td, td, this.charTranfun(type), 3);
                }
            },
            /**
             * node:按的按钮 labelNode:需要添加的标签按钮
             */
            labelSet: function (btnNode, labelNode) {
                if (util.isEmptyObject(rangeTable.seleTdArray)) {
                    var range = this.getRange();
                    if (!this.resetNewRange(range)) {
                        return;
                    }
                    if (range.collapsed) {
                        this.addLabel(range, btnNode, labelNode);
                    } else {
                        this.addSpecalLabel(range, labelNode);
                    }
                } else {
                    this.tdLabelSet(btnNode, labelNode);
                }
            },
            // 针对表格添加标签
            tdLabelSet: function (btn, node) {
                var nodeList, tempNode, td;
                for (var i in rangeTable.seleTdArray) {
                    td = rangeTable.seleTdArray[i];
                    if (util.checkIsEmpty(td)) {
                        continue;
                    }
                    // 第一步先判断td中是否只有一个子节点
                    if (td.childNodes.length == 1) {
                        if (td.firstChild.nodeType == 1) {
                            if (node.tagName == td.firstChild.tagName) {
                                if (util.checkParentContainsChild(node, td.firstChild)) {
                                    continue;
                                }
                            }
                            if (util.isSingleNodeCheck(td.firstChild)) {
                                continue;
                            }
                            tempNode = node.cloneNode();
                            tempNode.appendChild(td.firstChild);
                            td.appendChild(tempNode);
                            util.dealInnerSerisSameTagNameNode(tempNode, tempNode);
                            nodeList = util.getElementsByTagName(tempNode, tempNode.tagName);
                            if (nodeList.length > 0) {
                                this.checkStyleAndRemove(nodeList, tempNode);
                            }
                        } else {
                            tempNode = node.cloneNode();
                            tempNode.appendChild(td.firstChild);
                            td.appendChild(tempNode);
                        }
                    } else if (td.childNodes.length > 1) {
                        nodeList = util.getElementsByTagName(td, node.tagName);
                        if (btn != null) {
                            if (nodeList.length > 0) {
                                this.checkStyleAndRemove(nodeList, btn);
                            }
                        }
                        tempNode = node.cloneNode();
                        // 将td中的子节点全部移到tempNode节点中
                        util.removeChildrenToOtherNode(td, tempNode);
                        td.appendChild(tempNode);
                    }
                    // 删除内部样式相同的样式名或标签
                    util.delSameLabelOrCss(tempNode, node);
                }
            },
            // 空样式的节点将节点中的内容移到外面
            checkStyleAndRemove: function (nodeList, btn) {
                for (var j = 0; j < nodeList.length; j++) {
                    if (util.checkParentContainsChild(nodeList[j], btn)) {
                        // 将节点中的内容移到外面
                        util.moveAllChildNodeToOut(nodeList[j]);
                    }
                }
            },
            // 获取年月日
            getYMD: function () {
                var date = new Date();
                var seperator1 = "-";
                var month = date.getMonth() + 1;
                var strDate = date.getDate();
                if (month >= 1 && month <= 9) {
                    month = "0" + month;
                }
                if (strDate >= 0 && strDate <= 9) {
                    strDate = "0" + strDate;
                }
                return util.createTextNode(date.getFullYear() + seperator1 + month + seperator1 + strDate);
            },
            // 获取时分秒
            getHmD: function () {
                var date = new Date();
                var seperator2 = ":";
                var h, m, s;
                h = date.getHours();
                m = date.getMinutes();
                s = date.getSeconds();
                if (h < 10) {
                    h = "0" + h;
                }
                if (m < 10) {
                    m = "0" + m;
                }
                if (s < 10) {
                    s = "0" + s;
                }
                return util.createTextNode(h + seperator2 + m + seperator2 + s);
            },
            // 插入表情
            getExpression: function () {
                return util.createTextNode("^_^!");
            },
            // 查看源码(格式化方式一，只格式化外置标签，不格式化内部标签)
            htmlSource: function () {
                var t = util.createCustomNode(constants.DIV);
                t.innerHTML = editorContext.innerHTML.trim();
                var node = t.firstChild;
                var pre1, pre2, pre3, span;
                var lastNode = util.createCustomNode(constants.DIV);
                var tagName;
                while (node) {
                    if (node.nodeType == 3 || node.nodeType == 8) {
                        util.removeNode(node);
                        node = t.firstChild;
                        continue;
                    }
                    tagName = node.tagName.toLocaleLowerCase();
                    pre1 = util.createCustomNode(constants.PRE);
                    pre2 = util.createCustomNode(constants.PRE);
                    pre3 = util.createCustomNode(constants.PRE);
                    span = util.createCustomNode(constants.SPAN);
                    pre1.appendChild(span);
                    span.style.color = "#170";
                    span.innerHTML = "&lt;" + tagName;
                    for (var i = 0; i < node.attributes.length; i++) {
                        span = util.createCustomNode(constants.SPAN);
                        pre1.appendChild(util.createTextNode(" "));
                        pre1.appendChild(span);
                        span.style.color = "#00c";
                        span.innerHTML = node.attributes[i].name;
                        pre1.appendChild(util.createTextNode("="));
                        span = util.createCustomNode(constants.SPAN);
                        span.style.color = "#a11";
                        pre1.appendChild(span);
                        span.innerHTML = "\"" + node.attributes[i].value.replace(/"/g, "'") + "\"";
                    }
                    if (pre1.firstElementChild != span) {
                        span = util.createCustomNode(constants.SPAN);
                        pre1.appendChild(span);
                        span.style.color = "#170";
                        span.innerHTML = "&gt;";
                    } else {
                        span.innerHTML += "&gt;";
                    }

                    var ht = node.innerHTML.replace(/\$/g, "&#36;")
                        .replace(/&lt;/g, "&amp;lt;")
                        .replace(/&gt;/g, "&amp;gt;")
                        .replace(/\</g, "<span style='color:blue;'>&lt;")
                        .replace(/\>/g, "&gt;</span>")
                        .replace(/&quot;/g, "'");
                    if (/   /g.test(ht)) {
                        pre2.innerHTML = ht;
                    } else {
                        pre2.innerHTML = "    " + ht;
                    }
                    span = util.createCustomNode(constants.SPAN);
                    pre3.appendChild(span);
                    span.style.color = "#170";
                    span.innerHTML = "&lt;/" + tagName + "&gt;";
                    util.removeNode(node);
                    lastNode.appendChild(pre1);
                    lastNode.appendChild(pre2);
                    lastNode.appendChild(pre3);
                    node = t.firstChild;
                }
                var tipNode = ids.editorTip;
                // 获取提示文案
                tipNode.firstElementChild.innerHTML = sysConfig.titleTip.htmlSource;
                styles.showStyleNode(tipNode);
                return lastNode;
            },
            // 查看源码（格式化方式二，外部、内部全部都格式化）
            htmlSource1: function () {
                var t = util.createCustomNode(constants.DIV);
                t.innerHTML = editorContext.innerHTML.trim();
                var node = t.firstChild;
                var array = [];
                var lastNode = util.createCustomNode(constants.DIV);
                // 循环所有需要格式的外置标签
                for (var i = 0; i < t.childNodes.length; i++) {
                    array.push(0);
                    this.SourceDecode1(t.childNodes[i], array, lastNode);
                }
                util.removeNode(t);
                var tipNode = ids.editorTip;
                // 获取提示文案
                tipNode.firstElementChild.innerHTML = sysConfig.titleTip.htmlSource;
                styles.showStyleNode(tipNode);
                return lastNode;
            },
            // 代码格式化，递归调用
            SourceDecode1: function (node, array, lastNode) {
                var emptyStr, pre1, pre2, span, tagName;
                var index = array.pop();
                if (node.nodeType == 1) {
                    // 第一步先转让标签的开始部分
                    tagName = node.tagName.toLocaleLowerCase();
                    pre1 = util.createCustomNode(constants.PRE);
                    span = util.createCustomNode(constants.SPAN);
                    pre1.appendChild(util.createTextNode(this.getSpeace(index)));
                    pre1.appendChild(span);
                    span.style.color = "#170";
                    span.innerHTML = "&lt;" + tagName;
                    // 第二步转让标签内部的属性
                    for (var i = 0; i < node.attributes.length; i++) {
                        span = util.createCustomNode(constants.SPAN);
                        pre1.appendChild(util.createTextNode(" "));
                        pre1.appendChild(span);
                        span.style.color = "#00c";
                        span.innerHTML = node.attributes[i].name;
                        pre1.appendChild(util.createTextNode("="));
                        span = util.createCustomNode(constants.SPAN);
                        span.style.color = "#a11";
                        pre1.appendChild(span);
                        span.innerHTML = "\"" + node.attributes[i].value.replace(/"/g, "'") + "\"";
                    }
                    // 第三步闭合开始部分的标签
                    if (pre1.firstElementChild != span) {
                        span = util.createCustomNode(constants.SPAN);
                        pre1.appendChild(span);
                        span.style.color = "#170";
                        span.innerHTML = "&gt;";
                    } else {
                        span.innerHTML += "&gt;";
                    }
                    lastNode.appendChild(pre1);
                    if (!util.isSingleNodeCheck(node)) {
                        // 第四步对标签内部的节点进行格式化
                        for (var i = 0; i < node.childNodes.length; i++) {
                            array.push(index + 1);
                            this.SourceDecode1(node.childNodes[i], array, lastNode);
                        }
                        // 第五步闭合标签的结束节点
                        pre2 = util.createCustomNode(constants.PRE);
                        span = util.createCustomNode(constants.SPAN);
                        pre2.appendChild(util.createTextNode(this.getSpeace(index)));
                        pre2.appendChild(span);
                        span.style.color = "#170";
                        span.innerHTML = "&lt;/" + tagName + "&gt;";
                        lastNode.appendChild(pre2);
                    }
                } else if (node.nodeType == 3) {
                    if (node.wholeText.trim() != constants.EMPTY) {
                        pre1 = util.createCustomNode(constants.PRE);
                        span = util.createCustomNode(constants.SPAN);
                        pre1.appendChild(util.createTextNode(this.getSpeace(index)));
                        pre1.appendChild(util.createTextNode(node.data
                            .replace(/\</g, "&lt;").replace(/\>/g, "&gt;")));
                        lastNode.appendChild(pre1);
                    }
                }
            },
            // 根据index获取指定长度的空格
            getSpeace: function (index) {
                var speace = constants.EMPTY;
                for (var i = 0; i < index; i++) {
                    speace += "    ";
                }
                return speace;
            },
            // 将源码转换为html页面
            backToView: function () {
                var htmlNode = util.getElementsByClassName(editorNode, "codeCenterCenter")[0];
                var textNode = constants.EMPTY;
                var tempNode = htmlNode.firstChild;
                // 为了区分在源码环境下，不包含在div中的一些代码
                var t = 0, hyht;
                while (tempNode) {
                    if (tempNode.nodeType == 1) {
                        hyht = util.trim(tempNode.innerHTML).replace(/<.+?>/g, constants.EMPTY);
                        if (this.isParentpjNode("&lt;", hyht)) {
                            t = 0;
                            textNode += hyht;
                        } else if (this.isParentpjNode("&lt;/", hyht)) {
                            t = 1;
                            textNode += hyht;
                        } else if (t == 1) {
                            var temp = util.createCustomNode(constants.DIV);
                            temp.innerHTML = hyht;
                            textNode += "&lt;div&gt;" + hyht + "&lt;/div&gt;";
                        } else {
                            textNode += hyht;
                        }
                    }
                    util.removeNode(tempNode);
                    tempNode = htmlNode.firstChild;
                }
                // 将源码节点区域隐藏
                styles.hideStyleNode(htmlNode.parentNode);
                textNode = textNode.replace(new RegExp(constants.SPACE), constants.EMPTY);
// htmlNode.parentNode.remove();
                var labelList = textNode.match(/(&lt;)((?!(&lt;)).)+(&gt;)/g);
                var replaceValue;
                for (var i = 0; i < labelList.length; i++) {
                    replaceValue = labelList[i].replace(/&lt;/g, "\<").replace(/&gt;/g, "\>")
                    textNode = textNode.replace(labelList[i], replaceValue);
                }
                editorContext.innerHTML = textNode.replace(/&amp;lt;/g, "&lt;").replace(/&amp;gt;/g, "&gt;").replace(/[\r\n\t]/g, constants.EMPTY);
                // 对代码进行格式化
                this.decodeService(editorContext.firstChild);
                // 由于进行转换后所有节点全部都不存在了，所以选区也要重新设置
                this.setRangeBefore(util.getMinNode(editorContext));
                styles.showStyleNode(editorContext);
                styles.hideStyleNode(ids.editorTip);
            },
            // 判断是否是外标签开头或结束
            isParentpjNode: function (spart, text) {
                var label;
                for (var i = 0; i < parentIncludeNode.length; i++) {
                    label = spart + parentIncludeNode[i].toLowerCase();
                    if (text.startsWith(label)) {
                        return true;
                    }
                }
            },
// ----------------------------------------------图片上传开始-----------------------------------------
            picDrop: function (event) {
                var parentNode = rangePic.node.parentNode;
                /*
				 * util.getStyle(parentNode.bandNode,"maxWidth");
				 * util.getStyle(parentNode.bandNode,"maxHeight");
				 */
                var lastwidth = parseInt(parentNode.style.width.split("px")[0]),
                    lastheight = parseInt(parentNode.style.height.split("px")[0]), width, height;
                switch (rangePic.node.index) {
                    case 1:
                        height = event.y - rangePic.starty;
                        lastheight = lastheight - height;
                        rangePic.starty = event.y;
                        break;
                    case 6:
                        height = event.y - rangePic.starty;
                        lastheight = lastheight + height;
                        rangePic.starty = event.y;
                        break;
                    case 3:
                        width = event.x - rangePic.startx;
                        lastwidth = lastwidth - width;
                        rangePic.startx = event.x;
                        break;
                    case 4:
                        width = event.x - rangePic.startx;
                        lastwidth = lastwidth + width;
                        rangePic.startx = event.x;
                        break;
                    case 0:
                        width = event.x - rangePic.startx;
                        lastwidth = lastwidth - width;
                        height = event.y - rangePic.starty;
                        lastheight = lastheight - height;
                        rangePic.startx = event.x;
                        rangePic.starty = event.y;
                        break;
                    case 2:
                        width = event.x - rangePic.startx;
                        lastwidth = lastwidth + width;
                        height = event.y - rangePic.starty;
                        lastheight = lastheight - height;
                        rangePic.startx = event.x;
                        rangePic.starty = event.y;
                        break;
                    case 5:
                        width = event.x - rangePic.startx;
                        lastwidth = lastwidth - width;
                        height = event.y - rangePic.starty;
                        lastheight = lastheight + height;
                        rangePic.startx = event.x;
                        rangePic.starty = event.y;
                        break;
                    case 7:
                        width = event.x - rangePic.startx;
                        lastwidth = lastwidth + width;
                        height = event.y - rangePic.starty;
                        lastheight = lastheight + height;
                        rangePic.startx = event.x;
                        rangePic.starty = event.y;
                        break;
                    default:
                        break;
                }
                // 设置图片外框的长宽
                styles.setNodeWidthAndHeight(parentNode, lastwidth, lastheight);
                // 图片的宽度和高度
                styles.setNodeWidthAndHeight(parentNode.bandNode, lastwidth, lastheight);
                var imgPlace = util.getPlace(parentNode.bandNode, parentNode);
                // 设置图片外框的位置
                styles.setNodePlace(parentNode, imgPlace.offsetLeft - 1, imgPlace.offsetTop - 1);
            },
            fileChange: function (target, range) {
                var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
                var fileSize = 0;
                var filetypes = [".jpg", ".png"];
                var filepath = target.value;
                var filemaxsize = 1024 * 2;// 2M
                if (filepath) {
                    var isnext = false;
                    var fileend = filepath.substring(util.indexOf(filepath, "."));
                    if (filetypes && filetypes.length > 0) {
                        for (var i = 0; i < filetypes.length; i++) {
                            if (filetypes[i] == fileend) {
                                isnext = true;
                                break;
                            }
                        }
                    }
                    if (!isnext) {
                        alert("不接受此文件类型！");
                        target.value = constants.EMPTY;
                        return false;
                    }
                } else {
                    return false;
                }
                if (isIE && !target.files) {
                    var filePath = target.value;
                    var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
                    if (!fileSystem.FileExists(filePath)) {
                        alert("附件不存在，请重新输入！");
                        return false;
                    }
                    var file = fileSystem.GetFile(filePath);
                    fileSize = file.Size;
                } else {
                    fileSize = target.files[0].size;
                }

                var size = fileSize / 1024;
                if (size > filemaxsize) {
                    alert("附件大小不能大于" + filemaxsize / 1024 + "M！");
                    target.value = constants.EMPTY;
                    return false;
                }
                if (size <= 0) {
                    alert("附件大小不能为0M！");
                    target.value = constants.EMPTY;
                    return false;
                }
                var img = new Image();
                img.src = window.URL.createObjectURL(target.files[0]);
                var picuplabel = Math.random() + constants.EMPTY;
                picuplabel = "pic" + picuplabel.substring(picuplabel.length - 6, picuplabel.length);
                img.setAttribute("picuplabel", picuplabel);
                img.onload = function () {
                    if (img.width > userConfig.pic.maxWidth) {
                        img.height = userConfig.pic.maxWidth / (img.width / img.height)
                        img.width = userConfig.pic.maxWidth;
                    }
                }
                util.addCommonEventListener(img, "click", "_seleImg", 5);
                if (!styles.checkIsHide(ids.picSele)) {
                    styles.hideStyleNode(ids.picSele);
                    range.setStartBefore(ids.picSele.bandNode);
                    range.collapse(true);
                    ids.picSele.bandNode.remove();
                    this.insertNode(range, img);
                } else {
                    util.rangeAreaDele(range);
                    this.insertNode(range, img);
                }
                //将图片放入缓存集合中
                upPicArray[picuplabel] = target.files[0];
                // 如果配置是实时上传图片，那么立即进行图片上传
                if (userConfig.pic.upType == 2) {
                    upPicToServer();
                }
                this.sethistroy(range);
            },
            // 批量插入图片
            morePicsInsert: function (imgs) {
                var range = this.getRange();
                util.rangeAreaDele(range);
                var fimg = imgs[0];
                var eimg = imgs[imgs.length - 1];
                var timg;
                //如果点击了图片，就替换图片
                if (!styles.checkIsHide(ids.picSele)) {
                    styles.hideStyleNode(ids.picSele);
                    range.setStartBefore(ids.picSele.bandNode);
                    range.collapse(true);
                    ids.picSele.bandNode.remove();
                }
                for (var i = 0; imgs.length > 0;) {
                    util.addCommonEventListener(imgs[i], "click", "_seleImg", 5);
                    timg = imgs[i];
                    this.insertNode(range, timg);
                    timg.style.width = timg.dataWidth + "px";
                    timg.style.height = timg.dataHeight + "px";
                    timg.width = null;
                    timg.height = null;
                    range.setStartAfter(timg);
                    range.collapse(true);
                }
                resetRange.setStartBefore(fimg);
                resetRange.setEndAfter(eimg);
            },
// ----------------------------------------------图片上传结束-----------------------------------------
// ----------------------------------------------表格设置开始-----------------------------------------
            // 重置表格属性
            resetTable: function () {
                this.clearTdRange(rangeTable);
                rangeTable.arrayTr = [];
                rangeTable.arrayTd = [];
                rangeTable.seleTdArray = {};
                rangeTable.currenttNode = null;
                rangeTable.tdlength = 0;
                rangeTable.beginNode = null;
                rangeTable.table = null;
                rangeTable.beginRowIndex = 0;
                rangeTable.beginColIndex = 0;
                rangeTable.rangeTd = null;
                rangeTable.endRowIndex = 0;
                rangeTable.endColIndex = 0;
                rangeTable.tempTable = [];
                rangeTable.empty = true;
                rangeTable.noclear = true;
            },
            // 记录表格的一些信息
            getTableInfo: function (tdnode) {
                rangeTable.arrayTr = util.getElementsByTagName(rangeTable.table, constants.TR);
                rangeTable.arrayTd = [];
                rangeTable.seleTdArray = {};
                rangeTable.currenttNode = tdnode;
                // noclear表示当前是否有选中单元格，true当前没有选中单元格
                rangeTable.noclear = true;
                // empty表示是否有设置过当前table，false表示已经设置过了
                rangeTable.empty = false;
                var tdnum = 0, trnum = rangeTable.table.rows.length;
                var table = rangeTable.table;
                for (var i = 0; i < rangeTable.arrayTr.length; i++) {
                    rangeTable.arrayTd.push(util.getElementsByTagName(rangeTable.arrayTr[i], constants.TD));
                }
                for (var i = 0; i < table.rows[0].cells.length; i++) {
                    tdnum += table.rows[0].cells[i].colSpan;
                }
                rangeTable.tdlength = tdnum;
                this.getTempTable();
                this.clearTdRange(rangeTable);
                if (!styles.isTdselected(tdnode)) {
                    rangeTable.beginNode = tdnode;
                    var nodeIndex = util.getArrayIndex(tdnode, rangeTable.tempTable, 0);
                    if (nodeIndex) {
                        var v = nodeIndex.split(",");
                        rangeTable.beginRowIndex = parseInt(v[0]);
                        rangeTable.beginColIndex = parseInt(v[1]);
                        rangeTable.rangeTd = tdnode;
                    }
                }
                var nodeIndex = util.getArrayIndex(tdnode, rangeTable.tempTable, 0);
                if (nodeIndex) {
                    var v = nodeIndex.split(",");
                    rangeTable.beginRowIndex = rangeTable.endRowIndex = parseInt(v[0]);
                    rangeTable.beginColIndex = rangeTable.endColIndex = parseInt(v[1]);
                }
            },
            // 获取表格快照
            getTempTable: function () {
                var table = rangeTable.table;
                rangeTable.tempTable = [];
                for (var i = 0; i < rangeTable.arrayTr.length; i++) {
                    rangeTable.tempTable[i] = {};
                }
                for (var i = 0; i < table.rows.length; i++) {
                    var k = 0;
                    for (var j = 0; j < table.rows[i].cells.length; j++) {
                        var td = table.rows[i].cells[j];
                        if (rangeTable.tempTable[i][k] != null) {
                            while (rangeTable.tempTable[i][k]) {
                                k += 1;
                            }
                        }
                        var dex = k;
                        rangeTable.tempTable[i][k] = {
                            td: td,
                            col: td.colSpan,
                            row: td.rowSpan
                        };
                        if (td.colSpan > 1 && td.rowSpan == 1) {
                            for (var l = 1; l < td.colSpan; l++) {
                                k += 1;
                                rangeTable.tempTable[i][k] = i + "," + dex;
                            }
                        }
                        if (td.rowSpan > 1) {
                            for (var l = 1; l < td.rowSpan; l++) {
                                rangeTable.tempTable[i + l][k] = i + "," + dex;
                            }
                        }
                        if (td.colSpan > 1 && td.rowSpan > 1) {
                            for (var l = 1; l < td.colSpan; l++) {
                                k += 1;
                                rangeTable.tempTable[i][k] = i + "," + dex;
                                for (var p = 1; p < td.rowSpan; p++) {
                                    rangeTable.tempTable[i + p][k] = i + "," + dex;
                                }
                            }
                        }
                        k += 1;
                    }
                }
            },
            // 选中单元格
            seleTdRange: function (rangeTable) {
                this.resetCompute(rangeTable);
                rangeTable.noclear = false;
                var trlength = rangeTable.endRowIndex - rangeTable.beginRowIndex;
                var tdlength = rangeTable.endColIndex - rangeTable.beginColIndex;

                var minX, maxX, minY, maxY;
                if (rangeTable.endRowIndex > rangeTable.beginRowIndex) {
                    minY = rangeTable.beginRowIndex;
                    maxY = rangeTable.endRowIndex;
                } else {
                    minY = rangeTable.endRowIndex;
                    maxY = rangeTable.beginRowIndex;
                }
                if (rangeTable.endColIndex > rangeTable.beginColIndex) {
                    minX = rangeTable.beginColIndex;
                    maxX = rangeTable.endColIndex;
                } else {
                    minX = rangeTable.endColIndex;
                    maxX = rangeTable.beginColIndex;
                }
                var yl = maxY - minY;
                var xl = maxX - minX;
                minX = parseInt(minX);
                minY = parseInt(minY);
                var yindex, xindex;
                for (var i = 0; i <= yl; i++) {
                    for (var j = 0; j <= xl; j++) {
                        if (typeof rangeTable.tempTable[(i + minY)][(j + minX)] == "object") {
                            xindex = j + minX;
                            yindex = i + minY;
                            styles.selectTd(rangeTable.tempTable[yindex][xindex].td);
                            rangeTable.seleTdArray[yindex + "," + xindex] = rangeTable.tempTable[yindex][xindex].td;
                        }
                    }
                }
            },
            // 重新计算各个点的位置
            resetCompute: function (rangeTable) {
                var trlength = rangeTable.endRowIndex - rangeTable.beginRowIndex;
                var tdlength = rangeTable.endColIndex - rangeTable.beginColIndex;
                var minX, maxX, minY, maxY;
                if (rangeTable.endRowIndex > rangeTable.beginRowIndex) {
                    minY = rangeTable.beginRowIndex;
                    maxY = rangeTable.endRowIndex;
                } else {
                    minY = rangeTable.endRowIndex;
                    maxY = rangeTable.beginRowIndex;
                }
                if (rangeTable.endColIndex > rangeTable.beginColIndex) {
                    minX = rangeTable.beginColIndex;
                    maxX = rangeTable.endColIndex;
                } else {
                    minX = rangeTable.endColIndex;
                    maxX = rangeTable.beginColIndex;
                }
                rangeTable.beginRowIndex = minY;
                rangeTable.beginColIndex = minX;
                rangeTable.endRowIndex = maxY;
                rangeTable.endColIndex = maxX;
                var yl = maxY - minY;
                var xl = maxX - minX;
                minX = parseInt(minX);
                minY = parseInt(minY);
                var n, flag = false;
                for (var i = 0; i <= yl; i++) {
                    n = i + minY;
                    for (var j = 0; j <= xl; j++) {
                        var k = minX + j;
                        if (typeof rangeTable.tempTable[n][k] == "string") {
                            if (rangeTable.tempTable[n][k].split(",")[0] < minY) {
                                rangeTable.beginRowIndex = parseInt(rangeTable.tempTable[n][k].split(",")[0]);
                                flag = true;
                            }
                            if (rangeTable.tempTable[n][k].split(",")[1] < minX) {
                                rangeTable.beginColIndex = parseInt(rangeTable.tempTable[n][k].split(",")[1]);
                                flag = true;
                            }
                            if (rangeTable.tempTable[n][k].split(",")[0] > maxY) {
                                rangeTable.endRowIndex = parseInt(rangeTable.tempTable[n][k].split(",")[0]);
                                flag = true;
                            }
                            if (rangeTable.tempTable[n][k].split(",")[1] > maxX) {
                                rangeTable.endColIndex = parseInt(rangeTable.tempTable[n][k].split(",")[1]);
                                flag = true;
                            }
                        } else {
                            if (rangeTable.tempTable[n][k].col > 1) {
                                if ((k + rangeTable.tempTable[n][k].col - 1) > maxX) {
                                    rangeTable.endColIndex = (k + rangeTable.tempTable[n][k].col - 1);
                                    flag = true;
                                }
                            }
                            if (rangeTable.tempTable[n][k].row > 1) {
                                if ((n + rangeTable.tempTable[n][k].row - 1) > maxY) {
                                    rangeTable.endRowIndex = (n + rangeTable.tempTable[n][k].row - 1);
                                    flag = true;
                                }
                            }
                        }
                        if (flag) {
                            break;
                        }

                    }
                }
                if (flag) {
                    this.resetCompute(rangeTable);
                }

            },
            // 清除单元格选中状态
            clearTdRange: function (rangeTable) {
                var classTdList = util.getElementsByClassName(editorContext, "selectTdClass");
                for (var i in classTdList) {
                    styles.unselectTd(classTdList[i]);
                }
                rangeTable.seleTdArray = {};
            },
            // 合并单元格
            mergeCells: function (rangeTable) {
                var nodeValue = constants.EMPTY;
                var firstTd = rangeTable.tempTable[rangeTable.beginRowIndex][rangeTable.beginColIndex].td;
                for (var i = rangeTable.beginRowIndex; i <= rangeTable.endRowIndex; i++) {
                    for (var j = rangeTable.beginColIndex; j <= rangeTable.endColIndex; j++) {
                        if (!rangeTable.tempTable[i][j].td) {
                            continue;
                        }
                        if (rangeTable.tempTable[i][j].td == firstTd) {
                            continue;
                        }
                        if (!(rangeTable.tempTable[i][j].td.childNodes.length == 1 &&
                            util.isBrLabel(rangeTable.tempTable[i][j].td.firstChild))) {
                            util.mergeToOtherNode(rangeTable.tempTable[i][j].td, firstTd);
                        }
                        util.removeNode(rangeTable.tempTable[i][j].td);
                    }
                }
                firstTd.rowSpan = rangeTable.endRowIndex - rangeTable.beginRowIndex + 1;
                firstTd.colSpan = rangeTable.endColIndex - rangeTable.beginColIndex + 1;
                this.getTableInfo(rangeTable.rangeTd);
                resetRange.setStart(util.getMinNode(firstTd), 0);
                resetRange.collapse(true);
            },
            // 拆分单元格
            breakTd: function (rangeTable) {
                var node, tr, td, w, h, index;
                var seleTdArray = rangeTable.seleTdArray;
                if (util.isEmptyObject(seleTdArray)) {
                    seleTdArray[rangeTable.beginRowIndex + "," + rangeTable.beginColIndex] = rangeTable.rangeTd
                }
                for (var i in seleTdArray) {
                    node = seleTdArray[i];
                    tr = node.parentNode;
                    w = seleTdArray[i].colSpan;
                    h = seleTdArray[i].rowSpan;
                    index = i.split(",");
                    if (w > 1 || h > 1) {
                        seleTdArray[i].colSpan = 1;
                        seleTdArray[i].rowSpan = 1;
                        for (var k = 0; k < h; k++) {
                            for (var j = 0; j < w; j++) {
                                if (j == 0 && k == 0) {
                                    continue;
                                }
                                td = util.createCustomNode(constants.TD);
                                if (node) {
                                    if (node.nextSibling) {
                                        tr.insertBefore(td, node.nextSibling);
                                    } else {
                                        tr.appendChild(td);
                                    }
                                } else {
                                    tr.insertBefore(td, tr.firstChild);
                                }
                            }
                            tr = tr.nextSibling;
                            node = this.getBeforeTd(rangeTable.tempTable, parseInt(index[0]) + 1 + k, parseInt(index[1]));
                        }

                    }
                }
                this.getTableInfo(rangeTable.rangeTd);
            },
            // 获取临时表中前一个可用的td
            getBeforeTd: function (table, y, x) {
                if (y >= table.length) {
                    return;
                }
                var node = table[y][x];
                while (node && !node.td && x > -1) {
                    x -= 1;
                    node = table[y][x];
                }
                if (node) {
                    return node.td;
                }
            },
            // 删除整行
            deleteRow: function () {
                var delRow, addRow, addTd, d, delList = [];
                for (var i = rangeTable.beginRowIndex; i <= rangeTable.endRowIndex; i++) {
                    delRow = {}, addRow = {};
                    for (var j = 0; j < rangeTable.tdlength; j++) {
                        if (rangeTable.tempTable[i][j].td) {
                            if (rangeTable.tempTable[i][j].td.rowSpan > 1) {
                                addRow[i + "," + j] = rangeTable.tempTable[i][j].td;
                            }
                        } else {
                            var n = rangeTable.tempTable[i][j].split(",");
                            if (n[0] < i) {
                                delRow[n[0] + "," + n[1]] = rangeTable.tempTable[n[0]][n[1]].td;
                            } else {
                                if (rangeTable.tempTable[n[0]][n[1]].td.rowSpan > 1) {
                                    addRow[n[0] + "," + n[1]] = rangeTable.tempTable[n[0]][n[1]].td;
                                }
                            }
                        }
                    }

                    // 先往下面补充td
                    for (var k in addRow) {
                        addTd = util.createCustomNode(constants.TD);
                        addTd.rowSpan = addRow[k].rowSpan - 1;
                        addTd.colSpan = addRow[k].colSpan;
                        var nextTrNum = parseInt(k.split(",")[0]) + 1;
                        d = this.getBeforeTd(rangeTable.tempTable, nextTrNum, k.split(",")[1]);
                        rangeTable.tempTable[nextTrNum][k.split(",")[1]] = {
                            col: addTd.colSpan,
                            row: addTd.rowSpan,
                            td: addTd
                        };
                        // d不存在代表下一行tr的第一个元素不存在
                        if (d) {
                            util.insertAfter(addTd, d);
                        } else {
                            util.insertBefore(addTd, addRow[k].parentNode.nextSibling.firstChild);
                        }
                        for (var u = 0; u < rangeTable.tempTable.length; u++) {
                            for (var p in rangeTable.tempTable[u]) {
                                if (rangeTable.tempTable[u][p] == k) {
                                    rangeTable.tempTable[u][p] = nextTrNum + "," + k.split(",")[1];
                                }
                            }
                        }
                    }
                    // 然后上面的td行数跨越数减1
                    for (var k in delRow) {
                        if (delRow[k].rowSpan > 1) {
                            delRow[k].rowSpan -= 1;
                        }
                    }
                    delList.push(rangeTable.arrayTr[i]);
                }
                var rangeTd;
                // 获取删除后光标所属单元格位置
                var n = null;
                if ((rangeTable.endRowIndex + 1) < rangeTable.arrayTr.length) {
                    n = rangeTable.tempTable[rangeTable.endRowIndex + 1][rangeTable.beginColIndex];
                } else if (rangeTable.beginRowIndex > 0) {
                    n = rangeTable.tempTable[rangeTable.beginRowIndex - 1][rangeTable.beginColIndex];
                }
                if (n) {
                    if (n.td) {
                        rangeTd = n.td;
                    } else {
                        rangeTd = rangeTable.tempTable[n.split(",")[0]][n.split(",")[1]].td;
                    }
                }
                var delTr;
                // 开始一行一行的删除整行
                while (delTr = delList.pop()) {
                    util.removeNode(delTr);
                }
                // 如果单元格行数被删光了，那么将table也一起删除
                if (util.getElementsByTagName(rangeTable.table, constants.TR).length == 0) {
                    //重置选区
                    if (rangeTable.table.nextSibling) {
                        this.setRangeBefore(util.getMinNode(rangeTable.table.nextSibling));
                    } else {
                        this.setRangeBefore(util.getMinNode(rangeTable.table.previousSibling));
                    }
                    util.removeNode(rangeTable.table);
                    this.resetTable();
                } else {
                    this.getTableInfo(rangeTd);
                    this.setRangeBefore(rangeTd);
                }
            },
            // 删除整列
            deleteCol: function () {
                var rangeTd;
                // 获取删除后光标所属单元格位置
                var n;
                if ((rangeTable.endColIndex + 1) < rangeTable.tdlength) {
                    n = rangeTable.tempTable[rangeTable.beginRowIndex][rangeTable.endColIndex + 1];
                } else if (rangeTable.beginColIndex > 0) {
                    n = rangeTable.tempTable[rangeTable.beginRowIndex][rangeTable.beginColIndex - 1];
                }
                if (n) {
                    if (n.td) {
                        rangeTd = n.td;
                    } else {
                        rangeTd = rangeTable.tempTable[n.split(",")[0]][n.split(",")[1]].td;
                    }
                }
                var delCol;
                for (var i = rangeTable.beginColIndex; i <= rangeTable.endColIndex; i++) {
                    delCol = {};
                    for (var j = 0; j < rangeTable.arrayTr.length; j++) {
                        if (rangeTable.tempTable[j][i].td) {
                            if (rangeTable.tempTable[j][i].td.colSpan > 1) {
                                delCol[j + "," + i] = rangeTable.tempTable[j][i].td;
                            } else {
                                util.removeNode(rangeTable.tempTable[j][i].td);
                            }
                        } else {
                            var n = rangeTable.tempTable[j][i].split(",");
                            delCol[n[0] + "," + n[1]] = rangeTable.tempTable[n[0]][n[1]].td;
                        }
                    }
                    for (var k in delCol) {
                        if (delCol[k].colSpan > 1) {
                            delCol[k].colSpan -= 1;
                        } else {
                            util.removeNode(delCol[k]);
                        }
                    }
                }
                if (util.getElementsByTagName(rangeTable.table, constants.TD).length == 0) {
                    //重置选区
                    if (rangeTable.table.nextSibling) {
                        this.setRangeBefore(util.getMinNode(rangeTable.table.nextSibling));
                    } else {
                        this.setRangeBefore(util.getMinNode(rangeTable.table.previousSibling));
                    }
                    util.removeNode(rangeTable.table);
                    this.resetTable();
                } else {
                    this.getTableInfo(rangeTd);
                    this.setRangeBefore(rangeTd);
                }
            },
            // 插入整行单元格
            insertRow: function (node) {
                var t1 = util.getSpecalParentNode(constants.TR, node);
                var trindex;
                for (var i = 0; i < rangeTable.arrayTr.length; i++) {
                    if (t1 == rangeTable.arrayTr[i]) {
                        trindex = i + 1;
                        break;
                    }
                }
                var t2 = rangeTable.arrayTr[trindex], num = 0;
                var tempTd, needModiNode = {};
                var l = rangeTable.tdlength;
                if (t2) {
                    tempTd = rangeTable.tempTable[trindex][num];
                    while (tempTd) {
                        if (!tempTd.td) {
                            needModiNode[trindex + "," + num] = tempTd;
                        }
                        num++;
                        tempTd = rangeTable.tempTable[trindex][num];
                    }
                    var tempIndex1, tempIndex2, addlen = 0, modiTd = {};
                    for (var i in needModiNode) {
                        tempIndex1 = needModiNode[i].split(",");
                        tempIndex2 = i.split(",");
                        if (tempIndex1[0] < tempIndex2[0]) {
                            addlen++;
                            modiTd[needModiNode[i]] = true;
                        }

                    }
                    for (var i in modiTd) {
                        rangeTable.tempTable[i.split(",")[0]][i.split(",")[1]].td.rowSpan += 1;
                    }
                    l = rangeTable.tdlength - addlen;
                }
                var td, tr = util.createCustomNode(constants.TR);
                for (var i = 0; i < l; i++) {
                    td = util.createCustomNode(constants.TD);
                    tr.appendChild(td);
                }
                if (t2) {
                    util.insertBefore(tr, t2);
                } else {
                    t1.parentNode.appendChild(tr);
                }
                this.getTableInfo(rangeTable.rangeTd);
                // 隔行变色的处理
                if (rangeTable.table.colorChange) {
                    for (var i = 0; i < rangeTable.arrayTr.length; i++) {
                        if (i % 2 == 0) {
                            styles.setTrColorStyle(rangeTable.arrayTr[i]);
                        } else {
                            styles.setTrStyle(rangeTable.arrayTr[i]);
                        }
                    }
                }
            },
            // 插入整列单元格
            insertCol: function () {
                var index = rangeTable.endColIndex, addCol = {}, tdindex;
                var maxTdLength = rangeTable.tdlength;
                var td;
                // 针对是最后一列的情况
                if (index == (maxTdLength - 1)) {
                    for (var i = 0; i < rangeTable.arrayTr.length; i++) {
                        td = util.createCustomNode(constants.TD)
                        rangeTable.arrayTr[i].appendChild(td);
                    }
                } else {
                    index += 1;
                    for (var i = 0; i < rangeTable.arrayTr.length; i++) {
                        if (rangeTable.tempTable[i][index].td) {
                            td = util.createCustomNode(constants.TD);
                            rangeTable.arrayTr[i].insertBefore(td, rangeTable.tempTable[i][index].td);
                        } else {
                            tdindex = rangeTable.tempTable[i][index].split(",");
                            if (tdindex[1] < index) {
                                addCol[tdindex[0] + "," + tdindex[1]] = rangeTable.tempTable[tdindex[0]][tdindex[1]];
                            } else {
                                td = util.createCustomNode(constants.TD);
                                rangeTable.arrayTr[i].insertBefore(td, rangeTable.tempTable[i][index].td);
                            }
                        }
                    }
                }
                for (var i in addCol) {
                    addCol[i].td.colSpan += 1;
                }
                this.getTableInfo(rangeTable.rangeTd);
                // 重置td的宽度
                var w = Math.ceil(100 / rangeTable.arrayTr[0].childElementCount);
                for (var i = 0; i < rangeTable.arrayTr[0].childElementCount; i++) {
                    styles.setTdReWidth(rangeTable.arrayTr[0].children[i], w);
                }
            },
// ----------------------------------------------表格设置开始-----------------------------------------
// ----------------------------------------------段落操作开始-----------------------------------------
            // 对段落进行操作
            setParaOper: function (style) {
                var parentArray = util.getOutNode(this.getRange());
                var styleArray = style.split(";"), childArray;
                // 分割后将最后的空元素剔除掉
                if (styleArray[styleArray.length - 1] == constants.EMPTY) {
                    styleArray.pop();
                }
                for (var i in parentArray) {
                    for (var j in styleArray) {
                        childArray = styleArray[j].split(":");
                        styles.setSpecalStyleValue(parentArray[i], childArray[0], childArray[1]);
                    }
                }
            },
            // 删除段落指定样式
            recoverParaOper: function (styleName) {
                var parentArray = util.getOutNode(this.getRange());
                for (var i in parentArray) {
                    styles.delStyleCSS(parentArray[i], styleName);
                }
            },
// ----------------------------------------------段落操作结束-----------------------------------------

// ----------------------------------------------标题设置开始-----------------------------------------
            setTextTitle: function (titleTag) {
                // 如果是表格不进行设置
                if (!util.isEmptyObject(rangeTable.seleTdArray)) {
                    return;
                }
                var range = this.getRange();
                if (range.collapsed) {
                    var startNode;
                    var startOffset = range.startOffset;
                    var parentNode = util.getRangeOutParentNode(range.startContainer, range, 1);
                    var newNode = util.createCustomNode(titleTag);
                    if (util.isOutNodeCheck(range.startContainer)) {
                        startNode = newNode;
                    } else {
                        startNode = range.startContainer;
                    }
                    // 将当前节点移动到新的节点中
                    util.mergeToOtherNode(parentNode, newNode);
                    util.insertBefore(newNode, parentNode);
                    util.removeNode(parentNode);
                    resetRange.setStart(startNode, startOffset);
                    resetRange.collapse(true);
                } else {
                    var parentNode, eparentNode,
                        snode = range.startContainer,
                        enode = range.endContainer;
                    var sOffset = range.startOffset;
                    var eOffset = range.endOffset;
                    var rangeSnode = null, rangeEnode = null;
                    if (enode.id) {
                        enode = enode.childNodes[eOffset - 1];
                        eOffset = enode.childElementCount;
                    }
                    // 获取开始节点的选区
                    if (util.isTextNode(snode)) {
                        rangeSnode = snode;
                    } else {
                        util.insertBefore(stMark, snode.childNodes[sOffset]);
                    }
                    // 获取结束节点的选区
                    if (util.isTextNode(enode)) {
                        rangeEnode = enode;
                    } else {
                        if (eOffset > 0) {
                            util.insertAfter(enMark, enode.childNodes[eOffset - 1]);
                        } else {
                            util.insertBefore(enMark, enode.childNodes[eOffset]);
                        }
                    }
                    parentNode = util.getRangeOutParentNode(snode, range, 1);
                    eparentNode = util.getRangeOutParentNode(enode, range, 2);
                    var flag = false, newNode;
                    while (parentNode) {
                        if (parentNode == eparentNode) {
                            flag = true;
                        }
                        if (!util.checkIstouNode(parentNode)) {
                            if (parentNode.tagName != titleTag.toUpperCase()) {
                                newNode = util.createCustomNode(titleTag);
                                // 将当前节点移动到新的节点中
                                util.mergeToOtherNode(parentNode, newNode);
                                util.insertBefore(newNode, parentNode);
                                util.removeNode(parentNode);
                                parentNode = newNode.nextSibling;
                            } else {
                                parentNode = parentNode.nextSibling;
                            }
                        } else {
                            parentNode = parentNode.nextSibling;
                        }
                        if (flag) {
                            break;
                        }
                    }
                    if (rangeSnode) {
                        resetRange.setStart(rangeSnode, sOffset);
                    } else {
                        resetRange.setStartAfter(stMark);
                        util.remove(stMark);
                    }
                    if (rangeEnode) {
                        resetRange.setEnd(rangeEnode, eOffset);
                    } else {
                        resetRange.setEndBefore(enMark);
                        util.remove(enMark);
                    }
                }
            },
// ----------------------------------------------标题设置结束-----------------------------------------

// ----------------------------------------------代码格式化开始-----------------------------------------
            /**
             * 将p标签转换成div节点
             */
            changePtoDiv: function (dealNode) {
                var pLis = util.getElementsByTagName(dealNode, constants.P);
                var tempd;
                // 先将所有p标签替换成div标签
                for (var i in pLis) {
                    // 在pre标签中的节点不进行替换
                    if (!util.getSpecalParentNode(constants.PRE, pLis[i])) {
                        tempd = util.createCustomNode(constants.DIV);
                        if (pLis[i].getAttribute("style")) {
                            tempd.setAttribute("style", pLis[i].getAttribute("style"));
                        }
                        util.insertBefore(tempd, pLis[i]);
                        util.changeChildNode(pLis[i], tempd);
                        util.removeNode(pLis[i]);
                    }
                }
            },
            /**
             * 遍历所有元素节点，去除多余的样式、去除空节点，为图片添加事件、去除没样式的span标签
             * @param sNode
             * @param eNode
             */
            clearStyleEmptyNode: function (sNode, eNode) {
                util.forListNode(sNode, eNode, function (node) {
                    // 去除一些不知名的标签
                    if (util.indexOf(sysConfig.childIncludeNode, node.tagName) == -1) {
                        return service.delUnNeedNode(node);
                    }
                    styles.decodeClear(node);
                    if (util.checkIsEmpty(node)) {
                        // 如果当前节点是空节点，那么直接进行删除
                        while (util.checkIsEmpty(node)) {
                            node = service.delUnNeedNode(node);
                        }
                        return node;
                    }
                    if (node.tagName == constants.IMG) {
                        // 对于word中的图片，那么提示用户手动去替换
                        if (/^(?:(file:\/+))/.test(node.src)) {
                            var img = util.createCustomNode(constants.IMG);
                            img.src = userConfig.url + "image/wordpic.png";
                            styles.setBorderSolid(img);
                            util.insertBefore(img, node);
                            util.removeNode(node);
                            node = img;
                        }
                    }
                    // 对于没有样式的span标签需要进行删除处理
                    if (util.checkStrIsEmpty(node.style.cssText) && node.tagName == constants.SPAN) {
                        return service.delUnNeedNode(node);
                    }
                    if (node.id) {
                        if (node.id.startWith("njqEditor")) {
                            return;
                        }
                        node.removeAttribute("id");
                    }
                    return node;
                }, 1);
            },
            /**
             * 剥离出所有的文本节点，每个原外置节点之间用br间隔
             * @param sNode
             * @param eNode
             */
            peelAllTextNode: function (sNode, eNode) {
                var divNode = util.createCustomNode(constants.DIV);
                var tempBr;
                util.forListNode(sNode, eNode, function (node) {
                    if (node.nodeType == 1 && util.isOutNodeCheck(node)) {
                        if (node.childNodes.length > 0) {
                            util.insertBefore(enMark, node.firstChild);
                        } else {
                            util.insertAfter(enMark, node);
                        }
                        if (!(util.isBrLabel(divNode.lastChild) && util.isBrLabel(divNode.lastChild.previousSibling))) {
                            tempBr = util.createCustomNode(constants.BR);
                            divNode.appendChild(tempBr);
                        }
                    } else if (node.tagName == constants.TR || node.tagName == constants.LI) {
                        if (!(util.isBrLabel(divNode.lastChild) && util.isBrLabel(divNode.lastChild.previousSibling))) {
                            tempBr = util.createCustomNode(constants.BR);
                            divNode.appendChild(tempBr);
                        }
                        return node;
                    } else if (node.tagName == constants.TD) {
                        if (tempBr) {
                            util.insertBefore(util.createTextNode("    "), tempBr);
                        } else {
                            divNode.appendChild(util.createTextNode("    "));
                        }
                        return node;
                    } else if (node.nodeType == 3 || util.isBrLabel(node)) {
                        util.insertAfter(enMark, node);
                        if (tempBr) {
                            util.insertBefore(node, tempBr);
                        } else {
                            divNode.appendChild(node);
                        }
                    } else {
                        return node;
                    }
                    return enMark;
                }, -1);
                return divNode;
            },
            /**
             * 将外置节点内的外置节点提取出来进行并列排放
             */
            dealOutNode: function (cnode, enode) {
                var parentNode;
                var flag = false, beforeNode, divNode, customRange, exNode;
                util.forListNode(cnode, enode, function (node) {
                    // 去除一些不知名的标签
                    if (node.nodeType == 1 && util.indexOf(sysConfig.childIncludeNode, node.tagName) == -1) {
                        return service.delUnNeedNode(node);
                    }
                    //从word中复制过来的图片标签
                    if (node.tagName == "V:IMAGEDATA") {
                        var img = util.createCustomNode(constants.IMG);
                        img.src = userConfig.url + "image/wordpic.png";
                        styles.setBorderSolid(img);
                        util.insertBefore(img, node);
                        util.removeNode(node);
                        node = img;
                    }
                    if (util.checkIsEmpty(node)) {
                        return util.removeNode(node);
                    }
                    if (node.id) {
                        if (node.id.startWith("njqEditor")) {
                            return;
                        }
                        node.removeAttribute("id");
                    }
                    if (util.isOutNodeCheck(node)) {
                        //复制进来的pre会自带一些样式
                        if (util.checkIsPreNode(node)) {
                            styles.removePreStyle(node);
                        }
                        if (parentNode && (!parentNode.contains(node))) {
                            flag = false;
                        }
                        if (flag) {
                            customRange = util.createRange();
                            customRange.setStartAfter(node);
                            customRange.setEndAfter(parentNode);
                            exNode = util.extractContents(customRange);
                            util.insertAfter(exNode, parentNode);
                            util.insertAfter(node, parentNode);
                            util.deleteAllChildrenEmptyNodes(parentNode, true);
                            flag = false;
                            node.newNode = true;
                        } else {
                            flag = true;
                            parentNode = node;
                            if (util.checkIstouNode(node)) {
                                //将内部的外置标签全转换成span
                                service.decodeDealTouNode(node);
                                node.checkNext = true;
                            }
                        }
                    } else {
                        if ((!parentNode) || (!parentNode.contains(node))) {
                            flag = false;
                            beforeNode = node.previousSibling;
                            // 对于非外标签节点直接扔进上一个div外标签节点中
                            if (beforeNode && beforeNode.parentLabel && beforeNode.tagName == constants.DIV) {
                                beforeNode.appendChild(node);
                                beforeNode.checkNext = true;
                                return beforeNode;
                            } else {
                                // 若没有外标签，那么创建一个div标签来包此标签
                                divNode = util.createCustomNode(constants.DIV);
                                divNode.checkNext = true;
                                util.insertBefore(divNode, node);
                                divNode.appendChild(node);
                                if (util.checkIsEmpty(divNode)) {
                                    return util.removeNode(divNode);
                                } else {
                                    divNode.parentLabel = true;
                                }
                                return divNode;
                            }
                        }
                    }
                    return node;
                }, -1);
            },
            /**
             * 检查所有的外置标签，并加上记号（保证最外层没有不是外标签的节点）
             */
            dealInfoOutNode: function (node) {
                var afterNode = node;
                var beforeNode, divNode;
                while (afterNode) {
                    afterNode = node.nextSibling;
                    beforeNode = node.previousSibling;
                    // 全是空格或者空的节点直接删除
                    if (node.nodeType == 3 && util.trim(node.data) == constants.EMPTY) {
                        util.removeNode(node);
                        node = afterNode;
                        continue;
                    }
                    if (util.isOutNodeCheck(node)) {
                        // 对于外标签节点加个属性即可
                        node.parentLabel = true;
                    } else {
                        // 对于非外标签节点直接扔进上一个div外标签节点中
                        if (beforeNode && beforeNode.parentLabel && beforeNode.tagName == constants.DIV) {
                            beforeNode.appendChild(node);
                        } else {
                            // 若没有外标签，那么创建一个div标签来包此标签
                            divNode = util.createCustomNode(constants.DIV);
                            util.insertBefore(divNode, node);
                            divNode.appendChild(node);
                            if (util.checkIsEmpty(divNode)) {
                                util.removeNode(divNode);
                            } else {
                                divNode.parentLabel = true;
                            }
                        }
                    }
                    node = afterNode;
                }
            },
            // 格式化
            decodeService: function (node) {
                var editorContext = ids.editorContext;
                //进行处理先准备
                var styleNodeList = util.getElementsByTagName(editorContext, constants.STYLE);
                for (var i in styleNodeList) {
                    util.remove(styleNodeList[i]);
                }

                //第一步将p标签替换成div标签
                this.changePtoDiv(editorContext);

                //第二步先检查所有的外置标签，并加上记号（保证最外层没有不是外标签的节点）
                this.dealOutNode(editorContext.firstChild, editorContext);

                //第三步将外标签中包含的pre标签要提出来到外面
                var tempd, preLis = util.getElementsByTagName(editorContext, constants.PRE);
                for (var i = 0; i < preLis.length; i++) {
                    tempd = util.getOutParentNode(preLis[i]);
                    if (tempd) {
                        util.insertAfter(preLis[i], tempd);
                    }
                }

                //遍历所有元素节点，去除多余的样式、去除空节点，为图片添加事件、去除没样式的span标签
                this.clearStyleEmptyNode(editorContext.firstChild, editorContext);

                // 在最后一个位置中添加一个节点
                if (!(editorContext.lastChild.tagName == constants.DIV
                    && editorContext.lastChild.childNodes.length == 1
                    && editorContext.lastChild.firstChild.tagName == constants.BR)) {
                    editorContext.appendChild(util.createEmptyNode(constants.DIV));
                }

                util.forListNode(editorContext.firstChild, editorContext, function (node) {
                    return util.mergePreNode(node);
                }, 1);
                this.dealInnerImgs();
            },
            /**
             * 编辑器中所有图片进行绑定
             */
            dealInnerImgs: function () {
                var imgs = util.getElementsByTagName(editorContext, "img");
                for (var i = 0; i < imgs.length; i++) {
                    util.addCommonEventListener(imgs[i], "click", "_seleImg", 5);
                }
                if (userConfig.pic.upType == 2) {
                    upPicToServer();
                }
            },
            /**
             * 将节点中的外置节点移动到外面
             */
            moveOutDiv: function (fnode, enode) {
                var flag = false, temp, exNode, tempArrays;
                while (fnode) {
                    if (fnode == enode) {
                        flag = true;
                    }
                    if (util.checkIstouNode(fnode)) {
                        //将内部的外置标签全转换成span
                        this.decodeDealTouNode(fnode);
                    } else {
                        this.decodeOutNode(fnode);
                    }
                    if (flag) {
                        break;
                    }
                    fnode = fnode.nextSibling;
                }
            },
            //针对在table中的外置标签场景是将这些外置标签的节点转换成span标签
            decodeDealTouNode: function (fnode) {
                var spanNode, tempArrays, nodeStyle, temp;
                var emptyNodeList, liList, tdList;
                if (fnode.tagName == constants.TABLE) {
                    //把th标签转换成td
                    var thList = util.getElementsByTagName(fnode, constants.TH);
                    var td = util.createCustomNode(constants.TD), tnode;
                    for (var i in thList) {
                        tnode = td.cloneNode();
                        tnode.style.cssText = thList[i].style.cssText;
                        tnode.colSpan = thList[i].colSpan;
                        tnode.rowSpan = thList[i].rowSpan;
                        util.insertBefore(tnode, thList[i]);
                        util.changeChildNode(thList[i], tnode);
                        util.remove(thList[i]);
                    }
                    emptyNodeList = util.getElementsByTagName(fnode, constants.TD);
                    util.emptyNodeListAddBr(emptyNodeList);
                }

                if (fnode.tagName == constants.OL || fnode.tagName == constants.UL) {
                    //对于不在li标签内的节点，要移除到外面
                    var tnode = fnode.firstChild, tempNode, textNode = fnode;
                    while (tnode) {
                        tempNode = tnode.nextSibling;
                        if (tnode.tagName != constants.LI) {
                            util.insertAfter(tnode, textNode);
                            textNode = tnode;
                        }
                        tnode = tempNode;
                    }
                    emptyNodeList = util.getElementsByTagName(fnode, constants.LI);
                    util.emptyNodeListAddBr(emptyNodeList);
                }
                //在table、Ol、Ul中的外置标签不移动只替换成span节点
                for (var i in parentIncludeNode) {
                    //暂不处理pre标签
                    if (parentIncludeNode[i] == constants.PRE) {
                        continue;
                    }
                    tempArrays = util.getElementsByTagName(fnode, parentIncludeNode[i]);
                    if (tempArrays.length > 0) {
                        for (var j in tempArrays) {
                            temp = tempArrays[j];
                            spanNode = util.createCustomNode(constants.SPAN);
                            nodeStyle = temp.getAttribute("style");
                            if (nodeStyle != null && nodeStyle != constants.EMPTY) {
                                spanNode.setAttribute("style", nodeStyle);
                            }
                            util.insertBefore(spanNode, temp);
                            util.insertBefore(util.createCustomNode(constants.BR), temp);
                            util.mergeToOtherNode(temp, spanNode);

                            liList = util.getElementsByTagName(spanNode, constants.LI);
                            for (var k in liList) {
                                util.insertAfter(util.createCustomNode(constants.BR), liList[k]);
                                util.moveAllChildNodeToOut(liList[k]);
                            }
                            tdList = util.getElementsByTagName(spanNode, constants.TD);
                            for (var k in tdList) {
                                util.moveAllChildNodeToOut(tdList[k]);
                            }
                            util.removeNode(temp);
                        }
                    }
                }
                //针对table、ul、ol中的图片处理
                var imgs = util.getElementsByTagName(fnode, "V:IMAGEDATA");
                for (var i in imgs) {
                    var img = util.createCustomNode(constants.IMG);
                    img.src = userConfig.url + "image/wordpic.png";
                    styles.setBorderSolid(img);
                    util.insertBefore(img, imgs[i]);
                    util.removeNode(imgs[i]);
                }
                imgs = util.getElementsByTagName(fnode, "IMG");
                for (var i in imgs) {
                    if (/^(?:(file:\/+))/.test(imgs[i].src)) {
                        var img = util.createCustomNode(constants.IMG);
                        img.src = userConfig.url + "image/wordpic.png";
                        styles.setBorderSolid(img);
                        util.insertBefore(img, imgs[i]);
                        util.removeNode(imgs[i]);
                    }
                }

            },
            //针对在正常外置标签之间的场景，内部的外置节点放在和父节点进行并列
            decodeOutNode: function (fnode) {
                var exNode, tempArrays, temp, customRange;
                var emptyNodeList;
                //外置标签内的外置标签要移动到下面
                for (var i in parentIncludeNode) {
                    tempArrays = util.getElementsByTagName(fnode, parentIncludeNode[i]);
                    if (tempArrays.length > 0) {
                        for (var j in tempArrays) {
                            temp = tempArrays[j];
                            customRange = util.createRange();
                            customRange.setStartAfter(temp);
                            customRange.setEndAfter(fnode);
                            exNode = util.extractContents(customRange);
                            util.deleteAllChildrenEmptyNodes(temp);
                            util.insertAfter(exNode, fnode);
                            fnode = fnode.nextSibling;
                            if (!util.checkIsEmpty(temp)) {
                                temp.parentLabel = true;
                                util.insertBefore(temp, fnode);
                            }
                        }
                    }
                }
                return fnode;
            },
            // 删除一些不需要的节点
            delUnNeedNode: function (node) {
                var fnode;
                if (node.firstChild) {
                    util.moveAllChildNodeToOut(node, 1);
                    fnode = node.nextSibling;
                    if (!fnode) {
                        fnode = util.getNextNode(node);
                    }
                    fnode.newNode = true;
                } else if (node.previousSibling) {
                    fnode = node.previousSibling;
                } else if (node.nextSibling) {
                    fnode = node.nextSibling;
                    fnode.newNode = true;
                } else {
                    fnode = node.parentNode;
                }
                util.removeNode(node);
                return fnode;
            },
            /**
             * 判断节点下所有span属性，如果属性为空则剔除节点 node:父节点
             */
            labelCheckAndTran: function (node) {
                var nodeList = util.getElementsByTagName(node, constants.SPAN);
                for (var i = 0; i < nodeList.length;) {
                    if (!nodeList[i].getAttribute("id") && !node.getAttribute("style") && !node.getAttribute("class")) {
                        util.moveAllChildNodeToOut(nodeList[i]);
                    } else {
                        ++i;
                    }
                }
            },
            //粘贴前处理
            beforePaste: function (decodeNode) {
                //粘贴代码的时候会出现内部全是id相同的节点，需要进行替换
                var sameIdNodes = decodeNode.getElementById(decodeNode.id);
                if (sameIdNodes) {
                    for (var i = 0; i < sameIdNodes.length; i++) {
                        var div = util.createCustomNode(constants.DIV);
                        div.innerHTML = sameIdNodes[i].innerHTML;
                        util.insertBefore(div, sameIdNodes[i]);
                        sameIdNodes[i].remove();
                    }
                }
                //把一些回车符等都删除掉
                decodeNode.innerHTML = decodeNode.innerHTML.replace(/^\s+|\s+$/gm, '').replace(/[\r\n\t]/g, constants.EMPTY);
                //进行处理先准备(粘贴的头部可能带有style标签)
                var styleNodeList = util.getElementsByTagName(decodeNode, constants.STYLE);
                for (var i in styleNodeList) {
                    util.remove(styleNodeList[i]);
                }
                if(userConfig.parseTextType == 2){
                	util.forListNode(decodeNode.firstChild, decodeNode.lastChild, this.clearDecodefun(), 1);
                }
                //先将p节点替换成div节点
                this.changePtoDiv(decodeNode);
            },
            //格式化粘贴内容
            decodePaste: function (decodeNode) {
                this.beforePaste(decodeNode);
                //如果没有内容粘贴过来，直接返回
                if (decodeNode.innerHTML == constants.SPACE) {
                    if (stMark.parentNode.childNodes.length == 1) {
                        util.insertAfter(util.createCustomNode(constants.BR), stMark);
                    }
                    resetRange.setStartAfter(stMark);
                    resetRange.collapse(true);
                    stMark.remove();
                    this.clearAndAddNewRange(resetRange);
                    return;
                }
                //对内部的节点归并到外置节点中
                this.dealOutNode(decodeNode.firstChild, decodeNode);

                //处理样式
                this.clearStyleEmptyNode(decodeNode.firstChild, decodeNode.lastChild);

                var parentNode = util.getOutParentNode(stMark);
                var flag = true, imgs = [];
                //针对pre节点的特殊处理
                if (util.checkIsPreNode(parentNode)) {
                    var divNode = this.peelAllTextNode(decodeNode.firstChild, decodeNode);
                    if (util.isBrLabel(divNode.firstChild)) {
                        util.remove(divNode.firstChild);
                    }
                    if (stMark.nextSibling && util.isTextNode(stMark.nextSibling)
                        && util.isBrLabel(divNode.lastChild)) {
                        util.remove(divNode.lastChild);
                    }
                    util.insertAfter(enMark, stMark);
                    util.moveToNodeAfter(divNode, stMark);
                    flag = false;
                }

                //针对在table中的特殊处理
                if (flag && parentNode.tagName == constants.TABLE) {
                    this.dealTabel(decodeNode);
                    imgs = util.getElementsByTagName(decodeNode, "img");
                    util.insertAfter(enMark, stMark);
                    util.moveToNodeAfter(decodeNode, stMark);
                    flag = false;
                }
                //针对在ul或ol中的特殊处理
                if (flag && (parentNode.tagName == constants.OL || parentNode.tagName == constants.UL)) {
                    this.dealOl(decodeNode);
                    imgs = util.getElementsByTagName(decodeNode, "img");
                    util.insertAfter(enMark, stMark);
                    var liNode = util.getSpecalParentNode(constants.LI, stMark);
                    if (liNode) {
                        if (decodeNode.childNodes.length > 1) {
                            var customRange = util.createRange();
                            customRange.setStartAfter(stMark);
                            customRange.setEndAfter(liNode);
                            var exNode = util.extractContents(customRange);
                            util.insertAfter(exNode, liNode);
                            util.deleteAllChildrenEmptyNodes(exNode.previousSibling, true);
                        }
                        //如果开始标签在li里面，那么将第一个li进行合并
                        if (decodeNode.firstChild.childNodes.length == 1 &&
                            decodeNode.firstChild.firstChild.tagName == stMark.parentNode.tagName) {
                            util.mergeStyle(stMark.parentNode, decodeNode.firstChild.firstChild)
                            util.moveToNodeAfter(decodeNode.firstChild.firstChild, stMark);
                        } else {
                            util.moveToNodeAfter(decodeNode.firstChild, stMark);
                        }
                        util.removeNode(decodeNode.firstChild);
                        util.moveToNodeAfter(decodeNode, liNode);
                    } else {
                        util.moveToNodeAfter(decodeNode, stMark);
                    }
                    flag = false;
                }
                if (flag) {
                    var node = util.getOutParentNode(stMark);
                    imgs = util.getElementsByTagName(decodeNode, "img");
                    var customRange = util.createRange();
                    customRange.setStartAfter(stMark);
                    if (decodeNode.childNodes.length == 1 &&
                        decodeNode.firstChild.tagName == constants.DIV) {
                        customRange.setEndAfter(node.lastChild);
                        util.insertAfter(enMark, node.lastChild);
                        var exNode = util.extractContents(customRange);
                        util.insertAfter(exNode, enMark);
                        util.moveToNodeBefore(decodeNode.firstChild, enMark);
                    } else {
                        if (stMark.nextSibling) {
                            util.insertAfter(enMark, node);
                            customRange.setEndAfter(node);
                            var exNode = util.extractContents(customRange);
                            util.insertAfter(exNode, enMark);
                        } else {
                            util.insertAfter(enMark, node);
                        }
                        //第一个外置标签内的节点转移到光标所在的外置节点中
                        if (!util.checkIstouNode(decodeNode.firstChild)) {
                            util.moveToNodeAfter(decodeNode.firstChild, stMark);
                            util.remove(decodeNode.firstChild);
                        }
                        util.moveToNodeAfter(decodeNode, node);
                    }
                    flag = false;
                }
                //粘贴后让选取默认选中复制的内容
//                resetRange.setStartAfter(stMark);
                resetRange.setStartBefore(enMark);
                resetRange.setEndBefore(enMark);
                if (stMark.parentNode.childNodes.length == 1) {
                    util.remove(stMark.parentNode);
                } else {
                    util.remove(stMark);
                }
                util.deleteAllChildrenEmptyNodes(node);
                util.deleteAllChildrenEmptyNodes(enMark.nextSibling);
                util.remove(enMark);
                decodeNode.innerHTML = null;
                this.clearAndAddNewRange(resetRange);
                // 重置按钮状态
                service.resetBtnStatus();
                decodeNode.removeAttribute("contenteditable");
                this.decodeDealImg(imgs);
                // 在最后一个位置中添加一个节点
                if (!(editorContext.lastChild.tagName == constants.DIV
                    && editorContext.lastChild.childNodes.length == 1
                    && editorContext.lastChild.firstChild.tagName == constants.BR)) {
                    editorContext.appendChild(util.createEmptyNode(constants.DIV));
                }

                return;
            },
            //图片加点击事件
            decodeDealImg: function (imgs) {
                for (var i = 0; i < imgs.length; i++) {
                    util.addCommonEventListener(imgs[i], "click", "_seleImg", 5);
                }
                if (userConfig.pic.upType == 2) {
                    upPicToServer();
                }
            },
            dealOl: function (fnode) {
                var tempNode = fnode.firstElementChild;
                var tds;
                var li = util.createCustomNode(constants.LI);
                var templi;
                while (tempNode) {
                    if (tempNode.tagName == constants.TABLE || tempNode.tagName == constants.PRE) {
                        templi = li.cloneNode();
                        util.insertBefore(templi, tempNode);
                        templi.appendChild(tempNode);
                        tempNode = templi;
                    } else if (tempNode.tagName == constants.OL || tempNode.tagName == constants.UL) {
                        util.moveToNodeBefore(tempNode, tempNode);
                    } else {
                        templi = li.cloneNode();
                        util.insertBefore(templi, tempNode);
                        util.changeChildNode(tempNode, templi);
                    }
                    if (tempNode.nextSibling) {
                        tempNode = tempNode.nextSibling;
                        if (util.checkIsEmpty(tempNode.previousSibling)) {
                            util.removeNode(tempNode.previousSibling);
                        }
                    } else {
                        if (util.checkIsEmpty(tempNode)) {
                            util.removeNode(tempNode);
                        }
                        break;
                    }
                }
            },
            dealTabel: function (fnode) {
                var tempNode = fnode.firstElementChild;
                var tds;
                while (tempNode) {
                    if (tempNode.tagName == constants.TABLE) {
                        tds = util.getElementsByTagName(tempNode, constants.TD);
                        var ths = util.getElementsByTagName(fnode, constants.TH);
                        tds.concat(ths);
                    } else if (tempNode.tagName == constants.OL || tempNode.tagName == constants.UL) {
                        tds = tempNode.getElementsByTagName(constants.LI);
                    } else {
                        util.moveToNodeBefore(tempNode, tempNode);
                    }
                    if (tds) {
                        for (var i = 0; i < tds.length; i++) {
                            util.moveToNodeBefore(tds[i], tempNode);
                        }

                    }
                    util.insertBefore(util.createCustomNode(constants.BR), tempNode);
                    if (tempNode.nextSibling) {
                        tempNode = tempNode.nextSibling;
                        util.removeNode(tempNode.previousSibling);
                    } else {
                        util.removeNode(tempNode);
                        break;
                    }
                }
            },
            //代码区的粘贴事件
            codePaste: function (decodeNode) {
                this.beforePaste(decodeNode);
                if (decodeNode.childNodes.length == 1 && util.isTextNode(decodeNode.firstChild)) {
                    util.moveToNodeAfter(decodeNode, stMark);
                } else {
                    var divNode = this.peelAllTextNode(decodeNode.firstChild, decodeNode);
                    if (util.isBrLabel(divNode.lastChild)) {
                        util.remove(divNode.lastChild);
                    }
                    util.moveToNodeAfter(divNode, stMark);
                }
            },
// ----------------------------------------------代码格式化结束-----------------------------------------


// ----------------------------------------------历史记录功能开始-----------------------------------------
            /**
             * 记录到历史记录中
             */
            sethistroy: function (range) {
                var backBtn = ids.editorBack;
                var goBtn = ids.editorGo;
                //之后后退或全进按钮全可用，才能使用历史记录
                if (!(backBtn && goBtn)) {
                    return;
                }
                var context = editorContext.innerHTML;
                var his = {
                    range: {}
                };
                if (njqHistory.historyIndex > 0) {
                    // 内容相同不加入到历史记录中
                    if (njqHistory.list[njqHistory.historyIndex].context == context) {
                        // ，选区相同的内容
                        njqHistory.list.splice(njqHistory.historyIndex + 1, njqHistory.maxLength - njqHistory.historyIndex - 1);
                        tempVar.saveFlag = false;
                        if (goBtn.isEnable) {
                            styles.btnDisable(goBtn);
                            goBtn.isEnable = false;
                        }
                        return;
                    }
                }
                // 生成选区位置
                his.range.startNode = this.getRangeTagAddress(range, true);
                his.range.endNode = this.getRangeTagAddress(range, false);
                if (range.startContainer == range.endContainer && range.startOffset == range.endOffset) {
                    his.range.collapsed = true;
                    his.range.startOffset = range.startOffset;
                    his.range.endOffset = range.endOffset;
                } else {
                    his.range.collapsed = false;
                    his.range.startOffset = this.getRangePlace(range, true);
                    his.range.endOffset = this.getRangePlace(range, false);
                }
                his.context = context;
                if (njqHistory.list.length >= njqHistory.maxLength) {
                    if (njqHistory.historyIndex == (njqHistory.maxLength - 1)) {
                        njqHistory.list.shift();
                    } else {
                        njqHistory.list.splice(njqHistory.historyIndex + 1, njqHistory.maxLength - njqHistory.historyIndex - 1);
                        njqHistory.historyIndex += 1;
                    }
                } else if (njqHistory.historyIndex < (njqHistory.list.length - 1)) {
                    njqHistory.list.splice(njqHistory.historyIndex + 1, njqHistory.maxLength - njqHistory.historyIndex - 1);
                    njqHistory.historyIndex += 1;
                } else {
                    njqHistory.historyIndex += 1;
                }
                njqHistory.list.push(his);
                tempVar.saveFlag = false;
                if (njqHistory.historyIndex > -1) {
                    var backBtn = ids.editorBack;
                    if (!backBtn.isEnable) {
                        backBtn.isEnable = true;
                        styles.btnEnable(backBtn);
                    }
                }
                if (goBtn.isEnable) {
                    styles.btnDisable(goBtn);
                    goBtn.isEnable = false;
                }
            },
            /**
             * 后退或者前进
             */
            getCache: function (flag) {
                if (flag) {
                    if ((njqHistory.historyIndex + 1) >= njqHistory.list.length) {
                        return false;
                    }
                    njqHistory.historyIndex += 1;
                } else {
                    if ((njqHistory.historyIndex - 1) < 0) {
                        return false;
                    }
                    njqHistory.historyIndex -= 1;
                }
                editorContext.innerHTML = njqHistory.list[njqHistory.historyIndex].context;
                this.clearStyleEmptyNode(editorContext.firstChild, editorContext);
                if (njqHistory.list[njqHistory.historyIndex].range.collapsed) {
                    var rangeNode = service.setRangeTag(njqHistory.list[njqHistory.historyIndex].range.startNode);
                    var rangeOffset = njqHistory.list[njqHistory.historyIndex].range.startOffset;
                    var outNode = util.getOutParentNode(rangeNode);
                    if (outNode && outNode.tagName == constants.TABLE) {
                        resetRange.setStartBefore(util.getMinNode(util.getOutParentNode(rangeNode).nextSibling));
                    } else {
                        resetRange.setStart(rangeNode, rangeOffset);
                        resetRange.setEnd(rangeNode, rangeOffset);
                    }
                } else {
                    var rangeSnode = service.setRangeTag(njqHistory.list[njqHistory.historyIndex].range.startNode);
                    var rangeEnode = service.setRangeTag(njqHistory.list[njqHistory.historyIndex].range.endNode);
                    // 重置选区
                    resetRange.setStart(rangeSnode,
                        njqHistory.list[njqHistory.historyIndex].range.startOffset);
                    resetRange.setEnd(rangeEnode,
                        njqHistory.list[njqHistory.historyIndex].range.endOffset);
                }
                this.dealInnerImgs();
                return true;
            },
            /**
             * 获取选区节点
             */
            setRangeTag: function (nodeList) {
                var node = editorContext;
                for (var i = nodeList.length - 1; i > -1; i--) {
                    if (nodeList[i] == -1) {
                        node = editorContext;
                    } else {
                        node = node.childNodes[nodeList[i]];
                    }
                }
                return node;
            },
            /**
             * 创建节点地址
             */
            getRangeTagAddress: function (range, isStart) {
                var node = isStart ? range.startContainer : range.endContainer;
                var parents = util.getAllParentNodes(node);
                addrs = [];
                if (parents.length == 0) {
//	            	isStart?addrs.push(range.startOffset):addrs.push(range.endOffset>0?range.endOffset-1:range.endOffset);
                    addrs.push(-1);
                    return addrs;
                } else {
                    for (var i = 0, ci; ci = parents[i++];) {
                        addrs.push(this.getNodeIndex(ci));
                    }
                }
                return addrs;
            },
            /**
             * 获取节点的位置（专门用作历史记录保存（兄弟节点如果为文本节点那么不会统计进去）） node:所需要获取的节点
             */
            getNodeIndex: function (node) {
                var preNode = node, i = 0;
                while (preNode = preNode.previousSibling) {
                    //多个连续文本节点，在粘贴后会合并成同一个文本节点
                    if (util.isTextNode(preNode)) {
                        if (preNode.nodeType != preNode.nextSibling.nodeType) {
                            i++;
                        }
                        continue;
                    }
                    i++;
                }
                return i;
            },
            /**
             * 获取选区选择点的位置 isStart：true为开始节点，false为结束节点
             */
            getRangePlace: function (range, isStart) {
                var node = isStart ? range.startContainer : range.endContainer;
                var firstIndex = 0;
                if (util.isTextNode(node)) {
                    var tmpNode = node.previousSibling;
                    while (tmpNode && util.isTextNode(tmpNode)) {
                        firstIndex += tmpNode.nodeValue.length;
                        tmpNode = tmpNode.previousSibling;
                    }
                    firstIndex += (isStart ? range.startOffset : range.endOffset)
                } else {
                    node = node.childNodes[isStart ? range.startOffset : range.endOffset];
                    if (node) {
                        firstIndex = service.getNodeIndex(node);
                    } else {
                        node = isStart ? range.startContainer : range.endContainer;
                        var first = node.firstChild;
                        while (first) {
                            // 是否是填充的节点
                            if (constants.SPACE == first) {
                                first = first.nextSibling;
                                continue;
                            }
                            firstIndex++;
                            if (util.isTextNode(first)) {
                                while (first && util.isTextNode(first)) {
                                    first = first.nextSibling;
                                }
                            } else {
                                first = first.nextSibling;
                            }
                        }
                    }
                }
                if (firstIndex < 0) {
                    firstIndex = 0;
                }
                return firstIndex;
            },
// ----------------------------------------------历史记录功能结束-----------------------------------------
// ----------------------------------------------添加标签功能开始-----------------------------------------
            /**
             * 判断父标签节点是否具有与当前节点标签名相同样式包含的节点 fNode:当前节点 sNode:进行比较的节点
             */
            parentEqNodeCheck: function (fNode, sNode) {
                // 判断是否是文本节点
                if (util.checkIsTextNode(fNode)) {
                    fNode = fNode.parentNode;
                }
                var parentsArray = util.getAllParentNodes(fNode);
                var findNode = null;
                for (var i = 0; i < parentsArray.length; i++) {
                    if (parentsArray[i].tagName == sNode.tagName) {
                        findNode = parentsArray[i];
                        break;
                    }
                }
                if (findNode) {
                    // 进行两个节点的style和class比较，用于判断两个节点是否可以合并
                    return util.checkParentContainsChild(sNode, fNode);
                } else {
                    return false;
                }
            },
            /**
             * 判断父标签中是否包含当前标签 node:当前标签 name:检测的标签名
             */
            parentLabelCheck: function (node, name) {
                var node;
                // 判断是否是文本节点
                if (util.checkIsTextNode(node)) {
                    node = node.parentNode;
                }
                var parentsArray = util.getAllParentNodesName(node);
                if (util.indexOf(parentsArray, name.toUpperCase()) > -1) {
                    return true;
                } else {
                    return false;
                }
            },
            /**
             * 设置所有按钮的可用状态
             *
             * @param type
             *            1：可用 2：不可用
             * @param exceptArray
             *            例外按钮数组
             */
            setAllBtnStatus: function (type, exceptArray) {
                for (var tool in tools) {
                    if (type == 1) {
                        if (util.indexOf(exceptArray, tools[tool]) == -1) {
                            tools[tool].isEnable = true;
                            styles.btnEnable(tools[tool]);
                        }
                    } else if (type == 2) {
                        if (util.indexOf(exceptArray, tools[tool]) == -1) {
                            tools[tool].isEnable = false;
                            styles.btnDisable(tools[tool]);
                        }
                    }
                }
            },
            /**
             * 鼠标修改光标位置，工具条的各个背景的颜色调整
             */
            changePlace: function () {
                if (tempVar.disable) {
                    return;
                }
                //更新按钮状态防并发
                if (tempVar.changeFlag) {
                    return;
                }
                tempVar.changeFlag = true;
                var range = this.getRange();
                this.resetNewRange(range);
                if (range.startContainer.id) {
                    tempVar.changeFlag = false;
                    return;
                }
                this.checkManyNodeStatus(range);
                /*
				 * 按钮可用与变色条件为： 1：按钮是否需要检测的 2：按钮是否允许点击（一般情况下需要检测的按钮都是允许点击）
				 * 3：按钮是否允许置为按下状态 4：最后根据检测结果修改按钮状态
				 */
                for (var tool in tools) {
                    //进行手动置灰的按钮，永远默认是不可点的
                    if (tools[tool].handFalse) {
                        styles.btnDisable(tools[tool]);
                        continue;
                    }
                    if (tools[tool].isNeedCheck) {
                        if (tools[tool].isEnable) {
                            // 处理按钮按下的情况
                            if (tools[tool].downflag) {
                                // 设置按钮的按下状态
                                if (tools[tool].btnDownFlag) {
                                    styles.btnChangeColor(tools[tool]);
                                } else {
                                    styles.btnRecoverColor(tools[tool]);
                                }
                            }
                            styles.btnEnable(tools[tool]);
                        } else {
                            styles.btnDisable(tools[tool]);
                        }
                    } else {
                        if (tools[tool].isEnable) {
                            styles.btnEnable(tools[tool]);
                        } else {
                            styles.btnDisable(tools[tool]);
                        }
                    }
                }
                tempVar.changeFlag = false;
            },
            // 针对选区在不同节点上的情况
            checkManyNodeStatus: function (range) {
                var startNode = util.getFirstStartMinNode(range);
                var endNode;
                if (range.collapsed) {
                    endNode = startNode;
                } else {
                    //鼠标狂点空的td会出现选中多个td的情况，进行选区重置
                    if (range.startContainer.tagName == constants.TD && range.endContainer.tagName == constants.TD) {
                        range.setStart(range.startContainer, 0);
                        range.collapse(true);
                        this.clearAndAddNewRange(range);
                        endNode = startNode;
                    } else {
                        endNode = util.getLastEndMinNode(range);
                        if (util.isElementNode(endNode)) {
                            if (range.endOffset == 0) {
                                endNode = endNode.childNodes[range.endOffset];
                            } else {
                                endNode = endNode.childNodes[range.endOffset - 1];
                            }
                        }
                    }
                }
                var textList = [];
                if (range.collapsed) {
                    if (range.startContainer.nodeType == 1) {
                        textList.push(util.getRangeStartText(range));
                    }
                } else {
                    util.forListNode(startNode, endNode, function (node) {
                        textList.push(node);
                        return node;
                    }, 3);
                }

                var checkNode, node;
                for (var tool in tools) {
                    //如果按钮是手动置灰的，权限最高，无须再进行检测
                    if (tools[tool].handFalse) {
                        tools[tool].isEnable = false;
                        // 是否还需要检测
                        tools[tool].tempFlag = false;
                        // 按钮按下状态
                        tools[tool].btnDownFlag = false;
                        // 内容是否有改变
                        tools[tool].valueChanged = false;
                        continue;
                    } else if (tools[tool].isNeedCheck) {
                        tools[tool].isEnable = true;
                        // 是否还需要检测
                        tools[tool].tempFlag = true;
                        // 按钮按下状态
                        tools[tool].btnDownFlag = false;
                        // 内容是否有改变
                        tools[tool].valueChanged = false;
                        // 是否支持在pre标签中使用
                        if (!tools[tool].inPre) {
                            if (this.checkRuleBase(startNode, tools[tool])) {
                                tools[tool].isEnable = false;
                                continue;
                            }
                            if (this.checkRuleBase(endNode, tools[tool])) {
                                tools[tool].isEnable = false;
                                continue;
                            }
                        }
                        if (tools[tool].checkType.type == "1") {
                            node = startNode;
                            var index = 0;
                            while (tools[tool].tempFlag) {
                                if (!node) {
                                    index = 0;
                                    break;
                                }
                                checkNode = this[tools[tool].checkfun[0]](node, tools[tool]);
                                // 当循环到最后一个节点时需要跳出循环
                                if (node == endNode) {
                                    index = 0;
                                    break;
                                }
                                // 如果存在检测到的节点，那么判断最后的节点是否也存在这个检测节点中
                                if (checkNode && checkNode.contains(endNode)) {
                                    index = 0;
                                    break;
                                }
                                node = textList[index++];
                                if (!node) {
                                    break;
                                }
                            }
                        } else if (tools[tool].checkType.type == "2") {
                            if (rangeTable.table) {
                                this[tools[tool].checkfun[0]](rangeTable.rangeTd, tools[tool]);
                            } else {
                                tools[tool].isEnable = false;
                            }
                        } else if (tools[tool].checkType.type == "3") {
                            this[tools[tool].checkfun[0]](tools[tool]);
                        }
                    }
                }
            },
            /**
             * 规则的基本内容
             */
            checkRuleBase: function (node, btn) {
                var preNode = util.getSpecalParentNode(constants.PRE, node);
                if (preNode) {
                    return preNode;
                }
            },
            /**
             * 规则只针对特殊标签，如加粗、斜体的情况
             *
             * @param btn
             *            按钮
             * @param node
             *            检测的节点
             */
            checkRuleInTag: function (node, btn) {
                if (this.checkRuleBase(node, btn)) {
                    return;
                }
                var parentNode = util.getSpecalParentNode(btn.checkType.label, node);
                if (parentNode) {
                    btn.btnDownFlag = true;
                } else {
                    btn.tempFlag = false;
                    btn.btnDownFlag = false;
                }
                return parentNode;
            },
            /**
             * 规则只针对特殊标签，只要出现一次就标亮
             */
            checkRuleOutTag: function (node, btn) {
                if (this.checkRuleBase(node, btn)) {
                    return;
                }
                btn.isEnable = false;
                var parentNode = util.getSpecalParentNode(btn.checkType.label, node);
                if (parentNode) {
                    btn.isEnable = true;
                    btn.tempFlag = false;
                }
                return parentNode;
            },
            /**
             * 规则只针对样式，如边框，下划线
             */
            checkRuleInStyle: function (node, btn) {
                if (this.checkRuleBase(node, btn)) {
                    return;
                }
                var parentNode = util.getContainsStyleParentNodeUntagName(node, btn.checkType.style);
                if (parentNode) {
                    btn.btnDownFlag = true;
                } else {
                    btn.tempFlag = false;
                    btn.btnDownFlag = false;
                }
                return parentNode;
            },
            /**
             * 规则同时针对标签和样式
             *
             * @param btn
             *            按钮
             * @param node
             *            检测的节点
             */
            checkRuleInTagAndStyle: function (node, btn) {
                if (this.checkRuleBase(node, btn)) {
                    return;
                }
                var parentNode = util.getSpecalParentNode(btn.labelName, node);
                if (util.checkParentContainsChild(btn, parentNode)) {
                    btn.btnDownFlag = true;
                } else {
                    btn.tempFlag = false;
                    btn.btnDownFlag = false;
                }
            },
            /**
             * 规则针对外层父节点如居中、居左等
             *
             * @param btn
             * @param node
             */
            checkRuleOutNodeStyle: function (node, btn) {
                node = util.getOutParentNode(node);
                if (!node) {
                    return;
                }
                for (var st in btn.checkType.style) {
                    if (node.style[st]) {
                        if (node.style[st] == btn.checkType.style[st]) {
                            btn.btnDownFlag = true;
                        } else {
                            btn.tempFlag = false;
                            btn.btnDownFlag = false;
                        }
                    } else {
                        if (btn.isDefault) {
                            btn.btnDownFlag = true;
                        } else {
                            btn.tempFlag = false;
                            btn.btnDownFlag = false;
                        }
                    }
                }
                return node;
            },
            /**
             * 规则针对同一标签，如 table
             *
             * @param btn
             * @param node
             */
            checkRuleSameNode: function (node, btn) {
                if (this.checkRuleBase(node, btn)) {
                    return;
                }
                var parentNode = util.getSpecalParentNode(btn.checkType.label, node);
                if (parentNode) {
                    if (btn.setNode) {
                        if (btn.tempNode != parentNode) {
                            btn.tempFlag = false;
                            btn.isEnable = false;
                            btn.setNode = false;
                            btn.tempNode = null;
                        }
                    } else {
                        btn.tempNode = parentNode;
                    }
                } else {
                    btn.tempFlag = false;
                    btn.isEnable = false;
                    btn.setNode = false;
                    btn.tempNode = null;
                }
                return parentNode;
            },
            /**
             * 规则针对插入行操作,限制最大插入数量
             *
             * @param btn
             * @param node
             */
            checkRuleTableTr: function (node, btn) {
                if (rangeTable.arrayTr.length >= btn.checkType.maxLength) {
                    btn.isEnable = false;
                }
            },
            /**
             * 规则针对插入列操作,限制最大插入数量
             *
             * @param btn
             * @param node
             */
            checkRuleTableTd: function (node, btn) {
                if (rangeTable.tdlength >= btn.checkType.maxLength) {
                    btn.isEnable = false;
                }
            },
            /**
             * 规则单独针对合并单元格
             */
            checkRuleTableMer: function (node, btn) {
                if (util.isEmptyObject(rangeTable.seleTdArray)) {
                    btn.isEnable = false;
                }
            },
            /**
             * 规则针对功能拆分单元格
             */
            checkRuleTableBreak: function (node, btn) {
                if (this.checkRuleBase(node, btn)) {
                    return;
                }
                btn.isEnable = false;
                if (!rangeTable.rangeTd) {
                    btn.tempFlag = false;
                    return
                }
                var tdList = [];
                if (util.isEmptyObject(rangeTable.seleTdArray)) {
                    tdList.push(rangeTable.rangeTd);
                } else {
                    for (var i in rangeTable.seleTdArray) {
                        tdList.push(rangeTable.seleTdArray[i]);
                    }
                }
                for (var i = 0; i < tdList.length; i++) {
                    for (var st in btn.checkType.attr) {
                        if (st == "colSpan") {
                            if (tdList[i].colSpan > btn.checkType.attr[st]) {
                                btn.isEnable = true;
                                btn.tempFlag = false;
                            }
                        }
                        if (st == "rowSpan") {
                            if (tdList[i].rowSpan > btn.checkType.attr[st]) {
                                btn.isEnable = true;
                                btn.tempFlag = false;
                            }
                        }
                    }
                }
            },
            /**
             * 规则检测在td中不允许出现的标签，如hr标签
             */
            checkRuleNotAllowTag: function (node, btn) {
                if (this.checkRuleBase(node, btn)) {
                    return;
                }
                if (rangeTable.table) {
                    btn.isEnable = false;
                    btn.tempFlag = false;
                    return
                }
                var parentNode = util.getSpecalParentNode(btn.checkType.label, node);
                if (parentNode) {
                    btn.isEnable = false;
                    btn.tempFlag = false;
                }
                return parentNode;
            },
            /**
             * 规则针对下拉框的内容改变如段前距
             */
            checkRuleDialogValue: function (node, btn) {
                if (this.checkRuleBase(node, btn)) {
                    return;
                }
                var nodeDialog = ids.editorDlgDiv.getElementById(btn.dlgId);
                var parent = util.getOutParentNode(node);
                if (!parent) {
                    return;
                }
                var value = parent.style[btn.checkType.styleName];
                var act, firstNode;
                for (var i in btn.bindAttr) {
                    firstNode = btn.bindAttr[i];
                    break;
                }
                if (!firstNode) {
                    return parent;
                }
                act = util.getElementsByClassName(firstNode.parentNode, "active");
                if (act.length > 0) {
                    act[0].classList.remove("active");
                }
                if (value && btn.bindAttr[value]) {
                    btn.bindAttr[value].firstElementChild.firstElementChild.classList.add("active");
                    btn.tempFlag = false;
                }
                return parent;
            },
            /**
             * 规则针对功能段落
             *
             * @param btn
             * @param node
             */
            checkRuleOutNodeTag: function (node, btn) {
                if (this.checkRuleBase(node, btn)) {
                    return;
                }
                var parent = util.getOutParentNode(node);
                if (!parent) {
//		    		setValue=btn.defaultVaule;
//	    			return;
                    console.info(1111)
                }
                var innerHtml = btn.bindAttr[parent.tagName];
                var setValue;
                if (innerHtml) {
                    setValue = innerHtml;
                } else {
                    setValue = btn.defaultVaule;
                }
                if (btn.valueChanged) {
                    // 段落不校验 ol ul table
                    if (util.checkIstouNode(parent)) {
                        return parent;
                    }
                    if (btn.valueNode.innerHTML != setValue) {
                        btn.valueNode.innerHTML = constants.EMPTY;
                        btn.tempFlag = false;
                    }
                } else {
                    btn.valueNode.innerHTML = setValue;
                    btn.valueChanged = true;
                }
                return parent;
            },
            /**
             * 规则针对功能特殊样式 校验规则：
             */
            checkRuleSetValueManyStyleList: function (node, btn) {
                if (this.checkRuleBase(node, btn)) {
                    return;
                }
                var parentNode, setValue;
                for (var name in btn.bindAttr) {
                    parentNode = util.getContainsStyleParentNodeUntagName(node, btn.bindAttr[name]);
                    if (parentNode) {
                        setValue = name;
                        break;
                    }
                }
                if (!parentNode) {
                    setValue = btn.defaultVaule;
                }
                if (btn.valueChanged) {
                    if (btn.valueNode.innerHTML != setValue) {
                        btn.valueNode.innerHTML = constants.EMPTY;
                        btn.tempFlag = false;
                    }
                } else {
                    btn.valueNode.innerHTML = setValue;
                    btn.valueChanged = true;
                }
                return parentNode;
            },
            /**
             * 规则针对功能字体类型、字体大小 校验规则：只校验样式名称，然后将值样式值进行填充
             */
            checkRuleSetValueStyle: function (node, btn) {
                if (this.checkRuleBase(node, btn)) {
                    return;
                }
                var parent = util.getParentPointStyleNameNode(node, btn.checkType.styleName);
                var setValue;
                if (parent) {
                    setValue = btn.bindAttr[parent.style[btn.checkType.styleName]];
                    if (!setValue) {
                        setValue = parent.style[btn.checkType.styleName];
                    }
                } else {
                    setValue = btn.defaultVaule;
                }
                if (btn.valueChanged) {
                    if (btn.valueNode.innerHTML != setValue) {
                        btn.valueNode.innerHTML = constants.EMPTY;
                        btn.tempFlag = false;
                    }
                } else {
                    btn.valueNode.innerHTML = setValue;
                    btn.valueChanged = true;
                }
                return parent;
            },
            /**
             * 针对后退事件
             *
             * @param node
             * @param btn
             */
            checkRuleHistoryBack: function (btn) {
                if (njqHistory.historyIndex <= 0) {
                    btn.isEnable = false;
                    return;
                }
                if (njqHistory.list.length <= 0) {
                    btn.isEnable = false;
                    return;
                }
                btn.isEnable = true;
            },
            /**
             * 针对前进事件
             *
             * @param node
             * @param btn
             */
            checkRuleHistoryGo: function (btn) {
                if (njqHistory.list.length <= 0) {
                    btn.isEnable = false;
                    return;
                }
                if (njqHistory.historyIndex >= (njqHistory.list.length - 1)) {
                    btn.isEnable = false;
                    return;
                }
                btn.isEnable = true;
            },
            removeListLabel: function (tagName) {
                var range = this.getRange();
                var sNode = range.startContainer;
                var eNode = range.endContainer;
                var rangeSnode, rangeEnode, soffset, eoffset;
                if (sNode.nodeType == 3) {
                    rangeSnode = sNode;
                    soffset = range.startOffset;
                } else {
                    if (sNode.childNodes.length == range.startOffset) {
                        util.insertAfter(stMark, sNode.childNodes[range.startOffset - 1]);
                    } else {
                        util.insertBefore(stMark, sNode.childNodes[range.startOffset]);
                    }
                }
                if (eNode.nodeType == 3) {
                    rangeEnode = eNode;
                    eoffset = range.endOffset;
                } else {
                    if (range.endOffset == 0) {
                        util.insertBefore(enMark, eNode.childNodes[range.endOffset]);
                    } else {
                        util.insertAfter(enMark, eNode.childNodes[range.endOffset - 1]);
                    }
                }

                var liNode = util.getSpecalParentNode(constants.LI, sNode);
                var lastliNode = util.getSpecalParentNode(constants.LI, eNode);
                var sol = util.getSpecalParentNode(tagName, sNode);
                var eol = util.getSpecalParentNode(tagName, eNode);
                var divNode = util.createCustomNode(constants.DIV);
                var flag = true, parentNode = sol, tempDiv, preDiv, templi = liNode,
                    cutLi = lastliNode.nextSibling, insertFlag = false;
                if (sol == eol && sol.firstChild == liNode) {
                    insertFlag = true;
                }
                while (flag) {
                    if (parentNode.tagName == constants.TABLE || parentNode.tagName == constants.PRE) {
                        parentNode = parentNode.nextSibling;
                        continue;
                    } else {
                        if (!templi) {
                            templi = parentNode.firstChild;
                        }
                    }
                    if (templi == lastliNode) {
                        flag = false;
                    }
                    if (util.checkIstouNode(templi.firstElementChild)) {
                        tempDiv = templi.firstElementChild;
                    } else {
                        tempDiv = divNode.cloneNode();
                        util.changeChildNode(templi, tempDiv);
                    }
                    if (!preDiv) {
                        preDiv = parentNode;
                    }
                    //选区是同一个列表且是从第一个开始选的，那么是插入到前面
                    if (insertFlag) {
                        util.insertBefore(tempDiv, parentNode);
                    } else {
                        util.insertAfter(tempDiv, preDiv);
                    }
                    preDiv = tempDiv;
                    if (templi.nextSibling) {
                        templi = templi.nextSibling;
                        util.removeNode(templi.previousSibling);
                    } else {
                        util.removeNode(templi);
                        if (parentNode.childNodes.length == 0) {
                            util.removeNode(parentNode);
                        }
                        if (tempDiv.nextSibling) {
                            parentNode = tempDiv.nextSibling;
                            templi = null;
                            preDiv = parentNode;
                        } else {
                            break;
                        }
                    }
                    //避免无限循环
                    if (preDiv.id) {
                        break;
                    }
                }
                //剩余的li放到新的ol里
                if (cutLi && cutLi.previousSibling) {
                    var nextol = util.createCustomNode(tagName);
                    util.insertAfter(nextol, tempDiv);
                    nextol.style.listStyleType = parentNode.style.listStyleType;
                    while (cutLi) {
                        templi = cutLi.nextSibling;
                        nextol.appendChild(cutLi);
                        cutLi = templi;
                    }
                }

                if (rangeSnode) {
                    resetRange.setStart(rangeSnode, soffset);
                } else {
                    resetRange.setStartAfter(stMark);
                    stMark.remove();
                }
                if (rangeEnode) {
                    resetRange.setEnd(rangeEnode, eoffset);
                } else {
                    resetRange.setEndBefore(enMark);
                    enMark.remove();
                }
            },
            /**
             * 添加序号节点
             */
            addListLabel: function (range, node, btnNode) {
                var sparentNode, eparentNode, liNode, lastliNode, ol;
                var sNode = range.startContainer;
                var eNode = range.endContainer;
                var soffset = range.startOffset;
                var eoffset = range.endOffset;
                // 针对开始节点选区在父标签外面的情况，需要先进行选区重选
                if (util.isOutNodeCheck(sNode)) {
                    if (sNode.childNodes.length == soffset) {
                        sNode = sNode.childNodes[soffset - 1];
                        if (util.isTextNode(sNode)) {
                            soffset = sNode.data.length;
                        } else {
                            soffset = -1;
                        }
                    } else {
                        sNode = sNode.childNodes[soffset];
                        soffset = -1;
                    }
                }
                var colFlag = false;
                if (range.collapsed) {
                    colFlag = range.collapsed;
                } else {
                    // 针对结束节点选区在父标签外面的情况，需要先进行选区重选
                    if (util.isOutNodeCheck(eNode)) {
                        if (eoffset == 0) {
                            eNode = eNode.childNodes[eoffset];
                        } else {
                            eNode = eNode.childNodes[eoffset - 1];
                        }
                        eoffset = -1;
                    }
                }
                // 第一步先获取选区开始位置对应的节点和选区结束位置对应的节点
                sparentNode = util.getRangeOutParentNode(sNode, range, 1);
                if (range.collapsed) {
                    eparentNode = sparentNode;
                } else {
                    eparentNode = util.getRangeOutParentNode(eNode, range, 2);
                }
                // 第二步获取起始节点是否包含在li标签内
                liNode = util.getSpecalParentNode(constants.LI, sNode);
                lastliNode = util.getSpecalParentNode(constants.LI, eNode);
                if (eparentNode == sparentNode) {
                    if (liNode) {
                        ol = liNode.parentNode;
                        if (ol.style.listStyleType != node.style.listStyleType) {
                            if (liNode == ol.firstChild && lastliNode == ol.lastChild) {
                                if (ol.tagName == node.tagName) {
                                    ol.style.listStyleType = node.style.listStyleType;
                                } else {
                                    util.insertBefore(node, ol);
                                    util.changeChildNode(ol, node);
                                    util.removeNode(ol);
                                }
                            } else if (liNode.parentNode == lastliNode.parentNode) {
                                if (lastliNode != ol.lastChild) {
                                    // 生成最后的列表
                                    var nextol = ol.cloneNode();
                                    while (lastliNode.nextSibling) {
                                        nextol.appendChild(lastliNode.nextSibling);
                                    }
                                    util.insertAfter(nextol, ol);
                                }
                                // 生成中间的列表
                                var centerol = node.cloneNode();
                                var tnode = liNode.nextSibling;
                                centerol.appendChild(liNode);
                                if (tnode) {
                                    while (tnode != lastliNode) {
                                        tnode = tnode.nextSibling;
                                        centerol.appendChild(tnode.previousSibling);
                                    }
                                    centerol.appendChild(lastliNode);
                                }
                                util.insertAfter(centerol, ol);
                            }
                        }
                    } else {
                        /*
						 * 针对开始节点结束节点在同一个非ol标签内的情况 如: 'aassdsdsdsd' 选择其中设置列表
						 */
                        var li = btnNode.cloneNode();
                        ol = node.cloneNode();
                        ol.appendChild(li);
                        util.insertAfter(ol, sparentNode);
                        if (util.checkIstouNode(sparentNode)) {
                            li.appendChild(sparentNode);
                        } else {
                            if (sparentNode.childNodes.length == 1 && util.isBrLabel(sparentNode.lastChild)) {
                                li.appendChild(util.createCustomNode(constants.BR));
                                util.removeNode(sparentNode.lastChild);
                                this.setRangeBefore(li.firstChild);
                                util.removeNode(sparentNode);
                                return;
                            } else {
                                util.mergeToOtherNode(sparentNode, li);
                                util.removeNode(sparentNode);
                            }
                        }
                    }
                } else {
                    var tempNode = sparentNode.nextSibling;
                    if (liNode) {
                        // 开始节点在li标签内，那么将整个序列进行转换
                        ol = liNode.parentNode;
                        if (ol.tagName == node.tagName) {
                            ol.style.listStyleType = node.style.listStyleType;
                        } else {
                            var tempNode = node.cloneNode();
                            util.insertBefore(tempNode, ol);
                            util.changeChildNode(ol, tempNode);
                            util.removeNode(ol);
                            ol = tempNode;
                            tempNode = tempNode.nextSibling;
                        }
                    } else {
                        ol = node.cloneNode();
                        /*
						 * 针对开始节点结束节点在同一个非ol标签内的情况 如: 'aassdsdsdsd' 选择其中设置列表
						 */
                        var li = btnNode.cloneNode();
                        ol.appendChild(li);
                        util.insertBefore(ol, sparentNode);
                        if (util.checkIstouNode(sparentNode)) {
                            li.appendChild(sparentNode);
                        } else {
                            if (sparentNode.childNodes.length == 1 && util.isBrLabel(sparentNode.lastChild)) {
                                li.appendChild(util.createSpace(range));
                                util.removeNode(sparentNode.lastChild);
                            } else {
                                util.mergeToOtherNode(sparentNode, li);
                                util.removeNode(sparentNode);
                            }
                        }
                    }
                    var n = 0;
                    var flag = true;
                    while (tempNode && tempNode != eparentNode) {
                        if (util.checkIsList(tempNode)) {
                            this.liMerge(tempNode, ol);
                            tempNode = tempNode.nextSibling;
                            util.removeNode(tempNode.previousSibling);
                        } else {
                            li = btnNode.cloneNode();
                            ol.appendChild(li);
                            if (util.checkIstouNode(tempNode)) {
                                li.appendChild(tempNode);
                                tempNode = ol.nextSibling;
                            } else {
                                util.mergeToOtherNode(tempNode, li);
                                tempNode = tempNode.nextSibling;
                                util.removeNode(tempNode.previousSibling);
                            }
                        }
                    }
                    if (util.checkIsList(tempNode)) {
                        this.liMerge(tempNode, ol);
                        util.removeNode(tempNode);
                    } else {
                        li = btnNode.cloneNode();
                        ol.appendChild(li);
                        if (util.checkIstouNode(tempNode)) {
                            li.appendChild(tempNode);
                        } else {
                            util.mergeToOtherNode(tempNode, li);
                            util.removeNode(tempNode);
                        }
                    }
                }
                if (sNode.id) {
                    resetRange.setStartBefore(ol.firstChild);
                } else {
                    if (soffset != -1) {
                        resetRange.setStart(sNode, soffset);
                    } else {
                        resetRange.setStartBefore(sNode);
                    }
                }
                if (!colFlag) {
                    if (eNode.id) {
                        resetRange.setEndAfter(ol.lastChild);
                    } else {
                        if (eoffset != -1) {
                            resetRange.setEnd(eNode, eoffset);
                        } else {
                            resetRange.setEndAfter(eNode);
                        }
                    }
                }
            },
            // 列表标签合并
            liMerge: function (list, node) {
                while (list.firstChild) {
                    node.appendChild(list.firstChild);
                }
            },
            /**
             * 添加pre标签 range：选区对象 btnNode：添加的标签对象
             */
            addPreLabel: function (range, btnNode) {
                // 先将节点拆分成两个div
                var cutNode, lip = null;
                var preNode = util.createEmptyNode(constants.PRE);
                //先
                if (!range.collapsed) {
                    var sliNode = util.getSpecalParentNode(constants.LI, range.startContainer);
                    var eliNode = util.getSpecalParentNode(constants.LI, range.endContainer);
                    if (sliNode && eliNode && sliNode.parentNode == eliNode.parentNode) {
                        lip = sliNode.parentNode.cloneNode();
                    }
                    cutNode = util.extractContents(range);
                    if (lip) {
                        if (cutNode.firstChild.tagName == constants.LI) {
                            lip.appendChild(cutNode);
                            cutNode = lip;
                        }
                    }
                    var temps = util.createCustomNode(constants.DIV);
                    temps.appendChild(cutNode);
                    var preList = util.getElementsByTagName(temps, constants.PRE);
                    for (var i = 0; i < preList.length; i++) {
                        util.moveAllChildNodeToOut(preList[i]);
                    }
                    preNode.innerHTML = temps.innerHTML;
                }
                var tempRange = range.cloneRange();
                if (!range.startContainer.id) {
                    var parent = util.getOutParentNode(range.startContainer);
                    tempRange.setStart(range.startContainer, range.startOffset);
                    tempRange.setEndAfter(parent);
                    var tempNode = util.extractContents(tempRange);
                    var divNode = tempNode.firstElementChild;
                    util.deleteAllChildrenEmptyNodes(divNode.firstChild);
                    if (util.checkIsEmpty(divNode)) {
                        divNode.appendChild(util.createCustomNode(constants.BR));
                    }
                    if (ids.editorContext.childNodes.length == 0) {
                        ids.editorContext.appendChild(divNode);
                    } else {
                        if (ids.editorContext.contains(parent)) {
                            util.insertAfter(divNode, parent);
                        } else {
                            this.insertNode(range, divNode);
                        }
                        util.deleteAllChildrenEmptyNodes(parent, true);
                    }
                    util.insertBefore(preNode, divNode);
                    util.deleteAllChildrenEmptyNodes(divNode, true);
                    this.setRangeBefore(preNode.firstChild);
                } else {
                    this.insertNode(range, preNode);
                }
                if (!preNode.nextSibling) {
                    util.insertAfter(util.createEmptyNode(constants.DIV), preNode);
                }
            },
            /**
             * 添加标签，并将选区定位在当前节点中（非选中，只有光标的状态） btnNode:当前按钮对象 addLabel:添加的标签对象
             */
            addLabel: function (range, btnNode, addLabel) {
                var name = btnNode.labelName;
                var tmpNode = addLabel;
                var sNode = range.startContainer;
                // 判断当前按钮是否可用
                if (!btnNode.isEnable) {
                    return;
                }
                /*
				 * 先判断当前按钮是否允许按下，如果是允许按下的按钮要判断当前按钮是否已经按下
				 */
                if (btnNode.isLabelBtn) {
                    if (!btnNode.btnDownFlag) {
                        if (!this.parentEqNodeCheck(sNode, addLabel)) {
                            tmpNode.appendChild(util.createSpace(range));
                            this.insertNode(range, tmpNode);
                            if (util.isBrLabel(tmpNode.nextSibling)) {
                                tmpNode.nextSibling.remove();
                            }
                            this.setRangeAfter(tmpNode.firstChild);
                        }
                    }
                } else {
                    if (!this.parentEqNodeCheck(sNode, addLabel)) {
                        /*
						 * 如果父节点和要添加的节点类型相同，且父节点内容为空或只有br，那么将要添加的节点样式赋予给父节点
						 * 否则创建一个要添加的节点到父节点下
						 */
                        if (sNode.tagName == addLabel.tagName &&
                            (sNode.innerHTML == constants.SPACE ||
                                (sNode.childNodes.length == 1 && util.isBrLabel(sNode.firstChild)))) {
                            util.mergeStyle(sNode, addLabel);
                            this.setTranRange(range);
                        } else {
                            tmpNode.appendChild(util.createSpace(range));
                            this.insertNode(range, tmpNode);
                            this.setRangeAfter(tmpNode.firstChild);
                        }
                    }
                }
            },
            /**
             * 添加标签（选中状态，非只有光标） range:选区 btnNode:需要添加的按钮
             */
            addSpecalLabel: function (range, btnNode) {

                var totalRange = {};
                // 第一步，单独针对检测开始节点、结束节点出现在li中的情况
                var temptRange = this.addLabelStartAndEndPartDeal(range, totalRange, btnNode);
                if (!temptRange) {
                    return;
                }
                this.resetNewRange(temptRange);

                // 第二步检测中间的选区是否是跨父标签的情况
                var commmonNode = temptRange.commonAncestorContainer;
                // 如果公共父节点存在id，说明是跨外置标签加标签，需要进行拆分处理
                if (commmonNode.id) {
                    trange = this.addOutSpecalLabel(temptRange, btnNode);
                } else {
                    trange = this.addInnerLabel(temptRange, btnNode);
                }

                // 第三步，所有选区处理完之后进行选区设置
                if (totalRange.startContainer) {
                    resetRange.setStart(totalRange.startContainer, totalRange.startOffset);
                } else {
                    resetRange.setStart(trange.startContainer, trange.startOffset);
                }
                if (totalRange.endContainer) {
                    resetRange.setEnd(totalRange.endContainer, totalRange.endOffset);
                } else {
                    resetRange.setEnd(trange.endContainer, trange.endOffset);
                }

            },
            /**
             * 处理开始节点和结束节点的特殊情况，如：li
             *
             * @param range
             * @param totalRange
             * @param btnNode
             * @returns
             */
            addLabelStartAndEndPartDeal: function (range, totalRange, btnNode) {
                var sNode = range.startContainer;
                var eNode = range.endContainer;
                var sliNode = util.getSpecalParentNode(constants.LI, sNode);
                var eliNode = util.getSpecalParentNode(constants.LI, eNode);
                var temptRange = range.cloneRange();
                if (sliNode && eliNode) {
                    if (sliNode != eliNode) {
                        if (sliNode.parentNode == eliNode.parentNode) {
                            trange = this.addOutSpecalLabel(range, btnNode, constants.LI);
                            resetRange.setStart(trange.startContainer, trange.startOffset);
                            resetRange.setEnd(trange.endContainer, trange.endOffset);
                            return;
                        } else {
                            var stempRange = util.createRange();
                            stempRange.setStart(range.startContainer, range.startOffset);
                            stempRange.setEndAfter(sliNode.parentNode.lastChild.lastChild);
                            temptRange.setStartAfter(sliNode.parentNode);
                            trange = this.addOutSpecalLabel(stempRange, btnNode, constants.LI);
                            totalRange.startOffset = trange.startOffset;
                            totalRange.startContainer = trange.startContainer;
                            var etempRange = util.createRange();
                            etempRange.setStartBefore(eliNode.parentNode.firstChild.firstChild);
                            etempRange.setEnd(range.endContainer, range.endOffset);
                            temptRange.setEndBefore(eliNode.parentNode);
                            if (etempRange.startContainer.contains(etempRange.endContainer)) {
                                trange = this.addInnerLabel(etempRange, btnNode);
                            } else {
                                trange = this.addOutSpecalLabel(etempRange, btnNode, constants.LI);
                            }
                            totalRange.endOffset = trange.endOffset;
                            totalRange.endContainer = trange.endContainer;
                        }
                    }
                }
                if (sliNode && (!eliNode)) {
                    var stempRange = util.createRange();
                    stempRange.setStart(range.startContainer, range.startOffset);
                    // 设置结束节点为最后1个li的最后一个节点
                    stempRange.setEndAfter(sliNode.parentNode.lastChild.lastChild);
                    temptRange.setStartAfter(sliNode.parentNode);
                    trange = this.addOutSpecalLabel(stempRange, btnNode, constants.LI);
                    totalRange.startOffset = trange.startOffset;
                    totalRange.startContainer = trange.startContainer;
                }
                if ((!sliNode) && eliNode) {
                    var stempRange = util.createRange();
                    stempRange.setStartBefore(eliNode.parentNode.firstChild.firstChild);
                    stempRange.setEnd(range.endContainer, range.endOffset);
                    temptRange.setEndBefore(eliNode.parentNode);
                    if (stempRange.startContainer.contains(stempRange.endContainer)) {
                        trange = this.addInnerLabel(stempRange, btnNode);
                    } else {
                        trange = this.addOutSpecalLabel(stempRange, btnNode, constants.LI);
                    }
                    totalRange.endOffset = trange.endOffset;
                    totalRange.endContainer = trange.endContainer;
                }
                return temptRange;
            },
            /**
             * 添加非跨父标签的场景
             *
             * @param range
             * @param btnNode
             */
            addInnerLabel: function (range, btnNode) {
                var sNode = range.startContainer;
                var eNode = range.endContainer;
                var spNode = util.getSpecalParentNode(btnNode.tagName, sNode);
                var epNode = util.getSpecalParentNode(btnNode.tagName, eNode);
                // 针对父节点相同的节点
                if (spNode && spNode == epNode && util.checkIsSerisNodeChild(spNode, sNode, range, 1) &&
                    util.checkIsSerisNodeChild(epNode, eNode, range, 2)) {
                    util.setNodeStyle(spNode, btnNode);
                    // 删除内部样式相同的样式名或标签
                    util.delSameLabelOrCss(spNode, btnNode);
                    util.deleteAllChildrenEmptyNodes(spNode, true);
                    return range;
                } else {
                    // 父标签不是相同的场景
                    btnNode = btnNode.cloneNode();
                    btnNode.appendChild(util.extractContents(range));
                    this.insertNode(range, btnNode);
                    util.insertBefore(stMark, btnNode.firstChild);
                    util.insertAfter(enMark, btnNode.lastChild);
                    // 删除内部样式相同的样式名或标签
                    util.delSameLabelOrCss(btnNode, btnNode);
                    this.addRemoveSpecalLabel(btnNode, btnNode.cloneNode());
                    var merfNode = util.mergePreNode(btnNode);
                    var mersNode = util.mergeNextNode(merfNode);
                    var trange = util.createRange();
                    trange.setStartBefore(stMark);
                    trange.setEndAfter(enMark);
                    util.removeNode(stMark);
                    util.removeNode(enMark);
                    util.deleteAllChildrenEmptyNodes(mersNode.parentNode, true);
                    return trange;
                }
            },
            /**
             * 跨外置节点加标签
             *
             * @param range
             * @param btnNode
             */
            addOutSpecalLabel: function (range, btnNode, tagName) {
                var sNode = range.startContainer;
                var eNode = range.endContainer;
                var frange = range.cloneRange();
                var crange = range.cloneRange();
                var trange = util.createRange();
                var foutNode, eoutNode;
                if (tagName) {
                    foutNode = util.getSpecalParentNode(constants.LI, sNode);
                    eoutNode = util.getSpecalParentNode(constants.LI, eNode);
                } else {
                    foutNode = util.getOutParentNode(sNode);
                    if (!foutNode) {
                        foutNode = range.startContainer.childNodes[range.startOffset];
                    }
                    eoutNode = util.getOutParentNode(eNode);
                    if (!eoutNode) {
                        eoutNode = range.endContainer.childNodes[range.endOffset - 1];
                    }
                }
                var startNode, startOffset;
                var endNode, endOffset;
                if (foutNode.tagName == constants.PRE) {
                    startNode = sNode;
                    startOffset = range.startOffset;
                } else {
                    if (foutNode.childNodes.length == 1 && util.isSingleNodeCheck(foutNode.firstChild)) {
                        startNode = foutNode;
                    } else {
                        // 处理起始部分
                        var ffpNode = util.getContainsStyleParentNode(sNode, btnNode);
                        var lastText = util.getInnerOrBeforeTextNode(foutNode.lastChild);
                        var isNeed;
                        if (ffpNode) {
                            var fspNode = util.getContainsStyleParentNode(lastText, btnNode);
                            if (ffpNode != fspNode) {
                                frange.setEndAfter(foutNode.lastChild);
                                isNeed = true;
                            } else {
                                startNode = frange.startContainer;
                                startOffset = frange.startOffset;
                            }
                        } else {
                            var f1, f2;
                            if (util.checkSincloudAll(frange)) {
                                f1 = util.getSpecalParentNode(btnNode.tagName, sNode);
                                f2 = util.getSpecalParentNode(btnNode.tagName, lastText);
                                if (f1 && f1 == f2) {
                                    util.setNodeStyle(f1, btnNode);
                                    // 删除内部样式相同的样式名或标签
                                    util.delSameLabelOrCss(f1, btnNode);
                                    frange.setStartBefore(f1);
                                    startNode = frange.startContainer;
                                    startOffset = frange.startOffset;
                                } else {
                                    frange.setEndAfter(foutNode.lastChild);
                                    isNeed = true;
                                }
                            } else {
                                frange.setEndAfter(foutNode.lastChild);
                                if (!frange.collapsed) {
                                    isNeed = true;
                                } else {
                                    startNode = frange.startContainer;
                                    startOffset = frange.startOffset;
                                }
                            }
                        }
                    }
                    if (isNeed) {
                        var insertfNode = this.cutRange(frange, btnNode.cloneNode());
                        // 把节点填充到最后位置
                        foutNode.appendChild(insertfNode);
                        util.insertBefore(stMark, util.getfirstTextNode(insertfNode.firstChild));
                        this.addRemoveSpecalLabel(insertfNode, btnNode);
                        util.deleteAllChildrenEmptyNodes(util.mergePreNode(insertfNode).parentNode, true);
                        isNeed = false;
                    }
                }
                if (foutNode == eoutNode) {
                    if (startNode) {
                        trange.setStart(startNode, startOffset);
                    } else {
                        trange.setStartAfter(stMark);
                        util.removeNode(stMark);
                    }
                    trange.setEndAfter(eoutNode.lastChild);
                    return trange;
                }

                var fnextNode = foutNode.nextSibling;
                var tempRange = util.createRange();
                var exNode, nodeList, partNode, btnClone;
                // 处理中间部分
                while (fnextNode) {
                    if (fnextNode == eoutNode) {
                        break;
                    }
                    if (fnextNode.tagName == constants.PRE) {
                        fnextNode = fnextNode.nextElementSibling;
                        continue;
                    }
                    if (util.checkIstouNode(fnextNode)) {
                        if (fnextNode.tagName == constants.TABLE) {
                            this.specalNodeAddBtn(fnextNode, constants.TD, btnNode);
                        } else {
                            this.specalNodeAddBtn(fnextNode, constants.LI, btnNode);
                        }
                        fnextNode = fnextNode.nextElementSibling;
                    } else {

                        if (fnextNode.childNodes.length == 1) {
                            if (fnextNode.firstChild.tagName == btnNode.tagName) {
                                if (btnNode.getAttribute("style")) {
                                    util.setNodeStyle(fnextNode.firstChild, btnNode);
                                    util.delSameLabelOrCss(fnextNode.firstChild, btnNode);
                                    fnextNode = fnextNode.nextElementSibling;
                                    continue;
                                }
                            }
                            if (util.isSingleNodeCheck(fnextNode.firstChild)) {
                                fnextNode = fnextNode.nextElementSibling;
                                continue;
                            }
                            btnClone = btnNode.cloneNode();
                            btnClone.appendChild(fnextNode.firstChild);
                            fnextNode.appendChild(btnClone);
                        } else {
                            tempRange.setStartBefore(fnextNode.firstChild);
                            tempRange.setEndAfter(fnextNode.lastChild);
                            btnClone = this.cutRange(tempRange, btnNode.cloneNode());
                            fnextNode.appendChild(btnClone);
                        }
                        this.addRemoveSpecalLabel(btnClone, btnNode);
                        util.delSameLabelOrCss(btnClone, btnNode);
                        util.deleteAllChildrenEmptyNodes(fnextNode);
                        fnextNode = fnextNode.nextElementSibling;

                    }
                }

                if (eoutNode.tagName == constants.PRE) {
                    endNode = eNode;
                    endOffset = crange.endOffset;
                } else {
                    if (eoutNode.childNodes.length == 1 && util.isSingleNodeCheck(eoutNode.firstChild)) {
                        endNode = eoutNode;
                    } else {
                        // 处理最后部分
                        if (eNode == eoutNode && crange.endOffset == 0) {
                            endNode = eNode;
                            endOffset = crange.endOffset;
                        } else {
                            var fistText = util.getfirstTextNode(eoutNode.firstChild);
                            var efoutNode = util.getContainsStyleParentNode(fistText, btnNode);
                            var stempNode;
                            if (efoutNode) {
                                var espNode = util.getContainsStyleParentNode(eNode, btnNode);
                                if (efoutNode != espNode) {
                                    crange.setStartBefore(eoutNode.firstChild);
                                    isNeed = true;
                                } else {
                                    endNode = crange.endContainer;
                                    endOffset = crange.endOffset;
                                }
                            } else {
                                var f1, f2;
                                if (util.checkEincloudAll(crange)) {
                                    f1 = util.getSpecalParentNode(btnNode.tagName, fistText);
                                    f2 = util.getSpecalParentNode(btnNode.tagName, eNode);
                                    if (f1 && f1 == f2) {
                                        util.setNodeStyle(f1, btnNode);
                                        // 删除内部样式相同的样式名或标签
                                        util.delSameLabelOrCss(f1, btnNode);
                                        crange.setEndAfter(f1);
                                        endNode = crange.endContainer;
                                        endOffset = crange.endOffset;
                                    } else {
                                        crange.setStartBefore(eoutNode.firstChild);
                                        isNeed = true;
                                    }
                                } else {
                                    crange.setStartBefore(eoutNode.firstChild);
                                    isNeed = true;
                                }
                            }
                            if (isNeed) {
                                var insereNode = this.cutRange(crange, btnNode.cloneNode());
                                if (eoutNode.tagName == insereNode.firstChild.tagName) {
                                    // 处理<table><strong><table></</</的场景
                                    util.insertBefore(insereNode, eoutNode);
                                    eoutNode.remove();
                                } else {
                                    if (eoutNode.firstChild) {
                                        util.insertBefore(insereNode, eoutNode.firstChild);
                                    } else {
                                        eoutNode.appendChild(insereNode);
                                    }
                                }
                                util.insertAfter(enMark, util.getInnerOrBeforeTextNode(insereNode.lastChild));
                                this.addRemoveSpecalLabel(insereNode, btnNode);
                                util.deleteAllChildrenEmptyNodes(util.mergeNextNode(insereNode).parentNode, true);
                            }
                        }
                    }
                }
                // 设定选区
                if (startNode) {
                    trange.setStart(startNode, startOffset);
                } else {
                    trange.setStartAfter(stMark);
                    util.removeNode(stMark);
                }
                if (endNode) {
                    trange.setEnd(endNode, endOffset);
                } else {
                    trange.setEndBefore(enMark);
                    util.removeNode(enMark);
                }

                return trange;
            },
            /**
             * 针对一些不能全包的标签，如ol、ul、table
             *
             * @param dealNode
             * @param btnNode
             */
            addRemoveSpecalLabel: function (dealNode, btnNode) {
                var olList = util.getElementsByTagName(dealNode, constants.OL);
                var lrange = util.createRange();
                for (var l = 0; l < olList.length; l++) {
                    this.dealRemoveLabel(lrange, dealNode, btnNode, olList[l], util.getElementsByTagName(olList[l], constants.LI));
                }
                var ulList = util.getElementsByTagName(dealNode, constants.UL);
                for (var l = 0; ulList.length; l++) {
                    this.dealRemoveLabel(lrange, dealNode, btnNode, ulList[l], util.getElementsByTagName(ulList[l], constants.LI));
                }
                var tableList = util.getElementsByTagName(dealNode, constants.TABLE);
                for (var l = 0; tableList.length; l++) {
                    this.dealRemoveLabel(lrange, dealNode, btnNode, tableList[l], util.getElementsByTagName(tableList[l], constants.TD));
                }
                return dealNode;
            },
            /**
             * 处理不能全包的节点，如table li等
             * @param range
             * @param dealNode
             * @param btnNode
             * @param outNode
             * @param children
             */
            dealRemoveLabel: function (range, dealNode, btnNode, outNode, children) {
                range.setStartBefore(dealNode);
                range.setEndBefore(outNode);
                var cutNode = util.extractContents(range);
                util.insertBefore(cutNode, dealNode);
                util.insertBefore(outNode, dealNode);
                var addNode;
                for (var i = 0; i < children.length; i++) {
                    addNode = btnNode.cloneNode();
                    if (children[i].childNodes.length == 1 && children[i].firstChild.tagName == btnNode.tagName) {
                        util.setNodeStyle(children[i].firstChild, btnNode);
                        // 删除内部样式相同的样式名或标签
                        util.delSameLabelOrCss(children[i].firstChild, btnNode);
                    } else {
                        if (children[i].childNodes.length != 0) {
                            if (!util.checkIsOnlyBr(children[i])) {
                                util.removeChildrenToOtherNode(children[i], addNode);
                                children[i].appendChild(addNode);
                                // 删除内部样式相同的样式名或标签
                                util.delSameLabelOrCss(addNode, btnNode);
                            }
                        }
                    }
                }
            },
            /**
             * 给特殊节点添加标签
             *
             * @param node
             * @param tagName
             * @param btnNode
             */
            specalNodeAddBtn: function (node, tagName, btnNode) {
                var addNode;
                var children = util.getElementsByTagName(node, tagName);
                for (var i = 0; i < children.length; i++) {
                    addNode = btnNode.cloneNode();
                    if (children[i].childNodes.length == 1 && children[i].firstChild.tagName == btnNode.tagName) {
                        util.setNodeStyle(children[i].firstChild, btnNode);
                        // 删除内部样式相同的样式名或标签
                        util.delSameLabelOrCss(children[i].firstChild, btnNode);
                    } else {
                        if (children[i].childNodes.length != 0) {
                            if (!(children[i].childNodes.length == 1 && children[i].firstChild.tagName == constants.BR)) {
                                util.removeChildrenToOtherNode(children[i], addNode);
                                children[i].appendChild(addNode);
                                // 删除内部样式相同的样式名或标签
                                util.delSameLabelOrCss(addNode, btnNode);
                            }
                        }
                    }
                }
            },
            /**
             * 选区切分填充
             */
            cutRange: function (range, btnNode) {
                btnNode.appendChild(util.extractContents(range));
                // 删除内部样式相同的样式名或标签
                util.delSameLabelOrCss(btnNode, btnNode);
                util.dealInnerSerisSameTagNameNode(btnNode, btnNode);
                return btnNode;
            },
            /**
             * 取消标签
             */
            cancelLabel: function (name, styleName, styleValue) {
                var range = this.getRange();
                if (util.isEmptyObject(rangeTable.seleTdArray)) {
                    var range = this.getRange();
                    if (!this.resetNewRange(range)) {
                        return;
                    }
                    if (range.collapsed) {
                        this.cancelForOne(name, range, styleName, styleValue);
                    } else {
                        this.cancelForTwo(name, range, styleName, styleValue);
                    }
                } else {
                    this.cancelForTable(name, styleName, styleValue);
                }
            },
            /**
             * 选中td的取消标签
             *
             * @param name
             * @param styleName
             * @param styleValue
             */
            cancelForTable: function (name, styleName, styleValue) {
                var btnNode = util.createCustomNode(name);
                if (styleName) {
                    styles.setSpecalStyleValue(btnNode, styleName, styleValue);
                }
                for (var td in rangeTable.seleTdArray) {
                    // 删除内部样式相同的样式名或标签
                    util.delSameLabelOrCss(rangeTable.seleTdArray[td], btnNode);
                }
            },
            /**
             * 处理光标的去除标签按钮的场景 range 选区为光标 name 标签按钮名称
             */
            cancelForOne: function (name, range, styleName, styleValue) {
                var sNode = range.startContainer;
                var eNode = range.endContainer;
                // 分两种处理场景，1：新建标签，内部是一个空文本节点 2：内部不是空的
                if (util.isElementNode(sNode) && sNode.childNodes.length == 1 
                		&& ((util.isElementNode(sNode.firstChild) && sNode.firstChild.tagName == constants.BR)
                		|| (util.isTextNode(sNode.firstChild) && sNode.firstChild.data == constants.SPACE))) {
                    var pNode = util.getSpecalParentNode(name, sNode, styleName, styleValue);
                    if (styleName) {
                        if (pNode.getAttribute("style").split(";").length > 2) {
                            pNode.style[styleName] = null;
                            this.setRangeAfter(sNode.firstChild);
                            return;
                        }
                    }
                    if (util.sameNodeCompare(sNode, pNode)) {
                        // 如果节点中只有空字符串那么直接删除
                        if (pNode.innerHTML == constants.SPACE) {
                            // 如果父节点只有1个子节点，那么要补充br节点
                            if (pNode.parentElement.childNodes.length == 1) {
                                util.insertAfter(util.createCustomNode(constants.BR), pNode);
                            }
                            util.removeNode(pNode);
                        } else {
                            // 将选中的节点移动到外面
                            util.moveAllChildNodeToOut(pNode);
                        }
                        this.setTranRange(range);
                    } else {
                        // 将选中的节点移动到外面
                        util.moveAllChildNodeToOut(pNode);
                        if (!sNode.firstChild) {
                            var dd = util.createSpace(range);
                            sNode.appendChild(dd);
                        }
                        this.setRangeAfter(sNode.firstChild);
                    }
                    return;
                } else {
                    var pNode = util.getSpecalParentNode(name, sNode, styleName, styleValue);
                    var eRange = range.cloneRange();
                    eRange.setEndAfter(pNode.lastChild);
                    var nextNode = pNode.cloneNode();
                    var node1 = util.extractContents(eRange);
                    nextNode.appendChild(node1);
                    util.deleteAllChildrenEmptyNodes(nextNode.firstChild);
                    if (!util.checkIsEmpty(nextNode)) {
                        // 将后半部分补充回去
                        util.insertAfter(nextNode, pNode);
                    }
                    var sRange = range.cloneRange();
                    sRange.setStartBefore(pNode);
                    var rangeNode;
                    if (sNode.data == constants.SPACE) {
                        sRange.setEndBefore(sNode.parentNode);
                        rangeNode = sNode;
                    } else {
                        var dd = util.createSpace(range);
                        rangeNode = dd;
                        if (util.isElementNode(sNode)) {
                            sNode.appendChild(dd);
                        } else {
                            util.insertAfter(dd, sNode);
                        }
                        sRange.setEndBefore(dd);
                    }
                    var node2 = util.extractContents(sRange);
                    var preNode = node2.firstChild;
                    util.deleteAllChildrenEmptyNodes(preNode.firstChild);
                    if (!util.checkIsEmpty(preNode)) {
                        // 将前半部分补充回去
                        util.insertBefore(preNode, pNode);
                    }
                    if (styleName && pNode.getAttribute("style").split(";").length > 2) {
                        pNode.style[styleName] = null;
                    } else {
                        // 将选中的节点移动到外面
                        util.moveAllChildNodeToOut(pNode);
                    }
                    this.setRangeAfter(rangeNode);
                }
            },
            /**
             * 处理光标的去除标签按钮的场景 range 选区为区间 name 标签按钮名称
             */
            cancelForTwo: function (name, range, styleName, styleValue) {
                var sNode = range.startContainer;
                var eNode = range.endContainer;
                var pNode = util.getSpecalParentNode(name, sNode, styleName, styleValue);
                if (!pNode) {
                    return;
                }
                // 开始节点和结束节点都包含在同一个外置节点内的场景
                if (pNode == util.getSpecalParentNode(name, eNode, styleName, styleValue)) {
                    if (styleName) {
                        // 如果判断是否是选中了标签内的所有内容
                        if (pNode.firstChild == sNode && pNode.lastChild == eNode &&
                            range.startOffset == 0 && range.endOffset == eNode.length) {
                            if (pNode.getAttribute("style").split(";").length > 2) {
                                pNode.style[styleName] = null;
                                resetRange.setStartBefore(sNode);
                                resetRange.setEndAfter(eNode);
                                return;
                            }
                        }
                    }
                    // 获取选区前半部的内容
                    var sRange = range.cloneRange();
                    sRange.setStart(pNode, 0);
                    sRange.setEnd(sNode, range.startOffset);
                    // 获取选区后半部的内容
                    var eRange = range.cloneRange();
                    eRange.setStart(eNode, range.endOffset);
                    var lastNode = pNode.lastChild;
                    eRange.setEndAfter(lastNode);
                    var einsertNode = null;
                    if (!eRange.collapsed) {
                        // 截取后半部分的内容
                        var node3 = util.extractContents(eRange);
                        einsertNode = pNode.cloneNode();
                        einsertNode.appendChild(node3);
                    }
                    var finsertNode = null;
                    if (!sRange.collapsed) {
                        var node1 = util.extractContents(sRange);
                        finsertNode = pNode.cloneNode();
                        finsertNode.appendChild(node1);
                    }
                    // 插入前置书签节点
                    util.insertBefore(stMark, pNode);
                    util.deleteAllChildrenEmptyNodes(pNode.lastChild, true);

                    // 插入后置书签节点
                    util.insertAfter(enMark, pNode);
                    this.cancelDiff(pNode, styleName);
                    // 重置选区
                    resetRange.setStart(util.getfirstTextNode(stMark.nextSibling), 0);
                    if (util.isElementNode(enMark.previousSibling)) {
                        var lastNode = util.getInnerOrBeforeTextNode(enMark.previousSibling);
                        resetRange.setEnd(lastNode, lastNode.length);
                    } else {
                        var lastNode = enMark.previousSibling;
                        resetRange.setEnd(lastNode, lastNode.length);
                    }
                    if (finsertNode) {
                        util.deleteAllChildrenEmptyNodes(finsertNode.firstChild);
                        if (!util.checkIsEmpty(finsertNode)) {
                            // 将前半部分补充回去
                            util.insertBefore(finsertNode, stMark);
                        }
                    }
                    if (einsertNode) {
                        util.deleteAllChildrenEmptyNodes(einsertNode.firstChild);
                        if (!util.checkIsEmpty(einsertNode)) {
                            // 将后半部分补充回去
                            util.insertAfter(einsertNode, enMark);
                        }
                    }
                    util.removeNode(stMark);
                    util.removeNode(enMark);
                    // util.mergeNextNode(util.mergePreNode(finsertNode));
                } else {
                    // 开始节点和结束节点不处在同一个标签内的场景
                    var fNode = pNode;
                    var parent = range.commonAncestorContainer;
                    // 处理开始节点包含在标签内部的情况，需要对标签进行切割
                    if (fNode) {
                        if (util.checkIsSerisNodeChild(fNode, sNode, range, 1)) {
                            util.insertBefore(stMark, fNode);
                            fNode = this.cancelDiff(fNode, styleName);
                        } else {
                            var sRange = range.cloneRange();
                            sRange.setEndAfter(fNode);
                            var node1 = util.extractContents(sRange);
                            util.insertAfter(node1, fNode);
                            util.insertAfter(stMark, fNode);
                            this.cancelDiff(stMark.nextSibling, styleName);
                        }
                        if (util.checkIsEmpty(fNode)) {
                            util.removeNode(fNode);
                        }
                    } else {
                        util.insertBefore(stMark, sNode);
                    }
                    fNode = util.getSpecalParentNode(name, eNode, styleName, styleValue);
                    // 处理结束节点包含在标签内部的情况，需要对标签进行切割
                    if (fNode) {
                        if (util.checkIsSerisNodeChild(fNode, eNode, range, 2)) {
                            util.insertAfter(enMark, fNode);
                            fNode = this.cancelDiff(fNode, styleName);
                        } else {
                            var eRange = range.cloneRange();
                            eRange.setStartBefore(fNode);
                            var node2 = util.extractContents(eRange);
                            util.insertBefore(node2, fNode);
                            util.insertBefore(enMark, fNode);
                            this.cancelDiff(enMark.previousSibling, styleName);
                        }
                        if (util.checkIsEmpty(fNode)) {
                            util.removeNode(fNode);
                        }
                    } else {
                        if (eNode.id) {
                            util.insertAfter(enMark, util.getMinLastNode(eNode));
                        } else {
                            util.insertAfter(enMark, eNode);
                        }
                    }
                    util.forListNode(stMark, enMark, this.removeToOut(name, styleName), 1);
                    resetRange.setStartAfter(stMark);
                    resetRange.setEndBefore(enMark);
                    util.removeNode(stMark);
                    util.removeNode(enMark);
                    util.deleteAllChildrenEmptyNodes(parent, true);
                }
            },
            // 将节点的内容移到外面
            removeToOut: function (name, styleName) {
                return function (node) {
                    if (node.tagName == name.toUpperCase()) {
                        return service.cancelDiff(node, styleName);
                    } else {
                        return node
                    }
                }
            },
            // 取消操作
            cancelDiff: function (node, styleName) {
                if (styleName) {
                    if (node.style[styleName] != null && node.style[styleName] != constants.EMPTY) {
                        if (node.getAttribute("style").split(";").length > 2) {
                            node.style[styleName] = null;
                            return node;
                        } else {
                            var firstNode = node.firstChild;
                            // 将选中的节点移动到外面
                            util.moveAllChildNodeToOut(node);
                            return firstNode;
                        }
                    }
                } else {
                    var firstNode = node.firstChild;
                    // 将选中的节点移动到外面
                    util.moveAllChildNodeToOut(node);
                    return firstNode;
                }
                return node;
            },
// ----------------------------------------------添加标签功能结束-----------------------------------------
            // 回车事件处理
            enterKeyClick: function (e) {
                var range = this.getRange(), exNode, flag = true;
                // 针对父节点为li标签的节点
                var liNode = util.getSpecalParentNode(constants.LI, range.startContainer);
                if (liNode) {
                    if (!range.collapsed) {
                        util.rangeAreaDele(range);
                    }
                    /*
					 * 判断当前li的节点是否为空的节点 如果是空的，那么点击回车会创建div节点，放在当前div后面
					 * 如果不是空的，那么点击回车创建li节点放在之前的li后面
					 */
                    if (util.checkHasTextNode(liNode)) {
                        var tempRange = range.cloneRange();
                        var snode = tempRange.startContainer;
                        if (util.isElementNode(snode)) {
                            if (tempRange.startOffset == 0 && snode.tagName == constants.A) {
                                tempRange.setStartBefore(snode);
                                tempRange.collapse(true);
                                snode = tempRange.startContainer;
                            }
                            if (tempRange.startOffset == snode.childNodes.length && snode.tagName == constants.A) {
                                tempRange.setStartAfter(snode);
                                tempRange.collapse(true);
                                snode = tempRange.startContainer;
                            }
                            if (tempRange.startOffset == 0) {
                                util.insertBefore(util.createCustomNode(constants.BR), snode.firstChild);
                                tempRange.setStartAfter(snode.firstChild);
                            }
                            if (tempRange.startOffset == snode.childNodes.length) {
                                util.insertAfter(util.createCustomNode(constants.BR), snode.lastChild);
                            }
                        } else {
                            if (tempRange.startOffset == 0) {
                                util.insertBefore(util.createCustomNode(constants.BR), tempRange.startContainer);
                                tempRange.setStartAfter(snode.firstChild);
                            }
                            if (tempRange.startOffset == snode.length) {
                                util.insertAfter(util.createCustomNode(constants.BR), snode.lastChild);
                                tempRange.setStartBefore(snode.lastChild);
                            }
                        }
                        tempRange.setEndAfter(liNode);
                        var exNode = util.extractContents(tempRange);
                        util.insertAfter(exNode, liNode);
                        this.setRangeBefore(util.getMinNode(liNode.nextSibling));
                    } else {
                        var tempol = null;
                        if (liNode.nextSibling) {
                            tempol = liNode.parentNode.cloneNode();
                            while (liNode.nextSibling) {
                                tempol.appendChild(liNode.nextSibling);
                            }
                            util.insertAfter(tempol, liNode.parentNode);
                        }
                        var enterNode = util.createCustomNode(constants.DIV);
                        enterNode.appendChild(util.createCustomNode(constants.BR));
                        if (tempol) {
                            util.insertBefore(enterNode, tempol);
                        } else {
                            util.insertAfter(enterNode, liNode.parentNode);
                        }
                        if (liNode.parentNode.childNodes.length > 1) {
                            util.removeNode(liNode);
                        } else {
                            util.removeNode(liNode.parentNode);
                        }
                        this.setRangeBefore(enterNode.firstChild);
                    }
                    flag = false;
                }
                // 针对父节点为pre的情况
                var preNode = util.getSpecalParentNode(constants.PRE, range.startContainer);
                if (preNode) {
                    var brnode = util.createCustomNode(constants.BR);
                    if (range.startContainer.length == range.endOffset) {
                        var n = range.startContainer.nextSibling;
                        if (n == null || n.tagName == null) {
                            var b2node = util.createCustomNode(constants.BR);
                            this.insertNode(range, brnode);
                            util.insertBefore(b2node, brnode);
                        } else {
                            this.insertNode(range, brnode);
                        }
                    } else {
                        this.insertNode(range, brnode);
                    }
                    flag = false;
                    this.setRangeAfter(brnode);
                }
                // 针对光标在td中的时候
                var tableNode = util.getSpecalParentNode(constants.TABLE, range.startContainer);
                if (tableNode) {
                    if (!range.collapsed) {
                        util.rangeAreaDele(range);
                    }
                    var brnode = util.createCustomNode(constants.BR);
                    if (range.startContainer.innerHTML == constants.EMPTY) {
                        this.insertNode(range, brnode.cloneNode());
                    }
                    if (range.endContainer.childNodes.length == range.endOffset) {
                        this.insertNode(range, brnode.cloneNode());
                    }
                    this.insertNode(range, brnode);
                    flag = false;
                    this.setRangeAfter(brnode);
                }
                if (flag) {
                    resetRange = util.cutDiv(null, this.getRange());
                }
                exNode = resetRange.startContainer;
                if(window.getComputedStyle(ids.editorEditorDiv,null).height==window.getComputedStyle(ids.editorBody,null).height){
                	ids.editorContext.scrollTop +=exNode.offsetHeight;
                }else{
                	ids.editorBody.scrollTop +=exNode.offsetHeight;
                }
                e.preventDefault();
            },
            // 处理tab事件
            tabKeyClick: function (e) {
                var range = this.getRange();
                if (!range.collapsed) {
                    var foutNode = util.getOutParentNode(range.startContainer);
                    var loutNode = util.getOutParentNode(range.endContainer);
                    if (foutNode != loutNode) {
                        this.batchTab(foutNode, loutNode);
                        e.preventDefault();
                        return;
                    } else {
                        util.rangeAreaDele(range);
                    }
                }
                var span = util.createCustomNode(constants.SPAN);
                span.innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;";
                var textNode = span.firstChild;
                this.insertNode(range, textNode);
                this.setRangeAfter(textNode);
                e.preventDefault();
            },
            batchTab: function (fnode, lnode) {
                var tnode = fnode;
                while (tnode) {
                    this.appendOutFirst(tnode);
                    if (tnode != lnode) {
                        tnode = tnode.nextElementSibling;
                    } else {
                        tnode = null;
                    }
                }
            },
            appendOutFirst: function (tnode) {
                var span = util.createCustomNode(constants.SPAN);
                span.innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;";
                util.insertBefore(span.firstChild, tnode.firstChild);
            },
// -------------------------------------------针对选区的操作开始（本来针对选区的这部分放在util中，但选区是动态的，不好控制就放在服务中了）-----------------------------------
            // 获取选区
            getRange: function () {
                var range;
                // 获取当前获得焦点的元素，并判断当前获取焦点的是否是编辑器内
                if (document.activeElement.id == ids.editorContext.id) {
                    range = util.getRange();
                    if (ids.editorPaste.contains(range.startContainer)) {
                        range = resetRange;
                    }
                    if (!range) {
                        range = resetRange;
                    }
                } else {
                    range = resetRange;
                }
                // 避免开始选区出现在文本节点的最后位置，这样剪切会出现空节点
                if (range.startContainer.nodeType == 3) {
                    if (range.startOffset == range.startContainer.length) {
                        range.setStartAfter(range.startContainer);
                    }
                }
                if (range.endContainer.nodeType == 3) {
                    if (range.endOffset == 0) {
                        range.setEndBefore(range.endContainer);
                    }
                }
                //如果在父标签之间，那么重新设置下选区
                if (range.collapsed) {
                    if (util.isElementNode(range.startContainer) &&
                        range.startContainer.childNodes[range.startOffset]
                        && util.isElementNode(range.startContainer.childNodes[range.startOffset])) {
                        range.setStartBefore(util.getMinNode(range.startContainer.childNodes[range.startOffset]));
                        range.collapse(true);
                    }
                }
                return range;
            },
            // 选区替换
            setTranRange: function (range) {
                if (range) {
                    resetRange.setStart(range.startContainer, range.startOffset);
                    resetRange.setEnd(range.endContainer, range.endOffset);
                }
            },
            // 设置选区为之前
            setRangeBefore: function (node) {
                resetRange.setStartBefore(node);
                resetRange.setEndBefore(node);
            },
            // 设置选区为之后
            setRangeAfter: function (node) {
                resetRange.setStartAfter(node);
                resetRange.setEndAfter(node);
            },
            // 重置选区
            resetNewRange: function (range) {
                // 判断开始节点重置后，开始节点是否等于结束节点，如果是那么不再进行后续选区重置
                if (range.collapsed) {
                    if (util.isElementNode(range.startContainer)
                        && range.startContainer.childNodes[range.startOffset]
                        && util.isElementNode(range.startContainer.childNodes[range.startOffset])) {
                        range.setStartBefore(util.getMinNode(range.startContainer.childNodes[range.startOffset]));
                        range.collapse(true);
                    }
                    this.clearAndAddNewRange(range);
                    return true;
                }
                resetRange = range;
                var starNode = util.getFirstStartMinNode(range);
                starNode = range.startContainer;
                var endNode = util.getLastEndMinNode(range);
                endNode = range.endContainer;
                var firstTextNode;
                if (util.isElementNode(starNode)) {
                    if (range.startOffset == starNode.childNodes.length) {
                        firstTextNode = util.getNextTextNode(starNode, endNode);
                    } else if (util.isElementNode(starNode.childNodes[range.startOffset])) {
                        firstTextNode = util.getfirstTextNode(starNode.childNodes[range.startOffset]);
                        if (!firstTextNode || firstTextNode.nodeType != 3) {
                            firstTextNode = util.getNextTextNode(util.getMinNode(starNode.childNodes[range.startOffset]), endNode);
                        }
                    }
                }
                if (firstTextNode) {
                    if (firstTextNode.nodeType == 3) {
                        // 此处判断主要是针对选区中不存在文本节点，结束选区在文本节点的开始位置
                        if (range.endOffset == 0 && endNode.contains(firstTextNode)) {
                            return false;
                        } else {
                            range.setStartBefore(firstTextNode);
                        }
                    } else {
                        if (firstTextNode == endNode) {
                            return false;
                        }
                    }
                    starNode = firstTextNode;
                }
                // 判断开始节点重置后，开始节点是否等于结束节点，如果是那么不再进行后续选区重置
                if (range.collapsed) {
                    this.clearAndAddNewRange(range);
                    return;
                }
                var lastNode;
                if (util.isElementNode(endNode)) {
                    var lNode = endNode.childNodes[range.endOffset - 1];
                    if (range.endOffset == 0) {
                        lastNode = util.getBeforeTextNode(endNode, starNode);
                    } else if (util.isElementNode(lNode)) {
                        lastNode = util.getInnerOrBeforeTextNode(lNode);
                        if (!lastNode || lastNode.nodeType != 3) {
                            lastNode = util.getBeforeTextNode(util.getMinLastNode(lNode), starNode);
                        }
                    }
                }

                if (lastNode) {
                    if (lastNode.nodeType == 3) {
                        range.setEndAfter(lastNode);
                    }
                    endNode = lastNode;
                }
                if (util.isTextNode(starNode)) {
                    // 光标在文本节点最后的位置
                    if (starNode.length == range.startOffset) {
                        var firstNode = util.getNextTextNode(starNode, endNode);
                        range.setStartBefore(firstNode);
                    }
                }
                if (util.isTextNode(endNode)) {
                    if (range.endOffset == 0) {
                        lastNode = util.getBeforeTextNode(endNode, starNode);
                        range.setEndAfter(lastNode);
                    }
                }
                firstTextNode = util.getSpecalParentNode(constants.TD, starNode);
                lastNode = util.getSpecalParentNode(constants.TD, endNode);
                if (firstTextNode && lastNode) {
                    if (firstTextNode != lastNode) {
                        range.setStartBefore(util.getSpecalParentNode(constants.TABLE, starNode));
                        range.setEndAfter(util.getSpecalParentNode(constants.TABLE, endNode));
                    }
                } else if (firstTextNode) {
                    range.setStartBefore(util.getSpecalParentNode(constants.TABLE, starNode));
                } else if (lastNode) {
                    range.setEndAfter(util.getSpecalParentNode(constants.TABLE, endNode));
                }
                //针对全选的时候，选择选区处于外置节点的场景
                if (range.startContainer.id) {
                    range.setStartBefore(range.startContainer.childNodes[range.startOffset].firstChild);
                }
                //针对全选的时候，选择选区处于外置节点的场景
                if (range.endContainer.id) {
                    if (range.endOffset > 0) {
                        range.setEndAfter(range.endContainer.childNodes[range.endOffset - 1].lastChild);
                    } else {
                        range.setEndAfter(range.endContainer.childNodes[range.endOffset].lastChild);
                    }
                }
                this.clearAndAddNewRange(range);
                return true;
            },
            /**
             * 选区变换后校验按钮的状态
             */
            resetBtnStatus: function () {
                if (tempVar.isNeedResetTool) {
                    return;
                }
                clearTimeout(initTimer);
                initTimer = setTimeout(function () {
                    try {
                        //如果粘贴节点在编辑器中那么不允许进行按钮校验，会报错
                        if (ids.editorPaste.parentNode) {
                            return;
                        }
                        service.changePlace();
                    } catch (e) {
                        console.error("重置按钮捕获异常，选区进行了重置！", e);
                        allEvents._recoverData();
                    }
                }, 200);
            },
            /**
             * 清除原来的所有选区，添加最新选区 se:当前selection ran:最新的range
             */
            clearAndAddNewRange: function (range) {
                if (!range) {
                    range = this.getRange();
                }
                resetRange = range;
                util.setRange(range);
            },
            // 插入节点
            insertNode: function (range, node, flag) {
                // 如果是单标签节点，那么将选区放在节点之前
                if (util.isSingleNodeCheck(range.startContainer)) {
                    range.setStartBefore(range.startContainer);
                    range.setEndBefore(range.startContainer);
                }
                range.insertNode(node);
//		    	util.delEmptyNode(node.nextSibling,flag);
            },
            /**
             * 获取纯文本
             */
            getContentTxt: function (firstNode, lastNode) {
                var text = constants.EMPTY;
                util.forListNode(firstNode, lastNode, function (node) {
                    text += node.data;
                    return node;
                }, 3);
                return util.tranWord(text);
            },
            /**
             * 获取带格式文本
             */
            getPlainTxt: function () {
                tempDiv = util.createCustomNode(constants.DIV);
                tempDiv.innerHTML = editorContext.innerHTML;
                this.delStyle(tempDiv);
                return tempDiv.innerHTML;
            },
            /**
             * 设置编辑器为不可用
             */
            unUsedEdit: function () {
                // 检测当前是否可用
                if (tempVar.disable) {
                    return;
                }
                // 标记当前编辑器为不可用
                tempVar.disable = true;
                for (var tool in tools) {
                    // 是否允许按下
                    tools[tool].btnDownFlag = false;
                    // 是否可用
                    tools[tool].isEnable = false;
                }
                for (var tool in tools) {
                    // 设置按钮的按下状态
                    if (tools[tool].btnDownFlag) {
                        styles.btnChangeColor(tools[tool]);
                    } else {
                        styles.btnRecoverColor(tools[tool]);
                    }
                    // 设置按钮的可用状态
                    if (tools[tool].isEnable) {
                        styles.btnEnable(tools[tool]);
                    } else {
                        styles.btnDisable(tools[tool]);
                    }
                }
                editorContext.setAttribute("contenteditable", false);
            },
            /**
             * 恢复编辑器为可用
             */
            enableUsedEdit: function () {
                // 只有编辑器处于不可用状态才能执行恢复操作
                if (!tempVar.disable) {
                    return;
                }
                editorContext.setAttribute("contenteditable", true);
                editorContext.focus();
                for (var tool in tools) {
                    if (tool == ids.editorGo.id) {
                        if (njqHistory.historyIndex < (njqHistory.list.length - 1)) {
                            // 是否允许按下
                            tools[tool].btnDownFlag = false;
                            // 是否可用
                            tools[tool].isEnable = true;
                        } else {
                            // 是否允许按下
                            tools[tool].btnDownFlag = false;
                            // 是否可用
                            tools[tool].isEnable = false;
                        }
                        continue;
                    }
                    if (tool == ids.editorBack.id) {
                        // 后退按钮只有在有历史记录时才显示
                        if (njqHistory.list.length > 0 && njqHistory.historyIndex > 0) {
                            // 是否允许按下
                            tools[tool].btnDownFlag = false;
                            // 是否可用
                            tools[tool].isEnable = true;
                        } else {
                            // 是否允许按下
                            tools[tool].btnDownFlag = false;
                            // 是否可用
                            tools[tool].isEnable = false;
                        }
                        continue;
                    }
                    // 是否允许按下
                    tools[tool].btnDownFlag = false;
                    // 是否可用
                    tools[tool].isEnable = true;
                }
                for (var tool in tools) {
                    // 设置按钮的按下状态
                    if (tools[tool].btnDownFlag) {
                        styles.btnChangeColor(tools[tool]);
                    } else {
                        styles.btnRecoverColor(tools[tool]);
                    }
                    // 设置按钮的可用状态
                    if (tools[tool].isEnable) {
                        styles.btnEnable(tools[tool]);
                    } else {
                        styles.btnDisable(tools[tool]);
                    }
                }
                service.resetBtnStatus();
                tempVar.disable = false;
            },
            // 整理待上传的图片
            arrangeUpPic: function () {
//		    	upPicArray={};
                // 先获取出所有图片
                var imgs = util.getElementsByTagName(editorContext, "img");
                if (imgs.length == 0) {
                    return;
                }
                var reg = false;
                var ignoreUrlList;
                var regArray = [];
                if (!(userConfig.pic.ignoreSrc == "/")) {
                    // 获取所有需要过滤的网址
                    ignoreUrlList = userConfig.pic.ignoreSrc.split("|");
                    for (var i = 0; i < ignoreUrlList.length; i++) {
                        regArray.push(new RegExp("^" + ignoreUrlList[i]))
                    }
                }

                // 提出去需要上传的图片
                for (var i = 0; i < imgs.length; i++) {
                    if (imgs[i].getAttribute("picuplabel")) {
                        continue;
                    } else {
                        //判断网络地址的图片是否允许上传
                        if (userConfig.parsePicType == 2 && /^http/g.test(imgs[i].src)) {
                            continue;
                        }
                        var picuplabel = Math.random() + constants.EMPTY;
                        picuplabel = "pic" + picuplabel.substring(picuplabel.length - 6, picuplabel.length);
                        imgs[i].setAttribute("picuplabel", picuplabel);
                        if (regArray.length > 0) {
                            for (var j = 0; j < regArray.length; j++) {
                                if (regArray[j].test(imgs[i].src)) {
                                    reg = true;
                                    break;
                                }
                            }
                            if (!reg) {
                                upPicArray[picuplabel] = imgs[i].src;
                            }
                        } else {
                            upPicArray[picuplabel] = imgs[i].src;
                        }
                    }
                }
            },
            // 删除外置标签最后的br符
            removeLastBrLabel: function (range) {
                var pNodes = util.getOutNode(range);
                for (var i = 0; i < pNodes.length; i++) {
                    if (util.isBrLabel(pNodes[i].lastElementChild)) {
                        util.removeNode(pNodes[i].lastElementChild);
                    }
                }
            },
            //隐藏图片锚点并且删除图片
            hidePic: function () {
                if (!styles.checkIsHide(ids.picSele)) {
                    styles.hideStyleNode(ids.picSele);
                    var outNode = util.getOutParentNode(ids.picSele.bandNode);
                    ids.picSele.bandNode.remove();
                    util.deleteAllChildrenEmptyNodes(outNode);
                    if (util.checkIsEmpty(outNode)) {
                        outNode.appendChild(util.createCustomNode(constants.BR));
                    }
                }
            },
            hiPic: function () {
                styles.hideStyleNode(ids.picSele);
            }
        };
        var dlgs = ids.editorDlgDiv;
        // 构建弹框
        var makeDialog = njqEditor.makeDialog = {
            // 弹框基本初始化
            baseSet: function (text, node, isCenter) {
                var temp = util.createCustomNode(constants.DIV), top, left;
                temp.innerHTML = text;
                temp = temp.firstChild;
                temp.id = node.dlgId;
                temp.btnId = node.id;
                dlgs.appendChild(temp);
                if (isCenter) {
                    styles.setDialogOffset(temp,
                        (ids.editor.clientHeight - temp.offsetHeight) / 2,
                        (ids.editor.clientWidth - temp.offsetWidth) / 2);
                } else {
                    top = node.offsetTop + 30;
                    left = node.offsetLeft + 4;
                    styles.setDialogOffset(temp, top, left);
                }
                util.addCommonEventListener(temp, "mouseup", "_stopEvent", 5);
                util.addCommonEventListener(temp, "mousedown", "_stopEvent", 5);
                return temp;
            },
            // 设置标题的弹框
            _njqEditor_setTitle_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node);
                    styles.hideDialog(temp);
                    node.defaultVaule = "段落";
                    node.valueNode = node.firstElementChild.firstElementChild;
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_selectType", 2);
                    loadJs(node.id, node.dlgId, "fontTitle/fontTitle.js", fun);

                });
            },
            // 添加超链接弹框
            _njqEditor_ahref_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node, 1);
                    styles.showDialog(temp);
                    temp.unclose = true;
                    closeDialog(temp);
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_addHref", 2);
                    loadJs(node.id, node.dlgId, "ahref/ahref.js", fun);
                });
            },
            // 保存文件
            _njqEditor_saveSchedule_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node, 1);
                    styles.hideDialog(temp);
                    temp.unclose = true;
                    var dialogValue = util.getElementsByClassName(temp, "dialogValue")[0];
                    node.barNode = dialogValue;
                    loadJs(node.id, node.dlgId, "tip/tip.js", fun);
                });
            },
            // 自定义样式的弹框
            _njqEditor_custom_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node);
                    styles.hideDialog(temp);
                    node.defaultVaule = "特殊样式";
                    node.valueNode = node.firstElementChild.firstElementChild;
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_selectCustom", 2);
                    loadJs(node.id, node.dlgId, "customStyle/customStyle.js", fun);
                });
            },
            // 字体颜色弹框
            _njqEditor_fontColor_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node);
                    styles.hideDialog(temp);
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_setFontColor", 2);
                    loadJs(node.id, node.dlgId, "fontColor/fontColor.js", fun);
                });
            },
            // 背景颜色弹框
            _njqEditor_backGroundColor_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node);
                    styles.hideDialog(temp);
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_setBackGroundColor", 2);
                    loadJs(node.id, node.dlgId, "fontColor/fontColor.js", fun);
                });
            },
            // 字体类型弹框
            _njqEditor_setFontType_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node);
                    styles.hideDialog(temp);
                    node.defaultVaule = "arial";
                    node.valueNode = node.firstElementChild.firstElementChild;
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_selectFontType", 2);
                    loadJs(node.id, node.dlgId, "fontType/fontType.js", fun);
                });
            },
            // 字体大小弹框
            _njqEditor_setFontSize_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node);
                    styles.hideDialog(temp);
                    node.defaultVaule = "16px";
                    node.valueNode = node.firstElementChild.firstElementChild;
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_selectFontSize", 2);
                    loadJs(node.id, node.dlgId, "fontSize/fontSize.js", fun);
                });
            },
            // 有序列表弹框
            _njqEditor_orderList_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node);
                    styles.showDialog(temp);
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_selectOrderList", 2);
                    loadJs(node.id, node.dlgId, "orderList/orderList.js", fun);
                });
            },
            // 无序列表弹框
            _njqEditor_unList_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node);
                    styles.showDialog(temp);
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_selectunList", 2);
                    loadJs(node.id, node.dlgId, "unList/unList.js", fun);
                });
            },
            // 行前距弹框
            _njqEditor_beforeHeight_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node);
                    styles.hideDialog(temp);
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_beforeHeight", 2);
                    loadJs(node.id, node.dlgId, "beforeHeight/beforeHeight.js", fun);
                });
            },
            // 行后距弹框
            _njqEditor_afterHeight_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node);
                    styles.hideDialog(temp);
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_afterHeight", 2);
                    loadJs(node.id, node.dlgId, "beforeHeight/beforeHeight.js", fun);
                });
            },
            // 行间距弹框
            _njqEditor_lineHeight_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node);
                    styles.hideDialog(temp);
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_lineHeight", 2);
                    loadJs(node.id, node.dlgId, "lineHeight/lineHeight.js", fun);
                });
            },
            // 显示图片锚点
            _picSele: function (imgNode) {
                var node = ids.picSele;
                if (node) {
                    node.style.display = "block";
                    node.style.width = (imgNode.width - 1) + "px";
                    node.style.height = (imgNode.height - 1) + "px";
                    var imgPlace = util.getPlace(imgNode, node);
                    node.style.left = (imgPlace.offsetLeft - 1) + "px";
                    node.style.top = (imgPlace.offsetTop - 1) + "px";
                    node.bandNode = imgNode;
                    return;
                }
                loadPage("dialog/picAnchor/picAnchor.html", function (text) {
                    var temp = util.createCustomNode(constants.DIV);
                    if (userConfig.autoHeight) {
                        dlgs.appendChild(temp);
                    } else {
                        var body = ids.editorBody;
                        body.appendChild(temp);
                        body.style.position = "relative";

                    }
                    text = text.replace("${1}", (imgNode.width - 1)).replace("${2}", (imgNode.height - 1));
                    temp.innerHTML = text;
                    temp = temp.firstChild;
                    ids.picSele = temp;
                    var imgPlace = util.getPlace(imgNode, temp);
                    temp.style.left = (imgPlace.offsetLeft - 1) + "px";
                    temp.style.top = (imgPlace.offsetTop - 1) + "px";
                    temp.style.display = "block";
                    if (imgNode) {
                        temp.bandNode = imgNode;
                    }
                    var childNode;
                    var sizeList = temp.children;
                    for (var i = 0; i < sizeList.length; i++) {
                        childNode = sizeList[i];
                        childNode.index = i;
                        util.addCommonEventListener(childNode, "mousedown", "_picMouseDown", 5);
                        util.addCommonEventListener(childNode, "mouseup", "_mouseUp", 5);
                    }
                    util.addCommonEventListener(temp, "mouseup", "_mouseUp", 5);
                });
            },
            // 显示源代码
            _htmlSourceShow: function (btn, source) {
                var node = util.getElementsByClassName(editorNode, "codeCenterCenter")[0];
                if (node) {
                    node.innerHTML = source.innerHTML.replace(/\$/g, "&#36;");
                    styles.showStyleNode(ids.editorTip);
                    styles.hideStyleNode(editorContext);
                    styles.showStyleNode(node.parentNode);
                    allEvents._resetNum();
                    return node;
                }
                loadPage(btn.dlgUrl, function (text) {
                    var temp = util.createCustomNode(constants.DIV);
                    // 对于$符号要进行特殊处理 如果同时出现 $$ 那么将会消失掉后一个
                    // ，如果同时出现$&那么这两个字符会变成替换之前的字符
                    text = text.replace("${contextValue}", source.innerHTML.replace(/\$/g, "&#36;"));
                    temp.innerHTML = text;
                    temp = temp.firstChild;
                    temp.id = btn.dlgId;
                    var childList = temp.childNodes;
                    styles.hideStyleNode(editorContext);
                    var editorDiv = ids.editorEditorDiv
                    editorDiv.insertBefore(temp, editorContext);
                    var editNode = util.getElementsByClassName(editorNode, "codeCenterCenter")[0];
                    var height = editNode.offsetHeight;
                    editNode.beforeHeight = height;
                    var num = height / 15;
                    var leftDiv = constants.EMPTY, rightDiv = constants.EMPTY;
                    for (var i = 1; i <= num; i++) {
                        leftDiv += "<div>" + i + "</div>";
                        rightDiv += "<div>" + i + "</div>";
                    }
                    util.getElementsByClassName(editorNode, "codeCenterLeft")[0].innerHTML = leftDiv;
                    util.getElementsByClassName(editorNode, "codeCenterRight")[0].innerHTML = rightDiv;
                    util.addCommonEventListener(editNode, "keyup", "_resetNum", 5);
                    util.addCommonEventListener(editNode, "keydown", "_resetRangePlace", 5);
                    util.addCommonEventListener(editNode, "paste", "_htmlPaste", 5);
                });
            },
            // 预览文档弹框
            _viewDocShow: function (node) {
                var viewNode = ids.editorViewBtn;
                styles.hideStyleNode(toolBar);
                styles.hideStyleNode(ids.editorBody);
                if (viewNode) {
                    ids.editorViewContext.innerHTML = editorContext.innerHTML;
                    styles.showStyleNode(viewNode);
                } else {
                    loadPage(node.dlgUrl, function (text) {
                        var temp = util.createCustomNode(constants.DIV);
                        text = text.replace("${contextValue}", editorContext.innerHTML);
                        temp.innerHTML = text;
                        temp = temp.firstChild;
                        dlgs.appendChild(temp);
                        ids.editorViewBtn = temp;
                        ids.editorViewContext = util.getElementsByClassName(temp, "partOne")[0];
                        util.addCommonEventListener(util.getElementsByClassName(temp, "topdiv")[0], "dblclick", "_closeViewDoc", 5);
                    });
                }
            },
            // 选择模板
            _njqEditor_docModel_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node, editorContext.offsetHeight / 4, editorContext.offsetWidth / 5);
                    styles.showDialog(temp);
                    temp.unclose = true;
                    closeDialog(temp);
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_useModel", 2);
                    loadJs(node.id, node.dlgId, "model/model.js", fun);
                });
            },
            // 创建表格弹框
            _njqEditor_addTable_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node);
                    styles.showDialog(temp);
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_createTable", 2);
                    loadJs(node.id, node.dlgId, "table/table.js", fun);
                })
            },
            // 表情弹框
            _njqEditor_expression_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node, 1);
                    //此处比较特殊，由于加载了弹框，但里面的图片尚未加载导致未撑开，所以一开始设置了高度，然后在此处删除
                    temp.style.height = null;
                    styles.showDialog(temp);
                    temp.unclose = true;
                    closeDialog(temp);
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_expression", 2);
                    loadJs(node.id, node.dlgId, "emotion/emotion.js", fun);
                });
            },
            // 多图上传
            _njqEditor_morePics_dlg: function (node, fun) {
                var baseSet = this.baseSet;
                loadPage(node.dlgUrl, function (text) {
                    var temp = baseSet(text, node, 1);
                    styles.showDialog(temp);
                    temp.unclose = true;
                    closeDialog(temp);
                    util.addCommonEventListener(util.getElementsByClassName(temp, "dialogValue")[0], "click", "_insertPics", 2);
                    loadJs(node.id, node.dlgId, "morePics/morePics.js", fun);
                });
            }
        }

        var closeDialog = function (dialog) {
            var closeBtns = util.getElementsByClassName(dialog, "closeDialog");
            for (var i = 0; i < closeBtns.length; i++) {
                util.addCommonEventListener(closeBtns[i], "click", "_commonClose", 6, dialog, dialog.hideFun);
            }
        }
        // 加载html页面
        var loadPage = function (url, fun) {
            var xmlhttp;
            // 兼容 IE7+, Firefox, Chrome, Opera, Safari
            if (window.XMLHttpRequest) {
                xmlhttp = new XMLHttpRequest();
            } else {
                // 兼容IE6, IE5
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    var text = xmlhttp.responseText.match(/<body.*?>[\S\s]*?<\/body>/g)[0];
                    text = text.replace(/<body.*?>/g, constants.EMPTY).replace(/<\/body>/g, constants.EMPTY).trim();
                    fun(text);
                }
            }
            xmlhttp.open("GET", userConfig.url + url, true);
            xmlhttp.send();
        }
        // html中的js
        var loadJs = function (id, dlgId, src, fun) {
            // 用于定位到具体哪个弹框
            var sl = src.split("/");
            var key = sl[sl.length - 1];
            if (njqEditor.dialogIds[key]) {
                njqEditor.dialogIds[key].push(id + "&" + dlgId);
            } else {
                njqEditor.dialogIds[key] = [(id + "&" + dlgId)];
            }
            var hm = util.createCustomNode("script");
            hm.src = userConfig.url + "dialog/" + src;
            util.getElementsByTagName(document, 'head')[0].appendChild(hm);
            if (fun) {
                fun();
            }
        }
        var upContextToServer = function (data, fn) {
            if (!userConfig.saveWord.enable) {
                return;
            }
            if (tempVar.saveFlag) {
                if (fn) {
                    fn("已经保存过了", "saveFalse");
                }
                return;
            }
            //通过对时间轴进行校验，进行js防并发
//            if (userConfig.saveWord.isNeedTimes) {
//                if (!tempVar.timestamp) {
//                    //设置时间轴
//                    tempVar.timestamp = Date.parse(new Date()) / 1000;
//                } else {
//                    if (fn) {
//                        fn("正在保存，请稍后再试！", "saveFalse");
//                    }
//                    return;
//                }
//            }


//			if(!/^http/g.test(userConfig.wordUrl)){
//				fn("上传文章失败，请检查上传文章地址！","saveFalse");
//				return;
//			}

            if (sysConfig.customUpDocEvent) {
                sysConfig.customUpDocEvent(data, fn);
            } else {
                upDoc(data, fn);
            }
        }

        function upDoc(data, fn) {
            var xmlhttp;
            // 兼容 IE7+, Firefox, Chrome, Opera, Safari
            if (window.XMLHttpRequest) {
                xmlhttp = new XMLHttpRequest();
            } else {
                // 兼容IE6, IE5
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4) {
                    //无论上传成功还是失败都要清空时间轴
                    tempVar.timestamp = null;
                    if (xmlhttp.status == 200) {
                        //上传成功标志
                        tempVar.saveFlag = true;
                        var dd;
                        if (xmlhttp.responseText) {
                            dd = eval('(' + xmlhttp.responseText + ')');
                        }
                        //检查是否要清空文档
                        if (userConfig.saveWord.afterSaveDealType == 1) {
                            allEvents._clearAll();
                        }
                        if (fn) {
                            fn(dd, xmlhttp.status);
                        }
                    } else {
                        if (fn) {
                            fn("上传文章失败，请检查上传文章地址！", "saveFalse");
                        }
                    }
                }
            }
            xmlhttp.open("POST", userConfig.wordUrl, true);
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            var dt = constants.EMPTY;
            if (userConfig.saveWord.saveType == 2) {
                var array = service.separateData();
                dt += userConfig.saveWord.textName + "=" + array[0] + "&" +
                    userConfig.saveWord.cssName + "=" + array[1];
            } else {
                dt += userConfig.saveWord.textName + "=" + editorContext.innerHTML;
            }
            if (data) {
                dt += "&" + data;
            }
            //添加时间轴
            if (userConfig.saveWord.isNeedTimes) {
                dt += "&" + userConfig.saveWord.timestamp + "=" + tempVar.timestamp;
            }
            var passParam = ids.editorParamDiv.children[0].value;
            if (passParam) {
                dt += "&" + passParam;
            }
            xmlhttp.send(dt);
        }

        // 上传图片到服务器(二进制流、网络图片、图片data数据)，图片不防并发
        var upPicToServer = function (fun, data, fn) {
            if (!userConfig.pic.enable) {
                return;
            }
            if (userConfig.pic.upType == 3) {
                return;
            }
            // 先整理待上传的图片
            service.arrangeUpPic();
            if (util.isEmptyObject(upPicArray)) {
                if (fun) {
                    fun(data, fn);
                }
                return;
            }
            var formData = new FormData();
            // 然后批量上传图片
            for (var i in upPicArray) {
                formData.append(i, upPicArray[i]);
            }
            var xmlhttp;
            if (window.XMLHttpRequest) {
                xmlhttp = new XMLHttpRequest();
            } else {
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4) {
                    if (xmlhttp.status == 200) {
                        var obj;
                        if (xmlhttp.responseText) {
                            obj = eval('(' + xmlhttp.responseText + ')');
                            var imgs = util.getElementsByTagName(editorContext, "img");
                            for (var i in obj) {
                                for (var j = 0; j < imgs.length; j++) {
                                    if (imgs[j].getAttribute("picuplabel") == i) {
                                        imgs[j].src = obj[i];
                                        // 删除节点中的属性
                                        delete(upPicArray[i]);
                                    }
                                }
                            }
                        }
                        if (fun) {
                            fun(data, fn);
                        }
                    } else {
                        alert("请检查上传图片的地址");
                    }
                }
            }
            util.addEventListener(xmlhttp.upload, "progress", function (evt) {
                var loaded = evt.loaded; // 已上传的文件大小
                var allTotal = evt.total; // 总大小
//				 console.info(loaded+"--"+allTotal);
            });
            xmlhttp.open("POST", userConfig.pic.picSrc, true);
            xmlhttp.send(formData);
        }

        var customEvent = njqEditor.customEvent;
    })();

})();