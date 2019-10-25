package com.skyedu.model.book;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author huangRunDong
 * @createTime 10:42 2019/8/19
 *
 */
public class JsonsRootBean {
        @JsonProperty("bookId")
        private int bookId;
        private int type;
        @JsonProperty("lessonList")
        private List<Lessonlist> lessonList;
        private int width;
        private int height;

        public int getBookId() {
            return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public List<Lessonlist> getLessonList() {
            return lessonList;
        }

        public void setLessonList(List<Lessonlist> lessonList) {
            this.lessonList = lessonList;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getWidth() {
            return width;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public static class Points {

            private double x;
            private double y;


            public Points(double x, double y) {
                this.x = x;
                this.y = y;
            }
            public  Points(){}

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }
        }
        public static class Rectlist {

            private int type;
            @JsonProperty("sortNo")
            private int sortNo;
            private Shape shape;
            @JsonProperty("audioName")
            private String audioName;
            private String describe;
            private String duration;

            public Rectlist(int type, int sortNo,  String audioName, String describe, String duration) {
                this.type = type;
                this.sortNo = sortNo;
                this.audioName = audioName;
                this.describe = describe;
                this.duration = duration;
            }
            public Rectlist(){}

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

            public Shape getShape() {
                return shape;
            }

            public void setShape(Shape shape) {
                this.shape = shape;
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

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }
        }
        public static class Shape {

            private List<Points> points;
            private Points point;
            private double widthRadius;
            private double heightRadius;

            public Points getPoint() {
                return point;
            }

            public void setPoint(Points point) {
                this.point = point;
            }

            public void setPoints(List<Points> points) {
                this.points = points;
            }

            public List<Points> getPoints() {
                return points;
            }

            public double getWidthRadius() {
                return widthRadius;
            }

            public void setWidthRadius(double widthRadius) {
                this.widthRadius = widthRadius;
            }

            public double getHeightRadius() {
                return heightRadius;
            }

            public void setHeightRadius(double heightRadius) {
                this.heightRadius = heightRadius;
            }
        }
        public static class Pagelist {

            @JsonProperty("pageNumber")
            private int pageNumber;
            @JsonProperty("imageName")
            private String imageName;
            @JsonProperty("rectList")
            private List<Rectlist> rectList;

            public Pagelist(int pageNumber, String imageName) {
                this.pageNumber = pageNumber;
                this.imageName = imageName;
            }

            public Pagelist(){}
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

            public List<Rectlist> getRectList() {
                return rectList;
            }

            public void setRectList(List<Rectlist> rectList) {
                this.rectList = rectList;
            }
        }

        public static class Lessonlist {

            @JsonProperty("lessonId")
            private int lessonId;
            @JsonProperty("lessonTitle")
            private String lessonTitle;
            @JsonProperty("lessonDesc")
            private String lessonDesc;
            @JsonProperty("lessonPop")
            private String lessonPop;
            @JsonProperty("pageList")
            private List<Pagelist> pageList;

            public int getLessonId() {
                return lessonId;
            }

            public void setLessonId(int lessonId) {
                this.lessonId = lessonId;
            }

            public String getLessonTitle() {
                return lessonTitle;
            }

            public void setLessonTitle(String lessonTitle) {
                this.lessonTitle = lessonTitle;
            }

            public String getLessonDesc() {
                return lessonDesc;
            }

            public void setLessonDesc(String lessonDesc) {
                this.lessonDesc = lessonDesc;
            }

            public String getLessonPop() {
                return lessonPop;
            }

            public void setLessonPop(String lessonPop) {
                this.lessonPop = lessonPop;
            }

            public List<Pagelist> getPageList() {
                return pageList;
            }

            public void setPageList(List<Pagelist> pageList) {
                this.pageList = pageList;
            }
        }
}

