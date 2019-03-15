package com.njq.common.model.ro;

import org.apache.commons.lang3.tuple.Pair;

/**
 * @author: nijiaqi
 * @date: 2019/3/15
 */
public final class BaseFileSaveRequestBuilder {
    private String channel;
    private String name;
    private String oldName;
    private String filePlace;
    private String realPlace;
    private Long typeId;
    private String oldSrc;
    private Pair<Boolean, String> resultPair;

    public BaseFileSaveRequestBuilder() {
    }

    public static BaseFileSaveRequestBuilder aBaseFileSaveRequest() {
        return new BaseFileSaveRequestBuilder();
    }

    public BaseFileSaveRequestBuilder ofChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public BaseFileSaveRequestBuilder ofName(String name) {
        this.name = name;
        return this;
    }

    public BaseFileSaveRequestBuilder ofOldName(String oldName) {
        this.oldName = oldName;
        return this;
    }

    public BaseFileSaveRequestBuilder ofFilePlace(String filePlace) {
        this.filePlace = filePlace;
        return this;
    }

    public BaseFileSaveRequestBuilder ofRealPlace(String realPlace) {
        this.realPlace = realPlace;
        return this;
    }

    public BaseFileSaveRequestBuilder ofTypeId(Long typeId) {
        this.typeId = typeId;
        return this;
    }

    public BaseFileSaveRequestBuilder ofOldSrc(String oldSrc) {
        this.oldSrc = oldSrc;
        return this;
    }

    public BaseFileSaveRequestBuilder ofResultPair(Pair<Boolean, String> resultPair) {
        this.resultPair = resultPair;
        return this;
    }

    public BaseFileSaveRequest build() {
        BaseFileSaveRequest baseFileSaveRequest = new BaseFileSaveRequest();
        baseFileSaveRequest.setChannel(channel);
        baseFileSaveRequest.setName(name);
        baseFileSaveRequest.setOldName(oldName);
        baseFileSaveRequest.setFilePlace(filePlace);
        baseFileSaveRequest.setRealPlace(realPlace);
        baseFileSaveRequest.setTypeId(typeId);
        baseFileSaveRequest.setOldSrc(oldSrc);
        baseFileSaveRequest.setResultPair(resultPair);
        return baseFileSaveRequest;
    }
}
