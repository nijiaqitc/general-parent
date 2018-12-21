package com.njq.admin.controller;

/**
 * 登录类
 */

import com.njq.basis.service.impl.BaseChannelService;
import com.njq.basis.service.impl.BaseUserService;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseUser;
import com.njq.common.util.encrypt.Md5Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class LoginController {

    @Resource
    private BaseUserService userService;
    @Resource
    private BaseChannelService channelService;

    /**
     * 跳转到登录页面
     *
     * @param model
     * @return 2015-11-28 author njq
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String jumpToPage(Model model) {
        return "login";
    }

    /**
     * 注册请求
     *
     * @return 2015-11-28 author njq
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> register(@Valid BaseUser user, Errors errors) {
        Map<String, Object> map = new HashMap<String, Object>();
        /*
         * 验证user是否存在问题，如果有问题则返回
         */
        if (errors.hasErrors()) {
            for (FieldError file : errors.getFieldErrors()) {
                map.put(file.getField(), file.getDefaultMessage());
            }
            map.put("state", "0");
            map.put("message", "输入信息有误，请检查后再提交！");
            return map;
        }
        user.setCreateBy(ConstantsCommon.Oper_User.NO_USER);
        user.setCreateDate(new Timestamp(System.currentTimeMillis()));
        user.setModiBy(ConstantsCommon.Oper_User.NO_USER);
        userService.saveUser(user, null, map);
        return map;
    }

    /**
     * 登录验证
     *
     * @return 2015-11-28 author njq
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> loginCheck(@RequestParam String account, @RequestParam String pwd,
                                   @RequestParam String code, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        /*
         * 判断验证码是否错误
         */
        if (!code.equals(session.getAttribute("codeImg"))) {
            MessageCommon.getFalseMap(map);
            map.put("message", "验证码错误！");
            return map;
        }

        BaseUser user = userService.queryUserByAccountAndPwd(account, Md5Util.getMD5Password(pwd));
        if (user == null) {
            MessageCommon.getFalseMap(map);
            map.put("message", "账户密码错误！");
        } else {
            // 在session中填充sessionId,唯一
            session.setAttribute("sessionId", request.getSession().getId());
            // 在session中填充用户信息
            session.setAttribute("user", user);
            Map<String, Object> channelMap = channelService.queryUserChannel(user.getId());
            // 在session中填充角色
            session.setAttribute("powerList", (List<Map<String, Object>>) channelMap.get("children"));
            session.setAttribute("power", (Map<String, Object>) channelMap.get("power"));
            session.setAttribute("powerMap", (Map<Long, Boolean>) channelMap.get("powerMap"));
            MessageCommon.getSuccessMap(map);
        }
        return map;
    }

    /**
     * 跳转到后台主页
     *
     * @return 2015-12-4 author njq
     */
    @RequestMapping(value = "/loginIndex", method = RequestMethod.GET)
    public String loginIndex() {
        return "redirect:/totalInfo/backIndex";
    }

    /**
     * 跳转到没有权限页面
     *
     * @return 2015-12-29 author njq
     */
    @RequestMapping(value = "/noPowerPage", method = RequestMethod.GET)
    public String noPowerPage() {
        return "noPowerPage";
    }

    /**
     * 退出
     *
     * @return 2015-12-29 author njq
     */
    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> loginOut(HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        session.invalidate();
        MessageCommon.getSuccessMap(map);
        return map;
    }

    /**
     * 生成验证码
     *
     * @param req
     * @param resp 2015-12-31 author njq
     */
    @RequestMapping("/getPic")
    public void getCertPic(HttpServletRequest req, HttpServletResponse resp) {
        char mapTable[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', '2', '3', '4', '5', '6', '7', '8', '9'};
        int width = 0;
        int height = 0;
        if (width <= 0) {
            width = 60;
        }
        if (height <= 0) {
            height = 20;
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 设定背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 画边框
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, width - 1, height - 1);
        // 取随机产生的认证码
        String strEnsure = "";
        // 4代表4位验证码,如果要生成更多位的认证码,则加大数值
        for (int i = 0; i < 4; ++i) {
            strEnsure += mapTable[(int) (mapTable.length * Math.random())];
        }
        // 将认证码显示到图像中,如果要生成更多位的认证码,增加drawString语句
        g.setColor(Color.black);
        g.setFont(new Font("Atlantic Inline", Font.PLAIN, 16));
        String str = strEnsure.substring(0, 1);
        g.drawString(str, 8, 17);
        str = strEnsure.substring(1, 2);
        g.drawString(str, 20, 15);
        str = strEnsure.substring(2, 3);
        g.drawString(str, 35, 18);
        str = strEnsure.substring(3, 4);
        g.drawString(str, 45, 15);
        // 随机产生10个干扰点
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            int xs = rand.nextInt(width);
            int ys = rand.nextInt(height);
            int xe = xs + rand.nextInt(width / 8);
            int ye = ys + rand.nextInt(height / 8);
            int red = rand.nextInt(255);
            int green = rand.nextInt(255);
            int blue = rand.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }
        // 释放图形上下文
        g.dispose();
        try {
            ServletOutputStream sos = resp.getOutputStream();
            HttpSession session = req.getSession();
            session.setAttribute("codeImg", strEnsure);
            // 输出图像到页面
            ImageIO.write(image, "JPEG", sos);
            sos.close();
        } catch (IOException e) {
        }
    }

}
