<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="技术区域，暂未开发">
<meta name="description" content="目前暂未开发，只显示1张图片冲冲场">
<title>技术成长</title>
<jsp:include page="../../../tbk/commonjsp/commonTop.jsp"></jsp:include>
<script src="${resPath }/tbk/js/technologyUp.js" type="text/javascript"></script>
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
	    <div style="margin-top: 20px;">
	    	<canvas id="c"></canvas>
		<script>
			var c=document.getElementById("c");
			var	ctx=c.getContext("2d");
				c.width=window.innerWidth;
				c.height=window.innerHeight;
			var string1 = "abcdefghijklmnopqrstuvwxyz";
				string1.split("");
			var fontsize=20;
				columns=c.width/fontsize;
			var drop = [];
			for(var x=0;x<columns;x++)
			{
				drop[x]=0;
			}
		function drap(){
			ctx.fillStyle="rgba(0,0,0,0.07)";
			ctx.fillRect(0,0,c.width,c.height);
			ctx.fillStyle="#0F0";
			ctx.font=fontsize+"px arial";
			for(var i=0;i<drop.length;i++){
				var text1=string1[Math.floor(Math.random()*string1.length)];
				ctx.fillText(text1,i*fontsize,drop[i]*fontsize);
				drop[i]++;
				if(drop[i]*fontsize>c.height&&Math.random()>0.9){//90%的几率掉落
					drop[i]=0;
				}
			}
		}
		setInterval(drap,20);
		</script>
	    </div>
    </div>
    <!--     开始：页面底部 -->
    <div class="aFootBefore"></div>
    <div align="center"  class="menu-box aFootTotal">
    	<jsp:include page="../../../tbk/commonjsp/footer.jsp"></jsp:include>
    </div>
    <!--     结束：页面底部  -->
    <jsp:include page="../../../tbk/commonjsp/commonBottom.jsp"></jsp:include>
</body>
</html>