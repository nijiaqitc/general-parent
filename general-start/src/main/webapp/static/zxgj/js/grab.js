$(function () {
    var cd = new CustomDecoder();
    cd.str = $("#context").html();
    $("#context").html(cd.decode());
    decodePre();
})


function decodePre() {
    for (var i = 0; i < $("pre").length; i++) {
        var code = document.createElement("code");
        code.innerHTML = $($("pre")[i]).html();
        $($("pre")[i]).html(code);
        var ul = document.createElement("ul");
        ul.classList.add("pre-numbering");
        var height = $($("pre")[i]).height();
        var num = Number(height / 22);
        for (var j = 0; j < num; j++) {
            var li = document.createElement("li");
            li.innerHTML = j + 1;
            ul.append(li);
        }
        $($("pre")[i]).append(ul);
    }
    $("pre").addClass("prettyprint");
    prettyPrint();
}