/**
 * 对文章进行样式清理
 * 第一步：先去除所有样式
 * 第二步：去除所有空标签
 * 第三步：
 */
function CustomDecoder(){
	this.str="";
	//先提取出所有开头标签
	this.firstClear1=/<[^\/].*?>/g;
	//再判断提出来的标签是否包含了样式
	//this.firstClear2=/<[^\/]\w+ .+?>/g; 直接修改下面的正则不修改此处的正则
	//因为第一步清除了样式，所以不会再存在标签里面存在空格的情况，所以直接用下面的匹配即可
	//以<开始匹配后面的连续字符直到第一个>结尾接着以</开始 和第一个括号内相同的字符>结尾
	this.secondClear=/<(\w*)>(<\/\1*>)/g;
	//判断标签所属类型
	this.typeZz=[/<img?/,/<form?/];
	//匹配标签中的前一部分如：(<p )
	this.zz1=/.*? /;
	//去除上面匹配出来的字符串中的"<"、" "
	this.zz2=/[^< ]+/;
	//匹配标签中的style或class
	this.zz3=/(style|class)=".*?"/g;
	this.customStr="";
	this.validate=function(){
		if(this.str==undefined){
			return true;
		}else{
			return false;
		}
	}
	//开始处理
	this.decode=function(){
		if(this.validate()){
			return "";
		}
		this.customHandlerBrefore();
		this.clearLabelProperty();
		this.clearEmptyLabel();
		this.customHandlerAfter();
		return this.str;
	};
	//第一步处理，清理样式
	this.clearLabelProperty=function(){
		//将所有符合正则表达式的字符串提取出来
		var sz=this.str.match(this.firstClear1);
		if(sz!=undefined){
			for(var i=0;i<sz.length;i++){
				//直接使用对象名称来测试居然有问题，只好这样写了.
				if(/<[^\/]\w* .*?>/g.test(sz[i])){
					this.chooseAndReplace(sz[i]);					
				}
			}
		}
	};
	//第二步处理，清除空标签
	this.clearEmptyLabel=function(){
		var sz=this.str.match(this.secondClear);
		if(sz!=undefined){
			for(var i=0;i<sz.length;i++){
				this.str=this.str.replace(sz[i],"");
			}
		}
		this.str=this.str.replace(/\&nbsp;/g,"");
	};
	//特殊处理,对于文章中的代码块， 做不修改操作，方式是在清理样式之前就把不修改的代码块替换成自定义的内容
	this.customHandlerBrefore=function(){
		this.customStr=this.str.match(/<(pre)(.*?)>.*?<\/\1>/g);
		if(this.customStr!=undefined){
			for(var i=0;i<this.customStr.length;i++){
				this.str=this.str.replace(this.customStr[i], "<customPre>"+i+"</customPre>");
			}
		}
	};
	//在对整个字符串修改完毕后，再将不修改的部分替换回去
	this.customHandlerAfter=function(){
		if(this.customStr!=undefined){
			for(var i=0;i<this.customStr.length;i++){
				this.str=this.str.replace("<customPre>"+i+"</customPre>",this.customStr[i] );
			}
		}
	};
	//对提取出来的标签进行处理
	this.chooseAndReplace=function(s){
		var bool=true;
		for(var i=0;i<this.typeZz.length;i++){
			if(this.typeZz[i].test(s)){
				bool=false;
			}			
		}
		if(bool){
			//如果是普通标签那么去除标签里面的所有内容
			this.str=this.str.replace(s, this.getLabelName(s))
		}else{
			//如果是图片类标签那么就只去除class和style
			this.str=this.str.replace(s,this.getStyleOrClass(s))
		}
	};
	//清除除了标签名字其他所有的内容（img、form等不适合此方法）
	this.getLabelName=function(s){
		var ss=s.match(this.zz1)[0].match(this.zz2)[0];
		return "<"+ss+">";
	};
	//生成不含class的字符串（针对img、form等标签）(如果是图片，那么根据其宽度等比生成长宽)
	this.getStyleOrClass=function(s){
		var w1=s.match(/width(=|:)("|)(\w+)?("|;)/g);
		var h1=s.match(/height(=|:)("|)(\w+)?("|;)/g);
		var width="";
		var height="";
		var h="200";
		var w="300";
		//先判断原图是否已经被设定了宽高，如果已经有宽高则计算设定的宽高
		if(w1!=undefined&&h1!=undefined){
			for(var i=0;i<w1.length;i++){
				width=w1[i].match(/\d+/g);
			}
			for(var i=0;i<h1.length;i++){
				height=h1[i].match(/\d+/g);
			}			
		}else{
			//否则获取图片的原始宽高，计算原始宽高
			var src=s.match(/src=".*?"/);
			var theImage = new Image();
			theImage.src = src[0].match(/".*?"/)[0].match(/[^"]+/);
			width=theImage.width;
			height=theImage.height;
		}
		if(width!=""&height!=""){
			h=Math.round(height/width*w);
		}
		var ss=s.match(this.zz3);
		var st='style="width:'+w+'px;height:'+h+'px;"'
		if(ss!=undefined){
			for(var i=0;i<ss.length;i++){
				if(s.indexOf("style")){
					s=s.replace(ss[i],st);
				}else{
					s=s.replace(ss[i],"");					
				}
			}
		}
		return s;
	};
	
	
}
