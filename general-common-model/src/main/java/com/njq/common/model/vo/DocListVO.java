package com.njq.common.model.vo;

import java.util.List;

public class DocListVO implements java.io.Serializable{

    
    /**
     * 
     */
    private static final long serialVersionUID = 4444372160113161854L;
    private Long id;
    private Long docId;
    private String title;
    private String general;
    private Long typeId;
    private String isShow;
    private Integer view;
    private Long userId;
    private String createDate;
    private List<String> tipList;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getDocId() {
        return docId;
    }
    public void setDocId(Long docId) {
        this.docId = docId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getGeneral() {
        return general;
    }
    public void setGeneral(String general) {
        this.general = general;
    }
    public Long getTypeId() {
        return typeId;
    }
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
    public String getIsShow() {
        return isShow;
    }
    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }
    public Integer getView() {
        return view;
    }
    public void setView(Integer view) {
        this.view = view;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public List<String> getTipList() {
        return tipList;
    }
    public void setTipList(List<String> tipList) {
        this.tipList = tipList;
    }
    
    
    
}
