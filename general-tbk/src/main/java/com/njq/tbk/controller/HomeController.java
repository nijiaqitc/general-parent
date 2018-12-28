/**
 * 首页
 */
package com.njq.tbk.controller;

import com.njq.common.model.po.TbkRecommendDocView;
import com.njq.common.model.po.TbkType;
import com.njq.tbk.service.TbkDocService;
import com.njq.tbk.service.TbkTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("tbk")
public class HomeController {

    @Resource
    public TbkDocService tbkdocService;
    @Resource
    public TbkTypeService tbktypeService;

    /**
     * 加载首页
     *
     * @param model
     * @return 2016-4-11
     * author njq
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toIndex(Model model, String ismob) {
        List<TbkRecommendDocView> viewList = tbkdocService.queryRecommendDoc(new HashMap<String, Object>(), 1, 3);
        List<TbkType> littleDivTextList = tbktypeService.queryLittleDivText(1, 3);
        model.addAttribute("recommendDocList", viewList);
        model.addAttribute("littleDivTextList", littleDivTextList);
        model.addAttribute("ismob", ismob);
        return "tbk/home";
    }

    /**
     * 查询首页的小文章
     *
     * @param page
     * @return 2016-4-11
     * author njq
     */
    @RequestMapping(value = "queryLittleDoc", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryLittleDoc(int page) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<TbkType> littleDivTextList = tbktypeService.queryLittleDivText(page, 3);
        map.put("littleDivTextList", littleDivTextList);
        return map;
    }

    /**
     * 快速搜索初始化
     *
     * @param model
     * @return 2016-4-11
     * author njq
     */
    @RequestMapping(value = "fastSearchInit", method = RequestMethod.GET)
    public String fastSearchInit(Model model, String searchValue) {
        model.addAttribute("searchValue", searchValue);
        return "tbk/fastSearch";
    }

    /**
     * 技术成长初始化
     *
     * @param model
     * @return 2016-4-11
     * author njq
     */
    @RequestMapping(value = "technologyUpInit", method = RequestMethod.GET)
    public String technologyUpInit(Model model) {
        return "tbk/technologyUp";
    }

    /**
     * 照片墙初始化
     *
     * @param model
     * @return 2016-4-11
     * author njq
     */
    @RequestMapping(value = "picWallInit", method = RequestMethod.GET)
    public String picWallInit(Model model) {
        return "tbk/picWall";
    }

    /**
     * 网站地图初始化
     *
     * @param model
     * @return 2016-4-11
     * author njq
     */
    @RequestMapping(value = "webMapInit", method = RequestMethod.GET)
    public String webMapInit(Model model) {
        return "tbk/webMap";
    }

    /**
     * 关于我初始化
     *
     * @param model
     * @return 2016-4-11
     * author njq
     */
    @RequestMapping(value = "aboutMeInit", method = RequestMethod.GET)
    public String aboutMeInit(Model model) {
        return "tbk/aboutMe";
    }


    @RequestMapping(value = "about", method = RequestMethod.GET)
    public String jumpToPage(Model model) {
        return "front/about";
    }

    /**
     * 跳转到快速发布文章页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "fastNote", method = RequestMethod.GET)
    public String fastNoteInit(Model model, String token) {
        if (!"qiqi".equals(token)) {
            return "";
        }
        List<TbkType> typeList = tbktypeService.queryTbktypeByCreatedBy(2l);
        model.addAttribute("typeList", typeList);
        return "tbk/fastNote";
    }

}
