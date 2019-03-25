package com.njq.yxl.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseBanner;
import com.njq.common.model.po.YxlDocSearch;
import com.njq.common.model.po.YxlTip;
import com.njq.common.model.vo.DocVO;
import com.njq.common.util.other.PropertyUtil;
import com.njq.file.load.api.FileLoadService;
import com.njq.file.load.api.model.DownLoadFileRequestBuilder;
import com.njq.file.load.api.model.ReBackFileInfo;
import com.njq.yxl.cache.BannerCacheReader;
import com.njq.yxl.service.YxlDocSearchService;
import com.njq.yxl.service.YxlDocService;

@Controller
public class GjHomeController {
    private static final Logger logger = LoggerFactory.getLogger(GjHomeController.class);
    @Resource
    private YxlDocSearchService yxlDocSearchService;
    @Resource
    private YxlDocService yxlDocService;
    @Autowired
    private BannerCacheReader bannerCacheReader;
    @Autowired
    private FileLoadService fileLoadService;

    /**
     * 跳转到首页
     *
     * @param model
     * @param ismob
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toIndex(Model model, String ismob) {
//    	List<YxlDocSearch> xlList=yxlDocSearchService.queryTitleYxlList(true);
//    	List<DocVO> docList=yxlDocSearchService.queryTitlelist();
//        
//        
//        model.addAttribute("doclist",docList);
//        model.addAttribute("xlDoclist",xlList);
//        model.addAttribute("ismob", ismob);
        return "zxgj/index";
    }

    @RequestMapping(value = "index2", method = RequestMethod.GET)
    public String toIndex2(Model model, String ismob) {
    	List<YxlDocSearch> xlList=yxlDocSearchService.queryTitleYxlList(true);
    	List<DocVO> docList=yxlDocSearchService.queryTitlelist();
        model.addAttribute("doclist",docList);
        model.addAttribute("xlDoclist",xlList);
        model.addAttribute("ismob", ismob);
        return "zxgj/index2";
    }
    
    /**
     * 获取banner图片
     *
     * @return
     */
    @RequestMapping(value = "getShowBannerList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getShowBannerList() {
        Map<String, Object> m = new HashMap<String, Object>();
        List<BaseBanner> bannerList = bannerCacheReader.get("indexBanner");
        for (BaseBanner b : bannerList) {
            b.setPicPlace(b.getPicPlace());
        }
        m.put("bannerList", bannerList);
        MessageCommon.getSuccessMap(m);
        return m;
    }


    /**
     * 跳转到关于我们
     *
     * @param model
     * @param ismob
     * @return
     */
    @RequestMapping(value = "aboutUs", method = RequestMethod.GET)
    public String aboutUs(Model model, String ismob) {
        model.addAttribute("ismob", ismob);
        return "zxgj/aboutMe";
    }


    /**
     * 跳转到富文本编辑器下载区域
     *
     * @param model
     * @param ismob
     * @return
     */
    @RequestMapping(value = "editor", method = RequestMethod.GET)
    public String editor(Model model, String ismob, String modelStyle) {
        model.addAttribute("ismob", ismob);
        model.addAttribute("modelStyle", modelStyle);
        return "zxgj/editor";
    }

    /**
     * 跳转到在线工具区域
     *
     * @param model
     * @param ismob
     * @return
     */
    @RequestMapping(value = "tools", method = RequestMethod.GET)
    public String tools(Model model, String ismob) {
        model.addAttribute("ismob", ismob);
        return "zxgj/tools";
    }

    /**
     * 跳转到文章列表
     *
     * @param model
     * @param ismob
     * @return
     */
    @RequestMapping(value = "docList", method = RequestMethod.GET)
    public String docList(Model model, String ismob, String tipName) {
        List<YxlTip> tipList = yxlDocService.queryTipList();
        try {
            model.addAttribute("tipName", tipName);
            model.addAttribute("tipList", tipList);
            model.addAttribute("ismob", ismob);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "zxgj/docList";
    }


    @RequestMapping(value = "knowledgeDoc", method = RequestMethod.GET)
    public String knowledgeDoc(Model model, String docId) {
        model.addAttribute("ismob", docId);
        return "zxgj/knowledgeDoc";
    }

    /**
     * 引用顶部公共部分
     *
     * @return
     */
    @RequestMapping(value = "top", method = RequestMethod.GET)
    public String top() {
        return "zxgj/top";
    }

    /**
     * 引用底部公共部分
     *
     * @return
     */
    @RequestMapping(value = "bottom", method = RequestMethod.GET)
    public String bottom() {
        return "zxgj/bottom";
    }


    /**
     * 跳转到文档
     *
     * @return
     */
    @RequestMapping(value = "editDoc", method = RequestMethod.GET)
    public String editDoc(Model model) {
        List<Map<String, Object>> listType = yxlDocSearchService.queryListType(71);
        List<Map<String, Object>> listTitle = yxlDocSearchService.queryListTitle();
        model.addAttribute("titleList", listType);
        model.addAttribute("nameList", listTitle);
        System.out.println(listType);
        return "zxgj/editDoc";
    }

    /**
     * 下载文件
     *
     * @param request
     * @return
     */
    @SuppressWarnings("resource")
    @RequestMapping(value = "downLoadEditor", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downLoadEditor(HttpServletRequest request) {
        return downLoad(PropertyUtil.get("downLoadFile"));
    }


    @RequestMapping(value = "{shortname}/{dfolder}/downLoadFile", method = RequestMethod.GET)
    public void downLoadFile(String file,@PathVariable(value = "shortname") String shortname,
                             @PathVariable(value = "dfolder") String dfolder,
                             HttpServletRequest request, HttpServletResponse response) {
    	ReBackFileInfo fileInfo = fileLoadService.readFile(DownLoadFileRequestBuilder
    			.aDownLoadFileRequest()
    			.ofFile(file)
    			.ofDfolder(dfolder)
    			.ofShortname(shortname)
    			.build());
    	if(fileInfo.getName()==null) {
    		return;
    	}
        BufferedOutputStream bous = null;
        try {
            response.reset();
            response.addHeader("Content-Disposition", "attchement;filename=" +  new String(fileInfo.getName().getBytes("UTF-8"),"ISO-8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            bous = new BufferedOutputStream(outputStream);
            response.setContentType("application/octet-stream");
            outputStream.write(fileInfo.getBody());
            outputStream.close();
        } catch (Exception e) {
            logger.error("下载文件出错：" + e.getMessage());
        } finally {
            try {
                if (bous != null) {
                    bous.close();
                }
            } catch (IOException e) {
                logger.error("关闭流出错：" + e.getMessage());
            }
        }
    }


    private ResponseEntity<byte[]> downLoad(String url) {
        try {
            File file = new File(url);
            byte[] body = null;
            InputStream is = new FileInputStream(file);
            body = new byte[is.available()];
            is.read(body);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attchement;filename=" + file.getName());
            HttpStatus statusCode = HttpStatus.OK;
            ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
            is.close();
            return entity;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
