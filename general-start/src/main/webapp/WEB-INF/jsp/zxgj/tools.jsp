<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工具列表</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/zxgj/css/tools.css">
</head>
<body>
    <!--     开始：顶部菜单栏-->
    <jsp:include page="top1.jsp"></jsp:include>
    <div style="height: 20px;width: 100%;background-color: #ec8316;"></div>
    <!--     结束：顶部菜单栏 -->
    <!-- 中间正文公用部分 -->
    <div class="contextArea">
		<div align="center">
		  <div class="btnArea">
		      <div align="left">
		          <div class="toolsTitleDec" align="center">代码格式化</div>    
		      </div>
		      <div class="toolsBtn">
		          <div class="btn"><a href="${path }/tools/jsonDecode">JSON格式化</a></div>
		          <div class="btn"><a href="${path }/tools/jsDecode">JS格式化</a></div>
		          <div class="btn"><a href="${path }/tools/sqlDecode">SQL格式化</a></div>
		          <div class="btn"><a href="${path }/tools/cssDecode">CSS格式化</a></div>
		          <div class="btn"><a href="${path }/tools/xmlDecode">XML格式化</a></div>
		          <div class="btn"><a href="${path }/tools/customHtmlDecode">自定义格式化</a></div>
		          <div class="btn"></div>
		          <div class="btn"></div>
		          <div class="btn"></div>
		          <div class="btn"></div>
		      </div>
		  </div>
		  <div class="btnArea">
              <div align="left">
                  <div class="toolsTitleDec" align="center">加密/解密</div>    
              </div>
              <div class="toolsBtn">
                  <div class="btn"><a href="${path }/tools/md5">MD5加密</a></div>
                  <div class="btn"><a href="${path }/tools/javaDecode">RSA加密/解密</a></div>
                  <div class="btn"><a href="${path }/tools/base">BASE64加密/解密</a></div>
                  <div class="btn"><a href="${path }/tools/sha">sha1加密/解密</a></div>
                  <div class="btn"><a href="${path }/tools/jslock">js加密/解密</a></div>
                  <div class="btn"><a href="${path }/tools/encrypt">encryp加密/解密</a></div>
                  <div class="btn"><a href="${path }/tools/des">des加密/解密</a></div>
                  <div class="btn"><a href="${path }/tools/cipherlock">暗号加密/解密</a></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
              </div>
          </div>
          <div class="btnArea">
              <div align="left">
                  <div class="toolsTitleDec" align="center">计算器</div>    
              </div>
              <div class="toolsBtn">
                  <div class="btn"></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
              </div>
          </div>
          <div class="btnArea">
              <div align="left">
                  <div class="toolsTitleDec" align="center">其他</div>    
              </div>
              <div class="toolsBtn">
                  <div class="btn"><a href="${path }/tools/zzbds">正则表达式</a></div>
                  <div class="btn"><a href="${path }/tools/htmlts">HTML调试</a></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
                  <div class="btn"></div>
              </div>
          </div>
		</div>
		<div class="bottomD"></div>
    </div>
    
    <!--     开始：顶部菜单栏-->
    <jsp:include page="bottom.jsp"></jsp:include>
    <!--     结束：顶部菜单栏 -->
    
    <script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
    <script src="${resPath }/zxgj/js/tools.js" type="text/javascript"></script>
    <script type="text/javascript">
		$(document).ready(function(){
			$(".nagivationBottomSelect").removeClass("nagivationBottomSelect");
			$($(".nagivationBottom")[1]).addClass("nagivationBottomSelect");
		})
	</script>
</body>
</html>