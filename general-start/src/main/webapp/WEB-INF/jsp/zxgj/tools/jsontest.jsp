<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<body>
	<div style="float:right;width:78%;height:100%;">
		<div class="panel panel-default" style="margin-bottom: 0px;height:100%;">
		  <div class="panel-body" >
		  	<div style="height: 34px;">
			  	<div style="float:left;">
				    <button type="button" class="btn btn-danger" onclick="decode1()">格式化</button>
				    <button type="button" class="btn btn-danger" onclick="mincode()">压缩</button>
			  	</div>
			    <div style="float:right;">
				    <span class="label label-primary" style="padding: 10px 4px;font-size: 12px;">填充空格：</span>
				    <select style="width: 54px;height: 34px;border-radius: 4px;" id="spnum">
						<option value="2">2</option>
						<option selected value="4">4</option>
						<option value="6">6</option>
						<option value="8">8</option>
						<option value="10">10</option>
					</select>
			    </div>
		  	</div>
		    
		    <hr style="margin-top: 8px;margin-bottom: 8px;">
		    <textarea style="width:100%;height:92%;border: 0;outline:none;min-height: 600px;" id="text1">{"menu":{"id": "file","value": [1,2,3],"popup":{"menuitem":[{"value":["one","two"],"onclick":"CreateNewDoc()"},{"value":"Close","onclick":"CloseDoc()"}]}}}</textarea>
		  </div>
		</div>
	</div>
	<script src="${resPath }/zxgj/js/tools/jsonDecode.js" type="text/javascript"></script>
    <script src="${resPath }/zxgj/js/tools/vkbeautify.js" type="text/javascript"></script>
	<script type="text/javascript">
		function decode(){
    		var resultJson = formatJson($("#text1").val());
    		$("#text1").val(resultJson.trim());
    	}
    	
    	function decode1(){
    		$("#text1").val(vkbeautify.json($("#text1").val(),Number($("#spnum").val())));
    		
    	}
    	function mincode(){
    		$("#text1").val(vkbeautify.jsonmin($("#text1").val()));
    	}
	</script>
</body>
</html>