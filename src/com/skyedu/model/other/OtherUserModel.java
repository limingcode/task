package com.skyedu.model.other;

import java.io.Serializable;
import java.util.List;

public class OtherUserModel implements Serializable {

    private int errcode;
    private Data data;

    public OtherUserModel() {
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


   public static class Data implements Serializable{
        private String img;
        private String code;
        private String designation;
        private String school;
        private String address;
        private String motherTel;
        private String fatherTel;
        private int sex;
        private String name;
        private List<CourseModel> courseList;
        private int iD;
        private Object bookList;


        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMotherTel() {
            return motherTel;
        }

        public void setMotherTel(String motherTel) {
            this.motherTel = motherTel;
        }

        public String getFatherTel() {
            return fatherTel;
        }

        public void setFatherTel(String fatherTel) {
            this.fatherTel = fatherTel;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

       public int getSex() {
           return sex;
       }

       public void setSex(int sex) {
           this.sex = sex;
       }

       public int getiD() {
           return iD;
       }

       public void setiD(int iD) {
           this.iD = iD;
       }

       public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CourseModel> getCourseList() {
            return courseList;
        }

        public void setCourseList(List<CourseModel> courseList) {
            this.courseList = courseList;
        }



        public Object getBookList() {
            return bookList;
        }

        public void setBookList(String bookList) {
            this.bookList = bookList;
        }
       public void setBookList(Object bookList) {
           this.bookList = bookList;
       }
    }
    public static class CourseModel implements Serializable{
        private String periodName;
        private String periodYear;
        private String photo;
        private String teacherName;
        private String subjectName;
        private String deptName;
        private String endDate;
        private String beginDate;
        private String gradeName;
        private String cateName;
        private int iD;
        private String courseTime;

        public String getCourseTime() {
            return courseTime;
        }

        public void setCourseTime(String courseTime) {
            this.courseTime = courseTime;
        }

        public String getPeriodName() {
            return periodName;
        }

        public void setPeriodName(String periodName) {
            this.periodName = periodName;
        }

        public String getPeriodYear() {
            return periodYear;
        }

        public void setPeriodYear(String periodYear) {
            this.periodYear = periodYear;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        public int getiD() {
            return iD;
        }

        public void setiD(int iD) {
            this.iD = iD;
        }
    }

}
