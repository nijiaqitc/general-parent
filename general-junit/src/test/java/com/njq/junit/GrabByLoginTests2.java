package com.njq.junit;

import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.grab.SendConstants;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: nijiaqi
 * @date: 2019/8/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GrabByLoginTests2 {

    @Test
    public void contextLoads() {
        Map<String, String> param = new HashMap<>();
        param.put("user[login]", "nijiaqi");
        param.put("user[password]", "Aa11111111");
        param.put("authenticity_token", "jXVKvkx48vF5TZSsSi0PY7OqE6+6R4u454Ck1XNK8ngJ3idDbtBeAc/tkkLWVRB1CwdvXr/5roixUUZRdpNJrg==");
        param.put("utf8", "✓");
        param.put("user[remember_me]", "0");
        HtmlGrabUtil.build("aaa").login("http://gitlab.yonghuivip.com/users/sign_in", SendConstants.SEND_POST, param);


    }
}
