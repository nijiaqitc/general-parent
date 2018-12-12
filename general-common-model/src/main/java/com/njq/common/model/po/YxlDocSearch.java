package com.njq.common.model.po;

import com.njq.common.util.date.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;




/**
 * The persistent class for the yxl_doc_search database table.
 * 
 */
@Entity
@Table(name="yxl_doc_search")
@NamedQuery(name="YxlDocSearch.findAll", query="SELECT y FROM YxlDocSearch y")
public class YxlDocSearch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="doc_id")
	private Long docId;

	@Column(name="general")
	private String general;

	@Column(name="is_show")
	private String isShow;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="parent_index")
	private Integer parentIndex;

	@Column(name="parent_name")
	private String parentName;

	@Column(name="specal_type")
	private String specalType;

	@Column(name="title")
	private String title;

	@Column(name="type_id")
	private Long typeId;

	@Column(name="user_id")
	private Long userId;

	@Column(name="view")
	private Integer view;

	// Constructors

    /** default constructor */
    public YxlDocSearch() {
    }

    /** minimal constructor */
    public YxlDocSearch(Integer view) {
        this.view = view;
    }

    /** full constructor */
    public YxlDocSearch(Long docId, String title, String general, Long typeId, String isShow, Integer view, Date createDate, Timestamp modiDate) {
        this.docId = docId;
        this.title = title;
        this.general = general;
        this.typeId = typeId;
        this.isShow = isShow;
        this.view = view;
        this.createDate = createDate;
        this.modiDate = modiDate;
    }

    // Property accessors

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocId() {
        return this.docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getTitle() {
        return this.title;
    }
    
    public String getFormatTitle(){
    	if(this.getTitle().length()>20){
    		return this.getTitle().substring(0, 18)+"...";
    	}else{
    		return this.getTitle();
    	}
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeneral() {
        return this.general;
    }

    public String getFormatGeneral(){
        if(this.general==null){
            return "";
        }
    	if(this.general.length()>20){
    		return this.general.substring(0, 18)+"...";
    	}else{
    		return this.general;
    	}
    }
    
    public void setGeneral(String general) {
        this.general = general;
    }

    public Long getTypeId() {
        return this.typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getIsShow() {
        return this.isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public Integer getView() {
        return this.view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public String getFormatCreatedDate(){
    	return DateUtil.toDateString2(this.createDate);
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Timestamp getModiDate() {
        return this.modiDate;
    }

    public void setModiDate(Timestamp modiDate) {
        this.modiDate = modiDate;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getParentIndex() {
		return parentIndex;
	}

	public void setParentIndex(Integer parentIndex) {
		this.parentIndex = parentIndex;
	}

    public String getSpecalType() {
        return specalType;
    }

    public void setSpecalType(String specalType) {
        this.specalType = specalType;
    }

}