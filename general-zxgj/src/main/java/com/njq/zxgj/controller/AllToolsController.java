package com.njq.zxgj.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("tools")
public class AllToolsController {

	
	/**
	 * 跳转到json格式化页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="jsonDecode",method=RequestMethod.GET)
    public String jsondecode(HttpServletRequest request,Model model){
    	return "zxgj/tools/jsonDecode";
    }
	
	/**
	 * 跳转到js格式化页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="jsDecode",method=RequestMethod.GET)
    public String jsdecode(HttpServletRequest request,Model model){
    	return "zxgj/tools/jsDecode";
    }

	/**
	 * 跳转到sql格式化页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="sqlDecode",method=RequestMethod.GET)
    public String sqlcode(HttpServletRequest request,Model model){
    	return "zxgj/tools/sqlDecode";
    }
	
	/**
	 * 跳转到java格式化页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="javaDecode",method=RequestMethod.GET)
    public String javadecode(HttpServletRequest request,Model model){
    	return "zxgj/tools/developing";
    }
	
	/**
	 * 跳转到css格式化页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="cssDecode",method=RequestMethod.GET)
    public String cssdecode(HttpServletRequest request,Model model){
    	return "zxgj/tools/cssDecode";
    }
	
	/**
	 * 跳转到html格式化页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="htmlDecode",method=RequestMethod.GET)
    public String htmldecode(HttpServletRequest request,Model model){
    	return "zxgj/tools/developing";
    }
	
	/**
	 * 跳转到xml格式化页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="xmlDecode",method=RequestMethod.GET)
    public String xmlDecode(HttpServletRequest request,Model model){
    	return "zxgj/tools/xmlDecode";
    }
	
	 /**
     * 跳转到md5工具页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="md5",method=RequestMethod.GET)
    public String md5(HttpServletRequest request,Model model){
    	return "zxgj/tools/md5";
    }
    
    /**
     * 跳转到rsa页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="rsa",method=RequestMethod.GET)
    public String rsa(HttpServletRequest request,Model model){
    	return "zxgj/tools/developing";
    }
    
    
    /**
     * 跳转到base64页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="base",method=RequestMethod.GET)
    public String base(HttpServletRequest request,Model model){
    	return "zxgj/tools/base64";
    }
    
    /**
     * 跳转到sha页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="sha",method=RequestMethod.GET)
    public String sha(HttpServletRequest request,Model model){
        return "zxgj/tools/sha1";
    }
    
    /**
     * 跳转到js加密页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="jslock",method=RequestMethod.GET)
    public String jslock(HttpServletRequest request,Model model){
        return "zxgj/tools/jslock";
    }
    
    /**
     * 跳转到encrypt加密
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="encrypt",method=RequestMethod.GET)
    public String encrypt(HttpServletRequest request,Model model){
        return "zxgj/tools/encrypt";
    }
    
    /**
     * 跳转到des加密
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="des",method=RequestMethod.GET)
    public String des(HttpServletRequest request,Model model){
        return "zxgj/tools/des";
    }
    
    /**
     * 暗号加密
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="cipherlock",method=RequestMethod.GET)
    public String cipherlock(HttpServletRequest request,Model model){
        return "zxgj/tools/cipherlock";
    }
    
    /**
     * 跳转到自定义锁页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="custlock",method=RequestMethod.GET)
    public String custlock(HttpServletRequest request,Model model){
    	return "zxgj/tools/developing";
    }
    
    /**
     * 跳转到利率计算器页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="llcomplete",method=RequestMethod.GET)
    public String llcomplete(HttpServletRequest request,Model model){
    	return "zxgj/tools/developing";
    }
    
    /**
     * 跳转到房贷计算器页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="fdcomplete",method=RequestMethod.GET)
    public String fdcomplete(HttpServletRequest request,Model model){
    	return "zxgj/tools/developing";
    }
    
    /**
     * 跳转到表达式页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="zzbds",method=RequestMethod.GET)
    public String zzbds(HttpServletRequest request,Model model){
    	return "zxgj/tools/zzbds";
    }
    
    /**
     * 跳转到js调试页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="jsts",method=RequestMethod.GET)
    public String jsts(HttpServletRequest request,Model model){
    	return "zxgj/tools/jsts";
    }
    
    /**
     * 跳转到html调试页面 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="htmlts",method=RequestMethod.GET)
    public String htmlts(HttpServletRequest request,Model model){
    	return "zxgj/tools/htmlts";
    }
    
    /**
     * 加载调试页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="ts",method=RequestMethod.GET)
    public String ts(HttpServletRequest request,Model model){
        return "zxgj/tools/ts";
    }
    
    /**
     * 跳转到身份证调试页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="sfzts",method=RequestMethod.GET)
    public String sfzts(HttpServletRequest request,Model model){
    	return "zxgj/tools/developing";
    }
    
    /**
     * 自定义格式化
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="customHtmlDecode",method=RequestMethod.GET)
    public String customHtmlDecode(HttpServletRequest request,Model model){
    	return "zxgj/tools/customhtmlDecode";
    }
    
    
}
