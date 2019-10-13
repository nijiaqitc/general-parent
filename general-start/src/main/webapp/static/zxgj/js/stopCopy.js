document.oncontextmenu=new Function("event.returnValue=false;");
document.onselectstart=new Function("event.returnValue=false;");
var omitformtags=["input", "textarea", "select"];
omitformtagsomitformtags=omitformtags.join("|");
function disableselect(e){
	if (omitformtags.indexOf(e.target.tagName.toLowerCase())==-1){
		return false;
	}
}
function reEnable(){
	return true;
}
if (typeof document.onselectstart!="undefined"){
	document.onselectstart=new Function ("return false");
}else{
	document.onmousedown=disableselect;
	document.onmouseup=reEnable;
}