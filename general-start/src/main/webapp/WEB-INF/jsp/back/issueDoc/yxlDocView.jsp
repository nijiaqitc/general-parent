<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${doc.title }</title>
<jsp:include page="${basePath}/commonTopLink"></jsp:include>
<style type="text/css">
/*全屏开始*/
.adocDiv{
    z-index: 998;width: 100%;margin: 10px;position:relative;
/*     margin-top: -150px; */
/*     display: none; */
}
.adocDiv1{
    background-color: #fff;border-radius: 6px;width: 997px;box-shadow: 0 3px 9px rgba(0,0,0,0.5);min-height: 1000px;
}
.transparent_class {  
    /* Required for IE 5, 6, 7 */  
    /* ...or something to trigger hasLayout, like zoom: 1; */  
    width: 100%;
    height: 100%; 
    position: fixed;
    line-height:300px;  
    text-align:center;  
    background:#cccccc;  
    color:#fff;  
    top: 0;right: 0;bottom: 0;left: 0; 
    display: none;
}

.aopenWord{
    width: 20px;border-radius: 2px;cursor: pointer;
}
.aopenWord1{
    background: url('../../tbk/images/icons.png') no-repeat;width: 20px;display: block;height: 20px;background-position: -100px -20px;
}
.aopenWord2{
    background-color: #ffe69f;width: 20px;border: 1px #dcac6c solid;border-radius: 2px;cursor: pointer;
}
.aopenWord3{
    background: url('../../tbk/images/icons.png') no-repeat;width: 20px;display: block;height: 20px;background-position: -100px -20px;
}
.aopenWord4{
    width:760px;padding-top: 60px;
}
.aopenWord5{
/*     font-family:'SimHei';font-size: 30px; */
    font-size: 30px;
    height: 60px;
    line-height: 60px;
    font-weight: 600;
}
.aopenWord6{
    margin-top: 30px;text-indent: 30px;
}
.aopenWord7{
    margin-top: 30px;font-size: 14px;
}
.aopenWord8{
/*     font-family:'SimSun';font-size: 16px;line-height: 26px; */
    margin-top: 50px;padding-bottom: 50px;
}
.aopenWord9{
    font-weight: 600;font-size: 12px;margin: 10px 0 4px 0;
}
.aopenWord10{
    font-weight: 600;font-size: 12px;margin-bottom: 20px;
}
.aopenWord11{
    color: red;font-size: 14px;
}
.aopenWord12{
    
}
.aopenWord13{
    width: 50px;
}
/*全屏结束*/
</style>
</head>
<body>
    <div id="clearCss" style="display: none;">${doc.css }</div>
    <div id="clearText" style="display: none;">${doc.text }</div>
    <!--     开始：全屏阅览 -->
    <div id="wordForShow" align="center" class="adocDiv" >
        <div class="adocDiv1" align="center" >
            <div align="right">
<!--                 <div onclick="closeWord()" class="aopenWord2"> -->
<!--                     <a title="关闭全屏" class="aopenWord3"></a> -->
<!--                 </div> -->
            </div>
            <div class="aopenWord4" >
                <div class="aopenWord5">${doc.title }</div>
                <div class="aopenWord6" align="left" >
                </div>
                <div class="aopenWord7">
                    <div></div>
                    <div></div>
                </div>
                <div class="aopenWord8" align="left"></div>
            </div>
        </div>
    </div>
    <div id="wordModel" class="transparent_class" ></div>
    
    
    <!--     结束：全屏阅览  -->
    <!-- 通用底部 -->
    <jsp:include page="${basePath}/commonBottom"></jsp:include>
    <script src="${resPath }/jquery/jquery.min.js"></script>
    <script type="text/javascript">
    var text=assembleContext($("#clearText").html(),$("#clearCss").html());
    $(".aopenWord8")[0].innerHTML=text;
    $("#clearCss").remove();
    $("#clearText").remove();
    function assembleContext(html,css){
        if(css==""){
            return html;
        }
        var tempDiv=document.createElement("div");
        tempDiv.innerHTML=html;
        var cs=css.split("|");
        var cc,pl,cnode=tempDiv;
        for(var i=0;i<cs.length;i++){
            cc=cs[i].split("=");
            pl=cc[0].split(",");
            for(var j=0;j<pl.length;j++){
                cnode=cnode.children[pl[j]];                        
            }
            cnode.setAttribute("style",cc[1]);
            cnode=tempDiv;
        }
        return tempDiv.innerHTML;
    }
    </script>
</body>
</html>