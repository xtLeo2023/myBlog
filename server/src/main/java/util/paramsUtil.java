package util;

import com.google.gson.JsonObject;

import java.sql.*;

public class paramsUtil {
    private static String params;

    public static void setParams(String newParams) {
        params = newParams;
    }

    public static JsonObject getParams(Connection connection, String procedure) throws SQLException {
        JsonObject paramsInfo = new JsonObject();
        String query = "SELECT parameter_name, data_type FROM information_schema.parameters WHERE specific_name = ? ORDER BY ordinal_position";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, procedure);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String paramName = rs.getString("parameter_name");
                    String paramType = rs.getString("data_type");
                    paramsInfo.addProperty(paramName, paramType);
                }
            }
        }
        return paramsInfo;
    }

    public static CallableStatement createCallableStatement(Connection connection, JsonObject params) throws SQLException {
        String procedure = params.get("procedure").getAsString();
        JsonObject paramsInfo = getParams(connection, procedure);
        CallableStatement callableStatement;

        StringBuilder callStatement = new StringBuilder("{call " + procedure);
        if (!paramsInfo.keySet().isEmpty()) {
            callStatement.append("(");
            for (String paramName : paramsInfo.keySet()) {
                if (!paramName.equals("procedure")) {
                    callStatement.append("?, ");
                }
            }
            callStatement = new StringBuilder(callStatement.substring(0, callStatement.length() - 2) + ")}");
        } else {
            callStatement.append("()}");
        }

        callableStatement = connection.prepareCall(callStatement.toString());

        int paramIndex = 1;
        for (String paramName : paramsInfo.keySet()) {
            if (!paramName.equals("procedure")) {
                String paramValue = params.get(paramName).getAsString();
                String paramType = paramsInfo.get(paramName).getAsString();
                setCallableStatementParameter(callableStatement, paramIndex, paramType, paramValue);
                paramIndex++;
            }
        }

        return callableStatement;
    }

    private static void setCallableStatementParameter(CallableStatement cs, int paramIndex, String paramType, String paramValue) throws SQLException {
        // 根据参数类型设置不同的方法
        switch (paramType.toLowerCase()) {
            case "integer":
                cs.setInt(paramIndex, Integer.parseInt(paramValue));
                break;
            case "float":
                cs.setFloat(paramIndex, Float.parseFloat(paramValue));
                break;
            case "double":
                cs.setDouble(paramIndex, Double.parseDouble(paramValue));
                break;
            // 添加更多数据类型的处理
            default:
                cs.setString(paramIndex, paramValue);
                break;
        }
    }
}
