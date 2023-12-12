package dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import util.druidUtil;
import java.sql.*;
import util.paramsUtil;

public class SQLDao {


    public String doSelect(String sql) {
        ResultSet result = null;
        try (
                Connection connection = druidUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            result = statement.executeQuery();
            return toJson(result);
        } catch (SQLException e) {
            System.out.println("查询操作失败: " + e.getMessage());
            throw new DaoException("查询操作失败", e);
        } finally {
            druidUtil.close(result);
        }
    }

    public String doUpdate(String sql) {
        try (
                Connection connection = druidUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            int affectedRows = statement.executeUpdate();
            System.out.println("受影响的行数: " + affectedRows);
            return "success";
        } catch (SQLException e) {
            System.out.println("修改操作失败: " + e.getMessage());
            throw new DaoException("修改操作失败", e);
        }
    }

    public String doProcedure(JsonObject params) {
        Connection connection = null;
        CallableStatement statement = null;
        ResultSet result = null;
        try {
            connection = druidUtil.getConnection();
            statement = paramsUtil.createCallableStatement(connection, params); // Assuming this method exists in druidUtil
            result = statement.executeQuery();
            return toJson(result);
        } catch (SQLException e) {
            System.out.println("存储过程执行错误: " + e.getMessage());
            throw new DaoException("存储过程执行错误", e);
        } finally {
            druidUtil.close(result, statement, connection);
        }
    }

    private static String toJson(ResultSet resultSet) {
        if (resultSet == null) {
            return "[]";
        }

        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();

        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                JsonObject jsonObject = getJsonObject(resultSet, gson, metaData, columnCount);
                jsonArray.add(jsonObject);
            }
        } catch (SQLException e) {
            throw new DaoException("处理结果集时出错", e);
        }

        return gson.toJson(jsonArray);
    }

    private static JsonObject getJsonObject(ResultSet resultSet, Gson gson, ResultSetMetaData metaData, int columnCount) throws SQLException {
        JsonObject jsonObject = new JsonObject();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            Object columnValue = resultSet.getObject(i);
            JsonElement valueElement = columnValue != null ? gson.toJsonTree(columnValue) : null;
            jsonObject.add(columnName, valueElement);
        }

        return jsonObject;
    }

    // 自定义异常类
    public static class DaoException extends RuntimeException {
        public DaoException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
