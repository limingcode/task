package com.skyedu.model.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
/**
 * @author huangRunDong
 * @createTime 10:25 2019/8/26
 */

@Entity
@Table(name = "tk_shareBook_t")
public class ShareBookModel implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private int shareBookId;
    private int typeId;
    /**
     * 0无效 1有效
     */
    private int valid=1;
    /**
     *
     */
    private int type=1;
    private int studentId;
    private int bookId;
    private Date createTime=new Date();
    private Date lastUpdateTime=new Date();
    @Transient
    private String pop;

    @Transient
    private String bookName;

    public int getShareBookId() {
        return shareBookId;
    }

    public void setShareBookId(int shareBookId) {
        this.shareBookId = shareBookId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}



