<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>sql格式化</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/zxgj/css/tools.css">
<style type="text/css">
	.btnStyle{
		cursor: pointer;
	    width: 50px;
	    height: 30px;
	    color: white;
	    background-color: #E12422;
	    font-size: 12px;
	    border: 0;
	}

</style>
</head>
<body>
    <!--     开始：顶部菜单栏-->
    <jsp:include page="../top1.jsp"></jsp:include>
    <div style="height: 20px;width: 100%;background-color: #ec8316;"></div>
    <!--     结束：顶部菜单栏 -->
    <!-- 中间正文公用部分 -->
    <div class="contextArea" align="center">
    	<div style="font-size: 12px;background-color: #c0d9ef;overflow: auto;">
    		<div style="margin-left: 10px;float: left;">当前位置：加密工具>>sql格式化</div>
    	</div>
		<div align="center" style="width: 1000px;overflow: auto;margin-top: 20px;clear: both;">
			<div style="float: left;">
				<textarea style="width: 400px;height: 480px;resize:vertical;outline: none;" id="text1" ></textarea>
			</div>
			<div style="float: left;height: 480px;"> 
				<div class="btnStyle" style="margin-left: 50px;margin-top: 200px;" onclick="decode()">格式化</div>
			</div>
			<div style="float: right: ;">
				<textarea style="width: 400px;height: 480px;resize:vertical;outline: none;" id="text2" readonly="readonly"></textarea>
			</div>
		</div>
		<div class="bottomD"></div>
    </div>
    
    <!--     开始：顶部菜单栏-->
    <jsp:include page="../bottom.jsp"></jsp:include>
    <!--     结束：顶部菜单栏 -->
    
    <script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
    <script src="${resPath }/zxgj/js/tools/sqlDecode.js" type="text/javascript"></script>
    <script type="text/javascript">
    	function decode(){
    		if($("#text1").val()==""){
    			return;
    		}
    		var str=beautify($("#text1").val());
    		$("#text2").val(str);
    	}
    	
    	function beautify(str) {
//     	    if (the.beautify_in_progress)
//     	        return;

//     	    store_settings_to_cookie();

//     	    the.beautify_in_progress = true;

    	    var source = str; 
    	    
    	    var output, opts = {};
    	    //缩进数量
    	    opts.indent_size = 4;
    	    opts.indent_char = opts.indent_size == 1 ? '\t' : ' ';
    	    //标记之间保留几个换行
    	    opts.max_preserve_newlines = 5;
    	    opts.preserve_newlines = opts.max_preserve_newlines !== "-1";
    	    //是否保持数组缩进
    	    opts.keep_array_indentation = true;
    	    //是否链式方法处换行
    	    opts.break_chained_methods = false;
    	    //缩进级别keep、normal、separate
    	    opts.indent_scripts ="keep";
    	    //控制括号的形式collapse、expand、end-expand
    	    opts.brace_style="collapse";
    	    //条件语句之前的空格
    	    opts.space_before_conditional = true;
    	    //是否转义可打印字符
    	    opts.unescape_strings = false;
    	    //是否使用 JSLint-happy格式调整
    	    opts.jslint_happy = false;
    	    //是否以换行符结束脚本和样式
    	    opts.end_with_newline = false;
    	    //第几个字符换行
    	    opts.wrap_line_length = 0;

    	    /* if (looks_like_html(source)) {
    	        output = html_beautify(source, opts);
    	    } else if (the.editor.options.mode == 'css') {
    	        output = css_beautify(source);
    	    } else {
    	    } */
    	    	//是否检测包并模糊化处理
//     	        if (true) {
//     	            source = unpacker_filter(source);
//     	        }
    	        output = js_beautify(source, opts);
    	    /* if (the.editor) {
    	        the.editor.setValue(output);
    	    } else {
    	        $('#code').val(output);
    	    } */
    	    return output;
//    	    	$("#text2").val(output);

//     	    the.beautify_in_progress = false;
    	}
    	
    	
    	$(document).ready(function(){
			$(".nagivationBottomSelect").removeClass("nagivationBottomSelect");
			$($(".nagivationBottom")[1]).addClass("nagivationBottomSelect");
		})
    </script>
</body>
</html>