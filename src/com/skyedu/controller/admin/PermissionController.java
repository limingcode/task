package com.skyedu.controller.admin;

import com.skyedu.model.EduStudent;
import com.skyedu.model.Question;
import com.skyedu.service.BasePermissionService;
import com.skyedu.service.BaseService;
import com.skyedu.service.MessageService;
import com.skyedu.service.PermissionService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

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
    @Autowired
    private BasePermissionService baseService;

    /**
     * 显示班级权限控制列表  只有设置阅读的才显示
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("classPermission")
    public String classPermission(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        //查询对应的数据设置

        Map<String, Object> map = new HashMap<>();
        String cityCode = request.getParameter("");//地区

        String xueQiName = request.getParameter("");//学期名称

        String className = request.getParameter("");//班级名称

        String teacherName = request.getParameter("");//老师

        String school = request.getParameter("");//校区
        String grageName = request.getParameter("");//年级

        String cate = request.getParameter("");//层次
        String attendTime = request.getParameter("");//上课时间
        String betweenTime = request.getParameter("");//时段
        String permission = request.getParameter("");//阅读权限
        String optionTime = request.getParameter("");//操作时间
        String optionPeople = request.getParameter("");//最后操作人

        map.put("", cityCode);
        map.put("", cityCode);
        map.put("", cityCode);

        List<Map<String, Object>> maps = permissionService.getclassPermissionList(map);
        modelMap.put("maps", maps);
        modelMap.put("map", map);


        return "admin/permission/classPermission";
    }

    /**
     * 个人操作权限列表
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     */

    @RequestMapping("everPermission")
    public String everPermission(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Object> condition = new HashMap<String, Object>();
        String id = request.getParameter("id");//姓名
        String name = request.getParameter("name");//姓名
        String code = request.getParameter("code");//编码
        String bookName = request.getParameter("bookName");//层次级别
        String operationTime = request.getParameter("operationTime");//操作时间
        String operationPeople = request.getParameter("operationPeople");//操作人
        String pageNoo = request.getParameter("pageNo");

        int pageNo = 1;
        if (pageNoo != null && Integer.valueOf(pageNoo) > 0) {
            pageNo = Integer.valueOf(pageNoo);
        }
        condition.put("id", id);
        condition.put("name", name);
        condition.put("code", code);
        condition.put("bookName", bookName);
        condition.put("operationTime", operationTime);
        condition.put("operationPeople", operationPeople);
        condition.put("pageNo", pageNo);
        //查询对应设置过阅读权限的学生列表
        List<Map<String, Object>> permissionList = permissionService.getEveryList(condition);
        modelMap.addAttribute("permissionList", permissionList);
        modelMap.addAttribute("condition", condition);
        return "admin/permission/everPermission";


    }

    /**
     * 个人权限的新增操作
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     * 进入新增页面加载初始数据
     */

    @RequestMapping("addEverPermission")
    public String addEverPermissiondel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Object> condition = new HashMap<String, Object>();
        String name = request.getParameter("name");//姓名
        String code = request.getParameter("code");//编码
        String bookName = request.getParameter("bookName");//层次级别
        String pageNoo = request.getParameter("pageNo");
        int pageNo = 1;
        if (pageNoo != null && Integer.valueOf(pageNoo) > 0) {
            pageNo = Integer.valueOf(pageNoo);
        }
        condition.put("name", name);
        condition.put("code", code);
        condition.put("bookName", bookName);
        condition.put("pageNo", pageNo);
        //查询对应设置过阅读权限的学生列表
        List<Map<String, Object>> permissionList = permissionService.getEveryList(condition);
        modelMap.addAttribute("permissionList", permissionList);
        modelMap.addAttribute("condition", condition);


        return "admin/permission/addEverPermission";
    }

    /**
     * 根据具体的学生进行编辑操作
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("editorPermission")
    public String editorPermission(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Object> condition = new HashMap<String, Object>();
        String city = request.getParameter("city");//地区
        String grade = request.getParameter("grade");//获取年级
        String subject = request.getParameter("subject");//科目

        condition.put("city", city);
        condition.put("grade", grade);
        condition.put("subject", subject);


        int pageNo = 1;
        List<Map<String, Object>> getCity = baseService.getCity();
        List<Map<String, Object>> gradeList = baseService.gradeList();
        List<Map<String, Object>> subjectList = baseService.subjectList();
        modelMap.addAttribute("getCity", getCity);
        modelMap.addAttribute("gradeList", gradeList);
        modelMap.addAttribute("subjectList", subjectList);



        return "admin/permission/euditPermission";
    }

    /**
     * 个人权限的删除操作
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("delPermissiondel")
    @ResponseBody
    public int delQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String everId = request.getParameter("everId");
        HttpSession session = request.getSession();
        int row = permissionService.delStudent(Integer.valueOf(everId));
        if (row<0){
                throw new RuntimeException("删除学生执行失败!!!");
        }
        return row;
    }

    /**
     * 编辑总体的  对书籍进行编辑
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("euditEverPermission")
    public String euditEverPermission(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
            //个人权限数据编辑查询 返回


        return "admin/permission/euditPermission";
    }

    /**
     * 统一跳转到设置页面 并且查询对应的数据
     */
    @RequestMapping("setEverPermission")

    public String setEverPermission(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Object> condition = new HashMap<String, Object>();
        String city = request.getParameter("city");//地区
        String grade = request.getParameter("grade");//获取年级
        String subject = request.getParameter("subject");//科目
        String pageNoo = request.getParameter("pageNo");
        condition.put("city", city);
        condition.put("grade", grade);
        condition.put("subject", subject);


        int pageNo = 1;
        if (pageNoo != null && Integer.valueOf(pageNoo) > 0) {
            pageNo = Integer.valueOf(pageNoo);

        }
        List<Map<String, Object>> getCity = baseService.getCity();
        List<Map<String, Object>> gradeList = baseService.gradeList();
        List<Map<String, Object>> subjectList = baseService.subjectList();
        modelMap.addAttribute("getCity", getCity);
        modelMap.addAttribute("gradeList", gradeList);
        modelMap.addAttribute("subjectList", subjectList);

        return "admin/permission/setEverPermission";





    }

}
