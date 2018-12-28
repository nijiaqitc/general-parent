package com.njq.wap.controller;

import com.njq.basis.service.impl.BaseCodeService;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseCode;
import com.njq.common.model.po.ToolFeeling;
import com.njq.common.util.encrypt.Base64Util;
import com.njq.wap.service.ToolFeelingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("feel")
@Controller
public class FeelController {

    @Resource
    public ToolFeelingService feelService;
    @Resource
    public BaseCodeService codeService;

    /**
     * 跳转到页面
     *
     * @return
     */
    @RequestMapping("jump")
    public String jumpToPage(Model model) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        PageList<BaseCode> pageCode = codeService.queryChildrenCodeList(paramMap, 3L, 1, 20);
        model.addAttribute("codeList", pageCode.getList());
        return "wap/feeling";
    }

    /**
     * 查询心情
     *
     * @param page
     * @param size
     * @param textType
     * @param token    标志，只有知道此标志的才进行查询数据库
     * @return
     */
    @RequestMapping("feel")
    @ResponseBody
    public PageList<ToolFeeling> queryList(@RequestParam(required = false, defaultValue = "1") int page,
                                           @RequestParam(required = false, defaultValue = "10") int size,
                                           @RequestParam(required = false, defaultValue = "2") String textType, String token) {
        if ("qss".equals(token)) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("textType", textType);
            paramMap.put("userId", 2L);
            PageList<ToolFeeling> pageList = feelService.queryFeelings(paramMap, page, size);
            List<ToolFeeling> list = pageList.getList();
            for (ToolFeeling feel : list) {
                if ("1".equals(feel.getIsLock())) {
                    feel.setPlace(Base64Util.getFromBase64(feel.getPlace()));
                    feel.setText(Base64Util.getFromBase64(feel.getText()));
                }
            }
            return pageList;
        } else {
            return new PageList<ToolFeeling>();
        }
    }

    /**
     * 添加心情
     *
     * @return
     */
    @RequestMapping("addFeel")
    @ResponseBody
    public Map<String, Object> addFeel(ToolFeeling feel) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] check = feel.getText().split("&&");
        if (check.length > 1) {
            if ("qss".equals(check[1])) {
                feel.setUserId(2L);
                feel.setCreateTime(new Date());
                if ("2".equals(feel.getTextType())) {
                    feel.setIsLock("2");
                    feel.setPlace("");
                    feel.setFeelType("");
                }
                /*
                 * 进行base64位加密
                 */
                if ("1".equals(feel.getIsLock())) {
                    feel.setText(Base64Util.getBase64(check[0]));
                    feel.setPlace(Base64Util.getBase64(feel.getPlace()));
                }
                feelService.saveFeelings(feel, 2L, map);
                MessageCommon.getSuccessMap(map);
            } else {
                MessageCommon.getFalseMap(map, "当前功能暂时不对外人开放！");
            }
        } else {
            MessageCommon.getFalseMap(map, "当前功能暂时不对外人开放！");
        }
        return map;
    }
}
