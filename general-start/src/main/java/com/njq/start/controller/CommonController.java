package com.njq.start.controller;

/**
 * 公共类，如果url不属于任何板块，就放此处
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {

	/**
	 * 跳转到公共头部(js 或 css)
	 * 
	 * @return 2015-10-30 author njq
	 */
	@RequestMapping("head")
	public String getHead() {
		return "back/public/head";
	}

	/**
	 * 跳转到公共尾部(js 或 css)
	 * 
	 * @return 2015-10-30 author njq
	 */
	@RequestMapping("foot")
	public String getFoot() {
		return "back/public/foot";
	}

	/**
	 * 跳转到头部菜单
	 * 
	 * @return 2015-11-2 author njq
	 */
	@RequestMapping("top")
	public String getTop() {
		return "back/public/top";
	}

	/**
	 * 跳转到左边菜单
	 * 
	 * @return 2015-11-2 author njq
	 */
	@RequestMapping("left")
	public String getLeft() {
		return "back/public/left";
	}

	/**
	 * 跳转到底部菜单
	 * 
	 * @return 2015-11-2 author njq
	 */
	@RequestMapping("boom")
	public String getBoom() {
		return "back/public/boom";
	}

	/**
	 * 跳转到前台底部js
	 * 
	 * @return 2015-11-3 author njq
	 */
	@RequestMapping("frontBoomJs")
	public String frontBoomJs() {
		return "back/back/public/front-boom-js";
	}

	/**
	 * 跳转到前台底部
	 * 
	 * @return 2015-11-3 author njq
	 */
	@RequestMapping("frontBoom")
	public String frontBoom() {
		return "back/public/front-boom";
	}

	/**
	 * 跳转到前台顶部js
	 * 
	 * @return 2015-11-3 author njq
	 */
	@RequestMapping("frontHeadJs")
	public String frontHeadJs() {
		return "back/public/front-head-js";
	}

	/**
	 * 跳转到前台顶部
	 * 
	 * @return 2015-11-3 author njq
	 */
	@RequestMapping("frontHead")
	public String frontHead() {
		return "back/public/front-head";
	}

	/**
	 * 跳转到公用的jsp页面
	 * 
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping("publicJsp")
	public String publicJsp() {
		return "back/public/publicJsp";
	}

	/**
	 * 如果不是谷歌浏览器提示系统不支持
	 * 
	 * @return
	 */
	@RequestMapping("chromeTip")
	public String chromeTip() {
		return "back/public/chromeTip";
	}

	/**
	 * 自定义的通用顶部
	 * 
	 * @return
	 */
	@RequestMapping("commonTop")
	public String commonTop() {
		return "back/public/commonTop";
	}

	/**
	 * 自定义的通用左边菜单
	 * 
	 * @return
	 */
	@RequestMapping("commonLeft")
	public String commonLeft() {
		return "back/public/commonLeft";
	}

	/**
	 * 自定义的通用底部
	 * 
	 * @return
	 */
	@RequestMapping("commonBottom")
	public String commonBottom() {
		return "back/public/commonBottom";
	}

	/**
	 * 顶部引用
	 * 
	 * @return
	 */
	@RequestMapping("commonTopLink")
	public String commonTopLink() {
		return "back/public/commonTopLink";
	}

	/**
	 * 底部引用
	 * 
	 * @return
	 */
	@RequestMapping("commonBottomLink")
	public String commonBottomLink() {
		return "back/public/commonBottomLink";
	}

	
	@RequestMapping("test11")
	public String test11() {
		return "test";
	}
}
