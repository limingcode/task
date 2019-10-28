package com.skyedu.service.impl;

import com.skyedu.dao.impl.PermissonDao;
import com.skyedu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description xxx
 * @author: LiMing
 * @date: 2019/10/25  14:19
 * 权限控制实现业务类
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissonDao permissonDao;
    @Override
    public List<Map<String, Object>> getclassPermissionList(Map<String, Object> condition) {
        List<Map<String, Object>> classPermissionList = permissonDao
                .getMessageList(condition);
        return classPermissionList;

    }
}
