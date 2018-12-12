package com.njq.admin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.basis.service.impl.BaseBannerService;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseBanner;
import com.njq.common.util.other.PropertyUtil;

@Controller
@RequestMapping("banner")
public class BannerManagerController {

	@Resource
	public BaseBannerService bannerService;

	/**
	 * 跳转到图片管理页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "bannerPage", method = RequestMethod.GET)
	public String jumpToPage(Model model) {
		return "back/sysManage/bannerManager/banner";
	}

	/**
	 * 获取图片列表
	 * 
	 * @param model
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "getBannerList", method = RequestMethod.POST)
	public @ResponseBody PageList<BaseBanner> getChannelList(Model model,
			@RequestParam(required = false, defaultValue = "-1") int page,
			@RequestParam(required = false, defaultValue = "-1") int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取栏目的分页列表
		PageList<BaseBanner> bannerList = bannerService.queryPageList(map, page, size);
		return bannerList;
	}

	/**
	 * 设置是否可用
	 * 
	 * @param isUse
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "setPicUseType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setPicUseType(String isUse, Long[] ids) {
		bannerService.updateBannerApplyStatus(ids, isUse);
		return MessageCommon.getSuccessMap();
	}

	/**
	 * 上传banner图片
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "upBanner", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upBanner(HttpServletRequest req) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(1024 * 400);
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			List<FileItem> items = sfu.parseRequest(req);
			// 区分表单域
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				// isFormField为true，表示这不是文件上传表单域
				if (!item.isFormField()) {
					// 获得文件名
					String fileName = item.getName();
					System.out.println(fileName);
					// 该方法在某些平台(操作系统),会返回路径+文件名/Users/njq
					fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
					String filePlace = PropertyUtil.get("image.place") + "/banner";
					File fileFolder = new File(filePlace);
					// 创建目录
					if (!fileFolder.exists()) {
						fileFolder.mkdirs();
					}
					filePlace += "/" + fileName;
					File file = new File(filePlace);
					if (!file.exists()) {
						item.write(file);
						BaseBanner banner = new BaseBanner();
						banner.setCreateDate(new Date());
						banner.setIsUse(ConstantsCommon.Use_Type.UN_USE);
						banner.setPicPlace(PropertyUtil.get("image.url") + "/uploadImage/banner/" + fileName);
						banner.setName(fileName);
						bannerService.saveObject(banner);
						MessageCommon.getSuccessMap(m);
					} else {
						MessageCommon.getFalseMap(m, "已经存在同名的文件，请修改文件名后再上传");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageCommon.getFalseMap(m, "上传出现异常！");
		}
		return m;
	}

	/**
	 * 删除图片
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delBanner", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delBanner(@RequestParam(required = true) Long[] delIds) {
		// 先查询数据
		List<BaseBanner> bList = bannerService.queryListByIds(delIds);
		// 然后删除文件
		List<Long> delList = new ArrayList<Long>();
		for (BaseBanner b : bList) {
			File file = new File("E:/apache-tomcat-7.0.61/webapps" + b.getPicPlace());
			if (file.exists()) {
				if (!file.delete()) {
					delList.add(b.getId());
				}
			}
		}
		// 然后删除数据
		bannerService.deleteByIds(delIds);
		if (delList.size() > 0) {
			Map<String, Object> m = new HashMap<String, Object>();
			MessageCommon.getFalseMap(m, "存在无法删除的图片");
			return m;
		} else {
			return MessageCommon.getSuccessMap();
		}
	}

	/**
	 * 修改图片
	 * 
	 * @param banner
	 * @return
	 */
	@RequestMapping(value = "updateBanner", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBanner(BaseBanner banner) {

		return null;
	}

}
