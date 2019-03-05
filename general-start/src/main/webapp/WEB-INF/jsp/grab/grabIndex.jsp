<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>主页</title>
    <link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png"/>
    <style type="text/css">
    	.aoutDiv1{
			height: 550px;clear: both;
		}
		.asearchDiv{
/* 			max-width: 1190px; */
		}
		.asearchDiv1{
			clear:both; height: 60px;margin-top: 20px;
		}
		.asearchDiv2{
			width: 640px;height: 40px;padding-top: 10px;
		}
		.asearchText{
			border: 1px solid #b6b6b6;vertical-align: top;height: 36px;width: 520px;-webkit-appearance: textfield;padding: 1px;background-color: white;border-image-source: initial;border-image-slice: initial;border-image-width: initial;border-image-outset: initial;border-image-repeat: initial;-webkit-rtl-ordering: logical;-webkit-user-select: text;cursor: auto;margin: 0em;color: initial;letter-spacing: normal;word-spacing: normal;text-transform: none;text-indent: 0px;text-shadow: none;display: inline-block;text-align: start;
		}
		.asearchbutton{
			cursor: pointer;width: 100px;height: 40px;color: white;background-color: #E12422;font-size: 16px;border: 0;line-height: 40px;
		}
		
		.label-list {
		    margin-right: -24px;
		    margin-top: 10px;
		    padding-bottom: 50px;
		    overflow: auto;
		    clear: both;
		}

		.label-list a {
		    color: #666;
		    text-decoration: none;
		    display: block;
		    float: left;
		    border: 1px solid #EEEFF2;
		    border-radius: 2px;
		    box-sizing: border-box;
		    width: 132.5px;
		    height: 50px;
		    line-height: 48px;
		    background: #F9F9F9;
		}
		.label-list a {
		    width: 116px;
		    margin-right: 10.3px;
		    -webkit-transition: all .2s;
		    -o-transition: all .2s;
		    transition: all .2s;
		    cursor: pointer;
		    overflow: auto;
		}
		.label-list a:hover {
		    border-color: #ff5200;
		    background: #ff5200;
		    color: #fff;
		    transition: background,border-color .2s;
		}
		.classify-more {
		    height: 70px;
		    box-sizing: border-box;
		    text-align: center;
		    padding-top: 20px;
		}
		.classify-more p {
		    display: inline-block;
		    width: 552px;
		    border-top: 1px solid #ddd;
		    height: 0;
		}
		.classify-more p span {
		    display: inline-block;
		    padding: 0 31px;
		    background: #fff;
		    position: relative;
		    top: -11px;
		}
		.classify-more p span a{
			padding: 20px;
			font-size: 14px;
    		color: #666;
    		-webkit-transition: all .2s;
		    -o-transition: all .2s;
		    transition: all .2s;
		    cursor: pointer;
		    text-decoration: none;
		}
		
		.totalMenu a{
			width: 116px;
			margin-left: 10px;
			cursor: pointer;
			float: left;
			color: #666;
			display: block;
			border-right: 1px solid #EEEFF2;
		    border-left: 1px solid #EEEFF2;
		    text-decoration: none;		
		    font-size: 14px;
		}
		.totalMenu a:hover{
			background: #F9F9F9;
		}
		.labelArea{
			width: 800px;border-bottom: 1px solid #EEEFF2;overflow: auto;padding-top: 40px;
		}
		.labelArea .active{
			background-color: #EEEFF2;
		}
		.labelInArea{
			width: 520px;overflow: auto;
		}
		.outd{
		    position: relative;
		    width: 116px;
		    overflow: auto;
		    float: left;
    		margin: 12px 12px 0 0;
		}
		.rightLabel{
			position: relative;
		    margin-top: -50px;
		    margin-right: 4px;
		    float: right;
		    font-size: 12px;
		}
		
    </style>
</head>
<body>
	
	<div align="center" class="asearchDiv">
    	<div align="center"  class="asearchDiv1">
    		<div class="asearchDiv2" >
    			<form action="searchForList" method="get" target="_blank">
	    			<span><input type="text" class="asearchText" id="searchValue" name="searchValue" maxlength="60" value="" ></span>
	    			<span><input type="submit" onclick="" value="搜索一下" class="asearchbutton" id="searchButton" ></span>
    			</form>
    		</div>
    	</div>
   	</div>
   	
   	<div align="center"> 
   		<div class="labelArea">
   			<div style="width: 396px;" class="totalMenu">
	   			<a href="javascript:void(0)" onclick="loadTypeData(this)">类型</a>
	   			<a href="javascript:void(0)" onclick="loadTipData(this)">标签</a>
	   			<a href="searchForList?star=1" target="_blank">星标</a>
   			</div>
   		</div>
   	</div>
   	
	<!-- <div class="classify-more" style="margin-top: -30px;">
        <p>
        	<span>
        		<a rel="nofollow" href="javascript:void(0)" target="_blank" style="padding: 20px;" class="click-statistics">查看更多</a>
        	</span>
        </p>
    </div> -->
	
	<div id="tipLabel" class = "label-list" align="center" >
		<div class="labelInArea">
<!-- 			<div class="outd"> -->
<!-- 				<a href="#">dddd</a> -->
<!-- 				<span class="rightLabel">11</span> -->
<!-- 			</div> -->
			
		</div>
	</div>
	
	
	<!-- <div class="classify-more" style="margin-top: -30px;">
        <p>
        	<span>
        		<a rel="nofollow" href="javascript:void(0)" target="_blank" style="padding: 20px;" class="click-statistics">查看更多</a>
        	</span>
        </p>
    </div> -->

	<div id="typeLabel" class = "label-list" align="center" >
		<div class="labelInArea">
<!-- 			<div> -->
<!-- 				<a href="#">dddd (10)</a> -->
<!-- 				<span class="rightLabel">11</span> -->
<!-- 			</div> -->
		</div>
	</div>

    <script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    	function loadTipData(target){
	    	if(checkBtn(target)){
    			return;
    		}
    		$("#tipLabel").show();
    		$("#typeLabel").hide();
	    	$.ajax({
	    		url:"${path}/grab/getTipList",
	    		type:"post",
	    		success:function(data){
	    			$("#tipLabel").children(":first").html("");
	    			if(data != null){
			    		data.forEach(d =>{  
			    			var name = d.name.length>6? d.name.substring(0,6)+"..":d.name; 
			    			$("#tipLabel").children(":first").append("<div class='outd'>" + 
			    				"<a href='showTitleListByTip?tipId="+d.id+"' target='_blank' title = '"+d.name+"' >" + name + "</a>" + 
			    				"<span class='rightLabel'>"+d.num+"</span></div> ");
						});
	    			}
	    		}
	    	})
    	}
    	
    	function loadTypeData(target){
    		if(checkBtn(target)){
    			return;
    		}
    		$("#tipLabel").hide();
    		$("#typeLabel").show();
	    	$.ajax({
	    		url:"${path}/grab/getTypeList",
	    		type:"post",
	    		success:function(data){
	    			$("#typeLabel").children(":first").html("");
					if(data != null){
			    		data.forEach(d =>{ 
			    			var name = d.name.length>6? d.name.substring(0,6)+"..":d.name;  
			    			$("#typeLabel").children(":first").append("<div class='outd'>" + 
			    				"<a href='showTitleListByType?typeId="+d.id+"'  target='_blank' title ='"+d.name+"'>" + name + "</a>" +
			    				"<span class='rightLabel'>"+d.num+"</span></div>");
						});
	    			}
	    		}
	    	})
    	}
    	
    	function checkBtn(target){
    		var tlist = $(target).parent().find(".active");
    		if(tlist.size() > 0){
    			if(tlist.size()==1 && tlist[0] == target){
					return true;
    			}else{
    				for(var i = 0 ;i<tlist.size();i++){
			   			$(tlist[i]).removeClass("active");
    				}
    			}
    		}
    		$(target).addClass("active");
    	}
    </script>
</body>
</html>