$("#tInput").keydown(function(e){
    if(!e) var e = window.event; 
    var inputValue;
    if(e.keyCode==32){
        var s=false;
        inputValue=$("#tInput").val().trim();
        if(inputValue==""){
            $("#tInput").val("");
            return
        }
        if(inputValue.length>16){
            showSureMessage("提示","标签内容长度不能超过16！");
            $("#tInput").val("");
            return
        }
        if($(".tip").length>4){
            $("#tInput").val("");                       
        }else{
            $.each($(".tip"),function(a,b){
                if(inputValue==$($(b).children("span")).html()){
                    s=true;
                }
            })
            if(s!=true){
                $("#tInput").before('<div class="tip"><span>'+inputValue+'</span><a onclick="closeTip(this)" href="javascript:;" title="删除" style="color: #999;">×</a></div>')
            }
            s=false;
            $("#tInput").val("");
        }
    }
 });

function appendTips(str){
	$("#tInput").before('<div class="tip"><span>'+
			str+'</span><a onclick="closeTip(this)" href="javascript:;" title="删除" style="color: #999;">×</a></div>')
}
function saveDoc(){
    if($("#title").val()==null||$("#title").val()==""){
        showSureMessage("提示","请输入标题！");
        return;
    }
    var typeId,name;
    if($("input[name='isxl']:checked").val()==1){
        typeId=$("#selectType").val();
        name=$("#name").val();
    }else{
        typeId=null;
        name=null;
    }
    var tipList="";
    $.each($(".tip span"),function(a,b){
        tipList +=$(b).html()+","
    })
    tipList=tipList.substring(0,tipList.length-1).trim();
    
    if(saveCheck()){
        return;
    }
    var contextArray=njq.getSaveContext();
    var data="title="+$("#title").val();
    if($("input[name='isxl']:checked").val()=="1"){
    	data+="&name="+$("#name").val()+
        "&typeId="+$("#selectType").val();
    }
    data+="&tips="+tipList+"&isShow="+$("input[name='isshow']:checked").val()+
    "&id="+$("#docId").val()+"&specalType="+$("input[name='specalType']:checked").val()+"&general="+njq.getContentTxt();
    
    njq.saveData(data,function(data,status){
    	if(status=="saveFalse"){
    		showSureMessage("提示",data);
    		return;
    	}
    	if(data.state==1){
    		if(data.docId!=null&&data.docId!=""){
    			$("#docId").val(data.docId);    			
    		}
    		showSureMessage("提示","操作成功！");
    	}else{
    		showSureMessage("提示",data.message);
    	}
    });
//    $.ajax({
//        url:"/weibo/yxl/saveKnowledge",
//        type:"post",
//        data:{
//            title:$("#title").val(),
//            text:contextArray[0],
//            css:contextArray[1],
//            name:$("#name").val(),
//            typeId:$("#selectType").val(),
//            tips:tipList,
//            isShow:$("input[name='isshow']:checked").val(),
//            id:$("#docId").val(),
//            general:njq.getContentTxt()
//        },
//        beforeSend:ajaxBefore(),
//        success:function(data){
//            ajaxAfter();
//            if(data.state==1){
//                $("#docId").val(data.docId);
//                showSureMessage("提示","操作成功！");
//            }else{
//                showSureMessage("提示",data.message);
//            }
//        }
//    })      
}

function saveCheck(){
    if($("#title").val().trim()==""){
        showSureMessage("提示","请输入标题！");
        return true;
    }
    
}

function isxl(value){
    if(value==0){
        $("#xlhd").hide();
    }
    if(value==1){
        $("#xlhd").show();
    }
}

function showhideDialog(){
    if($("#csdialog").css("right")=="-560px"){
        $("#csdialog").animate({right: '0px'},"fast");
    }else{
        $("#csdialog").animate({right: '-560px'},"fast");   
    }
}

function saveValue(){
    njq.setTitle($("#title").val());
    showhideDialog();
}

function inputfocuse(){
    $("#tInput").focus();
}

function closeTip(e){
    $(e).parent("div").remove();
}

function tipcheck(e){
    var inputValue=$("#tInput").val().trim();
    if(inputValue==""){
        $("#tInput").val("");
        return
    }
    if(inputValue.length>5){
        showSureMessage("提示","标签内容长度不能超过5！");
        $("#tInput").val("");
        return
    }
    var s=false;
    if($(".tip").length>4){
        $("#tInput").val("");
    }else{
        $.each($(".tip"),function(a,b){
            if(inputValue==$($(b).children("span")).html()){
                s=true;
            }
        })
        if(s!=true){
            $("#tInput").before('<div class="tip"><span>'+inputValue+'</span><a onclick="closeTip(this)" href="javascript:;" title="删除" style="color: #999;">×</a></div>')
        }
        s=false;
        $("#tInput").val("");
    }
}

function seleChange(sele){
    if($(sele).val()==""){
        $("#newType").show();
    }else{
        $("#newType").hide();
        $("#name").val(null);
    }
}


njqEditor.sysConfig.finishEvent(function(){
	if($("#upIsUpdate").val()=="true"){
		njq.setContent(njq.assembleContext($("#upDocText").html(),$("#upDocCss").html()));
		njq.setTitle($("#upTitle").val());
		$("#selectType").val($("#upTypeId").val());
		$("#title").val($("#upTitle").val());
		if($("#upIsShow").val()=="0"){
			$("input[name='isshow']").eq(1).click();			
		}else{
			$("input[name='isshow']").eq(0).click();
		}
		var st=$("#upTipName").val().split(",");
		for(var i=0;i<st.length;i++){
			if(st[i]!=""){
				appendTips(st[i]);
			}
		}
		if($("#upType").val()==""){
			$("input[name='isxl']").eq(1).click();			
		}else{
			$("input[name='isxl']").eq(0).click();
			$("#selectType").val($("#upType").val());
			$("#newType").hide();
		}
	}
});
