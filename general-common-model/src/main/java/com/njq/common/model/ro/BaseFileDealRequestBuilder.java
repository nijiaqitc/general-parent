package com.njq.common.model.ro;

/**
 * @author: nijiaqi
 * @date: 2019/3/16
 */
public final class BaseFileDealRequestBuilder {
    private Long typeId;
    private String channel;
    private String prefix;
    private String src;
    private String shortName;
    private String savePlace;
    private String imgPlace;

    public BaseFileDealRequestBuilder() {
    }

    public static BaseFileDealRequestBuilder aBaseFileRequest() {
        return new BaseFileDealRequestBuilder();
    }

    public BaseFileDealRequestBuilder ofTypeId(Long typeId) {
        this.typeId = typeId;
        return this;
    }

    public BaseFileDealRequestBuilder ofChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public BaseFileDealRequestBuilder ofPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public BaseFileDealRequestBuilder ofSrc(String src) {
        this.src = src;
        return this;
    }

    public BaseFileDealRequestBuilder ofShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public BaseFileDealRequestBuilder ofSavePlace(String savePlace) {
        this.savePlace = savePlace;
        return this;
    }

    public BaseFileDealRequestBuilder ofImgPlace(String imgPlace) {
        this.imgPlace = imgPlace;
        return this;
    }

    public BaseFileDealRequest build() {
        BaseFileDealRequest baseFileRequest = new BaseFileDealRequest();
        baseFileRequest.setTypeId(typeId);
        baseFileRequest.setChannel(channel);
        baseFileRequest.setPrefix(prefix);
        baseFileRequest.setSrc(src);
        baseFileRequest.setShortName(shortName);
        baseFileRequest.setSavePlace(savePlace);
        baseFileRequest.setImgPlace(imgPlace);
        return baseFileRequest;
    }
}
