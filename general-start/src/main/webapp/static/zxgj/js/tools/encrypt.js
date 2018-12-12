var p=0;
function Encrypt(Text) {
	p=p+1;
	output = new String;
	alterText = new Array();
	varCost = new Array();
	TextSize = Text.length;
	for (i = 0; i < TextSize; i++) {
	idea = Math.round(Math.random() * 111) + 77;
	alterText[i] = Text.charCodeAt(i) + idea;
	varCost[i] = idea;
	}
	for (i = 0; i < TextSize; i++) {
		output += String.fromCharCode(alterText[i], varCost[i]);
	}
	return output;
}
function unEncrypt(Text) {
	if (p>0){
		p=p-1;
		output = new String;
		alterText1 = new Array();
		varCost1 = new Array();
		TextSize = Text.length;
		for (i = 0; i < TextSize; i++) {
			alterText[i] = Text.charCodeAt(i);
			varCost[i] = Text.charCodeAt(i + 1);
		}
		for (i = 0; i < TextSize; i = i+2) {
			output += String.fromCharCode(alterText[i] - varCost[i]);
		}
		return output;
	}
}