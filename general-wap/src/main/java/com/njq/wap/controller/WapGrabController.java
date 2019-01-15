package com.njq.wap.controller;

import java.util.List;

import javax.annotation.Resource;

import com.njq.common.model.vo.grab.GrabTypeInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.common.base.constants.ChannelType;
import com.njq.common.model.vo.grab.GrabTitleVO;
import com.njq.grab.service.impl.GrabService;
import com.njq.wap.service.WapGrabService;

@RequestMapping("wap")
@Controller
public class WapGrabController {
	@Resource
    public WapGrabService wapGrabService;
	@Resource
    public GrabService grabService;
	
	@RequestMapping(value = "/grab/grabList", method = RequestMethod.GET)
    public String noteList(Model model) {
        return "wap/grab/grabList";
    }
	
	@RequestMapping(value = "/grab/noteList", method = RequestMethod.POST)
	@ResponseBody
    public List<GrabTypeInfo> noteList(Model model, @RequestParam(required=false) Long docId, String channel) {
		return wapGrabService.makeTitleVo(docId, ChannelType.getChannelType(channel));
	}
	
	@RequestMapping(value = "/grab/{noteId}", method = RequestMethod.GET)
    public String grab(Model model, @PathVariable(value = "noteId") Long noteId) {
        model.addAttribute("docViewText", grabService.queryById(noteId));
        return "wap/grab/grabnote";
    }

	
	
}
