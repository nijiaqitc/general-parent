package com.njq.common.model.ro;

import org.apache.commons.lang3.tuple.Pair;

/**
 * @author: nijiaqi
 * @date: 2019/3/15
 */
public class BaseFileSaveRequest {
    /**
     * 渠道
     */
    private String channel;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件原名
     */
    private String oldName;
    /**
     * 新的url地址
     */
    private String filePlace;
    /**
     * 实际的物理存储地址
     */
    private String realPlace;
    /**
     * 类型id
     */
    private Long typeId;
    /**
     * 原来的链接地址
     */
    private String oldSrc;
    /**
     * L:下载状态 R:备注
     */
    private Pair<Boolean, String> resultPair;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getFilePlace() {
        return filePlace;
    }

    public void setFilePlace(String filePlace) {
        this.filePlace = filePlace;
    }

    public String getRealPlace() {
        return realPlace;
    }

    public void setRealPlace(String realPlace) {
        this.realPlace = realPlace;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getOldSrc() {
        return oldSrc;
    }

    public void setOldSrc(String oldSrc) {
        this.oldSrc = oldSrc;
    }

    public Pair<Boolean, String> getResultPair() {
        return resultPair;
    }

    public void setResultPair(Pair<Boolean, String> resultPair) {
        this.resultPair = resultPair;
    }
}
