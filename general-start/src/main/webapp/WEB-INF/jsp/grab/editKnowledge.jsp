<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文档编辑</title>
<link href="${resPath }/jsTool/njqeditor/css/njqEditor_styleTwo.css"  rel="stylesheet">
</head>
<body>
	<div style="float: left;width: 100%" align="center">
		<input type="hidden" value="${doc.id }" id="docId">
        <div  align="center">
            <div id="njqEditorDiv" modelStyle="styleTwo" env="3" pv="docId=${doc.id }"  >${doc.doc }</div>
        </div>
    </div>  
	<script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${resPath }/jsTool/njqeditor/js/njqEditor_config.js"></script>
	<script type="text/javascript">
		njqEditor.sysConfig.finishEvent(function(){
			$("#njqEditor_allFullSceen .boderOutDiv").click();
		});
		njqEditor.sysConfig.customUpDocEvent=function(data,fn){
			var form = new FormData();
			form.append("text",njqEditor.api.getContent());
			form.append("docId",$("#docId").val());
			$.ajax({
				url:"editToSave",
                type:"post",
                data:form,
                processData:false,
                contentType:false,
                success:function(data){
                	tempVar.timestamp=null;
                    if(data.state==1){
                    	fn(data.message,200);
                    }else{
                    	fn("上传文章失败，请检查上传文章地址！","saveFalse");	
                    }
                }
			})
		}
	</script>
</body>
</html>