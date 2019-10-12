function saveDoc(){
    $.ajax({
        url:"/admin/notes/dealReviews",
        type:"post",
        data:{
        	id:$("#docId").val(),
        	chunkId:$("#chunkId").val(),
        	doc:njq.getContent(),
        	general:$("#general").val(),
        	index:$("#index").val(),
        },
        beforeSend:ajaxBefore(),
        success:function(data){
            ajaxAfter();
            if(data.state==1){
                showSureMessage("提示","操作成功！");
            }else{
                showSureMessage("提示",data.message);
            }
        }
    })      
}

function showhideDialog(){
    if($("#csdialog").css("right")=="-560px"){
        $("#csdialog").animate({right: '0px'},"fast");
    }else{
        $("#csdialog").animate({right: '-560px'},"fast");   
    }
}

function saveValue(){
    showhideDialog();
}




njqEditor.sysConfig.finishEvent(function(){
//	if($("#upIsUpdate").val()=="true"){
//		njq.setContent(njq.assembleContext($("#upDocText").html(),$("#upDocCss").html()));
//		njq.setTitle($("#upTitle").val());
//		$("#selectType").val($("#upTypeId").val());
//		$("#title").val($("#upTitle").val());
//		if($("#upIsShow").val()=="0"){
//			$("input[name='isshow']").eq(1).click();			
//		}else{
//			$("input[name='isshow']").eq(0).click();
//		}
//		var st=$("#upTipName").val().split(",");
//		for(var i=0;i<st.length;i++){
//			if(st[i]!=""){
//				appendTips(st[i]);
//			}
//		}
//		if($("#upType").val()==""){
//			$("input[name='isxl']").eq(1).click();			
//		}else{
//			$("input[name='isxl']").eq(0).click();
//			$("#selectType").val($("#upType").val());
//			$("#newType").hide();
//		}
//	}
});
