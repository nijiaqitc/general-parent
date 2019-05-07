package com.njq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.njq.common.exception.BaseKnownException;
import com.njq.wx.service.WxService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author: nijiaqi
 * @date: 2019/5/7
 */
@RequestMapping("wx")
@Controller
public class WxController {
    private static final Logger logger = LoggerFactory.getLogger(WxController.class);
    @Autowired
    private WxService wxService;
    /**
     * 微信token校验（微信公众号会发请求过来验证token）
     *
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    @GetMapping
    public void verifyToken(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
    	logger.info("进入wx");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");
        TreeSet<String> params = new TreeSet<>();
        params.add("8536cb26d597fbdddb9807778f518dc3");
        params.add(String.valueOf(timestamp));
        params.add(null == nonce ? "" : nonce);
        String join = StringUtils.join(params, "");
        String sha1 = DigestUtils.sha1Hex(join);
        if (!sha1.equals(signature)) {
            throw new BaseKnownException("不合法的请求");
        }
        PrintWriter out = null;
        try {
            response.setContentType("text/html");
            out = response.getWriter();
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            response.getWriter().write(echostr);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * 微信发送消息事件
     * @param request
     * @param response
     */
    @PostMapping
    public void handleWechatMessage(HttpServletRequest request, HttpServletResponse response) {
        String respMessage = "异常消息！";
        try {
            String appName = request.getParameter("sourceName");
            Map<String, String> map = this.xmlToMap(request);
            logger.info("appName: " + appName + " wxmsg:" + JSON.toJSONString(map));
            String msgType = map.get("MsgType");
            respMessage = msgType + appName;
            PrintWriter out = response.getWriter();
            if (StringUtils.isBlank(respMessage)) {
                return;
            }
            out.write(respMessage);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        InputStream ins = null;
        try {
            ins = request.getInputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Document doc = null;
        try {
            doc = reader.read(ins);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            return map;
        } catch (DocumentException e1) {
            logger.error(e1.getMessage());
        } finally {
            try {
                ins.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return null;
    }

    /**
     * 获取消息列表
      * @return
     */
    @RequestMapping("loadMedia")
    @ResponseBody
    public String loadMedia(){
        return wxService.loadMediaList();
    }

    /**
     * 群发消息
     */
    @RequestMapping("sendAllMsg")
    public void sendAllMsg(){
        wxService.sendAllMsg();
    }

}
