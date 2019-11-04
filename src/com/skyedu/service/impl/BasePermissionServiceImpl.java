package com.skyedu.service.impl;

import com.skyedu.dao.impl.BasePermissionDao;
import com.skyedu.service.BasePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description xxx
 * @author: LiMing
 * @date: 2019/11/1  15:55
 */
@Service
public class BasePermissionServiceImpl implements BasePermissionService {
    @Autowired
    private BasePermissionDao basePermissionDao;

    /**
     * 获取地区
     * @return
     */
    @Override
    public List<Map<String, Object>> getCity() {
//        SELECT * from Edu_City

        return basePermissionDao.getCity();

    }

    /**
     * 获取年级
     * @return
     */
    @Override
    public List<Map<String, Object>> gradeList() {
        return basePermissionDao.gradeList();
    }

    /**
     * 获取科目
     * @return
     */

    @Override
    public List<Map<String, Object>> subjectList() {
        return basePermissionDao.subjectList();
    }
}
