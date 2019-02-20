package com.njq.admin.controller;

/**
 * 发表文章接口
 */
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.njq.common.util.string.IdGen;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.admin.common.UserCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.base.redis.RedisCommon;
import com.njq.common.model.po.TbkDoc;
import com.njq.common.model.po.TbkPic;
import com.njq.common.model.po.TbkTip;
import com.njq.common.model.po.TbkType;
import com.njq.common.model.po.YxlDoc;
import com.njq.common.model.po.YxlDocSearch;
import com.njq.common.model.po.YxlTip;
import com.njq.common.model.po.YxlType;
import com.njq.common.util.date.DateUtil;
import com.njq.common.util.encrypt.Base64Util;
import com.njq.common.util.other.PropertyUtil;
import com.njq.tbk.service.TbkDocService;
import com.njq.tbk.service.TbkPicService;
import com.njq.tbk.service.TbkTipService;
import com.njq.tbk.service.TbkTypeService;
import com.njq.yxl.service.YxlDocSearchService;
import com.njq.yxl.service.YxlDocService;

@RequestMapping("issueDoc")
@Controller
public class IssueDocController {

	@Resource
	public TbkDocService tbkdocService;
	@Resource
	public TbkPicService tbkpicService;
	@Resource
	public TbkTipService tbktipService;
	@Resource
	public TbkTypeService tbkTypeService;
	@Resource
	public TbkTypeService tbktypeService;
	@Resource
	public YxlDocService yxlDocService;
	@Resource
	public YxlDocSearchService yxlDocSearchService;

	@RequestMapping(value = "issueType", method = RequestMethod.GET)
	public String issueType(Model model) {
		return "back/issueDoc/issueType";
	}

	/**
	 * 跳转到发表yxl文章页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "yxlIssueDoc", method = RequestMethod.GET)
	public String yxlIssueDoc(Model model) {
		List<YxlType> list = yxlDocService.queryTypeTitleList();
		model.addAttribute("list", list);
		return "back/issueDoc/yxlIssueDoc";
	}

	/**
	 * 修改yxl文章进行跳转
	 * 
	 * @param model
	 * @param docId
	 * @return
	 */
	@RequestMapping(value = "updateYxlDocPage", method = RequestMethod.GET)
	public String updateYxlDocPage(Model model, @RequestParam(required = true) Long docId) {
		YxlDocSearch search = yxlDocSearchService.queryByDocId(docId);
		if (search.getTypeId() != null) {
			YxlType type = yxlDocService.queryTypeById(search.getTypeId());
			model.addAttribute("type", type);
		}
		List<YxlType> list = yxlDocService.queryTypeTitleList();
		YxlDoc doc = yxlDocService.queryById(docId);
		List<YxlTip> tipList = yxlDocService.queryDocTipList(docId);
		String tipName = "";
		for (YxlTip t : tipList) {
			tipName += t.getTipName() + ",";
		}
		model.addAttribute("list", list);
		model.addAttribute("doc", doc);
		model.addAttribute("tipName", tipName);
		model.addAttribute("isUpdate", true);
		model.addAttribute("search", search);
		return "back/issueDoc/yxlIssueDoc";
	}

	/**
	 * 系列文章预览
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "yxlDocView", method = RequestMethod.GET)
	public String yxlDocView(Model model, Long docId) {
		YxlDoc doc = yxlDocService.queryById(docId);
		model.addAttribute("doc", doc);
		return "back/issueDoc/yxlDocView";
	}

	/**
	 * 跳转到后台主页
	 * 
	 * @return 2015-11-2 author njq
	 */
	@RequestMapping(value = "issueDoc", method = RequestMethod.GET)
	public String jumpToPage(Model model) {
		List<TbkType> typeList = tbkTypeService.queryAllTbktype(UserCommon.getUserId());
		List<TbkType> parentList = tbkTypeService.queryParentTbktype(ConstantsCommon.Org_Id.FIRST_ORG_ID);
		model.addAttribute("typeList", typeList);
		model.addAttribute("parentList", parentList);
		return "back/issueDoc/issueDoc";
	}

	/**
	 * 发表文章
	 * 
	 * @param doc    文章对象
	 * @param typeId 类型id
	 * @param picId  图片id
	 * @return
	 */
	@RequestMapping(value = "releaseDoc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> releaseDoc(@Valid TbkDoc doc, Errors errors, @RequestParam(required = true) Long typeId,
			@RequestParam(required = false) Long picId, @RequestParam(required = true) String[] tips,
			@RequestParam(required = true) String docType, @RequestParam(required = true) String picType) {
		Map<String, Object> map = new HashMap<String, Object>();
		/*
		 * 判断用户在1分钟内是否已经发表过文章了，如果发表过了那么不支持发表 避免系统被爆
		 */
		if (RedisCommon.getString("doc" + UserCommon.getUserId()) != null) {
			MessageCommon.getFalseMap(map, "不好意思不能在1分钟内连续发表文章！");
			return map;
		} else {
			RedisCommon.setString("doc" + UserCommon.getUserId(), doc.getTitle(), 1L);
		}
		if (errors.hasErrors()) {
			for (FieldError file : errors.getFieldErrors()) {
				map.put("message", file.getField() + file.getDefaultMessage());
			}
			map.put("state", "0");
			return map;
		}
		if (typeId == null) {
			MessageCommon.getFalseMap(map, "暂不支持默认类型，请选择类型！");
			return map;
		}
		if ("1".equals(picType)) {
			picId = tbkpicService.queryYuanPicForRandom();
		}
		if ("2".equals(picType) && picId == null) {
			MessageCommon.getFalseMap(map, "图片不能为空！");
			return map;
		}
		if (tips == null) {
			MessageCommon.getFalseMap(map, "请输入标签！");
			return map;
		}
		doc.setUserId(UserCommon.getUserId());
		if ("1".equals(docType)) {
			doc.setReprint(2);
		} else {
			doc.setReprint(1);
		}
		doc.setCreateBy(UserCommon.getUserId());
		doc.setModiBy(UserCommon.getUserId());
		doc.setCreateDate(new Date());
		tbkdocService.saveDoc(doc, typeId, picId, tips, UserCommon.getUserId(), map);
		return map;
	}

	/**
	 * 文章预览
	 * 
	 * @return 2016-3-28 author njq
	 */
	@RequestMapping(value = "openPostWindow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> openPostWindow(Model model, String text, String general, String[] tips, String title,
			String picPlace, String type, HttpSession session) {
		session.setAttribute("docViewText", text);
		session.setAttribute("docViewGeneral", general);
		session.setAttribute("docViewTips", tips);
		session.setAttribute("docViewTitle", title);
		session.setAttribute("docViewPicPlace", picPlace);
		session.setAttribute("docViewType", type);
		session.setAttribute("docViewTime", DateUtil.toDateString1(new Date()));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 1);
		map.put("message", "ok");
		return map;
	}

	/**
	 * 上传图片
	 * 
	 * @param base64Data
	 * @return 2016-3-27 author njq
	 */
	@RequestMapping(value = "upPic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> releaseDoc(@RequestParam String base64Data, HttpServletRequest req) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String picName = IdGen.get().toString() + "";
			if (base64Data.split("base64,").length < 2) {
				MessageCommon.getFalseMap(map);
				return map;
			}
			String picPlace = Base64Util.GenerateImage(base64Data.split("base64,")[1], picName,
					PropertyUtil.get("image.place"));
			TbkPic pic = new TbkPic();
			pic.setUrl(PropertyUtil.get("image.url") + picPlace);
			pic.setName(picName);
			pic.setCreateBy(UserCommon.getUserId());
			pic.setModiBy(UserCommon.getUserId());
			pic.setCreateDate(new Date());
			tbkpicService.saveTbkpic(pic, map);
			map.put("state", 1);
			map.put("id", pic.getId());
			map.put("place", picPlace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 发送图片
	 * 
	 * @param base64Data
	 * @return
	 */
	@SuppressWarnings("unused")
	private String sendPic(String base64Data) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod("http://localhost:8080/picService/upPic");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		postMethod.getParams().setContentCharset("utf-8");
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		postMethod.addParameter("base64Data", base64Data);
		try {
			int status = httpClient.executeMethod(postMethod);
			if (status == 200) {
				String str = postMethod.getResponseBodyAsString();
				String state = str.split("&")[0];
				if ("1".equals(state)) {
					return str.split("&")[1];
				} else {
					return "error";
				}
			} else {
				return "error";
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 跳转到修改文章界面
	 * 
	 * @param model
	 * @return 2016年7月4日 author njq
	 */
	@RequestMapping(value = "upDocPage", method = RequestMethod.GET)
	public String upDocPage(Model model, @RequestParam(required = true) Long docId) {
		TbkDoc doc = tbkdocService.queryDocById(docId);
		List<TbkType> typeList = tbkTypeService.queryAllTbktype(UserCommon.getUserId());
		List<TbkType> parentList = tbkTypeService.queryParentTbktype(ConstantsCommon.Org_Id.FIRST_ORG_ID);
		TbkType t = tbkTypeService.queryTbktypeByDocId(docId);
		List<TbkTip> tipList = tbktipService.queryTbktipByDocId(docId);
		TbkPic pic = tbkpicService.queryTbkpicByDocId(docId);
		model.addAttribute("typeList", typeList);
		model.addAttribute("parentList", parentList);
		model.addAttribute("doc", doc);
		model.addAttribute("docType", t);
		model.addAttribute("tipList", tipList);
		model.addAttribute("pic", pic);
		return "back/docManage/updateDoc";
	}

	/**
	 * 修改文章
	 * 
	 * @param doc
	 * @param errors
	 * @param model
	 * @return 2016-4-8 author njq
	 */
	@RequestMapping(value = "updateDoc", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateDoc(@Valid TbkDoc doc, Errors errors,
			@RequestParam(required = true) Long typeId, @RequestParam(required = true) Long picId,
			@RequestParam(required = true) String[] tips) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (errors.hasErrors()) {
			for (FieldError file : errors.getFieldErrors()) {
				map.put("message", file.getField() + file.getDefaultMessage());
			}
			map.put("state", "0");
			return map;
		}
		if (typeId == null) {
			MessageCommon.getFalseMap(map, "暂不支持默认类型，请选择类型！");
			return map;
		}
		if (tips == null) {
			MessageCommon.getFalseMap(map, "请输入标签！");
			return map;
		}
		tbkdocService.updateDoc(doc, typeId, picId, tips, UserCommon.getUserId(), map);
		return map;
	}

	/**
	 * 预览文档
	 * 
	 * @param model
	 * @return 2016-3-30 author njq
	 */
	@RequestMapping(value = "docView", method = RequestMethod.GET)
	public String docView(Model model) {
		return "back/issueDoc/docView";
	}
}
