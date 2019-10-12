package com.njq.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.basis.service.impl.YxlNotesChunkService;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.YxlNotesChunk;

@RequestMapping("admin/notes")
@Controller
public class NotesChunkController {

	@Resource
	private YxlNotesChunkService yxlNotesChunkService;
	
	@RequestMapping("chunkManage")
	public String page(Model model) {
		YxlNotesChunk chunk = yxlNotesChunkService.queryLastChunk();
		model.addAttribute("maxIndex", chunk.getIndex1()+1);
		return "back/noteManage/chunkManage";
	}
	
	@RequestMapping(value = "chunkList", method = RequestMethod.POST)
	public @ResponseBody PageList<YxlNotesChunk> chunkList(Model model,
			@RequestParam(required = false, defaultValue = "-1") int page,
			@RequestParam(required = false, defaultValue = "-1") int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取栏目的分页列表
		PageList<YxlNotesChunk> chunkList = yxlNotesChunkService.queryList(map, page, size);
		return chunkList;
	}
	
	@RequestMapping(value="dealChunk",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> addNotesChunk(Long id, String name,Integer index1,Integer index2) {
		if(id == null) {
			yxlNotesChunkService.saveChunk(name, index1, index2);
		}else {
			yxlNotesChunkService.updateChunk(index1, index2, id, name);
		}
		return MessageCommon.getSuccessMap();
	}
	
	
	
	
	
}
