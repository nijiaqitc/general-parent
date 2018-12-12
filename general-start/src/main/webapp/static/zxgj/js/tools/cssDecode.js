var CSSPacker = {
    format: function (s) {//格式化代码
        s = s.replace(/\s*([\{\}\:\;\,])\s*/g, "$1");
        s = s.replace(/;\s*;/g, ";"); //清除连续分号
        s = s.replace(/\,[\s\.\#\d]*{/g, "{");
        s = s.replace(/([^\s])\{([^\s])/g, "$1 {\n\t$2");
        s = s.replace(/([^\s])\}([^\n]*)/g, "$1\n}\n$2");
        s = s.replace(/([^\s]);([^\s\}])/g, "$1;\n\t$2");
        return s;
    },
    pack: function (s) {//高级
        s = s.replace(/\/\*(.|\n)*?\*\//g, ""); //删除注释
        s = s.replace(/\s*([\{\}\:\;\,])\s*/g, "$1");
        s = s.replace(/\,[\s\.\#\d]*\{/g, "{"); //容错处理
        s = s.replace(/;\s*;/g, ";"); //清除连续分号
        s = s.replace(/;\s*}/g, "}"); //清除末尾分号和大括号
        s = s.match(/^\s*(\S+(\s+\S+)*)\s*$/); //去掉首尾空白
        return (s == null) ? "" : s[1];
    },
    packNor: function (s) {//普通
        s = s.replace(/\/\*(.|\n)*?\*\//g, ""); //删除注释
        s = s.replace(/\s*([\{\}\:\;\,])\s*/g, "$1");
        s = s.replace(/\,[\s\.\#\d]*\{/g, "{"); //容错处理
        s = s.replace(/;\s*;/g, ";"); //清除连续分号
        s = s.replace(/;\s*}/g, "}"); //清除末尾分号和大括号
        s = s.replace(/([^\s])\{([^\s])/g, "$1{$2");
        s = s.replace(/([^\s])\}([^\n]s*)/g, "$1}\n$2");
        return s;
    }
}
/*function CSS(s) {
    function $$(id){return (document.getElementById) ? document.getElementById(id): document.all[id]}  
    $$("packer").value = CSSPacker[s]($$("code").value);
}*/