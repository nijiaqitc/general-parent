package com.njq.common.model.ro;

/**
 * @author: nijiaqi
 * @date: 2019/3/16
 */
public class BaseFileDealRequest {
    private Long typeId;
    private String channel;
    private String prefix;
    private String src;
    private String shortName;
    private String savePlace;
    private String imgPlace;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSavePlace() {
        return savePlace;
    }

    public void setSavePlace(String savePlace) {
        this.savePlace = savePlace;
    }

    public String getImgPlace() {
        return imgPlace;
    }

    public void setImgPlace(String imgPlace) {
        this.imgPlace = imgPlace;
    }
}
