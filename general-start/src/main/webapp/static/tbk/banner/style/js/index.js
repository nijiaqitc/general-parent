
/*******************************************************************************
 jquery.mb.components
 Copyright (c) 2001-2010. Matteo Bicocchi (Pupunzi); Open lab srl, Firenze - Italy
 email: mbicocchi@open-lab.com
 site: http://pupunzi.com

 Licences: MIT, GPL
 http://www.opensource.org/licenses/mit-license.php
 http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/

// t: current time, b: begInnIng value, c: change In value, d: duration
jQuery.easing['jswing'] = jQuery.easing['swing'];

jQuery.extend( jQuery.easing,
{
	def: 'easeOutQuad',
	swing: function (x, t, b, c, d) {
		//alert(jQuery.easing.default);
		return jQuery.easing[jQuery.easing.def](x, t, b, c, d);
	},
	easeInQuad: function (x, t, b, c, d) {
		return c*(t/=d)*t + b;
	},
	easeOutQuad: function (x, t, b, c, d) {
		return -c *(t/=d)*(t-2) + b;
	},
	easeInOutQuad: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t + b;
		return -c/2 * ((--t)*(t-2) - 1) + b;
	},
	easeInCubic: function (x, t, b, c, d) {
		return c*(t/=d)*t*t + b;
	},
	easeOutCubic: function (x, t, b, c, d) {
		return c*((t=t/d-1)*t*t + 1) + b;
	},
	easeInOutCubic: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t*t + b;
		return c/2*((t-=2)*t*t + 2) + b;
	},
	easeInQuart: function (x, t, b, c, d) {
		return c*(t/=d)*t*t*t + b;
	},
	easeOutQuart: function (x, t, b, c, d) {
		return -c * ((t=t/d-1)*t*t*t - 1) + b;
	},
	easeInOutQuart: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t*t*t + b;
		return -c/2 * ((t-=2)*t*t*t - 2) + b;
	},
	easeInQuint: function (x, t, b, c, d) {
		return c*(t/=d)*t*t*t*t + b;
	},
	easeOutQuint: function (x, t, b, c, d) {
		return c*((t=t/d-1)*t*t*t*t + 1) + b;
	},
	easeInOutQuint: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t*t*t*t + b;
		return c/2*((t-=2)*t*t*t*t + 2) + b;
	},
	easeInSine: function (x, t, b, c, d) {
		return -c * Math.cos(t/d * (Math.PI/2)) + c + b;
	},
	easeOutSine: function (x, t, b, c, d) {
		return c * Math.sin(t/d * (Math.PI/2)) + b;
	},
	easeInOutSine: function (x, t, b, c, d) {
		return -c/2 * (Math.cos(Math.PI*t/d) - 1) + b;
	},
	easeInExpo: function (x, t, b, c, d) {
		return (t==0) ? b : c * Math.pow(2, 10 * (t/d - 1)) + b;
	},
	easeOutExpo: function (x, t, b, c, d) {
		return (t==d) ? b+c : c * (-Math.pow(2, -10 * t/d) + 1) + b;
	},
	easeInOutExpo: function (x, t, b, c, d) {
		if (t==0) return b;
		if (t==d) return b+c;
		if ((t/=d/2) < 1) return c/2 * Math.pow(2, 10 * (t - 1)) + b;
		return c/2 * (-Math.pow(2, -10 * --t) + 2) + b;
	},
	easeInCirc: function (x, t, b, c, d) {
		return -c * (Math.sqrt(1 - (t/=d)*t) - 1) + b;
	},
	easeOutCirc: function (x, t, b, c, d) {
		return c * Math.sqrt(1 - (t=t/d-1)*t) + b;
	},
	easeInOutCirc: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return -c/2 * (Math.sqrt(1 - t*t) - 1) + b;
		return c/2 * (Math.sqrt(1 - (t-=2)*t) + 1) + b;
	},
	easeInElastic: function (x, t, b, c, d) {
		var s=1.70158;var p=0;var a=c;
		if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
		if (a < Math.abs(c)) { a=c; var s=p/4; }
		else var s = p/(2*Math.PI) * Math.asin (c/a);
		return -(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
	},
	easeOutElastic: function (x, t, b, c, d) {
		var s=1.70158;var p=0;var a=c;
		if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
		if (a < Math.abs(c)) { a=c; var s=p/4; }
		else var s = p/(2*Math.PI) * Math.asin (c/a);
		return a*Math.pow(2,-10*t) * Math.sin( (t*d-s)*(2*Math.PI)/p ) + c + b;
	},
	easeInOutElastic: function (x, t, b, c, d) {
		var s=1.70158;var p=0;var a=c;
		if (t==0) return b;  if ((t/=d/2)==2) return b+c;  if (!p) p=d*(.3*1.5);
		if (a < Math.abs(c)) { a=c; var s=p/4; }
		else var s = p/(2*Math.PI) * Math.asin (c/a);
		if (t < 1) return -.5*(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
		return a*Math.pow(2,-10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )*.5 + c + b;
	},
	easeInBack: function (x, t, b, c, d, s) {
		if (s == undefined) s = 1.70158;
		return c*(t/=d)*t*((s+1)*t - s) + b;
	},
	easeOutBack: function (x, t, b, c, d, s) {
		if (s == undefined) s = 1.70158;
		return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
	},
	easeInOutBack: function (x, t, b, c, d, s) {
		if (s == undefined) s = 1.70158; 
		if ((t/=d/2) < 1) return c/2*(t*t*(((s*=(1.525))+1)*t - s)) + b;
		return c/2*((t-=2)*t*(((s*=(1.525))+1)*t + s) + 2) + b;
	},
	easeInBounce: function (x, t, b, c, d) {
		return c - jQuery.easing.easeOutBounce (x, d-t, 0, c, d) + b;
	},
	easeOutBounce: function (x, t, b, c, d) {
		if ((t/=d) < (1/2.75)) {
			return c*(7.5625*t*t) + b;
		} else if (t < (2/2.75)) {
			return c*(7.5625*(t-=(1.5/2.75))*t + .75) + b;
		} else if (t < (2.5/2.75)) {
			return c*(7.5625*(t-=(2.25/2.75))*t + .9375) + b;
		} else {
			return c*(7.5625*(t-=(2.625/2.75))*t + .984375) + b;
		}
	},
	easeInOutBounce: function (x, t, b, c, d) {
		if (t < d/2) return jQuery.easing.easeInBounce (x, t*2, 0, c, d) * .5 + b;
		return jQuery.easing.easeOutBounce (x, t*2-d, 0, c, d) * .5 + c*.5 + b;
	}
});

/*
 *
 * TERMS OF USE - EASING EQUATIONS
 * 
 * Open source under the BSD License. 
 * 
 * Copyright © 2001 Robert Penner
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of 
 * conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list 
 * of conditions and the following disclaimer in the documentation and/or other materials 
 * provided with the distribution.
 * 
 * Neither the name of the author nor the names of contributors may be used to endorse 
 * or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 *  COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 *  GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE. 
 *
 */



//下拉菜单延迟插件
(function($){
    $.fn.hoverDelay = function(options){
        var defaults = {
            hoverDuring: 120,
            outDuring: 120,
            hoverEvent: function(){
                $.noop();
            },
            outEvent: function(){
                $.noop();    
            }
        };
        var sets = $.extend(defaults,options || {});
        var hoverTimer, outTimer, that = this;
        return $(this).each(function(){
            $(this).hover(function(){
                clearTimeout(outTimer);
                hoverTimer = setTimeout(function(){sets.hoverEvent.apply(that)}, sets.hoverDuring);
            },function(){
                clearTimeout(hoverTimer);
                outTimer = setTimeout(function(){sets.outEvent.apply(that)}, sets.outDuring);
            });    
        });
    }      
})(jQuery);


/*滚动插件*/
(function($){
$.fn.extend({
	slide:function(opt,callback){
		//定义变量
		if(!opt) var opt={};
		var timerID;
		var btnLeft = $("#"+ opt.left);//向左按钮
		var btnRight = $("#"+ opt.right);//向右按钮
		var _this=this.find("ul");
			liW=opt.li_w//参数：每个li的宽度（包括border,margin,padding,都要算进去）
			li_size=opt.li_size ? parseInt(opt.li_size,10) : parseInt(this.width()/liW,10)  //每次滚动的个数，默认为一屏，即父容器宽度
			speed=opt.speed ? parseInt(opt.speed,10) : 500; //滚动速度，数值越大，速度越慢（毫秒）
			timer=opt.timer //?parseInt(opt.timer,10):3000; //滚动的时间间隔（毫秒）
		if(li_size==0) {li_size=1};
		var leftW=0-li_size*liW;
		//向左滚动函数
		function scrollLeft(){
				btnLeft.unbind("click",scrollLeft); //滑动时先取消点击滑动事件
				btnRight.unbind("click",scrollRight);
				_this.animate({
					marginLeft:leftW
					},speed,function(){ //参数"easeOutExpo"不需要可以删掉，需要的话必选先加载"easing.js"文件
					for(i=1;i<=li_size;i++){
							_this.find("li:first").appendTo(_this);
					}
					_this.css({'margin-left':0});
					btnLeft.bind("click",scrollLeft); //滑动结束后绑定按钮的点击事件
					btnRight.bind("click",scrollRight);
				});
		}
		//向右滚动函数
		function scrollRight(){
				btnLeft.unbind("click",scrollLeft);
				btnRight.unbind("click",scrollRight);
				for(i=1;i<=li_size;i++){
					_this.find("li:last").prependTo(_this);
				}
				_this.css({'margin-left':leftW});
				_this.animate({
					marginLeft:0
					},speed,function(){
					btnLeft.bind("click",scrollLeft);
					btnRight.bind("click",scrollRight);
				});
		}
		//自动播放
		function autoPlay(){
				if(timer) 
				timerID = window.setInterval(scrollLeft,timer);
		};
		//停止自动播放
		function autoStop(){
				if(timer) 
				window.clearInterval(timerID);
		};
		 //鼠标事件绑定
		_this.hover(autoStop,autoPlay).mouseout() //加载完成后自动开始
		btnLeft.click( scrollLeft ).hover(autoStop,autoPlay);//向左鼠标事件绑定
		btnRight.click( scrollRight ).hover(autoStop,autoPlay);//向右鼠标事件绑定
	}       
})
})(jQuery); 




//幻灯片
$(function() {
	var sWidth = $("#focus").width(); //获取焦点图的宽度（显示面积）
	var len = $("#focus ul li").length; //获取焦点图个数
	var index = 0;
	var picTimer;
	
	//以下代码添加数字按钮和按钮后的半透明条，还有上一页、下一页两个按钮
	var btn = "<div class='btnBg'></div><div class='btn'>";
	for(var i=0; i < len; i++) {
		btn += "<span></span>";
	}
	btn += "</div><div class='preNext pre'></div><div class='preNext next'></div>";
	$("#focus").append(btn);
	$("#focus .btnBg").css("opacity",0.5);

	//为小按钮添加鼠标滑入事件，以显示相应的内容
	$("#focus .btn span").click(function() {
		index = $("#focus .btn span").index(this);
		showPics(index);
	}).eq(0).trigger("click");

	//上一页、下一页按钮透明度处理
	$("#focus .preNext").css("opacity",0.2).hover(function() {
		$(this).stop(true,false).animate({"opacity":"0.5"},300);
	},function() {
		$(this).stop(true,false).animate({"opacity":"0.2"},300);
	});

	//上一页按钮
	$("#focus .pre").click(function() {
		index -= 1;
		if(index == -1) {index = len - 1;}
		showPics(index);
	});

	//下一页按钮
	$("#focus .next").click(function() {
		index += 1;
		if(index == len) {index = 0;}
		showPics(index);
	});

	//本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
	$("#focus ul").css("width",sWidth * (len));
	
	//鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
	$("#focus").hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			showPics(index);
			index++;
			if(index == len) {index = 0;}
		},10000); //此4000代表自动播放的间隔，单位：毫秒
	}).trigger("mouseleave");
	
	//显示图片函数，根据接收的index值显示相应的内容
	function showPics(index) { //普通切换
		var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
		$("#focus ul").stop(true,false).animate({"left":nowLeft},300); //通过animate()调整ul元素滚动到计算出的position
		//$("#focus .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
		$("#focus .btn span").stop(true,false).removeClass("on").eq(index).stop(true,false).addClass("on"); //为当前的按钮切换到选中的效果
	}
});

//边栏需求
$(function(){
	$(".tjxq").click(function(){
		$(this).animate({right: '-114px'},200,"easeOutExpo",function(){
			$(".sform").animate({right: '0px'},300,"easeInOutExpo");
		});
	});
	$(".scloce").click(function(){
		$(".sform").animate({right: '-275px'},200,"easeOutExpo",function(){
			$(".tjxq").animate({right: '0px'},200,"easeInOutExpo");
		});
	});
});


//主导航下拉菜单延迟实例化
$(function(){
	$("#laug").each(function(e) {
        var that = $(this);
		that.hoverDelay({
            hoverEvent: function(){
                that.animate({left: '0px'},400,"easeOutExpo");
            }        
        });
		that.hoverDelay({
            outEvent: function(){
                that.animate({left: '-168px'},300,"easeInOutExpo");
            }    
        });
    });
});

/*控制滚动条滚动插件*/
jQuery.fn.scrollTo = function(speed) {
	var targetOffset = $(this).offset().top;
	$('html,body').stop().animate({scrollTop: targetOffset}, speed);
	return this;
}; 
$(function(){
	var sideTop = $("#sideBox").offset().top;
	$(window).scroll(function (){ 
		var offsetTop = sideTop + $(window).scrollTop() +"px";
		$("#sideBox").animate({top : offsetTop },{ duration:250 , queue:false});
	});
	$("#minshow").click(function(){
		var target_id = $(this).attr("href");
		$(target_id).scrollTo(600);
		return false;
	});
	function goTop(){
		var nh=$(window).scrollTop();
		if(nh >= 200){
			$("#goTop").fadeIn();
		}else{
			$("#goTop").fadeOut();
		}
	};
	$(window).scroll(function(){
		goTop();
	});
	$("#goTop").click(function(){
		$("body").scrollTo(400);
		return false;
	});

});

//导航效果
(function($) {
        $.fn.mainmenuslide = function(o) {
                o = $.extend({
                        fx: "linear",
                        speed: 500,
                        click: function() {}
                }, o || {});
                return this.each(function() {
                        var b = $(this),
                            noop = function() {},
                            $back = $('<li class="back"><div class="left"></div></li>').appendTo(b),
                            $li = $("li", this),
                            curr = $("li.current", this)[0] || $($li[0]).addClass("current")[0];
                        $li.not(".back").hover(function() {
                                move(this)
                        }, noop);
                        $(this).hover(noop, function() {
                                move(curr)
                        });
                        $li.click(function(e) {
                                setCurr(this);
                                return o.click.apply(this, [e, this])
                        });
                        setCurr(curr);

                        function setCurr(a) {
                                $back.css({
                                        "left": a.offsetLeft + "px",
                                        "width": a.offsetWidth + "px"
                                });
                                curr = a
                        };

                        function move(a) {
                                $back.each(function() {
                                        $(this).dequeue()
                                }).animate({
                                        width: a.offsetWidth,
                                        left: a.offsetLeft
                                }, o.speed, o.fx)
                        }
                })
        }
})(jQuery);

$(function() {
	$("#nav ul").mainmenuslide({
		fx: "easeOutBack",
		speed: 700,
		click: function(event, menuItem) {
			//return false;
		}
	});
});

$(".box,.box01,.tbox").hover(function(){
	$(this).addClass("wbg");
},function(){
	$(this).removeClass("wbg");
});
