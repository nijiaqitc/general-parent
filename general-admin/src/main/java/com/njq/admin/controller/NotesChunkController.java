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
import com.njq.common.model.po.YxlNotesChunk;

@RequestMapping("admin/notes")
@Controller
public class NotesChunkController {

	@Resource
	private YxlNotesChunkService yxlNotesChunkService;
	
	@RequestMapping(value = "chunkList", method = RequestMethod.GET)
	public @ResponseBody PageList<YxlNotesChunk> chunkList(Model model,
			@RequestParam(required = false, defaultValue = "-1") int page,
			@RequestParam(required = false, defaultValue = "-1") int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取栏目的分页列表
		PageList<YxlNotesChunk> chunkList = yxlNotesChunkService.queryList(map, page, size);
		return chunkList;
	}
	
	@RequestMapping("dealChunk")
	public void addNotesChunk(Long id, String name,int index1,int index2) {
		if(id == null) {
			yxlNotesChunkService.saveNotes(name, index1, index2);
		}else {
			yxlNotesChunkService.updateNotes(index1, index2, id, name);
		}
	}
	
	
	
	
	
}
