package com.skyedu.service;

import com.skyedu.model.EduCity;
import com.skyedu.model.EduPeriod;

import java.util.List;
import java.util.Map;

/**
 * @Description xxx
 * @author: LiMing
 * @date: 2019/11/1  15:54
 * 个人设置级别的CRUD
 */
public interface BasePermissionService {
    /**
     * 地区
     */
    List<Map<String, Object>> getCity();

    /**
     * 询权限需要的设置的年级范围
     */
    List<Map<String, Object>> gradeList();

    /**
     * 科目
     */
    List<Map<String, Object>> subjectList();


}
