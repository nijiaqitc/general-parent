function decodeStr(st) {
    var cd = new CustomDecoder();
    cd.str = st;
    return cd.decode();
}

/**
 * 对文章进行样式清理
 * 第一步：先去除所有样式
 * 第二步：去除所有空标签
 * 第三步：
 */
function CustomDecoder() {
    this.str = "";
    //显示平台，如果在app显示 图片大小需要特殊处理
    this.showPlatform = "";
    //先提取出所有开头标签
    this.firstClear1 = /<[^\/].*?>/g;
    //再判断提出来的标签是否包含了样式
    //this.firstClear2=/<[^\/]\w+ .+?>/g; 直接修改下面的正则不修改此处的正则
    //因为第一步清除了样式，所以不会再存在标签里面存在空格的情况，所以直接用下面的匹配即可
    //以<开始匹配后面的连续字符直到第一个>结尾接着以</开始 和第一个括号内相同的字符>结尾
    this.secondClear = /<(\w*)>(<\/\1*>)/g;
    //判断标签所属类型
    this.typeZz = [/<img.?/, /<form.?/, /<table.?/, /<th.?/, /<tr.?/, /<td.?/, /<a.?/];
    //匹配标签中的前一部分如：(<p )
    this.zz1 = /.*? /;
    //去除上面匹配出来的字符串中的"<"、" "
    this.zz2 = /[^< ]+/;
    //匹配标签中的style或class
    this.zz3 = /(style|class)=".*?"/g;
    this.customStr = "";
    this.validate = function () {
        if (this.str == undefined) {
            return true;
        } else {
            return false;
        }
    }
    //开始处理
    this.decode = function () {
        if (this.validate()) {
            return "";
        }
        this.customHandlerBrefore();
        this.clearLabelProperty();
        this.clearEmptyLabel();
        this.customHandlerAfter();
        return this.str;
    };
    //第一步处理，清理样式
    this.clearLabelProperty = function () {
        //将所有符合正则表达式的字符串提取出来
        var sz = this.str.match(this.firstClear1);
        if (sz != null) {
            for (var i = 0; i < sz.length; i++) {
                //直接使用对象名称来测试居然有问题，只好这样写了.
                if (/<[^\/]\w* .*?>/g.test(sz[i])) {
                    this.chooseAndReplace(sz[i]);
                }
            }
        }
    };
    //第二步处理，清除空标签
    this.clearEmptyLabel = function () {
        this.str = this.str.replace(/\&nbsp;/g, "");
        var sz = this.str.match(this.secondClear);
        if (sz != undefined) {
            for (var i = 0; i < sz.length; i++) {
                if (!(/<th.?/.test(sz[i]) || /<td.?/.test(sz[i]))) {
                    this.str = this.str.replace(sz[i], "");
                }
            }
        }
    };
    //特殊处理,对于文章中的代码块， 做不修改操作，方式是在清理样式之前就把不修改的代码块替换成自定义的内容
    this.customHandlerBrefore = function () {
        var tablestr = this.str.match(/<(table)(.*?)>(.|\n)*?<\/\1>/g);
        if (tablestr != null) {
            for (var i = 0; i < tablestr.length; i++) {
                var prestr = tablestr[i].match(/<pre/g);
                if (prestr != null) {
                    var tstr = tablestr[i];
                    this.str = this.str.replace(tablestr[i],"<customTable />");
                    for (var j = 0; j < prestr.length; j++) {
                        tstr = tstr.replace(prestr[j], "<span");
                    }
                    prestr = tablestr[i].match(/<\/pre/g);
                    for (var j = 0; j < prestr.length; j++) {
                        tstr = tstr.replace(prestr[j], "</span");
                    }
                    this.str = this.str.replace("<customTable />",tstr);
                }
            }
        }
        this.customStr = this.str.match(/<(pre)(.*?)>(.|\n)*?<\/\1>/g);
        if (this.customStr != null) {
            for (var i = 0; i < this.customStr.length; i++) {
                var s = this.customStr[i].match(/<.*?>/)[0];
                var e = this.customStr[i].match(/<\/pre.*?>/)[0];
                var con = this.customStr[i].substring(s.length, this.customStr[i].length - e.length);
                this.str = this.str.replace(con, "<customPre>" + i + "</customPre>");
                this.customStr[i] = con;
            }
        }
    };
    //在对整个字符串修改完毕后，再将不修改的部分替换回去
    this.customHandlerAfter = function () {
        if (this.customStr != undefined) {
            for (var i = 0; i < this.customStr.length; i++) {
                this.str = this.str.replace("<customPre>" + i + "</customPre>", this.customStr[i]);
            }
        }
    };
    //对提取出来的标签进行处理
    this.chooseAndReplace = function (s) {
        var bool = true;
        for (var i = 0; i < this.typeZz.length; i++) {
            if (this.typeZz[i].test(s)) {
                bool = false;
                break;
            }
        }
        if (bool) {
            //如果是普通标签那么去除标签里面的所有内容
            this.str = this.str.replace(s, this.getLabelName(s))
        } else {
            //如果是图片、表单等标签那么进行对应的标签处理
            this.str = this.str.replace(s, this.getStyleOrClass(s))
        }
    };
    //清除除了标签名字其他所有的内容（img、form等不适合此方法）
    this.getLabelName = function (s) {
        var ss = s.match(this.zz1)[0].match(this.zz2)[0];
        return "<" + ss + ">";
    };
    //生成不含class的字符串（针对img、form等标签）(如果是图片，那么根据其宽度等比生成长宽)
    this.getStyleOrClass = function (s) {
        if (s.startsWith("<img ")) {
            return this.dealImgStr(s);
        }
        if (s.startsWith("<a ")) {
            return this.dealAStr(s);
        }
        return this.removeCss(s);
    };
    this.dealImgStr = function (s) {
        var w1 = s.match(/width(=|:)("|)(\w+)?("|;)/g);
        var h1 = s.match(/height(=|:)("|)(\w+)?("|;)/g);
        var h = "200";
        var w = "300";
        var st = "";
        if (w1 != undefined || h1 != undefined) {
            var width = "";
            var height = "";
            //先判断原图是否已经被设定了宽高，如果已经有宽高则计算设定的宽高
            if (w1 != undefined) {
                for (var i = 0; i < w1.length; i++) {
                    width = w1[i].match(/\d+/g);
                }
            }
            if (h1 != undefined) {
                for (var i = 0; i < h1.length; i++) {
                    height = h1[i].match(/\d+/g);
                }
            }
            if (width != "") {
                w = width;
                st += "width:" + w + "px;";
            }
            if (height != "") {
                h = height;
                st += "height:" + h + "px;";
            }
        }
        if (this.showPlatform != "") {
            st += "max-width:300px;max-height:200px;"
        }
        var src = s.match(/src=".*?"/);
        if(src != null){
            src = src[0];
        }else{
            src = "src=''";
        }
        if(st == ""){
        	return "<img " + src + " >";
        }else{
        	return "<img " + src + ' style="' + st + '"' + " >";
        }
    };
    this.dealAStr = function (s) {
        var href = s.match(/href=".*?"/);
        if (href != null) {
            href = href[0];
        }else{
            href = "href=''";
        }
        var jumptype = " target='_blank' ";
        return "<a " + href + jumptype + ">";
    };
    this.removeCss = function (s) {
        var ss = s.match(this.zz3);
        if (ss != null) {
            for (var i = 0; i < ss.length; i++) {
                s = s.replace(ss[i], "");
            }
        }
        return s;
    };
}
