function decodeJ(unDealStr){
	var newStr="";
	var dealFun={
			"/*":1,
			"/**":2,
			"@":dealChar3,
			"{":dealChar4,
			";":dealChar5,
			"}":dealChar5,
			"*/":7,
			"(":dealChar8,
			")":dealChar9,
			" ":dealChar10
			};
	var kuoLeft=[];
	var parser_pos=0;
	var charValue;
	var emptyChar=" ";
	var indent_size = 4;
	var indent_string = "";
	var cindex = 0;
	var atEnter=true;
	while (indent_size--) {
        indent_string += emptyChar;
    }
	
	while(parser_pos < unDealStr.length){
		charValue=unDealStr.charAt(parser_pos);
		if(dealFun[charValue]){
			dealFun[charValue]();
		}else{
			newStr+=charValue;
		}
		++parser_pos;
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
	
	function dealChar3(){
		if(kuoLeft.length>0){
			atEnter = true;
		}
	}
	
	function dealChar4(){
		++cindex;
		addEnter();
		addEmpty();
	}
	
	function dealChar5(){
		addEnter();
		addEmpty();
	}
	
	function dealChar8(){
		kuoLeft.push(1);
	}
	
	function dealChar9(){
		kuoLeft.pop();
	}
	
	function dealChar10(){
		if(atEnter){
			dealChar5();
		}
	}
	
}