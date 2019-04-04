<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>笔记管理</title>
<link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"  />
<jsp:include page="${path}/commonTopLink"></jsp:include>
<link href="${resPath }/jsTool/njqeditor/css/njqEditor_styleSeven.css"  rel="stylesheet">
<style type="text/css">
.btnStyle{
    display: table-cell !important;
    background: #67c2ef;
    color: #fff;
    padding: 4px 0;
    width: 36px;
    display: inline-block;
    text-align: center;
    margin: -10px 20px -10px -20px;
}
.genTitleList{
    cursor: pointer;
}
.genTitleList:hover{
    background-color: #666;color:white;
}
.titleActive{
    background-color: #eee;
}
.rightContextt{
    overflow:auto; height: 800px;background-color: white !important;
}

.rightContextt .leftGenList{
    height:100%;float: left;width: 200px;min-height:660px; background-color: #fff;font-size: 12px;border-right: 1px solid #aaa;overflow: auto;
}
.rightContextt .leftGenList .leftTitleTools{
    cursor: pointer;height: 34px;
}
.rightContextt .leftGenList .gentitle{
    font-weight: 600;text-indent: 12px;height: 30px;line-height: 30px;
}
.rightContextt .leftGenList .gen{
    text-indent: 24px;min-height: 60px;word-break: break-all;
}
.rightContextt .rightContextArea{
    min-height:660px;overflow: hidden;height:100%;
}

.rightContextt .rightContextArea .outdiv{
    margin-left: 10px;margin-right: 10px;height:100%;
}
.rightContextt .rightContextArea .outdiv .topBtns{
    overflow: auto;margin-top: 10px;
}
.rightContextt .rightContextArea .outdiv .topBtns .lefttitles{
    float: left;
}

.rightContextt .rightContextArea .outdiv .topBtns .lefttitles .hidinput{
    background-color: white;border: none;width: 450px;outline:none;
}
.rightContextt .rightContextArea .outdiv .topBtns .righttitles{
    float: right;
}
.rightContextt .rightContextArea .outdiv .topBtns .righttitles span{
    cursor: pointer;
}
.rightContextt .rightContextArea .outdiv .fontLine{
    clear: both;padding:10px 0;color: #aaa;
}
/*定义滚动条宽高及背景，宽高分别对应横竖滚动条的尺寸*/
.leftGenList::-webkit-scrollbar{
    width: 12px;
    height: 30px;
    background-color: #f5f5f5;
}
/*定义滚动条的轨道，内阴影及圆角*/
.leftGenList::-webkit-scrollbar-track{
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
    border-radius: 0px;
    background-color: #f5f5f5;
}
/*定义滑块，内阴影及圆角*/
.leftGenList::-webkit-scrollbar-thumb{
    /*width: 10px;*/
    height: 20px;
    border-radius: 0px;
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
    background-color: #555;
}
</style>
</head>
<body>
	<!-- 通用顶部 -->
	<jsp:include page="${path}/commonTop"></jsp:include>
	<!-- 中下部分 -->
	<div id="centerPlace">
		<!-- 通用左边菜单 -->	
		<jsp:include page="${path}/commonLeft"></jsp:include>
		<!-- 正文区域 -->
		<div id="rightContext" class="rightContextt">
			<div align="left" class="leftGenList">
			    <div class="leftTitleTools">
			         <div><i onclick="createNote()" class="icon-plus btnStyle"></i></div>
			    </div>
			    <div id="genList">
				    <c:forEach items="${gList }" var="gl">
					    <div class="genTitleList menuBox" onclick="seleTit(this,${gl.docId })">
						    <div class="gentitle">${gl.title }</div>
							<div class="gen">${gl.general }</div>
					    </div>
				    </c:forEach>
			    </div>
			</div>
			<div class="rightContextArea" align="left">
			    <div align="left" class="outdiv">
					<div class="topBtns">
					    <div class="lefttitles">
					        <div id="noteTitled"></div>   
						    <input id="noteTitle" placeholder="无标题" class="hidinput" type="text">
					    </div>
					    <div class="righttitles">
					    	<span id="editBtn" style="font-family:cursive;font-weight: 600;" onclick="editNode()" >编辑</span>
					    	<span style="display: none;font-family:cursive;font-weight: 600;" id="saveNoteBtn" onclick="saveOrUpdate()" >保存并阅览</span>
					    	<span style="margin-left: 20px;font-family:cursive;font-weight: 600;" onclick="confirmDel()">删除</span>
					    	<input type="hidden" id="cnoteId">
					    </div>
					</div>
					<div style="height:90%;overflow: auto;">
					   <div style="width: 100%;height:724px;outline:none;background-color: white;font-size: 16px;text-indent: 24px;" id="noteContext"></div>
					   <div id="njqEditorDiv" modelStyle="styleSeven" env="2"></div>
					</div>
			    </div>
			</div>
		</div>
		<!-- 通用底部 -->	
		<jsp:include page="${path}/commonBottom"></jsp:include>
	</div>
</body>
<jsp:include page="${path}/commonBottomLink"></jsp:include>
<script src="${resPath }/common/js/publicJs.js"></script>
<!-- start:公共页，存放公共框 -->
<jsp:include page="${path}/publicJsp"></jsp:include>
<!-- end:公共页，存放公共框 -->


    <!--     加载用户自定义配置 -->
    <script type="text/javascript" src="${resPath }/jsTool/njqeditor/js/njqEditor_config.js"></script>
<script type="text/javascript">
	function seleTit(target,docId){
		if($("#saveNoteBtn").css("display")!="none"){
			saveOrUpdate();
		}
 		$(".titleActive").removeClass("titleActive");
		$(target).addClass("titleActive");
		$("#editBtn").show();
		$("#saveNoteBtn").hide();
		$("#noteTitled").show();
        $("#noteTitle").hide();
		$.ajax({
			url:"readNote",
			type:"post",
			data:{
				docId:docId
			},
			success:function(data){
				$("#cnoteId").val(data.note.id);
				$("#noteContext").html(data.note.text);
				$("#noteTitled").html(data.note.title);
				njq.setHide();
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
		   url:"saveNote",
		   type:"post",
		   data:{
			   id:$("#cnoteId").val(),
			   title:$("#noteTitle").val(),
			   text:njq.getContent(),
			   general:njq.getContentTxt()
		   },
		   async:false,
		   beforeSend:ajaxBefore(),
		   success:function(data){
			   ajaxAfter();
			   if(data.state==1){
				    showSureMessage("提示","操作成功!");
				   	$("#cnoteId").val(data.note.id);
				   	$("#noteContext").show();
			    	njq.setHide();
			    	$("#noteContext").html(njq.getContent());
			    	$(".titleActive .gentitle").html(data.note.title);
			    	$(".titleActive .gen").html(data.gen.general);
			    	$(".titleActive").click(function(){
                        seleTit(this,data.note.id);
                    })
			    	$("#saveNoteBtn").hide();
			    	$("#editBtn").show();
			   }
		   }
	   })
    }
    function confirmDel(){
    	if($("#cnoteId").val()==""||$("#cnoteId").val()==null){
    		return;
    	}
    	showMsg("提示","确认删除？",delNote);
    }
    function delNote(){
    	$.ajax({
    		url:"delNote",
    		type:"post",
    		data:{
    			docId:$("#cnoteId").val()
    		},
    		success:function(data){
    			if(data.state==1){
	    			$(".titleActive").remove();
	    			$("#noteContext").html(null);
	    			$("#cnoteId").val(null);
    			}
    		}
    	})
    }

    function createNote(){
    	if($("#saveNoteBtn").css("display")!="none"){
            save();
        }else{
        	resetEmpty();
        }
        
    }
    
    function save(){
    	$.ajax({
            url:"saveNote",
            type:"post",
            data:{
                id:$("#cnoteId").val(),
                title:$("#noteTitle").val(),
                text:njq.getContent(),
                general:njq.getContentTxt()
            },
            beforeSend:ajaxBefore(),
            success:function(data){
                ajaxAfter();
                if(data.state==1){
                     $(".titleActive .gentitle").html(data.note.title);
                     $(".titleActive .gen").html(data.gen.general);
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
            "<div class='gen'></div>"+
        "</div></div>";
        $("#genList")[0].insertBefore($(str).children(":first")[0],$("#genList").children(":first")[0]);
        $("#noteTitled").hide();
        $("#noteTitle").show();
        $("#editBtn").hide();
        $("#saveNoteBtn").show();
        $("#noteContext").hide();
        $("#cnoteId").val(null);
        njq.setShow();
        $("#noteTitle").val(null);
    }
</script>
</html>