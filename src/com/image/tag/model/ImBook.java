package com.image.tag.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.skyedu.model.book.Permission;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name="IM_Book")
public class ImBook implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	
	@Column(name="gradeCode")
	private String gradeCode;
	
	@Column(name="subjectCode")
	private String subjectCode;
	
	@Column(name="cateCode")
	private String cateCode;
	
	@Column(name="bookName")
	private String bookName;
	
	@Column(name="status")
	private int status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime")
	private Date createTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updateTime")
	private Date updateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sendTime")
	private Date sendTime;
	
	@Column(name="sendNum")
	private int sendNum;
	
	@Column(name="downPath")
	private String downPath;
	
	@Column(name="pop")
	private String pop;
	
	@Column(name="zipSize")
	private Long zipSize;
	
	@Column(name="md5Code")
	private String md5Code;


    @Column(name="md5JsonCode")
    private String md5JsonCode;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modifyJsonTime")
	private Date modifyJsonTime;
	private int type;
	private int width;
	private int height;
//	@ManyToOne(cascade = {CascadeType.ALL})
//	@JoinColumn(name = "id")
//
//	private Permission permission;
	public ImBook() {
		super();
	}

	public ImBook(Integer id, String gradeCode, String subjectCode, String cateCode, String bookName, int status,
			Date createTime, Date updateTime, Date sendTime, int sendNum, String downPath, String pop, Long zipSize) {
		super();
		this.id = id;
		this.gradeCode = gradeCode;
		this.subjectCode = subjectCode;
		this.cateCode = cateCode;
		this.bookName = bookName;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.sendTime = sendTime;
		this.sendNum = sendNum;
		this.downPath = downPath;
		this.pop = pop;
		this.zipSize = zipSize;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getCateCode() {
		return cateCode;
	}

	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public int getSendNum() {
		return sendNum;
	}
	
	public void setSendNum(int sendNum) {
		this.sendNum = sendNum;
	}
	
	public String getDownPath() {
		return downPath;
	}

	public void setDownPath(String downPath) {
		this.downPath = downPath;
	}

	public String getPop() {
		return pop;
	}
	
	public void setPop(String pop) {
		this.pop = pop;
	}

	public Long getZipSize() {
		return zipSize;
	}

	public void setZipSize(Long zipSize) {
		this.zipSize = zipSize;
	}

	public String getMd5Code() {
		return md5Code;
	}

	public void setMd5Code(String md5Code) {
		this.md5Code = md5Code;
	}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Date getModifyJsonTime() {
        return modifyJsonTime;
    }

    public String getMd5JsonCode() {
        return md5JsonCode;
    }

    public void setMd5JsonCode(String md5JsonCode) {
        this.md5JsonCode = md5JsonCode;
    }

    public void setModifyJsonTime(Date modifyJsonTime) {
        this.modifyJsonTime = modifyJsonTime;
    }
}

