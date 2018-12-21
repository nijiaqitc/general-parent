package com.njq.common.base.email;

import com.njq.common.util.other.CookieUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: nijiaqi
 * @date: 2018/12/20
 */
@Component
public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private static final String DEFAULT_ENCODING = "utf-8";
//    @Autowired
    private JavaMailSender mailSender;
//    @Autowired
    private Configuration configuration;
//    @Value("${spring.mail.username}")
    private String fromAddress;
    private Template emailTemplate;

    /**
     * 群发验证码
     * 2016年7月6日
     * author njq
     */
    public void sendCheckCode(String[] to, String type) {
        Map<String, String> map = new HashMap<String, String>();
        String str = System.currentTimeMillis() + "";
        String code = str.substring(8);
        map.put("checkCode", code);
        map.put("type", type);
        String content = getEamilContent(map, "");
        send(to, "网站验证码", content);
        //将验证码放到cookie中
        CookieUtil.addCookie("modiCode", code);
    }

    /**
     * 单发验证码
     *
     * @param to
     * @param type 2016年7月7日
     *             author njq
     */
    public void sendCheckCode(String to, String type) {
        Map<String, String> map = new HashMap<String, String>();
        String str = System.currentTimeMillis() + "";
        String code = str.substring(8);
        map.put("checkCode", code);
        map.put("type", type);
        String content = getEamilContent(map, "");
        send(new String[]{to}, "网站验证码", content);
        //将验证码放到cookie中
        CookieUtil.addCookie("modiCode", code);
    }

    /**
     * 发送邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    public void send(String[] to, String subject, String content) {
        MimeMessage msg = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
            helper.setTo(to);
            String nick = javax.mail.internet.MimeUtility.encodeText("小琦");
            helper.setFrom(new InternetAddress(nick + "<" + fromAddress + ">"));
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(msg);
        } catch (Exception e) {
            throw new RuntimeException("发送不含附件邮件异常！", e);
        }
    }

    /**
     * 发送邮件包含附件
     *
     * @param to          接收人
     * @param subject     邮件标题
     * @param content     邮件内容
     * @param attachments 附件集合
     */
    public void send(String[] to, String subject, String content, File[] attachments) {
        MimeMessage msg = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
            helper.setTo(to);
            helper.setFrom(fromAddress);
            helper.setSubject(subject);
            helper.setText(content, true);
            for (int i = 0; i < attachments.length; i++) {
                helper.addAttachment(attachments[i].getName(), attachments[i]);
            }
            mailSender.send(msg);
        } catch (Exception e) {
            throw new RuntimeException("发送含附件邮件异常！", e);
        }
    }

    private String getEamilContent(Map<String, String> param, String templateType) {
        try {
            Template template = configuration.getTemplate("mailTemplate.ftl", DEFAULT_ENCODING);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
