<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<body>
	<div class="commonRight">
		<div class="panel panel-default" style="margin-bottom: 0px;height:100%;">
		  <div class="panel-body" style="height:100%;">
		  	<div style="height: 34px;">
			  	<div style="float:left;">
				    <button type="button" class="btn btn-danger" onclick="jiami()">加密</button>
				    <button type="button" class="btn btn-danger" onclick="jiemi()">解密</button>
			  	</div>
			  	<div style="float:right;width: 620px;">
					
					<div class="input-group" id="ivdiv" style="width: 230px;float: right;margin-left: 4px;">
					  <span class="input-group-addon" id="ivhex" >偏移量</span>
					  <input type="text" class="form-control" placeholder="未输入默认为盐值" value="1234123412ABCDEF"  aria-describedby="basic-addon2" id="ivhexv">
					</div>
				    <div class="input-group" style="width: 230px;float: right;margin-left: 2px;">
					  <span class="input-group-addon" id="basic-addon2">盐值</span>
					  <input type="text" class="form-control" placeholder="未输入默认为空值"  value="ABCDEF1234123412" aria-describedby="basic-addon2" id="desckey">
					</div>
			    </div>
		  	</div>
		    <hr style="margin-top: 10px;margin-bottom: 8px;">
		    <textarea style="width:100%;height:20%;border: 0;outline:none;border-bottom: 1px solid #ddd;min-height: 300px;"  placeholder="输入需要加密的明文"  id="area1"></textarea>
		    <textarea style="width:100%;height:68%;border: 0;outline:none;margin-top: 24px;min-height: 340px;max-height: 400px;" id="area2"  placeholder="输入需要解密的密文" ></textarea>
		  </div>
		</div>
	</div>
	<script src="${resPath }/jquery/jquery-3.4.1.min.js" type="text/javascript"></script>
    <script src="${resPath }/zxgj/js/tools/cryptoJs/core.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/tools/cryptoJs/enc-base64.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/tools/cryptoJs/cipher-core.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/tools/cryptoJs/aes.js" type="text/javascript"></script>
	
	
	<script type="text/javascript">
		function jiami(){
    		if($("#area1").val()==""){
    			return
    		}
    		var lockst = encrypt($("#area1").val());
    		$("#area2").val(lockst);
    	}
    	
    	function jiemi(){
    		if($("#area2").val()==""){
                return
            }
            var lockst = decrypt($("#area2").val());
    		if(lockst.trim()){
	            $("#area1").val(lockst);
    		}else{
    			$("#area1").val("解密失败！");
    		}
    	}
    	function decrypt(word) {
	        let encryptedHexStr = CryptoJS.enc.Hex.parse(word);
	        let srcs = CryptoJS.enc.Base64.stringify(encryptedHexStr);
	        let decrypt = CryptoJS.AES.decrypt(srcs, getkey(), { iv: getivkey(), mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
	        let decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
	        return decryptedStr.toString();
	    }
	    
	    function encrypt(word) {
	        let srcs = CryptoJS.enc.Utf8.parse(word);
	        let encrypted = CryptoJS.AES.encrypt(srcs, getkey(), { iv: getivkey(), mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
	        return encrypted.ciphertext.toString().toUpperCase();
	    }
	    
	    function getivkey(){
	   		if(!$("#ivhexv").val()){
	   			return CryptoJS.enc.Utf8.parse(getkey());
	   		}else{
	   			return CryptoJS.enc.Utf8.parse($("#ivhexv").val().trim());
	   		}
   		}
   		
   		function getkey(){
	   		if(!$("#desckey").val()){
	   			return CryptoJS.enc.Utf8.parse(" ");
	   		}else{
	   			return CryptoJS.enc.Utf8.parse($("#desckey").val().trim());
	   		}
   		}
   		
	</script>
</body>
</html>