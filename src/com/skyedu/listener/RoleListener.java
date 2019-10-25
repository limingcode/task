package com.skyedu.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.skyedu.service.RoleService;
import com.util.SystemConfigures;


public class RoleListener implements ServletContextListener {

    @Override  
    public void contextDestroyed(ServletContextEvent event) {  
        // TODO Auto-generated method stub  
  
    }  
  
    @Override  
    public void contextInitialized(ServletContextEvent event) {  
  
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());  
        RoleService roleService = (RoleService) context.getBean("roleService"); 
        List<String> roleList = roleService.getRoleList();
        SystemConfigures.setRoleUrls(roleList);
    }  
}
