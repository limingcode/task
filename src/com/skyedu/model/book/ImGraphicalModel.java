package com.skyedu.model.book;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static javax.persistence.GenerationType.IDENTITY;
/**
 * @author huangRunDong
 * @createTime 17:13 2019/8/16
 *
 */
@Table(name="IM_Graphical")
@Entity
public class ImGraphicalModel implements Serializable {
    private int graphicalId;
    private int pageId;
    private int type;
    private int sortNo;
    private String audioName;
    private String describe;
    private String duration;
    private Date createTime= new Date();
    private int bookId;
    private int lessonId;
    private int valid=1;
    private Object shape;
    public ImGraphicalModel(int pageId,int type, int sortNo, String audioName, String describe, String duration,int bookId,int lessonId) {
        this.pageId=pageId;
        this.type = type;
        this.sortNo = sortNo;
        this.audioName = audioName;
        this.describe = describe;
        this.duration = duration;
        this.bookId = bookId;
        this.lessonId = lessonId;
    }
    public ImGraphicalModel(){}
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int getGraphicalId() {
        return graphicalId;
    }

    public void setGraphicalId(int graphicalId) {
        this.graphicalId = graphicalId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
    @Transient
    public Object getShape() {
        return shape;
    }

    public void setShape(Object shape) {
        this.shape = shape;
    }
}
