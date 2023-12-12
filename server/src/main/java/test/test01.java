package test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/test01")
public class test01 extends HttpServlet {

    private String customParam;

    @Override
    public void init() throws ServletException {
        // 在init()方法中获取Servlet的初始参数
        customParam = getServletConfig().getInitParameter("testParam");

        // 可以在控制台输出参数值，以验证是否成功获取
        System.out.println("Servlet初始化参数 customParam 的值为: " + customParam);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        // 在doGet()方法中使用获取到的参数值
        response.getWriter().println("Servlet初始化参数 customParam 的值为: " + customParam);
    }
}
