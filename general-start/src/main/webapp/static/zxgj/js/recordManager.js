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
        url:jspath+"/admin/note/queryTypeNameList",
        type:"post",
        data:{
        	id:$("select").val()
        },
        success:function(data){
        	var genList="";
        	for(var i in data){
        		genList+="<div class='genTitleList menuBox' dataId='"+data[i].id+"' onclick='seleTit(this,"+data[i].id+")'>";
        		if(data[i].name.length>12){
        			genList+="<div class='gentitle' title="+data[i].name+">"+data[i].name.substring(0,12)+"...</div></div>";
        		}else{
        			genList+="<div class='gentitle' title="+data[i].name+">"+data[i].name+"</div></div>";
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
        url:jspath+"/admin/note/lockCheck",
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
        url:jspath+"/admin/note/delFolder",
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
        url:jspath+"/admin/note/saveFolder",
        type:"post",
        data:{
        	folderName:$("#folderName").val(),
        	lockPwd:$("#folderPwd").val(),
        	type:2
        },
        success:function(data){
        	location.reload();
        }
    })
}


function addTr(){
	var thead=$("table thead")[0];
	var tdnum=thead.childElementCount;
	var tr=document.createElement("tr");
	var td=document.createElement("td");
	var ctd;
	ctd=td.cloneNode();
	ctd.innerHTML=formatTime(new Date());
	tr.appendChild(ctd);
	for(var i=1;i<tdnum-1;i++){
		ctd=td.cloneNode();
		ctd.appendChild(document.createElement("textarea"));
		tr.appendChild(ctd);
	}
	ctd=td.cloneNode();
	ctd.appendChild(document.createElement("textarea"));
	ctd.innerHTML="<i onclick='saveOrUpdate(this)' class='icon-plus btnStyle'></i>";
	tr.appendChild(ctd);
	if(thead.nextSibling){
		thead.parentNode.insertBefore(tr,thead.nextSibling);
	}else{
		thead.parentNode.appendChild(tr);
	}
	
}

function saveOrUpdate(op){
	var tr=$(op).parents("tr")[0];	
	var areas=$(op).parents("tr").find("textarea");
	var strs="";
	for(var i=0;i<areas.length;i++){
		strs+=areas[i].value+"|"
	}
	strs=strs.substring(0, strs.length-1);
	if(strs=="||"){
		showSureMessage("提示","不能全为空!");
		return;
	}
   $.ajax({
       url:jspath+"/admin/note/saveStore",
       type:"post",
       data:{
    	   id:tr.dataId,
    	   recordType:$("#curFile").val(),
    	   strs:strs
       },
       beforeSend:ajaxBefore(),
       success:function(data){
           ajaxAfter();
           if(data.state==1){
                showSureMessage("提示","操作成功!");
                for(var i=0;i<areas.length;i++){
                	areas[i].parentNode.innerHTML=areas[i].value;
                }
                if(op.classList.contains("icon-plus")){
                	tr.dataId=data.id;
                	op.parentNode.innerHTML="<i onclick='editTr(this)' class='icon-edit btnStyle'></i><i onclick='delTr(this)' class='icon-minus btnStyle'></i>"; 
                }else{
                	op.edit=false;
                }
                setTableColor();
           }
       }
   })
}

function seleTit(target,docId){
	$(".titleActive").removeClass("titleActive");
	$(target).addClass("titleActive");
	$("#curFile").val(docId);
	$.ajax({
        url:jspath+"/admin/note/queryTypeRecordList",
        type:"post",
        data:{
            id:docId
        },
        success:function(data){
        	if(data.state==1){
        		$(".emptyShow").hide();
        		$("#tablearea").html("");
        		var table=document.createElement("table");
        		var thead=document.createElement("thead");
        		table.appendChild(thead);
        		var th=document.createElement("th");
        		th.innerHTML="时间";
        		th.style.width="130px";
        		thead.appendChild(th);        		
        		for(var i in data.defineList){
        			var th=document.createElement("th");
        			th.innerHTML=data.defineList[i].defineName;
        			thead.appendChild(th);
        		}
        		var tdnum=thead.childElementCount-1;
        		var th=document.createElement("th");
        		th.style.width="94px";
        		th.innerHTML="<i onclick='addTr()' class='icon-plus btnStyle'></i>";
    			thead.appendChild(th);
    			var h1=document.createElement("h1");
    			$(h1).html($(".titleActive .gentitle").html());
    			$(h1).addClass("headTitle");
    			$("#tablearea").append(h1);
        		$("#tablearea").append(table);
        		var ctr,ctd;  
        		var tr=document.createElement("tr");
        		var td=document.createElement("td");
        		for(var i in data.storeList){
        			ctr=tr.cloneNode();
        			ctr.dataId=data.storeList[i].id;
        			ctd=td.cloneNode();
        			ctd.innerHTML=formatTime(data.storeList[i].createDate);        				
        			ctr.appendChild(ctd);
        			for(var j=1;j<tdnum+1;j++){
        				ctd=td.cloneNode();
        				ctd.innerHTML=data.storeList[i]["col"+j];        				
        				ctr.appendChild(ctd);
        			}
        			ctd=td.cloneNode();
    				ctd.innerHTML="<i onclick='editTr(this)' class='icon-edit btnStyle'></i><i onclick='delTr(this)' class='icon-minus btnStyle'></i>";        				
    				ctr.appendChild(ctd);
        			$("table").append(ctr);
        		}
        		setTableColor();
        	}
        }
    })
}
function editTr(opt){
	if(opt.edit){
		saveOrUpdate(opt);
	}else{
		opt.edit=true;
		var tds=$(opt).parents("tr").find("td");
		var textarea=document.createElement("textarea");
		var ctextarea;
		for(var i=1;i<tds.length-1;i++){
			ctextarea=textarea.cloneNode();
			ctextarea.value=tds[i].innerHTML;
			$(tds[i]).html(ctextarea);
		}
	}
}
function delTr(tar){
	var opt=function(){
		var id=$(tar).parents("tr")[0].dataId;		
		$.ajax({
			url:jspath+"/admin/note/delStore",
			type:"post",
			data:{
				id:id
			},
			success:function(data){
				if(data.state==1){
					showSureMessage("提示", "删除成功！");
					$(tar).parents("tr")[0].remove();
					setTableColor();
				}else{
					showSureMessage("提示", data.message);
				}
			}
		})
	}
	showMsg("提示","确认删除当前行？",opt);
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
        url:jspath+"/admin/note/delNote",
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

function showAddColumDlg(){
	$("#addColumDialog").show();
	$("#addColumArea").html("");
	$("#infoGround").show();
}

function hideAddColumDlg(){
	$("#addColumDialog").hide();
	$("#infoGround").hide();
}

function dlgAddColum(){
	var div=document.createElement("div");
	div.innerHTML="字段名称：<input><i onclick='dlgDelColum(this)' class='icon-minus btnStyle'></i>";
	$("#addColumArea").append(div);
}

function dlgDelColum(opt){
	var div=$(opt).parents("div")[0];
	div.remove();
}

function createNote(){
	if(isNaN($("select").val())){
		showSureMessage("提示","请选择文件夹！");
		return;
	}
	var name=$("#addColumName input")[0].value;
	var column=$("#addColumArea input");
	var st="";
	for(var i=0;i<column.length;i++){
		if(column[i].value==""){
			showSureMessage("提示","不允许出现空字段！");
			return;
		}
		st+=column[i].value+","
	}
	st=st.substring(0, st.length-1);
	if(st==""){
		showSureMessage("提示","请设置字段！");
		return;
	}
	save(name,st);
}

function delRecordType(){
	var delType=function(){
		var id=$(".titleActive").attr("dataId")
		if(id==null){
			return;
		}
		$.ajax({
	        url:jspath+"/admin/note/delRecordType",
	        type:"post",
	        data:{
	        	id:id
	        },
	        beforeSend:ajaxBefore(),
	        success:function(data){
	            ajaxAfter();
	            if(data.state==1){
	            	$(".titleActive")[0].remove();
	            	$(".emptyShow").show();
	            	$("#tablearea").html("");
	            }else{
	            	showSureMessage("提示", data.message);
	            }
	        }
	    })
	}
	showMsg("提示","确认删除类型?",delType);
}

function save(name,colums){
    $.ajax({
        url:jspath+"/admin/note/saveDefine",
        type:"post",
        data:{
        	name:name,
        	colums:colums,
            id:$("select").val()
        },
        beforeSend:ajaxBefore(),
        success:function(data){
            ajaxAfter();
            if(data.state==1){
            	hideAddColumDlg();
            	loadFiles();
            }
        }
    })
}

Date.prototype.format = function(fmt) { 
    var o = { 
       "M+" : this.getMonth()+1,                 //月份 
       "d+" : this.getDate(),                    //日 
       "h+" : this.getHours(),                   //小时 
       "m+" : this.getMinutes(),                 //分 
       "s+" : this.getSeconds(),                 //秒 
       "q+" : Math.floor((this.getMonth()+3)/3), //季度 
       "S"  : this.getMilliseconds()             //毫秒 
   }; 
   if(/(y+)/.test(fmt)) {
           fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
   }
    for(var k in o) {
       if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
   return fmt; 
}

function formatTime(long){
	var date=new Date(long);
	return date.format("yyyy-MM-dd hh:mm");
}




/**
 * 为表格设置隔行变色
 */
function setTableColor(){
	var trs=$("table").find("tr");
	for(var i=0;i<trs.length;i++){
		if(i%2==0){
			$(trs[i]).addClass("colorTr");
		}else{
			$(trs[i]).removeClass("colorTr");
		}
	}
}