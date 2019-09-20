<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<body>
	<div class="commonRight">
		<div class="panel panel-default" style="margin-bottom: 0px;height:100%;">
		  <div class="panel-body" style="height:100%;">
		  	<div style="height: 34px;">
			  	<div style="float:left;">
				    <button type="button" class="btn btn-danger" onclick="utf8()">utf8转换</button>
				    <button type="button" class="btn btn-danger" onclick="utf16()">utf16转换</button>
				    <button type="button" class="btn btn-danger" onclick="hex()">hex转换</button>
				    <button type="button" class="btn btn-danger" onclick="latin1()">latin转换</button>
				    <button type="button" class="btn btn-danger" onclick="Base64()">base64转换</button>
				    <button type="button" class="btn btn-danger" onclick="oxf()">0x24000000转换</button>
			  	</div>
		  	</div>
		    <hr style="margin-top: 10px;margin-bottom: 8px;">
		    <textarea style="width:100%;height:20%;border: 0;outline:none;border-bottom: 1px solid #ddd;min-height: 300px;"  placeholder="输入转换的明文"  id="area1"></textarea>
		    <textarea style="width:100%;height:68%;border: 0;outline:none;margin-top: 24px;min-height: 340px;max-height: 400px;" id="area2"  placeholder="" ></textarea>
		  </div>
		</div>
	</div>
    <script src="${resPath }/zxgj/js/tools/cryptoJs/core.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/tools/cryptoJs/enc-base64.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/tools/cryptoJs/enc-utf16.js" type="text/javascript"></script>
	<script type="text/javascript">
		function utf8(){
    		if($("#area1").val()==""){
    			return
    		}
    		var lockst = CryptoJS.enc.Utf8.parse($("#area1").val()).toString();
    		$("#area2").val(lockst);
    	}
    	function utf16(){
    		if($("#area1").val()==""){
    			return
    		}
    		var lockst = CryptoJS.enc.Utf16.parse($("#area1").val()).toString();
    		$("#area2").val(lockst);
    	}
    	function hex(){
    		if($("#area1").val()==""){
    			return
    		}
    		var lockst = CryptoJS.enc.Hex.parse($("#area1").val()).toString();
    		$("#area2").val(lockst);
    	}
    	function latin1(){
    		if($("#area1").val()==""){
    			return
    		}
    		var lockst = CryptoJS.enc.Latin1.parse($("#area1").val()).toString();
    		$("#area2").val(lockst);
    	}
    	function Base64(){
    		if($("#area1").val()==""){
    			return
    		}
    		var lockst = CryptoJS.enc.Base64.parse($("#area1").val()).toString();
    		$("#area2").val(lockst);
    	}
    	
    	function oxf(){
    		if($("#area1").val()==""){
    			return
    		}
    		var lockst = CryptoJS.enc.Utf8.stringify(CryptoJS.lib.WordArray.create([0xf0a4ada2], 4))
    		$("#area2").val(lockst);
    	}
	</script>
</body>
</html>