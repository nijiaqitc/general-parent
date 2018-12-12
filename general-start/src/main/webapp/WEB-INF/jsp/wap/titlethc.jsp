<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"  "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>网站介绍</title>
<jsp:include page="../../../wap/commonwap/common.jsp"></jsp:include>
<style type="text/css">
	.st0{
		font-size: 20px;
		clear: both;
		font-weight: bold;
	}
	.st1{
		border-top: 1px dashed #ddd;border-bottom: 1px solid #ddd;;overflow: auto;clear: both;
	}
	.st2{
		float: left;width: 100px;text-indent: 12px;
	}
	.st3{
		clear: both;text-indent: 24px;
	}
	.st4{
		overflow: auto;clear: both;
	}
	.st5{
		cursor: pointer;padding-left: 14px;
	}
	.showSaveDlg{
        background-color: #fff;
        width: 280px;
        line-height: 30px;
        position: fixed;
        margin-left: 50px;
        margin-top:80px;
        z-index: 99;
        display: none;
    }
    .model{
        width:100%;height:100%;position: fixed;z-index: 50;background-color: #222;opacity: 0.5;display: none;
    }
</style>
<script type="text/javascript">
	function addTitle(e){
		var tr=$(e).parent().parent()[0];
		var inputs=$(tr).find("input");
		var desc=$(tr).find("textarea")[0];
		var index=inputs[0];
		var title=inputs[1];
		var grade=inputs[2];
		var parentId=inputs[3];
		if(title.value==""){
			alert("请输入标题")
			return 
		}
		var type=$(tr).find("select").val();
		$.ajax({
			url:"${path}/novel/addTitle",
			data:{
				indexOne:index.value,
				title:title.value,
				grade:grade.value,
				type:type,
				contextDesc:desc.value,
				parentId:parentId.value
			},
			type:"post",
			success:function(data){
				if(data.state==1){
					alert("保存成功！")
					var str="";
					if(type=="0"){
						str="<div align='left' class='st0'>"+data.title+"</div>";
					}else{
						str="<div class='st1'>"+
							"<div align='left' class='st2'>第"+data.index+"章</div>"+
							"<div style='float: left;'>"+data.title+"</div>"+
							"<div style='float: right;margin-right: 6px;'>（"+data.grade+"）</div>"+
						"</div>"+
						"<div class='st3' align='left'>"+data.desc+"</div>"+
						"<div class='st4'>"+
						"</div>";
					}
					var tempNode=document.createElement("div");
					tempNode.innerHTML=str;
					tr.parentNode.insertBefore(tempNode,tr);
				}
			}
		})		
	}
	
	function addInput(e){
		var dd=e.parentNode.parentNode.parentNode;
		var str="<div align='left' style='clear:both;border-top: 1px solid red;'>"+
			"<div><input style='height: 24px;width: 40px;margin-left: 8px;' name='index' /><input style='height: 24px;width: 100px;margin-left: 8px;' name='title' /><input style='height: 24px;width: 100px;margin-left: 8px;' name='grade' /> </div>"+
			"<div><textarea style='width: 240px;margin-left: 8px;'></textarea><select style='display:none;'><option value='3'>章节名</option><option value='2'>卷名</option></select>"+
			"<input type='hidden' name='parentId' value='"+e.previousElementSibling.value+"'/><label style='border: 1px solid #ddd;margin-left: 10px;font-size: 30px;' onclick='addTitle(this)'>保存</label></div>"+
		"</div>";
		var tempNode=document.createElement("div");
		tempNode.innerHTML=str;
		if(dd.nextElementSibling){
			dd.parentNode.insertBefore(tempNode.firstChild,dd.nextElementSibling);
		}else{
			dd.parentNode.appendChild(tempNode.firstChild);
		}
	}
	
	function modiInput(e){
		var dd=e.parentNode.parentNode.parentNode;
		var v=dd.getElementsByClassName("hideInfo")[0].innerHTML;
		var values=v.split("||");
		var str="<div align='left' style='clear:both;border-top: 1px solid red;'>"+
			"<div><input style='height: 24px;width: 40px;margin-left: 8px;' type='hidden' value='"+values[0]+"' name='id' /><input style='height: 24px;width: 40px;margin-left: 8px;' value='"+values[2]+"' name='index' /><input style='height: 24px;width: 100px;margin-left: 8px;' value='"+values[3]+"' name='title' /><input value='"+values[4]+"' style='height: 24px;width: 100px;margin-left: 8px;' name='grade' /> </div>"+
			"<div><textarea style='width: 240px;margin-left: 8px;'>"+values[5]+"</textarea><select style='display:none;'><option value='3'>章节名</option><option value='2'>卷名</option></select>"+
			"<input type='hidden' name='parentId' value='"+values[1]+"'/><label style='border: 1px solid #ddd;margin-left: 10px;font-size: 30px;' onclick='modi(this)'>修改</label></div>"+
		"</div>";
		var tempNode=document.createElement("div");
		tempNode.innerHTML=str;
		if(dd.nextElementSibling){
			dd.parentNode.insertBefore(tempNode.firstChild,dd.nextElementSibling);
		}else{
			dd.parentNode.appendChild(tempNode.firstChild);
		}
	}
	
	function modi(e){
		console.info(e);
		var tr=e.parentNode.parentNode;
		var inputs=$(tr).find("input");
		var desc=$(tr).find("textarea")[0];
		var id=inputs[0];
		var index=inputs[1];
		var title=inputs[2];
		var grade=inputs[3];
		var parentId=inputs[4];
		if(title.value==""){
			alert("请输入标题")
			return 
		}
		var type=$(tr).find("select").val();
		$.ajax({
			url:"${path}/novel/updateTitle",
			data:{
				indexOne:index.value,
				title:title.value,
				grade:grade.value,
				type:type,
				contextDesc:desc.value,
				id:id.value,
				parentId:parentId.value
			},
			type:"post",
			success:function(data){
				if(data.state==1){
					alert("保存成功！")
					var str="";
					if(type=="0"){
						str="<div align='left' class='st0'>"+data.title+"</div>";
					}else{
						str="<div class='st1'>"+
							"<div align='left' class='st2'>第"+data.index+"章</div>"+
							"<div style='float: left;'>"+data.title+"</div>"+
							"<div style='float: right;margin-right: 6px;'>（"+data.grade+"）</div>"+
						"</div>"+
						"<div class='st3' align='left'>"+data.desc+"</div>"+
						"<div class='st4'>"+
						"</div>";
					}
					var tempNode=document.createElement("div");
					tempNode.innerHTML=str;
					tr.parentNode.insertBefore(tempNode,tr);
					tempNode.previousElementSibling.remove();
				}
			}
		}) 	 
	}
	
	function delInput(e){
		var id=e.previousElementSibling.value;
		$.ajax({
			url:"${path}/novel/delTitle",
			data:{
				id:id
			},
			type:"post",
			success:function(data){
				e.parentNode.parentNode.parentNode.remove();
			}
		})		
	}
	function saveJuanName(){
        $.ajax({
            url:"saveJuanName",
            type:"post",
            data:{
                newName:$("#newName").val(),
                oldName:$("#oldName").val(),
                id:$("#titleId").val(),
                docId:$("#docId").val()
            },
            success:function(data){
                $(".showSaveDlg").hide();
                $(".model").hide();
                alert("操作成功！")
                location.reload()
            }
        })
    }
	function showModiBtn(title,id){
        $("#oldName").val(title);
        $("#titleId").val(id);
        showDialog();
    }
	function showDialog(){
        $(".showSaveDlg").show();
        $(".model").show();
        
    }
	function hideDialog(){
        $(".showSaveDlg").hide();
        $(".model").hide();
        $("#newName").val("");
        $("#oldName").val("");
        $("#titleId").val("");
    }
</script>
</head>
<body>
	<!-- 	顶部div开始 -->
	<!-- 	顶部div结束 -->
	<div class="showSaveDlg" align="center">
        <div style="padding: 14px;">
            <div>原卷名：<input id="oldName" type="text"  readOnly="true" style="background-color: #f2f2f2;" /></div>
            <div>新卷名：<input id="newName" type="text"/></div>       
            <div>
               
               <input type="button" value="提交" onclick="saveJuanName()"/>
               <input type="button" onclick="hideDialog()" value="关闭" style="margin-left:18px;"/>
            </div>
        </div>
    </div>
    <div class="model"></div>
	<!-- 	正文部分开始 -->
	<div class="textContext" align="center">
		<div style="padding-top: 20px;">
			<div style="height: 30px;font-size: 24px;width: 90%;line-height: 26px;display: block;">
			 ${titleName}
			   <input type="hidden" id="docId" value="${docId}"/>
			   <input type="hidden" id="titleId" />
	           <div style="float: right;height: 100%;width: 30px;border: 1px solid #aaa;" onclick="showDialog()">+</div>
	        </div>
			<c:forEach items="${titleList }"  var="title">
				<div style="margin-top: 10px;">
					<c:if test="${title.type==2}">
					   <div style="overflow: auto;">
						<div align="left" style="float:left;height: 24px;line-height: 24px;" class='st0'>${title.title}</div>
						<div style="float:right;margin-right: 8px;" onclick="showModiBtn('${title.title}',${title.id})">※</div>
					   </div>
					</c:if>
					<c:if test="${title.type==3}">
						<div class="st1" >
							<div class="hideInfo" style="display: none;" >${title.id}||${title.parentId}||${title.indexOne}||${title.title}||${title.grade}||${title.contextDesc}</div>
							<div align="left" class="st2">第${title.indexOne}章</div>
							<div style="float: left;"><a href="readDoc/${title.id}">${title.title}</a></div>
							<div style="float: right;margin-right: 6px;">（${title.grade}）</div>
						</div>
						<div style="clear: both;text-indent: 24px;" align="left">
							${title.contextDesc}
						</div>
						<div style="overflow: auto;clear: both;">
							<div style="float: right;">
								<input type="hidden" value="${title.parentId}" />
								<label onclick="addInput(this)" style='cursor: pointer;padding-left: 14px;'>添加</label>
								<label onclick="modiInput(this)" style='cursor: pointer;padding-left: 14px;'>修改</label>
								<input type="hidden" value="${title.id}" />
								<label onclick="delInput(this)" style='cursor: pointer;padding-left: 14px;'>删除</label> 
							</div>
						</div>
					</c:if>
				</div>
			</c:forEach>
		</div>
		
	</div>
	<!-- 	正文部分结束 -->

	<!-- 	底部通用部分开始 -->
	<jsp:include page="../../../wap/commonwap/bottom.jsp"></jsp:include>
	<!-- 	底部通用部分结束 -->
	<jsp:include page="../../../wap/commonwap/commonBottom.jsp"></jsp:include>
</body>
</html>