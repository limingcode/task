package com.skyedu.controller.admin;

import com.skyedu.service.MessageService;
import com.skyedu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description xxx
 * @author: LiMing
 * @date: 2019/10/25  9:37
 * 权限相关操作
 */
@Controller
@RequestMapping("/permission/")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private MessageService messageService;
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
        //查询对应的数据设置

        Map<String,Object> map=new HashMap<>();
        String  cityCode= request.getParameter("");//地区

        String  xueQiName= request.getParameter("");//学期名称

        String  className= request.getParameter("");//班级名称

        String  teacherName= request.getParameter("");//老师

        String  school= request.getParameter("");//校区
        String  grageName= request.getParameter("");//年级

        String  cate= request.getParameter("");//层次
        String  attendTime= request.getParameter("");//上课时间
        String  betweenTime = request.getParameter("");//时段
        String  permission= request.getParameter("");//阅读权限
        String  optionTime = request.getParameter("");//操作时间
        String  optionPeople=request.getParameter("");//最后操作人

        map.put("",cityCode);
        map.put("",cityCode);
        map.put("",cityCode);

        List<Map<String, Object>> maps = permissionService.getclassPermissionList(map);
        modelMap.put("maps",maps);
        modelMap.put("map",map);

        return "admin/permission/classPermission";
    }
    /**
     *
     *个人操作权限列表
     * @param request
     * @param response
     * @param modelMap
     * @return
     */

    @RequestMapping("everPermission")
    public String everPermission(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap){
        Map<String, Object> condition = new HashMap<String, Object>();
        String name   = request.getParameter("name");//姓名
        String code = request.getParameter("code");//编码
        String levelCateId = request.getParameter("levelCateId");
        String operationTime = request.getParameter("operationTime");
        String operationPeople = request.getParameter("operationPeople");
        String pageNoo = request.getParameter("pageNo");

        int pageNo = 1;
        if (pageNoo != null && Integer.valueOf(pageNoo) > 0) {
            pageNo = Integer.valueOf(pageNoo);
        }
        condition.put("name", name);
        condition.put("code", code);
        condition.put("levelCateId", levelCateId);
        condition.put("operationTime", operationTime);
        condition.put("operationPeople", operationPeople);
        condition.put("pageNo", pageNo);
        //查询对应设置过阅读权限的学生列表
        List<Map<String, Object>> permissionList = permissionService.getEveryList(condition);
        modelMap.addAttribute("permissionList", permissionList);
        modelMap.addAttribute("condition", condition);
        return "admin/permission/everPermission";


    }
}
