package com.njq.common.model.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the yxl_folder database table.
 * 
 */
@Entity
@Table(name="yxl_folder")
@NamedQuery(name="YxlFolder.findAll", query="SELECT y FROM YxlFolder y")
public class YxlFolder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="folder_name")
	private String folderName;

	@Column(name="is_lock")
	private String isLock;

	@Column(name="lock_pwd")
	private String lockPwd;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="parent_id")
	private Long parentId;

	@Column(name="type")
	private Integer type;

	// Constructors

		/** default constructor */
		public YxlFolder() {
		}

		/** full constructor */
		public YxlFolder(Long parentId, String folderName, String isLock,
				String lockPwd, Integer type, Timestamp createDate,
				Timestamp modiDate) {
			this.parentId = parentId;
			this.folderName = folderName;
			this.isLock = isLock;
			this.lockPwd = lockPwd;
			this.type = type;
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

		public Long getParentId() {
			return this.parentId;
		}

		public void setParentId(Long parentId) {
			this.parentId = parentId;
		}

		public String getFolderName() {
			return this.folderName;
		}

		public void setFolderName(String folderName) {
			this.folderName = folderName;
		}

		public String getIsLock() {
			return this.isLock;
		}

		public void setIsLock(String isLock) {
			this.isLock = isLock;
		}

		public String getLockPwd() {
			return this.lockPwd;
		}

		public void setLockPwd(String lockPwd) {
			this.lockPwd = lockPwd;
		}

		public Integer getType() {
			return this.type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public Timestamp getModiDate() {
			return this.modiDate;
		}

		public void setModiDate(Timestamp modiDate) {
			this.modiDate = modiDate;
		}

		public Date getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}

}