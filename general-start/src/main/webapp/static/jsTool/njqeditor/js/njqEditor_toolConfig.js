(function(){
	njqEditor.toolConfig={
		tools:{
			njqEditor_html:{
				title:"html",
				defaultClass:"toolBtnBackImg toolBtnHtml",
				downClass:"toolBtnHtmlDown",
				hoverClass:"toolBtnHtmlHover",
				//初始状态是否可用，true为是
				isEnable:true,
				dlgId:"njqEditor_htmlCode",
				dlgUrl:"dialog/htmlSource/htmlSource.html",
				btnType:1,
				event:{
					click:"_html",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_save:{
				title:"保存",
				defaultClass:"toolBtnBackImg toolBtnSave",
				hoverClass:"toolBtnSaveHover",
				enableClass:"toolBtnClearAllEnable",
				initDialog:true,
				//初始状态是否可用，true为是
				isEnable:true,
				needModel:1,
				dlgId:"njqEditor_saveSchedule_dlg",
	            dlgUrl:"dialog/tip/tip.html",
				btnType:1,
				event:{
					click:"_save",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_back:{
				title:"后退",
				defaultClass:"toolBtnBackImg toolBtnBack",
				hoverClass:"toolBtnBackHover",
				enableClass:"toolBtnBackEnable",
				//是否允许撤销0为不允许
				needCache:0,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:1,
				event:{
					click:"_back",
					mouseenter :"_hover",
					mouseleave:"_out"
				},
				//是否适用pre标签
				inPre:true,
				isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"3"//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点3：历史记录
				},
				checkfun:["checkRuleHistoryBack"],
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_go:{
				title:"前进",
				defaultClass:"toolBtnBackImg toolBtnGo",
				hoverClass:"toolBtnGoHover",
				enableClass:"toolBtnGoEnable",
				needCache:0,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:1,
				event:{
					click:"_go",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//是否适用pre标签
				inPre:true,
				isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"3"//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点3：历史记录
				},
				checkfun:["checkRuleHistoryGo"],
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_clearAll:{
				title:"清空文档",
				defaultClass:"toolBtnBackImg toolBtnClearAll",
				hoverClass:"toolBtnClearAllHover",
				enableClass:"toolBtnClearAllEnable",
				//初始状态是否可用，true为是
				isEnable:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:1,
				//需要加入历史记录
				needHistroy:true,
				event:{
					click:"_clearAll",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_allFullSceen:{
				title:"全屏",
				defaultClass:"toolBtnBackImg toolBtnAllFullSceen",
				downClass:"toolBtnAllFullSceenDown",
				hoverClass:"toolBtnAllFullSceenHover",
				changeClass:"toolBtnAllFullSceenChange",
				enableClass:"toolBtnGoEnable",
				//初始状态是否可用，true为是
				isEnable:true,
				btnType:1,
				event:{
					click:"_allFullSceen",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_seleAll:{
				title:"全选",
				defaultClass:"toolBtnBackImg toolBtnSeleAll",
				hoverClass:"toolBtnSeleAllHover",
				enableClass:"toolBtnSeleAllEnable",
				//初始状态是否可用，true为是
				isEnable:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:1,
				event:{
					click:"_seleAll",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_viewDoc:{
				title:"预览",
				defaultClass:"toolBtnBackImg toolBtnViewDoc",
				hoverClass:"toolBtnViewDocHover",
				enableClass:"toolBtnViewDocEnable",
				//初始状态是否可用，true为是
				isEnable:true,
				dlgId:"njqEditor_viewBtn",
				dlgUrl:"dialog/viewDoc/viewDoc.html",
				btnType:1,
				event:{
					click:"_viewDoc",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_strong:{
				title:"加粗",
				defaultClass:"toolBtnBackImg toolBtnStrong",
				downClass:"toolBtnStrongDown",
				hoverClass:"toolBtnStrongHover",
				//是否允许按钮按下
				isLabelBtn:true,
				labelName:"strong",
				//初始状态是否可用，true为是
				isEnable:true,
				//是否可置为按下状态
				downflag:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:2,
				//需要加入历史记录
				needHistroy:true,
				isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"strong"
				},
				checkfun:["checkRuleInTag"],
				event:{
					click:"_addSpecialLabel",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_lean:{
				title:"斜体",
				defaultClass:"toolBtnBackImg toolBtnLean",
				downClass:"toolBtnLeanDwon",
				hoverClass:"toolBtnLeanHover",
				isLabelBtn:true,
				labelName:"lean",
				//初始状态是否可用，true为是
				isEnable:true,
				//是否可置为按下状态
				downflag:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:2,
				//需要加入历史记录
				needHistroy:true,
				isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"lean"
				},
				checkfun:["checkRuleInTag"],
				event:{
					click:"_addSpecialLabel",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_underLine:{
				title:"下划线",
				defaultClass:"toolBtnBackImg toolBtnUnderLine",
				downClass:"toolBtnUnderLineDwon",
				hoverClass:"toolBtnUnderLineHover",
				labelName:"span",
				//初始状态是否可用，true为是
				isEnable:true,
				//是否可置为按下状态
				downflag:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:2,
				//需要加入历史记录
				needHistroy:true,
				isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"",
					style:{textDecoration:"underline"},
					attr:{}
				},
				checkfun:["checkRuleInStyle"],
				event:{
					click:"_underLine",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_around:{
				title:"字符边框",
				defaultClass:"toolBtnBackImg toolBtnAround",
				downClass:"toolBtnAroundDwon",
				hoverClass:"toolBtnAroundHover",
				labelName:"span",
				//初始状态是否可用，true为是
				isEnable:true,
				//是否可置为按下状态
				downflag:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:2,
				//需要加入历史记录
				needHistroy:true,
				isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					style:{border:"1px solid rgb(0, 0, 0)"}
				},
				checkfun:["checkRuleInStyle"],
				event:{
					click:"_around",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_deleLine:{
				title:"删除线",
				defaultClass:"toolBtnBackImg toolBtnDeleLine",
				downClass:"toolBtnDeleLineDwon",
				hoverClass:"toolBtnDeleLineHover",
				labelName:"span",
				//初始状态是否可用，true为是
				isEnable:true,
				//是否可置为按下状态
				downflag:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:2,
				//需要加入历史记录
				needHistroy:true,
				isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					style:{textDecoration:"line-through"}
				},
				checkfun:["checkRuleInStyle"],
				event:{
					click:"_deleLine",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_upChar:{
				title:"上标",
				defaultClass:"toolBtnBackImg toolBtnUpChar",
				downClass:"toolBtnUpCharDwon",
				hoverClass:"toolBtnUpCharHover",
				isLabelBtn:true,
				labelName:"sup",
				//初始状态是否可用，true为是
				isEnable:true,
				//是否可置为按下状态
				downflag:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:2,
				//需要加入历史记录
				needHistroy:true,
				isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"sup"
				},
				checkfun:["checkRuleInTag"],
				event:{
					click:"_addSpecialLabel",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_downChar:{
				title:"下标",
				defaultClass:"toolBtnBackImg toolBtnDownChar",
				downClass:"toolBtnDownCharDwon",
				hoverClass:"toolBtnDownCharHover",
				isLabelBtn:true,
				labelName:"sub",
				//初始状态是否可用，true为是
				isEnable:true,
				//是否可置为按下状态
				downflag:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:2,
				//需要加入历史记录
				needHistroy:true,
				isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"sub"
				},
				checkfun:["checkRuleInTag"],
				event:{
					click:"_addSpecialLabel",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_fontColor:{
				title:"字体颜色",
				defaultClass:"btnDiv",
				hoverClass:"toolBtnFontColorHover",
				labelName:"span",
	            dlgId:"njqEditor_fontColor_dlg",
				dlgUrl:"dialog/fontColor/fontColor.html",
	            btnType:2,
	            //需要加入历史记录
				needDlgHistroy:true,
	            //是否使用自定义内容填充按钮区域
	            cus:true,
	            //初始状态是否可用，true为是
	            isEnable:true,
	            //是否需要立即初始化弹框
	            initDialog:true,
	            //点击按钮是否需要重置按钮状态
	            isNeedResetTool:true,
	            //是否有右边向下箭头
	            hasRightBtn:true,
	            //是否需要校验
	            isNeedCheck:true,
	            checkType:{
					type:"3"//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点 3:只需要进行基本校验如插入图片
				},
				checkfun:["checkRuleBase"],
				event:{
					click:"_setFontColor,_showDialog",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:2
			},
			njqEditor_backgroundColor:{
				title:"背景颜色",
				defaultClass:"btnDiv",
				hoverClass:"toolBtnBackgroundColorHover",
				labelName:"span",
				//初始状态是否可用，true为是
				isEnable:true,
				initDialog:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				//是否有右边向下箭头
				hasRightBtn:true,
	            dlgId:"njqEditor_backGroundColor_dlg",
	            dlgUrl:"dialog/fontColor/fontColor.html",
	            btnType:2,
	            //需要加入历史记录
	            needDlgHistroy:true,
	            cus:true,
	            isNeedCheck:true,
	            checkType:{
					type:"3"//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点 3:只需要进行基本校验如插入图片
				},
				checkfun:["checkRuleBase"],
				event:{
					click:"_setBackGroundColor,_showDialog",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:2
			},
			njqEditor_charToLow:{
				title:"转小写",
				defaultClass:"toolBtnBackImg toolBtnCharToLow",
				hoverClass:"toolBtnCharToLowHover",
				//初始状态是否可用，true为是
				isEnable:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:2,
				//需要加入历史记录
				needHistroy:true,
				event:{
					click:"_charToLow",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_charToUp:{
				title:"转大写",
				defaultClass:"toolBtnBackImg toolBtnCharToUp",
				hoverClass:"toolBtnCharToUpHover",
				//初始状态是否可用，true为是
				isEnable:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:2,
				//需要加入历史记录
				needHistroy:true,
				event:{
					click:"_charToUp",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_addHref:{
				title:"添加超链接",
				defaultClass:"toolBtnBackImg toolBtnAddHref",
				hoverClass:"toolBtnAddHrefHover",
				//初始状态是否可用，true为是
				isEnable:true,
				//弹框是否需要遮罩层
				needModel:true,
				dlgId:"njqEditor_ahref_dlg",
	            dlgUrl:"dialog/ahref/ahref.html",
				btnType:2,
				//需要加入历史记录
				needDlgHistroy:true,
				initDialog:true,
				isNeedCheck:true,
				checkType:{
					type:"3"//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点 3:只需要进行基本校验如插入图片
				},
				checkfun:["checkRuleBase"],
				event:{
					click:"_clickHrefBtn",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:3
			},
			njqEditor_cancelHref:{
				title:"取消超链接",
				defaultClass:"toolBtnBackImg toolBtnCancelHref",
				hoverClass:"toolBtnCancelHrefHover",
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:2,
				//需要加入历史记录
				needHistroy:true,
				isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"a"
				},
				checkfun:["checkRuleOutTag"],
				event:{
					click:"_cancelHref",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_fontType:{
	            title:"字体类型",
	            cus:true,
	            defaultClass:"toolBtnParaType",
	            hoverClass:"toolBtnFontTypeHover",
	            labelName:"span",
	            //初始状态是否可用，true为是
	            isEnable:true,
	            dlgId:"njqEditor_setFontType_dlg",
	            dlgUrl:"dialog/fontType/fontType.html",
	            btnType:2,
	            //需要加入历史记录
	            needDlgHistroy:true,
	            initDialog:true,
	            isNeedCheck:true,
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					styleName:"fontFamily"
				},
				checkfun:["checkRuleSetValueStyle"],
				event:{
					click:"_showDialog,_showDialog",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:3
	        },
			njqEditor_fontSize:{
	            title:"字体大小",
	            cus:true,
	            defaultClass:"toolBtnParaType",
	            hoverClass:"toolBtnFontSizeHover",
	            labelName:"span",
	            //初始状态是否可用，true为是
	            isEnable:true,
	            dlgId:"njqEditor_setFontSize_dlg",
	            dlgUrl:"dialog/fontSize/fontSize.html",
	            btnType:2,
	            //需要加入历史记录
	            needDlgHistroy:true,
	            initDialog:true,
	            isNeedCheck:true,
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					styleName:"fontSize"
				},
				checkfun:["checkRuleSetValueStyle"],
				event:{
					click:"_showDialog,_showDialog",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:3
	        },
			njqEditor_alignLeft:{
				title:"居左",
				defaultClass:"toolBtnBackImg toolBtnAlignLeft",
				downClass:"toolBtnAlignLeftDown",
				hoverClass:"toolBtnAlignLeftHover",
				//初始状态是否可用，true为是
				isEnable:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				//是否可置为按下状态
				downflag:true,
				btnType:3,
				//需要加入历史记录
				needHistroy:true,
				//是否适用pre标签
				inPre:true,
				isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					style:{textAlign:"left"}
				},
				//在这段落居左中右默认展示这个
				isDefault:true,
				checkfun:["checkRuleOutNodeStyle"],
				event:{
					click:"_alignLeft",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_alignCenter:{
				title:"居中",
				defaultClass:"toolBtnBackImg toolBtnAlignCenter",
				downClass:"toolBtnAlignCenterDown",
				hoverClass:"toolBtnAlignCenterHover",
				//初始状态是否可用，true为是
				isEnable:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				//是否可置为按下状态
				downflag:true,
				btnType:3,
				//需要加入历史记录
				needHistroy:true,
				isNeedCheck:true,
				//是否适用pre标签
				inPre:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					style:{textAlign:"center"}
				},
				checkfun:["checkRuleOutNodeStyle"],
				event:{
					click:"_alignCenter",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_alignRight:{
				title:"居右",
				defaultClass:"toolBtnBackImg toolBtnAlignRight",
				downClass:"toolBtnAlignRightDown",
				hoverClass:"toolBtnAlignRightHover",
				//初始状态是否可用，true为是
				isEnable:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				//是否可置为按下状态
				downflag:true,
				btnType:3,
				//需要加入历史记录
				needHistroy:true,
				isNeedCheck:true,
				//是否适用pre标签
				inPre:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					style:{textAlign:"right"}
				},
				checkfun:["checkRuleOutNodeStyle"],
				event:{
					click:"_alignRight",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_alignBoth:{
				title:"两边对齐",
				defaultClass:"toolBtnBackImg toolBtnAlignBoth",
				downClass:"toolBtnAlignBothDown",
				hoverClass:"toolBtnAlignBothHover",
				//初始状态是否可用，true为是
				isEnable:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				//是否可置为按下状态
				downflag:true,
				btnType:3,
				//需要加入历史记录
				needHistroy:true,
				isNeedCheck:true,
				//是否适用pre标签
				inPre:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					style:{textAlign:"justify"}
				},
				checkfun:["checkRuleOutNodeStyle"],
				event:{
					click:"_alignBoth",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_textIndent:{
				title:"首行缩进",
				defaultClass:"toolBtnBackImg toolBtnTextIndent",
				downClass:"toolBtnTextIndentDown",
				hoverClass:"toolBtnTextIndentHover",
				//初始状态是否可用，true为是
				isEnable:true,
				//是否可置为按下状态
				downflag:true,
				//需要加入历史记录
				needHistroy:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:3,
				isNeedCheck:true,
				//是否适用pre标签
				inPre:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					style:{textIndent: "24px"}
				},
				checkfun:["checkRuleOutNodeStyle"],
				event:{
					click:"_textIndent",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_beforeHeight:{
				title:"段前距",
				defaultClass:"toolBtnBackImg beforeHeightDivSize",
				hoverClass:"toolBtnBeforeHeightHover",
				//初始状态是否可用，true为是
				isEnable:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				//是否有右边向下箭头
				hasRightBtn:true,
				//需要加入历史记录
				needDlgHistroy:true,
	            dlgId:"njqEditor_beforeHeight_dlg",
	            dlgUrl:"dialog/beforeHeight/beforeHeight.html",
				btnType:3,
				initDialog:true,
				isNeedCheck:true,
				//是否适用pre标签
				inPre:true,
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					styleName:"marginTop"
				},
				checkfun:["checkRuleDialogValue"],
				event:{
					click:"_beforeHeight,_showDialog",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:2
			},
			njqEditor_afterHeight:{
				title:"段后距",
				defaultClass:"toolBtnBackImg afterHeightDivSize",
				hoverClass:"toolBtnAfterHeightHover",
				//初始状态是否可用，true为是
				isEnable:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				//是否有右边向下箭头
				hasRightBtn:true,
	            dlgId:"njqEditor_afterHeight_dlg",
	            dlgUrl:"dialog/beforeHeight/beforeHeight.html",
				btnType:3,
				//需要加入历史记录
				needDlgHistroy:true,
				initDialog:true,
				isNeedCheck:true,
				//是否适用pre标签
				inPre:true,
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					styleName:"marginBottom"
				},
				checkfun:["checkRuleDialogValue"],
				event:{
					click:"_afterHeight,_showDialog",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:2
			},
			njqEditor_lineHeight:{
				title:"行间距",
				defaultClass:"toolBtnBackImg lineHeightDivSize",
				hoverClass:"toolBtnLineHeightHover",
				//初始状态是否可用，true为是
				isEnable:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				//是否有右边向下箭头
				hasRightBtn:true,
	            dlgId:"njqEditor_lineHeight_dlg",
	            dlgUrl:"dialog/lineHeight/lineHeight.html",
				btnType:3,
				//需要加入历史记录
				needDlgHistroy:true,
				initDialog:true,
				isNeedCheck:true,
				//是否适用pre标签
				inPre:true,
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					styleName:"lineHeight"
				},
				checkfun:["checkRuleDialogValue"],
				event:{
					click:"_lineHeight,_showDialog",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:2
			},
			njqEditor_orderList:{
				title:"有序列表",
				defaultClass:"toolBtnBackImg orderList",
				downClass:"toolBtnIdentifierListDown",
				hoverClass:"toolBtnIdentifierListHover",
				//初始状态是否可用，true为是
				isEnable:true,
				//是否可置为按下状态
				downflag:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				labelName:"ol",
				//是否有右边向下箭头
				hasRightBtn:true,
				dlgId:"njqEditor_orderList_dlg",
				dlgUrl:"dialog/orderList/orderList.html",
				btnType:3,
				//需要加入历史记录
				needDlgHistroy:true,
				isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"ol"
				},
				checkfun:["checkRuleInTag"],
				event:{
					click:"_selectOrderList,_showDialog",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:2
			},
			njqEditor_unList:{
				title:"无序列表",
				defaultClass:"toolBtnBackImg unListDivSize",
				downClass:"toolBtnUnListDown",
				hoverClass:"toolBtnUnListHover",
				//初始状态是否可用，true为是
				isEnable:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				labelName:"ul",
				//是否有右边向下箭头
				hasRightBtn:true,
				//是否可置为按下状态
				downflag:true,
	            dlgId:"njqEditor_unList_dlg",
	            dlgUrl:"dialog/unList/unList.html",
				btnType:3,
				//需要加入历史记录
				needDlgHistroy:true,
				isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"ul"
				},
				checkfun:["checkRuleInTag"],
				event:{
					click:"_selectunList,_showDialog",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:2
			},
			njqEditor_clearDecode:{
				title:"清除格式",
				defaultClass:"toolBtnBackImg toolBtnClearDecode",
				hoverClass:"toolBtnClearDecodeHover",
				//初始状态是否可用，true为是
				isEnable:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
				btnType:3,
				//需要加入历史记录
				needHistroy:true,
				event:{
					click:"_clearDecode",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
			},
			njqEditor_setTitle:{
				title:"设置标题",
				cus:true,
				defaultClass:"toolBtnParaType",
				hoverClass:"toolBtnIdentifierListHover",
				//初始状态是否可用，true为是
				isEnable:true,
				dlgId:"njqEditor_setTitle_dlg",
				dlgUrl:"dialog/fontTitle/fontTitle.html",
				btnType:3,
				//需要加入历史记录
				needDlgHistroy:true,
				initDialog:true,
				isNeedCheck:true,
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:true
				},
				checkfun:["checkRuleOutNodeTag"],
				event:{
					click:"_showDialog,_showDialog",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:3
			},
			njqEditor_custom:{
				title:"特殊样式",						
				//是否需要自定义
				cus:true,
				defaultClass:"toolBtnParaType",
				hoverClass:"toolBtnIdentifierListHover",
				//初始状态是否可用，true为是
				isEnable:true,
				labelName:"span",
				dlgId:"njqEditor_custom_dlg",
				dlgUrl:"dialog/customStyle/customStyle.html",
				btnType:3,
				//需要加入历史记录
				needDlgHistroy:true,
				initDialog:true,
				isNeedCheck:true,
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					attr:"label"
				},
				checkfun:["checkRuleSetValueManyStyleList"],
				event:{
					click:"_showDialog,_showDialog",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:3
			},
			njqEditor_table:{
	            title:"添加表格",
	            defaultClass:"toolBtnBackImg toolBtnTable",
	            hoverClass:"toolBtnTableHover",
	            enableClass:"toolBtnTableEnable",
	            labelName:"table",
	            //初始状态是否可用，true为是
	            isEnable:true,
	            dlgId:"njqEditor_addTable_dlg",
				dlgUrl:"dialog/table/table.html",
	            btnType:4,
	            //需要加入历史记录
	            needDlgHistroy:true,
	            isNeedCheck:true,
	            checkType:{
	            	type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"table"
				},
				checkfun:["checkRuleNotAllowTag"],
				event:{
					click:"_showDialog",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:3
	        },
	        njqEditor_delTable:{
	            title:"删除表格",
	            defaultClass:"toolBtnBackImg toolBtnDelTable",
	            hoverClass:"toolBtnDelTableHover",
	            enableClass:"toolBtnDelTableEnable",
	            labelName:"table",
	            //点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
	            btnType:4,
	            //需要加入历史记录
				needHistroy:true,
	            isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"2",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"table"
				},
				checkfun:["checkRuleSameNode"],
				event:{
					click:"_delTable",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
	        },
			njqEditor_table_mergeCells:{
	            title:"合并单元格",
	            defaultClass:"toolBtnBackImg toolBtnTableMergeCells",
	            hoverClass:"toolBtnTableMergeCellsHover",
	            enableClass:"toolBtnTableMergeCellsEnable",
	            //点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
	            btnType:4,
	            //需要加入历史记录
				needHistroy:true,
	            isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"2",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"td"
				},
				checkfun:["checkRuleTableMer"],
				event:{
					click:"_tableMergeCells",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
	        },
	        njqEditor_table_breakTd:{
	            title:"拆分单元格",
	            defaultClass:"toolBtnBackImg toolBtnTableBreakTd",
	            hoverClass:"toolBtnTableBreakTdHover",
	            enableClass:"toolBtnTableBreakTdEnable",
	            //点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
	            btnType:4,
	         	//需要加入历史记录
				needHistroy:true,
	            isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"2",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					attr:{
						colSpan:1,
						rowSpan:1
					}
				},
				checkfun:["checkRuleTableBreak"],
				event:{
					click:"_tableBreakTd",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
	        },
	        njqEditor_table_insertRow:{
	            title:"插入行",
	            defaultClass:"toolBtnBackImg toolBtnTableInsertRow",
	            hoverClass:"toolBtnTableInsertRowHover",
	            enableClass:"toolBtnTableInsertRowEnable",
	            btnType:4,
	            //需要加入历史记录
				needHistroy:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
	            isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"2",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"td",
					maxLength:10
				},
				checkfun:["checkRuleTableTr"],
				event:{
					click:"_tableInsertRow",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
	        },
	        njqEditor_table_deleteRow:{
	            title:"删除行",
	            defaultClass:"toolBtnBackImg toolBtnTableDeleteRow",
	            hoverClass:"toolBtnTableDeleteRowHover",
	            enableClass:"toolBtnTableDeleteRowEnable",
	            //点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
	            btnType:4,
	            //需要加入历史记录
				needHistroy:true,
	            isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"2",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"td"
				},
				checkfun:["checkRuleSameNode"],
				event:{
					click:"_tableDeleteRow",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
	        },
	        njqEditor_table_insertCol:{
	            title:"插入列",
	            defaultClass:"toolBtnBackImg toolBtnTableInsertCol",
	            hoverClass:"toolBtnTableInsertColHover",
	            enableClass:"toolBtnTableInsertColEnable",
	            //点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
	            btnType:4,
	            //需要加入历史记录
				needHistroy:true,
	            isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"2",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"td",
					maxLength:10
				},
				checkfun:["checkRuleTableTd"],
				event:{
					click:"_tableInsertCol",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
	        },
	        njqEditor_table_deleteCol:{
	            title:"删除列",
	            defaultClass:"toolBtnBackImg toolBtnTableDeleteCol",
	            hoverClass:"toolBtnTableDeleteColHover",
	            enableClass:"toolBtnTableDeleteColEnable",
	            btnType:4,
	            //需要加入历史记录
				needHistroy:true,
				//点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
	            isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"2",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"td"
				},
				checkfun:["checkRuleSameNode"],
				event:{
					click:"_tableDeleteCol",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
	        },
	        njqEditor_upPic:{
	            title:"上传图片",
	            cus:true,
	            defaultClass:"btnDiv",
	            hoverClass:"toolBtnUpPicHover",
	            enableClass:"toolBtnUpPicEnable",
	            //初始状态是否可用，true为是
	            isEnable:true,
				dlgUrl:"dialog/picAnchor/picAnchor.html",
				btnType:5,
				//需要加入历史记录
				needHistroy:true,
				isNeedCheck:true,
				checkType:{
					type:"3"//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点 3:只需要进行基本校验如插入图片
				},
				checkfun:["checkRuleBase"],
				event:{
					click:"_imageUpClick",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
	        },
	        njqEditor_upManyPics:{
				title:"多图上传",
				defaultClass:"toolBtnBackImg toolBtnManyPics",
				hoverClass:"toolBtnManyPicsHover",
				//初始状态是否可用，true为是
				isEnable:true,
				needModel:true,
				dlgId:"njqEditor_morePics_dlg",
	            dlgUrl:"dialog/morePics/morePics.html",
				btnType:5,
				//需要加入历史记录
				needDlgHistroy:true,
				isNeedCheck:true,
				checkType:{
					type:"3"//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点 3:只需要进行基本校验如插入图片
				},
				checkfun:["checkRuleBase"],
				event:{
					click:"_clickUpManyPicsBtn",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:3
			},
	        njqEditor_dataCode:{
	        	title:"代码",
	            defaultClass:"toolBtnBackImg toolBtnDataCode",
	            hoverClass:"toolBtnDataCodeHover",
	            enableClass:"toolBtnDataCodeEnable",
	            labelName:"pre",
	            //初始状态是否可用，true为是
	            isEnable:true,
	            //点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
	            btnType:5,
	            //需要加入历史记录
				needHistroy:true,
	            isNeedCheck:true,
	            checkType:{
	            	type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"td"
				},
				checkfun:["checkRuleNotAllowTag"],
				event:{
					click:"_dataCode",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
	        },
	        njqEditor_docModel:{
	        	title:"模板",
	            defaultClass:"toolBtnBackImg toolBtnDocModel",
	            hoverClass:"toolBtnDocModelHover",
	            enableClass:"toolBtnDocModelEnable",
	            needModel:true,
	            //初始状态是否可用，true为是
	            isEnable:true,
	            dlgId:"njqEditor_docModel_dlg",
	            dlgUrl:"dialog/model/model.html",
	            btnType:5,
	            //需要加入历史记录
	            needDlgHistroy:true,
				event:{
					click:"_docModel",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:3
	        },
	        njqEditor_cutLine:{
	        	title:"分隔线",
	            defaultClass:"toolBtnBackImg toolBtnCutLine",
	            hoverClass:"toolBtnCutLineHover",
	            enableClass:"toolBtnCutLineEnable",
	            //初始状态是否可用，true为是
	            isEnable:true,
	            //点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
	            btnType:5,
	            //需要加入历史记录
				needHistroy:true,
				isNeedCheck:true,
				//分别对下面几种类型进行检测
				checkType:{
					type:"1",//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点
					label:"td"
				},
				checkfun:["checkRuleNotAllowTag"],
				event:{
					click:"_cutLine",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
	        },
	        njqEditor_monthDay:{
	        	title:"日期",
	            defaultClass:"toolBtnBackImg toolBtnMonthDay",
	            hoverClass:"toolBtnMonthDayHover",
	            enableClass:"toolBtnMonthDayEnable",
	            //初始状态是否可用，true为是
	            isEnable:true,
	            //点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
	            btnType:5,
	            //需要加入历史记录
				needHistroy:true,
				event:{
					click:"_monthDay",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
	        },
	        njqEditor_dayTime:{
	        	title:"时间",
	            defaultClass:"toolBtnBackImg toolBtnDayTime",
	            hoverClass:"toolBtnDayTimeHover",
	            enableClass:"toolBtnDayTimeEnable",
	            //初始状态是否可用，true为是
	            isEnable:true,
	            //点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
	            btnType:5,
	            //需要加入历史记录
				needHistroy:true,
				event:{
					click:"_dayTime",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:1
	        },
	        njqEditor_expression:{
	        	title:"表情",
	            defaultClass:"toolBtnBackImg toolBtnExpression",
	            hoverClass:"toolBtnExpressionHover",
	            enableClass:"toolBtnExpressionEnable",
	            needModel:true,
	            //初始状态是否可用，true为是
	            isEnable:true,
	            //点击按钮是否需要重置按钮状态
				isNeedResetTool:true,
	            btnType:5,
	            //需要加入历史记录
	            needDlgHistroy:true,
	            isNeedCheck:true,
	            checkType:{
					type:"3"//检测类型1：检测文本是否处在标签内 2：针对table，检测是否选中了td而不是检测文本节点 3:只需要进行基本校验如插入图片
				},
				checkfun:["checkRuleBase"],
				dlgId:"njqEditor_expression_dlg",
				dlgUrl:"dialog/emotion/emotion.html",
				event:{
					click:"_emotion",
					mouseenter:"_hover",
					mouseleave:"_out"
				},
				//事件类型：1:立即执行事件 2：无属性弹框有属性立即执行事件 3：只弹框
				clevty:3
	        }
	        /*,
	        njqEditor_demo:{
	        	title:"举例",
	            defaultClass:"toolBtnBackImg toolBtnDemo",
	            downClass:"toolBtnDemoDown",
	            hoverClass:"toolBtnDemoHover",
	            hasRightBtn:true,
	            btnType:5,
	            //初始状态是否可用，true为是
	            isEnable:true,
				event:{
					click:"_demoEvent,_demoDown",
					mouseenter:"_hover",
					mouseleave:"_out"
				}
	        }*/
		}
	}
})();
