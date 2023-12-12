package test;

import dao.SQLDao;
import dao.UserDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "test", value = "/test")
public class test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应的内容类型为"application/json"
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        //提取数据并转为json
        String sql="select * from product";
        SQLDao sqlDao=new SQLDao();
        String data=sqlDao.doSelect(sql);

        String username= "user001";
        UserDao uDao = new UserDao();
        User user= uDao.search(username);
        // 获取输出流并写入JSON数据
        PrintWriter out = response.getWriter();
        out.println(data);
        out.println(user.getUsername()+"  "+user.getPassword());
        // 完成响应
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
