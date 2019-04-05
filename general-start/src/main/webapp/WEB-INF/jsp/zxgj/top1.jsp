<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">
    var jspath="${path}"
</script>
<div class="topBarMenuDiv" align="center">
    <div class="incBarMenuDiv">
        <div class="topBarLeft">
        	<img alt="" src="${resPath }/zxgj/img/logomax.png" style="height: 70px;margin-top: 4px;"> 
<!--         	LOGO -->
        </div>
        <div class="topBarRight">
            <ul class="nagivationMenu">
                <li class="nagivationMenuContext">
                    <a href="${path }/">
                        <div class="topFontStyle">首页</div>
                        <div class="nagivationBottom nagivationBottomSelect" ></div> 
                    </a>
                </li>
                <li class="nagivationMenuContext">
                    <a href="${path }/tools">
                        <div class="topFontStyle">工具列表</div>
                        <div class="nagivationBottom"></div>
                    </a>
                </li>
                <li class="nagivationMenuContext">
                    <a href="${path }/docList">
                        <div class="topFontStyle">文章列表</div>
                        <div class="nagivationBottom" ></div>
                    </a>
                </li>
                <li class="nagivationMenuContext">
                    <a href="${path }/aboutUs">
                        <div class="topFontStyle">关于站长</div>
                        <div class="nagivationBottom" ></div>
                    </a>
                </li>
                <li class="moveDiv"></li>
            </ul>
        </div>
    </div>
</div>
<ul class="commonUl">
	<li><a href="#" class="helpTip" ></a></li>
</ul>