package com.njq.admin.controller;

import com.njq.basis.service.impl.BaseBannerService;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.base.other.TokenCheck;
import com.njq.common.model.po.BaseBanner;
import com.njq.file.load.api.FileLoadService;
import com.njq.file.load.api.model.SaveFileInfo;
import com.njq.file.load.api.model.UpBannerRequestBuilder;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setFileSizeMax(1024 * 400);
        Map<String, Object> m = new HashMap<String, Object>();
        try {
            SaveFileInfo saveFileInfo;
            List<FileItem> items = sfu.parseRequest(req);
            // 区分表单域
            for (int i = 0; i < items.size(); i++) {
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
