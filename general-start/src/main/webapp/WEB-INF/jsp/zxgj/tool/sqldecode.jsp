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
		    <textarea style="width:100%;height:92%;border: 0;outline:none;min-height: 600px;" id="area1">SELECT ca.proj_id AS proj_id, ca.ca_name AS proj_name, ca.ca_date_start AS proj_start, ca.ca_date_end AS proj_end,(SELECT COUNT(*) FROM rotations r WHERE r.proj_id = proj_id AND r.r_status = 'R' GROUP BY r.proj_id) r_count, (SELECT count(*) FROM rotations r WHERE r.proj_id = proj_id AND r.channel_id = 24 ) r_rtb_count FROM projs ca, clients c, proj_auth caa WHERE ca.client_id = 12345 AND ca.client_id = c.client_id AND ca_type = 'zzz' AND c.agency_id = 0 AND ca.client_id = NVL( caa.client_id, ca.client_id ) AND proj_id = NVL( caa.proj_id, proj_id ) AND caa.contact_id = 7890</textarea>
		  </div>
		</div>
	</div>
	<script src="${resPath }/zxgj/js/tools/jsonDecode.js" type="text/javascript"></script>
    <script src="${resPath }/zxgj/js/tools/vkbeautify.js" type="text/javascript"></script>
	<script type="text/javascript">
		function decode1(){
    		$("#area1").val(vkbeautify.sql($("#area1").val(),Number($("#spnum").val())));
    		
    	}
    	function mincode(){
    		$("#area1").val(vkbeautify.sqlmin($("#area1").val()));
    	}
	</script>
</body>
</html>