package com.skyedu.model.book;

import java.util.List;

/**
 * @author huangRunDong
 * @createTime 18:30 2019/8/19
 */
public class Shape {
    public Shape(List<ImPointsModel> points) {
        this.points = points;
    }
    public Shape(){}
    private List<ImPointsModel> points;

    public void setPoints(List<ImPointsModel> points) {
        this.points = points;
    }

    public List<ImPointsModel> getPoints() {
        return points;
    }
}
