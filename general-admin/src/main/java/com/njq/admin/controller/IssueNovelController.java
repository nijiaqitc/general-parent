package com.njq.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.admin.common.UserCommon;
import com.njq.basis.service.impl.BaseUserService;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseUser;
import com.njq.common.model.po.XsDocDetail;
import com.njq.common.model.po.XsDocGeneralInfo;
import com.njq.common.model.po.XsTitleDetail;
import com.njq.common.model.vo.TitlethcVO;
import com.njq.common.model.vo.xs.XsTitleDetailVO;
import com.njq.xs.service.XsDocDetailService;
import com.njq.xs.service.XsDocDiscussService;
import com.njq.xs.service.XsDocGeneralInfoService;
import com.njq.xs.service.XsDocUserOpService;
import com.njq.xs.service.XsTitleDetailService;

@RequestMapping("admin/novelManage")
@Controller
public class IssueNovelController {

	@Resource
	public XsTitleDetailService titleService;
	@Resource
	public XsDocDiscussService docDiscussService;
	@Resource
	public XsDocUserOpService docUserOpService;
	@Resource
	public XsDocDetailService docDetailService;
	@Resource
	public XsDocGeneralInfoService docGeneralInfoService;
	@Resource
	public BaseUserService baseUserService; 
	/**
	 * 跳转到小说管理界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "novelPage", method = RequestMethod.GET)
	public String jumpToPage(Model model) {
		List<TitlethcVO> list = titleService.queryDocList(0L,false);
		model.addAttribute("list", list);
		return "back/novelArea/novelManage";
	}

	/**
	 * 跳转到编辑文章页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "editNovelArea", method = RequestMethod.GET)
	public String editNovelArea(Model model) {
		return "back/novelArea/testEdit";
	}

	/**
	 * 跳转到文章预览页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "novelView", method = RequestMethod.GET)
	public String novelView(Model model, @RequestParam(required = true) Long docId) {
		XsTitleDetail titleDetail = titleService.queryById(docId);
		if(titleDetail.getDocId() == null) {
			return "grab/noDoc";
		}
		XsDocDetail docdetail = docDetailService.queryById(titleDetail.getDocId());
		model.addAttribute("docdetail", docdetail);
		model.addAttribute("titleDetail", titleDetail);
		return "back/novelArea/novelView";
	}

	/**
	 * 跳转到章节列表页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "novelTitleList", method = RequestMethod.GET)
	public String novelTitleList(Model model, @RequestParam(required = true) Long docId) {
		//查询书本名称
		XsTitleDetail docInfo = titleService.queryById(docId);
		XsTitleDetailVO vo = new XsTitleDetailVO();
		BeanUtils.copyProperties(docInfo, vo);
		Map<String, Object> m = titleService.queryMaxNum(docInfo.getId());
		vo.setMaxTitleIndex(m.get("titleIndex")==null?0:Integer.valueOf(m.get("titleIndex").toString()));
		vo.setMaxOrderIndex(m.get("orderIndex")==null?0:Integer.valueOf(m.get("orderIndex").toString()));
		BaseUser user = baseUserService.queryUserById(2L);
		List<XsTitleDetailVO> list = titleService.queryAllTitleListByDocId(docId);
		XsDocGeneralInfo info = docGeneralInfoService.queryByTitleId(docId);
		model.addAttribute("user", user);
		model.addAttribute("list", list);
		model.addAttribute("info", info);
		model.addAttribute("docInfo", vo);
		return "back/novelArea/novelTitleList";
	}

	/**
	 * 发表文章
	 * 
	 * @param detail
	 * @return
	 */
	@RequestMapping(value = "/saveNovel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveNovel(XsDocDetail detail,String finishStatus, Integer titleIndex) {
		docDetailService.updateObject(detail,finishStatus,titleIndex);
		return MessageCommon.getSuccessMap();
	}

	/**
	 * 添加章节
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/addTitle", method = RequestMethod.POST)
	public String addTitle(XsTitleDetail detail, Model model) {
		XsTitleDetail d = titleService.queryDetalByOrderIndex(detail);
		XsDocDetail docDetail;
		if (d == null) {
			docDetail = docDetailService.saveDoc(detail, UserCommon.getUserId());
		} else {
			detail = d;
			docDetail = docDetailService.queryById(d.getDocId());
		}
		model.addAttribute("docdetail", docDetail);
		model.addAttribute("titleDetail", detail);
		return "back/novelArea/editNovel";
	}
	
	/**
	 * 修改文章
	 * 
	 * @param docId
	 * @return
	 */
	@RequestMapping(value = "/editNovel", method = RequestMethod.GET)
	public String editNovel(@RequestParam(required = true) Long docId, Model model) {
		XsTitleDetail titleDetail = titleService.queryById(docId);
		if(titleDetail.getDocId() == null) {
			return "grab/noDoc";
		}
		XsDocDetail docdetail = docDetailService.queryById(titleDetail.getDocId());
		model.addAttribute("docdetail", docdetail);
		model.addAttribute("titleDetail", titleDetail);
		return "back/novelArea/editNovel";
	}

	/**
	 * 保存卷
	 * 
	 * @param detail
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addJuan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addJuan(XsTitleDetail detail, Model model) {
		if ("0".equals(detail.getTitleIndex())) {
			detail.setTitleIndex(null);
		}
		detail.setUserId(UserCommon.getUserId());
		detail.setCreateDate(new Date());
		titleService.saveTitle(detail);
		return MessageCommon.getSuccessMap();
	}

	
	@RequestMapping(value = "/addNovel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addNovel(String title,String contextDesc,String finishStatus, Model model) {
		titleService.saveNovel(title, contextDesc,UserCommon.getUserId(),finishStatus);
		return MessageCommon.getSuccessMap();
	}
	
	@RequestMapping(value = "/updateNovel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNovel(Long id, String title,String contextDesc,String finishStatus, Model model) {
		titleService.updateNovel(id, title, contextDesc,finishStatus);
		return MessageCommon.getSuccessMap();
	}

	/**
	 * 修改章节
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/updateTitle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateTitle(XsTitleDetail detail) {
		Map<String, Object> map = new HashMap<String, Object>();
//        map.put("index", title.getIndexOne());
//        map.put("title", title.getTitle());
//        map.put("grade", title.getGrade());
//        map.put("desc", title.getContextDesc());
//        titleService.updateTitleById(title);
//        MessageCommon.getSuccessMap(map);
		return map;
	}

	/**
	 * 删除卷
	 * 
	 * @param docId
	 * @return
	 */
	@RequestMapping(value = "/delJuan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delJuan(@RequestParam(required = true) Long docId) {
		titleService.deleteTitleById(docId);
		return MessageCommon.getSuccessMap();
	}

	/**
	 * 修改卷名
	 * 
	 * @param id
	 * @param title
	 * @param contextDesc
	 * @param isShow
	 * @return
	 */
	@RequestMapping(value = "/updateJuan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateJuan(Long id,Integer titleIndex, String title,Integer orderIndex, String contextDesc, String isShow) {
		XsTitleDetail de = new XsTitleDetail();
		de.setId(id);
		de.setTitle(title);
		de.setIsShow(isShow);
		de.setContextDesc(contextDesc);
		de.setOrderIndex(orderIndex);
		de.setTitleIndex(titleIndex);
		titleService.updateTitleById(de);
		return MessageCommon.getSuccessMap();
	}

	/**
	 * 修改显示状态
	 * 
	 * @param id
	 * @param isShow
	 * @return
	 */
	@RequestMapping(value = "/updateShowType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateShowType(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String isShow) {
		titleService.updateShowType(id, isShow);
		return MessageCommon.getSuccessMap();
	}
}
