package com.example.spectacole_iss.repository;

import com.example.spectacole_iss.model.User;
import com.example.spectacole_iss.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UserDBRepository {

    private JDBCUtils jdbcUtils;

    public UserDBRepository(Properties properties) {
        this.jdbcUtils = new JDBCUtils(properties);
    }

    public User adaugaUser(User user) {
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into users(nume, parola, type, username) values(?, ?, ?, ?)"
        )) {
            preparedStatement.setString(1, user.getNume());
            preparedStatement.setString(2, user.getParola());
            preparedStatement.setString(3, user.getType().toString());
            preparedStatement.setString(4, user.getUsername());
            int result = preparedStatement.executeUpdate();
            if (result == 0) return null;
            return user;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public User cautaUser(String username, String parola, User.Type type) {
        Connection connection = jdbcUtils.getConnection();
        int id=-1;
        String nume = null;
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement("select id, nume, username, parola, type from users where username=? and parola=? and type=?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, parola);
            preparedStatement.setString(3, String.valueOf(type));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) return null;
            while (resultSet.next()) {
                id = resultSet.getInt(1);
                nume = resultSet.getString(2);
                username = resultSet.getString(3);
                parola = resultSet.getString(4);
                type = User.Type.valueOf(resultSet.getString(5));
            }
            return new User(id, nume, username, parola, type);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
