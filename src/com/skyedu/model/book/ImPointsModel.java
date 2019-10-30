package com.skyedu.model.book;
import com.teach.util.MathUtil;
import com.util.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;
/**
 * @author huangRunDong
 * @createTime 17:13 2019/8/16
 *
 */
@Table(name = "IM_Points")
@Entity
public class ImPointsModel implements Serializable {
    private int pointsId;
    private double x;
    private double y;
    private Double widthRadius;
    private Double heightRadius;
    private int pageId;
    private int bookId;
    private int lessonId;
    private int graphicalId;
    private Date createTime= new Date();
    private int valid=1;
    public ImPointsModel(){}
       public ImPointsModel(double x, double y, int pageId, int bookId, int lessonId,int graphicalId) {
        this.x = x;
        this.y = y;
        this.pageId = pageId;
        this.bookId = bookId;
        this.lessonId = lessonId;
        this.graphicalId=graphicalId;
    }

    public ImPointsModel(double widthRadius,double heightRadius,double x, double y, int pageId, int bookId, int lessonId,int graphicalId) {
        this.widthRadius=widthRadius;
        this.heightRadius=heightRadius;
        this.x = x;
        this.y = y;
        this.pageId = pageId;
        this.bookId = bookId;
        this.lessonId = lessonId;
        this.graphicalId=graphicalId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int getPointsId() {
        return pointsId;
    }

    public void setPointsId(int pointsId) {
        this.pointsId = pointsId;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = MathUtil.keepDecimal(x,3);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = MathUtil.keepDecimal(y,3);
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

    public int getGraphicalId() {
        return graphicalId;
    }

    public void setGraphicalId(int graphicalId) {
        this.graphicalId = graphicalId;
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

    public Double getWidthRadius() {
        return widthRadius;
    }

    public void setWidthRadius(Double widthRadius) {
        this.widthRadius = widthRadius;
    }

    public Double getHeightRadius() {
        return heightRadius;
    }

    public void setHeightRadius(Double heightRadius) {
        this.heightRadius = heightRadius;
    }
}
