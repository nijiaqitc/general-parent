<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>正则表达式</title>
<link rel="shortcut icon" href="${resPath }/zxgj/img/logo.png" />
<link rel="stylesheet" href="${resPath }/zxgj/css/common.css">
<link rel="stylesheet" href="${resPath }/zxgj/css/tools.css">
<style type="text/css">
.btnStyle {
	cursor: pointer;
	width: 50px;
	height: 30px;
	color: white;
	background-color: #E12422;
	font-size: 12px;
	border: 0;
}

.leftCommUl {
	font-size: 12px;
	border-right: 1px solid #ddd;
}

.leftCommUl li {
	cursor: pointer;
}

.leftCommUl li:hover {
	background-color: #eee;
}

table td {
	border: 1px solid;
	padding-left: 4px;
}

.zztable {
	border-spacing: 0px;
	margin-top: 5px;
	margin-bottom: 5px;
	word-wrap: break-word;
	word-break: break-all;
	font-size: 12px;
	line-height: 22px;
	color: rgb(0, 0, 0);
	font-family: arial, 宋体, sans-serif;
	font-style: normal;
}

.leftTd {
	padding-top: 2px;
	padding-bottom: 2px;
	font-size: 12px;
	line-height: 22px;
	height: 22px;
	border-color: rgb(230, 230, 230);
}

.leftTd span {
	font-size: 12px;
	word-wrap: break-word;
	color: rgb(51, 51, 51);
	margin-top: 0px;
	margin-bottom: 0px;
	line-height: 24px;
	zoom: 1;
	height: auto;
}

.trd {
	background-color: #F6F4F0;
}

.trf {
	background-color: #fff;
}
</style>
</head>
<body>
	<!--     开始：顶部菜单栏-->
	<jsp:include page="../top1.jsp"></jsp:include>
	<div style="height: 20px;width: 100%;background-color: #ec8316;"></div>
	<!--     结束：顶部菜单栏 -->
	<!-- 中间正文公用部分 -->
	<div class="contextArea" align="center">
		<div style="font-size: 12px;background-color: #c0d9ef;overflow: auto;">
			<div style="margin-left: 10px;float: left;">当前位置：加密工具>>正则表达式</div>
		</div>
		<div align="center"
			style="width: 1000px;overflow: auto;margin-top: 20px;clear: both;">
			<div style="float: left;width: 200px;" align="left">
				<div>常用正则表达式</div>
				<div style="margin-left: 10px;">
					<ul class="leftCommUl">
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/[\u4e00-\u9fa5]/g" title="匹配中文字符">匹配中文字符</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/[^\x00-\xff]/g" title="匹配双字节字符(包括汉字在内)">匹配双字节字符(包括汉字在内)</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/\n\s*\r/g" title="匹配空白行">匹配空白行</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/[\w!#$%&amp;'*+/=?^_`{|}~-]+(?:\.[\w!#$%&amp;'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/g"
							title="匹配Email地址">匹配Email地址</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/[a-zA-z]+:\/\/[^\s]*/g" title="匹配网址URL">匹配网址URL</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/\d{3}-\d{8}|\d{4}-\{7,8}/g" title="匹配国内电话号码">匹配国内电话号码</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/[1-9][0-9]{4,}/g" title="匹配腾讯QQ号">匹配腾讯QQ号</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/[1-9]\d{5}(?!\d)/g" title="匹配中国邮政编码">匹配中国邮政编码</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/g"
							title="匹配18位身份证号">匹配18位身份证号</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))/g"
							title="匹配(年-月-日)格式日期">匹配(年-月-日)格式日期</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/^[1-9]\d*$/g" title="匹配正整数">匹配正整数</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/^-[1-9]\d*$/g" title="匹配负整数">匹配负整数</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/^-?[1-9]\d*$/g" title="匹配整数">匹配整数</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/^[1-9]\d*|0$/g" title="匹配非负整数（正整数 + 0）">匹配非负整数（正整数 +
								0）</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/^-[1-9]\d*|0$/g" title="匹配非正整数（负整数 + 0）">匹配非正整数（负整数
								+ 0）</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/g" title="匹配正浮点数">匹配正浮点数</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/^-[1-9]\d*\.\d*|-0\.\d*[1-9]\d*$/g" title="匹配负浮点数">匹配负浮点数</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/^[a-zA-Z]\w{5,15}$/g" title="验证用户名和密码">验证用户名和密码</a></li>
						<li onclick="getthis(this)"><a href="javascript:void(0);"
							zzattr="/^[0-9]+([.][0-9]+){0,1}$/g" title="整数或者小数">整数或者小数</a></li>
					</ul>
				</div>
			</div>
			<div style="float:left;width: 750px;margin-left: 20px;">
				<div>
					<textarea
						style="min-width: 600px;width:100%;height: 280px;resize:vertical;outline: none;padding: 12px;"
						id="text1">测a试b文c本</textarea>
				</div>
				<div
					style="float: left;margin-top: 10px;margin-bottom: 10px;margin-left: 16px;">
					<input type="text" placeholder="请输入正则表达式如：/abc/g" value="/[abc]/g"
						style="width: 500px;" id="zzbds">
				</div>
				<div class="btnStyle"
					style="margin-left: 10px;float: left;margin-top: 10px;margin-bottom: 10px;"
					onclick="exec()">exec</div>
				<div class="btnStyle"
					style="margin-left: 10px;float: left;margin-top: 10px;margin-bottom: 10px;"
					onclick="match()">match</div>
				<div class="btnStyle"
					style="margin-left: 10px;float: left;margin-top: 10px;margin-bottom: 10px;"
					onclick="test()">test</div>
				<div>
					<textarea
						style="min-width: 600px;width:100%;height: 280px;resize:vertical;outline: none;padding: 12px;"
						id="text2" readonly="readonly"></textarea>
				</div>
			</div>
		</div>
		<div align="left"
			style="width: 1000px;background-color: bisque;padding: 4px;margin-top: 10px;cursor: pointer;font-size: 12px;"
			onclick="showgz()">显示正则规则----来自百度百科+</div>
		<div style="clear: both;width: 1000px;display: none;" id="gzb">
			<table class="zztable">
				<thead>
					<tr>
						<th style="background-color: #555555;color: white;">符号</th>
						<th style="background-color: #555555;color: white;">说明</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="leftTd"><span>\</span><br></td>
						<td class="leftTd"><span>转义字符</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>^</span><br></td>
						<td class="leftTd"><span>匹配输入字符串的开始位置。如果设置了RegExp对象的Multiline属性，^也匹配“\n”或“\r”之后的位置。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>$</span><br></td>
						<td class="leftTd"><span>匹配输入字符串的结束位置。如果设置了RegExp对象的Multiline属性，$也匹配“\n”或“\r”之前的位置。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>*</span><br></td>
						<td class="leftTd"><span>匹配前面的 子表达式 任意 次。*等价于o{0,}</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>+</span><br></td>
						<td class="leftTd"><span>匹配前面的子表达式一次或多次(大于等于1次）。+等价于{1,}。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>?</span><br></td>
						<td class="leftTd"><span>匹配前面的子表达式零次或一次。?等价于{0,1}。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>{n}</span><br></td>
						<td class="leftTd"><span>n是一个非负整数。匹配确定的n次。例如，“o{2}”不能匹配“Bob”中的“o”，但是能匹配“food”中的两个o。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>{n,}</span><br></td>
						<td class="leftTd"><span>n是一个非负整数。至少匹配n次。例如，“o{2,}”不能匹配“Bob”中的“o”，但能匹配“foooood”中的所有o。“o{1,}”等价于“o+”。“o{0,}”则等价于“o*”。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>{n,m}</span><br></td>
						<td class="leftTd"><span>m和n均为非负整数，其中n&lt;=m。最少匹配n次且最多匹配m次。例如，“o{1,3}”将匹配“fooooood”中的前三个o为一组，后三个o为一组。“o{0,1}”等价于“o?”。请注意在逗号和两个数之间不能有空格。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>?</span><br></td>
						<td class="leftTd"><span>当该字符紧跟在任何一个其他限制符（*,+,?，{n}，{n,}，{n,m}）后面时，匹配模式是非贪婪的。非贪婪模式尽可能少的匹配所搜索的字符串，而默认的贪婪模式则尽可能多的匹配所搜索的字符串。例如，对于字符串“oooo”，“o+”将尽可能多的匹配“o”，得到结果[“oooo”]，而“o+?”将尽可能少的匹配“o”，得到结果
								['o', 'o', 'o', 'o']</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>.点</span><br></td>
						<td class="leftTd"><span>匹配除“\r\n”之外的任何单个字符。要匹配包括“\r\n”在内的任何字符，请使用像“[\s\S]”的模式。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>(pattern)</span><br></td>
						<td class="leftTd"><span>匹配pattern并获取这一匹配。所获取的匹配可以从产生的Matches集合得到，在VBScript中使用SubMatches集合，在JScript中则使用$0…$9属性。要匹配圆括号字符，请使用“\(”或“\)”。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>(?:pattern)</span><br></td>
						<td class="leftTd"><span>非获取匹配，匹配pattern但不获取匹配结果，不进行存储供以后使用。这在使用或字符“(|)”来组合一个模式的各个部分时很有用。例如“industr(?:y|ies)”就是一个比“industry|industries”更简略的表达式。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>(?=pattern)</span><br></td>
						<td class="leftTd"><span>非获取匹配，正向肯定预查，在任何匹配pattern的字符串开始处匹配查找字符串，该匹配不需要获取供以后使用。例如，“Windows(?=95|98|NT|2000)”能匹配“Windows2000”中的“Windows”，但不能匹配“Windows3.1”中的“Windows”。预查不消耗字符，也就是说，在一个匹配发生后，在最后一次匹配之后立即开始下一次匹配的搜索，而不是从包含预查的字符之后开始。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>(?!pattern)</span><br></td>
						<td class="leftTd"><span>非获取匹配，正向否定预查，在任何不匹配pattern的字符串开始处匹配查找字符串，该匹配不需要获取供以后使用。例如“Windows(?!95|98|NT|2000)”能匹配“Windows3.1”中的“Windows”，但不能匹配“Windows2000”中的“Windows”。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>(?&lt;=pattern)</span><br></td>
						<td class="leftTd"><span>非获取匹配，反向肯定预查，与正向肯定预查类似，只是方向相反。例如，“(?&lt;=95|98|NT|2000)Windows”能匹配“2000Windows”中的“Windows”，但不能匹配“3.1Windows”中的“Windows”。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>(?&lt;!pattern)</span><br></td>
						<td class="leftTd"><span>非获取匹配，反向否定预查，与正向否定预查类似，只是方向相反。例如“(?&lt;!95|98|NT|2000)Windows”能匹配“3.1Windows”中的“Windows”，但不能匹配“2000Windows”中的“Windows”。这个地方不正确，有问题</span><br>
							<span>此处用或任意一项都不能超过2位，如“(?&lt;!95|98|NT|20)Windows正确，“(?&lt;!95|980|NT|20)Windows
								报错，若是单独使用则无限制，如(?&lt;!2000)Windows 正确匹配</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>x|y</span><br></td>
						<td class="leftTd"><span>匹配x或y。例如，“z|food”能匹配“z”或“food”(此处请谨慎)。“[z|f]ood”则匹配“zood”或“food”。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>[xyz]</span><br></td>
						<td class="leftTd"><span>字符集合。匹配所包含的任意一个字符。例如，“[abc]”可以匹配“plain”中的“a”。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>[^xyz]</span><br></td>
						<td class="leftTd"><span>负值字符集合。匹配未包含的任意字符。例如，“[^abc]”可以匹配“plain”中的“plin”。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>[a-z]</span><br></td>
						<td class="leftTd"><span>字符范围。匹配指定范围内的任意字符。例如，“[a-z]”可以匹配“a”到“z”范围内的任意小写字母字符。</span><br>
							<span>注意:只有连字符在字符组内部时,并且出现在两个字符之间时,才能表示字符的范围;
								如果出字符组的开头,则只能表示连字符本身.</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>[^a-z]</span><br></td>
						<td class="leftTd"><span>负值字符范围。匹配任何不在指定范围内的任意字符。例如，“[^a-z]”可以匹配任何不在“a”到“z”范围内的任意字符。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>\b</span><br></td>
						<td class="leftTd"><span>匹配一个单词边界，也就是指单词和空格间的位置（即正则表达式的“匹配”有两种概念，一种是匹配字符，一种是匹配位置，这里的\b就是匹配位置的）。例如，“er\b”可以匹配“never”中的“er”，但不能匹配“verb”中的“er”。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>\B</span><br></td>
						<td class="leftTd"><span>匹配非单词边界。“er\B”能匹配“verb”中的“er”，但不能匹配“never”中的“er”。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>\cx</span><br></td>
						<td class="leftTd"><span>匹配由x指明的控制字符。例如，\cM匹配一个Control-M或回车符。x的值必须为A-Z或a-z之一。否则，将c视为一个原义的“c”字符。</span><br></td>
					</tr>
					<tr>
						<td width="75" class="leftTd"><span>\d</span><br></td>
						<td class="leftTd"><span>匹配一个数字字符。等价于[0-9]。grep
								要加上-P，perl正则支持</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\D</span><br></td>
						<td class="leftTd"><span>匹配一个非数字字符。等价于[^0-9]。grep要加上-P，perl正则支持</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\f</span><br></td>
						<td class="leftTd"><span>匹配一个换页符。等价于\x0c和\cL。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\n</span><br></td>
						<td class="leftTd"><span>匹配一个换行符。等价于\x0a和\cJ。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\r</span><br></td>
						<td class="leftTd"><span>匹配一个回车符。等价于\x0d和\cM。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\s</span><br></td>
						<td class="leftTd"><span>匹配任何不可见字符，包括空格、制表符、换页符等等。等价于[
								\f\n\r\t\v]。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\S</span><br></td>
						<td class="leftTd"><span>匹配任何可见字符。等价于[^ \f\n\r\t\v]。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\t</span><br></td>
						<td class="leftTd"><span>匹配一个制表符。等价于\x09和\cI。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\v</span><br></td>
						<td class="leftTd"><span>匹配一个垂直制表符。等价于\x0b和\cK。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\w</span><br></td>
						<td class="leftTd"><span>匹配包括下划线的任何单词字符。类似但不等价于“[A-Za-z0-9_]”，这里的"单词"字符使用Unicode字符集。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\W</span><br></td>
						<td class="leftTd"><span>匹配任何非单词字符。等价于“[^A-Za-z0-9_]”。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\xn</span><br></td>
						<td class="leftTd"><span>匹配n，其中n为十六进制转义值。十六进制转义值必须为确定的两个数字长。例如，“\x41”匹配“A”。“\x041”则等价于“\x04&amp;1”。正则表达式中可以使用ASCII编码。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\num</span><br></td>
						<td class="leftTd"><span>匹配num，其中num是一个正整数。对所获取的匹配的引用。例如，“(.)\1”匹配两个连续的相同字符。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\n</span><br></td>
						<td class="leftTd"><span>标识一个八进制转义值或一个向后引用。如果\n之前至少n个获取的子表达式，则n为向后引用。否则，如果n为八进制数字（0-7），则n为一个八进制转义值。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\nm</span><br></td>
						<td class="leftTd"><span>标识一个八进制转义值或一个向后引用。如果\nm之前至少有nm个获得子表达式，则nm为向后引用。如果\nm之前至少有n个获取，则n为一个后跟文字m的向后引用。如果前面的条件都不满足，若n和m均为八进制数字（0-7），则\nm将匹配八进制转义值nm。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\nml</span><br></td>
						<td class="leftTd"><span>如果n为八进制数字（0-7），且m和l均为八进制数字（0-7），则匹配八进制转义值nml。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\un</span><br></td>
						<td class="leftTd"><span>匹配n，其中n是一个用四个十六进制数字表示的Unicode字符。例如，\u00A9匹配版权符号（&amp;copy;）。</span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\p{P}</span><br></td>
						<td class="leftTd"><span>小写 p 是 property 的意思，表示
								Unicode 属性，用于 Unicode 正表达式的前缀。中括号内的“P”表示Unicode
								字符集七个字符属性之一：标点字符。</span><br> <span>其他六个属性：</span><br> <span>L：字母；</span><br>
							<span>M：标记符号（一般不会单独出现）；</span><br> <span>Z：分隔符（比如空格、换行等）；</span><br>
							<span>S：符号（比如数学符号、货币符号等）；</span><br> <span>N：数字（比如阿拉伯数字、罗马数字等）；</span><br>
							<span>C：其他字符。</span><br> <span><i
								style="font-style:italic;">*注：此语法部分语言不支持，例：javascript。</i></span><br></td>
					</tr>
					<tr>
						<td class="leftTd"><span>\&lt;</span><br> <span>\&gt;</span><br></td>
						<td class="leftTd">匹配词（word）的开始（\&lt;）和结束（\&gt;）。例如正则表达式\&lt;the\&gt;能够匹配字符串"for
							the wise"中的"the"，但是不能匹配字符串"otherwise"中的"the"。注意：这个元字符不是所有的软件都支持的。</td>
					</tr>
					<tr>
						<td class="leftTd">( )</td>
						<td class="leftTd">将( 和 )
							之间的表达式定义为“组”（group），并且将匹配这个表达式的字符保存到一个临时区域（一个正则表达式中最多可以保存9个），它们可以用
							\1 到\9 的符号来引用。</td>
					</tr>
					<tr>
						<td class="leftTd">|</td>
						<td class="leftTd">将两个匹配条件进行逻辑“或”（Or）运算。例如正则表达式(him|her)
							匹配"it belongs to him"和"it belongs to her"，但是不能匹配"it belongs to
							them."。注意：这个元字符不是所有的软件都支持的。</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="bottomD"></div>
	</div>

	<!--     开始：顶部菜单栏-->
	<jsp:include page="../bottom.jsp"></jsp:include>
	<!--     结束：顶部菜单栏 -->
	<script src="${resPath }/jquery/jquery.min.js"
		type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/common.js" type="text/javascript"></script>
	<script src="${resPath }/zxgj/js/tools/jsDecode.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		function exec() {
			if ($("#text1").val() == "") {
				return;
			}
			var exval = eval($("#zzbds").val()).exec($("#text1").val());
			if (exval) {
				$("#text2").val(exval);
			} else {
				$("#text2").val("无匹配结果");
			}
		}

		function match() {
			if ($("#text1").val() == "") {
				return;
			}
			var exval = $("#text1").val().match(eval($("#zzbds").val()));
			if (exval) {
				var v = "匹配到结果数：" + exval.length + "个\n 结果为：" + exval;
				$("#text2").val(v);
			} else {
				$("#text2").val("无匹配结果");
			}
		}

		function test() {
			if ($("#text1").val() == "") {
				return;
			}
			var exval = eval($("#zzbds").val()).test($("#text1").val());
			$("#text2").val(exval);
		}

		function getthis(target) {
			$("#zzbds").val(target.firstElementChild.getAttribute("zzattr"));
			$("#text2").val(null);
		}
		
		function showgz(){
			if($("#gzb").css("display")=="block"){
				$("#gzb").hide();
			}else{
				$("#gzb").show();
			}
		}
		
		var trarray=$(".zztable tr");
		for(var i=0;i<trarray.length;i++){
			if(i%2==0){
				trarray[i].classList.add("trd");
			}else{
				trarray[i].classList.add("trf");
			}
		}
		
		$(document).ready(function(){
			$(".nagivationBottomSelect").removeClass("nagivationBottomSelect");
			$($(".nagivationBottom")[1]).addClass("nagivationBottomSelect");
		})
	</script>
</body>
</html>