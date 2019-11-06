package com.skyedu.service.impl;

import com.skyedu.dao.impl.GradeBaseRelateDao;
import com.skyedu.model.EduCity;
import com.skyedu.model.EduGrade;
import com.skyedu.model.EduPeriod;
import com.skyedu.service.GradeBaseRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description xxx
 * @author: LiMing
 * @date: 2019/11/6  9:53
 * 班级特有的相关操作业务实现类
 */
@Service
public class GradeBaseRelateServiceImpl implements GradeBaseRelateService {
    @Autowired
    private GradeBaseRelateDao gradeBaseRelateDao;

    @Override
    public List<EduCity> getIiniTtermName() {
        List<EduCity> list=  gradeBaseRelateDao.getIiniTtermName();
        return list;
    }

    @Override
    public List<EduPeriod> getInitPeriod() {
        List<EduPeriod> list=  gradeBaseRelateDao.getInitPeriod();
        return list;
    }
}
