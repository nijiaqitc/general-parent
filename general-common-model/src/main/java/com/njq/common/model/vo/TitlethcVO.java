package com.njq.common.model.vo;

/**
 * Titlethc entity. @author MyEclipse Persistence Tools
 */

public class TitlethcVO implements java.io.Serializable {

	// Fields

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
	private Long parentId;
	private Integer type;
	private String title;
	private String grade;
	private String contextDesc;
	private Integer indexOne;
	private Integer indexTwo;
	private Integer total;
	private Integer goodNum;
	private Integer badNum;
	private String isShow;
	private String finishStatus;
	// Constructors

	/** default constructor */
	public TitlethcVO() {
	}

	/** full constructor */
	public TitlethcVO(Long parentId, Integer type, String title, String grade,
			String contextDesc, Integer indexOne, Integer indexTwo) {
		this.parentId = parentId;
		this.type = type;
		this.title = title;
		this.grade = grade;
		this.contextDesc = contextDesc;
		this.indexOne = indexOne;
		this.indexTwo = indexTwo;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getContextDesc() {
		return this.contextDesc;
	}

	public void setContextDesc(String contextDesc) {
		this.contextDesc = contextDesc;
	}

	public Integer getIndexOne() {
		return this.indexOne;
	}

	public void setIndexOne(Integer indexOne) {
		this.indexOne = indexOne;
	}

	public Integer getIndexTwo() {
		return this.indexTwo;
	}

	public void setIndexTwo(Integer indexTwo) {
		this.indexTwo = indexTwo;
	}

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public Integer getGoodNum() {
		return goodNum;
	}

	public void setGoodNum(Integer goodNum) {
		this.goodNum = goodNum;
	}

	public Integer getBadNum() {
		return badNum;
	}

	public void setBadNum(Integer badNum) {
		this.badNum = badNum;
	}

	public String getFinishStatus() {
		return finishStatus;
	}

	public void setFinishStatus(String finishStatus) {
		this.finishStatus = finishStatus;
	}
	
	
}