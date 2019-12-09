package com.njq.grab.service.impl.txsq;

import java.io.Serializable;

/**
 * @author: nijiaqi
 * @date: 2019/12/9
 */
public class Txmodel implements Serializable {
    private String action;
    private Payload payload;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
