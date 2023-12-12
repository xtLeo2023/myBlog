package util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/*
Druid连接池的工具类
 */
public class druidUtil {
    //1.定义成员变量 DataSource
    private static DataSource dataSource;
    static {
        Properties properties = new Properties();
        try {
            // 确保文件路径正确
            InputStream inputStream = druidUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            if (inputStream == null) {
                // 文件未找到，抛出异常
                throw new RuntimeException("未在类路径中找到 druid.properties 文件");
            }
            properties.load(inputStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
            if (dataSource == null) {
                // 数据源未成功创建，抛出异常
                throw new RuntimeException("Druid 数据源未能创建");
            }
        } catch (IOException e) {
            throw new RuntimeException("加载 druid.properties 文件时出错", e);
        } catch (Exception e) {
            throw new RuntimeException("创建 Druid 数据源时出错", e);
        }
    }

    //获取连接
    public static Connection getConnection() throws SQLException {
        System.out.println("获取connection:"+dataSource.getConnection());
        return dataSource.getConnection();
    }

    //获取连接池
    public static DataSource getDataSource(){
        return dataSource;
    }

    //释放资源
    public static void close(Connection connection) {
        if (connection!=null){
            try {
                System.out.println("关闭 connection:"+connection);
                connection.close();//归还连接
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet!=null){
            try {
                System.out.println("关闭 resultSet:"+resultSet);
                resultSet.close();//归还连接
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void close(Statement statement) {
        if (statement!=null){
            try {
                System.out.println("关闭 statement:"+statement);
                statement.close();//归还连接
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static void close(PreparedStatement statement) {
        if (statement!=null){
            try {
                System.out.println("关闭 statement:"+statement);
                statement.close();//归还连接
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void close(CallableStatement statement) {
        if (statement!=null){
            try {
                System.out.println("关闭 statement:"+statement);
                statement.close();//归还连接
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void close(ResultSet resultSet, Statement statement, Connection connection){
        if (resultSet!=null) {
            try {
                System.out.println("关闭 resultSet:"+resultSet);
                resultSet.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (statement!=null) {
            try {
                System.out.println("关闭 statement:"+statement);
                statement.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (connection!=null){
            try {
                System.out.println("关闭 connection:"+connection);
                connection.close();//归还连接
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void close(ResultSet resultSet, PreparedStatement statement, Connection connection){
        if (resultSet!=null) {
            try {
                System.out.println("关闭 resultSet:"+resultSet);
                resultSet.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (statement!=null) {
            try {
                System.out.println("关闭 statement:"+statement);
                statement.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (connection!=null){
            try {
                System.out.println("关闭 connection:"+connection);
                connection.close();//归还连接
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void close(ResultSet resultSet, CallableStatement statement, Connection connection){
        if (resultSet!=null) {
            try {
                System.out.println("关闭 resultSet:"+resultSet);
                resultSet.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (statement!=null) {
            try {
                System.out.println("关闭 statement:"+statement);
                statement.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (connection!=null){
            try {
                System.out.println("关闭 connection:"+connection);
                connection.close();//归还连接
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
