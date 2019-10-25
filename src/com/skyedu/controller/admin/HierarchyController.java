package com.skyedu.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyedu.model.Hierarchy;
import com.skyedu.service.HierarchyService;

@Controller
@RequestMapping("/hierarchy")
public class HierarchyController {

	@Autowired
	private HierarchyService hierarchyService;
	
	/**
	 * 年级，学科，层次三级联动
	 */
	@RequestMapping("/subAndCate")
	@ResponseBody
	public Map<String,Object> getSubAndCate(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> data = new HashMap<String,Object>();
		String grade = request.getParameter("grade");
		//获取学科列表
		List<Map<String,Object>> subjectList = hierarchyService.getSubjectList(grade);
		data.put("subjectList", subjectList);
		if (subjectList!=null && subjectList.size()>0) {
			String subject = (String) subjectList.get(0).get("subject");
			//获取层次列表
			List<Map<String,Object>> cateList = hierarchyService.getCateList(grade, subject);
			data.put("cateList", cateList);
			if (cateList!=null && cateList.size()>0) {
				String cate = (String) cateList.get(0).get("cate");
				//获取综合层次信息
				Hierarchy hierarchy = hierarchyService.getHierarchy(grade, subject, cate);
				data.put("hierarchy", hierarchy);
			}
		}
		return data;
	}
	
	/**
	 * 年级，学科，层次三级联动
	 */
	@RequestMapping("/cateAndHier")
	@ResponseBody
	public Map<String,Object> getCateAndHier(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> data = new HashMap<String,Object>();
		String grade = request.getParameter("grade");
		String subject = request.getParameter("subject");
		//获取层次列表
		List<Map<String,Object>> cateList = hierarchyService.getCateList(grade, subject);
		data.put("cateList", cateList);
		if (cateList!=null && cateList.size()>0) {
			String cate = (String) cateList.get(0).get("cate");
			//获取综合层次信息
			Hierarchy hierarchy = hierarchyService.getHierarchy(grade, subject, cate);
			data.put("hierarchy", hierarchy);
		}
		return data;
	}
	
	/**
	 * 年级，学科，层次三级联动
	 */
	@RequestMapping("/hierarchyInfo")
	@ResponseBody
	public Map<String,Object> getHierarchyInfo(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> data = new HashMap<String,Object>();
		String grade = request.getParameter("grade");
		String subject = request.getParameter("subject");
		String cate = request.getParameter("cate");
		//获取综合层次信息
		Hierarchy hierarchy = hierarchyService.getHierarchy(grade, subject, cate);
		data.put("hierarchy", hierarchy);
		return data;
	}
}
