$(function () {
    if ((document.documentElement.clientHeight - $("body").height()) > 80) {
        $(".bottomInfoDiv").addClass("stbot");
    } else {
        $(".bottomInfoDiv").removeClass("stbot");
    }
})

window.onresize = function () {
    var h = document.documentElement.clientHeight;
    var hbody = $("body").height();
    if ((h - hbody) > 0) {
        if (!$(".bottomInfoDiv").hasClass("stbot")) {
            $(".bottomInfoDiv").addClass("stbot");
        }
    } else {
        if ($(".bottomInfoDiv").hasClass("stbot")) {
            $(".bottomInfoDiv").removeClass("stbot");
        }
    }
}