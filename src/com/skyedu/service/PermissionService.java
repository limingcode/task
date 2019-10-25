package com.skyedu.service;

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

    List<Map<String, Object>> getclassPermissionList(Map<String, Object> condition);

}
