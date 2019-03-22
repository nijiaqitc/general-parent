package com.njq.admin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.njq.basis.service.impl.BaseBannerService;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.base.other.TokenCheck;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.po.BaseBanner;
import com.njq.file.load.api.FileLoadService;
import com.njq.file.load.api.model.ByteRequestBuilder;
import com.njq.file.load.api.model.SaveFileInfo;

@Controller
@RequestMapping("banner")
public class BannerManagerController {

    @Resource
    public BaseBannerService bannerService;
    @Autowired
    private FileLoadService fileLoadService;

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
    public @ResponseBody
    PageList<BaseBanner> getChannelList(Model model,
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
    	
    	Map<String, Object> resultMap = new HashMap<>();
    	Map<String, MultipartFile> mms =  ((MultipartHttpServletRequest) req).getFileMap();
    	for (Map.Entry<String, MultipartFile> entry : mms.entrySet()) { 
    		try {
    			SaveFileInfo saveFileInfo = fileLoadService.upBannerByteFile(ByteRequestBuilder.aByteRequest()
    					.ofType(ChannelType.SBANNER)
    					.ofName(entry.getValue().getOriginalFilename())
    					.ofData(entry.getValue().getBytes())
    					.ofDebugFlag(TokenCheck.debugType())
    					.build());
    			if (saveFileInfo.getFileNewName() != null) {
                    BaseBanner banner = new BaseBanner();
                    banner.setCreateDate(new Date());
                    banner.setIsUse(ConstantsCommon.Use_Type.UN_USE);
                    banner.setPicPlace(saveFileInfo.getFilePlace());
                    banner.setName(saveFileInfo.getFileNewName());
                    bannerService.saveObject(banner);
                    MessageCommon.getSuccessMap(resultMap);
                }
			} catch (Exception e) {
				e.printStackTrace();
	            MessageCommon.getFalseMap(resultMap, "上传出现异常！");
			}
		}
    	return resultMap;
    }

    /**
     * 删除图片
     *
     * @param delIds
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
