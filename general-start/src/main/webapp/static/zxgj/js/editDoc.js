$(document).ready(function(){
	$(".nagivationBottomSelect").removeClass("nagivationBottomSelect");
	$($(".nagivationBottom")[2]).addClass("nagivationBottomSelect");
})
function hideOrShow(target){
	var childUl=target.nextElementSibling;
	if(childUl.style.display=="none"){
		childUl.style.display="";
		target.getElementsByTagName("i")[0].setAttribute("class","icon-caret-down");
	}else{
		childUl.style.display="none";
		target.getElementsByTagName("i")[0].setAttribute("class","icon-caret-right");
	}
}
function readNode(target,docId){
	var actList=document.getElementsByClassName("active");
	for(var i=actList.length-1;i>=0;i--){
		actList[i].classList.remove("active");
	}
	target.classList.add("active");
	target.parentNode.parentNode.previousElementSibling.classList.add("active");
	$.ajax({
        url:"yxl/doc",
        data:{
        	docId:docId
        },
        type:"get",
        success:function(data){
        	$("#docContext").html(assembleContext(data.text,data.css));
       		$("pre").addClass("prettyprint");//如果其他地方也要用到pre，我们可以再加一个父标签的选择器来区分
       		  prettyPrint();//代替body上的onload事件加载该方法
        }
    })
}
//默认加载第一篇数据
$(".leftUl ul li a")[0].click();


//组合样式
function assembleContext(html,css){
    if(!css){
        return html;
    }
    var cs=css.split("|");
    var tempDiv=document.createElement("div");
    tempDiv.innerHTML=html;
    var cc,pl,cnode=tempDiv,vt;
    for(var i=0;i<cs.length;i++){
        cc=cs[i].split("=");
        vt=cc[0].split("]");
        pl=vt[1].split(",");
        for(var j=0;j<pl.length;j++){
            cnode=cnode.childNodes[pl[j]];                      
        }
        if(vt[0]=="[1"){
            cnode.setAttribute("class",cc[1]);
        }else{
            cnode.setAttribute("style",cc[1]);                      
        }
        cnode.removeAttribute("labelIndex");
        cnode=tempDiv;
    }
    return tempDiv.innerHTML;
}