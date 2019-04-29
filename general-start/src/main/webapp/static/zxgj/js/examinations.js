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
	$(".answerDes").show();
	if(total < 30){
		$("#pingyu").html("赶紧再充下电吧，欠缺的有点多哦！");
	}else if(total < 60){
		$("#pingyu").html("离及格差不远了哦，加油！");
	}else if(total < 80){
		$("#pingyu").html("已经及格啦，马上就要良好了哦，加油！");
	}else if(total < 100){
		$("#pingyu").html("可以准备准备投简历啦^_^");
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