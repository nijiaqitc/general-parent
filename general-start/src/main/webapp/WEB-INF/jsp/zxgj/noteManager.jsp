<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>笔记管理</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"  />
<link href="${resPath }/jsTool/njqeditor/css/njqEditor_styleSeven.css"  rel="stylesheet">
<link rel="stylesheet" href="${resPath }/zxgj/css/noteManager.css">
</head>
<body>
    <div id="rightContext" class="rightContextt">
            <div align="left" class="leftGenList">
                <div class="leftTitleTools">
                     <div>
                     	<select  onchange="inputPwd()" class="selectSt">
	                     	<option>请选择文件夹</option>
	                     	<c:forEach items="${fdList}" var="fd">
	                     		<option value="${fd.id}">
	                     		<c:if test="${fd.isLock==1 }">*</c:if>
	                     		${fd.folderName}
	                     		</option>
	                     	</c:forEach>
                     	</select>
                     </div>
                     <div style="clear: both;">
                     	<i onclick="addFolderDlg()" class="icon-plus btnStyle"></i>
                     	<i onclick="delFolder()" class="icon-minus btnStyle"></i>
                     	<i onclick="createNote()" style="background-color: cornflowerblue;" class="icon-plus btnStyle"></i>
                     	<i onclick="" style="background-color: cornflowerblue;" class="icon-plus btnStyle"></i>
                     </div>
                </div>
                <div id="genList"></div>
            </div>
            <div style="display:block;width: 210px;float:left;">
            	<br>
			</div>
            <div class="rightContextArea" align="left">
                <div class="emptyShow">请选择标签进行查看...</div>
                <div align="left" class="outdiv" style="display: none;">
                    <div class="topBtns">
                        <div class="lefttitles">
                            <div id="noteTitled"></div>   
                            <input id="noteTitle" placeholder="无标题" class="hidinput" type="text" autocomplete="off">
                        </div>
                        <div class="righttitles">
                            <span class="btn" id="editBtn" onclick="editNode()" style="font-family:cursive;font-weight: 600;">编辑</span>
                            <span class="btn" style="display: none;font-family:cursive;font-weight: 600;" id="saveNoteBtn" onclick="saveOrUpdate()" >保存并阅览</span>
                            <span class="btn" style="margin-left: 20px;font-family:cursive;font-weight: 600;" onclick="confirmDel()">删除</span>
                            <input type="hidden" id="cnoteId">
                        </div>
                    </div>
                    <hr style="margin-top: 20px;margin-bottom: 20px;">
                    <div style="overflow: auto;">
                       <div id="njqEditorDiv" modelStyle="styleSeven"></div>
                    </div>
                    <div style="outline:none;background-color: white;text-indent: 24px;" id="noteContext"></div>
                </div>
            </div>
        </div>
        <div style="height: 100px;display: block;"></div>
        
        
        <div id="csdialog" class="csdialogdlg" align="center">
			<div class="innarea">
				密码：<input id="userPwd" type="password">
				<div  align="center" class="dlgarea"  >
					<div onclick="hideDig()" class="dlgInBtn" style="margin-left: 30px;">隐藏</div>
					<div onclick="checkLock()" class="dlgInBtn" style="margin-left: 10px;">确定</div>
				</div>
			</div>
			
			<div class="innarea">
				<div align="left">文件夹名：<input id="folderName" class="inputCss" type="text"  ></div>
				<div align="left">需 密 码：<span style="margin-left: 20px;">
					是：<input onclick="showpwd(1)" type="radio" name="npwd" checked="checked" > 
					否：<input onclick="showpwd(0)" type="radio" name="npwd"></span></div>
				<div id="pwdarea">
					<div align="left">输入密码：<input id="folderPwd" class="inputCss"  type="password" > </div>
					<div align="left">再次密码：<input id="refolderPwd" class="inputCss"  type="password" > </div>
				</div>
				<div  align="center" class="dlgarea" >
					<div onclick="hideDig()" class="dlgInBtn" style="margin-left: 30px;">隐藏</div>
					<div onclick="saveFold()" class="dlgInBtn" style="margin-left: 10px;">确定</div>
				</div>
			</div>
		</div>
</body>
<jsp:include page="${path}/commonBottomLink"></jsp:include>
<script src="${resPath }/common/js/publicJs.js"></script>
<!-- start:公共页，存放公共框 -->
<jsp:include page="${path}/publicJsp"></jsp:include>
<script type="text/javascript">
var jspath="${path}"
</script>
<!--     加载用户自定义配置 -->
<script type="text/javascript" src="${resPath }/jsTool/njqeditor/js/njqEditor_config.js"></script>
<script type="text/javascript" src="${resPath }/zxgj/js/noteManager.js"></script>
</html>