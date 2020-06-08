package com.atguigu.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        //获取当前项目的路径
        String path = servletContext.getContextPath();
        servletContext.setAttribute("path",path);

    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
