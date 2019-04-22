package com.njq.admin.controller;

/**
 * 笔记管理
 */
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.njq.common.base.interceptor.NeedPwd;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.admin.common.UserCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.YxlColumnDefine;
import com.njq.common.model.po.YxlColumnStore;
import com.njq.common.model.po.YxlFolder;
import com.njq.common.model.po.YxlNote;
import com.njq.common.model.po.YxlNoteGeneral;
import com.njq.common.model.po.YxlTypeName;
import com.njq.common.util.string.StringUtil;
import com.njq.yxl.service.YxlColumnService;
import com.njq.yxl.service.YxlNoteService;

@RequestMapping("admin/note")
@Controller
public class NoteManageController {

	@Resource
	public YxlNoteService yxlNoteService;
	@Resource
	public YxlColumnService yxlColumnService;

	/**
	 * 跳转到后台笔记管理页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "noteManage", method = RequestMethod.GET)
	public String jumpToPage(Model model) {
		List<YxlNoteGeneral> gList = yxlNoteService.queryTitleList();
		model.addAttribute("gList", gList);
		return "back/noteManage/noteManage";
	}

	/**
	 * 查询对应文件夹下的所有文件
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "queryGenList", method = RequestMethod.POST)
	@ResponseBody
	public List<YxlNoteGeneral> queryGenList(Long id) {
		return yxlNoteService.queryTitleListByFolder(id);
	}

	@RequestMapping(value = "lockCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> lockCheck(Long id, String pwd, HttpServletRequest req) {
		if (yxlNoteService.queryFolderCount(id, pwd) == 1) {
			req.getSession().setAttribute("folderUnLock", id);
			return MessageCommon.getSuccessMap();
		} else {
			return MessageCommon.getFalseMap("密码错误或不存在文件夹");
		}
	}

	@RequestMapping(value = "delFolder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delFolder(@RequestParam(required = true) Long id) {
		int num = yxlNoteService.queryCount("folder_id", id.toString());
		List<YxlTypeName> list = yxlColumnService.queryNameList(id);
		if (num > 0 || list.size() > 0) {
			return MessageCommon.getFalseMap("文件夹中存在文件，不能删除!");
		} else {
			yxlNoteService.deleteFolderById(id);
			return MessageCommon.getSuccessMap();
		}
	}

	/**
	 * 保存文件夹
	 * 
	 * @param folder
	 * @return
	 */
	@RequestMapping(value = "saveFolder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveFolder(YxlFolder folder) {
		yxlNoteService.saveFolder(folder);
		return MessageCommon.getSuccessMap();
	}

	/**
	 * 保存笔记
	 * 
	 * @param note
	 * @return
	 */
	@RequestMapping(value = "saveNote", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveNote(YxlNote note, String general, Long folderId) {
		note.setUserId(UserCommon.getUserId());
		note.setStatus(ConstantsCommon.Del_Status.YES);
		note.setCreateDate(new Date());
		if (StringUtil.IsEmpty(note.getTitle())) {
			note.setTitle("无标题");
		}
		YxlNoteGeneral gen = yxlNoteService.saveObject(note, general, folderId);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("note", note);
		m.put("gen", gen);
		MessageCommon.getSuccessMap(m);
		return m;
	}

	/**
	 * 阅读文章
	 * @param docId
	 * @param folderId
	 * @param req
	 * @return
	 */
	@NeedPwd
	@RequestMapping(value = "readNote", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> readNote(Long docId, Long folderId, HttpServletRequest req) {
		if (UserCommon.getUserId() !=null || req.getSession().getAttribute("folderUnLock").equals(folderId)) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("note", yxlNoteService.queryById(docId));
			return m;
		} else {
			return MessageCommon.getFalseMap("没有权限");
		}
	}

	/**
	 * 删除笔记
	 * 
	 * @param docId
	 * @return
	 */
	@RequestMapping(value = "delNote", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delNote(Long docId) {
		yxlNoteService.deleteById(docId);
		return MessageCommon.getSuccessMap();
	}

	@RequestMapping("demo")
	public String test() {
		return "back/demo2";
	}

	@RequestMapping(value = "saveDefine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveDefine(Long id, String name, String... colums) {
		yxlColumnService.saveDefine(Arrays.asList(colums), name, id);
		return MessageCommon.getSuccessMap();
	}

	/**
	 * 保存记录
	 */
	@RequestMapping(value = "saveStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveStore(Long id, Long recordType, String strs) {
		String[] st = strs.split("\\|");
		YxlColumnStore store = new YxlColumnStore();
		store.setId(id);
		yxlColumnService.saveStore(store, recordType, st);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", store.getId());
		MessageCommon.getSuccessMap(map);
		return map;
	}

	/**
	 * 查询类型集合
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "queryTypeNameList", method = RequestMethod.POST)
	@ResponseBody
	public List<YxlTypeName> queryTypeNameList(Long id) {
		return yxlColumnService.queryNameList(id);
	}

	/**
	 * 查询记录集合
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "queryTypeRecordList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTypeRecordList(Long id,
			@RequestParam(required=false,defaultValue="1") Integer page, 
			@RequestParam(required=false,defaultValue="10") Integer size) {
		List<YxlColumnDefine> defineList = yxlColumnService.queryDefineList(id);
		PageList<YxlColumnStore> pgList = yxlColumnService.queryStorePage(id, page, size);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storeList", pgList.getList());
		map.put("total", pgList.getTotal());
		map.put("defineList", defineList);
		MessageCommon.getSuccessMap(map);
		return map;
	}

	/**
	 * 删除记录
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delStore(Long id) {
		if (yxlColumnService.deleteStore(id) == 1) {
			return MessageCommon.getSuccessMap();
		} else {
			return MessageCommon.getFalseMap("删除失败，请刷新页面！");
		}
	}

	@RequestMapping(value = "delRecordType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delRecordType(Long id) {
		return yxlColumnService.deleteDefine(id);
	}

}
