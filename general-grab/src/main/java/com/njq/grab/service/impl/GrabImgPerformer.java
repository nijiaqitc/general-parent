package com.njq.grab.service.impl;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.util.string.StringUtil;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: nijiaqi
 * @date: 2019/12/9
 */
public class GrabImgPerformer {
    private static final Logger logger = LoggerFactory.getLogger(GrabImgPerformer.class);

    public static void loadImg(Element enode, ChannelType type, GrabConfig config) {
        enode.getElementsByTag("img").parallelStream().forEach(n -> {
            String imgUrl = "";
            if (StringUtil.IsNotEmpty(n.attr("src"))) {
                imgUrl = n.attr("src");
            } else if (StringUtil.IsNotEmpty(n.attr("data-original-src"))) {
                imgUrl = n.attr("data-original-src");
            }
            if (StringUtil.IsNotEmpty(imgUrl)) {
                logger.info("读取图片:" + n.attr("src"));
                n.attr("src", config.getBaseFileService()
                        .dealImgSrc(config.getBaseTitle().getTypeId(),
                                type,
                                config.getGrabUrl(),
                                imgUrl));
            }
        });
    }
}
