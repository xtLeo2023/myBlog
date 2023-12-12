package servlet;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.SQLDao;
import dao.TreeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "doSQL", value = "/doSQL")
public class doSQL extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 允许来自任何源的跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // 处理前端请求并返回响应
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        String data="";
        String params=request.getParameter("params");   //提取数据并转为json
        // 使用 Gson 的 JsonParser 解析 JSON 字符串
        JsonObject paramObject = JsonParser.parseString(params).getAsJsonObject();
        if (paramObject.has("sql")){
            //"sql"键存在
            SQLDao sqlDao=new SQLDao();
            String sql=paramObject.get("sql").getAsString();
            if(paramObject.has("update")){
                data= sqlDao.doUpdate(sql);
            }else {
                data=sqlDao.doSelect(sql);
            }
        } else if(paramObject.has("procedure")){
            //"procedure"键存在
            if(paramObject.has("tree")){
                //"tree"键存在
                TreeDao treeDao=new TreeDao();
                data=treeDao.doTree(paramObject);
            }else{
                SQLDao sqlDao=new SQLDao();
                data=sqlDao.doProcedure(paramObject);
            }
        }

        // 将 JSON 数据写入响应
        try {
            PrintWriter out = response.getWriter();
            out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
