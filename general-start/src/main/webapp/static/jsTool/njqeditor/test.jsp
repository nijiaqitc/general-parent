<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="css/njqEditor_styleTwo.css"  rel="stylesheet">


<style type="text/css">
    .ool{
        background-color: #eee;
        border: 1px solid #ccc;
    }
    .ool li{
        list-style: decimal-leading-zero;
        list-style-position: outside!important;
        border-left: 1px solid #82a8cf;
        background-color: #F8F8F8;
        color: #5C5C5C;
        padding: 0 3px 0 10px!important;
        margin: 0!important;
        line-height: 150%;
    }

</style>
</head>
<body>
    <div style=""   id="testmmm"  contenteditable="true">111111</div>
    <ol class="ool">
        <li>11111</li>
        <li>11111</li>
        <li>11111</li>
        <li>11111</li>
        <li>11111</li>
    </ol>

    <!-- 包括但不限于第三方是什么作用、第三方怎么用、我们哪里用的、还可以怎么用、用得好不好等等 -->
    <div align="center" >
        <div prefix="design" name="njqEditorDiv" modelStyle="styleTwo" env="1"></div>
    </div>
    
    <div id="aaaa1" style="display: none;"></div>
    <div id="aaaa2" style="display: none;"></div>
    <div id="aaaa3"></div>
    
    
    <script type="text/javascript">
        var jspath="/webTest";
    </script>
    <!--     加载用户自定义配置 -->
    <script type="text/javascript" src="js/njqEditor_config.js"></script>
    
    
    <script type="text/javascript">
        var cssObj={}
        //读取样式
        function loadClass(){
            var cssName=document.styleSheets[0].href.split("/").pop();
            var classNames=["njqEditor_styleTwo.css"];
            var styleSheets=document.styleSheets;
            var loadSheets=[];
            for(var i in classNames){
                for(var j=0;j<styleSheets.length;j++){
                    if(styleSheets[j].href&&classNames[i]==styleSheets[j].href.split("/").pop()){
                        loadSheets.push(styleSheets[j]);
                    }
                }
            }
            var num;
            for(var i in loadSheets){
                for(var j=0,len=loadSheets[i].cssRules.length;j<len;j++){
                    num=loadSheets[i].cssRules[j].cssText.split(";").length;
                    if(!cssObj[num]){
                        cssObj[num]={};
                    }
                    cssObj[num][loadSheets[i].cssRules[j].
                                selectorText.split(" ").pop().split(".").pop()]
                        =loadSheets[i].cssRules[j].style.cssText;
                }
            }
        }
        
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
    
        //样式转换成class
        function styleToClass(html,style){
            var cssArray=style.split("|");
            var stvalue,styv,stlen,styleStr="",flag=true;
            for(var i in cssArray){
                stvalue=cssArray[i].split("=");
                styv=stvalue[0].split("]");
                stlen=stvalue[1].split(";").length;
                for(var j in cssObj[stlen]){
                    if(stvalue[1]==cssObj[stlen][j]){
                        styleStr+="[1]"+styv[1]+"="+j+"|";
                        flag=false;
                        break;
                    }
                }
                flag?styleStr+="[0]"+styv[1]+"="+stvalue[1]+"|":flag=true;
            }
            styleStr=styleStr.substring(0,styleStr.length-1);
            console.info(this.assembleContext(html,styleStr))
        }
        
        
    </script>
</body>
</html>