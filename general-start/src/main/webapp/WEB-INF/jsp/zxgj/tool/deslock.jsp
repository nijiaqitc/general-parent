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
					
					<div class="input-group" id="ivdiv" style="width: 220px;float: right;margin-left: 4px;display: none;">
					  <span class="input-group-addon" id="ivhex" >偏移量</span>
					  <input type="text" class="form-control" placeholder="未输入默认为盐值"  aria-describedby="basic-addon2" id="ivhexv">
					</div>
					
					<select class="form-control" id="selType" style="width: 130px;float: right;margin-left: 4px;" onchange="changev()">
						<option value="ecb1">ECB 字符串</option>
						<option value="ecb2">ECB 数值</option>
						<option value="cbc1">CBC 数值</option>
						<option value="cbc2">CBC 字符串</option>
					</select>
					
				    <div class="input-group" style="width: 200px;float: right;margin-left: 2px;">
					  <span class="input-group-addon" id="basic-addon2">盐值</span>
					  <input type="text" class="form-control" placeholder="未输入默认为空值"  aria-describedby="basic-addon2" id="desckey">
					</div>
			    </div>
		  	</div>
		    <hr style="margin-top: 10px;margin-bottom: 8px;">
		    <textarea style="width:100%;height:20%;border: 0;outline:none;border-bottom: 1px solid #ddd;min-height: 300px;"  placeholder="输入需要加密的明文"  id="area1"></textarea>
		    <textarea style="width:100%;height:68%;border: 0;outline:none;margin-top: 24px;min-height: 340px;max-height: 400px;" id="area2"  placeholder="输入需要解密的密文" ></textarea>
		  </div>
		</div>
	</div>
    <script src="${resPath }/zxgj/js/tools/cryptoJs/core.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/tools/cryptoJs/enc-base64.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/tools/cryptoJs/cipher-core.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/tools/cryptoJs/mode-ecb.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/tools/cryptoJs/tripledes.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		function jiami(){
    		if($("#area1").val()==""){
    			return
    		}
    		var lockst;
    		if($("#selType").val()=="ecb1"){
    			lockst = encMe($("#area1").val());
    		}else if($("#selType").val()=="ecb2"){
    			lockst = encryptByDESModeEBC($("#area1").val());
    		}else if($("#selType").val()=="cbc1"){
    			lockst = encryptByDESModeCBC($("#area1").val());
    		}else if($("#selType").val()=="cbc2"){
    			lockst = encryptByDESModeCBC1($("#area1").val());
    		}
    		$("#area2").val(lockst);
    	}
    	
    	function jiemi(){
    		if($("#area2").val()==""){
                return
            }
            var lockst;
            if($("#selType").val()=="ecb1"){
            	lockst=uncMe($("#area2").val());
            }else if($("#selType").val()=="ecb2"){
            	lockst = decryptByDESModeEBC($("#area2").val());
            }else if($("#selType").val()=="cbc1"){
    			lockst = decryptByDESModeCBC($("#area2").val());
    		}else if($("#selType").val()=="cbc2"){
    			lockst = decryptByDESModeCBC1($("#area2").val());
    		}
    		if(lockst.trim()){
	            $("#area1").val(lockst);
    		}else{
    			$("#area1").val("解密失败！");
    		}
    	}
    	function encMe(str){
	   		const key = CryptoJS.enc.Utf8.parse(getkey());
	        let encrypted = CryptoJS.DES.encrypt(str, key, {
		        mode: CryptoJS.mode.ECB,
		        padding: CryptoJS.pad.Pkcs7
		    });
			return encrypted.toString()
   		}
   		function uncMe(str){
	   		var keyHex = CryptoJS.enc.Utf8.parse(getkey());
			var decrypted = CryptoJS.DES.decrypt({
				ciphertext: CryptoJS.enc.Base64.parse(str)
				}, keyHex, {
				mode: CryptoJS.mode.ECB,
				padding: CryptoJS.pad.Pkcs7
			});
			var result_value = decrypted.toString(CryptoJS.enc.Utf8);
			return result_value;
   		}
   		
   		function getkey(){
	   		if(!$("#desckey").val()){
	   			return " ";
	   		}else{
	   			return $("#desckey").val().trim();
	   		}
   		}
   		
   		
   		function encryptByDESModeEBC(message) {
            var keyHex = CryptoJS.enc.Utf8.parse(getkey());
            var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
                mode: CryptoJS.mode.ECB,
                padding: CryptoJS.pad.Pkcs7
            });
            return encrypted.ciphertext.toString();
        }
        
        
        function decryptByDESModeEBC(ciphertext) {
            var keyHex = CryptoJS.enc.Utf8.parse(getkey());
            var decrypted = CryptoJS.DES.decrypt({
                ciphertext: CryptoJS.enc.Hex.parse(ciphertext)
            }, keyHex, {
                mode: CryptoJS.mode.ECB,
                padding: CryptoJS.pad.Pkcs7
            });
            var result_value = decrypted.toString(CryptoJS.enc.Utf8);
            return result_value;
        }
        
        
        function getivkey(){
	   		if(!$("#ivhexv").val()){
	   			return getkey();
	   		}else{
	   			return $("#ivhexv").val().trim();
	   		}
   		}
   		
        function encryptByDESModeCBC(message) {
            var keyHex = CryptoJS.enc.Utf8.parse(getkey());
            var ivHex = CryptoJS.enc.Utf8.parse(getivkey());
            encrypted = CryptoJS.DES.encrypt(message, keyHex, {
                iv: ivHex,
                mode: CryptoJS.mode.CBC,
                padding: CryptoJS.pad.Pkcs7
            });
            return encrypted.ciphertext.toString();
        }


        function decryptByDESModeCBC(ciphertext2) {
            var keyHex = CryptoJS.enc.Utf8.parse(getkey());
            var ivHex = CryptoJS.enc.Utf8.parse(getivkey());
            var decrypted = CryptoJS.DES.decrypt({
                ciphertext: CryptoJS.enc.Hex.parse(ciphertext2)
            }, keyHex, {
                iv: ivHex,
                mode: CryptoJS.mode.CBC,
                padding: CryptoJS.pad.Pkcs7
            });
            return decrypted.toString(CryptoJS.enc.Utf8);
        }
        
        
        function changev(){
        	if($("#selType").val()=="cbc1"||$("#selType").val()=="cbc2"){
        		$("#ivdiv").show();
        	}else{
        		$("#ivdiv").hide();
        	}
        }
        
        
        
        function encryptByDESModeCBC1(message) {
            var keyHex = CryptoJS.enc.Utf8.parse(getkey());
            var ivHex = CryptoJS.enc.Utf8.parse(getivkey());
            encrypted = CryptoJS.DES.encrypt(message, keyHex, {
                iv: ivHex,
                mode: CryptoJS.mode.CBC,
                padding: CryptoJS.pad.Pkcs7
            });
            return encrypted.toString();
        }

        function decryptByDESModeCBC1(ciphertext2) {
            var keyHex = CryptoJS.enc.Utf8.parse(getkey());
            var ivHex = CryptoJS.enc.Utf8.parse(getivkey());
            var decrypted = CryptoJS.DES.decrypt({
                ciphertext: CryptoJS.enc.Base64.parse(ciphertext2)
            }, keyHex, {
                iv: ivHex,
                mode: CryptoJS.mode.CBC,
                padding: CryptoJS.pad.Pkcs7
            });
            return decrypted.toString(CryptoJS.enc.Utf8);
        }
	</script>
</body>
</html>