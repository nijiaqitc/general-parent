function decodeJ(unDealStr){
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
			" ":dealChar10
			};
	//存放左括号 =-*/!^
	var kuoLeft=[];
	//当前处理到的字符位置
	var parser_pos=0;
	//每个字符
	var charValue;
	var emptyChar=" ";
	//空格占位符包含几个空格
	var indent_size = 4;
	//填充的空格占位符
	var indent_string = "";
	//当前放几次前置空格占位符
	var cindex = 0;
	//@符号标记
	var atEnter=false;
	// 行注释标记
	var lineAnnot=false;
	var morelineAnnot=false;
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
//		if(morelineAnnot){
//			
//		}
		if("	" == charValue){
			continue;
		}
		if(dealFun[charValue]){
			dealFun[charValue]();
		}else{
			addChar();
		}
	}
	$("#outInfo").val(newStr);
	
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
	
	
	function dealChar1(){
		var lastChar = getLastChar(); 
		addChar();
		if(lastChar=="*"){
			addEnterEmpty();
			morelineAnnot=false;
		}else if(lastChar == "/"){
			lineAnnot = true;
		}
	}
	
	function dealChar2(){
		addChar();
		var lastt = newStr.substr(newStr.length-2);
		if(lastt.indexOf("/")>-1){
			morelineAnnot = true;
		}
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
		addEnter();
		addEmpty();
	}
	
	/**
	 * } 操作符号
	 */
	function dealChar6(){
		newStr = newStr.substr(0,newStr.length-indent_string.length);
		--cindex;
		dealChar5();
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
	
	function getLastChar(){
		return newStr.charAt(newStr.length-1);
	}
	
	
	function addEnterEmpty(){
		addEnter();		
		addEmpty();
	}
}