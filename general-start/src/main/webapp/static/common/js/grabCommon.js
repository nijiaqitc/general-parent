$(function () {
    resetBottom();
})

window.onresize = function () {
    resetBottom();
}


function resetBottom() {
	if($(".centerDiv")[0]){
		var h = document.documentElement.clientHeight;
		var hbody = $(".centerDiv").height();
		if ((h - hbody) > 80) {
			if (!$(".bottomInfoDiv").hasClass("stbot")) {
				$(".bottomInfoDiv").addClass("stbot");
			}
		} else {
			if ($(".bottomInfoDiv").hasClass("stbot")) {
				$(".bottomInfoDiv").removeClass("stbot");
			}
		}
	}else{
		if((document.documentElement.scrollHeight-$(".bottomInfoDiv")[0].offsetTop)>=600){
			if (!$(".bottomInfoDiv").hasClass("stbot")) {
				$(".bottomInfoDiv").addClass("stbot");
			}
		}
	}
}