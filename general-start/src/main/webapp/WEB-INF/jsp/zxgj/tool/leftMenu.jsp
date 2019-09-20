<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<body>
<style type="text/css">
.navbar li a{
    text-align: left;
    padding-left: 40px;
}
.active{
    background-color: aliceblue;
}
</style>
	<div style="float:left;width:20%;height:800px;max-width: 220px;min-width: 190px;">
		<div class="panel panel-default" style="height:100%;margin-bottom: 0px;">
		  <div class="panel-body" >
		    <nav class="navbar  navbar-fixed-top" style="position: relative;" id="sidebar-wrapper" role="navigation">
			  <ul class="nav sidebar-nav" style="height: 760px;overflow: auto;">
			    <li> <a href="${path }/tools/inx/jsondecode"> JSON格式化 </a> </li>
			    <li> <a href="${path }/tools/inx/cssdecode"> <i class="fa fa-fw fa-home"> </i> CSS格式化 </a> </li>
			    <li> <a href="${path }/tools/inx/xmldecode"> <i class="fa fa-fw fa-folder"> </i> XML格式化 </a> </li>
			    <li> <a href="${path }/tools/inx/sqldecode"> <i class="fa fa-fw fa-file-o"> </i> SQL格式化 </a> </li>
			    <li> <a href="${path }/tools/inx/jsdecode"> <i class="fa fa-fw fa-file-o"> </i> JS格式化 </a> </li>
			    <li> <a href="${path }/tools/inx/htmldecode"> <i class="fa fa-fw fa-file-o"> </i> HTML格式化 </a> </li>
			    <li> <a href="${path }/tools/inx/clearxmlstyle"> <i class="fa fa-fw fa-file-o"> </i> 清除XML样式 </a> </li>
			    <li> <a href="${path }/tools/inx/md5lock"> <i class="fa fa-fw fa-file-o"> </i> MD5加密 </a> </li>
			    <li> <a href="${path }/tools/inx/deslock"> <i class="fa fa-fw fa-file-o"> </i> DES加密 </a> </li>
			    <li> <a href="${path }/tools/inx/rsalock"> <i class="fa fa-fw fa-file-o"> </i> RSA加密 </a> </li>
			    <li> <a href="${path }/tools/inx/jslock"> <i class="fa fa-fw fa-file-o"> </i> JS加密 </a> </li>
			    <li> <a href="${path }/tools/inx/base64lock"> <i class="fa fa-fw fa-file-o"> </i> BASE64加密 </a> </li>
			    <li> <a href="${path }/tools/inx/cipherlock"> <i class="fa fa-fw fa-file-o"> </i> CIPHER加密 </a> </li>
			    <li> <a href="${path }/tools/inx/encryptlock"> <i class="fa fa-fw fa-file-o"> </i> ENCRYPT加密 </a> </li>
			    <li> <a href="${path }/tools/inx/sha1lock"> <i class="fa fa-fw fa-file-o"> </i> SHA1加密 </a> </li>
			    <li> <a href="${path }/tools/inx/htmlts"> <i class="fa fa-fw fa-file-o"> </i> HTML调试 </a> </li>
			    <li> <a href="${path }/tools/inx/jsts"> <i class="fa fa-fw fa-file-o"> </i> JS调试 </a> </li>
			    <li> <a href="${path }/tools/inx/chartran"> <i class="fa fa-fw fa-file-o"> </i> 编码转换 </a> </li>
			    <li> <a href="${path }/tools/inx/zxbds"> <i class="fa fa-fw fa-file-o"> </i> 在线表达式 </a> </li>
			    <li> <a href="${path }/tools/inx/charReport"> <i class="fa fa-fw fa-file-o"> </i> 数字字符对比表 </a> </li>
			  </ul>
			</nav>
		  </div>
		</div>
	</div>
	<script type="text/javascript">
		window.onload=function(){
			active();
		}
		function active(){
			$(".active").removeClass("active");
			$.each($(".nav li a"),function(a,b){
				if($(b).attr("href").endsWith("${toolName}")){
					$(b).addClass("active");
					return;
				}
			});
		} 
	</script>
</body>
</html>