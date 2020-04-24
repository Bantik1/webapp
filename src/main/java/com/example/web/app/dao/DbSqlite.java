package com.example.web.app.dao;

import com.example.web.app.dao.model.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DbSqlite implements InitializingBean {
    private Logger log = Logger.getLogger(getClass().getName());

    private String dbPath = "webappp-example.db";

    @Override
    public void afterPropertiesSet() throws Exception {
        initDb();
    }

    public void initDb() {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
            }
        } catch (ClassNotFoundException | SQLException ex) {
            log.log(Level.WARNING, "База не подключена", ex);
        }
    }

    public Boolean execute(String query) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            return stat.execute(query);
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return false;
        }
    }

    public Boolean getNickname(String nick) {
        String query = "select NICKNAME from USER where NICKNAME = '%s'";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(String.format(query, nick));
            return resultSet.next();
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return null;
        }
    }

    public Boolean addUser(User user) {
        String query = "insert into USER (nickname, name, birthday, phone_number, group_number, " +
                "info, gender, password, role) values ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')";
        System.out.println("Запрос: " + String.format(query, user.getNickname(), user.getName(), user.getBirthdayTime(), user.getNumberPhone(),
                user.getGroupNumber(), user.getInfo(), user.getGender(), user.getPassword(), user.getRole()));
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            return stat.execute(String.format(query, user.getNickname(), user.getName(), user.getBirthdayTime(), user.getNumberPhone(),
                    user.getGroupNumber(), user.getInfo(), user.getGender(), user.getPassword(), user.getRole()));
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return null;
        }
    }

    public User getLoginUser(String nick) {
        String query = "select * from USER where NICKNAME = '%s'";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(String.format(query, nick));
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setName(resultSet.getString("name"));
            user.setNickname(resultSet.getString("nickname"));
            user.setNumberPhone(resultSet.getString("phone_number"));
            user.setGroupNumber(resultSet.getString("group_number"));
            user.setInfo(resultSet.getString("info"));
            user.setGender(resultSet.getString("gender"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(resultSet.getString("role"));
            return user;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return new User();
        }
    }

    public User getFirstUser() {
        String query = "select * from USER limit 1";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setName(resultSet.getString("name"));
                user.setNickname(resultSet.getString("nickname"));
                user.setNumberPhone(resultSet.getString("phone_number"));
                user.setGroupNumber(resultSet.getString("group_number"));
                user.setInfo(resultSet.getString("info"));
                user.setGender(resultSet.getString("gender"));
                return user;
            } else return new User();
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return new User();
        }
    }

    public User nextUser(int id) {
        String query = "select * from USER where ID > %s limit 1";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(String.format(query, id));
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setName(resultSet.getString("name"));
                user.setNickname(resultSet.getString("nickname"));
                user.setNumberPhone(resultSet.getString("phone_number"));
                user.setGroupNumber(resultSet.getString("group_number"));
                user.setInfo(resultSet.getString("info"));
                user.setGender(resultSet.getString("gender"));
                return user;
            } else return new User();
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return new User();
        }
    }

    public User prevUser(int id) {
        String query = "select * from USER where ID < %s order by id desc limit 1";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(String.format(query, id));
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setName(resultSet.getString("name"));
                user.setNickname(resultSet.getString("nickname"));
                user.setNumberPhone(resultSet.getString("phone_number"));
                user.setGroupNumber(resultSet.getString("group_number"));
                user.setInfo(resultSet.getString("info"));
                user.setGender(resultSet.getString("gender"));
                return user;
            } else return new User();
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return new User();
        }
    }
}