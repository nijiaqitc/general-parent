<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<jsp:include page="${path}/commonTopLink"></jsp:include>
<link href="${resPath }/jsTool/njqeditor/css/njqEditor_styleOne.css"  rel="stylesheet">
</head>
<body>
<!-- 通用顶部 -->
    <jsp:include page="${path}/commonTop"></jsp:include>
    <!-- 中下部分 -->
    <div id="centerPlace">
        <!-- 通用左边菜单 --> 
        <jsp:include page="${path}/commonLeft"></jsp:include>
        <!-- 正文区域 -->
        <div id="rightContext">
            <div><div>111</div>222</div>
            <div id="njqEditorDiv" env="3"></div>
            <div onclick="javascript:alert(njq.getContent())" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">获取编辑器内容</div>
            <div onclick="njq.setContent('欢迎使用')" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">写入内容</div>
            <div onclick="javascript:alert(njq.getContentTxt())" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">获得纯文本</div>
            <div onclick="javascript:alert(njq.getPlainTxt())" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">获得带格式的纯文本</div>
            <div onclick="javascript:alert(njq.hasContent())" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">判断是否有内容</div>
            <div onclick="njq.setFocus()" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">使编辑器获得焦点</div>
            <div onclick="javascript:alert(njq.getText())" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">获得当前选中的文本</div>
            <div onclick="njq.getSaveContext()" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">获取准备保存的内容</div>
            <div onclick="njq.assembleContext()" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">组合内容</div>
            <div onclick="njq.insertHtml(1111)" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">插入给定的内容</div>
            <div onclick="njq.setEnabled()" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">可以编辑</div>
            <div onclick="njq.setDisabled()" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">不可编辑</div>
            <div onclick="njq.setHide()" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">隐藏编辑器</div>
            <div onclick="njq.setShow()" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">显示编辑器</div>
            <div onclick="njq.setHeight(500)" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">设置编辑器的高度</div>
            <div onclick="njq.setTitleNum(500)" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">设置标题序号</div>
            <div onclick="njq.setTitle('阿拉啦啦啦')" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">设置标题</div>
            <div onclick="javascript:alert(njq.getTitle())" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">获取标题</div>
            <div onclick="javascript:alert(njq.getTitleNum())" style="background-color: #333;color:white;margin-left: 4px;float: left;cursor: pointer;">获取标题序号</div>
        </div>
        <!-- 通用底部 -->   
        <jsp:include page="${path}/commonBottom"></jsp:include>
    </div>
</body>
    <jsp:include page="${path}/commonBottomLink"></jsp:include>
    <script type="text/javascript">
        //ie8不支持trim方法
        String.prototype.trim = function () {
            return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
         }
    </script>
    <!--     加载用户自定义配置 -->
    <script type="text/javascript" src="${resPath }/jsTool/njqeditor/js/njqEditor_config.js"></script>
    <script type="text/javascript">
    function test(){
    	
    	/* var array=njq.getSaveContext();
    	var html=njq.assembleContext(array[0],array[1]);
    	njq.insertHtml(html); */
    	njq.setDisabled();
    	
    	
//     	console.info(njqEditor)
    }
    
    </script>
</body>
</html>