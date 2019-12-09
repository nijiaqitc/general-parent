package com.njq.grab.service.impl.txsq;

import java.io.Serializable;

/**
 * @author: nijiaqi
 * @date: 2019/12/9
 */
public class TxRespone implements Serializable {
    private int code;
    private String msg;
    private TxRespData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TxRespData getData() {
        return data;
    }

    public void setData(TxRespData data) {
        this.data = data;
    }
}
