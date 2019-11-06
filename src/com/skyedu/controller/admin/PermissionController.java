package com.skyedu.controller.admin;

import com.alibaba.fastjson.JSONPObject;
import com.skyedu.model.EduCity;
import com.skyedu.model.EduPeriod;
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


        Map<String, Object> map = new HashMap<>(16);
        /**
         * SELECT a.type,a.createTime,i.CityName,e.termName,d.name as dname,j.name as jname,g.name as gname,f.name as fname,h.name as hname,d.courseTime,
         * c.bookName,a.operationTime,a.operationPeople FROM Tk_rule_base_t a LEFT JOIN Tk_rule_mapping_t b ON a.typeId = b.typeId LEFT JOIN IM_Book
         * c ON b.bookId = c.id LEFT JOIN Edu_Course d ON c.id = d.id LEFT JOIN Edu_Period e ON d.period = e.id LEFT JOIN Edu_Grade
         * f ON d.grade = f.code LEFT JOIN Edu_Depa g ON d.depa = g.code LEFT JOIN Edu_Cate h ON d.cate = h.code LEFT JOIN Edu_City
         * i ON c.cityId = i.CityId LEFT JOIN Edu_Teacher j ON  d.id=j.id WHERE a.type = 1";
         *
         */
        //操作时间
        String createTime = request.getParameter("createTime");
        //创建时间
        //地区
        String cityName = request.getParameter("CityName");
        //学期名称
        String termName = request.getParameter("termName");
        //班级名称
        String dname = request.getParameter("dname");
        //老师
        String jname = request.getParameter("jname");
        //校区
        String gname = request.getParameter("gname");
        //年级
        String fname = request.getParameter("fname");
        //层次
        String hname = request.getParameter("hname");
        //上课时间
        String courseTime = request.getParameter("courseTime");
        String bookName = request.getParameter("bookName");
        String operationTime = request.getParameter("operationTime");
        String operationPeople = request.getParameter("operationPeople");


        map.put("createTime", createTime);
        map.put("CityName", cityName);
        map.put("termName", termName);
        map.put("dname", dname);
        map.put("jname", jname);
        map.put("gname", gname);
        map.put("fname", fname);
        map.put("hname", hname);
        map.put("courseTime", courseTime);
        map.put("bookName", bookName);
        map.put("operationTime", operationTime);
        map.put("operationPeople", operationPeople);
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
        Map<String, Object> condition = new HashMap<String, Object>(16);
        //姓名
        String id = request.getParameter("id");
        //姓名
        String name = request.getParameter("name");
        //编码
        String code = request.getParameter("code");
        //层次级别
        String bookName = request.getParameter("bookName");
        //操作时间
        String operationTime = request.getParameter("operationTime");
        //操作人
        String operationPeople = request.getParameter("operationPeople");
        //分页参数
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
     * @return 进入新增页面加载初始数据
     */

    @RequestMapping("addEverPermission")
    public String addEverPermissiondel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Object> condition = new HashMap<String, Object>(16);
        //姓名
        String name = request.getParameter("name");
        //编码
        String code = request.getParameter("code");
        //层次级别
        String bookName = request.getParameter("bookName");

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
        Map<String, Object> condition = new HashMap<String, Object>(16);
        //地区
        String city = request.getParameter("city");
        //获取年级
        String grade = request.getParameter("grade");
        //科目
        String subject = request.getParameter("subject");

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
        Map<String, Object> map = new HashMap<String, Object>(16);
        String everId = request.getParameter("everId");
        HttpSession session = request.getSession();
        int row = permissionService.delStudent(Integer.valueOf(everId));
        if (row < 0) {
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
        //TODO


        return "admin/permission/euditPermission";
    }

    /**
     * 统一跳转到设置页面 并且查询对应的数据
     */
    @RequestMapping("setEverPermission")

    public String setEverPermission(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Object> condition = new HashMap<String, Object>(16);
        //地区
        String city = request.getParameter("city");
        //获取年级
        String grade = request.getParameter("grade");
        //科目
        String subject = request.getParameter("subject");
        //
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
    @RequestMapping("setEverPermissionClass")

    public String setEverPermissionClass(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Object> condition = new HashMap<String, Object>(16);
        //地区
        String city = request.getParameter("city");
        //获取年级
        String grade = request.getParameter("grade");
        //科目
        String subject = request.getParameter("subject");
        //
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

        return "admin/permission/setEverPermissionClass";


    }

}
