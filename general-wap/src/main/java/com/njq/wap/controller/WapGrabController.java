package com.njq.wap.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.njq.basis.service.impl.BaseTipService;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.GrabDoc;
import com.njq.common.model.vo.grab.GrabTypeInfo;
import com.njq.common.util.string.StringUtil;
import com.njq.grab.service.impl.GrabService;
import com.njq.wap.service.WapGrabService;

@RequestMapping("wap")
@Controller
public class WapGrabController {
    @Resource
    public WapGrabService wapGrabService;
    @Resource
    public GrabService grabService;
    @Resource
    public BaseTitleService baseTitleService;
    @Resource
    public BaseTipService baseTipService;

    @RequestMapping(value = "/grab/grabList", method = RequestMethod.GET)
    public String noteList(Model model) {
        model.addAttribute("grabList", wapGrabService.makeTitleVo(null, null));
        return "wap/grab/grabList";
    }

    @RequestMapping(value = "/grab/grabInfo", method = RequestMethod.GET)
    public String grabInfo(Model model) {
        return "wap/grab/grabInfo";
    }

    @RequestMapping(value = "/grab/noteList", method = RequestMethod.POST)
    @ResponseBody
    public List<GrabTypeInfo> noteList(Model model, @RequestParam(required = false) Long docId, String channel) {
        return wapGrabService.makeTitleVo(docId, ChannelType.getChannelType(channel));
    }

    @RequestMapping(value = "/grab/{noteId}", method = RequestMethod.GET)
    public String grab(Model model, @PathVariable(value = "noteId") Long noteId) {
        GrabDoc doc = grabService.readDoc(noteId);
        if(doc == null) {
        	return "wap/grab/abc";
        }
        BaseTitle title = baseTitleService.getTitleByDocId(doc.getId());
        model.addAttribute("docViewText", doc);
        if (!StringUtils.isEmpty(title.getTips())) {
            model.addAttribute("tipList", baseTipService.getTipListByIds(title.getTips()));
        }
        return "wap/grab/grabnote";
    }

    @RequestMapping(value = "/grab/grabStarList", method = RequestMethod.GET)
    public String grabStarList(Model model) {
        model.addAttribute("grabList", wapGrabService.conver(baseTitleService.getStarTitleList()));
        return "wap/grab/grabStarList";
    }


    @RequestMapping(value = "/grab/grabNewLoadList", method = RequestMethod.GET)
    public String grabNewLoadList(Model model) {
        model.addAttribute("grabList", wapGrabService.conver(baseTitleService.getNewLoadTitleList()));
        return "wap/grab/grabNewLoadList";
    }

    @RequestMapping(value = "/grab/grabSearchList", method = RequestMethod.GET)
    public String grabSearchList(Model model, String searchValue) {
        model.addAttribute("grabList", wapGrabService.conver(baseTitleService.getSearchTitleList(StringUtil.isEmpty(searchValue) ? null : searchValue.trim().split(" "))));
        return "wap/grab/grabStarList";
    }
}
