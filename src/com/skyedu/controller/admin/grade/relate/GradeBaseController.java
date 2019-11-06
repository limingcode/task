package com.skyedu.controller.admin.grade.relate;

import com.skyedu.controller.admin.commons.Msg;
import com.skyedu.model.EduCity;
import com.skyedu.model.EduGrade;
import com.skyedu.model.EduPeriod;
import com.skyedu.service.GradeBaseRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description xxx
 * @author: LiMing
 * @date: 2019/11/6  9:47
 * 班级特有的相关操作
 */
@Controller
@RequestMapping("/grades/")
public class GradeBaseController {
    /**
     * 注入对应的特殊处理service
     */
    @Autowired
    private GradeBaseRelateService gradeBaseRelateService;

    /**
     * get请求获取 地区列表
     *
     * @return
     */
    @RequestMapping(value = "getIiniTtermName", method = RequestMethod.GET)
    @ResponseBody
    public Msg getIiniTtermName() {
        List<EduCity> eduCities = gradeBaseRelateService.getIiniTtermName();
        System.out.println(eduCities+"size"+eduCities.size());
        if (eduCities == null || eduCities.size() < 0) {
            return Msg.fail();
        }


        return Msg.success().add("grades", eduCities);
    }

    /**
     * get请求获取  学期列表
     *
     * @return
     */
    @RequestMapping(value = "getInitPeriod", method = RequestMethod.GET)
    @ResponseBody
    public Msg getInitGrade() {
        List<EduPeriod> eduPeriods = gradeBaseRelateService.getInitPeriod();
        System.out.println("aaaa"+eduPeriods+"size================"+eduPeriods.size());
        if (eduPeriods == null || eduPeriods.size() < 0) {
            return Msg.fail();
        }


        return Msg.success().add("eduPeriods", eduPeriods);
    }
    /**
     * 单个批量二合一
     * 批量删除：1-2-3
     * 单个删除：1
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
    public Msg deletOnAndMany(@PathVariable("ids") String ids){
        /**判断包含你ids的属性*/
        if (ids.contains("-")){
           // List<>
        }
        return null;
    }



}
