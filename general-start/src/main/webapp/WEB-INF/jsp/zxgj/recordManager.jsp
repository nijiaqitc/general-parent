<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>一些记录管理</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"  />
<link rel="stylesheet" href="${resPath }/zxgj/css/recordManager.css">
<link rel="stylesheet" href="${resPath }/jsTool/customPage/customPage.css" />
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
                     	<i onclick="showAddColumDlg()" style="background-color: cornflowerblue;" class="icon-plus btnStyle"></i>
                     	<i onclick="delRecordType()" style="background-color: cornflowerblue;" class="icon-minus btnStyle"></i>
                     </div>
                </div>
                <div id="genList"></div>
            </div>
            <div class="rightContextArea" align="left">
            	<input id="hideDocId" type="hidden">
            	<input id="curFile" type="hidden">
                <div class="emptyShow">请选择标签进行查看...</div>
                <div align="left" class="outdiv" id="tablearea" >
                </div>
                <div id="pageDiv"></div>
            </div>
        </div>
        <div style="height: 100px;display: block;"></div>
        <div id="addColumDialog" class="csdialogdlg" align="center" >
        	<div class="innarea">
				<div id="addColumName">类型名称：<input><i onclick="dlgAddColum()" class="icon-plus btnStyle"></i></div>
				<div id="addColumArea">
					<div>字段名称：<input><i onclick="dlgDelColum(this)" class="icon-minus btnStyle"></i></div> 
				</div>
        		<div  align="center" class="dlgarea"  >
					<div onclick="hideAddColumDlg()" class="dlgInBtn" style="margin-left: 30px;">隐藏</div>
					<div onclick="createNote()" class="dlgInBtn" style="margin-left: 10px;">确定</div>
				</div>
        	</div>
        </div>
        
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
<script type="text/javascript" src="${resPath }/jsTool/customPage/customPage.js"></script>
<!--     加载用户自定义配置 -->
<script type="text/javascript" src="${resPath }/zxgj/js/recordManager.js"></script>
</html>