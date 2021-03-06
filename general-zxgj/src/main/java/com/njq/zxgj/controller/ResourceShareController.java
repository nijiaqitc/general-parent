package com.njq.zxgj.controller;
/**
 * 资源分享页面
 */

import com.njq.basis.service.impl.BaseCodeService;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.interceptor.NeedPwd;
import com.njq.common.base.other.IpUtil;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.base.other.TokenCheck;
import com.njq.common.base.redis.RedisCommon;
import com.njq.common.model.po.BaseCode;
import com.njq.common.model.po.ToolResourceshare;
import com.njq.common.model.vo.ResourceshareVO;
import com.njq.common.util.image.ImageUtil;
import com.njq.common.util.other.CookieExpire;
import com.njq.common.util.other.CookieUtil;
import com.njq.common.util.string.IdGen;
import com.njq.file.load.api.FileLoadService;
import com.njq.file.load.api.model.ResourceShareRequestBuilder;
import com.njq.file.load.api.model.SaveFileInfo;
import com.njq.zxgj.service.ToolResourceShareService;
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("rcShare")
@Controller
public class ResourceShareController {

    @Resource
    public BaseCodeService codeService;
    @Resource
    public ToolResourceShareService resourceShareService;
    @Autowired
    private FileLoadService fileLoadService;

    /**
     * 跳转到资源分享页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public String init(Model model) {
        List<BaseCode> codeList = codeService.queryChildrenCodeList(null, 188l);
        model.addAttribute("codeList", codeList);
        String isRead = CookieUtil.getCookieVaule("isRead");
        model.addAttribute("isRead", isRead);
        return "tbk/resourceShare";
    }

    /**
     * 查询子code类型
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = "queryChildrenCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryChildrenCode(@RequestParam(required = true) Long parentId) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<BaseCode> codeList = codeService.queryChildrenCodeList(null, parentId);
        map.put("codeList", codeList);
        return map;
    }


    /**
     * 查询分享资源
     *
     * @param type1     一级目录
     * @param type2     二级目录
     * @param isDetail  是否是明细查询
     * @param queryText 模糊查询
     * @param page      第几页
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryResourceShare", method = RequestMethod.POST)
    public PageList<ResourceshareVO> queryResourceShare(@RequestParam(defaultValue = "-1") Long type1,
                                                        @RequestParam(defaultValue = "-1") Long type2, @RequestParam(defaultValue = "-1") Long type3,
                                                        @RequestParam(defaultValue = "0") int isDetail, String queryText,
                                                        @RequestParam(defaultValue = "0") int page) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("type1", type1);
        paramMap.put("type2", type2);
        paramMap.put("type3", type3);
        paramMap.put("isDetail", isDetail);
        paramMap.put("queryText", queryText);
        PageList<ResourceshareVO> list = resourceShareService.queryAllRc(paramMap, page, 10);
        return list;
    }


    /**
     * 点击下载按钮进行校验
     *
     * @param request
     * @param downLoad
     * @return
     */
    @RequestMapping(value = "resourceDownLoad", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> resourceDownLoad(HttpServletRequest request, @RequestParam(required = true) Long downLoad) {
        Map<String, Object> map = new HashMap<String, Object>();
        String ip = IpUtil.getIpAddr(request);
        Map<String, Object> mm = new HashMap<String, Object>();
        Object ob = RedisCommon.getString(ip, "ip");
        String isRead = CookieUtil.getCookieVaule("isRead");
        if (!"1".equals(isRead)) {
            MessageCommon.getFalseMap(map, "您尚未接受版权说明，无法下载！！！");
        }
        if (ob == null) {
            mm.put("ip", ip);
            mm.put("downLoadTimes", "0");
            RedisCommon.setHash(ip, mm);
        } else {
            Object cc = RedisCommon.getString(ip, "downLoadTimes");
            int times = Integer.parseInt(cc.toString());
            System.out.println(times);
            if (times > 30) {
                MessageCommon.getFalseMap(map, "下载累了吧，明天再下吧！！！");
            } else {
                //次数+1
                RedisCommon.setHashString(ip, "downLoadTimes", (times + 1) + "");
                MessageCommon.getSuccessMap(map);
                ToolResourceshare rc = resourceShareService.queryRcById(downLoad);
                map.put("jumpUrl", rc.getPlace());
            }
        }
        return map;
    }


    /**
     * 重置下载次数
     *
     * @param request
     * @param token
     */
    @NeedPwd
    @RequestMapping(value = "resetDownLoadTimes", method = RequestMethod.GET)
    @ResponseBody
    public void resetDownLoadTimes(HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        RedisCommon.setHashString(ip, "downLoadTimes", "0");
        Object cc = RedisCommon.getString(ip, "downLoadTimes");
        int times = Integer.parseInt(cc.toString());
        System.out.println("重置下载次数为：" + times);
    }

    /**
     * 保存分享资源
     *
     * @param request
     * @param shareTypeOne
     * @param shareTypeTwo
     * @param shareTypeThree
     * @param selepicOne
     * @param selepicTwo
     * @param selepicThree
     * @param shareUrl
     * @param sharePwd
     * @return
     */
    @RequestMapping(value = "saveShareResource", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveShareResource(HttpServletRequest request, @RequestParam(required = true) Long shareTypeOne
            , @RequestParam(required = true) Long shareTypeTwo, @RequestParam(required = true) Long shareTypeThree,
                                                 @RequestParam(required = true) String selepicOne, @RequestParam(required = true) String selepicTwo, @RequestParam(required = true) String selepicThree,
                                                 @RequestParam(required = true) String shareUrl, String sharePwd,
                                                 @RequestParam(required = true) String resourceDesc) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (selepicOne.split("base64,").length < 2) {
            MessageCommon.getFalseMap(map, "请上传第一张图");
            return map;
        }
        if (selepicTwo.split("base64,").length < 2) {
            MessageCommon.getFalseMap(map, "请上传第二张图");
            return map;
        }
        if (selepicThree.split("base64,").length < 2) {
            MessageCommon.getFalseMap(map, "请上传第三张图");
            return map;
        }

        String picName = IdGen.get().toString();
        SaveFileInfo infoA = fileLoadService.upShareFile(ResourceShareRequestBuilder.aResourceShareRequest()
                .ofShareTypeOne(shareTypeOne)
                .ofShareTypeTwo(shareTypeTwo)
                .ofShareTypeThree(shareTypeThree)
                .ofImgStr(selepicOne.split("base64,")[1])
                .ofName(picName + "-1")
                .ofSkeletonize(picName + "-z-0" + ".jpg")
                .ofWidth(328)
                .ofHeight(196)
                .ofDebugFlag(TokenCheck.debugType())
                .build());
        SaveFileInfo infoB = fileLoadService.upShareFile(ResourceShareRequestBuilder.aResourceShareRequest()
                .ofShareTypeOne(shareTypeOne)
                .ofShareTypeTwo(shareTypeTwo)
                .ofShareTypeThree(shareTypeThree)
                .ofImgStr(selepicOne.split("base64,")[1])
                .ofName(picName + "-1")
                .ofSkeletonize(picName + "-z-1" + ".jpg")
                .ofWidth(860)
                .ofHeight(516)
                .ofDebugFlag(TokenCheck.debugType())
                .build());
        SaveFileInfo infoC = fileLoadService.upShareFile(ResourceShareRequestBuilder.aResourceShareRequest()
                .ofShareTypeOne(shareTypeOne)
                .ofShareTypeTwo(shareTypeTwo)
                .ofShareTypeThree(shareTypeThree)
                .ofImgStr(selepicOne.split("base64,")[1])
                .ofName(picName + "-2")
                .ofSkeletonize(picName + "-z-2" + ".jpg")
                .ofWidth(860)
                .ofHeight(516)
                .ofDebugFlag(TokenCheck.debugType())
                .build());

        SaveFileInfo infoD = fileLoadService.upShareFile(ResourceShareRequestBuilder.aResourceShareRequest()
                .ofShareTypeOne(shareTypeOne)
                .ofShareTypeTwo(shareTypeTwo)
                .ofShareTypeThree(shareTypeThree)
                .ofImgStr(selepicOne.split("base64,")[1])
                .ofName(picName + "-3")
                .ofSkeletonize(picName + "-z-3" + ".jpg")
                .ofWidth(860)
                .ofHeight(516)
                .ofDebugFlag(TokenCheck.debugType())
                .build());
        ToolResourceshare rc = new ToolResourceshare();
        rc.setPicUrlBase(infoA.getFilePlace() + picName);
        rc.setPicUrlA(infoB.getFilePlace() + picName + "-z-1" + ".jpg");
        rc.setPicUrlB(infoC.getFilePlace() + picName + "-z-2" + ".jpg");
        rc.setPicUrlC(infoD.getFilePlace() + picName + "-z-3" + ".jpg");
        rc.setResourceDesc(resourceDesc);
        rc.setCodeIdA(shareTypeOne);
        rc.setCodeIdB(shareTypeTwo);
        rc.setCodeIdC(shareTypeThree.toString());
        rc.setPlace(shareUrl);
        rc.setIsChecked("0");
        rc.setIsLosted("1");
        rc.setPwd(sharePwd);
        rc.setIsStoped("0");
        rc.setCreateDate(new Date());
        rc.setModiBy(2L);
        rc.setModiDate(new Timestamp(System.currentTimeMillis()));
        rc.setCreateBy(2L);
        resourceShareService.saveRc(rc, 2L, map);
        MessageCommon.getSuccessMap(map);
        return map;
    }


    /**
     * 图片转换
     */
    @NeedPwd
    @RequestMapping(value = "tranPic", method = RequestMethod.GET)
    public void tranPic() {
        String path = "c:/mywork/image/uploadImage/shareResources/";
        self(new File(path));
    }

    private void self(File f) {
        if (f.isDirectory()) {
            File[] fList = f.listFiles();
            for (int i = 0; i < fList.length; i++) {
                self(fList[i]);
            }
        } else {
            System.out.println(f.getAbsolutePath());
            System.out.println(f.getName());
            try {
                String c = f.getName().split("\\.")[0].split("-")[1];
                if (!"z".equals(c)) {
                    if ("1".equals(c)) {
                        ImageUtil.scale(f.getAbsolutePath(), f.getParent() + "\\" + f.getName().split("\\.")[0].split("-")[0] + "-z" + "-0.jpg", 328, 196);
                    }
                    ImageUtil.scale(f.getAbsolutePath(), f.getParent() + "\\" + f.getName().split("\\.")[0].split("-")[0] + "-z" + "-" + c + ".jpg", 860, 516);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @RequestMapping(value = "pic", method = RequestMethod.GET)
    public String pic(Model model) {
        List<BaseCode> codeList = codeService.queryChildrenCodeList(null, 188L);
        model.addAttribute("codeList", codeList);
        return "tbk/jcrop";
    }


    /**
     * 阅读版权声明，在cookie中设置一个标记
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "setRead", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setRead(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        CookieUtil.addCookie("isRead", "1", CookieExpire.SEVEN_DAY);
        MessageCommon.getSuccessMap(map);
        return map;
    }
}
