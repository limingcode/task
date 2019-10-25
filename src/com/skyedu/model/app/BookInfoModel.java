package com.skyedu.model.app;

import com.skyedu.service.LessonService;
import com.util.CommonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookInfoModel implements Serializable {
    private int id;
    private String name;
    private Integer cateCode;
    private String img;
    private String pop;
    private int cityId;

    /**
     * 获取首页信息
     * @param cityId
     * @return
     */
    public static List<BookInfoModel> getBookInfoList(int cityId) {
        List<BookInfoModel> objects = new ArrayList<>();
        if (cityId == 2) {
            objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/hz/LEVEL1+@3x.png",1, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/杭州Level1+@2x.png", 111, "Level 1+",2, null));
            objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/hz/LEVEL2@3x.png",2, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/杭州Level2@2x.png", 112, "Level 2", 2,null));
            objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/hz/LEVEL3@2x.png",3, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/杭州Level3@2x.png", 113, "Level 3", 2,null));
            objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/hz/LEVEL4@3x.png",4, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/杭州Level4@2x.png", 114, "Level 4", 2,null));
            objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/hz/LEVEL5@3x.png",5, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/杭州Level5@2x.png", 115, "Level 5", 2,null));
            objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/hz/LONGMAN@3x.png",6, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/点读LEW@2x.png", 2, "LONGMAN", 2,null));
        }else {
            objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/sz/LEAP@3x.png",1, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/点读LEAP@2x.png", 7, "LEAP",1, null));
            objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/sz/PLE2@3x.png",2, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/点读PLE2@2x.png", 1, "PLE2",1, null));
            objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/sz/LWTE@3x.png",3, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/点读LWTE@2x.png", 3, "LWTE",1, null));
            objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/sz/LEW@3x.png",4, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/点读LEW@2x.png", 2, "LEW", 1,null));
            objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/sz/分级阅读@3x.png",5, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/绘本@2x.png", 201, "绘本", 1,null));
        }
        return objects;
    }
    public static List<BookInfoModel> getBookInfoList() {
        List<BookInfoModel> objects = new ArrayList<>();
        objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/sz/LEAP@3x.png",1, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/点读LEAP@2x.png", 7, "LEAP",1, null));
        objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/sz/PLE2@3x.png",2, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/点读PLE2@2x.png", 1, "PLE2",1, null));
        objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/sz/LWTE@3x.png",3, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/点读LWTE@2x.png", 3, "LWTE",1, null));
        objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/sz/LEW@3x.png",4, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/点读LEW@2x.png", 2, "LEW", 1,null));
        objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/sz/分级阅读@3x.png",5, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/绘本@2x.png", 201, "绘本", 1,null));
        objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/hz/LEVEL1+@3x.png",6, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/杭州Level1+@2x.png", 111, "Level 1+",2, null));
        objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/hz/LEVEL2@3x.png",7, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/杭州Level2@2x.png", 112, "Level 2", 2,null));
        objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/hz/LEVEL3@2x.png",8, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/杭州Level3@2x.png", 113, "Level 3", 2,null));
        objects.add(new BookInfoModel(CommonUtil.APPCLIENTURL+"/AppTask/home/hz/LEVEL4@3x.png",9, CommonUtil.APPCLIENTURL+"/AppTask/upload/image/杭州Level4@2x.png", 114, "Level 4", 2,null));
        return objects;
    }


    public BookInfoModel(String pop,int id, String img, Integer cateCode, String name,int cityId,List<Map<String, Object>> maps) {
        this.pop=pop;
        this.id = id;
        this.img = img;
        this.cateCode = cateCode;
        this.name = name;
        this.cityId=cityId;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCateCode(Integer cateCode) {
        this.cateCode = cateCode;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getCateCode() {
        return cateCode;
    }

    public void setCateCode(int cateCode) {
        this.cateCode = cateCode;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }
}
