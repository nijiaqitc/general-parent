package com.njq.grab.service.impl.txsq;

import java.io.Serializable;

/**
 * @author: nijiaqi
 * @date: 2019/12/9
 */
public class TxRespTit implements Serializable {
    private Long id;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
