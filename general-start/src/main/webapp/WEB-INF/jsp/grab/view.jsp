<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${resPath }/zxgj/js/prettify.css"/>
    <style type="text/css">
    	table{
    		border-collapse: collapse;
    	}
    	th{
    		user-select: none;
		    min-width: 0px;
		    max-width: none;
		    background: #f0f0f0 center right no-repeat;
		    padding-right: 15px;
		    cursor: pointer;
		    border: 1px solid #ddd;
		    padding: 7px 10px;
		    vertical-align: top;
		    text-align: left;
    	}
    	
    	td{
    		border: 1px solid #ddd;
		    padding: 7px 10px;
		    vertical-align: top;
		    text-align: left;
    	}
    	
    	pre{
    		position: relative !important;
    		overflow-y: hidden !important;
    		overflow-x: auto !important;
    		font-size: 16px !important;
		    line-height: 22px !important;
		    font-family: Source Code Pro,DejaVu Sans Mono,Ubuntu Mono,Anonymous Pro,Droid Sans Mono,Menlo,Monaco,Consolas,Inconsolata,Courier,monospace,PingFang SC,Microsoft YaHei,sans-serif !important;
		    margin: 0 0 24px !important;
		    padding: 8px 16px 6px 56px !important;
		    background-color: #282C33 !important;
		    border: none !important;
		    white-space: pre !important;
    	}
    	.prettyprint ul{
	    	position: absolute;
		    width: 36px;
		    background-color: #282C33;
		    top: 0;
		    left: 0;
		    margin: 0;
		    padding: 8px 0;
		    list-style: none;
		    text-align: right;
    	}
	    .prettyprint ul li{
	    	color: #abb2bf!important;
    		border-right: 1px solid #c5c5c5;
    		padding: 0 8px;
		    list-style: none;
		    margin: 0;
	    }
	    .pre-numbering li span{
	    	color: #fff !important;
	    }
    </style>
</head>
<body>
<div id="vvvv">
	${doc }
</div>


<script type="text/javascript" src="${resPath }/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${resPath }/zxgj/js/prettify.js"></script>
<script type="text/javascript"  src="${resPath }/jsTool/customClearStyle/customClearStyle.js"></script>
<script type="text/javascript">
    $(function(){
    	var cd=new CustomDecoder();
		cd.str=$("#vvvv").html();
    	$("#vvvv").html(cd.decode());
    	decodePre();
    })
    
    
    function decodePre(){
    	for(var i=0;i<$("pre").length;i++){
	    	var code = document.createElement("code");
			code.innerHTML=$($("pre")[i]).html();
			$($("pre")[i]).html(code);
			var ul  = document.createElement("ul");		
			ul.classList.add("pre-numbering");
			var height = $($("pre")[i]).height();
			var num = Number(height/22);
			for(var j = 0; j<num ; j++){
				var li = document.createElement("li");
				li.innerHTML=j+1;
				ul.append(li);
			}
			$($("pre")[i]).append(ul);
    	}
        $("pre").addClass("prettyprint");
        prettyPrint();
    }
</script>
</body>
</html>
