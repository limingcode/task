package com.skyedu.controller.admin;

import com.skyedu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description xxx
 * @author: LiMing
 * @date: 2019/10/25  9:37
 * 权限相操作
 */
@Controller
@RequestMapping("/permission/")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;


    /**
     *
     * 显示班级权限控制列表  只有设置阅读的才显示
     * @param request
     * @param response
     * @param modelMap
     * @return
     *
     */


    @RequestMapping("classPermission")
    public String classPermission(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){


        return "admin/permission/classPermission";
    }


    /**
     *
     * @return
     */

    @RequestMapping("everPermission")
    public String everPermission(){

        return "admin/permission/everPermission";
    }
}
