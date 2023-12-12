package servlet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.UserDao;
import entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "doUser", value = "/doUser")
public class doUser extends HttpServlet {

    private void setAccessControlHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setAccessControlHeaders(response);
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        String data = "";
        String params = request.getParameter("params");
        JsonObject paramObject = JsonParser.parseString(params).getAsJsonObject();

        if (paramObject.has("action")) {
            UserDao uDao = new UserDao();
            String action = paramObject.get("action").getAsString();
            JsonObject userObject=paramObject.get("data").getAsJsonObject();
            String username = userObject.get("username").getAsString();
            String password = userObject.get("password").getAsString();

            if (action.equals("login")) {
                User user = uDao.search(username);
                if (user != null && user.getPassword().equals(password)) {
                    data = "success";
                } else {
                    data = "error";
                }
            } else if (action.equals("register")) {
                // 这里应该添加一些逻辑来检查用户是否已经存在
                User user = new User();
                user.setUsername(username);
                user.setPassword(password); // 注意: 应该存储加密后的密码
                user.setEmail(userObject.get("email").getAsString());
                uDao.insert(user);
                data = "success";
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);
    }
}
