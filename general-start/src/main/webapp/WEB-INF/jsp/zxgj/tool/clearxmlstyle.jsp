<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<body>
	<div style="float:right;width:78%;height:100%;">
		<div class="panel panel-default" style="margin-bottom: 0px;height:100%;">
		  <div class="panel-body" >
		  	<div style="height: 34px;">
			  	<div style="float:left;">
				    <button type="button" class="btn btn-danger" onclick="clearStyle()">清除样式</button>
				    <button type="button" class="btn btn-danger" onclick="decode()">格式化</button>
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
		    <textarea style="width:100%;height:92%;border: 0;outline:none;min-height: 600px;" id="area1"><html>
<body>
	<div style="float:left;width:20%;height:100%;max-width: 220px;min-width: 190px;">
		<div class="panel panel-default" style="height:100%;margin-bottom: 0px;">
		  <div class="panel-body" >
		    <nav class="navbar  navbar-fixed-top" style="position: relative;" id="sidebar-wrapper" role="navigation">
			  <ul class="nav sidebar-nav">
			    <li class="active"> <a href="${path }/tools/inx/jsondecode"> aaa </a> </li>
			    </ul>
			</nav>
		  </div>
		</div>
	</div>
</body>
</html></textarea>
		  </div>
		</div>
	</div>
	<script type="text/javascript" src="${resPath }/jsTool/customClearStyle/customClearStyle.js"></script>
	<script type="text/javascript" src="${resPath }/zxgj/js/tools/xmlDecode.js" ></script>
	<script type="text/javascript">
		function clearStyle(){
    		var cd = new CustomDecoder();
    		cd.str = $("#area1").val();
    		$("#area1").val(cd.decode());
    	}
    	
    	function decode(){
    		var resultXml = formatXml($("#area1").val());
    		$("#area1").val(resultXml);
    	}
	</script>
</body>
</html>