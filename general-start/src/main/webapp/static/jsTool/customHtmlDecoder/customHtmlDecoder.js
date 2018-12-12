/**
 * 自定义html代码格式化
 * 对一些特殊标签不进行处理，如script、style、textarea、pre等
 * 若需要格式化的内容本身有问题，将导致格式化后的代码不是想要的代码，如多了</div>等结尾标签
 * 一种实现思路：（以外标签为闭合状态为准，如获取第一个标签为父标签，内有子标签，当获取到闭合标签后判断是否是子标签的，如果是（子标签的闭合状态改成1），
 * 那么下一个标签就是子标签的兄弟标签，如果不是那么就是子标签的子标签，如果下一个标签又是闭合标签那么判断是否是父标签的，如果是（父标签的闭合标签状态改成1），
 * 如果下一个标签还是闭合标签而父标签的闭合状态已经变成1了，那么进行相应的操作如删除等）
 * 本格式化方案为：
 * 1、先初始化获取字符串，将其中没有闭合的标签、不处理标签替换成自定义标签
 * 2、将新字符串中所有的标签和标签内容剥离
 * 3、判断标签是哪种类型（1、回车加空格加字符串、2、回车加字符串减空格、3、回车加空格加字符串减空格），进行字符串拼接
 * 4、将自定义标签替换成对应的没有闭合的标签、不处理标签
 */
function customHtmlDecoder(){
	//需要格式化的html代码
	var oldStr="";
	//格式后的html代码
	var newStr="";
	//4个空格(1：4个空格 2：8个空格 3：16个空格 4：Tab符号)
	var fourSpace="    ";
	var enter="\n";
	//是否对pre内容格式化
	var ispre=false;
	//转化方式(1结束标签必定回车2，内部无标签结束标签不回车)
	var tranType=1;
	//开头结束的特殊标签
	var startEndLables=[["<%","%>"],["<!--","-->"],["<!DOCTYPE",">"]]
	//一些没有闭合标签的标签
	var unIncludeLables=["input","img","meta","link","customLables"];
	//不需要处理的标签
	var undealLables=[];
	//替换不需要处理的标签
	var replaceUndealLables=[];
	//不需要处理的自定义标签
	var undealIncludeLables=["undealLables"];
	//代码格式化（用类名直接调用此方法那么要用this，如果不用this，无法直接调用）
	this.decode=function(str,config){
		//判断需要格式化的内容中是否包含了标签
		if(/<.+>/g.test(str)){
			//提取开头<之前的内容
			var s=str.match(/[\S\s]*?</)[0].replace("<","");
			//提取结束>之后的内容
			var e=str.substring(str.match(/[\S\s]*>/g)[0].length,str.length) ;
			//初始化字符串
			init(str,config);
			//开始格式化字符串
			newStr=startDecode(oldStr);
			//将自定义标签替换回去
			lastDeal();	
			return s+enter+newStr+enter+e;
		}else{
			return str;
		}
		
	}
	//初始化
	function init(str,config){
		if(config.ispre!=undefined){
			ispre=config.ispre;
		}
		if(config.tranType!=undefined){
			tranType=config.tranType;
		}
		if(config.spaceType!=undefined){
			switch (config.spaceType) {
			case "1":
				fourSpace="    ";
				break;
			case "2":
				fourSpace="        ";
				break;
			case "3":
				fourSpace="                ";
				break;
			case "4":
				fourSpace="	";
				break;
			default:
				fourSpace="    ";
				break;
			}
		}
		oldStr=str;
		//把无用标签替换掉
		oldStr=oldStr.replace("</input>","");
		oldStr=oldStr.replace("</img>","");
		oldStr=oldStr.replace(/(<br>|<\/br>|<br\/>)/g,"<\/br>");
		//对特殊标签进行处理
		dealSpealLables();
		//去除其中的回车、tab等
		oldStr=oldStr.replace(/(	|	|\n|\t|\r)/g,"");
	}
	//特殊标签
	function dealSpealLables(){
		//替换script标签为自定义标签
		Array.prototype.push.apply(undealLables, oldStr.match(/<script.*?>[\S\s]*?(<\/script>)/g));
		//替换style标签为自定义标签
		Array.prototype.push.apply(undealLables, oldStr.match(/<style.*?>[\S\s]*?(<\/style>)/g)); 
		//替换textarea标签为自定义标签
		Array.prototype.push.apply(undealLables, oldStr.match(/<textarea.*?>[\S\s]*?(<\/textarea>)/g)); 
		//替换pre标签为自定义标签
		Array.prototype.push.apply(undealLables, oldStr.match(/<pre.*?>[\S\s]*?(<\/pre>)/g));
		//进行替换
		for(var i=0;i<undealLables.length;i++){
			replaceUndealLables[i]="<undealLables"+i+">";
			oldStr=oldStr.replace(undealLables[i],replaceUndealLables[i]);
		}
		if(ispre){
			for(var i=0;i<undealLables.length;i++){
				if(undealLables[i].startsWith("<pre")){
					undealLables[i]=dealPre(undealLables[i])
				}
			}
		}
	}
	//把自定义标签替换成不处理标签
	function lastDeal(){
		for(var i=0;i<undealLables.length;i++){
			newStr=newStr.replace(replaceUndealLables[i],undealLables[i]);
		}
	}
	
	function dealPre(str){
		str=str.replace(/&gt;/g, ">");
		str=str.replace(/&lt;/g, "<");
		str=str.replace(/&nbsp;/g," ");
		var s1=str.match(/<pre.*?>/)[0];
		var s2="</pre>";
		var ss=str.substring(s1.length, str.length-6)
		if(/<.+>/g.test(ss)){
			ss=ss.replace(/(<\/input>|<\/img>)/g,"");
			ss=ss.replace(/(<br>|<\/br>|<br\/>)/g,"<\/br>");
			ss=ss.replace(/(	|	|\n|\t|\r)/g,"");
			var s=startDecode(ss);
			s=s.replace(/>/g, "&gt;");
			s=s.replace(/</g, "&lt;");
			s=s.replace(/ /g,"&nbsp;");
			s=s.replace(/	/g,"&nbsp;&nbsp;&nbsp;&nbsp;");
			return s1+enter+s+enter+s2;		
		}else{
			return str;
		}
	}
	//开始格式化字符串
	function startDecode(str){
		//存放所有标签
		var lables=[];
		//存放标签内的内容
		var values=[];
		//用于判断标签内部是否存在内容
		var flag=[false,false];
		//转换后的字符串
		var newStr="";
		//需要添加空格的次数
		var times=-1;
		resolveStr();
		return newStr.substring(1,newStr.length);
		
		//字符串解析
		function resolveStr(){
			//获取标签
			lables=str.match(/<.*?>/g);
			var jstr=str.match(/>[\s\S]*?</g);
			if(jstr!=undefined){
				//获取标签内容
				for(var i=0;i<jstr.length;i++){
					values[i]=jstr[i].match(/>.*?</g)[0].replace(">","").replace("<","").trim();
				}
				if(tranType==2){
					montageStrTwo();					
				}else{
					montageStrOne();
				}
			}else{
				newStr=str;
			}
		}
		//标签和内容进行拼接（结尾标签必定回车）
		function montageStrOne(){
			var index=1;
			for(var i=0;i<lables.length;i++){
				var f=false;
				//如果是起始标签
				if(/<[^\/.*?]/g.test(lables[i])){
					//先判断特殊标签
					for(var j=0;j<startEndLables.length;j++){
						if(lables[i].startsWith(startEndLables[j][0])&&lables[i].endsWith(startEndLables[j][1])){
							index=3;
							flag[0]=false;
							f=true;
							break;
						}
					}
					if(!f){
						//获取标签里面的内容
						var l=lables[i].match(/<[^\/].*?( |>)/g)[0].replace(/<|>| /g,"");
						//如果是自定义标签
						if(l.length>10){
							l=l.substr(0,12);
						}
						//如果标签直接以/>结尾了那直接给处理方案
						if(/[^\/].*?\/>/.test(lables[i])){
							index=3;
							flag[0]=false;
						}else{
							//判断是不闭合标签还是不处理标签还是正常标签
							if(unIncludeLables.indexOf(l)!=-1){
								index=3;
								flag[0]=false;
							}else if(undealIncludeLables.indexOf(l)!=-1){
								index=4;
								flag[0]=false;
							}else{
								index=1;
								flag[0]=true;
							}
						}
					}
				}else{
					//判断是否是br标签
					if(/<\/br>/g.test(lables[i])){
						index=6;
					}else{
						//只有flag[0][1]同时都是true说明标签内部没有内容
						if(flag[0]==true&&flag[1]==true){
							index=5;
						}else{
							//结束标签
							index=2;
						}
					}
					flag[0]=false;
				}
				//添加标签
				addStr(lables[i],index);
				//添加标签内容
				addValue(values[i],3)
			}
		}
		//标签和内容进行拼接（标签内无其他标签结尾标签不回车）
		function montageStrTwo(){
			var index=1;
			for(var i=0;i<lables.length;i++){
				var left="";
				var right="";
				var center="";
				var f=false;
				//获取作标签里面的内容
				if((i-1)>=0){
					left=lables[i-1].match(/<.*?( |>)/g)[0].replace(/<|>| /g,"");
				}
				center=lables[i].match(/<.*?( |>)/g)[0].replace(/<|>| /g,"");
				//获取右标签里面的内容
				if((i+1)<lables.length){
					right=lables[i+1].match(/<.*?( |>)/g)[0].replace(/<|>| /g,"");
				}
				//如果是起始标签
				if(/<[^\/.*?]/g.test(lables[i])){
					for(var j=0;j<startEndLables.length;j++){
						if(lables[i].startsWith(startEndLables[j][0])&&lables[i].endsWith(startEndLables[j][1])){
							index=3;
							f=true;
							break;
						}
					}
					if(!f){
						//获取标签里面的内容
						var l=lables[i].match(/<[^\/].*?( |>)/g)[0].replace(/<|>| /g,"");
						//如果是自定义标签
						if(l.length>10){
							l=l.substr(0,12);
						}
						//如果标签直接以/>结尾了那直接给处理方案
						if(/[^\/].*?\/>/.test(lables[i])){
							index=3;
						}else{
							//判断是不闭合标签还是不处理标签还是正常标签
							if(unIncludeLables.indexOf(l)!=-1){
								index=3;
							}else if(undealIncludeLables.indexOf(l)!=-1){
								index=4;
							}else{
								index=1;
							}
						}
					}
				}else{
					//判断是否是br标签
					if(/<\/br>/g.test(lables[i])){
						index=6;
					}else{
						//只有flag[0][1]同时都是true说明标签内部没有内容
						if(("/"+left)==center){
							index=5;
						}else{
							//结束标签
							index=2;
						}
					}
				}
				//添加标签
				addStr(lables[i],index);
				if(("/"+center)==right){
					//添加标签内容
					addValue(values[i],6)
				}else{
					addValue(values[i],3)
				}
			}
		}
		//根据不同方案将字符串添加进去
		function addStr(st,index){
			switch (index) {
			//起始标签如<div>
			case 1:
				times+=1;
				newStr+=enter+getspace()+st;
				break;
			//结束标签如：</div>
			case 2:
				newStr+=enter+getspace()+st;
				if(times>-1){
					times-=1;
				}
				break;
			//不闭合标签如：<input>
			case 3:
				times+=1;
				newStr+=enter+getspace()+st;
				if(times>-1){
					times-=1;
				}
				break;
			//不处理标签如：<style>
			case 4:
				var newTime=times;
				times=-1;
				newStr+=enter+getspace()+st;
				times=newTime;
				break;
			//标签内部没有内容则不回车
			case 5:
				newStr+=st;
				times-=1;
				break;
			//其他类型直接在后面加上对应的标签
			default:
				newStr+=st;
				break;
			}
		}
		//把内容添加到标签里面
		function addValue(st,index){
			if(checkValue(st)){
				addStr(st,index);
				flag[1]=false;
			}else{
				flag[1]=true;
			}
		}
		//判断是否是无须闭合的标签
		function checkValue(v){
			if(v!=undefined&&v!=""){
				return true;
			}else{
				return false;
			}
		}
		//获取需要添加的空格数
		function getspace(){
			var s="";
			for(var i=0;i<times;i++){
				s+=fourSpace;
			}
			return s;
		}
	}
}
