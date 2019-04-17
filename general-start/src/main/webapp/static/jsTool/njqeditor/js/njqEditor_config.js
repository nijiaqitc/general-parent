(function() {
	/*----------------浏览器校验低版本不绑定事件开始--------------------*/
	var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
//    var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器
//    var isSafari = userAgent.indexOf("Safari") > -1; //判断是否Safari浏览器
    if (isIE) {
        var IE5 = IE55 = IE6 = IE7 = IE8 = false;
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if(fIEVersion<10){
        	console.error("抱歉，当前编辑器不支持低版本ie！");
        	return;
        }
    }
    /*----------------浏览器校验低版本不绑定事件结束--------------------*/
	/**
     * 构建步骤：
     * 1、设定工具栏按钮各类参数
     * 2、设定按钮事件样式
     * 3、  编辑器进行渲染
     *    各类弹框进行设定，在第一次点击按钮时进行初始化创建
     * 4、  创建监听事件
     *    创建事件服务
     *    创建公共调用方法
     * 5、将所有事件与编辑器进行绑定
     * 6、编辑器数据初始化
     */
	window.njqEditor = {
		// 系统自带配置及全局变量初始化，强烈不建议修改
		sysConfig : {
			// 是否是ie浏览器的状态
			ieFlag : window.getSelection ? true : false,
			// 历史记录
			njqHistory : {
				// 最大历史记录缓存
				maxLength : 12,
				historyIndex : -1,
				list : []
			},
			// 表格选区
			rangeTable : {},
			// 图片的选区
			rangePic : {},
			// 存放空字符，不同的浏览器所对应的空字符不一样
			spaceNode : null,
			// 外部标签
			parentIncludeNode : [ "DIV", "H1", "H2", "H3", "H4", "H5", "H6",
					"PRE", "UL", "OL", "TABLE" ],
			// 单标签节点名称
			singleNode : [ "BR", "HR", "IMG", "CANVAS" ],
			//可以进行融合的节点
			mergeLabel: [ "SPAN", "SUP", "SUB", "STRONG", "LABEL", "LEAN" ],
			// 需要忽略的节点
			ignoreNode : [ "BR", "HR", "IMG", "CANVAS", "PRE", "BOOKMARK" ],
			// 本编辑器所使用的所有节点，不在此处中的所有标签将被移除
			childIncludeNode : [ "DIV", "PRE", "H1", "H2", "H3", "H4", "H5",
					"H6", "TABLE", "TR", "TD", "UL", "OL", "LI", "A", "SPAN",
					"SUP", "SUB", "STRONG", "LEAN", "HR", "IMG", "BR", "CANVAS" ],
			//不在这些类型中的属性全部剔除
			removeAttr:["title","target","style","colspan","rowspan","align","href","face","src","width","height","picuplabel"],
			//要移除的属性
			removeStyle:["position","left","right","top","bottom","float"],
			//不允许使用的标签
			unStyleLabel:["LI"],
			// 存放编辑器中所使用到的id集合，避免到处乱写id，导致后期维护不方便
			ids : {
				editorDiv : "njqEditorDiv",
				editor : "njqEditor",
				editorContext : "njqEditor_context",
				editorBack : "njqEditor_back",
				editorGo : "njqEditor_go",
				editorTool : "njqEditor_toolDiv",
				editorBody : "njqEditor_body",
				editorWordCount : "njqEditor_wordCount",
				editorTopWordCount : "njqEditor_topWordCount",
				editorEditorDiv : "njqEditor_editorDiv",
				editorShadeBox : "njqEditor_shadeBox",
				editorDlgDiv : "njqEditor_dlg_div",
				editorTip : "njqEditor_tip",
				editorNameValue : "njqEditor_name_value",
				editorNumValue : "njqEditor_num_value",
				editorPaste:"njqEditor_paste",
				editorParamDiv:"njqEditor_paramDiv"
			},
			// 提示内容文案
			titleTip : {
				htmlSource : "温馨提示：本编辑器源码转换比较简单，未考虑太多场景，若无百分百把握请勿随意修改源码！"
			},
			//初始化标记
			initFinshFlag:false,
			errorLog:true,//是否打印错误日志
			//系统加载完成后执行
			finishEvent:function(fun){
				//如果编辑器已加载完成则立即执行
				if(njqEditor.sysConfig.initFinshFlag){
					if(fun){
						fun();						
					}else{
						if(njqEditor.sysConfig.waitEvent){
							njqEditor.sysConfig.waitEvent();							
						}
					}
				}else{
					if(fun){
						//否则进行等待
						njqEditor.sysConfig.waitEvent=fun;						
					}
				}
			}
		}
	}

	var editorDiv = njqEditor.editorNode = document.getElementById(njqEditor.sysConfig.ids.editorDiv);
	var env = editorDiv.getAttribute("env");
	// 根据不同的使用场景使用不同的用户配置
	if (env == 1) {
		/*-------------------------------------配置使用场景为1的配置-----------------------------------------------------*/
		// 用户自定义配置
		njqEditor.userConfig = {
			url : "/static/jsTool/njqeditor/",// 项目路径
			pic : {
				enable:true,//是否允许上传
				picSrc : "/jsTool/upload",// 图片上传地址
				upType : 1,// 上传方式，1：上传图片为临时图片，最后同文字一起上传到服务，2：上传图片立马传入到服务器，3:不上传图片
				ignoreSrc : "/",// 忽略的url地址，使用|进行多网址分隔，不想用忽略地址直接用“/”即可，比如引用本网站静态资源库的图片，但地址不同，不想上传这些地址是配置
				maxWidth:550//图片最大宽度限制
			},
			wordUrl : "/",// 内容上传地址
			parsePicType : 1,// 粘贴图片的方式，1:抓取并上传到服务器，2:不上传图片，使用网络地址
			parseTextType:1,//粘贴文本方式，1：带格式粘贴  2：无格式粘贴
			initContent : "",// 默认编辑器初始文本，如果不需要，直接设置为""即可
			indentValue : "24px",// 段落缩进位数
			wordCount : {
				isShow : true,// 是否使用计算字数功能 true：是 ，false：不展示
				top : true,// 是否展示上部的计算字数块，前提是isShow为true， true：展示上部的计数块 false 不展示上部的计数块
				bottom : true // 是否展示底部的计算字数块，前提是isShow为true， true：展示底部的计数块 false 不展示底部的计数块
			},
			initHeight : 600,// 初始化编辑器编写区域高度,不设置高度，默认为600
			autoHeight : false,// 是否自动长高,默认true，若为true那么将不设置编写区域高度
			initShow : true,// 初始化后是否显示，默认true ，若为true则显示，false不显示
			isToolScrollTop : false,// 工具条到达顶部时，是否需要置顶，true:是 false:否(不建议开启，有卡顿现象)
			saveWord : {
				enable:true,//是否允许上传
				saveType : 2,// 保存类型，1:直接整个文档上传 2:将文档拆分成2部分，一部分为样式，一部分为文本
				breakType : 2,// 分离样式类型，1:不带标签<div>11</div> （适合前台进行组合） 2:带标签 <div labelIndex="0,0,1">11</div> 样式表0,0,0=color:red; （适合后台进行组合）
				isTranClass:true,//是否需要将css转换成class
				tranClassName:["aaa.css"],//转化样式文件名
				afterSaveDealType : 1,// 保存后处理方式，1：清空当前文档 2：不处理
				textName:"text",//保存内容的参数名
				cssName:"css",//保存样式的参数名
				isNeedTimes:true,//是否需要时间轴，不要是进行页面防并发
				timestamp:"timestamp"//时间轴变量名
			},
			// 所使用的按钮
			useBtn : [ "njqEditor_html", "njqEditor_save", "njqEditor_back",
					"njqEditor_go", "njqEditor_clearAll",
					"njqEditor_allFullSceen", "njqEditor_seleAll",
					"njqEditor_viewDoc", "njqEditor_strong", "njqEditor_lean",
					"njqEditor_underLine", "njqEditor_around",
					"njqEditor_deleLine", "njqEditor_upChar",
					"njqEditor_downChar", "njqEditor_fontColor",
					"njqEditor_backgroundColor", "njqEditor_charToLow",
					"njqEditor_charToUp", "njqEditor_addHref",
					"njqEditor_cancelHref", "njqEditor_fontType",
					"njqEditor_fontSize", "njqEditor_alignLeft",
					"njqEditor_alignCenter", "njqEditor_alignRight",
					"njqEditor_alignBoth", "njqEditor_textIndent",
					"njqEditor_beforeHeight", "njqEditor_afterHeight",
					"njqEditor_lineHeight", "njqEditor_orderList",
					"njqEditor_unList", "njqEditor_clearDecode",
					"njqEditor_setTitle", "njqEditor_custom",
					"njqEditor_table", "njqEditor_delTable",
					"njqEditor_table_mergeCells", "njqEditor_table_deleteRow",
					"njqEditor_table_deleteCol", "njqEditor_table_insertRow",
					"njqEditor_table_insertCol", "njqEditor_table_breakTd",
					"njqEditor_upPic", "njqEditor_upManyPics",
					"njqEditor_dataCode", "njqEditor_docModel",
					"njqEditor_cutLine", "njqEditor_monthDay",
					"njqEditor_dayTime", "njqEditor_expression" ]
		}
		// ------------------------------------------------------------------------------------------
	} else if (env == 2) {
		/*-------------------------------------配置使用场景为2的配置-----------------------------------------------------*/
		// 用户自定义配置
		njqEditor.userConfig = {
			url : "/static/jsTool/njqeditor/",// 项目路径
			pic : {
				enable:true,//是否允许上传
				picSrc : "/grab/editPicUp",// 图片上传地址
				upType : 2,// 上传方式，1：上传图片为临时图片，最后保存同文字一起上传到服务，2：上传图片立马传入到服务器，3:不上传图片
				ignoreSrc : "http://www.njqityun.com|http://image.njqityun.com",// 忽略的url地址，使用|进行多网址分隔，不想用忽略地址直接用“/”即可，比如引用本网站静态资源库的图片，但地址不同，不想上传这些地址是配置
				maxWidth:550//图片最大宽度限制
			},
			wordUrl : "/",// 内容上传地址
			parsePicType : 1,// 粘贴图片的方式，1:抓取并上传到服务器，2:不上传图片，使用网络地址
			parseTextType:1,//粘贴文本方式，1：带格式粘贴  2：无格式粘贴
			initContent : "",// 默认编辑器初始文本，如果不需要，直接设置为""即可
			indentValue : "24px",// 段落缩进位数
			wordCount : {
				isShow : true,// 是否使用计算字数功能 true：是 ，false：不展示
				top : true,// 是否展示上部的计算字数块，前提是isShow为true， true：展示上部的计数块 false 不展示上部的计数块
				bottom : true// 是否展示底部的计算字数块，前提是isShow为true， true：展示底部的计数块 false 不展示底部的计数块
			},
			initHeight : 600,// 初始化编辑器编写区域高度,不设置高度，默认为600
			autoHeight : false,// 是否自动长高,默认true，若为true那么将不设置编写区域高度
			initShow : true,// 初始化后是否显示，默认true ，若为true则显示，false不显示
			isToolScrollTop : false,// 工具条到达顶部时，是否需要置顶，true:是 false:否(不建议开启，有卡顿现象)
			saveWord : {
				enable:true,//是否允许上传
				saveType : 2,// 保存类型，1:直接整个文档上传 2:将文档拆分成2部分，一部分为样式，一部分为文本
				breakType : 2,// 分离样式类型，1:不带标签<div>11</div> （适合前台进行组合 2:带标签 <div labelIndex="0,0,1">11</div> 样式表0,0,0=color:red; （适合后台进行组合）
				isTranClass:false,//是否需要将css转换成class
				tranClassName:["aaa.css"],//转化样式文件名
				afterSaveDealType : 2,// 保存后处理方式，1：清空当前文档 2：不处理
				textName:"text",//保存内容的参数名
				cssName:"css",//保存样式的参数名
				isNeedTimes:true,//是否需要时间轴，不要是进行页面防并发
				timestamp:"timestamp"//时间轴变量名
			},
			// 所使用的按钮
			useBtn : [ "njqEditor_html", "njqEditor_save", "njqEditor_back",
						"njqEditor_go", "njqEditor_clearAll",
						"njqEditor_allFullSceen", "njqEditor_seleAll",
						"njqEditor_viewDoc", "njqEditor_strong", "njqEditor_lean",
						"njqEditor_underLine", "njqEditor_around",
						"njqEditor_deleLine", "njqEditor_upChar",
						"njqEditor_downChar", "njqEditor_fontColor",
						"njqEditor_backgroundColor", "njqEditor_charToLow",
						"njqEditor_charToUp", "njqEditor_addHref",
						"njqEditor_cancelHref", "njqEditor_fontType",
						"njqEditor_fontSize", "njqEditor_alignLeft",
						"njqEditor_alignCenter", "njqEditor_alignRight",
						"njqEditor_alignBoth", "njqEditor_textIndent",
						"njqEditor_beforeHeight", "njqEditor_afterHeight",
						"njqEditor_lineHeight", "njqEditor_orderList",
						"njqEditor_unList", "njqEditor_clearDecode",
						"njqEditor_setTitle", "njqEditor_custom",
						"njqEditor_table", "njqEditor_delTable",
						"njqEditor_table_mergeCells", "njqEditor_table_deleteRow",
						"njqEditor_table_deleteCol", "njqEditor_table_insertRow",
						"njqEditor_table_insertCol", "njqEditor_table_breakTd",
						"njqEditor_upPic", "njqEditor_upManyPics",
						"njqEditor_dataCode", "njqEditor_docModel",
						"njqEditor_cutLine", "njqEditor_monthDay",
						"njqEditor_dayTime", "njqEditor_expression" ]
		}
		// ------------------------------------------------------------------------------------------
	} else if (env == 3) {
		/*-------------------------------------配置使用场景为3的配置-----------------------------------------------------*/
		// 用户自定义配置
		njqEditor.userConfig = {
			url : "/static/jsTool/njqeditor/",// 项目路径
			pic : {
				enable:true,//是否允许上传
				picSrc : "/grab/editPicUp",// 图片上传地址
				upType : 2,// 上传方式，1：上传图片为临时图片，最后保存同文字一起上传到服务，2：上传图片立马传入到服务器，3:不上传图片
				ignoreSrc : "http://www.njqityun.com|http://image.njqityun.com",// 忽略的url地址，使用|进行多网址分隔，不想用忽略地址直接用“/”即可，比如引用本网站静态资源库的图片，但地址不同，不想上传这些地址是配置
				maxWidth:550//图片最大宽度限制
			},
			wordUrl : "editToSave",// 内容上传地址
			parsePicType : 1,// 粘贴图片的方式，1:抓取并上传到服务器，2:不上传图片，使用网络地址
			parseTextType:1,//粘贴文本方式，1：带格式粘贴  2：无格式粘贴
			initContent : "",// 默认编辑器初始文本，如果不需要，直接设置为""即可
			indentValue : "24px",// 段落缩进位数
			wordCount : {
				isShow : true,// 是否使用计算字数功能 true：是 ，false：不展示
				top : true,// 是否展示上部的计算字数块，前提是isShow为true， true：展示上部的计数块 false 不展示上部的计数块
				bottom : true// 是否展示底部的计算字数块，前提是isShow为true， true：展示底部的计数块 false 不展示底部的计数块
			},
			initHeight : 600,// 初始化编辑器编写区域高度,不设置高度，默认为600
			autoHeight : false,// 是否自动长高,默认true，若为true那么将不设置编写区域高度
			initShow : true,// 初始化后是否显示，默认true ，若为true则显示，false不显示
			isToolScrollTop : false,// 工具条到达顶部时，是否需要置顶，true:是 false:否(不建议开启，有卡顿现象)
			saveWord : {
				enable:true,//是否允许上传
				saveType : 1,// 保存类型，1:直接整个文档上传 2:将文档拆分成2部分，一部分为样式，一部分为文本
				breakType : 2,// 分离样式类型，1:不带标签<div>11</div> （适合前台进行组合 2:带标签 <div labelIndex="0,0,1">11</div> 样式表0,0,0=color:red; （适合后台进行组合）
				isTranClass:false,//是否需要将css转换成class
				tranClassName:["aaa.css"],//转化样式文件名
				afterSaveDealType : 2,// 保存后处理方式，1：清空当前文档 2：不处理
				textName:"text",//保存内容的参数名
				cssName:"css",//保存样式的参数名
				isNeedTimes:true,//是否需要时间轴，不要是进行页面防并发
				timestamp:"timestamp"//时间轴变量名
			},
			// 所使用的按钮
			useBtn : [ "njqEditor_html", "njqEditor_save", "njqEditor_back",
						"njqEditor_go", "njqEditor_clearAll",
						"njqEditor_allFullSceen", "njqEditor_seleAll",
						"njqEditor_viewDoc", "njqEditor_strong", "njqEditor_lean",
						"njqEditor_underLine", "njqEditor_around",
						"njqEditor_deleLine", "njqEditor_upChar",
						"njqEditor_downChar", "njqEditor_fontColor",
						"njqEditor_backgroundColor", "njqEditor_charToLow",
						"njqEditor_charToUp", "njqEditor_addHref",
						"njqEditor_cancelHref", "njqEditor_fontType",
						"njqEditor_fontSize", "njqEditor_alignLeft",
						"njqEditor_alignCenter", "njqEditor_alignRight",
						"njqEditor_alignBoth", "njqEditor_textIndent",
						"njqEditor_beforeHeight", "njqEditor_afterHeight",
						"njqEditor_lineHeight", "njqEditor_orderList",
						"njqEditor_unList", "njqEditor_clearDecode",
						"njqEditor_setTitle", "njqEditor_custom",
						"njqEditor_table", "njqEditor_delTable",
						"njqEditor_table_mergeCells", "njqEditor_table_deleteRow",
						"njqEditor_table_deleteCol", "njqEditor_table_insertRow",
						"njqEditor_table_insertCol", "njqEditor_table_breakTd",
						"njqEditor_upPic", "njqEditor_upManyPics",
						"njqEditor_dataCode", "njqEditor_docModel",
						"njqEditor_cutLine", "njqEditor_monthDay",
						"njqEditor_dayTime", "njqEditor_expression" ]
		}
		// ------------------------------------------------------------------------------------------
	} else if (env == 4) {
		/*-------------------------------------配置使用场景为3的配置-----------------------------------------------------*/
		// 用户自定义配置
		njqEditor.userConfig = {
			url : "/static/jsTool/njqeditor/",// 项目路径
			pic : {
				enable:true,//是否允许上传
				picSrc : "/grab/editPicUp",// 图片上传地址
				upType : 2,// 上传方式，1：上传图片为临时图片，最后保存同文字一起上传到服务，2：上传图片立马传入到服务器，3:不上传图片
				ignoreSrc : "http://www.njqityun.com|http://image.njqityun.com",// 忽略的url地址，使用|进行多网址分隔，不想用忽略地址直接用“/”即可，比如引用本网站静态资源库的图片，但地址不同，不想上传这些地址是配置
				maxWidth:550//图片最大宽度限制
			},
			wordUrl : "editToSave",// 内容上传地址
			parsePicType : 1,// 粘贴图片的方式，1:抓取并上传到服务器，2:不上传图片，使用网络地址
			parseTextType:2,//粘贴文本方式，1：带格式粘贴  2：无格式粘贴
			initContent : "",// 默认编辑器初始文本，如果不需要，直接设置为""即可
			indentValue : "24px",// 段落缩进位数
			wordCount : {
				isShow : false,// 是否使用计算字数功能 true：是 ，false：不展示
				top : true,// 是否展示上部的计算字数块，前提是isShow为true， true：展示上部的计数块 false 不展示上部的计数块
				bottom : true// 是否展示底部的计算字数块，前提是isShow为true， true：展示底部的计数块 false 不展示底部的计数块
			},
			initHeight : 200,// 初始化编辑器编写区域高度,不设置高度，默认为600
			autoHeight : false,// 是否自动长高,默认true，若为true那么将不设置编写区域高度
			initShow : true,// 初始化后是否显示，默认true ，若为true则显示，false不显示
			isToolScrollTop : false,// 工具条到达顶部时，是否需要置顶，true:是 false:否(不建议开启，有卡顿现象)
			saveWord : {
				enable:true,//是否允许上传
				saveType : 1,// 保存类型，1:直接整个文档上传 2:将文档拆分成2部分，一部分为样式，一部分为文本
				breakType : 2,// 分离样式类型，1:不带标签<div>11</div> （适合前台进行组合 2:带标签 <div labelIndex="0,0,1">11</div> 样式表0,0,0=color:red; （适合后台进行组合）
				isTranClass:false,//是否需要将css转换成class
				tranClassName:["aaa.css"],//转化样式文件名
				afterSaveDealType : 2,// 保存后处理方式，1：清空当前文档 2：不处理
				textName:"text",//保存内容的参数名
				cssName:"css",//保存样式的参数名
				isNeedTimes:true,//是否需要时间轴，不要是进行页面防并发
				timestamp:"timestamp"//时间轴变量名
			},
			// 所使用的按钮
			useBtn : ["njqEditor_strong", "njqEditor_lean","njqEditor_clearDecode","njqEditor_dataCode","njqEditor_addHref" ]
		}
		// ------------------------------------------------------------------------------------------
	} else {
		/*-------------------------------------不配置使用场景的默认配置-----------------------------------------------------*/
		// 用户自定义配置
		njqEditor.userConfig = {
			url : "/static/jsTool/njqeditor/",// 项目路径
			pic : {
				enable:true,//是否允许上传
				picSrc : "/grab/editPicUp",// 图片上传地址
				upType : 2,// 上传方式，1：上传图片为临时图片，最后保存同文字一起上传到服务，2：上传图片立马传入到服务器，3:不上传图片
				ignoreSrc : "http://www.njqityun.com|http://image.njqityun.com",// 忽略的url地址，使用|进行多网址分隔，不想用忽略地址直接用“/”即可，比如引用本网站静态资源库的图片，但地址不同，不想上传这些地址是配置
				maxWidth:550//图片最大宽度限制
			},
			wordUrl : "/yxl/saveKnowledge",// 内容上传地址
			parsePicType : 1,// 粘贴图片的方式，1:抓取并上传到服务器，2:不上传图片，使用网络地址
			parseTextType:1,//粘贴文本方式，1：带格式粘贴  2：无格式粘贴
			initContent : "",// 默认编辑器初始文本，如果不需要，直接设置为""即可
			indentValue : "24px",// 段落缩进位数
			wordCount : {
				isShow : true,// 是否使用计算字数功能 true：是 ，false：不展示
				top : true,// 是否展示上部的计算字数块，前提是isShow为true， true：展示上部的计数块 false 不展示上部的计数块
				bottom : true// 是否展示底部的计算字数块，前提是isShow为true， true：展示底部的计数块 false 不展示底部的计数块
			},
			initHeight : 600,// 初始化编辑器编写区域高度,不设置高度，默认为600
			autoHeight : false,// 是否自动长高,默认true，若为true那么将不设置编写区域高度
			initShow : true,// 初始化后是否显示，默认true ，若为true则显示，false不显示
			isToolScrollTop : false,// 工具条到达顶部时，是否需要置顶，true:是 false:否(不建议开启，有卡顿现象)
			saveWord : {
				enable:true,//是否允许上传
				saveType : 2,// 保存类型，1:直接整个文档上传 2:将文档拆分成2部分，一部分为样式，一部分为文本
				breakType : 2,// 分离样式类型，1:不带标签<div>11</div> （适合前台进行组合 2:带标签 <div labelIndex="0,0,1">11</div> 样式表0,0,0=color:red; （适合后台进行组合）
				isTranClass:true,//是否需要将css转换成class
				tranClassName:["aaa.css"],//转化样式文件名
				afterSaveDealType : 2,// 保存后处理方式，1：清空当前文档 2：不处理
				textName:"text",//保存内容的参数名
				cssName:"css",//保存样式的参数名
				isNeedTimes:true,//是否需要时间轴，不要是进行页面防并发
				timestamp:"timestamp"//时间轴变量名
			},
			// 所使用的按钮
			useBtn : [ "njqEditor_html", "njqEditor_save", "njqEditor_back",
						"njqEditor_go", "njqEditor_clearAll",
						"njqEditor_allFullSceen", "njqEditor_seleAll",
						"njqEditor_viewDoc", "njqEditor_strong", "njqEditor_lean",
						"njqEditor_underLine", "njqEditor_around",
						"njqEditor_deleLine", "njqEditor_upChar",
						"njqEditor_downChar", "njqEditor_fontColor",
						"njqEditor_backgroundColor", "njqEditor_charToLow",
						"njqEditor_charToUp", "njqEditor_addHref",
						"njqEditor_cancelHref", "njqEditor_fontType",
						"njqEditor_fontSize", "njqEditor_alignLeft",
						"njqEditor_alignCenter", "njqEditor_alignRight",
						"njqEditor_alignBoth", "njqEditor_textIndent",
						"njqEditor_beforeHeight", "njqEditor_afterHeight",
						"njqEditor_lineHeight", "njqEditor_orderList",
						"njqEditor_unList", "njqEditor_clearDecode",
						"njqEditor_setTitle", "njqEditor_custom",
						"njqEditor_table", "njqEditor_delTable",
						"njqEditor_table_mergeCells", "njqEditor_table_deleteRow",
						"njqEditor_table_deleteCol", "njqEditor_table_insertRow",
						"njqEditor_table_insertCol", "njqEditor_table_breakTd",
						"njqEditor_upPic", "njqEditor_upManyPics",
						"njqEditor_dataCode", "njqEditor_docModel",
						"njqEditor_cutLine", "njqEditor_monthDay",
						"njqEditor_dayTime", "njqEditor_expression" ]
		}
		// ------------------------------------------------------------------------------------------
	}
	//加载配置的时候立即撑开div高度，避免一些特殊场景
	editorDiv.style.height=njqEditor.userConfig.initHeight+"px";
	var loadPage = function() {
		//加载按钮配置
		loadScript(njqEditor.userConfig.url+"js/njqEditor_toolConfig.js", function() {
			//加载工具类
			loadScript(njqEditor.userConfig.url+"js/njqEditor_util.js", function() {
				//加载样式配置 
				loadScript(njqEditor.userConfig.url+"js/njqEditor_styleConfig.js", function() {
					//加载模板
					loadScript(njqEditor.userConfig.url+"js/njqEditor_model.js", function() {
					});						
				});
			});
		});
	}
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
	
	var unEvent=editorDiv.getAttribute("unEvent");
	//当前加载是否需要事件
	if(!unEvent){
		loadPage();		
	}

})()
