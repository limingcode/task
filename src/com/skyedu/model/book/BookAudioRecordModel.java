package com.skyedu.model.book;

import javax.persistence.*;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
/**
 * @author huangRunDong
 * @createTime 14:12 2019/9/9
 *
 */
@Entity
@Table(name = "Tk_Book_AudioRecord_t")
public class BookAudioRecordModel implements Serializable {

    private int bookAudioRecordId;
    private int studentId;
    private String audioUrl;
    private int bookId;
    private int graphicalId;
    private Date createTime=new Date();
    private Date lastUpdateTime=new Date();
    /**
     * 0无效 1有效
     */
    private int valid;
    /**
     * 0 未分享 1 已分享
     */
    private int isShare;
    /**
     *  0分享不存在 1 分享已存在
     */
    private int tag=0;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int getBookAudioRecordId() {
        return bookAudioRecordId;
    }

    public void setBookAudioRecordId(int bookAudioRecordId) {
        this.bookAudioRecordId = bookAudioRecordId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getGraphicalId() {
        return graphicalId;
    }

    public void setGraphicalId(int graphicalId) {
        this.graphicalId = graphicalId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getIsShare() {
        return isShare;
    }

    public void setIsShare(int isShare) {
        this.isShare = isShare;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
