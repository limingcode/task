package com.skyedu.model.book;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
/**
 * @author huangRunDong
 * @createTime 17:12 2019/8/16
 */
@Table(name="IM_Page")
@Entity
public class ImPageModel implements Serializable {

    private int pageId;
    private int bookId;
    private int lessonId;
    private int pageNumber;
    private String imageName;
    private int valid=1;
    private Date createTime= new Date();

    private List<ImGraphicalModel> rectList;

    public ImPageModel(int bookId, int lessonId, int pageNumber, String imageName) {
        this.lessonId = lessonId;
        this.pageNumber = pageNumber;
        this.imageName = imageName;
        this.bookId=bookId;
    }
    public  ImPageModel(){

    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Transient
    public List<ImGraphicalModel> getRectList() {
        return rectList;
    }

    public void setRectList(List<ImGraphicalModel> rectList) {
        this.rectList = rectList;
    }
}
