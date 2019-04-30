function decodeJ(unDealStr,fillChar,fillTimes){
	var newStr="";
	var dealFun={
			"/":dealChar1,
			"*":dealChar2,
			"@":dealChar3,
			"{":dealChar4,
			";":dealChar5,
			"}":dealChar6,
			"111":dealChar7,
			"(":dealChar8,
			")":dealChar9,
			" ":dealChar10,
			"\"":dealChar11
			};
	var completeChar= "=+-*/^&";
	//存放左括号 
	var kuoLeft=[];
	//当前处理到的字符位置
	var parser_pos=0;
	//每个字符
	var charValue;
	var emptyChar=fillChar||" ";
	//空格占位符包含几个空格
	var indent_size = fillTimes||4;
	//填充的空格占位符
	var indent_string = "";
	//当前放几次前置空格占位符
	var cindex = 0;
	//@符号标记
	var atEnter=false;
	// 行注释标记
	var lineAnnot=false;
	var morelineAnnot=false;
	var yinhaoAnnot = false;
	while (indent_size--) {
        indent_string += emptyChar;
    }
	while(parser_pos < unDealStr.length){
		charValue=unDealStr.charAt(parser_pos);
		++parser_pos;
		if("\n\r\t".indexOf(charValue) > -1){
			if(lineAnnot){
				lineAnnot=false;
				if("\n"==charValue){
					addEnterEmpty();
				}else{
					addChar();
				}
			}
			if(morelineAnnot){
				if("\n"==charValue){
					addEnterEmpty();
				}else if("\t" != charValue){
					addChar();
				}
			}
			if(atEnter){
				addEnter();		
				addEmpty();
				atEnter = false;
			}
			continue;
		}
		if(lineAnnot){
			addChar();
			continue;
		}
		if(dealFun[charValue]){
			if(yinhaoAnnot){
				if("\"" == charValue){
					dealFun[charValue]();
				}else{
					addChar();
				}
			}else{
				dealFun[charValue]();
			}
		}else{
			if(/\s/g.test(charValue)){
				dealChar10();
			}else{
				dealCompleteChar();
				addChar();
			}
		}
	}
	return newStr;
	
	function addEnter(){
		newStr += "\n";
	}
	
	function addEmpty(){
		var emptyTimes=cindex;
		while(emptyTimes--){
			newStr += indent_string;			
		}
	}
	function addEpt(){
		newStr += " ";
	}
	function addChar(){
		newStr += charValue;
	}
	
	/**
	 * 左斜杠
	 */
	function dealChar1(){
		var lastChar = getLastChar(); 
		addChar();
		if(lastChar=="*"){
			addEnterEmpty();
			morelineAnnot=false;
		}else if(lastChar == "/"){
			if(!yinhaoAnnot){
				lineAnnot = true;				
			}
		}
	}
	
	/**
	 *  星号
	 */
	function dealChar2(){
		var lastt = newStr.substr(newStr.length-2);
		if(lastt.indexOf("/")>-1){
			if(!yinhaoAnnot){
				morelineAnnot = true;				
			}
		}
		if(!(morelineAnnot||lineAnnot)){
			dealCompleteChar();
		}
		addChar();
	}
	/**
	 * @ 操作符号
	 */
	function dealChar3(){
		addChar();
		if(kuoLeft.length == 0){
			atEnter = true;
		}else{
			atEnter = false;
		}
		
	}
	
	/**
	 * { 操作符号
	 */
	function dealChar4(){
		if(" " != getLastChar()){
			addEpt();
		}
		addChar();
		++cindex;
		addEnter();
		addEmpty();
	}
	
	/**
	 * ; 操作符号
	 */
	function dealChar5(){
		addChar();
		if(kuoLeft.length == 0){
			addEnter();
			addEmpty();			
		}
	}
	
	/**
	 * } 操作符号
	 */
	function dealChar6(){
		newStr = newStr.substr(0,newStr.length-indent_string.length);
		--cindex;
		if(rcheck()){
			addChar();
			addEpt();
		}else{
			addChar();
			addEnter();
			addEmpty();
		}
		function rcheck(){
			var rchar = ["finally","catch","else"];
			var substr = unDealStr.substr(parser_pos,unDealStr.length);
			substr = substr.trim();
			for(var i = 0 ;i<rchar.length;i++){
				if(substr.startsWith(rchar[i])){
					return true;
				}
			}
			
		}
	}
	
	
	function dealChar7(){
		addChar();
	}
	/**
	 * （
	 */
	function dealChar8(){
		kuoLeft.push(1);
		addChar();
	}
	
	/**
	 * ）
	 */
	function dealChar9(){
		kuoLeft.pop();
		addChar();
		if(kuoLeft.length == 0&& atEnter){
			addEnter();		
			addEmpty();
			atEnter = false;
		}
	}
	
	/**
	 * 空格
	 */
	function dealChar10(){
		var lastChar =newStr.charAt(newStr.length-1) ;
		if("\n\r\t ".indexOf(getLastChar()) > -1){
			return;
		}else{
			addEpt();
		}
	}
	
	/**
	 * 双引号
	 */
	function dealChar11(){
		if(!(lineAnnot || morelineAnnot)){
			if(yinhaoAnnot){
				yinhaoAnnot = false;
			}else{
				yinhaoAnnot = true;
			}
		}
		addChar();
	}
	
	
	function getLastChar(){
		return newStr.charAt(newStr.length-1);
	}
	
	
	function addEnterEmpty(){
		addEnter();		
		addEmpty();
	}
	
	function dealCompleteChar(){
		/*var lc = getLastChar();
		if(completeChar.indexOf(charValue)>-1){
			if(completeChar.indexOf(lc)==-1){
				addEpt();
			}
		}else{
			if(lc!=""&&completeChar.indexOf(lc)>-1){
				addEpt();
			}
		}*/
	}
}