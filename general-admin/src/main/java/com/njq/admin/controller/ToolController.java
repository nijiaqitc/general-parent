package com.njq.admin.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 工具接口，集中上传图片。等一系列功能
 */

import com.njq.admin.common.UserCommon;
import com.njq.basis.service.impl.BaseBannerService;
import com.njq.basis.service.impl.BaseFileService;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.base.other.TokenCheck;
import com.njq.common.enumreg.FileType;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.po.BaseBanner;
import com.njq.common.model.po.TbkPic;
import com.njq.common.model.ro.BaseFileSaveRequestBuilder;
import com.njq.file.load.api.FileLoadService;
import com.njq.file.load.api.model.ByteRequestBuilder;
import com.njq.file.load.api.model.SaveFileInfo;
import com.njq.file.load.api.model.UpBannerRequestBuilder;
import com.njq.file.load.api.model.UpBase64RequestBuilder;
import com.njq.file.load.api.model.UpFileInfoRequestBuilder;
import com.njq.tbk.service.TbkPicService;

@RequestMapping("toolsManage")
@Controller
public class ToolController {

    private static Logger logger = LoggerFactory.getLogger(ToolController.class);
    @Resource
    public BaseBannerService bannerService;
    @Resource
    public TbkPicService tbkpicService;
    @Autowired
    private FileLoadService fileLoadService;
    @Autowired
    private BaseFileService  baseFileService;

    @RequestMapping(value = "formUpTest", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> formUpTest(HttpServletRequest req) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setFileSizeMax(1024 * 1024);
        try {
            List<FileItem> items = sfu.parseRequest(req);
            System.out.println(items);
        } catch (Exception e) {
        }
        return null;
    }

    @RequestMapping(value = "upTest", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upTest(HttpServletRequest req) {
    	Map<String, Object> resultMap = new HashMap<>();
    	Map<String, MultipartFile> mms =  ((MultipartHttpServletRequest) req).getFileMap();
    	for (Map.Entry<String, MultipartFile> entry : mms.entrySet()) { 
    		try {
    			SaveFileInfo fileInfo = fileLoadService.upYxlByteFile(ByteRequestBuilder.aByteRequest()
    					.ofType(ChannelType.YXL)
    					.ofName(entry.getValue().getOriginalFilename())
    					.ofData(entry.getValue().getBytes())
    					.ofDebugFlag(TokenCheck.debugType())
    					.build());
    			baseFileService.saveInfo(BaseFileSaveRequestBuilder.aBaseFileSaveRequest()
    					.ofChannel(ChannelType.YXL.getValue())
    					.ofFilePlace(fileInfo.getFilePlace())
    					.ofName(fileInfo.getFileNewName())
    					.ofOldName(entry.getValue().getOriginalFilename())
    					.ofRealPlace(fileInfo.getRealPlace())
    					.ofResultPair(fileInfo.getResultPair())
                        .ofFileType(FileType.IMAGE.getValue())
    					.build());
    			resultMap.put(entry.getKey(), fileInfo.getFilePlace());				
			} catch (Exception e) {
				e.printStackTrace();
	            MessageCommon.getFalseMap(resultMap, "上传出现异常！");
			}
		}
    	
    	Map<String, String[]> strMap =  req.getParameterMap();
    	for(Map.Entry<String, String[]> entry:strMap.entrySet()) {
    		if(entry.getValue() != null) {
    			SaveFileInfo fileInfo=null;
    			if(entry.getValue()[0].startsWith("data")) {
    				fileLoadService.loadBase64(UpFileInfoRequestBuilder.anUpFileInfoRequest()
    						.ofUrl(entry.getValue()[0])
    						.ofType(ChannelType.YXL)
    						.ofDebugFlag(TokenCheck.debugType())
    						.build());
    			}else {
    				if(entry.getValue()[0].contains("njqityun.com")) {
    					continue;
    				}
    				fileInfo = fileLoadService.loadPic(UpFileInfoRequestBuilder.anUpFileInfoRequest()
    						.ofUrl(entry.getValue()[0])
    						.ofType(ChannelType.YXL)
    						.ofDebugFlag(TokenCheck.debugType())
    						.build());      				
    			}
    			baseFileService.saveInfo(BaseFileSaveRequestBuilder.aBaseFileSaveRequest()
    					.ofChannel(ChannelType.YXL.getValue())
    					.ofFilePlace(fileInfo.getFilePlace())
    					.ofName(fileInfo.getFileNewName())
    					.ofOldName(fileInfo.getFileOldName())
    					.ofRealPlace(fileInfo.getRealPlace())
    					.ofResultPair(fileInfo.getResultPair())
                        .ofFileType(FileType.IMAGE.getValue())
    					.build());
    			resultMap.put(entry.getKey(), fileInfo.getFilePlace());
    		}
    	}
    	
    	return resultMap;
    	
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
                SaveFileInfo saveFileInfo = fileLoadService.upYxlFile(UpBannerRequestBuilder
                        .anUpBannerRequest()
                        .ofItem(items.get(i))
                        .ofDebugFlag(TokenCheck.debugType())
                        .build());
                m.put(saveFileInfo.getFileNewName(), saveFileInfo.getFilePlace());
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
            SaveFileInfo saveFileInfo;
            // 区分表单域
            for (int i = 0; i < items.size(); i++) {
                /**
                 * isFormField为true，表示这不是文件上传表单域
                 */
                if (!items.get(i).isFormField()) {
                    saveFileInfo = fileLoadService.upBanner(UpBannerRequestBuilder
                            .anUpBannerRequest()
                            .ofItem(items.get(i))
                            .ofDebugFlag(TokenCheck.debugType())
                            .build());
                    if (saveFileInfo.getFileNewName() != null) {

                        BaseBanner banner = new BaseBanner();
                        banner.setCreateDate(new Date());
                        banner.setIsUse(ConstantsCommon.Use_Type.UN_USE);
                        banner.setPicPlace(saveFileInfo.getFilePlace());
                        banner.setName(saveFileInfo.getFileNewName());
                        bannerService.saveObject(banner);
                        MessageCommon.getSuccessMap(m);
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
            SaveFileInfo fileInfo = fileLoadService.upBase64(UpBase64RequestBuilder
                    .anUpBase64Request()
                    .ofBase64Data(base64Data)
                    .ofDebugFlag(TokenCheck.debugType())
                    .build());
            if (fileInfo.getFilePlace() == null) {
                return MessageCommon.getFalseMap("上传失败！");
            }
            TbkPic pic = new TbkPic();
            pic.setUrl(fileInfo.getFilePlace());
            pic.setName(fileInfo.getFileNewName());
            pic.setCreateBy(UserCommon.getUserId());
            pic.setModiBy(UserCommon.getUserId());
            pic.setCreateDate(new Date());
            tbkpicService.saveTbkpic(pic, map);
            map.put("state", 1);
            map.put("id", pic.getId());
            map.put("place", fileInfo.getFilePlace());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "tttt", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> tttt(String base64Data, String ff, HttpServletRequest req) {
        return null;
    }
}
