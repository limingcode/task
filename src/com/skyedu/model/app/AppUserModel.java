package com.skyedu.model.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppUserModel implements Serializable{
    private String img;
    private String code;
    private String designation;
    private String school;
    private String address;
    private String motherTel;
    private String fatherTel;
    private int sex;
    private String name;
    private List<Map<String,Object>> courseList=new ArrayList<>();
    private int id;
    private Integer cityId;
    private Object bookSeriesList;
    private int voiceEvaluationChannel=0;
    /**
     *  0 大班,1外教
     */
    private int tag;


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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public List<Map<String, Object>> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Map<String, Object>> courseList) {
        this.courseList = courseList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getBookSeriesList() {
        return bookSeriesList;
    }

    public void setBookSeriesList(Object bookSeriesList) {
        this.bookSeriesList = bookSeriesList;
    }

    public int getVoiceEvaluationChannel() {
        return voiceEvaluationChannel;
    }

    public void setVoiceEvaluationChannel(int voiceEvaluationChannel) {
        this.voiceEvaluationChannel = voiceEvaluationChannel;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public static class CourseModel implements Serializable {
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

        public String getCourseTime() {
            return courseTime;
        }

        public void setCourseTime(String courseTime) {
            this.courseTime = courseTime;
        }
    }

}
