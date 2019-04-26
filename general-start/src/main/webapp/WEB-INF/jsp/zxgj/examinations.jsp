<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>主页</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/common/css/font-awesome.min.css"  />
<link rel="stylesheet" href="${resPath }/zxgj/css/novelList.css">
<link rel="stylesheet" href="${resPath }/zxgj/js/prettify.css"/>
<link rel="stylesheet" href="${resPath }/zxgj/css/examinations.css"/>
<style type="text/css">
	
	
</style>
</head>
<body>
	<!--     开始：顶部菜单栏-->
	<div style="height: 40px;" class="menu-box topMenu" align="center">
		<div style="width: 860px;overflow: auto;line-height: 40px;">
			<div class="topTip">本试卷总分100分，80分通过</div>
			<div class="topTip">考题数量：61 题</div>
			<div class="topTip">考试时长：60 分钟</div>
			<div class="topTip"><span class="subEx" onclick="submitEx()">交卷</span></div>
			<div class="topTip"><a href="/study/examinations?change=1"><span class="subEx" onclick="changeSubject()">换题</span></a></div>
			<div style="float: right;display:inline-block;vertical-align:top;color:#fead11;font-size:21px;">
				<i class="icon-time"></i>
				<span id="leftTime">00:00:00</span>
			</div>
		</div>
	</div>
	<!--     结束：顶部菜单栏 -->
	<!-- 中间正文公用部分 -->
	<div align="center">
		<div id="wordForShow" align="center" class="adocDiv"  style="margin-top: 50px;" >
	     	<div class="adocDiv1"  align="center">
				<div class="fractionValue" >
					<div id="fenarea" class="fenarea">
						100
					</div>
				</div>
				<div id="pingyu" style="color: #ef4a4a;position: absolute;right: 16%;font-size: 16px;overflow: auto;margin-top: 120px;"></div>
	     		<div class="aopenWord4" align="left">
	     			<div class="aopenT">
	     				一、单选题（<span>共30题，每题1分</span>）
	     			</div>
	     			<c:forEach items="${examap.selectSub }" var="sub" varStatus="order">
		     			<div class="bonT">
				     		<div class="aopenWord5" id="title_${sub.id }">
				     			${order.index+1 }、${sub.title }
				     		</div>
				     		<div class="aopenWord6 selectSub" align="left"  answer="${sub.answerList[0].answer }" attId="${sub.id }" >
				     			<c:if test="${sub.general != null}">
					     			<div>
					     				${sub.general }
					     			</div>
				     			</c:if>
				     			<div class="gouarea"></div>
			     				<div class="charea"></div>
				     			<c:forEach items="${sub.optionsub }" var="option">
					     			<div class="subjectArea">
						     			<input type="radio"  name="st${sub.id }" value="${option[0] }" >
						     			<span>${option[0] }、${option[1] }</span>
					     			</div>
				     			</c:forEach>
				     		</div>
		     			</div>	     			
	     			</c:forEach>
	     			<div class="aopenT">
	     				二、简答题（<span>共30题，每题2分</span>）<span style="font-size: 12px;">（提示：请直接根据题目进行回答，然后根据回答的情况选择合适的打分）</span>
	     			</div>
	     			<c:forEach items="${examap.questions }" var="qus" varStatus="order">
		     			<div class="bonT">
				     		<div class="aopenWord5" id="title_${qus.id }">
				     			${order.index+31 }、${qus.title }
				     		</div>
				     		<div class="aopenWord6 questions" align="left"  attId="${qus.id }">
				     			<div class="subjectArea">
					     			<input type="radio"  name="ques${qus.id }" value="2">
					     			<span>A、通晓原理</span>
				     			</div>
				     			<div class="subjectArea">
					     			<input type="radio"  name="ques${qus.id }" value="1">
					     			<span>B、知晓大概</span>
				     			</div>
				     			<div class="subjectArea">
					     			<input type="radio"  name="ques${qus.id }" value="0">
					     			<span>C、含含糊糊</span>
				     			</div>
				     		</div>
		     			</div>
	     			</c:forEach>
		     		<div class="aopenT">
	     				三、笔试题（<span>共1题，每题10分</span>）
	     			</div>
	     			<div class="bonT">
			     		<div class="aopenWord5" id="title_${examap.penques.id }">
			     			61、${examap.penques.title }
			     		</div>
			     		<div class="aopenWord6" align="left" >
			     			<div class="subjectArea">
				     			<input type="radio" value="10"  name="pen" onclick="showOrHideWriteArea(this)">
				     			<span>A、会</span>
			     			</div>
			     			<div class="subjectArea">
				     			<input type="radio" value="0"  name="pen"  onclick="showOrHideWriteArea(this)">
				     			<span>B、不会</span>
			     			</div>
			     			<div align="left" id="penarea" style="text-indent: 0px;display: none;">
			     				<label>会，那就写一个</label>
			     				<textarea id="writePenArea" attrId="${examap.penques.id }" style="width: 100%;height: 300px;"></textarea>
			     			</div>
			     		</div>
	     			</div>
		     	</div>
		     	<div style="height: 50px;">
		     		<br>
		     	</div>
	     	</div>
	    </div>
	  	<div id="wordModel" class="transparent_class" ></div>
	  	<div onclick="showTotalArea()" class="rightBtn rightBtnsz">大纲</div>
	  	<div id="csdialog" style="" class="sidebar" align="center">
	  		<div style="height: 30px;margin-bottom: 6px;">
	  			<div style="float: right;height: 30px;border-bottom: 1px solid #e7e8e8;font-size: 12px;width: 220px;">
	  				答题情况：<span id="finishNum">0</span>/61
	  				<span id="finishResultArea" style="margin-left: 20px;"></span>
	  			</div>
	  		</div>
			<div style="width: 430px;overflow: auto;" align="left">
				<div class="outArea">
					<div class="caption">
						<i></i> 单选题<span class="mark">(每题 1分，共 30题)</span>
					</div>
					<div>
						<ul>
							<c:forEach items="${examap.selectSub }" var="sub"  varStatus="order">
								<li id="subt_${sub.id }">
									${order.index + 1 }
									<div class="charea"></div>
								</li>
			     			</c:forEach>
						</ul>
					</div>
				</div>
				<div class="outArea">
					<div class="caption">
						<i></i> 简答题<span class="mark">(每题 2分，共 30题)</span>
					</div>
					<div>
						<ul>
							<c:forEach items="${examap.questions }" var="ques" varStatus="order">
								<li id="subt_${ques.id }">${order.index + 31 }</li>
			     			</c:forEach>
						</ul>
					</div>
				</div>
				<div class="outArea" style="border-bottom: 0;">
					<div class="caption">
						<i></i> 笔试题<span class="mark">(每题 10分，共 1题)</span>
					</div>
					<div>
						<ul>
							<li id="subt_${examap.penques.id }">61</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--     开始：顶部菜单栏-->
	<jsp:include page="../zxgj/bottom.jsp"></jsp:include>
	<!--     结束：顶部菜单栏 -->
	<script src="${resPath }/jquery/jquery.min.js" type="text/javascript"></script>
	<script src="${resPath }/jquery/countDown.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/prettify.js" type="text/javascript"></script>
	<script type="text/javascript">
		function decodePre() {
		    for (var i = 0; i < $("pre").length; i++) {
		        var code = document.createElement("code");
		        code.innerHTML = $($("pre")[i]).html();
		        $($("pre")[i]).html(code);
		        var ul = document.createElement("ul");
		        ul.classList.add("pre-numbering");
		        var height = $($("pre")[i]).height();
		        var num = parseInt(Number(height / 22));
		        for (var j = 0; j < num; j++) {
		            var li = document.createElement("li");
		            li.innerHTML = j + 1;
		            ul.append(li);
		        }
		        $($("pre")[i]).append(ul);
		    }
		    $("pre").addClass("prettyprint");
		    prettyPrint();
		}
		
		function submitEx(){
			var selsub = $(".selectSub");
			var total = 0;
			var rightNum=0;
			for(var i = 0 ;i < selsub.length;i++){
				var checkValue = $("input[name='st"+$(selsub[i]).attr("attId")+"']:checked").val();
				var answerValue = $(selsub[i]).attr("answer");
                if(checkValue == answerValue){
                	$(selsub[i]).children(".gouarea").show();
					++total;	
					++rightNum;                
                }else{
                	$(selsub[i]).children(".charea").show();
                	$("#subt_"+$(selsub[i]).attr("attId")).addClass("subfinishResultCha");
                }
			}
			var quesub = $(".questions");
			for(var i = 0 ;i < quesub.length;i++){
				var checkValue = $("input[name='ques"+$(quesub[i]).attr("attId")+"']:checked").val();
				if(checkValue){
					total += Number(checkValue); 
					if(Number(checkValue)==2){
						++rightNum;
					}else if(Number(checkValue)==1){
						$("#subt_"+$(quesub[i]).attr("attId")).addClass("subfinishResultBanCha");
					}else if(Number(checkValue)==0){
						$("#subt_"+$(quesub[i]).attr("attId")).addClass("subfinishResultAllCha");
					}
				}else{
					$("#subt_"+$(quesub[i]).attr("attId")).addClass("subfinishResultAllCha");
				}
			}
			var penValue = $("input[name='pen']:checked").val();
			var id = $("#writePenArea").attr("attrId");
			if(penValue){
				if(Number(penValue)==10){
					total += 10 ;
					++rightNum;
				}else{
					if($("#writePenArea").val().trim() == ""){
						$("#subt_"+id).addClass("subfinishResultAllCha");
					}
				}
			}else{
				$("#subt_"+id).addClass("subfinishResultAllCha");
			}
			$("input[type='radio']").attr("disabled","disabled");
			$("#fenarea").html(total);
			$("#fenarea").show();
			scrollTo(0,0);
			
			if(total < 30){
				$("#pingyu").html("赶紧再充下电吧，欠缺的有点多哦！");
			}else if(total < 60){
				$("#pingyu").html("再努力下哦");
			}else if(total < 80){
				$("#pingyu").html("再接再厉哈");
			}else if(total < 100){
				$("#pingyu").html("不错不错");
			}
			
			$("#finishResultArea").html("答错题目:"+(61-rightNum));
			
		}
		
		function showOrHideWriteArea(target){
			if($(target).val() == 10){
				$("#penarea").show();
			}else{
				$("#penarea").hide();
			}
		}
		
		function showTotalArea(){
			if($("#csdialog").css("right")=="-490px"){
		        $("#csdialog").animate({right: '0px'},"fast");
		    }else{
		        $("#csdialog").animate({right: '-490px'},"fast");   
		    }
		}
		
		
		$(function (){
			$("#leftTime").countDown({
			    times: 60,  
			    ms: false,   
			    Hour: true   
			},function(){});
			decodePre();
			$(".subjectArea span").click(function(){
				this.previousElementSibling.click();
			});
			$("#csdialog li").click(function(){
				var id = this.id.substr(this.id.indexOf("_")+1);
				$(window).scrollTop($("#title_"+id).offset().top-100);
			});
			var finishNum=0;
			$("input[type='radio']").click(function(){
				var id = $(this.parentNode.parentNode).attr("attid");
				$("#subt_"+id).addClass("subfinish");
				finishNum +=1;
				$("#finishNum").html(finishNum);
			});
			
		});
		
		
		
	</script>
</body>
</html>