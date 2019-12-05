<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<body>
	<div class="commonRight">
		<div class="panel panel-default" style="margin-bottom: 0px;height:100%;">
		  <div class="panel-body" style="height:100%;">
		  	<div style="height: 34px;">
			  	<div style="float:left;">
				    <button type="button" class="btn btn-danger" onclick="decode()">格式化</button>
			  	</div>
			    <div style="float:right;width: 460px;">
				    <span class="label label-primary" style="padding: 10px 4px;font-size: 12px;">填充空格：</span>
				    <select style="width: 54px;height: 34px;border-radius: 4px;" id="spnum">
						<option value="2">2</option>
						<option selected value="4">4</option>
						<option value="6">6</option>
						<option value="8">8</option>
						<option value="10">10</option>
					</select>
					<span class="label" style="font-size: 12px;color: black;border: 1px solid #aaa;padding: 9px 4px;border-radius: 4px;margin-right:4px;">
						<input type="checkbox" id="braces-on-own-line" />花括号分行
					</span>
					<span class="label" style="font-size: 12px;color: black;border: 1px solid #aaa;padding: 9px 4px;border-radius: 4px;margin-right:4px;">
					  <input type="checkbox" id="preserve-newlines" checked="checked" /><label for="preserve-newlines">保留空行</label>
					</span>
					<span class="label" style="font-size: 12px;color: black;border: 1px solid #aaa;padding: 9px 4px;border-radius: 4px;margin-right:4px;">
					  <input type="checkbox" id="detect-packers" checked="checked" /><label for="detect-packers">压缩检查</label>
					</span>
					<span class="label" style="font-size: 12px;color: black;border: 1px solid #aaa;padding: 9px 4px;border-radius: 4px;margin-right:4px;">
					  <input type="checkbox" id="keep-array-indentation" /><label for="keep-array-indentation">数组切分</label>
					</span>
			    </div>
		  	</div>
		    <hr style="margin-top: 8px;margin-bottom: 8px;">
		    <textarea style="width:100%;height:92%;border: 0;outline:none;min-height: 600px;" id="text1" placeholder="请输入带格式化字符串"></textarea>
		  </div>
		</div>
	</div>
	<script type="text/javascript" src="${resPath }/zxgj/js/tools/jslock/beautify.js" ></script>
	<script type="text/javascript" src="${resPath }/zxgj/js/tools/jslock/beautify-html.js" ></script>
	<script type="text/javascript" src="${resPath }/zxgj/js/tools/jslock/javascriptobfuscator_unpacker.js" ></script>
	<script type="text/javascript" src="${resPath }/zxgj/js/tools/jslock/bookmarklet_unpacker.js" ></script>
	<script type="text/javascript" src="${resPath }/zxgj/js/tools/jslock/p_a_c_k_e_r_unpacker.js" ></script>
	<script type="text/javascript">
		function decode() {
		    var js_source = $("#text1").val().replace(/^\s+/, '');
		    var indent_size = $("#spnum").val();
		    var indent_char = ' ';
		    var preserve_newlines = $("#preserve-newlines").prop("checked");
		    var keep_array_indentation = $("#keep-array-indentation").prop("checked");
		    var braces_on_own_line = $("#braces-on-own-line").prop("checked");
		    if (indent_size == 1) {
		        indent_char = '\t';
		    }
		    if (js_source && js_source[0] === '<' && js_source.substring(0, 4) !== '<!--') {
		        $("#text1").val(style_html(js_source, indent_size, indent_char, 80));
		    } else {
		        $("#text1").val(js_beautify(unpacker_filter(js_source), {
		            indent_size: indent_size,
		            indent_char: indent_char,
		            preserve_newlines: preserve_newlines,
		            braces_on_own_line: braces_on_own_line,
		            keep_array_indentation: keep_array_indentation,
		            space_after_anon_function: true,
		            indent_level:0
		        }));
		    }
		    return false;
		}
		function unpacker_filter(source) {
		    if ($("#detect-packers").prop("checked")) {
		        var stripped_source = trim_leading_comments(source);
		        var unpacked = '';
		        if (P_A_C_K_E_R.detect(stripped_source)) {
		            unpacked = P_A_C_K_E_R.unpack(stripped_source);
		            if (unpacked !== stripped_source) {
		                return unpacker_filter(unpacked);
		            }
		        }
		        if (EscapedBookmarklet.detect(source)) {
		            unpacked = EscapedBookmarklet.unpack(source);
		            if (unpacked !== stripped_source) {
		                return unpacker_filter(unpacked);
		            }
		        }
		        if (JavascriptObfuscator.detect(stripped_source)) {
		            unpacked = JavascriptObfuscator.unpack(stripped_source);
		            if (unpacked !== stripped_source) {
		                return unpacker_filter(unpacked);
		            }
		        }
		    }
		    return source;
		}
		function trim_leading_comments(str) {
		    // very basic. doesn't support /* ... */
		    str = str.replace(/^(\s*\/\/[^\n]*\n)+/, '');
		    str = str.replace(/^\s+/, '');
		    return str;
		}
	</script>
</body>
</html>