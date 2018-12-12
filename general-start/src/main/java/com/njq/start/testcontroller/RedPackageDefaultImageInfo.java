package com.njq.start.testcontroller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author: nijiaqi
 * @date: 2018/9/20
 */
public class RedPackageDefaultImageInfo implements Serializable {
    /**
     * 业态
     */
    private List<YHSeller> yhSellerList;
    /**
     * 顶部广告图列表
     */
    private List<RedPackagePictureInfo> topPicList;
    /**
     * 分享弹框图片列表
     */
    private List<RedPackagePictureInfo> shareDialogList;
    /**
     * 分享浮标图片列表
     */
    private List<RedPackagePictureInfo> shareBuoyList;
    /**
     * wx分享图标集合
     */
    private List<RedPackagePictureInfo> receiveIconList;
    /**
     * 领取红包弹窗图片集合
     */
    private List<RedPackagePictureInfo> receiveDialogList;
    /**
     * 新客券标
     */
    private List<RedPackagePictureInfo> firstMarkingImgList;

    public RedPackageDefaultImageInfo() {

    }

    public RedPackageDefaultImageInfo(boolean isInit){
        if(isInit){
            this.yhSellerList = new ArrayList<>();
            this.topPicList = new ArrayList<>();
            this.shareDialogList = new ArrayList<>();
            this.receiveDialogList = new ArrayList<>();
            this.shareBuoyList = new ArrayList<>();
            this.receiveIconList = new ArrayList<>();
            this.firstMarkingImgList = new ArrayList<>();
        }
    }

    public List<YHSeller> getYhSellerList() {
        return yhSellerList;
    }

    public void setYhSellerList(List<YHSeller> yhSellerList) {
        this.yhSellerList = yhSellerList;
    }

    public List<RedPackagePictureInfo> getTopPicList() {
        return topPicList;
    }

    public void setTopPicList(List<RedPackagePictureInfo> topPicList) {
        this.topPicList = topPicList;
    }

    public List<RedPackagePictureInfo> getShareDialogList() {
        return shareDialogList;
    }

    public void setShareDialogList(List<RedPackagePictureInfo> shareDialogList) {
        this.shareDialogList = shareDialogList;
    }

    public List<RedPackagePictureInfo> getShareBuoyList() {
        return shareBuoyList;
    }

    public void setShareBuoyList(List<RedPackagePictureInfo> shareBuoyList) {
        this.shareBuoyList = shareBuoyList;
    }

    public List<RedPackagePictureInfo> getReceiveIconList() {
        return receiveIconList;
    }

    public void setReceiveIconList(List<RedPackagePictureInfo> receiveIconList) {
        this.receiveIconList = receiveIconList;
    }

    public List<RedPackagePictureInfo> getReceiveDialogList() {
        return receiveDialogList;
    }

    public void setReceiveDialogList(List<RedPackagePictureInfo> receiveDialogList) {
        this.receiveDialogList = receiveDialogList;
    }

    public List<RedPackagePictureInfo> getFirstMarkingImgList() {
        return firstMarkingImgList;
    }

    public void setFirstMarkingImgList(List<RedPackagePictureInfo> firstMarkingImgList) {
        this.firstMarkingImgList = firstMarkingImgList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("yhSellerList", yhSellerList)
                .append("topPicList", topPicList)
                .append("shareDialogList", shareDialogList)
                .append("shareBuoyList", shareBuoyList)
                .append("receiveIconList", receiveIconList)
                .append("receiveDialogList", receiveDialogList)
                .append("firstMarkingImgList", firstMarkingImgList)
                .toString();
    }
}
