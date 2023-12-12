package listener;

import com.alibaba.druid.pool.DruidDataSource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DruidContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 初始化逻辑，如果需要的话
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Closing Druid connection pool...");
        DruidDataSource dataSource = (DruidDataSource) sce.getServletContext().getAttribute("druidDataSource");
        if (dataSource != null) {
            dataSource.close();
            System.out.println("Druid connection pool closed successfully.");
        } else {
            System.out.println("Druid connection pool not found.");
        }
    }

}
