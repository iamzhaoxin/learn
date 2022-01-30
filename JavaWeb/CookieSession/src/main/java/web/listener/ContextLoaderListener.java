package web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/30 13:52
 */

/*
    notice listener
        - JavaWeb三大组件之一（Servlet、Filter、Listener）
        - application(ServletContextListener)，session(HttpSessionListener)，request(ServletRequestListener)三个对象
          在创建、销毁、往其中增删修改属性时，自动执行代码的功能组件
 */
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 加载资源
        System.out.println("ContextLoaderListener...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 释放资源

    }
}
