$(function () {
    resetBottom();
})

window.onresize = function () {
    resetBottom();
}


function resetBottom() {
    var h = document.documentElement.clientHeight;
    var hbody = $("html").height();
    if ((h - hbody) > 80) {
        if (!$(".bottomInfoDiv").hasClass("stbot")) {
            $(".bottomInfoDiv").addClass("stbot");
        }
    } else {
        if ($(".bottomInfoDiv").hasClass("stbot")) {
            $(".bottomInfoDiv").removeClass("stbot");
        }
    }
    if((document.documentElement.scrollHeight-$(".bottomInfoDiv")[0].offsetTop)>600){
    	if (!$(".bottomInfoDiv").hasClass("stbot")) {
            $(".bottomInfoDiv").addClass("stbot");
        }
    }
    
}