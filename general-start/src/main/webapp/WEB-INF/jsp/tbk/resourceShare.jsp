<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="网站模板，模型，教程下载">
<meta name="description" content="分享各类精美的资源，有游戏模型，ps素材，网站模板等等">
<jsp:include page="../../../tbk/commonjsp/commonTop.jsp"></jsp:include>
<title>资源分享</title>
<link rel="stylesheet" type="text/css" href="${resPath }/tbk/css/resourceShare.css"  >
<link rel="stylesheet" href="${resPath }/chajian/Jcrop-0.9.12/css/jquery.Jcrop.css">
</head>
<body>
	<!--     开始：顶部联系方式 -->
    <div class="menu-box topMenu"  align="center">
        <jsp:include page="../../../tbk/commonjsp/top.jsp"></jsp:include>
    </div>
    <!--     结束：顶部联系方式 -->
    <!--     开始：导航条 -->
    <div class="anavi1" ></div>
    <div class="menu-box anavi2" align="center" >
    	<jsp:include page="../../../tbk/commonjsp/navigate.jsp"></jsp:include>
    </div>
    <!--     结束：导航条 -->
    <!--     开始：最新文章区域 -->
    <div align="center" style="height: 710px;clear: both;">
		<div style="width: 1000px;">
	   		<div style="margin-top: 20px;width: 70px;float: right;background-color: white;">
	   			<input type="button" onclick="topicUp()" style="cursor: pointer;border: none;" value="资源分享">
	   		</div>
		</div>
    	<div align="center" class="menu-box" style="clear: both;float: left;padding-top: 10px;padding-bottom: 10px;">
	    	<div id="selectType"  style="width: 800px;">
	    		<div>
		    		<span style="float: left">一级目录：</span>
		    		<ul>
		    			<li>
		    				<span class="chooseBtn checked" typeId="-1" onclick="selectTypeForOne(-1,this,1)">全部</span>
		    			</li>
			    		<c:forEach items="${codeList }"  var="code">
			    			<li style="margin-left: 10px;">
			    				<span class="chooseBtn" typeId="${code.id}" onclick="selectTypeForOne('${code.id}',this,1)">${code.name}</span>
			    			</li>
			    		</c:forEach>
		   			</ul>
	    		</div>
	    		<div id="secondMenu" style="clear: both;padding-top: 20px;display: none;">
		    		<span style="float: left">二级目录：</span>
		    		<ul></ul>
	    		</div>
	    		<div id="thirdMenu" style="clear: both;padding-top: 20px;display: none;">
		    		<span style="float: left">三级目录：</span>
		    		<ul></ul>
	    		</div> 
	    	</div>
    	</div>
    	<div id="contextShare"  align="center">
    		<div class="context1" align="center">
    			<ul id="contextUl">
    			</ul>
    		</div>
    	</div>
    </div>
    <!-- start:弹出框 -->
    <div id="picView" class="custom-customModel" style="width: 860px;height: 570px;display: none;" >
		<div id="picShowTitle" class="custom-customModel-header" style="padding: 0px 15px;cursor: move;" >
			<button type="button" onclick="closeDialog(this)" class="custom-customModel-header-close">×</button>
			<h3 id="picTitle">图片预览</h3>
		</div>
		<div class="custom-customModel-body" style="height: 516px;overflow: hidden;">
			<ul id="picUl" style="width: 2600px;">
				<li style="float: left;"><img style="position: relative;width: 860px;height: 516px;" src=""></li>
				<li style="float: left;"><img style="position: relative;width: 860px;height: 516px;" src=""></li>
				<li style="float: left;"><img style="position: relative;width: 860px;height: 516px;" src=""></li>
			</ul>
			<div id="nav-arrows" class="nav-arrows" style="display: block;">
				<a href="javascript:void(0)" class="leftPic" onclick="showBefore()" >Previous</a>
				<a href="javascript:void(0)" class="rightPic" onclick="showNext()">Next</a>
			</div>
		</div>
		<div class="custom-customModel-bottom" style="position: relative;text-align: center;background-color:#fff;padding: 0 0;" align="center">
			<ol class="dots">
				<li onclick="" picIndex="0" class="dot active"></li>
				<li onclick="" picIndex="1" class="dot"></li>
				<li onclick="" picIndex="2" class="dot"></li>
			</ol>
		</div>
	</div> 

    <div id="picUp" class="custom-customModel" style="width: 1000px;height: 660px;z-index: 1100;display: none;" >
    	<input type="hidden" id="picPlace" >
		<div class="custom-customModel-header" style="padding: 0px 15px;" >
			<button type="button" onclick="closeDialog(this)" class="custom-customModel-header-close">×</button>
			<h3>图片上传</h3>
		</div>
		<div class="custom-customModel-body" style="height: 600px;overflow: hidden;">
			<img id="element_id" style="position: relative;width: 1000px;height: 600px;" src="http://screenshot.dwstatic.com/yysnapshot/ae76bfb09b1d1d888499ca4e50144c39ae6e0735?imageview/4/0/w/338/h/190/blur/1">
		</div>
		<div class="custom-customModel-bottom" style="position: relative;text-align: center;background-color:#fff;padding: 0 0;" align="center">
			<a href="#" class="custom-customModel-btnCancel" onclick="closeDialog(this)" >取消</a>
			<a href="javascript:void(0)" style="margin-left: 50px;" class="custom-customModel-btnSave" onclick="surePic()">上传</a>
		</div>
	</div>
    
	<div id="myModal" class="custom-customModel"  style="display: none;">
		<div class="custom-customModel-header">
			<button type="button" onclick="closeDialog(this)" class="custom-customModel-header-close">×</button>
			<h3>分享您的资源</h3>
		</div>
		<div class="custom-customModel-body" style="height: 330px;">
		<form id="shareForm">
			<div class="body-div2" style="height: 320px;">
				<div>
					<input id="choosePic" style="display: none;" type="file" name="file" onchange="fileChange(this);">
				</div>
				<div class="div2-div1" style="height: 180px;border-bottom: 0px;">
					<div class="div1-div1" align="left" style="border-bottom: 1px #dadada solid;">
							<div class="div1-div1" >须知：1、分享资源将进行严格审核，您的分享将是对本站的最大支持。</div>
							<div class="div1-div2">2、分享来源必须是百度网盘。</div>
							<div class="div1-div2">3、分享内容应不侵犯他人权益，分享的内容仅供学习,严禁商用。</div>
							<div class="div1-div2">4、第一张图默认为封面。</div>
					</div>
					<div style="padding: 6px 0;">
						<textarea placeholder="请输入标题简介，输入的内容应该言简意骇！" id="resourceDesc" style="height: 30px;width:530px;resize: none;line-height: 15px;background-color: #fff;background-image: none;border: 1px solid #ccc;box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);" ></textarea>
					</div>
					<div>
						类别一：<select id="shareTypeOne" onchange="selectType(1)" style="width: 120px;background: rgb(255, 255, 255);">
							<option value="-1">请选类别</option>
							<c:forEach items="${codeList }"  var="code">
								<option value="${code.id}">${code.name}</option>
			    			</c:forEach>
						</select>
						类别二：<select id="shareTypeTwo" onchange="selectType(2)" style="width: 120px;background: rgb(255, 255, 255);">
							<option value="-1">请选类别</option>
						</select>
						类别三：<select id="shareTypeThree" onchange="selectType(3)" style="width: 120px;background: rgb(255, 255, 255);"><option value="-1">请选类别</option></select>
					</div>
					<div class="div2-div2">url:<input type="text" id="shareUrl" style="width: 320px;" > 密码：<input type="text" id="sharePwd" style="width: 100px;" maxlength="4"></div>
					<div class="picview">
						<img id="selepicOne"  onclick="choosePicBtn(1)" alt="" src="${resPath }/tbk/images/image.png">
					</div>
					<div class="picview"><img id="selepicTwo" onclick="choosePicBtn(2)"  alt="" src="${resPath }/tbk/images/image.png"></div>
					<div class="picview"><img id="selepicThree" onclick="choosePicBtn(3)"  alt="" src="${resPath }/tbk/images/image.png"></div>
				</div>
			</div>
			</form>
		</div>
		<div class="custom-customModel-bottom" >
			<a href="javascript:void(0)" id="saveBtn" class="custom-customModel-btnSave" onclick="saveShareResource(this)">保存</a>
			<a href="#" class="custom-customModel-btnCancel" onclick="closeDialog(this)" >关闭</a>
		</div>
	</div> 

	<div id="bqTip" class="custom-customModel" style="display: none;">
		<div class="custom-customModel-header">
			<h3>版权说明</h3>
		</div>
		<div class="custom-customModel-body">
			<div class="body-div1">
				<div class="div1-div1">
					<div align="left" class="textDiv1">
							<div class="textDive2">1、根据有关法律规定用户禁止违法上传、存储并分享未经授权的作品，其中包括：正在热播、热卖的作品、出版、影视、音乐等专业机构出版或者制作的作品。</div>
							<div class="textDive2">2、本站所提供的资源大都来自于互联网，版权归原作者所有！由于收集资源已几经转载，因此大多作者不详。如果本站的转载而侵犯了您的权益，站长向您表示深深的歉意，希望您能多多包涵且期望您立即联系站长，站长会进行相应的处理。</div>
							<div class="textDive2">3、本站对非法转载，盗版行为的发生不具备充分的监控能力。</div>
							<div class="textDive2">4、本站分享的资源均仅供学习参考用途<em>不允许进行商用</em>，由于商业用途而引起的纠纷，本站不负任何责任。</div>
							<div class="textDive2">5、本站属于个人网站，所有资源资源均免费分享，不会收取任何费用，不进行任何商用。</div>
							<div class="textDive2">6、本站收集的资源均是经过精挑细选而来且分享的每一份资源均经过仔细阅览，保证资源非常实用，希望您能好好欣赏学习。</div>
							<div class="textDive2">7、本站分享的资源均存放在百度网盘。</div>
							<div class="textDive2">8、本站分享的资源将一直存在，无须一次性全部下载，只需下载您需要的资源即可切莫贪多，本站对下载次数进行了限制，<em>每天只允许下载30</em>次。</div>
					</div>
				</div>
				<div class="div1-div2" align="right">
				 	您是否已阅读并同意本站的做法<input type="checkbox" onchange="showSureBtn()">
				</div>
			</div>
		</div>
		<div class="custom-customModel-bottom" >
			<a href="javascript:void(0)" id="agreeBtn" class="custom-customModel-btnCancel" onclick="agreeBtn()">同意</a>
			<a href="#" class="custom-customModel-btnCancel" id="noBtn" onclick="noBtn()" >不同意</a>
		</div>
	</div>
	<!--end:弹出框-->
	<!--start:遮罩层-->
	<div id="custom-background" style="display: none;"></div>
	<!--end:遮罩层-->
    <input type="hidden" id="x1">
	<input type="hidden" id="y1">
	<input type="hidden" id="x2">
	<input type="hidden" id="y2">
	<input type="hidden" id="w">
	<input type="hidden" id="h">
    <input type="hidden" id="isRead" value="${isRead}">
    <div id="hidePic" style="display: none;"></div>
    <!--     开始：页面底部 -->
    <div class="aFootBefore"></div>
    <div align="center"  class="menu-box aFootTotal">
    	<jsp:include page="../../../tbk/commonjsp/footer.jsp"></jsp:include>
    </div>
    <!--     结束：页面底部  -->
    <jsp:include page="../../../tbk/commonjsp/commonBottom.jsp"></jsp:include>
    <script src="${resPath }/chajian/Jcrop-0.9.12/js/jquery.Jcrop.js"></script>
    <script src="${resPath }/tbk/js/resourceShare.js"></script>
</body>
</html>