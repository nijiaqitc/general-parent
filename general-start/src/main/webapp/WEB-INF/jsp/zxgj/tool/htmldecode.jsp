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
					<select id="isPre" style="width: 120px;height: 34px;border-radius: 4px;">
						<option value="1">pre标签不格式</option>
						<option value="2">pre标签格式</option>
					</select>
					<select id="tranType" style="width: 130px;height: 34px;border-radius: 4px;">
						<option value="1">内结束标签换行</option>
						<option value="2">内结束标签不换行</option>
					</select>
					<select id="spaceType" style="width: 110px;height: 34px;border-radius: 4px;">
						<option value="1">缩进4空格</option>
						<option value="2">缩进8空格</option>
						<option value="3">缩进16空格</option>
						<option value="4">缩进1Tab</option>
					</select>
			    </div>
		  	</div>
		    <hr style="margin-top: 8px;margin-bottom: 8px;">
		   <textarea style="width:100%;height:92%;border: 0;outline:none;min-height: 600px;" id="decodeBefore"><html><head><title></title></head><body></body></html></textarea>
		  </div>
		</div>
	</div>
	<script type="text/javascript" src="${resPath }/jsTool/customHtmlDecoder/customHtmlDecoder.js"></script>
	<script type="text/javascript">
		function decode(){
			if(!$("#decodeBefore").val()){
				return;
			}
			var chd=new customHtmlDecoder();
			var ispre=1;
			if($("#isPre").val()==1){
				ispre=false;
			}else{
				ispre=true
			}
			$("#decodeBefore").val(chd.decode($("#decodeBefore").val(),{ispre:ispre,tranType:$("#tranType").val(),spaceType:$("#spaceType").val()}).trim());
		}
	</script>
</body>
</html>