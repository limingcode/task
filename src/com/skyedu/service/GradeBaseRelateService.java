package com.skyedu.service;

import com.skyedu.model.EduCity;
import com.skyedu.model.EduGrade;
import com.skyedu.model.EduPeriod;

import java.util.List;

/**
 * @Description xxx
 * @author: LiMing
 * @date: 2019/11/6  9:53
 * 班级特有的相关操作
 */
public interface GradeBaseRelateService {
    List<EduCity> getIiniTtermName();

    List<EduPeriod> getInitPeriod();
}
