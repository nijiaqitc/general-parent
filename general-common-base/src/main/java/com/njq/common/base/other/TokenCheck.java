package com.njq.common.base.other;

import com.njq.common.base.config.SpringContextUtil;

/**
 * @author: nijiaqi
 * @date: 2019/2/18
 */
public class TokenCheck {

    public static boolean checkToken(String token) {
        if ("f23e7c5ca69e363d8bc49f595a24192a".equals(token)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean debugType(){
    	String flag = SpringContextUtil.getValue("debugFlag");
        return Boolean.parseBoolean(flag);
    }

}
