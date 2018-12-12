package com.njq.admin.controller;

/**
 * 工具接口，集中上传图片。等一系列功能
 */
import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.admin.common.UserCommon;
import com.njq.basis.service.impl.BaseBannerService;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseBanner;
import com.njq.common.model.po.TbkPic;
import com.njq.common.util.date.DateUtil;
import com.njq.common.util.encrypt.Base64Util;
import com.njq.common.util.image.UpPicUtil;
import com.njq.common.util.other.PropertyUtil;
import com.njq.tbk.service.TbkPicService;

@RequestMapping("toolsManage")
@Controller
public class ToolController {

	private static Logger logger = LoggerFactory.getLogger(ToolController.class);
	@Resource
	public BaseBannerService bannerService;
	@Resource
	public TbkPicService tbkpicService;

	@RequestMapping(value = "formUpTest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> formUpTest(HttpServletRequest req) {

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(1024 * 1024);
//        Map<String, Object> m=new HashMap<String, Object>();
		try {
			List<FileItem> items = sfu.parseRequest(req);
//            String fileName;
//            String dd=DateUtil.toDateString8(new Date());
			System.out.println(items);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@RequestMapping(value = "upTest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upTest(HttpServletRequest req) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(1024 * 1024);
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			List<FileItem> items = sfu.parseRequest(req);
			String fileName;
			String dd = DateUtil.toDateString8(new Date());
			// 区分表单域
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				// 如果是文件说明是上传的图片，否则就是网络图片
				if (!item.isFormField()) {
					fileName = UpPicUtil.upBlobPic(item, PropertyUtil.get("image.place") + "/yxl/" + dd);
				} else if (item.getString().startsWith("data")) {
					fileName = UpPicUtil.upBase64Pic(item.getString(), PropertyUtil.get("image.place") + "/yxl/" + dd);
				} else {
					// 上传前先判断当前图片是否是本站的图片
					String url = item.getString();
					if (url.startsWith(PropertyUtil.get("auth.url"))) {
						String s[] = url.split("dialog/emotion");
						if (s.length > 1) {
							fileName = PropertyUtil.get("image.url") + "/uploadImage" + s[1];
						} else {
							// 进行图片下载
							fileName = UpPicUtil.upIntenetPic(item.getString(),
									PropertyUtil.get("image.place") + "/yxl/" + dd);
						}
					} else if (url.startsWith(PropertyUtil.get("image.url"))) {
						fileName = url;
					} else {
						// 进行图片下载
						fileName = UpPicUtil.upIntenetPic(item.getString(),
								PropertyUtil.get("image.place") + "/yxl/" + dd);
					}
				}
				m.put(item.getFieldName(), PropertyUtil.get("image.url") + "/uploadImage/yxl/" + dd + "/" + fileName);
			}
			return m;
		} catch (Exception e) {
			logger.error("上传图片出现异常", e);
		}
		return null;
	}

	@RequestMapping(value = "upTest1", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> upTest1(String urlString, HttpServletRequest req) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(1024 * 400);
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			List<FileItem> items = sfu.parseRequest(req);
			// 区分表单域
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				// 如果是文件说明是上传的图片，否则就是网络图片
				if (!item.isFormField()) {
					m.put(item.getFieldName(), UpPicUtil.upBlobPic(item, PropertyUtil.get("image.place")));
				} else {
					m.put(item.getFieldName(),
							UpPicUtil.upIntenetPic(item.getString(), PropertyUtil.get("image.place")));
				}
			}
			return m;
		} catch (Exception e) {

		}
		return null;
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
			String picName = System.currentTimeMillis() + "";
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
			pic.setCreateDate(new Timestamp(System.currentTimeMillis()));
			tbkpicService.saveTbkpic(pic, map);
			map.put("state", 1);
			map.put("id", pic.getId());
			map.put("place", picPlace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "tttt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> tttt(String base64Data, String ff, HttpServletRequest req) {

		System.out.println("-----");
		return null;
	}
}
