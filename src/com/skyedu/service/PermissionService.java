package com.skyedu.service;

import com.skyedu.model.EduStudent;

import java.util.List;
import java.util.Map;

/**
 * @Description xxx
 * @author: LiMing
 * @date: 2019/10/25  14:16
 * 权限控制处理
 *
 */

public interface PermissionService {

    //查询所有的设置过权限的班级

    List<Map<String, Object>> getclassPermissionList(Map<String, Object> condition);

    //个人数据权限处理  只显示设置过的 根据levelCateId 查询

    List<Map<String, Object>> getEveryList(Map<String, Object> condition);


}
