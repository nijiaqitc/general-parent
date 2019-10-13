package com.njq.common.base.other;

import com.njq.common.base.config.SpringContextUtil;

/**
 * @author: nijiaqi
 * @date: 2019/2/18
 */
public class TokenCheck {

    public static boolean checkToken(String token) {
    	//wapToken123
        if ("bd5e5018bcbb459d31d5e4dd895ff36e".equals(token)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkGrabToken(String token) {
    	//grabToken123
        if ("17092a4561c9530bc098bdde911dab2f".equals(token)) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean checkReviewToken(String token) {
    	//reviewToken123
        if ("339efbd21d44a0806e6b26aedcaf420f".equals(token)) {
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
