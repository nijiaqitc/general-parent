<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<body>
	<div style="float:right;width:78%;height:100%;">
		<div class="panel panel-default" style="margin-bottom: 0px;height:100%;">
		  <div class="panel-body" >
		  	<div style="height: 34px;">
			  	<div style="float:left;">
				    <button type="button" class="btn btn-danger" onclick="jiami()">加密</button>
				    <button type="button" class="btn btn-danger" onclick="jiemi()">解密</button>
			  	</div>
			  	<div style="float:right;width: 260px;">
				    <div class="input-group">
					  <span class="input-group-addon" id="basic-addon2">盐值</span>
					  <input type="text" class="form-control" placeholder="未输入默认为空值"  aria-describedby="basic-addon2" id="desckey">
					</div>
			    </div>
		  	</div>
		    <hr style="margin-top: 8px;margin-bottom: 8px;">
		    <textarea style="width:100%;height:20%;border: 0;outline:none;border-bottom: 1px solid #ddd;height: 300px;"  placeholder="输入需要加密的明文"  id="area1">{"menu":{"id": "file","value": [1,2,3],"popup":{"menuitem":[{"value":["one","two"],"onclick":"CreateNewDoc()"},{"value":"Close","onclick":"CloseDoc()"}]}}}</textarea>
		    <textarea style="width:100%;height:68%;border: 0;outline:none;margin-top: 24px;height: 340px;" id="area2"  placeholder="输入需要解密的密文" ></textarea>
		  </div>
		</div>
	</div>
    <script src="${resPath }/zxgj/js/tools/cipherlock.js" type="text/javascript"></script>
	<script type="text/javascript">
		function jiami(){
    		if($("#area1").val()==""){
    			return
    		}
    		var lockst=encrypt($("#area1").val(),$("#desckey").val());
    		$("#area2").val(lockst);
    	}
    	
    	function jiemi(){
    		if($("#area2").val()==""){
                return
            }
            var lockst=decrypt($("#area2").val(),$("#desckey").val());
            $("#area1").val(lockst);
    	}
	</script>
</body>
</html>