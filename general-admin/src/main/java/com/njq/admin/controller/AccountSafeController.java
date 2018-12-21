package com.njq.admin.controller;

/**
 * 账户安全接口
 */

import com.njq.admin.common.UserCommon;
import com.njq.basis.service.impl.BaseUserService;
import com.njq.common.base.email.EmailSender;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseUser;
import com.njq.common.util.other.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("accountSafe")
@Controller
public class AccountSafeController {

    @Resource
    public EmailSender emailSender;

    @Resource
    private BaseUserService userService;

    /**
     * 跳转到账户安全页面
     *
     * @return 2015-11-2 author njq
     */
    @RequestMapping(value = "accountSafe", method = RequestMethod.GET)
    public String jumpToPage(Model model) {
        return "back/accountSafe/accountSafe";
    }

    /**
     * 跳转到修改密码界面
     *
     * @param model
     * @return 2016年7月6日 author njq
     */
    @RequestMapping(value = "modipwd", method = RequestMethod.GET)
    public String modipwd(Model model) {
        return "back/accountSafe/modipwd";
    }

    /**
     * 跳转到修改手机号码界面
     *
     * @param model
     * @return 2016年7月7日 author njq
     */
    @RequestMapping(value = "moditel", method = RequestMethod.GET)
    public String moditel(Model model) {
        return "back/accountSafe/moditel";
    }

    /**
     * 跳转到修改email界面
     *
     * @param model
     * @return 2016年7月7日 author njq
     */
    @RequestMapping(value = "modiemail", method = RequestMethod.GET)
    public String modiemail(Model model) {
        return "back/accountSafe/modiemail";
    }

    /**
     * 发送邮件
     *
     * @param model
     * @return 2016年7月6日 author njq
     */
    @RequestMapping(value = "sendCode", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendCode(Model model, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        if ("1".equals(type)) {
            emailSender.sendCheckCode(UserCommon.getUser().getEmail(), "修改密码");
        } else if ("2".equals(type)) {
            emailSender.sendCheckCode(UserCommon.getUser().getEmail(), "修改手机");
        } else {
            emailSender.sendCheckCode(UserCommon.getUser().getEmail(), "修改邮箱");
        }
        MessageCommon.getSuccessMap(map);
        return map;
    }

    /**
     * 验证验证码是否正确
     *
     * @param model
     * @param code  验证码
     * @return 2016年7月6日 author njq
     */
    @RequestMapping(value = "checkCode", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> checkCode(Model model, @RequestParam(required = true) String code,
                                  HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        String coke = CookieUtil.getCookieVaule("modiCode", req);
        if (code.equals(coke)) {
            CookieUtil.removeCookieByName("modiCode");
            CookieUtil.addCookie("modiCodeFlag", "true");
            MessageCommon.getSuccessMap(map);
        } else {
            MessageCommon.getFalseMap(map);
        }
        return map;
    }

    /**
     * 修改密码
     *
     * @param newPwd
     * @param model
     * @return 2016年7月7日 author njq
     */
    @RequestMapping(value = "upUserPwd", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> upUserPwd(@RequestParam String newPwd, Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        String coke = CookieUtil.getCookieVaule("modiCodeFlag");
        if ("true".equals(coke)) {
            userService.updatePwd(newPwd, UserCommon.getUserId(), map);
        } else {
            MessageCommon.getFalseMap(map, "请正规操作，重新获取验证码！");
        }
        return map;
    }

    /**
     * 修改手机号
     *
     * @param newTel
     * @param model
     * @return 2016年7月7日 author njq
     */
    @RequestMapping(value = "upTel", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> upTel(@RequestParam String newTel, Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        String coke = CookieUtil.getCookieVaule("modiCodeFlag");
        if ("true".equals(coke)) {
            userService.updateTel(newTel, UserCommon.getUserId(), map);
        } else {
            MessageCommon.getFalseMap(map, "*请正规操作，重新获取验证码！");
        }
        return map;
    }

    /**
     * 修改email
     *
     * @param newEmail
     * @param model
     * @return 2016年7月7日 author njq
     */
    @RequestMapping(value = "upEmail", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> upEmail(@RequestParam String newEmail, Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        String coke = CookieUtil.getCookieVaule("modiCodeFlag");
        if ("true".equals(coke)) {
            userService.updateEmail(newEmail, UserCommon.getUserId(), map);
        } else {
            MessageCommon.getFalseMap(map, "*请正规操作，重新获取验证码！");
        }
        return map;
    }

    /**
     * 修改用户头像
     *
     * @param picPlace
     * @param model
     * @return
     */
    @RequestMapping(value = "upUserPic", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> upUserPic(@RequestParam String picPlace, Model model,
                                  HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        userService.updatePic(picPlace, UserCommon.getUserId(), map);
        BaseUser u = (BaseUser) session.getAttribute("user");
        u.setPicPlace(picPlace);
        session.setAttribute("user", u);
        return map;
    }

    @RequestMapping("demo")
    public String test() {
        return "back/demo";
    }
}
