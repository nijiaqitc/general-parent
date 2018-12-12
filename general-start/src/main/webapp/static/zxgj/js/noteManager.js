function hideDig(){
	$("#csdialog").hide();
	$("#infoGround").hide();
}

function showpwd(num){
	if(num==1){
		$("#pwdarea").show();
	}else{
		$("#pwdarea").hide();
	}
}


function loadFiles(){
	$.ajax({
        url:jspath+"/note/queryGenList",
        type:"post",
        data:{
        	id:$("select").val()
        },
        success:function(data){
        	var genList="";
        	for(var i in data){
        		genList+="<div class='genTitleList menuBox' onclick='seleTit(this,"+data[i].docId+")'>";
        		if(data[i].title.length>12){
        			genList+="<div class='gentitle' title="+data[i].title+">"+data[i].title.substring(0,12)+"...</div></div>"
        		}else{
        			genList+="<div class='gentitle' title="+data[i].title+">"+data[i].title+"</div></div>";
        		}
        		
        	}
        	$("#genList").html(genList);
        }
    })
}

function inputPwd(){
	if(isNaN($("select").val())){
		$("#genList").html("");
		return;
	}
	$("#csdialog").show();
	$("#infoGround").show();
	$($("#csdialog")[0].firstElementChild).show();
	$($("#csdialog")[0].lastElementChild).hide();
}

function addFolderDlg(){
	$("#csdialog").show();
	$("#infoGround").show();
	$($("#csdialog")[0].firstElementChild).hide();
	$($("#csdialog")[0].lastElementChild).show();
}

function checkLock(){
	$.ajax({
        url:jspath+"/note/lockCheck",
        type:"post",
        data:{
        	id:$("select").val(),
        	pwd:$("#userPwd").val()
        },
        success:function(data){
        	if(data.state=="0"){
        		showSureMessage("提示",data.message);
        	}else{
        		hideDig();
        		loadFiles();
        	}
        }
    })
}

function delFolder(){
	if(isNaN($("select").val())){
		return;
	}
	$.ajax({
        url:jspath+"/note/delFolder",
        type:"post",
        data:{
        	id:$("select").val()
        },
        success:function(data){
        	if(data.state=="0"){
        		showSureMessage("提示",data.message);
        	}else{
            	location.reload();
        	}
        }
    })
}

function saveFold(text){
	if($("#folderName").val()==""){
		showSureMessage("提示","请输入文件名!");
		return;
	}
	if($('input[name="npwd"]')[0].checked){
		if($("#folderPwd").val()==""){
			showSureMessage("提示","请输入密码!");
			return;
		}
		if($("#folderPwd").val()!=$("#refolderPwd").val()){
			showSureMessage("提示","两次密码不一致!");
			return;
		}
	}		
	$.ajax({
        url:jspath+"/note/saveFolder",
        type:"post",
        data:{
        	folderName:$("#folderName").val(),
        	lockPwd:$("#folderPwd").val(),
        	type:1
        },
        success:function(data){
        	location.reload();
        }
    })
}

function seleTit(target,docId){
    if($("#saveNoteBtn").css("display")!="none"){
    	if(njq.getContentTxt()!=""){
            saveOrUpdate();
    	}else{
    		$("#genList").children()[0].remove()
    		saveDeal();
    	}
    }
    if($(".outdiv").css("display")=="none"){
    	$(".emptyShow").hide();
    	$(".outdiv").show();
    	njq.setHide();
    }
    $(".titleActive").removeClass("titleActive");
    $(target).addClass("titleActive");
    $("#editBtn").show();
    $("#saveNoteBtn").hide();
    $("#noteTitled").show();
    $("#noteTitle").hide();
    $.ajax({
        url:jspath+"/note/readNote",
        type:"post",
        data:{
            docId:docId,
            folderId:$("select").val()
        },
        success:function(data){
            $("#cnoteId").val(data.note.id);
            $("#noteContext").html(data.note.text);
            $("#noteTitled").html(data.note.title);
            $("#noteContext").show();
        }
    })
}
function editNode(){
 	njq.setContent($("#noteContext").html());
 	njq.setShow();
    $("#noteTitle").val($("#noteTitled").html());
    $("#noteTitle").show();
    $("#noteTitled").hide();
    $("#noteContext").hide();
    $("#saveNoteBtn").show();
    $("#editBtn").hide();
    
}
function saveOrUpdate(){
   $.ajax({
       url:jspath+"/note/saveNote",
       type:"post",
       data:{
           id:$("#cnoteId").val(),
           title:$("#noteTitle").val(),
           text:njq.getContent(),
           general:njq.getContentTxt(),
           folderId:$("select").val()
       },
       async:false,
       beforeSend:ajaxBefore(),
       success:function(data){
           ajaxAfter();
           if(data.state==1){
                showSureMessage("提示","操作成功!");
                $("#cnoteId").val(data.note.id);
                $("#noteContext").show();
                $("#noteContext").html(njq.getContent());
                $(".titleActive .gentitle").html(data.note.title);
                $(".titleActive .gen").html(data.gen.general);
                $(".titleActive").click(function(){
                    seleTit(this,data.note.id);
                })
                saveDeal();
           }
       }
   })
}

function saveDeal(){
	njq.setHide();
	$("#saveNoteBtn").hide();
    $("#editBtn").show();
}
function confirmDel(){
    if($("#cnoteId").val()==""||$("#cnoteId").val()==null){
    	showSureMessage("提示","尚未保存，刷新页面即可！");
        return;
    }
    showMsg("提示","确认删除？",delNote);
}
function delNote(){
    $.ajax({
        url:jspath+"/note/delNote",
        type:"post",
        data:{
            docId:$("#cnoteId").val()
        },
        success:function(data){
            if(data.state==1){
                $(".titleActive").remove();
                $("#noteContext").html(null);
                $("#cnoteId").val(null);
                $(".outdiv").hide();
                $(".emptyShow").show();
            }
        }
    })
}

function createNote(){
	if(isNaN($("select").val())){
		showSureMessage("提示","请选择文件夹！");
		return;
	}
    if($("#saveNoteBtn").css("display")!="none"){
        save();
    }else{
        resetEmpty();
    }
    
}

function save(){
    $.ajax({
        url:jspath+"/note/saveNote",
        type:"post",
        data:{
            id:$("#cnoteId").val(),
            title:$("#noteTitle").val(),
            text:njq.getContent(),
            general:njq.getContentTxt(),
            folderId:$("select").val()
        },
        beforeSend:ajaxBefore(),
        success:function(data){
            ajaxAfter();
            if(data.state==1){
                 $(".titleActive .gentitle").html(data.note.title);
                 /* $(".titleActive .gen").html(data.gen.general); */
                 $(".titleActive").click(function(){
                     seleTit(this,data.note.id);
                 })
                 resetEmpty();
            }
        }
    })
}

function resetEmpty(){
    $(".titleActive").removeClass("titleActive");
    var str="<div><div class='genTitleList menuBox titleActive'>"+
        "<div class='gentitle'></div>"+
    "</div></div>";
    $("#genList")[0].insertBefore($(str).children(":first")[0],$("#genList").children(":first")[0]);
    $("#noteTitled").hide();
    $("#noteTitle").show();
    $("#editBtn").hide();
    $("#saveNoteBtn").show();
    $("#noteContext").hide();
    njq.setShow();
    njq.setContent("");
    $("#cnoteId").val(null);
    $("#noteTitle").val(null);
    $(".emptyShow").hide();
    $(".outdiv").show();
}