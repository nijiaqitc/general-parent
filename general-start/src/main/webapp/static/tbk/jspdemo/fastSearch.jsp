<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>首页</title>
<link href="index.css" type="text/css" rel="stylesheet">
<script src="banner/js/jquery.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="css/index.css" type="text/css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/zzsc.js"></script>
<style type="text/css">

	em {
		font-style: normal;
		color: #c00;
	}
</style>
</head>
<body>
    <!--     开始：顶部联系方式 -->
    <div class="menu-box topMenu"  align="center">
        <div style="width: 1000px;" align="left">
            <ul class="topUl">
                <li><span class="topFont">QQ:12345678</span></li>
                <li style="margin-left: 20px;"><span class="topFont">QQ:12345678</span></li>
            </ul>
        </div>
    </div>
    <!--     结束：顶部联系方式 -->
    <!--     开始：导航条 -->
    <div style="height: 30px;" ></div>
    <div class="menu-box" align="center" style="margin-top: 10px;height: 150px;">
    	<jsp:include page="navigate.jsp"></jsp:include>
    </div>
    <!--     结束：导航条 -->
    <!--     开始：最新文章区域 -->
    <div align="center" style="height: 1350px;clear: both;">
    	<div align="center" style="width: 1190px;">
	    	<div align="center"  style="clear:both; height: 60px;margin-top: 20px;">
	    		<div style="width: 640px;height: 40px;padding-top: 10px;">
	    			<input type="text" style="width: 520px;height: 40px;-webkit-appearance: textfield;padding: 1px;background-color: white;border: 2px inset;border-image-source: initial;border-image-slice: initial;border-image-width: initial;border-image-outset: initial;border-image-repeat: initial;-webkit-rtl-ordering: logical;-webkit-user-select: text;cursor: auto;margin: 0em;color: initial;letter-spacing: normal;word-spacing: normal;text-transform: none;text-indent: 0px;text-shadow: none;display: inline-block;text-align: start;">
	    			<input type="submit" value="搜索一下" style="cursor: pointer;width: 100px;height: 40px;width: 100px;height: 40px;color: white;background-color: #E12422;font-size: 16px;border: 0;" >
	    		</div>
	    	</div>
    	</div>
    	<div align="center" style="width: 1190px;">
	    	<div align="center" style="height: 300px;margin-top: 20px;width: 250px;float: left;display: inline;">
	    		<div class="menu-box" style="height: 300px;width: 250px;display: none;" >
	    			可以放搜索排行 或者 其他人还搜  可以放相关文档（暂时不知道怎么算相关文档）
	    		</div>
	    		<div class="menu-box" style="height: 300px;width: 250px;margin-top: 20px;display: none;" >
	    		</div>
	    		<div class="menu-box" style="height: 300px;width: 250px;margin-top: 20px;display: none;" >
	    		</div>
	    		<div class="menu-box" style="height: 300px;width: 250px;margin-top: 20px;display: none;" ></div>
	    	</div>
	    	<div align="left" style="height: 1464px;margin-top: 20px;margin-left:20px; width: 650px;float: left;display: inline;">
	    		<div class="menu-box" style="width: 650px;height: 1464px;" >
	    			<div align="left" style="width: 610px;height: 120px;margin-left: 20px;padding-top: 12px;">
	    				<div>文章标题文章标题文章标题文章标题</div>
	    				<div style="height: 80px;width: 80px;float: left;display: inline;" >
	    					<img style="clear: both;float: left;" src="images/pic04.jpg" alt="" />
	    				</div>
	    				<div style="height: 20px;width: 500px;float: left;display: inline;margin-top: -8px;">
	    					<span style="margin-left: 10px;font-size: 12px;color: #909090">标签:</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签1</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签2</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签3</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签4</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签5</span> 
	    				</div>
	    				<div style="height: 50px;width: 500px;float: left;display: inline;margin-top: 8px;">
	    					<span style="font-size: 6px;line-height: 18px;">文档内容文档内容文档内容文档<em>内容文档内</em>容文档内容文档内容文档内容文档内容文档内容文档内容文档
	    					内容文档内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容<em>内容文档</em>内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容内容
	    					</span>
	    				</div>
	    				<div align="right" style="height: 20px;width: 530px;float: left;display: inline;font-size: 10px;">
	    					2016-01-01 阅读量(1000)
	    				</div>
	    			</div>
	    			<div align="left" style="border-top: 1px solid #ECECEC;width: 610px;height: 120px;margin-top: 6px;margin-left: 20px;padding-top: 12px;">
	    				<div>文章标题文章标题文章标题文章标题</div>
	    				<div style="height: 80px;width: 80px;float: left;display: inline;" >
	    					<img style="clear: both;float: left;" src="images/pic04.jpg" alt="" />
	    				</div>
	    				<div style="height: 20px;width: 500px;float: left;display: inline;margin-top: -8px;">
	    					<span style="margin-left: 10px;font-size: 12px;color: #909090">标签:</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签1</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签2</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签3</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签4</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签5</span> 
	    				</div>
	    				<div style="height: 50px;width: 500px;float: left;display: inline;margin-top: 8px;">
	    					<span style="font-size: 6px;line-height: 18px;">文档内容文档内容文档内容文档<em>内容文档内</em>容文档内容文档内容文档内容文档内容文档内容文档内容文档
	    					内容文档内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容<em>内容文档</em>内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容内容
	    					</span>
	    				</div>
	    				<div align="right" style="height: 20px;width: 530px;float: left;display: inline;font-size: 10px;">
	    					2016-01-01 阅读量(1000)
	    				</div>
	    			</div>
	    			<div align="left" style="border-top: 1px solid #ECECEC;width: 610px;height: 120px;margin-top: 6px;margin-left: 20px;padding-top: 12px;">
	    				<div>文章标题文章标题文章标题文章标题</div>
	    				<div style="height: 80px;width: 80px;float: left;display: inline;" >
	    					<img style="clear: both;float: left;" src="images/pic04.jpg" alt="" />
	    				</div>
	    				<div style="height: 20px;width: 500px;float: left;display: inline;margin-top: -8px;">
	    					<span style="margin-left: 10px;font-size: 12px;color: #909090">标签:</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签1</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签2</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签3</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签4</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签5</span> 
	    				</div>
	    				<div style="height: 50px;width: 500px;float: left;display: inline;margin-top: 8px;">
	    					<span style="font-size: 6px;line-height: 18px;">文档内容文档内容文档内容文档<em>内容文档内</em>容文档内容文档内容文档内容文档内容文档内容文档内容文档
	    					内容文档内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容<em>内容文档</em>内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容内容
	    					</span>
	    				</div>
	    				<div align="right" style="height: 20px;width: 530px;float: left;display: inline;font-size: 10px;">
	    					2016-01-01 阅读量(1000)
	    				</div>
	    			</div>
	    			<div align="left" style="border-top: 1px solid #ECECEC;width: 610px;height: 120px;margin-top: 6px;margin-left: 20px;padding-top: 12px;">
	    				<div>文章标题文章标题文章标题文章标题</div>
	    				<div style="height: 80px;width: 80px;float: left;display: inline;" >
	    					<img style="clear: both;float: left;" src="images/pic04.jpg" alt="" />
	    				</div>
	    				<div style="height: 20px;width: 500px;float: left;display: inline;margin-top: -8px;">
	    					<span style="margin-left: 10px;font-size: 12px;color: #909090">标签:</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签1</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签2</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签3</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签4</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签5</span> 
	    				</div>
	    				<div style="height: 50px;width: 500px;float: left;display: inline;margin-top: 8px;">
	    					<span style="font-size: 6px;line-height: 18px;">文档内容文档内容文档内容文档<em>内容文档内</em>容文档内容文档内容文档内容文档内容文档内容文档内容文档
	    					内容文档内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容<em>内容文档</em>内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容内容
	    					</span>
	    				</div>
	    				<div align="right" style="height: 20px;width: 530px;float: left;display: inline;font-size: 10px;">
	    					2016-01-01 阅读量(1000)
	    				</div>
	    			</div>
	    			<div align="left" style="border-top: 1px solid #ECECEC;width: 610px;height: 120px;margin-top: 6px;margin-left: 20px;padding-top: 12px;">
	    				<div>文章标题文章标题文章标题文章标题</div>
	    				<div style="height: 80px;width: 80px;float: left;display: inline;" >
	    					<img style="clear: both;float: left;" src="images/pic04.jpg" alt="" />
	    				</div>
	    				<div style="height: 20px;width: 500px;float: left;display: inline;margin-top: -8px;">
	    					<span style="margin-left: 10px;font-size: 12px;color: #909090">标签:</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签1</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签2</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签3</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签4</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签5</span> 
	    				</div>
	    				<div style="height: 50px;width: 500px;float: left;display: inline;margin-top: 8px;">
	    					<span style="font-size: 6px;line-height: 18px;">文档内容文档内容文档内容文档<em>内容文档内</em>容文档内容文档内容文档内容文档内容文档内容文档内容文档
	    					内容文档内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容<em>内容文档</em>内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容内容
	    					</span>
	    				</div>
	    				<div align="right" style="height: 20px;width: 530px;float: left;display: inline;font-size: 10px;">
	    					2016-01-01 阅读量(1000)
	    				</div>
	    			</div>
	    			<div align="left" style="border-top: 1px solid #ECECEC;width: 610px;height: 120px;margin-top: 6px;margin-left: 20px;padding-top: 12px;">
	    				<div>文章标题文章标题文章标题文章标题</div>
	    				<div style="height: 80px;width: 80px;float: left;display: inline;" >
	    					<img style="clear: both;float: left;" src="images/pic04.jpg" alt="" />
	    				</div>
	    				<div style="height: 20px;width: 500px;float: left;display: inline;margin-top: -8px;">
	    					<span style="margin-left: 10px;font-size: 12px;color: #909090">标签:</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签1</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签2</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签3</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签4</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签5</span> 
	    				</div>
	    				<div style="height: 50px;width: 500px;float: left;display: inline;margin-top: 8px;">
	    					<span style="font-size: 6px;line-height: 18px;">文档内容文档内容文档内容文档<em>内容文档内</em>容文档内容文档内容文档内容文档内容文档内容文档内容文档
	    					内容文档内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容<em>内容文档</em>内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容内容
	    					</span>
	    				</div>
	    				<div align="right" style="height: 20px;width: 530px;float: left;display: inline;font-size: 10px;">
	    					2016-01-01 阅读量(1000)
	    				</div>
	    			</div>
	    			<div align="left" style="border-top: 1px solid #ECECEC;width: 610px;height: 120px;margin-top: 6px;margin-left: 20px;padding-top: 12px;">
	    				<div>文章标题文章标题文章标题文章标题</div>
	    				<div style="height: 80px;width: 80px;float: left;display: inline;" >
	    					<img style="clear: both;float: left;" src="images/pic04.jpg" alt="" />
	    				</div>
	    				<div style="height: 20px;width: 500px;float: left;display: inline;margin-top: -8px;">
	    					<span style="margin-left: 10px;font-size: 12px;color: #909090">标签:</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签1</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签2</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签3</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签4</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签5</span> 
	    				</div>
	    				<div style="height: 50px;width: 500px;float: left;display: inline;margin-top: 8px;">
	    					<span style="font-size: 6px;line-height: 18px;">文档内容文档内容文档内容文档<em>内容文档内</em>容文档内容文档内容文档内容文档内容文档内容文档内容文档
	    					内容文档内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容<em>内容文档</em>内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容内容
	    					</span>
	    				</div>
	    				<div align="right" style="height: 20px;width: 530px;float: left;display: inline;font-size: 10px;">
	    					2016-01-01 阅读量(1000)
	    				</div>
	    			</div>
	    			<div align="left" style="border-top: 1px solid #ECECEC;width: 610px;height: 120px;margin-top: 6px;margin-left: 20px;padding-top: 12px;">
	    				<div>文章标题文章标题文章标题文章标题</div>
	    				<div style="height: 80px;width: 80px;float: left;display: inline;" >
	    					<img style="clear: both;float: left;" src="images/pic04.jpg" alt="" />
	    				</div>
	    				<div style="height: 20px;width: 500px;float: left;display: inline;margin-top: -8px;">
	    					<span style="margin-left: 10px;font-size: 12px;color: #909090">标签:</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签1</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签2</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签3</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签4</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签5</span> 
	    				</div>
	    				<div style="height: 50px;width: 500px;float: left;display: inline;margin-top: 8px;">
	    					<span style="font-size: 6px;line-height: 18px;">文档内容文档内容文档内容文档<em>内容文档内</em>容文档内容文档内容文档内容文档内容文档内容文档内容文档
	    					内容文档内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容<em>内容文档</em>内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容内容
	    					</span>
	    				</div>
	    				<div align="right" style="height: 20px;width: 530px;float: left;display: inline;font-size: 10px;">
	    					2016-01-01 阅读量(1000)
	    				</div>
	    			</div>
	    			<div align="left" style="border-top: 1px solid #ECECEC;width: 610px;height: 120px;margin-top: 6px;margin-left: 20px;padding-top: 12px;">
	    				<div>文章标题文章标题文章标题文章标题</div>
	    				<div style="height: 80px;width: 80px;float: left;display: inline;" >
	    					<img style="clear: both;float: left;" src="images/pic04.jpg" alt="" />
	    				</div>
	    				<div style="height: 20px;width: 500px;float: left;display: inline;margin-top: -8px;">
	    					<span style="margin-left: 10px;font-size: 12px;color: #909090">标签:</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签1</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签2</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签3</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签4</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签5</span> 
	    				</div>
	    				<div style="height: 50px;width: 500px;float: left;display: inline;margin-top: 8px;">
	    					<span style="font-size: 6px;line-height: 18px;">文档内容文档内容文档内容文档<em>内容文档内</em>容文档内容文档内容文档内容文档内容文档内容文档内容文档
	    					内容文档内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容<em>内容文档</em>内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容内容
	    					</span>
	    				</div>
	    				<div align="right" style="height: 20px;width: 530px;float: left;display: inline;font-size: 10px;">
	    					2016-01-01 阅读量(1000)
	    				</div>
	    			</div>
	    			<div align="left" style="border-top: 1px solid #ECECEC;width: 610px;height: 120px;margin-top: 6px;margin-left: 20px;padding-top: 12px;">
	    				<div>文章标题文章标题文章标题文章标题</div>
	    				<div style="height: 80px;width: 80px;float: left;display: inline;" >
	    					<img style="clear: both;float: left;" src="images/pic04.jpg" alt="" />
	    				</div>
	    				<div style="height: 20px;width: 500px;float: left;display: inline;margin-top: -8px;">
	    					<span style="margin-left: 10px;font-size: 12px;color: #909090">标签:</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签1</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签2</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签3</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签4</span>
	    					<span style="margin-left: 10px;font-size: 12px;color: #596485">标签5</span> 
	    				</div>
	    				<div style="height: 50px;width: 500px;float: left;display: inline;margin-top: 8px;">
	    					<span style="font-size: 6px;line-height: 18px;">文档内容文档内容文档内容文档<em>内容文档内</em>容文档内容文档内容文档内容文档内容文档内容文档内容文档
	    					内容文档内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容<em>内容文档</em>内容文档内容文档内容文档内容内容文档内容文档内容文档内容文档内容内容
	    					</span>
	    				</div>
	    				<div align="right" style="height: 20px;width: 530px;float: left;display: inline;font-size: 10px;">
	    					2016-01-01 阅读量(1000)
	    				</div>
	    			</div>
	    			<div align="center" style="width: 650px;height: 40px;margin-top: 6px;">
	    				上一页｜分页栏｜下一页
	    			</div>
	    		</div>
	    	</div>
	    	<div align="center" style="height: 300px;margin-top: 20px;margin-left:20px; width: 250px;float: left;display: inline;">
	    		<div class="menu-box" style="height: 300px;width: 250px;display: none;" >
	    		</div>
	    		<div class="menu-box" style="height: 300px;width: 250px;margin-top: 20px;display: none;" >
	    		</div>
	    		<div class="menu-box" style="height: 300px;width: 250px;margin-top: 20px;display: none;" >
	    		</div>
	    		<div class="menu-box" style="height: 300px;width: 250px;margin-top: 20px;display: none;" ></div>
	    	</div>
    	</div>
    </div>
    <!--     开始：页面底部 -->
    <div style="clear:both; height: 50px;"></div>
    <div align="center" class="menu-box" style="clear:both; height: 100px;">
    	<div style="width: 100%;height: 20px;background-color: #555"></div>
    	<div style="margin-top: 10px;"><span style="font-size: 14px;">浙ICP备16006900号</span></div>
    	<div><span style="font-size: 14px;">若有疑问请及时联系站长，QQ:2439794916 微信：qisanshu</span></div>
    </div>
    <!--     结束：页面底部  -->
    <script src="index.js" type="text/javascript"></script>
    <script type="text/javascript">
    	window.onresize=function(){
	    	/* console.info($(window).width()) */
    	}
    </script>
</body>
</html>