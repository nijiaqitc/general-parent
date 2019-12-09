package com.njq.grab.service.impl.txsq;

import java.io.Serializable;
import java.util.List;

/**
 * @author: nijiaqi
 * @date: 2019/12/9
 */
public class TxRespData implements Serializable {
    private List<TxRespTit> list;
    private int total;

    public List<TxRespTit> getList() {
        return list;
    }

    public void setList(List<TxRespTit> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
