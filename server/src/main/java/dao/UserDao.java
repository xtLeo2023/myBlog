package dao;

import entity.User;
import util.druidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public void insert(User user) {
        String sql = "insert into user values (?, ?, ?, null)";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = druidUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            druidUtil.close(statement);
            druidUtil.close(connection);
        }
    }

    public User search(String username) {
        User user = new User();
        String sql = "select * from user where username = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            connection = druidUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            result = statement.executeQuery();

            if (result.next()) {
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            druidUtil.close(result);
            druidUtil.close(statement);
            druidUtil.close(connection);
        }

        return user;
    }
}
