var a=62; 
function encode(code) {
//	var code = document.getElementById('code').value;
	code = code.replace(/[\r\n]+/g, '');
	code = code.replace(/'/g, "\\'");
	var tmp = code.match(/\b(\w+)\b/g);
	tmp.sort();
	var dict = [];
	var i, t = '';
	for ( var i = 0; i < tmp.length; i++) {
		if (tmp[i] != t)
			dict.push(t = tmp[i]);
	}
	var len = dict.length;
	var ch;
	for (i = 0; i < len; i++) {
		ch = num(i);
		code = code.replace(new RegExp('\\b' + dict[i] + '\\b', 'g'), ch);
		if (ch == dict[i])
			dict[i] = '';
	}
	var lockst= "eval(function(p,a,c,k,e,d){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)d[e(c)]=k[c]||e(c);k=[function(e){return d[e]}];e=function(){return'\\\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\\\b'+e(c)+'\\\\b','g'),k[c]);return p}("
			+ "'"
			+ code
			+ "',"
			+ a
			+ ","
			+ len
			+ ",'"
			+ dict.join('|')
			+ "'.split('|'),0,{}))";
	return lockst;
}
function num(c) {
	return (c < a ? '' : num(parseInt(c / a)))
			+ ((c = c % a) > 35 ? String.fromCharCode(c + 29) : c.toString(36));

}
function run(code) {
	eval(code);
}
function decode(code) {
	code = code.replace(/^eval/, '');
	return eval(code);
} 